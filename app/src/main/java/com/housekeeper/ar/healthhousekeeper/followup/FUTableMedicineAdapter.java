package com.housekeeper.ar.healthhousekeeper.followup;

import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.R;

import java.util.HashMap;
import java.util.List;

public class FUTableMedicineAdapter extends BaseAdapter {
	private Context context;
	public List<HashMap<String,String>> data;

	String TAG = "FUTableMedicineAdapter";
	EditText et1,et2,et3;
	TextView unitTV;
	ViewHolder viewHolder;
	int index=0;
	public FUTableMedicineAdapter(Context context, List<HashMap<String, String>> data) {
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

		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_follow_up_table_medicinelist_item, null);
			viewHolder = new ViewHolder();
			viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.medicine_name);
			viewHolder.yongliangLayout = (LinearLayout)convertView.findViewById(R.id.medicine_yongliang);
//			viewHolder.unitTextView = (TextView)convertView.findViewById(R.id.medicine_unit);

			viewHolder.cishuET = (EditText)convertView.findViewById(R.id.medicine_cishu);
			viewHolder.shuliangET = (EditText)convertView.findViewById(R.id.medicine_shuliang);
			viewHolder.tianshuET = (EditText)convertView.findViewById(R.id.medicine_tianshu);
			viewHolder.deleteBtn = (Button)convertView.findViewById(R.id.delete_btn);

			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		index = position;
		Log.i(TAG, "data " + data.toString());
		viewHolder.nameTextView.setText(data.get(position).get("drugName"));

//		viewHolder.unitTextView.setText(data.get(position).get("unit"));
		viewHolder.cishuET.setText(data.get(position).get("cishu"));
		viewHolder.shuliangET.setText(data.get(position).get("shuliang"));
		viewHolder.tianshuET.setText(data.get(position).get("tianshu"));

		viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HashMap<String, String> map = data.get(position);
				data.remove(map);
				notifyDataSetChanged();
			}
		});


		initYongLiang(position, viewHolder.yongliangLayout);
		//TODO 打开与否，根据后期要求
//		viewHolder.cishuET.addTextChangedListener(cishuWacher);

//		viewHolder.tianshuET.addTextChangedListener(watcher);
//		viewHolder.shuliangET.addTextChangedListener(watcher);
		return convertView;
	}
	String frequencyTypeStr,tianshuStr,cishuStr;
	int ciPerDay;
	private void initYongLiang(int position,LinearLayout yongliangLayout){
		frequencyTypeStr = data.get(position).get("frequencyType");
		String frequencyStr = data.get(position).get("frequency");
		String drugId = data.get(position).get("drugId");
		ciPerDay = Integer.valueOf(data.get(position).get("cishu"));

		if(frequencyTypeStr.equals("QD")){

			ciPerDay = 1;
			et1 = new EditText(context);
			et1.setText(frequencyStr);
			et1.setGravity(Gravity.CENTER);

			et1.setLayoutParams(new ViewGroup.LayoutParams(100, ViewGroup.LayoutParams.WRAP_CONTENT));
			et1.setEnabled(true);
			et1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
			et1.setSelectAllOnFocus(true);
			et1.setTextSize(13);
			et1.setBackgroundResource(R.drawable.simple_gray_stroke);
//TODO 打开与否，根据后期要求
//			et1.addTextChangedListener(watcher);

			et1.setSingleLine();

			unitTV = new TextView(context);
			unitTV.setTextSize(13);
			unitTV.setText(data.get(position).get("unit"));


			yongliangLayout.removeAllViews();
			yongliangLayout.addView(et1);
			yongliangLayout.addView(unitTV);


		}else if(frequencyTypeStr.equals("BID")){
			String[] split = frequencyStr.split(",");
			ciPerDay = 2;
			et1 = new EditText(context);
			et2 = new EditText(context);

            et1.setGravity(Gravity.CENTER);
			et2.setGravity(Gravity.CENTER);

			et1.setLayoutParams(new ViewGroup.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT));
			et2.setLayoutParams(new ViewGroup.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT));

			et1.setEnabled(true);
			et2.setEnabled(true);

			et1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
			et2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);


			et1.setSelectAllOnFocus(true);
			et2.setSelectAllOnFocus(true);

			et1.setTextSize(13);
			et2.setTextSize(13);

			et1.setBackgroundResource(R.drawable.simple_gray_stroke);
			et2.setBackgroundResource(R.drawable.simple_gray_stroke);
//TODO 打开与否，根据后期要求
//			et1.addTextChangedListener(watcher);
//			et2.addTextChangedListener(watcher);

			et1.setSingleLine();
			et2.setSingleLine();


			et1.setText(split[0]);
			et2.setText(split[1]);

			unitTV = new TextView(context);
			unitTV.setTextSize(13);
			unitTV.setText(data.get(position).get("unit"));

			yongliangLayout.removeAllViews();
			yongliangLayout.addView(et1);
			yongliangLayout.addView(et2);
			yongliangLayout.addView(unitTV);



		}else if(frequencyTypeStr.equals("TID")){
			String[] split = frequencyStr.split(",");

			ciPerDay = 3;
			et1 = new EditText(context);
			et2 = new EditText(context);
			et3 = new EditText(context);

			et1.setGravity(Gravity.CENTER);
			et2.setGravity(Gravity.CENTER);
			et3.setGravity(Gravity.CENTER);

			et1.setTextSize(13);
			et2.setTextSize(13);
			et3.setTextSize(13);

			et1.setLayoutParams(new ViewGroup.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT));
			et2.setLayoutParams(new ViewGroup.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT));
			et3.setLayoutParams(new ViewGroup.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT));
			et1.setEnabled(true);
			et2.setEnabled(true);
			et3.setEnabled(true);

			et1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
			et2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
			et3.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);

			et1.setSelectAllOnFocus(true);
			et2.setSelectAllOnFocus(true);
			et3.setSelectAllOnFocus(true);

			et1.setBackgroundResource(R.drawable.simple_gray_stroke);
			et2.setBackgroundResource(R.drawable.simple_gray_stroke);
			et3.setBackgroundResource(R.drawable.simple_gray_stroke);
//TODO 打开与否，根据后期要求
//			et1.addTextChangedListener(watcher);
//			et2.addTextChangedListener(watcher);
//			et3.addTextChangedListener(watcher);

			et1.setSingleLine();
			et2.setSingleLine();
			et3.setSingleLine();

			et1.setText(split[0]);
			et2.setText(split[1]);
			et3.setText(split[2]);

			unitTV = new TextView(context);
			unitTV.setTextSize(13);
			unitTV.setText(data.get(position).get("unit"));

			yongliangLayout.removeAllViews();
			yongliangLayout.addView(et1);
			yongliangLayout.addView(et2);
			yongliangLayout.addView(et3);
			yongliangLayout.addView(unitTV);
//			yongliangLayout.requestFocus();

		}

	}

	class ViewHolder{

		TextView nameTextView;
//		TextView unitTextView;
		LinearLayout yongliangLayout;
		EditText cishuET,tianshuET,shuliangET;
		Button deleteBtn;


	}
//	TextWatcher cishuWacher = new TextWatcher() {
//
//		@Override
//		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//		}
//
//		@Override
//		public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//
//
//		}
//
//		@Override
//		public void afterTextChanged(Editable s) {
//
//			Log.i(TAG,"cishuWacher index "+index);
//
//			if(s != null){
//				String cishu = s.toString();
//				data.get(index).put("cishu",cishu);
//				if(cishu.equals("1")){
//					changeCiShuTo1();
//				}else if(s.toString().equals("2")){
//					changeCiShuTo2();
//				}else if(s.toString().equals("3")){
//					changeCiShuTo3();
//				}
//
//			}
//
//		}
//	};
//
//	private void changeCiShuTo1(){
//		if(frequencyTypeStr.equals("TID")){
//			//次数变成1，原来的频率次数是3
//			viewHolder.yongliangLayout.removeView(et2);
//			viewHolder.yongliangLayout.removeView(et3);
//
//			data.get(index).put("frequency",et1.getText().toString());
//		}else if(frequencyTypeStr.equals("BID")){
//			viewHolder.yongliangLayout.removeView(et3);
//			data.get(index).put("frequency",et1.getText().toString()+","+et2.getText().toString());
//		}
//		data.get(index).put("frequencyType","QD");
//		data.get(index).put("cishu","1");
//
//	}
//	private void changeCiShuTo2(){
//		if(frequencyTypeStr.equals("TID")){
//			viewHolder.yongliangLayout.removeView(et3);
//			data.get(index).put("frequency", et1.getText().toString() + "," + et2.getText().toString());
//		}else if(frequencyTypeStr.equals("QD")){
//			//TODO 用量单位的处理
//			et2 = new EditText(context);
//			et2.setTextSize(13);
//			//设置默认值
//			et2.setText("0");
//
//			et2.setGravity(Gravity.CENTER);
//			et2.setBackgroundResource(R.drawable.simple_gray_stroke);
//			et2.setLayoutParams(new ViewGroup.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT));
//			et2.setEnabled(true);
//			et2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
//
//			et2.setSelectAllOnFocus(true);
//			//TODO 打开与否，根据后期要求
//			et2.addTextChangedListener(watcher);
//
//			et2.setSingleLine();
//
//			String unit = unitTV.getText().toString();
//			viewHolder.yongliangLayout.removeView(unitTV);
//			unitTV = new TextView(context);
//			unitTV.setTextSize(13);
//			unitTV.setText(unit);
//
//
//			viewHolder.yongliangLayout.addView(et2);
//			viewHolder.yongliangLayout.addView(unitTV);
//
//			data.get(index).put("frequency",et1.getText().toString()+","+et2.getText().toString());
//		}
//		data.get(index).put("frequencyType","BID");
//		data.get(index).put("cishu","2");
//	}
//	private void changeCiShuTo3(){
//		if(frequencyTypeStr.equals("QD")){
//			et3 = new EditText(context);
//			et2 = new EditText(context);
//
//			et3.setTextSize(13);
//			et2.setTextSize(13);
//
//			et2.setText("0");
//			et3.setText("0");
//
//			et2.setGravity(Gravity.CENTER);
//			et2.setBackgroundResource(R.drawable.simple_gray_stroke);
//			et3.setGravity(Gravity.CENTER);
//			et3.setBackgroundResource(R.drawable.simple_gray_stroke);
//
//			et3.setLayoutParams(new ViewGroup.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT));
//			et2.setLayoutParams(new ViewGroup.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT));
//
//			et3.setEnabled(true);
//			et2.setEnabled(true);
//
//			et3.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
//			et2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
//
//
//			et3.setSelectAllOnFocus(true);
//			et2.setSelectAllOnFocus(true);
//			//TODO 打开与否，根据后期要求
//			et3.addTextChangedListener(watcher);
//			et2.addTextChangedListener(watcher);
//
//			et3.setSingleLine();
//			et2.setSingleLine();
//
//			String unit = unitTV.getText().toString();
//			viewHolder.yongliangLayout.removeView(unitTV);
//			unitTV = new TextView(context);
//			unitTV.setTextSize(13);
//			unitTV.setText(unit);
//
//			viewHolder.yongliangLayout.addView(et2);
//			viewHolder.yongliangLayout.addView(et3);
//			viewHolder.yongliangLayout.addView(unitTV);
//
//			data.get(index).put("frequency", et1.getText().toString() + "," + et2.getText().toString() + "," + et3.getText().toString());
//		}else if(frequencyTypeStr.equals("BID")){
//			et3 = new EditText(context);
//			et3.setTextSize(13);
//			et3.setText("0");
//			et3.setGravity(Gravity.CENTER);
//			et3.setBackgroundResource(R.drawable.simple_gray_stroke);
//
//			et3.setLayoutParams(new ViewGroup.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT));
//			et3.setEnabled(true);
//			et3.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
//
//			et3.setSelectAllOnFocus(true);
//			//TODO 打开与否，根据后期要求
//
//			et3.addTextChangedListener(watcher);
//
//			et3.setSingleLine();
//
//			String unit = unitTV.getText().toString();
//			viewHolder.yongliangLayout.removeView(unitTV);
//			unitTV = new TextView(context);
//			unitTV.setTextSize(13);
//			unitTV.setText(unit);
//
//			viewHolder.yongliangLayout.addView(et3);
//			viewHolder.yongliangLayout.addView(unitTV);
//
//			data.get(index).put("frequency",et1.getText().toString()+","+et2.getText().toString()+","+et3.getText().toString());
//		}
//		data.get(index).put("frequencyType","TID");
//		data.get(index).put("cishu","3");
//	}
//	//根据天数、次数用量生成数量，每次天数、次数用量变化时，数量也变化
//	final TextWatcher watcher = new TextWatcher() {
//		@Override
//		public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
//			Log.i(TAG,"currentMedicine modify tianshuET "+viewHolder.tianshuET);
//			Log.i(TAG,"currentMedicine modify et1 "+et1);
//			Log.i(TAG,"currentMedicine modify pinciStr "+frequencyTypeStr);
//			// TODO Auto-generated method stub
////			if(frequencyTypeStr.equals("TID")&&et1 != null && et2 != null && et3 != null && !s.toString().equals("")
////					&&!et1.getText().toString().equals("")&&!et2.getText().toString().equals("")&&!et3.getText().toString().equals("")
////					&&!viewHolder.tianshuET.getText().toString().equals("")){
////				int e1 = Integer.parseInt(et1.getText().toString().trim());
////				int e2 = Integer.parseInt(et2.getText().toString().trim());
////				int e3 = Integer.parseInt(et3.getText().toString().trim());
////				int day = Integer.parseInt(viewHolder.tianshuET.getText().toString());
////				viewHolder.shuliangET.setText(String.valueOf((e1+e2+e3)*day));
////				cishuStr = String.valueOf(ciPerDay);
////			}else if(frequencyTypeStr.equals("QD")&&et1 != null&&!s.toString().equals("")&&!et1.getText().toString().equals("")&&!viewHolder.tianshuET.getText().toString().equals("")){
////				int e1 = Integer.parseInt(et1.getText().toString().trim());
////				int day = Integer.parseInt(viewHolder.tianshuET.getText().toString());
////				viewHolder.shuliangET.setText(String.valueOf(e1*day));
////				cishuStr = String.valueOf(ciPerDay);
////			} else if(frequencyTypeStr.equals("BID")&&et1 != null && et2 != null && !s.toString().equals("")
////					&&!et1.getText().toString().equals("")&&!et2.getText().toString().equals("")&&!viewHolder.tianshuET.getText().toString().equals("")){
////
////				int e1 = Integer.parseInt(et1.getText().toString().trim());
////				int e2 = Integer.parseInt(et2.getText().toString().trim());
////
////				int day = Integer.parseInt(viewHolder.tianshuET.getText().toString());
////				viewHolder.shuliangET.setText(String.valueOf((e1+e2)*day));
////				cishuStr = String.valueOf(ciPerDay);
////			}
//		}
//
//		@Override
//		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
//									  int arg3) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void afterTextChanged(Editable arg0) {
//			// TODO Auto-generated method stub
//
//			if(arg0 != null){
//				Activity activity = (Activity)context;
////				int currentId = activity.getCurrentFocus().getId();
////				if(currentId == viewHolder.tianshuET.getId()){
////					data.get(index).put("tianshu",arg0.toString());
////				}else if(currentId == viewHolder.shuliangET.getId()){
////					data.get(index).put("shuliang",arg0.toString());
////				}
//			}
//
//		}
//	};
//
//	private TextWatcher et1TextWacher = new TextWatcher() {
//		@Override
//		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//		}
//
//		@Override
//		public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//		}
//
//		@Override
//		public void afterTextChanged(Editable s) {
//
//		}
//	};

}
