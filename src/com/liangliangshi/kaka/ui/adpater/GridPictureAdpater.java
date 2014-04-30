package com.liangliangshi.kaka.ui.adpater;

import java.util.List;

import com.liangliangshi.kaka.R;
import com.liangliangshi.kaka.common.FileUtils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class GridPictureAdpater extends MyBaseAdapter {


	private ViewHolder holder = null;
	private Context context;
	private int [] image_path = { R.drawable.a7bcad2642b48513fe9bd4bffec,
															R.drawable.aa12abc76d6f40265b1c311d3993f30,
															R.drawable.b1ed1d478df508e9ae51c150e564dbbf,
															R.drawable.b44c226f9c6768c153cdfbd98258558b,
															R.drawable.ba6d644a9573fcadb99692a9240e99c,
															R.drawable.c56dd93fa89845ea0b29fcd96c7cdf0,
															R.drawable.d3d09e3bb4d7d0f94e639d348f64e36,
															R.drawable.d23d1bb19c14e316cb7536362f19b826,
															R.drawable.d81c3bca1886662c4d030137e3b,
															R.drawable.d3150bebf5c9c4f1ee83ec6b2a68add1,
															R.drawable.dddfd5624f7b99e40de4a9431d06,
															R.drawable.e2fe7e14fd13dfe3f65aa58fa58,
															R.drawable.e6f8d9902bb32950cf3d7e0fbcedceff,
															R.drawable.e987d401556728fc593868fea8ba041,
															R.drawable.ec40036cccaa46d9abcee05ae9955904,
															R.drawable.f8fb24a6a805bd6f830679b8f3fbd502,
															R.drawable.f3185e65a606c2f04c308381dd,
															R.drawable.fb0b9fc0e9a0ee471a38a04f94334db0};
	static class ViewHolder {
		private ImageView gridview_image;
		private ImageView gridview_image_bg;
		
	}
	
	public GridPictureAdpater(Context context, List<? extends Object> list) {
		super(context, list);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		String timeperiod= (String) getList().get(position);
		System.out.println("~~~position~~~~~"+position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_main2_gridview, null);
			holder.gridview_image = (ImageView) convertView.findViewById(R.id.gridview_image);
			holder.gridview_image_bg = (ImageView) convertView.findViewById(R.id.gridview_image_bg);
			convertView.setTag(holder);  
		} else {
			holder = (ViewHolder) convertView.getTag();  
		}
		Bitmap  bitmap = BitmapFactory.decodeResource(context.getResources(),image_path[position]);
		holder.gridview_image.setImageBitmap(Bitmap.createScaledBitmap(bitmap, FileUtils.dip2px(context, 123), FileUtils.dip2px(context, 120), true));
//		holder.gridview_image.setImageBitmap(ImageUtils.getRoundedCornerBitmap(Bitmap.createScaledBitmap(bitmap, FileUtils.dip2px(context, 123), FileUtils.dip2px(context, 120), true),140));
		holder.gridview_image_bg.getBackground().setAlpha(150);
		return convertView;
	}
	
	
	
	public Drawable convertBitmap2Drawable(Bitmap bitmap) {
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		return bd;
	}
}
