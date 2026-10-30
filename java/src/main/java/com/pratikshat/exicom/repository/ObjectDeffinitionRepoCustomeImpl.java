package com.pratikshat.exicom.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.LoginUserRoles;
import com.pratikshat.exicom.entity.UserRoles;

public class ObjectDeffinitionRepoCustomeImpl implements ObjectDeffinitionRepoCustome {

	@Autowired
	JdbcTemplate db;
	
	@Override
	public List<Map<String, Object>> executeQuery(String query, LoginUser user) {
		
		Iterator<LoginUserRoles> ite= (Iterator<LoginUserRoles>)user.getAuthorities().iterator();
		while(ite.hasNext()) {
			LoginUserRoles rl=ite.next();
			if(rl.getAuthority().equalsIgnoreCase("Admin")) {
				query=query.trim();
				db.update("insert into query_browser_execution_log(execution_by_email_id,query,execution_date) values(?,?,now()) ", user.getUsername(),query);
				if(query.startsWith("insert")||query.startsWith("update")||query.startsWith("delete")) {
					
					int row=db.update(query);
					Map<String,Object> s=new HashMap<String,Object>();
					s.put("message",String.format(" %d row affected", row));
					List<Map<String,Object>> ls=new ArrayList<Map<String,Object>>();
					ls.add(s);
					return ls;
				}
				return db.queryForList(query);
			}
		}
		
		return new ArrayList<Map<String,Object>>();
	}

	

}
