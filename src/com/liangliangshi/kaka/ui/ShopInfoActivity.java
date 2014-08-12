package com.liangliangshi.kaka.ui;

import java.util.ArrayList;

import com.liangliangshi.kaka.R;
import com.liangliangshi.kaka.ui.adpater.GridPictureAdpater;
import com.liangliangshi.kaka.ui.adpater.ShopPeopleAdpater;
import com.liangliangshi.kaka.ui.animation.Rotate3dAnimation;
import com.liangliangshi.kaka.util.People;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ShopInfoActivity extends BaseActivity {
	
	private ImageView shop_info_image;
	private GridView shop_gridview_people;
	
	private ShopPeopleAdpater peopleAdp;
	private ArrayList<String> people_list = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_info_view);
		
		initList();
		initHeader();
		initView();
		
	}
	
	private void initList() {
		for(int i =0;i<18;i++){
			people_list.add("1");
		}
	}

	private void initHeader() {
		ImageView but_header_back = (ImageView) findViewById(R.id.but_header_back);
		but_header_back.setOnClickListener(new OnClickListener() {public void onClick(View v) {finish();}});
		TextView head_view_title = (TextView) findViewById(R.id.head_view_title);
		head_view_title.setText("CCOOCCOO");
	}
	
	private void initView() {
		shop_info_image = (ImageView) findViewById(R.id.shop_info_image);
		shop_gridview_people = (GridView) findViewById(R.id.shop_gridview_people);
		peopleAdp = new ShopPeopleAdpater(this, people_list);
		shop_gridview_people.setAdapter(peopleAdp);
		shop_info_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				applyRotation(0, 90);
			}
		});
	}
	
    private void applyRotation(float start, float end) {  
        // 计算中心点  
        final float centerX = shop_info_image.getWidth() / 2.0f;  
        final float centerY = shop_info_image.getHeight() / 2.0f;  
  
        final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end,  
                centerX, centerY, 310.0f, true);  
        rotation.setDuration(500);  
        rotation.setFillAfter(true);  
        rotation.setInterpolator(new AccelerateInterpolator());  
        // 设置监听  
        rotation.setAnimationListener(new DisplayNextView());  
  
        shop_info_image.startAnimation(rotation);  
    }  
    
    private final class DisplayNextView implements Animation.AnimationListener {  
    	  
        public void onAnimationStart(Animation animation) {  
        }  
        // 动画结束  
        public void onAnimationEnd(Animation animation) {  
        	shop_info_image.post(new SwapViews());  
        }  
        public void onAnimationRepeat(Animation animation) {  
        }  
    } 
    
    private final class SwapViews implements Runnable {  
        public void run() {  
            final float centerX = shop_info_image.getWidth() / 2.0f;  
            final float centerY = shop_info_image.getHeight() / 2.0f;  
            Rotate3dAnimation rotation = null;  
  
            shop_info_image.requestFocus();  
  
            rotation = new Rotate3dAnimation(90, 0, centerX, centerY, 310.0f,  
                    false);  
            rotation.setDuration(500);  
            rotation.setFillAfter(true);  
            rotation.setInterpolator(new DecelerateInterpolator());  
            shop_info_image.startAnimation(rotation);  
        }  
    }  

}
