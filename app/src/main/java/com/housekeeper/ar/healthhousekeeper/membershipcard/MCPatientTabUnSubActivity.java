package com.housekeeper.ar.healthhousekeeper.membershipcard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
//退订
public class MCPatientTabUnSubActivity extends BaseActivity {


    String TAG = "MCPatientTabUnSubActivity";



    String http, httpUrl;

    private ToastCommom toastCommom;
    private Button backBtn;

    private TextView mcTypeTextView;
    private TextView hospitalTextView;
    //余额
    private TextView balanceTextView;
    //退款金额
    private TextView unsubAmountTextView;


    private Button okButton;
    private Button cancelButton;

    private MyApp myApp;

    private TextView patientNameTextView;
    private TextView patientSexTextView;
    private TextView patientAgeTextView;
    private TextView patientTelTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_patient_tab_unsub);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(MCPatientTabUnSubActivity.this);

        myApp = (MyApp)getApplication();

        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        patientAgeTextView = (TextView)findViewById(R.id.patient_age_tv);
        patientNameTextView = (TextView)findViewById(R.id.patient_name_tv);
        patientSexTextView = (TextView)findViewById(R.id.patient_sex_tv);
        patientTelTextView = (TextView)findViewById(R.id.patient_tel_tv);

        mcTypeTextView = (TextView)findViewById(R.id.mc_type_tv);

        balanceTextView = (TextView)findViewById(R.id.balance_money_tv);
        unsubAmountTextView = (TextView)findViewById(R.id.unsub_money_tv);
        hospitalTextView = (TextView)findViewById(R.id.hospital_tv);



        okButton = (Button)findViewById(R.id.ok_btn);
        cancelButton = (Button)findViewById(R.id.cancel_btn);






        String patientId = myApp.getPatientId();

        Bundle bundle = getIntent().getExtras();

        patientNameTextView.setText(bundle.getString("name"));
        patientAgeTextView.setText(bundle.getString("age"));
        patientSexTextView.setText(bundle.getString("sex"));
        patientTelTextView.setText(bundle.getString("tel"));
        mcTypeTextView.setText(bundle.getString("mctype"));
        hospitalTextView.setText(bundle.getString("hospital"));

        balanceTextView.setText("1500");
        unsubAmountTextView.setText("1000");




        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("ok");

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
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

        final MyDialog dialog = new MyDialog(MCPatientTabUnSubActivity.this, functionListView,R.style.load_dialog);
        dialog.show();
        int width = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
//		int height = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView titleTV = (TextView)dialog.findViewById(R.id.textView1);
        TextView tvTV = (TextView)dialog.findViewById(R.id.content_tv);
        Button okBtn = (Button)dialog.findViewById(R.id.ok_btn);
        Button cancelBtn = (Button)dialog.findViewById(R.id.cancel_btn);

        if(string.equals("cancel")){
            titleTV.setText("取消退订");
            tvTV.setText("确定取消退订吗？");
        }else{
            titleTV.setText("确认退订");
            tvTV.setText("确定退订吗？");
        }


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(string.equals("cancel")){
                    //TODO 取消退订

                    dialog.dismiss();
                    finish();


                }else{

                    Intent intent = new Intent(MCPatientTabUnSubActivity.this,MCPatientTabunSubSuccessActivity.class);
                    intent.putExtra("type", mcTypeTextView.getText().toString());
                    startActivity(intent);
                    //TODO 确认退订

                    dialog.dismiss();

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
