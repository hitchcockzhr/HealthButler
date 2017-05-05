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

//退药费
//若已经取药 退款状态是：提交申请-医生确认-药房确认-退款完毕；未取药则直接提交申请-退款完毕。若已经检查了则不能退款吧
public class RefundMedicineDetailActivity extends BaseActivity {

	private Spinner reasonSpinner;
	private ImageButton reasonBtn;
	private Button tuikuanBtn;
	private EditText tuikuanShuoMingEditText;
	private TextView dingdanIDTextView;
	private TextView statusTextView;

	private Button backBtn;
	private TextView feiyongTextView;

	JSONObject params = new JSONObject();
	private LinearLayout statusLayout;
	private String TAG = "RefundMedicineDetailActivity";

	//退款原因选其他时，存入“其他-原因”格式，在读取数据时（已退款、退款中、退款失败），可据此规则解析
	private String reasonString="";

	//标记是否已经取药，若已经取药，则需要药房确认，否则不用药房确认
	private boolean getMedicine = false;
	private String status="";

	String http, httpUrl;
	MyApp myApp;

	String tuiyaofeiUrl = "shlc/healthButler/refund/prescriptionRecord?presId=";
	String reasonUrl = "&refundReason=";
	String dingdanID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyActivityManager.pushOneActivity(RefundMedicineDetailActivity.this);
		SysApplication.getInstance().addActivity(this);
//		setContentView(R.layout.activity_fukuan_success);
		setContentView(R.layout.activity_refund_detail);
		Toast.makeText(RefundMedicineDetailActivity.this, "退药费界面", Toast.LENGTH_LONG).show();

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

		Log.i(TAG, "TuiKuanDetailActivity oncreate ");

		tuikuanShuoMingEditText = (EditText)findViewById(R.id.tuikuan_shuoming_tv);
		dingdanIDTextView = (TextView)findViewById(R.id.dingdan_id);
		statusTextView = (TextView)findViewById(R.id.status_tv);
		feiyongTextView = (TextView)findViewById(R.id.tuikuan_feiyong);
		backBtn = (Button)findViewById(R.id.back_btn);
		statusLayout = (LinearLayout)findViewById(R.id.tuikuan_status_layout);

		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		reasonSpinner = (Spinner)findViewById(R.id.reason_spinner);
		final List<String> reasonsList = getTuiKuanReasonData();


		ArrayAdapter<String> reasonAdapter = new ArrayAdapter<String>
				(RefundMedicineDetailActivity.this, R.layout.spinner_item,getTuiKuanReasonData()){
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
		reasonAdapter.setDropDownViewResource(R.layout.spinner_item_layout);

		reasonSpinner.setAdapter(reasonAdapter);
		reasonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				Log.i(TAG, "reason selected");
				//退款原因选其他时，存入“其他-原因”格式，在读取数据时（已退款、退款中、退款失败），可据此规则解析
				if(reasonsList.get(i).equals("其他")){
					tuikuanShuoMingEditText.setVisibility(View.VISIBLE);
					reasonString = reasonsList.get(i);
//					reasonString = reasonsList.get(i)+"-"+tuikuanShuoMingEditText.getText().toString();
				}else{
					tuikuanShuoMingEditText.setVisibility(View.GONE);
					reasonString = reasonsList.get(i);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});

		Intent intent = getIntent();
		Log.i(TAG, "intent " + intent);
		if(intent != null){


			//String from = intent.getStringExtra("from");
			dingdanID = intent.getStringExtra("dingdanID");
			dingdanIDTextView.setText(dingdanID);
			status = intent.getStringExtra("status");
			String from = null;
			if(status.equals("8")){
				from = "yituikuan";
			}else if(status.equals("2") ||status.equals("1")){
				from = "ketuikuan";
			}else {
				from = "tuikuanIng";
			}
			Log.i(TAG, "from:"+from);
			feiyongTextView.setText(intent.getStringExtra("price"));
			//reasonString = intent.getStringExtra("refundReason");
			//TODO 判断订单是否是已经取药
			getMedicine = isDingDanHasGetMedicine(status);

			Log.i(TAG, "from " + from);
			if(from.equals("yituikuan")){

				//如果是已经退完款
				//TODO 从已退款列表进入，已经退款了，选择设置成灰色，不能提交退款
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
				//退款完成的状态是8，但是不能确定是未取药退款完成的，还是已取药退款的，目前先不显示了
//				if(getMedicine){
//					//如果已经取药了，退款需要经过药房确认
//					LayoutInflater inflater =getLayoutInflater();
//					final View view = inflater.inflate(R.layout.activity_tuikuan_detail_finish_ok_layout, null);
//					statusLayout.removeAllViews();
//					statusLayout.addView(view);
//				}else{
//					//还没有取药，不需要经过药房确认
//					LayoutInflater inflater =getLayoutInflater();
//					final View view = inflater.inflate(R.layout.activity_tuikuan_jian_detail_finish_ok_layout, null);
//					statusLayout.removeAllViews();
//					statusLayout.addView(view);
//				}
				//统一只显示病人提交。。。。。。。退款完成
				LayoutInflater inflater =getLayoutInflater();
				final View view = inflater.inflate(R.layout.activity_refund_medicine_detail_finish_ok_layout, null);
				statusLayout.removeAllViews();
				statusLayout.addView(view);



			}else if(from.equals("tuikuanIng")){

				//正在退款中

				tuikuanBtn.setEnabled(false);
				tuikuanBtn.setBackgroundColor(getResources().getColor(R.color.light_gray));
				reasonSpinner.setBackgroundColor(getResources().getColor(R.color.light_gray));
				tuikuanShuoMingEditText.setText("从数据库中获取");
				tuikuanShuoMingEditText.setEnabled(false);
				reasonSpinner.setEnabled(false);
//				String reasonString = "医生未响应";//从数据库中获取
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
//				statusTextView.setText(status);



				if(getMedicine) {
					//如果已经取药了，退款需要经过药房确认

					//获取退款状态，根据退款状态显示状态流程图
//				status = intent.getIntExtra("status",0);
					Log.i(TAG, "TuiKuanDetailActivity status " + status);
					if(status.equals("3")){
						statusTextView.setText("病人提交");
						LayoutInflater inflater =getLayoutInflater();
						final View view = inflater.inflate(R.layout.activity_refund_medicine_detail_tijiao_layout, null);
						statusLayout.removeAllViews();
						statusLayout.addView(view);
					}else if(status.equals("4")){
						statusTextView.setText("医生同意");
						LayoutInflater inflater =getLayoutInflater();
						final View view = inflater.inflate(R.layout.activity_refund_medicine_detail_doctor_ok_layout, null);
						statusLayout.removeAllViews();
						statusLayout.addView(view);
					}else if(status.equals("5")){
						statusTextView.setText("药房同意");
						LayoutInflater inflater =getLayoutInflater();
						final View view = inflater.inflate(R.layout.activity_refund_medicine_detail_store_ok_layout, null);
						statusLayout.removeAllViews();
						statusLayout.addView(view);
					}else if(status.equals("6")){
						statusTextView.setText("医生拒绝");
						LayoutInflater inflater =getLayoutInflater();
						final View view = inflater.inflate(R.layout.activity_refund_medicine_detail_doctor_failed_layout, null);
						statusLayout.removeAllViews();
						statusLayout.addView(view);
					}else if(status.equals("7")){
						statusTextView.setText("药房拒绝");
						LayoutInflater inflater =getLayoutInflater();
						final View view = inflater.inflate(R.layout.activity_refund_medicine_detail_store_failed_layout, null);
						statusLayout.removeAllViews();
						statusLayout.addView(view);
					}

				}else{

					//未取药
					Log.i(TAG, "TuiKuanDetailActivity status " + status);
//					if(status.equals("3")){
//						statusTextView.setText("病人提交");
//						LayoutInflater inflater =getLayoutInflater();
//						final View view = inflater.inflate(R.layout.activity_tuikuan_jian_detail_tijiao_ok_layout, null);
//						statusLayout.removeAllViews();
//						statusLayout.addView(view);
//					}else if(status.equals("4")){
//						statusTextView.setText("医生同意");
//						LayoutInflater inflater =getLayoutInflater();
//						final View view = inflater.inflate(R.layout.activity_tuikuan_jian_detail_doctor_ok_layout, null);
//						statusLayout.removeAllViews();
//						statusLayout.addView(view);
//					}
				}


			}else{

				if(getMedicine){
					//如果已经取药了，退款需要经过药房确认
					statusTextView.setText("已取药");
					LayoutInflater inflater =getLayoutInflater();
					final View view = inflater.inflate(R.layout.activity_refund_medicine_detail_start_layout, null);
					statusLayout.removeAllViews();
					statusLayout.addView(view);

				}else{
					statusTextView.setText("未取药");
					LayoutInflater inflater =getLayoutInflater();
					final View view = inflater.inflate(R.layout.activity_refund_guahao_detail_start_layout, null);
					statusLayout.removeAllViews();
					statusLayout.addView(view);
				}
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
				tuikuanBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						showRefundDialog();
					}
				});

			}
		}else{
			return;
		}

	}

	//判断订单是否已经取药
	private boolean isDingDanHasGetMedicine(String status){
		boolean result= false;
		if(status.equals("1")){
			statusTextView.setText("未取药");
			result = false;
		}else if(status.equals("2")){
			statusTextView.setText("已取药");
			result = true;
		}else if(status.equals("3")){
			result = true;
		}else if(status.equals("4")){
			result = true;
		}else if(status.equals("5")){
			result = true;
		}else if(status.equals("6")){
			result = true;
		}else if(status.equals("7")){
			result = true;
		}else{

		}

		//TODO 能不能通过id判断是否已经取药。。如果已经在医生同意阶段，status=4，怎么判断是否已经取药了；退款完成status=8，怎么判断取药否

		return  result;

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
		final MyDialog dialog = new MyDialog(RefundMedicineDetailActivity.this, functionListView,R.style.load_dialog);
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

				//TODO 完成退款
				dialog.dismiss();

			}
		});

	}


	private  void tuikuan(){

		//TODO 退款接口以后更改


        //退款原因选其他时，存入“其他-原因”格式，在读取数据时（已退款、退款中、退款失败），可据此规则解析
		if(reasonString.equals("其他")){
			reasonString = reasonString + "-" + tuikuanShuoMingEditText.getText().toString();
		}
		httpUrl = http + tuiyaofeiUrl + dingdanIDTextView.getText().toString()+ reasonUrl + reasonString;
		Log.i(TAG, "refund medicin url:  " + httpUrl);
		try {

			HttpPost post = HttpUtil.getPost(httpUrl, null);
			JSONObject rev = HttpUtil.getString(post, 3);
			Log.e(TAG, "退药费rev:" + rev.toString());
			if(String.valueOf(rev.get("result")).equals("200")){
				Toast.makeText(RefundMedicineDetailActivity.this, "提交成功", Toast.LENGTH_LONG).show();

				Intent intent = new Intent(RefundMedicineDetailActivity.this, GuidanceBottomTabActivity.class);
				startActivity(intent);
				finish();

			}else{
				Toast.makeText(RefundMedicineDetailActivity.this, "退费失败", Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}


	}

}
