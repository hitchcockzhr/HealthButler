package com.housekeeper.ar.healthhousekeeper.followup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.RoundImageView;

import java.util.HashMap;
import java.util.List;

public class FollowUpPatientAdapter extends BaseAdapter {
	private Context context;
	public List<HashMap<String,String>> data;

	String TAG = "FollowUpPatientAdapter";
	float x_temp01 = 0.0f;
	float y_temp01 = 0.0f;
	float x_temp02 = 0.0f;
	float y_temp02 = 0.0f;



	public FollowUpPatientAdapter(Context context, List<HashMap<String, String>> data) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_follow_up_patient_content_item, null);
			viewHolder = new ViewHolder();
			viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.name_tv);
			viewHolder.typeTextView = (TextView) convertView.findViewById(R.id.type_tv);
			viewHolder.deleteBtn = (Button)convertView.findViewById(R.id.delete_btn);
			viewHolder.deleteLayout = (LinearLayout)convertView.findViewById(R.id.delete_layout);



			viewHolder.deleteLayout.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					//获得当前坐标
					float x = event.getX();
					float y = event.getY();
					Log.i(TAG,"event.getAction() "+event.getAction());
					switch(event.getAction())
					{
						case MotionEvent.ACTION_DOWN:
						{
							x_temp01 = x;
							y_temp01 = y;
						}
						break;
						case MotionEvent.ACTION_UP:
						{
						/*	x_temp02 = x;
							y_temp02 = y;
							Log.i(TAG,"x_temp02 "+x_temp02);
							Log.i(TAG,"x_temp01 "+x_temp01);
							Log.i(TAG,"y_temp02 "+y_temp02);
							Log.i(TAG,"y_temp01 "+y_temp01);
							if(x_temp01!=0 && y_temp01!=0)//
							{
								// 比较x_temp01和x_temp02
								// x_temp01>x_temp02         //向左
								// x_temp01==x_temp02         //竖直方向或者没动
								// x_temp01<x_temp02         //向右

								if(x_temp01>x_temp02)//向左
								{
									//移动了x_temp01-x_temp02
									Log.i(TAG,"向左");
									viewHolder.deleteBtn.setVisibility(View.VISIBLE);
								}

								if(x_temp01<x_temp02)//向右
								{
									Log.i(TAG,"向右");
									//移动了x_temp02-x_temp01
									viewHolder.deleteBtn.setVisibility(View.GONE);
								}
							}*/
						}
						break;
						case MotionEvent.ACTION_MOVE:
						{

							x_temp02 = x;
							y_temp02 = y;
							Log.i(TAG,"x_temp02 "+x_temp02);
							Log.i(TAG,"x_temp01 "+x_temp01);
							Log.i(TAG,"y_temp02 "+y_temp02);
							Log.i(TAG,"y_temp01 "+y_temp01);
							if(x_temp01!=0 && y_temp01!=0)//
							{
								// 比较x_temp01和x_temp02
								// x_temp01>x_temp02         //向左
								// x_temp01==x_temp02         //竖直方向或者没动
								// x_temp01<x_temp02         //向右

								if(x_temp01>x_temp02)//向左
								{
									//移动了x_temp01-x_temp02
									Log.i(TAG,"向左");
									viewHolder.deleteBtn.setVisibility(View.VISIBLE);
								}

								if(x_temp01<x_temp02)//向右
								{
									Log.i(TAG,"向右");
									//移动了x_temp02-x_temp01
									viewHolder.deleteBtn.setVisibility(View.GONE);
								}
							}
						}
						break;

					}
					return true;
				}
			});




			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		Log.i(TAG, "data " + data.toString());
		viewHolder.nameTextView.setText(data.get(position).get("name"));
		viewHolder.typeTextView.setText(data.get(position).get("type"));
//		viewHolder.deleteBtn.setVisibility(View.GONE);

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
		RoundImageView headerImage;

		TextView nameTextView,typeTextView;
		Button deleteBtn;
		LinearLayout deleteLayout;

	}

}
