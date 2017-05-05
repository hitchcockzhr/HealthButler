package com.housekeeper.ar.healthhousekeeper.guidance;

import android.app.LocalActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.housekeeper.ar.healthhousekeeper.AppContext;
import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SaveCache;
import com.housekeeper.ar.healthhousekeeper.SysApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class YuYueGuanLiActivity extends BaseActivity {
	//
	// 
	private String TAG = "YuYueGuanLiActivity";
	MyApp myApp;
	TextView hospitalTextView, keshiTextView, zhichengTextView, zhuanchangTextView, nameTextView, diquTextView;
	ImageView headImageView;
	String doctorId;
	JSONObject joDoctor;
	private String readFileCache;
	private JSONObject joReadFileCache;
	private SaveCache saveCache = new SaveCache(AppContext.get());
	private static JSONArray jaPros;
	private static JSONObject[] joPros;
	private static String[] namePros;
	private static int[] idJobTitles;
	private long[] scheIDs;
	private ViewPager mPager;//
	private List<View> listViews; //
	private ImageView cursor;//
	private TextView t1, t2, t3;//
	private int offset = 0;// 
	private int currIndex = 0;//
	private int bmpW;// 
	Context context = null;
	LocalActivityManager manager = null;
	private MyPagerAdapter myPagerAdapter;
	Button backButton;
	JSONObject doctorJson;
	String http, httpUrl;
	String getScheduleUrl = "shlc/healthButler/doctor/schedules";
	String userIdUrl = "?userId=";
	String docIdUrl = "&doctorId=";
	String statusUrl = "&inprogress=";
//	private LinearLayout bottomLayout;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyActivityManager.pushOneActivity(YuYueGuanLiActivity.this);
		SysApplication.getInstance().addActivity(this);
		setContentView(R.layout.activity_yygl);
		context = YuYueGuanLiActivity.this;
		manager = new LocalActivityManager(this , true);
		manager.dispatchCreate(savedInstanceState);
		myApp = (MyApp)getApplication();

//		bottomLayout = (LinearLayout)findViewById(R.id.bottom_layout);
		http = myApp.getHttp();

		initData();
		InitImageView();
		InitTextView();
		InitViewPager();
		backToMain();

	}
	public void backToMain(){
		backButton = (Button)findViewById(R.id.back_btn);
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/*
				myApp.setCityString(null);
				myApp.setDiquString(null);
				myApp.setKeshiString(null);
				myApp.setYiyuanString(null);
				//myApp.setDoctorIDString(null);
				myApp.setYishengString(null);
				Intent intent = new Intent(YuYueGuanLiActivity.this, MainActivity.class);
				startActivity(intent);
				*/
				YuYueGuanLiActivity.this.finish();
			}
		});
	}

	private void initData(){
		try {
			doctorJson = myApp.getJoDoctor();
			Log.i(TAG, "doctorJson: "+ doctorJson.toString());
			diquTextView = (TextView)findViewById(R.id.city_tv);
			hospitalTextView = (TextView)findViewById(R.id.hospital_tv);
			keshiTextView = (TextView)findViewById(R.id.keshi_tv);
			zhichengTextView = (TextView)findViewById(R.id.zhicheng_tv);
			zhuanchangTextView = (TextView)findViewById(R.id.zhuanchang_tv);
			nameTextView = (TextView)findViewById(R.id.name_tv);
			diquTextView.setText(doctorJson.getString("cityName"));
			keshiTextView.setText(doctorJson.getString("departmentName"));
		
			zhichengTextView.setText(doctorJson.getString("jobTitleName"));
			nameTextView.setText(doctorJson.getString("name"));
			zhuanchangTextView.setText(doctorJson.getString("skill"));
			hospitalTextView.setText(doctorJson.getString("hosptialName"));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 
	 */
	private void InitTextView() {
		t1 = (TextView) findViewById(R.id.text1);
		t2 = (TextView) findViewById(R.id.text2);
		t3 = (TextView) findViewById(R.id.text3);
		t1.setBackgroundResource(R.drawable.ysyq_xz);
		t2.setBackgroundResource(R.drawable.yygh_wxz);
		t3.setBackgroundResource(R.drawable.srys_wxz);
		t1.setOnClickListener(new MyOnClickListener(0));
		t2.setOnClickListener(new MyOnClickListener(1));
		t3.setOnClickListener(new MyOnClickListener(2));
		
	}

	/**
	 * 
	 */
	private void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.vPager);
		listViews = new ArrayList<View>();
		/*
		LayoutInflater mInflater = getLayoutInflater();
		listViews.add(mInflater.inflate(R.layout.dd_qb, null));
		listViews.add(mInflater.inflate(R.layout.dd_dfk, null));
		listViews.add(mInflater.inflate(R.layout.dd_dpj, null));
		*/
		Intent intent1 = new Intent(context, YiShengJianJieActivity.class);
		listViews.add(getView("YiShengJianJie", intent1));
		Intent intent2 = new Intent(context, YuyueActivity.class);
		listViews.add(getView("YuYueGuaHao", intent2));
		Intent intent3 = new Intent(context, SiRenYiShengActivity.class);
		listViews.add(getView("SiRenYiSheng", intent3));
		
		
		myPagerAdapter = new MyPagerAdapter(listViews);
		mPager.setAdapter(myPagerAdapter);
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
		myPagerAdapter.notifyDataSetChanged();
	}
	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}
	/**
	 * 
	 */
	private void InitImageView() {
		cursor = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a)
				.getWidth();//
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 
		offset = (screenW / 3 - bmpW) / 2;//
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);// 
	}

	/**
	 * 
	 */
	public class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;
		private int mChildCount = 0;
		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}
		 @Override
	     public void notifyDataSetChanged() {         
			 mChildCount = getCount();
	         super.notifyDataSetChanged();
	     }
		@Override
		public int getItemPosition(Object object) {
			if ( mChildCount > 0) {
				mChildCount --;
		        return POSITION_NONE;
		    }
		    return super.getItemPosition(object);  
		}  
		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(mListViews.get(arg1), 0);
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}

	/**
	 * 
	 */
	public class MyOnClickListener implements OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};

	/**
	 * 
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;//
		int two = one * 2;// 

		@Override
		public void onPageSelected(int arg0) {
			/*
			Animation animation = null;
			switch (arg0) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
				}
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, one, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
				}
				break;
			case 2:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, two, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
				}
				break;
			}
			*/
			Animation animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);
			currIndex = arg0;
			switch (arg0) {
			case 0:
				t1.setBackgroundResource(R.drawable.ysyq_xz);
				t2.setBackgroundResource(R.drawable.yygh_wxz);
				t3.setBackgroundResource(R.drawable.srys_wxz);
				break;
			case 1:
				t1.setBackgroundResource(R.drawable.yyxq_wxz);
				t2.setBackgroundResource(R.drawable.yygh_xz);
				t3.setBackgroundResource(R.drawable.srys_wxz);
				break;
			case 2:
				t1.setBackgroundResource(R.drawable.yyxq_wxz);
				t2.setBackgroundResource(R.drawable.yygh_wxz);
				t3.setBackgroundResource(R.drawable.srys_xz);
				break;
			default:
				t1.setBackgroundResource(R.drawable.ysyq_xz);
				t2.setBackgroundResource(R.drawable.yygh_wxz);
				t3.setBackgroundResource(R.drawable.srys_wxz);
				break;
			}
			animation.setFillAfter(true);
			animation.setDuration(300);
			cursor.startAnimation(animation);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

}
