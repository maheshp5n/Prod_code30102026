package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.StatusMessageResponse;

public interface BtBoardingExpenseService extends GenricService {
	
	Page<Map<String, Object>> getList(long id,Pageable page) throws BadRequestException;
	StatusMessageResponse deleteBtBoardingExpense(long id,LoginUser user)throws BadRequestException, UnauthoriseException;

}
