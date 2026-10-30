package com.pratikshat.exicom.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.pratikshat.exicom.entity.LoginUser;
@Service
public class LocationNativeSerivceImpl implements LocationNativeSerivce {

	@Autowired
	JdbcTemplate jdbc;
	
	@Override
	public List<Map<String, Object>> getLocationParentAndSiblings(LoginUser user) {
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> parentLocations=jdbc.queryForList("SELECT  \r\n" + 
				"	loc.`location_id` as `locationId`,\r\n" + 
				"	loc.`location_name` as `locationName`,\r\n" + 
				"	loc.`parent_location_location_id`\r\n" + 
				"FROM `service_recource` sr JOIN `location` loc ON sr.`location_location_id`=loc.`location_id` WHERE sr.`user_email_id`=?",user.getUsername());
		result.addAll(parentLocations);
		for(Map<String,Object> pr:parentLocations) {
			if(pr.get("parent_location_location_id")!=null) {
				List<Map<String,Object>> siblings=jdbc.queryForList("SELECT loc.`location_id` as `locationId`,\r\n" + 
						"	loc.`location_name` as `locationName`,\r\n" + 
						"	loc.`parent_location_location_id` \r\n" + 
						"	FROM `location` loc where loc.parent_location_location_id=?",pr.get("parent_location_location_id"));
				if(siblings.size()>0) {
					result.addAll(siblings);
				}
				
				List<Map<String,Object>> pLocation=jdbc.queryForList("SELECT loc.`location_id` as `locationId`,\r\n" + 
						"	loc.`location_name` as `locationName`,\r\n" + 
						"	loc.`parent_location_location_id` \r\n" + 
						"	FROM `location` loc where loc.`location_id`=?",pr.get("parent_location_location_id"));
				if(pLocation!=null&&pLocation.size()>0)
					result.add(pLocation.get(0));
			}
				
			
			
			List<Map<String,Object>> childs=jdbc.queryForList("SELECT loc.`location_id` as `locationId`,\r\n" + 
					"	loc.`location_name` as `locationName`,\r\n" + 
					"	loc.`parent_location_location_id` \r\n" + 
					"	FROM `location` loc where loc.parent_location_location_id=?",pr.get("locationId"));
			if(childs.size()>0)
				result.addAll(childs);
			
			
		}
		
		
		
		return result;
	}

}
