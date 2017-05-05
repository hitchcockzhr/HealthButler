package com.housekeeper.ar.healthhousekeeper.checkstand;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyDialog;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lenovo on 2016/3/5.
 */
//病人付款列表
public class PatientPayListActivity extends BaseActivity {


    String TAG = "PatientPayListActivity";



    String http, httpUrl;

    private ToastCommom toastCommom;
    private ListView payListView;
    private List<HashMap<String,String>> data = new ArrayList<>();
    private List<HashMap<String,String>> selectedData = new ArrayList<>();
    private Button backBtn;
    private Button payBtn;
    private PatientPayListAdapter patientPayListAdapter;
    private String name="",age="",sex="";
    private TextView nameView,ageView,sexView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_pay_list);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(PatientPayListActivity.this);

        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        payBtn = (Button)findViewById(R.id.pay_btn);
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPayDialog();
            }
        });
        payListView = (ListView)findViewById(R.id.patient_pay_listview);
        patientPayListAdapter = new PatientPayListAdapter(PatientPayListActivity.this,data);
        payListView.setAdapter(patientPayListAdapter);

        nameView = (TextView)findViewById(R.id.patient_name_tv);
        sexView = (TextView)findViewById(R.id.patient_sex_tv);
        ageView = (TextView)findViewById(R.id.patient_age_tv);

        Intent intent = getIntent();
        if(intent != null) {
            name = intent.getStringExtra("name");
            age = intent.getStringExtra("age");
            sex = intent.getStringExtra("sex");
        }
        nameView.setText(name);
        sexView.setText(sex);
        ageView.setText(age);

//        payListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Log.i(TAG, "onItemSelected " + data.get(position).get("type"));
//                if (data.get(position).get("type").equals("处方费")) {
//                    //TODO　进入处方详情
//                    Intent intent = new Intent(PatientPayListActivity.this, MedicineDetailActivity.class);
//
//                    intent.putExtra("dingdanID", data.get(position).get("id"));
//                    intent.putExtra("name", name);
//                    intent.putExtra("sex", sex);
//                    intent.putExtra("age", age);
//
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        payListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick " + data.get(position).get("type"));
                if (data.get(position).get("type").equals("处方费")) {
                    //TODO　进入处方详情
                    Intent intent = new Intent(PatientPayListActivity.this, MedicineDetailActivity.class);

                    intent.putExtra("dingdanID", data.get(position).get("id"));
                    intent.putExtra("name", name);
                    intent.putExtra("sex", sex);
                    intent.putExtra("age", age);

                    startActivity(intent);
                }
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
    private void initData(){
        data.clear();
        HashMap<String,String> map = new HashMap<>();
        map.put("id","111111");
        map.put("type","挂号费");
        map.put("jiage","12.0");

        data.add(map);

        HashMap<String,String> map2 = new HashMap<>();
        map2.put("id","222222");
        map2.put("type","处方费");
        map2.put("jiage","30.0");

        data.add(map2);
    }
    private void showPayDialog() {
        selectedData.clear();
        selectedData.addAll(patientPayListAdapter.getSelectedData());
        Log.i(TAG, "selectedData " + selectedData.toString());
        if(selectedData == null || selectedData.isEmpty()){
            toastCommom.ToastShow(PatientPayListActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "请选中订单");
            return;
        }
        Log.i(TAG,"selectedData "+selectedData.toString());
        LayoutInflater inflater =getLayoutInflater();
        final View functionListView = inflater.inflate(R.layout.dialog_finance, null);
//		dialog = new AlertDialog.Builder(DiagnosisActivity.this, R.style.load_dialog).setView(functionListView).show();

        final MyDialog dialog = new MyDialog(PatientPayListActivity.this, functionListView, R.style.load_dialog);
        int width = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
//		int height = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        Button okBtn = (Button)dialog.findViewById(R.id.dialog_shoufei_ok_btn);
        Button cancelBtn = (Button)dialog.findViewById(R.id.dialog_shoufei_cancel_btn);
        final TextView yingfuTV = (TextView)dialog.findViewById(R.id.yingfu_tv);
        final EditText shifuTV = (EditText)dialog.findViewById(R.id.shifu_tv);
        final TextView zhaolingTV = (TextView)dialog.findViewById(R.id.zhaoling_tv);


        double jiage = 0;
        for(HashMap<String,String> map:selectedData){
            jiage = jiage + Double.valueOf(map.get("jiage"));
        }

        yingfuTV.setText(""+jiage);
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
					toastCommom.ToastShow(PatientPayListActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "实付金额小于应付金额");
//                    Toast.makeText(PatientPayListActivity.this, "实付金额小于应付金额", Toast.LENGTH_LONG).show();
                    return;
                }


//				HashMap<String, String> map = fukuandata.get(selectedFuKuanIndex);
//				fukuandata.remove(map);
//				showNextAfterDelete();

                //TODO 付款实现

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
