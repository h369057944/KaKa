package com.liangliangshi.kaka.ui;

import com.liangliangshi.kaka.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ShopInfoActivity extends BaseActivity {

	private GridView shop_gridview_people;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_info_view);
		
		initHeader();
		initView();
	}

	private void initHeader() {
		ImageView but_header_back = (ImageView) findViewById(R.id.but_header_back);
		but_header_back.setOnClickListener(new OnClickListener() {public void onClick(View v) {finish();}});
		TextView head_view_title = (TextView) findViewById(R.id.head_view_title);
		head_view_title.setText("CCOOCCOO");
	}
	
	private void initView() {
		shop_gridview_people = (GridView) findViewById(R.id.shop_gridview_people);
	}

}
