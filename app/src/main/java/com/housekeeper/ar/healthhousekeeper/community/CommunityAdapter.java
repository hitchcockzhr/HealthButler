package com.housekeeper.ar.healthhousekeeper.community;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.RoundImageView;

import java.util.HashMap;
import java.util.List;

public class CommunityAdapter extends BaseAdapter {
	private Context context;
	public List<HashMap<String,String>> data;

	String TAG = "CommunityAdapter";



	private int flag = 0;

	public CommunityAdapter(Context context, List<HashMap<String, String>> data,int flag) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_community_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.name_tv);
			viewHolder.addressTextView = (TextView)convertView.findViewById(R.id.address_tv);
			viewHolder.cancelBtn = (Button)convertView.findViewById(R.id.cancel_btn);

			viewHolder.okBtn = (Button)convertView.findViewById(R.id.ok_btn);

			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		Log.i(TAG, "data " + data.toString());
		viewHolder.nameTextView.setText(data.get(position).get("name"));
		viewHolder.addressTextView.setText(data.get(position).get("address"));



		viewHolder.cancelBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				data.remove(data.get(position));
				notifyDataSetChanged();

			}
		});





		viewHolder.okBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if(flag == 0){

					//社区
					CommunityActivity activity1 = (CommunityActivity)context;
					AddCommmunityTab mTabMainActivity = (AddCommmunityTab) activity1.getParent();
					Intent mIntent = activity1.getIntent();
					mIntent.putExtra("name", data.get(position).get("name"));
					mIntent.putExtra("address",data.get(position).get("address"));
					mIntent.putExtra("type",""+flag);
					mTabMainActivity.setResult(activity1.RESULT_OK, mIntent);

					Log.i(TAG,"CommunityActivity");
					activity1.finish();
				}else{
					HospitalActivity activity1 = (HospitalActivity)context;
					AddCommmunityTab mTabMainActivity = (AddCommmunityTab) activity1.getParent();
					Intent mIntent = activity1.getIntent();
					mIntent.putExtra("name",data.get(position).get("name"));
					mIntent.putExtra("address",data.get(position).get("address"));
					mIntent.putExtra("type",""+flag);
					mTabMainActivity.setResult(activity1.RESULT_OK, mIntent);
					Log.i(TAG, "HospitalActivity");
					activity1.finish();
				}




			}
		});

		return convertView;
	}

	class ViewHolder{
		RoundImageView headerImage;

		TextView nameTextView,addressTextView;
		Button okBtn,cancelBtn;


	}

}
