package com.housekeeper.ar.healthhousekeeper.followup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.FlowLayout;
import com.housekeeper.ar.healthhousekeeper.HttpUtil;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
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
 * Created by lenovo on 2016/3/5.
 */
//随访表
public class FollowUpTableActivity extends BaseActivity {


    String TAG = "FollowUpTableActivity";



    String http, httpUrl;
    String get_info_url = "xys/healthButler/patient/";
    String put_info_url = "xys/healthButler/followupSheet/add";
    String id;
    JSONObject jo_basic_data;
    private ToastCommom toastCommom;
    private FlowLayout diagnoseFlowLayout,commonSickFlowLayout,followUpTypeFlowLayout;
    ArrayList<HashMap<String, String>> diagnoseList = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> commonSickList = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> followUpTypeList = new ArrayList<HashMap<String, String>>();
    private EditText nameET,ageET,sexET,basicHeightET,basicWeightET,nationET,bloodET,rhBloodET;
    private EditText addressET,telET,familyTelET;
    private Button moreBtn;
    //基础指标 身高、体重、基础代谢率、体脂率、内脏脂肪率
    private EditText heightET,weightET,metabolicRateET,bodyFatRateET,visceralFatRateET,bloodPressureET;
    private TextView bmiTV,metabolicRateLevelTV,bodyFatRateLevelTV,visceralFatRateLevelTV;
//    private Spinner userTypeSpnner;
    private Button catchBtn,backBtn;

    private MyApp myApp;

    private InnerScrollView historyDiagnoseInnerScrollView;//既往诊断滚动
    private InnerScrollView commonSickInnerScrollView;//常见病滚动
    private InnerScrollView fuTypeInnerScrollView;//随访类型滚动
    private ScrollView sv;

    private TextView diagnoseMoreBtn;
    private PopupWindow mHistoryDiagnosePopupWindow;
    JSONObject jo_base_info, jo_basic_indexes;
    JSONArray ja_diagnosis;
    JSONObject jo_symptomslist, jo_lifestyleGuide, jo_diagnosticTests;
    //如果常见病例和随访类型也要长按删除，在长按时需要设置selectCommonSick等标志位，在pop中使用。如果长按常见病例，既往诊断的pop消失
//    private PopupWindow mCommonSickPopupWindow;
//    private PopupWindow mFUTypePopupWindow;
//
//    private boolean selectHistoryDiagnose=false;
//    private boolean selectCommonSick=false;
//    private boolean selectFUType=false;

    private TextView diagnosisTV;

    private int ADD_DIAGNOSE = 2;


    private FUTableMedicineView medicineView;
    private LinearLayout medicineAddLayout;

    private List<HashMap<String,String>> medicineData = new ArrayList<>();
    private Button historyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_table);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(FollowUpTableActivity.this);
        myApp = (MyApp)getApplication();
        http = myApp.getHttp();
        final Intent intent = getIntent();
        if(intent != null){

            id = intent.getStringExtra("id");
        }
        init_patient_data(id);
        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        historyBtn = (Button)findViewById(R.id.fu_history_btn);
        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FollowUpTableActivity.this,FUTableHistoryActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        nameET = (EditText)findViewById(R.id.name_et);
        sexET = (EditText)findViewById(R.id.sex_et);

        ageET = (EditText)findViewById(R.id.age_et);
        basicHeightET = (EditText)findViewById(R.id.basic_hight_et);
        basicWeightET = (EditText)findViewById(R.id.basic_weight_et);
        nationET = (EditText)findViewById(R.id.nation_et);
        bloodET = (EditText)findViewById(R.id.blood_et);
        rhBloodET = (EditText)findViewById(R.id.rh_blood_et);

        addressET = (EditText)findViewById(R.id.address_et);
        telET = (EditText)findViewById(R.id.tel_et);
        familyTelET = (EditText)findViewById(R.id.family_tel_et);

        sv = (ScrollView)findViewById(R.id.sv);

        diagnoseFlowLayout = (FlowLayout)findViewById(R.id.diagnose_flowlayout);

        historyDiagnoseInnerScrollView = (InnerScrollView)findViewById(R.id.history_diagnose_inner_scrollview);
        historyDiagnoseInnerScrollView.setParentScrollView(sv);

        commonSickInnerScrollView = (InnerScrollView)findViewById(R.id.common_sick_inner_scrollview);
        commonSickInnerScrollView.setParentScrollView(sv);

        fuTypeInnerScrollView = (InnerScrollView)findViewById(R.id.follow_up_type_inner_scrollview);
        fuTypeInnerScrollView.setParentScrollView(sv);



        bmiTV = (TextView)findViewById(R.id.bmi_tv);
        metabolicRateLevelTV = (TextView)findViewById(R.id.metabolic_rate_level);
        bodyFatRateLevelTV = (TextView)findViewById(R.id.body_fat_rate_level);
        visceralFatRateLevelTV = (TextView)findViewById(R.id.visceral_fat_rate_level);
        bloodPressureET = (EditText)findViewById(R.id.blood_pressure_et);
        heightET = (EditText)findViewById(R.id.height_et);
        weightET = (EditText)findViewById(R.id.weight_et);
        metabolicRateET = (EditText)findViewById(R.id.metabolic_rate_et);
        bodyFatRateET = (EditText)findViewById(R.id.body_fat_rate_et);
        visceralFatRateET = (EditText)findViewById(R.id.visceral_fat_rate_et);

        commonSickFlowLayout = (FlowLayout)findViewById(R.id.common_sick_flowlayout);
        followUpTypeFlowLayout = (FlowLayout)findViewById(R.id.follow_up_type_flowlayout);
        catchBtn = (Button)findViewById(R.id.catch_btn);

        diagnosisTV = (TextView)findViewById(R.id.diagnosis_tv);
        diagnosisTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(diagnoseList.isEmpty()){
                    Toast.makeText(FollowUpTableActivity.this,"无诊断",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(FollowUpTableActivity.this,FUTableDiagnosisActivity.class);
                Bundle bundle=new Bundle();

                bundle.putParcelableArrayList("diagnose", (ArrayList)diagnoseList);
                intent.putExtras(bundle);
                startActivityForResult(intent,ADD_DIAGNOSE);
            }
        });

        diagnoseMoreBtn = (TextView)findViewById(R.id.diagonose_add_btn);
        diagnoseMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"既往诊断更多点击diagnoseMoreBtn");
                Intent intent = new Intent(FollowUpTableActivity.this,FUTableAddHistoryDiagnoseActivity.class);
                Bundle bundle=new Bundle();

                bundle.putParcelableArrayList("diagnose", (ArrayList) diagnoseList);
                intent.putExtras(bundle);
                startActivityForResult(intent,ADD_DIAGNOSE);

            }
        });

        catchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FollowUpTableActivity.this,FollowUpTableCatchActivity.class);
                startActivity(intent);
            }
        });

        setPatientData();

        getDiagnoseList();
        getCommonSickList();
        getFollowUpTypeList();
        initDiagnoseFlowChildViews();
        initCommonSickFlowChildViews();
        initFollowUpTypeFlowChildViews();
        setBMI();
        initMedicine();
    }
    @Override
    protected void onResume() {
        super.onResume();
//        init();
    }
    void init_patient_data(String id){
        httpUrl = http + get_info_url + id;
        HttpPost post = HttpUtil.getPost(httpUrl, null);
        JSONObject joRev = HttpUtil.getString(post, 3);
        Log.i(TAG, "init_patient_data:"+joRev.toString());
        try {
            jo_basic_data = joRev.getJSONObject("value");
            Log.i(TAG, "jo_basic_data:"+jo_basic_data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void setPatientData(){
        String patientId = myApp.getPatientId();
        try {
            nameET.setText(jo_basic_data.getString("name"));
            sexET.setText(jo_basic_data.getString("sex"));
            ageET.setText(jo_basic_data.getString("age"));
            basicHeightET.setText(jo_basic_data.getInt("height")+"cm");
            basicWeightET.setText(jo_basic_data.getInt("weight")+"kg");
            bloodET.setText(jo_basic_data.getString("bloodType"));
            rhBloodET.setText(jo_basic_data.getString("rhType"));
            addressET.setText(jo_basic_data.getString("address"));
            familyTelET.setText(jo_basic_data.getString("familyPhone"));
            telET.setText(jo_basic_data.getString("phone"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    void saveBaseInfo(){
        try {
            jo_base_info.put("id", jo_basic_data.getInt("id"));
            jo_base_info.put("name",jo_basic_data.getString("name"));
            jo_base_info.put("nation",jo_basic_data.getString("nation"));
            jo_base_info.put("phone",jo_basic_data.getString("phone"));
            jo_base_info.put("rhType",jo_basic_data.getString("rhType"));
            jo_base_info.put("sex",jo_basic_data.getString("sex"));
            jo_base_info.put("height",jo_basic_data.getInt("height"));
            jo_base_info.put("weight",jo_basic_data.getInt("weight"));
            jo_base_info.put("address", jo_basic_data.getString("address"));
            jo_base_info.put("age", jo_basic_data.getString("age"));
            jo_base_info.put("bloodType",jo_basic_data.getString("bloodType"));
            jo_base_info.put("familyPhone",jo_basic_data.getString("familyPhone"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    void saveBasicIndexes(){
        try {
            jo_basic_indexes.put("bloodPressure",bloodPressureET.getText().toString());
            jo_basic_indexes.put("bmr",metabolicRateET.getText().toString());
            jo_basic_indexes.put("bfr",bodyFatRateET.getText().toString());
            jo_basic_indexes.put("vfr",visceralFatRateET.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void closeSoftKeyboard(){

        /**隐藏软键盘**/
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    //获取诊断的列表
    private void getDiagnoseList(){
        HashMap<String,String> map = new HashMap<>();
        map.put("diagnose","糖尿病");

        HashMap<String,String> map2 = new HashMap<>();
        map2.put("diagnose","高血压");

        HashMap<String,String> map3 = new HashMap<>();
        map3.put("diagnose","冠心病");

        HashMap<String,String> map4 = new HashMap<>();
        map4.put("diagnose","脑卒中");

        HashMap<String,String> map5 = new HashMap<>();
        map5.put("diagnose","血脂异常");


        diagnoseList.add(map);
        diagnoseList.add(map2);
        diagnoseList.add(map3);
        diagnoseList.add(map4);
        diagnoseList.add(map5);

    }
    //获取常见病表的列表
    private void getCommonSickList(){
        HashMap<String,String> map = new HashMap<>();
        map.put("commonSick","糖尿病");

        HashMap<String,String> map2 = new HashMap<>();
        map2.put("commonSick","脑卒中");

        HashMap<String,String> map3 = new HashMap<>();
        map3.put("commonSick","高血压");

        HashMap<String,String> map4 = new HashMap<>();
        map4.put("commonSick","冠心病");

        HashMap<String,String> map5 = new HashMap<>();
        map5.put("commonSick","血脂异常");

//        HashMap<String,String> map6 = new HashMap<>();
//        map6.put("commonSick","COPD");
//
//        HashMap<String,String> map7 = new HashMap<>();
//        map7.put("commonSick","结核病");
//        HashMap<String,String> map8 = new HashMap<>();
//        map8.put("commonSick","通风");


        commonSickList.add(map);
        commonSickList.add(map2);
        commonSickList.add(map3);
        commonSickList.add(map4);
        commonSickList.add(map5);
//        commonSickList.add(map6);
//        commonSickList.add(map7);
//        commonSickList.add(map8);

    }
    //获取随访类型的列表
    private void getFollowUpTypeList(){
        HashMap<String,String> map = new HashMap<>();
        map.put("fuType","老年人健康管理");

        HashMap<String,String> map2 = new HashMap<>();
        map2.put("fuType","孕产健康管理");

        HashMap<String,String> map3 = new HashMap<>();
        map3.put("fuType","健身减肥");

        HashMap<String,String> map4 = new HashMap<>();
        map4.put("fuType", "儿童营养与成长");






        HashMap<String,String> map5 = new HashMap<>();
        map5.put("fuType","老年人健康管理");

        HashMap<String,String> map6 = new HashMap<>();
        map6.put("fuType","孕产健康管理");

        HashMap<String,String> map7 = new HashMap<>();
        map7.put("fuType","健身减肥");

        HashMap<String,String> map8 = new HashMap<>();
        map8.put("fuType", "儿童营养与成长");

        HashMap<String,String> map9 = new HashMap<>();
        map9.put("fuType","老年人健康管理");

        HashMap<String,String> map10 = new HashMap<>();
        map10.put("fuType","孕产健康管理");

        HashMap<String,String> map11 = new HashMap<>();
        map11.put("fuType","健身减肥");

        HashMap<String,String> map12 = new HashMap<>();
        map12.put("fuType", "儿童营养与成长");


        followUpTypeList.add(map);
        followUpTypeList.add(map2);
        followUpTypeList.add(map3);
        followUpTypeList.add(map4);




        followUpTypeList.add(map5);
        followUpTypeList.add(map6);
        followUpTypeList.add(map7);
        followUpTypeList.add(map8);


        followUpTypeList.add(map9);
        followUpTypeList.add(map10);
        followUpTypeList.add(map11);
        followUpTypeList.add(map12);


    }

    //基础指标-bmi
    private void setBMI(){
       // BMI = 体重 / （身高）^2
        bmiTV.setText("0");
        if(!heightET.getText().toString().equals("")&&!weightET.getText().toString().equals("")){
            double height = Double.valueOf(heightET.getText().toString());
            double weight = Double.valueOf(weightET.getText().toString());
            if (height != 0 && weight != 0){
                double bmi = weight/(height*height);
                bmiTV.setText(""+bmi);
            }
        }

    }
    View deleteView;
    HashMap<String,String> deleteMap;
    //初始化流式布局
    private void initDiagnoseFlowChildViews() {
        // TODO Auto-generated method stub

        if(diagnoseFlowLayout.getChildCount() > 0){
            diagnoseFlowLayout.removeAllViews();
        }

        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        if(diagnoseList == null || diagnoseList.isEmpty()){
            return;
        }
//        Log.i(TAG, "initHistoryDateChildViews selectedDate " + selectedDate);
        for(int i = 0; i < diagnoseList.size(); i ++){
            final TextView view = new TextView(this);
            final HashMap<String, String > map = diagnoseList.get(i);
            final String name = map.get("diagnose");

            view.setText(name);
            Log.i(TAG, "diagnoseFlowLayout result" + name);
            view.setTextSize(12);


            view.setTextColor(Color.BLACK);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_flow_shape_unpressed));

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    view.setTextColor(Color.WHITE);
                    view.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_flow_shape_pressed));
                    deleteView = view;
                    deleteMap = map;
                    getPopupWindowInstance();

                    mHistoryDiagnosePopupWindow.showAsDropDown(v, v.getWidth()
                            - mHistoryDiagnosePopupWindow.getWidth() - 10, -80);

                    return false;
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (name.equals("糖尿病")) {

                        Intent intent = new Intent(FollowUpTableActivity.this, FUTableDiabetesActivity.class);
                        startActivity(intent);
                    } else if (name.equals("高血压")) {
                        Intent intent = new Intent(FollowUpTableActivity.this, FUTAbleHypertensionActivity.class);
                        startActivity(intent);
                    }else if (name.equals("冠心病")) {
                        Intent intent = new Intent(FollowUpTableActivity.this, FUTAbleCHDActivity.class);
                        startActivity(intent);
                    }else if (name.equals("脑卒中")) {
                        Intent intent = new Intent(FollowUpTableActivity.this, FUTableStrokeActivity.class);
                        startActivity(intent);
                    }else if (name.equals("血脂异常")) {
                        Intent intent = new Intent(FollowUpTableActivity.this, FUTAbleDyslipidemiaActivity.class);
                        startActivity(intent);
                    }
                }
            });
            diagnoseFlowLayout.addView(view, lp);
        }
    }

    private void getPopupWindowInstance() {
        if (null != mHistoryDiagnosePopupWindow) {
            mHistoryDiagnosePopupWindow.dismiss();
            return;
        } else {
            initPopuptWindow();
        }
    }

    /*
     * 创建PopupWindow
     */
    private void initPopuptWindow() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View popupWindowView = layoutInflater.inflate(
                R.layout.activity_followup_table_delete_popupwindown, null);
        TextView deleteTV = (TextView) popupWindowView
                .findViewById(R.id.delete_tv);




        deleteTV.setOnClickListener(popupWindowClickListener);





        // 创建一个PopupWindow
        // 参数1：contentView 指定PopupWindow的内容
        // 参数2：width 指定PopupWindow的width
        // 参数3：height 指定PopupWindow的height
//		mPopupWindow = new PopupWindow(popupWindowView, getWindowManager()
//				.getDefaultDisplay().getWidth() /3, getWindowManager()
//				.getDefaultDisplay().getHeight() / 5);

        mHistoryDiagnosePopupWindow = new PopupWindow(popupWindowView, 200, 300);
        mHistoryDiagnosePopupWindow.setFocusable(true);
        mHistoryDiagnosePopupWindow.setOutsideTouchable(true);
        mHistoryDiagnosePopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mHistoryDiagnosePopupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                /**** 如果点击了popupwindow的外部，popupwindow也会消失 ****/
                Log.i(TAG, "pop onTouch " + event.getAction());
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    mHistoryDiagnosePopupWindow.dismiss();
//                    mHistoryDiagnosePopupWindow = null;
                    initDiagnoseFlowChildViews();
                    Log.i(TAG, "onTouch22");
                    return false;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    diagnoseFlowLayout.removeView(deleteView);
                    diagnoseList.remove(deleteMap);

                    initDiagnoseFlowChildViews();
                    Log.i(TAG, "pop deleteMap " + deleteMap.toString());
                    mHistoryDiagnosePopupWindow.dismiss();
                    mHistoryDiagnosePopupWindow = null;

                }
                return false;
            }
        });
    }


    private View.OnClickListener popupWindowClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.delete_tv:

//                    diagnoseFlowLayout.removeView(deleteView);
//                    diagnoseList.remove(deleteMap);
//
//                    mHistoryDiagnosePopupWindow.dismiss();
//                    mHistoryDiagnosePopupWindow = null;

                    break;


                default:
                    break;
            }

        }
    };
    //初始化流式布局
    private void initCommonSickFlowChildViews() {
        // TODO Auto-generated method stub

        if(commonSickFlowLayout.getChildCount() > 0){
            commonSickFlowLayout.removeAllViews();
        }

        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        if(commonSickList == null || commonSickList.isEmpty()){
            return;
        }
//        Log.i(TAG, "initHistoryDateChildViews selectedDate " + selectedDate);
        for(int i = 0; i < commonSickList.size(); i ++){
            final TextView view = new TextView(this);
            final HashMap<String, String > map = commonSickList.get(i);
            final String name = map.get("commonSick");

            view.setText(name);
            Log.i(TAG, "commonSickFlowLayout result" + name);
            view.setTextSize(12);


            view.setTextColor(Color.BLACK);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_flow_shape_unpressed));

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (HashMap<String, String> map : diagnoseList) {
                        if (map.get("diagnose").equals(name)) {
                            Toast.makeText(FollowUpTableActivity.this, name + "已经在诊断中", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    addToDiagnoseListDialog(name);


                }
            });

            commonSickFlowLayout.addView(view, lp);
        }
    }
    private void addToDiagnoseListDialog(final String name){

        LayoutInflater inflater =getLayoutInflater();
        final View functionListView = inflater.inflate(R.layout.activity_follow_up_table_add_diagnose_dialog, null);

        final MyDialog dialog = new MyDialog(FollowUpTableActivity.this, functionListView,R.style.load_dialog);
        int width = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        TextView nameTV = (TextView)functionListView.findViewById(R.id.name_tv);
        nameTV.setText("确定将" + name + "添加至诊断中吗？");

        Button okBtn = (Button)functionListView.findViewById(R.id.ok_btn);

        Button cancelBtn = (Button)functionListView.findViewById(R.id.cancel_btn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> diagMap = new HashMap<String, String>();
                diagMap.put("diagnose", name);
                diagnoseList.add(diagMap);
                initDiagnoseFlowChildViews();
                dialog.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    //初始化流式布局
    private void initFollowUpTypeFlowChildViews() {
        // TODO Auto-generated method stub

        if(followUpTypeFlowLayout.getChildCount() > 0){
            followUpTypeFlowLayout.removeAllViews();
        }

        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        if(followUpTypeList == null || followUpTypeList.isEmpty()){
            return;
        }
//        Log.i(TAG, "initHistoryDateChildViews selectedDate " + selectedDate);
        for(int i = 0; i < followUpTypeList.size(); i ++){
            final TextView view = new TextView(this);
            final HashMap<String, String > map = followUpTypeList.get(i);
            final String name = map.get("fuType");

            view.setText(name);
            Log.i(TAG, "followUpTypeFlowLayout result" + name);
            view.setTextSize(12);


            view.setTextColor(Color.BLACK);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_flow_shape_unpressed));



            followUpTypeFlowLayout.addView(view, lp);
        }
    }

    List<HashMap<String,String>> medicineDatas = new ArrayList<>();
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG,"onActivityResult ");
        if(requestCode == ADD_DIAGNOSE && resultCode == RESULT_OK){
            //添加诊断
            ///测试activity间传递list
            Bundle bundle=data.getExtras();

            ArrayList list2 = bundle.getParcelableArrayList("diagnose");


            if(list2 != null && !list2.isEmpty()){

                Log.i(TAG, "onActivityResult测试activity间传递list list2 " + list2);
                for(int i=0;i<list2.size();i++)
                {
                    HashMap<String ,String> map=(HashMap<String,String>)list2.get(i);
                    Log.i("GXF", "onActivityResult测试activity间传递list map " + map);
//                    if(currentMedicineLists != null && !currentMedicineLists.isEmpty()){
////						if(currentMedicineLists.contains(map)){
//                        if(isDuplicateMedicineName(map)){
//
//                            //弹出对话框，当前列表中已经存在该医嘱,用contains比较还是for循环比较yizhu
//                            showDuplicateMedicineDialog(map);
//
//                        }else{
//                            currentMedicineLists.add(map);
//                        }
//                    }else{
//                        currentMedicineLists.add(map);
//                    }

                    diagnoseList.add(map);
                }
            }


          initDiagnoseFlowChildViews();
        }else{
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
                    Log.i(TAG, "onActivityResult测试activity间传递list list2 " + list2);
                    for(int i=0;i<list2.size();i++)
                    {
                        HashMap<String ,String> map=(HashMap<String,String>)list2.get(i);
                        Log.i(TAG, "onActivityResult测试activity间传递list map " + map);
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
    }

    private void initMedicine(){
        medicineAddLayout = (LinearLayout)findViewById(R.id.medicine_add_layout);
        getALLMedicineData();
        medicineView = new FUTableMedicineView(FollowUpTableActivity.this,medicineAddLayout,medicineData,sv);

    }
    private void getALLMedicineData(){
        medicineData.clear();
        HashMap<String,String> map = new HashMap<>();
        map.put("drugName","阿司匹林");
        map.put("drugId", "1");
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


}
