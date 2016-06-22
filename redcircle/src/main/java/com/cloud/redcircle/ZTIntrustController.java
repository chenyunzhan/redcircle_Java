package com.cloud.redcircle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ZTIntrustController {
	
	@RequestMapping(value="/x/mobsrv/login")
    public String login() {
		return "/user.json";

//		return getJson("login.json");
	}
	
	
	@RequestMapping(value="/x/mobsrv/user/info")
    public String info() {
		return "/info.json";
//		return getJson("info.json");
	}
	
	
	
	@RequestMapping(value="/x/mobsrv/proxys")
    public String proxys() {
		return "/proxys.json";
//		return getJson("proxys.json");
	}
	
	
	@RequestMapping(value="/x/mobsrv/tabbar")
    public String tabbar() {
		return "/tabbar.json";
//		return getJson("tabbar.json");
	}
	
	
	@RequestMapping(value="/x/mobsrv/homepage0")
    public String homepage() {
		return "/homepage.json";
//		return getJson("homepage.json");
	}
	
	
	@RequestMapping(value="/x/mobsrv/customer")
    public String customer() {
		return "/customer.json";
//		return getJson("customer.json");
	}
	
	
	@RequestMapping(value="/x/mobsrv/product")
    public String product() {
		return "/product.json";
//		return getJson("product.json");
	}
	
	
	@RequestMapping(value="/x/mobsrv/customer/view")
    public String customerView() {
		return "/customerView.json";
//		return getJson("customerView.json");
	}
	
	
	@RequestMapping(value="/x/mobsrv/product/booking")
    public String preBooking() {
		return "/selectProduct.json";
//		return getJson("selectProduct.json");
	}
	
	
	@RequestMapping(value="/x/mobsrv/product/selectContract")
    public String selectContract() {
		return "/selectContract.json";
//		return getJson("selectContract.json");
	}
	
	
	
	@RequestMapping(value="/x/mobsrv/product/selectCustomer")
    public String selectCustomer() {
		return "/selectCustomer.json";
//		return getJson("selectCustomer.json");
	}
	
	
	
	
	
	
	public String getJson (String name) {

		 String fullFileName = "F:\\redcircle-0.0.1-SNAPSHOT\\" + name;
//		 String fullFileName = "/Users/zhan/Desktop/cloud/redcircle/redcircleserver/maven.1459818988790/redcircle/src/main/webapp/" + name;
		 System.out.println(fullFileName);
	        
	        File file = new File(fullFileName);
	        Scanner scanner = null;
	        StringBuilder buffer = new StringBuilder();
	        try {
	            scanner = new Scanner(file, "utf-8");
	            while (scanner.hasNextLine()) {
	                buffer.append(scanner.nextLine());
	            }
	 
	        } catch (FileNotFoundException e) {
	            // TODO Auto-generated catch block  
	 
	        } finally {
	            if (scanner != null) {
	                scanner.close();
	            }
	        }
	         
	        System.out.println(buffer.toString());
//			return "/agreement.html";
			return buffer.toString();
	}

}
