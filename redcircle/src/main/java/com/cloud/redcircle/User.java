package com.cloud.redcircle;

public class User {
	
	private String mePhone;
	private String friendPhone;
	private String sex;
	private String name;
	private String intimacy;
	
	
	public User(String mePhone, String friendPhone, String sex, String name, String intimacy) {
		super();
		this.mePhone = mePhone;
		this.friendPhone = friendPhone;
		this.sex = sex;
		this.name = name;
		this.intimacy = intimacy;
	}


	public String getMePhone() {
		return mePhone;
	}


	public void setMePhone(String mePhone) {
		this.mePhone = mePhone;
	}


	public String getFriendPhone() {
		return friendPhone;
	}


	public void setFriendPhone(String friendPhone) {
		this.friendPhone = friendPhone;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getIntimacy() {
		return intimacy;
	}


	public void setIntimacy(String intimacy) {
		this.intimacy = intimacy;
	}
	
	
	
}
