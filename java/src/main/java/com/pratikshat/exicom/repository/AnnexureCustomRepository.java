package com.pratikshat.exicom.repository;

import java.io.ByteArrayInputStream;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.request.AnnexureLineItemRequest;
import com.pratikshat.exicom.model.request.AnnexureRequest;

public interface AnnexureCustomRepository {

	AnnexureRequest saveAnnexure(AnnexureRequest request,LoginUser user)throws Exception;
	AnnexureRequest updateAnnexure(AnnexureRequest request,LoginUser user)throws Exception;
	
	AnnexureLineItemRequest addAnnexureLineItem(AnnexureLineItemRequest request, LoginUser user)throws Exception;
	AnnexureLineItemRequest updateAnnexureLineItem(AnnexureLineItemRequest request, LoginUser user)throws Exception;
	
	void deleteAnnexureLineItem(long annexureLineItemId, LoginUser user) throws Exception;
	
	void submitForApproval(long annexureId, int businessDomainId, LoginUser user) throws Exception;
	
	
	ByteArrayInputStream createPdf(long annexureId,LoginUser user) throws Exception;
}
