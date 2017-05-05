package com.housekeeper.ar.healthhousekeeper.followup;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
 * 心肌酶学检查结果
 * Created by Lenovo on 2016/12/12.
 */
public class FUTableCHDAuxMyocardialEnzymeView extends ViewGroup {
    private String TAG = "FUTableMyocardialEnzymeView";
    private LinearLayout parentView;
    View view;
    Context mContext;
    String from;
    String name = "心肌酶学检查结果";
    public FUTableCHDAuxMyocardialEnzymeView(Context context) {
        super(context);
        mContext = context;
    }

    public FUTableCHDAuxMyocardialEnzymeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }
    public FUTableCHDAuxMyocardialEnzymeView(Context context, LinearLayout parent,String from){
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

    MyTextView meTV;
    EditText meET;
    TextView meLevelTV;
    ImageButton deleteBtn;

    //测试数据，最大值、最小值
    int meHigh = 10,meLow = 4;

    private TextView dateTV,timeTV;
    int year,month,day;
    int hour,min;
    private void initView(){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.activity_follow_up_table_aux_myocardial_enzyme, null);
        parentView.addView(view);


        meTV = (MyTextView)view.findViewById(R.id.me_tv);


        meET = (EditText)view.findViewById(R.id.me_et);
        meET.addTextChangedListener(textWatcher);

       meLevelTV = (TextView)view.findViewById(R.id.me_level_tv);

        deleteBtn = (ImageButton)view.findViewById(R.id.me_delete_image);
        deleteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                parentView.removeView(view);
                if(from.equals("冠心病")){
                    FUTAbleCHDActivity activity = (FUTAbleCHDActivity)mContext;
                    activity.removeAuxMap(name);
                }

            }
        });

        dateTV = (TextView)view.findViewById(R.id.date_tv);
        timeTV = (TextView)view.findViewById(R.id.time_tv);
        //初始化记录时间，以后可能从数据库读取
        //初始化Calendar日历对象
        Calendar mycalendar=Calendar.getInstance(Locale.CHINA);
        Date mydate=new Date(); //获取当前日期Date对象
        mycalendar.setTime(mydate);////为Calendar对象设置时间为当前日期

        year=mycalendar.get(Calendar.YEAR); //获取Calendar对象中的年
        month=mycalendar.get(Calendar.MONTH);//获取Calendar对象中的月
        day=mycalendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
        hour = mycalendar.get(Calendar.HOUR_OF_DAY);
        min = mycalendar.get(Calendar.MINUTE);
        dateTV.setText(+year+"-"+(month+1)+"-"+day); //显示当前的年月日

        timeTV.setText(hour+":"+min);

        dateTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建DatePickerDialog对象
                DatePickerDialog dpd = new DatePickerDialog(mContext, DatePickerDialog.THEME_HOLO_LIGHT, BloodDatelistener, year, month, day);
                dpd.show();//显示DatePickerDialog组件


            }
        });
        timeTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext,TimePickerDialog.THEME_HOLO_LIGHT,BloodTimeListener,hour,min,true);
                timePickerDialog.show();
            }
        });


        initData();
        initChartData();

    }

    public void setData(String meString){
        meET.setText(meString);
    }
    private  void initData(){
        meET.setText("12");

        meLevelTV.setText("↑");

    }
    private void initChartData(){
        double[] radarViewData = {30,60,60,60,80,50,10,20}; //各维度分值
        String[] radarViewTitles = {"测试1","测试2","测试3","测试4","测试5","测试6"};//各维度名称
        //设置饮水量的六边形数据
        meTV.setRadarViewData(radarViewData);
        meTV.setRadarViewMaxValue(90);
        meTV.setRadarViewTitles(radarViewTitles);
        //设置饮水量的折线数据
        String title=meTV.getText().toString();
        int yMax = 100; //y轴最大值
        int ySteps = 10;//Y轴间隔值
        int yDetailSteps = 1;//Y轴文字显示间隔数，Y轴每隔ySteps*yDetailSteps显示一次文字，如10*1=10，在0，10，20...处显示文字
        LinkedList<String> xLabels = new LinkedList<>(); //X轴数值

        //线段文字集合
        List<String> dataTextList = new ArrayList<>();
        //线段颜色集合，若成员HashMap<String,Integer>是-1，-1，-1，则设置成默认值绿色58,171,119
        List<HashMap<String,Integer>>dataColorList = new ArrayList<>();
        //线段值集合
        List<LinkedList<Double>>dataList = new ArrayList<>();

        for(int i=0;i<8;i++){
            xLabels.add(i+"");
        }


        //如果有多条曲线，可以设置dataTextList、dataList、dataColorList
        dataTextList.add("正常");

        HashMap<String,Integer> map = new HashMap<>();
        map.put("r",245);
        map.put("g",30);
        map.put("b",150);
        dataColorList.add(map);

        LinkedList<Double> dataSeries1= new LinkedList<Double>();
        dataSeries1.add(40d);
        dataSeries1.add(48d);
        dataSeries1.add(50d);
        dataSeries1.add(56d);
        dataList.add(dataSeries1);



        meTV.setLineData(title, yMax, ySteps, yDetailSteps, xLabels, dataTextList, null, dataList);
    }


    private DatePickerDialog.OnDateSetListener BloodDatelistener=new DatePickerDialog.OnDateSetListener()
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

    private TimePickerDialog.OnTimeSetListener BloodTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour=hourOfDay;
            min=minute;
            //更新时间
            updateTime();
        }
        private void updateTime()
        {
            //在TextView上显示日期
            timeTV.setText(hour+":"+min);
        }
    };

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Activity activity = (Activity) mContext;
            if (!s.toString().equals("")) {
                if (activity.getCurrentFocus().getId() == meET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(meLow)) {
                        meLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(meHigh)) {
                        meLevelTV.setText("↑");
                    } else {
                        meLevelTV.setText("正常");
                    }
                }
            }
            }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
