package com.housekeeper.ar.healthhousekeeper.followup;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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
 * 血糖
 * Created by Lenovo on 2016/11/17.
 */
public class FUTableDiabetesBloodSugarView extends ViewGroup {
    private String TAG = "FUTableDiabetesBloodSugarView";
    private LinearLayout parentView;
    private TextView dateTV,timeTV;
    int year,month,day;
    int hour,min;
    View view;
    Context mContext;
    String name="血糖";
    String from;

    public FUTableDiabetesBloodSugarView(Context context) {
        super(context);

    }

    public FUTableDiabetesBloodSugarView(Context context,LinearLayout parent,String from){
        this(context);
        Log.i(TAG, "parent " + parent);
        parentView = parent;
        mContext = context;
        this.from = from;
        initView();
    }

    private ImageButton deleteBtn;
    private EditText ghET,pbgET,fbgET;
    private TextView ghLevelTV,pbgLevelTV,fbgLevelTV;

    private MyTextView fbgTV;
    private TextView fbgHighValueTV,fbgLowValueTV;
    private String fbgHighValue,fbgLowValue;

    private MyTextView  pbgTV;
    private TextView pbgHighValueTV;
    private String pbgHighValue;

    private MyTextView ghTV;
    private TextView ghHighValueTV,ghLowValueTV;
    private String ghHighValue,ghLowValue;

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.activity_follow_up_table_diabetes_aux_blood_sugar, null);
        parentView.addView(view);
//        this.addView(biochemicalView);

        deleteBtn = (ImageButton) view.findViewById(R.id.blood_delete_image);
        deleteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                parentView.removeView(view);
                if(from.equals("糖尿病")){
                    FUTableDiabetesActivity activity = (FUTableDiabetesActivity)mContext;
                    activity.removeAuxMap(name);
                }

            }
        });

        ghET = (EditText)view.findViewById(R.id.glycosylated_hemoglobin_et);
        ghLevelTV = (TextView)view.findViewById(R.id.glycosylated_hemoglobin_level_tv);

        ghHighValueTV = (TextView)view.findViewById(R.id.gh_high_normal_tv);
        ghLowValueTV = (TextView)view.findViewById(R.id.gh_low_normal_tv);
        ghHighValue = ghHighValueTV.getText().toString();
        ghLowValue = ghLowValueTV.getText().toString();


        pbgET = (EditText)view.findViewById(R.id.pbg_et);
        pbgLevelTV = (TextView)view.findViewById(R.id.pbg_level_tv);

        pbgHighValueTV = (TextView)view.findViewById(R.id.pbg_high_normal_tv);
        pbgHighValue = pbgHighValueTV.getText().toString();

        fbgET = (EditText)view.findViewById(R.id.fbg_et);
        fbgLevelTV = (TextView)view.findViewById(R.id.fbg_level_tv);

        fbgHighValueTV = (TextView)view.findViewById(R.id.fbg_high_normal_tv);
        fbgLowValueTV = (TextView)view.findViewById(R.id.fbg_low_normal_tv);
        fbgHighValue = fbgHighValueTV.getText().toString();
        fbgLowValue = fbgLowValueTV.getText().toString();

        ghET.addTextChangedListener(bloodSugarTextWatcher);
        pbgET.addTextChangedListener(bloodSugarTextWatcher);
        fbgET.addTextChangedListener(bloodSugarTextWatcher);


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
                DatePickerDialog dpd = new DatePickerDialog(mContext, DatePickerDialog.THEME_HOLO_LIGHT, Datelistener, year, month, day);
                dpd.show();//显示DatePickerDialog组件


            }
        });
        timeTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext,TimePickerDialog.THEME_HOLO_LIGHT,timeListener,hour,min,true);
                timePickerDialog.show();
            }
        });

        initChartData();
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

    private TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
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
    private void initChartData(){
        fbgTV = (MyTextView)view.findViewById(R.id.fbg_tv);
        pbgTV = (MyTextView)view.findViewById(R.id.pbg_tv);
        ghTV = (MyTextView)view.findViewById(R.id.glycosylated_hemoglobin_tv);


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
        fbgTV.setRadarViewData(radarViewData);
        fbgTV.setRadarViewMaxValue(90);
        fbgTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        fbgTV.setLineData(fbgTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        pbgTV.setRadarViewData(radarViewData);
        pbgTV.setRadarViewMaxValue(90);
        pbgTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        pbgTV.setLineData(pbgTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        ghTV.setRadarViewData(radarViewData);
        ghTV.setRadarViewMaxValue(90);
        ghTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        ghTV.setLineData(ghTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);
    }
    private String getFBG(){
        return fbgET.getText().toString();
    }
    private String getGH(){
        return ghET.getText().toString();
    }
    private String getPBG(){
        return pbgET.getText().toString();
    }
    private TextWatcher bloodSugarTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (!s.toString().equals("")) {
                FUTableDiabetesActivity activity = (FUTableDiabetesActivity) mContext;
                if (activity.getCurrentFocus().getId() == fbgET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(fbgLowValue)) {
                        fbgLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(fbgHighValue)) {
                        fbgLevelTV.setText("↑");
                    } else {
                        fbgLevelTV.setText("正常");
                    }
                } else if (activity.getCurrentFocus().getId() == ghET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(ghLowValue)) {
                        ghLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(ghHighValue)) {
                        ghLevelTV.setText("↑");
                    } else {
                        ghLevelTV.setText("正常");
                    }
                }else if(activity.getCurrentFocus().getId() == pbgET.getId()){
                    if(Float.valueOf(s.toString())>=Float.valueOf(pbgHighValue)){
                        pbgLevelTV.setText("↑");
                    }else{
                        pbgLevelTV.setText("正常");
                    }
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
