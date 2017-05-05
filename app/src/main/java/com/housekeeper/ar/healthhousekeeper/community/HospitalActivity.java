package com.housekeeper.ar.healthhousekeeper.community;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
//会员卡
public class HospitalActivity extends Activity {


    String TAG = "HospitalActivity";



    String http, httpUrl;

    private ToastCommom toastCommom;
    private List<HashMap<String,String>> data = new ArrayList<>();
    private ListView listView;
    private CommunityAdapter hospitalAdapter;
    private TextView selectHospitalTV;
    private LinearLayout selectHospitalLayout;
    private int ADD = 0;
    private Button okBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(HospitalActivity.this);


        listView = (ListView)findViewById(R.id.community_listview);
        hospitalAdapter = new CommunityAdapter(HospitalActivity.this,data,1);
        listView.setAdapter(hospitalAdapter);

        selectHospitalTV = (TextView)findViewById(R.id.choose_community_tv);
        selectHospitalTV.setText("医院地址维护");

        okBtn = (Button)findViewById(R.id.ok_btn);

        selectHospitalLayout = (LinearLayout)findViewById(R.id.choose_community_layout);
        selectHospitalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HospitalActivity.this, AddHospitalActivity.class);
//                startActivity(intent);
                startActivityForResult(intent, ADD);

            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddCommmunityTab addCommmunityTab = (AddCommmunityTab) getParent();

                Intent intent = new Intent();
                Bundle bundle=new Bundle();

                bundle.putParcelableArrayList("list", (ArrayList)data);
                bundle.putString("type","1");//0表示是社区
                intent.putExtras(bundle);
                //设置返回数据
                addCommmunityTab.setResult(RESULT_OK, intent);

                finish();
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
        HashMap<String,String> map = new HashMap<>();
        map.put("name","医院1");
        map.put("address", "XXX省XXX市");

        data.add(map);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//		String result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
        Log.i(TAG, "onActivityResult data " + data);
        Log.i(TAG, "onActivityResult requestCode " + requestCode);
        Log.i(TAG, "onActivityResult resultCode " + resultCode);

        if (resultCode == RESULT_OK) {
            String name = intent.getStringExtra("name");
            String address = intent.getStringExtra("address");
            HashMap<String,String> map = new HashMap<>();
            map.put("name",name);
            map.put("address",address);
            data.add(map);
            hospitalAdapter.notifyDataSetChanged();
        }
    }



}
