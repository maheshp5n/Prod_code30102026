package com.pratikshat.exicom.services;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pratikshat.exicom.entity.BulkUploadAction;
import com.pratikshat.exicom.entity.BulkuploadFileDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public interface BulkuploadService {
	
	Page<Map<String,Object>> getBulkuploadOption(LoginUser user,Pageable page);
	Map<String,Object> getBulkuploadOptionById(LoginUser user,Long id);
	
	Page<Map<String,Object>> getBulkuploadAction(LoginUser user,Long bulkuploadId,Pageable page);
	Map<String,Object> getBulkuploadActions(LoginUser user,Long actionId);
	
	void saveBulkuploadAction(BulkUploadAction actionDetails,LoginUser user,Long bulkId) throws BadRequestException,UnauthoriseException;
	void updateBulkuploadAction(BulkUploadAction actionDetails,LoginUser user,Long actionId) throws BadRequestException,UnauthoriseException;
	
	void saveBulkuploadDocument(BulkuploadFileDetails bulkuploadDocument,LoginUser user) throws BadRequestException,UnauthoriseException;
	
	Map<String,Object> getDocumentDetails(Long id);
	
	void updateFilePath(BulkuploadFileDetails bulkuploadDocument,LoginUser user,Long docId) throws BadRequestException,UnauthoriseException;
	
}
