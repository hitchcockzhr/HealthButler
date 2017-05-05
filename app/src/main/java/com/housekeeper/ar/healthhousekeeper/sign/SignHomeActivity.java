package com.housekeeper.ar.healthhousekeeper.sign;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.RoundImageView;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

/**
 * Created by lenovo on 2016/3/5.
 */
//签约首页
public class SignHomeActivity extends Activity {


    String TAG = "SignHomeActivity";



    String http, httpUrl;

    private ToastCommom toastCommom;
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

    private int PATIENT_CODE=0,DOCTOR_CODE=1;

    private Button backBtn;

    private String patientID="";
    private TextView patientMoreView;
    private Button doctorSignView;
    private Button okBtn;
    private Button cancelBtn;
    MyApp myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_home);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(SignHomeActivity.this);

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

        doctorSignView = (Button)findViewById(R.id.doctor_qianyue_btn);

        okBtn = (Button)findViewById(R.id.ok_btn);
        cancelBtn = (Button)findViewById(R.id.cancel_btn);

        patientNameView = (TextView)findViewById(R.id.patient_name_tv);
        patientSexView = (TextView)findViewById(R.id.patient_sex_tv);
        patientAgeView = (TextView)findViewById(R.id.patient_age_tv);
        patientHeaderImageView = (RoundImageView)findViewById(R.id.patient_header_image);
        patientTelView = (TextView)findViewById(R.id.patient_tel_tv);
        patientBingLiHaoView = (TextView)findViewById(R.id.patient_binghao_tv);


        doctorNameView = (TextView)findViewById(R.id.doctor_name_tv);
        doctorHospitalView = (TextView)findViewById(R.id.doctor_hospital_tv);
        doctorAddressView = (TextView)findViewById(R.id.doctor_address_tv);
        doctorTelView = (TextView)findViewById(R.id.doctor_tel_tv);
        doctorHeaderImageView = (RoundImageView)findViewById(R.id.doctor_header_image);

        doctorSignView.setOnClickListener(clickListener);
        patientMoreView.setOnClickListener(clickListener);

    }
    @Override
    protected void onResume() {
        super.onResume();
//        init();
    }


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.patient_add_tv:
                    Intent intent = new Intent(SignHomeActivity.this,SignPatientTabActivity.class);
                    startActivityForResult(intent, PATIENT_CODE);
                    break;

                case R.id.doctor_qianyue_btn:
                    if(patientID.equals("")){
                        toastCommom.ToastShow(SignHomeActivity.this,(ViewGroup)findViewById(R.id.toast_layout_root),"请选择患者");
                        return;
                    }
                    myApp.setPatientId(patientID);
                    Intent intent2 = new Intent(SignHomeActivity.this,SignDoctorListActivity.class);
                    startActivity(intent2);

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
        Log.i(TAG, "requestCode " + requestCode + " resultCode " + resultCode);
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
