package com.housekeeper.ar.healthhousekeeper.personalinfo;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpdateVersionFinishActivity extends BaseActivity {
	private String TAG = "UpdateVersionFinishActivity";

	private ListView listView;
	MyApp myApp;
	String http, httpUrl;
	String medicalRecordId;
	private List<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
	private Button backButton;
	private TextView nowVersionTextView;
	private TextView nowVersionDetailTextView;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);
		MyActivityManager.pushOneActivity(UpdateVersionFinishActivity.this);
		setContentView(R.layout.version_update_finish_activity);
		nowVersionTextView = (TextView)findViewById(R.id.now_version_id_tv);
		nowVersionDetailTextView = (TextView)findViewById(R.id.now_version_right_arrow);
		nowVersionDetailTextView.setClickable(true);
		nowVersionDetailTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(UpdateVersionFinishActivity.this,UpdateVersionLogActivity.class);
				startActivity(intent);
			}
		});

		nowVersionTextView.setText("6.2.1");
		myApp = (MyApp)getApplication();
//		jsonList = myApp.getJsonList();
		http = myApp.getHttp();
		medicalRecordId = myApp.getMedicalRecordId();


//		backButton = (Button)findViewById(R.id.back_btn);
//		backButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
////				Intent intent = new Intent(DoctorCard.this, MainActivity.class);
////				startActivity(intent);
//				finish();
//				//YuYueGuanLiActivity.this.finish();
//			}
//		});

	}

}
