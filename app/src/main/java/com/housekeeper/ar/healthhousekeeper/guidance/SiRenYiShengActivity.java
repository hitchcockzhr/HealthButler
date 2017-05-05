package com.housekeeper.ar.healthhousekeeper.guidance;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SiRenYiShengActivity extends Activity {
	String TAG = "SiRenYiShengActivity";
	ListView jiageListView, youhuiListView;
	private HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyActivityManager.pushOneActivity(SiRenYiShengActivity.this);
		SysApplication.getInstance().addActivity(this);
		setContentView(R.layout.activity_syrs);
		initView();
	}
	private void initView(){
		jiageListView = (ListView)findViewById(R.id.srys_jg_listview);
	//	jiageListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		final JiageRadioAdapter jiageRadioAdapter = new JiageRadioAdapter(this , getJiageData());
		jiageListView.setAdapter(jiageRadioAdapter);
		jiageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				jiageRadioAdapter.setSelectedIndex(i);
				Log.i(TAG, "私人医生 list item onItemClick " + i);
				jiageRadioAdapter.notifyDataSetChanged();

			}
		});

		final YouHuiAdapter youhuiRadioAdapter = new YouHuiAdapter(this , getYouHuiData());
		youhuiListView = (ListView)findViewById(R.id.srys_fw_listview);
		youhuiListView.setAdapter(youhuiRadioAdapter);
		youhuiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				youhuiRadioAdapter.setSelectedIndex(i);
				Log.i(TAG, "私人医生 list item onItemClick " + i);
				youhuiRadioAdapter.notifyDataSetChanged();

			}
		});


		Log.i(TAG, "私人医生 list item jiageListView " + jiageListView);
//		jiageListView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				Log.i(TAG, "私人医生 list item setOnClickListener ");
//			}
//		});

		jiageRadioAdapter.setSelectedIndex(-1);
		youhuiRadioAdapter.setSelectedIndex(-1);

	}

	public List<HashMap<String,String>> getYouHuiData(){
		List<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
		for(int i=0 ; i<3; i++){
			HashMap<String, String> itemHashMap = new HashMap<String, String>();
			if(i==0){
				itemHashMap.put("youhui", "减少30元");
				itemHashMap.put("radio", "select");
			}else if(i==1){
				itemHashMap.put("youhui", "满200减20元");
				itemHashMap.put("radio", "select");
			}else if(i == 2){
				itemHashMap.put("youhui", "满199减100元");
				itemHashMap.put("radio", "select");
			}
			data.add(itemHashMap);
		}
		Log.v(TAG, "jiage List:" + data.toString());
		return data;
	}
	public List<HashMap<String,String>> getJiageData(){
		List<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
		for(int i=0 ; i<3; i++){
			HashMap<String, String> itemHashMap = new HashMap<String, String>();
			if(i==0){
				itemHashMap.put("zhouqi", "两周");
				itemHashMap.put("jiage", "50元");
				itemHashMap.put("radio", "select");
			}else if(i==1){
				itemHashMap.put("zhouqi", "一月");
				itemHashMap.put("jiage", "150元");
				itemHashMap.put("radio", "select");
			}else if(i == 2){
				itemHashMap.put("zhouqi", "十月");
				itemHashMap.put("jiage", "200元");
				itemHashMap.put("radio", "select");
			}
			data.add(itemHashMap);
		}
		Log.v(TAG, "jiage List:" + data.toString());
		return data;
	}
	class  YouHuiAdapter extends BaseAdapter {
		Context context;
		List<HashMap<String,String>> appList;
		int selectedIndex;
		public YouHuiAdapter(Context context,  List<HashMap<String,String>> data) {
			this.context = context;
			appList = data;
		}

		@Override
		public int getCount() {
			return appList.size();
		}

		@Override
		public Object getItem(int i) {
			return appList.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup viewGroup) {
			YouHuiRadioHolder rhHolder;
			if(convertView == null){
				convertView = LayoutInflater.from(context).inflate(R.layout.srys_youhui_item, null);
				rhHolder = new YouHuiRadioHolder(convertView);
				convertView.setTag(rhHolder);
			}else{
				rhHolder = (YouHuiRadioHolder)convertView.getTag();
			}
			rhHolder.radioButton.setChecked(map.get(position) == null ? false : true);
			rhHolder.youhuiTextView.setText(appList.get(position).get("youhui"));


			rhHolder.radioButton.setClickable(false);
			Log.i(TAG, "私人医生 list item position " + position + " selectedIndex " + selectedIndex);
			if(selectedIndex == position){
				//如果已经选中状态，则取消选中，清空检查类别，否则选中，显示类别
				if(rhHolder.radioButton.isChecked()){
					rhHolder.radioButton.setChecked(false);
					//TODO
//					inspectTypeLists.clear();
//					inspectedTypeListItemBaseAdapter.notifyDataSetChanged();
				}else{
//					showAllInspectedTypeList(dialog, appInfo,false);
					rhHolder.radioButton.setChecked(true);

				}


			}else{
				rhHolder.radioButton.setChecked(false);
			}
			return convertView;
		}
		public void setSelectedIndex(int position) {
			this.selectedIndex = position;
			notifyDataSetChanged();
		}
	}
	class JiageRadioAdapter extends BaseAdapter {

		private Context context;
		List<HashMap<String,String>> data;
		private int selectedIndex;//用于listview的radiobutton只能选一个
		public JiageRadioAdapter(Context context,  List<HashMap<String,String>> data){
			this.context = context;
			this.data = data;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		public void setSelectedIndex(int position) {
			this.selectedIndex = position;
			notifyDataSetChanged();
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			JiageRadioHolder rhHolder;
			if(convertView == null){
				convertView = LayoutInflater.from(context).inflate(R.layout.srys_jg_item, null);
				rhHolder = new JiageRadioHolder(convertView);
				convertView.setTag(rhHolder);
			}else{
				rhHolder = (JiageRadioHolder)convertView.getTag();
			}
			rhHolder.radioButton.setChecked(map.get(position) == null ?false:true);
			rhHolder.zhouqiTextView.setText(data.get(position).get("zhouqi"));
			rhHolder.jiageTextView.setText(data.get(position).get("jiage"));

			rhHolder.radioButton.setClickable(false);
			Log.i(TAG, "私人医生 list item position " + position + " selectedIndex " + selectedIndex);
			if(selectedIndex == position){
				//如果已经选中状态，则取消选中，清空检查类别，否则选中，显示类别
				if(rhHolder.radioButton.isChecked()){
					rhHolder.radioButton.setChecked(false);
					//TODO
//					inspectTypeLists.clear();
//					inspectedTypeListItemBaseAdapter.notifyDataSetChanged();
				}else{
//					showAllInspectedTypeList(dialog, appInfo,false);
					rhHolder.radioButton.setChecked(true);

				}


			}else{
				rhHolder.radioButton.setChecked(false);
			}
			return convertView;
		}

	}
	class JiageRadioHolder{
		private RadioButton radioButton;
		private TextView zhouqiTextView, jiageTextView;
		public JiageRadioHolder(View view){
			this.radioButton = (RadioButton)view.findViewById(R.id.srys_jg_radio);
			this.zhouqiTextView = (TextView)view.findViewById(R.id.srys_zq_tv);
			this.jiageTextView = (TextView)view.findViewById(R.id.srys_jg_tv);


		}
	}
	class YouHuiRadioHolder{
		private RadioButton radioButton;
		private TextView youhuiTextView;
		public YouHuiRadioHolder(View view){
			this.radioButton = (RadioButton)view.findViewById(R.id.srys_youhui_radio);
			this.youhuiTextView = (TextView)view.findViewById(R.id.srys_youhui_tv);



		}
	}

}
