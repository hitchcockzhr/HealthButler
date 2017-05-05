package com.housekeeper.ar.healthhousekeeper.guidance;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.AppContext;
import com.housekeeper.ar.healthhousekeeper.HttpUtil;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SaveCache;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lenovo on 2016/3/5.
 */
//看医生
public class SeeDoctorNewActivity extends Activity {


    String TAG = "SeeDoctorNewActivity";
    public static SeeDoctorNewActivity activity;
    private MyApp myApp;
    JSONArray provincesJA, jobTitlesJA, departmentTypesJA;
    int currentHosptialId;

    int currentProvinceId, currentCityId, currentDepartmentId;
    String currentProviceName, currentCityName, currentHospitalName, currentDepartmentName;
    JSONArray departmentJA, feesJA;
    private String readFileCache;
    private JSONObject joReadFileCache;
    private String[] arrProvinces, citiesSpinner, hospitalSpinner1, nameProvinces, nameCities,
            nameHospitals, nameDepartments, namePros, nameDoctors, nameDoctorIDs,nameDepartmentTypes;
    private int[] idDepartments, idJobTitles,departmentTypeIdInts;

    private static JSONObject[] joProvinces;
    private static JSONObject[] joCities;
    private static JSONObject[] joHospitals, joDepartments, joPros, joDoctors,joDepartmentTypes;
    private static JSONObject joDepartmentType, joDoctor;
    private static JSONArray jaProvinces, jaCities, jaHospitals, jaDepartments, jaPros, jaDoctors, jaDoctorIDs, jaDepartmentTypes ;
    ProgressDialog pDialog;

    String http, httpUrl, showStr, resultStr ;
    //TODO 通过department获取医生
    String getDoctorsbyAllUrl = "shlc/healthButler/doctors/";
    String getDoctorbyDepartmentUrl = "shlc/healthButler/doctors/department/";
    String getScheUrlString = "shlc/patient/schedules?doctorId=";
    String inprogressString = "&inprogress=false";

    private SaveCache saveCache = new SaveCache(AppContext.get());


    ArrayList<JSONObject> jsonList = new ArrayList<JSONObject>();
    private ToastCommom toastCommom;

    private Button backBtn;
    private EditText searchEditText;
    private ImageView searchImageView;

    private List<HashMap<String,String>> data = new ArrayList<>();

    private List<HashMap<String,String>> searchData = new ArrayList<HashMap<String, String>>();

    private DoctorListAdapter doctorListAdapter;
    private ListView doctorListView;

    private Spinner provinceSpinner;
    private Spinner hospitalSpinner;
    private Spinner departmentSpinner;
    private Spinner citySpinner;
    private static String birthdayStr, yearStr, monthStr, dayStr, sexStr, shengStr, shiStr, yyStr;
    private String provinceStr,hospitalStr,cityStr,departmentStr;
    String finalCurrentProvince, finalCurrentCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_doctor_new);
        activity = this;
        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(SeeDoctorNewActivity.this);
        String patientID = getIntent().getStringExtra("patientID");
        if(patientID == null || patientID.equals("")){
            //如果没有选择病人，则提示并返回
            toastCommom.ToastShow(SeeDoctorNewActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "请先选择患者");
            finish();
        }

        provinceSpinner = (Spinner)findViewById(R.id.province_spinner);
        hospitalSpinner = (Spinner)findViewById(R.id.hospital_spinner);
        citySpinner = (Spinner)findViewById(R.id.city_spinner);
        departmentSpinner = (Spinner)findViewById(R.id.department_spinner);

        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        myApp = (MyApp)getApplication();
        http = myApp.getHttp();
        //TODO 使用患者端的http，看看医生端或者导购端http有获取医生的服务吗？/doctors/department/
        //http = "http://www.airclinic.com.cn:8080/";

        initCurrentHosptial();
        //getSpinner();
        /*
        //从myApp中获取初始化信息
        jsonList = myApp.getJsonList();
        //TODO 获取所有未添加到私人医生的数据
        String fileName = myApp.getJoStartPath();
        initData(fileName);
        */
        searchEditText = (EditText)findViewById(R.id.search_edittext);
        searchImageView = (ImageView)findViewById(R.id.deatail_image_view);
        doctorListView = (ListView)findViewById(R.id.doctor_search_listview);
        doctorListAdapter = new DoctorListAdapter(SeeDoctorNewActivity.this,myApp,searchData);
        doctorListView.setAdapter(doctorListAdapter);
        getAllDoctorData();

        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData.clear();
                if (searchEditText.getText().toString().equals("")) {
                    // Toast.makeText(FollowUpClinicActivity.this,"搜索内容不能为空",Toast.LENGTH_LONG).show();
//                    toastCommom.ToastShow(SeeDoctorNewActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "搜索内容不能为空");
                    getAllDoctorData();

                    return;
                }
                String searchString = searchEditText.getText().toString();

                //如果是姓名
//					for (HashMap<String, String> map : data) {
//						if (map.get("name").contains(searchString)) {
//							searchData.add(map);
//						}
//					}

                getDoctorDataByKeyWords(searchString);

                Log.i(TAG, "searchData " + searchData);
//                doctorListAdapter.notifyDataSetChanged();
                if (searchData.isEmpty()) {
//					Toast.makeText(SeeDoctorActivity.this, "无搜索结果", Toast.LENGTH_LONG).show();
                    toastCommom.ToastShow(SeeDoctorNewActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "无搜索结果");
                }
            }

        });


        //获取病人周围的所有医生，需要打开界面就获取所有医生信息吗？


    }

    void initCurrentHosptial(){
        try {
            provincesJA = myApp.getProvincesJA();
            Log.i(TAG, "provincesJA:"+provincesJA.toString());
            jobTitlesJA = myApp.getJobTitlesJA();
            departmentTypesJA = myApp.getDepartmentTypesJA();
            feesJA = myApp.getFeeJA();
            currentHosptialId = myApp.getJoDoc().getInt("hospitalId");
            for(int i=0; i<provincesJA.length(); i++){
                JSONObject joProvince = provincesJA.getJSONObject(i);
                currentProvinceId = joProvince.getInt("id");
                currentProviceName = joProvince.getString("name");
                JSONArray cityJA = joProvince.getJSONArray("cities");
                for(int j=0; j<cityJA.length(); j++){
                    JSONObject joCity = cityJA.getJSONObject(j);
                    currentCityId = joCity.getInt("id");
                    currentCityName = joCity.getString("name");
                    JSONArray hospitalJA = joCity.getJSONArray("hospitals");
                    for(int k=0; k<hospitalJA.length(); k++){
                        JSONObject joHospital = hospitalJA.getJSONObject(k);
                        if(currentHosptialId == joHospital.getInt("id")){
                            currentHospitalName = joHospital.getString("name");
                            departmentJA = joHospital.getJSONArray("departments");
                            feesJA = joHospital.getJSONArray("fees");
                            myApp.setFeeJA(feesJA);
                            Log.i(TAG, "currentProviceName:"+currentProviceName+" currentCityName:"+currentCityName+ " currentHospitalName:"+currentHospitalName);
                            finalCurrentProvince = currentProviceName;
                            finalCurrentCity = currentCityName;
                            getSpinner();
                            break;
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
//        init();
    }

    private void getSpinner(){
        final List<String> provinceList = getProvinceData();
        final List<String> cityList = getCityData();
        final List<String> hospitalList = getHospitalData();
        final List<String> departmentList = getDepartmentData();

        // 声明一个ArrayAdapter用于存放简单数据
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>
                (SeeDoctorNewActivity.this, R.layout.spinner_item,cityList){
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
                (SeeDoctorNewActivity.this, R.layout.spinner_item,getHospitalData()){
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
        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<String>
                (SeeDoctorNewActivity.this, R.layout.spinner_item,getDepartmentData()){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(departmentList.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };


        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>
                (SeeDoctorNewActivity.this, R.layout.spinner_item,getProvinceData()){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(provinceList.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };

        //设置样式
        cityAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        //设置样式
       hospitalAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        //设置样式
        departmentAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        //设置样式
        provinceAdapter.setDropDownViewResource(R.layout.spinner_item_layout);

        citySpinner.setAdapter(cityAdapter);
        hospitalSpinner.setAdapter(hospitalAdapter);
        departmentSpinner.setAdapter(departmentAdapter);
        provinceSpinner.setAdapter(provinceAdapter);


        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        hospitalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        departmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentDepartmentName =  parent.getItemAtPosition(position).toString();

                if(position != 0){
                    for(int i=0; i<departmentJA.length(); i++){
                        try {
                            if(currentDepartmentName.equals(departmentJA.getJSONObject(i).getString("name"))){
                                currentDepartmentId = departmentJA.getJSONObject(i).getInt("id");
                                getDoctorByDepartment(currentDepartmentId);
                                Log.i(TAG, "currentDepartmentId:"+currentDepartmentId+"currentDepartmentName:"+currentDepartmentName);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //getDoctorDataByDetail();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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
    //获取所有医生
    private void getAllDoctorData(){
        searchData.clear();
        httpUrl = http + getDoctorsbyAllUrl + currentHosptialId;
        Log.i(TAG, "getAllDoctorData url:"+httpUrl);
        try {
            HttpPost post = HttpUtil.getPost(httpUrl, null);
            JSONObject joRev = HttpUtil.getString(post, 3);
            JSONArray jaAllDoc = joRev.getJSONArray("value");
            Log.i(TAG, "jo all doc: "+joRev.toString());
            for(int i=0; i<jaAllDoc.length(); i++){
                HashMap<String,String> map = new HashMap<String,String>();
                JSONObject joDoc = jaAllDoc.getJSONObject(i);
                map.put("name", joDoc.getString("name"));
                map.put("jobTitleId", String.valueOf(joDoc.getInt("jobTitleId")));
                map.put("jobTitleName", getJobTitleName(joDoc.getInt("jobTitleId")));
                map.put("hosptialName", currentHospitalName);
                map.put("hospitalId", String.valueOf(currentHosptialId));
                map.put("photoPic", joDoc.getString("photoPic"));
                map.put("meetPlace", joDoc.getString("meetPlace"));
                map.put("tel", String.valueOf(joDoc.getLong("phone")));
                map.put("userId", joDoc.getString("userId"));
                map.put("departmentId", String.valueOf(joDoc.getLong("departmentId")));
                map.put("departmentName", getDepartmentName(joDoc.getInt("departmentId")));
                joDoc.put("hosptialName", currentHospitalName);
                joDoc.put("departmentName", getDepartmentName(joDoc.getInt("departmentId")));
                joDoc.put("provinceName", finalCurrentProvince);
                joDoc.put("cityName", finalCurrentCity);
                joDoc.put("jobTitleName", getJobTitleName(joDoc.getInt("jobTitleId")));
                joDoc.put("feeId",getFeeId(joDoc.getInt("jobTitleId")));
                joDoc.put("feeName",getFeeName(joDoc.getInt("jobTitleId")));
                joDoc.put("feePrice",getFeePrice(joDoc.getInt("jobTitleId")));
                map.put("doctorJson", joDoc.toString());
                Log.i(TAG, "joDoc: " + joDoc.toString());
                searchData.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        doctorListAdapter.notifyDataSetChanged();

    }

    void getDoctorByDepartment(int currentDepartmentId){
        searchData.clear();
        httpUrl = http + getDoctorbyDepartmentUrl + currentDepartmentId;
        try {
            JSONObject joRev = HttpUtil.getResultForHttpGet(httpUrl);
            JSONArray jaAllDoc = joRev.getJSONArray("value");
            for(int i=0; i<jaAllDoc.length(); i++) {
                HashMap<String,String> map = new HashMap<String,String>();
                JSONObject joDoc = jaAllDoc.getJSONObject(i);
                map.put("name", joDoc.getString("name"));
                map.put("jobTitleId", String.valueOf(joDoc.getInt("jobTitleId")));
                map.put("jobTitleName", getJobTitleName(joDoc.getInt("jobTitleId")));
                map.put("hosptialName", currentHospitalName);
                map.put("hospitalId", String.valueOf(currentHosptialId));
                map.put("photoPic",joDoc.getString("photoPic"));
                map.put("meetPlace", joDoc.getString("meetPlace"));
                map.put("tel", String.valueOf(joDoc.getLong("phone")));
                map.put("userId", joDoc.getString("userId"));
                map.put("departmentId", String.valueOf(joDoc.getLong("departmentId")));
                map.put("departmentName", getDepartmentName(joDoc.getInt("departmentId")));
                map.put("provinceName", finalCurrentProvince);
                map.put("provinceId", String.valueOf(currentProvinceId));
                map.put("cityName", finalCurrentCity);
                map.put("cityId", String.valueOf(currentCityId));
                joDoc.put("hosptialName", currentHospitalName);
                joDoc.put("departmentName", getDepartmentName(joDoc.getInt("departmentId")));
                joDoc.put("provinceName", finalCurrentProvince);
                joDoc.put("cityName", finalCurrentCity);
                joDoc.put("jobTitleName", getJobTitleName(joDoc.getInt("jobTitleId")));
                joDoc.put("feeId",getFeeId(joDoc.getInt("jobTitleId")));
                joDoc.put("feeName",getFeeName(joDoc.getInt("jobTitleId")));
                joDoc.put("feePrice", getFeePrice(joDoc.getInt("jobTitleId")));
                map.put("doctorJson", joDoc.toString());
                searchData.add(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        doctorListAdapter.notifyDataSetChanged();
    }

    //根据关键字搜索医生,searchName有可能是名字、地区、医院？开启一个新搜索，地区等重置。点击搜索按钮的逻辑对吗？
    private void getDoctorDataByKeyWords(String searchName){
        searchData.clear();

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

        map.put("name", "83版小熊");
        map.put("address","山东省");
        map.put("hospital","测试医院");
        map.put("tel", "13845668888");
        map.put("zhicheng","主治医师");

        map.put("id","kevin");
        map.put("city", "测试城市");
        map.put("keshi", "科室测试");
        map.put("doctorJson",doctorJson.toString());



//        HashMap<String,String> map2 = new HashMap<String,String>();
//        map2.put("name","测试2");
//        map2.put("address", "天津市和平区XX街道");
//        map2.put("hospital", "测试医院");
//        map2.put("tel", "13845668888");
//
//        data.add(map2);
        searchData.add(map);

        //复制一份，为根据条件搜索提供源数据
        data.clear();
        data.addAll(searchData);




        doctorListAdapter.notifyDataSetChanged();
    }



    public void initData(String fileName){


        Log.v(TAG, "fileName:" + fileName);
        try {
            readFileCache = saveCache.read(fileName);
            joReadFileCache = new JSONObject(readFileCache);
            String result = joReadFileCache.getString("result");
            String resultMessage = joReadFileCache.getString("resultMessage");

            JSONObject joValue = joReadFileCache.getJSONObject("value");
            Log.v(TAG, "initData:" + joValue);
            jaProvinces = joValue.getJSONArray("provinces");
            joProvinces = new JSONObject[jaProvinces.length()];
            nameProvinces = new String[jaProvinces.length()];

            for(int i=0; i<jaProvinces.length(); i++){
                joProvinces[i] = jaProvinces.getJSONObject(i);
                nameProvinces[i] = joProvinces[i].getString("name");
                Log.v(TAG, "nameProvinces:" + nameProvinces[i]);
            }

            jaPros = joValue.getJSONArray("jobTitles");
            joPros = new JSONObject[jaPros.length()];
            namePros = new String[jaPros.length()];
            idJobTitles = new int[namePros.length];
            for(int i=0; i<jaPros.length(); i++){
                joPros[i] = jaPros.getJSONObject(i);
                namePros[i] = joPros[i].getString("name");
                idJobTitles[i] = joPros[i].getInt("id");
                Log.v(TAG, "namePros:" + namePros[i]);
                Log.v(TAG, "idJobTitles:" + idJobTitles[i]);
            }

            jaDepartmentTypes = joValue.getJSONArray("departmentTypes");
            joDepartmentTypes = new JSONObject[jaDepartmentTypes.length()];
            departmentTypeIdInts = new int[jaDepartmentTypes.length()];
            nameDepartmentTypes = new String[jaDepartmentTypes.length()];
            for(int i=0; i<jaDepartmentTypes.length(); i++){
                joDepartmentTypes[i] = jaDepartmentTypes.getJSONObject(i);
                departmentTypeIdInts[i] = joDepartmentTypes[i].getInt("id");
                nameDepartmentTypes[i] = joDepartmentTypes[i].getString("name");

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }




    //下拉菜单数据源
    private List<String> getCityData(){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        List<String> dataList = new ArrayList<String>();
        //dataList.add("地区");
        dataList.add(finalCurrentCity);
        return dataList;
    }

    private List<String> getHospitalData() {
        List<String> dataList = new ArrayList<String>();
        //dataList.add("医院");
        dataList.add(currentHospitalName);
        //dataList.add("北京医院");
        return dataList;
    }

    private List<String> getDepartmentData(){
        List<String> dataList = new ArrayList<String>();
        dataList.add("科室");
        for(int i=0; i<departmentJA.length(); i++){

            try {
                dataList.add(departmentJA.getJSONObject(i).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //dataList.add("神经科");
        }
        return dataList;
    }
    private List<String> getProvinceData(){
        List<String> dataList = new ArrayList<String>();
        //dataList.add("医生");
        dataList.add(finalCurrentProvince);
        //dataList.add("kevin");

        return dataList;
    }

    String getJobTitleName(int jobTitleId){
        for(int i=0; i<jobTitlesJA.length(); i++){
            try {
                if(jobTitleId == jobTitlesJA.getJSONObject(i).getInt("id")){
                    return jobTitlesJA.getJSONObject(i).getString("name");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
       return null;
    }

    String getDepartmentName(int departmentId){
        for(int i=0; i<departmentJA.length(); i++){
            try {
                if(departmentId == departmentJA.getJSONObject(i).getInt("id")){
                    return departmentJA.getJSONObject(i).getString("name");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    String getFeeId(int jobTitleId){
        for(int i=0; i<feesJA.length(); i++){
            try {
                if(jobTitleId == feesJA.getJSONObject(i).getJSONObject("jobTitle").getInt("id")){
                    return String.valueOf(feesJA.getJSONObject(i).getInt("id"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    String getFeeName(int jobTitleId){
        for(int i=0; i<feesJA.length(); i++){
            try {
                if(jobTitleId == feesJA.getJSONObject(i).getJSONObject("jobTitle").getInt("id")){
                    return feesJA.getJSONObject(i).getString("name");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    String getFeePrice(int jobTitleId){
        for(int i=0; i<feesJA.length(); i++){
            try {
                if(jobTitleId == feesJA.getJSONObject(i).getJSONObject("jobTitle").getInt("id")){
                    return String.valueOf(feesJA.getJSONObject(i).getLong("price")/100.00);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
