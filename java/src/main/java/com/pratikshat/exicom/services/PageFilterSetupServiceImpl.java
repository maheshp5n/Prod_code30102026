package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PageFilterSetupServiceImpl implements PageFilterSetupService {
	@Autowired
	JdbcTemplate db;

	@Override
	public List<Map<String, Object>> getNaviationModules() {
		
		return db.queryForList("SELECT * FROM uinavigation_modules WHERE parent_ui_modules_id IS NOT NULL ORDER BY `name`");
	}

	@Override
	public List<Map<String, Object>> getProfiles() {
		
		return db.queryForList("SELECT * FROM user_profile");
	}

	@Override
	public List<Map<String, Object>> getFilters(long navId, long profileId) {
		
		return db.queryForList("SELECT sm.`fileter_name`,sm.`filter_param`,sm.`id` AS sid,sm.`navigation_module_ui_modules_id`,usm.id AS usmid FROM `summary_page_filter` sm LEFT JOIN `user_summary_page_filter` usm ON usm.page_filter_id=sm.`id` AND usm.user_profile_id=? WHERE sm.`navigation_module_ui_modules_id`=?",new Object[] {profileId,navId});
	}

	@Override
	public void saveProfileFilters(List<Map<String, Object>> idsFilter) {
		for(Map<String,Object> ms:idsFilter) {
			List<Map<String,Object>> exist=null;
			try {
				exist=db.queryForList("select * from user_summary_page_filter where page_filter_id=? and user_profile_id=?",new Object[] {ms.get("sid"),ms.get("profileId")});
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			boolean b=false;
			if(ms.get("selected")!=null)
			 b=(boolean) ms.get("selected");//Boolean.getBoolean(ms.get("selected").toString());
			
			if(!b) {
				if(exist!=null)
				{
					for(Map<String,Object> oc:exist) {
						db.update("delete from user_summary_page_filter_pin where filter_id=?",oc.get("id"));
						db.update("delete from user_summary_page_filter where id=?",oc.get("id"));
					}
				}
			}
			else {
				
				System.out.println("IT IS SELECTED");
				
				if(exist==null||exist.size()==0) {
					
					db.update("insert into user_summary_page_filter(page_filter_id,user_profile_id) values(?,?)",ms.get("sid"),ms.get("profileId"));
				}
			}
		}
	}
}
