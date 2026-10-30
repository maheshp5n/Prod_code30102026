package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BoardingExpenseRequest;
import com.pratikshat.exicom.model.request.LocalConveyanceExpenseRequest;
import com.pratikshat.exicom.model.response.StatusMessageResponse;

public interface BtLocalConveyanceExpenseCustomRepository extends GenericOperation<LocalConveyanceExpenseRequest> {

	StatusMessageResponse deleteLocalConveyanceExpense(long id, LoginUser user)throws BadRequestException,UnauthoriseException;

	
}
