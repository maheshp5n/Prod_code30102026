package com.pratikshat.exicom.repository;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.VendorPoDetails;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.VendorInvoiceCommercialRequest;
import com.pratikshat.exicom.model.request.VendorInvoiceRequest;
import com.pratikshat.exicom.model.request.VendorInvoiceSapRequest;
import com.pratikshat.exicom.model.response.CircleDetailsResponse;

public interface VendorPoDetailsRepositoryCustome {
	public void saveVendorInvoice(VendorInvoiceRequest req,LoginUser user) throws BadRequestException,UnauthoriseException;
	public void updateGrnDetails(VendorInvoiceCommercialRequest req, LoginUser user) throws BadRequestException,UnauthoriseException;
	public void udpateVendorInvoice(VendorInvoiceRequest req,LoginUser user) throws BadRequestException,UnauthoriseException;
	public void saveVendorInvoiceWithCopy(VendorInvoiceRequest req,LoginUser user,MultipartFile invoice) throws BadRequestException,UnauthoriseException;
	public void updateVendorSapDetails(VendorInvoiceSapRequest req,LoginUser user) throws BadRequestException,UnauthoriseException;
	public void updateVendorInvoiceWithCopy(VendorInvoiceRequest req,LoginUser user,MultipartFile invoice) throws BadRequestException,UnauthoriseException;
	public void submitforApproval(Long invoiceid,LoginUser user) throws BadRequestException,UnauthoriseException;
	
	public List<Map<String,Object>> advanceSearch(Map<String,Object> searchKeys,LoginUser user,List<String> userLists) throws ParseException;
	
	
}
