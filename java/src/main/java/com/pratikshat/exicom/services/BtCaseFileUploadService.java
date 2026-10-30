package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public interface BtCaseFileUploadService extends GenricService {
	List<Map<String,Object>> getCaseFilesByBtCaseId(Long caseId,LoginUser user) throws BadRequestException,UnauthoriseException;
	void saveFileList(Long btCaseId,List<MultipartFile> files,LoginUser user) throws BadRequestException,UnauthoriseException;
}
