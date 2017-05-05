package com.housekeeper.ar.healthhousekeeper.membershipcard;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lenovo on 2016/3/5.
 */
//某个病人的会员卡列表
public class MCPatientTabPerMCActivity extends BaseActivity {


    String TAG = "MCPatientTabPerMCActivity";



    String http, httpUrl;

    private ToastCommom toastCommom;
    private MCPatientTabPerMCListAdapter mcPatientTabPerMCListAdapter;
    private List<HashMap<String,String>> data = new ArrayList<>();
    private MyApp myApp;
    private ListView mctypeListView;
    private Button backBtn;
    private TextView patientNameTextView;
    private TextView patientSexTextView;
    private TextView patientAgeTextView;
    private TextView patientTelTextView;
    private String patientId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_patient_tab_perpatient_mctype);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(MCPatientTabPerMCActivity.this);

        myApp = (MyApp)getApplication();

        patientAgeTextView = (TextView)findViewById(R.id.patient_age_tv);
        patientNameTextView = (TextView)findViewById(R.id.patient_name_tv);
        patientSexTextView = (TextView)findViewById(R.id.patient_sex_tv);
        patientTelTextView = (TextView)findViewById(R.id.patient_tel_tv);
        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mctypeListView = (ListView)findViewById(R.id.patient_mctype_listview);

        mcPatientTabPerMCListAdapter = new MCPatientTabPerMCListAdapter(MCPatientTabPerMCActivity.this,myApp,data);
        mctypeListView.setAdapter(mcPatientTabPerMCListAdapter);

        patientId = myApp.getPatientId();

        Bundle bundle = getIntent().getExtras();

        patientNameTextView.setText(bundle.getString("name"));
        patientAgeTextView.setText(bundle.getString("age"));
        patientSexTextView.setText(bundle.getString("sex"));
        patientTelTextView.setText(bundle.getString("tel"));


    }
    @Override
    protected void onResume() {
        super.onResume();
        initData();
        mcPatientTabPerMCListAdapter.notifyDataSetChanged();
    }


    //获取病人的会员卡列表
    private void initData(){
        data.clear();

        HashMap<String,String> map = new HashMap<>();
        map.put("hospital","测试医院");
        map.put("mctype","白金会员卡");
        map.put("balance","1500");
        map.put("date","2017.06");
        map.put("name","测试name");
        map.put("sex","男");
        map.put("age","30");
        map.put("tel","12345668765");

        data.add(map);

        HashMap<String,String> map2 = new HashMap<>();
        map2.put("hospital","赵鑫鑫口腔诊所");
        map2.put("mctype","白银会员卡");
        map2.put("balance","300");
        map2.put("date","2018.03");
        map.put("name","测试name");
        map.put("sex","男");
        map.put("age","30");
        map.put("tel","12345668765");

        data.add(map2);

    }

}
