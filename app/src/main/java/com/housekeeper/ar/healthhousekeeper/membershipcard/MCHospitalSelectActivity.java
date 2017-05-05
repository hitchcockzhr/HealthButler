package com.housekeeper.ar.healthhousekeeper.membershipcard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.RoundImageView;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lenovo on 2016/3/5.
 */
//会员卡选择患者-医院界面
public class MCHospitalSelectActivity extends Activity implements MembershipCardTab.OnTabActivityResultListener {


    String TAG = "MCHospitalSelectActivity";

    private List<HashMap<String,String>>  patientData = new ArrayList<HashMap<String, String>>();
    private HashMap<String,String> patientMap = new HashMap<>();
    private HashMap<String,String> doctorMap = new HashMap<>();

//    private Button okBtn;
//    private Button cancelBtn;

    private TextView patientMoreView;
    private TextView hospitalMoreView;
//    private Button quyaoBtn;
//    private Button seeDoctorBtn;

    private RelativeLayout patientInitLayout;
    private RelativeLayout patientLayout;
    private RelativeLayout hospitalInitLayout;
//    private RelativeLayout hospitalLayout;

    private TextView patientNameView;
    private TextView patientSexView,patientAgeView,patientBingLiHaoView,patientTelView;
//    private ImageView patientTelImageView,patientWeiXinImageView,patientMessageImageView;
    private RoundImageView patientHeaderImageView;

//    private TextView doctorNameView,doctorHospitalView,doctorTelView,doctorAddressView;

    private RoundImageView hospitalHeaderImageView;

    private ToastCommom toastCommom;


    private int PATIENT_CODE=0,DOCTOR_CODE=1;

//    private Button backBtn;

    private String patientID="";
    MyApp myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membershipcard_hospital_index);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(MCHospitalSelectActivity.this);

        myApp = (MyApp)getApplication();


        patientInitLayout = (RelativeLayout)findViewById(R.id.init_patient_layout);
        patientLayout = (RelativeLayout)findViewById(R.id.patient_layout);
        hospitalInitLayout = (RelativeLayout)findViewById(R.id.hospital_init_layout);


        patientMoreView = (TextView)findViewById(R.id.patient_add_tv);
        hospitalMoreView = (TextView)findViewById(R.id.hospital_add_tv);


        patientNameView = (TextView)findViewById(R.id.patient_name_tv);
        patientSexView = (TextView)findViewById(R.id.patient_sex_tv);
        patientAgeView = (TextView)findViewById(R.id.patient_age_tv);
        patientHeaderImageView = (RoundImageView)findViewById(R.id.patient_header_image);
        patientTelView = (TextView)findViewById(R.id.patient_tel_tv);
        patientBingLiHaoView = (TextView)findViewById(R.id.patient_binghao_tv);



        patientMoreView.setOnClickListener(clickListener);
        hospitalMoreView.setOnClickListener(clickListener);



    }
    @Override
    protected void onResume() {
        super.onResume();
        initData();

    }

    private void initData(){
        patientData.clear();
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("name","测试name");
        map.put("sex","男");
        map.put("age","30");
        map.put("tel","12345668765");
        map.put("binghao", "125675379");

        HashMap<String,String> map2 = new HashMap<String,String>();
        map2.put("name","测试2");
        map2.put("sex","女");
        map2.put("age","25");
        map2.put("tel","13845668888");
        map2.put("binghao","88888888");

        patientData.add(map2);
        patientData.add(map);


    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.patient_add_tv:
//                    Intent intent = new Intent(MCHospitalSelectActivity.this,MCHospitalPatientListActivity.class);
//                    startActivityForResult(intent,PATIENT_CODE);

                    Intent intent = new Intent(MCHospitalSelectActivity.this,MCHospitalPatientListActivity.class);
                    getParent().startActivityForResult(intent, PATIENT_CODE);

                    break;

                case R.id.hospital_add_tv:
                    Intent intent2 = new Intent(MCHospitalSelectActivity.this,MCHospitalListActivity.class);
                    Log.i(TAG,"hoapitalADD btn patientID  "+patientID);
                    intent2.putExtra("patientID",patientID);
                    startActivity(intent2);

                    break;


                default:


                    break;
            }
        }
    };


    /**
     * 为了得到传回的数据，必须在前面的Activity中（指MainActivity类）重写onActivityResult方法
     *
     * requestCode 请求码，即调用startActivityForResult()传递过去的值
     * resultCode 结果码，结果码用于标识返回数据来自哪个新Activity
     * TabHost中和TabActivity的子Activity使用startActivityForResult无法接收返回值
     * http://blog.csdn.net/ljz2009y/article/details/7942958
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "requestCode "+requestCode+" resultCode "+resultCode);
        if(requestCode == PATIENT_CODE){

            if(data != null){
                patientLayout.setVisibility(View.VISIBLE);
                patientInitLayout.setVisibility(View.GONE);
                Bundle bundle = data.getExtras();
                String name = bundle.getString("name");
                String sex = bundle.getString("sex");
                String age = bundle.getString("age");
                String tel = bundle.getString("tel");
                String binghao = bundle.getString("binghao");

                patientID = bundle.getString("patientID");
                myApp.setPatientId(patientID);

                patientNameView.setText(name);
                patientSexView.setText(sex);
                patientAgeView.setText(age);
                patientTelView.setText(tel);
                patientBingLiHaoView.setText(binghao);
            }




        }

    }

    @Override
    public void onTabActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onTabActivityResult requestCode "+requestCode+" resultCode "+resultCode);
        if (requestCode == PATIENT_CODE && resultCode == RESULT_OK) {
            //实现该处逻辑
            if(data != null){
                patientLayout.setVisibility(View.VISIBLE);
                patientInitLayout.setVisibility(View.GONE);
                Bundle bundle = data.getExtras();
                String name = bundle.getString("name");
                String sex = bundle.getString("sex");
                String age = bundle.getString("age");
                String tel = bundle.getString("tel");
                String binghao = bundle.getString("binghao");

                patientID = bundle.getString("patientID");
                myApp.setPatientId(patientID);
                Log.i(TAG, "onTabActivityResult patientID " + patientID);
                patientNameView.setText(name);
                patientSexView.setText(sex);
                patientAgeView.setText(age);
                patientTelView.setText(tel);
                patientBingLiHaoView.setText(binghao);
            }
        }
    }


}
