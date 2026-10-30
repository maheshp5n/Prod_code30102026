package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.StatusMessageResponse;

public interface BtOtherChargesExpenseService extends GenricService {

	Page<Map<String, Object>> getAll(long btExpenseId,Pageable page) throws BadRequestException;
	
	List<Map<String, Object>> getList(long btExpenseId) throws BadRequestException;
	
	StatusMessageResponse deleteOtherChargesExpense(long id,LoginUser user)throws BadRequestException, UnauthoriseException;
	
}
