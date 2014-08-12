package com.liangliangshi.kaka.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.liangliangshi.kaka.common.FileUtils;



public class HttpDownloadUtil {
    
    /**
     * ����URL�����ļ���ǰ��������ļ����е��������ı�����������ֵ���ļ����е��ı�����
     * @param urlstr
     * @return
     */
    public static String downFile(String urlstr){
        StringBuffer sb=new StringBuffer();
        BufferedReader buffer=null;
        URL url=null;
        String line=null;
        try {
            //����һ��URL����
            url=new URL(urlstr);
            //����URL���󴴽�һ��Http����
            HttpURLConnection urlConn=(HttpURLConnection) url.openConnection();
            //ʹ��IO��ȡ���ص��ļ�����
            buffer=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            while((line=buffer.readLine())!=null){
                sb.append(line);
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                buffer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    
    /**
     * �ú�����������    -1�����������ļ�����0 �������ļ��ɹ�1���ļ��Ѿ����� 
     * @param urlstr
     * @param path
     * @param fileName
     * @return
*/
    public int downFile(String urlstr,String path,String fileName){
        InputStream inputStream=null;
        
        if(FileUtils.isFileExist(path+fileName)){
            return 1;
        }else{
            inputStream=getInputStreamFormUrl(urlstr);
            File resultFile= FileUtils.writeToSDfromInput(path, fileName, inputStream);
            if(resultFile==null){
                return -1;
            }
        }
        return 0;
    }
    
    /**
     * ����URL�õ�������
     * @param urlstr
     * @return
*/
    public InputStream getInputStreamFormUrl(String urlstr){
        InputStream inputStream=null;
        try {
            URL url=new URL(urlstr);
            HttpURLConnection urlConn=(HttpURLConnection) url.openConnection();
            inputStream=urlConn.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }
}