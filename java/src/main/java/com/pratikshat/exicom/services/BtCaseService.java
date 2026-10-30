package com.pratikshat.exicom.services;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

import javax.mail.Multipart;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BtCaseRequest;
import com.pratikshat.exicom.model.request.CaseProofOfDeliveryRequest;
import com.pratikshat.exicom.model.request.ChangeCaseTypeRequest;
import com.pratikshat.exicom.model.request.InvoiceUpdateRequest;
import com.pratikshat.exicom.model.request.PodRequest;
import com.pratikshat.exicom.model.response.BtCaseResponse;

public interface BtCaseService extends GenricService {
	
	
	public Map<String ,Object> btCaseReject(Map<String,Object> btcase,LoginUser user)throws BadRequestException,UnauthoriseException;
	public Page<Map<String, Object>> getAll(LoginUser user,Pageable page,String filtername) throws BadRequestException, UnauthoriseException;	
	public Page<Map<String, Object>> getServiceAppointmentByCaseId(long caseid, Pageable page);
	public List<Map<String, Object>> getCaseForList(List<String> caseStatus);
	public Page<Map<String, Object>> getCaseListByStage(String stage, Pageable page,String filtername,LoginUser user)throws BadRequestException,UnauthoriseException;
	public void documentReceived(long caseId, LoginUser user)throws BadRequestException, UnauthoriseException;
	public CaseProofOfDeliveryRequest updateProofOfDelivery(CaseProofOfDeliveryRequest request, LoginUser user)throws BadRequestException, UnauthoriseException;
	public BtCaseResponse getByCaseId(LoginUser user,long caseid) throws BadRequestException,UnauthoriseException;
	public Map<String, Object> updateProofOfDelivery(Map<String, Object> request, LoginUser user)throws BadRequestException, UnauthoriseException;
	public InvoiceUpdateRequest updateInvoice(InvoiceUpdateRequest req, LoginUser user)throws BadRequestException, UnauthoriseException;
	public ChangeCaseTypeRequest changeCaseType(ChangeCaseTypeRequest req, LoginUser user)throws BadRequestException, UnauthoriseException;
	public BtCaseResponse saveDetails(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException ;
	BtCaseResponse saveDetailRequest(BtCaseRequest request, LoginUser user)
			throws BadRequestException, UnauthoriseException;
//    public List<Map<String, Object>> podNewUpdate(PodRequest req, List<MultipartFile> file,LoginUser user)throws BadRequestException,UnauthoriseException;
    public BtCaseResponse updateObjects(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException;
    public List<Map<String, Object>> getCasesubReason();
//<<<<<<< HEAD
//	public List<Map<String,Object>> advanceSearch(Map<String,Object> searchRequest,LoginUser user,List<String> users) throws BadRequestException,UnauthoriseException;
////	shagufta
//	public ByteArrayInputStream generatePdf(LoginUser user, long id) throws Exception;
//=======
	public List<Map<String,Object>> advanceSearch(Map<String,Object> searchRequest,LoginUser user,List<String> users,int size,int page) throws BadRequestException,UnauthoriseException;
//>>>>>>> de7d6498d2b2477ab7e78c79429cc9517a6ad3fd
	
//	public List<Map<String, Object>> getCasesubReason();
}