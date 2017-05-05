package com.housekeeper.ar.healthhousekeeper.appointmentlist;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lenovo on 2016/3/5.
 */
//预约列表
public class AppointmentListActivity extends BaseActivity {


    String TAG = "AppointmentListActivity";



    String http, httpUrl;

    private ToastCommom toastCommom;
    private Button backBtn;
    private ParentAdapt mParentAdapt;
    private List<HashMap<String,String>> data = new ArrayList<>();

    private Spinner diquSpinner;
    private Spinner hospitalSpinner;
    private Spinner keshiSpinner;

    private String diquStr,hospitalStr,keshiStr;
    private List<HashMap<String,String>> searchData = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(AppointmentListActivity.this);


        initData();
        initView();
        getSpinner();
    }
    @Override
    protected void onResume() {
        super.onResume();
//        init();
    }

    private void initData() {
        data.clear();
        HashMap<String,String> map = new HashMap<>();
        map.put("name","张三丰");
        map.put("hospital", "1号社区医院");
        map.put("keshi","内科");
        map.put("zhicheng","主任医师");
        map.put("city", "石家庄");

        data.add(map);

        HashMap<String,String> map2 = new HashMap<>();
        map2.put("name","刘亚新");
        map2.put("hospital","2号社区医院");
        map2.put("keshi","外科");
        map2.put("zhicheng","主任医师");
        map2.put("city", "北京");

        data.add(map2);

        searchData.clear();
        searchData.addAll(data);

    }

    private void initView() {

        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        diquSpinner = (Spinner)findViewById(R.id.diqu_spinner);
        hospitalSpinner = (Spinner)findViewById(R.id.hospital_spinner);
        keshiSpinner = (Spinner)findViewById(R.id.keshi_spinner);

        ListView listView = (ListView)findViewById(R.id.doctor_listview);
        mParentAdapt = new ParentAdapt(this, searchData);
        listView.setAdapter(mParentAdapt);
        listView.setOnItemClickListener(new AdaptItemClick());
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG,"医生列表 onItemSelected");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //父listview的单击事件的监听
    private class AdaptItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Log.i(TAG, "医生列表AdaptItemClick before mParentItem " + ParentAdapt.mParentItem);
            Log.i(TAG, "医生列表AdaptItemClick before mbShowChild " + ParentAdapt.mbShowChild);
            Log.i(TAG, "医生列表AdaptItemClick before position " + position);
            if (ParentAdapt.mParentItem == position && ParentAdapt.mbShowChild) {
                ParentAdapt.mbShowChild = false;
            }else {
                ParentAdapt.mbShowChild = true;
            }
            ParentAdapt.mParentItem = position;

            mParentAdapt.notifyDataSetChanged();
            Log.i(TAG, "医生列表AdaptItemClick after mParentItem " + ParentAdapt.mParentItem);
            Log.i(TAG, "医生列表AdaptItemClick after mbShowChild " + ParentAdapt.mbShowChild);
        }
    }


    private void getSpinner(){

        final List<String> cityList = getCityData();
        final List<String> hospitalList = getHospitalData();
        final List<String> keshiList = getKeShiData();

        // 声明一个ArrayAdapter用于存放简单数据
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>
                (AppointmentListActivity.this, R.layout.spinner_item,cityList){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(cityList.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };
        ArrayAdapter<String> hospitalAdapter = new ArrayAdapter<String>
                (AppointmentListActivity.this, R.layout.spinner_item,getHospitalData()){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(hospitalList.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };
        ArrayAdapter<String> keshiAdapter = new ArrayAdapter<String>
                (AppointmentListActivity.this, R.layout.spinner_item,getKeShiData()){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(keshiList.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };



        //设置样式
        cityAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        //设置样式
        hospitalAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        //设置样式
        keshiAdapter.setDropDownViewResource(R.layout.spinner_item_layout);


        diquSpinner.setAdapter(cityAdapter);
        hospitalSpinner.setAdapter(hospitalAdapter);
        keshiSpinner.setAdapter(keshiAdapter);


        diquSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diquStr = parent.getItemAtPosition(position).toString();
                Log.i(TAG, "diquSpinner onItemSelected ");

                    getDoctorDataByDetail();




                diquSpinner.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        Log.i(TAG, "diquSpinner touch ");
                        closeSoftKeyboard();
                        return false;
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        hospitalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hospitalStr =  parent.getItemAtPosition(position).toString();
                Log.i(TAG, "hospitalSpinner onItemSelected ");

                    getDoctorDataByDetail();



                hospitalSpinner.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {

                        Log.i(TAG, "hospitalSpinner touch ");
                        closeSoftKeyboard();
                        return false;
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        keshiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                keshiStr =  parent.getItemAtPosition(position).toString();
                Log.i(TAG, "keshiSpinner onItemSelected ");

                    getDoctorDataByDetail();



                keshiSpinner.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {

                        Log.i(TAG, "keshiSpinner touch ");
                        closeSoftKeyboard();
                        return false;
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void closeSoftKeyboard(){

        /**隐藏软键盘**/
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private List<String> getCityData(){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        List<String> dataList = new ArrayList<String>();
        dataList.add("地区");
        dataList.add("北京");
        return dataList;
    }

    private List<String> getHospitalData() {
        List<String> dataList = new ArrayList<String>();
        dataList.add("医院");
        dataList.add("协和医院");
        dataList.add("1号社区医院");
        return dataList;
    }

    private List<String> getKeShiData(){
        List<String> dataList = new ArrayList<String>();
        dataList.add("科室");
        dataList.add("外科");
        dataList.add("内科");
        return dataList;
    }

    //根据选中的条件进行搜索
    private void getDoctorDataByDetail(){

        searchData.clear();
        List<HashMap<String,String>> diquList = new ArrayList<>();
        List<HashMap<String,String>> hospitalList = new ArrayList<>();
        List<HashMap<String,String>> keshiList = new ArrayList<>();

        diquList.clear();
        hospitalList.clear();
        keshiList.clear();

        Log.i(TAG,"diquStr "+diquStr);
        if(diquStr== null || diquStr.equals("地区")){
            //不用搜索地区部分了
            diquStr="地区";
            diquList.addAll(data);

        }else{
            for(HashMap<String,String> map:data){
                if(diquStr.equals(map.get("city"))){
                    diquList.add(map);
                }
            }
        }
        Log.i(TAG,"下拉框 diquList "+diquList.toString());
        Log.i(TAG,"hospitalStr "+hospitalStr);
        if(hospitalStr == null || hospitalStr.equals("医院")) {
            hospitalStr = "医院";
            hospitalList.addAll(diquList);
        }else{
            for(HashMap<String,String> map:diquList){
                if(hospitalStr.equals(map.get("hospital"))){

                    hospitalList.add(map);

                }
            }
        }
        Log.i(TAG,"下拉框 hospitalList "+hospitalList.toString());
        Log.i(TAG,"keshiStr "+keshiStr);
        if(keshiStr == null || keshiStr.equals("科室")){
            keshiStr = "科室";
            keshiList.addAll(hospitalList);
        }else{
            for(HashMap<String,String> map:hospitalList){
                if(keshiStr.equals(map.get("keshi"))){

                    keshiList.add(map);

                }
            }
        }
        Log.i(TAG,"下拉框 keshiList "+keshiList.toString());
        searchData.addAll(keshiList);
        Log.i(TAG,"下拉框 searchData "+searchData.toString());
        mParentAdapt.notifyDataSetChanged();
        if (searchData.isEmpty()) {
            toastCommom.ToastShow(AppointmentListActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "无搜索结果");
        }

    }


}
