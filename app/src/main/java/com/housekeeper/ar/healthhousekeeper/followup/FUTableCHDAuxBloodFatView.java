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
 * 血脂全项
 * Created by Lenovo on 2016/12/12.
 */
public class FUTableCHDAuxBloodFatView extends ViewGroup {
    private String TAG = "FUTableBloodFatView";
    private LinearLayout parentView;
    View view;
    Context mContext;
    String from;
    String name = "血脂全项";
    public FUTableCHDAuxBloodFatView(Context context) {
        super(context);
        mContext = context;
    }

    public FUTableCHDAuxBloodFatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }
    public FUTableCHDAuxBloodFatView(Context context, LinearLayout parent,String from){
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

    MyTextView hdlTV,ldlTV,tgTV,choTV;
    EditText hdlET,ldlET,tgET,choET;
    TextView hdlLevelTV,ldlLevelTV,tgLevelTV,choLevelTV;
    ImageButton deleteBloodFatBtn;

    //测试数据
    private int hdlHigh = 10,hdlLow = 5;
    private int ldlHigh = 10,ldlLow = 5;
    private int tgHigh = 10,tgLow = 5;
    private int choHigh = 10,choLow = 5;

    private TextView bloodDateTV,bloodTimeTV;
    int bloodYear,bloodMonth,bloodDay;
    int bloodHour,bloodMin;
    private void initView(){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.activity_follow_up_table_aux_blood_fat, null);
        parentView.addView(view);


        hdlTV = (MyTextView)view.findViewById(R.id.hdl_tv);
        ldlTV = (MyTextView)view.findViewById(R.id.ldl_tv);
        tgTV = (MyTextView)view.findViewById(R.id.tg_tv);
        choTV = (MyTextView)view.findViewById(R.id.cholesterol_tv);

        hdlET = (EditText)view.findViewById(R.id.hdl_et);
        ldlET = (EditText)view.findViewById(R.id.ldl_et);
        tgET = (EditText)view.findViewById(R.id.tg_et);
        choET = (EditText)view.findViewById(R.id.cholesterol_et);

        hdlLevelTV = (TextView)view.findViewById(R.id.hdl_level_tv);
        ldlLevelTV = (TextView)view.findViewById(R.id.ldl_level_tv);
        tgLevelTV = (TextView)view.findViewById(R.id.tg_level_tv);
        choLevelTV = (TextView)view.findViewById(R.id.cholesterol_level_tv);

        hdlET.addTextChangedListener(textWatcher);
        ldlET.addTextChangedListener(textWatcher);
        tgET.addTextChangedListener(textWatcher);
        choET.addTextChangedListener(textWatcher);

        deleteBloodFatBtn = (ImageButton)view.findViewById(R.id.blood_fat_delete_image);
        deleteBloodFatBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                parentView.removeView(view);
                if(from.equals("冠心病")){
                    FUTAbleCHDActivity activity = (FUTAbleCHDActivity)mContext;
                    activity.removeAuxMap(name);
                }

            }
        });

        bloodDateTV = (TextView)view.findViewById(R.id.date_tv);
        bloodTimeTV = (TextView)view.findViewById(R.id.time_tv);
        //初始化记录时间，以后可能从数据库读取
        //初始化Calendar日历对象
        Calendar mycalendar=Calendar.getInstance(Locale.CHINA);
        Date mydate=new Date(); //获取当前日期Date对象
        mycalendar.setTime(mydate);////为Calendar对象设置时间为当前日期

        bloodYear=mycalendar.get(Calendar.YEAR); //获取Calendar对象中的年
        bloodMonth=mycalendar.get(Calendar.MONTH);//获取Calendar对象中的月
        bloodDay=mycalendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
        bloodHour = mycalendar.get(Calendar.HOUR_OF_DAY);
        bloodMin = mycalendar.get(Calendar.MINUTE);
        bloodDateTV.setText(+bloodYear+"-"+(bloodMonth+1)+"-"+bloodDay); //显示当前的年月日

        bloodTimeTV.setText(bloodHour+":"+bloodMin);

        bloodDateTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建DatePickerDialog对象
                DatePickerDialog dpd = new DatePickerDialog(mContext, DatePickerDialog.THEME_HOLO_LIGHT, BloodDatelistener, bloodYear, bloodMonth, bloodDay);
                dpd.show();//显示DatePickerDialog组件


            }
        });
        bloodTimeTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext,TimePickerDialog.THEME_HOLO_LIGHT,BloodTimeListener,bloodHour,bloodMin,true);
                timePickerDialog.show();
            }
        });


        initBloodFatData();
        initBloodFatChartData();

    }

    private  void initBloodFatData(){
        hdlET.setText("12");
        ldlET.setText("15");
        tgET.setText("12");
        choET.setText("20");

        hdlLevelTV.setText("↑");

    }
    private void initBloodFatChartData(){
        double[] radarViewData = {30,60,60,60,80,50,10,20}; //各维度分值
        String[] radarViewTitles = {"测试1","测试2","测试3","测试4","测试5","测试6"};//各维度名称
        //设置饮水量的六边形数据
        hdlTV.setRadarViewData(radarViewData);
        hdlTV.setRadarViewMaxValue(90);
        hdlTV.setRadarViewTitles(radarViewTitles);
        //设置饮水量的折线数据
        String title=hdlTV.getText().toString();
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


        dataTextList.add(hdlTV.getText().toString());
        HashMap<String,Integer> map = new HashMap<>();
        map.put("r",245);
        map.put("g",30);
        map.put("b", 150);
        dataColorList.add(map);

        LinkedList<Double> dataSeries1= new LinkedList<Double>();
        dataSeries1.add(40d);
        dataSeries1.add(48d);
        dataSeries1.add(50d);
        dataSeries1.add(56d);
        dataList.add(dataSeries1);



        hdlTV.setLineData(title, yMax, ySteps, yDetailSteps, xLabels, dataTextList, null, dataList);


        //设置饮水量的六边形数据
        ldlTV.setRadarViewData(radarViewData);
        ldlTV.setRadarViewMaxValue(90);
        ldlTV.setRadarViewTitles(radarViewTitles);
        ldlTV.setLineData(ldlTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, null, dataList);

        //设置饮水量的六边形数据
        tgTV.setRadarViewData(radarViewData);
        tgTV.setRadarViewMaxValue(90);
        tgTV.setRadarViewTitles(radarViewTitles);
        tgTV.setLineData(tgTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, null, dataList);

        //设置饮水量的六边形数据
        choTV.setRadarViewData(radarViewData);
        choTV.setRadarViewMaxValue(90);
        choTV.setRadarViewTitles(radarViewTitles);
        choTV.setLineData(choTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, null, dataList);
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
            bloodYear=myyear;
            bloodMonth=monthOfYear;
            bloodDay=dayOfMonth;
            //更新日期
            updateDate();

        }
        //当DatePickerDialog关闭时，更新日期显示
        private void updateDate()
        {
            //在TextView上显示日期
            bloodDateTV.setText(bloodYear+"-"+(bloodMonth+1)+"-"+bloodDay);
        }
    };

    private TimePickerDialog.OnTimeSetListener BloodTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            bloodHour=hourOfDay;
            bloodMin=minute;
            //更新时间
            updateTime();
        }
        private void updateTime()
        {
            //在TextView上显示日期
            bloodTimeTV.setText(bloodHour+":"+bloodMin);
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
                if (activity.getCurrentFocus().getId() == hdlET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(hdlLow)) {
                        hdlLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(hdlHigh)) {
                        hdlLevelTV.setText("↑");
                    } else {
                        hdlLevelTV.setText("正常");
                    }
                } else  if (activity.getCurrentFocus().getId() == ldlET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(ldlLow)) {
                        ldlLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(ldlHigh)) {
                        ldlLevelTV.setText("↑");
                    } else {
                        ldlLevelTV.setText("正常");
                    }
                }else if (activity.getCurrentFocus().getId() == tgET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(tgLow)) {
                        tgLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(tgHigh)) {
                        tgLevelTV.setText("↑");
                    } else {
                        tgLevelTV.setText("正常");
                    }
                } else if (activity.getCurrentFocus().getId() == choET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(choLow)) {
                        choLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(choHigh)) {
                        choLevelTV.setText("↑");
                    } else {
                        choLevelTV.setText("正常");
                    }
                }
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
