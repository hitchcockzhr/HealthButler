package com.housekeeper.ar.healthhousekeeper;

import android.content.Context;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class SaveCache {
	private Context context;
	private static final String WHOLESALE_CONV = ".txt";
	private String TAG = "SaveCache";
	File file;
	public SaveCache(Context context){
		this.context = context;
	}

	/********************************
	 **
	 * @return
	 */
	public String getCacheDir(){
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
			file= context.getExternalCacheDir();
		}else{
			file = context.getCacheDir();
		}
		return file.getAbsolutePath();
	}

	/********************************
	 *

	 */
	public String mixPath(String path, String modelTag){
		return path + "/" + modelTag;
	}

	/********************************

	 */
	public String convertUrlToFileName(String url){
		//String url = request[0] + request[1];
		BASE64Encoder base = new BASE64Encoder();
		return base.encode(url.getBytes()) + WHOLESALE_CONV;
	}

	/********************************

	 */
	public String getFilePath(String modelPath, String fileName){
		return modelPath + "/" + fileName;
	}

	/********************************

	 */
	public void save(String fileName, String content) throws Exception{
		File saveFile = new File(fileName);
		if (!saveFile.exists()){
			File dir = new File(saveFile.getParent());
			dir.mkdir();
			saveFile.createNewFile();
//			Log.v(TAG,"���������ļ�");
		}

		FileOutputStream outStream = new FileOutputStream(saveFile);
		outStream.write(content.getBytes());
		outStream.close();
	}

	//============================================================================
	public String saveFileCachePath(String modelTag, String url){
		String path = getCacheDir();
		String modelPath = mixPath(path, modelTag);
		String fileName = convertUrlToFileName(url);
		String fileNamePath = getFilePath(modelPath, fileName);
		return fileNamePath;
	}
	public void saveFileCache(String fileName, String content){
		//String path = getCacheDir();
		//String modelPath = mixPath(path, modelTag);
		//String fileName = convertUrlToFileName(url);
		//fileName = saveFileCachePath(modelTag, url);
		try {
			save(fileName, content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	/*******************************

	 */
	public String read(String fileName) throws Exception{
		String str = "";
		Log.v(TAG, "file:"+fileName);
		File readFile = new File(fileName);
		if (!readFile.exists()){
			return null;
		}
		FileInputStream inStream = new FileInputStream(readFile);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = -1;
		while((length = inStream.read(buffer)) != -1){
			stream.write(buffer, 0, length);
		}
		str = stream.toString();
		stream.close();
		inStream.close();
		return str;
	}
}
	
