package com.housekeeper.ar.healthhousekeeper.community;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyDialog;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;
import com.housekeeper.ar.healthhousekeeper.cascade.activity.AddressBaseActivity;

import java.util.ArrayList;
import java.util.HashMap;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

/**
 * Created by lenovo on 2016/3/5.
 */
//医院名称维护
public class AddHospitalActivity extends AddressBaseActivity implements View.OnClickListener, OnWheelChangedListener {


    String TAG = "AddCommunityActivity";



    String http, httpUrl;

    private ToastCommom toastCommom;
    private LinearLayout districtLayout;
    private EditText hospitalEditText;
    private TextView districtTextView;
    private Button backBtn;
    private Button saveBtn;
    private LinearLayout communityLayout;
    private int EXIST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_address);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(AddHospitalActivity.this);

        districtLayout = (LinearLayout)findViewById(R.id.district_layout);
        hospitalEditText = (EditText)findViewById(R.id.hospital_et);
        districtTextView = (TextView)findViewById(R.id.address_tv);
        backBtn = (Button)findViewById(R.id.back_btn);
        saveBtn = (Button)findViewById(R.id.btn_confirm);

        communityLayout = (LinearLayout)findViewById(R.id.community_layout);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        districtLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAddressDialog();
            }
        });

        communityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddHospitalActivity.this,ExistingCommunityListActivity.class);
                startActivityForResult(intent,EXIST);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(districtTextView.getText().toString().equals("")){
                    toastCommom.ToastShow(AddHospitalActivity.this,(ViewGroup)findViewById(R.id.toast_layout_root),"地区不能为空");
                    return;
                }
                if(hospitalEditText.getText().toString().equals("")){
                    toastCommom.ToastShow(AddHospitalActivity.this,(ViewGroup)findViewById(R.id.toast_layout_root),"医院地址不能为空");
                    return;
                }
                Intent mIntent = getIntent();
                mIntent.putExtra("name",hospitalEditText.getText().toString());
                mIntent.putExtra("address",districtTextView.getText().toString());
                setResult(RESULT_OK, mIntent);
                finish();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
//        init();
    }


    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private String addressString="";
//    private Button mBtnConfirm;
    //地址对话框
    private void showAddressDialog(){
        LayoutInflater inflater =getLayoutInflater();
        final View functionListView = inflater.inflate(R.layout.dialog_address, null);
//		dialog = new AlertDialog.Builder(DiagnosisActivity.this, R.style.load_dialog).setView(functionListView).show();

        final MyDialog dialog = new MyDialog(AddHospitalActivity.this, functionListView,R.style.load_dialog);
        dialog.show();
        int width = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
//		int height = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button okBtn = (Button)dialog.findViewById(R.id.ok_btn);
        Button cancelBtn = (Button)dialog.findViewById(R.id.cancel_btn);


        setUpViews(functionListView);
        setUpListener();
        setUpData();

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showSelectedResult();
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

    private void setUpViews(View view) {
        mViewProvince = (WheelView) view.findViewById(R.id.id_province);
        mViewCity = (WheelView) view.findViewById(R.id.id_city);
        mViewDistrict = (WheelView)view.findViewById(R.id.id_district);
//        mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
    }

    private void setUpListener() {
        // ���change�¼�
        mViewProvince.addChangingListener(this);
        // ���change�¼�
        mViewCity.addChangingListener(this);
        // ���change�¼�
        mViewDistrict.addChangingListener(this);
        // ���onclick�¼�
//        mBtnConfirm.setOnClickListener(this);
    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(AddHospitalActivity.this, mProvinceDatas));
        // ���ÿɼ���Ŀ����
        mViewProvince.setVisibleItems(3);
        mViewCity.setVisibleItems(3);
        mViewDistrict.setVisibleItems(3);
        updateCities();
        updateAreas();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    /**
     * ��ݵ�ǰ���У�������WheelView����Ϣ
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[] { "" };
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        mViewDistrict.setCurrentItem(0);
    }

    /**
     * ��ݵ�ǰ��ʡ��������WheelView����Ϣ
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[] { "" };
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_confirm:
//                showSelectedResult();
//                break;
            default:
                break;
        }
    }

    private void showSelectedResult() {
        addressString = mCurrentProviceName + "," + mCurrentCityName + "," + mCurrentDistrictName;
        districtTextView.setText(addressString);
        Toast.makeText(AddHospitalActivity.this, "选择地址:" + mCurrentProviceName + "," + mCurrentCityName + ","
                + mCurrentDistrictName + "," + mCurrentZipCode, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EXIST && resultCode == RESULT_OK) {
            ///测试activity间传递list
            Bundle bundle = data.getExtras();

            ArrayList list2 = bundle.getParcelableArrayList("selectedlist");

            //如果是社区
            if (list2 != null && !list2.isEmpty()) {

                Log.i("GXF", "onActivityResult测试activity间传递list list2 " + list2);
                HashMap<String, String> map = (HashMap<String, String>) list2.get(0);
                String address = map.get("address")+","+map.get("name");
                districtTextView.setText(address);
            }
        }
    }


}
