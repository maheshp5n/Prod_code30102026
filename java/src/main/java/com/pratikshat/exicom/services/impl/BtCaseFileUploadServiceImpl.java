package com.pratikshat.exicom.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.entity.BtCase;
import com.pratikshat.exicom.entity.BtCaseFileUpload;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtCaseFileUploadRepository;
import com.pratikshat.exicom.repository.BtCaseRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtCaseFileUploadService;
import com.pratikshat.exicom.services.FileStorageService;
@Service
public class BtCaseFileUploadServiceImpl implements BtCaseFileUploadService {

	@Autowired BtCaseFileUploadRepository dbRepo;
	@Autowired FileStorageService fileStorage;
	@Autowired BtCaseRepository dbCase;
	@Autowired UserProfileRepository dbUserProfile;
	
	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		return dbRepo.getById((Long)id);
	}

	@Deprecated
	/*
	 * No in use;
	 * */
	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Deprecated
	@Override
	/*
	 * Do not use this method to save, will not save object
	 * */
	public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Deprecated
	/*
	 * 
	 * Do not use this method to update, will not update object
	 * */
	@Override
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	/*
	 * No in use;
	 * */
	public List<Map<String, Object>> searchForList(String search, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	/*
	 * No in use;
	 * */
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getCaseFilesByBtCaseId(Long caseId, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return dbRepo.getByBtCaseId(caseId);
	}

	@Override
	public void saveFileList(Long btCaseId, List<MultipartFile> files, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(dbUserProfile.hasObjectAccess(user.getUsername(),BtCaseFileUpload.class, UserProfileRepository.CREATE)) {
			if(files!=null&&files.size()>0) {
				for(MultipartFile file:files) {
					BtCase cs=dbCase.findById(btCaseId).get();
					String fileName=fileStorage.storeFile(file);
					BtCaseFileUpload upload=new BtCaseFileUpload();
					upload.setBtCase(cs);
					upload.setFileName(file.getOriginalFilename());
					//upload.setManualId(manualId);
					upload.setOwner(new EmployeeDetails(user.getUsername()));
					upload.setPhysicalFileLocation(fileName);
					dbRepo.saveDetails(upload, user);
				}
				
			}			
		}
		else 
			throw new UnauthoriseException("You are not authorised");

	}

}
