package com.pratikshat.exicom.services.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.BtApproval;
import com.pratikshat.exicom.entity.BtApprovalStapes;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtApprovalRepository;
import com.pratikshat.exicom.repository.BtApprovalStapesRepository;
import com.pratikshat.exicom.services.BtApprovalStapesService;

@Service
public class BtApprovalStapesServiceImpl implements BtApprovalStapesService {

	@Autowired
	BtApprovalStapesRepository dbRepo;
	
	@Autowired
	BtApprovalRepository dbApprovalRepo;
	
	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		
		return dbRepo.getById(Long.parseLong(id.toString()));
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		return dbRepo.getPage(page);
	}

	@Override
	@Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
	public Map<String, Object> saveObject(Map<String, Object> assets, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		BtApprovalStapes stapes=new BtApprovalStapes();
		
		BtApproval approval=dbApprovalRepo.findById(Long.parseLong(assets.get("approvalId").toString())).get();
		
		stapes.setApproval(approval);
		stapes.setAssignedTo(assets.get("assignedTo").toString());
		stapes.setOnApprove(assets.get("onApprove").toString());
		stapes.setOnReject(assets.get("onReject").toString());
		stapes.setOwner(new EmployeeDetails(user.getUsername()));
		stapes.setStapeNumber(Integer.parseInt(assets.get("stapesNumber").toString()));
		stapes.setStapeName(assets.get("stapeName").toString());
		 dbRepo.saveDetails(stapes, user);
		 
		 return dbRepo.getById(stapes.getId());
	}

	@Override
	@Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
	public Map<String, Object> updateObject(Map<String, Object> assets, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		BtApprovalStapes stapes=dbRepo.findById(Long.parseLong(assets.get("id").toString())).get();
		stapes.setAssignedTo(assets.get("assignedTo").toString());
		stapes.setOnApprove(assets.get("onApprove").toString());
		stapes.setOnReject(assets.get("onReject").toString());
		stapes.setStapeNumber(Integer.parseInt(assets.get("stapesNumber").toString()));
		stapes.setStapeName(assets.get("stapeName").toString());
		dbRepo.updateDetails(stapes, user);
		return dbRepo.getById(stapes.getId());
	}

	@Override
	public List<Map<String, Object>> searchForList(String search, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		return null;
	}

	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchKey, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		return null;
	}

	@Override
	public List<Map<String, Object>> getApprovalStapesByApprovalId(Long approvalId, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		return dbRepo.getListByApprovalId(approvalId);
	}

	@Override
	public Map<String, Object> getApprovalStateByApprovalIdAndStateId(Long approvalId, Long approvalStapeId,
			LoginUser user) throws BadRequestException, UnauthoriseException {
		
		return dbRepo.getObjectByApprovalIdAndStapeId(approvalId, approvalStapeId);
	}
	
	
	
}
