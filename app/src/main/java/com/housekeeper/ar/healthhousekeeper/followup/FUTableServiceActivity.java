package com.housekeeper.ar.healthhousekeeper.followup;

import android.content.Intent;
import android.os.Bundle;
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

/**已购服务
 * Created by Lenovo on 2017/3/29.
 */
public class FUTableServiceActivity extends BaseActivity {
    String TAG="FUTableServiceActivity";
    private ToastCommom toastCommom;
    private Button backBtn;

    private FUTableServiceListAdapter serviceListAdapter;
    private ListView serviceListView;
    private TextView newTV;

    private List<HashMap<String,String>> serviceData= new ArrayList<>();

    private Button okBtn,cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_table_visit_service);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(FUTableServiceActivity.this);

        backBtn = (Button) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        okBtn = (Button)findViewById(R.id.ok_btn);
        cancelBtn = (Button)findViewById(R.id.cancel_btn);

        serviceListAdapter = new FUTableServiceListAdapter(FUTableServiceActivity.this,serviceData);
        serviceListView = (ListView)findViewById(R.id.service_listview);
        serviceListView.setAdapter(serviceListAdapter);

        newTV = (TextView)findViewById(R.id.new_tv);
        newTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FUTableServiceActivity.this, FUTableAddServiceActivity.class);
                startActivity(intent1);
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData(){
        //TODO  现实实现中，该数据从数据库中获取，所以点击加号添加了新服务后不用通过页面将新数据添加到serviceData
        serviceData.clear();

        HashMap<String,String> map = new HashMap<>();
        map.put("name","打针");
        map.put("detail","肌肉注射，皮下注射");
        map.put("total_count", "3");
        map.put("count","3");

        HashMap<String,String> map2 = new HashMap<>();
        map2.put("name","输液");
        map2.put("detail","静脉点滴");
        map2.put("total_count","5");
        map2.put("count","5");

        serviceData.add(map);
        serviceData.add(map2);
    }
}
