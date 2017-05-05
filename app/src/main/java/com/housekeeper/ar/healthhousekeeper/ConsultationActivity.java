package com.housekeeper.ar.healthhousekeeper;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by lenovo on 2016/3/5.
 */
//会诊
public class ConsultationActivity extends Activity {


    String TAG = "ConsultationActivity";



    String http, httpUrl;
    private ToastCommom toastCommom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance_home);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(ConsultationActivity.this);




    }
    @Override
    protected void onResume() {
        super.onResume();
//        init();
    }



}
