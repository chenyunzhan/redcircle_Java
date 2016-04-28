package com.cloud.redcircle;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReportController {
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	@RequestMapping(value="/reportUser", method=RequestMethod.POST)
	@ResponseBody
    public String register(@RequestBody HashMap<String, Object> reportMap) {
		
		
		final Map<String, String> report = (Map<String, String>) reportMap.get("report");
        
		int[] results;
        try {
        	results = jdbcTemplate.batchUpdate("INSERT INTO t_red_report_user(report_type, user_phone) VALUES (?, ?)", new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, report.get("type"));
//					ps.setString(2, report.get("name"));
					ps.setString(2, report.get("userPhone"));
				}
				
				@Override
				public int getBatchSize() {
					return 1;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"success\":false, \"msg\":\"举报失败\"}";
		}
        
        if (results.length >0) {
    		return "{\"success\":true, \"msg\":\"举报成功\"}";

        } else {
        	return "{\"success\":false, \"msg\":\"举报失败\"}";
        }

    }
}
