package com.housekeeper.ar.healthhousekeeper.sign;

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

import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.RoundImageView;

import java.util.HashMap;
import java.util.List;

public class SignPatientListAdapter extends BaseAdapter {
	private Context context;
	public List<HashMap<String,String>> data;

	String TAG = "SignPatientListAdapter";

	private int flag=0;//0表示未签约，1表示已签约，2表示未完成



	public SignPatientListAdapter(Context context, List<HashMap<String, String>> data, int flag) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.data = data;
		this.flag = flag;
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

			viewHolder.qianyueBtn = (Button)convertView.findViewById(R.id.yuyue_btn);
			viewHolder.xuyueBtn = (Button)convertView.findViewById(R.id.huiyuanka_btn);
			if(flag == 0){
				viewHolder.qianyueBtn.setText("签约");
				viewHolder.xuyueBtn.setVisibility(View.GONE);
			}else if(flag == 1){
				viewHolder.qianyueBtn.setText("签约");
				viewHolder.xuyueBtn.setVisibility(View.VISIBLE);
				viewHolder.xuyueBtn.setText("续约");
			}else if(flag == 2){
				viewHolder.qianyueBtn.setText("签约");
				viewHolder.xuyueBtn.setVisibility(View.GONE);
				viewHolder.xuyueBtn.setText("续约");
			}

			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		Log.i(TAG, "data " + data.toString());
		viewHolder.nameTextView.setText(data.get(position).get("name"));
		viewHolder.sexTextView.setText(data.get(position).get("sex"));
		viewHolder.ageTextView.setText(data.get(position).get("age"));
		viewHolder.telTextView.setText(data.get(position).get("tel"));
		viewHolder.binghaoTextView.setText(data.get(position).get("binghao"));

		viewHolder.qianyueBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				Bundle bundle = new Bundle();
				bundle.putString("name",data.get(position).get("name"));
				bundle.putString("sex",data.get(position).get("sex"));
				bundle.putString("age",data.get(position).get("age"));
				bundle.putString("tel",data.get(position).get("tel"));
				bundle.putString("binghao",data.get(position).get("binghao"));
				bundle.putString("patientID", data.get(position).get("patientID"));
//				bundle.putString("header",data.get(position).get("header"));


				if(flag == 0){
					NotSignPatientListActivity activity = (NotSignPatientListActivity)context;
					SignPatientTabActivity mTabMainActivity = (SignPatientTabActivity) activity.getParent();
					Intent mIntent = activity.getIntent();
					mIntent.putExtras(bundle);
					mTabMainActivity.setResult(activity.RESULT_OK, mIntent);

					activity.finish();
				}else if(flag == 1){
					HasSignedPatientListActivity activity = (HasSignedPatientListActivity)context;
					SignPatientTabActivity mTabMainActivity = (SignPatientTabActivity) activity.getParent();
					Intent mIntent = activity.getIntent();
					mIntent.putExtras(bundle);
					mTabMainActivity.setResult(activity.RESULT_OK, mIntent);
					activity.finish();
				}else if(flag == 2){
					NotFinishPatientListActivity activity = (NotFinishPatientListActivity)context;
					SignPatientTabActivity mTabMainActivity = (SignPatientTabActivity) activity.getParent();
					Intent mIntent = activity.getIntent();
					mIntent.putExtras(bundle);
					mTabMainActivity.setResult(activity.RESULT_OK, mIntent);
					activity.finish();
				}



			}
		});

		viewHolder.xuyueBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO 续约

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
		Button qianyueBtn,xuyueBtn;

	}

}
