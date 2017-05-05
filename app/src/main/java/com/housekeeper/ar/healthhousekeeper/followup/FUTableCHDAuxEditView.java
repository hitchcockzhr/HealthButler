package com.housekeeper.ar.healthhousekeeper.followup;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 只有一个编辑框的view
 * Created by Lenovo on 2016/12/12.
 */
public class FUTableCHDAuxEditView extends ViewGroup {
    private String TAG = "FUTableAuxEditView";
    private LinearLayout parentView;
    View view;
    Context mContext;
    String name = "心肌酶学检查结果";
    String fromType;
    public FUTableCHDAuxEditView(Context context) {
        super(context);
        mContext = context;
    }

    public FUTableCHDAuxEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }
    public FUTableCHDAuxEditView(Context context, LinearLayout parent, String name,String type){
        this(context);
        Log.i(TAG, "parent " + parent);
        parentView = parent;
        mContext = context;
        this.name = name;
        this.fromType = type;
        initView();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    TextView titleTV;
    EditText editview;
    ImageButton deleteBtn;

    private TextView dateTV,timeTV;
    int year,month,day;
    int hour,min;
    private void initView(){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.activity_follow_up_table_aux_electrocardiogram, null);
        parentView.addView(view);


        titleTV = (TextView)view.findViewById(R.id.ecg_title_tv);
        titleTV.setText(name);


        editview = (EditText)view.findViewById(R.id.ecg_et);
        editview.setText("");


        deleteBtn = (ImageButton)view.findViewById(R.id.ecg_delete_image);
        deleteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                parentView.removeView(view);
                if(fromType.equals("冠心病")){
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

        timeTV.setText(hour + ":" + min);

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
                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, TimePickerDialog.THEME_HOLO_LIGHT, BloodTimeListener, hour, min, true);
                timePickerDialog.show();
            }
        });





    }

    public void setData(String string){
        editview.setText(string);
    }
    public String getData(){
        return editview.getText().toString();
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
}
