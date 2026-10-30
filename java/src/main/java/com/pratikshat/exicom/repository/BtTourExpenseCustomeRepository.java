package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.LodgingExpenseRequest;
import com.pratikshat.exicom.model.request.TourExpenseRequest;
import com.pratikshat.exicom.model.response.StatusMessageResponse;
import com.pratikshat.exicom.services.GenricService;

public interface BtTourExpenseCustomeRepository extends GenericOperation<TourExpenseRequest> {
	

	StatusMessageResponse deleteTourExpense(long id,LoginUser user)throws BadRequestException,UnauthoriseException;

}
