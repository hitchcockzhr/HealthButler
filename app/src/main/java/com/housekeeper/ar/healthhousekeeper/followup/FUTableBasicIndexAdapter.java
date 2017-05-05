package com.housekeeper.ar.healthhousekeeper.followup;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.MyDialog;
import com.housekeeper.ar.healthhousekeeper.R;

import java.util.HashMap;
import java.util.List;

public class FUTableBasicIndexAdapter extends BaseAdapter {
	private Context context;
	public List<HashMap<String,String>> data;

	String TAG = "FUTableBasicIndexAdapter";
	private PopupWindow mPopupWindow;


	public FUTableBasicIndexAdapter(Context context, List<HashMap<String, String>> data) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_follow_up_table_catch_item, null);
			viewHolder = new ViewHolder();
			viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.program_name_tv);
			viewHolder.valueTextView = (TextView)convertView.findViewById(R.id.value_tv);
			viewHolder.unitTextView = (TextView)convertView.findViewById(R.id.unit_tv);
			viewHolder.levelTextView = (TextView)convertView.findViewById(R.id.level_tv);
			viewHolder.rangeTextView = (TextView)convertView.findViewById(R.id.range_tv);
			viewHolder.operateTextView = (TextView)convertView.findViewById(R.id.operate_tv);


			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		Log.i(TAG, "data " + data.toString());
		viewHolder.nameTextView.setText(data.get(position).get("program"));
		viewHolder.valueTextView.setText(data.get(position).get("value"));
		viewHolder.unitTextView.setText(data.get(position).get("unit"));
		viewHolder.rangeTextView.setText(data.get(position).get("range"));

		viewHolder.levelTextView.setText("↑");

		viewHolder.operateTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				getPopupWindowInstance();

				mPopupWindow.showAsDropDown(v, v.getWidth()
						- mPopupWindow.getWidth() - 10, 10);

			}
		});




		return convertView;
	}


	private void showDialog(){
		final View functionListView = LayoutInflater.from(context).inflate(R.layout.activity_membership_dialog_edit_mctype, null);
//		dialog = new AlertDialog.Builder(DiagnosisActivity.this, R.style.load_dialog).setView(functionListView).show();

		MyDialog dialog = new MyDialog(context, functionListView,R.style.load_dialog);
		dialog.show();
		int width = context.getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
//		int height = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
		dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

	}

	private void getPopupWindowInstance() {
		if (null != mPopupWindow) {
			mPopupWindow.dismiss();
			return;
		} else {
			initPopuptWindow();
		}
	}

	/*
     * 创建PopupWindow
     */
	private void initPopuptWindow() {
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View popupWindowView = layoutInflater.inflate(
				R.layout.activity_followup_table_catch_popupwindown, null);
		TextView historyTV = (TextView) popupWindowView
				.findViewById(R.id.history_tv);
		TextView contactDoctorTV = (TextView) popupWindowView
				.findViewById(R.id.contact_doctor_tv);



		historyTV.setOnClickListener(popupWindowClickListener);
		contactDoctorTV.setOnClickListener(popupWindowClickListener);




		// 创建一个PopupWindow
		// 参数1：contentView 指定PopupWindow的内容
		// 参数2：width 指定PopupWindow的width
		// 参数3：height 指定PopupWindow的height
//		mPopupWindow = new PopupWindow(popupWindowView, getWindowManager()
//				.getDefaultDisplay().getWidth() /3, getWindowManager()
//				.getDefaultDisplay().getHeight() / 5);

		mPopupWindow = new PopupWindow(popupWindowView, 200, 300);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

	}


	private View.OnClickListener popupWindowClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.history_tv:


					mPopupWindow.dismiss();
					mPopupWindow = null;
					break;

				case R.id.contact_doctor_tv:


					mPopupWindow.dismiss();
					mPopupWindow = null;

					break;

				default:
					break;
			}

		}
	};
	class ViewHolder{

		TextView nameTextView,valueTextView,unitTextView,levelTextView,rangeTextView,operateTextView;


	}

}
