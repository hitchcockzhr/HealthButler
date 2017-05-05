package com.housekeeper.ar.healthhousekeeper.checkstand;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.GuidanceBottomTabActivity;
import com.housekeeper.ar.healthhousekeeper.HttpUtil;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.MyDialog;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//退挂号费，退款状态是：审核-退款完毕 尚未取药或检查时
public class RefundRegisterDetailActivity extends BaseActivity {

	private Spinner reasonSpinner;
	private ImageButton reasonBtn;

	private Button tuikuanBtn;
	private EditText tuikuanShuoMingEditText;
	private TextView dingdanIDTextView;
	private TextView statusTextView;

	private Button backBtn;
	private TextView feiyongTextView;
	private LinearLayout statusLayout;
	JSONObject params = new JSONObject();
	String status;

	private String TAG="TuiKuanGuaHaoDetailActivity";
	//退款原因选其他时，存入“其他-原因”格式，在读取数据时（已退款、退款中、退款失败），可据此规则解析
	private String reasonString="";

	String http;
	MyApp myApp;

	String tuiguahaofeiUrl = "shlc/healthButler/refund/medicalRecord?medId=";
	String reasonUrl = "&refundReason=";
	String dingdanID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyActivityManager.pushOneActivity(RefundRegisterDetailActivity.this);
		SysApplication.getInstance().addActivity(this);
//		setContentView(R.layout.activity_fukuan_success);
		setContentView(R.layout.activity_refund_detail);
		myApp = (MyApp)getApplication();
		http = myApp.getHttp();
		tuikuanBtn = (Button)findViewById(R.id.tijiao_btn);
		tuikuanBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//TODO 提交退款
				Log.i(TAG, "reasonString " + reasonString);

				showRefundDialog();
			}
		});

		statusLayout = (LinearLayout)findViewById(R.id.tuikuan_status_layout);


		tuikuanShuoMingEditText = (EditText)findViewById(R.id.tuikuan_shuoming_tv);
		dingdanIDTextView = (TextView)findViewById(R.id.dingdan_id);
		statusTextView = (TextView)findViewById(R.id.status_tv);
		feiyongTextView = (TextView)findViewById(R.id.tuikuan_feiyong);
		backBtn = (Button)findViewById(R.id.back_btn);


		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		reasonSpinner = (Spinner)findViewById(R.id.reason_spinner);
		final List<String> reasonsList = getTuiKuanReasonData();

		ArrayAdapter<String> sexAdapter = new ArrayAdapter<String>
				(RefundRegisterDetailActivity.this, R.layout.spinner_item,getTuiKuanReasonData()){
			@Override
			public View getDropDownView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.spinner_item_layout,
						null);
				TextView label = (TextView) view
						.findViewById(R.id.spinner_item_label);

				label.setText(reasonsList.get(position));

				return view;
				//return super.getDropDownView(position, convertView, parent);
			}
		};
		//设置样式
		sexAdapter.setDropDownViewResource(R.layout.spinner_item_layout);

		reasonSpinner.setAdapter(sexAdapter);

		reasonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				Log.i(TAG, "reason selected " + reasonsList.get(i));
				//退款原因选其他时，存入“其他-原因”格式，在读取数据时（已退款、退款中、退款失败），可据此规则解析
				if (reasonsList.get(i).equals("其他")) {
					tuikuanShuoMingEditText.setVisibility(View.VISIBLE);
					reasonString = reasonsList.get(i);
//					reasonString = reasonsList.get(i) + "-" + tuikuanShuoMingEditText.getText().toString();
				} else {
					tuikuanShuoMingEditText.setVisibility(View.GONE);
					reasonString = reasonsList.get(i);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});

		Intent intent = getIntent();
		if(intent != null){
			//String from = intent.getStringExtra("from");
			String from = null;
            status = intent.getStringExtra("status");
			if(status.equals("10")){
				from = "yituikuan";
			}else if (status.equals("1") ||status.equals("2")||status.equals("3")){
				from = "ketuikuan";
			}else {
				from = "tuikuanIng";
			}
			dingdanID = intent.getStringExtra("dingdanID");
			dingdanIDTextView.setText(dingdanID);
			feiyongTextView.setText(intent.getStringExtra("price"));
			//reasonString = intent.getStringExtra("refundReason");

			if(from.equals("yituikuan")){

				//TODO 从已退款列表进入，已经退款了，选择设置成灰色，不能提交退款
				tuikuanBtn.setEnabled(false);
				tuikuanBtn.setBackgroundColor(getResources().getColor(R.color.light_gray));
				reasonSpinner.setBackgroundColor(getResources().getColor(R.color.light_gray));
				tuikuanShuoMingEditText.setText("从数据库中获取");
				tuikuanShuoMingEditText.setEnabled(false);
				reasonSpinner.setEnabled(false);
				statusTextView.setText("已退挂号费");
//				String reason = "医生未响应";//从数据库中获取
//				String reason = "其他-你好";//从数据库中获取
				List<String> list = getTuiKuanReasonData();

				if(list != null){

					if(reasonString.contains("其他-")){
						//原因是其他
						tuikuanShuoMingEditText.setVisibility(View.VISIBLE);
						tuikuanShuoMingEditText.setText(reasonString.substring(3));

						reasonSpinner.setSelection(list.size() - 1, true);
					}else {
						tuikuanShuoMingEditText.setVisibility(View.GONE);
						for (int i = 0; i < list.size(); i++) {
							if (list.get(i).equals(reasonString)) {
								reasonSpinner.setSelection(i, true);
								break;
							}
						}
					}


				}
				LayoutInflater inflater =getLayoutInflater();
				final View view = inflater.inflate(R.layout.activity_refund_guahao_detail_finish_ok_layout, null);
				statusLayout.removeAllViews();
				statusLayout.addView(view);



			}else if(from.equals("tuikuanIng")){

				//TODO 退款中，选择设置成灰色，不能提交退款
				tuikuanBtn.setEnabled(false);
				tuikuanBtn.setBackgroundColor(getResources().getColor(R.color.light_gray));
				reasonSpinner.setBackgroundColor(getResources().getColor(R.color.light_gray));
				tuikuanShuoMingEditText.setText("从数据库中获取");
				tuikuanShuoMingEditText.setEnabled(false);
				reasonSpinner.setEnabled(false);
//				String reason = "医生未响应";//从数据库中获取
//				String reason = "其他-你好";//从数据库中获取
				List<String> list = getTuiKuanReasonData();

				if(list != null){

					if(reasonString.contains("其他-")){
						//原因是其他
						tuikuanShuoMingEditText.setVisibility(View.VISIBLE);
						tuikuanShuoMingEditText.setText(reasonString.substring(3));

						reasonSpinner.setSelection(list.size() - 1, true);
					}else {
						tuikuanShuoMingEditText.setVisibility(View.GONE);
						for (int i = 0; i < list.size(); i++) {
							if (list.get(i).equals(reasonString)) {
								reasonSpinner.setSelection(i, true);
								break;
							}
						}
					}


				}

				if(status.equals("3")){
					statusTextView.setText("病人提交");
					LayoutInflater inflater =getLayoutInflater();
					final View view = inflater.inflate(R.layout.activity_refund_guahao_detail_tijiao_ok_layout, null);
					statusLayout.removeAllViews();
					statusLayout.addView(view);
				}



			} else{

				tuikuanBtn.setEnabled(true);
//				tuikuanBtn.setBackgroundColor(getResources().getColor(R.color.white));
//				reasonSpinner.setBackgroundColor(getResources().getColor(R.color.deep_gray));

				statusTextView.setText("退挂号费");
				LayoutInflater inflater =getLayoutInflater();
				final View view = inflater.inflate(R.layout.activity_refund_guahao_detail_start_layout, null);
				statusLayout.removeAllViews();
				statusLayout.addView(view);
				showRefundDialog();
			}
		}else{
			return;
		}

	}

	private List<String> getTuiKuanReasonData(){
		List<String> dataList = new ArrayList<String>();

		dataList.add("挂错科室了");
		dataList.add("医生未响应");

		dataList.add("已有药品，不需要取药");
		dataList.add("开错药品，重新开");
		dataList.add("其他");

		return dataList;
	}

	//显示退款提示框
	private void showRefundDialog(){

		LayoutInflater inflater =getLayoutInflater();
		final View functionListView = inflater.inflate(R.layout.dialog_refund, null);
		final MyDialog dialog = new MyDialog(RefundRegisterDetailActivity.this, functionListView,R.style.load_dialog);
		int width = getResources().getDimensionPixelSize(R.dimen.dialog_weight);
		dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
		dialog.show();
		Log.i(TAG, "showRefundDialog dialog.show");

		Button okBtn = (Button)functionListView.findViewById(R.id.ok_btn);
		Button cancelBtn = (Button)functionListView.findViewById(R.id.cancel_btn);

		okBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				//TODO 完成退款
				dialog.dismiss();
				tuikuan();

			}
		});

		cancelBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				//TODO 取消退款
				dialog.dismiss();

			}
		});

	}

	private void tuikuan(){
		String url = "";

		//退款原因选其他时，存入“其他-原因”格式，在读取数据时（已退款、退款中、退款失败），可据此规则解析
		if(reasonString.equals("其他")){
			reasonString = reasonString + "-" + tuikuanShuoMingEditText.getText().toString();
		}
		url = http + tuiguahaofeiUrl + dingdanIDTextView.getText().toString() + reasonUrl +reasonString;
		Log.i(TAG, "url " + url);
		Log.i(TAG, "reasonString " + reasonString);
		try {

			HttpPost post = HttpUtil.getPost(url, null);
			JSONObject rev = HttpUtil.getString(post, 3);
			Log.e(TAG, "退挂号费rev:" + rev.toString());
			if(String.valueOf(rev.get("result")).equals("200")){
				Log.e(TAG, "resultequals=200 ");
				Intent intent = new Intent(RefundRegisterDetailActivity.this, GuidanceBottomTabActivity.class);
				startActivity(intent);
				Toast.makeText(RefundRegisterDetailActivity.this, "退费成功", Toast.LENGTH_LONG).show();
				finish();

			}else{
				Toast.makeText(RefundRegisterDetailActivity.this, "退费失败", Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			Log.e(TAG, "JSONException");
			e.printStackTrace();
		}

	}


}
