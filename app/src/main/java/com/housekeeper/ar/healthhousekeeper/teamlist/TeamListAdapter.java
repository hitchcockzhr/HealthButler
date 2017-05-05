package com.housekeeper.ar.healthhousekeeper.teamlist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.RoundImageView;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import java.util.HashMap;
import java.util.List;

public class TeamListAdapter extends BaseAdapter {
	private Context context;
	public List<HashMap<String,String>> data;

	String TAG = "TeamListAdapter";


	private ToastCommom toastCommom;

	public TeamListAdapter(Context context, List<HashMap<String, String>> data) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.data = data;
		toastCommom = ToastCommom.createToastConfig();
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
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_team_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.name_tv);
			viewHolder.sexTextView = (TextView)convertView.findViewById(R.id.sex_tv);
			viewHolder.ageTextView = (TextView)convertView.findViewById(R.id.age_tv);
			viewHolder.headerImage = (RoundImageView)convertView.findViewById(R.id.header_image);
			viewHolder.telTextView = (TextView)convertView.findViewById(R.id.tel_tv);
			viewHolder.typeTextView=(TextView)convertView.findViewById(R.id.type_tv);

			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		Log.i(TAG,"data "+ data.toString());
		viewHolder.nameTextView.setText(data.get(position).get("name"));
		viewHolder.sexTextView.setText(data.get(position).get("sex"));
		viewHolder.ageTextView.setText(data.get(position).get("age") + "岁");
		viewHolder.telTextView.setText(data.get(position).get("tel"));
		viewHolder.typeTextView.setText(data.get(position).get("type"));

		return convertView;
	}

	class ViewHolder{
		RoundImageView headerImage;

		TextView nameTextView,sexTextView,ageTextView,telTextView,typeTextView;


	}

}
