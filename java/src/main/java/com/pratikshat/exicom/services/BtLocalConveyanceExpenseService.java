package com.pratikshat.exicom.services;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.StatusMessageResponse;

public interface BtLocalConveyanceExpenseService extends GenricService {

	Page<Map<String, Object>> getList(long btExpenseId,Pageable page) throws BadRequestException;
	
	StatusMessageResponse deleteLocalConveyanceExpense(long id, LoginUser user)throws BadRequestException,UnauthoriseException;
}
