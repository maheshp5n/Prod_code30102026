package com.pratikshat.exicom.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.CaseDetails;
import com.pratikshat.exicom.entity.CaseFilesUpload;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.custom.CaseFilesUploadRepositoryCustome;
import com.pratikshat.exicom.services.FileStorageService;

public class CaseFilesUploadRepositoryCustomeImpl implements CaseFilesUploadRepositoryCustome {
	@PersistenceContext
	EntityManager em;

	@Autowired
	FileStorageService fileServie;
	
	@Override
	@Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class})
	public void uploadImage(Long caseId, List<MultipartFile> files, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		CaseDetails caseDetails=em.find(CaseDetails.class, caseId);
		if((caseDetails.getStatus().getId()==1||caseDetails.getStatus().getId()==3)&&(caseDetails.getStage().getId()==1||caseDetails.getStage().getId()==5||caseDetails.getStage().getId()==6||caseDetails.getStage().getId()==7|| caseDetails.getStage().getId()==11||caseDetails.getStage().getId()==12)) {
			if(files!=null&&files.size()>0) {
				for(MultipartFile file:files) {
					String fileLocationName= fileServie.storeFile(file);
					CaseFilesUpload upload=new CaseFilesUpload();
					upload.setCaseDetails(new CaseDetails(caseId));
					upload.setOwner(new EmployeeDetails(user.getUsername()));
					upload.setUploadedFileName(file.getOriginalFilename());
					upload.setUploadPhysicalLocation(fileLocationName);
					
					em.persist(upload);
					upload.setManualId("CS-FILES-"+MiscellaneousUtils.fillZero(String.valueOf(upload.getId()), 5));
					System.out.println("Implementing case file upload save");
				}
				
			}
			else
				System.out.println("No file found for upload");	
		}
		else
			throw new BadRequestException("Case status has changed");
	}
	
	
}
