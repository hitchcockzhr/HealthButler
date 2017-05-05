package com.housekeeper.ar.healthhousekeeper.personalinfo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.RoundImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class PersonalInfoActivity extends BaseActivity {
	String TAG = "PersonalInfoActivity";
	String httpUrl, http;
	JSONObject joDoc;
	MyApp myApp;
	private TextView modifyInfoTV;
	private TextView modifyPwdTV;
	private TextView updateTV;
	private TextView exitTV;
	private TextView setTV;
	private TextView helpTV;
	RoundImageView doc_head_iv;
	TextView name_tv;
	Bitmap doc_head;
 	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_info);
		MyActivityManager.pushOneActivity(PersonalInfoActivity.this);
		myApp = (MyApp)getApplication();
		joDoc = myApp.getJoDoc();
		doc_head = myApp.getDoc_head();
		doc_head_iv = (RoundImageView)findViewById(R.id.doc_head_iv);
		if(doc_head != null){
			doc_head_iv.setImageBitmap(doc_head);
		}

		name_tv = (TextView)findViewById(R.id.name_tv);
		if(joDoc != null && joDoc.length() != 0){

			try {
		    	name_tv.setText(joDoc.getString("name"));
	    	} catch (JSONException e) {
	    		e.printStackTrace();
	    	}
		}

		modifyPwdTV = (TextView)findViewById(R.id.modify_password_tv);
		modifyInfoTV = (TextView)findViewById(R.id.modify_info_tv);
		setTV = (TextView)findViewById(R.id.setting_tv);
		updateTV = (TextView)findViewById(R.id.update_tv);
		exitTV = (TextView)findViewById(R.id.exit_tv);
		helpTV = (TextView)findViewById(R.id.help_tv);

		modifyPwdTV.setClickable(true);
		modifyPwdTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(PersonalInfoActivity.this, ModifyPasswordActivity.class);
				startActivity(intent);
			}
		});

		updateTV.setClickable(true);
		updateTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(PersonalInfoActivity.this, "待做：如果当前版本已经是最新版本了进入UpdateVersionFinishActivity，否则进入UpdateVersionActivity", Toast.LENGTH_LONG).show();
				//TODO 如果当前版本已经是最新版本了进入UpdateVersionFinishActivity，否则进入UpdateVersionActivity
				int flag = 1;
				if (flag == 0) {
					Intent intent = new Intent(PersonalInfoActivity.this, UpdateVersionFinishActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(PersonalInfoActivity.this, UpdateVersionActivity.class);
					startActivity(intent);
				}


			}
		});

		modifyInfoTV.setClickable(true);
		modifyInfoTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(PersonalInfoActivity.this, ModifyInfoActivity.class);
				startActivity(intent);
			}
		});



		
	}





}
