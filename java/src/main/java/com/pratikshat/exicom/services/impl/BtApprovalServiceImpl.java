package com.pratikshat.exicom.services.impl;

import java.util.ArrayList;
import java.util.Date;
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

import com.pratikshat.exicom.entity.BtApproval;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtApprovalRepository;
import com.pratikshat.exicom.services.BtApprovalService;

@Service
public class BtApprovalServiceImpl implements BtApprovalService {

	@Autowired
	BtApprovalRepository dbRepo;
	@Autowired
	JdbcTemplate jdbc;
	
	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		
		Long _id=(Long)id;
		
		return dbRepo.getById(_id);
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		return dbRepo.getPage(page);
	}

	@Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
	@Override
	public Map<String, Object> saveObject(Map<String, Object> assets, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		EmployeeDetails currentEmp=new EmployeeDetails(user.getUsername());
		
		BtApproval _approval=new BtApproval();
		_approval.setActive(true);
		_approval.setCreatedBy(currentEmp);
		_approval.setCreatedDate(new Date());
		_approval.setCtOnAction(assets.get("action_cat").toString());
		_approval.setDescription(assets.get("description").toString());
		_approval.setInitialSubmitters(assets.get("init_submitters").toString());
		_approval.setLastModifiedBy(currentEmp);
		_approval.setLastModifiedDate(new Date());
		_approval.setObjectClassName(assets.get("object_class_name").toString());
		_approval.setObjectName(assets.get("object_name").toString());
		_approval.setOwner(currentEmp);
		_approval.setTotalStapes(Integer.parseInt(assets.get("stapes").toString()));
		
		dbRepo.saveDetails(_approval, user);
		System.out.println("Approval validated for saveD");
		return dbRepo.getById(_approval.getId());
	}

	@Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
	@Override
	public Map<String, Object> updateObject(Map<String, Object> assets, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		EmployeeDetails currentEmp=new EmployeeDetails(user.getUsername());
		
		Optional<BtApproval> _approvalOption=dbRepo.findById(Long.parseLong(assets.get("id").toString()));
		if(!_approvalOption.isPresent())
			throw new BadRequestException("Approval Id not found");
		
		BtApproval _approval=_approvalOption.get();
		
		//_approval.setActive(Boolean.parseBoolean(assets.get("active").toString()));
		_approval.setCtOnAction(assets.get("action_cat").toString());
		_approval.setDescription(assets.get("description").toString());
		_approval.setInitialSubmitters(assets.get("init_submitters").toString());
		_approval.setLastModifiedBy(currentEmp);
		_approval.setLastModifiedDate(new Date());
		_approval.setObjectClassName(assets.get("object_class_name").toString());
		_approval.setObjectName(assets.get("object_name").toString());
		_approval.setOwner(currentEmp);
		_approval.setTotalStapes(Integer.parseInt(assets.get("stapes").toString()));
		
		dbRepo.updateDetails(_approval, user);
		
		return dbRepo.getById(_approval.getId());
	
	}

	@Override
	public List<Map<String, Object>> searchForList(String search, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		return null;
	}

	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchKeys, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		List<Object> value = new ArrayList<>();
		String _query="SELECT * FROM `bt_approval` ap WHERE 1=1";
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(_query);
		if (searchKeys.get("objectName") != null && !searchKeys.get("objectName").equals("")) {
			queryBuilder.append(" and ap.`object_class_name` like ?");
			value.add("%" + searchKeys.get("objectName") + "%");
		}
		if(searchKeys.get("description") != null && !searchKeys.get("description").equals("")) {
			queryBuilder.append(" AND ap.`description` LIKE ?");
			value.add("%" + searchKeys.get("") + "%");
		}
		if(searchKeys.get("name") != null && !searchKeys.get("name").equals("")) {
			queryBuilder.append(" AND ap.`object_name` LIKE ?");
			value.add("%" + searchKeys.get("name") + "%");
		}
		if(searchKeys.get("initiator") != null && !searchKeys.get("initiator").equals("")) {
			queryBuilder.append(" AND ap.`initial_submitters` LIKE ?");
			value.add("%" + searchKeys.get("initiator") + "%");
		}
		return jdbc.queryForList(queryBuilder.toString(), value.toArray());
	}

	@Override
	@Transactional
	public void enableDisable(Long id, boolean flag) {
		dbRepo.enableDisable(id, flag);
	}

}