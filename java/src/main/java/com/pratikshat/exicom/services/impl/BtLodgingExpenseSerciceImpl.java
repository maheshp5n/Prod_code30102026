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
import com.pratikshat.exicom.model.request.LodgingExpenseRequest;
import com.pratikshat.exicom.model.response.StatusMessageResponse;
import com.pratikshat.exicom.repository.BtLodgingExpenseCustomRepositoryImpl;
import com.pratikshat.exicom.repository.BtLodgingExpenseRepository;
import com.pratikshat.exicom.services.BtLodgingExpenseSercice;

@Service
public class BtLodgingExpenseSerciceImpl implements BtLodgingExpenseSercice {

	@Autowired
	BtLodgingExpenseCustomRepositoryImpl btLodgingExpenseImpl;
	@Autowired
	BtLodgingExpenseRepository btLodgingExpenseDB;
	
	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return btLodgingExpenseDB.getById(Long.parseLong(id.toString()));
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
	public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)throws BadRequestException, UnauthoriseException {
		System.out.println("map => "+request);
		if(request.get("btExpenseManagementId")!=null && Long.parseLong(request.get("btExpenseManagementId").toString())==0 && request.get("btExpenseManagementId").toString().equalsIgnoreCase("null") && request.get("btExpenseManagementId").toString().equalsIgnoreCase("")) {
			throw new BadRequestException("Expense id is null");
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
		} else if(!Boolean.parseBoolean(request.get("isSelfArrangement").toString()) && !Boolean.parseBoolean(request.get("isComapnyPaid").toString())) {
				if(request.get("hotelName")!=null && request.get("hotelName").toString().equalsIgnoreCase("null") && request.get("hotelName").toString().equalsIgnoreCase("")) {
					throw new BadRequestException("Hotel name is null");
				}else if(request.get("billNo")!=null && request.get("billNo").toString().equalsIgnoreCase("null") && request.get("billNo").toString().equalsIgnoreCase("")) {
					throw new BadRequestException("Bill No. is null");
				}else if(request.get("amount")!=null && request.get("amount").toString().equalsIgnoreCase("0") && request.get("amount").toString().equalsIgnoreCase("") && Double.parseDouble(request.get("amount").toString())==0) {
					throw new BadRequestException("Amount is 0");
				}
			}
			LodgingExpenseRequest req=new LodgingExpenseRequest();
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
			if(request.get("hotelName")!=null && !request.get("hotelName").toString().equalsIgnoreCase("null") && !request.get("hotelName").toString().equalsIgnoreCase("")) {
			req.setHotelName(request.get("hotelName").toString());}
			if(request.get("billNo")!=null && !request.get("billNo").toString().equalsIgnoreCase("null") && !request.get("billNo").toString().equalsIgnoreCase("")) {
			req.setBillNo(request.get("billNo").toString());}
			if(request.get("amount")!=null && !request.get("amount").toString().equalsIgnoreCase("null") && !request.get("amount").toString().equalsIgnoreCase("")) {
			req.setAmount(Double.parseDouble(request.get("amount").toString()));}
			if(request.get("isSelfArrangement")!=null && !request.get("isSelfArrangement").toString().equalsIgnoreCase("null") && !request.get("isSelfArrangement").toString().equalsIgnoreCase("")) {
			req.setSelfArrangement(Boolean.parseBoolean(request.get("isSelfArrangement").toString()));}
			if(request.get("isComapnyPaid")!=null && !request.get("isComapnyPaid").toString().equalsIgnoreCase("null") && !request.get("isComapnyPaid").toString().equalsIgnoreCase("")) {
			req.setComapnyPaid(Boolean.parseBoolean(request.get("isComapnyPaid").toString()));}
			if(request.get("otherCity")!=null && !request.get("otherCity").toString().equalsIgnoreCase("null") && !request.get("otherCity").toString().equalsIgnoreCase("")) {
			req.setOtherCity(request.get("otherCity").toString());}
			if(request.get("approvedAmount")!=null && !request.get("approvedAmount").toString().equalsIgnoreCase("null") && !request.get("approvedAmount").toString().equalsIgnoreCase("")) {
			req.setApprovedAmount(Double.parseDouble(request.get("approvedAmount").toString()));}
			if(request.get("remark")!=null && !request.get("remark").toString().equalsIgnoreCase("null") && !request.get("remark").toString().equalsIgnoreCase("")) {
			req.setRemark(request.get("remark").toString());}
			if(request.get("amountToDisbursed")!=null && !request.get("amountToDisbursed").toString().equalsIgnoreCase("null") && !request.get("amountToDisbursed").toString().equalsIgnoreCase("")) {
			req.setAmountToDisbursed(Double.parseDouble(request.get("amountToDisbursed").toString()));}
			LodgingExpenseRequest response=btLodgingExpenseImpl.saveDetails(req, user);
			System.out.println("inserted");
		return btLodgingExpenseDB.getById(response.getId());
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class,BadRequestException.class,UnauthoriseException.class,
			RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(request.get("id")!=null && request.get("id").toString().equalsIgnoreCase("null") && request.get("id").toString().equalsIgnoreCase("")) {
			throw new BadRequestException(" id is null");
		}else if(request.get("btExpenseManagementId")!=null && request.get("btExpenseManagementId").toString().equalsIgnoreCase("null") && request.get("btExpenseManagementId").toString().equalsIgnoreCase("")) {
			throw new BadRequestException("Expense id is null");
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
		} else if(!Boolean.parseBoolean(request.get("isSelfArrangement").toString()) && !Boolean.parseBoolean(request.get("isComapnyPaid").toString())) {
				if(request.get("hotelName")!=null && request.get("hotelName").toString().equalsIgnoreCase("null") && request.get("hotelName").toString().equalsIgnoreCase("")) {
					throw new BadRequestException("Hotel name is null");
				}else if(request.get("billNo")!=null && request.get("billNo").toString().equalsIgnoreCase("null") && request.get("billNo").toString().equalsIgnoreCase("")) {
					throw new BadRequestException("Bill No. is null");
				}else if(request.get("amount")!=null && request.get("amount").toString().equalsIgnoreCase("0") && request.get("amount").toString().equalsIgnoreCase("") && Double.parseDouble( request.get("amount").toString())==0) {
					throw new BadRequestException("Amount is 0");
				}
			}
			LodgingExpenseRequest req=new LodgingExpenseRequest();
			if(request.get("id")!=null && !request.get("id").toString().equalsIgnoreCase("null") && !request.get("id").toString().equalsIgnoreCase("")) {
			req.setId(Long.parseLong(request.get("id").toString()));}
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
			if(request.get("hotelName")!=null && !request.get("hotelName").toString().equalsIgnoreCase("null") && !request.get("hotelName").toString().equalsIgnoreCase("")) {
			req.setHotelName(request.get("hotelName").toString());}
			if(request.get("billNo")!=null && !request.get("billNo").toString().equalsIgnoreCase("null") && !request.get("billNo").toString().equalsIgnoreCase("")) {
			req.setBillNo(request.get("billNo").toString());}
			if(request.get("amount")!=null && !request.get("amount").toString().equalsIgnoreCase("null") && !request.get("amount").toString().equalsIgnoreCase("")) {
			req.setAmount(Double.parseDouble(request.get("amount").toString()));}
			if(request.get("isSelfArrangement")!=null && !request.get("isSelfArrangement").toString().equalsIgnoreCase("null") && !request.get("isSelfArrangement").toString().equalsIgnoreCase("")) {
			req.setSelfArrangement(Boolean.parseBoolean(request.get("isSelfArrangement").toString()));}
			if(request.get("isComapnyPaid")!=null && !request.get("isComapnyPaid").toString().equalsIgnoreCase("null") && !request.get("isComapnyPaid").toString().equalsIgnoreCase("")) {
			req.setComapnyPaid(Boolean.parseBoolean(request.get("isComapnyPaid").toString()));}
			if(request.get("otherCity")!=null && !request.get("otherCity").toString().equalsIgnoreCase("null") && !request.get("otherCity").toString().equalsIgnoreCase("")) {
			req.setOtherCity(request.get("otherCity").toString());}
			if(request.get("approvedAmount")!=null && !request.get("approvedAmount").toString().equalsIgnoreCase("null") && !request.get("approvedAmount").toString().equalsIgnoreCase("")) {
			req.setApprovedAmount(Double.parseDouble(request.get("approvedAmount").toString()));}
			if(request.get("remark")!=null && !request.get("remark").toString().equalsIgnoreCase("null") && !request.get("remark").toString().equalsIgnoreCase("")) {
			req.setRemark(request.get("remark").toString());}
			if(request.get("amountToDisbursed")!=null && !request.get("amountToDisbursed").toString().equalsIgnoreCase("null") && !request.get("amountToDisbursed").toString().equalsIgnoreCase("")) {
			req.setAmountToDisbursed(Double.parseDouble(request.get("amountToDisbursed").toString()));}
		
			LodgingExpenseRequest response=btLodgingExpenseImpl.updateDetails(req, user);
			System.out.println("updated");
			return btLodgingExpenseDB.getById(response.getId());
		
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
	public Page<Map<String,Object>> getList(long btExpenseManagmentId, Pageable page) throws BadRequestException {
		// TODO Auto-generated method stub
		System.out.println("expense id  =>  "+btExpenseManagmentId);
		System.out.println("page and size  => "+page.getPageSize()+"  "+page.getPageNumber());
		return btLodgingExpenseDB.getByBtExpenseManagmentId(btExpenseManagmentId, page);
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class,BadRequestException.class,UnauthoriseException.class,
			RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	@Override
	public StatusMessageResponse deleteLogdingExpense(long id, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return btLodgingExpenseImpl.deleteLogdingExpense(id, user);
	}

}
