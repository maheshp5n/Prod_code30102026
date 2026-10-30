package com.pratikshat.exicom.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.repository.UserProfileRepository;

@Service
public class GlobalSearchFilterServiceImpl implements GlobalSearchFilterService {
	@Autowired
	JdbcTemplate jdbc;
	@Autowired
	UserProfileRepository dbProfile;
	final static Logger LOGGER=LogManager.getLogger(GlobalSearchFilterServiceImpl.class);
	@Override
	public Map<String, List> getSearchFilter(String searchKey, LoginUser user) {
		List<Map<String,Object>> accessObjects=getAuthoriseObjects(user.getUsername());
		
		ExecutorService executer=Executors.newFixedThreadPool(10);
		
		List<Map<String,List>> result=new ArrayList<Map<String,List>>();
		Map<String,List> fReult=new HashMap<String,List>();
		boolean progress=true;
		
		List<String> cUsers=dbProfile.getListOfUserAndChildByUser(user.getUsername());
		
		List<FutureTask<Map<String,List>>> fTak=new ArrayList<FutureTask<Map<String,List>>>();
		
		for(Map<String,Object> ob:accessObjects) {
			FutureTask<Map<String,List>> tk= new FutureTask<Map<String,List>>(new SearchIn(ob.get("boject_label_db_name").toString(),searchKey,ob.get("object_lable_name").toString(),ob.get("object_name").toString(),cUsers));
			fTak.add(tk);
			executer.submit(tk);
		}
		for(FutureTask<Map<String,List>> tk:fTak ) {
			try {
				Map<String,List> d=tk.get();
				Set<String> c= d.keySet();
				for(String _c:c) {
					fReult.put(_c, d.get(_c));
				}
				//result.add(tk.get());
			} catch (InterruptedException e) {
				//e.printStackTrace();
			} catch (ExecutionException e) {
				//e.printStackTrace();
			}
		}
		executer.shutdown();
		return fReult;
	}
	
	private List<Map<String,Object>> getAuthoriseObjects(String userEmail){
		
		return jdbc.queryForList("SELECT obj.`object_name`,obj.`object_lable_name`,obj.`boject_label_db_name`,obj.`object_url` FROM `object_definition` obj\r\n" + 
				"	JOIN `user_profile_object_access` objacc ON objacc.`object_object_name`=obj.`object_name` AND (objacc.`view_all_rights`=1 OR objacc.`view_rights`=1)\r\n" + 
				"	JOIN employee_details emp ON emp.`user_profile_id`=objacc.`user_profile_id` WHERE emp.`email_id`=?",new Object[] {userEmail});
	}
	@Override
	public List<Map<String, Object>> oldsearch(String searchKey, LoginUser user) {
		
		List<Map<String,Object>>  data=jdbc.queryForList("SELECT 	glb.`key_name` AS keyName,\r\n" + 
				"	glb.`object_details_url` AS objectDetailsUrl,\r\n" + 
				"	glb.`object_id_name` AS objectIdName,\r\n" + 
				"	glb.`object_id_value` AS objectIdValue,\r\n" + 
				"	glb.`object_name` AS objectName,\r\n" + 
				"	glb.`search_id` AS searchId,\r\n" + 
				"	glb.`search_string` AS searchString\r\n" + 
				"	\r\n" + 
				"	FROM `global_search_index` glb\r\n" + 
				"	JOIN `object_definition` obj ON glb.`object_name`=obj.`object_name`\r\n" + 
				"	JOIN `user_profile_object_access` objacc ON objacc.`object_object_name`=obj.`object_name` AND (objacc.`view_all_rights`=1 OR objacc.`view_rights`=1)\r\n" + 
				"	JOIN employee_details emp ON emp.`user_profile_id`=objacc.`user_profile_id` WHERE emp.`email_id`=? AND glb.`search_string` LIKE ? ORDER BY obj.`object_name` ASC",new Object[] {user.getUsername(),"%"+searchKey+"%"});
		
		return data;
	}
	
	public class SearchIn implements Callable<Map<String,List>>{

		String tableName;
		String searchString;
		String classLabelName;
		String className;
		Map<String,String> keyLavelName;
		List<String> owners;
		
		public SearchIn(String tableName,String searchString,String classLabelName,String className,List<String>owners) {
			this.tableName=tableName;
			this.searchString=searchString;
			this.classLabelName=classLabelName;
			this.className=className;
			this.owners=owners;
		}
		
		@Override
		public Map<String, List> call() throws Exception {
			
			Map<String,List> rResult=new HashMap<String,List>();
			
			List<Map<String,Object>> describetable=jdbc.queryForList("SELECT * FROM `object_field_definition` WHERE `object_def_object_name`=?",new Object[] {className});
			
			StringBuilder des=new StringBuilder();
			
			int j=0;
			String idname="";
			String ownerlabel="";
			for(Map<String,Object> jd:describetable) {
				if(jd.get("key_name").toString().equalsIgnoreCase("PK")){
					idname=jd.get("object_field_db_name").toString();
				}
				
				if(jd.get("object_field_db_name")==null)
					continue;
				if(jd.get("key_references").toString().contains("com.pratikshat.exicom.")) {
					continue;
				}
				
				if(j!=0) {
					des.append(",");
				}
				
				des.append(jd.get("object_field_db_name").toString());
				des.append(" as `"+(jd.get("object_field_label_name")==null?jd.get("object_field_db_name"):jd.get("object_field_label_name"))+"`");
				j++;
			}
			
			
			StringBuilder ss=new StringBuilder();
			
			ss.append("select DISTINCT CONCAT('<a target=\"_blank\" href=\"#/',gbl.object_details_url,'\">View Details</a>') as `Link`, "+des.toString()+" from "+tableName+" obj JOIN `global_search_index` gbl ON gbl.object_name= '"+className+"' AND cast(gbl.object_id_value as char(30))=cast(obj."+idname+" as char(30))  where ");
			
			int i=0;
			for(Map<String,Object> sq:describetable) {
				if(sq.get("object_field_db_name")==null)
					continue;
				String type=sq.get("key_references").toString();
				if(type.contains("String")||type.contains("String")) {
					if(i==0)
						ss.append(sq.get("object_field_db_name")+" like '%"+searchString+"%' ");
					else
						ss.append(" or "+ sq.get("object_field_db_name")+" like '%"+searchString+"%' ");
					i++;
				}
			}
			//LOGGER.info(ss.toString());
			List<Map<String,Object>> r=jdbc.queryForList(ss.toString());
			rResult.put(classLabelName, r);
			return rResult;
		}
		
	}
	
}
