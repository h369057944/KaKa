package com.liangliangshi.kaka.ui;


import com.liangliangshi.kaka.R;
import com.liangliangshi.kaka.app.AppContext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class BaseActivity extends Activity{

	public AppContext appContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		appContext = (AppContext) this.getApplication();
	}
	
	
	public void startActivitys(Intent intent) {
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
	/** Ä¬ÈÏÍË³ö **/
	public void defaultFinish() {
		super.finish();
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}

}
