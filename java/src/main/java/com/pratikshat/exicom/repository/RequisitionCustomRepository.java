package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.request.RequisitionRequest;

public interface RequisitionCustomRepository {

	public RequisitionRequest createRequisition(RequisitionRequest requisitionRequest,LoginUser user) throws Exception;
	public RequisitionRequest updateRequisition(RequisitionRequest requisitionRequest,LoginUser user) throws Exception;
}
