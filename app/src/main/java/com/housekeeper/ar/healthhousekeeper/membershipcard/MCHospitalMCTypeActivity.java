package com.housekeeper.ar.healthhousekeeper.membershipcard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lenovo on 2016/3/5.
 */
//会员卡类型
public class MCHospitalMCTypeActivity extends BaseActivity {


    String TAG = "MCHospitalMCTypeActivity";



    String http, httpUrl;

    private ToastCommom toastCommom;
    private List<HashMap<String,String>> data = new ArrayList<>();
    private ListView listview;
    private MCHospitalMCTypeAdapter mcHospitalMCTypeAdapter;
    private TextView hospitalTextView;
    private TextView moreTextView;
    private Button backBtn;
    private String hospital = "";
    private int ADD = 0;
    public static int EDIT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membershipcard_hospital_mctype);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(MCHospitalMCTypeActivity.this);



        listview = (ListView)findViewById(R.id.mctype_listview);
        mcHospitalMCTypeAdapter = new MCHospitalMCTypeAdapter(MCHospitalMCTypeActivity.this,data);
        listview.setAdapter(mcHospitalMCTypeAdapter);

        hospitalTextView = (TextView)findViewById(R.id.hospital_tv);
        moreTextView = (TextView)findViewById(R.id.add_mctype_tv);
        backBtn = (Button)findViewById(R.id.back_btn);

        Intent intent = getIntent();
        if(intent != null){
            hospital = intent.getStringExtra("hospital");
            hospitalTextView.setText(hospital);
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        moreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MCHospitalMCTypeActivity.this,MCHoapitalAddMCTypeActivity.class);
                intent.putExtra("hospital",hospital);
                startActivityForResult(intent,ADD);
            }
        });

        initData();

    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initData(){

        data.clear();
        //多个规则用分号划分
        HashMap<String,String> map = new HashMap<>();
        map.put("name","钻石会员");
        map.put("rules", "充满1000元，赠送500元;免费签约医生1个月;每周1次免费体检");

        data.add(map);
        HashMap<String,String> map2 = new HashMap<>();
        map2.put("name","白金会员");
        map2.put("rules", "充满1000元，赠送500元;免费签约医生1个月;每周1次免费体检");

        data.add(map2);

        HashMap<String,String> map3 = new HashMap<>();
        map3.put("name","黄金会员");
        map3.put("rules","充满1000元，赠送500元;免费签约医生1个月;每周1次免费体检");

        data.add(map3);

        HashMap<String,String> map4 = new HashMap<>();
        map4.put("name","白银会员");
        map4.put("rules","充满1000元，赠送500元;免费签约医生1个月;每周1次免费体检");

        data.add(map4);

    }

    /**
     * 为了得到传回的数据，必须在前面的Activity中（指MainActivity类）重写onActivityResult方法
     *
     * requestCode 请求码，即调用startActivityForResult()传递过去的值
     * resultCode 结果码，结果码用于标识返回数据来自哪个新Activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.i(TAG, "requestCode " + requestCode + " resultCode " + resultCode);
        if(requestCode == ADD){

            if(intent != null){

                String name = intent.getStringExtra("name");
                String rules = intent.getStringExtra("rules");
                String discount = intent.getStringExtra("discount");

                HashMap<String,String> map = new HashMap<>();
                map.put("name",name);
                map.put("rules",rules);
                map.put("discount", discount);
                data.add(map);

                mcHospitalMCTypeAdapter.notifyDataSetChanged();


            }




        }else if(requestCode == EDIT){

            if(intent != null) {

                String name = intent.getStringExtra("name");
                String rules = intent.getStringExtra("rules");
                String discount = intent.getStringExtra("discount");
                //编辑的是哪个position
                int position = mcHospitalMCTypeAdapter.getIndex();

                data.get(position).put("name", name);
                data.get(position).put("rules", rules);
                data.get(position).put("discount", discount);

                mcHospitalMCTypeAdapter.notifyDataSetChanged();
            }
        }

    }

}
