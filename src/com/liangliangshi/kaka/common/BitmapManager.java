package com.liangliangshi.kaka.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
/**
 * 异步线程加载图片工具类
 * 使用说明：
 * BitmapManager bmpManager;
 * bmpManager = new BitmapManager(BitmapFactory.decodeResource(context.getResources(), R.drawable.loading));
 * bmpManager.loadBitmap(imageURL, imageView);
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-6-25
 */
public class BitmapManager {  
	  
    private static HashMap<String, SoftReference<Bitmap>> cache;  
    private static ExecutorService pool;  
    private static Map<ImageView, String> imageViews;  
    private Bitmap defaultBmp;  
    
    public final static String SAVE_IMAGE_PATH_TOSD = Environment.getExternalStorageDirectory()+ File.separator+ "DeviceOO/pictureDown"+ File.separator;
    public final static String SAVE_IMAGESMALL_PATH_TOSD = Environment.getExternalStorageDirectory()+ File.separator+ "DeviceOO/pictureSmallDown"+ File.separator;
    static {  
        cache = new HashMap<String, SoftReference<Bitmap>>();  
        pool = Executors.newFixedThreadPool(5);  //固定线程池
        imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    }  
    
    public BitmapManager(){}
    
    public BitmapManager(Bitmap def) {
    	this.defaultBmp = def;
    }
    
    /**
     * 设置默认图片
     * @param bmp
     */
    public void setDefaultBmp(Bitmap bmp) {  
    	defaultBmp = bmp;  
    }   
  
    /**
     * 加载图片
     * @param url
     * @param imageView
     */
    public void loadBitmap(String url, ImageView imageView) {  
    	loadBitmap(url, imageView, this.defaultBmp, 0, 0,false,false,false,null);
    }
    
    /**
     * 加载图片-并且小图显示
     * @param url
     * @param imageView
     */
    public void loadSmallBitmap(String url, ImageView imageView,boolean isSmall) {  
    	loadBitmap(url, imageView, this.defaultBmp, 0, 0,false,false,isSmall,null);
    }
    
    /**
     * 加载图片_有返回函数
     * @param url
     * @param imageView
     */
    public void loadBitmap(String url, ImageView imageView,ImageDownload imageDownCallback) {  
    	loadBitmap(url, imageView, this.defaultBmp, 0, 0,false,false,false,imageDownCallback);
    }
    
    /**
     * 加载图片-制定图片大小
     * @param url
     * @param imageView
     */
    public void loadBitmap(String url, ImageView imageView,int width, int height) {  
    	loadBitmap(url, imageView, this.defaultBmp, width, height,false,false,true,null);
    }
    
    /**
     * 加载图片-制定图片大小-并且设置成圆角
     * @param url
     * @param imageView
     */
    public void loadBitmap(String url, ImageView imageView,int width, int height,boolean isFillet) {  
    	loadBitmap(url, imageView, this.defaultBmp, width, height,false,isFillet,true,null);
    }
	
    /**
     * 加载图片-可设置加载失败后显示的默认图片
     * @param url
     * @param imageView
     * @param defaultBmp
     */
    public void loadBitmap(String url, ImageView imageView, Bitmap defaultBmp) {  
    	loadBitmap(url, imageView, defaultBmp, 0, 0,false,false,false,null);
    }
    
    /**
     * 加载图片后图片缩小
     * @param url
     * @param imageView
     * @param defaultBmp
     */
    public void loadBitmap(String url, ImageView imageView,boolean isNarrow) {  
    	loadBitmap(url, imageView, defaultBmp,0,0,isNarrow,false,false,null);
    }
    
    /**
     * 加载图片-可指定显示图片的高宽
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public void loadBitmap(String url, ImageView imageView, Bitmap defaultBmp, int width, int height,boolean isNarrow,boolean isFillet,boolean isSmall,ImageDownload imageDownCallback) {  
    	if(url == null||url.equals("")){
    		imageView.setImageBitmap(defaultBmp);//如果值为空 那么就设置成默认头像 
    		return;
    	}
        imageViews.put(imageView, url);  
        Bitmap bitmap = getBitmapFromCache(url);  
   
        if (bitmap != null) {
        	 //显示缓存图片
        	if(isNarrow){
        		imageView.setImageBitmap(ImageUtils.NarrowBit(bitmap));
        		if(imageDownCallback !=null){
            		imageDownCallback.imageDownloadCallback(true);
            	}
			} else {
				if(width > 0 && height > 0) {
					if(isFillet){
						imageView.setImageBitmap(ImageUtils.getRoundedCornerBitmap(Bitmap.createScaledBitmap(bitmap, width, height, true),14));
					} else {
						imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, width, height, true));
					}
				} else {
					 imageView.setImageBitmap(bitmap);  
				}
			}
        	if(imageDownCallback !=null){
        		imageDownCallback.imageDownloadCallback(true);
        	}
        } else {
        	String filename = FileUtils.getFileNameNoFormat(url);
        	String filepath = "";
        	if(!isSmall){
        		filepath = SAVE_IMAGE_PATH_TOSD + filename;
        	} else {
        		filepath = SAVE_IMAGESMALL_PATH_TOSD + filename;
        	}
    		File file = new File(filepath);
    		if(file.exists()){
    			if(isNarrow){
    				imageView.setImageBitmap(ImageUtils.FileCalculate(filepath,100,100));
    			} else {
    				Bitmap bitmaps = ImageUtils.getBitmapByPath(filepath);
    				if(bitmaps!=null){
	    				if(width > 0 && height > 0) {
	    					if(isFillet){
	    						imageView.setImageBitmap(ImageUtils.getRoundedCornerBitmap(Bitmap.createScaledBitmap(bitmaps, width, height, true),14));
	    					} else {
	    						imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmaps, width, height, true));
	    					}
		   				} else {
		   					 imageView.setImageBitmap(bitmaps);  
		   				}
	    			}
    			}
    			if(imageDownCallback !=null){
            		imageDownCallback.imageDownloadCallback(true);
            	}
        	}else{
				//线程加载网络图片
        		if(defaultBmp!=null){
        			imageView.setImageBitmap(defaultBmp);
        		}
        		queueJob(url, imageView, width, height,isNarrow,isFillet,isSmall,imageDownCallback);
        	}
        }  
    }  
    /**
     * 从缓存中获取图片
     * @param url
     */
    public Bitmap getBitmapFromCache(String url) {  
    	Bitmap bitmap = null;
        if (cache.containsKey(url)) {  
            bitmap = cache.get(url).get();  
        }  
        return bitmap;  
    }  
    
    /**
     * 从网络中加载图片
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public void queueJob(final String url, final ImageView imageView, final int width, 
    		final int height,final boolean isNarrow,final boolean isFillet,
    		final boolean isSmall,final ImageDownload imageDownCallback) {  
        /* Create handler in UI thread. */  
        final Handler handler = new Handler() {  
            public void handleMessage(Message msg) {  
                String tag = imageViews.get(imageView);  
                if (tag != null && tag.equals(url)) {
                    if (msg.obj != null) {
                    	if(isNarrow){
                    		imageView.setImageBitmap(ImageUtils.NarrowBit((Bitmap) msg.obj));  
            			} else {
            				if(width > 0 && height > 0) {
            					if(isFillet){
            						imageView.setImageBitmap(ImageUtils.getRoundedCornerBitmap(Bitmap.createScaledBitmap((Bitmap) msg.obj, width, height, true),14));
            					} else {
            						imageView.setImageBitmap(Bitmap.createScaledBitmap((Bitmap) msg.obj, width, height, true));  
            					}
            				} else {
            					 imageView.setImageBitmap((Bitmap) msg.obj);  
            				}
            			}
                    	//向SD卡中写入图片缓存
                        try {
                        	if(!isSmall){
                        		ImageUtils.saveImageToSD(null, SAVE_IMAGE_PATH_TOSD+FileUtils.getFileNameNoFormat(url), (Bitmap) msg.obj, 80);
                        	}else {
                        		ImageUtils.saveImageToSD(null, SAVE_IMAGESMALL_PATH_TOSD+FileUtils.getFileNameNoFormat(url), (Bitmap) msg.obj, 80);
                        	}
						} catch (IOException e) {
							e.printStackTrace();
						}
                        if(imageDownCallback !=null){
                    		imageDownCallback.imageDownloadCallback(true);
                    	}
                    } else {
                    	if(imageDownCallback !=null){
                    		imageDownCallback.imageDownloadCallback(false);
                    	}
                    }
                } 
            }  
        };  
  
        pool.execute(new Runnable() {   
            public void run() {  
                Message message = Message.obtain();  
                message.obj = downloadBitmap(url, width, height);  
                handler.sendMessage(message);  
            }  
        });  
    } 
  
    /**
     * 下载图片-可指定显示图片的高宽
     * @param url
     * @param width
     * @param height
     */
    private Bitmap downloadBitmap(String url, int width, int height) {   
        URL m;
		InputStream i = null;
		Bitmap bitmap = null;
        try {
        	m = new URL(url);
			HttpURLConnection conn  = (HttpURLConnection)m.openConnection();
            conn.setDoInput(true);
            conn.connect(); 
            
			i = (InputStream) m.getContent();
			 BitmapFactory.Options newOpts  = ImageUtils.Inputcalculate(i,720,1280);
	        i = (InputStream) m.getContent();
	        
            bitmap = BitmapFactory.decodeStream(i,null, newOpts);  

			i.close();
			
//			if(width > 0 && height > 0) {
//				bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);//指定显示图片的高宽
//			} 
			cache.put(url, new SoftReference<Bitmap>(bitmap));//放入缓存
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return bitmap;  
    }  
    
    public interface ImageDownload {
        public void imageDownloadCallback(Boolean isSuccess);
    }
}