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
public class CommunityActivity extends Activity {


    String TAG = "CommunityActivity";



    String http, httpUrl;

    private ToastCommom toastCommom;
    private List<HashMap<String,String>> data = new ArrayList<>();
    private ListView listView;
    private CommunityAdapter communityAdapter;
    private TextView selectCommunityTV;
    private LinearLayout selectCommunityLayout;
    private Button okBtn;
    private Button backBtn;
    private int ADD = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(CommunityActivity.this);

        listView = (ListView)findViewById(R.id.community_listview);
        communityAdapter = new CommunityAdapter(CommunityActivity.this,data,0);
        listView.setAdapter(communityAdapter);

//        backBtn = (Button)findViewById(R.id.back_btn);
        okBtn = (Button)findViewById(R.id.ok_btn);

        selectCommunityTV = (TextView)findViewById(R.id.choose_community_tv);
        selectCommunityLayout = (LinearLayout)findViewById(R.id.choose_community_layout);
        selectCommunityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommunityActivity.this, AddCommunityActivity.class);
//                startActivity(intent);
                startActivityForResult(intent,ADD);

            }
        });
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddCommmunityTab addCommmunityTab = (AddCommmunityTab) getParent();

                Intent intent = new Intent();
                Bundle bundle=new Bundle();

                bundle.putParcelableArrayList("list", (ArrayList)data);
                bundle.putString("type","0");//0表示是社区
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
        map.put("name","社区1");
        map.put("address","XXX省XXX市");

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
            communityAdapter.notifyDataSetChanged();
        }
    }
}
