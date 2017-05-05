package com.housekeeper.ar.healthhousekeeper.followup;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.RoundImageView;

import java.util.HashMap;
import java.util.List;

public class FollowUpListAdapter extends BaseAdapter {
	private Context context;
	public List<HashMap<String,String>> data;

	String TAG = "FollowUpListAdapter";



	public FollowUpListAdapter(Context context, List<HashMap<String, String>> data) {
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
	public void refresh(List<HashMap<String,String>> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	@Override
	public View getView( final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.follow_up_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.name_tv);
			viewHolder.sexTextView = (TextView)convertView.findViewById(R.id.sex_tv);
			viewHolder.ageTextView = (TextView)convertView.findViewById(R.id.age_tv);
			viewHolder.headerImage = (RoundImageView)convertView.findViewById(R.id.header_image);
			viewHolder.binghaoTextView = (TextView)convertView.findViewById(R.id.binghao_tv);
			viewHolder.telTextView = (TextView)convertView.findViewById(R.id.tel_tv);
			viewHolder.signStatusTextView = (TextView)convertView.findViewById(R.id.sign_status_tv);
			viewHolder.lastTimeTextView = (TextView)convertView.findViewById(R.id.last_follow_up_time_tv);

			viewHolder.telImageView = (ImageView)convertView.findViewById(R.id.tel_image);
			viewHolder.messageImageView = (ImageView)convertView.findViewById(R.id.message_image);
			viewHolder.weixinImageView = (ImageView)convertView.findViewById(R.id.weixin_image);

			viewHolder.yuyueBtn = (Button)convertView.findViewById(R.id.yuyue_btn);
			viewHolder.huiyuankaBtn = (Button)convertView.findViewById(R.id.huiyuanka_btn);
			viewHolder.visitBtn = (Button)convertView.findViewById(R.id.visit_btn);

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

		viewHolder.lastTimeTextView.setText(data.get(position).get("lastTime"));
		viewHolder.signStatusTextView.setText(data.get(position).get("contractStatus"));

		viewHolder.yuyueBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				//测试
//				FollowUpClinicActivity activity = (FollowUpClinicActivity)context;
//				Intent intent = new Intent(context, FollowUpPatientActivity.class);
//				myApp.setPatientId(data.get(position).get("id"));
//				intent.putExtra("name", data.get(position).get("name"));
//				intent.putExtra("id", data.get(position).get("id"));
//				intent.putExtra("sex", data.get(position).get("sex"));
//				intent.putExtra("age", data.get(position).get("age"));
//				activity.startActivity(intent);

			}
		});

		viewHolder.huiyuankaBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		viewHolder.visitBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,FUTableVisitActivity.class);
				intent.putExtra("tel",data.get(position).get("tel"));
				context.startActivity(intent);
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

		TextView nameTextView,sexTextView,ageTextView,telTextView,binghaoTextView,lastTimeTextView,signStatusTextView;
		ImageView telImageView,weixinImageView,messageImageView;
		Button yuyueBtn,huiyuankaBtn,visitBtn;

	}

}
