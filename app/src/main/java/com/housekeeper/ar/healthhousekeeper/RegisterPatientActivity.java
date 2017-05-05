package com.housekeeper.ar.healthhousekeeper;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RegisterPatientActivity extends BaseActivity {
	EditText nameEditText, psdEditText, psdAgainEditText, xingmingEditText, idEditText,
				mailEditText, phoneEditText, addressEditText;
	Spinner sexSpinner, yearSpinner, monthSpinner, daySpinner;
	Button registerButton;
	JSONObject params = new JSONObject();
	String sexStr, dayStr, monthStr, yearStr, birthdayStr;
	private String TAG = "RegisterPatientActivity";
	static String http ;//="http://192.168.1.54:8080/";
	static String regUrl = "shlc/patient/user";
	static String httpUrl;
	private String showStr = "";
	private String resultStr = "";

	ProgressDialog pDialog;
	private FrameLayout backLayout;
	private LinearLayout yanzhengmaLayout;
	private RelativeLayout wanshanLayout;
	private Button yanzhengmaRegBtn;
	private TextView titleTextView;

	private Button backBtn;
	private ToastCommom toastCommom;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_register_patient);

		SysApplication.getInstance().addActivity(this);
		toastCommom = ToastCommom.createToastConfig();
		MyActivityManager.pushOneActivity(RegisterPatientActivity.this);
		toastCommom = ToastCommom.createToastConfig();

		backBtn = (Button)findViewById(R.id.back_btn1);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		LinearLayout rLayout = (LinearLayout)findViewById(R.id.register_layout);
		rLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
	            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
	            		InputMethodManager.HIDE_NOT_ALWAYS);
			}
		});
		yanzhengmaLayout = (LinearLayout)findViewById(R.id.yanzhengma_layout);
		wanshanLayout = (RelativeLayout)findViewById(R.id.second_page_layout);
		yanzhengmaRegBtn = (Button)findViewById(R.id.yanzhengma_regbtn);
		titleTextView = (TextView)findViewById(R.id.title);

		yanzhengmaLayout.setVisibility(View.VISIBLE);
		wanshanLayout.setVisibility(View.GONE);
		yanzhengmaRegBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				yanzhengmaLayout.setVisibility(View.GONE);
				wanshanLayout.setVisibility(View.VISIBLE);
				titleTextView.setText("完 善 患 者 信 息");
			}
		});

		
		nameEditText = (EditText)findViewById(R.id.name_et);
		psdEditText = (EditText)findViewById(R.id.psd_et);
		psdAgainEditText = (EditText)findViewById(R.id.psd_again_et);
		xingmingEditText = (EditText)findViewById(R.id.xingming_et);
		idEditText = (EditText)findViewById(R.id.id_et);
		mailEditText = (EditText)findViewById(R.id.mail_et);
		phoneEditText = (EditText)findViewById(R.id.phone_et);
		addressEditText = (EditText)findViewById(R.id.address_et);
		sexSpinner = (Spinner)findViewById(R.id.sex_spinner);
		yearSpinner = (Spinner)findViewById(R.id.year_spinner);
		monthSpinner = (Spinner)findViewById(R.id.month_spinner);
		daySpinner = (Spinner)findViewById(R.id.day_spinner);
		registerButton = (Button)findViewById(R.id.ok_btn);


		final List<String> years = getYearData();
		final List<String> months = getMonthData();
		final List<String> days = getDayData();
		final List<String> sexs = getSexData();

		ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>
        (RegisterPatientActivity.this, R.layout.spinner_item,getYearData()){
			@Override
			public View getDropDownView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.spinner_item_layout,
						null);
				TextView label = (TextView) view
						.findViewById(R.id.spinner_item_label);

				label.setText(years.get(position));

				return view;
				//return super.getDropDownView(position, convertView, parent);
			}
		};
		//设置样式
		yearAdapter.setDropDownViewResource(R.layout.spinner_item_layout);

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>
        (RegisterPatientActivity.this, R.layout.spinner_item,getMonthData()){
			@Override
			public View getDropDownView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.spinner_item_layout,
						null);
				TextView label = (TextView) view
						.findViewById(R.id.spinner_item_label);

				label.setText(months.get(position));

				return view;
				//return super.getDropDownView(position, convertView, parent);
			}
		};
		//设置样式
		monthAdapter.setDropDownViewResource(R.layout.spinner_item_layout);

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>
        (RegisterPatientActivity.this, R.layout.spinner_item,getDayData()){
			@Override
			public View getDropDownView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.spinner_item_layout,
						null);
				TextView label = (TextView) view
						.findViewById(R.id.spinner_item_label);

				label.setText(days.get(position));

				return view;
				//return super.getDropDownView(position, convertView, parent);
			}
		};
		//设置样式
		dayAdapter.setDropDownViewResource(R.layout.spinner_item_layout);

        ArrayAdapter<String> sexAdapter = new ArrayAdapter<String>
        (RegisterPatientActivity.this, R.layout.spinner_item,getSexData()){
			@Override
			public View getDropDownView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.spinner_item_layout,
						null);
				TextView label = (TextView) view
						.findViewById(R.id.spinner_item_label);

				label.setText(sexs.get(position));

				return view;
				//return super.getDropDownView(position, convertView, parent);
			}
		};
		//设置样式
		sexAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        
        sexSpinner.setAdapter(sexAdapter);
        yearSpinner.setAdapter(yearAdapter);
        monthSpinner.setAdapter(monthAdapter);
        daySpinner.setAdapter(dayAdapter);
        setSpinner();
        registerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				birthdayStr = yearStr+"-"+monthStr+"-"+dayStr;
				if (xingmingEditText.getText().toString().equals("")) {
//					Toast.makeText(RegisterPatientActivity.this, "姓名不能为空", Toast.LENGTH_LONG).show();
					toastCommom.ToastShow(RegisterPatientActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "姓名不能为空");
					return;
				}
				if(idEditText.getText().toString().equals("")){
//					Toast.makeText(RegisterPatientActivity.this, "身份证不能为空", Toast.LENGTH_LONG).show();
					toastCommom.ToastShow(RegisterPatientActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "身份证不能为空");
					return;
				}
				if(psdEditText.getText().toString().equals("")||psdAgainEditText.getText().toString().equals("")){
//					Toast.makeText(RegisterPatientActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
					toastCommom.ToastShow(RegisterPatientActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "密码不能为空");
					return;
				}
				if(!psdEditText.getText().toString().equals(psdAgainEditText.getText().toString())){
//					Toast.makeText(RegisterPatientActivity.this, "确认密码与密码不一样", Toast.LENGTH_LONG).show();
					toastCommom.ToastShow(RegisterPatientActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "确认密码与密码不一样");
					return;
				}
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					String time = birthdayStr+" 00:00:00";
					Log.i(TAG, "time " + time);
					Date date = format.parse(time);
				} catch (ParseException e) {
//					Toast.makeText(RegisterPatientActivity.this, "出生日期格式不正确", Toast.LENGTH_LONG).show();
					toastCommom.ToastShow(RegisterPatientActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "出生日期格式不正确");
					e.printStackTrace();
					return;
				}
				pDialog = new ProgressDialog(RegisterPatientActivity.this);
				pDialog.setTitle("注册中");
				pDialog.setMessage("请稍等......");
				pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				pDialog.show();
				new RegisterThread().run();
			}
		});
	}
	
	class RegisterThread extends Thread {
		@SuppressLint("NewApi")
		public void run() {
			if (android.os.Build.VERSION.SDK_INT > 9) {
			    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			    StrictMode.setThreadPolicy(policy);
			}
			birthdayStr = yearStr+"-"+monthStr+"-"+dayStr;
			try {
				params.put("userId", nameEditText.getText().toString());
				params.put("password", psdEditText.getText().toString());
				params.put("name", xingmingEditText.getText().toString());
				params.put("sex", sexStr);
				params.put("birthday", birthdayStr);
				params.put("identity", idEditText.getText().toString());
				params.put("email", mailEditText.getText().toString());
				params.put("phone", phoneEditText.getText().toString());
				params.put("address", addressEditText.getText().toString());
				
				Log.d(TAG, "httpUrl:" + http + regUrl);
				Log.d(TAG, "params:" + params.toString());
				
				httpUrl = http + regUrl;
				HttpPost post = HttpUtil.getPost(httpUrl, params);
				JSONObject joRev = HttpUtil.getString(post, 3);
				showStr = joRev.getString("result");
				resultStr = joRev.getString("resultMessage");
				pDialog.dismiss();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if (showStr.equals("200")) {
				Log.d(TAG, "resultStr:" + resultStr);
//				Toast.makeText(RegisterPatientActivity.this, "注册成功！",
//						Toast.LENGTH_LONG).show();
				toastCommom.ToastShow(RegisterPatientActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "注册成功");
//				Intent intent = new Intent(RegisterPatientActivity.this , GuidanceBottomTabActivity.class);
//				startActivity(intent);
				RegisterPatientActivity.this.finish();
			}
		}
	}
	
	public void setSpinner() {
		yearSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
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
	    monthSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
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
	    daySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
	
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
	    sexSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
	    	
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


	private List<String> getYearData(){
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		List<String> dataList = new ArrayList<String>();
		for(int i=year; i>=1940; i--){
			dataList.add(String.valueOf(i));
		}
		return dataList;
	}
	private List<String> getMonthData(){
		List<String> dataList = new ArrayList<String>();
		for(int i=1; i<13; i++){
			if(i<10){
				dataList.add("0"+ String.valueOf(i));
			}else{
				dataList.add(String.valueOf(i));
			}

		}
		return dataList;
	}
	private List<String> getDayData(){
		List<String> dataList = new ArrayList<String>();
		for(int i=1; i<32; i++){
			if(i<10){
				dataList.add("0"+ String.valueOf(i));
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
	/*
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_HOME){
        	Toast.makeText(this, "请使用退出按钮退出程序！Home键已禁止！",Toast.LENGTH_LONG).show();
        	return false;
        }else if(keyCode == KeyEvent.KEYCODE_BACK){
        	Toast.makeText(this, "请使用按钮操作程序！返回键已禁止！", Toast.LENGTH_LONG).show();
        	return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    */
}
