package com.housekeeper.ar.healthhousekeeper.followup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2016/3/5.
 */
//随诊
public class FollowUpClinicActivity extends Activity {


    String TAG = "FollowUpClinicActivity";
    //String get_bingren_list_url = "shlc/healthButler/contractPatient/list";
    String get_bingren_list_url = "xys/healthButler/patient/list";
    String http,http_url;
    private ToastCommom toastCommom;

    private EditText searchEditText;
    private ImageView searchImageView;

    private ListView listview;
    private List<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
    private List<HashMap<String,String>> searchData = new ArrayList<HashMap<String, String>>();

    private FollowUpListAdapter followUpListAdapter;

    private MyApp myApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance_follow_up_clinic);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(FollowUpClinicActivity.this);

        myApp = (MyApp)getApplication();
        http = myApp.getHttp();
        searchEditText = (EditText)findViewById(R.id.search_edittext);
        searchImageView = (ImageView)findViewById(R.id.deatail_image_view);

        listview = (ListView)findViewById(R.id.follow_up_listview);

        followUpListAdapter = new FollowUpListAdapter(FollowUpClinicActivity.this,data);

        listview.setAdapter(followUpListAdapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "OnItemClick");
                Intent intent = new Intent(FollowUpClinicActivity.this, FollowUpPatientActivity.class);
                myApp.setPatientId(data.get(position).get("id"));
                intent.putExtra("name", data.get(position).get("name"));
                intent.putExtra("id", data.get(position).get("id"));
                intent.putExtra("sex", data.get(position).get("sex"));
                intent.putExtra("age", data.get(position).get("age"));
                intent.putExtra("contractStatus", data.get(position).get("contractStatus"));
                intent.putExtra("contractTimee",data.get(position).get("contractTimee"));
                startActivity(intent);
            }
        });




        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData.clear();
                if (searchEditText.getText().toString().equals("")) {
                    // Toast.makeText(FollowUpClinicActivity.this,"搜索内容不能为空",Toast.LENGTH_LONG).show();
                    initData();
                    searchData.addAll(data);
                    followUpListAdapter.notifyDataSetChanged();
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
                followUpListAdapter.notifyDataSetChanged();
                if (data.isEmpty()) {
                    Toast.makeText(FollowUpClinicActivity.this, "无搜索结果", Toast.LENGTH_LONG).show();
                }
            }

        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        initData();
        sortDataByLastTime();
        followUpListAdapter.notifyDataSetChanged();
    }

    private void initData(){
        data.clear();
        http_url = http + get_bingren_list_url;
        http_url = "http://123.56.155.17:8080/xys/healthButler/patient/list";
        Log.i(TAG, "bingren_list_url:"+http_url);
        JSONObject josend = new JSONObject();
        try {
            josend.put("pageSize","1000");
            josend.put("page","1");
            josend.put("queryParam","");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpPost post = HttpUtil.getPost(http_url, josend);
        JSONObject jo_rev = HttpUtil.getString(post, 3);
        Log.i(TAG, "jo_rev_bingren_list:"+jo_rev.toString());
        try {
            JSONArray ja_bingren = jo_rev.getJSONArray("value");
            for(int i=0; i<ja_bingren.length(); i++){
                HashMap<String,String> map = new HashMap<String,String>();
                JSONObject jo_bingren = ja_bingren.getJSONObject(i);
                map.put("id", String.valueOf(jo_bingren.getInt("id")));
                map.put("name",jo_bingren.getString("name"));
                map.put("sex", jo_bingren.getString("sex"));
                map.put("age", String.valueOf(2017-Integer.parseInt(jo_bingren.getString("birthday").substring(0,4))));
                map.put("tel",jo_bingren.getString("phone"));
                map.put("binghao", "125675379");
                map.put("lastTime", jo_bingren.getString("followupTimeLs"));
                map.put("contractStatus", jo_bingren.getInt("contractStatus")==1?"已签约":"未签约");
                map.put("contractTimes",jo_bingren.getString("contractTimes"));
                map.put("contractTimee",jo_bingren.getString("contractTimee"));
                data.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        HashMap<String,String> map = new HashMap<String,String>();
//        map.put("id","1");
//        map.put("name","测试name");
//        map.put("sex", "男");
//        map.put("age","30");
//        map.put("tel","12345668765");
//        map.put("binghao", "125675379");
//        map.put("lastTime", "2016-07-13 11:00:06");
//        map.put("signStatus", "已签约");
//
//        HashMap<String,String> map2 = new HashMap<String,String>();
//        map2.put("id","2");
//        map2.put("name","测试2");
//        map2.put("sex", "女");
//        map2.put("age","25");
//        map2.put("tel","13845668888");
//        map2.put("binghao","88888888");
//
//        map2.put("lastTime","2016-07-13 22:00:10");
//        map2.put("signStatus", "未签约");

//        data.add(map2);
//        data.add(map);


    }

    Comparator<HashMap<String, String>> mapComprator = new Comparator<HashMap<String, String>>(){


        @Override
        public int compare(HashMap<String, String> map1,
                           HashMap<String, String> map2) {
            // TODO Auto-generated method stub
            String start1 = map1.get("lastTime");
            String start2 = map2.get("lastTime");
            long long1 = Long.parseLong(dateToInt(start1));
            long long2 = Long.parseLong(dateToInt(start2));
            int result = (int)(long1-long2);
            Log.i(TAG, "compare:"+long1+" "+long2+" "+result);
            return result;
        }
    };
    public String dateToInt(String str){
        str=str.replace(":", "");
        str=str.replace("-", "");
        str=str.replace(" ", "");
        return str;

    }
    public void sortDataByLastTime(){
        Collections.sort(data, mapComprator);
        Log.i(TAG, data.toString());
    }


}
