package com.housekeeper.ar.healthhousekeeper.membershipcard;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

/**
 * Created by lenovo on 2016/3/5.
 */
//充值成功
public class MCHospitalRechargeSuccessActivity extends BaseActivity {


    String TAG = "MCHospitalRechargeSuccessActivity";



    String http, httpUrl;

    private ToastCommom toastCommom;

    private Button backBtn;

    private TextView mcTypeTextView;
    private TextView hospitalTextView;
    private TextView patientNameTextView;
    private TextView rechargeAmountTextView;

    private TextView caozuorenTextView;
    private TextView dingdanIdTextView;

    private MyApp myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_hospital_recharge_success);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(MCHospitalRechargeSuccessActivity.this);

        myApp = (MyApp)getApplication();

        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mcTypeTextView = (TextView)findViewById(R.id.mc_type_tv);
        patientNameTextView = (TextView)findViewById(R.id.zhanghu_tv);
        rechargeAmountTextView = (TextView)findViewById(R.id.recharge_amount_tv);
        hospitalTextView = (TextView)findViewById(R.id.hospital_tv);
        caozuorenTextView = (TextView)findViewById(R.id.caozuoren_tv);
        dingdanIdTextView = (TextView)findViewById(R.id.dingdan_tv);




        mcTypeTextView.setText(getIntent().getStringExtra("type"));
        String hospital = myApp.getYiyuanString();
        hospitalTextView.setText(hospital);
        String patientId = myApp.getPatientId();
//        patientNameTextView.setText(patientId);
        patientNameTextView.setText("病人名字");

        rechargeAmountTextView.setText("1500");
    }
    @Override
    protected void onResume() {
        super.onResume();
//        init();
    }



}
