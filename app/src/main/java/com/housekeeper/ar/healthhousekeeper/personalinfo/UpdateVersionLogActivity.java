package com.housekeeper.ar.healthhousekeeper.personalinfo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class UpdateVersionLogActivity extends BaseActivity {
	private String TAG = "UpdateVersionActivity";

	private ListView listView;
	MyApp myApp;
	String http, httpUrl;
	String medicalRecordId;
	private List<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
	private Button backButton;
	private TextView newVersionIDTextView;
	private TextView newVersionSizeTextView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);
		MyActivityManager.pushOneActivity(UpdateVersionLogActivity.this);
		setContentView(R.layout.version_update_log_activity);

		myApp = (MyApp)getApplication();
//		jsonList = myApp.getJsonList();
		http = myApp.getHttp();
		medicalRecordId = myApp.getMedicalRecordId();

		newVersionIDTextView = (TextView)findViewById(R.id.new_version_id_tv);
		newVersionSizeTextView = (TextView)findViewById(R.id.new_version_size_tv);
		backButton = (Button)findViewById(R.id.back_btn);

		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		newVersionIDTextView.setText("6.2.1");
		newVersionSizeTextView.setText("87M");
		initData();
		sortDataByType();
		listView = (ListView)findViewById(R.id.new_version_list_view);
		Log.i("GXF", "doctor card " + data);
		listView.setAdapter(new UpdateVersionAdapter(this, data, http, myApp,medicalRecordId));
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
	private void initData(){

		HashMap<String,String> map = new HashMap<String, String>();
		map.put("content","无图模式回归");
		map.put("type","系统");
		data.add(map);
		HashMap<String,String> map1 = new HashMap<String, String>();
		map1.put("content","众测商品评价");
		map1.put("type","个人");
		data.add(map1);
		HashMap<String,String> map2 = new HashMap<String, String>();
		map2.put("content","热搜词");
		map2.put("type","系统");
		data.add(map2);
		HashMap<String,String> map3 = new HashMap<String, String>();
		map3.put("content","我的消息未读模式");
		map3.put("type","多媒体");
		data.add(map3);
		HashMap<String,String> map4 = new HashMap<String, String>();
		map4.put("content", "搜索结果显示调整");
		map4.put("type","个人");
		data.add(map4);
		HashMap<String,String> map5 = new HashMap<String, String>();
		map5.put("content", "众测商品评价");
		map5.put("type","多媒体");
		data.add(map5);
		HashMap<String,String> map6 = new HashMap<String, String>();
		map6.put("content","我的消息未读模式");
		map6.put("type","多媒体");
		data.add(map6);
		HashMap<String,String> map7 = new HashMap<String, String>();
		map7.put("content", "搜索结果显示调整");
		map7.put("type","系统");
		data.add(map7);
	}
	Comparator<HashMap<String, String>> mapComprator = new Comparator<HashMap<String, String>>(){


		@Override
		public int compare(HashMap<String, String> map1,
						   HashMap<String, String> map2) {
			// TODO Auto-generated method stub
			String start1 = map1.get("type");
			String start2 = map2.get("type");


			return getPinYin(start1).compareTo(getPinYin(start2));
		}
	};

	public void sortDataByType(){
		Collections.sort(data,mapComprator);
		Log.v(TAG, data.toString());
	}
	public static String getPinYin(String inputString) {

		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();

		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);

		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

		format.setVCharType(HanyuPinyinVCharType.WITH_V);

		char[] input = inputString.trim().toCharArray();

		String output = "";

		try {

			for (char curchar : input) {

				if (Character.toString(curchar).matches(

						"[\\u4E00-\\u9FA5]+")) {

					String[] temp = PinyinHelper.toHanyuPinyinStringArray(

							curchar, format);

					output += temp[0];

				} else

					output += Character.toString(curchar);

			}

		} catch (BadHanyuPinyinOutputFormatCombination e) {

			e.printStackTrace();

		}

		return output;

	}
}
