package com.housekeeper.ar.healthhousekeeper.packages;

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
import android.widget.TextView;
import android.widget.Toast;

import com.housekeeper.ar.healthhousekeeper.MyDialog;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import java.util.HashMap;
import java.util.List;

public class PackagesListAdapter extends BaseAdapter {
	private Context context;
	public List<HashMap<String,String>> data;

	String TAG = "PackagesListAdapter";

	private ToastCommom toastCommom;

	public PackagesListAdapter(Context context, List<HashMap<String, String>> data) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_packages_service_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.service_name_tv);
			viewHolder.itemTextView = (TextView)convertView.findViewById(R.id.service_item_tv);
			viewHolder.priceTextView = (TextView)convertView.findViewById(R.id.service_price_tv);

			viewHolder.buyBtn = (Button)convertView.findViewById(R.id.buy_btn);

			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		Log.i(TAG,"data "+ data.toString());
		viewHolder.nameTextView.setText(data.get(position).get("name"));
		viewHolder.itemTextView.setText("服务项目："+data.get(position).get("item"));
		viewHolder.priceTextView.setText("收费标准："+data.get(position).get("charge_standard"));

		viewHolder.buyBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(context,PackagesRechargeActivity.class);
				intent.putExtra("service_name",data.get(position).get("name"));
				intent.putExtra("service_price",data.get(position).get("price"));
				context.startActivity(intent);

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
		TextView nameTextView,itemTextView,priceTextView;
		Button buyBtn;

	}

}
