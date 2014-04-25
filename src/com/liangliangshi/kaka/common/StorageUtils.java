package com.liangliangshi.kaka.common;

import java.io.File;

import android.os.Environment;

public class StorageUtils {
	public final static String SAVE_VIDEO_PATH_TOSD = Environment.getExternalStorageDirectory()+ File.separator+ "DeviceOO/videoRecord"+ File.separator;
	
	public static boolean checkExternalStorageAvailable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
	    }
		else {
			return false;
		}
	}
	
	public static String getFileName() {
		String fileName = SAVE_VIDEO_PATH_TOSD+StringUtils.getlongDate()+".mp4";
		FileUtils.iscreatePath(fileName);
		return fileName;
	}
}
