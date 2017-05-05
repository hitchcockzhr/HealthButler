package com.housekeeper.ar.healthhousekeeper.checkstand;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2016/3/5.
 */
//付款病人列表
public class PayPatientListActivity extends Activity {


    String TAG = "PayPatientListActivity";

    String getNotPaitUrl = "shlc/healthButler/noPaidMedicalRecord?patientNameOrPhone=";
    MyApp myApp;
    String http, httpUrl;
    private ToastCommom toastCommom;
    int systemYear, systemMonth, systemDay, systemHour, systemMinute;
    private ListView patientListView;
    private PayPatientListAdapter patientListAdapter;
    private List<HashMap<String,String>> data = new ArrayList<>();

    private List<HashMap<String,String>> searchData = new ArrayList<HashMap<String, String>>();

    private Button backBtn;
    private EditText searchEditText;
    private ImageView searchImageView;
    private LinearLayout titleLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(PayPatientListActivity.this);
        myApp = (MyApp)getApplication();
        http = myApp.getHttp();
        getTime();
        titleLayout = (LinearLayout)findViewById(R.id.title_layout);
        titleLayout.setVisibility(View.GONE);

        patientListView = (ListView)findViewById(R.id.patient_listview);
        patientListAdapter = new PayPatientListAdapter(PayPatientListActivity.this,data,0);
        patientListView.setAdapter(patientListAdapter);

        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchEditText = (EditText)findViewById(R.id.search_edittext);
        searchImageView = (ImageView)findViewById(R.id.search_iv);


        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData.clear();

                initData();
                searchData.addAll(data);
                patientListAdapter.notifyDataSetChanged();

            }

        });



    }
    @Override
    protected void onResume() {
        super.onResume();
        getTime();
        //initData();
        //patientListAdapter.notifyDataSetChanged();
    }

    private void initData(){
        data.clear();
        httpUrl = http + getNotPaitUrl + searchEditText.getText().toString();
        Log.i(TAG, "not paid med: "+httpUrl);
        HttpPost post = HttpUtil.getPost(httpUrl,null);
        JSONObject joRev = HttpUtil.getString(post, 3);
        Log.i(TAG, "jo not paid: "+joRev.toString());
        try {
            JSONArray jaRev = joRev.getJSONArray("value");
            for (int i=0; i<jaRev.length(); i++){
                JSONObject jo = jaRev.getJSONObject(i);
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("name", jo.getString("patientName"));
                map.put("sex",jo.getString("patientSex"));
                map.put("age", String.valueOf(systemYear - Integer.parseInt(jo.getString("patientBirthday").substring(0, 4))));
                map.put("tel",jo.getString("patientPhone"));
                map.put("binghao", String.valueOf(jo.getLong("id")));
                map.put("patientID",jo.getString("patientId"));
                map.put("status", String.valueOf(jo.getLong("status")));
                data.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //获取系统时间
    public void getTime(){
        Time time = new Time();
        time.setToNow();
        systemYear = time.year;
        systemMonth = time.month+1;
        systemDay = time.monthDay;
        systemHour = time.hour;
        systemMinute = time.minute;
        Log.i("SystemTime", systemYear + " " + systemMonth + " " + systemDay + " " + systemHour + " " + systemMinute);
    }
}
