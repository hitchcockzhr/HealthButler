package com.housekeeper.ar.healthhousekeeper.guidance;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;

import org.json.JSONException;
import org.json.JSONObject;

public class YiShengJianJieActivity extends Activity {
	MyApp myApp;
	JSONObject joDoctor;
	TextView ysjjTextView;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyActivityManager.pushOneActivity(YiShengJianJieActivity.this);
		SysApplication.getInstance().addActivity(this);
		setContentView(R.layout.activity_ysjj);
		myApp = (MyApp)getApplication();
		joDoctor = myApp.getJoDoctor();
		ysjjTextView = (TextView)findViewById(R.id.ysjj_detail_tv);
		try {
			ysjjTextView.setText(joDoctor.getString("description"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
