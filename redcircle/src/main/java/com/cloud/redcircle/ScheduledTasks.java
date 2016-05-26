package com.cloud.redcircle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron="0 01 16 * * ?")
    public void reportCurrentTime() {
        System.out.println("The time is now " + dateFormat.format(new Date()));
        
        String sql =" SELECT " +
        	    	"   * " +
        	    	" FROM " +
	        	    "    (SELECT " +
	        	    "        COUNT(*) messageCount, fromUserId, targetId " +
	        	    "    FROM " +
	        	    "        t_red_message " +
	        	    "    WHERE " +
	        	    "       classname != 'RC:ReadNtf' " +
	        	    "            AND dateTime >= NOW() - INTERVAL 1 DAY " +
	        	    "    GROUP BY fromUserId , targetId ) a " +
	        	    " WHERE " +
	        	    "    a.messageCount > 10; ";
        
		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
		
		
//		Set set = new HashSet();
//		for (Iterator iterator = results.iterator(); iterator.hasNext();) {
//			Map<String, Object> map = (Map<String, Object>) iterator.next();
//			set.add(map.get("fromUserId"));
//		}
//		
//		String usersSQL = set.toString().replace("[", "(").replace("]", ")");
//		List<Map<String, Object>> results2 = jdbcTemplate.queryForList("select distinct me_phone, sex, name from t_red_user where me_phone in " + usersSQL);
		List<String> sqlList = new ArrayList<String>();
		
		sqlList.add("UPDATE t_red_me_friend SET intimacy = intimacy - 1 where intimacy > 0");
		
		for (Iterator iterator = results.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			String fromUserId = (String) map.get("fromUserId");
			String targetId = (String) map.get("targetId");
			
			
			for (Iterator iterator2 = results.iterator(); iterator2.hasNext();) {
				Map<String, Object> map2 = (Map<String, Object>) iterator2.next();
				String fromUserId2 = (String) map2.get("fromUserId");
				String targetId2 = (String) map2.get("targetId");
				
				if(fromUserId.equals(targetId2) && targetId.equals(fromUserId2)) {
					
					List<Map<String, Object>> results2 = jdbcTemplate.queryForList("select * from t_red_me_friend WHERE me_phone = " + fromUserId + " AND friend_phone = " + targetId);
					if (results2.size()==0) {
						String sql2 = "INSERT INTO t_red_me_friend(me_phone, friend_phone) VALUES ('" + fromUserId + "', '" + targetId + "'); ";
						String sql3 = "UPDATE t_red_me_friend SET intimacy = 10 WHERE me_phone = " + fromUserId + " AND friend_phone = " + targetId + "; ";
						sqlList.add(sql2);
						sqlList.add(sql3);
					} else {
						String sql3 = "UPDATE t_red_me_friend SET intimacy = intimacy + 10 WHERE me_phone = " + fromUserId + " AND friend_phone = " + targetId + "; ";
						sqlList.add(sql3);
					}
				}
				
			}

			
		}
		
		
		String[] sqls = new String[sqlList.size()];
		
		for (int i = 0; i < sqlList.size(); i++) {
			sqls[i] = sqlList.get(i);
		}
		
		
		int[] result = jdbcTemplate.batchUpdate(sqls);
		
		

    }
}