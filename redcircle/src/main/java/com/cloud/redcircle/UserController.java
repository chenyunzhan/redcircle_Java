package com.cloud.redcircle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	@ResponseBody
    public String register(@RequestBody HashMap<String, Object> friendArrayMap) {
		
		
		List<HashMap<String, String>> friendArray = (List<HashMap<String, String>>) friendArrayMap.get("friendArrayMap");
		Map<String, String> meInfo = (Map<String, String>) friendArrayMap.get("meInfo");
		List<Object[]> newFriendArray = new ArrayList<Object[]>();
		
		
		for (Iterator<HashMap<String, String>> iterator = friendArray.iterator(); iterator.hasNext();) {
			HashMap<String, String> object = (HashMap<String, String>) iterator.next();
			object.remove("verify_code_text");
			object.put("me_phone", meInfo.get("me_phone"));
			List<String> valueList = new ArrayList<String>(object.values());
			Object[] valueArray = valueList.toArray();
			newFriendArray.add(valueArray);
		}
//		
//        List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
//                .map(name -> name.split(" "))
//                .collect(Collectors.toList());
//        
//        splitUpNames = (List<Object[]>) friendArrayMap.get("friendArrayMap");
//        
        
        
        
        
        jdbcTemplate.batchUpdate("INSERT INTO t_red_user(friend_phone, me_phone) VALUES (?, ?)", newFriendArray);

        return "greeting";
    }

}
