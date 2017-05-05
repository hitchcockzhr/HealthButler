package com.housekeeper.ar.healthhousekeeper.followup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;
import com.housekeeper.ar.healthhousekeeper.packages.PackagesActivity;

/**
 * Created by Lenovo on 2017/4/6.
 */
public class FUTableAddServiceActivity extends BaseActivity {
    String TAG="FUTableAddServiceActivity";
    private ToastCommom toastCommom;
    private Button backBtn;

    private TextView packagesServiceTV,singleServiceTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_table_add_service);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(FUTableAddServiceActivity.this);

        backBtn = (Button) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        packagesServiceTV = (TextView)findViewById(R.id.add_package__service_tv);
        singleServiceTV = (TextView)findViewById(R.id.add_single__service_tv);

        packagesServiceTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FUTableAddServiceActivity.this, PackagesActivity.class);
                startActivity(intent1);
            }
        });
        singleServiceTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FUTableAddServiceActivity.this, FUTableSingleServiceActivity.class);
                startActivity(intent1);
            }
        });
    }
}
