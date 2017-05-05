package com.housekeeper.ar.healthhousekeeper.guidance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lenovo on 2016/3/5.
 */
//根据药品获取患者周围的医院
public class HospitalListActivity extends Activity {


    String TAG = "HospitalListActivity";



    String http, httpUrl;

    private List<HashMap<String,String>> hospitalLists = new ArrayList<>();
    //药品列表，根据药品获取患者周围的医院
    private List<String> drugLists = new ArrayList<>();

    private ToastCommom toastCommom;
    private String patientId;
//    private String drugsString="";

    private HospitalListAdapter hospitalListAdapter;
    private ListView hospitalListView;
    private Button backBtn;
    private MyApp myApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(HospitalListActivity.this);

        myApp = (MyApp)getApplication();
        Intent intent = getIntent();
        drugLists.clear();
//        drugsString="";
        if(intent != null){
            Bundle bundle=intent.getExtras();

            ArrayList list2 = bundle.getParcelableArrayList("drugs");


            if(list2 != null && !list2.isEmpty()) {
//                 drugLists.addAll(list2);
                for(int i=0;i<list2.size();i++) {
                    HashMap<String, String> map = (HashMap<String, String>) list2.get(i);
                    drugLists.add(map.get("name"));

                }
            }
        }

        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        hospitalListView = (ListView)findViewById(R.id.hospital_listview);
        hospitalListAdapter = new HospitalListAdapter(HospitalListActivity.this,myApp,hospitalLists,drugLists);
        hospitalListView.setAdapter(hospitalListAdapter);


        }
    @Override
    protected void onResume() {
        super.onResume();
        initData();
        sortDataByLength();
        hospitalListAdapter.notifyDataSetChanged();
    }


    //初始化数据，根据药方列表获取患者周围的医院
    private void initData(){

        hospitalLists.clear();
        HashMap<String,String> map = new HashMap<>();
        map.put("hospital", "测试医院");
        map.put("drugs", "测试");//医院里面含有搜索目的药品的药品
        map.put("hospitalId","1");
        map.put("address", "天津市XXXX");

        hospitalLists.add(map);

        HashMap<String,String> map2 = new HashMap<>();
        map2.put("hospital","测试医院2");
        map2.put("drugs", "测试,头孢");
        map2.put("hospitalId","2");
        map2.put("address","天津市XXXX");

        hospitalLists.add(map2);
    }


    Comparator<HashMap<String, String>> mapComprator = new Comparator<HashMap<String, String>>(){


        @Override
        public int compare(HashMap<String, String> map1,
                           HashMap<String, String> map2) {
            // TODO Auto-generated method stub
            String start1 = map1.get("drugs");
            String start2 = map2.get("drugs");
            long long1 = start1.length();
            long long2 = start2.length();
            int result = (int)(long2-long1);
            Log.i(TAG, "compare:" + long1 + " " + long2 + " " + result);
            return result;
        }
    };

    public void sortDataByLength(){
        Collections.sort(hospitalLists, mapComprator);
        Log.i(TAG, hospitalLists.toString());
    }

}
