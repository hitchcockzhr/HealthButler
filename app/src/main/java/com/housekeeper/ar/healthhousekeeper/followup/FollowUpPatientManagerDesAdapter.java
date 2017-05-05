package com.housekeeper.ar.healthhousekeeper.followup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.R;

import java.util.HashMap;
import java.util.List;

public class FollowUpPatientManagerDesAdapter extends BaseAdapter {
	private Context context;
	public List<HashMap<String,String>> data;

	String TAG = "FollowUpPatientManagerDesAdapter";
	float x_temp01 = 0.0f;
	float y_temp01 = 0.0f;
	float x_temp02 = 0.0f;
	float y_temp02 = 0.0f;



	public FollowUpPatientManagerDesAdapter(Context context, List<HashMap<String, String>> data) {
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
		final ViewHolder viewHolder;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_follow_up_patient_manager_description_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.name_tv);
			viewHolder.typeTextView = (TextView) convertView.findViewById(R.id.type_tv);
			viewHolder.desTextView = (TextView) convertView.findViewById(R.id.des_tv);
			viewHolder.dateTextView = (TextView) convertView.findViewById(R.id.date_tv);
			viewHolder.deleteBtn = (Button)convertView.findViewById(R.id.delete_btn);

			viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

				}
			});




			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		Log.i(TAG, "data " + data.toString());
		viewHolder.nameTextView.setText(data.get(position).get("name"));
		viewHolder.typeTextView.setText(data.get(position).get("type"));
		viewHolder.desTextView.setText(data.get(position).get("des"));
		viewHolder.dateTextView.setText(data.get(position).get("date"));

		viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				data.remove(data.get(position));
//				viewHolder.deleteBtn.setVisibility(View.GONE);
				notifyDataSetChanged();

			}
		});




		return convertView;
	}

	class ViewHolder{
		TextView nameTextView,typeTextView,desTextView,dateTextView;
		Button deleteBtn;

	}

}
