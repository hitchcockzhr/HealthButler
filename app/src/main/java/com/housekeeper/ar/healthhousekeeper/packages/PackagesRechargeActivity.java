package com.housekeeper.ar.healthhousekeeper.packages;

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
import com.housekeeper.ar.healthhousekeeper.MyDialog;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;
import com.housekeeper.ar.healthhousekeeper.membershipcard.MCHospitalRechargeSuccessActivity;

/**
 * Created by Lenovo on 2017/3/1.
 * 套餐服务付款
 */
public class PackagesRechargeActivity extends BaseActivity {
    private ToastCommom toastCommom;
    private String TAG = "PackagesRechargeActivity";
    private Button backBtn;

    private TextView serviceNameTextView;
    private TextView patientNameTextView;
    private TextView rechargeAmountTextView;

    //充值额

    private Button payButton;
    private Button cancelPayButton;

    private RadioButton weixinRadio,cashRadio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages_service_recharge);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(PackagesRechargeActivity.this);

        backBtn = (Button) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        serviceNameTextView = (TextView)findViewById(R.id.service_type_tv);
        rechargeAmountTextView = (TextView)findViewById(R.id.recharge_amount_tv);
        weixinRadio = (RadioButton)findViewById(R.id.weixin_pay_radio);
        cashRadio = (RadioButton)findViewById(R.id.cash_pay_radio);

        Drawable drawable = getResources().getDrawable(R.drawable.weixinzhifu_tv);
        drawable.setBounds(0, 0, 250, 150);//必须设置图片大小，否则不显示
        weixinRadio.setCompoundDrawables(drawable, null, null, null);

        Drawable drawable2 = getResources().getDrawable(R.drawable.xianjinzhifu_tv);
        drawable2.setBounds(0, 0, 250, 150);//必须设置图片大小，否则不显示
        cashRadio.setCompoundDrawables(drawable2, null, null, null);

        payButton = (Button)findViewById(R.id.pay_btn);
        cancelPayButton = (Button)findViewById(R.id.cancel_pay_btn);



        serviceNameTextView.setText(getIntent().getStringExtra("service_name"));
        rechargeAmountTextView.setText(getIntent().getStringExtra("service_price"));

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cashRadio.isChecked() && !weixinRadio.isChecked()){
                    toastCommom.ToastShow(PackagesRechargeActivity.this,(ViewGroup) findViewById(R.id.toast_layout_root),"请选择付款方式");
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
    //对话框
    private void showDialog(final String string){
        LayoutInflater inflater =getLayoutInflater();
        final View functionListView = inflater.inflate(R.layout.dialog_refund, null);
//		dialog = new AlertDialog.Builder(DiagnosisActivity.this, R.style.load_dialog).setView(functionListView).show();

        final MyDialog dialog = new MyDialog(PackagesRechargeActivity.this, functionListView,R.style.load_dialog);
        dialog.show();
        int width = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
//		int height = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView titleTV = (TextView)dialog.findViewById(R.id.textView1);
        TextView tvTV = (TextView)dialog.findViewById(R.id.content_tv);
        Button okBtn = (Button)dialog.findViewById(R.id.ok_btn);
        Button cancelBtn = (Button)dialog.findViewById(R.id.cancel_btn);

        if(string.equals("cancel")){
            titleTV.setText("取消购买");
            tvTV.setText("确定取消购买吗？");
        }else{
            titleTV.setText("确认购买");
            tvTV.setText("确定购买吗？");
        }


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(string.equals("cancel")){
                    //TODO 取消付款
                    finish();
                }else{
                    //TODO 确定付款
                    Intent intent = new Intent(PackagesRechargeActivity.this,MCHospitalRechargeSuccessActivity.class);
                    intent.putExtra("type",serviceNameTextView.getText().toString());
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
