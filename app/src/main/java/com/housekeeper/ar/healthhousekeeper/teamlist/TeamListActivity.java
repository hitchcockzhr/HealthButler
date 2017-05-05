package com.housekeeper.ar.healthhousekeeper.teamlist;

import android.os.Bundle;
import android.view.View;
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
//小组成员列表
public class TeamListActivity extends BaseActivity {


    String TAG = "TeamListActivity";



    String http, httpUrl;

    private ToastCommom toastCommom;

    private ListView teamListView;
    private TeamListAdapter teamListAdapter;

    private List<HashMap<String,String>> data = new ArrayList<>();
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(TeamListActivity.this);

        teamListView = (ListView)findViewById(R.id.teamer_listview);

        teamListAdapter = new TeamListAdapter(TeamListActivity.this,data);

        teamListView.setAdapter(teamListAdapter);

        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    //初始化小组成员数据
    private void initData(){

        data.clear();

        HashMap<String,String> map = new HashMap<>();
        map.put("name","张龙");
        map.put("sex","男");
        map.put("age","30");
        map.put("tel","13587564556");
        map.put("type","组长");

        data.add(map);

        HashMap<String,String> map2 = new HashMap<>();
        map2.put("name","吴玲玲");
        map2.put("sex","女");
        map2.put("age","25");
        map2.put("tel","13254857788");
        map2.put("type","组员");

        data.add(map2);


        HashMap<String,String> map3 = new HashMap<>();
        map3.put("name","王云龙");
        map3.put("sex","男");
        map3.put("age","20");
        map3.put("tel","18699885523");
        map3.put("type","组员");

        data.add(map3);


        HashMap<String,String> map4 = new HashMap<>();
        map4.put("name","王陵");
        map4.put("sex","男");
        map4.put("age","30");
        map4.put("tel","13866665234");
        map4.put("type","组员");

        data.add(map4);

    }


}
