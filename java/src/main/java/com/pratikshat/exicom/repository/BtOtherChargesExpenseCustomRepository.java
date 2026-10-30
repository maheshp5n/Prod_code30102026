package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtOtherChargesExpense;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.LodgingExpenseRequest;
import com.pratikshat.exicom.model.request.OtherChargesExpenseRequest;
import com.pratikshat.exicom.model.response.StatusMessageResponse;

public interface BtOtherChargesExpenseCustomRepository extends GenericOperation<OtherChargesExpenseRequest> {
	

	StatusMessageResponse deleteOtherChargesExpense(long id,LoginUser user)throws BadRequestException,UnauthoriseException;

}
