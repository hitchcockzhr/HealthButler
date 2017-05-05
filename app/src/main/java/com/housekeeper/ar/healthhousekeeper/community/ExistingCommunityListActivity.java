package com.housekeeper.ar.healthhousekeeper.community;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

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
//现有小区
public class ExistingCommunityListActivity extends BaseActivity {


    String TAG = "ExistingCommunityListActivity";

    String http, httpUrl;
    private ToastCommom toastCommom;
    private List<HashMap<String,String>> data = new ArrayList<>();
    private ListView listView;
    private ExistingCommunityAdapter existingCommunityAdapter;
    private Button okBtn;
    private Button backBtn;
    private int ADD = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exist_community);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(ExistingCommunityListActivity.this);


        listView = (ListView)findViewById(R.id.community_listview);
        existingCommunityAdapter = new ExistingCommunityAdapter(ExistingCommunityListActivity.this,data,0);
        listView.setAdapter(existingCommunityAdapter);

//        backBtn = (Button)findViewById(R.id.back_btn);
        okBtn = (Button)findViewById(R.id.ok_btn);
        backBtn = (Button)findViewById(R.id.back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<HashMap<String,String>> list = existingCommunityAdapter.getSelectedData();
                if (list.isEmpty()){
                    toastCommom.ToastShow(ExistingCommunityListActivity.this,(ViewGroup)findViewById(R.id.toast_layout_root),"请选择一个社区");
                    return;
                }else{
                    HashMap<String,String> map = list.get(0);
                    Intent intent = getIntent();
                    Bundle bundle=new Bundle();

                    bundle.putParcelableArrayList("selectedlist", (ArrayList)list);
                    intent.putExtras(bundle);
                    //设置返回数据
                    setResult(RESULT_OK, intent);

                    finish();


                }
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }


    private void initData(){
        data.clear();
        HashMap<String,String> map = new HashMap<>();
        map.put("name","社区1");
        map.put("address","XXX省XXX市");

        data.add(map);

        HashMap<String,String> map2 = new HashMap<>();
        map2.put("name","社区2");
        map2.put("address","山东省烟台市");

        data.add(map2);

    }


}
