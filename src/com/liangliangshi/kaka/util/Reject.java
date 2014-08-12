package com.liangliangshi.kaka.util;

import org.json.JSONException;

import com.liangliangshi.kaka.http.CustomHttpClient;


public class Reject extends Base{
	
	private String return_str;


	public String getReturn_str() {
		return return_str;
	}

	public void setReturn_str(String return_str) {
		this.return_str = return_str;
	}

	public static Reject parse(String str) {
		Reject reject = new Reject();

		Reject response = (Reject) CustomHttpClient.Http_post(
				reject, str);
		if (response.isSuccess() == false) {
			return reject;
		}
		reject.setReturn_str(str);
		return reject;
	}
}
