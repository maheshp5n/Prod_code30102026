package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.entity.BtCase;
import com.pratikshat.exicom.entity.BtCaseProblemReported;
import com.pratikshat.exicom.entity.BtWccDetails;
import com.pratikshat.exicom.entity.CaseDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.UploadedFiles;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.RejectCaseRequest;


public interface BtCaseCustomRepository extends GenericOperation<BtCase> {

	public BtCase getAppointment(long id) throws BadRequestException;
	public BtCase getCaseForService(long id) throws BadRequestException;
	public BtCase getCaseWihtId(long id) throws BadRequestException;
	public BtCase getCaseUsingPerCase(long id) throws BadRequestException;
	BtCaseProblemReported saveDetails(BtCaseProblemReported t,LoginUser user) throws BadRequestException,UnauthoriseException;
	BtCase getCaseForInvoiceUpdate(String manualId) throws BadRequestException,UnauthoriseException;
	public BtCase updateDetail(BtCase btCase,MultipartFile file, LoginUser user) throws BadRequestException, UnauthoriseException ;
	public void UpdateCase(int domainId,long caseId,long reasonId,LoginUser user) throws BadRequestException;
	
	List<BtCase> getCaseWithWcc(BtWccDetails wcc) throws BadRequestException;
	
	public void deleteCaseProblemReported(long id);
	
	EmployeeDetails employeeDetails(String email)throws BadRequestException;
	
	BtCase findByCaseManualId(@Param("manualId")String manualId)throws BadRequestException;
	
	List<BtCase> getByStage(String manualId)throws BadRequestException;
	
	
	List<BtCase> getByListId(long[] id)throws BadRequestException;
    public void fileUpload(UploadedFiles user,MultipartFile file,LoginUser loginUser) throws BadRequestException,UnauthoriseException;
	public RejectCaseRequest rejectCase(RejectCaseRequest rejectCaseRequest, LoginUser user)throws BadRequestException, UnauthoriseException;
	
}
