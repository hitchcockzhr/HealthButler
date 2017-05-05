package com.housekeeper.ar.healthhousekeeper.followup;

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
//异常指标
public class FollowUpTableCatchActivity extends BaseActivity {


    String TAG = "FollowUpTableCatchActivity";



    String http, httpUrl;

    private ToastCommom toastCommom;
    private Button backBtn;
    //基础指标
    private ListView basicIndexListView;
    //检查名称
    private ListView jcNameListView;
    private List<HashMap<String,String>> basicIndexData = new ArrayList<>();
    private List<HashMap<String,String>> jcNameData = new ArrayList<>();
    private FUTableBasicIndexAdapter basicIndexAdapter;
    private FUTableBasicIndexAdapter jcNameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_table_catch);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(FollowUpTableCatchActivity.this);

        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        basicIndexListView = (ListView)findViewById(R.id.basic_list);
        jcNameListView = (ListView)findViewById(R.id.jc_list);

        basicIndexAdapter = new FUTableBasicIndexAdapter(FollowUpTableCatchActivity.this,basicIndexData);

        basicIndexListView.setAdapter(basicIndexAdapter);


        jcNameAdapter = new FUTableBasicIndexAdapter(FollowUpTableCatchActivity.this,jcNameData);
        jcNameListView.setAdapter(jcNameAdapter);


        initBasicIndexData();
        initJCNameData();

    }
    @Override
    protected void onResume() {
        super.onResume();
//        init();
    }


    private void initBasicIndexData(){
        HashMap<String,String> map = new HashMap<>();
        map.put("program","体重");
        map.put("value","98");
        map.put("unit","kg");
        map.put("range","90-95");

        basicIndexData.add(map);

        HashMap<String,String> map2 = new HashMap<>();
        map2.put("program","BMI");
        map2.put("value","33");
        map2.put("unit","kg");
        map2.put("range","20-30");

        basicIndexData.add(map2);
    }

    private void initJCNameData(){

        HashMap<String,String> map = new HashMap<>();
        map.put("program","餐前血糖");
        map.put("value","8.2");
        map.put("unit","mmol/L");
        map.put("range","7-8");

        jcNameData.add(map);

        HashMap<String,String> map2 = new HashMap<>();
        map2.put("program","餐后2h血糖");
        map2.put("value","11.2");
        map2.put("unit","mmol/L");
        map2.put("range","9-11");

        jcNameData.add(map2);


        HashMap<String,String> map3 = new HashMap<>();
        map3.put("program","舒张压");
        map3.put("value","95");
        map3.put("unit","mmHg");
        map3.put("range","80-90");

        jcNameData.add(map3);

        HashMap<String,String> map4 = new HashMap<>();
        map4.put("program","收缩压");
        map4.put("value","145");
        map4.put("unit","mmHg");
        map4.put("range","100-130");

        jcNameData.add(map4);
    }

}
