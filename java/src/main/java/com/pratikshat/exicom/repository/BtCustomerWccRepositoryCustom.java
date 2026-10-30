package com.pratikshat.exicom.repository;

import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.entity.BtCustomerWcc;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.UploadedFiles;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public interface BtCustomerWccRepositoryCustom extends GenericOperation<BtCustomerWcc> {

	public void wccfileUpload(UploadedFiles globalFile, MultipartFile wccfile, LoginUser user) throws BadRequestException, UnauthoriseException;

}
