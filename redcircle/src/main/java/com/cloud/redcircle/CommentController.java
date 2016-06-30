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
public class CommentController {
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
    @Autowired  
    RedCircleProperties redCircleProperties;
    
    
    
    
	@RequestMapping(method = RequestMethod.POST, value = "/addComment")
	@ResponseBody
	public List<Map<String, Object>> handleFileUpload(@RequestParam("articleId") String articleId,
									@RequestParam("content") String content,
									@RequestParam("commentBy") String commentBy,
									@RequestParam("commentTo") String commentTo) {

		
		
		Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 0);
		
//		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
		String dateStr = dateFormat.format(calendar.getTime());

		
		int result = jdbcTemplate.update("INSERT INTO t_red_comment(id, article_id, content, commenter_by, commenter_to, created_at, updated_at) VALUES (?,?,?,?,?,?,?)", UUID.randomUUID().toString(),articleId, content, commentBy, commentTo, dateStr, dateStr);

		if (result>0) {
//    		return "{\"success\":true, \"msg\":\"添加成功\"}";
    		
			String commentSQL = "select a.*, b.name as commenter_by_name, c.name as commenter_to_name from t_red_comment a left join t_red_user b on a.commenter_by = b.me_phone left join t_red_user c on a.commenter_to = c.me_phone where a.article_id =  ?";

			List<Map<String, Object>> comments = jdbcTemplate.queryForList(commentSQL, new Object[] { articleId });
			return comments;

        } else {
//        	return "{\"success\":false, \"msg\":\"添加失败\"}";
        	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        	return list;
        }
		
		

	}
	
	
	

}
