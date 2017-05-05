/**
 * Copyright 2014  XCL-Charts
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 	
 * @Project XCL-Charts 
 * @Description Android图表基类库
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * @Copyright Copyright (c) 2014 XCL-Charts (www.xclcharts.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version 1.0
 */
package com.housekeeper.ar.healthhousekeeper.followup.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import org.xclcharts.renderer.XEnum;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName LineChart01View
 * @Description  折线图的例子 <br/>
 *  * 	~_~
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */
public class MyLineView extends DemoView implements Runnable {

	private String TAG = "LineChart02View";
	private org.xclcharts.chart.LineChart chart = new org.xclcharts.chart.LineChart();

	//标签集合
	private LinkedList<String> labels = new LinkedList<String>();
	private LinkedList<org.xclcharts.chart.LineData> chartData = new LinkedList<org.xclcharts.chart.LineData>();
	private List<org.xclcharts.chart.CustomLineData> mCustomLineDataset = new LinkedList<org.xclcharts.chart.CustomLineData>();

	//批注
	List<org.xclcharts.renderer.info.AnchorDataPoint> mAnchorSet = new ArrayList<org.xclcharts.renderer.info.AnchorDataPoint>();

	public MyLineView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}

	public MyLineView(Context context, AttributeSet attrs){
        super(context, attrs);
        initView();
	 }

	 public MyLineView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }

	private List<String>dataTextList;
	List<HashMap<String,Integer>>dataColorList;
	List<LinkedList<Double>>dataList;
	String title;
	int axisMax,axisSteps, axisDetailModeSteps;

	/**
	 * 设置折线数据
	 * @param xLabels X坐标值集合
	 * @param title   标题
	 * @param axisMax Y坐标最大值
	 * @param axisSteps Y坐标刻度值
	 * @param axisDetailModeSteps 刻度
	 * @param dataTextList  线段描述文字集合
	 * @param dataColorList 线段颜色集合,若成员是-1，-1，-1，则设置成默认值58,171,119
	 * @param dataList  线段各点值集合
	 */
	public void setData(String title,int axisMax,int axisSteps,int axisDetailModeSteps,LinkedList<String> xLabels, List<String>dataTextList,List<HashMap<String,Integer>>dataColorList,List<LinkedList<Double>>dataList){
		//X坐标值
		labels.clear();
		labels.addAll(xLabels);

		//文字、颜色、数值:
		this.dataTextList = dataTextList;
		this.dataColorList = dataColorList;
		this.dataList = dataList;

		this.title = title;
		this.axisMax = axisMax;
		this.axisSteps = axisSteps;
		this.axisDetailModeSteps = axisDetailModeSteps;


		setChartData();
		chartRender(title,axisMax,axisSteps,axisDetailModeSteps);
		new Thread(this).start();

		//綁定手势滑动事件
		this.bindTouch(this, chart);
	}
	private void setChartData(){

		chartData.clear();
		if(dataTextList != null &&dataList!=null){
			for(int i=0;i<dataList.size();i++){

				//Line 2
				LinkedList<Double> dataSeries= dataList.get(i);
				String text = dataTextList.get(i);

				int r=58;
				int g=171;
				int b=119;
				if(dataColorList != null && !dataColorList.isEmpty()){
					 r = dataColorList.get(i).get("r");
					 g = dataColorList.get(i).get("g");
					 b = dataColorList.get(i).get("b");
					if(r == -1){
						r=58;
					}
					if(g==-1){
						g=171;
					}
					if (b==-1){
						b=119;
					}
				}



				org.xclcharts.chart.LineData lineData = new org.xclcharts.chart.LineData(text,dataSeries, Color.rgb(r, g, b));
				lineData.setDotStyle(XEnum.DotStyle.DOT);
				lineData.getPlotLine().getDotPaint().setColor(Color.rgb(r, g, b));
				lineData.getDotLabelPaint().setColor(Color.rgb(r, g, b));
//				lineData.setLabelVisible(true);
//				lineData.getLabelOptions().getBox().getBackgroundPaint().setColor(Color.rgb(76, 76, 76));

				chartData.add(lineData);
			}
		}
	}
	 private void initView()
	 {
//		 	chartLabels();
//			chartDataSet();
//			chartDesireLines();
//			chartRender("测试");
//			new Thread(this).start();
//
//			//綁定手势滑动事件
//			this.bindTouch(this,chart);
	 }
	 
	
	@Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        super.onSizeChanged(w, h, oldw, oldh);  
       //图所占范围大小
        chart.setChartRange(w,h);
    }  

	private void chartRender(String title,int axisMax,int axisSteps,int axisDetailModeSteps)
	{
		try {				
						
			//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....		
			int [] ltrb = getBarLnDefaultSpadding();
			chart.setPadding(org.xclcharts.common.DensityUtil.dip2px(getContext(), 45),ltrb[1], ltrb[2],  ltrb[3]);
		
			
			//设定数据源
			chart.setCategories(labels);								
		//	chart.setDataSource(chartData);
			//chart.setCustomLines(mCustomLineDataset);
			
			//数据轴最大值
			chart.getDataAxis().setAxisMax(axisMax);
			//数据轴刻度间隔
			chart.getDataAxis().setAxisSteps(axisSteps);
			//指隔多少个轴刻度(即细刻度)后为主刻度
			chart.getDataAxis().setDetailModeSteps(axisDetailModeSteps);
					
			//背景网格
			chart.getPlotGrid().showHorizontalLines(); 
			
			//标题
			chart.setTitle(title);
//			chart.addSubtitle("(XCL-Charts Demo)");
			
			//隐藏顶轴和右边轴
			//chart.hideTopAxis();
			//chart.hideRightAxis();
			
			//设置轴风格
		
			//chart.getDataAxis().setTickMarksVisible(false);
			chart.getDataAxis().getAxisPaint().setStrokeWidth(2);
			chart.getDataAxis().getTickMarksPaint().setStrokeWidth(2);
			chart.getDataAxis().showAxisLabels();
			
			chart.getCategoryAxis().getAxisPaint().setStrokeWidth(2);
			chart.getCategoryAxis().hideTickMarks();
												
			//定义数据轴标签显示格式
			chart.getDataAxis().setLabelFormatter(new org.xclcharts.common.IFormatterTextCallBack(){
	
				@Override
				public String textFormatter(String value) {
					// TODO Auto-generated method stub		
					Double tmp = Double.parseDouble(value);
					DecimalFormat df=new DecimalFormat("#0");
					String label = df.format(tmp).toString();
					return (label);
				}
				
			});
			
			
			//定义线上交叉点标签显示格式
			chart.setItemLabelFormatter(new org.xclcharts.common.IFormatterDoubleCallBack() {
				@Override
				public String doubleFormatter(Double value) {
					// TODO Auto-generated method stub
					DecimalFormat df=new DecimalFormat("#0");
					String label = df.format(value).toString();
					return label;
				}});
			
			//chart.setItemLabelFormatter(callBack)
						
			//允许线与轴交叉时，线会断开
			chart.setLineAxisIntersectVisible(false);
			
			//chart.setDataSource(chartData); 
			//动态线									
			chart.showDyLine();
			
			//不封闭
			chart.setAxesClosed(false);
			
			//扩展绘图区右边分割的范围，让定制线的说明文字能显示出来
			chart.getClipExt().setExtRight(150.f);
			
			//设置标签交错换行显示
			chart.getCategoryAxis().setLabelLineFeed(XEnum.LabelLineFeed.ODD_EVEN);
			
			//仅能横向移动
			chart.setPlotPanMode(XEnum.PanMode.HORIZONTAL);
			
			
			//chart.getDataAxis().hide();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}
	
	//计算下平均线
	private double calcAvg()
	{
		double total = 400d + 480d + 500d + 560d + 800d + 950d +1200d + 630d + 710d;
		double yearNumber = 9d;
		
		return (total/yearNumber);
	}

	//数值
	private void chartDataSet()
	{
		//Line 1
		LinkedList<Double> dataSeries1= new LinkedList<Double>();
		dataSeries1.add(400d); 
		dataSeries1.add(480d); 
		dataSeries1.add(500d); 
		dataSeries1.add(560d); 
		org.xclcharts.chart.LineData lineData1 = new org.xclcharts.chart.LineData("单间(5层光线好)",dataSeries1, Color.rgb(234, 83, 71));
		lineData1.setDotStyle(XEnum.DotStyle.DOT);
	
		//Line 2
		LinkedList<Double> dataSeries2= new LinkedList<Double>();
		dataSeries2.add(0d);
		dataSeries2.add(0d);
		dataSeries2.add(0d);
		dataSeries2.add(0d);
		dataSeries2.add((double)800); 
		dataSeries2.add((double)950); 
		dataSeries2.add((double)1200); 	
		
		org.xclcharts.chart.LineData lineData2 = new org.xclcharts.chart.LineData("一房一厅(3层无光线)",dataSeries2, Color.rgb(75, 166, 51));
		lineData2.setDotStyle(XEnum.DotStyle.PRISMATIC);
		lineData2.getPlotLine().getDotPaint().setColor(Color.rgb(234, 142, 43));
		lineData2.getDotLabelPaint().setColor(Color.rgb(234, 142, 43));
		lineData2.setLabelVisible(true);	
		lineData2.getLabelOptions().getBox().getBackgroundPaint().setColor(Color.rgb(76, 76, 76));
		//lineData2.getPlotLabel().hideBox();
	
		//Line 3
		LinkedList<Double> dataSeries3= new LinkedList<Double>();
		dataSeries3.add(0d);
		dataSeries3.add(0d);
		dataSeries3.add(0d);
		dataSeries3.add(0d);
		dataSeries3.add(0d);
		dataSeries3.add(0d);
		dataSeries3.add(0d);
		dataSeries3.add(630d);
		dataSeries3.add(710d);
		
		org.xclcharts.chart.LineData lineData3 = new org.xclcharts.chart.LineData("单间(9层无电梯)",dataSeries3, Color.rgb(123, 89, 168));
		lineData3.setDotStyle(XEnum.DotStyle.DOT);
		
		//轴上分界线的交叉点
		LinkedList<Double> dataSeries4= new LinkedList<Double>();
		dataSeries4.add(1500d);
		LinkedList<Double> dataSeries5= new LinkedList<Double>();
		dataSeries5.add(3000d);
		LinkedList<Double> dataSeries6= new LinkedList<Double>();
		dataSeries6.add(calcAvg());		
		
		org.xclcharts.chart.LineData lineData4 = new org.xclcharts.chart.LineData("",dataSeries4, Color.rgb(35, 172, 57));
		lineData4.setDotStyle(XEnum.DotStyle.RECT);
		org.xclcharts.chart.LineData lineData5 = new org.xclcharts.chart.LineData("",dataSeries5, Color.rgb(69, 181, 248));
		lineData5.setDotStyle(XEnum.DotStyle.RECT);
		org.xclcharts.chart.LineData lineData6 = new org.xclcharts.chart.LineData("",dataSeries6, Color.rgb(251, 79, 128));
		lineData6.setDotStyle(XEnum.DotStyle.TRIANGLE);
	
		chartData.add(lineData1);
		chartData.add(lineData2);
		chartData.add(lineData3);
		
		chartData.add(lineData4);
		chartData.add(lineData5);
		chartData.add(lineData6); 
		

		//批注
		//List<AnchorDataPoint> mAnchorSet = new ArrayList<AnchorDataPoint>();
		org.xclcharts.renderer.info.AnchorDataPoint an4 = new org.xclcharts.renderer.info.AnchorDataPoint(0,2, XEnum.AnchorStyle.CAPRECT);
		an4.setAnchor("批注");
		an4.setBgColor(Color.rgb(255, 145, 126));
		mAnchorSet.add(an4);
		chart.setAnchorDataPoint(mAnchorSet);	
	}

	//横坐标
	private void chartLabels()
	{
		labels.add("2006");
		labels.add("2007");
		labels.add("2008");
		labels.add("2009");
		labels.add("2010");
		labels.add("2011");
		labels.add("2012");
		labels.add("2013");
		labels.add("2014");
	}
	
	/**
	 * 期望线/分界线
	 */
	private void chartDesireLines()
	{				
		mCustomLineDataset.add(new org.xclcharts.chart.CustomLineData("稍好",1500d, Color.rgb(35, 172, 57),5));
		mCustomLineDataset.add(new org.xclcharts.chart.CustomLineData("舒适",3000d, Color.rgb(69, 181, 248),5));
		mCustomLineDataset.add(new org.xclcharts.chart.CustomLineData("[个人均线]",calcAvg(), Color.rgb(251, 79, 128),6));
	}
	
	@Override
    public void render(Canvas canvas) {
        try{
            chart.render(canvas);
        } catch (Exception e){
        	Log.e(TAG, e.toString());
        }
    }


	@Override
	public void run() {
		// TODO Auto-generated method stub
		 try {          
	         	chartAnimation();         	
	         }
	         catch(Exception e) {
	             Thread.currentThread().interrupt();
	         }  
	}
	
	private void chartAnimation()
	{
		  try { 
          	int count =  chartData.size();
          	for(int i=0;i< count ;i++)
          	{
          		Thread.sleep(150);
          		LinkedList<org.xclcharts.chart.LineData> animationData = new LinkedList<org.xclcharts.chart.LineData>();
          		for(int j=0;j<=i;j++)
                {            			            			
          			animationData.add(chartData.get(j));          			
                }   
         
          		//Log.e(TAG,"size = "+animationData.size());
          		chart.setDataSource(animationData);          		
          		if(i == count - 1)
          		{
          			chart.getDataAxis().show();
          			chart.getDataAxis().showAxisLabels();   
          			
          			chart.setCustomLines(mCustomLineDataset);
          		}
          		postInvalidate();            		
          	}            	
          }
          catch(Exception e) {
              Thread.currentThread().interrupt();
          }            
	}
			
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub		
		
		super.onTouchEvent(event);
				
		if(event.getAction() == MotionEvent.ACTION_UP)
		{			
			//交叉线
			if(chart.getDyLineVisible())
			{
				chart.getDyLine().setCurrentXY(event.getX(),event.getY());
				if(chart.getDyLine().isInvalidate())this.invalidate();
			}
		}
		return true;
	}
	
}
