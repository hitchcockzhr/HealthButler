package com.housekeeper.ar.healthhousekeeper.guidance;

import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2016/3/5.
 */
//会诊
public class PatientListActivity extends BaseActivity {


    String TAG = "PatientListActivity";

    int systemYear, systemMonth, systemDay, systemHour, systemMinute;
    MyApp myApp;
    String http, httpUrl;
    String getPatientUrl = "shlc/healthButler/patient/list";
    private ToastCommom toastCommom;

    private ListView patientListView;
    private PatientListAdapter patientListAdapter;
    private List<HashMap<String,String>> data = new ArrayList<>();

    private List<HashMap<String,String>> searchData = new ArrayList<HashMap<String, String>>();

    private Button backBtn;
    private EditText searchEditText;
    private ImageView searchImageView;
    TextView moreTV;
    String searchString;
    int pageNumber = 1;
    int pageSize = 20;
    int totalCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        myApp = (MyApp)getApplication();
        http = myApp.getHttp();
        getTime();
        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(PatientListActivity.this);


        patientListView = (ListView)findViewById(R.id.patient_listview);
        patientListAdapter = new PatientListAdapter(PatientListActivity.this,data);
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
        moreTV = (TextView)findViewById(R.id.more_patient_btn);
        moreTV.setText("请在上方查询");

        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData.clear();
               // if(searchEditText.getText().toString().equals("")){
                    // Toast.makeText(FollowUpClinicActivity.this,"搜索内容不能为空",Toast.LENGTH_LONG).show();
                searchString = searchEditText.getText().toString();
                initData(searchString, pageNumber, pageSize);
                //searchData.addAll(data);
                //patientListAdapter.notifyDataSetChanged();
                // return;
               // }
                /*
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
                patientListAdapter.notifyDataSetChanged();
                if(data.isEmpty()){
                    Toast.makeText(PatientListActivity.this, "无搜索结果", Toast.LENGTH_LONG).show();
                }
                */
            }

        });



    }
    @Override
    protected void onResume() {
        super.onResume();
        //searchString = searchEditText.getText().toString();
        //initData(searchString);
        //patientListAdapter.notifyDataSetChanged();
    }

    private void initData( final String searchString,int pageNumber, final int pageSize){
        JSONObject joSend = new JSONObject();
        try {
            joSend.put("queryParam", searchString);
            joSend.put("pageNumber", pageNumber);
            joSend.put("pageSize", pageSize);
            httpUrl = http + getPatientUrl;
            Log.i(TAG, "httpurl:"+httpUrl);
            Log.i(TAG, "joSend:"+joSend.toString());
            HttpPost post = HttpUtil.getPost(httpUrl, joSend);
            JSONObject joRev = HttpUtil.getString(post, 3);
            Log.i(TAG, "joPatient List: "+ pageNumber + ": "+joRev.toString());
            JSONArray jaPatient = joRev.getJSONArray("value");
            for(int i=0; i<jaPatient.length(); i++){
                JSONObject jo = jaPatient.getJSONObject(i);
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("name", jo.getString("name"));
                map.put("sex", jo.getString("sex"));
                map.put("age", String.valueOf(systemYear - Integer.parseInt(jo.getString("birthday").substring(0, 4))));
                map.put("tel",jo.getString("phone"));
                map.put("userId", jo.getString("userId"));
                map.put("photoPic", jo.getString("photoPic"));
                map.put("level", String.valueOf(jo.getInt("level")));
                map.put("binghao","123456");
                data.add(map);
                totalCount ++;
            }
            Log.i(TAG, "totalCount:"+totalCount);
            pageNumber++;

            if(joRev.getInt("totalCount")==0){

                moreTV.setText("查询无结果，请重新检索");
                moreTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        return;
                    }
                });
            }
            else if(totalCount != joRev.getInt("totalCount")){

                moreTV.setText("点击获取更多");

                final int finalPageNumber = pageNumber;
                moreTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initData(searchString, finalPageNumber, pageSize);
                    }
                });
            }else if(totalCount == joRev.getInt("totalCount")){
                moreTV.setText("已经没有更多结果了");
                moreTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        return;
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        patientListAdapter.notifyDataSetChanged();




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
