package com.housekeeper.ar.healthhousekeeper;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class LoginActivity extends BaseActivity {
	MyApp myApp;
	static String http ;//= "http://192.168.1.54:8080/";
	//TODO 登录url，后期需要改变

	static String loginUrl = "xys/healthButler/login";
	static String startUrl = "shlc/initData";
	static String httpUrl;
	private String showStr = "";
	private Button loginBtn;
	private TextView regBtn;
	private EditText nameET , psdET;
	private JSONObject params = new JSONObject();
	private String TAG = "LoginActivity";
	SaveCache saveCache = new SaveCache(AppContext.get());
	CheckBox rememberCheckBox;
	private SharedPreferences sp;
	ProgressDialog pDialog;
	private ToastCommom toastCommom;
    TextView foget_tv;
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);
		setContentView(R.layout.activity_login);
		toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(LoginActivity.this);
		myApp = (MyApp)getApplication();
		http = myApp.getHttp();
		saveCache = new SaveCache(AppContext.get());

        foget_tv = (TextView)findViewById(R.id.foget_tv);
        foget_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                sendEmail();
				Intent intent = new Intent(LoginActivity.this,RememberPwdActivity.class);
				startActivity(intent);
            }
        });

		//============================================================
		//get
		getStart();
		sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);

		FrameLayout rl = (FrameLayout)findViewById(R.id.background_layout);
		rl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
			}
		});
		nameET = (EditText)findViewById(R.id.name_et);
		psdET = (EditText)findViewById(R.id.psd_et);
		rememberCheckBox = (CheckBox)findViewById(R.id.remember_psd_cb);
		//监听记住密码多选框按钮事件
		rememberCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				if (rememberCheckBox.isChecked()) {

					System.out.println("记住密码已选中");
					sp.edit().putBoolean("ISCHECK", true).commit();

				}else {

					System.out.println("记住密码没有选中");
					sp.edit().putBoolean("ISCHECK", false).commit();

				}

			}
		});
		if(sp.getBoolean("ISCHECK", false))
		{
			//设置默认是记录密码状态
			rememberCheckBox.setChecked(true);
			nameET.setText(sp.getString("USER_NAME", ""));
			psdET.setText(sp.getString("PASSWORD", ""));
			//判断自动登陆多选框状态

		}
		loginBtn = (Button)findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if(nameET.getText().toString().equals("")||psdET.getText().toString().equals("")){
					toastCommom.ToastShow(LoginActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "用户或密码不能为空！");
					return;
				}

				//测试============================================
//				Intent intent = new Intent(LoginActivity.this , GuidanceBottomTabActivity.class);
//				startActivity(intent);
//
//
//				LoginActivity.this.finish();


				//==============================================

             //实际代码
				//pDialog = new ProgressDialog(LoginActivity.this);
				//pDialog.setTitle("登录中");
				//pDialog.setMessage("请稍等......");
				//pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				//pDialog.show();
				login();
				//pDialog.dismiss();
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this ,GuidanceBottomTabActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();

			}
		});

		regBtn = (TextView)findViewById(R.id.regBtn);
		regBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//pDialog = new ProgressDialog(LoginActivity.this);
				//pDialog.setTitle("跳转中");
				//pDialog.setMessage("请稍等......");
				//pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				//pDialog.show();
				Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);

			}
		});
	}

    private boolean sendEmail(){
        Properties props = new Properties();
        props.put("mail.smtp.protocol", "smtp");
        props.put("mail.smtp.auth", "true");//设置要验证
        props.put("mail.smtp.host", "smtp.sina.com");//设置host
        props.put("mail.smtp.port", "25");  //设置端口
        PassAuthenticator pass = new PassAuthenticator();   //获取帐号密码
        Session session = Session.getInstance(props, pass); //获取验证会话
        try
        {
            //配置发送及接收邮箱
            InternetAddress fromAddress, toAddress;
            /**
             * 这个地方需要改成自己的邮箱
             */
            fromAddress = new InternetAddress("zhangruitest0113@sina.com");
            toAddress   = new InternetAddress("zhangruitest0113@sina.com");
            /**
             * 一下内容是：发送邮件时添加附件
             */
            MimeBodyPart attachPart = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(Environment.getExternalStorageDirectory()+"/crash"+"/crash.log"); //打开要发送的文件
            Log.v(TAG, "已打开crash文件");
            attachPart.setDataHandler(new DataHandler(fds));
            attachPart.setFileName(fds.getName());
            MimeMultipart allMultipart = new MimeMultipart("mixed"); //附件
            allMultipart.addBodyPart(attachPart);//添加
            Log.v(TAG, "已添加附件");
            //配置发送信息
            MimeMessage message = new MimeMessage(session);
//                message.setContent("test", "text/plain");
            message.setContent(allMultipart); //发邮件时添加附件
            message.setSubject("Doctor Phone Crash Log");
            message.setFrom(fromAddress);
            message.addRecipient(javax.mail.Message.RecipientType.TO, toAddress);
            message.saveChanges();
            //连接邮箱并发送
            Transport transport = session.getTransport("smtp");
            /**
             * 这个地方需要改称自己的账号和密码
             */
            transport.connect("smtp.sina.com", "zhangruitest0113@sina.com", "123456789Abc");
            transport.send(message);
            transport.close();
        } catch (Exception e) {
            //Log.e("sendmail", e.printStackTrace());
            e.printStackTrace();
            //throw new RuntimeException();//将此异常向上抛出，此时CrashHandler就能够接收这里抛出的异常并最终将其存放到txt文件中

        }
        return false;
    }

	@Override
	protected void onResume() {
		//if(pDialog != null && pDialog.isShowing()){
		//	pDialog.dismiss();
		//}
		super.onResume();
	}

	//class LoginThread extends Thread{
	//	public void run() {
	public void login(){
			try {
				params.put("userId", nameET.getText().toString());
				params.put("password", psdET.getText().toString());
				Log.d(TAG, "httpUrl:"+http+loginUrl);
				Log.d(TAG, "登录用户:"+nameET.getText().toString()+":"+psdET.getText().toString());
				httpUrl = http + loginUrl;
				HttpPost post = HttpUtil.getPost(httpUrl, params);
				JSONObject joRev = HttpUtil.getLoginString(post, 3);
				Log.v(TAG, "login Rev:" + joRev.toString());

				showStr = joRev.getString("result");
				if(showStr.equals("403")){
					//toastCommom.ToastShow(LoginActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "该用户注册后，尚未通过审核，请等待！");
					Toast.makeText(LoginActivity.this,"该用户注册后，尚未通过审核，请等待！",Toast.LENGTH_LONG).show();
					return;
				}else if(showStr.equals("200")){
                    Log.i(TAG,"doc info:"+joRev.getJSONObject("value") );
                    JSONObject joDoc = joRev.getJSONObject("value");
                    myApp.setJoDoc(joDoc);
                    myApp.setUserId(nameET.getText().toString());
					if(rememberCheckBox.isChecked())
					{
						//记住用户名、密码
						Editor editor = sp.edit();
						editor.putString("USER_NAME", nameET.getText().toString());
						editor.putString("PASSWORD",psdET.getText().toString());
						editor.commit();
					}

					Intent intent = new Intent(LoginActivity.this , GuidanceBottomTabActivity.class);
					startActivity(intent);


					LoginActivity.this.finish();
				}else if(showStr.equals("401")){
                    Toast.makeText(LoginActivity.this,"该用户不存在，请先注册！",Toast.LENGTH_LONG).show();
                   // toastCommom.ToastShow(LoginActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "该用户不存在，请先注册！");
					//pDialog.dismiss();
					return;
				}else {
					Toast.makeText(LoginActivity.this,"登录异常，请检查该账户是否已经登录",Toast.LENGTH_LONG).show();
                    //toastCommom.ToastShow(LoginActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "登录异常，请检查该账户是否已经登录");
                    //pDialog.dismiss();
                    return;
                }
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}finally{
				//pDialog.dismiss();
			}
			//handler.sendMessage(msg);


	}
	@SuppressLint("NewApi")
	private void getStart(){
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		JSONObject joRev = new JSONObject();
		try {
			joRev = HttpUtil.getResultForHttpGet(http+startUrl);
			Log.i(TAG, "jo init: "+joRev.toString());
			queryJson(joRev.getJSONObject("value"));
			//文件缓存
			String fileName = saveCache.saveFileCachePath("JSONStart", http+startUrl);
			myApp.setJoStartPath(fileName);
			Log.v(TAG,"JoStartPath:"+myApp.getJoStartPath());
			saveCache.save(fileName, joRev.toString());
			Log.v(TAG, "fileName:"+fileName.toString());
			//String readFileCache = saveCache.read(fileName);
			//Log.v(TAG, "readFileCache:"+readFileCache);
			//JSONObject joReadFileCache = new JSONObject(readFileCache);
			//Log.v(TAG, joRev.toString());
			/*
			String result = joReadFileCache.getString("result");
			String resultMessage = joReadFileCache.getString("resultMessage");
			JSONObject joValue = joReadFileCache.getJSONObject("value");
			JSONArray jaProvinces = joValue.getJSONArray("provinces");
			JSONArray jaJobTitiles = joValue.getJSONArray("jobTitles");
			for(int i=0; i<jaProvinces.length(); i++){
				JSONObject joProvince = jaProvinces.getJSONObject(i);
				//Log.v(TAG, joProvince.toString());
				if(joProvince.get("name").equals("����")){
					Log.v(TAG, "����");

				}
			}
			//Log.v(TAG, result);
			//Log.v(TAG, resultMessage);
			//Log.v(TAG, joValue.toString());
			//Log.v(TAG, jaProvinces.toString());
			//Log.v(TAG, jaJobTitiles.toString());
			//����Start���ص�JSON//shlc/initData
			 */

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
	//解析初始化数据-解析json
	private void queryJson(JSONObject jsonObject){
        try {
            JSONArray provincesJA = jsonObject.getJSONArray("provinces");
            JSONArray jobTitlesJA = jsonObject.getJSONArray("jobTitles");
            JSONArray departmentTypesJA = jsonObject.getJSONArray("departmentTypes");
			//JSONArray feeJA = jsonObject.getJSONArray("fees");
			Log.i(TAG, "provincesJA:"+provincesJA.toString());
			Log.i(TAG, "jobTitlesJA:"+jobTitlesJA.toString());
			Log.i(TAG, "departmentTypesJA:"+departmentTypesJA.toString());
			//Log.i(TAG, "feeJA:"+feeJA.toString());
            myApp.setProvincesJA(provincesJA);
            myApp.setJobTitlesJA(jobTitlesJA);
            myApp.setDepartmentTypesJA(departmentTypesJA);
			//myApp.setFeeJA(feeJA);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*
        ArrayList<JSONObject> jsonList = new ArrayList<JSONObject>();
		try {
			JSONArray provincesJsonArray = jsonObject.getJSONArray("provinces");
			for (int i = 0; i < provincesJsonArray.length(); i++) {
				JSONObject provinceJsonObject = provincesJsonArray.getJSONObject(i);
				JSONObject jo = new JSONObject();
				jo.put("province_name", provinceJsonObject.getString("name"));
				jo.put("province_id", provinceJsonObject.getLong("id"));
				JSONArray citiesJsonArray = provinceJsonObject.getJSONArray("cities");
				for (int j = 0; j < citiesJsonArray.length(); j++) {
					JSONObject cityJsonObject = citiesJsonArray.getJSONObject(j);
					jo.put("city_name", cityJsonObject.getString("name"));
					jo.put("city_id", cityJsonObject.getLong("id"));
					JSONArray hospitalsJsonArray = cityJsonObject.getJSONArray("hospitals");
					for(int k = 0; k < hospitalsJsonArray.length(); k++){
						JSONObject hospitalJsonObject = hospitalsJsonArray.getJSONObject(k);
						jo.put("hospital_name", hospitalJsonObject.getString("name"));
						jo.put("hospital_id", hospitalJsonObject.getLong("id"));
						JSONArray departmentsJsonArray = hospitalJsonObject.getJSONArray("departments");
						for (int l = 0; l < departmentsJsonArray.length(); l++) {
							JSONObject departmentJsonObject = departmentsJsonArray.getJSONObject(l);
							JSONObject departmentTypeJsonObject = departmentJsonObject.getJSONObject("departmentType");
							jo.put("department_name", departmentJsonObject.getString("name"));
							jo.put("department_id", departmentJsonObject.getLong("id"));
							jo.put("departmentType_name", departmentTypeJsonObject.getString("name"));
							jo.put("departmentType_id", departmentTypeJsonObject.getLong("id"));
							//Log.i(TAG, "department_jo:"+jo.toString());
							JSONArray feesJsonArray = hospitalJsonObject.getJSONArray("fees");
							for (int m = 0; m < feesJsonArray.length(); m++) {
								JSONObject feeJsonObject = feesJsonArray.getJSONObject(m);
								JSONObject jobTitleJsonObject = feeJsonObject.getJSONObject("jobTitle");
								jo.put("fee_name", feeJsonObject.getString("name"));
								jo.put("fee_id", feeJsonObject.getLong("id"));
								jo.put("fee_price", feeJsonObject.getLong("price"));
								jo.put("jobTitle_name", jobTitleJsonObject.getString("name"));
								jo.put("jobTitle_id", jobTitleJsonObject.getLong("id"));
								Log.i(TAG, "fee_jo:" + jo.toString());
								jsonList.add(jo);
							}
						}

					}
				}
			}
			Log.i(TAG, "jsonList: "+jsonList.toString());
			myApp.setJsonList(jsonList);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	public void showToast(String msg){
		Looper.prepare();
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//		toastCommom.ToastShow(getApplicationContext(), (ViewGroup) findViewById(R.id.toast_layout_root), msg);
		Looper.loop();
	}
}
