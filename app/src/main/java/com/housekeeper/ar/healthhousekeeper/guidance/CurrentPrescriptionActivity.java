package com.housekeeper.ar.healthhousekeeper.guidance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.MyDialog;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by lenovo on 2016/3/5.
 */
//当前处方
public class CurrentPrescriptionActivity extends BaseActivity {


    String TAG = "CurrentPrescriptionActivity";

    private Button btn;

    Button OKBtn;
    MyApp myApp;
    private String medicalRecordUrl = "shlc/doctor/prescriptionTemps?medicalRecordId=";
    private String medicalRecordId;
    private String http;

    private int REQUEST_HISTORY_CODE = 0;
    private int REQUEST_NEW_CODE = 1;
    private int REQUEST_HUAYAN_CODE = 2;
    private int REQUEST_MODIFY_MEDICINE_CODE = 3;

    //药品
    public ListView currentMedicineListView;
    public ArrayList<HashMap<String, String>> currentMedicineLists = new ArrayList<HashMap<String, String>>();

    public ArrayList<HashMap<String, String>> currentSelectedMedicineLists = new ArrayList<HashMap<String, String>>();
    CurrentMedicineItemBaseAdapter currentMedicineItemBaseAdapter;


    private TextView prescriptionTextView;


    private TextView clearTextView;



    private Button deleteBtn;//删除已有的药品
    private Button searchBtn;//搜索当前药品

    //最后一个选中的药品项的位置，用于标记修改位置
    int selectedMedicineIndex = 0;
    private String httpUrl;
    JSONArray jaPrescription;
    JSONObject[] joPrescriptions;

    private ToastCommom toastCommom;
    private String patientId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(CurrentPrescriptionActivity.this);

        Toast.makeText(CurrentPrescriptionActivity.this,"测试时，搜索药品应从历史处方选择测试和在新处方选择头孢",Toast.LENGTH_LONG).show();

        patientId = getIntent().getStringExtra("patientID");
        if(patientId == null || patientId.equals("")){
            //如果没有选择病人，则提示并返回
            toastCommom.ToastShow(CurrentPrescriptionActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "请先选择患者");
            finish();
        }
        myApp = (MyApp)getApplication();
        http = myApp.getHttp();


        btn = (Button)findViewById(R.id.back_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        currentMedicineListView = (ListView)findViewById(R.id.current_medicine_list);


        Log.i(TAG, "oncreate loadCurrentMedicine before");
        getPrescriptionTemps(medicalRecordId);
        Log.i(TAG, "oncreate loadCurrentMedicine after");
        prescriptionTextView = (TextView)findViewById(R.id.prescription_tv);
        //处方
        prescriptionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentPrescriptionActivity.this, PrescriptionTab.class);
                startActivityForResult(intent, REQUEST_NEW_CODE);
            }
        });

        clearTextView = (TextView)findViewById(R.id.clear_tv);
        deleteBtn = (Button)findViewById(R.id.current_medicine_delete_btn);

        searchBtn = (Button)findViewById(R.id.current_medicine_search_btn);



        //清空
        clearTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentMedicineListView.getVisibility() == View.VISIBLE){
                    currentMedicineLists.clear();
                    currentMedicineItemBaseAdapter.notifyDataSetChanged();
                }

            }
        });




        //删除选中的当前处方
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteSelectedCurrentMedicine();

            }
        });

        //搜索具有药房的医院
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CurrentPrescriptionActivity.this,HospitalListActivity.class);
                Bundle bundle=new Bundle();

                bundle.putParcelableArrayList("drugs", (ArrayList)currentMedicineLists);
                intent.putExtras(bundle);

                startActivity(intent);

            }
        });


    }
    @Override
    protected void onResume() {
        super.onResume();
//        init();
    }


    //删除选中的当前处方
    private void deleteSelectedCurrentMedicine(){
        if(currentMedicineLists != null && !currentMedicineLists.isEmpty() && currentSelectedMedicineLists != null && !currentSelectedMedicineLists.isEmpty()){
            currentMedicineLists.removeAll(currentSelectedMedicineLists);
            currentMedicineItemBaseAdapter.notifyDataSetChanged();
            currentSelectedMedicineLists.clear();
        }else{
            toastCommom.ToastShow(CurrentPrescriptionActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "请选择处方");
        }
    }




    String getPrescriptionTempsString = "shlc/doctor/prescriptionTemps?medicalRecordId=";
    //根据订单id获取当前的已选择的临时处方
    public void getPrescriptionTemps(String medicalRecordId){
        //doctor/prescriptions?medicalRecordId=xxx

        /*  //TODO 后期实现
        httpUrl = http+getPrescriptionTempsString+medicalRecordId;
        JSONObject joRev;
        try {
            joRev = HttpUtil.getResultForHttpGet(httpUrl);
            Log.i(TAG, "QRMainActivity get prescription joRev:"+joRev.toString());
            if(joRev.getString("result").equals("200")){
                jaPrescription = joRev.getJSONArray("value");
                joPrescriptions = new JSONObject[jaPrescription.length()];
                for(int i=0; i<jaPrescription.length(); i++){
                    joPrescriptions[i] = jaPrescription.getJSONObject(i);
                    Log.i(TAG, "joPrescription:"+joPrescriptions[i].toString());
                }
                for(int i=0; i<jaPrescription.length(); i++){
                    HashMap<String, String> map = new HashMap<String, String>();

                    try {
                        map.put("zhuangtai",  String.valueOf(joPrescriptions[i].getJSONObject("drugStore").getJSONObject("drug").getInt("id")));
                        map.put("yaopin", joPrescriptions[i].getJSONObject("drugStore").getJSONObject("drug").getString("name"));
                        map.put("pingjunyongliang",String.valueOf(joPrescriptions[i].getJSONObject("drugStore").getJSONObject("drug").getString("mcyl")));
                        map.put("guige",String.valueOf(joPrescriptions[i].getJSONObject("drugStore").getJSONObject("drug").getString("gg")));
                        map.put("drugId", String.valueOf(joPrescriptions[i].getJSONObject("drugStore").getJSONObject("drug").getInt("id")));

                        map.put("danjia", String.valueOf(TimeStamp.get2Double(joPrescriptions[i].getJSONObject("drugStore").getInt("dj")/1000.00)));

                        map.put("drugJO", joPrescriptions[i].getJSONObject("drugStore").getJSONObject("drug").toString());
                        map.put("description", joPrescriptions[i].getString("description"));
                        map.put("cancelBtn","删除");
                        map.put("selected","false");
                        currentMedicineLists.add(map);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    */

        ////////////测试///////////////////////////////////////////////////////////////////////////////////

//        HashMap<String,String> map = new HashMap<>();
//        map.put("name", "测试药品名");
//        map.put("guige","3ml/支");
//        map.put("drugId","1");
//        map.put("dj", "15");
//        map.put("cancelBtn","删除");
//        map.put("selected","false");
//        currentMedicineLists.add(map);
//
//
//
//        HashMap<String,String> map2 = new HashMap<>();
//        map2.put("name", "测试药品名2");
//        map2.put("guige","10mg/袋");
//        map2.put("drugId","1");
//        map2.put("dj", "15");
//        map2.put("cancelBtn","删除");
//        map2.put("selected","false");
//        currentMedicineLists.add(map2);


        //////////////////////////////////////////////////////////////////////////////////////////////////

        currentMedicineItemBaseAdapter = new CurrentMedicineItemBaseAdapter(CurrentPrescriptionActivity.this,
                currentMedicineLists, R.layout.prescription_title_1, new String[]{"xuhao","name","guige","dj","layout"},
                new int[]{R.id.xuhao_tv, R.id.yaopin_tv,R.id.guige_tv,R.id.danjia_tv,R.id.current_medicine_item_layout});
        currentMedicineListView.setAdapter(currentMedicineItemBaseAdapter);

        currentMedicineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final HashMap<String, String> map = (HashMap<String, String>) parent.getItemAtPosition(position);

                Log.i(TAG,"currentSelectedMedicineLists medicine list select map "+map);
                if (map.get("selected").equals("false")) {
                    Log.i(TAG,"currentSelectedMedicineLists medicine list select false ");
                    map.put("selected", "true");
                    currentSelectedMedicineLists.add(map);
                    //最后一个选中的项的位置，用于标记修改位置
                    selectedMedicineIndex = position;
                } else {
                    Log.i(TAG,"currentSelectedMedicineLists medicine list select true ");
                    currentSelectedMedicineLists.remove(map);
                    map.put("selected", "false");
                }

                Log.i(TAG,"currentSelectedMedicineLists list select currentSelectedMedicineLists "+currentSelectedMedicineLists);

                currentMedicineItemBaseAdapter.notifyDataSetChanged();


            }
        });



    }



    class CurrentMedicineItemBaseAdapter extends BaseAdapter {
        private class ButtonViewHolder{
            TextView xuhao;
            TextView yaopinTV;
            TextView guigeTV;
            TextView danjiaTV;
            LinearLayout layout;
        }

        private ArrayList<HashMap<String, String>> mAppList;
        private LayoutInflater mInflater;
        private Context mContext;
        private String[] keyString;
        private int[] valueViewID;
        private ButtonViewHolder holder;
        //private OnHDListener mCallBack;
        public CurrentMedicineItemBaseAdapter(Context c,
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
        public void refresh( ArrayList<HashMap<String, String>> mAppList)   {
            this.mAppList = mAppList;
            Log.v("CMIBA", mAppList.toString());
            notifyDataSetChanged();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView != null) {
                holder = (ButtonViewHolder) convertView.getTag();
            } else {
                convertView = mInflater.inflate(R.layout.prescription_title_1, null);
                holder = new ButtonViewHolder();
                holder.xuhao = (TextView)convertView.findViewById(valueViewID[0]);
                holder.yaopinTV = (TextView)convertView.findViewById(valueViewID[1]);
                holder.guigeTV = (TextView)convertView.findViewById(valueViewID[2]);
                holder.danjiaTV = (TextView)convertView.findViewById(valueViewID[3]);

                holder.layout = (LinearLayout)convertView.findViewById(valueViewID[4]);
                convertView.setTag(holder);
            }

            HashMap<String, String> appInfo = mAppList.get(position);
            if (appInfo != null) {
//				String str0 = (String) appInfo.get(keyString[0]);
//
//				holder.zhuangtaiTV.setText(str0);
                int pos = position+1;
                holder.xuhao.setText("" + pos);
                String str1 = (String) appInfo.get(keyString[1]);
                holder.yaopinTV.setText(str1);
                String str2 = (String) appInfo.get(keyString[2]);

                holder.guigeTV.setText(str2);


                String str3 = (String) appInfo.get(keyString[3]);
                holder.danjiaTV.setText(str3);


//				if(position % 2 == 0){
//					holder.layout.setBackgroundColor(getResources().getColor(R.color.white));
//				}else{
//
//					holder.layout.setBackgroundColor(getResources().getColor(R.color.light_gray));
//				}

                if(appInfo.get("selected").equals("true")){
                    //如果选中则将字体变成白色，背景变成绿色
                    holder.layout.setBackgroundColor(getResources().getColor(R.color.background_green));
                    holder.xuhao.setTextColor(getResources().getColor(R.color.white));
                    holder.yaopinTV.setTextColor(getResources().getColor(R.color.white));
                    holder.guigeTV.setTextColor(getResources().getColor(R.color.white));
                    holder.danjiaTV.setTextColor(getResources().getColor(R.color.white));
                }else{
                    //如果没选中，则字体是黑色，背景是白色

                    holder.layout.setBackgroundColor(getResources().getColor(R.color.white));
                    holder.xuhao.setTextColor(getResources().getColor(R.color.black));
                    holder.yaopinTV.setTextColor(getResources().getColor(R.color.black));
                    holder.guigeTV.setTextColor(getResources().getColor(R.color.black));
                    holder.danjiaTV.setTextColor(getResources().getColor(R.color.black));
                }
            }
            return convertView;
        }

        public void removeItem(int position){
            mAppList.remove(position);
            this.notifyDataSetChanged();
        }

    }

    /**
     * 为了得到传回的数据，必须在前面的Activity中（指MainActivity类）重写onActivityResult方法
     *
     * requestCode 请求码，即调用startActivityForResult()传递过去的值
     * resultCode 结果码，结果码用于标识返回数据来自哪个新Activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		String result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
		Log.i(TAG, "onActivityResult data "+data);
        Log.i(TAG, "onActivityResult requestCode "+requestCode);
        Log.i(TAG, "onActivityResult resultCode "+resultCode);

        if(resultCode == RESULT_OK){
            ///测试activity间传递list
            Bundle bundle=data.getExtras();

            ArrayList list2 = bundle.getParcelableArrayList("list");


            if(list2 != null && !list2.isEmpty()){

                Log.i(TAG, "onActivityResult测试activity间传递list list2 " + list2);
                for(int i=0;i<list2.size();i++)
                {
                    HashMap<String ,String> map=(HashMap<String,String>)list2.get(i);
                    Log.i(TAG, "onActivityResult测试activity间传递list map " + map);
                    if(currentMedicineLists != null && !currentMedicineLists.isEmpty()){
//						if(currentMedicineLists.contains(map)){
                        if(isDuplicateMedicineName(map)){

                            //弹出对话框，当前列表中已经存在该医嘱,用contains比较还是for循环比较yizhu
//                            showDuplicateMedicineDialog(map);
                            toastCommom.ToastShow(CurrentPrescriptionActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root),map.get("name")+"已经存在");

                        }else{
                            currentMedicineLists.add(map);
                        }
                    }else{
                        currentMedicineLists.add(map);
                    }

                }
            }

            currentMedicineItemBaseAdapter.notifyDataSetChanged();
        }

//        if(requestCode == REQUEST_NEW_CODE && resultCode == RESULT_OK){
//            ///测试activity间传递list
//            Bundle bundle=data.getExtras();
//
//            ArrayList list2 = bundle.getParcelableArrayList("new");
//
//
//            if(list2 != null && !list2.isEmpty()){
//
//                Log.i("GXF", "onActivityResult测试activity间传递list list2 " + list2);
//                for(int i=0;i<list2.size();i++)
//                {
//                    HashMap<String ,String> map=(HashMap<String,String>)list2.get(i);
//                    Log.i("GXF", "onActivityResult测试activity间传递list map " + map);
//                    if(currentMedicineLists != null && !currentMedicineLists.isEmpty()){
////						if(currentMedicineLists.contains(map)){
//                        if (isDuplicateMedicineName(map)){
//                            //弹出对话框，当前列表中已经存在该医嘱
//                            showDuplicateMedicineDialog(map);
//
//                        }else{
//                            currentMedicineLists.add(map);
//                        }
//                    }else{
//                        currentMedicineLists.add(map);
//                    }
//
//                }
//            }
//
//            currentMedicineItemBaseAdapter.notifyDataSetChanged();
//        }
    }

    //判断是否有重复的医嘱
    private boolean isDuplicateMedicineName( HashMap<String,String> map1){
        boolean isDup = false;
        for(HashMap<String,String>m:currentMedicineLists){
            if(m.get("name").equals(map1.get("name"))){
                isDup = true;
                break;
            }
        }

        return isDup;
    }

    //重复处方对话框
    private void showDuplicateMedicineDialog(final HashMap<String,String> map){
        LayoutInflater inflater =getLayoutInflater();
        final View functionListView = inflater.inflate(R.layout.duplicate_medicine_dialog, null);
//		dialog = new AlertDialog.Builder(DiagnosisActivity.this, R.style.load_dialog).setView(functionListView).show();

        final MyDialog dialog = new MyDialog(CurrentPrescriptionActivity.this, functionListView,R.style.load_dialog);
        int width = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
//		int height = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        Button okBtn = (Button)functionListView.findViewById(R.id.ok_btn);
        Button cancelBtn = (Button)functionListView.findViewById(R.id.cancel_btn);
        TextView name = (TextView)functionListView.findViewById(R.id.medicine_tv);
        name.setText(map.get("name"));

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentMedicineLists.add(map);
                currentMedicineItemBaseAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }



    public static String work(HashMap<String, String> map) {
        Collection<String> c = map.values();
        Iterator it = c.iterator();
        String str = "";
        for (; it.hasNext();) {
            str+=it.next()+" ";

        }
        return str;
    }





}
