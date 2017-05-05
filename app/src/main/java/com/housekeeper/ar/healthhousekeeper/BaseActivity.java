package com.housekeeper.ar.healthhousekeeper;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

//父Activity，设置任务栏背景
public class BaseActivity extends FragmentActivity {

	SystemBarTintManager tintManager;
	private String TAG = "BaseActivity";




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		setTranslucentStatus(true);
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.background_green);//通知栏所需颜色
		setContentView(R.layout.activity_main);




	}
	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	public void setStatusBarColor(int color){
		tintManager.setStatusBarTintResource(color);//通知栏所需颜色
	}

	@Override
	protected void onResume() {

		super.onResume();
	}

}
