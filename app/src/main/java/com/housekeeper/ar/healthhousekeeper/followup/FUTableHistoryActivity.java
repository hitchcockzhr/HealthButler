package com.housekeeper.ar.healthhousekeeper.followup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.HttpUtil;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Lenovo on 2017/3/23.
 */
public class FUTableHistoryActivity extends BaseActivity {

    String TAG = "FUTableHistoryActivity";
    String get_history_list_url = "http://123.56.155.17:8080/xys/healthButler/followupSheet/history/";//{patientId}
    private ToastCommom toastCommom;
    MyApp myApp;
    String http, httpUrl;
    private Button backBtn;
    String id;
    private ListView historyListView;
    private List<HashMap<String,String>> data = new ArrayList<>();
    private FUTableHistoryAdapter historyAdapter;
    private String formats = "yyyy-MM-dd HH:mm:ss";
    JSONObject joRev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_table_history);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(FUTableHistoryActivity.this);
        myApp = (MyApp)getApplication();
        final Intent intent = getIntent();
        if(intent != null){

            id = intent.getStringExtra("id");
        }
        backBtn = (Button) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        historyAdapter = new FUTableHistoryAdapter(FUTableHistoryActivity.this,data);
        historyListView = (ListView)findViewById(R.id.history_listview);
        historyListView.setAdapter(historyAdapter);

        initData();
    }

    private void initData(){
        data.clear();
        HttpPost post = HttpUtil.getPost(get_history_list_url+id, null);
        joRev = HttpUtil.getString(post, 3);
        Log.i(TAG, "get history list jo:"+joRev.toString());
//       String timeString = TimeStamp.TimeStamp2Date(timeLong, formats);
        try {
            JSONArray jaDiseases = joRev.getJSONArray("checkList");
            for(int i=0; i<jaDiseases.length(); i++){
                HashMap<String,String> map = new HashMap<>();
                JSONObject joDisease = jaDiseases.getJSONObject(i).getJSONObject("diease");
                map.put("time", joDisease.getString("createdTime"));
                map.put("template", joDisease.getString("dieaseName"));
                map.put("id", String.valueOf(joDisease.getInt("dieaseCheckId")));
                data.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sortDataByTime();
    }

    private void sortDataByTime(){
        Collections.sort(data, mapComprator);
    }

    Comparator<HashMap<String, String>> mapComprator = new Comparator<HashMap<String, String>>(){


        @Override
        public int compare(HashMap<String, String> map1,
                           HashMap<String, String> map2) {
            // TODO Auto-generated method stub
            String start1 = map1.get("time");
            String start2 = map2.get("time");
            long long1 = Long.parseLong(dateToInt(start1));
            long long2 = Long.parseLong(dateToInt(start2));
            int result = (int)(long1-long2);
            Log.i(TAG, "compare:" + long1 + " " + long2 + " " + result);
            return result;
        }
    };
    public String dateToInt(String str){
        str=str.replace(":", "");
        str=str.replace("-", "");
        str=str.replace(" ", "");
        return str;

    }
}
