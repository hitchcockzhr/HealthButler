package com.housekeeper.ar.healthhousekeeper.checkstand;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class ChuFangListAdapter extends BaseAdapter{
	private Context context;
	List<HashMap<String,String>> data;
	String medicalRecordId;

	private String cancelDdUrlString = "shlc/patient/cancel/medicalRecord/";
	private String http, httpUrl;
	private JSONObject joRev;
	public ChuFangListAdapter(Context context, List<HashMap<String, String>> data) {
		// TODO Auto-generated constructor stub
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
	

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_tuiyao_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.numTextView = (TextView) convertView.findViewById(R.id.num_tv);
			viewHolder.nameTextView = (TextView)convertView.findViewById(R.id.name_tv);
			viewHolder.danjiaTextView = (TextView)convertView.findViewById(R.id.danjia_tv);
			viewHolder.idTextView = (TextView)convertView.findViewById(R.id.id_tv);


			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		Log.v("RefundListAdapter", data.toString());


		viewHolder.nameTextView.setText(data.get(position).get("name"));
		viewHolder.numTextView.setText(data.get(position).get("num"));
		viewHolder.danjiaTextView.setText(data.get(position).get("danjia"));
		int pos = position+1;
		viewHolder.idTextView.setText(""+pos);

		return convertView;
	}

	class ViewHolder{
		TextView nameTextView, numTextView,danjiaTextView,idTextView;


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
