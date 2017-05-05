package com.housekeeper.ar.healthhousekeeper;

import android.app.Application;
import android.graphics.Bitmap;

import org.apache.http.client.HttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ȫ��application
 * ��Ҫ���ڳ�ʼ��AppContext
 */

public class MyApp extends Application
{


	private JSONObject joDoctor;
	JSONObject joDoc;
	Bitmap doc_head;
	private String joStartPath, http, userId, medicalRecordId;
	private String diquString, yiyuanString, keshiString, yishengString, cityString, doctorIDString;
	private ArrayList<JSONObject> jsonList = new ArrayList<JSONObject>();
	private HashMap<String,ArrayList<HashMap<String, String>> > diagnosisMap;
	ArrayList<HashMap<String, String>> prescriptionList;
	private String patientId;
	private String imageMedicalRecordId;
	private String openingScheduleID;
	JSONArray provincesJA;
	JSONArray jobTitlesJA;
	JSONArray departmentTypesJA;
	JSONArray FeeJA;
	public JSONArray getProvincesJA() {
		return provincesJA;
	}

	public void setProvincesJA(JSONArray provincesJA) {
		this.provincesJA = provincesJA;
	}

	public JSONArray getJobTitlesJA() {
		return jobTitlesJA;
	}

	public void setJobTitlesJA(JSONArray jobTitlesJA) {
		this.jobTitlesJA = jobTitlesJA;
	}

	public JSONArray getDepartmentTypesJA() {
		return departmentTypesJA;
	}

	public void setDepartmentTypesJA(JSONArray departmentTypesJA) {
		this.departmentTypesJA = departmentTypesJA;
	}



	public JSONArray getFeeJA() {
		return FeeJA;
	}

	public void setFeeJA(JSONArray feeJA) {
		FeeJA = feeJA;
	}



	public boolean isCheck_started() {
		return check_started;
	}

	public void setCheck_started(boolean check_started) {
		this.check_started = check_started;
	}

	boolean check_started;
	public String getImageMedicalRecordId() {
		return imageMedicalRecordId;
	}

	public void setImageMedicalRecordId(String imageMedicalRecordId) {
		this.imageMedicalRecordId = imageMedicalRecordId;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public ArrayList<HashMap<String, String>> getPrescriptionList() {
		return prescriptionList;
	}

	public void setPrescriptionList(
			ArrayList<HashMap<String, String>> prescriptionList) {
		this.prescriptionList = prescriptionList;
	}

	public HashMap<String, ArrayList<HashMap<String, String>>> getDiagnosisMap() {
		return diagnosisMap;
	}
	private int patient_count;
	public void setDiagnosisMap(
			HashMap<String, ArrayList<HashMap<String, String>>> diagnosisMap) {
		this.diagnosisMap = diagnosisMap;
	}

	public String getMedicalRecordId() {
		return medicalRecordId;
	}

	public void setMedicalRecordId(String medicalRecordId) {
		this.medicalRecordId = medicalRecordId;
	}

	private HttpClient httpClient;
	private static MyApp instance;
	public static MyApp getInstance(){
		return instance;
	}
	
	public HttpClient getHttpClient() {
		return httpClient;
	}
	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getHttp() {
		return http;
	}
	public void setHttp(String http) {
		this.http = http;
	}

	private int shengposition, shiposition;
	public int getShengposition() {
		return shengposition;
	}
	public void setShengposition(int shengposition) {
		this.shengposition = shengposition;
	}
	public int getShiposition() {
		return shiposition;
	}
	public void setShiposition(int shiposition) {
		this.shiposition = shiposition;
	}
	public String getJoStartPath() {
		return joStartPath;
	}
	public void setJoStartPath(String joStartPath) {
		this.joStartPath = joStartPath;
	}
	@Override
	public void onCreate()
	{
		super.onCreate();
//		setHttp("http://192.168.1.100:8080/");
		//setHttp("http://192.168.1.109:8080/");
		//setHttp("http://172.27.35.1:8080/");
		//setHttp("http://10.68.0.222:8080/");
		//TODO 后期需要更改
		setHttp("http://123.56.155.17:8080/");
		//setHttp("http://www.airclinic.com.cn:8080/");
		AppContext.init(this);
		instance = this;
		CrashHandler crashHandler = CrashHandler.getInstance();  
        crashHandler.init(getApplicationContext()); 
	}


	public int getPatient_count() {
		return patient_count;
	}

	public void setPatient_count(int patient_count) {
		this.patient_count = patient_count;
	}

	public String getOpeningScheduleID() {
		return openingScheduleID;
	}

	public void setOpeningScheduleID(String openingScheduleID) {
		this.openingScheduleID = openingScheduleID;
	}

	//判断搜索字符串string是订单号，否则是名字
	public boolean isDingDanID(String string){
		boolean result = false;


		return result;
	}

	public JSONObject getJoDoc() {
		return joDoc;
	}

	public void setJoDoc(JSONObject joDoc) {
		this.joDoc = joDoc;
	}
	public Bitmap getDoc_head() {
		return doc_head;
	}

	public void setDoc_head(Bitmap doc_head) {
		this.doc_head = doc_head;
	}


	public String getDiquString() {
		return diquString;
	}

	public void setDiquString(String diquString) {
		this.diquString = diquString;
	}

	public String getYiyuanString() {
		return yiyuanString;
	}

	public void setYiyuanString(String yiyuanString) {
		this.yiyuanString = yiyuanString;
	}

	public String getKeshiString() {
		return keshiString;
	}

	public void setKeshiString(String keshiString) {
		this.keshiString = keshiString;
	}

	public String getYishengString() {
		return yishengString;
	}

	public void setYishengString(String yishengString) {
		this.yishengString = yishengString;
	}

	public String getCityString() {
		return cityString;
	}

	public void setCityString(String cityString) {
		this.cityString = cityString;
	}

	public ArrayList<JSONObject> getJsonList() {
		return jsonList;
	}

	public void setJsonList(ArrayList<JSONObject> jsonList) {
		this.jsonList = jsonList;
	}

	public String getDoctorIDString() {
		return doctorIDString;
	}

	public void setDoctorIDString(String doctorIDString) {
		this.doctorIDString = doctorIDString;
	}

	public JSONObject getJoDoctor() {
		return joDoctor;
	}

	public void setJoDoctor(JSONObject joDoctor) {
		this.joDoctor = joDoctor;
	}
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	String token;
}