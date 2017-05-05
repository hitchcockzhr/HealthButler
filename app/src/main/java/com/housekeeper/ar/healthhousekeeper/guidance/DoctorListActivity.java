package com.housekeeper.ar.healthhousekeeper.guidance;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2016/3/5.
 */
//医生列表
public class DoctorListActivity extends BaseActivity {


    String TAG = "DoctorListActivity";



    String http, httpUrl;
    private ToastCommom toastCommom;

    private TextView titleView;
    private ListView doctorListView;
    private DoctorListAdapter doctorListAdapter;
    private List<HashMap<String,String>> data = new ArrayList<>();

    private List<HashMap<String,String>> searchData = new ArrayList<HashMap<String, String>>();

    private Button backBtn;
    private EditText searchEditText;
    private ImageView searchImageView;
    private MyApp myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(DoctorListActivity.this);
        myApp = (MyApp)getApplication();

        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        doctorListView = (ListView)findViewById(R.id.patient_listview);
        doctorListAdapter = new DoctorListAdapter(DoctorListActivity.this,myApp,data);
        doctorListView.setAdapter(doctorListAdapter);

        titleView = (TextView)findViewById(R.id.title);
        titleView.setText("医生列表");

        searchEditText = (EditText)findViewById(R.id.search_edittext);
        searchImageView = (ImageView)findViewById(R.id.deatail_image_view);


        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData.clear();
                if(searchEditText.getText().toString().equals("")){
                    // Toast.makeText(FollowUpClinicActivity.this,"搜索内容不能为空",Toast.LENGTH_LONG).show();
                    initData();
                    searchData.addAll(data);
                    doctorListAdapter.notifyDataSetChanged();
                    return;
                }
                String searchString = searchEditText.getText().toString();
                Pattern p = Pattern.compile("[0-9]*");
                Matcher m = p.matcher(searchString);
                if(m.matches() ){
//                    Toast.makeText(Main.this,"输入的是数字", Toast.LENGTH_SHORT).show();
                    //如果是手机号,包含数字？
                    for(HashMap<String,String> map:data){
                        if(map.get("tel").contains(searchString)){
                            searchData.add(map);
                        }
                    }
                } else{
                    //如果是姓名
                    for(HashMap<String,String> map:data){
                        if(map.get("name").contains(searchString)){
                            searchData.add(map);
                        }
                    }
                }

                data.clear();
                data.addAll(searchData);
                Log.i(TAG, "searchData " + searchData);
                doctorListAdapter.notifyDataSetChanged();
                if(data.isEmpty()){
                    Toast.makeText(DoctorListActivity.this, "无搜索结果", Toast.LENGTH_LONG).show();
                }
            }

        });


    }
    @Override
    protected void onResume() {
        super.onResume();
        initData();
        doctorListAdapter.notifyDataSetChanged();
    }

    private void initData(){
        data.clear();
        HashMap<String,String> map = new HashMap<String,String>();
        JSONObject doctorJson = new JSONObject();
        try {
            doctorJson.put("departmentId",2);
            doctorJson.put("description","认真复制");
            doctorJson.put("jobTitleId",2);
            doctorJson.put("jobTitleNo","9527");

            doctorJson.put("name","83版小熊");
            doctorJson.put("phone","18911568719");
            doctorJson.put("sex","男");
            doctorJson.put("skill","擅长手术");
            doctorJson.put("userId","kevin");
            doctorJson.put("zhicheng","医师");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        map.put("name","83版小熊");
        map.put("address","山东省");
        map.put("hospital","测试医院");
        map.put("tel", "13845668888");
        map.put("zhicheng","主治医师");

        map.put("id","kevin");
        map.put("city","测试城市");
        map.put("keshi","科室测试");
        map.put("doctorJson",doctorJson.toString());



//        HashMap<String,String> map2 = new HashMap<String,String>();
//        map2.put("name","测试2");
//        map2.put("address", "天津市和平区XX街道");
//        map2.put("hospital", "测试医院");
//        map2.put("tel", "13845668888");
//
//        data.add(map2);
        data.add(map);


    }


}
