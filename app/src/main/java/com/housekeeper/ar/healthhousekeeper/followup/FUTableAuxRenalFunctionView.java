package com.housekeeper.ar.healthhousekeeper.followup;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.followup.view.MyTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * 肾功能
 * Created by Lenovo on 2016/12/26.
 */
public class FUTableAuxRenalFunctionView extends ViewGroup {
    private String TAG = "FUTableAuxRenalFunctionView";
    private LinearLayout parentView;
    private TextView dateTV, timeTV;
    int year, month, day;
    int hour, min;
    View view;
    Context mContext;
    String name = "肾功能";
    private String sex;
    private int age;

    private String from;
    public FUTableAuxRenalFunctionView(Context context) {
        super(context);
    }

    public FUTableAuxRenalFunctionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FUTableAuxRenalFunctionView(Context context, LinearLayout parent,String from) {
        this(context);
        Log.i(TAG, "parent " + parent);
        parentView = parent;
        mContext = context;
        this.from = from;
        initView();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }


    private ImageButton deleteBtn;
    private MyTextView bunTV;
    private EditText bunET;
    private TextView bunLevelTV, bunHighTV, bunLowTV;

    private String bunHighString,bunLowString;
    private Spinner bunMethodSpinner;

    private MyTextView scrTV;
    private EditText scrET;
    private TextView scrLevelTV, scrHighTV, scrLowTV;

    private String scrHighString, scrLowString;

    private MyTextView buTV;
    private EditText buET;
    private TextView buLevelTV, buHighTV, buLowTV;

    private String buHighString, buLowString;

    private MyTextView uaTV;
    private EditText uaET;
    private TextView uaLevelTV, uaHighTV, uaLowTV;

    private String uaHighString, uaLowString;


    private MyTextView crTV;
    private EditText crET;
    private TextView crLevelTV, crHighTV, crLowTV,crUnitTV;

    private String crHighString, crLowString;

    private Spinner proSpinner;
    private String proString;

    private MyTextView spiTV;
    private EditText spiET;
    private TextView spiLevelTV, spiHighTV, spiLowTV;

    private String spiHighString, spiLowString;

    private MyTextView mctTV;
    private EditText mctET;
    private TextView mctLevelTV, mctHighTV, mctLowTV;

    private String mctHighString, mctLowString;

    private MyTextView cureaTV;
    private EditText cureaET;
    private TextView cureaLevelTV, cureaHighTV, cureaLowTV;

    private String cureaHighString, cureaLowString;

    private MyTextView ccrTV;
    private EditText ccrET;
    private TextView ccrLevelTV, ccrHighTV, ccrLowTV;

    private String ccrHighString, ccrLowString;

    private MyTextView buncrTV;
    private TextView buncrValueTV;
    private TextView buncrLevelTV, buncrHighTV, buncrLowTV;

    private String buncrHighString,buncrLowString;

    private MyTextView pspTV;
    private EditText pspET;
    private TextView pspLevelTV, pspHighTV, pspLowTV;

    private String pspHighString,pspLowString;

    private Spinner pspTimeSpinner;
    private Button personalAuxBtn,healthyKeeperAuxBtn,hospitalAuxBtn;
    private TextView proLevelTV;

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.activity_follow_up_table_aux_renal_function, null);
        parentView.addView(view);
//        this.addView(biochemicalView);
        personalAuxBtn = (Button)view.findViewById(R.id.personal_aux);
        healthyKeeperAuxBtn = (Button)view.findViewById(R.id.healthy_keeper_aux);
        hospitalAuxBtn = (Button)view.findViewById(R.id.hospital_aux);

        personalAuxBtn.setOnClickListener(clickListener);
        healthyKeeperAuxBtn.setOnClickListener(clickListener);
        hospitalAuxBtn.setOnClickListener(clickListener);

        deleteBtn = (ImageButton) view.findViewById(R.id.renal_delete_image);
        deleteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                parentView.removeView(view);
                if(from.equals("糖尿病")){
                    FUTableDiabetesActivity activity = (FUTableDiabetesActivity)mContext;
                    activity.removeAuxMap(name);
                }else if(from.equals("高血压")){
                    FUTAbleHypertensionActivity activity = (FUTAbleHypertensionActivity)mContext;
                    activity.removeAuxMap(name);
                }else if(from.equals("脑卒中")){
                    FUTableStrokeActivity activity = (FUTableStrokeActivity)mContext;
                    activity.removeAuxMap(name);
                }else if(from.equals("")){
                    FUTableDiagnosisActivity activity = (FUTableDiagnosisActivity)mContext;
                    activity.removeAuxMap(name);
                }
//                FUTableDiagnosisActivity activity = (FUTableDiagnosisActivity) mContext;
//                activity.removeAuxMap(name);


            }
        });


        bunTV = (MyTextView) view.findViewById(R.id.bun_tv);
        bunET = (EditText) view.findViewById(R.id.bun_et);
        bunLevelTV = (TextView) view.findViewById(R.id.bun_level_tv);
        bunHighTV = (TextView) view.findViewById(R.id.bun_high_normal_tv);
        bunLowTV = (TextView) view.findViewById(R.id.bun_low_normal_tv);
        bunHighString = bunHighTV.getText().toString();
        bunLowString = bunLowTV.getText().toString();

        bunMethodSpinner = (Spinner)view.findViewById(R.id.bun_method_spinner);

        scrTV = (MyTextView) view.findViewById(R.id.scr_tv);
        scrET = (EditText) view.findViewById(R.id.scr_et);
        scrLevelTV = (TextView) view.findViewById(R.id.scr_level_tv);
        scrHighTV = (TextView) view.findViewById(R.id.scr_high_normal_tv);
        scrLowTV = (TextView) view.findViewById(R.id.scr_low_normal_tv);

        scrHighString = scrHighTV.getText().toString();
        scrLowString = scrLowTV.getText().toString();


        buTV = (MyTextView) view.findViewById(R.id.bu_tv);
        buET = (EditText) view.findViewById(R.id.bu_et);
        buLevelTV = (TextView) view.findViewById(R.id.bu_level_tv);
        buHighTV = (TextView) view.findViewById(R.id.bu_high_normal_tv);
        buLowTV = (TextView) view.findViewById(R.id.bu_low_normal_tv);

        buHighString = buHighTV.getText().toString();
        buLowString = buLowTV.getText().toString();




        uaTV = (MyTextView) view.findViewById(R.id.ua_tv);
        uaET = (EditText) view.findViewById(R.id.ua_et);
        uaLevelTV = (TextView) view.findViewById(R.id.ua_level_tv);
        uaHighTV = (TextView) view.findViewById(R.id.ua_high_normal_tv);
        uaLowTV = (TextView) view.findViewById(R.id.ua_low_normal_tv);




        uaHighString = uaHighTV.getText().toString();
        uaLowString = uaLowTV.getText().toString();


        crTV = (MyTextView) view.findViewById(R.id.cr_tv);
        crET = (EditText) view.findViewById(R.id.cr_et);
        crUnitTV = (TextView) view.findViewById(R.id.cr_unit_tv);
        crLevelTV = (TextView) view.findViewById(R.id.cr_level_tv);
        crHighTV = (TextView) view.findViewById(R.id.cr_high_normal_tv);
        crLowTV = (TextView) view.findViewById(R.id.cr_low_normal_tv);

        crHighString = crHighTV.getText().toString();
        crLowString = crLowTV.getText().toString();

        proSpinner = (Spinner)view.findViewById(R.id.pro_spinner);
        proLevelTV = (TextView)view.findViewById(R.id.pro_level_tv);


        spiTV = (MyTextView) view.findViewById(R.id.spi_tv);
        spiET = (EditText) view.findViewById(R.id.spi_et);
        spiLevelTV = (TextView) view.findViewById(R.id.spi_level_tv);
        spiHighTV = (TextView) view.findViewById(R.id.spi_high_normal_tv);
        spiLowTV = (TextView) view.findViewById(R.id.spi_low_normal_tv);

        spiHighString = spiHighTV.getText().toString();
        spiLowString = spiLowTV.getText().toString();

        mctTV = (MyTextView) view.findViewById(R.id.mct_tv);
        mctET = (EditText) view.findViewById(R.id.mct_et);
        mctLevelTV = (TextView) view.findViewById(R.id.mct_level_tv);
        mctHighTV = (TextView) view.findViewById(R.id.mct_high_normal_tv);
        mctLowTV = (TextView) view.findViewById(R.id.mct_low_normal_tv);

        mctHighString = mctHighTV.getText().toString();
        mctLowString = mctLowTV.getText().toString();


        cureaTV = (MyTextView) view.findViewById(R.id.curea_tv);
        cureaET = (EditText) view.findViewById(R.id.curea_et);
        cureaLevelTV = (TextView) view.findViewById(R.id.curea_level_tv);
        cureaHighTV = (TextView) view.findViewById(R.id.curea_high_normal_tv);
        cureaLowTV = (TextView) view.findViewById(R.id.curea_low_normal_tv);

        cureaHighString = cureaHighTV.getText().toString();
        cureaLowString = cureaLowTV.getText().toString();

        ccrTV = (MyTextView) view.findViewById(R.id.ccr_tv);
        ccrET = (EditText) view.findViewById(R.id.ccr_et);
        ccrLevelTV = (TextView) view.findViewById(R.id.ccr_level_tv);
        ccrHighTV = (TextView) view.findViewById(R.id.ccr_high_normal_tv);
        ccrLowTV = (TextView) view.findViewById(R.id.ccr_low_normal_tv);

        ccrHighString = ccrHighTV.getText().toString();
        ccrLowString = ccrLowTV.getText().toString();

        buncrTV = (MyTextView) view.findViewById(R.id.buncr_tv);
        buncrValueTV = (TextView) view.findViewById(R.id.buncr_et);
        buncrLevelTV = (TextView) view.findViewById(R.id.buncr_level_tv);
        buncrHighTV = (TextView) view.findViewById(R.id.buncr_high_normal_tv);
        buncrLowTV = (TextView) view.findViewById(R.id.buncr_low_normal_tv);

        buncrHighString = buncrHighTV.getText().toString();
        buncrLowString = buncrLowTV.getText().toString();

        pspTV = (MyTextView) view.findViewById(R.id.psp_tv);
        pspET = (EditText) view.findViewById(R.id.psp_et);
        pspLevelTV = (TextView) view.findViewById(R.id.psp_level_tv);
        pspHighTV = (TextView) view.findViewById(R.id.psp_high_normal_tv);
        pspLowTV = (TextView) view.findViewById(R.id.psp_low_normal_tv);

        pspHighString = pspHighTV.getText().toString();
        pspLowString = pspLowTV.getText().toString();

        pspTimeSpinner = (Spinner)view.findViewById(R.id.psp_min_spinner);


        bunET.addTextChangedListener(bunTextWatcher);
        scrET.addTextChangedListener(scrTextWatcher);
        buET.addTextChangedListener(buTextWatcher);
        uaET.addTextChangedListener(uaTextWatcher);
        crET.addTextChangedListener(crTextWatcher);
        spiET.addTextChangedListener(spiTextWatcher);
        mctET.addTextChangedListener(mctTextWatcher);
        cureaET.addTextChangedListener(cureaTextWatcher);
        ccrET.addTextChangedListener(ccrTextWatcher);
        buncrValueTV.addTextChangedListener(buncrTextWatcher);
        pspET.addTextChangedListener(pspTextWatcher);

        bunLevelTV.setOnClickListener(clickListener);
        scrLevelTV.setOnClickListener(clickListener);
        buLevelTV.setOnClickListener(clickListener);
        uaLevelTV.setOnClickListener(clickListener);
        crLevelTV.setOnClickListener(clickListener);
        mctLevelTV.setOnClickListener(clickListener);
        proLevelTV.setOnClickListener(clickListener);

        spiLevelTV.setOnClickListener(clickListener);
        cureaLevelTV.setOnClickListener(clickListener);
        ccrLevelTV.setOnClickListener(clickListener);
        buncrLevelTV.setOnClickListener(clickListener);
        pspLevelTV.setOnClickListener(clickListener);


        dateTV = (TextView) view.findViewById(R.id.date_tv);
        timeTV = (TextView) view.findViewById(R.id.time_tv);

        //初始化记录时间，以后可能从数据库读取
        //初始化Calendar日历对象
        Calendar mycalendar = Calendar.getInstance(Locale.CHINA);
        Date mydate = new Date(); //获取当前日期Date对象
        mycalendar.setTime(mydate);////为Calendar对象设置时间为当前日期

        year = mycalendar.get(Calendar.YEAR); //获取Calendar对象中的年
        month = mycalendar.get(Calendar.MONTH);//获取Calendar对象中的月
        day = mycalendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
        hour = mycalendar.get(Calendar.HOUR_OF_DAY);
        min = mycalendar.get(Calendar.MINUTE);
        dateTV.setText(+year + "-" + (month + 1) + "-" + day); //显示当前的年月日

        timeTV.setText(hour + ":" + min);

        dateTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建DatePickerDialog对象
                DatePickerDialog dpd = new DatePickerDialog(mContext, DatePickerDialog.THEME_HOLO_LIGHT, Datelistener, year, month, day);
                dpd.show();//显示DatePickerDialog组件


            }
        });
        timeTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, TimePickerDialog.THEME_HOLO_LIGHT, timeListener, hour, min, true);
                timePickerDialog.show();
            }
        });
        initData();
        initSpinner();
        initChartData();
    }

    private DatePickerDialog.OnDateSetListener Datelistener = new DatePickerDialog.OnDateSetListener() {
        /**params：view：该事件关联的组件
         * params：myyear：当前选择的年
         * params：monthOfYear：当前选择的月
         * params：dayOfMonth：当前选择的日
         */
        @Override
        public void onDateSet(DatePicker view, int myyear, int monthOfYear, int dayOfMonth) {


            //修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
            year = myyear;
            month = monthOfYear;
            day = dayOfMonth;
            //更新日期
            updateDate();

        }

        //当DatePickerDialog关闭时，更新日期显示
        private void updateDate() {
            //在TextView上显示日期
            dateTV.setText(year + "-" + (month + 1) + "-" + day);
        }
    };

    private TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            min = minute;
            //更新时间
            updateTime();
        }

        private void updateTime() {
            //在TextView上显示日期
            timeTV.setText(hour + ":" + min);
        }
    };
    private OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.personal_aux:
                    personalAuxBtn.setBackgroundResource(R.drawable.tab_btn_pressed_shape);
                    personalAuxBtn.setTextColor(getResources().getColor(R.color.white));
                    healthyKeeperAuxBtn.setBackgroundResource(R.drawable.btn_green_stroke_shape);
                    healthyKeeperAuxBtn.setTextColor(getResources().getColor(R.color.black));
                    hospitalAuxBtn.setBackgroundResource(R.drawable.btn_green_stroke_shape);
                    hospitalAuxBtn.setTextColor(getResources().getColor(R.color.black));

                    break;

                case R.id.healthy_keeper_aux:

                    healthyKeeperAuxBtn.setBackgroundResource(R.drawable.tab_btn_pressed_shape);
                    healthyKeeperAuxBtn.setTextColor(getResources().getColor(R.color.white));
                    personalAuxBtn.setBackgroundResource(R.drawable.btn_green_stroke_shape);
                    personalAuxBtn.setTextColor(getResources().getColor(R.color.black));
                    hospitalAuxBtn.setBackgroundResource(R.drawable.btn_green_stroke_shape);
                    hospitalAuxBtn.setTextColor(getResources().getColor(R.color.black));

                    break;

                case R.id.hospital_aux:
                    hospitalAuxBtn.setBackgroundResource(R.drawable.tab_btn_pressed_shape);
                    hospitalAuxBtn.setTextColor(getResources().getColor(R.color.white));
                    personalAuxBtn.setBackgroundResource(R.drawable.btn_green_stroke_shape);
                    personalAuxBtn.setTextColor(getResources().getColor(R.color.black));
                    healthyKeeperAuxBtn.setBackgroundResource(R.drawable.btn_green_stroke_shape);
                    healthyKeeperAuxBtn.setTextColor(getResources().getColor(R.color.black));

                    break;
                case R.id.bun_level_tv:
                    if(!bunLevelTV.getText().toString().equals("正常")&&!bunLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(mContext,FUTableAuxRenalFunctionUnderstandingActivity.class);
                        intent.putExtra("name","血尿素氮");
                        mContext.startActivity(intent);
                    }
                    break;
                case R.id.scr_level_tv:
                    if(!scrLevelTV.getText().toString().equals("正常")&&!scrLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(mContext,FUTableAuxRenalFunctionUnderstandingActivity.class);
                        intent.putExtra("name","血肌酐");
                        mContext.startActivity(intent);
                    }
                    break;
                case R.id.bu_level_tv:
                    if(!buLevelTV.getText().toString().equals("正常")&&!buLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(mContext,FUTableAuxRenalFunctionUnderstandingActivity.class);
                        intent.putExtra("name","血尿素");
                        mContext.startActivity(intent);
                    }
                    break;
                case R.id.ua_level_tv:
                    if(!uaLevelTV.getText().toString().equals("正常")&&!uaLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(mContext,FUTableAuxRenalFunctionUnderstandingActivity.class);
                        intent.putExtra("name","血尿酸");
                        mContext.startActivity(intent);
                    }
                    break;
                case R.id.cr_level_tv:
                    if(!crLevelTV.getText().toString().equals("正常")&&!crLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(mContext,FUTableAuxRenalFunctionUnderstandingActivity.class);
                        intent.putExtra("name","尿肌酐");
                        mContext.startActivity(intent);
                    }
                    break;
                case R.id.pro_level_tv:
                    if(!proLevelTV.getText().toString().equals("正常")&&!proLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(mContext,FUTableAuxRenalFunctionUnderstandingActivity.class);
                        intent.putExtra("name","尿蛋白");
                        mContext.startActivity(intent);
                    }
                    break;
                case R.id.mct_level_tv:
                    if(!mctLevelTV.getText().toString().equals("正常")&&!mctLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(mContext,FUTableAuxRenalFunctionUnderstandingActivity.class);
                        intent.putExtra("name","β2-微球蛋白清除试验");
                        mContext.startActivity(intent);
                    }
                    break;
                case R.id.spi_level_tv:
                    if(!spiLevelTV.getText().toString().equals("正常")&&!spiLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(mContext,FUTableAuxRenalFunctionUnderstandingActivity.class);
                        intent.putExtra("name","选择性蛋白尿指数");
                        mContext.startActivity(intent);
                    }
                    break;
                case R.id.curea_level_tv:
                    if(!cureaLevelTV.getText().toString().equals("正常")&&!cureaLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(mContext,FUTableAuxRenalFunctionUnderstandingActivity.class);
                        intent.putExtra("name","尿素清除率");
                        mContext.startActivity(intent);
                    }
                    break;
                case R.id.ccr_level_tv:
                    if(!ccrLevelTV.getText().toString().equals("正常")&&!ccrLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(mContext,FUTableAuxRenalFunctionUnderstandingActivity.class);
                        intent.putExtra("name","血内生肌酐清除率");
                        mContext.startActivity(intent);
                    }
                    break;
                case R.id.buncr_level_tv:
                    if(!buncrLevelTV.getText().toString().equals("正常")&&!buncrLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(mContext,FUTableAuxRenalFunctionUnderstandingActivity.class);
                        intent.putExtra("name","尿素氮肌酐比值");
                        mContext.startActivity(intent);
                    }
                    break;
                case R.id.psp_level_tv:
                    if(!pspLevelTV.getText().toString().equals("正常")&&!pspLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(mContext,FUTableAuxRenalFunctionUnderstandingActivity.class);
                        intent.putExtra("name","酚红排泄试验");
                        mContext.startActivity(intent);
                    }
                    break;

            }
        }
    };
    private void initData(){

        sex = "女";
        age = 30;


        bunMethodSpinner.setSelection(0);
        bunET.setText("1");
        scrET.setText("4");

        buET.setText("2");
        uaET.setText("16");

        crET.setText("20");
        spiET.setText("3.0");

        mctET.setText("20");
        cureaET.setText("8");

        ccrET.setText("1");
        buncrValueTV.setText("15");

        pspET.setText("3");


        if(sex.equals("男")){


            scrHighTV.setText("132.6");
            scrLowTV.setText("79.6");

            uaHighTV.setText("417");
            uaLowTV.setText("149");
            if(age > 60){
                uaHighTV.setText("476");
                uaLowTV.setText("250");
            }

        }else{

            scrHighTV.setText("106.1");
            scrLowTV.setText("70.7");

            uaHighTV.setText("357");
            uaLowTV.setText("89");

            if(age > 60){
                uaHighTV.setText("434");
                uaLowTV.setText("190");
            }

        }


        if(age <= 3){
            //婴儿
            crHighTV.setText("176");
            crLowTV.setText("88");
            crUnitTV.setText("μmmol·kg-1/d");
        }else if(age > 3 && age <= 12){
            //儿童
            crHighTV.setText("352");
            crLowTV.setText("44");
            crUnitTV.setText("μmol·kg-1/d");
        }else{
            //成人
            crHighTV.setText("8");
            crLowTV.setText("7");
            crUnitTV.setText("mmol/d");
        }


        scrHighString = scrHighTV.getText().toString();
        scrLowString = scrLowTV.getText().toString();

        uaHighString = uaHighTV.getText().toString();
        uaLowString = uaLowTV.getText().toString();

        crHighString = crHighTV.getText().toString();
        crLowString = crLowTV.getText().toString();




        ccrHighString = ccrHighTV.getText().toString();
        ccrLowString =  ccrLowTV.getText().toString();

        calculateBuncrValue();


    }
    private void initSpinner(){

        final List<String> bunList = getBunMethodData();
        ArrayAdapter<String> bunAdapter = new ArrayAdapter<String>
                (mContext, R.layout.spinner_item,getBunMethodData()){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(bunList.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };

        //设置样式
        bunAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        bunMethodSpinner.setAdapter(bunAdapter);

        bunMethodSpinner.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.i(TAG, "bun方法 touch ");
                closeSoftKeyboard();
                return false;
            }
        });

        bunMethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                String bunMethodString = parent.getItemAtPosition(position).toString();
                if(bunMethodString.equals("二乙酰-肟显色法")){
                    bunHighTV.setText("6.8");
                    bunLowTV.setText("1.8");

                    bunHighString = bunHighTV.getText().toString();
                    bunLowString = bunLowTV.getText().toString();

                }else if(bunMethodString.equals("尿素酶-钠氏显色法")){
                    bunHighTV.setText("6.1");
                    bunLowTV.setText("3.2");

                    bunHighString = bunHighTV.getText().toString();
                    bunLowString = bunLowTV.getText().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        final List<String> pspTimeList = getPSPTimeData();
        ArrayAdapter<String> pspTimeAdapter = new ArrayAdapter<String>
                (mContext, R.layout.spinner_item,getPSPTimeData()){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(pspTimeList.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };

        //设置样式
        pspTimeAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        pspTimeSpinner.setAdapter(pspTimeAdapter);

        pspTimeSpinner.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.i(TAG, "psp时间 touch ");
                closeSoftKeyboard();
                return false;
            }
        });

        pspTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                String string = parent.getItemAtPosition(position).toString();
                if(string.equals("15min")){
                    pspHighTV.setText("0.51");
                    pspLowTV.setText("0.25");

                    pspHighString = pspHighTV.getText().toString();
                    pspLowString = pspLowTV.getText().toString();

                }else if(string.equals("30min")){
                    pspHighTV.setText("0.24");
                    pspLowTV.setText("0.13");

                    pspHighString = pspHighTV.getText().toString();
                    pspLowString = pspLowTV.getText().toString();
                }else if(string.equals("60min")){
                    pspHighTV.setText("0.17");
                    pspLowTV.setText("0.09");

                    pspHighString = pspHighTV.getText().toString();
                    pspLowString = pspLowTV.getText().toString();
                }else if(string.equals("120min")){
                    pspHighTV.setText("0.10");
                    pspLowTV.setText("0.03");

                    pspHighString = pspHighTV.getText().toString();
                    pspLowString = pspLowTV.getText().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        final List<String> proList = getFeminineData();
        ArrayAdapter<String> proAdapter = new ArrayAdapter<String>
                (mContext, R.layout.fu_spinner_item,getFeminineData()){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.fu_spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(proList.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };

        //设置样式
        proAdapter.setDropDownViewResource(R.layout.fu_spinner_item_layout);
        proSpinner.setAdapter(proAdapter);

        proSpinner.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.i(TAG, "尿蛋白 touch ");
                closeSoftKeyboard();
                return false;
            }
        });

        proSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                proString = parent.getItemAtPosition(position).toString();
                if(proString.equals("-")){
                    proLevelTV.setText("正常");
                }else{
                    proLevelTV.setText("不正常");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


    }
    private void closeSoftKeyboard(){
        Activity activity = (Activity)mContext;
        /**隐藏软键盘**/
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    //获取阴性、阳性数据
    private List<String> getFeminineData(){
        List<String> dataList = new ArrayList<String>();

//        dataList.add("阴性（-）");
//        dataList.add("阳性（+）");
        dataList.add("-");
        dataList.add("+");
        dataList.add("+-");
        dataList.add("++");
        dataList.add("+++");
        dataList.add("++++");

        return dataList;
    }

    private List<String> getBunMethodData(){
        List<String> dataList = new ArrayList<String>();

        dataList.add("二乙酰-肟显色法");
        dataList.add("尿素酶-钠氏显色法");


        return dataList;
    }
    private List<String> getPSPTimeData(){
        List<String> dataList = new ArrayList<String>();

        dataList.add("15min");
        dataList.add("30min");
        dataList.add("60min");
        dataList.add("120min");


        return dataList;
    }
    private void initChartData(){


        double[] radarViewData = {30,60,60,60,80,50,10,20}; //各维度分值
        String[] radarViewTitles = {"测试1","测试2","测试3","测试4","测试5","测试6"};//各维度名称

        int yMax = 100; //y轴最大值
        int ySteps = 10;//Y轴间隔值
        int yDetailSteps = 1;//Y轴文字显示间隔数，Y轴每隔ySteps*yDetailSteps显示一次文字，如10*1=10，在0，10，20...处显示文字
        //X轴数值
        LinkedList<String> xLabels = new LinkedList<>();

        //线段文字集合
        List<String> dataTextList = new ArrayList<>();
        //线段颜色集合，若为null，则默认绿色；若成员HashMap<String,Integer>是-1，-1，-1，则设置成默认值绿色58,171,119
        List<HashMap<String,Integer>>dataColorList = new ArrayList<>();
        //线段值集合
        List<LinkedList<Double>>dataList = new ArrayList<>();

        for(int i=0;i<8;i++){
            xLabels.add(i+"");
        }

        dataTextList.add("测试");

        HashMap<String,Integer> map = new HashMap<>();
        map.put("r", 245);
        map.put("g", 30);
        map.put("b", 150);
        dataColorList.add(map);

        LinkedList<Double> dataSeries1= new LinkedList<Double>();
        dataSeries1.add(40d);
        dataSeries1.add(48d);
        dataSeries1.add(50d);
        dataSeries1.add(56d);

        dataList.add(dataSeries1);

        //设置六边形数据
        bunTV.setRadarViewData(radarViewData);
        bunTV.setRadarViewMaxValue(90);
        bunTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        bunTV.setLineData(bunTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        scrTV.setRadarViewData(radarViewData);
        scrTV.setRadarViewMaxValue(90);
        scrTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        scrTV.setLineData(scrTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        buTV.setRadarViewData(radarViewData);
        buTV.setRadarViewMaxValue(90);
        buTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        buTV.setLineData(buTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        uaTV.setRadarViewData(radarViewData);
        uaTV.setRadarViewMaxValue(90);
        uaTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        uaTV.setLineData(uaTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        crTV.setRadarViewData(radarViewData);
        crTV.setRadarViewMaxValue(90);
        crTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        crTV.setLineData(crTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        spiTV.setRadarViewData(radarViewData);
        spiTV.setRadarViewMaxValue(90);
        spiTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        spiTV.setLineData(spiTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        mctTV.setRadarViewData(radarViewData);
        mctTV.setRadarViewMaxValue(90);
        mctTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        mctTV.setLineData(mctTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        cureaTV.setRadarViewData(radarViewData);
        cureaTV.setRadarViewMaxValue(90);
        cureaTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        cureaTV.setLineData(cureaTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        ccrTV.setRadarViewData(radarViewData);
        ccrTV.setRadarViewMaxValue(90);
        ccrTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        ccrTV.setLineData(ccrTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        buncrTV.setRadarViewData(radarViewData);
        buncrTV.setRadarViewMaxValue(90);
        buncrTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        buncrTV.setLineData(buncrTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        pspTV.setRadarViewData(radarViewData);
        pspTV.setRadarViewMaxValue(90);
        pspTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        pspTV.setLineData(pspTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

    }
    private TextWatcher bunTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(bunLowString)) {
                    bunLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(bunHighString)) {
                    bunLevelTV.setText("↑");
                } else {
                    bunLevelTV.setText("正常");
                }
                calculateBuncrValue();
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private void calculateBuncrValue(){
        if(bunET != null && scrET != null && !bunET.getText().toString().equals("") && !scrET.getText().toString().equals("")){
            float rate = Float.valueOf(bunET.getText().toString()) / Float.valueOf(scrET.getText().toString());
            buncrValueTV.setText("" + rate);
        }else{
            buncrValueTV.setText("0");
        }
    }
    private TextWatcher scrTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(scrLowString)) {
                    scrLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(scrHighString)) {
                    scrLevelTV.setText("↑");
                } else {
                    scrLevelTV.setText("正常");
                }
                calculateBuncrValue();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher buTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(buLowString)) {
                    buLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(buHighString)) {
                    buLevelTV.setText("↑");
                } else {
                    buLevelTV.setText("正常");
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher uaTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(uaLowString)) {
                    uaLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(uaHighString)) {
                    uaLevelTV.setText("↑");
                } else {
                    uaLevelTV.setText("正常");
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher crTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(crLowString)) {
                    crLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(crHighString)) {
                    crLevelTV.setText("↑");
                } else {
                    crLevelTV.setText("正常");
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher spiTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
//                if (Float.valueOf(s.toString()) < Float.valueOf(spiLowString)) {
//                    spiLevelTV.setText("↓");
//                } else if (Float.valueOf(s.toString()) > Float.valueOf(spiHighString)) {
//                    spiLevelTV.setText("↑");
//                } else {
//                    spiLevelTV.setText("正常");
//                }

                if(Float.valueOf(s.toString()) < 0.1){
                    spiLevelTV.setText("选择性好");
                }else if (Float.valueOf(s.toString()) > 0.2) {
                    spiLevelTV.setText("选择性差");
                } else {
                    spiLevelTV.setText("选择性一般");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher mctTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(mctLowString)) {
                    mctLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(mctHighString)) {
                    mctLevelTV.setText("↑");
                } else {
                    mctLevelTV.setText("正常");
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher cureaTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(cureaLowString)) {
                    cureaLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(cureaHighString)) {
                    cureaLevelTV.setText("↑");
                } else {
                    cureaLevelTV.setText("正常");
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher ccrTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(ccrLowString)) {
                    ccrLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(ccrHighString)) {
                    ccrLevelTV.setText("↑");
                } else {
                    ccrLevelTV.setText("正常");
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher buncrTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(buncrLowString)) {
                    buncrLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(buncrHighString)) {
                    buncrLevelTV.setText("↑");
                } else {
                    buncrLevelTV.setText("正常");
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher pspTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(pspLowString)) {
                    pspLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(pspHighString)) {
                    pspLevelTV.setText("↑");
                } else {
                    pspLevelTV.setText("正常");
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
