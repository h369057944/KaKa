package com.liangliangshi.kaka.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import com.liangliangshi.kaka.common.StringUtils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AppContext extends Application {
	
	public static final int NETTYPE_WIFI = 0x01;
	public static final int NETTYPE_CMWAP = 0x02;
	public static final int NETTYPE_CMNET = 0x03;

	public static final String PAGE_SIZE = "10";//Ĭ�Ϸ�ҳ��С
	
	private String SessionId;
	private String tokenId;
	
	public String getRole() {
		if(this.getProperty("user.role")==null){
			return "";
		} else {
			return this.getProperty("user.role");
		}
	}
	
	public void setRole(String role) {
		setProperty("user.role", role);
	}
	
	
	public String getAccountid() {
		return this.getProperty("user.accountid");
	}
	
	public void setAccountid(String accountid) {
		setProperty("user.accountid", accountid);
	}
	
	
	public String getSessionId() {
		return this.getProperty("user.SessionId");
	}
	
	public void setSessionId(String sessionId) {
		setProperty("user.SessionId", sessionId);
	}
	
	public String getTokenId() {
		return this.getProperty("user.tokenId");
	}
	
	public void setTokenId(String tokenid) {
		tokenId = tokenid;
		setProperty("user.tokenId", tokenid);
	}
	
	public String getisFrist() {
		return this.getProperty("user.isfrist");
	}
	
	public void setisFrist(String isfrist) {
		setProperty("user.isfrist", isfrist);
	}
	
	public String getIsCustomeServiceMan() {
		return this.getProperty("user.IsCustomeServiceMan");
	}
	
	public void setIsCustomeServiceMan(String is) {
		setProperty("user.IsCustomeServiceMan", is);
	}
	
	public String getisUserMamentCompetence() {
		return this.getProperty("user.isUserMamentCompetence");
	}
	
	public void setisUserMamentCompetence(String is) {
		setProperty("user.isUserMamentCompetence", is);
	}
	
	/**
	 * ��������Ƿ����
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}
	
	/**
	 * ��ȡ��ǰ��������
	 * @return 0��û������   1��WIFI����   2��WAP����    3��NET����
	 */
	public int getNetworkType() {
		int netType = 0;
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}		
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			String extraInfo = networkInfo.getExtraInfo();
			if(!StringUtils.isEmpty(extraInfo)){
				if (extraInfo.toLowerCase().equals("cmnet")) {
					netType = NETTYPE_CMNET;
				} else {
					netType = NETTYPE_CMWAP;
				}
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = NETTYPE_WIFI;
		}
		return netType;
	}
	
	/**
	 * �жϻ��������Ƿ�ɶ�
	 * @param cachefile
	 * @return
	 */
	private boolean isReadDataCache(String cachefile)
	{
		return readObject(cachefile) != null;
	}
	
	/**
	 * �������
	 * @param ser
	 * @param file
	 * @throws IOException
	 */
	public boolean saveObject(Serializable ser, String file) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try{
			fos = openFileOutput(file, MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(ser);
			oos.flush();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			try {
				oos.close();
			} catch (Exception e) {}
			try {
				fos.close();
			} catch (Exception e) {}
		}
	}
	
	/**
	 * �жϻ����Ƿ����
	 * @param cachefile
	 * @return
	 */
	private boolean isExistDataCache(String cachefile){
		boolean exist = false;
		File data = getFileStreamPath(cachefile);
		if(data.exists())
			exist = true;
		return exist;
	}
	/**
	 * ��ȡ����
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public Serializable readObject(String file){
		if(!isExistDataCache(file))
			return null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try{
			fis = openFileInput(file);
			ois = new ObjectInputStream(fis);
			return (Serializable)ois.readObject();
		}catch(FileNotFoundException e){
		}catch(Exception e){
			e.printStackTrace();
			//�����л�ʧ�� - ɾ�������ļ�
			if(e instanceof InvalidClassException){
				File data = getFileStreamPath(file);
				data.delete();
			}
		}finally{
			try {
				ois.close();
			} catch (Exception e) {}
			try {
				fis.close();
			} catch (Exception e) {}
		}
		return null;
	}
	
	public boolean containsProperty(String key){
		Properties props = getProperties();
		 return props.containsKey(key);
	}
	
	public void setProperties(Properties ps){
		AppConfig.getAppConfig(this).set(ps);
	}

	public Properties getProperties(){
		return AppConfig.getAppConfig(this).get();
	}
	
	public void setProperty(String key,String value){
		AppConfig.getAppConfig(this).set(key, value);
	}
	
	public String getProperty(String key){
		return AppConfig.getAppConfig(this).get(key);
	}
	public void removeProperty(String...key){
		AppConfig.getAppConfig(this).remove(key);
	}
	
}
