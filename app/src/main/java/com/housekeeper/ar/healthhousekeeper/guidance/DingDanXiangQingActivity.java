package com.housekeeper.ar.healthhousekeeper.guidance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.housekeeper.ar.healthhousekeeper.GuidanceBottomTabActivity;
import com.housekeeper.ar.healthhousekeeper.GuidanceHomeActivity;
import com.housekeeper.ar.healthhousekeeper.GuidanceHomeActivityGroupTab;
import com.housekeeper.ar.healthhousekeeper.HttpUtil;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

//下订单之详情页
public class DingDanXiangQingActivity extends Activity {
	String TAG = "DingDanXiangQingActivity";
	String joRevString;
	String id ;
	JSONObject joRev, joValue;
	Button fukuanButton, cancelButton, okButton;
	TextView hospitalTextView, keshiTextView, zhichengTextView, nameTextView,zhuanchangTextView;
	TextView dateTextView, weekTextView, timeTextView, priceTextView;
	//病情描述
	EditText descriptionEditText;
	String http, httpUrl, showStr, resultStr ;
	//付款
	String fukuanUrlString = "shlc/patient/payment/medicalRecord/";
	//TODO 上传描述 后期修改
	String descriptionUrlString = "shlc/patient/medicalRecord/description/";
	//TODO 取消订单  后期修改
	String cancelUrlString = "shlc/patient/cancel/medicalRecord/";
	MyApp myApp;
	String payUrl = "shlc/healthButler/payment/medicalRecord";
	String medicalRecordIdUrl = "?refId=";
	String realPriceUrl = "&realPrice=";
	String scheId;
	//存放已选择医生信息和时间信息
	HashMap<String, String> selectedMap;
	long medicalRecordId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyActivityManager.pushOneActivity(DingDanXiangQingActivity.this);
		SysApplication.getInstance().addActivity(this);
		setContentView(R.layout.activity_ddxq);
		myApp = (MyApp)getApplication();
		http = myApp.getHttp();
		Intent intent = getIntent();
		joRevString = intent.getStringExtra("yuyue");
		//scheId = intent.getStringExtra("scheId");
		selectedMap = (HashMap<String, String>)intent.getSerializableExtra("selectedMap");
		try {
			joRev = new JSONObject(joRevString);
			joValue = joRev.getJSONObject("value");
			Log.i(TAG, "intent joValue:" + joValue.toString());
			medicalRecordId = joValue.getLong("id");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		hospitalTextView = (TextView) findViewById(R.id.hospital_tv);
		keshiTextView = (TextView)findViewById(R.id.keshi_tv);
		zhichengTextView = (TextView)findViewById(R.id.zhicheng_tv);
		nameTextView = (TextView)findViewById(R.id.name_tv);
		zhuanchangTextView = (TextView)findViewById(R.id.zhuanchang_tv);
		dateTextView = (TextView)findViewById(R.id.date_tv);
		weekTextView =(TextView)findViewById(R.id.week_tv);
		timeTextView = (TextView)findViewById(R.id.time_tv);
		priceTextView = (TextView)findViewById(R.id.price_tv);
		hospitalTextView.setText(selectedMap.get("hospital"));
		keshiTextView.setText(selectedMap.get("keshi"));
		zhichengTextView.setText(selectedMap.get("zhicheng"));
		zhuanchangTextView.setText(selectedMap.get("zhuanchang"));
		nameTextView.setText(selectedMap.get("name"));
		dateTextView.setText(selectedMap.get("date"));
		weekTextView.setText(selectedMap.get("week"));
		timeTextView.setText(selectedMap.get("time"));
		priceTextView.setText(selectedMap.get("feePrice")+"元");



		descriptionEditText = (EditText)findViewById(R.id.description_et);

		okButton = (Button)findViewById(R.id.ok_btn);
		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.i(TAG, "okButton  ");
				try {
					Log.i(TAG, "okButton  joValue " + joValue);
					if (joValue == null) {

						return;
					}
					id = String.valueOf(joValue.getLong("id"));
					Log.i(TAG, "okButton  id " + id);
					httpUrl = http + descriptionUrlString;
					Log.i(TAG, "okButton httpUrl " + httpUrl);
					JSONObject joSend = new JSONObject();
					joSend.put("id", Integer.parseInt(id));
					joSend.put("description", descriptionEditText.getText().toString());
					HttpPut put = HttpUtil.getPut(httpUrl, joSend);

					String revString = HttpUtil.getPutString(put);
					Log.i(TAG, "okButton description Rev:" + revString);
					JSONObject joRev = new JSONObject(revString);
					if (joRev.getString("result").equals("200")) {
						Intent intent = new Intent(DingDanXiangQingActivity.this, GuidanceBottomTabActivity.class);
						startActivity(intent);
						DingDanXiangQingActivity.this.finish();
					}
				} catch (JSONException e) {
					Log.i(TAG, "okButton JSONException");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		fukuanButton = (Button)findViewById(R.id.fukuan_btn);
		fukuanButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//病情描述
				/*
				try {
					id = String.valueOf(joValue.getLong("id"));
					httpUrl = http + descriptionUrlString;
					Log.i(TAG, httpUrl);
					JSONObject joSend = new JSONObject();
					joSend.put("id", Integer.parseInt(id));
					joSend.put("description", descriptionEditText.getText().toString());
					HttpPut put = HttpUtil.getPut(httpUrl, joSend);

					String revString = HttpUtil.getPutString(put);
					Log.i(TAG, "description Rev:" + revString);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
				//付款
				try {
					id = String.valueOf(joValue.getLong("id"));
					String scheId = selectedMap.get("scheId");
					String price = selectedMap.get("feePrice");
					//httpUrl = http + payUrl + medicalRecordIdUrl + medicalRecordId + realPriceUrl + Long.parseLong(price);
					httpUrl = http + payUrl + medicalRecordIdUrl + medicalRecordId + realPriceUrl + 10;
					Log.i(TAG, "pay url:"+httpUrl);
					HttpPost post = HttpUtil.getPost(httpUrl, null);
					JSONObject joRev = HttpUtil.getString(post,3);
					Log.i(TAG, "pay joRev:" + joRev.toString());
					if(joRev.getLong("result")==200){
						Toast.makeText(DingDanXiangQingActivity.this, "预约成功！", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(DingDanXiangQingActivity.this, GuidanceHomeActivity.class).
								addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						//把一个Activity转换成一个View
						Window w = GuidanceHomeActivityGroupTab.group.getLocalActivityManager()
								.startActivity("GuidanceHomeActivity",intent);
						View view = w.getDecorView();
						//把View添加大ActivityGroup中
						GuidanceHomeActivityGroupTab.group.setContentView(view);
					}
//					Intent intent = new Intent(DingDanXiangQingActivity.this, PayActivity.class);
//					intent.putExtra("id", id);
//					intent.putExtra("class",0);
//					startActivity(intent);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		cancelButton = (Button)findViewById(R.id.cancel_btn);
		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					id = String.valueOf(joValue.getLong("id"));
					httpUrl = http + cancelUrlString+id;
					HttpPost post = HttpUtil.getPost(httpUrl, null);
					joRev = HttpUtil.getString(post, 3);
					Log.i(TAG, "joRev:" + joRev.toString());
					if(String.valueOf(joRev.get("result")).equals("200")){
						Intent intent = new Intent(DingDanXiangQingActivity.this, GuidanceBottomTabActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						DingDanXiangQingActivity.this.finish();
					}else{
						Toast.makeText(DingDanXiangQingActivity.this, "取消失败", Toast.LENGTH_SHORT).show();
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
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
