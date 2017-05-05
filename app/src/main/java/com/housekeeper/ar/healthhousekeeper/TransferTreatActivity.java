package com.housekeeper.ar.healthhousekeeper;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by lenovo on 2016/3/5.
 */
//转诊
public class TransferTreatActivity extends Activity {


    String TAG = "TransferTreatActivity";



    String http, httpUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance_home);



    }
    @Override
    protected void onResume() {
        super.onResume();
//        init();
    }



}
