package com.cloud.redcircle;

import java.text.SimpleDateFormat;
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

    @Scheduled(cron="0 46 16 * * ?")
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
	        	    "    GROUP BY fromUserId , targetId " +
	        	    "    ORDER BY dateTime DESC) a " +
	        	    " WHERE " +
	        	    "    a.messageCount > 10; ";
        
		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
		
		
		Set set = new HashSet();
		for (Iterator iterator = results.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			set.add(map.get("fromUserId"));
		}
		
		String usersSQL = set.toString().replace("[", "(").replace("]", ")");
		List<Map<String, Object>> results2 = jdbcTemplate.queryForList("select distinct me_phone, sex, name from t_red_user where me_phone in " + usersSQL);

		
		
		for (Iterator iterator = results.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			String fromUserId = (String) map.get("fromUserId");
			String targetId = (String) map.get("targetId");
			
			
			for (Iterator iterator2 = results.iterator(); iterator2.hasNext();) {
				Map<String, Object> map2 = (Map<String, Object>) iterator2.next();
				String fromUserId2 = (String) map.get("fromUserId");
				String targetId2 = (String) map.get("targetId");
				
				if(fromUserId.equals(targetId2) && targetId.equals(fromUserId2)) {
					
					Boolean isContain1 = true;
					Boolean isContain2 = true;

					for (Iterator iterator3 = results2.iterator(); iterator3.hasNext();) {
						Map<String, Object> map3 = (Map<String, Object>) iterator3.next();
						if (!(fromUserId.equals(map3.get(""))) && !(targetId.equals(map3.get("")))) {
							isContain1 = false;
						}
						if (!(fromUserId2.equals(map3.get(""))) && !(targetId2.equals(map3.get("")))) {
							isContain2 = false;
						}
					}
//		            int[] results3 = jdbcTemplate.batchUpdate("INSERT INTO t_red_user(friend_phone, me_phone) VALUES (?, ?)", newFriendArray);

				}
				
			}

			
		}
		
		

    }
}