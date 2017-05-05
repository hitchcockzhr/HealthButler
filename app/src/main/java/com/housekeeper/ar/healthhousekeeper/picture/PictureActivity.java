package com.housekeeper.ar.healthhousekeeper.picture;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.HttpUtil;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class PictureActivity extends BaseActivity {
	/* 组件 */
	private RelativeLayout switchAvatar;
	private ImageView faceImage;
	private String TAG = "PictureActivity";
	private String postMessageUrl = "shlc/doctor/message/medicalRecord/";
	String TYPE_ONE = "?type=INSPECTION", TYPE_TWO="?type=SORE", TYPE_THREE = "?type=PRESCRIPTION";
	private String[] items = new String[] { "选择本地图片", "拍照" };
	/* 头像名称 */
	private static final String IMAGE_FILE_NAME = "faceImage.jpg";

	/* 请求码 */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	private static final int SELECT_PIC_KITKAT = 3;
	//HttpPost
	//private JSONObject params = new JSONObject();
	static String picUploadUrl = "shlc/photo";
	static String httpUrl;
	static String http ;
	private String showStr = "";
	private static String address = "";
	private String id, medicalRecordId;
	MyApp myApp;
	Context context = PictureActivity.this;
	private ToastCommom toastCommom;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);
		setContentView(R.layout.picture);
		toastCommom = ToastCommom.createToastConfig();
		MyActivityManager.pushOneActivity(PictureActivity.this);
		if (Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		myApp = (MyApp)getApplication();
		http = myApp.getHttp();
		Intent intentReceive = getIntent();
		Bundle bundle = intentReceive.getExtras();
		//if(intentReceive.hasExtra("id")){
		id = bundle.getString("id");
		medicalRecordId = String.valueOf(bundle.getInt("medicalRecordId"));
		Log.v(TAG, "id:"+id);
		Log.v(TAG, "medicalRecordId:"+medicalRecordId);
		//}
		switchAvatar = (RelativeLayout) findViewById(R.id.setsetface_iv_switch);
		faceImage = (ImageView) findViewById(R.id.setface_iv);
		// 设置事件监听
		switchAvatar.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			showDialog();
		}
	};

	/**
	 * 显示选择对话框
	 */
	private void showDialog() {

		new AlertDialog.Builder(this)
				.setTitle("上传图片")
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case 0:
								Intent intentFromGallery = new Intent();
								intentFromGallery.setType("image/*"); // 设置文件类型
								intentFromGallery
										.setAction(Intent.ACTION_GET_CONTENT);
								if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
									Log.v(TAG, "KITKAT");
									startActivityForResult(intentFromGallery,SELECT_PIC_KITKAT);
								}else{
									startActivityForResult(intentFromGallery,IMAGE_REQUEST_CODE);
								}
								break;
							case 1:

								Intent intentFromCapture = new Intent(
										MediaStore.ACTION_IMAGE_CAPTURE);
								// 判断存储卡是否可以用，可用进行存储
								if (Tools.hasSdcard()) {

									intentFromCapture.putExtra(
											MediaStore.EXTRA_OUTPUT,
											Uri.fromFile(new File(Environment
													.getExternalStorageDirectory(),
													IMAGE_FILE_NAME)));
//									Toast.makeText(PictureActivity.this, "图片已储存",
//											Toast.LENGTH_LONG).show();
									toastCommom.ToastShow(PictureActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "图片已储存");
								}else{
//									Toast.makeText(PictureActivity.this, "未找到存储卡，无法存储照片！",
//											Toast.LENGTH_LONG).show();
									toastCommom.ToastShow(PictureActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "未找到存储卡，无法存储照片！");
								}

								startActivityForResult(intentFromCapture,
										CAMERA_REQUEST_CODE);
								break;
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//结果码不等于取消时候
		if (resultCode != RESULT_CANCELED) {

			switch (requestCode) {
				case IMAGE_REQUEST_CODE:
//					Toast.makeText(PictureActivity.this, "0",
//							Toast.LENGTH_LONG).show();
					toastCommom.ToastShow(PictureActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root),"0");
					startPhotoZoom(data.getData());
					break;
				case SELECT_PIC_KITKAT:
					startPhotoZoom(data.getData());
					break;
				case CAMERA_REQUEST_CODE:
//					Toast.makeText(PictureActivity.this, "1",
//							Toast.LENGTH_LONG).show();
					toastCommom.ToastShow(PictureActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "1");
					if (Tools.hasSdcard()) {
						File tempFile = new File(
								Environment.getExternalStorageDirectory(),IMAGE_FILE_NAME);
						//Toast.makeText(PictureActivity.this, Uri.fromFile(tempFile).toString(),
						//		Toast.LENGTH_LONG).show();

						startPhotoZoom(Uri.fromFile(tempFile));
					} else {
//						Toast.makeText(PictureActivity.this, "未找到存储卡，无法存储照片！",
//								Toast.LENGTH_LONG).show();
						toastCommom.ToastShow(PictureActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "未找到存储卡，无法存储照片！");
					}

					break;
				case RESULT_REQUEST_CODE:
//					Toast.makeText(PictureActivity.this, "2",
//							Toast.LENGTH_LONG).show();
					toastCommom.ToastShow(PictureActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "2");
					if (data != null) {

						try {
							getImageToView(data);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 裁剪图片方法实现
	 *
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		if (uri == null) {
			Log.i("tag", "The uri is not exist.");
			return;
		}
		Intent intent = new Intent("com.android.camera.action.CROP");
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			String url=getPath(context,uri);
			intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
		}else{
			intent.setDataAndType(uri, "image/*");
		}
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 5);
		intent.putExtra("aspectY", 8);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 1080);
		intent.putExtra("outputY", 1920);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 2);
	}

	/**
	 * 保存裁剪之后的图片数据
	 *
	 * @param data
	 * @throws FileNotFoundException
	 * @throws JSONException
	 */
	private void getImageToView(Intent data) throws FileNotFoundException, JSONException {
		Log.v(TAG, "getImageToView YES");
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			if(saveBitmap2file(photo, "tempImage.jpg")){
				Log.v(TAG, "YES");
				File file = new File(Environment.getExternalStorageDirectory()
						, "tempImage.jpg");
				Log.v(TAG, "readed");
				if(id.equals("5")){
					Log.v(TAG, "post Image:"+"YES!");
					httpUrl = http + postMessageUrl + medicalRecordId;
					Log.v(TAG, "httpUrl:" + httpUrl);
					JSONObject joRev = HttpUtil.postImage(file, httpUrl);
					Log.v(TAG, "image rev:"+joRev.toString());
					showStr = joRev.getString("result");
					int messageId = 0;
					long lastModified = 0;
					if(showStr.equals("200")){
						JSONObject joValue = joRev.getJSONObject("value");
						messageId = joValue.getInt("id");
						lastModified = joValue.getLong("lastModified");
						Log.v(TAG, "messageId rev:"+String.valueOf(messageId));
						Log.v(TAG, "lastModified rev:"+String.valueOf(lastModified));
					}
					Uri uri = Uri.parse(medicalRecordId);
					Intent result = new Intent(null, uri);
					result.putExtra("id", messageId);
					result.putExtra("lastModified", lastModified);
					result.putExtra("bitmap", photo);
					setResult(RESULT_OK, result);
					finish();

				}else if(id.equals("6")){
					Log.v(TAG, "post Image:"+"YES!");
					httpUrl = http + postMessageUrl + medicalRecordId+TYPE_TWO;
					Log.v(TAG, "httpUrl:"+httpUrl);
					JSONObject joRev = HttpUtil.postImage(file, httpUrl);
					Log.v(TAG, "image rev:"+joRev.toString());
					showStr = joRev.getString("result");
					int messageId = 0;
					long lastModified = 0;
					if(showStr.equals("200")){
						JSONObject joValue = joRev.getJSONObject("value");
						messageId = joValue.getInt("id");
						lastModified = joValue.getLong("lastModified");
						Log.v(TAG, "messageId rev:"+String.valueOf(messageId));
						Log.v(TAG, "lastModified rev:"+String.valueOf(lastModified));
					}
					Uri uri = Uri.parse(medicalRecordId);
					Intent result = new Intent(null, uri);
					result.putExtra("id", messageId);
					result.putExtra("lastModified", lastModified);
					result.putExtra("bitmap", photo);
					setResult(RESULT_OK, result);
					finish();

				}else if(id.equals("7")){
					Log.v(TAG, "post Image:"+"YES!");
					httpUrl = http + postMessageUrl + medicalRecordId+TYPE_THREE;
					Log.v(TAG, "httpUrl:"+httpUrl);
					JSONObject joRev = HttpUtil.postImage(file, httpUrl);
					Log.v(TAG, "image rev:"+joRev.toString());
					showStr = joRev.getString("result");
					int messageId = 0;
					long lastModified = 0;
					if(showStr.equals("200")){
						JSONObject joValue = joRev.getJSONObject("value");
						messageId = joValue.getInt("id");
						lastModified = joValue.getLong("lastModified");
						Log.v(TAG, "messageId rev:"+String.valueOf(messageId));
						Log.v(TAG, "lastModified rev:"+String.valueOf(lastModified));
					}
					Uri uri = Uri.parse(medicalRecordId);
					Intent result = new Intent(null, uri);
					result.putExtra("id", messageId);
					result.putExtra("lastModified", lastModified);
					result.putExtra("bitmap", photo);
					setResult(RESULT_OK, result);
					finish();

				}else if(id.equals("8")){
					Log.v(TAG, "post Image:"+"YES!");
					httpUrl = http + postMessageUrl + medicalRecordId+TYPE_ONE;
					Log.v(TAG, "httpUrl:"+httpUrl);
					JSONObject joRev = HttpUtil.postImage(file, httpUrl);
					Log.v(TAG, "image rev:"+joRev.toString());
					showStr = joRev.getString("result");
					int messageId = 0;
					long lastModified = 0;
					if(showStr.equals("200")){
						JSONObject joValue = joRev.getJSONObject("value");
						messageId = joValue.getInt("id");
						lastModified = joValue.getLong("lastModified");
						Log.v(TAG, "messageId rev:"+String.valueOf(messageId));
						Log.v(TAG, "lastModified rev:"+String.valueOf(lastModified));
					}
					Uri uri = Uri.parse(medicalRecordId);
					Intent result = new Intent(null, uri);
					result.putExtra("id", messageId);
					result.putExtra("lastModified", lastModified);
					result.putExtra("bitmap", photo);
					setResult(RESULT_OK, result);
					finish();

				}


			}


		}
	}
	public static boolean saveBitmap2file(Bitmap bmp, String filename)
	{
		File f = new File(Environment.getExternalStorageDirectory(),IMAGE_FILE_NAME);
		CompressFormat format = CompressFormat.JPEG;
		int quality = 100;
		FileOutputStream stream = null;
		try
		{
			f.createNewFile();
			stream = new FileOutputStream(f);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bmp.compress(format, quality, stream);
	}

	public static String uri2filePath(Uri uri, Activity activity) {

		String path = "";

		if (DocumentsContract.isDocumentUri(activity, uri)) {

			String wholeID = DocumentsContract.getDocumentId(uri);

			String id = wholeID.split(":")[1];

			String[] column = { MediaStore.Images.Media.DATA };

			String sel = MediaStore.Images.Media._ID + "=?";

			Cursor cursor = activity.getContentResolver().query(

					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel,

					new String[] { id }, null);

			int columnIndex = cursor.getColumnIndex(column[0]);

			if (cursor.moveToFirst()) {

				path = cursor.getString(columnIndex);

			}

			cursor.close();

		} else {

			String[] projection = { MediaStore.Images.Media.DATA };

			Cursor cursor = activity.getContentResolver().query(uri,

					projection, null, null, null);

			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

			cursor.moveToFirst();

			path = cursor.getString(column_index);

		}
		return path;

	}

	//以下是关键，原本uri返回的是file:///...来着的，android4.4返回的是content:///...
	@SuppressLint("NewApi")
	public static String getPath(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}

			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {
				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] {
						split[1]
				};

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {
			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 *
	 * @param context The context.
	 * @param uri The Uri to query.
	 * @param selection (Optional) Filter used in the query.
	 * @param selectionArgs (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri, String selection,
									   String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = {
				column
		};

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
					null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}


	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}

}
