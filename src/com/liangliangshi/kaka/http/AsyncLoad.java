package com.liangliangshi.kaka.http;



import com.liangliangshi.kaka.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

public class AsyncLoad extends AsyncTask<Integer, Integer, Integer> {

	private int mAction;
	private int mwhatRefresh;
	private Context mContext;
	private TaskListener mTaskListener;
	private View loadingview;
	private Dialog dialog;
	
	public AsyncLoad(Context context, TaskListener listener, int action) {
		mContext = context;
		mTaskListener = listener;
		mAction = action;
		loadingview = ((Activity) context).getLayoutInflater().inflate(R.layout.loading_view, null);
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		} else {
			dialog = new AlertDialog.Builder(mContext).show();
		}
		dialog.setContentView(loadingview);
	}
	
	
	public AsyncLoad(Context context, TaskListener listener, int action,int whatRefresh,Boolean ishaveLoading){
		mContext = context;
        mTaskListener = listener;
        mAction = action;
        mwhatRefresh = whatRefresh;
        if(ishaveLoading){
        	loadingview = ((Activity) context).getLayoutInflater().inflate(R.layout.loading_view, null);
    		if (dialog != null && dialog.isShowing()) {
    			dialog.dismiss();
    		} else {
    			dialog = new AlertDialog.Builder(mContext).show();
    		}
    		dialog.setContentView(loadingview);
        }
        
	}
	@Override
	protected Integer doInBackground(Integer... Integer) {
		mTaskListener.getData(mAction,mwhatRefresh);
		return 1;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
		}
		mTaskListener.Update(mAction,mwhatRefresh);
		clearTask();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}

	
	private void clearTask() {
        mTaskListener = null;
        mContext = null;
    }
	
	
	public interface TaskListener {  
		public void getData(int action,int whatRefresh);
        public void Update(int action,int whatRefresh);
    } 
}
