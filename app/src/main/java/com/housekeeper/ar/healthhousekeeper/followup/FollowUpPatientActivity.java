package com.housekeeper.ar.healthhousekeeper.followup;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.HttpUtil;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyDialog;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by lenovo on 2016/3/5.
 */
//会员卡
public class FollowUpPatientActivity extends BaseActivity {


    String TAG = "FollowUpPatientActivity";
    String add_des_url = "http://123.56.155.17:8080/xys/healthButler/event/add";


    String http, httpUrl;

    private ToastCommom toastCommom;
    private TextView nameTextView,sexTextView,ageTextView;
    String desString;
    private String name="",sex="",age="",id="";
    private ListView managerListView;
    private Button managerRemarkBtn,doctorRemarkBtn;
    private TextView dayTextView,overPlusTextView,statusTextView;

//    private FollowUpPatientAdapter followUpPatientAdapter;
    private FollowUpPatientManagerDesAdapter managerDesAdapter;
    private List<HashMap<String,String>> managerDesData = new ArrayList<>();
    private Button backBtn;
    private Button tableBtn;
    private Spinner userTypeSpnner;
    private Spinner managerCommTypeSpinner;
    private String managerCommTypeString;
    int managerCommTypeInt;
    static String contractStatus, contractTimee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_patient_item);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(FollowUpPatientActivity.this);

        final Intent intent = getIntent();
        if(intent != null){

            name = intent.getStringExtra("name");
            id = intent.getStringExtra("id");
            sex= intent.getStringExtra("sex");
            age = intent.getStringExtra("age");
            contractStatus = intent.getStringExtra("contractStatus");
            contractTimee = intent.getStringExtra("contractTimee");
        }

        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        nameTextView = (TextView)findViewById(R.id.patient_name_tv);
        ageTextView = (TextView)findViewById(R.id.patient_age_tv);
        sexTextView = (TextView)findViewById(R.id.patient_sex_tv);

        managerListView = (ListView)findViewById(R.id.manager_listview);
        managerRemarkBtn = (Button)findViewById(R.id.remark_btn);
        doctorRemarkBtn = (Button)findViewById(R.id.not_sign_remark_btn);
        statusTextView = (TextView)findViewById(R.id.sign_tv);
        dayTextView = (TextView)findViewById(R.id.day_tv);
        overPlusTextView = (TextView)findViewById(R.id.remainder_days_tv);
        userTypeSpnner = (Spinner)findViewById(R.id.user_type_spinner);
        managerCommTypeSpinner = (Spinner)findViewById(R.id.manager_communication_spinner);

        tableBtn = (Button)findViewById(R.id.follow_up_table_btn);

        tableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FollowUpPatientActivity.this, FollowUpTableActivity.class);
                intent1.putExtra("id", id);
                startActivity(intent1);
            }
        });

        Log.i(TAG, "name " + name);
        Log.i(TAG, "age " + age);
        Log.i(TAG, "sex " + sex);

        nameTextView.setText(name);
        ageTextView.setText(age);
        sexTextView.setText(sex);

        overPlusTextView.setText(String.valueOf(differentDays()));

        statusTextView.setText(contractStatus);
        managerDesAdapter = new FollowUpPatientManagerDesAdapter(FollowUpPatientActivity.this,managerDesData);
        managerListView.setAdapter(managerDesAdapter);



        managerRemarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDesDialog(0);
            }
        });

        doctorRemarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showRemark(1);
                Toast.makeText(FollowUpPatientActivity.this,"后期完成",Toast.LENGTH_SHORT).show();
            }
        });
        initSpinner();
/* */
    }
    public static  int  differentDays(){
        java.util.Date nowdate = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //年月日 时分秒
        String d2 = sdf.format(nowdate);
        String d1 = contractTimee;
        return differentDaysByMillisecond(d1,d2);
    }
    public static int differentDaysByMillisecond(String date1,String date2)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int days = 0;
        try {
            days = (int) ((sdf.parse(date1).getTime() - sdf.parse(date2).getTime()) / (1000*3600*24));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }
    @Override
    protected void onResume() {
        super.onResume();
        initManagerDesData();
        managerDesAdapter.notifyDataSetChanged();
//        managerCommunicationCounts = 0;
//        doctorCommunicationCounts = 0;
    }
    private void initSpinner(){
        final List<String> list = getUserTYpeData();
        // 声明一个ArrayAdapter用于存放简单数据
        ArrayAdapter<String> userTypeAdapter = new ArrayAdapter<String>
                (FollowUpPatientActivity.this, R.layout.spinner_item,list){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(list.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };
        //设置样式
        userTypeAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        userTypeSpnner.setAdapter(userTypeAdapter);
        userTypeSpnner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.i(TAG, "year touch ");
                closeSoftKeyboard();
                return false;
            }
        });


        final List<String> managerCommTypeList = getManagerCommTypeData();
        // 声明一个ArrayAdapter用于存放简单数据
        ArrayAdapter<String> managerCommTypeAdapter = new ArrayAdapter<String>
                (FollowUpPatientActivity.this, R.layout.spinner_item,managerCommTypeList){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(managerCommTypeList.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };
        //设置样式
        managerCommTypeAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        managerCommTypeSpinner.setAdapter(managerCommTypeAdapter);
        managerCommTypeSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                closeSoftKeyboard();
                return false;
            }
        });
        managerCommTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                managerCommTypeString = parent.getItemAtPosition(position).toString();
                managerCommTypeInt = position;
                Log.i(TAG, "managerCommTypeInt:"+managerCommTypeInt);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

    }
    private void closeSoftKeyboard(){

        /**隐藏软键盘**/
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    //下拉菜单数据源
    private List<String> getUserTYpeData(){

        List<String> dataList = new ArrayList<String>();
        for(int i=1; i<=5; i++){
            dataList.add(String.valueOf(i));
        }
        return dataList;
    }
    //下拉菜单数据源
    private List<String> getManagerCommTypeData(){

        List<String> dataList = new ArrayList<String>();


        dataList.add("记录");
        dataList.add("续费");
//        dataList.add("签约");
//        dataList.add("预约");
        return dataList;
    }

    //初始化健康管家描述列表
    private void initManagerDesData(){
        managerDesData.clear();

//        HashMap<String,String> map = new HashMap<>();
//        map.put("name","测试1");
//        map.put("type", "续费");
//        map.put("des","描述测试");
//        map.put("date","2017-01-12");
//        managerDesData.add(map);
//
//        HashMap<String,String> map2 = new HashMap<>();
//        map2.put("name", "测试2");
//        map2.put("type", "记录");
//        map2.put("des","描述测试");
//        map2.put("date","2017-01-12");
//        managerDesData.add(map2);

    }
    private TextView dateTV;
    int year,month,day;
    private void showDesDialog(final int flag){
        LayoutInflater inflater =getLayoutInflater();
        final View functionListView = inflater.inflate(R.layout.activity_follow_up_patient_des_dialog, null);

        final MyDialog dialog = new MyDialog(FollowUpPatientActivity.this, functionListView,R.style.load_dialog);
        int width = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
//		int height = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();



        Button okBtn = (Button)functionListView.findViewById(R.id.ok_btn);
        Button cancelBtn = (Button)functionListView.findViewById(R.id.cancel_btn);
        TextView typeTV = (TextView)functionListView.findViewById(R.id.type_tv);
       final  EditText desEditText = (EditText)functionListView.findViewById(R.id.des_edit);
        final TextView nameTV = (TextView)functionListView.findViewById(R.id.name_tv);
         dateTV = (TextView)functionListView.findViewById(R.id.date_tv);

        typeTV.setText(managerCommTypeString);

        nameTV.setText(name);

        //初始化记录时间，以后可能从数据库读取
        //初始化Calendar日历对象
        Calendar mycalendar=Calendar.getInstance(Locale.CHINA);
        Date mydate=new Date(); //获取当前日期Date对象
        mycalendar.setTime(mydate);////为Calendar对象设置时间为当前日期

        year=mycalendar.get(Calendar.YEAR); //获取Calendar对象中的年
        month=mycalendar.get(Calendar.MONTH);//获取Calendar对象中的月
        day=mycalendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天

        dateTV.setText(+year+"-"+(month+1)+"-"+day); //显示当前的年月日



        dateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建DatePickerDialog对象
                DatePickerDialog dpd = new DatePickerDialog(FollowUpPatientActivity.this, DatePickerDialog.THEME_HOLO_LIGHT, Datelistener, year, month, day);
                dpd.show();//显示DatePickerDialog组件


            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desString = desEditText.getText().toString();
                if(desString ==null ||desString.equals("")){
                    toastCommom.ToastShow(FollowUpPatientActivity.this,(ViewGroup)findViewById(R.id.toast_layout_root),"请输入描述");
                    return;
                }
                JSONObject josend = new JSONObject();
                try {
                    josend.put("type",managerCommTypeInt);
                    josend.put("desc",desString);
                    HttpPost post = HttpUtil.getPost(add_des_url, josend);
                    JSONObject joRev = HttpUtil.getString(post, 3);
                    Log.i(TAG, "add_des_result:"+joRev.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //健康管家
                HashMap<String,String> map = new HashMap<>();
                map.put("name",nameTV.getText().toString());
                map.put("type",managerCommTypeString);
                map.put("des", desString);
                map.put("date", dateTV.getText().toString());
                if (flag == 0) {
                    managerDesData.add(map);

                    managerDesAdapter.notifyDataSetChanged();
                }

                dialog.dismiss();
            }



        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }
    private DatePickerDialog.OnDateSetListener Datelistener=new DatePickerDialog.OnDateSetListener()
    {
        /**params：view：该事件关联的组件
         * params：myyear：当前选择的年
         * params：monthOfYear：当前选择的月
         * params：dayOfMonth：当前选择的日
         */
        @Override
        public void onDateSet(DatePicker view, int myyear, int monthOfYear,int dayOfMonth) {


            //修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
            year=myyear;
            month=monthOfYear;
            day=dayOfMonth;
            //更新日期
            updateDate();

        }
        //当DatePickerDialog关闭时，更新日期显示
        private void updateDate()
        {
            //在TextView上显示日期
            dateTV.setText(year+"-"+(month+1)+"-"+day);
        }
    };


}
