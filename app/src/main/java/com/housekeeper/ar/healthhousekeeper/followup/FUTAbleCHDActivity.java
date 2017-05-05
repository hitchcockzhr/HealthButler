package com.housekeeper.ar.healthhousekeeper.followup;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
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
 * 随访表-冠心病
 * Created by Lenovo on 2016/12/2.
 */
public class FUTAbleCHDActivity extends BaseActivity {
    String TAG="FUTAbleHypertensionActivity";
    private ToastCommom toastCommom;
    //心理调整
    private Spinner psychologicalRecoverySpinner;
    //遵医行为
    private Spinner  followingDoctorInstructionBehaviorSpinner;

    private String psyRecoveryString,fdiBehaviorString;
    private Button backBtn;
    private TextView auxAddTV;
    MyDialog addAuxDialog;
    private ScrollView sv;
    public ArrayList<HashMap<String, String>> currentAuxList= new ArrayList<HashMap<String, String>>();//当前辅助检查列表
    //存储选中的检查名称
    public ArrayList<HashMap<String, String>> selectAuxLists= new ArrayList<HashMap<String, String>>();

    public ArrayList<HashMap<String, String>> allAuxLists= new ArrayList<HashMap<String, String>>();
    private FlowLayout mSelectedAuxCheckFlowLayout;

    private LinearLayout auxAddLayout;


    private FUTableAuxBloodFatView bloodFatView;
    private FUTableAuxMyocardialEnzymeView myocardialEnzymeView;
    private FUTableAuxEditView ecgEditView;
    private FUTableAuxEditView ecgLoadEditView;
    private FUTableAuxEditView meEditView;
    private FUTableAuxEditView caEditView;


    private MyTextView bloodPressureTV;
    private MyTextView weightTV;
    private MyTextView bmiTV;
    private MyTextView heartRateTV;
    private MyTextView smokeAmountTV;
    private MyTextView drinkingAmountTV;
    private MyTextView sportTimeTV;
    private MyTextView foodAmountTV;
    private MyTextView saltAmountTV;

    private Button saveBtn;
    private ImageView saveView;

    private TextView bloodPressLevelTV;
    private EditText bloodPressLowET,bloodPressHighET;

    private TextView bmiLevelTV;
    private EditText bmiET;


    private FlowLayout symptomFlowLayout;

    //    private List<HashMap<String,String>> symptomLists = new ArrayList<>();
    //所有的症状列表
    private List<String> allSymptomLists = new ArrayList<>();
    //选中的症状列表
    private List<String> selectedSymptomLists = new ArrayList<>();

    private EditText smokeAmountET,drinkAmountET,sportCountET,sportMinutesET,eatAmountET,saltAmountET;

    private EditText weightET,heartRateET,otherET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_table_chd_new);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(FUTAbleCHDActivity.this);

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

        weightET = (EditText)findViewById(R.id.weight_et);
        heartRateET = (EditText)findViewById(R.id.heart_rate_et);
        otherET = (EditText)findViewById(R.id.other_et);

        symptomFlowLayout = (FlowLayout)findViewById(R.id.symptom_flowlayout);

        bloodPressLowET = (EditText)findViewById(R.id.blood_pressure_low_et);
        bloodPressHighET = (EditText)findViewById(R.id.blood_pressure_high_et);
        bloodPressLevelTV = (TextView)findViewById(R.id.blood_pressure_level_tv);

        bloodPressLowET.addTextChangedListener(sbpTextWatcher);
        bloodPressHighET.addTextChangedListener(sbpTextWatcher);
        bloodPressLevelTV.setOnClickListener(clickListener);

        auxAddTV = (TextView)findViewById(R.id.add_tv);
        auxAddTV.setOnClickListener(clickListener);
        auxAddLayout = (LinearLayout)findViewById(R.id.aux_add_layout);


        psychologicalRecoverySpinner = (Spinner)findViewById(R.id.psychological_recovery_spinner);

        followingDoctorInstructionBehaviorSpinner = (Spinner)findViewById(R.id.following_doctor_instruction_behavior_spinner);

        bloodPressureTV = (MyTextView)findViewById(R.id.blood_pressure_tv);
        weightTV = (MyTextView)findViewById(R.id.weight_tv);
        bmiTV = (MyTextView)findViewById(R.id.bmi_tv);
        heartRateTV = (MyTextView)findViewById(R.id.heart_rate_tv);
        smokeAmountTV = (MyTextView)findViewById(R.id.daily_smoke_amount_tv);
        drinkingAmountTV = (MyTextView)findViewById(R.id.daily_drinking_amount_tv);
        sportTimeTV = (MyTextView)findViewById(R.id.sport_times_tv);
        foodAmountTV = (MyTextView)findViewById(R.id.food_amount_tv);
        saltAmountTV = (MyTextView)findViewById(R.id.salt_amount_tv);

        bmiLevelTV = (TextView)findViewById(R.id.bmi_level_tv);
        bmiLevelTV.setOnClickListener(clickListener);
        bmiET = (EditText)findViewById(R.id.bmi_et);
        bmiET.addTextChangedListener(bmiTextWacher);

        currentAuxList.clear();

        initSpinner();

        initData();
        initXChartData();
    }


    private void initData(){
        initAllSymptomData();
        initSymptomFlowChildViews();
    }

    private void initAllSymptomData(){
        allSymptomLists.clear();

        allSymptomLists.add("无症状");
        allSymptomLists.add("心慌");
        allSymptomLists.add("胸口闷痛");
        allSymptomLists.add("心绞痛");
        allSymptomLists.add("呼吸困难");
        allSymptomLists.add("心悸胸闷");
        allSymptomLists.add("喘憋");
        allSymptomLists.add("不能平卧");
        allSymptomLists.add("下肢水肿");

    }

    boolean noSymptom = false;
    //初始化流式布局
    private void initSymptomFlowChildViews() {
        // TODO Auto-generated method stub
        Log.i(TAG, "symptomFlowLayout " + symptomFlowLayout);
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
    //初始化图像数据
    private void initXChartData(){
        double[] radarViewData = {30,60,60,60,80,50,10,20}; //各维度分值
        String[] radarViewTitles = {"测试1","测试2","测试3","测试4","测试5","测试6"};//各维度名称
        //设置六边形数据
        bloodPressureTV.setRadarViewData(radarViewData);
        bloodPressureTV.setRadarViewMaxValue(90);
        bloodPressureTV.setRadarViewTitles(radarViewTitles);

        int yMax = 100; //y轴最大值
        int ySteps = 10;//Y轴间隔值
        int yDetailSteps = 1;//Y轴文字显示间隔数，Y轴每隔ySteps*yDetailSteps显示一次文字，如10*1=10，在0，10，20...处显示文字
        //X轴数值
        LinkedList<String> xLabels = new LinkedList<>();

        //线段文字集合
        List<String> dataTextList = new ArrayList<>();
        //线段颜色集合，若为null，则默认绿色；若成员HashMap<String,Integer>是-1，-1，-1，则设置成默认值绿色58,171,119
        List<HashMap<String,Integer>>dataColorList = new ArrayList<>();
        //线段值集合
        List<LinkedList<Double>>dataList = new ArrayList<>();

        for(int i=0;i<8;i++){
            xLabels.add(i+"");
        }

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

        bloodPressureTV.setLineData(bloodPressureTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        weightTV.setRadarViewData(radarViewData);
        weightTV.setRadarViewMaxValue(100);
        weightTV.setRadarViewTitles(radarViewTitles);
        //设置折线数据
        weightTV.setLineData(weightTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        bmiTV.setRadarViewData(radarViewData);
        bmiTV.setRadarViewMaxValue(100);
        bmiTV.setRadarViewTitles(radarViewTitles);
        //设置折线数据
        bmiTV.setLineData(bmiTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        heartRateTV.setRadarViewData(radarViewData);
        heartRateTV.setRadarViewMaxValue(100);
        heartRateTV.setRadarViewTitles(radarViewTitles);
        //设置折线数据
        heartRateTV.setLineData(heartRateTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

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
    private void initSpinner(){
        final List<String> psyRecoveryList = getPsyRecoveryData();
        ArrayAdapter<String> psyRecoveryAdapter = new ArrayAdapter<String>
                (FUTAbleCHDActivity.this, R.layout.spinner_item,getPsyRecoveryData()){
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
                (FUTAbleCHDActivity.this, R.layout.spinner_item,getFDIBData()){
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
    private void closeSoftKeyboard(){

        /**隐藏软键盘**/
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.add_tv:
                    addAuxCheck();
                    break;
                case R.id.blood_pressure_level_tv:
                    if(!bloodPressLevelTV.getText().toString().equals("正常")){
                        Intent intent = new Intent(FUTAbleCHDActivity.this,FUTableAuxBloodPressUnderstandingActivity.class);
                        startActivity(intent);
                    }

                    break;
                case R.id.bmi_level_tv:
                    if(!bmiLevelTV.getText().toString().equals("正常")&& !bmiLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(FUTAbleCHDActivity.this,FUTableAuxHeightWeightUnderstandingActivity.class);
                        startActivity(intent);
                    }
                    break;
            }
        }
    };

    //增加辅助检查
    private void addAuxCheck(){
        LayoutInflater inflater =getLayoutInflater();
        final View functionListView = inflater.inflate(R.layout.activity_follow_up_table_diabetes_add_aux_dialog, null);

        addAuxDialog = new MyDialog(FUTAbleCHDActivity.this, functionListView,R.style.load_dialog);
        int width = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
        addAuxDialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

        getAllAuxCheckData();

        selectAuxLists.clear();
        selectAuxLists.addAll(currentAuxList);


        initSelectedAuxCheckChildView();

        Button dialogOkBtn = (Button)functionListView.findViewById(R.id.ok_btn);
        Button dialogCancelBtn = (Button)functionListView.findViewById(R.id.cancel_btn);

        dialogCancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                addAuxDialog.dismiss();
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
                addAuxDialog.dismiss();
            }
        });
        addAuxDialog.setCanceledOnTouchOutside(false);
        addAuxDialog.show();
    }
    private void getAllAuxCheckData(){
        //测试数据
        allAuxLists.clear();


        HashMap<String,String> map2 = new HashMap<>();
        map2.put("name","血脂全项");
        allAuxLists.add(map2);

        HashMap<String,String> map3 = new HashMap<>();
        map3.put("name","心肌酶学检查结果");
        allAuxLists.add(map3);

        HashMap<String,String> map4 = new HashMap<>();
        map4.put("name", "心电图检查结果");
        allAuxLists.add(map4);

        HashMap<String,String> map5 = new HashMap<>();
        map5.put("name", "心电图运动负荷实验结果");
        allAuxLists.add(map5);

        HashMap<String,String> map6 = new HashMap<>();
        map6.put("name", "心脏彩超检查结果");
        allAuxLists.add(map6);

        HashMap<String,String> map7 = new HashMap<>();
        map7.put("name", "冠状动脉造影结果");
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
        mSelectedAuxCheckFlowLayout = (FlowLayout)addAuxDialog.findViewById(R.id.aux_check_flowlayout);
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

                //TODO　目前是借用糖尿病的辅助检查界面，以后有详细需求再按照FUTableDiabetesUrineRoutineView等生成View
                if(map.get("name").equals("血脂全项")){

                    bloodFatView = new FUTableAuxBloodFatView(FUTAbleCHDActivity.this,auxAddLayout,"冠心病");
                }else if(map.get("name").equals("心肌酶学检查结果")){
                    myocardialEnzymeView = new FUTableAuxMyocardialEnzymeView(FUTAbleCHDActivity.this,auxAddLayout,"冠心病");
                }else if(map.get("name").equals("心电图检查结果")){
                    ecgEditView = new FUTableAuxEditView(FUTAbleCHDActivity.this,auxAddLayout,"心电图检查结果","冠心病");
                }else if(map.get("name").equals("心电图运动负荷实验结果")){
                    ecgLoadEditView = new FUTableAuxEditView(FUTAbleCHDActivity.this,auxAddLayout,"心电图运动负荷实验结果","冠心病");
                }else if(map.get("name").equals("心脏彩超检查结果")){
                    meEditView = new FUTableAuxEditView(FUTAbleCHDActivity.this,auxAddLayout,"心脏彩超检查结果","冠心病");
                }else if(map.get("name").equals("冠状动脉造影结果")){
                    caEditView = new FUTableAuxEditView(FUTAbleCHDActivity.this,auxAddLayout,"冠状动脉造影结果","冠心病");
                }
            }
        }


    }


    private TextWatcher sbpTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(s != null && !s.toString().equals("")&& !bloodPressHighET.getText().toString().equals("") && !bloodPressLowET.getText().toString().equals("")){
                int high = Integer.valueOf(bloodPressHighET.getText().toString());
                int low = Integer.valueOf(bloodPressLowET.getText().toString());
                if(low > 90 || low == 90){
                    bloodPressLevelTV.setText("偏高");
                }else if(low == 60 || low < 60){
                    bloodPressLevelTV.setText("偏低");
                }else if(high > 140 || high == 140){
                    bloodPressLevelTV.setText("偏高");
                }else if(high == 90 || high < 90){
                    bloodPressLevelTV.setText("偏低");
                }else {
                    bloodPressLevelTV.setText("正常");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher bmiTextWacher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(s != null && !s.toString().equals("")){

                double bmiD = Double.valueOf(bmiET.getText().toString());
                if(bmiD < 18.5){

                    bmiLevelTV.setText("消瘦");
                }else if(18.5 <= bmiD && bmiD <= 24.9){
                    bmiLevelTV.setText("正常");
                }else if(24.9 < bmiD && bmiD <= 28){
                    bmiLevelTV.setText("体重超标");
                }else{
                    bmiLevelTV.setText("肥胖症");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
