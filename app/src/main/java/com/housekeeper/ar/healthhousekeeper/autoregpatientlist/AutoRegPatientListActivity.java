package com.housekeeper.ar.healthhousekeeper.autoregpatientlist;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
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
//新注册用户列表，即自主注册的病人列表
public class AutoRegPatientListActivity extends BaseActivity {


    String TAG = "AutoRegPatientListActivity";



    String http, httpUrl;

    private ToastCommom toastCommom;

    private ListView aotoRegPatientListView;
    private AutoRegPatientListAdapter aotoRegPatientListAdapter;

    private List<HashMap<String,String>> data = new ArrayList<>();
    private List<HashMap<String,String>> searchData = new ArrayList<HashMap<String, String>>();

    private EditText searchEditText;
    private ImageView searchImageView;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aoto_reg_patient_list);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(AutoRegPatientListActivity.this);

        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        aotoRegPatientListView = (ListView)findViewById(R.id.aoto_reg_patient_listview);

        aotoRegPatientListAdapter = new AutoRegPatientListAdapter(AutoRegPatientListActivity.this,data);

        aotoRegPatientListView.setAdapter(aotoRegPatientListAdapter);


        searchEditText = (EditText)findViewById(R.id.search_edittext);
        searchImageView = (ImageView)findViewById(R.id.deatail_image_view);


        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData.clear();
                if (searchEditText.getText().toString().equals("")) {
                    // Toast.makeText(FollowUpClinicActivity.this,"搜索内容不能为空",Toast.LENGTH_LONG).show();
                    initData();
                    searchData.addAll(data);
                    aotoRegPatientListAdapter.notifyDataSetChanged();
                    return;
                }
                String searchString = searchEditText.getText().toString();
                Pattern p = Pattern.compile("[0-9]*");
                Matcher m = p.matcher(searchString);
                if (m.matches()) {
//                    Toast.makeText(Main.this,"输入的是数字", Toast.LENGTH_SHORT).show();
                    //如果是手机号,包含数字？
                    for (HashMap<String, String> map : data) {
                        if (map.get("tel").contains(searchString)) {
                            searchData.add(map);
                        }
                    }
                } else {
                    //如果是姓名
                    for (HashMap<String, String> map : data) {
                        if (map.get("name").contains(searchString)) {
                            searchData.add(map);
                        }
                    }
                }

                data.clear();
                data.addAll(searchData);
                Log.i(TAG, "searchData " + searchData);
                aotoRegPatientListAdapter.notifyDataSetChanged();
                if (data.isEmpty()) {
                    Toast.makeText(AutoRegPatientListActivity.this, "无搜索结果", Toast.LENGTH_LONG).show();
                }
            }

        });


    }
    @Override
    protected void onResume() {
        super.onResume();
        initData();
        aotoRegPatientListAdapter.notifyDataSetChanged();
    }

    //初始化小组成员数据
    private void initData(){

        data.clear();

        HashMap<String,String> map = new HashMap<>();
        map.put("name","黄娟娟");
        map.put("sex", "女");
        map.put("age", "20");
        map.put("tel", "13587564556");


        data.add(map);

        HashMap<String,String> map2 = new HashMap<>();
        map2.put("name","王楠");
        map2.put("sex","男");
        map2.put("age", "25");
        map2.put("tel", "13254857788");


        data.add(map2);


        HashMap<String,String> map3 = new HashMap<>();
        map3.put("name","陈晨");
        map3.put("sex","男");
        map3.put("age","30");
        map3.put("tel", "18699885523");


        data.add(map3);


        HashMap<String,String> map4 = new HashMap<>();
        map4.put("name","郝冰");
        map4.put("sex","女");
        map4.put("age","25");
        map4.put("tel","13866665234");


        data.add(map4);

    }


}
