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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.FlowLayout;
import com.housekeeper.ar.healthhousekeeper.HttpUtil;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyDialog;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 随访历史-高血压
 * Created by Lenovo on 2016/12/2.
 */
public class FUTAbleHistoryHypertensionActivity extends BaseActivity {
    String TAG="HistoryHypertension";
    String id;
    String get_diseas_info_url = "http://123.56.155.17:8080/xys/healthButler/followupSheet/history/dieaseCheckDetail/";//{dieaseCheckId}
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

    private FUTableAuxUrineRoutineView urineRoutineView;
    private FUTableAuxBloodRoutineView bloodRoutineView;
    private FUTableAuxRenalFunctionView renalFunctionView;

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

    private TextView titleTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_table_history_hypertension);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(FUTAbleHistoryHypertensionActivity.this);

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
            titleTV.setText("高血压-"+time);
            id = intent.getStringExtra("id");
        }
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

        bmiLevelTV = (TextView)findViewById(R.id.bmi_level_tv);
        bmiLevelTV.setOnClickListener(clickListener);
        bmiET = (EditText)findViewById(R.id.bmi_et);
        bmiET.addTextChangedListener(bmiTextWacher);


        currentAuxList.clear();

        initSpinner();

        try {
            initData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initData() throws JSONException {
        HttpPost post = HttpUtil.getPost(get_diseas_info_url+id, null);
        JSONObject jsonObject = HttpUtil.getString(post, 3);
        Log.i(TAG, "jsonObject:"+jsonObject.toString());

        JSONArray jaDiagnosticTests = jsonObject.getJSONArray("diagnosticTests");
        JSONObject joDiagnosticTests = jaDiagnosticTests.getJSONObject(0);
        JSONObject joItems = joDiagnosticTests.getJSONObject("items");
        JSONObject joLifestyleGuide = jsonObject.getJSONObject("lifestyleGuide");
        JSONObject josymptomslist = jsonObject.getJSONObject("symptomslist");

        //测试
        psychologicalRecoverySpinner.setSelection(0);
        followingDoctorInstructionBehaviorSpinner.setSelection(1);

        initAllSymptomData();
        initSelectSymptomData(josymptomslist);
        initSymptomFlowChildViews();

        bloodPressHighET.setText(String.valueOf(josymptomslist.getDouble("pressureS")));
        bloodPressLowET.setText(String.valueOf(josymptomslist.getDouble("pressureD")));
        bmiET.setText(String.valueOf(josymptomslist.getDouble("bmi")));
        weightET.setText(String.valueOf(josymptomslist.getDouble("weight")));
        heartRateET.setText(String.valueOf(josymptomslist.getDouble("heartRate")));

        smokeAmountET.setText(String.valueOf(joLifestyleGuide.getDouble("smokeVolume")));
        drinkAmountET.setText(String.valueOf(joLifestyleGuide.getDouble("drinkVolume")));
        sportCountET.setText(String.valueOf(joLifestyleGuide.getDouble("sportTimesW")));
        sportMinutesET.setText(String.valueOf(joLifestyleGuide.getDouble("sportMin")));
        eatAmountET.setText(String.valueOf(joLifestyleGuide.getDouble("stapleGram")));
        saltAmountET.setText(String.valueOf(joLifestyleGuide.getDouble("salt")));

        initCurrentAuxList();
        updateAuxCheckView();
    }

    private void initAllSymptomData(){
        allSymptomLists.clear();

        allSymptomLists.add("无症状");
        allSymptomLists.add("头痛头晕");
        allSymptomLists.add("恶心呕吐");
        allSymptomLists.add("眼花耳鸣");
        allSymptomLists.add("呼吸困难");
        allSymptomLists.add("心悸胸闷");
        allSymptomLists.add("鼻出血不止");
        allSymptomLists.add("四肢发麻");
        allSymptomLists.add("下肢水肿");

    }

    private void initSelectSymptomData(JSONObject josymptomslist) throws JSONException {
        selectedSymptomLists.clear();
        if(josymptomslist.getInt("headache") == 1){
            selectedSymptomLists.add("头痛头晕");
        }
        if(josymptomslist.getInt("symptomless") == 1){
            selectedSymptomLists.add("无症状");
        }
        if(josymptomslist.getInt("edemaOfLe") == 1){
            selectedSymptomLists.add("下肢浮肿");
        }
        if(josymptomslist.getInt("pct") == 1){
            selectedSymptomLists.add("恶心呕吐");
        }
        if(josymptomslist.getInt("acroanesthesia") == 1){
            selectedSymptomLists.add("四肢发麻");
        }
        if(josymptomslist.getInt("nAndV") == 1){
            selectedSymptomLists.add("心悸胸闷");
        }
        if(josymptomslist.getInt("dyspnea") == 1){
            selectedSymptomLists.add("呼吸困难");
        }
        if(josymptomslist.getInt("tAndV") == 1){
            selectedSymptomLists.add("眼花耳鸣");
        }
        if(josymptomslist.getInt("nasalBleeding") == 1){
            selectedSymptomLists.add("鼻出血不止");
        }
//
//
//        selectedSymptomLists.add("眼花耳鸣");
//        selectedSymptomLists.add("鼻出血不止");
    }
    boolean noSymptom = false;
    //初始化流式布局
    private void initSymptomFlowChildViews() {
        // TODO Auto-generated method stub
        Log.i(TAG,"symptomFlowLayout "+symptomFlowLayout);
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
        final List<String> psyRecoveryList = getPsyRecoveryData();
        ArrayAdapter<String> psyRecoveryAdapter = new ArrayAdapter<String>
                (FUTAbleHistoryHypertensionActivity.this, R.layout.spinner_item,getPsyRecoveryData()){
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
                (FUTAbleHistoryHypertensionActivity.this, R.layout.spinner_item,getFDIBData()){
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
                    if(!bloodPressLevelTV.getText().toString().equals("正常")&&!bloodPressLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(FUTAbleHistoryHypertensionActivity.this,FUTableAuxBloodPressUnderstandingActivity.class);
                        startActivity(intent);
                    }
                    break;
                case R.id.bmi_level_tv:
                    if(!bmiLevelTV.getText().toString().equals("正常")&& !bmiLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(FUTAbleHistoryHypertensionActivity.this,FUTableAuxHeightWeightUnderstandingActivity.class);
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

        addAuxDialog = new MyDialog(FUTAbleHistoryHypertensionActivity.this, functionListView,R.style.load_dialog);
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
        map2.put("name", "尿常规");
        allAuxLists.add(map2);
        HashMap<String,String> map3 = new HashMap<>();
        map3.put("name", "血常规");
        allAuxLists.add(map3);
        HashMap<String,String> map4 = new HashMap<>();
        map4.put("name", "肾功能");
        allAuxLists.add(map4);

    }
    private void initCurrentAuxList(){

        currentAuxList.clear();

        HashMap<String,String> map = new HashMap<>();
        map.put("name","血常规");
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

                    urineRoutineView = new FUTableAuxUrineRoutineView(FUTAbleHistoryHypertensionActivity.this,auxAddLayout,"高血压");
                }else if(map.get("name").equals("血常规")){

                    bloodRoutineView = new FUTableAuxBloodRoutineView(FUTAbleHistoryHypertensionActivity.this,auxAddLayout,"高血压");
                }else if(map.get("name").equals("肾功能")){

                    renalFunctionView = new FUTableAuxRenalFunctionView(FUTAbleHistoryHypertensionActivity.this,auxAddLayout,"高血压");
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
                int high = Double.valueOf(bloodPressHighET.getText().toString()).intValue();
                int low = Double.valueOf(bloodPressLowET.getText().toString()).intValue();
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
