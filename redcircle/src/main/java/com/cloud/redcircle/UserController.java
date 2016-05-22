package com.cloud.redcircle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
		Object[] meArray = {meInfo.get("me_phone"),meInfo.get("me_phone"), "36500"};
		newFriendArray.add(meArray);
		for (Iterator<HashMap<String, String>> iterator = friendArray.iterator(); iterator.hasNext();) {
			HashMap<String, String> object = (HashMap<String, String>) iterator.next();
			object.remove("verify_code_text");
			object.put("me_phone", meInfo.get("me_phone"));
			object.put("intimacy", "36500");
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
		int result;
        try {
        	result = jdbcTemplate.update("INSERT INTO t_red_user(me_phone) VALUES (?)", meInfo.get("me_phone"));
            results = jdbcTemplate.batchUpdate("INSERT INTO t_red_me_friend(friend_phone, me_phone, intimacy) VALUES (?, ?, ?)", newFriendArray);
		} catch (Exception e) {
			return "{\"success\":false, \"msg\":\"注册失败\"}";
		}
        
        if (results.length >0 && result>0) {
    		return "{\"success\":true, \"msg\":\"注册成功\"}";

        } else {
        	return "{\"success\":false, \"msg\":\"注册失败\"}";
        }

    }
	
	
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	@ResponseBody
    public String modify(@RequestBody HashMap<String, Object> meMap) {
		
		
		String mePhone = (String) meMap.get("mePhone");
		
		String paramKey = null;
		String paramValue = null;
		if (meMap.get("sex") != null) {
			paramKey = "sex";
			paramValue = (String) meMap.get("sex");
		} else if (meMap.get("name") != null) {
			paramKey = "name";
			paramValue = (String) meMap.get("name");
		}

		
		int[] results = jdbcTemplate.batchUpdate("update t_red_user set " + paramKey + " = '" + paramValue + "' where me_phone = " + mePhone);

        if (results.length >0) {
    		return "{\"success\":true, \"msg\":\"修改成功\"}";

        } else {
        	return "{\"success\":false, \"msg\":\"修改失败\"}";
        }
		

    }
	
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
    public User login(@RequestBody HashMap<String, Object> mePhoneMap) {
		
		
		String mePhone = (String) mePhoneMap.get("mePhone");

		
//		jdbcTemplate.query(
//                "SELECT id, first_name, last_name FROM customers WHERE first_name = ?", new Object[] { "Josh" },
//                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))
//        ).forEach(customer -> log.info(customer.toString()));
		
		List<Map<String, Object>> results = jdbcTemplate.queryForList("select * from t_red_user where me_phone = ? limit 0, 1", new Object[] { mePhone });
		List<User> userList = new ArrayList<User>();
		for (Iterator<Map<String, Object>> iterator = results.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			userList.add(new User(map.get("me_phone").toString(), "", map.get("sex").toString(),map.get("name").toString()));
		}
		
//		List<String> results = jdbcTemplate.queryForList("select * from t_red_user where me_phone = ? limit 0, 1", new Object[] { mePhone }, java.lang.String.class);
		
//		List<User> results  = jdbcTemplate.query("select * from t_red_user where me_phone = ? limit 0, 1", new Object[] { mePhone }, (rs,rowNum)-> new User(rs.getString("me_phone"),rs.getString("friend_phone"),rs.getString("sex"),rs.getString("name")));
		if (userList.size() > 0) {
			return userList.get(0);
		}
		return null;
		

    }
	
	
	@RequestMapping(value="/getFriends")
	@ResponseBody
    public List<Object> getFriends(@RequestParam HashMap<String, Object> mePhoneMap) {
		String mePhone = (String) mePhoneMap.get("mePhone");
		
		
		List<Object> ffriendList = new ArrayList<Object>();
//		List<User> results  = jdbcTemplate.query("select * from t_red_user where me_phone = ?", new Object[] { mePhone }, (rs,rowNum)-> new User(rs.getString("me_phone"),rs.getString("friend_phone"),rs.getString("sex"),rs.getString("name")));
		
		List<Map<String, Object>> results = jdbcTemplate.queryForList("SELECT DISTINCT    c.name, b.friend_phone, b.intimacy FROM    t_red_user a        LEFT JOIN    t_red_me_friend b ON a.me_phone = b.me_phone        LEFT JOIN    t_red_user c ON b.friend_phone = c.me_phone WHERE b.intimacy > 0 and    a.me_phone = ?", new Object[] { mePhone });
		List<User> userList = new ArrayList<User>();
		for (Iterator<Map<String, Object>> iterator = results.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			userList.add(new User(null,map.get("friend_phone").toString(),null,map.get("name") == null ? "" : map.get("name").toString()));
		}
		
		
		for (Iterator<User> iterator = userList.iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			
			
			List<Map<String, Object>> ffResults = jdbcTemplate.queryForList("SELECT DISTINCT    c.name, b.friend_phone, b.intimacy FROM    t_red_user a        LEFT JOIN    t_red_me_friend b ON a.me_phone = b.me_phone        LEFT JOIN    t_red_user c ON b.friend_phone = c.me_phone WHERE  b.intimacy > 0 and  a.me_phone = ?", new Object[] { user.getFriendPhone() });
			List<User> ffriend = new ArrayList<User>();
			for (Iterator<Map<String, Object>> iterator1 = ffResults.iterator(); iterator1.hasNext();) {
				Map<String, Object> map = (Map<String, Object>) iterator1.next();
				ffriend.add(new User(map.get("friend_phone").toString(),null,null,map.get("name") == null ? "" : map.get("name").toString()));
			}
//			List<User> ffriend  = jdbcTemplate.query("select distinct a.friend_phone, b.name from t_red_user a left join t_red_user b on a.friend_phone = b.me_phone where a.me_phone = ?", new Object[] { user.getFriendPhone() }, (rs,rowNum)-> new User(rs.getString("friend_phone"),null,null,rs.getString("name")));
			Map<String,Object> friendMap = new HashMap<String,Object>();
			friendMap.put("friend", user);
			friendMap.put("ffriend", ffriend);
			ffriendList.add(friendMap);
		}
		return ffriendList;
	}
	
	
	@RequestMapping(value="/addFriend", method=RequestMethod.POST)
	@ResponseBody
    public String addFriend(@RequestBody HashMap<String, Object> mePhoneMap) {
		
		String mePhone = (String) mePhoneMap.get("mePhone");
		String friendPhone = (String) mePhoneMap.get("friendPhone");

		
		int result = jdbcTemplate.update("INSERT INTO t_red_me_friend(me_phone, friend_phone, intimacy) VALUES (?,?,?)", mePhone, friendPhone, "36500");

		if (result>0) {
    		return "{\"success\":true, \"msg\":\"添加成功\"}";

        } else {
        	return "{\"success\":false, \"msg\":\"添加失败\"}";
        }
		
	}

}
