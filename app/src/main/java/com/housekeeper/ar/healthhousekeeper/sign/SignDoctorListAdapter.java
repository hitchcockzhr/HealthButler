package com.housekeeper.ar.healthhousekeeper.sign;

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

import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.RoundImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class SignDoctorListAdapter extends BaseAdapter {
	private Context context;
	public List<HashMap<String,String>> data;

	String TAG = "SignDoctorListAdapter";

	MyApp myApp;



	public SignDoctorListAdapter(Context context, MyApp myapp, List<HashMap<String, String>> data) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.data = data;
		this.myApp = myapp;
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
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_sign_doctor_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.name_tv);
			viewHolder.addressTextView = (TextView)convertView.findViewById(R.id.address_tv);
			viewHolder.hospitalTextView = (TextView)convertView.findViewById(R.id.hospital_tv);
			viewHolder.headerImage = (RoundImageView)convertView.findViewById(R.id.header_image);

			viewHolder.telTextView = (TextView)convertView.findViewById(R.id.tel_tv);
			viewHolder.zhichengTextView = (TextView)convertView.findViewById(R.id.zhicheng_tv);
			viewHolder.keshiTextView = (TextView)convertView.findViewById(R.id.keshi_tv);

			viewHolder.signNumTextView = (TextView)convertView.findViewById(R.id.keqianyue_tv);

			viewHolder.telImageView = (ImageView)convertView.findViewById(R.id.tel_image);
			viewHolder.messageImageView = (ImageView)convertView.findViewById(R.id.message_image);
			viewHolder.weixinImageView = (ImageView)convertView.findViewById(R.id.weixin_image);

			viewHolder.qianyueBtn = (Button)convertView.findViewById(R.id.qianyue_btn);


			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		Log.i(TAG, "data " + data.toString());
		viewHolder.nameTextView.setText(data.get(position).get("name"));
		viewHolder.hospitalTextView.setText(data.get(position).get("hospital"));
		viewHolder.addressTextView.setText(data.get(position).get("address"));
		viewHolder.telTextView.setText(data.get(position).get("tel"));

		viewHolder.keshiTextView.setText(data.get(position).get("keshi"));
		viewHolder.zhichengTextView.setText(data.get(position).get("zhicheng"));

		viewHolder.signNumTextView.setText(data.get(position).get("signNum"));



		viewHolder.qianyueBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,SignActivity.class);

				SignDoctorListActivity activity = (SignDoctorListActivity)context;

				myApp.setDoctorIDString(data.get(position).get("id"));
				myApp.setCityString(data.get(position).get("city"));
				myApp.setYiyuanString(data.get(position).get("hospital"));
				myApp.setYishengString(data.get(position).get("name"));
				myApp.setKeshiString(data.get(position).get("keshi"));

				try {
					JSONObject doctorJson = new JSONObject(data.get(position).get("doctorJson"));
					myApp.setJoDoctor(doctorJson);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				activity.startActivity(intent);
//				activity.finish();

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

		TextView nameTextView,hospitalTextView,addressTextView,telTextView,zhichengTextView,keshiTextView,signNumTextView;
		ImageView telImageView,weixinImageView,messageImageView;
		Button qianyueBtn;

	}

}
