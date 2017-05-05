package com.housekeeper.ar.healthhousekeeper.membershipcard;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2016/3/5.
 */
//病人列表Tab
public class MCPatientTabListActivity extends Activity {


    String TAG = "PatientListActivity";



    String http, httpUrl;
    private ToastCommom toastCommom;

    private ListView patientListView;
    private MCPatientListAdapter patientListAdapter;
    private List<HashMap<String,String>> data = new ArrayList<>();

    private List<HashMap<String,String>> searchData = new ArrayList<HashMap<String, String>>();

    private Button backBtn;
    private EditText searchEditText;
    private ImageView searchImageView;
    private MyApp myApp;
    private LinearLayout titleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(MCPatientTabListActivity.this);
        myApp = (MyApp)getApplication();

        patientListView = (ListView)findViewById(R.id.patient_listview);
        patientListAdapter = new MCPatientListAdapter(MCPatientTabListActivity.this,myApp,data,"patientTab");
        patientListView.setAdapter(patientListAdapter);

        titleLayout = (LinearLayout)findViewById(R.id.title_layout);
        titleLayout.setVisibility(View.GONE);

        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchEditText = (EditText)findViewById(R.id.search_edittext);
        searchImageView = (ImageView)findViewById(R.id.deatail_image_view);


        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData.clear();
                if(searchEditText.getText().toString().equals("")){
//                     Toast.makeText(MCPatientTabListActivity.this,"搜索内容不能为空",Toast.LENGTH_LONG).show();
                    initData();
                    searchData.addAll(data);
                    patientListAdapter.notifyDataSetChanged();
                    return;
                }
                String searchString = searchEditText.getText().toString();
                Pattern p = Pattern.compile("[0-9]*");
                Matcher m = p.matcher(searchString);
                if(m.matches() ){

                    //如果是手机号,包含数字？
//                    for(HashMap<String,String> map:data){
//                        if(map.get("tel").contains(searchString)){
//                            searchData.add(map);
//                        }
//                    }

                    //怎么判断输入是否是手机号？
                    searchPatientByTel(searchString);
                } else{
                    //如果是姓名
//                    for(HashMap<String,String> map:data){
//                        if(map.get("name").contains(searchString)){
//                            searchData.add(map);
//                        }
//                    }
                    searchPatientByName(searchString);
                }

                data.clear();
                data.addAll(searchData);
                Log.i(TAG, "searchData " + searchData);
                patientListAdapter.notifyDataSetChanged();
                if(data.isEmpty()){
                    Toast.makeText(MCPatientTabListActivity.this, "无搜索结果", Toast.LENGTH_LONG).show();
                }
            }

        });



    }
    @Override
    protected void onResume() {
        super.onResume();
        //进入Activity时需要获取全部病人列表吗？
//        initData();
//        patientListAdapter.notifyDataSetChanged();
    }

    //根据手机号查询病人
    private void searchPatientByTel(String tel){
        searchData.clear();
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("name","测试name");
        map.put("sex","男");
        map.put("age","30");
        map.put("tel","12345668765");
        map.put("binghao", "125675379");
        map.put("patientID","1");
        searchData.add(map);
    }
    //根据名称查询病人
    private void searchPatientByName(String name){
        searchData.clear();
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("name","测试name");
        map.put("sex","男");
        map.put("age","30");
        map.put("tel","12345668765");
        map.put("binghao", "125675379");
        map.put("patientID","1");
        searchData.add(map);
    }

    private void initData(){
        data.clear();
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("name","测试name");
        map.put("sex","男");
        map.put("age","30");
        map.put("tel","12345668765");
        map.put("binghao", "125675379");
        map.put("patientID","1");

        HashMap<String,String> map2 = new HashMap<String,String>();
        map2.put("name","测试2");
        map2.put("sex","女");
        map2.put("age","25");
        map2.put("tel","13845668888");
        map2.put("binghao","88888888");
        map2.put("patientID","2");

        data.add(map2);
        data.add(map);


    }

}
