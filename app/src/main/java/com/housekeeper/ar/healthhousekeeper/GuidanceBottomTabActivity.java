package com.housekeeper.ar.healthhousekeeper;

import android.annotation.TargetApi;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.followup.FollowUpClinicActivity;
import com.housekeeper.ar.healthhousekeeper.membershipcard.MembershipCardTab;

public class GuidanceBottomTabActivity extends TabActivity {

	//设置任务栏颜色
	SystemBarTintManager tintManager;
	private ToastCommom toastCommom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setTranslucentStatus(true);
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.background_green);//通知栏所需颜色

		setContentView(R.layout.activity_bottom_tabhost);

		SysApplication.getInstance().addActivity(this);
		toastCommom = ToastCommom.createToastConfig();
		MyActivityManager.pushOneActivity(GuidanceBottomTabActivity.this);
		toastCommom = ToastCommom.createToastConfig();

		 Resources res = getResources(); // Resource object to get Drawables  
	       final TabHost tabHost = getTabHost();  // The activity TabHost
	        TabSpec spec;  
	        Intent intent;  // Reusable Intent for each tab

		//第一个Tab
		intent = new Intent(this,TransferTreatActivity.class);//新建一个Intent用作Tab1显示的内容
		spec = tabHost.newTabSpec("tab1")//新建一个 Tab
				.setIndicator(createTabView(R.string.tab_transfer, R.drawable.menu_transfer))//设置名称以及图标
				.setContent(new Intent(this, TransferTreatActivityGroupTab.class));//设置显示的intent，这里的参数也可以是R.id.xxx
		tabHost.addTab(spec);//添加进tabHost

	       //第二个Tab
	        intent = new Intent(this,ConsultationActivity.class);//第二个Intent用作Tab1显示的内容
//		    intent.putExtra("from","quyao");
	        spec = tabHost.newTabSpec("tab2")//新建一个 Tab
	        .setIndicator(createTabView(R.string.tab_consult, R.drawable.menu_consultion))//设置名称以及图标
	        .setContent(new Intent(this, ConsultationActivityGroupTab.class));//设置显示的intent，这里的参数也可以是R.id.xxx
	        tabHost.addTab(spec);//添加进tabHost

        intent = new Intent(this,MembershipCardTab.class);//第二个Intent用作Tab1显示的内容
		//intent = new Intent(this,MembershipCardActivity.class);//第二个Intent用作Tab1显示的内容
		spec = tabHost.newTabSpec("tab3")//新建一个 Tab
				.setIndicator(createTabView(R.string.tab_membership, R.drawable.menu_membership))//设置名称以及图标
				.setContent(new Intent(this, MembershipCardActivityGroupTab.class));//设置显示的intent，这里的参数也可以是R.id.xxx
		tabHost.addTab(spec);//添加进tabHost


		intent = new Intent(this,FollowUpClinicActivity.class);//第二个Intent用作Tab1显示的内容
		spec = tabHost.newTabSpec("tab4")//新建一个 Tab
				.setIndicator(createTabView(R.string.tab_followup_clinic, R.drawable.menu_followup_clinic))//设置名称以及图标
				.setContent(new Intent(this, FollowUpClinicActivityGroupTab.class));//设置显示的intent，这里的参数也可以是R.id.xxx
		tabHost.addTab(spec);//添加进tabHost

		intent = new Intent(this,GuidanceHomeActivity.class);//第二个Intent用作Tab1显示的内容
		spec = tabHost.newTabSpec("tab5")//新建一个 Tab
				.setIndicator(createTabView(R.string.tab_home, R.drawable.menu_home_page))//设置名称以及图标
				.setContent(new Intent(this, GuidanceHomeActivityGroupTab.class));//设置显示的intent，这里的参数也可以是R.id.xxx
		tabHost.addTab(spec);//添加进tabHost

	   
		tabHost.setCurrentTab(4);//设置当前的选项卡,这里为Tab5
		View mView5 = tabHost.getTabWidget().getChildAt(4);//4是代表第五个Tab
		setTabPressedStatus(mView5, R.drawable.menu_home_page_pressed);

		tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			@Override
			public void onTabChanged(String s) {
				Log.i("GXF", "tab daifukuan s " + s);
				if (s.equals("tab1")) {

					View mView1 = tabHost.getTabWidget().getChildAt(0);//0是代表第一个Tab
					View mView2 = tabHost.getTabWidget().getChildAt(1);//1是代表第二个Tab
					View mView3 = tabHost.getTabWidget().getChildAt(2);
					View mView4 = tabHost.getTabWidget().getChildAt(3);
					View mView5 = tabHost.getTabWidget().getChildAt(4);

					setTabunPressedStatus(mView5, R.drawable.menu_home_page);

					setTabPressedStatus(mView1, R.drawable.menu_transfer_pressed);
					setTabunPressedStatus(mView2, R.drawable.menu_consultion);
					setTabunPressedStatus(mView3, R.drawable.menu_membership);

					setTabunPressedStatus(mView4, R.drawable.menu_followup_clinic);





				} else if (s.equals("tab2")) {



					View mView1 = tabHost.getTabWidget().getChildAt(0);//0是代表第一个Tab
					View mView2 = tabHost.getTabWidget().getChildAt(1);//1是代表第二个Tab
					View mView3 = tabHost.getTabWidget().getChildAt(2);
					View mView4 = tabHost.getTabWidget().getChildAt(3);
					View mView5 = tabHost.getTabWidget().getChildAt(4);

					setTabunPressedStatus(mView5, R.drawable.menu_home_page);



					setTabunPressedStatus(mView1, R.drawable.menu_transfer);
					setTabPressedStatus(mView2, R.drawable.menu_consultion_pressed);
					setTabunPressedStatus(mView3, R.drawable.menu_membership);

					setTabunPressedStatus(mView4, R.drawable.menu_followup_clinic);


				}else if (s.equals("tab3")) {



					View mView1 = tabHost.getTabWidget().getChildAt(0);//0是代表第一个Tab
					View mView2 = tabHost.getTabWidget().getChildAt(1);//1是代表第二个Tab
					View mView3 = tabHost.getTabWidget().getChildAt(2);
					View mView4 = tabHost.getTabWidget().getChildAt(3);


					View mView5 = tabHost.getTabWidget().getChildAt(4);

					setTabunPressedStatus(mView5, R.drawable.menu_home_page);



					setTabunPressedStatus(mView1, R.drawable.menu_transfer);
//					setTabunPressedStatus(mView1, R.drawable.queue);
					setTabunPressedStatus(mView2, R.drawable.menu_consultion);
					setTabPressedStatus(mView3, R.drawable.menu_membership_pressed);

					setTabunPressedStatus(mView4, R.drawable.menu_followup_clinic);


				}else if (s.equals("tab4")) {



					View mView1 = tabHost.getTabWidget().getChildAt(0);//0是代表第一个Tab
					View mView2 = tabHost.getTabWidget().getChildAt(1);//1是代表第二个Tab
					View mView3 = tabHost.getTabWidget().getChildAt(2);
					View mView4 = tabHost.getTabWidget().getChildAt(3);
					View mView5 = tabHost.getTabWidget().getChildAt(4);

					setTabunPressedStatus(mView5, R.drawable.menu_home_page);



					setTabunPressedStatus(mView1, R.drawable.menu_transfer);

//					setTabunPressedStatus(mView1, R.drawable.queue);
					setTabunPressedStatus(mView2, R.drawable.menu_consultion);
					setTabunPressedStatus(mView3, R.drawable.menu_membership);

					setTabPressedStatus(mView4, R.drawable.menu_followup_clinic_pressed);


				}else if (s.equals("tab5")) {



					View mView1 = tabHost.getTabWidget().getChildAt(0);//0是代表第一个Tab
					View mView2 = tabHost.getTabWidget().getChildAt(1);//1是代表第二个Tab
					View mView3 = tabHost.getTabWidget().getChildAt(2);
					View mView4 = tabHost.getTabWidget().getChildAt(3);
					View mView5 = tabHost.getTabWidget().getChildAt(4);





					setTabunPressedStatus(mView1, R.drawable.menu_transfer);

//					setTabunPressedStatus(mView1, R.drawable.queue);
					setTabunPressedStatus(mView2, R.drawable.menu_consultion);
					setTabunPressedStatus(mView3, R.drawable.menu_membership);

					setTabunPressedStatus(mView4, R.drawable.menu_followup_clinic);
					setTabPressedStatus(mView5, R.drawable.menu_home_page_pressed);


				}

			}
		});

		
	}

	
	 private View createTabView(int text,int resid) {
         View view = LayoutInflater.from(this).inflate(R.layout.tab_indicator, null);
         TextView tv = (TextView) view.findViewById(R.id.tv_tab);
         tv.setText(getResources().getString(text));
         
         ImageView image = (ImageView)view.findViewById(R.id.btn_tab);
        // image.setScaleType(ScaleType.CENTER_INSIDE);
         image.setBackgroundResource(resid);
         return view;
 }

	//Tab选中状态
	private void setTabPressedStatus(View view,int resid){
		ImageView image = (ImageView)view.findViewById(R.id.btn_tab);
		// image.setScaleType(ScaleType.CENTER_INSIDE);
		image.setBackgroundResource(resid);
		TextView tv = (TextView) view.findViewById(R.id.tv_tab);
		tv.setTextColor(getResources().getColor(R.color.black));
		LinearLayout layout = (LinearLayout)view.findViewById(R.id.tab_layout);
		layout.setBackgroundColor(getResources().getColor(R.color.white));
	}
	//Tab未选中状态
	private void setTabunPressedStatus(View view,int resid){
		ImageView image = (ImageView)view.findViewById(R.id.btn_tab);
		// image.setScaleType(ScaleType.CENTER_INSIDE);
		image.setBackgroundResource(resid);
		TextView tv = (TextView) view.findViewById(R.id.tv_tab);
		tv.setTextColor(getResources().getColor(R.color.white));
		LinearLayout layout = (LinearLayout)view.findViewById(R.id.tab_layout);
		layout.setBackgroundColor(getResources().getColor(R.color.background_green));
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


}
