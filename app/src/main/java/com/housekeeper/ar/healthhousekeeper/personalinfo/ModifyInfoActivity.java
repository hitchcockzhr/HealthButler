package com.housekeeper.ar.healthhousekeeper.personalinfo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.housekeeper.ar.healthhousekeeper.AppContext;
import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.GuidanceBottomTabActivity;
import com.housekeeper.ar.healthhousekeeper.HttpUtil;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SaveCache;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;
import com.housekeeper.ar.healthhousekeeper.picture.PhotoUtils;
import com.housekeeper.ar.healthhousekeeper.picture.SelectPictureActivity;

import org.apache.http.client.methods.HttpPut;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ModifyInfoActivity extends BaseActivity {

	String TAG = "ModifyInfoActivity";

	private SharedPreferences sp;
	MyApp myApp;
	JSONObject joDoc;
	static String http ;//= "http://192.168.1.54:8080/";
	static String loginUrl = "shlc/doctor/login";
	static String startUrl = "shlc/initData";
	static String httpUrl;
	private String showStr = "";
	static String regUrl = "shlc/doctor/user";
	private EditText usernameET, nameET, idET,workIdEditText;
	private EditText  mailET, telET;
	private Button regBtn, photoBtn, signBtn;
	private static String photoAddr, signAddr;
	private Spinner year, month, day, ks,  sex, sheng, shi, yy, pro;
	private ImageView photo;
	private RelativeLayout firstPageLayout;
	private TextView titleTextView;
//	private EditText addressEdit;
	private ToastCommom toastCommom;
    Context context;
	private String[] arrProvinces, citiesSpinner, hospitalSpinner, nameProvinces, nameCities,
			nameHospitals, nameDepartments, namePros;
	private int[] idDepartments, idJobTitles;
	private int idDepartmentInt, idJobTitlesInt;
	private static String[][] arrCities;
	private static String[][][] arrHospitals;
	private static ArrayAdapter<String> shiAdapter, hospitalAdapter, departmentAdapter;
	private static JSONObject[] joProvinces;
	private static JSONObject[] joCities;
	private static JSONObject[] joHospitals, joDepartments, joPros;
	private static JSONObject joDepartmentType;
	private static JSONArray jaProvinces, jaCities, jaHospitals, jaDepartments, jaPros;
	private SaveCache saveCache ;
	private String readFileCache;
	private JSONObject joReadFileCache;
	int default_year_pos, default_month_pos, default_day_pos, default_sex_pos;
	String proStr;
    private String shengStr, shiStr, yyStr;
    private String ksStr, yearStr, monthStr, dayStr, sexStr;
    private String birthdayStr,default_birth;

    private Button backBtn;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
//		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_info);
        context = getApplicationContext();
		MyActivityManager.pushOneActivity(ModifyInfoActivity.this);
		myApp = (MyApp)getApplication();
		http = myApp.getHttp();
		joDoc = myApp.getJoDoc();
        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

		Log.i(TAG, "doc info:"+joDoc.toString());
		toastCommom = ToastCommom.createToastConfig();
		saveCache = new SaveCache(AppContext.get());
		String fileName = myApp.getJoStartPath();
		Log.i(TAG, "fileName:"+fileName);

		try {
			readFileCache = saveCache.read(fileName);
			joReadFileCache = new JSONObject(readFileCache);
			String result = joReadFileCache.getString("result");
			String resultMessage = joReadFileCache.getString("resultMessage");
			//取出全部value
			JSONObject joValue = joReadFileCache.getJSONObject("value");
			//取出provinces数组
			jaProvinces = joValue.getJSONArray("provinces");
			joProvinces = new JSONObject[jaProvinces.length()];
			nameProvinces = new String[jaProvinces.length()];
			//nameProvinces[0] = "-请选择省份-";
			for(int i=0; i<jaProvinces.length(); i++){
				joProvinces[i] = jaProvinces.getJSONObject(i);
				nameProvinces[i] = joProvinces[i].getString("name");
				Log.v(TAG, "nameProvinces:"+nameProvinces[i]);
			}

			jaPros = joValue.getJSONArray("jobTitles");
			joPros = new JSONObject[jaPros.length()];
			namePros = new String[jaPros.length()];
			idJobTitles = new int[namePros.length];
			for(int i=0; i<jaPros.length(); i++){
				joPros[i] = jaPros.getJSONObject(i);
				namePros[i] = joPros[i].getString("name");
				idJobTitles[i] = joPros[i].getInt("id");
				Log.v(TAG, "namePros:"+namePros[i]);
				Log.v(TAG, "idJobTitles:"+idJobTitles[i]);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		Log.i(TAG, "sp " + sp);

		String name = sp.getString("USER_NAME", "");
		String pwd = sp.getString("PASSWORD", "");
		JSONObject params = new JSONObject();

		//        prePageBtn = (Button)findViewById(R.id.pre_btn);
		firstPageLayout = (RelativeLayout)findViewById(R.id.first_layout);
//		nextPageBtn = (Button)findViewById(R.id.next_btn);
//		secondPageLayout = (RelativeLayout)findViewById(R.id.second_layout);

		titleTextView = (TextView)findViewById(R.id.title);

		workIdEditText = (EditText)findViewById(R.id.work_id_et);

		usernameET = (EditText)findViewById(R.id.username_et);
		nameET = (EditText)findViewById(R.id.name_et);
		idET = (EditText)findViewById(R.id.id_et);

		telET = (EditText)findViewById(R.id.tel_et);
		mailET = (EditText)findViewById(R.id.mail_et);
		photo = (ImageView)findViewById(R.id.photo_image);
		regBtn = (Button)findViewById(R.id.regbtn);
		photoBtn = (Button)findViewById(R.id.photoBtn);

		signBtn = (Button)findViewById(R.id.sign_btn);
		//birthday
		birthdayStr = yearStr+"-"+monthStr+"-"+dayStr;
		year = (Spinner)findViewById(R.id.yearspinner);
		month = (Spinner)findViewById(R.id.monthspinner);
		day = (Spinner)findViewById(R.id.dayspinner);
		//sex
		sex = (Spinner)findViewById(R.id.sex_spinner);
		//province-city-hospital
		sheng = (Spinner)findViewById(R.id.sheng_spinner);
		shi = (Spinner)findViewById(R.id.shi_spinner);
		yy = (Spinner)findViewById(R.id.yy_spinner);
		titleTextView = (TextView)findViewById(R.id.title);
		titleTextView.setText("个 人 信 息");
		regBtn.setText("完成修改");



		initData();

	}

    @Override
    protected void onResume() {
        super.onResume();
        boolean b = isWorked();
    }

    private void initData(){
        try {
            usernameET.setText(joDoc.getString("userId"));
            //psdET.setText(joDoc.getString(""));
            nameET.setText(joDoc.getString("name"));
            idET.setText(joDoc.getString("identity"));

            telET.setText(joDoc.getString("phone"));
            mailET.setText(joDoc.getString("email"));
            //TODO 后期打开
//            workIdEditText.setText(joDoc.getString("workID"));

            sexStr = joDoc.getString("sex");
            birthdayStr = joDoc.getString("birthday");
            yearStr = birthdayStr.substring(0, 4);
            monthStr = birthdayStr.substring(5, 7);
            dayStr = birthdayStr.substring(8);
            Log.i(TAG,"yearString "+yearStr+" monthString "+monthStr +" dayString "+dayStr);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final List<String> yearList = getYearData();
		final List<String> monthList = getMonthData();
		final List<String> dayList = getDayData();
        final List<String> sexList = getSexData();


		ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>
				(ModifyInfoActivity.this, R.layout.spinner_item,yearList){
			@Override
			public View getDropDownView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.spinner_item_layout,
						null);
				TextView label = (TextView) view
						.findViewById(R.id.spinner_item_label);

				label.setText(yearList.get(position));

				return view;
				//return super.getDropDownView(position, convertView, parent);
			}
		};
		ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>
				(ModifyInfoActivity.this, R.layout.spinner_item,getMonthData()){
			@Override
			public View getDropDownView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.spinner_item_layout,
						null);
				TextView label = (TextView) view
						.findViewById(R.id.spinner_item_label);

				label.setText(monthList.get(position));

				return view;
				//return super.getDropDownView(position, convertView, parent);
			}
		};
		ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>
				(ModifyInfoActivity.this, R.layout.spinner_item,getDayData()){
			@Override
			public View getDropDownView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.spinner_item_layout,
						null);
				TextView label = (TextView) view
						.findViewById(R.id.spinner_item_label);

				label.setText(dayList.get(position));

				return view;
				//return super.getDropDownView(position, convertView, parent);
			}
		};
		year.setAdapter(yearAdapter);
//		year.setSelection(default_year_pos, true);
		month.setAdapter(monthAdapter);
//		month.setSelection(default_month_pos, true);
		day.setAdapter(dayAdapter);
//		day.setSelection(default_day_pos, true);


        ArrayAdapter<String> sexAdapter = new ArrayAdapter<String>
                (ModifyInfoActivity.this, R.layout.spinner_item,getSexData()){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(sexList.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };
        sex.setAdapter(sexAdapter);
//        sex.setSelection(default_sex_pos, true);


//		ArrayAdapter<String> proAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item,namePros){
//			@Override
//			public View getDropDownView(int position, View convertView, ViewGroup parent) {
//				LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//				View view = inflater.inflate(R.layout.spinner_item_layout,
//						null);
//				TextView label = (TextView) view
//						.findViewById(R.id.spinner_item_label);
//
//				label.setText(namePros[position]);
//
//
//				return view;
//				//return super.getDropDownView(position, convertView, parent);
//			}
//		};
//		//设置样式
//
//		proAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
////		proAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		pro.setAdapter(proAdapter);
//		pro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int position, long id) {
//                // TODO Auto-generated method stub
//                proStr = parent.getItemAtPosition(position).toString();
//                Log.v(TAG, "selcet proStr:" + proStr);
//                if (proStr.equals(namePros[position])) {
//                    idJobTitlesInt = idJobTitles[position];
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//                // TODO Auto-generated method stub
//
//            }
//
//        });
//        try {
//            pro.setSelection(joDoc.getInt("jobTitleId")-1,true);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        ArrayAdapter<String> shengAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item,nameProvinces){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(nameProvinces[position]);


                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };
        //设置样式

        shengAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        sheng.setAdapter(shengAdapter);
        sheng.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> shengparent, View shengview,
                                       int shengposition, long shengid) {
                // TODO Auto-generated method stub
                myApp.setShengposition(shengposition);
                shengStr = shengparent.getItemAtPosition(shengposition).toString();
                Log.v(TAG, "shengStr:" + shengStr);
                if (shengStr.equals(nameProvinces[shengposition])) {
                    Log.v(TAG, "position:" + shengposition);
                    for (int i = 0; i < joProvinces.length; i++) {
                        try {

                            if (shengStr.equals(joProvinces[i].getString("name"))) {
                                jaCities = joProvinces[i].getJSONArray("cities");
                                joCities = new JSONObject[jaCities.length()];
                                nameCities = new String[joCities.length];

                                for (int j = 0; j < jaCities.length(); j++) {
                                    joCities[j] = jaCities.getJSONObject(j);
                                    nameCities[j] = joCities[j].getString("name");
                                }
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block

                            e.printStackTrace();
                        }
                    }
                }
                sheng.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {

                        Log.i(TAG, "year touch ");
                        closeSoftKeyboard();
                        return false;
                    }
                });
                shiAdapter = new ArrayAdapter<String>(ModifyInfoActivity.this, R.layout.spinner_item, nameCities) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View view = inflater.inflate(R.layout.spinner_item_layout,
                                null);
                        TextView label = (TextView) view
                                .findViewById(R.id.spinner_item_label);

                        label.setText(nameCities[position]);


                        return view;
                        //return super.getDropDownView(position, convertView, parent);
                    }
                };

                //设置样式
                shiAdapter.setDropDownViewResource(R.layout.spinner_item_layout);

//				shiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                shi.setAdapter(shiAdapter);

                shi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> shiparent, View shiview,
                                               int shiposition, long shiid) {
                        // TODO Auto-generated method stub
                        shiStr = shiparent.getItemAtPosition(shiposition).toString();
                        Log.v(TAG, "shiStr:" + shiStr);
                        if (shiStr.equals(nameCities[shiposition])) {
                            for (int i = 0; i < joCities.length; i++) {
                                try {
                                    if (shiStr.equals(joCities[i].getString("name"))) {
                                        jaHospitals = joCities[i].getJSONArray("hospitals");
                                        joHospitals = new JSONObject[jaHospitals.length()];
                                        nameHospitals = new String[joHospitals.length];
                                        for (int j = 0; j < jaHospitals.length(); j++) {
                                            joHospitals[j] = jaHospitals.getJSONObject(j);
                                            nameHospitals[j] = joHospitals[j].getString("name");
                                        }

                                    }
                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block

                                    e.printStackTrace();
                                }
                            }
                        }
                        shi.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {

                                Log.i(TAG, "year touch ");
                                closeSoftKeyboard();
                                return false;
                            }
                        });
                        hospitalAdapter = new ArrayAdapter<String>(ModifyInfoActivity.this, R.layout.spinner_item, nameHospitals) {
                            @Override
                            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View view = inflater.inflate(R.layout.spinner_item_layout,
                                        null);
                                TextView label = (TextView) view
                                        .findViewById(R.id.spinner_item_label);

                                label.setText(nameHospitals[position]);


                                return view;
                                //return super.getDropDownView(position, convertView, parent);
                            }
                        };

                        //设置样式
                        hospitalAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
                        yy.setAdapter(hospitalAdapter);

                        yy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> yyparent,
                                                       View yyview, int yyposition, long yyid) {
                                // TODO Auto-generated method stub
                                yyStr = yyparent.getItemAtPosition(yyposition).toString();
                                if (yyStr.equals(nameHospitals[yyposition])) {
                                    for (int i = 0; i < joHospitals.length; i++) {
                                        try {
                                            if (yyStr.equals(joHospitals[i].getString("name"))) {
                                                jaDepartments = joHospitals[i].getJSONArray("departments");
                                                joDepartments = new JSONObject[jaDepartments.length()];
                                                nameDepartments = new String[joDepartments.length];
                                                idDepartments = new int[joDepartments.length];
                                                for (int j = 0; j < jaDepartments.length(); j++) {
                                                    joDepartments[j] = jaDepartments.getJSONObject(j);
                                                    nameDepartments[j] = joDepartments[j].getString("name");
                                                    idDepartments[j] = joDepartments[j].getInt("id");

                                                }

                                            }
                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch block

                                            e.printStackTrace();
                                        }
                                    }
                                }
                                yy.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View view, MotionEvent motionEvent) {

                                        Log.i(TAG, "year touch ");
                                        closeSoftKeyboard();
                                        return false;
                                    }
                                });
//                                departmentAdapter = new ArrayAdapter<String>(ModifyInfoActivity.this, android.R.layout.simple_spinner_item, nameDepartments) {
//                                    @Override
//                                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
//                                        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                                        View view = inflater.inflate(R.layout.spinner_item_layout,
//                                                null);
//                                        TextView label = (TextView) view
//                                                .findViewById(R.id.spinner_item_label);
//
//                                        label.setText(nameDepartments[position]);
//
//
//                                        return view;
//                                        //return super.getDropDownView(position, convertView, parent);
//                                    }
//                                };
//
//                                设置样式
//                                departmentAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
//                                ks.setAdapter(departmentAdapter);
//                                try {
//                                    ks.setSelection(joDoc.getInt("departmentId") - 1, true);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                                ks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                                    @Override
//                                    public void onItemSelected(
//                                            AdapterView<?> ksparent, View ksview,
//                                            int ksposition, long ksid) {
//                                        // TODO Auto-generated method stub
//                                        ksStr = ksparent.getItemAtPosition(ksposition).toString();
//                                        if (ksStr.equals(nameDepartments[ksposition])) {
//                                            try {
//                                                idDepartmentInt = idDepartments[ksposition];
//                                                joDepartmentType = joDepartments[ksposition].getJSONObject("departmentType");
//                                                departmentTypeTv.setText(joDepartmentType.getString("name"));
//                                            } catch (JSONException e) {
//                                                // TODO Auto-generated catch block
//
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                    }
//
//
//                                    @Override
//                                    public void onNothingSelected(
//                                            AdapterView<?> arg0) {
//                                        // TODO Auto-generated method stub
//
//                                    }
//                                });
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub

                            }
                        });
//                        ks.setOnTouchListener(new View.OnTouchListener() {
//                            @Override
//                            public boolean onTouch(View view, MotionEvent motionEvent) {
//
//                                Log.i(TAG, "year touch ");
//                                closeSoftKeyboard();
//                                return false;
//                            }
//                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                yearStr = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                monthStr = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                dayStr = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                sexStr = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });






        for (int i = 0; i < sexList.size(); i++) {
            if (sexList.get(i).equals(sexStr)) {
                sex.setSelection(i, true);
                break;
            }
        }
        for (int i = 0; i < yearList.size(); i++) {
            if (yearList.get(i).equals(yearStr)) {
                year.setSelection(i, true);
                break;
            }
        }
        for (int i = 0; i < monthList.size(); i++) {
            if (monthList.get(i).equals(monthStr)) {
                month.setSelection(i, true);
                break;
            }
        }
        for (int i = 0; i < dayList.size(); i++) {
            if (dayList.get(i).equals(dayStr)) {
                day.setSelection(i, true);
                break;
            }
        }
        photoBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                picKind = 0;
//                startActivityForResult(new Intent(ModifyInfoActivity.this, SelectPictureActivity.class), REQUEST_PICK);

                Intent intent = new Intent(ModifyInfoActivity.this, SelectPictureActivity.class);
                intent.putExtra("from","modify");
                startActivityForResult(intent, REQUEST_PICK);
                //RegisterActivity.this.finish();
            }
        });

        signBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                picKind = 1;
//                startActivityForResult(new Intent(ModifyInfoActivity.this, SelectPictureActivity.class), REQUEST_PICK);
                Intent intent = new Intent(ModifyInfoActivity.this, SelectPictureActivity.class);
                intent.putExtra("from","modify");
                startActivityForResult(intent, REQUEST_PICK);
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modify();

            }
        });

    }

    private void closeSoftKeyboard(){

        /**隐藏软键盘**/
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    
	//下拉菜单数据源
	private List<String> getYearData(){
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		List<String> dataList = new ArrayList<String>();
		for(int i=year; i>=1940; i--){
			dataList.add(String.valueOf(i));
		}
		return dataList;
	}
	private List<String> getMonthData(){
		List<String> dataList = new ArrayList<String>();
		for (int i = 1; i < 13; i++) {
			if (i < 10) {
				dataList.add("0" + String.valueOf(i));
			} else {
				dataList.add(String.valueOf(i));
			}

		}
		return dataList;
//		List<String> dataList = new ArrayList<String>();
//		for(int i=1; i<13; i++){
//			dataList.add(String.valueOf(i));
//			try {
//				if(Integer.parseInt(joDoc.getString("birthday").substring(5,7))== i){
//					default_month_pos = i-1;
//				}
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//		}
//		return dataList;
	}
	private List<String> getDayData(){
		List<String> dataList = new ArrayList<String>();
		for(int i=1; i<32; i++){
			if(i<10){
				dataList.add("0"+String.valueOf(i));
			}else{
				dataList.add(String.valueOf(i));
			}
//			dataList.add(String.valueOf(i));
		}
		return dataList;
//		List<String> dataList = new ArrayList<String>();
//		for(int i=1; i<32; i++){
//			dataList.add(String.valueOf(i));
//			try {
//				if(Integer.parseInt(joDoc.getString("birthday").substring(8))== i){
//					default_day_pos = i-1;
//				}
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//		}
//		return dataList;
	}
	private List<String> getSexData(){
		List<String> dataList = new ArrayList<String>();

		dataList.add("男");
		dataList.add("女");
        try {
            if(joDoc.getString("sex").equals("男")){
                default_sex_pos = 0;
            }else{
                default_sex_pos = 1;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dataList;
	}
    private ArrayList<String>selectedPicture = new ArrayList<String>();
    String upload_pic_url = "shlc/photo";
    private static final String IMAGE_FILE_NAME = "tempImage.jpg";
    private static final int REQUEST_PICK = 0;
    private int picKind;
    @SuppressWarnings("unchecked")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            selectedPicture = (ArrayList<String>) data
                    .getSerializableExtra(SelectPictureActivity.INTENT_SELECTED_PICTURE);
            Log.i(TAG, "SelectedPicture:" + selectedPicture.toString());
            if(selectedPicture.size()>0){
                //只取第一张
                for(int i=0; i<1; i++){
                    Bitmap bitmap = PhotoUtils.getimage(selectedPicture.get(i));
                    File file = null;
                    JSONObject joRev = new JSONObject();
                    httpUrl = http+ upload_pic_url;
                    Log.i(TAG, "post reg image url:"+ httpUrl);
                    if(PhotoUtils.saveBitmap2file(bitmap)){
                        file = new File(Environment.getExternalStorageDirectory(),IMAGE_FILE_NAME);
                        joRev = HttpUtil.postImage(file, httpUrl);
                        Log.i(TAG, "post image:"+joRev.toString());
                        try {
                            if(joRev.getLong("result")==200){
                                String returnAddr = joRev.getString("value");
                                switch (picKind){
                                    case 0:
                                        //头像
                                        Bitmap head_bitmap = bitmap;
                                        photo.setImageBitmap(head_bitmap);
                                        photoAddr = returnAddr;
                                        break;
                                    case 1:

                                        break;


                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private JSONObject params = new JSONObject();
    String resultStr;
    //class RegisterThread extends Thread{
   //     public void run() {
			/*
			if (android.os.Build.VERSION.SDK_INT > 9) {
			    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			    StrictMode.setThreadPolicy(policy);
			}
			*/
	public void modify(){
            try {
				if (usernameET.getText().toString().equals("")) {
//					Toast.makeText(RegisterActivity.this, "用户名为空",
//							Toast.LENGTH_LONG).show();
					toastCommom.ToastShow(ModifyInfoActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "用户名为空");
					return;
				}

				if (nameET.getText().toString().equals("")) {
//					Toast.makeText(RegisterActivity.this, "姓名为空",
//							Toast.LENGTH_LONG).show();
					toastCommom.ToastShow(ModifyInfoActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "姓名为空");
					return;
				}
				if (idET.getText().toString().equals("")) {
					Toast.makeText(ModifyInfoActivity.this, "身份证不能为空", Toast.LENGTH_LONG).show();
					return;
				}
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Log.i(TAG,"birthdayStr "+birthdayStr);
				try {
//					birthdayStr= "1983-07-02 00:00:00";
					String time = birthdayStr+" 00:00:00";
					Log.i(TAG,"time "+time);
					Date date = format.parse(time);
					Log.i(TAG,"出生日期格式正确 "+date.toString());
				} catch (ParseException e) {
					Toast.makeText(ModifyInfoActivity.this, "出生日期格式不正确", Toast.LENGTH_LONG).show();
					e.printStackTrace();
					Log.i(TAG,"出生日期格式不正确");
					return;
				}

                params.put("userId", usernameET.getText().toString());//str
                params.put("name", nameET.getText().toString());//str
                params.put("sex", sexStr);//str
				params.put("workId", workIdEditText.getText().toString());
                birthdayStr = yearStr+"-"+monthStr+"-"+dayStr;
                params.put("birthday", birthdayStr.contains("null")?joDoc.getString("birthday"):birthdayStr);//str
                params.put("identity", idET.getText().toString());//str
//                params.put("jobTitleId", idJobTitlesInt);//int
//                params.put("licenseNO", yszET.getText().toString());//str
//                params.put("licenseNOPic", zyysAddr==null?joDoc.getString("licenseNOPic"):zyysAddr);//str
//                params.put("certificateNo", zczET.getText().toString());//str
//                params.put("certificateNoPic", yszcAddr==null?joDoc.getString("certificateNoPic"):yszcAddr);//str
//                params.put("jobTitleNoPic", zcpsAddr==null?joDoc.getString("jobTitleNoPic"):zcpsAddr);//str
//                params.put("jobTitleNo", zcpsET.getText().toString());//str
                params.put("province", shengStr);
                params.put("city", shiStr);
                params.put("hospital", yyStr);
                params.put("phone", telET.getText().toString());//str
                params.put("email", mailET.getText().toString());//str
//                params.put("departmentId", idDepartmentInt);//int
                //params.put("departmentType", ksflStr);
//                params.put("skill", skillET.getText().toString());//str
//                params.put("description", detailET.getText().toString());//str
                //params.put("titlePicture", "picture Url");
                params.put("photoPic", photoAddr==null?joDoc.getString("photoPic"):photoAddr);//str
//                params.put("meetPlace", addressEdit.getText().toString());

                Log.i(TAG, "params:" + params.toString());
                httpUrl = http + regUrl;
                Log.i(TAG, "httpUrl:" + httpUrl);//str
                HttpPut put = HttpUtil.getPut(httpUrl, params);
                JSONObject joRev = HttpUtil.getString(put);
                //HttpPost post = HttpUtil.getPost(httpUrl, params);
               // JSONObject joRev = HttpUtil.getString(post, 3);
                showStr = joRev.getString("result");
                resultStr = joRev.getString("resultMessage");
            } catch (JSONException e) {
                // TODO Auto-generated catch block

                e.printStackTrace();
            }finally{

            }
            //handler.sendMessage(msg);
            if(showStr.equals("200")){
                Log.d(TAG, "resultStr:"+resultStr);
//				Toast.makeText(RegisterActivity.this, "已注册成功！",
//						Toast.LENGTH_LONG).show();
                toastCommom.ToastShow(ModifyInfoActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "已修改成功！");
                Intent intent = new Intent(ModifyInfoActivity.this , GuidanceBottomTabActivity.class);
                startActivity(intent);
                //pDialog.dismiss();
                ModifyInfoActivity.this.finish();
            }else{
//				Toast.makeText(RegisterActivity.this, resultStr,
//						Toast.LENGTH_LONG).show();
                toastCommom.ToastShow(ModifyInfoActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), resultStr+ " 修改失败");
            }

    }
    public boolean isWorked() {
        ActivityManager myManager=(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(30);
        for(int i = 0 ; i<runningService.size();i++) {
            Log.i(TAG,"service name:  "+runningService.get(i).service.getClassName().toString() );
            if(runningService.get(i).service.getClassName().toString().equals("com.cn.ar.doctorclient.service.CheckUserService")) {
                Log.i(TAG, "check service is running");
                return true;
            }
        }
        Log.i(TAG, "check service is not running");
        return false;
    }
}
