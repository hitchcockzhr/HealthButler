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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
//随访表-既往诊断
public class FUTableDiagnosisActivity extends BaseActivity {


    String TAG = "FUTableDiagnosisActivity";



    String http, httpUrl;

    private ToastCommom toastCommom;

    //心理调整
    private Spinner  psychologicalRecoverySpinner;
    //遵医行为
    private Spinner  followingDoctorInstructionBehaviorSpinner;


    private String psyRecoveryString,fdiBehaviorString;


    private Button backBtn;



    private TextView auxAddTV;
    MyDialog dialog;
    public ArrayList<HashMap<String, String>> currentAuxList= new ArrayList<HashMap<String, String>>();//当前辅助检查列表
    //存储选中的检查名称
    public ArrayList<HashMap<String, String>> selectAuxLists= new ArrayList<HashMap<String, String>>();

    public ArrayList<HashMap<String, String>> allAuxLists= new ArrayList<HashMap<String, String>>();
    private FlowLayout mSelectedAuxCheckFlowLayout;

    private LinearLayout auxAddLayout;

    private Button personalAuxBtn,healthyKeeperAuxBtn,hospitalAuxBtn;

    private FUTableAuxBiochemicalItemsView biochemicalItemsView;

    private FUTableAuxBloodSugarView bloodSugarView;

    private FUTableAuxBloodRoutineView bloodRoutineView;

    private FUTableAuxUrineRoutineView urineRoutineView;

    private FUTableAuxLiverFunctionView liverFunctionView;

    private FUTableAuxRenalFunctionView renalFunctionView;

    private FUTableAuxHemorheologyParamView hemorheologyParamView;

    private FUTableAuxBloodFatView bloodFatView;
    private FUTableAuxMyocardialEnzymeView myocardialEnzymeView;
    private FUTableAuxEditView ecgEditView;
    private FUTableAuxEditView ecgLoadEditView;
    private FUTableAuxEditView meEditView;
    private FUTableAuxEditView caEditView;

    private FUTableMedicineView medicineView;
    private LinearLayout medicineAddLayout;

    private List<HashMap<String,String>> medicineData = new ArrayList<>();
    private ScrollView sv;



    private MyTextView smokeAmountTV;
    private MyTextView drinkingAmountTV;
    private MyTextView sportTimeTV;
    private MyTextView saltAmountTV;
    private MyTextView foodAmountTV;

    private LinearLayout symptomLayout;
    private RelativeLayout basicDataLayout;

    private List<String> diagnosisList = new ArrayList<>();

    private TextView titleTV;

    private MyTextView bloodPressureTV;
    private MyTextView weightTV;
    private MyTextView bmiTV;
    private MyTextView heartRateTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_table_diagnosis);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(FUTableDiagnosisActivity.this);

        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sv = (ScrollView)findViewById(R.id.sv);


        titleTV = (TextView)findViewById(R.id.title_tv);
        smokeAmountTV = (MyTextView)findViewById(R.id.daily_smoke_amount_tv);
        drinkingAmountTV = (MyTextView)findViewById(R.id.daily_drinking_amount_tv);
        sportTimeTV = (MyTextView)findViewById(R.id.sport_times_tv);
        foodAmountTV = (MyTextView)findViewById(R.id.food_amount_tv);
        saltAmountTV = (MyTextView)findViewById(R.id.salt_amount_tv);


        auxAddTV = (TextView)findViewById(R.id.add_tv);
        auxAddTV.setOnClickListener(clickListener);
        auxAddLayout = (LinearLayout)findViewById(R.id.aux_add_layout);

        basicDataLayout = (RelativeLayout)findViewById(R.id.basic_data_layout);

        personalAuxBtn = (Button)findViewById(R.id.personal_aux);
        healthyKeeperAuxBtn = (Button)findViewById(R.id.healthy_keeper_aux);
        hospitalAuxBtn = (Button)findViewById(R.id.hospital_aux);

        personalAuxBtn.setOnClickListener(clickListener);
        healthyKeeperAuxBtn.setOnClickListener(clickListener);
        hospitalAuxBtn.setOnClickListener(clickListener);


        psychologicalRecoverySpinner = (Spinner)findViewById(R.id.psychological_recovery_spinner);

        followingDoctorInstructionBehaviorSpinner = (Spinner)findViewById(R.id.following_doctor_instruction_behavior_spinner);

        bloodPressureTV = (MyTextView)findViewById(R.id.blood_pressure_tv);
        weightTV = (MyTextView)findViewById(R.id.weight_tv);
        bmiTV = (MyTextView)findViewById(R.id.bmi_tv);
        heartRateTV = (MyTextView)findViewById(R.id.heart_rate_tv);


        currentAuxList.clear();
        getDiagnosis();
        initSymptomView();

        initSpinner();

        initData();
        initXChartData();
        initMedicine();

    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    private void getDiagnosis(){
//        diagnosisList.add("糖尿病");
//        diagnosisList.add("高血压");
//        diagnosisList.add("冠心病");
//        diagnosisList.add("脑卒中");
//        diagnosisList.add("高脂血症");

        Bundle bundle=getIntent().getExtras();
        ArrayList list2 = new ArrayList();

        list2 = bundle.getParcelableArrayList("diagnose");



        if(list2 != null && !list2.isEmpty()) {

            for (int i = 0; i < list2.size(); i++) {
                HashMap<String, String> map = (HashMap<String, String>) list2.get(i);
                diagnosisList.add(map.get("diagnose"));
            }
        }
    }
    private void initSymptomView(){
        if(!diagnosisList.isEmpty()){

            titleTV.setText(diagnosisList.toString());
        }

        symptomLayout = (LinearLayout)findViewById(R.id.symptom_layout);

        if(diagnosisList.contains("高血压")){
            LayoutInflater inflater =getLayoutInflater();
            final View view = inflater.inflate(R.layout.activity_follow_up_table_hypertension_symptom, null);

            symptomLayout.addView(view);

            basicDataLayout.setVisibility(View.VISIBLE);

            initHypertensionSymView(view);
        }

        if(diagnosisList.contains("冠心病")){
            LayoutInflater inflater =getLayoutInflater();
            final View view = inflater.inflate(R.layout.activity_follow_up_table_chd_symptom, null);

            symptomLayout.addView(view);

            basicDataLayout.setVisibility(View.VISIBLE);

            if(diagnosisList.contains("高血压")){
                TextView legsEdemaTV  = (TextView)view.findViewById(R.id.legs_edema_tv);
                CheckBox legsEdemaBox = (CheckBox)view.findViewById(R.id.legs_edema_check_btn);
                legsEdemaBox.setVisibility(View.GONE);
                legsEdemaTV.setVisibility(View.GONE);
            }


            initCHDSymView(view);
        }
        if(diagnosisList.contains("脑卒中")){
            LayoutInflater inflater =getLayoutInflater();
            final View view = inflater.inflate(R.layout.activity_follow_up_table_stroke_symptom, null);

            symptomLayout.addView(view);

            basicDataLayout.setVisibility(View.VISIBLE);
            initStrokeSymView(view);
        }
        if(diagnosisList.contains("高脂血症")){
//            LayoutInflater inflater =getLayoutInflater();
//            final View view = inflater.inflate(R.layout.activity_follow_up_table_chd_symptom, null);
//
//            symptomLayout.addView(view);
//
//            basicDataLayout.setVisibility(View.VISIBLE);
//            initHLPSymView(view);
        }
        //糖尿病放在最后一个，因为糖尿病的症状layout中不止是checkbox，还包含饮水量等数据
        if(diagnosisList.contains("糖尿病")){
            LayoutInflater inflater =getLayoutInflater();
            final View view = inflater.inflate(R.layout.activity_follow_up_table_diabetes_symptom, null);

            symptomLayout.addView(view);

            if(diagnosisList.size() == 1){
                basicDataLayout.setVisibility(View.GONE);
            }

            initDiabetesSymView(view);

        }


    }
    private TextView waterIntakeHighValueTV,waterIntakeLowValueTV;
    private String waterIntakeHighValue,waterIntakeLowValue;


    private TextView urineVolumeHighValueTV,urineVolumeLowValueTV;
    private String urineVolumeHighValue,urineVolumeLowValue;


    private EditText urineVolumeET,waterIntakeET;
    private TextView urineVolumeLevelTV,waterIntakeLevelTV;

    private String urineVolumeValueString,waterIntakeValueString;
    //足背动脉搏动
    private Spinner dorsalisPedisArteryPulseSpinner;
    private MyTextView waterIntakeTV;
    private MyTextView urineVolumeTV;
    private String dpsPluseString;
    private void initDiabetesSymView(View view){
        waterIntakeTV = (MyTextView)view.findViewById(R.id.water_intake_tv);
        urineVolumeTV = (MyTextView)view.findViewById(R.id.urine_volume_tv);

        urineVolumeET = (EditText)view.findViewById(R.id.urine_volume_et);
        urineVolumeLevelTV = (TextView)view.findViewById(R.id.urine_volume_level_tv);

        urineVolumeHighValueTV = (TextView)view.findViewById(R.id.urine_volume_high_normal_tv);
        urineVolumeLowValueTV = (TextView)view.findViewById(R.id.urine_volume_low_normal_tv);
        urineVolumeHighValue = urineVolumeHighValueTV.getText().toString();
        urineVolumeLowValue = urineVolumeLowValueTV.getText().toString();


        waterIntakeET = (EditText)view.findViewById(R.id.water_intake_et);
        waterIntakeLevelTV = (TextView)view.findViewById(R.id.water_intake_level_tv);

        waterIntakeHighValueTV = (TextView)view.findViewById(R.id.water_intake_high_normal_tv);
        waterIntakeLowValueTV = (TextView)view.findViewById(R.id.water_intake_low_normal_tv);

        dorsalisPedisArteryPulseSpinner = (Spinner)view.findViewById(R.id.dorsalis_pedis_artery_pulse_spinner);

        waterIntakeHighValue = waterIntakeHighValueTV.getText().toString();
        waterIntakeLowValue = waterIntakeLowValueTV.getText().toString();

        urineVolumeET.addTextChangedListener(urineVolumeWatcher);
        waterIntakeET.addTextChangedListener(waterIntakeWatcher);
        initDiabetesXChartData();


        final List<String> dpapList = getDPAPData();
        ArrayAdapter<String> dpapAdapter = new ArrayAdapter<String>
                (FUTableDiagnosisActivity.this, R.layout.spinner_item,getDPAPData()){
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
    }
    //初始化图像数据
    private void initDiabetesXChartData(){
        double[] radarViewData = {30,60,60,60,80,50,10,20}; //各维度分值
        String[] radarViewTitles = {"测试1","测试2","测试3","测试4","测试5","测试6"};//各维度名称

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
        //设置饮水量的六边形数据
        waterIntakeTV.setRadarViewData(radarViewData);
        waterIntakeTV.setRadarViewMaxValue(90);
        waterIntakeTV.setRadarViewTitles(radarViewTitles);
        waterIntakeTV.setLineData(waterIntakeTV.getText().toString(),yMax,ySteps,yDetailSteps,xLabels,dataTextList,dataColorList,dataList);

        //设置六边形数据
        urineVolumeTV.setRadarViewData(radarViewData);
        urineVolumeTV.setRadarViewMaxValue(100);
        urineVolumeTV.setRadarViewTitles(radarViewTitles);
        //设置折线数据
        urineVolumeTV.setLineData(urineVolumeTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);
    }
    private void initHLPSymView(View view){

    }
    private void initCHDSymView(View view){

    }
    private void initStrokeSymView(View view){

    }
    private void initHypertensionSymView(View view){

    }

    private void initMedicine(){
        medicineAddLayout = (LinearLayout)findViewById(R.id.medicine_add_layout);
        getALLMedicineData();
        medicineView = new FUTableMedicineView(FUTableDiagnosisActivity.this,medicineAddLayout,medicineData,sv);
//        medicineAddLayout.addView(medicineView);
    }
    private void getALLMedicineData(){
        medicineData.clear();
        HashMap<String,String> map = new HashMap<>();
        map.put("drugName","阿司匹林");
        map.put("drugId","1");
        map.put("unit","g");
        map.put("cishu","1");
        map.put("frequencyType","QD");
        map.put("frequency","0.1");
        map.put("tianshu","28");
        map.put("shuliang","1");
        map.put("dj","1");
        map.put("jiage",String.valueOf(Double.valueOf(map.get("dj"))*Integer.valueOf(map.get("shuliang"))));

        HashMap<String,String> map2 = new HashMap<>();
        map2.put("drugName","甘精胰岛素");
        map2.put("drugId","2");
        map2.put("unit","iu");
        map2.put("cishu","2");
        map2.put("frequencyType","BID");
        map2.put("frequency","10,18");
        map2.put("tianshu","14");
        map2.put("shuliang","1");
        map2.put("dj","1");
        map2.put("jiage",String.valueOf(Double.valueOf(map2.get("dj"))*Integer.valueOf(map2.get("shuliang"))));

        HashMap<String,String> map3 = new HashMap<>();
        map3.put("drugName","二甲双胍");
        map3.put("drugId","3");
        map3.put("unit","g");
        map3.put("cishu","3");
        map3.put("frequencyType","TID");
        map3.put("frequency","0.5,0.5,0.5");
        map3.put("tianshu","14");
        map3.put("shuliang","3");
        map3.put("dj","1");
        map3.put("jiage",String.valueOf(Double.valueOf(map3.get("dj"))*Integer.valueOf(map3.get("shuliang"))));
        medicineData.add(map);
        medicineData.add(map2);
        medicineData.add(map3);
    }
    //初始化图像数据
    private void initXChartData(){
        double[] radarViewData = {30,60,60,60,80,50,10,20}; //各维度分值
        String[] radarViewTitles = {"测试1","测试2","测试3","测试4","测试5","测试6"};//各维度名称

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
        //设置饮水量的六边形数据
        bloodPressureTV.setRadarViewData(radarViewData);
        bloodPressureTV.setRadarViewMaxValue(90);
        bloodPressureTV.setRadarViewTitles(radarViewTitles);
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

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.add_tv:
                    addAuxCheck();
                    break;
                case R.id.personal_aux:
                    personalAuxBtn.setBackgroundResource(R.drawable.tab_btn_pressed_shape);
                    personalAuxBtn.setTextColor(getResources().getColor(R.color.white));
                    healthyKeeperAuxBtn.setBackgroundResource(R.drawable.btn_green_stroke_shape);
                    healthyKeeperAuxBtn.setTextColor(getResources().getColor(R.color.black));
                    hospitalAuxBtn.setBackgroundResource(R.drawable.btn_green_stroke_shape);
                    hospitalAuxBtn.setTextColor(getResources().getColor(R.color.black));

                    break;

                case R.id.healthy_keeper_aux:

                    healthyKeeperAuxBtn.setBackgroundResource(R.drawable.tab_btn_pressed_shape);
                    healthyKeeperAuxBtn.setTextColor(getResources().getColor(R.color.white));
                    personalAuxBtn.setBackgroundResource(R.drawable.btn_green_stroke_shape);
                    personalAuxBtn.setTextColor(getResources().getColor(R.color.black));
                    hospitalAuxBtn.setBackgroundResource(R.drawable.btn_green_stroke_shape);
                    hospitalAuxBtn.setTextColor(getResources().getColor(R.color.black));

                    break;

                case R.id.hospital_aux:
                    hospitalAuxBtn.setBackgroundResource(R.drawable.tab_btn_pressed_shape);
                    hospitalAuxBtn.setTextColor(getResources().getColor(R.color.white));
                    personalAuxBtn.setBackgroundResource(R.drawable.btn_green_stroke_shape);
                    personalAuxBtn.setTextColor(getResources().getColor(R.color.black));
                    healthyKeeperAuxBtn.setBackgroundResource(R.drawable.btn_green_stroke_shape);
                    healthyKeeperAuxBtn.setTextColor(getResources().getColor(R.color.black));

                    break;
            }
        }
    };
    //增加辅助检查
    private void addAuxCheck(){
        LayoutInflater inflater =getLayoutInflater();
        final View functionListView = inflater.inflate(R.layout.activity_follow_up_table_diabetes_add_aux_dialog, null);
//		dialog = new AlertDialog.Builder(DiagnosisActivity.this, R.style.load_dialog).setView(functionListView).show();

        dialog = new MyDialog(FUTableDiagnosisActivity.this, functionListView,R.style.load_dialog);
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
        if(diagnosisList.contains("糖尿病")||diagnosisList.contains("高血压")||diagnosisList.contains("脑卒中")){
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
        if(diagnosisList.contains("冠心病")){
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

                    urineRoutineView = new FUTableAuxUrineRoutineView(FUTableDiagnosisActivity.this,auxAddLayout,"");
                }else if(map.get("name").equals("血常规")){
                    bloodRoutineView = new FUTableAuxBloodRoutineView(FUTableDiagnosisActivity.this,auxAddLayout,"");
                }else if(map.get("name").equals("生化全项")){
                    Log.i(TAG,"auxAddLayout "+auxAddLayout);
                    biochemicalItemsView = new FUTableAuxBiochemicalItemsView(FUTableDiagnosisActivity.this,auxAddLayout,"");

                }else if(map.get("name").equals("血糖")){
                    Log.i(TAG,"auxAddLayout "+auxAddLayout);
                    bloodSugarView = new FUTableAuxBloodSugarView(FUTableDiagnosisActivity.this,auxAddLayout,"");

                }else if(map.get("name").equals("肝功能")){
                    liverFunctionView = new FUTableAuxLiverFunctionView(FUTableDiagnosisActivity.this,auxAddLayout,"");

                }else if(map.get("name").equals("肾功能")){
                    renalFunctionView = new FUTableAuxRenalFunctionView(FUTableDiagnosisActivity.this,auxAddLayout,"");

                }else if(map.get("name").equals("血流变参数")){
                    hemorheologyParamView = new FUTableAuxHemorheologyParamView(FUTableDiagnosisActivity.this,auxAddLayout,"");

                }else if(map.get("name").equals("血脂全项")){

                    bloodFatView = new FUTableAuxBloodFatView(FUTableDiagnosisActivity.this,auxAddLayout,"");
                }else if(map.get("name").equals("心肌酶学检查结果")){
                    myocardialEnzymeView = new FUTableAuxMyocardialEnzymeView(FUTableDiagnosisActivity.this,auxAddLayout,"");
                }else if(map.get("name").equals("心电图检查结果")){
                    ecgEditView = new FUTableAuxEditView(FUTableDiagnosisActivity.this,auxAddLayout,"心电图检查结果","");
                }else if(map.get("name").equals("心电图运动负荷实验结果")){
                    ecgLoadEditView = new FUTableAuxEditView(FUTableDiagnosisActivity.this,auxAddLayout,"心电图运动负荷实验结果","");
                }else if(map.get("name").equals("心脏彩超检查结果")){
                    meEditView = new FUTableAuxEditView(FUTableDiagnosisActivity.this,auxAddLayout,"心脏彩超检查结果","");
                }else if(map.get("name").equals("冠状动脉造影结果")){
                    caEditView = new FUTableAuxEditView(FUTableDiagnosisActivity.this,auxAddLayout,"冠状动脉造影结果","");
                }
            }
        }


    }

    private void initData(){

        //测试

        psychologicalRecoverySpinner.setSelection(0);
        followingDoctorInstructionBehaviorSpinner.setSelection(1);



    }

    private void initSpinner(){

        final List<String> psyRecoveryList = getPsyRecoveryData();
        ArrayAdapter<String> psyRecoveryAdapter = new ArrayAdapter<String>
                (FUTableDiagnosisActivity.this, R.layout.spinner_item,getPsyRecoveryData()){
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
                (FUTableDiagnosisActivity.this, R.layout.spinner_item,getFDIBData()){
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

    /**
     * 为了得到传回的数据，必须在前面的Activity中（指MainActivity类）重写onActivityResult方法
     *
     * requestCode 请求码，即调用startActivityForResult()传递过去的值
     * resultCode 结果码，结果码用于标识返回数据来自哪个新Activity
     */
    List<HashMap<String,String>> medicineDatas = new ArrayList<>();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            ///测试activity间传递list
            Bundle bundle=data.getExtras();
            ArrayList list2 = new ArrayList();
            if(requestCode == FUTableMedicineView.REQUEST_HISTORY_CODE){
                list2 = bundle.getParcelableArrayList("history");
            }else if(requestCode == FUTableMedicineView.REQUEST_NEW_CODE ){
                list2 = bundle.getParcelableArrayList("new");
            }
            if(list2 != null && !list2.isEmpty()){

                medicineDatas = medicineView.getMedicineData();
                Log.i("GXF", "onActivityResult测试activity间传递list list2 " + list2);
                for(int i=0;i<list2.size();i++)
                {
                    HashMap<String ,String> map=(HashMap<String,String>)list2.get(i);
                    Log.i("GXF", "onActivityResult测试activity间传递list map " + map);
                    if(medicineDatas != null && !medicineDatas.isEmpty()){
//						if(currentMedicineLists.contains(map)){

                        medicineView.addMedicineData(map);

                    }else{
                        medicineView.addMedicineData(map);
                    }

                }

            }
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
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
