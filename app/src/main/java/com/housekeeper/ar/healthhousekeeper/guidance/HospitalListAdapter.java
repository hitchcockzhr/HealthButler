package com.housekeeper.ar.healthhousekeeper.guidance;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.RoundImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HospitalListAdapter extends BaseAdapter {
	private Context context;
	public List<HashMap<String,String>> data;
	public List<String> searchDugs;

	String TAG = "HospitalListAdapter";
	String searchDrugString = "";
	MyApp myApp;


	public HospitalListAdapter(Context context,MyApp myApp, List<HashMap<String, String>> data, List<String> searchDugs) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.data = data;
		this.searchDugs = searchDugs;
		this.myApp = myApp;

		if(searchDugs != null && !searchDugs.isEmpty()){
			for(String string:searchDugs){
				searchDrugString = searchDrugString+string+",";
			}
		}
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
	public void refresh(List<HashMap<String,String>> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	private String preDiffString="";
	@Override
	public View getView( final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_hospital_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.hospitalTextView = (TextView) convertView.findViewById(R.id.hospital_tv);
			viewHolder.addressTextView = (TextView)convertView.findViewById(R.id.hospital_address_tv);
			viewHolder.drugsTextView = (TextView)convertView.findViewById(R.id.drugs_tv);
			viewHolder.diffDrugsTextView = (TextView)convertView.findViewById(R.id.diff_drugs_tv);
//			viewHolder.headerImage = (RoundImageView)convertView.findViewById(R.id.header_image);
			viewHolder.okBtn = (Button)convertView.findViewById(R.id.ok_btn);

			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		Log.i(TAG, "data " + data.toString());
		viewHolder.hospitalTextView.setText(data.get(position).get("hospital"));
		viewHolder.addressTextView.setText(data.get(position).get("address"));
		//获取每个医院在搜索药品列表中缺少的药品
		List<String> diff = getDiff(data.get(position));
		String diffString = "";
		if(diff != null && !diff.isEmpty()){
			for(String str:diff){
				diffString = diffString+str+",";
			}
		}
		Log.i(TAG,"diffString "+diffString);
		Log.i(TAG, "preDiffString " + preDiffString);
		if(position > 0){
			if(diffString.equals(preDiffString)){
				viewHolder.diffDrugsTextView.setVisibility(View.GONE);
			}
		}else {
			viewHolder.diffDrugsTextView.setVisibility(View.VISIBLE);
		}


		if(position == 0){
			viewHolder.drugsTextView.setVisibility(View.VISIBLE);
		}else{
			viewHolder.drugsTextView.setVisibility(View.GONE);
		}
		viewHolder.drugsTextView.setText("搜索药品："+searchDrugString);
		if(diffString.equals("")){
			viewHolder.diffDrugsTextView.setText(" 有全部药品");
		}else{
			viewHolder.diffDrugsTextView.setText(" 缺少药品： "+diffString);
		}


		preDiffString = diffString;

		viewHolder.okBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(context,DoctorListActivity.class);
				HospitalListActivity activity = (HospitalListActivity)context;
				intent.putExtra("hospital",data.get(position).get("hospital"));
				myApp.setYiyuanString(data.get(position).get("hospital"));
				activity.startActivity(intent);


			}
		});

		return convertView;
	}

	class ViewHolder{
		RoundImageView headerImage;

		TextView hospitalTextView,addressTextView,drugsTextView,diffDrugsTextView;
		Button okBtn;


	}
	private  List<String> getDiff(HashMap<String,String> dataMap){
		List<String> diff = new ArrayList<>();
		//医院里面已有的搜索药品
		String drugs = dataMap.get("drugs");
		String[] drugArray = drugs.split(",");
		List<String> drugList = Arrays.asList(drugArray);
		for(String str:searchDugs){
			Log.i(TAG,"getDiff str "+str);
			Log.i(TAG,"getDiff drugList "+drugList.toString());
			if(!drugList.contains(str)){
				Log.i(TAG,"getDiff !drugList.contains(str) ");
				diff.add(str);
			}
		}

		Log.i(TAG,"getDiff difflist "+diff.toString());
		return diff;
	}

}
