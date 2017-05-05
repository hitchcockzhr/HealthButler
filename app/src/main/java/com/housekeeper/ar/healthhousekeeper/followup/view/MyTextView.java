package com.housekeeper.ar.healthhousekeeper.followup.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.MyDialog;
import com.housekeeper.ar.healthhousekeeper.R;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lenovo on 2016/11/29.
 */
public class MyTextView extends TextView {

    private Context mContext;
    private String TAG = "MyTextView";
    public MyTextView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }
    private void init(){
        this.setOnClickListener(clickListener);
    }
    private OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

//            Intent intent = new Intent(mContext,ChartActivity.class);
//            mContext.startActivity(intent);

            LayoutInflater inflater =LayoutInflater.from(mContext);
            final View view = inflater.inflate(R.layout.activity_followup_table_chartview, null);
            final MyDialog dialog = new MyDialog(mContext, view,R.style.load_dialog2);
            int width = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.show();

            Button backBtn = (Button)view.findViewById(R.id.back_btn);
            backBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            RadarView radarView = (RadarView)view.findViewById(R.id.radar_view);
            radarView.setData(radarViewData);
            radarView.setTitles(radarViewTitles);
            radarView.setMaxValue(radarViewMaxValue);

            MyLineView lineView = (MyLineView)view.findViewById(R.id.xchart_line_view);
            Log.i(TAG, "lineView "+lineView);
            lineView.setData(title, axisMax,axisSteps,axisDetailModeSteps, xLabels,dataTextList, dataColorList, dataList);
            Log.i(TAG, "lineView22 " + lineView);
        }
    };
    private String[] radarViewTitles = {"a","b","c","d","e","f"};
    private double[] radarViewData = {100,60,60,60,100,50,10,20}; //各维度分值
    private float radarViewMaxValue = 100;             //数据最大值
    //设置六角边标题
    public void setRadarViewTitles(String[] titles) {
        this.radarViewTitles = titles;
    }

    //设置六角边数值
    public void setRadarViewData(double[] data) {
        this.radarViewData = data;
    }
    //设置六角边最大数值
    public void setRadarViewMaxValue(float maxValue) {
        this.radarViewMaxValue = maxValue;
    }



    LinkedList<String> xLabels;
    String title;
    List<String> dataTextList;
    List<HashMap<String,Integer>>dataColorList;
    List<LinkedList<Double>>dataList;
    int axisMax,axisSteps, axisDetailModeSteps;
    /**
     * 设置折线数据
     * @param xLabels X坐标值集合
     * @param title   标题
     * @param axisMax Y坐标最大值
     * @param axisSteps Y坐标刻度值
     * @param axisDetailModeSteps 刻度段，表示axisDetailModeSteps个axisSteps标示一个Y数据
     * @param dataTextList  线段描述文字集合
     * @param dataColorList 线段颜色集合
     * @param dataList  线段各点值集合
     */
    public void setLineData(String title,int axisMax,int axisSteps, int axisDetailModeSteps,LinkedList<String> xLabels,List<String>dataTextList,List<HashMap<String,Integer>>dataColorList,List<LinkedList<Double>>dataList){

        this.xLabels = xLabels;
        this.title = title;
        this.dataTextList = dataTextList;
        this.dataColorList = dataColorList;
        this.dataList = dataList;
        this.axisMax = axisMax;
        this.axisSteps = axisSteps;
        this.axisDetailModeSteps = axisDetailModeSteps;
    }
}
