package com.liangliangshi.kaka.ui.adpater;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter extends BaseAdapter{
	
	protected List<? extends Object> list;
	protected LayoutInflater inflater;
	
	public MyBaseAdapter(Context context, List<? extends Object> list ){
		inflater = LayoutInflater.from(context);
		this.list = list;
	}
	
	public void setList(List<? extends Object> list) {
		this.list = list;
	}

	public List<? extends Object> getList() {
		return list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

}
