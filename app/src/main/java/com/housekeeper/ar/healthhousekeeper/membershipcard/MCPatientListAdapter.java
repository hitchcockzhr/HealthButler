package com.housekeeper.ar.healthhousekeeper.membershipcard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.RoundImageView;

import java.util.HashMap;
import java.util.List;

public class MCPatientListAdapter extends BaseAdapter {
	private Context context;
	public List<HashMap<String,String>> data;

	String TAG = "PatientListAdapter";

	//标记是医院Tab还是病人Tab
    String flag = "hospitalTab";
	MyApp myApp;

	public MCPatientListAdapter(Context context,MyApp myApp, List<HashMap<String, String>> data,String flag) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.data = data;
		this.flag = flag;
		this.myApp = myApp;
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

	@Override
	public View getView( final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.guidance_patient_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.name_tv);
			viewHolder.sexTextView = (TextView)convertView.findViewById(R.id.sex_tv);
			viewHolder.ageTextView = (TextView)convertView.findViewById(R.id.age_tv);
			viewHolder.headerImage = (RoundImageView)convertView.findViewById(R.id.header_image);
			viewHolder.binghaoTextView = (TextView)convertView.findViewById(R.id.binghao_tv);
			viewHolder.telTextView = (TextView)convertView.findViewById(R.id.tel_tv);

			viewHolder.telImageView = (ImageView)convertView.findViewById(R.id.tel_image);
			viewHolder.messageImageView = (ImageView)convertView.findViewById(R.id.message_image);
			viewHolder.weixinImageView = (ImageView)convertView.findViewById(R.id.weixin_image);

			viewHolder.yuyueBtn = (Button)convertView.findViewById(R.id.yuyue_btn);
			viewHolder.huiyuankaBtn = (Button)convertView.findViewById(R.id.huiyuanka_btn);



			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		Log.i(TAG,"data "+ data.toString());
		viewHolder.nameTextView.setText(data.get(position).get("name"));
		viewHolder.sexTextView.setText(data.get(position).get("sex"));
		viewHolder.ageTextView.setText(data.get(position).get("age"));
		viewHolder.telTextView.setText(data.get(position).get("tel"));
		viewHolder.binghaoTextView.setText(data.get(position).get("binghao"));

		viewHolder.yuyueBtn.setVisibility(View.GONE);
//		viewHolder.yuyueBtn.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//
//				Intent intent = new Intent();
//				PatientListActivity activity = (PatientListActivity) context;
//				Bundle bundle = new Bundle();
//				bundle.putString("name", data.get(position).get("name"));
//				bundle.putString("sex", data.get(position).get("sex"));
//				bundle.putString("age", data.get(position).get("age"));
//				bundle.putString("tel", data.get(position).get("tel"));
//				bundle.putString("binghao", data.get(position).get("binghao"));
//				bundle.putString("patientID", data.get(position).get("patientID"));
////				bundle.putString("header",data.get(position).get("header"));
//				intent.putExtras(bundle);
//
//				activity.setResult(activity.RESULT_OK, intent);
//				activity.finish();
//
//			}
//		});

		viewHolder.huiyuankaBtn.setText("确定");
		viewHolder.huiyuankaBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if(flag.equals("hospitalTab")){
					Intent intent = new Intent();
					MCHospitalPatientListActivity activity = (MCHospitalPatientListActivity)context;
					Bundle bundle = new Bundle();
					bundle.putString("name",data.get(position).get("name"));
					bundle.putString("sex",data.get(position).get("sex"));
					bundle.putString("age",data.get(position).get("age"));
					bundle.putString("tel",data.get(position).get("tel"));
					bundle.putString("binghao",data.get(position).get("binghao"));
					bundle.putString("patientID",data.get(position).get("patientID"));

					intent.putExtras(bundle);

					activity.setResult(activity.RESULT_OK, intent);
					activity.finish();
				}else{
					Intent intent = new Intent(context,MCPatientTabPerMCActivity.class);
					Bundle bundle = new Bundle();
					myApp.setPatientId(data.get(position).get("patientID"));
					bundle.putString("name",data.get(position).get("name"));
					bundle.putString("sex",data.get(position).get("sex"));
					bundle.putString("age",data.get(position).get("age"));
					bundle.putString("tel", data.get(position).get("tel"));
					intent.putExtras(bundle);
					context.startActivity(intent);
				}

			}
		});

		viewHolder.weixinImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		viewHolder.telImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});


		viewHolder.messageImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});


		return convertView;
	}

	class ViewHolder{
		RoundImageView headerImage;

		TextView nameTextView,sexTextView,ageTextView,telTextView,binghaoTextView;
		ImageView telImageView,weixinImageView,messageImageView;
		Button yuyueBtn,huiyuankaBtn;

	}

}
