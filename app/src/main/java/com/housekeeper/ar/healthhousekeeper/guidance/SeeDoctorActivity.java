package com.housekeeper.ar.healthhousekeeper.guidance;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.housekeeper.ar.healthhousekeeper.AppContext;
import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.HttpUtil;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SaveCache;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//看医生
public class SeeDoctorActivity extends BaseActivity {

	String TAG = "SeeDoctorActivity";

	private ActionBarDrawerToggle drawerbar;
	//实现右侧抽屉显示效果
	public DrawerLayout drawerLayout;
	private SeeDoctorSlideFragment slideFragment ;
	private RelativeLayout rightRelativeLayout;

	private MyApp myApp;

	private String readFileCache;
	private JSONObject joReadFileCache;
	private String[] arrProvinces, citiesSpinner, hospitalSpinner, nameProvinces, nameCities,
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
	String getDoctorsUrl = "shlc/patient/doctors/department/";

	String getScheUrlString = "shlc/patient/schedules?doctorId=";
	String inprogressString = "&inprogress=false";
	JSONObject joRev;
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

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyActivityManager.pushOneActivity(SeeDoctorActivity.this);
		SysApplication.getInstance().addActivity(this);
		toastCommom = ToastCommom.createToastConfig();
		setContentView(R.layout.activity_see_doctor);
		Log.i(TAG, "onCreate");


		String patientID = getIntent().getStringExtra("patientID");
		if(patientID == null || patientID.equals("")){
			//如果没有选择病人，则提示并返回
			toastCommom.ToastShow(SeeDoctorActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "请先选择患者");
			finish();
		}

		backBtn = (Button)findViewById(R.id.back_btn);
		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		myApp = (MyApp)getApplication();
//		http = myApp.getHttp();
		//TODO 使用患者端的http，看看医生端或者导购端http有获取医生的服务吗？/doctors/department/
		http = "http://www.airclinic.com.cn:8080/";

		//从myApp中获取初始化信息
		jsonList = myApp.getJsonList();
		//TODO 获取所有未添加到私人医生的数据
		String fileName = myApp.getJoStartPath();
		initData(fileName);

		initView("请点击医院进行选择");
		drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);

		drawerLayout.setScrimColor(0x00000000);;

		initEvent();

		searchEditText = (EditText)findViewById(R.id.search_edittext);
		searchImageView = (ImageView)findViewById(R.id.deatail_image_view);
		doctorListView = (ListView)findViewById(R.id.doctor_search_listview);
		doctorListAdapter = new DoctorListAdapter(SeeDoctorActivity.this,myApp,searchData);
		doctorListView.setAdapter(doctorListAdapter);

		searchImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				searchData.clear();
				if (searchEditText.getText().toString().equals("")) {
					// Toast.makeText(FollowUpClinicActivity.this,"搜索内容不能为空",Toast.LENGTH_LONG).show();
					toastCommom.ToastShow(SeeDoctorActivity.this,(ViewGroup)findViewById(R.id.toast_layout_root),"搜索内容不能为空");
//					initDoctorData();
//					searchData.addAll(data);
//					doctorListAdapter.notifyDataSetChanged();
					return;
				}
				String searchString = searchEditText.getText().toString();

					//如果是姓名
//					for (HashMap<String, String> map : data) {
//						if (map.get("name").contains(searchString)) {
//							searchData.add(map);
//						}
//					}

				getDoctorData(searchString);

				Log.i(TAG, "searchData " + searchData);
				doctorListAdapter.notifyDataSetChanged();
				if (searchData.isEmpty()) {
//					Toast.makeText(SeeDoctorActivity.this, "无搜索结果", Toast.LENGTH_LONG).show();
					toastCommom.ToastShow(SeeDoctorActivity.this,(ViewGroup)findViewById(R.id.toast_layout_root),"无搜索结果");
				}
			}

		});


	}

	@Override
	protected void onResume() {
		super.onResume();
//		searchEditText.setText("");
//		data.clear();
	}

	//根据医生名字搜索医生
	private void getDoctorData(String searchName){
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
	//初始化视图--中间选择部分
	public void initView(String str){
		slideFragment = new SeeDoctorSlideFragment();
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.main_content_frame_parent, slideFragment);
		fragmentTransaction.commitAllowingStateLoss();
		initRightLayout(str);
	}

	//打开右侧抽屉效果
	public void initRightLayout(String str){
		Log.i(TAG,"initRightLayout");
		rightRelativeLayout = (RelativeLayout)findViewById(R.id.main_right_drawer_layout);
		rightRelativeLayout.removeAllViews();
		View view = getLayoutInflater().inflate(R.layout.right_drawer_layout, null);
		TextView testTextView = (TextView)view.findViewById(R.id.right_drawer_textView);

		ListView rightDrawerListView = (ListView)view.findViewById(R.id.right_drawer_listView);
		if(str.equals("地区")){
			Log.v(TAG, "地区");
			testTextView.setText("省份");
			/*
			rightDrawerListView.setAdapter(
					new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getShengData()));
					*/
			SimpleAdapter adapter = new SimpleAdapter(this, getShengData(),
					R.layout.right_drawer_list_item, new String[]{"name"},
					new int[]{R.id.item_tv});
			rightDrawerListView.setAdapter(adapter);
			rightDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> shengParent, View shengView,
										int shengPosition, long shengId) {
					// TODO Auto-generated method stub
					myApp.setShengposition(shengPosition);
					String shengStr = shengParent.getItemAtPosition(shengPosition).toString();
					shengStr = shengStr.substring(6, shengStr.length()-1);
					Log.v(TAG, "shengStr:"+shengStr);
					Log.v(TAG, "name:"+nameProvinces[shengPosition]);
					if(shengStr.equals(nameProvinces[shengPosition])){
						if(shengStr.equals(myApp.getDiquString())){
							//return;
							myApp.setDiquString(shengStr);
						}else{
							myApp.setDiquString(shengStr);
							myApp.setKeshiString("科室");
							myApp.setYiyuanString("医院");
							myApp.setYishengString("医生");
							myApp.setCityString(null);
						}
						slideFragment.keshiTextView.setText(myApp.getKeshiString());
						slideFragment.yiyuanTextView.setText(myApp.getYiyuanString());
						slideFragment.yishengTextView.setText(myApp.getYishengString());
						slideFragment.diquTextView.setText(shengStr);

						Log.v(TAG, "position:"+shengPosition);
						for(int i=0; i<joProvinces.length; i++){
							try {

								if(shengStr.equals(joProvinces[i].getString("name"))){
									jaCities = joProvinces[i].getJSONArray("cities");
									joCities = new JSONObject[jaCities.length()];
									nameCities = new String[joCities.length];

									for(int j=0; j<jaCities.length(); j++){
										joCities[j] = jaCities.getJSONObject(j);
										nameCities[j] = joCities[j].getString("name");
										Log.v(TAG, "nameCity:"+nameCities[j]);
									}
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					initRightLayout("City");
					//openRightLayout();
				}
			});
		}else if(str.equals("City")){
			Log.v(TAG, "CITY");
			testTextView.setText("城市");
			SimpleAdapter adapter = new SimpleAdapter(this, getShiData(),
					R.layout.right_drawer_list_item, new String[]{"name"},
					new int[]{R.id.item_tv});
			rightDrawerListView.setAdapter(adapter);
			rightDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> shiParent, View shiView,
										int shiPosition, long shiId) {
					// TODO Auto-generated method stub
					String shiStr = shiParent.getItemAtPosition(shiPosition).toString();
					Log.v(TAG, "shiStr:"+shiStr);
					shiStr = shiStr.substring(6, shiStr.length()-1);
					if(shiStr.equals(nameCities[shiPosition])){
						if(shiStr.equals(myApp.getCityString())){
							//	return;
							myApp.setCityString(shiStr);
						}else{
							myApp.setCityString(shiStr);
							myApp.setKeshiString("科室");
							myApp.setYiyuanString("医院");
							myApp.setYishengString("医生");
						}
						//myApp.setCityString(null);
						slideFragment.keshiTextView.setText(myApp.getKeshiString());
						slideFragment.yiyuanTextView.setText(myApp.getYiyuanString());
						slideFragment.yishengTextView.setText(myApp.getYishengString());
						//slideFragment.diquTextView.setText(shengStr);
						for(int i=0; i<joCities.length; i++){
							try {
								if(shiStr.equals(joCities[i].getString("name"))){
									jaHospitals = joCities[i].getJSONArray("hospitals");
									joHospitals = new JSONObject[jaHospitals.length()];
									nameHospitals = new String[joHospitals.length];
									for (int j=0; j<jaHospitals.length(); j++){
										joHospitals[j] = jaHospitals.getJSONObject(j);
										nameHospitals[j] = joHospitals[j].getString("name");
										Log.v(TAG, "nameHospitals:"+nameHospitals[j]);
									}

								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					initRightLayout("Yiyuan");
				}

			});
		}else if(str.equals("Yiyuan")){
			Log.v(TAG, "Yiyuan");
			testTextView.setText("医院");
			SimpleAdapter adapter = new SimpleAdapter(this, getYiyuanData(),
					R.layout.right_drawer_list_item, new String[]{"name"},
					new int[]{R.id.item_tv});
			rightDrawerListView.setAdapter(adapter);
			rightDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> yiyuanParent, View yiyuanView,
										int yiyuanPosition, long yiyuanId) {
					// TODO Auto-generated method stub
					String yyStr = yiyuanParent.getItemAtPosition(yiyuanPosition).toString();
					yyStr = yyStr.substring(6, yyStr.length()-1);
					if(yyStr.equals(nameHospitals[yiyuanPosition])){
						if(yyStr.equals(myApp.getYiyuanString())){
							//	return;
							myApp.setYiyuanString(yyStr);
						}else{
							myApp.setYiyuanString(yyStr);
							myApp.setKeshiString("科室");
							myApp.setYishengString("医生");
						}


						slideFragment.keshiTextView.setText(myApp.getKeshiString());
						slideFragment.yishengTextView.setText(myApp.getYishengString());
						myApp.setYiyuanString(yyStr);
						slideFragment.yiyuanTextView.setText(yyStr);
						for(int i=0; i<joHospitals.length; i++){
							try {
								if(yyStr.equals(joHospitals[i].getString("name"))){
									jaDepartments = joHospitals[i].getJSONArray("departments");
									joDepartments = new JSONObject[jaDepartments.length()];
									nameDepartments = new String[joDepartments.length];
									idDepartments = new int[joDepartments.length];
									for(int j=0; j<jaDepartments.length(); j++){
										joDepartments[j] = jaDepartments.getJSONObject(j);
										nameDepartments[j] = joDepartments[j].getString("name");
										idDepartments[j] = joDepartments[j].getInt("id");
										Log.v(TAG, "nameDepartments:"+nameDepartments[j]);
									}

								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					initRightLayout("KeShi");
				}

			});
		}else if(str.equals("KeShi")){
			Log.v(TAG, "KeShi");
			testTextView.setText("科室");
			SimpleAdapter adapter = new SimpleAdapter(this, getKeShiData(),
					R.layout.right_drawer_list_item, new String[]{"name"},
					new int[]{R.id.item_tv});
			rightDrawerListView.setAdapter(adapter);
			rightDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> keshiParent, View keshiView,
										int keshiPosition, long keshiId) {
					// TODO Auto-generated method stub
					String keshiStr = keshiParent.getItemAtPosition(keshiPosition).toString();
					Log.v(TAG, "keshiStr:"+keshiStr);
					keshiStr = keshiStr.substring(6, keshiStr.length()-1);
					Log.v(TAG, "keshiStr:"+keshiStr);
					if(keshiStr.equals(nameDepartments[keshiPosition])){
						if(keshiStr.equals(myApp.getKeshiString())){
							//	return;
							myApp.setKeshiString(keshiStr);
						}else{
							myApp.setKeshiString(keshiStr);
							myApp.setYishengString("医生");
						}
						Log.v(TAG, "keshiStr:"+keshiStr);
						myApp.setKeshiString(keshiStr);
						slideFragment.keshiTextView.setText(keshiStr);
						slideFragment.yishengTextView.setText(myApp.getYishengString());
						String departmentId = String.valueOf(idDepartments[keshiPosition]);
						httpUrl = http + getDoctorsUrl + departmentId;
						Log.v(TAG, "httpUrl:"+httpUrl);
						//HttpPost post = HttpUtil.getPost(httpUrl, null);

						try {
							joRev = HttpUtil.getResultForHttpGet(httpUrl);
							Log.i(TAG,"joRev "+joRev);
							showStr = joRev.getString("result");
							resultStr = joRev.getString("resultMessage");

						} catch (JSONException e) {
							Log.i(TAG,"joRev JSONException");
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ClientProtocolException e) {
							Log.i(TAG,"joRev ClientProtocolException");
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							Log.i(TAG,"joRev IOException");
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (showStr.equals("200")) {
							Log.d(TAG,  "resultStr:"+resultStr);
							Log.d(TAG, "joRev:"+joRev.toString());
							try {
								jaDoctors = joRev.getJSONArray("value");
								joDoctors = new JSONObject[jaDoctors.length()];
								nameDoctors = new String[jaDoctors.length()];
								nameDoctorIDs = new String[jaDoctors.length()];
								for(int i=0; i<jaDoctors.length(); i++){
									joDoctors[i] = jaDoctors.getJSONObject(i);
									nameDoctors[i] = joDoctors[i].getString("name");
									nameDoctorIDs[i] = joDoctors[i].getString("userId");
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					initRightLayout("YiSheng");
				}

			});
		}else if(str.equals("YiSheng")){
			Log.v(TAG, "YiSheng");
			testTextView.setText("医生");
			SimpleAdapter adapter = new SimpleAdapter(this, getYiShengData(),
					R.layout.right_drawer_list_item, new String[]{"name"},
					new int[]{R.id.item_tv});
			rightDrawerListView.setAdapter(adapter);
			rightDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> yishengParent, View yishengView,
										int yishengPosition, long yishengId) {
					// TODO Auto-generated method stub
					String yishengStr = yishengParent.getItemAtPosition(yishengPosition).toString();
					Log.v(TAG, "yishengStr:"+yishengStr);
					yishengStr = yishengStr.substring(6, yishengStr.length()-1);
					Log.v(TAG, "yishengStr:"+yishengStr);
					if(yishengStr.equals(nameDoctors[yishengPosition])){
						Log.v(TAG, "yishengStr:"+yishengStr);
						myApp.setYishengString(yishengStr);
						myApp.setDoctorIDString(nameDoctorIDs[yishengPosition]);
						joDoctor = joDoctors[yishengPosition];
						Log.v(TAG,"joDoctor:"+joDoctor);
						slideFragment.yishengTextView.setText(yishengStr);
						rightRelativeLayout.setVisibility(View.GONE);
						drawerLayout.closeDrawer(rightRelativeLayout);
					}
				}

			});
		}
		rightRelativeLayout.addView(view);
	}
	/*
	private void setData(){
		Log.i(TAG, "setData joDoctor " + joDoctor);
		if(joDoctor != null){
			HashMap<String,String> map = new HashMap<String, String>();
			try {
				int departmentId =  joDoctor.getInt("departmentId");
				//通过departmentID来获取到医院、科室、职称等信息
				for (int j = 0; j < jsonList.size(); j++) {
					if(departmentId == jsonList.get(j).getLong("department_id")){
						map.put("hospital", jsonList.get(j).getString("hospital_name"));
						map.put("keshi", jsonList.get(j).getString("department_name"));
						map.put("zhicheng", jsonList.get(j).getString("jobTitle_name"));
					}
				}
				map.put("name",joDoctor.getString("name"));
//			map.put("hospital", slideFragment.yiyuanTextView.getText().toString());
//			map.put("keshi", slideFragment.keshiTextView.getText().toString());
//			map.put("zhicheng", joDoctor.getString("jobTitle_name"));
				map.put("zhuanchang",  joDoctor.getString("skill"));
				map.put("userId",  joDoctor.getString("userId"));
//				data.add(map);
//				findAdapter.notifyDataSetChanged();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else{
//			data = new ArrayList<HashMap<String, String>>();
//			findAdapter.notifyDataSetChanged();
		}

	}
	*/
	//切换右侧抽屉的显示/隐藏
	private void initEvent() {
		drawerbar = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_launcher, R.string.open, R.string.close) {
			@Override
			public void onDrawerOpened(View drawerView) {

				super.onDrawerOpened(drawerView);
			}

			@Override
			public void onDrawerClosed(View drawerView) {

				super.onDrawerClosed(drawerView);
			}
		};
		drawerLayout.setDrawerListener(drawerbar);
	}


	public void openRightLayout(String str) {
		initView(str);
		if (drawerLayout.isDrawerOpen(rightRelativeLayout)) {
			drawerLayout.closeDrawer(rightRelativeLayout);
		} else {
			drawerLayout.openDrawer(rightRelativeLayout);
		}
	}

	public List<HashMap<String,String>> getShengData(){
		List<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
		for(int i=0 ; i<nameProvinces.length; i++){
			HashMap<String, String> itemHashMap = new HashMap<String, String>();
			itemHashMap.put("name", nameProvinces[i]);
			data.add(itemHashMap);
		}
		return data;

	}

	public List<HashMap<String,String>> getShiData(){
		List<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
		for(int i=0 ; i<nameCities.length; i++){
			HashMap<String, String> itemHashMap = new HashMap<String, String>();
			itemHashMap.put("name", nameCities[i]);
			data.add(itemHashMap);
		}
		return data;

	}

	public List<HashMap<String,String>> getYiyuanData(){
		List<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
		if(nameHospitals == null){
			showToast("请按顺序选择后确定医生");

		}else {
			for(int i=0 ; i<nameHospitals.length; i++){
				HashMap<String, String> itemHashMap = new HashMap<String, String>();
				itemHashMap.put("name", nameHospitals[i]);
				data.add(itemHashMap);
			}
		}

		return data;

	}
	public void showToast(String msg){
		//Looper.prepare();
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
		// Looper.loop();
	}
	public List<HashMap<String,String>> getKeShiData(){
		List<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
		if(nameDepartments == null){
			showToast("请按顺序选择");
		}else{
			for(int i=0 ; i<nameDepartments.length; i++){
				HashMap<String, String> itemHashMap = new HashMap<String, String>();
				itemHashMap.put("name", nameDepartments[i]);
				//itemHashMap.put("id", String.valueOf(idDepartments[i]));
				data.add(itemHashMap);
			}
		}
		return data;

	}

	public List<HashMap<String,String>> getYiShengData(){
		List<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
		if(nameDoctors == null){
			showToast("请按顺序选择");
		}else{
			for(int i=0 ; i<nameDoctors.length; i++){
				HashMap<String, String> itemHashMap = new HashMap<String, String>();
				itemHashMap.put("name", nameDoctors[i]);
				data.add(itemHashMap);
			}
		}
		return data;

	}


	//预约医生
	public void yuyue(){
		//yuyueButton = (Button)findViewById(R.id.yuyue_btn);
		//slideFragment.yuyueButton.setOnClickListener(new OnClickListener() {

		//	@Override
		//	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String doctorId = myApp.getDoctorIDString();
		Log.i(TAG,"doctorID "+doctorId);
		if(doctorId!=null){
			pDialog = new ProgressDialog(SeeDoctorActivity.this);
			pDialog.setTitle("查找中");
			pDialog.setMessage("请稍等......");
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.show();
			httpUrl = http+getScheUrlString+doctorId+inprogressString;
			//httpUrl = http+getScheUrlString+doctorId;
			Log.v(TAG, "getSche:"+httpUrl);
			try {
				joRev = HttpUtil.getResultForHttpGet(httpUrl);
				Log.v(TAG, "get doctor:"+joRev.toString());
				pDialog.dismiss();

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent intent = new Intent(SeeDoctorActivity.this, YuYueGuanLiActivity.class);
//			Intent intent = new Intent(SeeDoctorActivity.this, ConsultationActivity.class);
			myApp.setJoDoctor(joDoctor);
			startActivity(intent);

		}else{
			//pDialog.dismiss();
			Toast.makeText(getApplicationContext(), "请先确定要挂号的医生", Toast.LENGTH_SHORT).show();

		}

		//	}
		//});

	}

}