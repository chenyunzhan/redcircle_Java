package com.cloud.redcircle;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.rong.ApiHttpClient;
import io.rong.models.FormatType;
import io.rong.models.SdkHttpResult;




@Controller
public class MessageController {
	
	
    @Autowired  
    RedCircleProperties redCircleProperties; 
	
	
	@RequestMapping(value="/getRongCloudToken")
	@ResponseBody
    public String getFriends(@RequestParam HashMap<String, Object> meMap) {
		String mePhone = (String) meMap.get("mePhone");
		String name = (String) meMap.get("name");

		SdkHttpResult result = null;

		//获取token
		try {
			result = ApiHttpClient.getToken(redCircleProperties.getRongCloudKey(), redCircleProperties.getRongCloudSecret(), mePhone, name,
					"http://aa.com/a.png", FormatType.json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("gettoken=" + result);
		return result.toString();
	}
}
