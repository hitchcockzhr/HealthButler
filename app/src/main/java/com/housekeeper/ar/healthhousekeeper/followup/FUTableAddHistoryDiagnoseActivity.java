package com.housekeeper.ar.healthhousekeeper.followup;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.FlowLayout;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.MyDialog;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by lenovo on 2016/3/5.
 */
//随访表-添加既往诊断

public class FUTableAddHistoryDiagnoseActivity extends BaseActivity {

    private String TAG="FUTableAddHistoryDiagnoseActivity";
    private Button btn;
    //	private GridView historyDiagnosisGridView;
//	private HistoryDiagnosisGridItemBaseAdapter historyDiagnosisGridItemBaseAdapter;
    ArrayList<HashMap<String, String>> historyDiagnosisLists = new ArrayList<HashMap<String, String>>();

    MyApp myApp;
    private String medicalRecordUpdateUrl = "shlc/doctor/diagnosis?medicalRecordId=";
    private String getDiseaseFromPinyinUrl = "shlc/doctor/diseases?pinyin=";
    private String medicalRecordUrl = "shlc/doctor/diseases?medicalRecordId=";
//    private long currentStatus = Status.MedicalRecordStatus.EVALUATED.getValue();
    String getDiagnosisString = "shlc/doctor/diagnosis?medicalRecordId=";
    String getMedicalString = "shlc/doctor/medicalRecord/";
    private String medicalRecordStatusUrl = "shlc/doctor/medicalRecords/status/";
    private String http;
    private static String httpUrl;
    public ListView currentDiagnosisListView;
    public ArrayList<HashMap<String, String>> currentDiagnosisList= new ArrayList<HashMap<String, String>>();//当前诊断列表
    ArrayList<HashMap<String, String>> diagnosisList, prescriptionList;
    public ArrayList<HashMap<String, String>> searchlist= new ArrayList<HashMap<String, String>>();  //一次搜索到的诊断列表

    FUTableAddHistoryDiagnosisItemAdapter currentDiagnosisItemAdapter;
    Button addBtn,dialogCancelBtn,dialogSearchBtn, okBtn,dialogOkBtn,clearBtn;
    MyDialog dialog;
    EditText dialogEditText;
    private JSONArray jaDiseases;
    private JSONObject[] joDiseases;
    private ListView currentDiseasesListView;
    private GridView diseasesGridView;
    //	private GridView selectDiseasesGridView;
//    String medicalRecordId;
    private JSONArray jaMedicalRecords;
    private JSONObject[] joMedicalRecords;
    JSONArray jaDiagnosis, jaPrescription;
    JSONObject[] joDiagnosises, joPrescriptions;
    private HashMap<String,ArrayList<HashMap<String, String>> > diagnosisMap = new HashMap<String, ArrayList<HashMap<String, String>>>();

    //存储选中的诊断位置（保存一次搜索searchlist与searchDieaseAllLists的对应关系，方便删除）
    //selectPosition是在当前搜索列表中的位置，searchPosition是在多次搜索结果列表searchDieaseAllLists中的位置
    private ArrayList<HashMap<String, Integer>> selectedCurrentDieasePositionLists= new ArrayList<HashMap<String, Integer>>();;
    //存储选中的诊断名称（多次搜索的结果合集）
    public ArrayList<HashMap<String, String>> searchDieaseAllLists= new ArrayList<HashMap<String, String>>();


    //历史诊断选择按钮
    private Button historySelectBtn;
    //历史诊断全选按钮
    private Button historySelectAllBtn;
    //选中的历史诊断列表
    private ArrayList<HashMap<String, String>> historySelectedDiagnosisLists = new ArrayList<HashMap<String, String>>();
    private Button backBtn;

    private FlowLayout mHistoryFlowLayout;


    //已选的诊断
    private FlowLayout mSelectedDiagnosisFlowLayout;

    private ToastCommom toastCommom;

    //常用诊断选择按钮
    private Button commonSelectBtn;
    //常用诊断全选按钮
    private Button commonSelectAllBtn;
    //选中的常用诊断列表
    private ArrayList<HashMap<String, String>> commonSelectedDiagnosisLists = new ArrayList<HashMap<String, String>>();

    private FlowLayout mCommonFlowLayout;

    ArrayList<HashMap<String, String>> commonDiagnosisLists = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//		 requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_table_add_history_diagnosis);
        MyActivityManager.pushOneActivity(FUTableAddHistoryDiagnoseActivity.this);
//		historyDiagnosisGridView = (GridView)findViewById(R.id.history_diagnosis_gridview);
        myApp = (MyApp)getApplication();
        http = myApp.getHttp();
//        medicalRecordId = myApp.getMedicalRecordId();
//        Log.v(TAG, "medicalRecordId:" + medicalRecordId);
        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toastCommom = ToastCommom.createToastConfig();
        //////////////////////////////////////历史诊断//////////////////////////////////////////////////////////////////////


        loadMedicalRecord();
        loadCommonDiagnose();
        loadCurrentDiagnosis();


        historySelectBtn = (Button)findViewById(R.id.history_diagnosis_select_btn);
        historySelectAllBtn = (Button)findViewById(R.id.history_diagnosis_all_btn);

        historySelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "historySelectedDiagnosisLists " + historySelectedDiagnosisLists);
                if (historySelectedDiagnosisLists != null && historySelectedDiagnosisLists.size() > 0) {
//					currentDiagnosisList.addAll(historySelectedDiagnosisLists);

                    //将所有的选择状态设置为未选中，恢复绿框黑字
                    for (HashMap<String, String> map : historyDiagnosisLists) {
                        map.put("selected", "false");
                    }
                    initHistoryChildViews();
                    for (HashMap<String, String> map : historySelectedDiagnosisLists) {
                        //检查该诊断是否已经存在在当前诊断列表中

                        if (currentDiagnosisList != null) {
                            currentDiagnosisList.add(map);

                            currentDiagnosisList = new ArrayList<HashMap<String, String>>(new HashSet<HashMap<String, String>>(currentDiagnosisList));
                        }
                    }
                    Log.i(TAG, "currentDiagnosisList:" + currentDiagnosisList.toString());
                    currentDiagnosisItemAdapter.refresh(currentDiagnosisList);
                    currentDiagnosisItemAdapter.notifyDataSetChanged();
                    historySelectedDiagnosisLists.clear();

                } else {
                    Log.i(TAG, "historySelectedDiagnosisLists null " + historySelectedDiagnosisLists);
                    toastCommom.ToastShow(FUTableAddHistoryDiagnoseActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "请选择历史诊断");
                }
            }
        });
        historySelectAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (historyDiagnosisLists != null && historyDiagnosisLists.size() > 0) {
                    for (HashMap<String, String> map : historyDiagnosisLists) {
                        //将所有的选择状态设置为未选中
                        map.put("selected", "false");
                        //检查该诊断是否已经存在在当前诊断列表中
                        if (currentDiagnosisList != null) {
                            currentDiagnosisList.add(map);
                            currentDiagnosisList = new ArrayList<HashMap<String, String>>(new HashSet<HashMap<String, String>>(currentDiagnosisList));
                        }
                    }
                    initHistoryChildViews();
                    currentDiagnosisItemAdapter.refresh(currentDiagnosisList);
                    historySelectedDiagnosisLists.clear();
                    currentDiagnosisItemAdapter.notifyDataSetChanged();
                } else {
                    toastCommom.ToastShow(FUTableAddHistoryDiagnoseActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "无历史诊断");
                }
            }
        });


        commonSelectBtn = (Button)findViewById(R.id.common_diagnosis_select_btn);
        commonSelectAllBtn = (Button)findViewById(R.id.common_diagnosis_all_btn);

        commonSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"commonSelectedDiagnosisLists "+commonSelectedDiagnosisLists);
                if(commonSelectedDiagnosisLists != null && commonSelectedDiagnosisLists.size() > 0){
//					currentDiagnosisList.addAll(historySelectedDiagnosisLists);

                    //将所有的选择状态设置为未选中，恢复绿框黑字
                    for(HashMap<String,String> map:commonDiagnosisLists) {
                        map.put("selected", "false");
                    }
                    initCommonChildViews();
                    for(HashMap<String,String> map:commonSelectedDiagnosisLists){
                        //检查该诊断是否已经存在在当前诊断列表中

                        if(currentDiagnosisList != null ) {
                            currentDiagnosisList.add(map);

                            currentDiagnosisList = new ArrayList<HashMap<String, String>>(new HashSet<HashMap<String, String>>(currentDiagnosisList));
                        }
                    }
                    Log.i(TAG,"currentDiagnosisList:"+currentDiagnosisList.toString());
                    currentDiagnosisItemAdapter.refresh(currentDiagnosisList);
                    currentDiagnosisItemAdapter.notifyDataSetChanged();
                    commonSelectedDiagnosisLists.clear();

                }else{
                    Log.i(TAG, "commonSelectedDiagnosisLists null " + commonSelectedDiagnosisLists);
                    toastCommom.ToastShow(FUTableAddHistoryDiagnoseActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "请选择常用诊断");
                }
            }
        });
        commonSelectAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(commonDiagnosisLists != null && commonDiagnosisLists.size() > 0){
                    for(HashMap<String,String> map:commonDiagnosisLists) {
                        //将所有的选择状态设置为未选中
                        map.put("selected", "false");
                        //检查该诊断是否已经存在在当前诊断列表中
                        if (currentDiagnosisList != null ) {
                            currentDiagnosisList.add(map);
                            currentDiagnosisList = new ArrayList<HashMap<String, String>>(new HashSet<HashMap<String, String>>(currentDiagnosisList));
                        }
                    }
                    initCommonChildViews();
                    currentDiagnosisItemAdapter.refresh(currentDiagnosisList);
                    commonSelectedDiagnosisLists.clear();
                    currentDiagnosisItemAdapter.notifyDataSetChanged();
                }else{
                    toastCommom.ToastShow(FUTableAddHistoryDiagnoseActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "无常用诊断");
                }
            }
        });
        ////////////////////////////////////////当前诊断///////////////////////////////////////////////////////////////

//		myApp = (MyApp)getApplication();
//		medicalRecordId = myApp.getMedicalRecordId();
        /*
		diagnosisMap = myApp.getDiagnosisMap();
		//Log.v(TAG, "diagnosisMap:"+diagnosisMap.toString());
		if(diagnosisMap != null && diagnosisMap.size() > 0 ){

			if(diagnosisMap.get(medicalRecordId)!=null){
				currentDiagnosisList = diagnosisMap.get(medicalRecordId);
				Log.v(TAG, "get myList:"+currentDiagnosisList.toString());
			}else{
				currentDiagnosisList= new ArrayList<HashMap<String, String>>();
			}
		}else{
			diagnosisMap = new HashMap<String,ArrayList<HashMap<String, String>> >();
		}
		loadCurrentDiagnosis();
		*/
    }



    private void loadMedicalRecord(){

        historyDiagnosisLists = new ArrayList<HashMap<String, String>>();
        for(int j=0; j<5; j++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("diagnose", "测试"+j);
            map.put("id", "1");
            map.put("selectBtn", "选择");
            map.put("selected", "false");
            historyDiagnosisLists.add(map);
        }
        historyDiagnosisLists = new ArrayList<HashMap<String, String>>(new HashSet<HashMap<String, String>>(historyDiagnosisLists));
        initHistoryChildViews();
    }


    //初始化历史诊断的流式布局
    private void initHistoryChildViews() {
        // TODO Auto-generated method stub
        mHistoryFlowLayout = (FlowLayout) findViewById(R.id.history_flowlayout);
        Log.i(TAG,"mHistoryFlowLayout "+mHistoryFlowLayout);
        if(mHistoryFlowLayout.getChildCount() > 0){
            mHistoryFlowLayout.removeAllViews();
        }
//		mHistoryFlowLayout.removeAllViews();
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        if(historyDiagnosisLists == null || historyDiagnosisLists.isEmpty()){
            return;
        }
        Log.i(TAG, "historyDiagnosisLists.size() " + historyDiagnosisLists.size());
        for(int i = 0; i < historyDiagnosisLists.size(); i ++){
            final TextView view = new TextView(this);
            final HashMap<String,String> map = historyDiagnosisLists.get(i);
            view.setText(map.get("diagnose"));
            view.setTextColor(Color.BLACK);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_flow_shape_unpressed));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "view flow onclick");
                    if(map.get("selected").equals("false")){
                        historySelectedDiagnosisLists.add(map);
                        map.put("selected", "true");
                        view.setTextColor(Color.WHITE);
                        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_flow_shape_pressed));
                    }else{
                        historySelectedDiagnosisLists.remove(map);
                        map.put("selected", "false");
                        view.setTextColor(Color.BLACK);
                        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_flow_shape_unpressed));
                    }
                }
            });
            mHistoryFlowLayout.addView(view,lp);
        }
    }
    //加载常用诊断
    private void loadCommonDiagnose(){

        commonDiagnosisLists = new ArrayList<HashMap<String, String>>();
//        for(int j=0; j<5; j++){
//            HashMap<String, String> map = new HashMap<String, String>();
//            map.put("diagnose", "糖尿病"+j);
//            map.put("id", "1");
//            map.put("selectBtn", "选择");
//            map.put("selected", "false");
//            commonDiagnosisLists.add(map);
//        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("diagnose", "糖尿病");
        map.put("id", "1");
        map.put("selectBtn", "选择");
        map.put("selected", "false");
        commonDiagnosisLists.add(map);

        HashMap<String, String> map2 = new HashMap<String, String>();
        map2.put("diagnose", "冠心病");
        map2.put("id", "1");
        map2.put("selectBtn", "选择");
        map2.put("selected", "false");
        commonDiagnosisLists.add(map2);

        HashMap<String, String> map3 = new HashMap<String, String>();
        map3.put("diagnose", "脑卒中");
        map3.put("id", "1");
        map3.put("selectBtn", "选择");
        map3.put("selected", "false");
        commonDiagnosisLists.add(map3);

        HashMap<String, String> map4 = new HashMap<String, String>();
        map4.put("diagnose", "高血压");
        map4.put("id", "1");
        map4.put("selectBtn", "选择");
        map4.put("selected", "false");
        commonDiagnosisLists.add(map4);

        HashMap<String, String> map5 = new HashMap<String, String>();
        map5.put("diagnose", "血脂异常");
        map5.put("id", "1");
        map5.put("selectBtn", "选择");
        map5.put("selected", "false");
        commonDiagnosisLists.add(map5);

        commonDiagnosisLists = new ArrayList<HashMap<String, String>>(new HashSet<HashMap<String, String>>(commonDiagnosisLists));
        initCommonChildViews();
    }


    //初始化常用诊断的流式布局
    private void initCommonChildViews() {
        // TODO Auto-generated method stub
        mCommonFlowLayout = (FlowLayout) findViewById(R.id.common_flowlayout);
        Log.i(TAG,"mCommonFlowLayout "+mCommonFlowLayout);
        if(mCommonFlowLayout.getChildCount() > 0){
            mCommonFlowLayout.removeAllViews();
        }
//		mHistoryFlowLayout.removeAllViews();
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        if(commonDiagnosisLists == null || commonDiagnosisLists.isEmpty()){
            return;
        }
        Log.i(TAG, "commonDiagnosisLists.size() " + commonDiagnosisLists.size());
        for(int i = 0; i < commonDiagnosisLists.size(); i ++){
            final TextView view = new TextView(this);
            final HashMap<String,String> map = commonDiagnosisLists.get(i);
            view.setText(map.get("diagnose"));
            view.setTextColor(Color.BLACK);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_flow_shape_unpressed));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "view flow onclick");
                    if(map.get("selected").equals("false")){
                        commonSelectedDiagnosisLists.add(map);
                        map.put("selected", "true");
                        view.setTextColor(Color.WHITE);
                        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_flow_shape_pressed));
                    }else{
                        commonSelectedDiagnosisLists.remove(map);
                        map.put("selected", "false");
                        view.setTextColor(Color.BLACK);
                        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_flow_shape_unpressed));
                    }
                }
            });
            mCommonFlowLayout.addView(view,lp);
        }
    }

    //加载诊断
    private void loadCurrentDiagnosis(){
//		initCurrentDiagnosisTest();
        currentDiagnosisListView = (ListView)findViewById(R.id.current_diagnosis_listview);
        currentDiagnosisItemAdapter = new FUTableAddHistoryDiagnosisItemAdapter(FUTableAddHistoryDiagnoseActivity.this, currentDiagnosisList,
                R.layout.activity_follow_up_table_add_history_diagnosis_item, new String[]{"name","cancelBtn","layout"},
                new int[]{R.id.current_diagnosis_tv, R.id.current_diagnosis_cancel_btn,R.id.current_diagnosis_item_layout});
        currentDiagnosisListView.setAdapter(currentDiagnosisItemAdapter);
        addBtn = (Button) findViewById(R.id.current_diagnosis_add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                addDiagnosis();
                dialog.show();

            }
        });

        okBtn = (Button)findViewById(R.id.current_diagnosis_ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                //medicalRecordUpdateUrl

                Intent intent = new Intent();
                Bundle bundle = new Bundle();

                bundle.putParcelableArrayList("diagnose", (ArrayList) currentDiagnosisList);
                intent.putExtras(bundle);
                //设置返回数据
                setResult(RESULT_OK, intent);

                finish();

            }
        });
        clearBtn = (Button)findViewById(R.id.current_diagnosis_delete_all_btn);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentDiagnosisList.clear();
                currentDiagnosisItemAdapter.notifyDataSetChanged();
            }
        });


    }

    //增加诊断
    private void addDiagnosis(){
        LayoutInflater inflater =getLayoutInflater();
        final View functionListView = inflater.inflate(R.layout.activity_follow_up_table_add_history_diagnosis_dialog, null);
//		dialog = new AlertDialog.Builder(DiagnosisActivity.this, R.style.load_dialog).setView(functionListView).show();

        dialog = new MyDialog(FUTableAddHistoryDiagnoseActivity.this, functionListView,R.style.load_dialog);
        int width = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
//		int height = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);



        searchDieaseAllLists = currentDiagnosisList;
        if (selectedCurrentDieasePositionLists != null && !selectedCurrentDieasePositionLists.isEmpty()){
            selectedCurrentDieasePositionLists.clear();
        }
        Log.i(TAG,"addDiagnosis 初始化 searchDieaseAllLists "+searchDieaseAllLists.size());
//		if (searchDieaseAllLists != null && !searchDieaseAllLists.isEmpty()){
//			searchDieaseAllLists.clear();
//		}
//		initSelectedDiagnosisList();
        initSelectedDiagnosisChildView();
        dialogSearchBtn = (Button) functionListView.findViewById(R.id.search_btn);
        dialogOkBtn = (Button)functionListView.findViewById(R.id.ok_btn);
        dialogCancelBtn = (Button)functionListView.findViewById(R.id.cancel_btn);
        dialogEditText = (EditText)functionListView.findViewById(R.id.add_diagnosis_et);
        //dialogEditText.setInputType(InputType.TYPE_NULL);
        dialogCancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        //搜索诊断
        dialogSearchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String pyStr = dialogEditText.getText().toString().trim();
                if(searchlist != null && !searchlist.isEmpty()){

                    searchlist.clear();
                    gridAdapter.notifyDataSetChanged();
                }
                if(pyStr == null || pyStr.equals("")){
//					Toast.makeText(DiagnosisActivity.this, "输入为空", Toast.LENGTH_LONG).show();
                    toastCommom.ToastShow(FUTableAddHistoryDiagnoseActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "输入为空");
                    return;
                }
                if(selectedCurrentDieasePositionLists != null &&  !selectedCurrentDieasePositionLists.isEmpty()){
                    selectedCurrentDieasePositionLists.clear();
                }
                getDiseasesFromPinyin(pyStr);
                loadDiseases(dialog);
            }
        });
        //添加所选中的疾病
        dialogOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//				if (searchDieaseAllLists!= null && !searchDieaseAllLists.isEmpty()){
                updateDiagnosisBySelect();


//				}
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);

    }

    //使用拼音搜索病名
    private void getDiseasesFromPinyin(String pyStr){
        myApp = (MyApp)getApplication();
        http = myApp.getHttp();
        JSONObject joRev = new JSONObject();

        //jaDiseases = null;
//        try {
//            Log.v(TAG, "addDiagnosis pyStr="+pyStr);
//            Log.v(TAG, "addDiagnosis url="+ http+getDiseaseFromPinyinUrl);
//            joRev = HttpUtil.getResultForHttpGet(http+getDiseaseFromPinyinUrl+pyStr.toUpperCase());
//            Log.v(TAG, "addDiagnosis joRevMessage:"+joRev.toString());
//            String resultStr = joRev.getString("result");
//            if(resultStr.equals("200")){
//                jaDiseases = joRev.getJSONArray("value");
//                joDiseases = new JSONObject[jaDiseases.length()];
//                for(int i=0; i<jaDiseases.length(); i++){
//                    joDiseases[i] = jaDiseases.getJSONObject(i);
//                    Log.v(TAG, "addDiagnosis joDiseases"+joDiseases[i].toString());
//                }
//
//            }
//        } catch (ClientProtocolException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }



    }
    DiseasesGridItemBaseAdapter gridAdapter;
    //	SelectDiseasesGridItemBaseAdapter selectDiseasesGridItemBaseAdapter;
    //加载病名
    private void loadDiseases(Dialog dialog){
//		currentDiseasesListView = (ListView)dialog.findViewById(R.id.diseases_listview);
        diseasesGridView = (GridView)dialog.findViewById(R.id.diseases_gridView);

        gridAdapter = new DiseasesGridItemBaseAdapter(FUTableAddHistoryDiagnoseActivity.this,
                searchlist, R.layout.activity_follow_up_table_add_history_diagnose_dialog_item,
                new String[]{"diagnose","selectBtn","layout"},
                new int[]{R.id.diseases_tv, R.id.diseases_select_check_btn,R.id.diseases_layout});
        diseasesGridView.setAdapter(gridAdapter);




        //显示结果前先清空listView
        searchlist.clear();
        gridAdapter.notifyDataSetChanged();

        ////////////////////////////////测试//////////////////////////////////////

        for(int i=0; i<10; i++){
            HashMap<String, String> map = new HashMap<String, String>();

            map.put("diagnose", "测试"+i);


            map.put("selectBtn", "选择");
            map.put("id", String.valueOf(i));
            searchlist.add(map);

        }


        ////////////////////////////////////////////////////////////////////////////

//        Log.d(TAG, "addDiagnosis loadDiseases jaDiseases " + jaDiseases);
//        if(jaDiseases == null||jaDiseases.length()==0){
//            toastCommom.ToastShow(FUTableAddHistoryDiagnoseActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "无搜索结果");
//            return;
//        }
//        for(int i=0; i<jaDiseases.length(); i++){
//            HashMap<String, String> map = new HashMap<String, String>();
//            try {
//                map.put("name", joDiseases[i].getString("name"));
//                Log.v(TAG, joDiseases[i].getString("name"));
//
//                map.put("selectBtn", "选择");
//                map.put("id", String.valueOf(joDiseases[i].getInt("id")));
//                searchlist.add(map);
//            } catch (JSONException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }


    }


    private void initSelectedDiagnosisChildView(){
        mSelectedDiagnosisFlowLayout = (FlowLayout)dialog.findViewById(R.id.selcected_current_diagnosis_flowlayout);
        if(mSelectedDiagnosisFlowLayout.getChildCount() > 0){
            mSelectedDiagnosisFlowLayout.removeAllViews();
        }

        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        if(searchDieaseAllLists == null || searchDieaseAllLists.isEmpty()){
            return;
        }

        for(int i = 0; i < searchDieaseAllLists.size(); i++){
            final TextView view = new TextView(this);
            final HashMap<String,String> map = searchDieaseAllLists.get(i);
            view.setText(map.get("diagnose"));
            view.setTextColor(Color.BLACK);
            view.setTextSize(12);


            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_flow_shape_unpressed));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "adapter selectedCurrentDieasePositionLists view flow onclick "+map);


                    //若取消的是当前正在搜索的诊断；若取消选择的不是当前正在搜索的诊断，当选择列表中有当前搜索的内容时，selectedCurrentDieasePositionLists的searchPos - 1；
//					Log.i(TAG, "adapter selectedCurrentDieasePositionLists position " + i);
                    if(searchlist.contains(map)){

                        if(selectedCurrentDieasePositionLists != null && !selectedCurrentDieasePositionLists.isEmpty()){
                            for(int i=0;i<selectedCurrentDieasePositionLists.size();i++){

                                int searchPos = selectedCurrentDieasePositionLists.get(i).get("searchPosition");
                                Log.i(TAG,"adapter selectedCurrentDieasePositionLists searchPos " +searchPos);

                                //确定该view在选中列表searchDieaseAllLists中是什么位置
                                int position = searchDieaseAllLists.indexOf(map);
                                Log.i(TAG,"adapter selectedCurrentDieasePositionLists position " +position);

                                if(searchPos == position){
                                    int selectPos = selectedCurrentDieasePositionLists.get(i).get("selectPosition");
                                    Log.i(TAG, "adapter selectedCurrentDieasePositionLists selectPos " + selectPos);

//										View itemView = (View)diseasesGridView.getItemAtPosition(selectPos);
                                    LinearLayout itemView = (LinearLayout)diseasesGridView.getChildAt(selectPos);
                                    Log.i(TAG,"adapter selectedCurrentDieasePositionLists itemView " +itemView);
                                    CheckBox checkBox = (CheckBox)itemView.findViewById(R.id.diseases_select_check_btn);
                                    checkBox.setChecked(false);

//										selectedCurrentDieasePositionLists.remove(selectedCurrentDieasePositionLists.get(i));
//										gridAdapter.notifyDataSetChanged();
                                    //TODO 怎么将其中圆圈的选中状态变成未选中状态

                                    break;
                                }
                            }
                        }

                    }else{
                        if(selectedCurrentDieasePositionLists != null && !selectedCurrentDieasePositionLists.isEmpty()){
                            for(int i=0;i<selectedCurrentDieasePositionLists.size();i++) {
                                int searchPos = selectedCurrentDieasePositionLists.get(i).get("searchPosition");
                                selectedCurrentDieasePositionLists.get(i).put("searchPosition",searchPos - 1);

                            }
                        }

                        searchDieaseAllLists.remove(map);
//						selectDiseasesGridItemBaseAdapter	.notifyDataSetChanged();
                        mSelectedDiagnosisFlowLayout.removeView(v);
//						initSelectedDiagnosisChildView();
                    }
                }
            });
            mSelectedDiagnosisFlowLayout.addView(view,lp);
        }
    }
    //	private void initSelectedDiagnosisList(){
//
//		selectDiseasesGridView = (GridView)dialog.findViewById(R.id.select_diseases_gridView);
//		selectDiseasesGridItemBaseAdapter = new SelectDiseasesGridItemBaseAdapter(DiagnosisActivity.this,
//				searchDieaseAllLists, R.layout.diseases_select_item,
//				new String[]{"name","layout"},
//				new int[]{R.id.select_diseases_tv, R.id.select_diseases_item_layout});
//		selectDiseasesGridView.setAdapter(selectDiseasesGridItemBaseAdapter);
//		selectDiseasesGridItemBaseAdapter.notifyDataSetChanged();
//
//
//
//	}
//	class SelectDiseasesGridItemBaseAdapter extends BaseAdapter{
//		public class ButtonViewHolder{
//			TextView selectDiseaseName;
//
//			LinearLayout layout;
//		}
//
//		private ArrayList<HashMap<String, String>> mAppList;
//		private LayoutInflater mInflater;
//		private Context mContext;
//		private String[] keyString;
//		private int[] valueViewID;
//		private ButtonViewHolder holder;
//		//private OnHDListener mCallBack;
//		public SelectDiseasesGridItemBaseAdapter(Context c,
//										   ArrayList<HashMap<String, String>> appList,
//										   int resource,
//										   String[] from, int[] to) {
//			mAppList = appList;
//			mContext = c;
//			mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			keyString = new String[from.length];
//			valueViewID = new int[to.length];
//			System.arraycopy(from, 0, keyString, 0, from.length);
//			System.arraycopy(to, 0, valueViewID, 0, to.length);
//
//		}
//
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return mAppList.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			// TODO Auto-generated method stub
//			return mAppList.get(position);
//		}
//
//		@Override
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return position;
//		}
//
//		@Override
//		public View getView(final int position, View convertView, ViewGroup parent) {
//			if (convertView != null) {
//				holder = (ButtonViewHolder) convertView.getTag();
//			} else {
//				convertView = mInflater.inflate(R.layout.diseases_select_item, null);
//				holder = new ButtonViewHolder();
//				holder.selectDiseaseName = (TextView)convertView.findViewById(valueViewID[0]);
//
//				holder.layout = (LinearLayout)convertView.findViewById(valueViewID[1]);
//				convertView.setTag(holder);
//			}
//
//			final HashMap<String, String> appInfo = mAppList.get(position);
//			Log.i(TAG,"SelectDiseasesGridItemBaseAdapter appInfo "+appInfo);
//			if (appInfo != null) {
//				Log.i(TAG,"SelectDiseasesGridItemBaseAdapter appInfo "+appInfo.toString());
//				final String aname = (String) appInfo.get(keyString[0]);
//				holder.selectDiseaseName.setText(aname);
//
//				holder.selectDiseaseName.setOnClickListener(new View.OnClickListener() {
//					@Override
//					public void onClick(View view) {
////						selectedCurrentDieasePositionLists.remove(new Integer(position));
//						//若取消的是当前正在搜索的诊断；若取消选择的不是当前正在搜索的诊断，当选择列表中有当前搜索的内容时，selectedCurrentDieasePositionLists的searchPos - 1；
//						Log.i(TAG, "adapter selectedCurrentDieasePositionLists position " + position);
//						if(searchlist.contains(appInfo)){
//
//							if(selectedCurrentDieasePositionLists != null && !selectedCurrentDieasePositionLists.isEmpty()){
//								for(int i=0;i<selectedCurrentDieasePositionLists.size();i++){
//
//									int searchPos = selectedCurrentDieasePositionLists.get(i).get("searchPosition");
//									Log.i(TAG,"adapter selectedCurrentDieasePositionLists searchPos " +searchPos);
//									if(searchPos == position){
//										int selectPos = selectedCurrentDieasePositionLists.get(i).get("selectPosition");
//										Log.i(TAG, "adapter selectedCurrentDieasePositionLists selectPos " + selectPos);
//
////										View itemView = (View)diseasesGridView.getItemAtPosition(selectPos);
//										LinearLayout itemView = (LinearLayout)diseasesGridView.getChildAt(selectPos);
//										Log.i(TAG,"adapter selectedCurrentDieasePositionLists itemView " +itemView);
//										CheckBox checkBox = (CheckBox)itemView.findViewById(R.id.diseases_select_check_btn);
//										checkBox.setChecked(false);
//
////										selectedCurrentDieasePositionLists.remove(selectedCurrentDieasePositionLists.get(i));
////										gridAdapter.notifyDataSetChanged();
//										//TODO 怎么将其中圆圈的选中状态变成未选中状态
//
//										break;
//									}
//								}
//							}
//
//						}else{
//							if(selectedCurrentDieasePositionLists != null && !selectedCurrentDieasePositionLists.isEmpty()){
//								for(int i=0;i<selectedCurrentDieasePositionLists.size();i++) {
//									int searchPos = selectedCurrentDieasePositionLists.get(i).get("searchPosition");
//									selectedCurrentDieasePositionLists.get(i).put("searchPosition",searchPos - 1);
//
//								}
//							}
//
//							searchDieaseAllLists.remove(appInfo);
//							selectDiseasesGridItemBaseAdapter.notifyDataSetChanged();
//						}
//
//					}
//				});
//
//			}
//			return convertView;
//		}
//
//	}
    class DiseasesGridItemBaseAdapter extends BaseAdapter{
        public class ButtonViewHolder{
            TextView DiseaseName;

            CheckBox selectBtn;
            LinearLayout layout;
        }

        private ArrayList<HashMap<String, String>> mAppList;
        private LayoutInflater mInflater;
        private Context mContext;
        private String[] keyString;
        private int[] valueViewID;
        private ButtonViewHolder holder;
        //private OnHDListener mCallBack;
        public DiseasesGridItemBaseAdapter(Context c,
                                           ArrayList<HashMap<String, String>> appList,
                                           int resource,
                                           String[] from, int[] to) {
            mAppList = appList;
            mContext = c;
            mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            keyString = new String[from.length];
            valueViewID = new int[to.length];
            System.arraycopy(from, 0, keyString, 0, from.length);
            System.arraycopy(to, 0, valueViewID, 0, to.length);

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mAppList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return mAppList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView != null) {
                holder = (ButtonViewHolder) convertView.getTag();
            } else {
                convertView = mInflater.inflate(R.layout.activity_follow_up_table_add_history_diagnose_dialog_item, null);
                holder = new ButtonViewHolder();
                holder.DiseaseName = (TextView)convertView.findViewById(valueViewID[0]);
                holder.selectBtn = (CheckBox)convertView.findViewById(valueViewID[1]);
                holder.layout = (LinearLayout)convertView.findViewById(valueViewID[2]);
                convertView.setTag(holder);
            }

            final HashMap<String, String> appInfo = mAppList.get(position);
            if (appInfo != null) {
                if(searchDieaseAllLists !=null && searchDieaseAllLists.contains(appInfo)){
                    holder.selectBtn.setChecked(true);
                    int searchPos = -1;
                    for (int i =0;i<searchDieaseAllLists.size();i++){
                        if(appInfo.get("diagnose").equals(searchDieaseAllLists.get(i).get("diagnose"))){
                            searchPos = i;
                            break;
                        }
                    }
                    if (selectedCurrentDieasePositionLists == null){
                        HashMap<String,Integer> map = new HashMap<String, Integer>();
                        map.put("selectPosition",position);
                        map.put("searchPosition",searchPos);
                        selectedCurrentDieasePositionLists.add(map);
                    }else{
                        int i=0;
                        for(i=0;i<selectedCurrentDieasePositionLists.size();i++){
                            int selectPos = selectedCurrentDieasePositionLists.get(i).get("selectPosition");
                            if (selectPos == i){
                                //已经存在列表中了
                                break;
                            }
                        }
                        if (i==selectedCurrentDieasePositionLists.size()){
                            //position不存在列表中
                            HashMap<String,Integer> map = new HashMap<String, Integer>();
                            map.put("selectPosition",position);
                            map.put("searchPosition",searchPos);
                            selectedCurrentDieasePositionLists.add(map);
                        }
                    }
                }
                final String aname = (String) appInfo.get(keyString[0]);
                holder.DiseaseName.setText(aname);
//		            holder.selectBtn.setOnClickListener(new SelectButtonListener(position));
                if(position % 4 ==0 || position % 4 ==1){

//					if(position == mAppList.size()-1&&position % 4 ==0 ){
//						Log.d(TAG,"addDiagnosis position == mAppList.size()-1 "+position);
//						int width = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
//						holder.layout.setLayoutParams(new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));
//						Log.d(TAG,"addDiagnosis position == mAppList.size()-1 after");
//
//					}
                    holder.layout.setBackgroundColor(getResources().getColor(R.color.light_gray));
                }else{
                    holder.layout.setBackgroundColor(getResources().getColor(R.color.white));
                }
                holder.selectBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        Log.d(TAG,"addDiagnosis selectBtn.setOnCheckedChangeListener b "+b);
                        if(b){
                            Log.d(TAG, "addDiagnosis selectBtn.setOnCheckedChangeListener true " + position);

                            searchDieaseAllLists.add(appInfo);
                            int size = searchDieaseAllLists.size();
                            int pos = size - 1;



                            HashMap<String,Integer> map = new HashMap<String, Integer>();
                            map.put("selectPosition",position);
                            map.put("searchPosition", pos);
                            selectedCurrentDieasePositionLists.add(map);
//							selectDiseasesGridItemBaseAdapter.notifyDataSetChanged();
                            initSelectedDiagnosisChildView();
                        }else{
                            Log.d(TAG,"addDiagnosis selectBtn.setOnCheckedChangeListener false "+position);
//							selectedCurrentDieasePositionLists.remove(new Integer(position));
                            Log.d(TAG, "addDiagnosis selectBtn.setOnCheckedChangeListener selectedDieasePositionLists " + selectedCurrentDieasePositionLists);

                            for(int i=0;i<selectedCurrentDieasePositionLists.size();i++){
                                int p = selectedCurrentDieasePositionLists.get(i).get("selectPosition");
                                if(p == position){
                                    int searchPos = selectedCurrentDieasePositionLists.get(i).get("searchPosition");
                                    HashMap<String,String> m = searchDieaseAllLists.get(searchPos);
                                    searchDieaseAllLists.remove(m);

                                    selectedCurrentDieasePositionLists.remove(selectedCurrentDieasePositionLists.get(i));
                                    break;

                                }
                            }
//							selectDiseasesGridItemBaseAdapter.notifyDataSetChanged();
                            initSelectedDiagnosisChildView();
                        }


                    }
                });
            }
            return convertView;
        }


    }

    //选中疾病点击确定后更新诊断
    public void updateDiagnosisBySelect(){
//		for(int i:selectedCurrentDieasePositionLists){
//			String diseaseStr = searchlist.get(i).get("name").toString().trim();
//			String diseaseId = searchlist.get(i).get("id");
//			Log.v(TAG, "searchlist:"+searchlist.toString());
//			Log.v("diseaseStr", diseaseStr);
//			Log.v("diseaseId", diseaseId);
//			HashMap<String, String> map = new HashMap<String, String>();
//			map.put("name", diseaseStr);
//
//			map.put("cancelBtn", "删除");
//			map.put("id", diseaseId);
//			currentDiagnosisList.add(map);
//			Log.v(TAG, "update myList:" + currentDiagnosisList.toString());
//
//		}
        currentDiagnosisList = searchDieaseAllLists;
        currentDiagnosisItemAdapter.refresh(currentDiagnosisList);
        selectedCurrentDieasePositionLists.clear();
        dialog.dismiss();

    }



}

