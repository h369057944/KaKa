package com.liangliangshi.kaka.common;

import android.annotation.SuppressLint;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {
	
	/**
	 * �ַ���ת����
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try{
			return Integer.parseInt(str);
		}catch(Exception e){}
		return defValue;
	}

	/**
	 * ����ת����
	 * @param obj
	 * @return ת���쳣���� 0
	 */
	public static int toInt(Object obj) {
		if(obj==null) return 0;
		return toInt(obj.toString(),0);
	}
	
	/**
	 * �жϸ����ַ����Ƿ�հ״���
	 * �հ״���ָ�ɿո��Ʊ�����س��������з���ɵ��ַ���
	 * �������ַ���Ϊnull����ַ���������true
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty( String input ) 
	{
		if ( input == null || "".equals( input ) )
			return true;
		
		for ( int i = 0; i < input.length(); i++ ) 
		{
			char c = input.charAt( i );
			if ( c != ' ' && c != '\t' && c != '\r' && c != '\n' )
			{
				return false;
			}
		}
		return true;
	}
	
	public static String clearString(String content){
		if(content.indexOf("<img")!=-1){
			 int i = content.indexOf("<img");
			 int j = content.indexOf("/>");
			 content =  content.substring(0,  i) +content.substring(j+2,  content.length());
		}
		
		if(content.indexOf("<div")!=-1){
			 int i = content.indexOf("<div");
			 int j = content.indexOf("</div>");
			 content =  content.substring(0,  i) +content.substring(j+6,  content.length());
		}
		
		return content;
	}
		
	public static Long getlongDate() {
		long str= System.currentTimeMillis()/1000;
		return str;
	}
	
	public static String getDate() {
		SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("hh:mm");     
		String   date   =   sDateFormat.format(new   java.util.Date());  
		return date;
	}
	
	/**
	 * URL����תstring�ַ���
	 * @param URL����
	 */
	public static String URLDecoder(String code){
		if(code == null || code.equals("")){
			return null;
		}
		String urlStr = null;
		 try {
			urlStr = URLDecoder.decode(code, "gb2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return urlStr; 
	}
	
	/**
	 * URL����תstring�ַ���
	 * @param URL����
	 */
	public static String TimeProcess(String time){
		if(time == null || time.equals("")){
			return null;
		}
		time = time.replace("T", " ");
		int point = time.lastIndexOf('.');
		time = time.substring(0, point);
		return time;
	}
	/**
	 * ʱ�䴦��
	 * @param 
	 */
	
	public static String getTime(String timeStr){
		if (StringUtils.isEmpty(timeStr)) {
			return "";
		}
		timeStr = timeStr.replace("T", " ");
		int point = timeStr.lastIndexOf(':');
		if (point != -1) {
			return timeStr.substring(0, point);
		} else {
			return timeStr;
		}
	}
	
	public static String TimeProcessTODAY(String timeStr){
		if (StringUtils.isEmpty(timeStr)) {
			return "";
		}
		return timeStr.substring(0, 10);
	}
}
