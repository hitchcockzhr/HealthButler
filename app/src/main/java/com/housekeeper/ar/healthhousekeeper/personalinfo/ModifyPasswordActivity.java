package com.housekeeper.ar.healthhousekeeper.personalinfo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;


public class ModifyPasswordActivity extends BaseActivity {

	private Button modifyBtn;

	private TextView oldPwdTextView;
	private TextView newPwdTextView;
	private TextView newAgainPwdTextView;
	private String TAG = "ModifyPasswordActivity";
	private Button backBtn;
	private ToastCommom toastCommom;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);
//		setContentView(R.layout.activity_fukuan_success);
		setContentView(R.layout.activity_change_psd);
		toastCommom = ToastCommom.createToastConfig();
		MyActivityManager.pushOneActivity(ModifyPasswordActivity.this);
		oldPwdTextView = (TextView)findViewById(R.id.old_psd_tv);
		newPwdTextView = (TextView)findViewById(R.id.new_psd_tv);
		newAgainPwdTextView = (TextView)findViewById(R.id.new_psd_again_tv);
		modifyBtn = (Button)findViewById(R.id.modify_psw_btn);
		backBtn = (Button)findViewById(R.id.back_btn);

		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

//		Toast.makeText(ModifyPasswordActivity.this,"待做密码修改",Toast.LENGTH_LONG).show();
		toastCommom.ToastShow(ModifyPasswordActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "待做密码修改");



		modifyBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//TODO 获取数据库中的密码
				final String pwdDBString = "ming";

				final String oldPwdString = oldPwdTextView.getText().toString();
				final String newPwdString = newPwdTextView.getText().toString();
				final String newAgainString = newAgainPwdTextView.getText().toString();

				Log.i(TAG, "oldPwdString " + oldPwdString + " newPwdString " + newPwdString + " newAgainString " + newAgainString);

				if(oldPwdString.equals("")||newPwdString.equals("")||newAgainString.equals("")){
//					Toast.makeText(ModifyPasswordActivity.this,"数据不能为空",Toast.LENGTH_LONG).show();
					toastCommom.ToastShow(ModifyPasswordActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "数据不能为空");
					return;
				}
				if(!oldPwdString.equals(pwdDBString)){
//					Toast.makeText(ModifyPasswordActivity.this,"原密码不对",Toast.LENGTH_LONG).show();
					toastCommom.ToastShow(ModifyPasswordActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "原密码不对");
					return;

				}
				if(!newPwdString.equals(newAgainString)){
//					Toast.makeText(ModifyPasswordActivity.this,"确认密码与新密码不相同",Toast.LENGTH_LONG).show();
					toastCommom.ToastShow(ModifyPasswordActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "确认密码与新密码不相同");
					return;
				}
				//其他条件

				//TODO 密码修改
			}
		});

	}





}
