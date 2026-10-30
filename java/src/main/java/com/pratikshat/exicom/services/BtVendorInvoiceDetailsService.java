package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;


public interface BtVendorInvoiceDetailsService extends GenricService{
	
	public Map<String, Object> saveVendorInvoiceWithCopy( Map<String, Object>req,LoginUser user,MultipartFile invoice) throws BadRequestException,UnauthoriseException;
	public Map<String, Object> updateVendorSapDetails( Map<String, Object>req,LoginUser user) throws BadRequestException,UnauthoriseException;
	public Map<String, Object> updateVendorInvoiceWithCopy( Map<String, Object> req,LoginUser user,MultipartFile invoice) throws BadRequestException,UnauthoriseException;
	public Page<Map<String, Object>> getData(LoginUser user, Pageable page, Integer filtername) throws BadRequestException,UnauthoriseException;
	public Map<String, Object> updateGrnDetails(Map<String, Object>req, LoginUser user) throws BadRequestException,UnauthoriseException;
	public List<String> getInvoiceStatusList();
	public List<Map<String,Object>> getPoCategory();
	public List<Map<String,Object>> getAllPoType();

	public void submitForApproval(LoginUser user, Long id) throws BadRequestException,UnauthoriseException;
}
