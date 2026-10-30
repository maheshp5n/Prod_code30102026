package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.pratikshat.exicom.entity.LoginUser;

@Service
public class SummeryPageMetaService {
	@Autowired
	JdbcTemplate template;
	
	public List<Map<String,Object>> getSummaryPageFilter(String pageurl,LoginUser user){
		
		return template.queryForList("SELECT * FROM `user_summary_page_filter` upf \r\n" + 
				"	JOIN user_navigation_module unav ON upf.nav_module_id=unav.`id`\r\n" + 
				"	JOIN `summary_page_filter` smmry ON upf.page_filter_id=smmry.id\r\n" + 
				"	JOIN `uinavigation_modules` mnv ON mnv.`ui_modules_id`=unav.`navigation_modules_ui_modules_id`\r\n" + 
				"	JOIN employee_details emp ON unav.`user_profile_id`=emp.`user_profile_id`\r\n" + 
				
				"	WHERE emp.`email_id`=? AND mnv.`url` = ?",new Object[] {user.getUsername(),pageurl});
	}
}
