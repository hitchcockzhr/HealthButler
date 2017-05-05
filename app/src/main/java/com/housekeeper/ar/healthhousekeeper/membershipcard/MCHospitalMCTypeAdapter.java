package com.housekeeper.ar.healthhousekeeper.membershipcard;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.housekeeper.ar.healthhousekeeper.MyDialog;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.RoundImageView;

import java.util.HashMap;
import java.util.List;

public class MCHospitalMCTypeAdapter extends BaseAdapter {
	private Context context;
	public List<HashMap<String,String>> data;

	String TAG = "MCHospitalMCTypeAdapter";
	private int index = 0;


	public MCHospitalMCTypeAdapter(Context context, List<HashMap<String, String>> data) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_membershipcard_hospital_mctype_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.name_tv);
			viewHolder.layout = (LinearLayout)convertView.findViewById(R.id.rules_layout);
			viewHolder.okBtn = (Button)convertView.findViewById(R.id.ok_btn);
			viewHolder.editBtn = (Button)convertView.findViewById(R.id.edit_btn);
			viewHolder.deleteBtn = (Button)convertView.findViewById(R.id.delete_btn);

			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		Log.i(TAG, "data " + data.toString());
		viewHolder.nameTextView.setText(data.get(position).get("name"));
		String rules = data.get(position).get("rules");
		String[] rulesArray = rules.split(";");
		viewHolder.layout.removeAllViews();
		for(int i = 0;i<rulesArray.length;i++){
			TextView textView = new TextView(context);
			int num = i+1;
			textView.setText(num+"."+rulesArray[i]);
			viewHolder.layout.addView(textView);
			viewHolder.layout.setBottom(20);
			viewHolder.layout.setTop(20);
		}
		if(data.get(position).get("discount")!=null&&!data.get(position).get("discount").equals("")){
			TextView textView = new TextView(context);
			int num =rulesArray.length+1;
			textView.setText(num+"."+"折扣率"+data.get(position).get("discount")+"%");
			viewHolder.layout.addView(textView);
			viewHolder.layout.setBottom(20);
			viewHolder.layout.setTop(20);
		}
		viewHolder.okBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				//TODO 办卡
				Intent intent = new Intent(context,MCHospitalRechargeActivity.class);
				intent.putExtra("type",data.get(position).get("name"));
//				intent.putExtra("rules",data.get(position).get("rules"));
//				intent.putExtra("discount",data.get(position).get("discount"));


				MCHospitalMCTypeActivity activity  = (MCHospitalMCTypeActivity)context;
				activity.startActivity(intent);
			}
		});

		viewHolder.editBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				index = position;
				Intent intent = new Intent(context,MCHoapitalAddMCTypeActivity.class);
                Log.i(TAG,"规则 "+data.get(position).get("rules"));
				intent.putExtra("type",data.get(position).get("name"));
				intent.putExtra("rules", data.get(position).get("rules"));
				intent.putExtra("discount",data.get(position).get("discount"));
				MCHospitalMCTypeActivity activity  = (MCHospitalMCTypeActivity)context;
				activity.startActivityForResult(intent, MCHospitalMCTypeActivity.EDIT);
//				showDialog(data.get(position));
			}
		});

		viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				data.remove(data.get(position));
				notifyDataSetChanged();
			}
		});
		return convertView;
	}

	class ViewHolder{
		RoundImageView headerImage;

		TextView nameTextView,addressTextView;
		Button okBtn,editBtn,deleteBtn;
		LinearLayout layout;


	}
	public int getIndex(){
		return index;
	}
	private LinearLayout rulesLayout;
	private Button okBtn;
	private String rules="";
	private EditText nameEditText;
	//折扣率
	private EditText discountRateEditText;
	//赠送额
	private EditText givenAmountEditText;
	//充值额
	private EditText rechargeAmountEditText;
	private TextView rechargeAmountTextView;
	private TextView moreTextView;
	MyDialog dialog;
	//对话框
	private void showDialog(HashMap<String,String> map){
		final View functionListView = LayoutInflater.from(context).inflate(R.layout.activity_membership_dialog_edit_mctype, null);
//		dialog = new AlertDialog.Builder(DiagnosisActivity.this, R.style.load_dialog).setView(functionListView).show();

		dialog = new MyDialog(context, functionListView,R.style.load_dialog);
		dialog.show();
		int width = context.getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
//		int height = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
		dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

		rulesLayout = (LinearLayout)functionListView.findViewById(R.id.rules_layout);
		okBtn = (Button)functionListView.findViewById(R.id.ok_btn);
		nameEditText = (EditText)functionListView.findViewById(R.id.mc_name_et);
		moreTextView = (TextView)functionListView.findViewById(R.id.add_tv);

		discountRateEditText=(EditText)functionListView.findViewById(R.id.zhekoulv_tv);
		givenAmountEditText=(EditText)functionListView.findViewById(R.id.zengsonge_tv);

		rechargeAmountEditText = (EditText)functionListView.findViewById(R.id.chongzhie_tv);
		rechargeAmountTextView = (TextView)functionListView.findViewById(R.id.chongzhie_tv2);



//		discountRateEditText.addTextChangedListener(discountTextWatcher);
//		givenAmountEditText.addTextChangedListener(givenAmountTextWatcher);
//		rechargeAmountEditText.addTextChangedListener(rechargeAmountTextWatcher);

		nameEditText.setText(map.get("name"));
		String rules = map.get("rules");
		String[] rulesArray = rules.split(";");
		rulesLayout.removeAllViews();
		for(int i = 0;i<rulesArray.length;i++){
			EditText editText = new EditText(context);
			editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 130));
			editText.setBackground(context.getResources().getDrawable(R.drawable.simple_gray_stroke));
			editText.setTextSize(13);
			editText.setTop(100);
			editText.setPadding(3, 3, 3, 3);
			int num = i+1;
			editText.setText(num+"."+rulesArray[i]);
			rulesLayout.addView(editText);
			rulesLayout.setBottom(20);
			rulesLayout.setTop(20);
		}
		moreTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				EditText editText = new EditText(context);
				editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 130));
				editText.setBackground(context.getResources().getDrawable(R.drawable.simple_gray_stroke));
				editText.setTextSize(13);
				editText.setTop(100);
				editText.setPadding(3, 3, 3, 3);

				rulesLayout.addView(editText);

			}
		});

		okBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (nameEditText.getText().toString().equals("")) {
					Toast.makeText(context,"卡名称不能为空",Toast.LENGTH_LONG).show();
					return;
				}
				String rules = "";
				//将卡名称nameEditText过滤
				for (int i = 1; i < rulesLayout.getChildCount(); i++) {
					EditText editText = (EditText) rulesLayout.getChildAt(i);
					if (editText.getText().toString().equals("")) {
						Toast.makeText(context,"规则不能为空",Toast.LENGTH_LONG).show();
						return;
					} else {
						rules = rules + ";" + editText.getText().toString();
					}

				}
				//折扣率要写进规则中吗？
				if (!discountRateEditText.getText().toString().equals("")) {

					rules = rules + ";" + "折扣率" + discountRateEditText.getText().toString() + "%";
				}
				//去掉rules里第一个空;数据
				rules = rules.substring(1);
				data.get(index).put("name", nameEditText.getText().toString());
				data.get(index).put("rules", rules);
				notifyDataSetChanged();
				dialog.dismiss();

			}
		});



	}

//	private TextWatcher discountTextWatcher = new TextWatcher() {
//		@Override
//		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//		}
//
//		@Override
//		public void onTextChanged(CharSequence s, int start, int before, int count) {
//			Log.i(TAG,"折扣率 CharSequence "+s);
//
//			if (s.toString().contains(".")) {
//				if (s.length() - 1 - s.toString().indexOf(".") > 2) {
//					s = s.toString().subSequence(0,
//							s.toString().indexOf(".") + 3);
//					discountRateEditText.setText(s);
//					discountRateEditText.setSelection(s.length());
//				}
//			}
//			if (s.toString().trim().substring(0).equals(".")) {
//				s = "0" + s;
//				discountRateEditText.setText(s);
//				discountRateEditText.setSelection(2);
//			}
//
//			if (s.toString().startsWith("0")
//					&& s.toString().trim().length() > 1) {
//				if (!s.toString().substring(1, 2).equals(".")) {
//					discountRateEditText.setText(s.subSequence(0, 1));
//					discountRateEditText.setSelection(1);
//					return;
//				}
//			}
//			String discount = discountRateEditText.getText().toString();
//			String given = givenAmountEditText.getText().toString();
//			String recharge = rechargeAmountEditText.getText().toString();
//			Log.i(TAG,"折扣率 "+discount);
//			Log.i(TAG,"赠送额 "+given);
//			Log.i(TAG,"充值额 "+recharge);
//			if(discount.equals("")){
//				Log.i(TAG,"折扣率为空");
//				return;
//			}
//
//			EditText editText = (EditText)dialog.getCurrentFocus();
//
//			Log.i(TAG,"焦点在 "+editText.getId());
//			Log.i(TAG,"折扣率ID "+discountRateEditText.getId());
//			Log.i(TAG, "赠送额ID " + givenAmountEditText.getId());
//			if(discountRateEditText.getId() != editText.getId()){
//				Log.i(TAG,"焦点没在折扣率上");
//				return;
//			}
//			if(!given.equals("") && !recharge.equals("")){
//				//如赠送额和充值额已经有内容了，折扣率变化，则改变赠送额
//				Double tmp = Double.valueOf(recharge)/(Double.valueOf(discount)/100)-Double.valueOf(recharge);
//				Log.i(TAG,"折扣率变化，充值额不变，赠送额= "+tmp);
//				givenAmountEditText.setText(""+tmp);
//			}else if(given.equals("")&&!recharge.equals("")){
//				Double tmp = Double.valueOf(recharge)/(Double.valueOf(discount)/100)-Double.valueOf(recharge);
//				Log.i(TAG,"折扣率变化，充值额不变，赠送额= "+tmp);
//				givenAmountEditText.setText(""+tmp);
//			}else if(!given.equals("")&&recharge.equals("")){
//				Double discountRate = Double.valueOf(discount)/100;
//				Double givenAmount = Double.valueOf(given);
//				Double rechargeAmount = discountRate*givenAmount/(1-discountRate);
//
//				rechargeAmountEditText.setText(""+rechargeAmount);
//			}
//
//
//		}
//
//		@Override
//		public void afterTextChanged(Editable s) {
//
//
//		}
//	};
//	private TextWatcher givenAmountTextWatcher = new TextWatcher() {
//		@Override
//		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//		}
//
//		@Override
//		public void onTextChanged(CharSequence s, int start, int before, int count) {
//			Log.i(TAG,"赠送额 CharSequence "+s);
//
//			if (s.toString().contains(".")) {
//				if (s.length() - 1 - s.toString().indexOf(".") > 2) {
//					s = s.toString().subSequence(0,
//							s.toString().indexOf(".") + 3);
//					givenAmountEditText.setText(s);
//					givenAmountEditText.setSelection(s.length());
//				}
//			}
//			if (s.toString().trim().substring(0).equals(".")) {
//				s = "0" + s;
//				givenAmountEditText.setText(s);
//				givenAmountEditText.setSelection(2);
//			}
//
//			if (s.toString().startsWith("0")
//					&& s.toString().trim().length() > 1) {
//				if (!s.toString().substring(1, 2).equals(".")) {
//					givenAmountEditText.setText(s.subSequence(0, 1));
//					givenAmountEditText.setSelection(1);
//					return;
//				}
//			}
//			String discount = discountRateEditText.getText().toString();
//			String given = givenAmountEditText.getText().toString();
//			String recharge = rechargeAmountEditText.getText().toString();
//			Log.i(TAG,"折扣率 "+discount);
//			Log.i(TAG,"赠送额 "+given);
//			Log.i(TAG,"充值额 "+recharge);
//
//			if(given.equals("")){
//
//				return;
//			}
//			EditText editText = (EditText)dialog.getCurrentFocus();
//			if(givenAmountEditText.getId() != editText.getId()){
//				Log.i(TAG,"焦点没在赠送额上");
//				return;
//			}
//			if(!discount.equals("") && !recharge.equals("")){
//
//				//如果赠送额开始变化，充值额和折扣率已经有数值时，改变折扣率
//				Double rechargeAmount = Double.valueOf(recharge);
//				Double givenAmount = Double.valueOf(given);
//				Double discountD = rechargeAmount/(givenAmount+rechargeAmount)*100;
//				Log.i(TAG,"赠送额变化，充值额不变，折扣率= "+discountD);
//				discountRateEditText.setText("" + discountD);
//			}else if(discount.equals("") && !recharge.equals("")){
//				Double rechargeAmount = Double.valueOf(recharge);
//				Double givenAmount = Double.valueOf(given);
//				Double discountD = rechargeAmount/(givenAmount+rechargeAmount)*100;
//				Log.i(TAG,"赠送额变化，充值额不变，折扣率= "+discountD);
//				discountRateEditText.setText("" + discountD);
//			}else if(!discount.equals("") && recharge.equals("")){
//				//计算充值额
//				Double discountRate = Double.valueOf(discount)/100;
//				Double givenAmount = Double.valueOf(given);
//				Double rechargeAmount = discountRate*givenAmount/(1-discountRate);
//
//				rechargeAmountEditText.setText(""+rechargeAmount);
//			}
//		}
//
//		@Override
//		public void afterTextChanged(Editable s) {
//
//		}
//	};
//	private TextWatcher rechargeAmountTextWatcher = new TextWatcher() {
//		@Override
//		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//		}
//
//		@Override
//		public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//			Log.i(TAG,"充值额 CharSequence "+s);
//
//
//			if (s.toString().contains(".")) {
//				if (s.length() - 1 - s.toString().indexOf(".") > 2) {
//					s = s.toString().subSequence(0,
//							s.toString().indexOf(".") + 3);
//					rechargeAmountEditText.setText(s);
//					rechargeAmountEditText.setSelection(s.length());
//				}
//			}
//			if (s.toString().trim().substring(0).equals(".")) {
//				s = "0" + s;
//				rechargeAmountEditText.setText(s);
//				rechargeAmountEditText.setSelection(2);
//			}
//
//			if (s.toString().startsWith("0")
//					&& s.toString().trim().length() > 1) {
//				if (!s.toString().substring(1, 2).equals(".")) {
//					rechargeAmountEditText.setText(s.subSequence(0, 1));
//					rechargeAmountEditText.setSelection(1);
//					return;
//				}
//			}
//			String discount = discountRateEditText.getText().toString();
//			String given = givenAmountEditText.getText().toString();
//			String recharge = rechargeAmountEditText.getText().toString();
//			Log.i(TAG,"折扣率 "+discount);
//			Log.i(TAG,"赠送额 "+given);
//			Log.i(TAG, "充值额 " + recharge);
//			if(recharge.equals("")){
//				return;
//			}
//			EditText editText = (EditText)dialog.getCurrentFocus();
//			if(rechargeAmountEditText.getId() != editText.getId()){
//				Log.i(TAG,"焦点没在充值额上");
//				return;
//			}
//			rechargeAmountTextView.setText(recharge);
//			if(!discount.equals("")&&!given.equals("")){
//				//如果折扣率和赠送额已经有内容了，充值额变化，改变赠送额
//				Double tmp = Double.valueOf(recharge)/(Double.valueOf(discount)/100)-Double.valueOf(recharge);
//				Log.i(TAG,"折扣率变化，充值额不变，赠送额= "+tmp);
//				givenAmountEditText.setText(""+tmp);
//
//			}else if(!discount.equals("")&& given.equals("")){
//
//				Double tmp = Double.valueOf(recharge)/(Double.valueOf(discount)/100)-Double.valueOf(recharge);
//				Log.i(TAG,"折扣率变化，充值额不变，赠送额= "+tmp);
//				givenAmountEditText.setText(""+tmp);
//			}else if(discount.equals("")&& !given.equals("")){
//				//计算折扣率
//				Double rechargeAmount = Double.valueOf(recharge);
//				Double givenAmount = Double.valueOf(given);
//				Double discountD = rechargeAmount/(givenAmount+rechargeAmount)*100;
//				Log.i(TAG,"赠送额变化，充值额不变，折扣率= "+discountD);
//				discountRateEditText.setText("" + discountD);
//			}
//		}
//
//		@Override
//		public void afterTextChanged(Editable s) {
//
//		}
//	};

}
