package com.pratikshat.exicom.services.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.BtVendorInvoiceDetails;
import com.pratikshat.exicom.entity.BtVendorInvoiceSap;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.VendorInvoiceDetails;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtVendorInvoiceDetailsRepository;
import com.pratikshat.exicom.repository.BtVendorInvoiceSapRepository;
import com.pratikshat.exicom.services.BtVendorInvoiceSapService;
import com.pratikshat.exicom.utils.DateConverter;

@Service
public class BtVendorInvoiceSapServiceImpl implements BtVendorInvoiceSapService{

	
	@Autowired
	BtVendorInvoiceSapRepository dbRepo;
	@Autowired
	BtVendorInvoiceDetailsRepository dbVendor;
	
	SimpleDateFormat fr=new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");

	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		
		return null;
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		BtVendorInvoiceDetails _invoice = dbVendor
				.getDetailsWithId(Long.parseLong(request.get("vendorInvoiceId").toString()));
		BtVendorInvoiceSap sap=new BtVendorInvoiceSap();
		sap.setOwner(new EmployeeDetails(user.getUsername()));
		sap.setSapDate(DateConverter.convertToDate(request.get("sapDate").toString()));
		sap.setSapNumber(request.get("sapNumber").toString());
		sap.setVendorIvoice(_invoice);
		dbRepo.saveDetails(sap, user);
		
		return new HashMap<String, Object>();
	}

	@Override
	@Transactional
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		Optional<BtVendorInvoiceSap> _sap =dbRepo.findById(Long.parseLong(request.get("vendorInvoiceId").toString()));
		if(!_sap.isPresent())
			throw new BadRequestException("Vendor Invoice SAP details not found");
		BtVendorInvoiceSap sap=_sap.get();
		sap.setOwner(new EmployeeDetails(user.getUsername()));
		sap.setSapDate(DateConverter.convertToDate(request.get("sapDate").toString()));
		sap.setSapNumber(request.get("sapNumber").toString());
		//sap.setVendorIvoice(new BtVendorInvoiceDetails(Long.parseLong(request.get("vendorInvoiceId").toString())));
		dbRepo.updateDetails(sap, user);
		return new HashMap<String, Object>();
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

}
