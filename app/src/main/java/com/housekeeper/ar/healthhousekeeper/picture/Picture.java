package com.housekeeper.ar.healthhousekeeper.picture;

import android.annotation.SuppressLint;
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
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.SysApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Picture extends BaseActivity {

	private String TAG = "Picture";
	private String postMessageUrl = "shlc/doctor/message/medicalRecord/";
	String TYPE_ONE = "?type=INSPECTION", TYPE_TWO="?type=SORE", TYPE_THREE = "?type=PRESCRIPTION";
	static String picUploadUrl = "shlc/photo";
	static String httpUrl;
	static String http ;
	private String showStr = "";
	private static String address = "";
	private String id, medicalRecordId;
	MyApp myApp;
	private String[] items = new String[] { "图库","拍照" };
	/* 头像名称 */
	private static final String IMAGE_FILE_NAME = "face.jpg";
	/* 请求码 */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int SELECT_PIC_KITKAT = 3;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	Context context;
	String filePath;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);
		//setContentView(R.layout.picture);
		myApp = (MyApp)getApplication();
		http = myApp.getHttp();
		Intent intentReceive = getIntent();
		Bundle bundle = intentReceive.getExtras();
		id = bundle.getString("id");
		medicalRecordId = String.valueOf(bundle.getInt("medicalRecordId"));
		Log.v(TAG, "id:"+id);
		Log.v(TAG, "medicalRecordId:"+medicalRecordId);
		context = Picture.this;
		//showSettingFaceDialog();
	}
	public static boolean hasSdcard(){
		String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
	}


	public void showSettingFaceDialog() {

		new AlertDialog.Builder(this)
				.setTitle("图片来源")
				.setCancelable(true)
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case 0:// Local Image
								Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
								intent.addCategory(Intent.CATEGORY_OPENABLE);
								intent.setType("image/*");
								if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
									startActivityForResult(intent,SELECT_PIC_KITKAT);
								} else {
									startActivityForResult(intent,IMAGE_REQUEST_CODE);
								}
								break;
							case 1:// Take Picture
								Intent intentFromCapture = new Intent(
										MediaStore.ACTION_IMAGE_CAPTURE);
								// 判断存储卡是否可以用，可用进行存储
								if (hasSdcard()) {
									intentFromCapture.putExtra(
											MediaStore.EXTRA_OUTPUT,
											Uri.fromFile(new File(Environment
													.getExternalStorageDirectory(),
													IMAGE_FILE_NAME)));
								}
								startActivityForResult(intentFromCapture,
										CAMERA_REQUEST_CODE);
								break;
						}
					}
				})
				.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
												int which) {
								dialog.dismiss();
							}
						}).show();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 结果码不等于取消时候
		if (resultCode != RESULT_CANCELED) {
			switch (requestCode) {
				case IMAGE_REQUEST_CODE:
					startPhotoZoom(data.getData());
					break;
				case SELECT_PIC_KITKAT:
					startPhotoZoom(data.getData());
					break;
				case CAMERA_REQUEST_CODE:
					if (hasSdcard()) {
						File tempFile = new File(Environment.getExternalStorageDirectory(),IMAGE_FILE_NAME);
						startPhotoZoom(Uri.fromFile(tempFile));
					} else {
						Toast.makeText(Picture.this, "未找到存储卡，无法存储照片！",
								Toast.LENGTH_LONG).show();
					}

					break;
				case RESULT_REQUEST_CODE:
					if (data != null) {
						setImageToView(data);
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

		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, RESULT_REQUEST_CODE);
	}

	/**
	 * 保存裁剪之后的图片数据
	 *
	 * @param picdata
	 */
	private void setImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			saveBitmap(photo);
		}
	}

	public void saveBitmap(Bitmap mBitmap) {
		File f = new File(Environment.getExternalStorageDirectory(),IMAGE_FILE_NAME);
		try {
			f.createNewFile();
			FileOutputStream fOut = null;
			fOut = new FileOutputStream(f);
			mBitmap.compress(CompressFormat.JPEG, 100, fOut);
			fOut.flush();
			fOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	private void uploadFace(){
		File file = new File(Environment.getExternalStorageDirectory(),IMAGE_FILE_NAME);
		String filePath=file.getAbsolutePath();
	}

}