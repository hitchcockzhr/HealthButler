package com.housekeeper.ar.healthhousekeeper.membershipcard;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

/**
 * Created by lenovo on 2016/3/5.
 */
//办卡
public class MCHospitalApplyMCActivity extends BaseActivity {


    String TAG = "MCHospitalApplyMCActivity";



    String http, httpUrl;

    private ToastCommom toastCommom;
    private Button backBtn;
    private LinearLayout rulesLayout;
    private TextView nameTextView;
    //充值额
    private EditText rechargeAmountEditText;
    private Button payButton;
    private Button laterPayButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_hospital_apply);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(MCHospitalApplyMCActivity.this);

        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rulesLayout = (LinearLayout)findViewById(R.id.rules_layout);
        nameTextView = (TextView)findViewById(R.id.name_tv);
        rechargeAmountEditText = (EditText)findViewById(R.id.money_tv);
        payButton = (Button)findViewById(R.id.pay_btn);
        laterPayButton = (Button)findViewById(R.id.later_pay_btn);


        String rules = getIntent().getStringExtra("rules");
        nameTextView.setText(getIntent().getStringExtra("name"));
        String[] rulesArray = rules.split(";");
        rulesLayout.removeAllViews();
        for(int i = 0;i<rulesArray.length;i++){
            TextView textView = new TextView(MCHospitalApplyMCActivity.this);
            int num = i+1;
            textView.setText(num+"."+rulesArray[i]);
            rulesLayout.addView(textView);
            rulesLayout.setBottom(20);
            rulesLayout.setTop(20);
        }


        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rechargeAmountEditText.getText().toString().equals("")){
                    toastCommom.ToastShow(MCHospitalApplyMCActivity.this,(ViewGroup)findViewById(R.id.toast_layout_root),"请输入充值额");
                    return;
                }
                //TODO 确认付款
            }
        });
        laterPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rechargeAmountEditText.getText().toString().equals("")){
                    toastCommom.ToastShow(MCHospitalApplyMCActivity.this,(ViewGroup)findViewById(R.id.toast_layout_root),"请输入充值额");
                    return;
                }
                //TODO 稍后付款
//                finish();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
//        init();
    }



}
