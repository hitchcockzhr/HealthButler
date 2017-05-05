package com.housekeeper.ar.healthhousekeeper.sign;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lenovo on 2016/3/5.
 */
//签约附近的医生列表
public class SignDoctorListActivity extends BaseActivity {


    String TAG = "SignDoctorListActivity";



    String http, httpUrl;
    private ToastCommom toastCommom;

    private TextView titleView;
    private ListView doctorListView;
    private SignDoctorListAdapter signDoctorListAdapter;
    private List<HashMap<String,String>> data = new ArrayList<>();

    private List<HashMap<String,String>> searchData = new ArrayList<HashMap<String, String>>();

    private Button backBtn;
    private EditText searchEditText;
    private ImageView searchImageView;
    private MyApp myApp;

    private String patientID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(SignDoctorListActivity.this);
        myApp = (MyApp)getApplication();

        patientID = myApp.getPatientId();
        if (patientID == null || patientID.equals("")){
            toastCommom.ToastShow(SignDoctorListActivity.this,(ViewGroup)findViewById(R.id.toast_layout_root),"请选择患者");
            finish();
        }

        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        doctorListView = (ListView)findViewById(R.id.patient_listview);
        signDoctorListAdapter = new SignDoctorListAdapter(SignDoctorListActivity.this,myApp,data);
        doctorListView.setAdapter(signDoctorListAdapter);

        titleView = (TextView)findViewById(R.id.title);
        titleView.setText("医生列表");

        searchEditText = (EditText)findViewById(R.id.search_edittext);
        searchImageView = (ImageView)findViewById(R.id.deatail_image_view);
        searchEditText.setHint("姓名");

        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData.clear();
                if(searchEditText.getText().toString().equals("")){
                    // Toast.makeText(FollowUpClinicActivity.this,"搜索内容不能为空",Toast.LENGTH_LONG).show();
                    initData();
                    searchData.addAll(data);
                    signDoctorListAdapter.notifyDataSetChanged();
                    return;
                }
               String searchString = searchEditText.getText().toString();
/*                 Pattern p = Pattern.compile("[0-9]*");
                Matcher m = p.matcher(searchString);
                if(m.matches() ){
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
*/
                //如果是姓名
                for(HashMap<String,String> map:data){
                    if(map.get("name").contains(searchString)){
                        searchData.add(map);
                    }
                }
                data.clear();
                data.addAll(searchData);
                Log.i(TAG, "searchData " + searchData);
                signDoctorListAdapter.notifyDataSetChanged();
                if(data.isEmpty()){
                    Toast.makeText(SignDoctorListActivity.this, "无搜索结果", Toast.LENGTH_LONG).show();
                }
            }

        });


    }
    @Override
    protected void onResume() {
        super.onResume();
        initData();
        signDoctorListAdapter.notifyDataSetChanged();
    }

    private void initData(){
        data.clear();
        HashMap<String,String> map = new HashMap<String,String>();
        JSONObject doctorJson = new JSONObject();
        try {
            doctorJson.put("departmentId",2);
            doctorJson.put("description","认真复制");
            doctorJson.put("jobTitleId",2);
            doctorJson.put("jobTitleNo","9527");

            doctorJson.put("name","83版小熊");
            doctorJson.put("phone","18911568719");
            doctorJson.put("sex","男");
            doctorJson.put("skill","擅长手术");
            doctorJson.put("userId","kevin");
            doctorJson.put("zhicheng","医师");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        map.put("name","83版小熊");
        map.put("address","山东省");
        map.put("hospital","测试医院");
        map.put("tel", "13845668888");
        map.put("zhicheng","主治医师");

        map.put("id","kevin");
        map.put("city","测试城市");
        map.put("keshi","科室测试");
        map.put("doctorJson",doctorJson.toString());
        map.put("signNum","5");



//        HashMap<String,String> map2 = new HashMap<String,String>();
//        map2.put("name","测试2");
//        map2.put("address", "天津市和平区XX街道");
//        map2.put("hospital", "测试医院");
//        map2.put("tel", "13845668888");
//
//        data.add(map2);
        data.add(map);


    }


}
