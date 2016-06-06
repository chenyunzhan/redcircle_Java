package com.cloud.redcircle;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
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
	public String handleFileUpload(@RequestParam("mePhone") String name,
									@RequestParam("content") String content,
								   @RequestParam("sourceList") CommonsMultipartFile[]  sourceList,
								   @RequestParam("thumbList") CommonsMultipartFile[] thumbList,
								   RedirectAttributes redirectAttributes) {
		if (name.contains("/")) {
			redirectAttributes.addFlashAttribute("message", "Folder separators not allowed");
			return "redirect:/";
		}
		if (name.contains("/")) {
			redirectAttributes.addFlashAttribute("message", "Relative pathnames not allowed");
			return "redirect:/";
		}


		for(int i=0; i<sourceList.length;i++) {
			MultipartFile file = sourceList[i];
			MultipartFile thumbnail = thumbList[i];
			
	        UUID uuid = UUID.randomUUID();


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
							"You successfully uploaded " + name + "!");
					
					
					System.out.println("{\"success\":true, \"msg\":\"上传成功\"}");

				}
				catch (Exception e) {
					redirectAttributes.addFlashAttribute("message",
							"You failed to upload " + name + " => " + e.getMessage());
					System.out.println("{\"success\":false, \"msg\":\"上传失败\"}");


				}
			}
			else {
				redirectAttributes.addFlashAttribute("message",
						"You failed to upload " + name + " because the file was empty");
				System.out.println("{\"success\":false, \"msg\":\"上传失败\"}");


			}
		}
		
		
		return null;

	}

}
