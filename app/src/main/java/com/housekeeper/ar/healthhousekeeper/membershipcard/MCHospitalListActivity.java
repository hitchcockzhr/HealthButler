package com.housekeeper.ar.healthhousekeeper.membershipcard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
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
//办会员卡的医院
public class MCHospitalListActivity extends BaseActivity {


    String TAG = "MCHospitalListActivity";



    String http, httpUrl;

    private List<HashMap<String,String>> hospitalLists = new ArrayList<>();


    private ToastCommom toastCommom;


    private MCHospitalListAdapter mcHospitalListAdapter;
    private ListView hospitalListView;
    private Button backBtn;
    private MyApp myApp;
    private String patientID="";
    private EditText searchEditText;
    private ImageView searchImageView;
    private List<HashMap<String,String>> searchData = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_hospital_list);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(MCHospitalListActivity.this);

        myApp = (MyApp)getApplication();
        Intent intent = getIntent();

        if(intent != null){
            patientID = intent.getStringExtra("patientID");
        }
        Log.i(TAG,"patientID "+patientID);
        if(patientID == null || patientID.equals("")){
            //如果没有选择病人，则提示并返回
            toastCommom.ToastShow(MCHospitalListActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "请先选择患者");
            finish();
        }

        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        hospitalListView = (ListView)findViewById(R.id.hospital_listview);
        mcHospitalListAdapter = new MCHospitalListAdapter(MCHospitalListActivity.this,hospitalLists);
        hospitalListView.setAdapter(mcHospitalListAdapter);

        hospitalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MCHospitalListActivity.this, MCHospitalMCTypeActivity.class);
                intent.putExtra("hospital", hospitalLists.get(position).get("hospital"));
                myApp.setYiyuanString(hospitalLists.get(position).get("hospital"));
                startActivity(intent);
            }
        });

        searchEditText = (EditText)findViewById(R.id.search_edittext);
        searchImageView = (ImageView)findViewById(R.id.deatail_image_view);
        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData.clear();
                if(searchEditText.getText().toString().equals("")){
                    // Toast.makeText(FollowUpClinicActivity.this,"搜索内容不能为空",Toast.LENGTH_LONG).show();
                    initData();
                    searchData.addAll(hospitalLists);
                    mcHospitalListAdapter.notifyDataSetChanged();
                    return;
                }
                String searchString = searchEditText.getText().toString();

                for(HashMap<String,String> map:hospitalLists){
                    if(map.get("hospital").contains(searchString)){
                        searchData.add(map);
                    }
                }


                hospitalLists.clear();
                hospitalLists.addAll(searchData);
                Log.i(TAG, "searchData " + searchData);
                mcHospitalListAdapter.notifyDataSetChanged();
                if(hospitalLists.isEmpty()){
                    toastCommom.ToastShow(MCHospitalListActivity.this,(ViewGroup)findViewById(R.id.toast_layout_root),"无搜索结果");
                }
            }

        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        initData();
//        sortDataByLength();
        mcHospitalListAdapter.notifyDataSetChanged();
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
