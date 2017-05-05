package com.housekeeper.ar.healthhousekeeper.checkstand;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.housekeeper.ar.healthhousekeeper.MyDialog;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.RoundImageView;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import java.util.HashMap;
import java.util.List;

public class PayPatientListAdapter extends BaseAdapter {
	private Context context;
	public List<HashMap<String,String>> data;

	String TAG = "PayPatientListAdapter";

	private int flag = 0;//0表示待付款、1表示退款
	private ToastCommom toastCommom;

	public PayPatientListAdapter(Context context, List<HashMap<String, String>> data,int flag) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.data = data;
		this.flag = flag;
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

			viewHolder.yuyueBtn = (Button)convertView.findViewById(R.id.yuyue_btn);
			viewHolder.huiyuankaBtn = (Button)convertView.findViewById(R.id.huiyuanka_btn);
			viewHolder.huiyuankaBtn.setVisibility(View.GONE);

			if(flag == 0){
				viewHolder.yuyueBtn.setText("付费");
			}else{
				viewHolder.yuyueBtn.setText("退费");
			}


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

		viewHolder.yuyueBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if(flag == 0){
					//待付款
//					showPayDialog(data.get(position));
					Intent intent = new Intent(context,PatientPayListActivity.class);
					intent.putExtra("name",data.get(position).get("name"));
					intent.putExtra("sex",data.get(position).get("sex"));
					intent.putExtra("age",data.get(position).get("age"));
					intent.putExtra("binghao",data.get(position).get("binghao"));
					context.startActivity(intent);
//					myApp.setPatientId();
				}else if(flag == 1){
					/*
					if(data.get(position).get("class").equals("0")) {
						//如果是挂号费
						if(data.get(position).get("type").equals("0")){
							//可以退款，还未退款
							Intent intent = new Intent(context, RefundRegisterDetailActivity.class);
							intent.putExtra("from","kaishituikuan");
							intent.putExtra("status",data.get(position).get("type"));
							intent.putExtra("dingdanID",data.get(position).get("binghao"));
							intent.putExtra("refundReason",data.get(position).get("refundReason"));
							intent.putExtra("jiage",data.get(position).get("zongjia"));
							context.startActivity(intent);

						}else if(data.get(position).get("type").equals("10")){
							//已退款
							Intent intent = new Intent(context, RefundRegisterDetailActivity.class);
							intent.putExtra("from","yituikuan");
							intent.putExtra("status",data.get(position).get("type"));
							intent.putExtra("dingdanID",data.get(position).get("binghao"));
							intent.putExtra("refundReason",data.get(position).get("refundReason"));
							intent.putExtra("jiage",data.get(position).get("zongjia"));
							context.startActivity(intent);
						}else{
							//退款中
							Intent intent = new Intent(context, RefundRegisterDetailActivity.class);
							intent.putExtra("from","tuikuanIng");
							intent.putExtra("status",data.get(position).get("type"));
							intent.putExtra("dingdanID",data.get(position).get("binghao"));
							intent.putExtra("refundReason",data.get(position).get("refundReason"));
							intent.putExtra("jiage",data.get(position).get("zongjia"));
							context.startActivity(intent);
						}
					}
					if(data.get(position).get("class").equals("1")){
						//如果是药费
						if(data.get(position).get("type").equals("0")){
							//可以退款，还未退款
							Intent intent = new Intent(context, RefundMedicineDetailActivity.class);
							intent.putExtra("from","kaishituikuan");
							intent.putExtra("status",data.get(position).get("type"));
							intent.putExtra("dingdanID",data.get(position).get("binghao"));
							intent.putExtra("refundReason",data.get(position).get("refundReason"));
							intent.putExtra("jiage",data.get(position).get("zongjia"));
							context.startActivity(intent);

						}else if(data.get(position).get("type").equals("8")){
							//已退款
							Intent intent = new Intent(context, RefundMedicineDetailActivity.class);
							intent.putExtra("from","yituikuan");
							intent.putExtra("status",data.get(position).get("type"));
							intent.putExtra("dingdanID",data.get(position).get("binghao"));
							intent.putExtra("refundReason",data.get(position).get("refundReason"));
							intent.putExtra("jiage",data.get(position).get("zongjia"));
							context.startActivity(intent);
						}else{
							//退款中
							Intent intent = new Intent(context, RefundMedicineDetailActivity.class);
							intent.putExtra("from","tuikuanIng");
							intent.putExtra("status",data.get(position).get("type"));
							intent.putExtra("dingdanID",data.get(position).get("binghao"));
							intent.putExtra("refundReason",data.get(position).get("refundReason"));
							intent.putExtra("jiage",data.get(position).get("zongjia"));
							context.startActivity(intent);
						}

					}
					*/
					//showPayDialog(data.get(position));
					Intent intent = new Intent(context,PatientRefundListActivity.class);
					intent.putExtra("name",data.get(position).get("name"));
					intent.putExtra("sex",data.get(position).get("sex"));
					intent.putExtra("age",data.get(position).get("age"));
					intent.putExtra("binghao",data.get(position).get("binghao"));
                    intent.putExtra("jaRefund", data.get(position).toString());
                    /*
					intent.putExtra("jiage",data.get(position).get("jiage"));
					intent.putExtra("orderType", data.get(position).get("orderType"));
					if( data.get(position).get("orderType").equals("处方费")){
						intent.putExtra("drugList", data.get(position).get("drugList"));
					}
					*/
					context.startActivity(intent);

				}



			}
		});

		viewHolder.huiyuankaBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

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

	private void showPayDialog(final HashMap<String,String> map) {

		final View functionListView = LayoutInflater.from(context).inflate(R.layout.dialog_finance, null);
//		dialog = new AlertDialog.Builder(DiagnosisActivity.this, R.style.load_dialog).setView(functionListView).show();

		final MyDialog dialog = new MyDialog(context, functionListView, R.style.load_dialog);
		int width = context.getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
//		int height = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
		dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
		dialog.show();

		Button okBtn = (Button)dialog.findViewById(R.id.dialog_shoufei_ok_btn);
		Button cancelBtn = (Button)dialog.findViewById(R.id.dialog_shoufei_cancel_btn);
		final TextView yingfuTV = (TextView)dialog.findViewById(R.id.yingfu_tv);
		final EditText shifuTV = (EditText)dialog.findViewById(R.id.shifu_tv);
		final TextView zhaolingTV = (TextView)dialog.findViewById(R.id.zhaoling_tv);

		yingfuTV.setText(map.get("zongjia"));
//		shifuTV.setText(zongjiTextView.getText().toString());
//		shifuTV.setSelectAllOnFocus(true);
		zhaolingTV.setText("0");

		shifuTV.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

				Log.i(TAG, "charSequence " + charSequence);
				String str = "0";
				if (charSequence == null || charSequence.toString().equals("")) {
					Log.i(TAG, "charSe null");
					str = yingfuTV.getText().toString();
				} else {
					str = charSequence.toString();
				}
				double shifuD = Double.valueOf(str);
				double yingfuD = Double.valueOf(yingfuTV.getText().toString());
				double zhaolingD = shifuD - yingfuD;
				zhaolingTV.setText("" + zhaolingD);
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});

		okBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(shifuTV.getText().toString().equals("")){
//					toastCommom.ToastShow(FinancePayMedicineDetailActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "实付金额为空");
//					return;
					shifuTV.setText(yingfuTV.getText().toString());
				}

				double shifuD = Double.valueOf(shifuTV.getText().toString());
				double yingfuD = Double.valueOf(yingfuTV.getText().toString());
				if(shifuD < yingfuD){
//					toastCommom.ToastShow(context, (ViewGroup) findViewById(R.id.toast_layout_root), "实付金额小于应付金额");
					Toast.makeText(context,"实付金额小于应付金额",Toast.LENGTH_LONG).show();
					return;
				}


//				HashMap<String, String> map = fukuandata.get(selectedFuKuanIndex);
//				fukuandata.remove(map);
//				showNextAfterDelete();

				//TODO 付款实现

				dialog.dismiss();
				notifyDataSetChanged();

			}
		});
		cancelBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
			}
		});
	}

	class ViewHolder{
		RoundImageView headerImage;

		TextView nameTextView,sexTextView,ageTextView,telTextView,binghaoTextView;
		ImageView telImageView,weixinImageView,messageImageView;
		Button yuyueBtn,huiyuankaBtn;

	}

}
