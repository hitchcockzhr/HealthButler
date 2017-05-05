package com.housekeeper.ar.healthhousekeeper.followup;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.housekeeper.ar.healthhousekeeper.FlowLayout;
import com.housekeeper.ar.healthhousekeeper.MyDialog;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 用药情况view
 * Created by Lenovo on 2016/12/5.
 */
public class FUTableMedicineView extends LinearLayout {
    private String TAG="FUTableMedicineView";
    private Context mContext;
    private List<HashMap<String,String>> medicineData = new ArrayList<>();
//    private List<HashMap<String,String>> searchMedicineData = new ArrayList<>();

    private TextView addMedicineTV;
    private EditText medicineSearchET;

    MyDialog addMedicineDialog;
    FUTableMedicineAdapter medicineAdapter;
    ListViewForScrollView medicineListview;
    private Spinner medicineComplianceSpinner;
    private String medicineComplianceString;
    private ScrollView parentSV;
    private View view;
    private ToastCommom toastCommom;

    private int selectedMedicineIndex;
    //不良反应
    private LinearLayout adrLayout;
    private CheckBox adrCheckBox;
    private ListViewForScrollView adrListView;
    private TextView addAdrTV;
    private FUTableMedicineADRAdapter adrAdapter;
    private List<HashMap<String,String>> adrData = new ArrayList<>();

    public static int REQUEST_HISTORY_CODE = 0;
    public static int REQUEST_NEW_CODE = 1;
    public FUTableMedicineView(Context context) {
        super(context);
        mContext = context;
        view=inflate(context, R.layout.activity_follow_up_table_medicine, this);
        initView();
    }

    public FUTableMedicineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        view=inflate(context, R.layout.activity_follow_up_table_medicine, this);
        initView();
    }
    public FUTableMedicineView(Context context, ViewGroup parentView) {
        super(context);
        mContext = context;
        view=inflate(context, R.layout.activity_follow_up_table_medicine, parentView);
        initView();
    }
    public FUTableMedicineView(Context context, ViewGroup parentView,List<HashMap<String,String>> data,ScrollView sv) {
        super(context);
        mContext = context;
        view=inflate(context, R.layout.activity_follow_up_table_medicine, parentView);
        this.medicineData = data;
        this.parentSV = sv;
        initView();

    }
    private void initView(){
        Toast.makeText(mContext, "用药情况的频次用量与数量、价格之间的关系,et1等需要打开watcher吗？", Toast.LENGTH_SHORT).show();


        toastCommom = ToastCommom.createToastConfig();
        medicineComplianceSpinner = (Spinner)view.findViewById(R.id.medicine_compliance_spinner);
        initSpinner();

        adrCheckBox = (CheckBox)view.findViewById(R.id.drug_adverse_reactions_btn);
        adrLayout = (LinearLayout)view.findViewById(R.id.adr_layout);
        adrListView = (ListViewForScrollView) view.findViewById(R.id.adr_listview);
        adrAdapter = new FUTableMedicineADRAdapter(mContext,adrData);

        adrListView.setAdapter(adrAdapter);
        adrListView.setParentScrollView(parentSV);
        adrListView.setMaxHeight(500);

        addAdrTV = (TextView)view.findViewById(R.id.add_adr_tv);
        addAdrTV.setOnClickListener(clickListener);
        adrCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    adrLayout.setVisibility(View.VISIBLE);
                    initAdrData();
                }else{
                    adrLayout.setVisibility(View.GONE);
                }
            }
        });

        medicineListview = (ListViewForScrollView) view.findViewById(R.id.medicine_listview);

        medicineAdapter = new FUTableMedicineAdapter(mContext,medicineData);

        medicineListview.setAdapter(medicineAdapter);
        medicineListview.setParentScrollView(parentSV);
        medicineListview.setMaxHeight(500);

        medicineListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick");
                selectedMedicineIndex = position;
                showModifyMedicineDialog(medicineData.get(position));
            }
        });

        //TODO 药品搜索逻辑需要重新做
        medicineSearchET = (EditText)view.findViewById(R.id.medicine_search_et);
        medicineSearchET.addTextChangedListener(medicineSeartchWatcher);

        addMedicineTV = (TextView)view.findViewById(R.id.add_medicine_tv);
        addMedicineTV.setOnClickListener(clickListener);


        initData();

    }
    private void initData(){
        medicineComplianceSpinner.setSelection(0);
    }
    private void initAdrData(){
        adrData.clear();
        HashMap<String,String> map = new HashMap<>();
        map.put("drugName","阿司匹林");

        adrData.add(map);
        adrAdapter.notifyDataSetChanged();
    }
    public List<HashMap<String,String>> getMedicineData(){
        //返回medicineData还是searchMedicineData？？
        return medicineData;
    }
    public void addMedicineData(HashMap<String,String> map){

        medicineData.add(map);

        medicineAdapter.notifyDataSetChanged();
        Log.i(TAG,"medicineData size "+medicineData.size());
    }
    private HashMap<String,String> modifyMedicineMap = new HashMap<String, String>();
    //修改药单对话框
    private void showModifyMedicineDialog(final HashMap<String,String> map){
        modifyMedicineMap = map;
        final View functionListView = LayoutInflater.from(mContext).inflate(R.layout.activity_follow_up_table_modify_medicinne_dialog, null);

        final MyDialog   dialog = new MyDialog(mContext, functionListView,R.style.load_dialog);
        int width = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        Button cancelBtn = (Button)functionListView.findViewById(R.id.new_medicine_cancel_btn);

        cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        initPinCiView(functionListView, dialog, map);
        try {
            setPinCi(map);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private OnClickListener  clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.add_medicine_tv:
                    addMedicine();
                    break;

                case R.id.add_adr_tv:
                    addADR();
                    break;
                default:
                    break;
            }
        }
    };
    //添加不良反应
    MyDialog addADRDialog;
    Button addBtn,dialogCancelBtn,dialogSearchBtn, okBtn,dialogOkBtn,clearBtn;
    EditText dialogEditText;
    //存储选中的诊断位置（保存一次搜索searchlist与searchDieaseAllLists的对应关系，方便删除）
    //selectPosition是在当前搜索列表中的位置，searchPosition是在多次搜索结果列表searchDieaseAllLists中的位置
    private List<HashMap<String, Integer>> selectedCurrentDieasePositionLists= new ArrayList<HashMap<String, Integer>>();;
    //存储选中的诊断名称（多次搜索的结果合集）
    public List<HashMap<String, String>> searchDieaseAllLists= new ArrayList<HashMap<String, String>>();
    List<HashMap<String, String>> diagnosisList, prescriptionList;
    public List<HashMap<String, String>> searchlist= new ArrayList<HashMap<String, String>>();  //一次搜索到的诊断列表
    //增加诊断
    private void addADR(){

        final View functionListView = LayoutInflater.from(mContext).inflate(R.layout.activity_follow_up_table_add_history_diagnosis_dialog, null);
//		dialog = new AlertDialog.Builder(DiagnosisActivity.this, R.style.load_dialog).setView(functionListView).show();

        addADRDialog = new MyDialog(mContext, functionListView,R.style.load_dialog);
        int width = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
        addADRDialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        addADRDialog.show();

        searchDieaseAllLists.clear();
        searchDieaseAllLists.addAll(adrData);
//        searchDieaseAllLists = adrData;
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
        TextView titleTV = (TextView)functionListView.findViewById(R.id.title_tv);
        titleTV.setText("添加不良反应药品");
        //dialogEditText.setInputType(InputType.TYPE_NULL);
        dialogCancelBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                addADRDialog.dismiss();
            }
        });
        //搜索诊断
        dialogSearchBtn.setOnClickListener(new OnClickListener() {

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
                    toastCommom.ToastShow(mContext, (ViewGroup) findViewById(R.id.toast_layout_root), "输入为空");
                    return;
                }
                if(selectedCurrentDieasePositionLists != null &&  !selectedCurrentDieasePositionLists.isEmpty()){
                    selectedCurrentDieasePositionLists.clear();
                }

                loadDiseases(addADRDialog);
                getDiseasesFromPinyin(pyStr);
            }
        });
        //添加所选中的疾病
        dialogOkBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//				if (searchDieaseAllLists!= null && !searchDieaseAllLists.isEmpty()){
                updateDiagnosisBySelect();


//				}
                addADRDialog.dismiss();
            }
        });
        addADRDialog.setCanceledOnTouchOutside(false);

    }

    //使用拼音搜索药名
    private void getDiseasesFromPinyin(String pyStr){
////////////////////////////////测试//////////////////////////////////////

        for(int i=0; i<10; i++){
            HashMap<String, String> map = new HashMap<String, String>();

            map.put("drugName", "测试"+i);


            map.put("selectBtn", "选择");
            map.put("id", String.valueOf(i));
            searchlist.add(map);

        }
        gridAdapter.notifyDataSetChanged();
    }
    DiseasesGridItemBaseAdapter gridAdapter;
    private GridView diseasesGridView;
    private FlowLayout mSelectedDiagnosisFlowLayout;
    //	SelectDiseasesGridItemBaseAdapter selectDiseasesGridItemBaseAdapter;
    //加载病名
    private void loadDiseases(Dialog dialog){
//		currentDiseasesListView = (ListView)dialog.findViewById(R.id.diseases_listview);
        diseasesGridView = (GridView)dialog.findViewById(R.id.diseases_gridView);

        gridAdapter = new DiseasesGridItemBaseAdapter(mContext,
                searchlist, R.layout.activity_follow_up_table_add_history_diagnose_dialog_item,
                new String[]{"drugName","selectBtn","layout"},
                new int[]{R.id.diseases_tv, R.id.diseases_select_check_btn,R.id.diseases_layout});
        diseasesGridView.setAdapter(gridAdapter);




        //显示结果前先清空listView
//        searchlist.clear();
//        gridAdapter.notifyDataSetChanged();





    }
    private void initSelectedDiagnosisChildView(){
        mSelectedDiagnosisFlowLayout = (FlowLayout)addADRDialog.findViewById(R.id.selcected_current_diagnosis_flowlayout);
        if(mSelectedDiagnosisFlowLayout.getChildCount() > 0){
            mSelectedDiagnosisFlowLayout.removeAllViews();
        }

        MarginLayoutParams lp = new MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        if(searchDieaseAllLists == null || searchDieaseAllLists.isEmpty()){
            return;
        }

        for(int i = 0; i < searchDieaseAllLists.size(); i++){
            final TextView view = new TextView(mContext);
            final HashMap<String,String> map = searchDieaseAllLists.get(i);
            view.setText(map.get("drugName"));
            view.setTextColor(Color.BLACK);
            view.setTextSize(12);


            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_flow_shape_unpressed));
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "adapter selectedCurrentDieasePositionLists view flow onclick " + map);


                    //若取消的是当前正在搜索的诊断；若取消选择的不是当前正在搜索的诊断，当选择列表中有当前搜索的内容时，selectedCurrentDieasePositionLists的searchPos - 1；
//					Log.i(TAG, "adapter selectedCurrentDieasePositionLists position " + i);
                    if (searchlist.contains(map)) {

                        if (selectedCurrentDieasePositionLists != null && !selectedCurrentDieasePositionLists.isEmpty()) {
                            for (int i = 0; i < selectedCurrentDieasePositionLists.size(); i++) {

                                int searchPos = selectedCurrentDieasePositionLists.get(i).get("searchPosition");
                                Log.i(TAG, "adapter selectedCurrentDieasePositionLists searchPos " + searchPos);

                                //确定该view在选中列表searchDieaseAllLists中是什么位置
                                int position = searchDieaseAllLists.indexOf(map);
                                Log.i(TAG, "adapter selectedCurrentDieasePositionLists position " + position);

                                if (searchPos == position) {
                                    int selectPos = selectedCurrentDieasePositionLists.get(i).get("selectPosition");
                                    Log.i(TAG, "adapter selectedCurrentDieasePositionLists selectPos " + selectPos);

//										View itemView = (View)diseasesGridView.getItemAtPosition(selectPos);
                                    LinearLayout itemView = (LinearLayout) diseasesGridView.getChildAt(selectPos);
                                    Log.i(TAG, "adapter selectedCurrentDieasePositionLists itemView " + itemView);
                                    CheckBox checkBox = (CheckBox) itemView.findViewById(R.id.diseases_select_check_btn);
                                    checkBox.setChecked(false);

//										selectedCurrentDieasePositionLists.remove(selectedCurrentDieasePositionLists.get(i));
//										gridAdapter.notifyDataSetChanged();
                                    //TODO 怎么将其中圆圈的选中状态变成未选中状态

                                    break;
                                }
                            }
                        }

                    } else {
                        if (selectedCurrentDieasePositionLists != null && !selectedCurrentDieasePositionLists.isEmpty()) {
                            for (int i = 0; i < selectedCurrentDieasePositionLists.size(); i++) {
                                int searchPos = selectedCurrentDieasePositionLists.get(i).get("searchPosition");
                                selectedCurrentDieasePositionLists.get(i).put("searchPosition", searchPos - 1);

                            }
                        }

                        searchDieaseAllLists.remove(map);
//						selectDiseasesGridItemBaseAdapter	.notifyDataSetChanged();
                        mSelectedDiagnosisFlowLayout.removeView(v);
//						initSelectedDiagnosisChildView();
                    }
                }
            });
            mSelectedDiagnosisFlowLayout.addView(view, lp);
        }
    }
    class DiseasesGridItemBaseAdapter extends BaseAdapter {
        public class ButtonViewHolder{
            TextView DiseaseName;

            CheckBox selectBtn;
            LinearLayout layout;
        }

        private List<HashMap<String, String>> mAppList;
        private LayoutInflater mInflater;
        private Context mContext;
        private String[] keyString;
        private int[] valueViewID;
        private ButtonViewHolder holder;
        //private OnHDListener mCallBack;
        public DiseasesGridItemBaseAdapter(Context c,
                                           List<HashMap<String, String>> appList,
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
                        if(appInfo.get("drugName").equals(searchDieaseAllLists.get(i).get("drugName"))){
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
        adrData.clear();
        adrData.addAll(searchDieaseAllLists);
        adrAdapter.refresh(adrData);
        adrAdapter.notifyDataSetChanged();
        selectedCurrentDieasePositionLists.clear();
        addADRDialog.dismiss();

    }
    //药品
    private void addMedicine(){
        final View functionListView = LayoutInflater.from(mContext).inflate(R.layout.activity_follow_up_table_add_medicine_dialog, null);

        addMedicineDialog = new MyDialog(mContext, functionListView,R.style.load_dialog);
        int width = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
//		int height = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
        addMedicineDialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button newPresBtn = (Button)functionListView.findViewById(R.id.new_prescription_btn);
        Button historyPresBtn = (Button)functionListView.findViewById(R.id.history_prescription_btn);
        Button clearPresBtn = (Button)functionListView.findViewById(R.id.clear_btn);

        newPresBtn.setOnClickListener(medicineDialogClickListener);
        historyPresBtn.setOnClickListener(medicineDialogClickListener);
        clearPresBtn.setOnClickListener(medicineDialogClickListener);

        Button dialogOkBtn = (Button)functionListView.findViewById(R.id.ok_btn);
        Button dialogCancelBtn = (Button)functionListView.findViewById(R.id.cancel_btn);

        dialogCancelBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                addMedicineDialog.dismiss();
            }
        });

        //添加所选中的检查
        dialogOkBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                addMedicineDialog.dismiss();
            }
        });
        addMedicineDialog.setCanceledOnTouchOutside(false);
        addMedicineDialog.show();
    }
    private OnClickListener medicineDialogClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.new_prescription_btn:
                    Intent intent = new Intent(mContext,FUTableNewMedicineActivity.class);
                    Activity activity = (Activity)mContext;
                    activity.startActivityForResult(intent, REQUEST_NEW_CODE);
                    addMedicineDialog.dismiss();
                    break;
                case R.id.history_prescription_btn:
                    Intent intent2 = new Intent(mContext,FUTableHistoryMedicineActivity.class);
                    Activity activity2 = (Activity)mContext;
                    activity2.startActivityForResult(intent2, REQUEST_HISTORY_CODE);
                    addMedicineDialog.dismiss();
                    break;
                case R.id.clear_btn:

//                    searchMedicineData.clear();
                    medicineData.clear();
                    medicineAdapter.notifyDataSetChanged();
                    addMedicineDialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    };

    private TextWatcher medicineSeartchWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(s == null || s.toString().equals("")){
//                searchMedicineData.clear();
//                searchMedicineData.addAll(medicineData);
                medicineAdapter.notifyDataSetChanged();
            }else{
                searchByName(s.toString());
                medicineAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private void searchByName(String name){
//        searchMedicineData.clear();
//        for(HashMap<String,String> map:medicineData){
//            if(map.get("name").contains(name)){
//                searchMedicineData.add(map);
//            }
//        }
    }
    private void initSpinner(){
        final List<String> medicineComList = getMedicineComData();
        ArrayAdapter<String> medicineComAdapter = new ArrayAdapter<String>
                (mContext, R.layout.spinner_item,getMedicineComData()){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(medicineComList.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };

        //设置样式
        medicineComAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        medicineComplianceSpinner.setAdapter(medicineComAdapter);

        medicineComplianceSpinner.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.i(TAG, "服药依从性 touch ");
                closeSoftKeyboard();
                return false;
            }
        });

        medicineComplianceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                medicineComplianceString = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }
    //获取服药依从性
    private List<String> getMedicineComData(){
        List<String> dataList = new ArrayList<String>();

        dataList.add("规律");
        dataList.add("不规律");

        return dataList;
    }
    private void closeSoftKeyboard(){

        /**隐藏软键盘**/
        Activity activity = (Activity)mContext;
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private TextView productTV;
    private TextView guigeTV;
    private EditText shuliangET;
    private EditText jiageET;
    private LinearLayout pinciLayout;
    //	private LinearLayout cishuLayout;
    private TextView tianshuET;
    private Spinner pinciSpinner;
    private Spinner caseSpinner;

    String frequencyTypeStr;
    String cishuStr;
    String frequencyStr;
    String pinciStr;
    int ciPerDay;
    private EditText et1;
    private EditText et2;
    private EditText et3;
    private Button saveBtn;
    private String reason;



    private void  initPinCiView(View view,final MyDialog dialog,final HashMap<String,String> map){

        productTV = (TextView)view.findViewById(R.id.selected_new_medicine_product_tv);
        guigeTV = (TextView)view.findViewById(R.id.selected_new_medicine_guige_tv);
        saveBtn = (Button)view.findViewById(R.id.new_medicine_ok_btn);

        pinciLayout = (LinearLayout)view.findViewById(R.id.pinci_edit);
//		cishuLayout = (LinearLayout)findViewById(R.id.cishu_edit);
        tianshuET = (EditText)view.findViewById(R.id.drugs_tianshu_et);
        shuliangET = (EditText)view.findViewById(R.id.drugs_shuangliang_et);
        pinciSpinner = (Spinner)view.findViewById(R.id.pinci_spinner);
        caseSpinner = (Spinner)view.findViewById(R.id.special_case_spinner);
        jiageET = (EditText)view.findViewById(R.id.drugs_jiage_et);


        final List<String> datas = getReasonData();
        ArrayAdapter<String> caseAdapter = new ArrayAdapter<String>
                (mContext, R.layout.spinner_item,datas){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(datas.get(position));

                return view;
            }
        };
//		//设置样式
        caseAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        caseSpinner.setAdapter(caseAdapter);

        caseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                reason = datas.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final List<String> fees = getPinCiData();
        ArrayAdapter<String> pinciAdapter = new ArrayAdapter<String>
                (mContext, R.layout.spinner_item,fees){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(fees.get(position));

                return view;
            }
        };
        //设置样式
        pinciAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        pinciSpinner.setAdapter(pinciAdapter);

        pinciSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                pinciStr = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "medicineFragment pinciStr " + pinciStr);
                if (pinciStr.equals("TID")) {

                    if (pinciLayout.getChildCount() == 3) {

                        // e1、e2、e3已经存在
                        return;
                    }
                    ciPerDay = 3;
                    cishuStr = String.valueOf(ciPerDay);

                    et1 = new EditText(mContext);
                    et2 = new EditText(mContext);
                    et3 = new EditText(mContext);
                    et1.setLayoutParams(new ViewGroup.LayoutParams(200, LayoutParams.WRAP_CONTENT));
                    et2.setLayoutParams(new ViewGroup.LayoutParams(200, LayoutParams.WRAP_CONTENT));
                    et3.setLayoutParams(new ViewGroup.LayoutParams(200, LayoutParams.WRAP_CONTENT));

                    et1.setEnabled(true);
                    et2.setEnabled(true);
                    et3.setEnabled(true);
                    Log.d(TAG, "medicineFragment pinciStr after" + pinciStr);
                    et1.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    et2.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    et3.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);

                    et1.setBackgroundResource(R.drawable.buttonstyle);
                    et2.setBackgroundResource(R.drawable.buttonstyle);
                    et3.setBackgroundResource(R.drawable.buttonstyle);

                    et1.setSingleLine();
                    et2.setSingleLine();
                    et3.setSingleLine();

                    et1.setTextSize(12);
                    et1.setGravity(Gravity.CENTER);
                    et2.setTextSize(12);
                    et2.setGravity(Gravity.CENTER);
                    et3.setTextSize(12);
                    et3.setGravity(Gravity.CENTER);

                    et1.setText("1");
                    et2.setText("1");
                    et3.setText("1");
                    shuliangET.setText("3");
                    tianshuET.setText("1");



//                    et1.addTextChangedListener(watcher);
//                    et2.addTextChangedListener(watcher);
//                    et3.addTextChangedListener(watcher);
                    et1.setSelectAllOnFocus(true);
                    et2.setSelectAllOnFocus(true);
                    et3.setSelectAllOnFocus(true);

                    if (pinciLayout.getChildCount() > 0) {
                        Log.i(TAG, "pinciLayout.getChildCount() > 0");
                        pinciLayout.removeAllViews();
                    }
                    pinciLayout.addView(et1);
                    pinciLayout.addView(et2);
                    pinciLayout.addView(et3);
                    pinciLayout.requestFocus();

                } else if (pinciStr.equals("QD")) {

                    if (pinciLayout.getChildCount() == 1) {

                        // e1已经存在
                        return;
                    }
                    ciPerDay = 1;

                    cishuStr = String.valueOf(ciPerDay);

                    et1 = new EditText(mContext);
                    et1.setLayoutParams(new ViewGroup.LayoutParams(200, LayoutParams.WRAP_CONTENT));
                    et1.setEnabled(true);
                    et1.setTextSize(12);
                    et1.setGravity(Gravity.CENTER);

                    et1.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    et1.setSingleLine();
                    et1.setBackgroundResource(R.drawable.buttonstyle);

                    et1.setText("1");

                    shuliangET.setText("1");
                    tianshuET.setText("1");


//                    et1.addTextChangedListener(watcher);
                    et1.setSelectAllOnFocus(true);

                    if (pinciLayout.getChildCount() > 0) {
                        pinciLayout.removeAllViews();
                    }
                    pinciLayout.addView(et1);
                    pinciLayout.requestFocus();

                } else if (pinciStr.equals("BID")) {
                    Log.i(TAG, " modify BID init click");
                    if (pinciLayout.getChildCount() == 2) {

                        // e1、e2已经存在
                        return;
                    }
                    ciPerDay = 2;
                    cishuStr = String.valueOf(ciPerDay);


                    et1 = new EditText(mContext);
                    et2 = new EditText(mContext);
                    et1.setLayoutParams(new ViewGroup.LayoutParams(200, LayoutParams.WRAP_CONTENT));
                    et2.setLayoutParams(new ViewGroup.LayoutParams(200, LayoutParams.WRAP_CONTENT));

                    et1.setEnabled(true);
                    et2.setEnabled(true);

                    et1.setTextSize(12);
                    et1.setGravity(Gravity.CENTER);
                    et2.setTextSize(12);
                    et2.setGravity(Gravity.CENTER);

                    et1.setBackgroundResource(R.drawable.buttonstyle);
                    et2.setBackgroundResource(R.drawable.buttonstyle);

                    et1.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    et2.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);

                    et1.setSingleLine();
                    et2.setSingleLine();

                    et1.setText("1");
                    et2.setText("1");

                    shuliangET.setText("2");
                    tianshuET.setText("1");



                    Log.i(TAG, "init pinci et2 " + et2.getText().toString());

//                    et1.addTextChangedListener(watcher);
//                    et2.addTextChangedListener(watcher);

                    et1.setSelectAllOnFocus(true);
                    et2.setSelectAllOnFocus(true);

                    if (pinciLayout.getChildCount() > 0) {
                        pinciLayout.removeAllViews();
                    }
//					pinciLayout.removeAllViews();
                    pinciLayout.addView(et1);
                    pinciLayout.addView(et2);

                    pinciLayout.requestFocus();
//					pinciLayout.invalidate();
//					modifyDialog.setContentView(modifyLayout);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        saveBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (pinciStr.equals("TID")) {
                    frequencyStr = et1.getText() + "," + et2.getText() + "," + et3.getText();
                    Log.v(TAG, "medicineFragment FrequencyType:" + pinciStr + " Frequency:" + frequencyStr);
                } else if (pinciStr.equals("QD")) {
                    frequencyStr = et1.getText().toString() + "";
                    Log.v(TAG, "medicineFragment FrequencyType:" + pinciStr + " Frequency:" + frequencyStr);
                } else if (pinciStr.equals("BID")) {
                    frequencyStr = et1.getText() + "," + et2.getText();
                    Log.v(TAG, "medicineFragment FrequencyType:" + pinciStr + " Frequency:" + frequencyStr);
                } else {

                    toastCommom.ToastShow(mContext, (ViewGroup) findViewById(R.id.toast_layout_root), "请选择频次");
                    return;
                }
                Log.i(TAG, "medicineFragment freqOkBtn et1 " + et1 + " " + et1.getText().toString());
                Log.i(TAG, "medicineFragment freqOkBtn pinciStr " + pinciStr);

                if (pinciStr.equals("QD") && et1.getText().toString().equals("")) {


                    toastCommom.ToastShow(mContext, (ViewGroup) findViewById(R.id.toast_layout_root), "每天用量不能为空");
                    return;
                } else if (pinciStr.equals("BID") && (et1.getText().toString().equals("") || et2.getText().toString().equals(""))) {


                    toastCommom.ToastShow(mContext, (ViewGroup) findViewById(R.id.toast_layout_root), "每天用量不能为空");
                    return;
                } else if (pinciStr.equals("TID") && (et1.getText().toString().equals("") || et2.getText().toString().equals("") || et3.getText().toString().equals(""))) {


                    toastCommom.ToastShow(mContext, (ViewGroup) findViewById(R.id.toast_layout_root), "每天用量不能为空");
                    return;
                }
                Log.i(TAG, "medicineFragment freqOkBtn 11 " + frequencyStr + " cishuStr " + cishuStr);

                if (frequencyStr.equals("") || tianshuET.getText().toString().equals("") || cishuStr.equals("")) {

                    toastCommom.ToastShow(mContext, (ViewGroup) findViewById(R.id.toast_layout_root), "数据不能为空");
                    return;
                }
                if (shuliangET.getText().toString().equals("0") || shuliangET.getText().toString().equals("")) {

                    toastCommom.ToastShow(mContext, (ViewGroup) findViewById(R.id.toast_layout_root), "数量不能为空");
                    return;
                }
                if (jiageET.getText().toString().equals("0") || jiageET.getText().toString().equals("")) {
                    toastCommom.ToastShow(mContext, (ViewGroup) findViewById(R.id.toast_layout_root), "价格不能为空");
                    return;
                }
                if (false) {
                    //TODO 如果数量大于库存
                    showNotEnoughMedineDialog(map);
                    return;
                }

                //将要修改的内容放到map中，其他内容不变
                map.put("cishu", cishuStr);
                map.put("tianshu", tianshuET.getText().toString());
                map.put("shuliang", shuliangET.getText().toString());
                map.put("frequency", frequencyStr);
                map.put("jiage", jiageET.getText().toString());
                map.put("frequencyType", pinciStr);
                map.put("reason", reason);
                setMedicineData(map);

                toastCommom.ToastShow(mContext, (ViewGroup) findViewById(R.id.toast_layout_root), "修改成功");
                Log.i(TAG, "修改处方 map " + map);

                dialog.dismiss();


            }

        });
    }
    //修改处方列表的数据
    private void setMedicineData(HashMap<String,String> modifyMap){
        HashMap<String,String> map = medicineData.get(selectedMedicineIndex);
        map.put("cishu", modifyMap.get("cishu"));
        map.put("tianshu", modifyMap.get("tianshu"));
        map.put("shuliang", modifyMap.get("shuliang"));
        map.put("frequency", modifyMap.get("frequency"));
//			map.put("pingjunyongliang", modifyMap.get("pingjunyongliang"));
        map.put("jiage", modifyMap.get("jiage"));
//			map.put("drugId", modifyMap.get("drugId"));
        map.put("frequencyType", modifyMap.get("frequencyType"));
//		map.put("drugJO", modifyMap.get("drugJO"));
        map.put("reason", modifyMap.get("reason"));
        medicineAdapter.notifyDataSetChanged();


    }
    private List<String> getPinCiData(){
        List<String> dataList = new ArrayList<String>();
        dataList.add("请选择频次");
        dataList.add("QD");//一天一次
        dataList.add("BID");//一天两次
        dataList.add("TID");//一天三次


        return dataList;
    }

    private List<String> getReasonData(){
        List<String> dataList = new ArrayList<String>();
        dataList.add("特殊情况说明");
        dataList.add("特殊情况说明1");
        dataList.add("特殊情况说明2");



        return dataList;
    }
    //提示库存不足对话框
    private void showNotEnoughMedineDialog(final HashMap<String,String> map){

        final View functionListView = LayoutInflater.from(mContext).inflate(R.layout.duplicate_medicine_dialog, null);


        final MyDialog   dialog = new MyDialog(mContext, functionListView,R.style.load_dialog);
        int width = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        Button okBtn = (Button)functionListView.findViewById(R.id.ok_btn);
        Button cancelBtn = (Button)functionListView.findViewById(R.id.cancel_btn);
        TextView name = (TextView)functionListView.findViewById(R.id.name_tv);
        TextView title = (TextView)functionListView.findViewById(R.id.title_tv);
        TextView medicine = (TextView)functionListView.findViewById(R.id.medicine_tv);
        name.setText("库存不足，请重新填写数量");
        title.setText("库存不足");
        medicine.setText(map.get("yizhu"));

        okBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    //设置频次
    private  void setPinCi(final HashMap<String,String> map)throws JSONException{
//        toastCommom.ToastShow(mContext, (ViewGroup) findViewById(R.id.toast_layout_root), "setPinCi函数 判断医院若是小医院，则可编辑价格，后期判断");
        Log.i(TAG, "modify map:"+ work(map));

        productTV.setText(map.get("name"));
        guigeTV.setText("测试规格");

        cishuStr = map.get("cishu");
        Log.i(TAG,"cushuStr "+cishuStr);


        //TODO 如果是校医院，可以调整价格
        if(true){
            jiageET.setEnabled(true);
            jiageET.setBackgroundResource(R.drawable.buttonstyle);
        }else{
            jiageET.setEnabled(false);
            jiageET.setBackgroundColor(getResources().getColor(R.color.Grey));
        }

        if(pinciLayout.getChildCount() > 0 ){
            pinciLayout.removeAllViews();
        }

        caseSpinner.setSelection(0, true);
        pinciSpinner.setSelection(0, true);
        shuliangET.setText("0");

        pinciStr = "";

        tianshuET.setSelectAllOnFocus(true);
//        tianshuET.addTextChangedListener(watcher);
//
//        tianshuET.addTextChangedListener(jiageWatcher);
        shuliangET.addTextChangedListener(jiageWatcher);

        shuliangET.setSelectAllOnFocus(true);
        jiageET.setSelectAllOnFocus(true);
        showDrugData(map);

    }
    //显示药品信息
    private void showDrugData(final HashMap<String,String> map) throws JSONException{
        cishuStr = map.get("cishu");
        String tianshuStr = map.get("tianshu");
        String shuliangStr = map.get("shuliang");
        String jiageStr = map.get("jiage");
        String idStr = map.get("drugId");
        frequencyStr = map.get("frequency");
        frequencyTypeStr = map.get("frequencyType");

        reason = map.get("reason");

        pinciStr = frequencyTypeStr;

        Log.i(TAG, "modify currentMedicine frequencyStr " + frequencyStr);
        Log.i(TAG, "modify currentMedicine  frequencyTypeStr " + frequencyTypeStr);
        int cishu = Integer.valueOf(cishuStr);
        Log.i(TAG, "modify currentMedicine cishu " + cishu);
        pinciSpinner.setSelection(cishu, true);

        if(frequencyTypeStr.equals("QD")){

            ciPerDay = 1;
            et1 = new EditText(mContext);
            et1.setText(frequencyStr);

            et1.setLayoutParams(new ViewGroup.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT));
            et1.setEnabled(true);
            et1.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
            et1.setSelectAllOnFocus(true);
            et1.setBackgroundResource(R.drawable.buttonstyle);

//            et1.addTextChangedListener(watcher);
//            et1.addTextChangedListener(jiageWatcher);

            et1.setSingleLine();

            et1.setTextSize(12);
            et1.setGravity(Gravity.CENTER);




            if(pinciLayout.getChildCount() > 0 ){
                pinciLayout.removeAllViews();
            }
            pinciLayout.addView(et1);
            pinciLayout.requestFocus();
        }else if(frequencyTypeStr.equals("BID")){
            String[] split = frequencyStr.split(",");
            ciPerDay = 2;
            et1 = new EditText(mContext);
            et2 = new EditText(mContext);

            et1.setLayoutParams(new ViewGroup.LayoutParams(200, LayoutParams.WRAP_CONTENT));
            et2.setLayoutParams(new ViewGroup.LayoutParams(200, LayoutParams.WRAP_CONTENT));

            et1.setEnabled(true);
            et2.setEnabled(true);

            et1.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL );
            et2.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL );

            et1.setBackgroundResource(R.drawable.buttonstyle);
            et2.setBackgroundResource(R.drawable.buttonstyle);

            et1.setSelectAllOnFocus(true);
            et2.setSelectAllOnFocus(true);

//            et1.addTextChangedListener(watcher);
//            et2.addTextChangedListener(watcher);


//            et1.addTextChangedListener(jiageWatcher);
//            et2.addTextChangedListener(jiageWatcher);

            et1.setSingleLine();
            et2.setSingleLine();

            et1.setTextSize(12);
            et1.setGravity(Gravity.CENTER);
            et2.setTextSize(12);
            et2.setGravity(Gravity.CENTER);


            et1.setText(split[0]);
            et2.setText(split[1]);


            if(pinciLayout.getChildCount() > 0 ){
                pinciLayout.removeAllViews();
            }

            pinciLayout.addView(et1);
            pinciLayout.addView(et2);

            pinciLayout.requestFocus();

        }else if(frequencyTypeStr.equals("TID")){
            String[] split = frequencyStr.split(",");

            ciPerDay = 3;
            et1 = new EditText(mContext);
            et2 = new EditText(mContext);
            et3 = new EditText(mContext);
            et1.setLayoutParams(new ViewGroup.LayoutParams(200, LayoutParams.WRAP_CONTENT));
            et2.setLayoutParams(new ViewGroup.LayoutParams(200, LayoutParams.WRAP_CONTENT));
            et3.setLayoutParams(new ViewGroup.LayoutParams(200, LayoutParams.WRAP_CONTENT));
            et1.setEnabled(true);
            et2.setEnabled(true);
            et3.setEnabled(true);

            et1.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
            et2.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
            et3.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);

            et1.setBackgroundResource(R.drawable.buttonstyle);
            et2.setBackgroundResource(R.drawable.buttonstyle);
            et3.setBackgroundResource(R.drawable.buttonstyle);

            et1.setSelectAllOnFocus(true);
            et2.setSelectAllOnFocus(true);
            et3.setSelectAllOnFocus(true);

//            et1.addTextChangedListener(watcher);
//            et2.addTextChangedListener(watcher);
//            et3.addTextChangedListener(watcher);

//            et1.addTextChangedListener(jiageWatcher);
//            et2.addTextChangedListener(jiageWatcher);
//            et3.addTextChangedListener(jiageWatcher);

            et1.setSingleLine();
            et2.setSingleLine();
            et3.setSingleLine();

            et1.setTextSize(12);
            et1.setGravity(Gravity.CENTER);
            et2.setTextSize(12);
            et2.setGravity(Gravity.CENTER);
            et3.setTextSize(12);
            et3.setGravity(Gravity.CENTER);

            et1.setText(split[0]);
            et2.setText(split[1]);
            et3.setText(split[2]);


            if(pinciLayout.getChildCount() > 0 ){
                pinciLayout.removeAllViews();
            }
            pinciLayout.addView(et1);
            pinciLayout.addView(et2);
            pinciLayout.addView(et3);
            pinciLayout.requestFocus();

        }



        int pos = 0;
        List<String> datas = getReasonData();
        for(int i = 0; i < datas.size();i++){
            if(datas.get(i).equals(reason)){
                pos = i;
                break;
            }
        }
        caseSpinner.setSelection(pos, true);

        tianshuET.setText(tianshuStr);
        shuliangET.setText(shuliangStr);
        jiageET.setText(jiageStr);

    }
    final TextWatcher jiageWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {

            Double dj = Double.valueOf(modifyMedicineMap.get("dj"));
            // TODO Auto-generated method stub
            if(pinciStr.equals("TID")&&et1 != null && et2 != null && et3 != null && !s.toString().equals("")
                    &&!et1.getText().toString().equals("")&&!et2.getText().toString().equals("")&&!et3.getText().toString().equals("")
                    &&!tianshuET.getText().toString().equals("")&&!shuliangET.getText().toString().equals("")){

                int shuliang = Integer.valueOf(shuliangET.getText().toString());



                jiageET.setText(String.valueOf(dj/100 * shuliang));


            }else if(pinciStr.equals("QD")&&et1 != null&&!s.toString().equals("")&&!et1.getText().toString().equals("")&&!tianshuET.getText().toString().equals("")&&!shuliangET.getText().toString().equals("")){

                int shuliang = Integer.valueOf(shuliangET.getText().toString());


                jiageET.setText(String.valueOf(dj/100 * shuliang));


            } else if(pinciStr.equals("BID")&&et1 != null && et2 != null && !s.toString().equals("")
                    &&!et1.getText().toString().equals("")&&!et2.getText().toString().equals("")&&!tianshuET.getText().toString().equals("")&&!shuliangET.getText().toString().equals("")){

                int shuliang = Integer.valueOf(shuliangET.getText().toString());

                jiageET.setText(String.valueOf(dj / 100 * shuliang));


            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable arg0) {
            // TODO Auto-generated method stub

        }
    };
    public static String work(HashMap<String, String> map) {
        Collection<String> c = map.values();
        Iterator it = c.iterator();
        String str = "";
        for (; it.hasNext();) {
            str+=it.next()+" ";

        }
        return str;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
