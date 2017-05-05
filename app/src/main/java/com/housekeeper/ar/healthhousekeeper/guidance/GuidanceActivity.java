package com.housekeeper.ar.healthhousekeeper.guidance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
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
//导诊预约
public class GuidanceActivity extends BaseActivity {


    String TAG = "GuidanceActivity";

    private List<HashMap<String,String>>  patientData = new ArrayList<HashMap<String, String>>();
    private HashMap<String,String> patientMap = new HashMap<>();
    private HashMap<String,String> doctorMap = new HashMap<>();

    private Button okBtn;
    private Button cancelBtn;

    private TextView patientMoreView;
    private Button quyaoBtn;
    private Button seeDoctorBtn;

    private RelativeLayout patientInitLayout;
    private RelativeLayout patientLayout;
    private RelativeLayout doctorInitLayout;
    private RelativeLayout doctorLayout;

    private TextView patientNameView;
    private TextView patientSexView,patientAgeView,patientBingLiHaoView,patientTelView;
//    private ImageView patientTelImageView,patientWeiXinImageView,patientMessageImageView;
    private RoundImageView patientHeaderImageView;

    private TextView doctorNameView,doctorHospitalView,doctorTelView,doctorAddressView;
 //   private ImageView doctorTelImageView,doctorWeiXinImageView,doctorMessageImageView;
    private RoundImageView doctorHeaderImageView;

    private ToastCommom toastCommom;


    private int PATIENT_CODE=0,DOCTOR_CODE=1;

    private Button backBtn;

    private String patientID="";
    MyApp myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance_new);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(GuidanceActivity.this);

        myApp = (MyApp)getApplication();

        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        patientInitLayout = (RelativeLayout)findViewById(R.id.init_patient_layout);
        patientLayout = (RelativeLayout)findViewById(R.id.patient_layout);
        doctorInitLayout = (RelativeLayout)findViewById(R.id.doctor_init_layout);
        doctorLayout = (RelativeLayout)findViewById(R.id.doctor_layout);

        patientMoreView = (TextView)findViewById(R.id.patient_add_tv);

        quyaoBtn = (Button)findViewById(R.id.doctor_quyao_btn);
        seeDoctorBtn = (Button)findViewById(R.id.kanyisheng_btn);

        okBtn = (Button)findViewById(R.id.ok_btn);
        cancelBtn = (Button)findViewById(R.id.cancel_btn);

        patientNameView = (TextView)findViewById(R.id.patient_name_tv);
        patientSexView = (TextView)findViewById(R.id.patient_sex_tv);
        patientAgeView = (TextView)findViewById(R.id.patient_age_tv);
        patientHeaderImageView = (RoundImageView)findViewById(R.id.patient_header_image);
        patientTelView = (TextView)findViewById(R.id.patient_tel_tv);
        patientBingLiHaoView = (TextView)findViewById(R.id.patient_binghao_tv);

//        patientTelImageView = (ImageView)findViewById(R.id.patient_tel_image);
//        patientMessageImageView = (ImageView)findViewById(R.id.patient_message_image);
//        patientWeiXinImageView = (ImageView)findViewById(R.id.patient_weixin_image);


        doctorNameView = (TextView)findViewById(R.id.doctor_name_tv);
        doctorHospitalView = (TextView)findViewById(R.id.doctor_hospital_tv);
        doctorAddressView = (TextView)findViewById(R.id.doctor_address_tv);
        doctorTelView = (TextView)findViewById(R.id.doctor_tel_tv);
        doctorHeaderImageView = (RoundImageView)findViewById(R.id.doctor_header_image);


//        doctorTelImageView = (ImageView)findViewById(R.id.doctor_tel_image);
//        doctorMessageImageView = (ImageView)findViewById(R.id.doctor_message_image);
//        doctorWeiXinImageView = (ImageView)findViewById(R.id.doctor_weixin_image);


        patientMoreView.setOnClickListener(clickListener);

        quyaoBtn.setOnClickListener(clickListener);

        seeDoctorBtn.setOnClickListener(clickListener);


        okBtn.setOnClickListener(clickListener);
        cancelBtn.setOnClickListener(clickListener);


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
                    Intent intent = new Intent(GuidanceActivity.this,PatientListActivity.class);
                    startActivityForResult(intent,PATIENT_CODE);

                    break;

                case R.id.doctor_quyao_btn:
                    Intent intent2 = new Intent(GuidanceActivity.this,CurrentPrescriptionActivity.class);
                    intent2.putExtra("patientID",patientID);
                    startActivityForResult(intent2,DOCTOR_CODE);

                    break;


                case R.id.kanyisheng_btn:

                    Intent intent4 = new Intent(GuidanceActivity.this,SeeDoctorNewActivity.class);
                    intent4.putExtra("patientID",patientID);
                    startActivity(intent4);

                    break;

                case R.id.ok_btn:


                    break;

                case R.id.cancel_btn:



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
                Log.i(TAG, "patientID:"+patientID);
                myApp.setPatientId(patientID);

                patientNameView.setText(name);
                patientSexView.setText(sex);
                patientAgeView.setText(age);
                patientTelView.setText(tel);
                patientBingLiHaoView.setText(binghao);
            }


        }else if(requestCode == DOCTOR_CODE){

            if(data != null){
                doctorInitLayout.setVisibility(View.GONE);
                doctorLayout.setVisibility(View.VISIBLE);

                Bundle bundle = data.getExtras();
                String name = bundle.getString("name");
                String hospital = bundle.getString("hospital");
                String address = bundle.getString("address");
                String tel = bundle.getString("tel");

                doctorNameView.setText(name);
                doctorAddressView.setText(address);
                doctorHospitalView.setText(hospital);
                doctorTelView.setText(tel);
            }


        }

    }

}
