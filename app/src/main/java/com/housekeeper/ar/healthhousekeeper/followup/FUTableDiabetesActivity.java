package com.housekeeper.ar.healthhousekeeper.followup;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.FlowLayout;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyDialog;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;
import com.housekeeper.ar.healthhousekeeper.followup.view.MyTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lenovo on 2016/3/5.
 */
//随访表-糖尿病
public class FUTableDiabetesActivity extends BaseActivity {


    String TAG = "FUTableDiabetesActivity";



    String http, httpUrl;

    private ToastCommom toastCommom;

    //足背动脉搏动
    private Spinner dorsalisPedisArteryPulseSpinner;
    //心理调整
    private Spinner  psychologicalRecoverySpinner;
    //遵医行为
    private Spinner  followingDoctorInstructionBehaviorSpinner;


    private String dpsPluseString,psyRecoveryString,fdiBehaviorString;


    private Button backBtn;



    private TextView waterIntakeHighValueTV,waterIntakeLowValueTV;
    private String waterIntakeHighValue,waterIntakeLowValue;


    private TextView urineVolumeHighValueTV,urineVolumeLowValueTV;
    private String urineVolumeHighValue,urineVolumeLowValue;


    private EditText urineVolumeET,waterIntakeET;
    private TextView urineVolumeLevelTV,waterIntakeLevelTV;

    private String urineVolumeValueString,waterIntakeValueString;
    private TextView auxAddTV;
    MyDialog dialog;
    public ArrayList<HashMap<String, String>> currentAuxList= new ArrayList<HashMap<String, String>>();//当前辅助检查列表
    //存储选中的检查名称
    public ArrayList<HashMap<String, String>> selectAuxLists= new ArrayList<HashMap<String, String>>();

    public ArrayList<HashMap<String, String>> allAuxLists= new ArrayList<HashMap<String, String>>();
    private FlowLayout mSelectedAuxCheckFlowLayout;

    private LinearLayout auxAddLayout;

//    private Button personalAuxBtn,healthyKeeperAuxBtn,hospitalAuxBtn;

    private FUTableAuxBiochemicalItemsView biochemicalItemsView;

    private FUTableAuxBloodSugarView bloodSugarView;

    private FUTableAuxBloodRoutineView bloodRoutineView;

    private FUTableAuxUrineRoutineView urineRoutineView;

    private FUTableAuxLiverFunctionView liverFunctionView;

    private FUTableAuxRenalFunctionView renalFunction;

    private FUTableAuxHemorheologyParamView hemorheologyParamView;


    private ScrollView sv;


    private MyTextView waterIntakeTV;
    private MyTextView urineVolumeTV;
    private MyTextView smokeAmountTV;
    private MyTextView drinkingAmountTV;
    private MyTextView sportTimeTV;
    private MyTextView saltAmountTV;
    private MyTextView foodAmountTV;


    private FlowLayout symptomFlowLayout;

    //    private List<HashMap<String,String>> symptomLists = new ArrayList<>();
    //所有的症状列表
    private List<String> allSymptomLists = new ArrayList<>();
    //选中的症状列表
    private List<String> selectedSymptomLists = new ArrayList<>();

    private EditText smokeAmountET,drinkAmountET,sportCountET,sportMinutesET,eatAmountET,saltAmountET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_table_diabetes_new);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(FUTableDiabetesActivity.this);

        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sv = (ScrollView)findViewById(R.id.sv);

        smokeAmountET = (EditText)findViewById(R.id.daily_smoke_amount_et);
        drinkAmountET = (EditText)findViewById(R.id.daily_drinking_amount_et);
        sportCountET = (EditText)findViewById(R.id.sport_times_et);
        sportMinutesET = (EditText)findViewById(R.id.sport_min_et);
        eatAmountET = (EditText)findViewById(R.id.food_amount_et);
        saltAmountET = (EditText)findViewById(R.id.salt_amount_et);

        symptomFlowLayout = (FlowLayout)findViewById(R.id.symptom_flowlayout);

        waterIntakeTV = (MyTextView)findViewById(R.id.water_intake_tv);
        urineVolumeTV = (MyTextView)findViewById(R.id.urine_volume_tv);
        smokeAmountTV = (MyTextView)findViewById(R.id.daily_smoke_amount_tv);
        drinkingAmountTV = (MyTextView)findViewById(R.id.daily_drinking_amount_tv);
        sportTimeTV = (MyTextView)findViewById(R.id.sport_times_tv);
        foodAmountTV = (MyTextView)findViewById(R.id.food_amount_tv);
        saltAmountTV = (MyTextView)findViewById(R.id.salt_amount_tv);


        urineVolumeET = (EditText)findViewById(R.id.urine_volume_et);
        urineVolumeLevelTV = (TextView)findViewById(R.id.urine_volume_level_tv);

        urineVolumeHighValueTV = (TextView)findViewById(R.id.urine_volume_high_normal_tv);
        urineVolumeLowValueTV = (TextView)findViewById(R.id.urine_volume_low_normal_tv);
        urineVolumeHighValue = urineVolumeHighValueTV.getText().toString();
        urineVolumeLowValue = urineVolumeLowValueTV.getText().toString();


        waterIntakeET = (EditText)findViewById(R.id.water_intake_et);
        waterIntakeLevelTV = (TextView)findViewById(R.id.water_intake_level_tv);

        waterIntakeHighValueTV = (TextView)findViewById(R.id.water_intake_high_normal_tv);
        waterIntakeLowValueTV = (TextView)findViewById(R.id.water_intake_low_normal_tv);



        waterIntakeHighValue = waterIntakeHighValueTV.getText().toString();
        waterIntakeLowValue = waterIntakeLowValueTV.getText().toString();

        urineVolumeET.addTextChangedListener(urineVolumeWatcher);
        waterIntakeET.addTextChangedListener(waterIntakeWatcher);


        auxAddTV = (TextView)findViewById(R.id.add_tv);
        auxAddTV.setOnClickListener(clickListener);
        auxAddLayout = (LinearLayout)findViewById(R.id.aux_add_layout);



        dorsalisPedisArteryPulseSpinner = (Spinner)findViewById(R.id.dorsalis_pedis_artery_pulse_spinner);

        psychologicalRecoverySpinner = (Spinner)findViewById(R.id.psychological_recovery_spinner);

        followingDoctorInstructionBehaviorSpinner = (Spinner)findViewById(R.id.following_doctor_instruction_behavior_spinner);



        currentAuxList.clear();

        initSpinner();

        initData();
        initXChartData();

    }
    @Override
    protected void onResume() {
        super.onResume();
//        init();
    }
    //初始化图像数据
    private void initXChartData(){
        double[] radarViewData = {30,60,60,60,80,50,10,20}; //各维度分值
        String[] radarViewTitles = {"测试1","测试2","测试3","测试4","测试5","测试6"};//各维度名称
        //设置饮水量的六边形数据
        waterIntakeTV.setRadarViewData(radarViewData);
        waterIntakeTV.setRadarViewMaxValue(90);
        waterIntakeTV.setRadarViewTitles(radarViewTitles);
        //设置饮水量的折线数据
        String title="饮水量";
        int yMax = 100; //y轴最大值
        int ySteps = 10;//Y轴间隔值
        int yDetailSteps = 1;//Y轴文字显示间隔数，Y轴每隔ySteps*yDetailSteps显示一次文字，如10*1=10，在0，10，20...处显示文字
        LinkedList<String> xLabels = new LinkedList<>(); //X轴数值

        //线段文字集合
        List<String> dataTextList = new ArrayList<>();
        //线段颜色集合，若成员HashMap<String,Integer>是-1，-1，-1，则设置成默认值绿色58,171,119
        List<HashMap<String,Integer>>dataColorList = new ArrayList<>();
        //线段值集合
        List<LinkedList<Double>>dataList = new ArrayList<>();

        for(int i=0;i<8;i++){
            xLabels.add(i+"");
        }
        for(int j=0;j<3;j++){
            if(j==0){
                dataTextList.add("低");
                HashMap<String,Integer> map = new HashMap<>();
                map.put("r",245);
                map.put("g",30);
                map.put("b",150);
                dataColorList.add(map);

                LinkedList<Double> dataSeries1= new LinkedList<Double>();
                dataSeries1.add(40d);
                dataSeries1.add(48d);
                dataSeries1.add(50d);
                dataSeries1.add(56d);
                dataList.add(dataSeries1);
            }
            if(j==1){
                dataTextList.add("高");
                HashMap<String,Integer> map = new HashMap<>();
                map.put("r",20);
                map.put("g",3);
                map.put("b",15);
                dataColorList.add(map);

                LinkedList<Double> dataSeries1= new LinkedList<Double>();
                dataSeries1.add(60d);
                dataSeries1.add(88d);
                dataSeries1.add(80d);
                dataSeries1.add(76d);
                dataList.add(dataSeries1);
            }
            if(j==2){
                dataTextList.add("正常");
                HashMap<String,Integer> map = new HashMap<>();
                map.put("r",-1);
                map.put("g",-1);
                map.put("b",-1);
                dataColorList.add(map);

                LinkedList<Double> dataSeries1= new LinkedList<Double>();
                dataSeries1.add(10d);
                dataSeries1.add(50d);
                dataSeries1.add(50d);
                dataSeries1.add(60d);
                dataSeries1.add(16d);
                dataSeries1.add(5d);
                dataList.add(dataSeries1);
            }
        }
        waterIntakeTV.setLineData(title,yMax,ySteps,yDetailSteps,xLabels,dataTextList,dataColorList,dataList);

        //设置六边形数据
        urineVolumeTV.setRadarViewData(radarViewData);
        urineVolumeTV.setRadarViewMaxValue(100);
        urineVolumeTV.setRadarViewTitles(radarViewTitles);
        //设置折线数据
        urineVolumeTV.setLineData(urineVolumeTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        smokeAmountTV.setRadarViewData(radarViewData);
        smokeAmountTV.setRadarViewMaxValue(100);
        smokeAmountTV.setRadarViewTitles(radarViewTitles);
        //设置折线数据
        smokeAmountTV.setLineData(smokeAmountTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        drinkingAmountTV.setRadarViewData(radarViewData);
        drinkingAmountTV.setRadarViewMaxValue(100);
        drinkingAmountTV.setRadarViewTitles(radarViewTitles);
        //设置折线数据
        drinkingAmountTV.setLineData(drinkingAmountTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        sportTimeTV.setRadarViewData(radarViewData);
        sportTimeTV.setRadarViewMaxValue(100);
        sportTimeTV.setRadarViewTitles(radarViewTitles);
        //设置折线数据
        sportTimeTV.setLineData(sportTimeTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        saltAmountTV.setRadarViewData(radarViewData);
        saltAmountTV.setRadarViewMaxValue(100);
        saltAmountTV.setRadarViewTitles(radarViewTitles);
        //设置折线数据
        saltAmountTV.setLineData(saltAmountTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        foodAmountTV.setRadarViewData(radarViewData);
        foodAmountTV.setRadarViewMaxValue(100);
        foodAmountTV.setRadarViewTitles(radarViewTitles);
        //设置折线数据
        foodAmountTV.setLineData(foodAmountTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.add_tv:
                    addAuxCheck();
                    break;
            }
        }
    };
    //增加辅助检查
    private void addAuxCheck(){
        LayoutInflater inflater =getLayoutInflater();
        final View functionListView = inflater.inflate(R.layout.activity_follow_up_table_diabetes_add_aux_dialog, null);
//		dialog = new AlertDialog.Builder(DiagnosisActivity.this, R.style.load_dialog).setView(functionListView).show();

        dialog = new MyDialog(FUTableDiabetesActivity.this, functionListView,R.style.load_dialog);
        int width = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
//		int height = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

        getAllAuxCheckData();

        selectAuxLists.clear();
        selectAuxLists.addAll(currentAuxList);
//        selectAuxLists = currentAuxList;

        initSelectedAuxCheckChildView();

        Button dialogOkBtn = (Button)functionListView.findViewById(R.id.ok_btn);
        Button dialogCancelBtn = (Button)functionListView.findViewById(R.id.cancel_btn);

        dialogCancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        //添加所选中的检查
        dialogOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "selectAuxLists " + selectAuxLists);
                currentAuxList.clear();
                currentAuxList.addAll(selectAuxLists);
                updateAuxCheckView();
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    private void getAllAuxCheckData(){
        allAuxLists.clear();
        HashMap<String,String> map = new HashMap<>();
        map.put("name", "血常规");
        allAuxLists.add(map);

        HashMap<String,String> map2 = new HashMap<>();
        map2.put("name","尿常规");
        allAuxLists.add(map2);


        HashMap<String,String> map3 = new HashMap<>();
        map3.put("name", "肝功能");
        allAuxLists.add(map3);


        HashMap<String,String> map4 = new HashMap<>();
        map4.put("name", "肾功能");
        allAuxLists.add(map4);

        HashMap<String,String> map5 = new HashMap<>();
        map5.put("name", "生化全项");
        allAuxLists.add(map5);

        HashMap<String,String> map6 = new HashMap<>();
        map6.put("name", "血糖");
        allAuxLists.add(map6);

        HashMap<String,String> map7 = new HashMap<>();
        map7.put("name", "血流变参数");
        allAuxLists.add(map7);
    }
    public void removeAuxMap(String name){
        for(HashMap<String,String> map:allAuxLists){
            if(map.get("name").equals(name)){
                currentAuxList.remove(map);
                break;
            }
        }

    }
    private void initSelectedAuxCheckChildView(){
        mSelectedAuxCheckFlowLayout = (FlowLayout)dialog.findViewById(R.id.aux_check_flowlayout);
        if(mSelectedAuxCheckFlowLayout.getChildCount() > 0){
            mSelectedAuxCheckFlowLayout.removeAllViews();
        }

        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        if(allAuxLists == null || allAuxLists.isEmpty()){
            return;
        }

        for(int i = 0; i < allAuxLists.size(); i++){
            final TextView view = new TextView(this);
            final HashMap<String,String> map = allAuxLists.get(i);
            view.setText(map.get("name"));
            view.setTextColor(Color.BLACK);
            view.setTextSize(12);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_flow_shape_unpressed));

            if(selectAuxLists.contains(map)){
                view.setTextColor(Color.WHITE);
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_flow_shape_pressed));
            }


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "adapter selectedCurrentDieasePositionLists view flow onclick " + map);

                    if(selectAuxLists.contains(map)){
                        //如果是正在选中的，则取消，颜色改变

                        selectAuxLists.remove(map);
                    }else{
                        selectAuxLists.add(map);
                    }
                    initSelectedAuxCheckChildView();

                }
            });
            mSelectedAuxCheckFlowLayout.addView(view, lp);
        }
    }

    private void updateAuxCheckView(){
        //TODO 更新辅助检查的视图
        auxAddLayout.removeAllViews();
        Log.i(TAG, "currentAuxList " + currentAuxList);
        if(currentAuxList != null && !currentAuxList.isEmpty()){
            Log.i(TAG,"currentAuxList "+currentAuxList.toString());
            for(HashMap<String,String> map:currentAuxList){

                if(map.get("name").equals("尿常规")){
//                    LayoutInflater inflater =getLayoutInflater();
//                    final View urineView = inflater.inflate(R.layout.activity_follow_up_table_diabetes_aux_urine_routine, null);
//
//                    initUrineView(urineView);
//                    auxAddLayout.addView(urineView);
//                    auxAddLayout.invalidate();

                    urineRoutineView = new FUTableAuxUrineRoutineView(FUTableDiabetesActivity.this,auxAddLayout,"糖尿病");
                }else if(map.get("name").equals("血常规")){
                    bloodRoutineView = new FUTableAuxBloodRoutineView(FUTableDiabetesActivity.this,auxAddLayout,"糖尿病");
                }else if(map.get("name").equals("生化全项")){
                    Log.i(TAG,"auxAddLayout "+auxAddLayout);
                    biochemicalItemsView = new FUTableAuxBiochemicalItemsView(FUTableDiabetesActivity.this,auxAddLayout,"糖尿病");

                }else if(map.get("name").equals("血糖")){
                    Log.i(TAG,"auxAddLayout "+auxAddLayout);
                    bloodSugarView = new FUTableAuxBloodSugarView(FUTableDiabetesActivity.this,auxAddLayout,"糖尿病");

                }else if(map.get("name").equals("肝功能")){
                    liverFunctionView = new FUTableAuxLiverFunctionView(FUTableDiabetesActivity.this,auxAddLayout,"糖尿病");

                }else if(map.get("name").equals("肾功能")){
                    renalFunction = new FUTableAuxRenalFunctionView(FUTableDiabetesActivity.this,auxAddLayout,"糖尿病");

                }else if(map.get("name").equals("血流变参数")){
                    hemorheologyParamView = new FUTableAuxHemorheologyParamView(FUTableDiabetesActivity.this,auxAddLayout,"糖尿病");
                }
            }
        }


    }

    private void initData(){

        //测试
//        dorsalisPedisArteryPulseSpinner.setSelection(1);
//        psychologicalRecoverySpinner.setSelection(0);
//        followingDoctorInstructionBehaviorSpinner.setSelection(1);

        initAllSymptomData();
        initSymptomFlowChildViews();

    }
    private void initAllSymptomData(){
        allSymptomLists.clear();

        allSymptomLists.add("无症状");
        allSymptomLists.add("多饮");
        allSymptomLists.add("多食");
        allSymptomLists.add("多尿");
        allSymptomLists.add("感染");
        allSymptomLists.add("视力模糊");
        allSymptomLists.add("手脚麻木");
        allSymptomLists.add("下肢浮肿");
        allSymptomLists.add("体重明显下降");

    }
    boolean noSymptom = false;
    //初始化流式布局
    private void initSymptomFlowChildViews() {
        // TODO Auto-generated method stub

        if(symptomFlowLayout.getChildCount() > 0){
            symptomFlowLayout.removeAllViews();
        }

        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 15;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        if(allSymptomLists == null || allSymptomLists.isEmpty()){
            return;
        }
        noSymptom = false;

        for(int i = 0; i < allSymptomLists.size(); i ++){
            final TextView view = new TextView(this);
//            final HashMap<String, String > map = symptomLists.get(i);
            final String name = allSymptomLists.get(i);

            view.setText(name);
            view.setTextSize(12);

            final CheckBox checkBox = new CheckBox(this);

            if(name.equals("无症状") && selectedSymptomLists.contains("无症状")){
                noSymptom = true;
            }

            if(noSymptom && !name.equals("无症状")){
                //非无症状都不可选
                checkBox.setEnabled(false);
//                map.put("selected","false");
            }

            if(selectedSymptomLists.contains(name)){
                checkBox.setChecked(true);
            }else{
                checkBox.setChecked(false);
            }

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.i(TAG, "initSymptomFlowChildViews onCheckedChanged ");
                    if(isChecked){
                        if(name.equals("无症状")){
                            noSymptom = true;
                            selectedSymptomLists.clear();
                            selectedSymptomLists.add(name);
                            initSymptomFlowChildViews();
                        }else{
                            selectedSymptomLists.add(name);
                        }
                    }else{
                        if(name.equals("无症状")){
                            noSymptom = false;
                            selectedSymptomLists.remove(name);
                            initSymptomFlowChildViews();
                        }else{
                            selectedSymptomLists.remove(name);
                        }
                    }

                }
            });

            symptomFlowLayout.addView(view, lp);
            symptomFlowLayout.addView(checkBox, lp);
        }
    }
    private void initSpinner(){
        final List<String> dpapList = getDPAPData();
        ArrayAdapter<String> dpapAdapter = new ArrayAdapter<String>
                (FUTableDiabetesActivity.this, R.layout.spinner_item,getDPAPData()){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(dpapList.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };

        //设置样式
        dpapAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        dorsalisPedisArteryPulseSpinner.setAdapter(dpapAdapter);

        dorsalisPedisArteryPulseSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.i(TAG, "足背动脉搏动 touch ");
                closeSoftKeyboard();
                return false;
            }
        });

        dorsalisPedisArteryPulseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                dpsPluseString = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        final List<String> psyRecoveryList = getPsyRecoveryData();
        ArrayAdapter<String> psyRecoveryAdapter = new ArrayAdapter<String>
                (FUTableDiabetesActivity.this, R.layout.spinner_item,getPsyRecoveryData()){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(psyRecoveryList.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };

        //设置样式
        psyRecoveryAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        psychologicalRecoverySpinner.setAdapter(psyRecoveryAdapter);

        psychologicalRecoverySpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.i(TAG, "心理调整 touch ");
                closeSoftKeyboard();
                return false;
            }
        });

        psychologicalRecoverySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                psyRecoveryString = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        final List<String> fdibList = getFDIBData();
        ArrayAdapter<String> fdibAdapter = new ArrayAdapter<String>
                (FUTableDiabetesActivity.this, R.layout.spinner_item,getFDIBData()){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(fdibList.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };

        //设置样式
        fdibAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        followingDoctorInstructionBehaviorSpinner.setAdapter(fdibAdapter);

        followingDoctorInstructionBehaviorSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.i(TAG, "遵医行为 touch ");
                closeSoftKeyboard();
                return false;
            }
        });

        followingDoctorInstructionBehaviorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                fdiBehaviorString = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });





    }

    //获取足背动脉搏动数据
    private List<String> getDPAPData(){
        List<String> dataList = new ArrayList<String>();

        dataList.add("触及");
        dataList.add("测试2");

        return dataList;
    }

    //获取心理调整数据
    private List<String> getPsyRecoveryData(){
        List<String> dataList = new ArrayList<String>();

        dataList.add("良好");
        dataList.add("优秀");

        return dataList;
    }

    //获取遵医行为数据
    private List<String> getFDIBData(){
        List<String> dataList = new ArrayList<String>();

        dataList.add("良好");
        dataList.add("优秀");

        return dataList;
    }


    private void closeSoftKeyboard(){

        /**隐藏软键盘**/
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private TextWatcher urineVolumeWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.i(TAG,"urineVolumeWatcher onTextChanged s "+s);
            if (!s.toString().equals("")){
                if(Float.valueOf(s.toString())<Float.valueOf(urineVolumeLowValue)){
                    urineVolumeLevelTV.setText("↓");
                }else if(Float.valueOf(s.toString())>Float.valueOf(urineVolumeHighValue)){
                    urineVolumeLevelTV.setText("↑");
                }else{
                    urineVolumeLevelTV.setText("正常");
                }
            }else{
                urineVolumeLevelTV.setText("--");
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    private TextWatcher waterIntakeWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.i(TAG,"waterIntakeWatcher onTextChanged s "+s);
            if (!s.toString().equals("")){
                if(Float.valueOf(s.toString())<Float.valueOf(waterIntakeLowValue)){
                    waterIntakeLevelTV.setText("↓");
                }else if(Float.valueOf(s.toString())>Float.valueOf(waterIntakeHighValue)){
                    waterIntakeLevelTV.setText("↑");
                }else{
                    waterIntakeLevelTV.setText("正常");
                }
            }else{
                waterIntakeLevelTV.setText("--");
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
