package com.housekeeper.ar.healthhousekeeper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.picture.PhotoUtils;
import com.housekeeper.ar.healthhousekeeper.picture.SelectPictureActivity;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegisterActivity extends BaseActivity {
	public static final String PREFS_NAME = "MyPrefsFile";
	private MyApp myApp;
	private SaveCache saveCache = new SaveCache(AppContext.get());
	private EditText  psdaET, nameET, idET;
//    private EditText meetplacET;
	private TextView usernameET, psdET;
	private EditText  mailET, telET;
	private Button regBtn, photoBtn, signBtn;
	private static String photoAddr, signAddr;
	private Spinner year, month, day,  sex, sheng, shi, yy;
//	private TextView departmentTypeTv;
	private EditText workIdEditText;
	private ImageView photo;
	private Bitmap bitmap;
	private String id;
	private static String birthdayStr, yearStr, monthStr, dayStr, sexStr, shengStr, shiStr, yyStr;
	//private  static String ksStr, ksflStr, proStr;
	private JSONObject params = new JSONObject();
	private String TAG = "RegisterActivity";
	static String http ;//="http://192.168.1.54:8080/";
	static String regUrl = "shlc/doctor/user";
	static String httpUrl;
	private String showStr = "";
	private String resultStr = "";
	private String readFileCache;
	private JSONObject joReadFileCache;
	private String[] arrProvinces, citiesSpinner, hospitalSpinner, nameProvinces, nameCities,
			nameHospitals, nameDepartments, namePros;
	private int[] idDepartments, idJobTitles;
	//private int idDepartmentInt, idJobTitlesInt;
	private static String[][] arrCities;
	private static String[][][] arrHospitals;
	private static ArrayAdapter<String> shiAdapter, hospitalAdapter, departmentAdapter;
	private static JSONObject[] joProvinces;
	private static JSONObject[] joCities;
	private static JSONObject[] joHospitals, joDepartments, joPros;
	private static JSONObject joDepartmentType;
	private static JSONArray jaProvinces, jaCities, jaHospitals, jaDepartments, jaPros;
	ProgressDialog pDialog;
//	private Button prePageBtn;
//	private Button nextPageBtn;
	private RelativeLayout firstPageLayout;
//	private RelativeLayout secondPageLayout;
	private ToastCommom toastCommom;
	private LinearLayout yanzhengmaLayout;
	private Button yanzhengmaBtn;
	private Button yanzhengmaRegBtn;
	private TextView titleTextView;
	private EditText yanzhengmaZhangHuEditText;
	private EditText yanzhengmaPsdEditText;
	private static final int REQUEST_PICK = 0;
	private int picKind;
	private ArrayList<String>selectedPicture = new ArrayList<String>();
    String upload_pic_url = "shlc/photo";
    private static final String IMAGE_FILE_NAME = "tempImage.jpg";

	private Button backBtn;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
       /*
        if (savedInstanceState != null) {	
        	String zcpsStr = savedInstanceState.getString("zcps");
        	zcpsET.setText(zcpsStr);
		}
		*/
		SysApplication.getInstance().addActivity(this);
		setContentView(R.layout.activity_register);
		toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(RegisterActivity.this);
		myApp = (MyApp)getApplication();
		http = myApp.getHttp();

		backBtn = (Button)findViewById(R.id.back_btn);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		//APP生成
		Log.i(TAG,"filename oncreate ");
		String fileName = myApp.getJoStartPath();

		Log.i(TAG, "fileName:"+fileName);
		if(fileName != null){
			try {
				readFileCache = saveCache.read(fileName);
				joReadFileCache = new JSONObject(readFileCache);
				String result = joReadFileCache.getString("result");
				String resultMessage = joReadFileCache.getString("resultMessage");
				//取出全部value
				JSONObject joValue = joReadFileCache.getJSONObject("value");
				//取出provinces数组
				jaProvinces = joValue.getJSONArray("provinces");
				joProvinces = new JSONObject[jaProvinces.length()];
				nameProvinces = new String[jaProvinces.length()];
				//nameProvinces[0] = "-请选择省份-";
				for(int i=0; i<jaProvinces.length(); i++){
					joProvinces[i] = jaProvinces.getJSONObject(i);
					nameProvinces[i] = joProvinces[i].getString("name");
					Log.v(TAG, "nameProvinces:"+nameProvinces[i]);
				}

				jaPros = joValue.getJSONArray("jobTitles");
				joPros = new JSONObject[jaPros.length()];
				namePros = new String[jaPros.length()];
				idJobTitles = new int[namePros.length];
				for(int i=0; i<jaPros.length(); i++){
					joPros[i] = jaPros.getJSONObject(i);
					namePros[i] = joPros[i].getString("name");
					idJobTitles[i] = joPros[i].getInt("id");
					Log.v(TAG, "namePros:"+namePros[i]);
					Log.v(TAG, "idJobTitles:"+idJobTitles[i]);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
			}
		}else{
			nameProvinces = new String[]{""};
			nameCities  = new String[]{""};
			nameHospitals= new String[]{""};
			nameDepartments= new String[]{""};
			namePros= new String[]{""};
		}



//        prePageBtn = (Button)findViewById(R.id.pre_btn);
		firstPageLayout = (RelativeLayout)findViewById(R.id.first_layout);
//		nextPageBtn = (Button)findViewById(R.id.next_btn);
//		secondPageLayout = (RelativeLayout)findViewById(R.id.second_layout);
		yanzhengmaLayout = (LinearLayout)findViewById(R.id.yanzhengma_layout);
		yanzhengmaRegBtn = (Button)findViewById(R.id.yanzhengma_regbtn);
		titleTextView = (TextView)findViewById(R.id.title);
		yanzhengmaPsdEditText = (EditText)findViewById(R.id.password_et);
		yanzhengmaZhangHuEditText = (EditText)findViewById(R.id.zhanghu_et);

		workIdEditText = (EditText)findViewById(R.id.work_id_et);

		usernameET = (TextView)findViewById(R.id.username_et);
		psdET = (TextView)findViewById(R.id.psd_et);
		psdaET = (EditText)findViewById(R.id.psd_again_et);
		nameET = (EditText)findViewById(R.id.name_et);
		idET = (EditText)findViewById(R.id.id_et);
//		yszET = (EditText)findViewById(R.id.zyys_et);
//		zczET = (EditText)findViewById(R.id.yszc_et);
//		zcpsET = (EditText)findViewById(R.id.zcps_et);
//		skillET = (EditText)findViewById(R.id.zs_et);
//		detailET = (EditText)findViewById(R.id.xxxx_et);
		telET = (EditText)findViewById(R.id.tel_et);
		mailET = (EditText)findViewById(R.id.mail_et);
		photo = (ImageView)findViewById(R.id.photo_image);
//        meetplacET = (EditText)findViewById(R.id.address_et);
		regBtn = (Button)findViewById(R.id.regbtn);
		photoBtn = (Button)findViewById(R.id.photoBtn);
//		zcpsBtn = (Button)findViewById(R.id.zcps_btn);
//		zyysBtn = (Button)findViewById(R.id.zyys_btn);
//		yszcBtn = (Button)findViewById(R.id.yszc_btn);
		signBtn = (Button)findViewById(R.id.sign_btn);
		//birthday
		birthdayStr = yearStr+"-"+monthStr+"-"+dayStr;
		year = (Spinner)findViewById(R.id.yearspinner);
		month = (Spinner)findViewById(R.id.monthspinner);
		day = (Spinner)findViewById(R.id.dayspinner);
		//sex
		sex = (Spinner)findViewById(R.id.sex_spinner);
		//province-city-hospital
		sheng = (Spinner)findViewById(R.id.sheng_spinner);
		shi = (Spinner)findViewById(R.id.shi_spinner);
		yy = (Spinner)findViewById(R.id.yy_spinner);
		//职称
//		pro = (Spinner)findViewById(R.id.pro_spinner);
		//科室-科室分类
//		ks = (Spinner)findViewById(R.id.ks_spinner);
		//ksfl = (Spinner)findViewById(R.id.ksfl_spinner);
//		departmentTypeTv = (TextView)findViewById(R.id.ksfl_tv);
		final List<String> yearList = getYearData();
		final List<String> monthList = getMonthData();
		final List<String> dayList = getDayData();

		yanzhengmaLayout.setVisibility(View.VISIBLE);
		firstPageLayout.setVisibility(View.GONE);
		yanzhengmaRegBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				yanzhengmaLayout.setVisibility(View.GONE);
				firstPageLayout.setVisibility(View.VISIBLE);
				titleTextView.setText("完 善 个 人 资 料");
				usernameET.setText(yanzhengmaZhangHuEditText.getText().toString());
				psdET.setText(yanzhengmaPsdEditText.getText().toString());
				psdaET.setText(yanzhengmaPsdEditText.getText().toString());
				telET.setText(yanzhengmaZhangHuEditText.getText().toString());
			}
		});
//		final String yearArray[] = new String[yearList.size()];
//		if(yearList != null){
//			for(int i = 0 ;i<yearList.size();i++){
//				yearArray[i] = yearList.get(i);
//			}
//		}


//		// 声明一个ArrayAdapter用于存放简单数据
//		ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>
//				(RegisterActivity.this, android.R.layout.simple_spinner_item,getYearData());
//		ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>
//				(RegisterActivity.this, android.R.layout.simple_spinner_item,getMonthData());
//		ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>
//				(RegisterActivity.this, android.R.layout.simple_spinner_item,getDayData());
//
//		final List<String> sexList = getSexData();
//		ArrayAdapter<String> sexAdapter = new ArrayAdapter<String>
//				(RegisterActivity.this, android.R.layout.simple_spinner_item,getSexData());

		// 声明一个ArrayAdapter用于存放简单数据
		ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>
				(RegisterActivity.this, R.layout.spinner_item,yearList){
			@Override
			public View getDropDownView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.spinner_item_layout,
						null);
				TextView label = (TextView) view
						.findViewById(R.id.spinner_item_label);

				label.setText(yearList.get(position));

				return view;
				//return super.getDropDownView(position, convertView, parent);
			}
		};
		ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>
				(RegisterActivity.this, R.layout.spinner_item,getMonthData()){
			@Override
			public View getDropDownView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.spinner_item_layout,
						null);
				TextView label = (TextView) view
						.findViewById(R.id.spinner_item_label);

				label.setText(monthList.get(position));

				return view;
				//return super.getDropDownView(position, convertView, parent);
			}
		};
		ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>
				(RegisterActivity.this, R.layout.spinner_item,getDayData()){
			@Override
			public View getDropDownView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.spinner_item_layout,
						null);
				TextView label = (TextView) view
						.findViewById(R.id.spinner_item_label);

				label.setText(dayList.get(position));

				return view;
				//return super.getDropDownView(position, convertView, parent);
			}
		};

		final List<String> sexList = getSexData();
		ArrayAdapter<String> sexAdapter = new ArrayAdapter<String>
				(RegisterActivity.this, R.layout.spinner_item,getSexData()){
			@Override
			public View getDropDownView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.spinner_item_layout,
						null);
				TextView label = (TextView) view
						.findViewById(R.id.spinner_item_label);

				label.setText(sexList.get(position));

				return view;
				//return super.getDropDownView(position, convertView, parent);
			}
		};

		//设置样式
		yearAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
		//设置样式
		monthAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
		//设置样式
		dayAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
		//设置样式
		sexAdapter.setDropDownViewResource(R.layout.spinner_item_layout);

		year.setAdapter(yearAdapter);
		month.setAdapter(monthAdapter);
		day.setAdapter(dayAdapter);
		sex.setAdapter(sexAdapter);

		year.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {

				Log.i(TAG, "year touch ");
				closeSoftKeyboard();
				return false;
			}
		});
		month.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {

				Log.i(TAG, "year touch ");
				closeSoftKeyboard();
				return false;
			}
		});
		day.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {

				Log.i(TAG, "year touch ");
				closeSoftKeyboard();
				return false;
			}
		});
		sex.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {

				Log.i(TAG, "year touch ");
				closeSoftKeyboard();
				return false;
			}
		});

		getShared();
		setSpinner();

		regBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(usernameET.getText().toString().equals("")){
//					Toast.makeText(RegisterActivity.this, "用户名为空",
//							Toast.LENGTH_LONG).show();
					toastCommom.ToastShow(RegisterActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "用户名为空");
					return ;
				}
				if(psdET.getText().toString().equals("")){
//					Toast.makeText(RegisterActivity.this, "密码为空",
//							Toast.LENGTH_LONG).show();
					toastCommom.ToastShow(RegisterActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "密码为空");
					return ;
				}
				if(psdaET.getText().toString().equals("")){
//					Toast.makeText(RegisterActivity.this, "确认密码为空",
//							Toast.LENGTH_LONG).show();
					toastCommom.ToastShow(RegisterActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "确认密码为空");
					return ;
				}
				if(!psdaET.getText().toString().equals(psdET.getText().toString())){
					Log.v(TAG,"两次密码输入不一致");
//					Toast.makeText(RegisterActivity.this, "两次密码输入不一致",
//							Toast.LENGTH_LONG).show();
					toastCommom.ToastShow(RegisterActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "两次密码输入不一致");
					return ;
				}
				if(nameET.getText().toString().equals("")){
//					Toast.makeText(RegisterActivity.this, "姓名为空",
//							Toast.LENGTH_LONG).show();
					toastCommom.ToastShow(RegisterActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "姓名为空");
					return ;
				}
				pDialog = new ProgressDialog(RegisterActivity.this);
				pDialog.setTitle("注册中");
				pDialog.setMessage("请稍等......");
				pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				pDialog.show();
				new RegisterThread().run();
			}
		});

		photoBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				picKind = 0;
				Intent intent = new Intent(RegisterActivity.this, SelectPictureActivity.class);
				intent.putExtra("from","register");
				startActivityForResult(intent, REQUEST_PICK);

				//RegisterActivity.this.finish();
			}
		});

		signBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				picKind = 1;
				Intent intent = new Intent(RegisterActivity.this, SelectPictureActivity.class);
				intent.putExtra("from","register");
				startActivityForResult(intent, REQUEST_PICK);
			}
		});



	}
	@SuppressWarnings("unchecked")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == -1) {
			selectedPicture = (ArrayList<String>) data
					.getSerializableExtra(SelectPictureActivity.INTENT_SELECTED_PICTURE);
			Log.i(TAG, "SelectedPicture:" + selectedPicture.toString());
			if(selectedPicture.size()>0){
				//只取第一张
				for(int i=0; i<1; i++){
					Bitmap bitmap = PhotoUtils.getimage(selectedPicture.get(i));
					File file = null;
					JSONObject joRev = new JSONObject();
                    httpUrl = http+ upload_pic_url;
                    Log.i(TAG, "post reg image url:"+ httpUrl);
                    if(PhotoUtils.saveBitmap2file(bitmap)){
                        file = new File(Environment.getExternalStorageDirectory(),IMAGE_FILE_NAME);
                        joRev = HttpUtil.postImage(file, httpUrl);
                        Log.i(TAG, "post image:"+joRev.toString());
                        try {
                            if(joRev.getLong("result")==200){
                                String returnAddr = joRev.getString("value");
                                switch (picKind){
                                    case 0:
                                        //头像
                                        Bitmap head_bitmap = bitmap;
                                        photo.setImageBitmap(head_bitmap);
                                        photoAddr = returnAddr;
                                        break;
                                    case 1:

                                        break;
//                                    case 2:
//                                        zyysAddr = returnAddr;
//                                        break;
//                                    case 3:
//                                        yszcAddr = returnAddr;
//                                        break;
//                                    case 4:
//                                        zcpsAddr = returnAddr;
//                                        break;

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
					}
				}
			}
		}
	}
	private void getShared(){
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//		String zcpsStr = settings.getString("zcpsStr", "");
//		if(!"".equals(zcpsStr) && zcpsStr!=null){
//			zcpsET.setText(zcpsStr);
//		}
		String psdStr = settings.getString("psdStr", "");
		if(!"".equals(psdStr) && psdStr!=null){
			psdET.setText(psdStr);
		}
		String usernameStr = settings.getString("usernameStr", "");
		if(!"".equals(usernameStr) && usernameStr!=null){
			usernameET.setText(usernameStr);
		}
		String nameStr = settings.getString("nameStr", "");
		if(!"".equals(nameStr) && nameStr!=null){
			nameET.setText(nameStr);
		}
		String idStr = settings.getString("idStr", "");
		if(!"".equals(idStr) && idStr!=null){
			idET.setText(idStr);
		}
//		String zyysStr = settings.getString("zyysStr", "");
//		if(!"".equals(zyysStr) && zyysStr!=null){
//			yszET.setText(zyysStr);
//		}
//		String yszcStr = settings.getString("yszcStr", "");
//		if(!"".equals(yszcStr) && yszcStr!=null){
//			zczET.setText(yszcStr);
//		}
		String emailStr = settings.getString("emailStr", "");
		if(!"".equals(emailStr) && emailStr!=null){
			mailET.setText(emailStr);
		}
		String telStr = settings.getString("telStr", "");
		if(!"".equals(telStr) && telStr!=null){
			telET.setText(telStr);
		}
		String workIdStr = settings.getString("workId", "");
		if(!"".equals(workIdStr) && workIdStr!=null){
			workIdEditText.setText(workIdStr);
		}

//		String zcpsAddrStr = settings.getString("zcpsAddr", "");
//		if(!"".equals(zcpsAddrStr) && zcpsAddrStr!=null){
//			zcpsAddr = zcpsAddrStr;
//		}
//		String zyysAddrStr = settings.getString("zyysAddr", "");
//		if(!"".equals(zyysAddrStr) && zyysAddrStr!=null){
//			zyysAddr = zyysAddrStr;
//		}
//		String yszcAddrStr = settings.getString("yszcAddr", "");
//		if(!"".equals(yszcAddrStr) && yszcAddrStr!=null){
//			yszcAddr = yszcAddrStr;
//		}
		String photoAddrStr = settings.getString("photoAddr", "");
		if(!"".equals(photoAddrStr) && photoAddrStr!=null){
			photoAddr = photoAddrStr;
		}
		Log.v(TAG, "photoAddr "+photoAddr);

	}

	private void closeSoftKeyboard(){

		/**隐藏软键盘**/
		View view = getWindow().peekDecorView();
		if (view != null) {
			InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}
	class RegisterThread extends Thread{
		public void run() {
			/*
			if (android.os.Build.VERSION.SDK_INT > 9) {
			    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			    StrictMode.setThreadPolicy(policy);
			}
			*/
			try {
				if(!psdaET.getText().toString().equals(psdET.getText().toString())){
					Log.v(TAG,"两次密码输入不一致");
//					Toast.makeText(RegisterActivity.this, "两次密码输入不一致",
//							Toast.LENGTH_LONG).show();
					toastCommom.ToastShow(RegisterActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "两次密码输入不一致");
					return ;
				}
				if(TimeStamp.isChinese(usernameET.getText().toString())){
					toastCommom.ToastShow(RegisterActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "不能使用中文作为用户名");
				}
				params.put("userId", usernameET.getText().toString());//str
				params.put("password", psdET.getText().toString());//str
				params.put("name", nameET.getText().toString());//str
				params.put("sex", sexStr);//str
				params.put("birthday", birthdayStr);//str
				params.put("identity", idET.getText().toString());//str
//				params.put("jobTitleId", idJobTitlesInt);//int
//				params.put("licenseNO", yszET.getText().toString());//str
//				params.put("licenseNOPic", zyysAddr);//str
//				params.put("certificateNo", zczET.getText().toString());//str
//				params.put("certificateNoPic", yszcAddr);//str
//				params.put("jobTitleNoPic", zcpsAddr);//str
//				params.put("jobTitleNo", zcpsET.getText().toString());//str
				params.put("province", shengStr);
				params.put("city", shiStr);
				params.put("hospital", yyStr);
				params.put("phone", telET.getText().toString());//str
				params.put("email", mailET.getText().toString());//str
				params.put("workId", workIdEditText.getText().toString());//str
//				params.put("departmentId", idDepartmentInt);//int
				//params.put("departmentType", ksflStr);
//				params.put("skill", skillET.getText().toString());//str
//				params.put("description", detailET.getText().toString());//str
				//params.put("titlePicture", "picture Url");
				params.put("photoPic", photoAddr);//str
//                params.put("meetPlace", meetplacET.getText().toString());
				Log.d(TAG, "httpUrl:"+http+regUrl);//str
				Log.d(TAG, "reg params:"+params.toString());
				httpUrl = http + regUrl;
				HttpPost post = HttpUtil.getPost(httpUrl, params);
				JSONObject joRev = HttpUtil.getString(post, 3);
				showStr = joRev.getString("result");
				resultStr = joRev.getString("resultMessage");
			} catch (JSONException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
			}finally{
				pDialog.dismiss();
			}
			//handler.sendMessage(msg);
			if(showStr.equals("200")){
				Log.d(TAG, "resultStr:"+resultStr);
//				Toast.makeText(RegisterActivity.this, "已注册成功！",
//						Toast.LENGTH_LONG).show();
				toastCommom.ToastShow(RegisterActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "已注册成功！");
				Intent intent = new Intent(RegisterActivity.this , LoginActivity.class);
				startActivity(intent);
				pDialog.dismiss();
				RegisterActivity.this.finish();
			}else{
//				Toast.makeText(RegisterActivity.this, resultStr,
//						Toast.LENGTH_LONG).show();
				toastCommom.ToastShow(RegisterActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), resultStr);
			}
		}
	}
	@Override
	protected void onStop() {
		super.onStop();
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
//		editor.putString("zcpsStr", zcpsET.getText().toString());
		editor.putString("psdStr", psdET.getText().toString());
		editor.putString("usernameStr", usernameET.getText().toString());
		editor.putString("nameStr", nameET.getText().toString());
		editor.putString("idStr", idET.getText().toString());
		editor.putString("workId",workIdEditText.getText().toString());
//		editor.putString("zyysStr", yszET.getText().toString());
//		editor.putString("yszcStr", zczET.getText().toString());
		editor.putString("emailStr", mailET.getText().toString());
		editor.putString("telStr", telET.getText().toString());
//		editor.putString("zsStr", skillET.getText().toString());
//		editor.putString("xxxxStr", detailET.getText().toString());
//		editor.putString("zcpsAddr", zcpsAddr);
//		editor.putString("zyysAddr", zyysAddr);
//		editor.putString("yszcAddr", yszcAddr);
		editor.putString("photoAddr", photoAddr);
		editor.commit();
		//Log.v(TAG, "zcpsStrStop:"+zcpsStr);
		//Log.e(TAG, "start onStop~~~");  
	}
	/*
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		//儲存UI狀態到bundle中
		outState.putString("zcps", zcpsET.getText().toString());
		//outState.putInt("int", 1);
		//outState.putBoolean("boolean", true);
	}
	*/
	/*
	protected void onPause(){
		super.onPause();
		Log.v(TAG, "zcpsStrPause:"+zcpsStr);
		zcpsStr = zcpsET.getText().toString();
		Log.e(TAG, "start onPause~~~"); 
	}
	protected void onResume() {  
        super.onResume();  
        Log.v(TAG, "zcpsStrResume:"+zcpsStr);
        zcpsET.setText(zcpsStr);
        Log.e(TAG, "start onResume~~~");  
    }  
	protected void onRestart() {  
        super.onRestart();  
        Log.v(TAG, "zcpsStrRestart:"+zcpsStr);
        //mEditText.setText(mString);  
        Log.e(TAG, "start onRestart~~~");  
    }  
	

	@Override  
	protected void onDestroy() {  
	    super.onDestroy();  
	    Log.e(TAG, "start onDestroy~~~");  
	}  
	*/
	//setSpinner
	private void setSpinner(){
		Log.i(TAG, "namePros:" + namePros);
//		ArrayAdapter<String> proAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,namePros){
//			@Override
//			public View getDropDownView(int position, View convertView, ViewGroup parent) {
//				LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//				View view = inflater.inflate(R.layout.spinner_item_layout,
//						null);
//				TextView label = (TextView) view
//						.findViewById(R.id.spinner_item_label);
//
//				label.setText(namePros[position]);
//
//
//				return view;
//				//return super.getDropDownView(position, convertView, parent);
//			}
//		};
//
//		//设置样式
//		proAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
////		proAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		pro.setAdapter(proAdapter);
//		pro.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View view,
//									   int position, long id) {
//				// TODO Auto-generated method stub
//				proStr = parent.getItemAtPosition(position).toString();
//				Log.v(TAG, "selcet proStr:" + proStr);
//				if (proStr.equals(namePros[position])) {
//					idJobTitlesInt = idJobTitles[position];
//				}
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//				// TODO Auto-generated method stub
//
//			}
//
//		});
//
//		pro.setOnTouchListener(new View.OnTouchListener() {
//			@Override
//			public boolean onTouch(View view, MotionEvent motionEvent) {
//
//				Log.i(TAG, "year touch ");
//				closeSoftKeyboard();
//				return false;
//			}
//		});
		ArrayAdapter<String> shengAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item,nameProvinces){
			@Override
			public View getDropDownView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.spinner_item_layout,
						null);
				TextView label = (TextView) view
						.findViewById(R.id.spinner_item_label);

				label.setText(nameProvinces[position]);


				return view;
				//return super.getDropDownView(position, convertView, parent);
			}
		};

		//设置样式
		shengAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
		sheng.setAdapter(shengAdapter);
		sheng.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> shengparent, View shengview,
									   int shengposition, long shengid) {
				// TODO Auto-generated method stub
				myApp.setShengposition(shengposition);
				shengStr = shengparent.getItemAtPosition(shengposition).toString();
				Log.v(TAG, "shengStr:" + shengStr);

				if (!shengStr.equals("")&&shengStr.equals(nameProvinces[shengposition])) {
					Log.v(TAG, "position:" + shengposition);
					for (int i = 0; i < joProvinces.length; i++) {
						try {

							if (shengStr.equals(joProvinces[i].getString("name"))) {
								jaCities = joProvinces[i].getJSONArray("cities");
								joCities = new JSONObject[jaCities.length()];
								nameCities = new String[joCities.length];

								for (int j = 0; j < jaCities.length(); j++) {
									joCities[j] = jaCities.getJSONObject(j);
									nameCities[j] = joCities[j].getString("name");
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block

							e.printStackTrace();
						}
					}
				}
				sheng.setOnTouchListener(new View.OnTouchListener() {
					@Override
					public boolean onTouch(View view, MotionEvent motionEvent) {

						Log.i(TAG, "year touch ");
						closeSoftKeyboard();
						return false;
					}
				});
				shiAdapter = new ArrayAdapter<String>(RegisterActivity.this, R.layout.spinner_item, nameCities) {
					@Override
					public View getDropDownView(int position, View convertView, ViewGroup parent) {
						LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
						View view = inflater.inflate(R.layout.spinner_item_layout,
								null);
						TextView label = (TextView) view
								.findViewById(R.id.spinner_item_label);

						label.setText(nameCities[position]);


						return view;
						//return super.getDropDownView(position, convertView, parent);
					}
				};

				//设置样式
				shiAdapter.setDropDownViewResource(R.layout.spinner_item_layout);

//				shiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				shi.setAdapter(shiAdapter);

				shi.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> shiparent, View shiview,
											   int shiposition, long shiid) {
						// TODO Auto-generated method stub
						shiStr = shiparent.getItemAtPosition(shiposition).toString();
						Log.v(TAG, "shiStr:" + shiStr);
						if (!shiStr.equals("")&&shiStr.equals(nameCities[shiposition])) {
							for (int i = 0; i < joCities.length; i++) {
								try {
									if (shiStr.equals(joCities[i].getString("name"))) {
										jaHospitals = joCities[i].getJSONArray("hospitals");
										joHospitals = new JSONObject[jaHospitals.length()];
										nameHospitals = new String[joHospitals.length];
										for (int j = 0; j < jaHospitals.length(); j++) {
											joHospitals[j] = jaHospitals.getJSONObject(j);
											nameHospitals[j] = joHospitals[j].getString("name");
										}

									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block

									e.printStackTrace();
								}
							}
						}
						shi.setOnTouchListener(new View.OnTouchListener() {
							@Override
							public boolean onTouch(View view, MotionEvent motionEvent) {

								Log.i(TAG, "year touch ");
								closeSoftKeyboard();
								return false;
							}
						});
						hospitalAdapter = new ArrayAdapter<String>(RegisterActivity.this, R.layout.spinner_item, nameHospitals) {
							@Override
							public View getDropDownView(int position, View convertView, ViewGroup parent) {
								LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
								View view = inflater.inflate(R.layout.spinner_item_layout,
										null);
								TextView label = (TextView) view
										.findViewById(R.id.spinner_item_label);

								label.setText(nameHospitals[position]);


								return view;
								//return super.getDropDownView(position, convertView, parent);
							}
						};

						//设置样式
						hospitalAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
						yy.setAdapter(hospitalAdapter);

						yy.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> yyparent,
													   View yyview, int yyposition, long yyid) {
								// TODO Auto-generated method stub
								yyStr = yyparent.getItemAtPosition(yyposition).toString();
								if (!yyStr.equals("")&&yyStr.equals(nameHospitals[yyposition])) {
									for (int i = 0; i < joHospitals.length; i++) {
										try {
											if (yyStr.equals(joHospitals[i].getString("name"))) {
												jaDepartments = joHospitals[i].getJSONArray("departments");
												joDepartments = new JSONObject[jaDepartments.length()];
												nameDepartments = new String[joDepartments.length];
												idDepartments = new int[joDepartments.length];
												for (int j = 0; j < jaDepartments.length(); j++) {
													joDepartments[j] = jaDepartments.getJSONObject(j);
													nameDepartments[j] = joDepartments[j].getString("name");
													idDepartments[j] = joDepartments[j].getInt("id");

												}

											}
										} catch (JSONException e) {
											// TODO Auto-generated catch block

											e.printStackTrace();
										}
									}
								}
								yy.setOnTouchListener(new View.OnTouchListener() {
									@Override
									public boolean onTouch(View view, MotionEvent motionEvent) {

										Log.i(TAG, "year touch ");
										closeSoftKeyboard();
										return false;
									}
								});
//								departmentAdapter = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, nameDepartments) {
//									@Override
//									public View getDropDownView(int position, View convertView, ViewGroup parent) {
//										LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//										View view = inflater.inflate(R.layout.spinner_item_layout,
//												null);
//										TextView label = (TextView) view
//												.findViewById(R.id.spinner_item_label);
//
//										label.setText(nameDepartments[position]);
//
//
//										return view;
//										//return super.getDropDownView(position, convertView, parent);
//									}
//								};

								//设置样式
//								departmentAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
//								ks.setAdapter(departmentAdapter);
//								ks.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//									@Override
//									public void onItemSelected(
//											AdapterView<?> ksparent, View ksview,
//											int ksposition, long ksid) {
//										// TODO Auto-generated method stub
//										ksStr = ksparent.getItemAtPosition(ksposition).toString();
//										if (ksStr.equals(nameDepartments[ksposition])) {
//											try {
//												idDepartmentInt = idDepartments[ksposition];
//												joDepartmentType = joDepartments[ksposition].getJSONObject("departmentType");
//												departmentTypeTv.setText(joDepartmentType.getString("name"));
//											} catch (JSONException e) {
//												// TODO Auto-generated catch block
//
//												e.printStackTrace();
//											}
//										}
//									}
//
//									@Override
//									public void onNothingSelected(
//											AdapterView<?> arg0) {
//										// TODO Auto-generated method stub
//
//									}
//								});
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub

							}
						});
//						ks.setOnTouchListener(new View.OnTouchListener() {
//							@Override
//							public boolean onTouch(View view, MotionEvent motionEvent) {
//
//								Log.i(TAG, "year touch ");
//								closeSoftKeyboard();
//								return false;
//							}
//						});
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});


		year.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {
				// TODO Auto-generated method stub
				yearStr = parent.getItemAtPosition(position).toString();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub	
			}
		});
		month.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {
				// TODO Auto-generated method stub
				monthStr = parent.getItemAtPosition(position).toString();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub	
			}
		});
		day.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {
				// TODO Auto-generated method stub
				dayStr = parent.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub	
			}
		});
		sex.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {
				// TODO Auto-generated method stub
				sexStr = parent.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub	
			}
		});


	}
	//下拉菜单数据源
	private List<String> getYearData(){
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		List<String> dataList = new ArrayList<String>();
		for(int i=year; i>=1940; i--){
			dataList.add(String.valueOf(i));
		}
		return dataList;
	}

	private List<String> getMonthData() {
		List<String> dataList = new ArrayList<String>();
		for (int i = 1; i < 13; i++) {
			if (i < 10) {
				dataList.add("0" + String.valueOf(i));
			} else {
				dataList.add(String.valueOf(i));
			}

		}
		return dataList;
	}

	private List<String> getDayData(){
		List<String> dataList = new ArrayList<String>();
		for(int i=1; i<32; i++){
			if(i<10){
				dataList.add("0"+String.valueOf(i));
			}else{
				dataList.add(String.valueOf(i));
			}
//			dataList.add(String.valueOf(i));
		}
		return dataList;
	}
	private List<String> getSexData(){
		List<String> dataList = new ArrayList<String>();

		dataList.add("男");
		dataList.add("女");

		return dataList;
	}

	private List<String> getKsData(){
		List<String> dataList = new ArrayList<String>();

		dataList.add("皮肤科");
		dataList.add("内科");

		return dataList;
	}
	private List<String> getKsflData(){
		List<String> dataList = new ArrayList<String>();

		dataList.add("分类一");
		dataList.add("分类二");

		return dataList;
	}

}
