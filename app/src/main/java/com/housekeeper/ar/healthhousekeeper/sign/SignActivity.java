package com.housekeeper.ar.healthhousekeeper.sign;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovo on 2016/3/5.
 */
//签约
public class SignActivity extends Activity {


    String TAG = "SignActivity";



    String http, httpUrl;

    private ToastCommom toastCommom;
    private TextView dingdanTextView,statusTextView;
    private TextView nameTextView,zhichengTextView,hospitalTextView,keshiTextView,patientNumTextView;
    LinearLayout statusLayout;
    private Button okBtn,cancelBtn,backBtn;


    private MyApp myApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(SignActivity.this);


        myApp = (MyApp)getApplication();
        dingdanTextView = (TextView)findViewById(R.id.dingdan_id);
        statusTextView = (TextView)findViewById(R.id.status_tv);
        nameTextView = (TextView)findViewById(R.id.doctor_name_tv);
        zhichengTextView = (TextView)findViewById(R.id.doctor_zhicheng_tv);
        hospitalTextView = (TextView)findViewById(R.id.doctor_hospital_tv);
        keshiTextView = (TextView)findViewById(R.id.doctor_keshi_tv);
        patientNumTextView = (TextView)findViewById(R.id.doctor_patient_num_tv);//可签约的人数
        statusLayout = (LinearLayout)findViewById(R.id.status_layout);
        okBtn = (Button)findViewById(R.id.ok_btn);
        cancelBtn = (Button)findViewById(R.id.cancel_btn);
        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 签约
                toastCommom.ToastShow(SignActivity.this,(ViewGroup) findViewById(R.id.toast_layout_root), "完成签约");
            }
        });


    }
    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData(){
        JSONObject jsonObject = new JSONObject();
        jsonObject = myApp.getJoDoctor();

        try {
            nameTextView.setText(String.valueOf(jsonObject.get("name")));
            zhichengTextView.setText(String.valueOf(jsonObject.get("zhicheng")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        hospitalTextView.setText(myApp.getYiyuanString());
        keshiTextView.setText(myApp.getKeshiString());
        //可签约人数
        patientNumTextView.setText("10");
        //签约订单号
        dingdanTextView.setText("1234455测试");

        //TODO　签约状态
        LayoutInflater inflater =getLayoutInflater();

        int status = 0;
        if(status == 0){
            //已签约
            final View view = inflater.inflate(R.layout.activity_sign_status_hassigned, null);
            statusLayout.removeAllViews();
            statusLayout.addView(view);
            statusTextView.setText("已签约");
        }else if(status == 1){

            final View view = inflater.inflate(R.layout.activity_sign_status_nopay, null);
            statusLayout.removeAllViews();
            statusLayout.addView(view);
            statusTextView.setText("未付款，签约失败");
        }else if(status == 2){
            final View view = inflater.inflate(R.layout.activity_sign_status_daituikuan, null);
            statusLayout.removeAllViews();
            statusLayout.addView(view);
            statusTextView.setText("待退款");
        }else if(status == 3){
            final View view = inflater.inflate(R.layout.activity_sign_status_yituikuan, null);
            statusLayout.removeAllViews();
            statusLayout.addView(view);
            statusTextView.setText("已退款");
        }
    }


}
