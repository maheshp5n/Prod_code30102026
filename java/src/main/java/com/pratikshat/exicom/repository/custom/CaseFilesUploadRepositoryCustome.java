package com.pratikshat.exicom.repository.custom;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public interface CaseFilesUploadRepositoryCustome {
	public void uploadImage(Long caseId,List<MultipartFile> files,LoginUser user)throws BadRequestException,UnauthoriseException;
}
