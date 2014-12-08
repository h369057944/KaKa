package com.liangliangshi.kaka.http;


import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.liangliangshi.kaka.app.AppContext;
import com.liangliangshi.kaka.util.Base;


import android.text.TextUtils;
import android.util.Log;

public class CustomHttpClient {
	private static String TAG = "CustomHttpClient";
	private static final String CHARSET_UTF8 = HTTP.UTF_8;
	private static HttpClient customerHttpClient;

	private CustomHttpClient() {

	}

	public static String doPost(AppContext context,String url, List<NameValuePair> params) {
		String result = null;

		HttpPost postRequest = new HttpPost(url);
		HttpEntity entity = null;
		HttpResponse response = null;
		try {
			entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
			postRequest.setEntity(entity);
			HttpClient httpclient = getHttpClient(context);

			response = httpclient.execute(postRequest);

			String errorString = new String(response.getStatusLine().toString().getBytes("iso-8859-1"),"UTF-8");
			String reason = new String((response.getStatusLine().getReasonPhrase()+"").getBytes("iso-8859-1"),"UTF-8");
			
			Log.e("getStatusCode:",response.getStatusLine().getStatusCode()+"");
			Log.e("getReasonPhrase:",reason);
			Log.e("getReasonToString:",errorString+"");
			
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				Log.e(TAG, "doPost 连接失败");
				if(response.getStatusLine().getStatusCode() == 500){
					result = "{\"error\":\""+reason+"\""+"}";
				} else {
					result = "{\"error\":\""+response.getStatusLine().getStatusCode()+"\""+"}";
				}
			} else {
				HttpEntity resEntity = response.getEntity();
				result = (resEntity == null) ? null : EntityUtils.toString(
						resEntity, HTTP.UTF_8);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "doPost 连接失败   " + url);
			result = "{\"error\":\"连接失败，请重试\"}";
			if(!context.isNetworkConnected()){
				result = "{\"error\":\"网络错误，请检查网络连接\"}";
			}
		} finally {
			postRequest.abort();
		}
		System.out.println("~~~~~result_psot~~~~~~"+result);
		return result;

	}
	
	
	public static String doGet(AppContext context,String url) {
		String result = null;

		HttpGet getRequest = new HttpGet(url);
		HttpResponse response = null;
		HttpEntity resEntity = null;
		
		HttpClient httpclient = getHttpClient(context);
		
		try {
			response = httpclient.execute(getRequest);

			String errorString = new String(response.getStatusLine().toString().getBytes("iso-8859-1"),"UTF-8");
			String reason = new String((response.getStatusLine().getReasonPhrase()+"").getBytes("iso-8859-1"),"UTF-8");
			Log.e("getStatusCode:",response.getStatusLine().getStatusCode()+"");
			Log.e("getReasonPhrase:",reason+"");
			Log.e("getReasonToString:",errorString+"");
	
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				Log.e(TAG, "doGet 连接失败");
				if(response.getStatusLine().getStatusCode() == 500){
					result = "{\"error\":\""+reason+"\""+"}";
				} else {
					result = "{\"error\":\""+response.getStatusLine().getStatusCode()+"\""+"}";
				}
			} else {
				resEntity = response.getEntity();
				result = (resEntity == null) ? null : EntityUtils.toString(resEntity, CHARSET_UTF8);
			}
		} catch (Exception e) {
			Log.e(TAG, "doGet 连接失败  exception" + url);
			e.printStackTrace();
			result = "{\"error\":\"连接失败，请重试\"}";
			if(!context.isNetworkConnected()){
				result = "{\"error\":\"网络错误，请检查网络连接\"}";
			}
		} finally {
			getRequest.abort();
		}
		System.out.println("~~~~~result_get~~~~~~"+result);
		return result;
	}
	
	
	
	public static String makeURL(String p_url, Map<String, Object> params) {
		StringBuilder url = new StringBuilder(p_url);
		if(url.indexOf("?")<0)
			url.append('?');

		for(String name : params.keySet()){
			url.append('&');
			url.append(name);
			url.append('=');
			url.append(String.valueOf(params.get(name)));
		}
		String urls = url.toString().replace("?&", "?");
		String urlss = urls.replaceAll(" ", "");
		System.out.println("~~~request~~~"+urlss);
		return urlss;
	}
	
	/**
	 * 创建httpClient实例
	 * 
	 * @return
	 * @throws Exception
	 */
	private static synchronized HttpClient getHttpClient(AppContext context) {
		if (null == customerHttpClient) {
			HttpParams params = new BasicHttpParams();
			// 设置�?��基本参数
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, CHARSET_UTF8);
			HttpProtocolParams.setUseExpectContinue(params, true);
			HttpProtocolParams
					.setUserAgent(
							params,
							"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
									+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
			// 超时设置
			/* 从连接池中取连接的超时时�?*/
			ConnManagerParams.setTimeout(params, 20000);
			/* 连接超时 */
			int ConnectionTimeOut = 20000;
			if (!context.isNetworkConnected()) {
				ConnectionTimeOut = 10000;
			}
			HttpConnectionParams
					.setConnectionTimeout(params, ConnectionTimeOut);
			/* 请求超时 */
			HttpConnectionParams.setSoTimeout(params, 20000);
			// 设置我们的HttpClient支持HTTP和HTTPS两种模式
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			schReg.register(new Scheme("https", SSLSocketFactory
					.getSocketFactory(), 443));

			// 使用线程安全的连接管理来创建HttpClient
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
					params, schReg);
			customerHttpClient = new DefaultHttpClient(conMgr, params);
		}
		return customerHttpClient;
	}
	
	public static Base Http_post(Base base,String str) {
		try {
			if(str.equals("")||TextUtils.isEmpty(str)){
				setErrorResult(base,"数据为空");
				return base;
			} 
			if(!new JSONObject(str).getString("error").equals("")){
				if(!new JSONObject(str).getString("error").equals("204")){
					setErrorResult(base,new JSONObject(str).getString("error"));
				}
				return base;
			} 
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return base;
	} 
	
	private static void setErrorResult(Base base,String error_msg) {
		base.setSuccess(false);
		base.setError_msg(error_msg);
	}
	
	
	
}
