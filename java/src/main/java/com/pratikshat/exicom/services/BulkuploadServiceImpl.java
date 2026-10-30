package com.pratikshat.exicom.services;

import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.BulkUploadAction;
import com.pratikshat.exicom.entity.Bulkupload;
import com.pratikshat.exicom.entity.BulkuploadFileDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.LoginUserRoles;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BulkUploadRepository;
import com.pratikshat.exicom.repository.BulkuploadActionRepository;
import com.pratikshat.exicom.repository.BulkuploadFileDetailsRepository;

@Service
public class BulkuploadServiceImpl implements BulkuploadService {

	@Autowired
	BulkUploadRepository dbBulkUploadRepo;

	@Autowired
	BulkuploadActionRepository dbBulkUploadActionRepo;

	@Autowired
	BulkuploadFileDetailsRepository dbBulkUploadFileRepo;

	@Override
	public Page<Map<String, Object>> getBulkuploadOption(LoginUser user, Pageable page) {

		return dbBulkUploadRepo.getBulkuploadOptions("%" + getRole(user) + "%", "%USER:" + user.getUsername() + "%",
				page);
	}

	@Override
	public Map<String, Object> getBulkuploadOptionById(LoginUser user, Long id) {

		return dbBulkUploadRepo.getBulkuploadById(id);
	}

	@Override
	public Page<Map<String, Object>> getBulkuploadAction(LoginUser user, Long bulkuploadId, Pageable page) {

		return dbBulkUploadActionRepo.getBulkuploadActionBy(bulkuploadId, "%" + getRole(user) + "%",
				"%USER:" + user.getUsername() + "%", page);
	}

	@Override
	public Map<String, Object> getBulkuploadActions(LoginUser user, Long actionId) {

		return dbBulkUploadActionRepo.getBulkuploadActionById(actionId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	public void saveBulkuploadAction(BulkUploadAction actionDetails, LoginUser user, Long bulkId)
			throws BadRequestException, UnauthoriseException {
		Map<String, Object> blk = dbBulkUploadRepo.getBulkuploadById(bulkId);
		if (blk == null || blk.get("id") == null)
			throw new BadRequestException("Bulk upload id not found");
		if (blk.get("assigned_to") == null)
			throw new UnauthoriseException("You are not authorised");
		if (!blk.get("assigned_to").toString().contains("ROLE:" + getRole(user)))
			if (!blk.get("assigned_to").toString().contains("USER:" + user.getUsername()))
				throw new UnauthoriseException("You are not authorised");
		actionDetails.setOwnerRoles("ROLE:" + getRole(user) + ",USER:" + user.getUsername());
		actionDetails.setCreatedDate(new Date());
		actionDetails.setLastMofidiedDate(new Date());
		dbBulkUploadActionRepo.save(actionDetails);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	public void updateBulkuploadAction(BulkUploadAction actionDetails, LoginUser user, Long actionId)
			throws BadRequestException, UnauthoriseException {
		Map<String, Object> actn = dbBulkUploadActionRepo.getBulkuploadActionById(actionId);
		if (actn == null || actn.get("id") == null)
			throw new BadRequestException("Bulkupload Action not found");
		actionDetails.setId(Long.parseLong(actn.get("id").toString()));
		actionDetails.setLastMofidiedDate(new Date());
		actionDetails.setBulkUpload(new Bulkupload(Long.parseLong(actn.get("bulk_upload_id").toString())));
		dbBulkUploadActionRepo.save(actionDetails);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	public void saveBulkuploadDocument(BulkuploadFileDetails bulkuploadDocument, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		bulkuploadDocument.setCreatedBy(user.getUsername());
		bulkuploadDocument.setCreatedDate(new Date());

		// if(bulkuploadDocument.getPhysicalPath()==null||bulkuploadDocument.getPhysicalPath().equalsIgnoreCase(""))
		// throw new BadRequestException("File path not found");

		// if(bulkuploadDocument.getSize()==null||bulkuploadDocument.getSize().equals(0L))
		// throw new BadRequestException("File size is 0");

		dbBulkUploadFileRepo.save(bulkuploadDocument);
	}

	@Override
	public Map<String, Object> getDocumentDetails(Long id) {

		return dbBulkUploadFileRepo.getById(id);
	}

	private String getRole(LoginUser user) {
		String role = "ROLE:NOROLE";

		Iterator<LoginUserRoles> roleite = (Iterator<LoginUserRoles>) user.getAuthorities().iterator();
		while (roleite.hasNext()) {
			LoginUserRoles rls = roleite.next();
			role = "ROLE:" + rls.getAuthority();
			break;
		}

		return role;
	}

	@Override
	public void updateFilePath(BulkuploadFileDetails bulkuploadDocument, LoginUser user, Long docId)
			throws BadRequestException, UnauthoriseException {
		if (bulkuploadDocument.getId() == null || !bulkuploadDocument.getId().equals(docId))
			throw new BadRequestException("File id not found");
		BulkuploadFileDetails fl = dbBulkUploadFileRepo.findById(docId).get();
		fl.setPhysicalPath(bulkuploadDocument.getPhysicalPath());
		fl.setSize(bulkuploadDocument.getSize());
		dbBulkUploadFileRepo.save(fl);
	}

}
