package com.cloud.redcircle;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserService extends TimerTask{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.calculateIntimacy();
	}
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	public void setIntimacyTimer() {
		Timer timer = new Timer(); 
	     timer.schedule(this, 5 * 1000, 60 * 60 * 1000 * 24);
	}

	
	public void calculateIntimacy () {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date()); 
		int week=calendar.get(Calendar.DAY_OF_WEEK)-1;
		if (week == 2) {
				
		}
	}
}
