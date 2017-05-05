package com.housekeeper.ar.healthhousekeeper.checkstand;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.HttpUtil;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.MyDialog;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//付费的处方详情
public class MedicineDetailActivity extends BaseActivity {

	private TextView dingdanIDTextView;
	private TextView statusTextView;
	private TextView zongjiTextView;
	private ListView chufangListView;
	private Button payBtn;
	private Button cancelBtn;
	private Button backBtn;
	private ChuFangListAdapter chuFangListAdapter;
	private List<HashMap<String,String>> data= new ArrayList<HashMap<String, String>>();
	private List<HashMap<String,String>> tuiYaodata= new ArrayList<HashMap<String, String>>();

	JSONArray jaSelected;
	TextView doc_name_tv;
	MyApp myApp;
    String http, httpUrl;
    String approveUrl = "shlc/doctor/approveRefundPre/prescriptionRecord/";
	JSONObject joDoc;
	//用于显示每列6个Item项。
	int VIEW_COUNT = 6;

	//用于显示页号的索引
	int index = 0;
	private String TAG="MedicineDetailActivity";

	private int selectedShenQingTuiYaoListIndex = 0;
	private TextView patientNameTextView;
	private TextView patientAgeTextView;
	private TextView patientSexTextView;
	private TextView suiTV;
	private String name="",age="",sex="",dingdanID="";
	private ToastCommom toastCommom;
	JSONArray jaDrug;
    String zongjiaString;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyActivityManager.pushOneActivity(MedicineDetailActivity.this);
//		setContentView(R.layout.activity_fukuan_success);
		setContentView(R.layout.activity_medicine_detail);
		myApp = (MyApp)getApplication();
        http = myApp.getHttp();
		toastCommom = ToastCommom.createToastConfig();

		backBtn = (Button)findViewById(R.id.back_btn);
		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		patientAgeTextView = (TextView)findViewById(R.id.patient_age_tv);
		patientSexTextView = (TextView)findViewById(R.id.patient_sex_tv);
		patientNameTextView = (TextView)findViewById(R.id.patient_name_tv);
		suiTV = (TextView)findViewById(R.id.patient_age_sui);
		doc_name_tv = (TextView)findViewById(R.id.doctor_name_tv);


		payBtn = (Button)findViewById(R.id.ok_btn);


		cancelBtn = (Button)findViewById(R.id.cancel_btn);


		dingdanIDTextView = (TextView)findViewById(R.id.dingdan_id);
		statusTextView = (TextView)findViewById(R.id.status_tv);
		zongjiTextView = (TextView)findViewById(R.id.zongji_tv);
		backBtn = (Button)findViewById(R.id.back_btn);

		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});


		chufangListView = (ListView)findViewById(R.id.chufang_listview);

		chuFangListAdapter = new ChuFangListAdapter(MedicineDetailActivity.this,data);

		chufangListView.setAdapter(chuFangListAdapter);
		zongjiTextView.setText("0");
		Intent intent = getIntent();
		if(intent !=null){
			dingdanID = intent.getStringExtra("dingdanID");
			name = intent.getStringExtra("name");
			age = intent.getStringExtra("age");
			sex = intent.getStringExtra("sex");
            zongjiaString = intent.getStringExtra("price");
			try {
				jaDrug = new JSONArray(intent.getStringExtra("drugList"));
				Log.i(TAG, "jaDrug:"+jaDrug.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			init(dingdanID);

		}






	}
	private void init(final String dingdanID){

		doc_name_tv.setText("测试医生");


        setPatient();

		initData(dingdanID);
		dingdanIDTextView.setText(dingdanID);
        payBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//TODO 付款
				if (data != null) {

					showPayDialog();
				}
			}
		});
        cancelBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if (data != null) {

					showCancelDialog();
				}
			}
		});

		zongjiTextView.setText(zongjiaString);
	}
	private void setPatient(){
		patientNameTextView.setText(name);
		patientAgeTextView.setText(age);
		patientSexTextView.setText(sex);

	}

	//根据订单号获取处方列表
	private void initData(String id){

		data.clear();

		for(int i=0; i<jaDrug.length(); i++){
            try {
                JSONObject jo = jaDrug.getJSONObject(i);
                HashMap<String,String> map = new HashMap<String, String>();
                map.put("name", jo.getString("drugName"));
                map.put("num", String.valueOf(jo.getLong("count")));
                map.put("danjia", String.valueOf(jo.getLong("dj")/100.00));
                data.add(map);
            } catch (JSONException e) {
                e.printStackTrace();
            }


		}
	}



	private String getZongJia(){
		String zongjiaString="0";

        /*
		float zongjia = 0;
		if (data != null){
			for(HashMap<String,String> m:data){
				float num = Float.valueOf(m.get("num"));
				float danjia = Float.valueOf(m.get("danjia"));
				float jiage = num*danjia;

				zongjia = zongjia+jiage;
			}
		}
		zongjiaString = String.valueOf(zongjia);
		*/
		return zongjiaString;
	}
	private void showPayDialog() {
	LayoutInflater inflater =getLayoutInflater();
	final View functionListView = inflater.inflate(R.layout.dialog_finance, null);
//		dialog = new AlertDialog.Builder(DiagnosisActivity.this, R.style.load_dialog).setView(functionListView).show();

	final MyDialog dialog = new MyDialog(MedicineDetailActivity.this, functionListView, R.style.load_dialog);
	int width = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
//		int height = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
	dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
	dialog.show();

	Button okBtn = (Button)dialog.findViewById(R.id.dialog_shoufei_ok_btn);
	Button cancelBtn = (Button)dialog.findViewById(R.id.dialog_shoufei_cancel_btn);
	final TextView yingfuTV = (TextView)dialog.findViewById(R.id.yingfu_tv);
	final EditText shifuTV = (EditText)dialog.findViewById(R.id.shifu_tv);
	final TextView zhaolingTV = (TextView)dialog.findViewById(R.id.zhaoling_tv);


	yingfuTV.setText(""+zongjiTextView.getText().toString());
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
				toastCommom.ToastShow(MedicineDetailActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "实付金额小于应付金额");
//                    Toast.makeText(PatientPayListActivity.this, "实付金额小于应付金额", Toast.LENGTH_LONG).show();
				return;
			}
            String payUrl = "shlc/healthButler/payment/prescriptionRecord?refId=";
            String realPriceurl = "&realPrice=";
            httpUrl = http + payUrl + dingdanID + realPriceurl+shifuD;
            Log.i(TAG, "pay url:"+httpUrl);
            HttpPost post = HttpUtil.getPost(httpUrl, null);
            JSONObject jsonObject = HttpUtil.getString(post, 3);
            Log.i(TAG, "pay result:"+jsonObject.toString());
            //TODO 付款实现
			finish();

			dialog.dismiss();

		}
	});
	cancelBtn.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			dialog.dismiss();
		}
	});
}
	//取消订单对话框
	private void showCancelDialog(){
		LayoutInflater inflater =getLayoutInflater();
		final View functionListView = inflater.inflate(R.layout.dialog_refund, null);
//		dialog = new AlertDialog.Builder(DiagnosisActivity.this, R.style.load_dialog).setView(functionListView).show();

		final MyDialog dialog = new MyDialog(MedicineDetailActivity.this, functionListView,R.style.load_dialog);
		dialog.show();
		int width = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
//		int height = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
		dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

		TextView titleTV = (TextView)dialog.findViewById(R.id.textView1);
		TextView tvTV = (TextView)dialog.findViewById(R.id.content_tv);
		Button okBtn = (Button)dialog.findViewById(R.id.ok_btn);
		Button cancelBtn = (Button)dialog.findViewById(R.id.cancel_btn);

		titleTV.setText("取消订单");
		tvTV.setText("确定取消订单吗？");

		okBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
                //TODO 取消订单
                finish();
			}
		});
		cancelBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
			}
		});



	}



}
