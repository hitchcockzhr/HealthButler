package com.housekeeper.ar.healthhousekeeper.followup;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

/**
 * Created by Lenovo on 2017/2/8.
 * 身高体重检查解读
 */
public class FUTableAuxHeightWeightUnderstandingActivity extends BaseActivity {
    private ToastCommom toastCommom;
    private String TAG = "FUTableAuxHeightWeightUnderstandingActivity";
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_table_aux_height_weight_understanding);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(FUTableAuxHeightWeightUnderstandingActivity.this);

        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
