package com.pratikshat.exicom.services.impl;

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
import com.pratikshat.exicom.model.request.BoardingExpenseRequest;
import com.pratikshat.exicom.model.response.StatusMessageResponse;
import com.pratikshat.exicom.repository.BtBoardingExpenseCustomRepositoryImpl;
import com.pratikshat.exicom.repository.BtBoardingExpenseRepository;
import com.pratikshat.exicom.services.BtBoardingExpenseService;

@Service
public class BtBoardingExpenseServiceImpl implements BtBoardingExpenseService {
	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	BtBoardingExpenseRepository btBoardingExpenseDB;
	@Autowired
	BtBoardingExpenseCustomRepositoryImpl BtBoardingExpenseImpl;
	
	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		if(id!=null && id.toString().equalsIgnoreCase("null") && id.toString().equalsIgnoreCase("")) {
			throw new BadRequestException(" btBoardingExpense id is null");}
		
		return btBoardingExpenseDB.getById(Long.parseLong(id.toString()));
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class,BadRequestException.class,UnauthoriseException.class,
			RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		if(request.get("btExpenseManagementId")!=null && request.get("btExpenseManagementId").toString().equalsIgnoreCase("null") && request.get("btExpenseManagementId").toString().equalsIgnoreCase("")) {
			throw new BadRequestException("BtExpense id is null");
		}else if(request.get("from")!=null && request.get("from").toString().equalsIgnoreCase("null") && request.get("from").toString().equalsIgnoreCase("")) {
			throw new BadRequestException("From date is null");
		}else if(request.get("fromTime")!=null && request.get("fromTime").toString().equalsIgnoreCase("null") && request.get("fromTime").toString().equalsIgnoreCase("")) {
			throw new BadRequestException("From time is null");
		} else if(request.get("to")!=null && request.get("to").toString().equalsIgnoreCase("null") && request.get("to").toString().equalsIgnoreCase("")) {
			throw new BadRequestException("To date is null");
		}else if(request.get("toTime")!=null && request.get("toTime").toString().equalsIgnoreCase("null") && request.get("toTime").toString().equalsIgnoreCase("")) {
			throw new BadRequestException("To time is null");
		}else if(request.get("amount")!=null && !request.get("amount").toString().equalsIgnoreCase("null") && request.get("amount").toString().equalsIgnoreCase("") && Double.parseDouble(request.get("amount").toString())==0) {
			throw new BadRequestException("amount is 0");}
		else if(request.get("cityName")!=null && request.get("cityName").toString().equalsIgnoreCase("null") && request.get("cityName").toString().equalsIgnoreCase("")) {
			throw new BadRequestException("City is null");
		}
		BoardingExpenseRequest req=new BoardingExpenseRequest();
		if(request.get("btExpenseManagementId")!=null && !request.get("btExpenseManagementId").toString().equalsIgnoreCase("null") && !request.get("btExpenseManagementId").toString().equalsIgnoreCase("")) {
			req.setExpenseManagementId(Long.parseLong(request.get("btExpenseManagementId").toString()));}
			if(request.get("from")!=null && !request.get("from").toString().equalsIgnoreCase("null") && !request.get("from").toString().equalsIgnoreCase("")) {
				try {
					req.setFromDate(sdf.parse(request.get("from").toString()));
				} catch (ParseException e) {
					
					e.printStackTrace();
				}}
			if(request.get("fromTime")!=null && !request.get("fromTime").toString().equalsIgnoreCase("null") && !request.get("fromTime").toString().equalsIgnoreCase("")) {
			req.setFromTime(request.get("fromTime").toString());}
			if(request.get("to")!=null && !request.get("to").toString().equalsIgnoreCase("null") && !request.get("to").toString().equalsIgnoreCase("")) {
				try {
					req.setToDate(sdf.parse(request.get("to").toString()));
				} catch (ParseException e) {
					
					e.printStackTrace();
				}}
			if(request.get("toTime")!=null && !request.get("toTime").toString().equalsIgnoreCase("null") && !request.get("toTime").toString().equalsIgnoreCase("")) {
			req.setToTime(request.get("toTime").toString());}
			if(request.get("cityName")!=null && !request.get("cityName").toString().equalsIgnoreCase("null") && !request.get("cityName").toString().equalsIgnoreCase("")) {
			req.setCityName(request.get("cityName").toString());}
			if(request.get("otherCity")!=null && !request.get("otherCity").toString().equalsIgnoreCase("null") && !request.get("otherCity").toString().equalsIgnoreCase("")) {
				req.setOtherCity(request.get("otherCity").toString());}
			if(request.get("amount")!=null && !request.get("amount").toString().equalsIgnoreCase("null") && !request.get("amount").toString().equalsIgnoreCase("")) {
				req.setAmount(Double.parseDouble(request.get("amount").toString()));}
			if(request.get("remark")!=null && !request.get("remark").toString().equalsIgnoreCase("null") && !request.get("remark").toString().equalsIgnoreCase("")) {
				req.setRemark(request.get("remark").toString());}
			if(request.get("amountToDisbursed")!=null && !request.get("amountToDisbursed").toString().equalsIgnoreCase("null") && !request.get("amountToDisbursed").toString().equalsIgnoreCase("")) {
				req.setAmountToDisbursed(Double.parseDouble(request.get("amountToDisbursed").toString()));}
			if(request.get("NoOfDayes")!=null && !request.get("NoOfDayes").toString().equalsIgnoreCase("null") && !request.get("NoOfDayes").toString().equalsIgnoreCase("")) {
				req.setNoOfDayes(Double.parseDouble(request.get("NoOfDayes").toString()));}
			BoardingExpenseRequest response=BtBoardingExpenseImpl.saveDetails(req, user);
			
			return btBoardingExpenseDB.getById(response.getId());
		
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class,BadRequestException.class,UnauthoriseException.class,
			RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {

		if(request.get("id")!=null && request.get("id").toString().equalsIgnoreCase("null") && request.get("id").toString().equalsIgnoreCase("")) {
			throw new BadRequestException(" btBoardingExpense id is null");}
		if(request.get("btExpenseManagementId")!=null && request.get("btExpenseManagementId").toString().equalsIgnoreCase("null") && request.get("btExpenseManagementId").toString().equalsIgnoreCase("")) {
			throw new BadRequestException("BtExpense id is null");
		}else if(request.get("from")!=null && request.get("from").toString().equalsIgnoreCase("null") && request.get("from").toString().equalsIgnoreCase("")) {
			throw new BadRequestException("From date is null");
		}else if(request.get("fromTime")!=null && request.get("fromTime").toString().equalsIgnoreCase("null") && request.get("fromTime").toString().equalsIgnoreCase("")) {
			throw new BadRequestException("From time is null");
		} else if(request.get("to")!=null && request.get("to").toString().equalsIgnoreCase("null") && request.get("to").toString().equalsIgnoreCase("")) {
			throw new BadRequestException("To date is null");
		}else if(request.get("toTime")!=null && request.get("toTime").toString().equalsIgnoreCase("null") && request.get("toTime").toString().equalsIgnoreCase("")) {
			throw new BadRequestException("To time is null");
		}else if(request.get("cityName")!=null && request.get("cityName").toString().equalsIgnoreCase("null") && request.get("cityName").toString().equalsIgnoreCase("")) {
			throw new BadRequestException("City is null");
		}else if(request.get("amount")!=null && !request.get("amount").toString().equalsIgnoreCase("null") && request.get("amount").toString().equalsIgnoreCase("") && Double.parseDouble(request.get("amount").toString())==0) {
			throw new BadRequestException("amount is 0");}
		
		BoardingExpenseRequest req=new BoardingExpenseRequest();
			if(request.get("btExpenseManagementId")!=null && !request.get("btExpenseManagementId").toString().equalsIgnoreCase("null") && !request.get("btExpenseManagementId").toString().equalsIgnoreCase("")) {
			req.setExpenseManagementId(Long.parseLong(request.get("btExpenseManagementId").toString()));}
			if(request.get("id")!=null && !request.get("id").toString().equalsIgnoreCase("null") && !request.get("id").toString().equalsIgnoreCase("")) {
			req.setId(Long.parseLong(request.get("id").toString()));}
			if(request.get("from")!=null && !request.get("from").toString().equalsIgnoreCase("null") && !request.get("from").toString().equalsIgnoreCase("")) {
			try {
				req.setFromDate(sdf.parse(request.get("from").toString()));
			} catch (ParseException e) {
				
				e.printStackTrace();
			}}
			if(request.get("fromTime")!=null && !request.get("fromTime").toString().equalsIgnoreCase("null") && !request.get("fromTime").toString().equalsIgnoreCase("")) {
			req.setFromTime(request.get("fromTime").toString());}
			if(request.get("to")!=null && !request.get("to").toString().equalsIgnoreCase("null") && !request.get("to").toString().equalsIgnoreCase("")) {
			try {
				req.setToDate(sdf.parse(request.get("to").toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}}
			if(request.get("toTime")!=null && !request.get("toTime").toString().equalsIgnoreCase("null") && !request.get("toTime").toString().equalsIgnoreCase("")) {
			req.setToTime(request.get("toTime").toString());}
			if(request.get("cityName")!=null && !request.get("cityName").toString().equalsIgnoreCase("null") && !request.get("cityName").toString().equalsIgnoreCase("")) {
			req.setCityName(request.get("cityName").toString());}
			if(request.get("otherCity")!=null && !request.get("otherCity").toString().equalsIgnoreCase("null") && !request.get("otherCity").toString().equalsIgnoreCase("")) {
				req.setOtherCity(request.get("otherCity").toString());}
			if(request.get("amount")!=null && !request.get("amount").toString().equalsIgnoreCase("null") && !request.get("amount").toString().equalsIgnoreCase("")) {
				req.setAmount(Double.parseDouble(request.get("amount").toString()));}
			if(request.get("remark")!=null && !request.get("remark").toString().equalsIgnoreCase("null") && !request.get("remark").toString().equalsIgnoreCase("")) {
				req.setRemark(request.get("remark").toString());}
			if(request.get("amountToDisbursed")!=null && !request.get("amountToDisbursed").toString().equalsIgnoreCase("null") && !request.get("amountToDisbursed").toString().equalsIgnoreCase("")) {
				req.setAmountToDisbursed(Double.parseDouble(request.get("amountToDisbursed").toString()));}
			if(request.get("NoOfDayes")!=null && !request.get("NoOfDayes").toString().equalsIgnoreCase("null") && !request.get("NoOfDayes").toString().equalsIgnoreCase("")) {
				req.setNoOfDayes(Double.parseDouble(request.get("NoOfDayes").toString()));}
			BoardingExpenseRequest response=BtBoardingExpenseImpl.updateDetails(req, user);
			return btBoardingExpenseDB.getById(response.getId());
			 
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
	public Page<Map<String, Object>> getList(long btExpenseManagementId, Pageable page) throws BadRequestException {
		if(btExpenseManagementId!=0) {
		return btBoardingExpenseDB.getByExpenseManagmentId(btExpenseManagementId, page);
		}
		else
		{
			throw new BadRequestException("BtExpense id is null");
		}
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class,BadRequestException.class,UnauthoriseException.class,
			RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	@Override
	public StatusMessageResponse deleteBtBoardingExpense(long id, LoginUser user) throws BadRequestException, UnauthoriseException {
		
		return BtBoardingExpenseImpl.deleteBtBoardingExpense(id, user);
	}

}
