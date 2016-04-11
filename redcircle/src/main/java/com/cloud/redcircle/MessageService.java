package com.cloud.redcircle;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import io.rong.ApiHttpClient;
import io.rong.models.FormatType;
import io.rong.models.SdkHttpResult;
import io.rong.util.GsonUtil;


@Service
public class MessageService {
	
	String key = "qf3d5gbj3ufqh";//替换成您的appkey
	String secret = "Aqccu1B5d4f";//替换成匹配上面key的secret
	
	String fileName = "test.zip";
	String savePath = "/Users/cloud/Desktop/";

	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	
	
	
	public void syncMessage() {
		SdkHttpResult result = null;
		try {
			result = ApiHttpClient.getMessageHistoryUrl(key, secret, "2016040913",
					FormatType.json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (result.getHttpCode() == 200) {
			String resultStr = result.getResult();
			HashMap resultMap = (HashMap) GsonUtil.fromJson(resultStr, HashMap.class);
	        try {
				this.downLoadFromUrl(resultMap.get("url").toString(), fileName, savePath);
				try {
					List<Object[]> messageArray = this.readZipFile(savePath+fileName);
					System.out.println(messageArray.size());
		            int[] results = jdbcTemplate.batchUpdate("INSERT INTO t_red_user(friend_phone, me_phone) VALUES (?, ?)", messageArray);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("getMessageHistoryUrl=" + result);

		}
		


	}
	
	
	/** 
     * 从网络Url中下载文件 
     * @param urlStr 
     * @param fileName 
     * @param savePath 
     * @throws IOException 
     */  
    public void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{  
        URL url = new URL(urlStr);    
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
                //设置超时间为3秒  
        conn.setConnectTimeout(3*1000);  
        //防止屏蔽程序抓取而返回403错误  
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
  
        //得到输入流  
        InputStream inputStream = conn.getInputStream();    
        //获取自己数组  
        byte[] getData = readInputStream(inputStream);      
  
        //文件保存位置  
        File saveDir = new File(savePath);  
        if(!saveDir.exists()){  
            saveDir.mkdir();  
        }  
        File file = new File(saveDir+File.separator+fileName);      
        FileOutputStream fos = new FileOutputStream(file);       
        fos.write(getData);   
        if(fos!=null){  
            fos.close();    
        }  
        if(inputStream!=null){  
            inputStream.close();  
        }  
  
  
        System.out.println("info:"+url+" download success");   
  
    }  
  
  
  
    /** 
     * 从输入流中获取字节数组 
     * @param inputStream 
     * @return 
     * @throws IOException 
     */  
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {    
        byte[] buffer = new byte[1024];    
        int len = 0;    
        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
        while((len = inputStream.read(buffer)) != -1) {    
            bos.write(buffer, 0, len);    
        }    
        bos.close();    
        return bos.toByteArray();    
    }

    
    public List<Object[]> readZipFile(String file) throws Exception {  
        ZipFile zf = new ZipFile(file);  
        InputStream in = new BufferedInputStream(new FileInputStream(file));  
        ZipInputStream zin = new ZipInputStream(in);  
        ZipEntry ze;  
		List<Object[]> messageArray = new ArrayList<Object[]>();
        while ((ze = zin.getNextEntry()) != null) {  
            if (ze.isDirectory()) {
            } else {  
                System.err.println("file - " + ze.getName() + " : "  
                        + ze.getSize() + " bytes");  
                long size = ze.getSize();  
                if (size > 0) {  
                    BufferedReader br = new BufferedReader(  
                            new InputStreamReader(zf.getInputStream(ze)));  
                    String line;  
                    while ((line = br.readLine()) != null) {  
                        System.out.println(line); 
            			HashMap messageMap = (HashMap) GsonUtil.fromJson(line.substring(19), HashMap.class);
            			Object[] message = {messageMap.get("appId"),messageMap.get("fromUserId"),messageMap.get("targetId"),messageMap.get("targetType"),messageMap.get("GroupId"),messageMap.get("classname"),messageMap.get("content"),messageMap.get("dateTime"),messageMap.get("msgUID")};
            			messageArray.add(message);
                    }  
                    br.close();  
                }  
                System.out.println();  
            }  
        }  
        zin.closeEntry();  
		return messageArray;

    }  
}
