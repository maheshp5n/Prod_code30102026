package com.pratikshat.exicom.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.BtToBeApproved;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.LoginUserRoles;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtToBeApprovedRepository;
import com.pratikshat.exicom.services.BtTobeApprovedService;

@Service
public class BtTobeApprovedServiceImpl implements BtTobeApprovedService {

	@Autowired
	BtToBeApprovedRepository db;
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
	public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		return updateObject(request,user);
	}

	@Override
	@Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		Long id=Long.parseLong(request.get("id").toString());
		Optional<BtToBeApproved> _tobeapproved= db.findById(id);
		if(!_tobeapproved.isPresent()) {
			throw new BadRequestException("Wrong Id");
		}
		BtToBeApproved tobeapproved=_tobeapproved.get();
		if(tobeapproved.isStatus()) {
			throw new BadRequestException("Action has already taken");
		}
		tobeapproved.setAction(request.get("action").toString());
		tobeapproved.setRemark(request.get("remarks").toString());
		tobeapproved.setApprovedDate(new Date());
		db.updateDetails(_tobeapproved.get(), user);
		return null;
	}

	@Override
	public List<Map<String, Object>> searchForList(String search, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		String query = "SELECT   \r\n"
				+ "			 	btt.`id`,  \r\n"
				+ "			 	btt.`created_date`,  \r\n"
				+ "			 	btt.`last_modified_date`,  \r\n"
				+ "			 	btt.`object_name`,  \r\n"
				+ "			 	btt.`action`,  \r\n"
				+ "			 	btt.`approved_date`,  \r\n"
				+ "			 	btt.`assigned_date`,  \r\n"
				+ "			 	btt.`assigned_to`,  \r\n"
				+ "			 	btt.`object_id`,  \r\n"
				+ "			 	btt.`object_manual_id`,  \r\n"
				+ "			 	btt.`object_url`,  \r\n"
				+ "			 	btt.`previous_remarks`,  \r\n"
				+ "			 	btt.`remark`,  \r\n"
				+ "			 	btt.`stage`,  \r\n"
				+ "			 	btt.`status`,  \r\n"
				+ "			 	btt.`created_by_email_id`,  \r\n"
				+ "			 	btt.`last_modified_by_email_id`,  \r\n"
				+ "			 	btt.`action_by_email_id`,  \r\n"
				+ "			 	btt.`initiator_email_id`,  \r\n"
				+ "			 	btt.`stapes_id`,  \r\n"
				+ "			 	obj.`object_lable_name`  \r\n"
				+ "			 	  \r\n"
				+ "			  FROM `bt_to_be_approved` btt JOIN `object_definition` obj ON btt.`object_name`=obj.`object_name` WHERE ";
		
		String role="";
		Iterator<LoginUserRoles> ite=(Iterator<LoginUserRoles>) user.getAuthorities().iterator();
		while(ite.hasNext()) {
			role=ite.next().getAuthority();
			break;
		}
		query+=" (btt.assigned_to LIKE '%ROLE:"+role+"%' OR btt.assigned_to LIKE '%USER:"+user.getUsername()+"%')";
		
		List<String> params=new ArrayList<String>();
		if(searchRequest.get("request_number")!=null && !searchRequest.get("request_number").toString().equalsIgnoreCase(""))
		{
			query+=" and btt.`object_manual_id` like ?";
			params.add("%"+ searchRequest.get("request_number").toString()+ "%");
		}
		if(searchRequest.get("type_name")!=null && !searchRequest.get("type_name").toString().equalsIgnoreCase(""))
		{
			query+=" and btt.`object_name` like ?";
			params.add("%"+ searchRequest.get("type_name").toString()+ "%");
		}
		if(searchRequest.get("date_from")!=null && !searchRequest.get("date_from").toString().equalsIgnoreCase("") && searchRequest.get("date_to")!=null && !searchRequest.get("date_to").toString().equalsIgnoreCase(""))
		{
			query+="and date_format(btt.`assigned_date`,'%Y-%m-%d') between date_format(?,'%Y-%m-%d') and date_format(?,'%Y-%m-%d')";
			params.add(searchRequest.get("date_from").toString());
			params.add(searchRequest.get("date_to").toString());
						
		}
		if(searchRequest.get("action")!=null&&!searchRequest.get("action").toString().equalsIgnoreCase("")) {
			if(searchRequest.get("action").toString().equalsIgnoreCase("Pending")) {
				query+=" and btt.action IS NULL";
			}else {
				query+=" and btt.action like ?";
				params.add("%"+searchRequest.get("action").toString()+"%");	
			}
			
		}
		
		List<Map<String, Object>> lst =null;
		if(params!=null && params.size()>0)
		{
			lst = jdbc.queryForList(query.toString(), params.toArray());
		}
		else {
			lst = jdbc.queryForList(query);
		}
		// TODO Auto-generated method stub
		return lst;
	}

	@Override
	public Page<Map<String, Object>> getPendingPage(Pageable page,LoginUser user) {
		String role="";
		Iterator<LoginUserRoles> ite=(Iterator<LoginUserRoles>) user.getAuthorities().iterator();
		while(ite.hasNext()) {
			role=ite.next().getAuthority();
			break;
		}
		
		return db.getPendingPage("%ROLE:"+role+"%", "%USER:"+user.getUsername()+"%",page);
	}

	@Override
	public List<Map<String, Object>> getApprovalHistory( String objectName, Long objectId)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return db.getApprovalHistory(objectName, objectId);
	}

	@Override
	public Map<String, Object> getToBeApprovedBasedOnIdAndObjectName(long objectId, String objectName,LoginUser user) {
		Iterator<LoginUserRoles> ite= (Iterator<LoginUserRoles>) user.getAuthorities().iterator();
		String role="";
		while(ite.hasNext()) {
			role=ite.next().getAuthority();
			break;
		}
		
		System.out.println(role+" role");
		return db.getApprovalOnIdAndName(objectId,objectName,"%ROLE:"+role+"%","%USER:"+user.getUsername()+"%");
	}
	
//	String getRoleLike(LoginUser user) {
//		Iterator<LoginUserRoles> ite = (Iterator<LoginUserRoles>) user.getAuthorities().iterator();
//		while(ite.hasNext()) {
//			String role=ite.next().getAuthority();
//			return "%ROLE:"+role+"%";
//		}
//		return null;
//	}
//	String getUserNameLike(LoginUser user) {
//		return "%USER:"+user.getUsername()+"%";
//	}

	
	}

