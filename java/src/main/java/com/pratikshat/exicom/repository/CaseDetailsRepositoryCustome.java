package com.pratikshat.exicom.repository;

import java.util.List;

import com.pratikshat.exicom.entity.CaseDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.CaseProofOfDeliveryRequest;
import com.pratikshat.exicom.model.request.CaseRequest;
import com.pratikshat.exicom.model.request.CaseSearchRequest;
import com.pratikshat.exicom.model.request.ChangeCaseTypeRequest;
import com.pratikshat.exicom.model.request.InvoiceUpdateRequest;
import com.pratikshat.exicom.model.request.RejectCaseRequest;
import com.pratikshat.exicom.model.response.CaseResponse;

public interface CaseDetailsRepositoryCustome {
	public com.pratikshat.exicom.model.request.CaseRequest saveCaseDetails(com.pratikshat.exicom.model.request.CaseRequest caseDetails,com.pratikshat.exicom.entity.LoginUser user) throws Exception;
	public CaseRequest updateCase(CaseRequest caseDetails,com.pratikshat.exicom.entity.LoginUser user) throws Exception;
	
	public ChangeCaseTypeRequest changeCaseType(ChangeCaseTypeRequest req, LoginUser user)throws BadRequestException, UnauthoriseException;
	
	public List<CaseResponse> getCaseSearchResult(CaseSearchRequest searchRequest,List<String>userList);
	
	public RejectCaseRequest rejectCase(RejectCaseRequest rejectCaseRequest, LoginUser user)throws BadRequestException, UnauthoriseException;
	
	public InvoiceUpdateRequest updateInvoice(InvoiceUpdateRequest req, LoginUser user)throws BadRequestException, UnauthoriseException;
	
	public CaseProofOfDeliveryRequest updateProofOfDelivery(CaseProofOfDeliveryRequest request, LoginUser user)throws BadRequestException, UnauthoriseException;
	
	public void documentReceived(long caseId, LoginUser user)throws BadRequestException, UnauthoriseException;
}
