package com.housekeeper.ar.healthhousekeeper.followup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 随访历史-脑卒中
 * Created by Lenovo on 2016/12/2.
 */
public class FUTableHistoryStrokeActivity extends BaseActivity {
    String TAG="FUTableHistoryStrokeActivity";
    private ToastCommom toastCommom;
    String id;
    String get_diseas_info_url = "http://123.56.155.17:8080/xys/healthButler/followupSheet/history/dieaseCheckDetail/";//{dieaseCheckId}
    //心理调整
    private Spinner psychologicalRecoverySpinner;
    //遵医行为
    private Spinner  followingDoctorInstructionBehaviorSpinner;

    private Spinner recoveryTreatmentSpinner;
    private Spinner limbRecoverySpinner;

    private String psyRecoveryString,fdiBehaviorString,recoveryTreatmentString,limbRecoveryString;
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

    private FUTableAuxBloodSugarView bloodSugarView;

    private FUTableAuxBloodRoutineView bloodRoutineView;

    private FUTableAuxUrineRoutineView urineRoutineView;


    private FUTableAuxRenalFunctionView renalFunctionView;

    private FUTableAuxHemorheologyParamView hemorheologyParamView;

    private Button saveBtn;
    private ImageView saveView;

    private FlowLayout symptomFlowLayout;

    //    private List<HashMap<String,String>> symptomLists = new ArrayList<>();
    //所有的症状列表
    private List<String> allSymptomLists = new ArrayList<>();
    //选中的症状列表
    private List<String> selectedSymptomLists = new ArrayList<>();


    private FlowLayout symptomTypeFlowLayout;
    //所有的新发卒中类型症状列表
    private List<String> allSymptomTypeLists = new ArrayList<>();
    //选中的新发卒中类型症状列表
    private List<String> selectedSymptomTypeLists = new ArrayList<>();

    private FlowLayout complicationFlowLayout;
    //所有的并发症症状列表
    private List<String> allComplicationLists = new ArrayList<>();
    //选中的症状列表
    private List<String> selectedComplicationLists = new ArrayList<>();


    private FlowLayout symptomPartFlowLayout;
    //所有的脑卒中部位列表
    private List<String> allSymptomPartLists = new ArrayList<>();
    //选中的脑卒中部位列表
    private List<String> selectedSymptomPartLists = new ArrayList<>();

    private FlowLayout newSymptomFlowLayout;
    //所有的新卒中症状列表
    private List<String> allNewSymptomLists = new ArrayList<>();
    //选中的新卒中症状列表
    private List<String> selectedNewSymptomLists = new ArrayList<>();

    private EditText smokeAmountET,drinkAmountET,sportCountET,sportMinutesET,eatAmountET,saltAmountET;

    private TextView titleTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_table_history_stroke);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(FUTableHistoryStrokeActivity.this);

        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sv = (ScrollView)findViewById(R.id.sv);

        Intent intent = getIntent();
        if(intent != null){
            String time = intent.getStringExtra("time");
            titleTV = (TextView)findViewById(R.id.title_tv);
            titleTV.setText("脑卒中-"+time);
        }
        smokeAmountET = (EditText)findViewById(R.id.daily_smoke_amount_et);
        drinkAmountET = (EditText)findViewById(R.id.daily_drinking_amount_et);
        sportCountET = (EditText)findViewById(R.id.sport_times_et);
        sportMinutesET = (EditText)findViewById(R.id.sport_min_et);
        eatAmountET = (EditText)findViewById(R.id.food_amount_et);
        saltAmountET = (EditText)findViewById(R.id.salt_amount_et);

        symptomFlowLayout = (FlowLayout)findViewById(R.id.symptom_flowlayout);
        symptomPartFlowLayout = (FlowLayout)findViewById(R.id.part_symptom_flowlayout);
        complicationFlowLayout = (FlowLayout)findViewById(R.id.complication_symptom_flowlayout);
        symptomTypeFlowLayout = (FlowLayout)findViewById(R.id.type_symptom_flowlayout);
        newSymptomFlowLayout = (FlowLayout)findViewById(R.id.new_stroke_symptom_flowlayout);

        auxAddTV = (TextView)findViewById(R.id.add_tv);
        auxAddTV.setOnClickListener(clickListener);
        auxAddLayout = (LinearLayout)findViewById(R.id.aux_add_layout);


        psychologicalRecoverySpinner = (Spinner)findViewById(R.id.psychological_recovery_spinner);

        followingDoctorInstructionBehaviorSpinner = (Spinner)findViewById(R.id.following_doctor_instruction_behavior_spinner);

        recoveryTreatmentSpinner = (Spinner)findViewById(R.id.recovery_treatment_spinner);

        limbRecoverySpinner = (Spinner)findViewById(R.id.limb_recovery_case_spinner);

        currentAuxList.clear();

        initSpinner();

        initData();
    }

    private void initData(){
        //测试
        psychologicalRecoverySpinner.setSelection(0);
        followingDoctorInstructionBehaviorSpinner.setSelection(1);

        recoveryTreatmentSpinner.setSelection(0);
        limbRecoverySpinner.setSelection(1);


        initAllSymptomData();
        initSelectSymptomData();
        initSymptomFlowChildViews();
        initSymptomPartFlowChildViews();
        initSymptomTypeFlowChildViews();
        initComplicationFlowChildViews();
        initNewSymptomFlowChildViews();


        smokeAmountET.setText("7");
        drinkAmountET.setText("2");
        sportCountET.setText("3");
        sportMinutesET.setText("30");
        eatAmountET.setText("300");
        saltAmountET.setText("3");

        initCurrentAuxList();
        updateAuxCheckView();
    }

    private void initAllSymptomData(){
        allSymptomLists.clear();

        allSymptomLists.add("无症状");
        allSymptomLists.add("喘、眼歪斜");
        allSymptomLists.add("半身不遂");
        allSymptomLists.add("舌强言蹇");
        allSymptomLists.add("智力障碍");
        allSymptomLists.add("其他症状");

//-----------------类型--------------------
        allSymptomTypeLists.clear();

        allSymptomTypeLists.add("蛛网膜下腔出血");
        allSymptomTypeLists.add("脑出血");
        allSymptomTypeLists.add("血栓形成");
        allSymptomTypeLists.add("脑栓塞");
        allSymptomTypeLists.add("腔隙性梗死");
        allSymptomTypeLists.add("分水岭脑梗死");
        allSymptomTypeLists.add("未分类脑卒中");

//-----------------并发症--------------------
        allComplicationLists.clear();
        allComplicationLists.add("梅疮");
        allComplicationLists.add("呼吸道感染");
        allComplicationLists.add("泌尿道感染");
        allComplicationLists.add("深静脉炎");
        allComplicationLists.add("其他");

//-----------------脑卒中部位-------------------
        allSymptomPartLists.clear();
        allSymptomPartLists.add("大脑半球左");
        allSymptomPartLists.add("大脑半球右");
        allSymptomPartLists.add("脑干");
        allSymptomPartLists.add("小脑");
        allSymptomPartLists.add("不确定");

//-----------------新卒中症状-------------------
        allNewSymptomLists.clear();
        allNewSymptomLists.add("构音障碍");
        allNewSymptomLists.add("失语");
        allNewSymptomLists.add("面瘫");
        allNewSymptomLists.add("感觉障碍");
        allNewSymptomLists.add("左侧肢体瘫");
        allNewSymptomLists.add("右侧肢体瘫");
        allNewSymptomLists.add("共济失调");
        allNewSymptomLists.add("昏迷");
        allNewSymptomLists.add("头疼");
        allNewSymptomLists.add("呕吐");
        allNewSymptomLists.add("意识障碍");
        allNewSymptomLists.add("眩晕");
        allNewSymptomLists.add("癫痫");
        allNewSymptomLists.add("无症状");
    }

    private void initSelectSymptomData(){

        selectedSymptomLists.clear();
        selectedSymptomLists.add("半身不遂");

        selectedSymptomTypeLists.clear();

        selectedSymptomTypeLists.add("蛛网膜下腔出血");
        selectedSymptomTypeLists.add("脑出血");

        selectedComplicationLists.clear();
        selectedComplicationLists.add("呼吸道感染");

        selectedSymptomPartLists.clear();
        selectedSymptomPartLists.add("大脑半球左");

        selectedNewSymptomLists.clear();
        selectedNewSymptomLists.add("构音障碍");
        selectedNewSymptomLists.add("失语");
        selectedNewSymptomLists.add("面瘫");
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
    private void initSymptomTypeFlowChildViews() {
        // TODO Auto-generated method stub
        Log.i(TAG, "symptomTypeFlowLayout " + symptomTypeFlowLayout);
        if(symptomTypeFlowLayout.getChildCount() > 0){
            symptomTypeFlowLayout.removeAllViews();
        }

        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 15;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        if(allSymptomTypeLists == null || allSymptomTypeLists.isEmpty()){
            return;
        }

        for(int i = 0; i < allSymptomTypeLists.size(); i ++){
            final TextView view = new TextView(this);
//            final HashMap<String, String > map = symptomLists.get(i);
            final String name = allSymptomTypeLists.get(i);

            view.setText(name);
            view.setTextSize(12);

            final CheckBox checkBox = new CheckBox(this);

            if(selectedSymptomTypeLists.contains(name)){
                checkBox.setChecked(true);
            }else{
                checkBox.setChecked(false);
            }

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.i(TAG, "initSymptomFlowChildViews onCheckedChanged ");
                    if(isChecked){

                        selectedSymptomTypeLists.add(name);

                    }else{

                        selectedSymptomTypeLists.remove(name);

                    }

                }
            });

            symptomTypeFlowLayout.addView(view, lp);
            symptomTypeFlowLayout.addView(checkBox, lp);
        }
    }
    private void initComplicationFlowChildViews() {
        // TODO Auto-generated method stub
        Log.i(TAG, "complicationFlowLayout " + complicationFlowLayout);
        if(complicationFlowLayout.getChildCount() > 0){
            complicationFlowLayout.removeAllViews();
        }

        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 15;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        if(allComplicationLists == null || allComplicationLists.isEmpty()){
            return;
        }
        boolean flag = false;
        for(int i = 0; i < allComplicationLists.size(); i ++){
            final TextView view = new TextView(this);
//            final HashMap<String, String > map = symptomLists.get(i);
            final String name = allComplicationLists.get(i);

            view.setText(name);
            view.setTextSize(12);

            if(name.equals("其他")){
                final EditText et = new EditText(this);
                flag = true;
            }else{
                flag = false;
            }
            final CheckBox checkBox = new CheckBox(this);

            if(selectedComplicationLists.contains(name)){
                checkBox.setChecked(true);
            }else{
                checkBox.setChecked(false);
            }

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.i(TAG, "initSymptomFlowChildViews onCheckedChanged ");
                    if(isChecked){

                        selectedComplicationLists.add(name);

                    }else{

                        selectedComplicationLists.remove(name);

                    }

                }
            });

            complicationFlowLayout.addView(view, lp);
            complicationFlowLayout.addView(checkBox, lp);
        }
    }
    private boolean noSure = false;
    private void initSymptomPartFlowChildViews() {
        // TODO Auto-generated method stub
        Log.i(TAG, "symptomPartFlowLayout " + symptomPartFlowLayout);
        if(symptomPartFlowLayout.getChildCount() > 0){
            symptomPartFlowLayout.removeAllViews();
        }

        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 15;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        if(allSymptomPartLists == null || allSymptomPartLists.isEmpty()){
            return;
        }
        noSure = false;

        for(int i = 0; i < allSymptomPartLists.size(); i ++){
            final TextView view = new TextView(this);
//            final HashMap<String, String > map = symptomLists.get(i);
            final String name = allSymptomPartLists.get(i);

            view.setText(name);
            view.setTextSize(12);

            final CheckBox checkBox = new CheckBox(this);

            if(name.equals("不确定") && selectedSymptomPartLists.contains("不确定")){
                noSure = true;
            }

            if(noSure && !name.equals("不确定")){
                //非无症状都不可选
                checkBox.setEnabled(false);
//                map.put("selected","false");
            }

            if(selectedSymptomPartLists.contains(name)){
                checkBox.setChecked(true);
            }else{
                checkBox.setChecked(false);
            }

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.i(TAG, "initSymptomFlowChildViews onCheckedChanged ");
                    if(isChecked){
                        if(name.equals("不确定")){
                            noSure = true;
                            selectedSymptomPartLists.clear();
                            selectedSymptomPartLists.add(name);
                            initSymptomPartFlowChildViews();
                        }else{
                            selectedSymptomPartLists.add(name);
                        }
                    }else{
                        if(name.equals("不确定")){
                            noSure = false;
                            selectedSymptomPartLists.remove(name);
                            initSymptomPartFlowChildViews();
                        }else{
                            selectedSymptomPartLists.remove(name);
                        }
                    }

                }
            });

            symptomPartFlowLayout.addView(view, lp);
            symptomPartFlowLayout.addView(checkBox, lp);
        }
    }
    private boolean noNewSymptom = false;
    private void initNewSymptomFlowChildViews() {
        // TODO Auto-generated method stub
        Log.i(TAG, "newSymptomFlowLayout " + newSymptomFlowLayout);
        if(newSymptomFlowLayout.getChildCount() > 0){
            newSymptomFlowLayout.removeAllViews();
        }

        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 15;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        if(allNewSymptomLists == null || allNewSymptomLists.isEmpty()){
            return;
        }
        noNewSymptom = false;

        for(int i = 0; i < allNewSymptomLists.size(); i ++){
            final TextView view = new TextView(this);
//            final HashMap<String, String > map = symptomLists.get(i);
            final String name = allNewSymptomLists.get(i);

            view.setText(name);
            view.setTextSize(12);

            final CheckBox checkBox = new CheckBox(this);

            if(name.equals("无症状") && selectedNewSymptomLists.contains("无症状")){
                noNewSymptom = true;
            }

            if(noNewSymptom && !name.equals("无症状")){
                //非无症状都不可选
                checkBox.setEnabled(false);
//                map.put("selected","false");
            }

            if(selectedNewSymptomLists.contains(name)){
                checkBox.setChecked(true);
            }else{
                checkBox.setChecked(false);
            }

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.i(TAG, "initNewSymptomFlowChildViews onCheckedChanged ");
                    if(isChecked){
                        if(name.equals("无症状")){
                            noNewSymptom = true;
                            selectedNewSymptomLists.clear();
                            selectedNewSymptomLists.add(name);
                            initNewSymptomFlowChildViews();
                        }else{
                            selectedNewSymptomLists.add(name);
                        }
                    }else{
                        if(name.equals("无症状")){
                            noNewSymptom = false;
                            selectedNewSymptomLists.remove(name);
                            initNewSymptomFlowChildViews();
                        }else{
                            selectedNewSymptomLists.remove(name);
                        }
                    }

                }
            });

            newSymptomFlowLayout.addView(view, lp);
            newSymptomFlowLayout.addView(checkBox, lp);
        }
    }
    private void initSpinner(){
        final List<String> psyRecoveryList = getPsyRecoveryData();
        ArrayAdapter<String> psyRecoveryAdapter = new ArrayAdapter<String>
                (FUTableHistoryStrokeActivity.this, R.layout.spinner_item,getPsyRecoveryData()){
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
                (FUTableHistoryStrokeActivity.this, R.layout.spinner_item,getFDIBData()){
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


        final List<String> recoveryTreatmentList = getRecoveryTreatmentData();
        ArrayAdapter<String> recoveryTreatmentAdapter = new ArrayAdapter<String>
                (FUTableHistoryStrokeActivity.this, R.layout.spinner_item,getRecoveryTreatmentData()){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(recoveryTreatmentList.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };

        //设置样式
        recoveryTreatmentAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        recoveryTreatmentSpinner.setAdapter(recoveryTreatmentAdapter);

        recoveryTreatmentSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.i(TAG, "康复治疗方式 touch ");
                closeSoftKeyboard();
                return false;
            }
        });

        recoveryTreatmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                recoveryTreatmentString = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        final List<String> limbRecvList = getLimbRecvData();
        ArrayAdapter<String> limbRecvAdapter = new ArrayAdapter<String>
                (FUTableHistoryStrokeActivity.this, R.layout.spinner_item,getLimbRecvData()){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(limbRecvList.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };

        //设置样式
        limbRecvAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        limbRecoverySpinner.setAdapter(limbRecvAdapter);

        limbRecoverySpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.i(TAG, "肢体康复情况 touch ");
                closeSoftKeyboard();
                return false;
            }
        });

        limbRecoverySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                limbRecoveryString = parent.getItemAtPosition(position).toString();
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
    //获取康复治疗方式数据
    private List<String> getRecoveryTreatmentData(){
        List<String> dataList = new ArrayList<String>();

        dataList.add("按摩");
        dataList.add("针灸");
        dataList.add("运动训练");
        dataList.add("其他");
        return dataList;
    }
    //获取肢体恢复情况数据
    private List<String> getLimbRecvData(){
        List<String> dataList = new ArrayList<String>();

        dataList.add("好");
        dataList.add("一般");
        dataList.add("差");
        return dataList;
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

        addAuxDialog = new MyDialog(FUTableHistoryStrokeActivity.this, functionListView,R.style.load_dialog);
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
        allAuxLists.clear();
        HashMap<String,String> map = new HashMap<>();
        map.put("name", "血常规");
        allAuxLists.add(map);

        HashMap<String,String> map2 = new HashMap<>();
        map2.put("name","尿常规");
        allAuxLists.add(map2);


//        HashMap<String,String> map3 = new HashMap<>();
//        map3.put("name", "肝功能");
//        allAuxLists.add(map3);


        HashMap<String,String> map4 = new HashMap<>();
        map4.put("name", "肾功能");
        allAuxLists.add(map4);

//        HashMap<String,String> map5 = new HashMap<>();
//        map5.put("name", "生化全项");
//        allAuxLists.add(map5);

        HashMap<String,String> map6 = new HashMap<>();
        map6.put("name", "血糖");
        allAuxLists.add(map6);

        HashMap<String,String> map7 = new HashMap<>();
        map7.put("name", "血流变参数");
        allAuxLists.add(map7);
    }
    private void initCurrentAuxList(){

        currentAuxList.clear();

        HashMap<String,String> map = new HashMap<>();
        map.put("name","血脂全项");
        currentAuxList.add(map);

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
                if(map.get("name").equals("尿常规")){
                    urineRoutineView = new FUTableAuxUrineRoutineView(FUTableHistoryStrokeActivity.this,auxAddLayout,"脑卒中");
                }else if(map.get("name").equals("血常规")){
                    bloodRoutineView = new FUTableAuxBloodRoutineView(FUTableHistoryStrokeActivity.this,auxAddLayout,"脑卒中");
                }else if(map.get("name").equals("血糖")){
                    Log.i(TAG,"auxAddLayout "+auxAddLayout);
                    bloodSugarView = new FUTableAuxBloodSugarView(FUTableHistoryStrokeActivity.this,auxAddLayout,"脑卒中");

                }else if(map.get("name").equals("肾功能")){
                    renalFunctionView = new FUTableAuxRenalFunctionView(FUTableHistoryStrokeActivity.this,auxAddLayout,"脑卒中");

                }else if(map.get("name").equals("血流变参数")){
                    hemorheologyParamView = new FUTableAuxHemorheologyParamView(FUTableHistoryStrokeActivity.this,auxAddLayout,"脑卒中");

                }
            }
        }


    }
}
