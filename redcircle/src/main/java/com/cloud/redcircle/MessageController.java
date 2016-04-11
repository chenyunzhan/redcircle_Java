package com.cloud.redcircle;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.rong.ApiHttpClient;
import io.rong.models.FormatType;
import io.rong.models.SdkHttpResult;

@Controller
public class MessageController {
	
	String key = "qf3d5gbj3ufqh";//替换成您的appkey
	String secret = "Aqccu1B5d4f";//替换成匹配上面key的secret
	
	
	@RequestMapping(value="/getRongCloudToken")
	@ResponseBody
    public String getFriends(@RequestParam HashMap<String, Object> meMap) {
		String mePhone = (String) meMap.get("mePhone");
		SdkHttpResult result = null;

		//获取token
		try {
			result = ApiHttpClient.getToken(key, secret, mePhone, "asdfa",
					"http://aa.com/a.png", FormatType.json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("gettoken=" + result);
		return result.toString();
	}
}
