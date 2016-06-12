package com.cloud.redcircle;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ArticleController {
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
    @Autowired  
    RedCircleProperties redCircleProperties;
    
    
    
    
	@RequestMapping(method = RequestMethod.POST, value = "/addArticle")
	@ResponseBody
	public String handleFileUpload(@RequestParam("mePhone") String mePhone,
									@RequestParam("content") String content,
								   @RequestParam("sourceList") MultipartFile[]  sourceList,
								   @RequestParam("thumbList") MultipartFile[] thumbList,
								   RedirectAttributes redirectAttributes) {
		if (mePhone.contains("/")) {
			redirectAttributes.addFlashAttribute("message", "Folder separators not allowed");
			return "redirect:/";
		}
		if (mePhone.contains("/")) {
			redirectAttributes.addFlashAttribute("message", "Relative pathnames not allowed");
			return "redirect:/";
		}
		
		
		StringBuffer imagesSB = new StringBuffer();


		for(int i=0; i<sourceList.length;i++) {
			MultipartFile file = sourceList[i];
			MultipartFile thumbnail = thumbList[i];
			
	        UUID uuid = UUID.randomUUID();
	        
	        imagesSB.append(uuid+"#");


			if (!file.isEmpty() && !thumbnail.isEmpty()) {
				try {
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(new File(redCircleProperties.getMopaasNFS() + "/" + uuid)));
	                FileCopyUtils.copy(file.getInputStream(), stream);
					stream.close();
					
					
					
					BufferedOutputStream stream2 = new BufferedOutputStream(
							new FileOutputStream(new File(redCircleProperties.getThumbnail() + "/" + uuid)));
	                FileCopyUtils.copy(thumbnail.getInputStream(), stream2);
					stream2.close();
					redirectAttributes.addFlashAttribute("message",
							"You successfully uploaded " + mePhone + "!");
					
					
					System.out.println("{\"success\":true, \"msg\":\"上传成功\"}");

				}
				catch (Exception e) {
					redirectAttributes.addFlashAttribute("message",
							"You failed to upload " + mePhone + " => " + e.getMessage());
					System.out.println("{\"success\":false, \"msg\":\"上传失败\"}");


				}
			}
			else {
				redirectAttributes.addFlashAttribute("message",
						"You failed to upload " + mePhone + " because the file was empty");
				System.out.println("{\"success\":false, \"msg\":\"上传失败\"}");


			}
		}
		
		String type = "10";
		if (imagesSB.length() > 0) {
			if(sourceList.length == 1) {
				type = "9";
			} else {
				type = "11";
			}
		}
		
		
		Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 0);
		
//		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
		String dateStr = dateFormat.format(calendar.getTime());

		
		int result = jdbcTemplate.update("INSERT INTO t_red_article(id, content, type, images, created_at, created_by, updated_at) VALUES (?,?,?,?,?,?)", UUID.randomUUID().toString(), content, type, imagesSB.toString(), dateStr,  mePhone, dateStr);

		if (result>0) {
    		return "{\"success\":true, \"msg\":\"添加成功\"}";

        } else {
        	return "{\"success\":false, \"msg\":\"添加失败\"}";
        }
		
		

	}
	
	
	
	
	@RequestMapping(value="/getArticles")
	@ResponseBody
    public List<Map<String, Object>> getArticles(@RequestParam HashMap<String, Object> mePhoneMap) {
		String mePhone = (String) mePhoneMap.get("mePhone");
		
				
		List<Map<String, Object>> results = jdbcTemplate.queryForList("SELECT * FROM redcircle.t_red_article where created_by = ?", new Object[] { mePhone });
//		List<User> articleList = new ArrayList<User>();
//		for (Iterator<Map<String, Object>> iterator = results.iterator(); iterator.hasNext();) {
//			Map<String, Object> map = (Map<String, Object>) iterator.next();
//			articleList.add(new User(null,map.get("friend_phone").toString(),null,map.get("name") == null ? "" : map.get("name").toString(), map.get("intimacy").toString()));
//		}
		
		
		return results;
	}

}
