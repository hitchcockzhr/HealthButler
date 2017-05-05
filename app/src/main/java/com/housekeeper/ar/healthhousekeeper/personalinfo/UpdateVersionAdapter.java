package com.housekeeper.ar.healthhousekeeper.personalinfo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class UpdateVersionAdapter extends BaseAdapter{
	String TAG = "UpdateVersionAdapter";
	private String cancelDdUrlString = "shlc/patient/cancel/medicalRecord/";
	private Context context;
	private String http, httpUrl;
	List<HashMap<String,String>> data;
	String scheduleId;
	String medicalRecordId;
	private JSONObject joRev;
	private MyApp myApp;
	public UpdateVersionAdapter(Context context, List<HashMap<String, String>> data, String http, MyApp myApp, String medicalRecordId) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.data = data;
		this.http = http;
		this.myApp = myApp;
		this.medicalRecordId = medicalRecordId;
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


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.version_update_activity_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.typeTextView = (TextView) convertView.findViewById(R.id.new_version_type);
			viewHolder.contentTextView = (TextView)convertView.findViewById(R.id.new_version_content);

			convertView.setTag(viewHolder);

		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		Log.v("djyAdapter", data.toString());
		viewHolder.typeTextView.setText(data.get(position).get("content"));
		int prePosition = position - 1;
		if(prePosition >= 0 && prePosition < data.size()){
			//如果已经显示了该
			// 分类，往后该分类都不显示，统一在一个TextView
			String preType = data.get(prePosition).get("type");
			String type = data.get(position).get("type");
			if (preType.equals(type)){
				viewHolder.typeTextView.setVisibility(View.GONE);
			}else{
				viewHolder.typeTextView.setVisibility(View.VISIBLE);
				viewHolder.typeTextView.setText(data.get(position).get("type"));
			}
		}else{
			viewHolder.typeTextView.setVisibility(View.VISIBLE);
			viewHolder.typeTextView.setText(data.get(position).get("type"));
		}

		return convertView;
	}

	class ViewHolder{
		TextView typeTextView, contentTextView;

		/*
		public ViewHolder(View view){
			hospitalTextView = (TextView)view.findViewById(R.id.hospital_tv);
			keshiTextView = (TextView)view.findViewById(R.id.keshi_tv);
			zhichengTextView = (TextView)view.findViewById(R.id.zhicheng_tv);
			yuyueTimeTextView = (TextView)view.findViewById(R.id.yuyue_time_tv);
			nameTextView = (TextView)view.findViewById(R.id.name_tv);
			zhuanchangTextView = (TextView)view.findViewById(R.id.zhuanchang_tv);
			headImageView = (ImageView)view.findViewById(R.id.head_imageview);
			fukuanButton = (Button)view.findViewById(R.id.fukuan_btn);
			cancelDdButton = (Button)view.findViewById(R.id.cancel_dd_btn);
		}
		*/

	}
}
