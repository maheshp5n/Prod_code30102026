package com.pratikshat.exicom.repository;

import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.entity.BtVendorInvoiceDetails;
import com.pratikshat.exicom.entity.BtVendorInvoiceGrn;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public interface BtVendorInvoiceDetailsRepositoryCustom extends GenericOperation<BtVendorInvoiceDetails>{
	
	BtVendorInvoiceDetails getDetailsWithId(long id)throws BadRequestException;
	
	public BtVendorInvoiceDetails saveVendorInvoiceWithCopy(BtVendorInvoiceDetails btVendorInvoiceDetails, LoginUser user, MultipartFile invoice)
			throws BadRequestException, UnauthoriseException ;
	
	public BtVendorInvoiceDetails updateVendorInvoiceWithCopy(BtVendorInvoiceDetails btVendorInvoiceDetails, LoginUser user, MultipartFile invoice)
			throws BadRequestException, UnauthoriseException ;
	
	BtVendorInvoiceGrn updateDetails(BtVendorInvoiceGrn btVendorInvoiceGrn,LoginUser user) throws BadRequestException,UnauthoriseException;

}
