package com.liangliangshi.kaka.ui;

import java.util.ArrayList;

import com.liangliangshi.kaka.R;
import com.liangliangshi.kaka.ui.adpater.GridPictureAdpater;
import com.liangliangshi.kaka.ui.wight.ScrollLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.app.Activity;
import android.content.Intent;

public class Main extends Activity {

	private int mCurSel;
	private int mViewCount;
	private RadioButton[] mButtons;
	private GridView main2_gridview;
	private ScrollLayout mScrollLayout;
	private GridPictureAdpater picAdpater;
	private ArrayList<String> image_list = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		System.out.println("~~~~~~");
		this.initList();
		this.initPageScroll();
	}

	private void initList() {
		for(int i =0;i<18;i++){
			image_list.add("1");
		}
		
		picAdpater = new GridPictureAdpater(this, image_list);
		main2_gridview = (GridView) findViewById(R.id.main2_gridview);
		main2_gridview.setAdapter(picAdpater);
		main2_gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
		});
	}

	private void initPageScroll() {
		mScrollLayout = (ScrollLayout) findViewById(R.id.main_scrolllayout);
		mScrollLayout.setIsScroll(false);// 设置是否可以滑动
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_linearlayout_footer);
		mViewCount = mScrollLayout.getChildCount();
		mButtons = new RadioButton[mViewCount];

		for (int i = 0; i < mViewCount; i++) {
			mButtons[i] = (RadioButton) linearLayout.getChildAt(i);
			mButtons[i].setTag(i);
			mButtons[i].setChecked(false);
			mButtons[i].setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					int pos = (Integer) (v.getTag());
					// 点击当前项刷新
					if (mCurSel == pos) {
						switch (pos) {
						case 0:// 接诊
							break;
						case 1:// 病人
							break;
						case 2:// 个人信息
							break;
						case 3:// 设置
							break;
						}
					}
					mScrollLayout.snapToScreen(pos);
				}
			});
		}

		// 设置第一显示屏//测试111111
		mCurSel = 0;
		mButtons[mCurSel].setChecked(true);

		mScrollLayout
				.SetOnViewChangeListener(new ScrollLayout.OnViewChangeListener() {
					public void OnViewChange(int viewIndex) {
						// 切换列表视图-如果列表数据为空：加载数据
						if (mCurSel != viewIndex) {
							switch (viewIndex) {
							case 0:// 接诊

								break;
							case 1:// 病人

								break;
							case 2:// 个人信息

								break;
							case 3:// 设置

								break;
							}
						}
						setCurPoint(viewIndex);
					}
				});
	}
	
	private void setCurPoint(int index) {
		if (index < 0 || index > mViewCount - 1 || mCurSel == index)
			return;

		mButtons[mCurSel].setChecked(false);
		mButtons[index].setChecked(true);
		mCurSel = index;
	}
	

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        }
    }

}
