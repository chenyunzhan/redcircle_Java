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
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import io.rong.ApiHttpClient;
import io.rong.models.FormatType;
import io.rong.models.SdkHttpResult;
import io.rong.util.GsonUtil;


@Service
public class MessageService extends TimerTask{
		
	String fileName = "message_log.zip";
	String savePath = System.getProperty("user.dir") + "/database/";

	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
    @Autowired  
    RedCircleProperties redCircleProperties; 
	
	public void setSyncMessageTimer() {
		Timer timer = new Timer(); 
	     timer.schedule(this, 60 * 1000, 60 * 60 * 1000);
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.syncMessage();
	} 
	
	public void syncMessage() {
		SdkHttpResult result = null;
		Date date=new Date();
		Date twoHoursDate = new Date(date.getTime() - 1000 * 60 * 60 * 2);
		DateFormat format=new SimpleDateFormat("yyyyMMddHH");
		String currentHours=format.format(twoHoursDate);
//		currentHours = "2016041821";
		System.out.println("尝试下载" + currentHours + "时段的消息记录");
		try {			
			result = ApiHttpClient.getMessageHistoryUrl(redCircleProperties.getRongCloudKey(), redCircleProperties.getRongCloudSecret(), currentHours,
					FormatType.json);
			System.out.println(result.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (result.getHttpCode() == 200) {
			String resultStr = result.getResult();
			HashMap resultMap = (HashMap) GsonUtil.fromJson(resultStr, HashMap.class);
			if(resultMap.get("url").toString().length() == 0) {
				return;
			}
	        try {
				this.downLoadFromUrl(resultMap.get("url").toString(), fileName, savePath);
				try {
					List<Object[]> messageArray = this.loadZipFile(savePath+fileName);
					System.out.println(messageArray.size());
		            int[] results = jdbcTemplate.batchUpdate("INSERT INTO t_red_message(appId, fromUserId, targetId, targetType, GroupId, classname, content ,dateTime ,msgUID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", messageArray);

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

    
//    public List<Object[]> readZipFile(String file) throws Exception {  
//		//        ZipFile zf = new ZipFile(file); 
//        ZipFile zf = new ZipFile(file, Charset.forName("UTF-8"));
//        InputStream in = new BufferedInputStream(new FileInputStream(file));  
//        ZipInputStream zin = new ZipInputStream(in); 
//        ZipEntry ze;  
//		List<Object[]> messageArray = new ArrayList<Object[]>();
//        while ((ze = zin.getNextEntry()) != null) {  
//            if (ze.isDirectory()) {
//            } else {  
//                System.err.println("file - " + ze.getName() + " : "  
//                        + ze.getSize() + " bytes");  
//                long size = ze.getSize();  
//                if (size > 0) {  
//                    BufferedReader br = new BufferedReader(  
//                            new InputStreamReader(zf.getInputStream(ze)));  
//                    String line;  
//                    while ((line = br.readLine()) != null) {  
//                        System.out.println(line); 
//            			HashMap messageMap = (HashMap) GsonUtil.fromJson(line.substring(19), HashMap.class);
////            			String messageContent = EmojiFilter.filterEmoji(messageMap.get("content").toString());
//            			
//            			String messageContent = messageMap.get("content").toString();
//            			Object[] message = {messageMap.get("appId"),messageMap.get("fromUserId"),messageMap.get("targetId"),messageMap.get("targetType"),messageMap.get("GroupId"),messageMap.get("classname"),messageContent,messageMap.get("dateTime"),messageMap.get("msgUID")};
//            			messageArray.add(message);
//                    }  
//                    br.close();  
//                }  
//                System.out.println();  
//            }  
//        }  
//        zin.closeEntry();  
//		return messageArray;
//
//    }

	public List<Object[]> loadZipFile(String zipname) {
//		zipname = System.getProperty("user.dir") + "/src/main/java/bbbbbbbb.zip";
		List<Object[]> messageArray = new ArrayList<Object[]>();

		try {
			ZipInputStream zin = new ZipInputStream(new FileInputStream(zipname));
			ZipEntry entry;
			System.out.println("");

			while ((entry = zin.getNextEntry()) != null) {
				// if (entry.getName().equals(name)) {
				BufferedReader in = new BufferedReader(new InputStreamReader(zin,Charset.forName("UTF-8")));
				String s;
				while ((s = in.readLine()) != null) {
					System.out.println(s + "\n");
					HashMap messageMap = (HashMap) GsonUtil.fromJson(s.substring(19), HashMap.class);
					 String messageContent = EmojiFilter.filterEmoji(messageMap.get("content").toString());
//					String messageContent = messageMap.get("content").toString();
//        			messageContent = "mopass不支持，我也没办法";
					if (messageContent.length() > 10000) {
						messageContent = "太长了，没有保存";
					}
					
					
					Object[] message = { messageMap.get("appId"), messageMap.get("fromUserId"),
							messageMap.get("targetId"), messageMap.get("targetType"), messageMap.get("GroupId"),
							messageMap.get("classname"), messageContent, messageMap.get("dateTime"),
							messageMap.get("msgUID") };
					messageArray.add(message);
				}
				// }
				zin.closeEntry();
			}
			zin.close();
		} catch (IOException e) {
		}

		return messageArray;

	}
 
}
