package com.cloud.redcircle;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(RedCircleProperties.PREFIX)
public class RedCircleProperties {
	
	public static final String PREFIX = "com.cloud.redcircle";

	private String rongCloudKey;
	
	private String rongCloudSecret;

	public String getRongCloudKey() {
		return rongCloudKey;
	}

	public void setRongCloudKey(String rongCloudKey) {
		this.rongCloudKey = rongCloudKey;
	}

	public String getRongCloudSecret() {
		return rongCloudSecret;
	}

	public void setRongCloudSecret(String rongCloudSecret) {
		this.rongCloudSecret = rongCloudSecret;
	}
	
	
	


}
