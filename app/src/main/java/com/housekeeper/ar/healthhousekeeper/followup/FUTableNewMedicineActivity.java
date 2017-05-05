package com.housekeeper.ar.healthhousekeeper.followup;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.FlowLayout;
import com.housekeeper.ar.healthhousekeeper.HttpUtil;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.MyDialog;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//新增处方
public class FUTableNewMedicineActivity extends BaseActivity {


	private String TAG="NewMedicineActivity";
	private FlowLayout newMedicineFlowLayout;
	//新处方列表，//标记是否被选择，初始化false
	private ArrayList<HashMap<String,String>> newMedicineLists =  new ArrayList<HashMap<String, String>>();
	//被选中新处方列表，用于删除//标记是否被选择，初始化false
	private ArrayList<HashMap<String,String>> selectedDeleteMedicineLists =  new ArrayList<HashMap<String, String>>();

	//被选中新处方列表，用于添加到新处方列表//标记是否被选择，初始化false
	private ArrayList<HashMap<String,String>> selectedMedicineLists =  new ArrayList<HashMap<String, String>>();

	//搜索的处方列表，用于添加到新处方列表//标记是否被选择，初始化false
	private ArrayList<HashMap<String,String>> searchMedicineLists =  new ArrayList<HashMap<String, String>>();

	private Button deleteBtn;
	private Button okBtn;
	private Button cancelBtn;
	private ImageView searchImageView;
	private Button backBtn;
	private ListView searchMedicineListView;


	private MyApp myApp;
	private String http;
	private static String httpUrl;
	private String getMedicineFromPinyinUrl = "shlc/doctor/drugs?pinyin=";
	private JSONArray jaMedicines;
	private JSONObject[] joMedicines;

	//选中的新处方index
	private int selectedPostion = 0;

	private ToastCommom toastCommom;

	boolean saved;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_follow_up_table_new_medicine);
		MyActivityManager.pushOneActivity(FUTableNewMedicineActivity.this);
		toastCommom = ToastCommom.createToastConfig();

		Toast.makeText(FUTableNewMedicineActivity.this,"频次用量与数量、价格之间的关系,et1等需要打开watcher吗？",Toast.LENGTH_SHORT).show();

		searchImageView = (ImageView)findViewById(R.id.new_medicine_search_image);
		searchImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				searchMedicineDialog();
			}
		});

		newMedicineFlowLayout = (FlowLayout)findViewById(R.id.new_medicine_flowlayout);
		backBtn = (Button)findViewById(R.id.back_btn);
		deleteBtn = (Button)findViewById(R.id.new_medicine_delete_btn);
		okBtn = (Button)findViewById(R.id.new_medicine_ok_btn);
		cancelBtn = (Button)findViewById(R.id.new_medicine_cancel_btn);

		cancelBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		//删除选中的处方
		deleteBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if(newMedicineLists == null || newMedicineLists.isEmpty()){
					toastCommom.ToastShow(FUTableNewMedicineActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "请选择处方");
					return;
				}
				HashMap<String,String> map = newMedicineLists.get(selectedPostion);
				newMedicineLists.remove(map);
				if(newMedicineLists.isEmpty()){
					selectedPostion = 0;
//					initPinCiView();
					enablePinCiView();
					initNewMedicineChildViews();
				}else if(selectedPostion == 0){
					selectedPostion = 0;
					initNewMedicineChildViews();
				}else{
					selectedPostion--;
					initNewMedicineChildViews();
				}


			}
		});
		//向当前处方中添加新处方
		okBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(!saved){
					toastCommom.ToastShow(FUTableNewMedicineActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "请先点击保存");
					return;
				}
				//TODO 首先检查频次是否都填写了
				if(newMedicineLists == null || newMedicineLists.isEmpty()){
					toastCommom.ToastShow(FUTableNewMedicineActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "请选择处方");
					return;
				}
				for(HashMap<String,String>map:newMedicineLists){
					map.put("selected","false");

					if(map.get("cishu").equals("")){
						//说明没有填写频次
						toastCommom.ToastShow(FUTableNewMedicineActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), map.get("yizhu")+"没有填频次，请先设置保存");

						return;
					}
				}
				Intent intent = new Intent();
				Bundle bundle=new Bundle();

				bundle.putParcelableArrayList("new", (ArrayList) newMedicineLists);
				intent.putExtras(bundle);
				//设置返回数据
				setResult(RESULT_OK, intent);

				finish();
			}
		});


		initNewMedicineChildViews();
		initPinCiView();
		
	}


	//初始化新增处方的流式布局
	private void initNewMedicineChildViews() {
		// TODO Auto-generated method stub

		if(newMedicineFlowLayout.getChildCount() > 0){
			newMedicineFlowLayout.removeAllViews();
		}

		ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = 5;
		lp.rightMargin = 5;
		lp.topMargin = 5;
		lp.bottomMargin = 5;
		if(newMedicineLists == null || newMedicineLists.isEmpty()){
			return;
		}
//		Log.i(TAG, "initHistoryDateChildViews selectedDate " + selectedDate);
		for(int i = 0; i < newMedicineLists.size(); i ++){
			final TextView view = new TextView(this);
			final HashMap<String,String> result = newMedicineLists.get(i);
			final int ii = i;
			view.setText(result.get("yizhu"));
			Log.i(TAG, "initHistoryDateChildViews result" + result);
			view.setTextSize(12);
			if(i==selectedPostion){
				view.setTextColor(Color.WHITE);
				view.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_flow_shape_pressed));
				try {
					setPinCi(newMedicineLists.get(selectedPostion));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}else{
				view.setTextColor(Color.BLACK);
				view.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_flow_shape_unpressed));
			}

			Log.i("GXF", "view flow onclick i " + i);
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
//					selectedDate = result;
					selectedPostion = ii;
					Log.i(TAG,"点击新处方 selectedPostion "+selectedPostion);
					view.setTextColor(Color.WHITE);
					view.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_flow_shape_pressed));
					//TODO 其他的view需要设置未选中状态
					initNewMedicineChildViews();

				}
			});
			newMedicineFlowLayout.addView(view, lp);
		}
	}
	private TextView productTV;
	private TextView guigeTV;
	private EditText shuliangET;
	private EditText jiageET;
	private LinearLayout pinciLayout;
//	private LinearLayout cishuLayout;
	private TextView tianshuET;
	private Spinner pinciSpinner;
	private Spinner reasonSpinner;

	String frequencyTypeStr;
	String cishuStr;
	String frequencyStr;
	String pinciStr;
	int ciPerDay;
	private EditText et1;
	private EditText et2;
	private EditText et3;
	private Button saveBtn;
	private String reason;

	private void enablePinCiView(){
		productTV.setText("");
		guigeTV.setText("");
		tianshuET.setEnabled(false);
		shuliangET.setEnabled(false);
		pinciSpinner.setEnabled(false);
		reasonSpinner.setEnabled(false);
		jiageET.setEnabled(false);
		tianshuET.setBackgroundColor(getResources().getColor(R.color.Grey));
		shuliangET.setBackgroundColor(getResources().getColor(R.color.Grey));
		pinciSpinner.setBackgroundColor(getResources().getColor(R.color.Grey));
		reasonSpinner.setBackgroundColor(getResources().getColor(R.color.Grey));
		jiageET.setBackgroundColor(getResources().getColor(R.color.Grey));
		if(pinciLayout.getChildCount() > 0 ){
			pinciLayout.removeAllViews();
		}
//		pinciLayout.removeAllViews();
	}

	private void  initPinCiView(){

		productTV = (TextView)findViewById(R.id.selected_new_medicine_product_tv);
		guigeTV = (TextView)findViewById(R.id.selected_new_medicine_guige_tv);
		saveBtn = (Button)findViewById(R.id.new_medicine_save_btn);

		pinciLayout = (LinearLayout)findViewById(R.id.pinci_edit);
//		cishuLayout = (LinearLayout)findViewById(R.id.cishu_edit);
		tianshuET = (EditText)findViewById(R.id.drugs_tianshu_et);
		shuliangET = (EditText)findViewById(R.id.drugs_shuangliang_et);
		pinciSpinner = (Spinner)findViewById(R.id.pinci_spinner);
		reasonSpinner = (Spinner)findViewById(R.id.special_case_spinner);
		jiageET = (EditText)findViewById(R.id.drugs_jiage_et);

		tianshuET.setEnabled(false);
		shuliangET.setEnabled(false);
		pinciSpinner.setEnabled(false);
		reasonSpinner.setEnabled(false);
		jiageET.setEnabled(false);
		tianshuET.setBackgroundColor(getResources().getColor(R.color.Grey));
		shuliangET.setBackgroundColor(getResources().getColor(R.color.Grey));
		pinciSpinner.setBackgroundColor(getResources().getColor(R.color.Grey));
		reasonSpinner.setBackgroundColor(getResources().getColor(R.color.Grey));
		jiageET.setBackgroundColor(getResources().getColor(R.color.Grey));

		final List<String> datas = getReasonData();
		ArrayAdapter<String> reasonAdapter = new ArrayAdapter<String>
				(FUTableNewMedicineActivity.this, R.layout.spinner_item,datas){
			@Override
			public View getDropDownView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.spinner_item_layout,
						null);
				TextView label = (TextView) view
						.findViewById(R.id.spinner_item_label);

				label.setText(datas.get(position));

				return view;
			}
		};
//		//设置样式
		reasonAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
		reasonSpinner.setAdapter(reasonAdapter);

		reasonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				reason = datas.get(i);
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});

		final List<String> fees = getPinCiData();
		ArrayAdapter<String> pinciAdapter = new ArrayAdapter<String>
				(FUTableNewMedicineActivity.this, R.layout.spinner_item,fees){
			@Override
			public View getDropDownView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.spinner_item_layout,
						null);
				TextView label = (TextView) view
						.findViewById(R.id.spinner_item_label);

				label.setText(fees.get(position));

				return view;
			}
		};
		//设置样式
		pinciAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
		pinciSpinner.setAdapter(pinciAdapter);

		pinciSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {
				// TODO Auto-generated method stub
				pinciStr = parent.getItemAtPosition(position).toString();
				Log.d(TAG, "medicineFragment pinciStr " + pinciStr);
				if (pinciStr.equals("TID")) {
					if(pinciLayout.getChildCount() == 3){
						//已经创建了编辑框
						return;
					}

					ciPerDay = 3;
					cishuStr = String.valueOf(ciPerDay);

					et1 = new EditText(FUTableNewMedicineActivity.this);
					et2 = new EditText(FUTableNewMedicineActivity.this);
					et3 = new EditText(FUTableNewMedicineActivity.this);
					et1.setLayoutParams(new ViewGroup.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT));
					et2.setLayoutParams(new ViewGroup.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT));
					et3.setLayoutParams(new ViewGroup.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT));

					et1.setEnabled(true);
					et2.setEnabled(true);
					et3.setEnabled(true);
					et1.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
					et2.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
					et3.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);

					et1.setBackgroundResource(R.drawable.buttonstyle);
					et2.setBackgroundResource(R.drawable.buttonstyle);
					et3.setBackgroundResource(R.drawable.buttonstyle);

					et1.setSingleLine();
					et2.setSingleLine();
					et3.setSingleLine();

					et1.setTextSize(12);
					et1.setGravity(Gravity.CENTER);
					et2.setTextSize(12);
					et2.setGravity(Gravity.CENTER);
					et3.setTextSize(12);
					et3.setGravity(Gravity.CENTER);


					et1.setText("1");
					et2.setText("1");
					et3.setText("1");
					shuliangET.setText("3");


//					et1.addTextChangedListener(watcher);
//					et2.addTextChangedListener(watcher);
//					et3.addTextChangedListener(watcher);

					et1.setSelectAllOnFocus(true);
					et2.setSelectAllOnFocus(true);
					et3.setSelectAllOnFocus(true);

					if(pinciLayout.getChildCount() > 0 ){
						pinciLayout.removeAllViews();
					}
					pinciLayout.addView(et1);
					pinciLayout.addView(et2);
					pinciLayout.addView(et3);
					pinciLayout.requestFocus();

				} else if (pinciStr.equals("QD")) {
					if(pinciLayout.getChildCount() == 1){
						//已经创建了编辑框
						return;
					}
					ciPerDay = 1;

					cishuStr = String.valueOf(ciPerDay);

					et1 = new EditText(FUTableNewMedicineActivity.this);
					et1.setLayoutParams(new ViewGroup.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT));
					et1.setEnabled(true);
					et1.setTextSize(12);
					et1.setGravity(Gravity.CENTER);

					et1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
					et1.setSingleLine();
					et1.setBackgroundResource(R.drawable.buttonstyle);


					et1.setText("1");
					shuliangET.setText("1");

//					et1.addTextChangedListener(watcher);
					et1.setSelectAllOnFocus(true);

					if(pinciLayout.getChildCount() > 0 ){
						pinciLayout.removeAllViews();
					}
					pinciLayout.addView(et1);
					pinciLayout.requestFocus();

				} else if (pinciStr.equals("BID")) {
					if(pinciLayout.getChildCount() == 2){
						//已经创建了编辑框
						return;
					}
					ciPerDay = 2;
					cishuStr = String.valueOf(ciPerDay);


					et1 = new EditText(FUTableNewMedicineActivity.this);
					et2 = new EditText(FUTableNewMedicineActivity.this);
					et1.setLayoutParams(new ViewGroup.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT));
					et2.setLayoutParams(new ViewGroup.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT));

					et1.setEnabled(true);
					et2.setEnabled(true);

					et1.setTextSize(12);
					et1.setGravity(Gravity.CENTER);
					et2.setTextSize(12);
					et2.setGravity(Gravity.CENTER);

					et1.setBackgroundResource(R.drawable.buttonstyle);
					et2.setBackgroundResource(R.drawable.buttonstyle);

					et1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
					et2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

					et1.setSingleLine();
					et2.setSingleLine();
					frequencyStr = newMedicineLists.get(selectedPostion).get("frequency");


					et1.setText("1");
					et2.setText("1");
					shuliangET.setText("2");

//					et1.addTextChangedListener(watcher);
//					et2.addTextChangedListener(watcher);

					et1.setSelectAllOnFocus(true);
					et2.setSelectAllOnFocus(true);

					if(pinciLayout.getChildCount() > 0 ){
						pinciLayout.removeAllViews();
					}

					pinciLayout.addView(et1);
					pinciLayout.addView(et2);

					pinciLayout.requestFocus();

				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
	}
	//设置频次
	private  void setPinCi(final HashMap<String,String> map)throws JSONException{

//		toastCommom.ToastShow(FUTableNewMedicineActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "setPinCi函数 判断医院若是小医院，则可编辑价格，后期判断");


		String drugJOStr = map.get("drugJO");
		final JSONObject joDrug =new JSONObject(drugJOStr);
//		productTV.setText(obj.getString("bxlx"));
		productTV.setText("商品名测试");
		guigeTV.setText(joDrug.getString("gg"));


		cishuStr = map.get("cishu");
		Log.i(TAG,"cushuStr "+cishuStr);


		tianshuET.setEnabled(true);
		shuliangET.setEnabled(true);
		pinciSpinner.setEnabled(true);
		reasonSpinner.setEnabled(true);
		tianshuET.setBackgroundResource(R.drawable.buttonstyle);
		shuliangET.setBackgroundResource(R.drawable.buttonstyle);
		pinciSpinner.setBackgroundResource(R.drawable.buttonstyle);
		reasonSpinner.setBackgroundResource(R.drawable.buttonstyle);
		//TODO 如果是校医院，可以调整价格
		if(true){
			jiageET.setEnabled(true);
			jiageET.setBackgroundResource(R.drawable.buttonstyle);
		}

		if(pinciLayout.getChildCount() > 0 ){
			pinciLayout.removeAllViews();
		}
//		pinciLayout.removeAllViews();
		reasonSpinner.setSelection(0, true);
		pinciSpinner.setSelection(0, true);
		shuliangET.setText("0");

		pinciStr = "";

		saveBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (pinciStr.equals("TID")) {
					frequencyStr = et1.getText() + "," + et2.getText() + "," + et3.getText();
					Log.v(TAG, "medicineFragment FrequencyType:" + pinciStr + " Frequency:" + frequencyStr);
				} else if (pinciStr.equals("QD")) {
					frequencyStr = et1.getText().toString() + "";
					Log.v(TAG, "medicineFragment FrequencyType:" + pinciStr + " Frequency:" + frequencyStr);
				} else if (pinciStr.equals("BID")) {
					frequencyStr = et1.getText() + "," + et2.getText();
					Log.v(TAG, "medicineFragment FrequencyType:" + pinciStr + " Frequency:" + frequencyStr);
				} else {

					toastCommom.ToastShow(FUTableNewMedicineActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "请选择频次");
					return;
				}
				Log.i(TAG, "medicineFragment freqOkBtn et1 " + et1 + " " + et1.getText().toString());
				Log.i(TAG, "medicineFragment freqOkBtn pinciStr " + pinciStr);

				if (pinciStr.equals("QD") && et1.getText().toString().equals("")) {

					toastCommom.ToastShow(FUTableNewMedicineActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "每天用量不能为空");
					return;
				} else if (pinciStr.equals("BID") && (et1.getText().toString().equals("") || et2.getText().toString().equals(""))) {

					toastCommom.ToastShow(FUTableNewMedicineActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "每天用量不能为空");
					return;
				} else if (pinciStr.equals("TID") && (et1.getText().toString().equals("") || et2.getText().toString().equals("") || et3.getText().toString().equals(""))) {

					toastCommom.ToastShow(FUTableNewMedicineActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "每天用量不能为空");
					return;
				}
				Log.i(TAG, "medicineFragment freqOkBtn 11 " + frequencyStr + " cishuStr " + cishuStr);

				if (frequencyStr.equals("") || tianshuET.getText().toString().equals("") || cishuStr.equals("")) {

					toastCommom.ToastShow(FUTableNewMedicineActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "数据不能为空");
					return;
				}
				if (shuliangET.getText().toString().equals("0") || shuliangET.getText().toString().equals("")) {

					toastCommom.ToastShow(FUTableNewMedicineActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "数量不能为空");
					return;
				}
				if (jiageET.getText().toString().equals("0") || jiageET.getText().toString().equals("")) {
					toastCommom.ToastShow(FUTableNewMedicineActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "价格不能为空");
					return;
				}
				if (false) {
					//TODO 如果数量大于库存
					toastCommom.ToastShow(FUTableNewMedicineActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "待做库存不足逻辑");
					showNotEnoughMedineDialog(map);
					return;
				}
//				HashMap<String, String> map = new HashMap<String, String>();
//				map.put("joDrug", joDrug.toString());
				try {
					map.put("yizhu", joDrug.getString("name"));
					map.put("cishu", cishuStr);
					map.put("tianshu", tianshuET.getText().toString());
					map.put("shuliang", shuliangET.getText().toString());
					map.put("frequency", frequencyStr);
					map.put("pinci", pinciStr);
//				map.put("position", "" + pos);
					map.put("pingjunyongliang", joDrug.getString("mcyl"));
					map.put("jiage", jiageET.getText().toString());
					map.put("drugId", String.valueOf(joDrug.getInt("id")));
					map.put("frequencyType", pinciStr);
					map.put("drugJO", joDrug.toString());
					map.put("reason", reason);
					map.put("selected", "false");

					toastCommom.ToastShow(FUTableNewMedicineActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "保存成功");
					saved = true;
				} catch (JSONException e) {
					e.printStackTrace();
				}


			}

		});


		tianshuET.setText("1");//默认值
		tianshuET.setSelectAllOnFocus(true);
//		tianshuET.addTextChangedListener(watcher);

//		tianshuET.addTextChangedListener(jiageWatcher);
		shuliangET.addTextChangedListener(jiageWatcher);

		shuliangET.setSelectAllOnFocus(true);
		jiageET.setSelectAllOnFocus(true);

		//如果已经有设置过频次了
		if(cishuStr != null &&!cishuStr.equals("")){

			modifyDrug(map);

		}


	}
	//修改药品信息
	private void modifyDrug(final HashMap<String,String> map) throws JSONException{
		Log.i(TAG,"currentMedicine modifyDrug map "+map);
		final JSONObject joDrug = new JSONObject(map.get("drugJO"));
		String yizhuStr = map.get("yizhu");
		String pingjunyongliangStr = map.get("pingjunyongliang");
		cishuStr = map.get("cishu");
		String tianshuStr = map.get("tianshu");
		String shuliangStr = map.get("shuliang");
		String jiageStr = map.get("jiage");
		String idStr = map.get("drugId");
		frequencyStr = map.get("frequency");
		frequencyTypeStr = map.get("frequencyType");
		String bxlxStr = joDrug.getString("bxlx");
		String ggStr = joDrug.getString("gg");
		reason = map.get("reason");

		pinciStr = frequencyTypeStr;


		int cishu = Integer.valueOf(cishuStr);
		pinciSpinner.setSelection(cishu, true);

		if(frequencyTypeStr.equals("QD")){

			ciPerDay = 1;
			Log.i(TAG, "modify currentMedicine QD  ");
			et1 = new EditText(FUTableNewMedicineActivity.this);
			Log.i(TAG, "modify currentMedicine QD frequencyStr " + frequencyStr);
			et1.setText(frequencyStr);
			Log.i(TAG, "modify currentMedicine QD et1.setTex ");
			et1.setLayoutParams(new ViewGroup.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT));
			et1.setEnabled(true);
			et1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
			et1.setSelectAllOnFocus(true);
			et1.setBackgroundResource(R.drawable.buttonstyle);

//			et1.addTextChangedListener(watcher);
//			et1.addTextChangedListener(jiageWatcher);

			et1.setSingleLine();

			et1.setTextSize(12);
			et1.setGravity(Gravity.CENTER);



			if(pinciLayout.getChildCount() > 0 ){
				pinciLayout.removeAllViews();
			}
			pinciLayout.addView(et1);
			pinciLayout.requestFocus();

		}else if(frequencyTypeStr.equals("BID")){
			String[] split = frequencyStr.split(",");
			ciPerDay = 2;
			et1 = new EditText(FUTableNewMedicineActivity.this);
			et2 = new EditText(FUTableNewMedicineActivity.this);

			et1.setLayoutParams(new ViewGroup.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT));
			et2.setLayoutParams(new ViewGroup.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT));

			et1.setEnabled(true);
			et2.setEnabled(true);

			et1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
			et2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

			et1.setBackgroundResource(R.drawable.buttonstyle);
			et2.setBackgroundResource(R.drawable.buttonstyle);

			et1.setSelectAllOnFocus(true);
			et2.setSelectAllOnFocus(true);

//			et1.addTextChangedListener(watcher);
//			et2.addTextChangedListener(watcher);

//			et1.addTextChangedListener(jiageWatcher);
//			et2.addTextChangedListener(jiageWatcher);

			et1.setSingleLine();
			et2.setSingleLine();

			et1.setTextSize(12);
			et1.setGravity(Gravity.CENTER);
			et2.setTextSize(12);
			et2.setGravity(Gravity.CENTER);


			et1.setText(split[0]);
			et2.setText(split[1]);


			if(pinciLayout.getChildCount() > 0 ){
				pinciLayout.removeAllViews();
			}

			pinciLayout.addView(et1);
			pinciLayout.addView(et2);

			pinciLayout.requestFocus();

		}else if(frequencyTypeStr.equals("TID")){
			String[] split = frequencyStr.split(",");

			ciPerDay = 3;
			et1 = new EditText(FUTableNewMedicineActivity.this);
			et2 = new EditText(FUTableNewMedicineActivity.this);
			et3 = new EditText(FUTableNewMedicineActivity.this);
			et1.setLayoutParams(new ViewGroup.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT));
			et2.setLayoutParams(new ViewGroup.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT));
			et3.setLayoutParams(new ViewGroup.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT));
			et1.setEnabled(true);
			et2.setEnabled(true);
			et3.setEnabled(true);

			et1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
			et2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
			et3.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

			et1.setBackgroundResource(R.drawable.buttonstyle);
			et2.setBackgroundResource(R.drawable.buttonstyle);
			et3.setBackgroundResource(R.drawable.buttonstyle);

			et1.setSelectAllOnFocus(true);
			et2.setSelectAllOnFocus(true);
			et3.setSelectAllOnFocus(true);

//			et1.addTextChangedListener(watcher);
//			et2.addTextChangedListener(watcher);
//			et3.addTextChangedListener(watcher);
//
//			et1.addTextChangedListener(jiageWatcher);
//			et2.addTextChangedListener(jiageWatcher);
//			et3.addTextChangedListener(jiageWatcher);

			et1.setSingleLine();
			et2.setSingleLine();
			et3.setSingleLine();

			et1.setTextSize(12);
			et1.setGravity(Gravity.CENTER);
			et2.setTextSize(12);
			et2.setGravity(Gravity.CENTER);
			et3.setTextSize(12);
			et3.setGravity(Gravity.CENTER);


			et1.setText(split[0]);
			et2.setText(split[1]);
			et3.setText(split[2]);


			if(pinciLayout.getChildCount() > 0 ){
				pinciLayout.removeAllViews();
			}
			pinciLayout.addView(et1);
			pinciLayout.addView(et2);
			pinciLayout.addView(et3);
			pinciLayout.requestFocus();
		}

		int pos = 0;
		List<String> datas = getReasonData();
		for(int i = 0; i < datas.size();i++){
			if(datas.get(i).equals(reason)){
				pos = i;
				break;
			}
		}
		reasonSpinner.setSelection(pos, true);

		tianshuET.setText(tianshuStr);
		shuliangET.setText(shuliangStr);
		jiageET.setText(jiageStr);



//		tianshuET.setSelectAllOnFocus(true);
//		tianshuET.addTextChangedListener(watcher);
	}
	final TextWatcher watcher = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {

			// TODO Auto-generated method stub
			if(pinciStr.equals("TID")&&et1 != null && et2 != null && et3 != null && !s.toString().equals("")
					&&!et1.getText().toString().equals("")&&!et2.getText().toString().equals("")&&!et3.getText().toString().equals("")
					&&!tianshuET.getText().toString().equals("")){
				int e1 = Integer.parseInt(et1.getText().toString().trim());
				int e2 = Integer.parseInt(et2.getText().toString().trim());
				int e3 = Integer.parseInt(et3.getText().toString().trim());
				int day = Integer.parseInt(tianshuET.getText().toString());
				shuliangET.setText(String.valueOf((e1+e2+e3)*day));
				cishuStr = String.valueOf(ciPerDay);
			}else if(pinciStr.equals("QD")&&et1 != null&&!s.toString().equals("")&&!et1.getText().toString().equals("")&&!tianshuET.getText().toString().equals("")){
				int e1 = Integer.parseInt(et1.getText().toString().trim());
				int day = Integer.parseInt(tianshuET.getText().toString());
				shuliangET.setText(String.valueOf(e1*day));
				cishuStr = String.valueOf(ciPerDay);
			} else if(pinciStr.equals("BID")&&et1 != null && et2 != null && !s.toString().equals("")
					&&!et1.getText().toString().equals("")&&!et2.getText().toString().equals("")&&!tianshuET.getText().toString().equals("")){

				int e1 = Integer.parseInt(et1.getText().toString().trim());
				int e2 = Integer.parseInt(et2.getText().toString().trim());

				int day = Integer.parseInt(tianshuET.getText().toString());
				shuliangET.setText(String.valueOf((e1+e2)*day));
				cishuStr = String.valueOf(ciPerDay);
				Log.i(TAG,"txtwatcher BID e1 "+e1);
				Log.i(TAG,"txtwatcher BID e2 "+e1);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
									  int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub

		}
	};

	final TextWatcher jiageWatcher = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
			Log.i(TAG,"currentMedicine modify tianshuET "+tianshuET);
			Log.i(TAG,"currentMedicine modify et1 "+et1);
			Log.i(TAG,"currentMedicine modify pinciStr "+pinciStr);
			// TODO Auto-generated method stub
			if(pinciStr.equals("TID")&&et1 != null && et2 != null && et3 != null && !s.toString().equals("")
					&&!et1.getText().toString().equals("")&&!et2.getText().toString().equals("")&&!et3.getText().toString().equals("")
					&&!tianshuET.getText().toString().equals("")&&!shuliangET.getText().toString().equals("")){

				int shuliang = Integer.valueOf(shuliangET.getText().toString());

				String drugJOStr = newMedicineLists.get(selectedPostion).get("drugJO");
				try {
					final JSONObject joDrug =new JSONObject(drugJOStr);
					jiageET.setText(String.valueOf(joDrug.getDouble("dj")/100 * shuliang));
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}else if(pinciStr.equals("QD")&&et1 != null&&!s.toString().equals("")&&!et1.getText().toString().equals("")&&!tianshuET.getText().toString().equals("")&&!shuliangET.getText().toString().equals("")){
				int shuliang = Integer.valueOf(shuliangET.getText().toString());

				String drugJOStr = newMedicineLists.get(selectedPostion).get("drugJO");
				try {
					final JSONObject joDrug =new JSONObject(drugJOStr);
					jiageET.setText(String.valueOf(joDrug.getDouble("dj")/100 * shuliang));
				} catch (JSONException e) {
					e.printStackTrace();
				}

			} else if(pinciStr.equals("BID")&&et1 != null && et2 != null && !s.toString().equals("")
					&&!et1.getText().toString().equals("")&&!et2.getText().toString().equals("")&&!tianshuET.getText().toString().equals("")&&!shuliangET.getText().toString().equals("")){


				int shuliang = Integer.valueOf(shuliangET.getText().toString());

				String drugJOStr = newMedicineLists.get(selectedPostion).get("drugJO");
				try {
					final JSONObject joDrug =new JSONObject(drugJOStr);
					jiageET.setText(String.valueOf(joDrug.getDouble("dj")/100 * shuliang));
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
									  int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub

		}
	};
	private List<String> getPinCiData(){
		List<String> dataList = new ArrayList<String>();
		dataList.add("请选择频次");
		dataList.add("QD");//一天一次
		dataList.add("BID");//一天两次
		dataList.add("TID");//一天三次


		return dataList;
	}

	private List<String> getReasonData(){
		List<String> dataList = new ArrayList<String>();
		dataList.add("特殊情况说明");
		dataList.add("特殊情况说明1");
		dataList.add("特殊情况说明2");



		return dataList;
	}



	//打开搜索对话框
	private void searchMedicineDialog(){

		LayoutInflater inflater =getLayoutInflater();
		final View functionListView = inflater.inflate(R.layout.activity_follow_up_table_search_medicine_dialog, null);
//		dialog = new AlertDialog.Builder(DiagnosisActivity.this, R.style.load_dialog).setView(functionListView).show();

		final MyDialog dialog = new MyDialog(FUTableNewMedicineActivity.this, functionListView,R.style.load_dialog);
		int width = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
//		int height = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
		dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

		dialog.show();


		Button dialogSearchBtn = (Button)dialog.findViewById(R.id.search_btn);
		Button dialogCancelBtn = (Button)dialog.findViewById(R.id.cancel_btn);
		final EditText dialogEditText = (EditText)dialog.findViewById(R.id.add_medicine_et);
		Button dialogOkBtn = (Button)functionListView.findViewById(R.id.ok_btn);
		searchMedicineListView = (ListView)functionListView.findViewById(R.id.new_medicine_listview);


		dialogOkBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(selectedMedicineLists == null ||selectedMedicineLists.isEmpty()){
					toastCommom.ToastShow(FUTableNewMedicineActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "请选择");
					return;
				}
				if(newMedicineLists != null && !newMedicineLists.isEmpty()){
					selectedPostion = newMedicineLists.size();
				}else{
					selectedPostion = 0;
				}


//				newMedicineLists.addAll(selectedMedicineLists);



				for (HashMap<String,String> map:selectedMedicineLists){

                    Log.i(TAG,"dialogOkBtn "+map.toString());
					try {

						//添加空的cishu是为了区分是否已经填写频次了
						JSONObject joDrug = new JSONObject(map.get("drugJO"));
						map.put("yizhu", joDrug.getString("name"));
						map.put("drugName", joDrug.getString("name"));
						map.put("unit", "g");
						map.put("cishu", "");
						map.put("tianshu", "");
						map.put("shuliang", "");
						map.put("frequency", "");
//				map.put("pinci", pinciStr);
//				map.put("position", "" + pos);
						map.put("pingjunyongliang", joDrug.getString("mcyl"));
						map.put("jiage", "");
						map.put("drugId", String.valueOf(joDrug.getInt("id")));
						map.put("frequencyType","");
						map.put("drugJO", joDrug.toString());
						map.put("reason", "");
						map.put("selected","false");

						newMedicineLists.add(map);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				initNewMedicineChildViews();
				dialog.dismiss();

			}
		});
		dialogCancelBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialogSearchBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String pyStr = dialogEditText.getText().toString().trim().toUpperCase();
				Log.d(TAG, "addMedicine search pyStr " + pyStr);

				if (searchMedicineLists != null && !searchMedicineLists.isEmpty()) {
					searchMedicineLists.clear();

					searchMedicinesItemBaseAdapter.notifyDataSetChanged();
				}


				if (pyStr == null || pyStr.equals("")) {
					Log.d(TAG, "addMedicine search pyStr null");
					toastCommom.ToastShow(FUTableNewMedicineActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "输入为空");
					return;

				}
				if (selectedMedicineLists != null && !selectedMedicineLists.isEmpty()) {
					selectedMedicineLists.clear();
				}

				//	if(!pyStr.equals(null)){
//					Log.d(TAG,"addMedicine search pyStr not null");
				getMedicineFromPinyin(pyStr);
//					Log.d(TAG, "addMedicine search pyStr not null after");
				showMedicineList(dialog);

				//	}
			}
		});

	}

	private void getMedicineFromPinyin(String pyStr){
		myApp = (MyApp)getApplication();
		http = myApp.getHttp();
		JSONObject joRev = new JSONObject();
		try {
			Log.v(TAG, "url="+ http+getMedicineFromPinyinUrl);

			joRev = HttpUtil.getResultForHttpGet(http + getMedicineFromPinyinUrl + pyStr.toUpperCase());
			Log.v(TAG, "addMedicine joRevMessage:"+joRev.toString());
			String resultStr = joRev.getString("result");
			if(resultStr.equals("200")){
				jaMedicines = joRev.getJSONArray("value");
				joMedicines = new JSONObject[jaMedicines.length()];
				for(int i=0; i<jaMedicines.length(); i++){
					joMedicines[i] = jaMedicines.getJSONObject(i);
					Log.v(TAG, "addMedicine joDiseases"+joMedicines[i].toString());
				}

			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	SearchMedicinesItemBaseAdapter searchMedicinesItemBaseAdapter;
	private void showMedicineList(Dialog dialog){

         ////////////////////测试数据///////////////////////////////////

		HashMap<String, String> tmpMap = new HashMap<String, String>();

			JSONObject tmpJoDrug = new JSONObject();

		try {
			tmpJoDrug.put("name","测试New");
			tmpJoDrug.put("mcyl", "");
			tmpJoDrug.put("id", "1");
			tmpJoDrug.put("dj", "1");
			tmpJoDrug.put("gg", "5");
			tmpJoDrug.put("product", "product");
			tmpJoDrug.put("guige", "1");
			tmpMap.put("drugJO", tmpJoDrug.toString());
			tmpMap.put("selected","false");
			searchMedicineLists.add(tmpMap);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		/////////////////////////////////////////////////////////////////


		searchMedicinesItemBaseAdapter = new SearchMedicinesItemBaseAdapter(FUTableNewMedicineActivity.this,
				searchMedicineLists, R.layout.search_medicine_dialog_item,
				new String[]{"name","product","guige","layout"},
				new int[]{R.id.medicine_name_item, R.id.medicine_product_name_item,R.id.medicine_guige_item,R.id.search_medicine_dialog_item_layout});
		searchMedicineListView.setAdapter(searchMedicinesItemBaseAdapter);
//		gridItemBaseAdapter.notifyDataSetChanged();
		searchMedicineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				final HashMap<String, String> map = (HashMap<String, String>) adapterView.getItemAtPosition(i);

				if (map.get("selected").equals("false")) {
					map.put("selected", "true");
					selectedMedicineLists.add(map);
				} else {
					selectedMedicineLists.remove(map);
					map.put("selected", "false");
				}


				searchMedicinesItemBaseAdapter.notifyDataSetChanged();
			}
		});

		Log.d(TAG, "addMedicine showMedicineList jaMedicines "+jaMedicines);
		if(jaMedicines == null || jaMedicines.length() == 0){

			toastCommom.ToastShow(FUTableNewMedicineActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "无搜索结果");
			return;
		}
		for(int i=0; i<jaMedicines.length(); i++){
			HashMap<String, String> map = new HashMap<String, String>();
			try {
				JSONObject joDrug = joMedicines[i].getJSONObject("drug");
//				map.put("name", joDrug.getString("name"));
				Log.v(TAG, joDrug.getString("name"));
				joDrug.put("dj", joMedicines[i].getDouble("dj"));
				map.put("drugJO", joDrug.toString());
				map.put("selected","false");
				searchMedicineLists.add(map);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}

	class SearchMedicinesItemBaseAdapter extends BaseAdapter {
		private class ButtonViewHolder{
			TextView drugName;
			TextView productName;
			TextView guigeTV;
			LinearLayout layout;
		}

		private ArrayList<HashMap<String, String>> mAppList;
		private LayoutInflater mInflater;
		private Context mContext;
		private String[] keyString;
		private int[] valueViewID;
		private ButtonViewHolder holder;
		//private OnHDListener mCallBack;
		public SearchMedicinesItemBaseAdapter(Context c,
											ArrayList<HashMap<String, String>> appList,
											int resource,
											String[] from, int[] to) {
			mAppList = appList;
			mContext = c;
			mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			keyString = new String[from.length];
			valueViewID = new int[to.length];
			System.arraycopy(from, 0, keyString, 0, from.length);
			System.arraycopy(to, 0, valueViewID, 0, to.length);

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mAppList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mAppList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView != null) {
				holder = (ButtonViewHolder) convertView.getTag();
			} else {
				convertView = mInflater.inflate(R.layout.search_medicine_dialog_item, null);
				holder = new ButtonViewHolder();
				holder.drugName = (TextView)convertView.findViewById(valueViewID[0]);
				holder.productName = (TextView)convertView.findViewById(valueViewID[1]);
				holder.guigeTV = (TextView)convertView.findViewById(valueViewID[2]);
				holder.layout = (LinearLayout)convertView.findViewById(valueViewID[3]);
				convertView.setTag(holder);
			}

			HashMap<String, String> appInfo = mAppList.get(position);
			if (appInfo != null) {
				String drugJO = mAppList.get(position).get("drugJO");
				try {
					JSONObject obj = new JSONObject(drugJO);
					holder.drugName.setText(obj.getString("name"));
					//TODO 商品名目前没有字段，是bxlx吗？
					holder.productName.setText("商品名测试");
//					holder.productName.setText(obj.getString("bxlx"));
					holder.guigeTV.setText(obj.getString("gg"));

					if(appInfo.get("selected").equals("true")){

						//如果选中则将字体变成白色，背景变成绿色
						holder.layout.setBackgroundColor(getResources().getColor(R.color.background_green));
						holder.productName.setTextColor(getResources().getColor(R.color.white));
						holder.drugName.setTextColor(getResources().getColor(R.color.white));
						holder.guigeTV.setTextColor(getResources().getColor(R.color.white));

					}else{
						holder.layout.setBackgroundColor(getResources().getColor(R.color.white));
						holder.productName.setTextColor(getResources().getColor(R.color.black));
						holder.drugName.setTextColor(getResources().getColor(R.color.black));
						holder.guigeTV.setTextColor(getResources().getColor(R.color.black));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}


			}
			return convertView;
		}


	}



	//提示库存不足对话框
	private void showNotEnoughMedineDialog(final HashMap<String,String> map){
		LayoutInflater inflater =getLayoutInflater();
		final View functionListView = inflater.inflate(R.layout.duplicate_medicine_dialog, null);
//		dialog = new AlertDialog.Builder(DiagnosisActivity.this, R.style.load_dialog).setView(functionListView).show();

		final MyDialog   dialog = new MyDialog(FUTableNewMedicineActivity.this, functionListView,R.style.load_dialog);
		int width = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
//		int height = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
		dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
		dialog.show();

		Button okBtn = (Button)functionListView.findViewById(R.id.ok_btn);
		Button cancelBtn = (Button)functionListView.findViewById(R.id.cancel_btn);
		TextView name = (TextView)functionListView.findViewById(R.id.name_tv);
		TextView title = (TextView)functionListView.findViewById(R.id.title_tv);
		TextView medicine = (TextView)functionListView.findViewById(R.id.medicine_tv);
		name.setText("库存不足，请重新填写数量");
		title.setText("库存不足");
		medicine.setText(map.get("yizhu"));

		okBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
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

}
