package com.housekeeper.ar.healthhousekeeper.membershipcard;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.MyDialog;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

/**
 * Created by lenovo on 2016/3/5.
 */
//充值
public class MCHospitalRechargeActivity extends BaseActivity {


    String TAG = "MCHospitalRechargeActivity";



    String http, httpUrl;

    private ToastCommom toastCommom;
    private Button backBtn;

    private TextView mcTypeTextView;
    private TextView hospitalTextView;
    private TextView patientNameTextView;
    private TextView rechargeAmountTextView;

    //充值额

    private Button payButton;
    private Button cancelPayButton;

    private MyApp myApp;

    private RadioButton cashRadio,weixinRadio,zhifubaoRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_hospital_recharge);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(MCHospitalRechargeActivity.this);

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

        cashRadio = (RadioButton)findViewById(R.id.cash_pay_radio);
        weixinRadio = (RadioButton)findViewById(R.id.weixin_pay_radio);
        zhifubaoRadio = (RadioButton)findViewById(R.id.zhifubao_pay_radio);

        payButton = (Button)findViewById(R.id.pay_btn);
        cancelPayButton = (Button)findViewById(R.id.cancel_pay_btn);

        Drawable drawable = getResources().getDrawable(R.drawable.weixinzhifu_tv);
        drawable.setBounds(0, 0, 240, 140);//必须设置图片大小，否则不显示
        weixinRadio.setCompoundDrawables(drawable, null, null, null);

        Drawable drawable2 = getResources().getDrawable(R.drawable.xianjinzhifu_tv);
        drawable2.setBounds(0, 0, 240, 140);//必须设置图片大小，否则不显示
        cashRadio.setCompoundDrawables(drawable2, null, null, null);

        Drawable drawable3 = getResources().getDrawable(R.drawable.zhifubao_tv);
        drawable3.setBounds(0, 0, 240, 140);//必须设置图片大小，否则不显示
        zhifubaoRadio.setCompoundDrawables(drawable3, null, null, null);



        mcTypeTextView.setText(getIntent().getStringExtra("type"));
        String hospital = myApp.getYiyuanString();
        hospitalTextView.setText(hospital);
        String patientId = myApp.getPatientId();
        //        patientNameTextView.setText(patientId);
        patientNameTextView.setText("病人名字");

        rechargeAmountTextView.setText("1000");




        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cashRadio.isChecked() && !weixinRadio.isChecked()&& !zhifubaoRadio.isChecked()){
                    toastCommom.ToastShow(MCHospitalRechargeActivity.this,(ViewGroup) findViewById(R.id.toast_layout_root),"请选择付款方式");
                    return;
                }
                showDialog("ok");

            }
        });
        cancelPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 取消付款
//                finish();
                showDialog("cancel");
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
//        init();
    }


    //对话框
    private void showDialog(final String string){
        LayoutInflater inflater =getLayoutInflater();
        final View functionListView = inflater.inflate(R.layout.dialog_refund, null);
//		dialog = new AlertDialog.Builder(DiagnosisActivity.this, R.style.load_dialog).setView(functionListView).show();

        final MyDialog dialog = new MyDialog(MCHospitalRechargeActivity.this, functionListView,R.style.load_dialog);
        dialog.show();
        int width = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
//		int height = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView titleTV = (TextView)dialog.findViewById(R.id.textView1);
        TextView tvTV = (TextView)dialog.findViewById(R.id.content_tv);
        Button okBtn = (Button)dialog.findViewById(R.id.ok_btn);
        Button cancelBtn = (Button)dialog.findViewById(R.id.cancel_btn);

        if(string.equals("cancel")){
            titleTV.setText("取消充值");
            tvTV.setText("确定取消充值吗？");
        }else{
            titleTV.setText("确认充值");
            tvTV.setText("确定充值吗？");
        }


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(string.equals("cancel")){
                    //TODO 取消付款
                    finish();
                }else{
                    //TODO 确定付款
                    Intent intent = new Intent(MCHospitalRechargeActivity.this,MCHospitalRechargeSuccessActivity.class);
                    intent.putExtra("type",mcTypeTextView.getText().toString());
                    startActivity(intent);
                    //TODO 确认付款

                    finish();
                }

            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });



    }

}
