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
import com.pratikshat.exicom.model.request.OtherChargesExpenseRequest;
import com.pratikshat.exicom.model.response.StatusMessageResponse;
import com.pratikshat.exicom.repository.BtOtherChargesExpenseCustomRepositoryImpl;
import com.pratikshat.exicom.repository.BtOtherChargesExpenseRepository;
import com.pratikshat.exicom.services.BtOtherChargesExpenseService;

@Service
public class BtOtherChargesExpenseServiceImpl implements BtOtherChargesExpenseService {

	@Autowired
	BtOtherChargesExpenseCustomRepositoryImpl BtOtherChargesExpenseDBImpl;
	
	@Autowired
	BtOtherChargesExpenseRepository btOtherChargesExpenseDB;
	
	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		if(id!=null && Long.parseLong(id.toString())!=0 && !id.toString().equalsIgnoreCase("") && !id.toString().equalsIgnoreCase("null"))
		{
			return btOtherChargesExpenseDB.getById(Long.parseLong(id.toString()));
		}
		else
		{
			throw new BadRequestException("BtOtherCharges Expense id is null");	
		}
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
System.out.println("request  =>"+request);
		if(request.get("btExpenseManagementId")==null && Long.parseLong(request.get("btExpenseManagementId").toString())==0 && request.get("btExpenseManagementId").toString().equalsIgnoreCase("") && request.get("btExpenseManagementId").toString().equalsIgnoreCase("null") ) {
			throw new BadRequestException("Expense id is null");
		}else if(request.get("date")==null && request.get("date").toString().equalsIgnoreCase("") && request.get("date").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Date is null");
		}else if(request.get("billNo")==null && request.get("billNo").toString().equalsIgnoreCase("") && request.get("billNo").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Bill No. is null");
		}else if(request.get("otherChargesTypeId")==null && Long.parseLong(request.get("otherChargesTypeId").toString())==0 && request.get("otherChargesTypeId").toString().equalsIgnoreCase("") && request.get("otherChargesTypeId").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Type Id is null");
		}else if(request.get("amount")==null && Double.parseDouble(request.get("amount").toString())==0 && request.get("amount").toString().equalsIgnoreCase("") && request.get("amount").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Amount is 0");
		}else {
			OtherChargesExpenseRequest req=new OtherChargesExpenseRequest();
			if(request.get("btExpenseManagementId")!=null && Long.parseLong(request.get("btExpenseManagementId").toString())!=0 && !request.get("btExpenseManagementId").toString().equalsIgnoreCase("") && !request.get("btExpenseManagementId").toString().equalsIgnoreCase("null") ) {
				req.setExpenseManagementId(Long.parseLong(request.get("btExpenseManagementId").toString()));
				}if(request.get("date")!=null && !request.get("date").toString().equalsIgnoreCase("") && !request.get("date").toString().equalsIgnoreCase("null") ) {
					try {
						req.setDate(sdf.parse(request.get("date").toString()));
					} catch (ParseException e) {
						
						e.printStackTrace();
					}
				}if(request.get("billNo")!=null && !request.get("billNo").toString().equalsIgnoreCase("") && !request.get("billNo").toString().equalsIgnoreCase("null") ) {
					req.setBillNo(request.get("billNo").toString());
				}if(request.get("amount")!=null && Double.parseDouble(request.get("amount").toString())!=0 && !request.get("amount").toString().equalsIgnoreCase("") && !request.get("amount").toString().equalsIgnoreCase("null") ) {
					req.setAmount(Double.parseDouble(request.get("amount").toString()));
				}if(request.get("otherChargesTypeId")!=null && Long.parseLong(request.get("otherChargesTypeId").toString())!=0 && !request.get("otherChargesTypeId").toString().equalsIgnoreCase("") && !request.get("otherChargesTypeId").toString().equalsIgnoreCase("null") ) {
					req.setOtherChargesTypeId(Integer.parseInt(request.get("otherChargesTypeId").toString()));
				}if(request.get("remark")!=null && !request.get("remark").toString().equalsIgnoreCase("") && !request.get("remark").toString().equalsIgnoreCase("null") ) {
					req.setRemark(request.get("remark").toString());
				}if(request.get("amountToDisbursed")!=null && !request.get("amountToDisbursed").toString().equalsIgnoreCase("") && !request.get("amountToDisbursed").toString().equalsIgnoreCase("null") ) {
					req.setAmountToDisbursed(Double.parseDouble(request.get("amountToDisbursed").toString()));
				}
				System.out.println("saveing");
			OtherChargesExpenseRequest _request = BtOtherChargesExpenseDBImpl.saveDetails(req, user);
			System.out.println("saved");
			return btOtherChargesExpenseDB.getById(_request.getId());	
		}
		
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class,BadRequestException.class,UnauthoriseException.class,
			RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(request.get("id")==null && Long.parseLong(request.get("id").toString())==0 && request.get("id").toString().equalsIgnoreCase("") && request.get("id").toString().equalsIgnoreCase("null") ) {
			throw new BadRequestException("Expense id is null");
		}else if(request.get("btExpenseManagementId")==null && Long.parseLong(request.get("btExpenseManagementId").toString())==0 && request.get("btExpenseManagementId").toString().equalsIgnoreCase("") && request.get("btExpenseManagementId").toString().equalsIgnoreCase("null") ) {
			throw new BadRequestException("Expense id is null");
		}else if(request.get("date")==null && request.get("date").toString().equalsIgnoreCase("") && request.get("date").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Date is null");
		}else if(request.get("billNo")==null && request.get("billNo").toString().equalsIgnoreCase("") && request.get("billNo").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Bill No. is null");
		}else if(request.get("otherChargesTypeId")==null && Long.parseLong(request.get("otherChargesTypeId").toString())==0 && request.get("otherChargesTypeId").toString().equalsIgnoreCase("") && request.get("otherChargesTypeId").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Type Id is null");
		}else if(request.get("amount")==null && Double.parseDouble(request.get("amount").toString())==0 && request.get("amount").toString().equalsIgnoreCase("") && request.get("amount").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Amount is 0");
		}else {
			OtherChargesExpenseRequest req=new OtherChargesExpenseRequest();
				if(request.get("id")!=null && Long.parseLong(request.get("id").toString())!=0 && !request.get("id").toString().equalsIgnoreCase("") && !request.get("id").toString().equalsIgnoreCase("null") ) {
				req.setId(Long.parseLong(request.get("id").toString()));
				}if(request.get("btExpenseManagementId")!=null && Long.parseLong(request.get("btExpenseManagementId").toString())!=0 && !request.get("btExpenseManagementId").toString().equalsIgnoreCase("") && !request.get("btExpenseManagementId").toString().equalsIgnoreCase("null") ) {
					req.setExpenseManagementId(Long.parseLong(request.get("btExpenseManagementId").toString()));
					}if(request.get("date")!=null && !request.get("date").toString().equalsIgnoreCase("") && !request.get("date").toString().equalsIgnoreCase("null") ) {
						
							try {
								req.setDate(sdf.parse(request.get("date").toString()));
							} catch (ParseException e) {
								
								e.printStackTrace();
							}
						
					}if(request.get("billNo")!=null && !request.get("billNo").toString().equalsIgnoreCase("") && !request.get("billNo").toString().equalsIgnoreCase("null") ) {
						req.setBillNo(request.get("billNo").toString());
					}if(request.get("amount")!=null && Double.parseDouble(request.get("amount").toString())!=0 && !request.get("amount").toString().equalsIgnoreCase("") && !request.get("amount").toString().equalsIgnoreCase("null") ) {
						req.setAmount(Double.parseDouble(request.get("amount").toString()));
					}if(request.get("otherChargesTypeId")!=null && Long.parseLong(request.get("otherChargesTypeId").toString())!=0 && !request.get("otherChargesTypeId").toString().equalsIgnoreCase("") && !request.get("otherChargesTypeId").toString().equalsIgnoreCase("null") ) {
						req.setOtherChargesTypeId(Integer.parseInt(request.get("otherChargesTypeId").toString()));
					}if(request.get("remark")!=null && !request.get("remark").toString().equalsIgnoreCase("") && !request.get("remark").toString().equalsIgnoreCase("null") ) {
						req.setRemark(request.get("remark").toString());
					}if(request.get("amountToDisbursed")!=null && !request.get("amountToDisbursed").toString().equalsIgnoreCase("") && !request.get("amountToDisbursed").toString().equalsIgnoreCase("null") ) {
						req.setAmountToDisbursed(Double.parseDouble(request.get("amountToDisbursed").toString()));
					}
			OtherChargesExpenseRequest _request = BtOtherChargesExpenseDBImpl.updateDetails(req, user);
			
			return btOtherChargesExpenseDB.getById(_request.getId());	
		}
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
	public Page<Map<String, Object>> getAll(long btExpenseId, Pageable page) throws BadRequestException {
		if(btExpenseId!=0)
		{
		return btOtherChargesExpenseDB.getByExpenseManagmentId(btExpenseId, page);
		}
		else
		{
			throw new BadRequestException("btExpense Id is null");
		}
	}

	@Override
	public List<Map<String, Object>> getList(long btExpenseId) throws BadRequestException {
		if(btExpenseId!=0)
		{
		return btOtherChargesExpenseDB.getListByExpenseManagmentId(btExpenseId);
		}
		else
		{
			throw new BadRequestException("btExpense Id is null");
		}
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class,BadRequestException.class,UnauthoriseException.class,
			RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	@Override
	public StatusMessageResponse deleteOtherChargesExpense(long id, LoginUser user) throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return BtOtherChargesExpenseDBImpl.deleteOtherChargesExpense(id, user);
	}

}
