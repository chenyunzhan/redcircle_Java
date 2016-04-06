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
import org.springframework.web.bind.annotation.RequestParam;
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
		Object[] meArray = {meInfo.get("me_phone"),meInfo.get("me_phone")};
		newFriendArray.add(meArray);
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
        
        
		int[] results;
        try {
            results = jdbcTemplate.batchUpdate("INSERT INTO t_red_user(friend_phone, me_phone) VALUES (?, ?)", newFriendArray);
		} catch (Exception e) {
			return "{\"success\":false, \"msg\":\"注册失败\"}";
		}
        
        if (results.length >0) {
    		return "{\"success\":true, \"msg\":\"注册成功\"}";

        } else {
        	return "{\"success\":false, \"msg\":\"注册失败\"}";
        }

    }
	
	
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
    public String login(@RequestBody HashMap<String, Object> mePhoneMap) {
		
		
		String mePhone = (String) mePhoneMap.get("mePhone");

		
//		jdbcTemplate.query(
//                "SELECT id, first_name, last_name FROM customers WHERE first_name = ?", new Object[] { "Josh" },
//                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))
//        ).forEach(customer -> log.info(customer.toString()));
		
		List<User> results  = jdbcTemplate.query("select me_phone from t_red_user where me_phone = ?", new Object[] { mePhone }, (rs,rowNum)-> new User(null, null, null, null));
			
		if (results.size() > 0) {
			return "{\"success\":true, \"msg\":\"登录成功\"}";
		}
		return "{\"success\":false, \"msg\":\"登录失败\"}";
    }
	
	
	@RequestMapping(value="/getFriends")
	@ResponseBody
    public List<Object> getFriends(@RequestParam HashMap<String, Object> mePhoneMap) {
		String mePhone = (String) mePhoneMap.get("mePhone");
		
		
		List<Object> ffriendList = new ArrayList<Object>();
		List<User> results  = jdbcTemplate.query("select * from t_red_user where me_phone = ?", new Object[] { mePhone }, (rs,rowNum)-> new User(rs.getString("me_phone"),rs.getString("friend_phone"),rs.getString("sex"),rs.getString("name")));
		for (Iterator<User> iterator = results.iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			List<User> ffriend  = jdbcTemplate.query("select * from t_red_user where me_phone = ?", new Object[] { user.getFriendPhone() }, (rs,rowNum)-> new User(rs.getString("me_phone"),rs.getString("friend_phone"),rs.getString("sex"),rs.getString("name")));
			Map<String,Object> friendMap = new HashMap<String,Object>();
			friendMap.put("friend", user);
			friendMap.put("ffriend", ffriend);
			ffriendList.add(friendMap);
		}
		return ffriendList;
	}

}
