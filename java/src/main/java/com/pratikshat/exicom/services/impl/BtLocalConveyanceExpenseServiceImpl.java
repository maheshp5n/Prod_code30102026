package com.pratikshat.exicom.services.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.LocalConveyanceExpenseRequest;
import com.pratikshat.exicom.model.response.StatusMessageResponse;
import com.pratikshat.exicom.repository.BtLocalConveyanceExpenseRepository;
import com.pratikshat.exicom.repository.BtLocalConveyanceExpenseCustomImpl;
import com.pratikshat.exicom.services.BtLocalConveyanceExpenseService;

@Service
public class BtLocalConveyanceExpenseServiceImpl implements BtLocalConveyanceExpenseService {

	@Autowired
	BtLocalConveyanceExpenseCustomImpl expenseCustomImpl; 
	
	@Autowired
	BtLocalConveyanceExpenseRepository btLocalConveyanceExpenseDB;
	
	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		if(id!=null && Long.parseLong(id.toString())!=0 && !id.toString().equalsIgnoreCase("") && !id.toString().equalsIgnoreCase("null"))
		{
			System.out.println("local id =>  "+Long.parseLong(id.toString()));
			return btLocalConveyanceExpenseDB.getById(Long.parseLong(id.toString()));
		}
		else
		{throw new BadRequestException("BtLocalConveyance id is null");}
		
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		return null;
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class,BadRequestException.class,UnauthoriseException.class, RuntimeException.class, SQLException.class })
	@Override
	public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(request.get("btExpenseManagementId")==null && request.get("btExpenseManagementId").toString().equalsIgnoreCase("") && request.get("btExpenseManagementId").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Expense id is null");
		}else if(request.get("date")==null && request.get("date").toString().equalsIgnoreCase("") && request.get("date").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Date is null");
		}else if(request.get("fromPlace")==null && request.get("fromPlace").toString().equalsIgnoreCase("") && request.get("fromPlace").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("From place is null");
		}else if(request.get("toPlace")==null && request.get("toPlace").toString().equalsIgnoreCase("") && request.get("toPlace").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("To place is null");
		}else if(request.get("modeOfTravelId")==null && request.get("modeOfTravelId").toString().equalsIgnoreCase("") && request.get("modeOfTravelId").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Mode of travel is null");
		}else if(request.get("fromPlace").toString().equalsIgnoreCase(request.get("toPlace").toString())){
			throw new BadRequestException("From Place And To Place are same");
		}else {
			if(Integer.parseInt(request.get("modeOfTravelId").toString())==8) {
				if(request.get("ownConveyanceId")==null && request.get("ownConveyanceId").toString().equalsIgnoreCase("") && request.get("ownConveyanceId").toString().equalsIgnoreCase("null")) {
					throw new BadRequestException("Own conveyance is null");
				}
				if(request.get("distance")==null && request.get("distance").toString().equalsIgnoreCase("") && request.get("distance").toString().equalsIgnoreCase("null") && Double.parseDouble(request.get("distance").toString())==0) {
					throw new BadRequestException("Distance is 0");
				}
			}else {
				if(request.get("amount")==null && request.get("amount").toString().equalsIgnoreCase("") && request.get("amount").toString().equalsIgnoreCase("null") && Double.parseDouble(request.get("amount").toString())==0) {
					throw new BadRequestException("Amount is 0");
				}
			}
		}
		LocalConveyanceExpenseRequest req=new LocalConveyanceExpenseRequest();
		if(request.get("btExpenseManagementId")!=null && !request.get("btExpenseManagementId").toString().equalsIgnoreCase("") && !request.get("btExpenseManagementId").toString().equalsIgnoreCase("null")) {
			req.setExpenseManagementId(Long.parseLong(request.get("btExpenseManagementId").toString()));
		}
		if(request.get("date")!=null && !request.get("date").toString().equalsIgnoreCase("") && !request.get("date").toString().equalsIgnoreCase("null")) {
			try {
				req.setDate(sdf.parse(request.get("date").toString()));
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}
		if(request.get("fromPlace")!=null && !request.get("fromPlace").toString().equalsIgnoreCase("") && !request.get("fromPlace").toString().equalsIgnoreCase("null")) {
			req.setFromPlace(request.get("fromPlace").toString());	
		}
		if(request.get("toPlace")!=null && !request.get("toPlace").toString().equalsIgnoreCase("") && !request.get("toPlace").toString().equalsIgnoreCase("null")) {
			req.setToPlace(request.get("toPlace").toString());
		}
		if(request.get("modeOfTravelId")!=null && !request.get("modeOfTravelId").toString().equalsIgnoreCase("") && !request.get("modeOfTravelId").toString().equalsIgnoreCase("null")) {
			req.setModeOfTravelId(Integer.parseInt(request.get("modeOfTravelId").toString()));
		}
		if(request.get("ownConveyanceId")!=null && !request.get("ownConveyanceId").toString().equalsIgnoreCase("") && !request.get("ownConveyanceId").toString().equalsIgnoreCase("null")) {
			req.setOwnConveyanceId(Integer.parseInt(request.get("ownConveyanceId").toString()));
		}
		if(request.get("distance")!=null && !request.get("distance").toString().equalsIgnoreCase("") && !request.get("distance").toString().equalsIgnoreCase("null")) {
			req.setDistance(Double.parseDouble(request.get("distance").toString()));
		}
		if(request.get("amount")!=null && !request.get("amount").toString().equalsIgnoreCase("") && !request.get("amount").toString().equalsIgnoreCase("null")) {
			req.setAmount(Double.parseDouble(request.get("amount").toString()));
		}
//		if(request.get("amountToDisbursed")!=null && !request.get("amountToDisbursed").toString().equalsIgnoreCase("") && !request.get("amountToDisbursed").toString().equalsIgnoreCase("null")) {
//			req.setAmountToDisbursed(Double.parseDouble(request.get("amountToDisbursed").toString()));
//		}
		if(request.get("remark")!=null && !request.get("remark").toString().equalsIgnoreCase("") && !request.get("remark").toString().equalsIgnoreCase("null")) {
			req.setRemark(request.get("remark").toString());
		}
		LocalConveyanceExpenseRequest reqests=expenseCustomImpl.saveDetails(req, user);
		
		return btLocalConveyanceExpenseDB.getById(reqests.getId());
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class,BadRequestException.class,UnauthoriseException.class, RuntimeException.class, SQLException.class })
	@Override
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {

		if(request.get("id")==null && request.get("id").toString().equalsIgnoreCase("") && request.get("id").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("BtLocalConveyance Expense is null");
		}else if(request.get("btExpenseManagementId")==null && request.get("btExpenseManagementId").toString().equalsIgnoreCase("") && request.get("btExpenseManagementId").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Expense id is null");
		}else if(request.get("date")==null && request.get("date").toString().equalsIgnoreCase("") && request.get("date").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Date is null");
		}else if(request.get("fromPlace")==null && request.get("fromPlace").toString().equalsIgnoreCase("") && request.get("fromPlace").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("From place is null");
		}else if(request.get("toPlace")==null && request.get("toPlace").toString().equalsIgnoreCase("") && request.get("toPlace").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("To place is null");
		}else if(request.get("fromPlace").toString().equalsIgnoreCase(request.get("toPlace").toString())){
			throw new BadRequestException("From Place And To Place are same");
		}else if(request.get("modeOfTravelId")==null && request.get("modeOfTravelId").toString().equalsIgnoreCase("") && request.get("modeOfTravelId").toString().equalsIgnoreCase("null") && Long.parseLong(request.get("modeOfTravelId").toString())==0) {
			throw new BadRequestException("Mode of travel is null");
		}else {
			if(Long.parseLong(request.get("modeOfTravelId").toString())==8) {
				if(request.get("ownConveyanceId")==null && request.get("ownConveyanceId").toString().equalsIgnoreCase("") && request.get("ownConveyanceId").toString().equalsIgnoreCase("null")) {
					throw new BadRequestException("Own conveyance is null");
				}
				if(request.get("distance")==null && request.get("distance").toString().equalsIgnoreCase("") && request.get("distance").toString().equalsIgnoreCase("null") && Double.parseDouble(request.get("distance").toString())==0) {
					throw new BadRequestException("Distance is 0");
				}
			}else {
				if(request.get("amount")==null && request.get("amount").toString().equalsIgnoreCase("") && request.get("amount").toString().equalsIgnoreCase("null") && Double.parseDouble(request.get("amount").toString())==0) {
					throw new BadRequestException("Amount is 0");
				}
			}
		}
		
		LocalConveyanceExpenseRequest req=new LocalConveyanceExpenseRequest();
		if(request.get("id")!=null && !request.get("id").toString().equalsIgnoreCase("") && !request.get("id").toString().equalsIgnoreCase("null")) {
			req.setId(Long.parseLong(request.get("id").toString()));
		}
		if(request.get("btExpenseManagementId")!=null && !request.get("btExpenseManagementId").toString().equalsIgnoreCase("") && !request.get("btExpenseManagementId").toString().equalsIgnoreCase("null")) {
			req.setExpenseManagementId(Long.parseLong(request.get("btExpenseManagementId").toString()));
		}
		if(request.get("date")!=null && !request.get("date").toString().equalsIgnoreCase("") && !request.get("date").toString().equalsIgnoreCase("null")) {
			try {
				req.setDate(sdf.parse(request.get("date").toString()));
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}
		if(request.get("fromPlace")!=null && !request.get("fromPlace").toString().equalsIgnoreCase("") && !request.get("fromPlace").toString().equalsIgnoreCase("null")) {
			req.setFromPlace(request.get("fromPlace").toString());	
		}
		if(request.get("toPlace")!=null && !request.get("toPlace").toString().equalsIgnoreCase("") && !request.get("toPlace").toString().equalsIgnoreCase("null")) {
			req.setToPlace(request.get("toPlace").toString());
		}
		if(request.get("modeOfTravelId")!=null && !request.get("modeOfTravelId").toString().equalsIgnoreCase("") && !request.get("modeOfTravelId").toString().equalsIgnoreCase("null")) {
			req.setModeOfTravelId(Integer.parseInt(request.get("modeOfTravelId").toString()));
		}
		if(request.get("ownConveyanceId")!=null && !request.get("ownConveyanceId").toString().equalsIgnoreCase("") && !request.get("ownConveyanceId").toString().equalsIgnoreCase("null")) {
			req.setOwnConveyanceId(Integer.parseInt(request.get("ownConveyanceId").toString()));
		}
		if(request.get("distance")!=null && !request.get("distance").toString().equalsIgnoreCase("") && !request.get("distance").toString().equalsIgnoreCase("null")) {
			req.setDistance(Double.parseDouble(request.get("distance").toString()));
		}
		if(request.get("amount")!=null && !request.get("amount").toString().equalsIgnoreCase("") && !request.get("amount").toString().equalsIgnoreCase("null")) {
			req.setAmount(Double.parseDouble(request.get("amount").toString()));
		}
		if(request.get("amountToDisbursed")!=null && !request.get("amountToDisbursed").toString().equalsIgnoreCase("") && !request.get("amountToDisbursed").toString().equalsIgnoreCase("null")) {
			req.setAmountToDisbursed(Double.parseDouble(request.get("amountToDisbursed").toString()));
		}
		if(request.get("remark")!=null && !request.get("remark").toString().equalsIgnoreCase("") && !request.get("remark").toString().equalsIgnoreCase("null")) {
			req.setRemark(request.get("remark").toString());
		}
		LocalConveyanceExpenseRequest reqests=expenseCustomImpl.updateDetails(req, user);
		return btLocalConveyanceExpenseDB.getById(reqests.getId());
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Map<String, Object>> getList(long btExpenseId, Pageable page) {
		
		return btLocalConveyanceExpenseDB.getByExpenseManagmentId(btExpenseId, page);
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class,BadRequestException.class,UnauthoriseException.class, RuntimeException.class, SQLException.class })
	@Override
	public StatusMessageResponse deleteLocalConveyanceExpense(long id, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return expenseCustomImpl.deleteLocalConveyanceExpense(id, user);
	}

}
