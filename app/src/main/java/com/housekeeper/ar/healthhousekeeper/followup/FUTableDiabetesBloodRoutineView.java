package com.housekeeper.ar.healthhousekeeper.followup;

import android.app.Activity;
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
 * 血常规
 * Created by Lenovo on 2016/11/17.
 */
public class FUTableDiabetesBloodRoutineView extends ViewGroup {
    private String TAG = "FUTableDiabetesBloodSugarView";
    private LinearLayout parentView;
//    private TextView dateTV,timeTV;
//    int year,month,day;
//    int hour,min;
    View view;
    Context mContext;
    String name = "血常规";
    String from;

    public FUTableDiabetesBloodRoutineView(Context context) {
        super(context);

    }

    public FUTableDiabetesBloodRoutineView(Context context, LinearLayout parent,String from){
        this(context);
        Log.i(TAG, "parent " + parent);
        parentView = parent;
        mContext = context;
        this.from = from;
        initView();
    }


    MyTextView twccTV;
    EditText twccET;
    TextView twccLevelTV,twccHighValueTV,twccLowValueTV;
    String twccHighValue,twccLowValue;

    MyTextView eorRatioTV;
    EditText eorRatioET;
    TextView eorRatioLevelTV,eorRatioHighValueTV,eorRatioLowValueTV;
    String eorRatioHighValue,eorRatioLowValue;

    MyTextView lymRatioTV;
    EditText lymRatioET;
    TextView lymRatioLevelTV,lymRatioHighValueTV,lymRatioLowValueTV;
    String lymRatioHighValue,lymRatioLowValue;

    MyTextView neuTV;
    EditText neuET;
    TextView neuLevelTV,neuHighValueTV,neuLowValueTV;
    String neuHighValue,neuLowValue;

    MyTextView mncTV;
    EditText mncET;
    TextView mncLevelTV,mncHighValueTV,mncLowValueTV;
    String mncHighValue,mncLowValue;

    MyTextView bgTV;
    EditText bgET;
    TextView bgLevelTV,bgHighValueTV,bgLowValueTV;
    String bgHighValue,bgLowValue;

    MyTextView hgbTV;
    EditText hgbET;
    TextView hgbLevelTV,hgbHighValueTV,hgbLowValueTV;
    String hgbHighValue,hgbLowValue;

    MyTextView mcvTV;
    EditText mcvET;
    TextView mcvLevelTV,mcvHighValueTV,mcvLowValueTV;
    String mcvHighValue,mcvLowValue;

    MyTextView rbcWidthTV;
    EditText rbcWidthET;
    TextView rbcWidthLevelTV,rbcWidthHighValueTV,rbcWidthLowValueTV;
    String rbcWidthHighValue,rbcWidthLowValue;

    MyTextView rbcAbsoluteTV;
    EditText rbcAbsoluteET;
    TextView rbcAbsoluteLevelTV,rbcAbsoluteHighValueTV,rbcAbsoluteLowValueTV;
    String rbcAbsoluteHighValue,rbcAbsoluteLowValue;

    MyTextView mpvTV;
    EditText mpvET;
    TextView mpvLevelTV,mpvHighValueTV,mpvLowValueTV;
    String mpvHighValue,mpvLowValue;

    MyTextView neuRatioTV;
    EditText neuRatioET;
    TextView neuRatioLevelTV,neuRatioHighValueTV,neuRatioLowValueTV;
    String neuRatioHighValue,neuRatioLowValue;

    MyTextView mncRatioTV;
    EditText mncRatioET;
    TextView mncRatioLevelTV,mncRatioHighValueTV,mncRatioLowValueTV;
    String mncRatioHighValue,mncRatioLowValue;

    MyTextView bgRatioTV;
    EditText bgRatioET;
    TextView bgRatioLevelTV,bgRatioHighValueTV,bgRatioLowValueTV;
    String bgRatioHighValue,bgRatioLowValue;

    MyTextView lymTV;
    EditText lymET;
    TextView lymLevelTV,lymHighValueTV,lymLowValueTV;
    String lymHighValue,lymLowValue;

    MyTextView eorTV;
    EditText eorET;
    TextView eorLevelTV,eorHighValueTV,eorLowValueTV;
    String eorHighValue,eorLowValue;

    MyTextView rbcTV;
    EditText rbcET;
    TextView rbcLevelTV,rbcHighValueTV,rbcLowValueTV;
    String rbcHighValue,rbcLowValue;

    MyTextView hctTV;
    EditText hctET;
    TextView hctLevelTV,hctHighValueTV,hctLowValueTV;
    String hctHighValue,hctLowValue;

    MyTextView mchTV;
    EditText mchET;
    TextView mchLevelTV,mchHighValueTV,mchLowValueTV;
    String mchHighValue,mchLowValue;

    MyTextView mchcTV;
    EditText mchcET;
    TextView mchcLevelTV,mchcHighValueTV,mchcLowValueTV;
    String mchcHighValue,mchcLowValue;

    MyTextView pltTV;
    EditText pltET;
    TextView pltLevelTV,pltHighValueTV,pltLowValueTV;
    String pltHighValue,pltLowValue;

    MyTextView pdwTV;
    EditText pdwET;
    TextView pdwLevelTV,pdwHighValueTV,pdwLowValueTV;
    String pdwHighValue,pdwLowValue;

    private TextView bloodDateTV,bloodTimeTV;
    int bloodYear,bloodMonth,bloodDay;
    int bloodHour,bloodMin;

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.activity_follow_up_table_diabetes_aux_blood_routine, null);
        parentView.addView(view);
//        this.addView(biochemicalView);

        ImageButton deleteBtn = (ImageButton)view.findViewById(R.id.blood_delete_image);
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

        twccET = (EditText)view.findViewById(R.id.twcc_et);
        twccLevelTV = (TextView)view.findViewById(R.id.twcc_level_tv);
        twccHighValueTV = (TextView)view.findViewById(R.id.twcc_high_normal_tv);
        twccLowValueTV = (TextView)view.findViewById(R.id.twcc_low_normal_tv);

        twccHighValue = twccHighValueTV.getText().toString();
        twccLowValue = twccLowValueTV.getText().toString();
        twccET.addTextChangedListener(bloodTextWatcher);

        lymRatioET = (EditText)view.findViewById(R.id.lym_ratio_et);
        lymRatioLevelTV = (TextView)view.findViewById(R.id.lym_ratio_level_tv);
        lymRatioHighValueTV = (TextView)view.findViewById(R.id.lym_ratio_high_normal_tv);
        lymRatioLowValueTV = (TextView)view.findViewById(R.id.lym_ratio_low_normal_tv);

        lymRatioHighValue =  lymRatioHighValueTV.getText().toString();
        lymRatioLowValue =  lymRatioLowValueTV.getText().toString();
        lymRatioET.addTextChangedListener( bloodTextWatcher);

        eorRatioET = (EditText)view.findViewById(R.id.eor_ratio_et);
        eorRatioLevelTV = (TextView)view.findViewById(R.id.eor_ratio_level_tv);
        eorRatioHighValueTV = (TextView)view.findViewById(R.id.eor_ratio_high_normal_tv);
        eorRatioLowValueTV = (TextView)view.findViewById(R.id.eor_ratio_low_normal_tv);

        eorRatioHighValue =  eorRatioHighValueTV.getText().toString();
        eorRatioLowValue =  eorRatioLowValueTV.getText().toString();
        eorRatioET.addTextChangedListener( bloodTextWatcher);

        neuET = (EditText)view.findViewById(R.id.neu_et);
        neuLevelTV = (TextView)view.findViewById(R.id.neu_level_tv);
        neuHighValueTV = (TextView)view.findViewById(R.id.neu_high_normal_tv);
        neuLowValueTV = (TextView)view.findViewById(R.id.neu_low_normal_tv);

        neuHighValue = neuHighValueTV.getText().toString();
        neuLowValue = neuLowValueTV.getText().toString();
        neuET.addTextChangedListener(bloodTextWatcher);

        mncET = (EditText)view.findViewById(R.id.mnc_et);
        mncLevelTV = (TextView)view.findViewById(R.id.mnc_level_tv);
        mncHighValueTV = (TextView)view.findViewById(R.id.mnc_high_normal_tv);
        mncLowValueTV = (TextView)view.findViewById(R.id.mnc_low_normal_tv);

        mncHighValue = mncHighValueTV.getText().toString();
        mncLowValue = mncLowValueTV.getText().toString();
        mncET.addTextChangedListener(bloodTextWatcher);


        bgET = (EditText)view.findViewById(R.id.bg_et);
        bgLevelTV = (TextView)view.findViewById(R.id.bg_level_tv);
        bgHighValueTV = (TextView)view.findViewById(R.id.bg_high_normal_tv);
        bgLowValueTV = (TextView)view.findViewById(R.id.bg_low_normal_tv);

        bgHighValue = bgHighValueTV.getText().toString();
        bgLowValue = bgLowValueTV.getText().toString();
        bgET.addTextChangedListener(bloodTextWatcher);


        hgbET = (EditText)view.findViewById(R.id.hgb_et);
        hgbLevelTV = (TextView)view.findViewById(R.id.hgb_level_tv);
        hgbHighValueTV = (TextView)view.findViewById(R.id.hgb_high_normal_tv);
        hgbLowValueTV = (TextView)view.findViewById(R.id.hgb_low_normal_tv);

        hgbHighValue = hgbHighValueTV.getText().toString();
        hgbLowValue = hgbLowValueTV.getText().toString();
        hgbET.addTextChangedListener(bloodTextWatcher);


        mcvET = (EditText)view.findViewById(R.id.mcv_et);
        mcvLevelTV = (TextView)view.findViewById(R.id.mcv_level_tv);
        mcvHighValueTV = (TextView)view.findViewById(R.id.mcv_high_normal_tv);
        mcvLowValueTV = (TextView)view.findViewById(R.id.mcv_low_normal_tv);

        mcvHighValue = mcvHighValueTV.getText().toString();
        mcvLowValue = mcvLowValueTV.getText().toString();
        mcvET.addTextChangedListener(bloodTextWatcher);

        rbcWidthET = (EditText)view.findViewById(R.id.rbc_width_et);
        rbcWidthLevelTV = (TextView)view.findViewById(R.id.rbc_width_level_tv);
        rbcWidthHighValueTV = (TextView)view.findViewById(R.id.rbc_width_high_normal_tv);
        rbcWidthLowValueTV = (TextView)view.findViewById(R.id.rbc_width_low_normal_tv);

        rbcWidthHighValue = rbcWidthHighValueTV.getText().toString();
        rbcWidthLowValue = rbcWidthLowValueTV.getText().toString();
        rbcWidthET.addTextChangedListener(bloodTextWatcher);


        rbcAbsoluteET = (EditText)view.findViewById(R.id.rbc_absolute_value_et);
        rbcAbsoluteLevelTV = (TextView)view.findViewById(R.id.rbc_absolute_value_level_tv);
        rbcAbsoluteHighValueTV = (TextView)view.findViewById(R.id.rbc_absolute_value_high_normal_tv);
        rbcAbsoluteLowValueTV = (TextView)view.findViewById(R.id.rbc_absolute_value_low_normal_tv);

        rbcAbsoluteHighValue = rbcAbsoluteHighValueTV.getText().toString();
        rbcAbsoluteLowValue = rbcAbsoluteLowValueTV.getText().toString();
        rbcAbsoluteET.addTextChangedListener(bloodTextWatcher);


        mpvET = (EditText)view.findViewById(R.id.mpv_et);
        mpvLevelTV = (TextView)view.findViewById(R.id.mpv_level_tv);
        mpvHighValueTV = (TextView)view.findViewById(R.id.mpv_high_normal_tv);
        mpvLowValueTV = (TextView)view.findViewById(R.id.mpv_low_normal_tv);

        mpvHighValue = mpvHighValueTV.getText().toString();
        mpvLowValue = mpvLowValueTV.getText().toString();
        mpvET.addTextChangedListener(bloodTextWatcher);


        neuRatioET = (EditText)view.findViewById(R.id.neu_ratio_et);
        neuRatioLevelTV = (TextView)view.findViewById(R.id.neu_ratio_level_tv);
        neuRatioHighValueTV = (TextView)view.findViewById(R.id.neu_ratio_high_normal_tv);
        neuRatioLowValueTV = (TextView)view.findViewById(R.id.neu_ratio_low_normal_tv);

        neuRatioHighValue = neuRatioHighValueTV.getText().toString();
        neuRatioLowValue = neuRatioLowValueTV.getText().toString();
        neuRatioET.addTextChangedListener(bloodTextWatcher);

        mncRatioET = (EditText)view.findViewById(R.id.mnc_ratio_et);
        mncRatioLevelTV = (TextView)view.findViewById(R.id.mnc_ratio_level_tv);
        mncRatioHighValueTV = (TextView)view.findViewById(R.id.mnc_ratio_high_normal_tv);
        mncRatioLowValueTV = (TextView)view.findViewById(R.id.mnc_ratio_low_normal_tv);

        mncRatioHighValue = mncRatioHighValueTV.getText().toString();
        mncRatioLowValue = mncRatioLowValueTV.getText().toString();
        mncRatioET.addTextChangedListener(bloodTextWatcher);


        bgRatioET = (EditText)view.findViewById(R.id.bg_ratio_et);
        bgRatioLevelTV = (TextView)view.findViewById(R.id.bg_ratio_level_tv);
        bgRatioHighValueTV = (TextView)view.findViewById(R.id.bg_ratio_high_normal_tv);
        bgRatioLowValueTV = (TextView)view.findViewById(R.id.bg_ratio_low_normal_tv);

        bgRatioHighValue = bgRatioHighValueTV.getText().toString();
        bgRatioLowValue = bgRatioLowValueTV.getText().toString();
        bgRatioET.addTextChangedListener(bloodTextWatcher);


        lymET = (EditText)view.findViewById(R.id.lym_et);
        lymLevelTV = (TextView)view.findViewById(R.id.lym_level_tv);
        lymHighValueTV = (TextView)view.findViewById(R.id.lym_high_normal_tv);
        lymLowValueTV = (TextView)view.findViewById(R.id.lym_low_normal_tv);

        lymHighValue = lymHighValueTV.getText().toString();
        lymLowValue = lymLowValueTV.getText().toString();
        lymET.addTextChangedListener(bloodTextWatcher);

        eorET = (EditText)view.findViewById(R.id.eor_et);
        eorLevelTV = (TextView)view.findViewById(R.id.eor_level_tv);
        eorHighValueTV = (TextView)view.findViewById(R.id.eor_high_normal_tv);
        eorLowValueTV = (TextView)view.findViewById(R.id.eor_low_normal_tv);

        eorHighValue = eorHighValueTV.getText().toString();
        eorLowValue = eorLowValueTV.getText().toString();
        eorET.addTextChangedListener(bloodTextWatcher);

        rbcET = (EditText)view.findViewById(R.id.rbc_et);
        rbcLevelTV = (TextView)view.findViewById(R.id.rbc_level_tv);
        rbcHighValueTV = (TextView)view.findViewById(R.id.rbc_high_normal_tv);
        rbcLowValueTV = (TextView)view.findViewById(R.id.rbc_low_normal_tv);

        rbcHighValue = rbcHighValueTV.getText().toString();
        rbcLowValue = rbcLowValueTV.getText().toString();
        rbcET.addTextChangedListener(bloodTextWatcher);


        hctET = (EditText)view.findViewById(R.id.hct_et);
        hctLevelTV = (TextView)view.findViewById(R.id.hct_level_tv);
        hctHighValueTV = (TextView)view.findViewById(R.id.hct_high_normal_tv);
        hctLowValueTV = (TextView)view.findViewById(R.id.hct_low_normal_tv);

        hctHighValue = hctHighValueTV.getText().toString();
        hctLowValue = hctLowValueTV.getText().toString();
        hctET.addTextChangedListener(bloodTextWatcher);

        mchET = (EditText)view.findViewById(R.id.mch_et);
        mchLevelTV = (TextView)view.findViewById(R.id.mch_level_tv);
        mchHighValueTV = (TextView)view.findViewById(R.id.mch_high_normal_tv);
        mchLowValueTV = (TextView)view.findViewById(R.id.mch_low_normal_tv);

        mchHighValue = mchHighValueTV.getText().toString();
        mchLowValue = mchLowValueTV.getText().toString();
        mchET.addTextChangedListener(bloodTextWatcher);


        mchcET = (EditText)view.findViewById(R.id.mchc_et);
        mchcLevelTV = (TextView)view.findViewById(R.id.mchc_level_tv);
        mchcHighValueTV = (TextView)view.findViewById(R.id.mchc_high_normal_tv);
        mchcLowValueTV = (TextView)view.findViewById(R.id.mchc_low_normal_tv);

        mchcHighValue = mchcHighValueTV.getText().toString();
        mchcLowValue = mchcLowValueTV.getText().toString();
        mchcET.addTextChangedListener(bloodTextWatcher);


        pltET = (EditText)view.findViewById(R.id.plt_et);
        pltLevelTV = (TextView)view.findViewById(R.id.plt_level_tv);
        pltHighValueTV = (TextView)view.findViewById(R.id.plt_high_normal_tv);
        pltLowValueTV = (TextView)view.findViewById(R.id.plt_low_normal_tv);

        pltHighValue = pltHighValueTV.getText().toString();
        pltLowValue = pltLowValueTV.getText().toString();
        pltET.addTextChangedListener(bloodTextWatcher);

        pdwET = (EditText)view.findViewById(R.id.pdw_et);
        pdwLevelTV = (TextView)view.findViewById(R.id.pdw_level_tv);
        pdwHighValueTV = (TextView)view.findViewById(R.id.pdw_high_normal_tv);
        pdwLowValueTV = (TextView)view.findViewById(R.id.pdw_low_normal_tv);

        pdwHighValue = pdwHighValueTV.getText().toString();
        pdwLowValue = pdwLowValueTV.getText().toString();
        pdwET.addTextChangedListener(bloodTextWatcher);

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


        initChartData();

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
    private void initChartData(){
        twccTV = (MyTextView)view.findViewById(R.id.twcc_tv);
        lymRatioTV = (MyTextView)view.findViewById(R.id.lym_ratio_tv);
        eorRatioTV = (MyTextView)view.findViewById(R.id.eor_ratio_tv);
        neuTV = (MyTextView)view.findViewById(R.id.neu_tv);
        mncTV = (MyTextView)view.findViewById(R.id.mnc_tv);
        bgTV = (MyTextView)view.findViewById(R.id.bg_tv);
        hgbTV = (MyTextView)view.findViewById(R.id.hgb_tv);
        mcvTV = (MyTextView)view.findViewById(R.id.mcv_tv);
        rbcWidthTV = (MyTextView)view.findViewById(R.id.rbc_width_tv);
        rbcAbsoluteTV = (MyTextView)view.findViewById(R.id.rbc_absolute_value_tv);
        mpvTV = (MyTextView)view.findViewById(R.id.mpv_tv);
        neuRatioTV = (MyTextView)view.findViewById(R.id.neu_ratio_tv);
        mncRatioTV = (MyTextView)view.findViewById(R.id.mnc_ratio_tv);
        bgRatioTV = (MyTextView)view.findViewById(R.id.bg_ratio_tv);
        lymTV = (MyTextView)view.findViewById(R.id.lym_tv);
        eorTV = (MyTextView)view.findViewById(R.id.eor_tv);
        rbcTV = (MyTextView)view.findViewById(R.id.rbc_tv);
        hctTV = (MyTextView)view.findViewById(R.id.hct_tv);
        mchTV = (MyTextView)view.findViewById(R.id.mch_tv);
        mchcTV = (MyTextView)view.findViewById(R.id.mchc_tv);
        pltTV = (MyTextView)view.findViewById(R.id.plt_tv);
        pdwTV = (MyTextView)view.findViewById(R.id.pdw_tv);


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
        twccTV.setRadarViewData(radarViewData);
        twccTV.setRadarViewMaxValue(90);
        twccTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        twccTV.setLineData(twccTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        lymRatioTV.setRadarViewData(radarViewData);
        lymRatioTV.setRadarViewMaxValue(90);
        lymRatioTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        lymRatioTV.setLineData(lymRatioTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        eorRatioTV.setRadarViewData(radarViewData);
        eorRatioTV.setRadarViewMaxValue(90);
        eorRatioTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        eorRatioTV.setLineData(eorRatioTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        neuTV.setRadarViewData(radarViewData);
        neuTV.setRadarViewMaxValue(90);
        neuTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        neuTV.setLineData(neuTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        mncTV.setRadarViewData(radarViewData);
        mncTV.setRadarViewMaxValue(90);
        mncTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        mncTV.setLineData(mncTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        bgTV.setRadarViewData(radarViewData);
        bgTV.setRadarViewMaxValue(90);
        bgTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        bgTV.setLineData(bgTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        hgbTV.setRadarViewData(radarViewData);
        hgbTV.setRadarViewMaxValue(90);
        hgbTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        hgbTV.setLineData(hgbTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        mcvTV.setRadarViewData(radarViewData);
        mcvTV.setRadarViewMaxValue(90);
        mcvTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        mcvTV.setLineData(mcvTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        rbcWidthTV.setRadarViewData(radarViewData);
        rbcWidthTV.setRadarViewMaxValue(90);
        rbcWidthTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        rbcWidthTV.setLineData(rbcWidthTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        rbcAbsoluteTV.setRadarViewData(radarViewData);
        rbcAbsoluteTV.setRadarViewMaxValue(90);
        rbcAbsoluteTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        rbcAbsoluteTV.setLineData(rbcAbsoluteTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        mpvTV.setRadarViewData(radarViewData);
        mpvTV.setRadarViewMaxValue(90);
        mpvTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        mpvTV.setLineData(mpvTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        neuRatioTV.setRadarViewData(radarViewData);
        neuRatioTV.setRadarViewMaxValue(90);
        neuRatioTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        neuRatioTV.setLineData(neuRatioTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        mncRatioTV.setRadarViewData(radarViewData);
        mncRatioTV.setRadarViewMaxValue(90);
        mncRatioTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        mncRatioTV.setLineData(mncRatioTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        bgRatioTV.setRadarViewData(radarViewData);
        bgRatioTV.setRadarViewMaxValue(90);
        bgRatioTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        bgRatioTV.setLineData(bgRatioTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        lymTV.setRadarViewData(radarViewData);
        lymTV.setRadarViewMaxValue(90);
        lymTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        lymTV.setLineData(lymTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        eorTV.setRadarViewData(radarViewData);
        eorTV.setRadarViewMaxValue(90);
        eorTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        eorTV.setLineData(eorTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        rbcTV.setRadarViewData(radarViewData);
        rbcTV.setRadarViewMaxValue(90);
        rbcTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        rbcTV.setLineData(rbcTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        hctTV.setRadarViewData(radarViewData);
        hctTV.setRadarViewMaxValue(90);
        hctTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        hctTV.setLineData(hctTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        mchTV.setRadarViewData(radarViewData);
        mchTV.setRadarViewMaxValue(90);
        mchTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        mchTV.setLineData(mchTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        mchcTV.setRadarViewData(radarViewData);
        mchcTV.setRadarViewMaxValue(90);
        mchcTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        mchcTV.setLineData(mchcTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        pltTV.setRadarViewData(radarViewData);
        pltTV.setRadarViewMaxValue(90);
        pltTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        pltTV.setLineData(pltTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        pdwTV.setRadarViewData(radarViewData);
        pdwTV.setRadarViewMaxValue(90);
        pdwTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        pdwTV.setLineData(pdwTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


    }

    private TextWatcher bloodTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.i(TAG,"twccTextWatcher onTextChanged s "+s);
            Activity activity = (Activity) mContext;
            if (!s.toString().equals("")){
                if(activity.getCurrentFocus().getId() == twccET.getId()){
                    if(Float.valueOf(s.toString())<Float.valueOf(twccLowValue)){
                        twccLevelTV.setText("↓");
                    }else if(Float.valueOf(s.toString())>Float.valueOf(twccHighValue)){
                        twccLevelTV.setText("↑");
                    }else{
                        twccLevelTV.setText("正常");
                    }
                }else  if(activity.getCurrentFocus().getId() == lymRatioET.getId()){
                    if(Float.valueOf(s.toString())<Float.valueOf(lymRatioLowValue)){
                        lymRatioLevelTV.setText("↓");
                    }else if(Float.valueOf(s.toString())>Float.valueOf(lymRatioHighValue)){
                        lymRatioLevelTV.setText("↑");
                    }else{
                        lymRatioLevelTV.setText("正常");
                    }
                }else  if(activity.getCurrentFocus().getId() == eorRatioET.getId()){
                    if(Float.valueOf(s.toString())<Float.valueOf(eorRatioLowValue)){
                        eorRatioLevelTV.setText("↓");
                    }else if(Float.valueOf(s.toString())>Float.valueOf(eorRatioHighValue)){
                        eorRatioLevelTV.setText("↑");
                    }else{
                        eorRatioLevelTV.setText("正常");
                    }
                }else  if(activity.getCurrentFocus().getId() == neuET.getId()){
                    if(Float.valueOf(s.toString())<Float.valueOf(neuLowValue)){
                        neuLevelTV.setText("↓");
                    }else if(Float.valueOf(s.toString())>Float.valueOf(neuHighValue)){
                        neuLevelTV.setText("↑");
                    }else{
                        neuLevelTV.setText("正常");
                    }
                }else  if(activity.getCurrentFocus().getId() == mncET.getId()){
                    if(Float.valueOf(s.toString())<Float.valueOf(mncLowValue)){
                        mncLevelTV.setText("↓");
                    }else if(Float.valueOf(s.toString())>Float.valueOf(mncHighValue)){
                        mncLevelTV.setText("↑");
                    }else{
                        mncLevelTV.setText("正常");
                    }
                }else  if(activity.getCurrentFocus().getId() == bgET.getId()){
                    if(Float.valueOf(s.toString())<Float.valueOf(bgLowValue)){
                        bgLevelTV.setText("↓");
                    }else if(Float.valueOf(s.toString())>Float.valueOf(bgHighValue)){
                        bgLevelTV.setText("↑");
                    }else{
                        bgLevelTV.setText("正常");
                    }
                }else  if(activity.getCurrentFocus().getId() == hgbET.getId()){
                    if(Float.valueOf(s.toString())<Float.valueOf(hgbLowValue)){
                        hgbLevelTV.setText("↓");
                    }else if(Float.valueOf(s.toString())>Float.valueOf(hgbHighValue)){
                        hgbLevelTV.setText("↑");
                    }else{
                        hgbLevelTV.setText("正常");
                    }
                }else  if(activity.getCurrentFocus().getId() == mcvET.getId()){
                    if(Float.valueOf(s.toString())<Float.valueOf(mcvLowValue)){
                        mcvLevelTV.setText("↓");
                    }else if(Float.valueOf(s.toString())>Float.valueOf(mcvHighValue)){
                        mcvLevelTV.setText("↑");
                    }else{
                        mcvLevelTV.setText("正常");
                    }
                }else  if(activity.getCurrentFocus().getId() == rbcWidthET.getId()){
                    if(Float.valueOf(s.toString())<Float.valueOf(rbcWidthLowValue)){
                        rbcWidthLevelTV.setText("↓");
                    }else if(Float.valueOf(s.toString())>Float.valueOf(rbcWidthHighValue)){
                        rbcWidthLevelTV.setText("↑");
                    }else{
                        rbcWidthLevelTV.setText("正常");
                    }
                }else  if(activity.getCurrentFocus().getId() == rbcAbsoluteET.getId()){
                    if(Float.valueOf(s.toString())<Float.valueOf(rbcAbsoluteLowValue)){
                        rbcAbsoluteLevelTV.setText("↓");
                    }else if(Float.valueOf(s.toString())>Float.valueOf(rbcAbsoluteHighValue)){
                        rbcAbsoluteLevelTV.setText("↑");
                    }else{
                        rbcAbsoluteLevelTV.setText("正常");
                    }
                }else  if(activity.getCurrentFocus().getId() == mpvET.getId()){
                    if(Float.valueOf(s.toString())<Float.valueOf(mpvLowValue)){
                        mpvLevelTV.setText("↓");
                    }else if(Float.valueOf(s.toString())>Float.valueOf(mpvHighValue)){
                        mpvLevelTV.setText("↑");
                    }else{
                        mpvLevelTV.setText("正常");
                    }
                }else  if(activity.getCurrentFocus().getId() == neuRatioET.getId()){
                    if(Float.valueOf(s.toString())<Float.valueOf(neuRatioLowValue)){
                        neuRatioLevelTV.setText("↓");
                    }else if(Float.valueOf(s.toString())>Float.valueOf(neuRatioHighValue)){
                        neuRatioLevelTV.setText("↑");
                    }else{
                        neuRatioLevelTV.setText("正常");
                    }
                }else  if(activity.getCurrentFocus().getId() == mncRatioET.getId()){
                    if(Float.valueOf(s.toString())<Float.valueOf(mncRatioLowValue)){
                        mncRatioLevelTV.setText("↓");
                    }else if(Float.valueOf(s.toString())>Float.valueOf(mncRatioHighValue)){
                        mncRatioLevelTV.setText("↑");
                    }else{
                        mncRatioLevelTV.setText("正常");
                    }
                }else  if(activity.getCurrentFocus().getId() == bgRatioET.getId()){
                    if(Float.valueOf(s.toString())<Float.valueOf(bgRatioLowValue)){
                        bgRatioLevelTV.setText("↓");
                    }else if(Float.valueOf(s.toString())>Float.valueOf(bgRatioHighValue)){
                        bgRatioLevelTV.setText("↑");
                    }else{
                        bgRatioLevelTV.setText("正常");
                    }
                }else  if(activity.getCurrentFocus().getId() == lymET.getId()){
                    if(Float.valueOf(s.toString())<Float.valueOf(lymLowValue)){
                        lymLevelTV.setText("↓");
                    }else if(Float.valueOf(s.toString())>Float.valueOf(lymHighValue)){
                        lymLevelTV.setText("↑");
                    }else{
                        lymLevelTV.setText("正常");
                    }
                }else  if(activity.getCurrentFocus().getId() == eorET.getId()){
                    if(Float.valueOf(s.toString())<Float.valueOf(eorLowValue)){
                        eorLevelTV.setText("↓");
                    }else if(Float.valueOf(s.toString())>Float.valueOf(eorHighValue)){
                        eorLevelTV.setText("↑");
                    }else{
                        eorLevelTV.setText("正常");
                    }
                }else  if(activity.getCurrentFocus().getId() == rbcET.getId()){
                    if(Float.valueOf(s.toString())<Float.valueOf(rbcLowValue)){
                        rbcLevelTV.setText("↓");
                    }else if(Float.valueOf(s.toString())>Float.valueOf(rbcHighValue)){
                        rbcLevelTV.setText("↑");
                    }else{
                        rbcLevelTV.setText("正常");
                    }
                }else  if(activity.getCurrentFocus().getId() == hctET.getId()){
                    if(Float.valueOf(s.toString())<Float.valueOf(hctLowValue)){
                        hctLevelTV.setText("↓");
                    }else if(Float.valueOf(s.toString())>Float.valueOf(hctHighValue)){
                        hctLevelTV.setText("↑");
                    }else{
                        hctLevelTV.setText("正常");
                    }
                }else  if(activity.getCurrentFocus().getId() == mchET.getId()){
                    if(Float.valueOf(s.toString())<Float.valueOf(mchLowValue)){
                        mchLevelTV.setText("↓");
                    }else if(Float.valueOf(s.toString())>Float.valueOf(mchHighValue)){
                        mchLevelTV.setText("↑");
                    }else{
                        mchLevelTV.setText("正常");
                    }
                }else  if(activity.getCurrentFocus().getId() == mchcET.getId()){
                    if(Float.valueOf(s.toString())<Float.valueOf(mchcLowValue)){
                        mchcLevelTV.setText("↓");
                    }else if(Float.valueOf(s.toString())>Float.valueOf(mchcHighValue)){
                        mchcLevelTV.setText("↑");
                    }else{
                        mchcLevelTV.setText("正常");
                    }
                }else  if(activity.getCurrentFocus().getId() == pltET.getId()){
                    if(Float.valueOf(s.toString())<Float.valueOf(pltLowValue)){
                        pltLevelTV.setText("↓");
                    }else if(Float.valueOf(s.toString())>Float.valueOf(pltHighValue)){
                        pltLevelTV.setText("↑");
                    }else{
                        pltLevelTV.setText("正常");
                    }
                }else  if(activity.getCurrentFocus().getId() == pdwET.getId()){
                    if(Float.valueOf(s.toString())<Float.valueOf(pdwLowValue)){
                        pdwLevelTV.setText("↓");
                    }else if(Float.valueOf(s.toString())>Float.valueOf(pdwHighValue)){
                        pdwLevelTV.setText("↑");
                    }else{
                        pdwLevelTV.setText("正常");
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
