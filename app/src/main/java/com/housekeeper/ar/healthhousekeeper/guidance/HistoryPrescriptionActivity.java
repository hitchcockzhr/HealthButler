package com.housekeeper.ar.healthhousekeeper.guidance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.FlowLayout;
import com.housekeeper.ar.healthhousekeeper.HttpUtil;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by lenovo on 2016/3/5.
 */
//历史处方
public class HistoryPrescriptionActivity extends Activity {


    String TAG = "HistoryPrescriptionActivity";

    private MyApp myApp;
	private FlowLayout historyDateFlowLayout;
	private ListView oneSearchHistoryMedicineListView;
//	private long currentStatus = Status.MedicalRecordStatus.EVALUATED.getValue();

	//某个日期的历史处方，//标记是否被选择，初始化false
	private ArrayList<HashMap<String,String>> oneSearchHistoryMedicineLists =  new ArrayList<HashMap<String, String>>();
	//选中的历史处方，//标记是否被选择，初始化false
	private List<HashMap<String,String>> selectedHistoryMedicineLists = new ArrayList<HashMap<String, String>>();

	//选中的某日期历史处方，//标记是否被选择，初始化false
	private List<HashMap<String,String>> oneSelectedHistoryMedicineLists = new ArrayList<HashMap<String, String>>();
	private String date;

	private ArrayList<String> historyDateList = new ArrayList<String>();

	//选择按钮
	private Button selectBtn;
	//全选按钮
	private Button selectAllBtn;

	private Button okBtn;

	private String medicalRecordId, patientId;
	private String medicalDateRecordUrl = "shlc/doctor/diseases?medicalRecordId=";
	private String medicalRecordUrl = "shlc/doctor/prescriptions?medicalRecordId=";
	private String http;
	private static String httpUrl;
	private JSONArray jaMedicalRecords;
	private JSONObject[] joMedicalRecords;

	HistoryMedicineItemBaseAdapter historyMedicineItemBaseAdapter;
	private String selectedDate = "";
	private Button backBtn;
	private ToastCommom toastCommom;

	String getHistoryPrescriptionsUrl = "shlc/doctor/prescriptionRecord/";
	String getHistoryPrescriptionIdsUrl = "shlc/doctor/medicalRecords/patient/";
	private String formats = "yyyy-MM-dd HH:mm:ss";
	ArrayList<HashMap<String, String>> historyPreTimeList = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> historyPreTimeMap = new HashMap<String, String>();
	ArrayList<HashMap<String, String>> historyPreList = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> historyPreMap = new HashMap<String, String>();
	ArrayList<String> historyPreIdList = new ArrayList<String>();
	JSONArray selected_lishi_pre;
	ArrayList<HashMap<String, String>> selected_lishi_pre_list = new ArrayList<HashMap<String, String>>();
	//TODO 获取某个日期的历史处方时怎么没传入日期？？？

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_medicine);
		MyActivityManager.pushOneActivity(HistoryPrescriptionActivity.this);
		toastCommom = ToastCommom.createToastConfig();
		myApp = (MyApp)getApplication();
		medicalRecordId = myApp.getMedicalRecordId();
		http = myApp.getHttp();
		patientId = myApp.getPatientId();


		historyDateFlowLayout = (FlowLayout)findViewById(R.id.history_medicine_date_flowlayout);
		oneSearchHistoryMedicineListView = (ListView)findViewById(R.id.history_medicine_list);

		selectAllBtn = (Button)findViewById(R.id.history_medicine_all_btn);
		selectBtn = (Button)findViewById(R.id.history_medicine_select_btn);
		okBtn = (Button)findViewById(R.id.history_medicine_ok_btn);
		backBtn = (Button)findViewById(R.id.back_btn);
		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});


		selectBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if(oneSelectedHistoryMedicineLists != null && !oneSelectedHistoryMedicineLists.isEmpty()){

					for(HashMap<String,String>map:oneSelectedHistoryMedicineLists){
						map.put("selected","false");
					}
//					selectedHistoryMedicineLists.addAll(oneSelectedHistoryMedicineLists);
//					Intent  intent = new Intent();
					Bundle bundle=new Bundle();

					bundle.putParcelableArrayList("list", (ArrayList)oneSelectedHistoryMedicineLists);


					PrescriptionTab  mTabMainActivity = (PrescriptionTab) getParent();
					Intent mIntent = getIntent();
					mIntent.putExtras(bundle);
					mTabMainActivity.setResult(RESULT_OK, mIntent);


					finish();
				}else{
//					Toast.makeText(HistoryMedicineActivity.this,"请选择处方",Toast.LENGTH_LONG).show();
					toastCommom.ToastShow(HistoryPrescriptionActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "请选择处方");
				}
			}
		});
		selectAllBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(selected_lishi_pre_list != null && !selected_lishi_pre_list.isEmpty()){

					for(HashMap<String,String>map:selected_lishi_pre_list){
						map.put("selected","false");
					}
//					selectedHistoryMedicineLists.addAll(oneSearchHistoryMedicineLists);
					Intent intent = new Intent();
					Bundle bundle=new Bundle();

					bundle.putParcelableArrayList("history", (ArrayList)selected_lishi_pre_list);
					intent.putExtras(bundle);
					//设置返回数据
					setResult(RESULT_OK, intent);

					finish();

				}else{
//					Toast.makeText(HistoryMedicineActivity.this,"请选择处方",Toast.LENGTH_LONG).show();
					toastCommom.ToastShow(HistoryPrescriptionActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "请选择处方");
				}
			}
		});

		okBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
//				if(selectedHistoryMedicineLists != null && !selectedHistoryMedicineLists.isEmpty()){
					//数据是使用Intent返回
					Intent intent = new Intent();
					Bundle bundle=new Bundle();

					bundle.putParcelableArrayList("history", (ArrayList)selected_lishi_pre_list);
					intent.putExtras(bundle);
					//设置返回数据
					setResult(RESULT_OK, intent);

					finish();
//				}
			}
		});




		getHistoryPrescriptionIds();
		initHistoryDateChildViews();


	}
	//=========================获取历史用药日期historyPreTimeList以及历史用药historyPreList=================================


	private void getHistoryPrescriptionIds(){
		JSONObject joRev = new JSONObject();
		try {
			joRev = HttpUtil.getResultForHttpGet(http + getHistoryPrescriptionIdsUrl + patientId);
            Log.i(TAG, "getHistoryPrescriptionIds url:" + http + getHistoryPrescriptionIdsUrl + patientId);
			Log.i(TAG, "getHistoryPrescriptionIds:"+ joRev.toString());
			JSONArray jaValue = joRev.getJSONArray("value");
			Log.i(TAG, "getHistoryPrescriptions jaValue:"+ jaValue.toString());
			for(int i=0; i<jaValue.length(); i++){
				JSONObject joMed = jaValue.getJSONObject(i);
				JSONArray jaPre = joMed.getJSONArray("prescriptionRecords");
				for(int j=0; j<jaPre.length(); j++){
					JSONObject joPre = jaPre.getJSONObject(j);
					long preId = joPre.getLong("id");
					historyPreIdList.add(String.valueOf(preId));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		getHistoryPrescriptions();
	}

	private void getHistoryPrescriptions(){
		/*
		for(int i=0; i<historyPreIdList.size(); i++){
			try {
				JSONObject joRev = HttpUtil.getResultForHttpGet(http + getHistoryPrescriptionsUrl + historyPreIdList.get(i));
				Log.i(TAG, "getHistoryPrescriptions:"+ joRev.toString());
				JSONObject joValue = joRev.getJSONObject("value");
				String preId = String.valueOf(joValue.getLong("id"));
				JSONArray jaPre = joValue.getJSONArray("prescriptions");
				c
				String date = TimeStamp.TimeStamp2Date(joValue.getLong("lastModified"), formats);
				historyPreTimeMap.clear();
				historyPreTimeMap.put("preId", preId);
				historyPreTimeMap.put("date", date);
				historyPreTimeList.add(historyPreTimeMap);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		*/
		historyPreList = new ArrayList<HashMap<String, String>>(new HashSet<HashMap<String, String>>(historyPreList));
		historyPreTimeList = new ArrayList<HashMap<String, String>>(new HashSet<HashMap<String, String>>(historyPreTimeList));



		//////////////////////TODO 测试//////////////////////////////////////////////////////////////

//		historyPreTimeMap.clear();
//		historyPreTimeMap.put("preId", "1");
//		historyPreTimeMap.put("date", "2016-07-06");
//		historyPreTimeList.add(historyPreTimeMap);

		historyPreTimeMap.clear();
		historyPreTimeMap.put("preId", "2");
		historyPreTimeMap.put("date", "2016-07-06");
		historyPreTimeList.add(historyPreTimeMap);


		/////////////////////////////////////////////////////////////////////////////////////
		Log.i(TAG, "historyPreList:"+historyPreList.toString());
		Log.i(TAG, "historyPreTimeList:" + historyPreTimeList.toString());

	}

	//初始化历史日期的流式布局
	private void initHistoryDateChildViews() {
		// TODO Auto-generated method stub

		if(historyDateFlowLayout.getChildCount() > 0){
			historyDateFlowLayout.removeAllViews();
		}

		ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = 5;
		lp.rightMargin = 5;
		lp.topMargin = 5;
		lp.bottomMargin = 5;
		if(historyPreTimeList == null || historyPreTimeList.isEmpty()){
			return;
		}
		Log.i(TAG, "initHistoryDateChildViews selectedDate " + selectedDate);
		for(int i = 0; i < historyPreTimeList.size(); i ++){
			final TextView view = new TextView(this);
			final HashMap<String, String > map = historyPreTimeList.get(i);
			final String preIdstr = map.get("preId");
			final String dateStr = map.get("date");
			final String result = "处方号:"+preIdstr+" 日期:"+dateStr;
			view.setText(result);
			Log.i(TAG, "initHistoryDateChildViews result" + result);
			view.setTextSize(12);
			if(result.equals(selectedDate)){
				view.setTextColor(Color.WHITE);
				view.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_flow_shape_pressed));

				show_lishi_pre(preIdstr);

			}else{
				view.setTextColor(Color.BLACK);
				view.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_flow_shape_unpressed));
			}

			Log.i("GXF", "view flow onclick i " + i);
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					selectedDate = result;
					view.setTextColor(Color.WHITE);
					view.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_flow_shape_pressed));
					//TODO 其他的view需要设置未选中状态
					initHistoryDateChildViews();


					Log.i("GXF", "view flow onclick date " + date);
				}
			});
			historyDateFlowLayout.addView(view, lp);
		}
	}

	void show_lishi_pre(String lishi_preId){

		selected_lishi_pre_list.clear();

		///////////////////////TODO 测试////////////////////////////////////////////////////
		HashMap<String, String> selected_map = new HashMap<String, String>();

		selected_map.put("id", "11");
		selected_map.put("description","description");
		//selected_map.put("pingjunyongliang", joSelected.getString("frequency"));
		selected_map.put("pingjunyongliang", "mcyl");
		//Log.i(TAG, "pingjunyongliang" + joSelected.getString("frequency"));
		selected_map.put("tianshu", "7");
		selected_map.put("cishu", "2");
		selected_map.put("shuliang", "10");


		selected_map.put("pc", "BID");
		selected_map.put("frequencyType", "BID");
		selected_map.put("count", "10");

		selected_map.put("jiage","20");



		selected_map.put("zhuangtai","1");
		selected_map.put("drugName", "测试");
		selected_map.put("yizhu", "测试");

		selected_map.put("presJO", "测试");
		selected_map.put("selected", "false");

		selected_map.put("drugId", "1");
		selected_map.put("name", "测试");
		selected_map.put("guige", "3ml/瓶");
		selected_map.put("dj","15");
		selected_lishi_pre_list.add(selected_map);


		///////////////////////////////////////////////////////////////////////////////////////////

		//TODO 后期打开
//		for(int i=0; i<historyPreList.size(); i++){
//			HashMap<String, String> map = historyPreList.get(i);
//			if(map.get("preId").equals(lishi_preId)){
//				try {
//					selected_lishi_pre = new JSONArray(map.get("jaPre"));
//					Log.i(TAG, "selected_lishi_pre: "+selected_lishi_pre.toString());
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		for(int i=0; i<selected_lishi_pre.length(); i++){
//			try {
//				HashMap<String, String> selected_map = new HashMap<String, String>();
//				JSONObject joSelected = selected_lishi_pre.getJSONObject(i);
//				Log.i(TAG, "joSelected:" + joSelected.toString());
//				selected_map.put("id", String.valueOf(joSelected.getLong("id")));
//				selected_map.put("description",joSelected.getString("description"));
//				//selected_map.put("pingjunyongliang", joSelected.getString("frequency"));
//                selected_map.put("pingjunyongliang", joSelected.getJSONObject("drugStore").getJSONObject("drug").getString("mcyl"));
//				//Log.i(TAG, "pingjunyongliang" + joSelected.getString("frequency"));
//				selected_map.put("tianshu", String.valueOf(joSelected.getLong("days")));
//				Log.i(TAG, "tianshu" + joSelected.getLong("days"));
//                selected_map.put("cishu", String.valueOf(joSelected.getLong("count")));
//				selected_map.put("shuliang", String.valueOf(joSelected.getLong("count") * joSelected.getLong("days")));
//				Log.i(TAG, "shuliang" + joSelected.getLong("count"));
//				selected_map.put("dj", String.valueOf(TimeStamp.get2Double(joSelected.getJSONObject("drugStore").getLong("dj") / 1000.00)));
//				Log.i(TAG, "dj" + joSelected.getJSONObject("drugStore").getLong("dj"));
//				Log.i(TAG, "pc"+joSelected.getString("pc"));
//				selected_map.put("pc", joSelected.getString("pc"));
//                selected_map.put("frequencyType", joSelected.getString("pc"));
//				selected_map.put("count", String.valueOf(joSelected.getLong("count")));
//                /*
//				double yongliang_meitian = 0;
//				if(joSelected.getString("pc").equals("QD")){
//					yongliang_meitian = Double.parseDouble(joSelected.getString("frequency"));
//					selected_map.put("cishu", "1");
//				}else {
//					String[] fre = joSelected.getString("frequency").split(",");
//
//					for(int j=0; j<fre.length; j++) {
//						String str_fre = fre[j];
//						Log.i(TAG, "str_fre:" + str_fre);
//						double double_fre = Double.parseDouble(str_fre);
//						Log.i(TAG, "double_fre:" + double_fre);
//						yongliang_meitian += double_fre;
//					}
//					selected_map.put("cishu", String.valueOf(fre.length));
//				}
//				Log.i(TAG, "yongliang_meitian:" + yongliang_meitian);
//				*/
//				double jiage = joSelected.getJSONObject("drugStore").getLong("dj")/1000.00*joSelected.getLong("days")*joSelected.getLong("count");
//				Log.i(TAG, "jiage:"+jiage);
//				selected_map.put("jiage", String.valueOf(TimeStamp.get2Double(jiage)));
//				JSONObject joDrug = joSelected.getJSONObject("drugStore").getJSONObject("drug");
//				Log.i(TAG, "joDrug:"+joDrug.toString());
//				selected_map.put("drugId", String.valueOf(joDrug.getLong("id")));
//				Log.i(TAG, "drugId:" + joDrug.getLong("id"));
//				selected_map.put("zhuangtai", String.valueOf(joDrug.getLong("id")));
//				selected_map.put("drugName", joDrug.getString("name"));
//                selected_map.put("yizhu", joDrug.getString("name"));
//				Log.i(TAG, "drugName:" + joDrug.getString("name"));
//				selected_map.put("presJO", joSelected.toString());
//				selected_map.put("selected", "false");
//				selected_lishi_pre_list.add(selected_map);
//				Log.i(TAG,"add done");
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//		}
		selected_lishi_pre_list = new ArrayList<HashMap<String, String>>(new HashSet<HashMap<String, String>>(selected_lishi_pre_list));



		historyMedicineItemBaseAdapter = new HistoryMedicineItemBaseAdapter(HistoryPrescriptionActivity.this,
				selected_lishi_pre_list, R.layout.medicine_title_1, new String[]{"xuhao","yizhu","pingjunyongliang","cishu","tianshu","shuliang","jiage","layout"},
				new int[]{R.id.xuhao_tv, R.id.yizhu_tv,R.id.pingjunyongliang_tv,R.id.cishu_tv,R.id.tianshu_tv,R.id.shuliang_tv,R.id.jiage_tv,R.id.current_medicine_item_layout});
		oneSearchHistoryMedicineListView.setAdapter(historyMedicineItemBaseAdapter);

		oneSearchHistoryMedicineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

				final HashMap<String, String> map = (HashMap<String, String>) adapterView.getItemAtPosition(i);

				if (map.get("selected").equals("false")) {
					map.put("selected", "true");
					oneSelectedHistoryMedicineLists.add(map);
				} else {
					oneSelectedHistoryMedicineLists.remove(map);
					map.put("selected", "false");
				}


				historyMedicineItemBaseAdapter.notifyDataSetChanged();
			}
		});
	}

	class HistoryMedicineItemBaseAdapter extends BaseAdapter {
		private class ButtonViewHolder{
			TextView xuhaoTV;
			TextView yizhuTV;
			TextView pingjunyongliangTV;
			TextView cishuTV;
			TextView tianshuTV;
			TextView shuliangTV;
			TextView jiageTV;
			LinearLayout layout;
		}

		private ArrayList<HashMap<String, String>> mAppList;
		private LayoutInflater mInflater;
		private Context mContext;
		private String[] keyString;
		private int[] valueViewID;
		private ButtonViewHolder holder;
		//private OnHMListener mCallBack;
		public HistoryMedicineItemBaseAdapter(Context c,
											  ArrayList<HashMap<String, String>> appList,
											  int resource,
											  String[] from, int[] to) {
			mAppList = appList;
			mContext = c;
			mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			keyString = new String[from.length];
			valueViewID = new int[to.length];
			System.arraycopy(from, 0, keyString, 0, from.length);
			System.arraycopy(to, 0, valueViewID, 0, to.length);

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mAppList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mAppList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView != null) {
				holder = (ButtonViewHolder) convertView.getTag();
			} else {
				convertView = mInflater.inflate(R.layout.medicine_title_1, null);
				holder = new ButtonViewHolder();
//				holder.zhuangtaiTV = (TextView)convertView.findViewById(valueViewID[0]);
				holder.xuhaoTV = (TextView)convertView.findViewById(valueViewID[0]);
				holder.yizhuTV = (TextView)convertView.findViewById(valueViewID[1]);
				holder.pingjunyongliangTV = (TextView)convertView.findViewById(valueViewID[2]);
				holder.cishuTV = (TextView)convertView.findViewById(valueViewID[3]);
				holder.tianshuTV = (TextView)convertView.findViewById(valueViewID[4]);
				holder.shuliangTV = (TextView)convertView.findViewById(valueViewID[5]);
				holder.jiageTV = (TextView)convertView.findViewById(valueViewID[6]);
				holder.layout = (LinearLayout)convertView.findViewById(valueViewID[7]);
				convertView.setTag(holder);
			}
//			holder.jiageTV.setVisibility(View.GONE);
			Log.i(TAG,"adapter mAppList "+mAppList);
			if(mAppList == null && mAppList.isEmpty()){

				return convertView;
			}
			HashMap<String, String> appInfo = mAppList.get(position);
			Log.i(TAG,"adapter appInfo "+appInfo);
			if (appInfo != null) {
//				String str0 = (String) appInfo.get(keyString[0]);
//				holder.zhuangtaiTV.setText(str0);
				int pos = position+1;
				holder.xuhaoTV.setText(""+pos);
				String str1 = (String) appInfo.get(keyString[1]);
				holder.yizhuTV.setText(str1);
				String str2 = (String) appInfo.get(keyString[2]);
				holder.pingjunyongliangTV.setText(str2);
				String str3 = (String) appInfo.get(keyString[3]);
				holder.cishuTV.setText(str3);
				String str4 = (String) appInfo.get(keyString[4]);
				holder.tianshuTV.setText(str4);
				String str5 = (String) appInfo.get(keyString[5]);
				holder.shuliangTV.setText(str5);
				holder.jiageTV.setText((String) appInfo.get(keyString[6]));

				if(appInfo.get("selected").equals("true")){
					//如果选中则将字体变成白色，背景变成绿色
					holder.layout.setBackgroundColor(getResources().getColor(R.color.background_green));
					holder.xuhaoTV.setTextColor(getResources().getColor(R.color.white));
					holder.yizhuTV.setTextColor(getResources().getColor(R.color.white));
					holder.pingjunyongliangTV.setTextColor(getResources().getColor(R.color.white));
					holder.cishuTV.setTextColor(getResources().getColor(R.color.white));
					holder.tianshuTV.setTextColor(getResources().getColor(R.color.white));
					holder.shuliangTV.setTextColor(getResources().getColor(R.color.white));
					holder.jiageTV.setTextColor(getResources().getColor(R.color.white));
				}else{
					//如果没选中，则字体是黑色，背景是白色

					holder.layout.setBackgroundColor(getResources().getColor(R.color.white));
					holder.xuhaoTV.setTextColor(getResources().getColor(R.color.black));
					holder.yizhuTV.setTextColor(getResources().getColor(R.color.black));
					holder.pingjunyongliangTV.setTextColor(getResources().getColor(R.color.black));
					holder.cishuTV.setTextColor(getResources().getColor(R.color.black));
					holder.tianshuTV.setTextColor(getResources().getColor(R.color.black));
					holder.shuliangTV.setTextColor(getResources().getColor(R.color.black));
					holder.jiageTV.setTextColor(getResources().getColor(R.color.black));
				}
			}
			return convertView;
		}



	}


}
