package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.request.LodgingExpenseRequest;

public interface LodgingExpenseCustomeRepository {

	public LodgingExpenseRequest saveLodgingExpense(LodgingExpenseRequest lodgingExpenseRequest,LoginUser user) throws Exception;
	public LodgingExpenseRequest updateLodgingExpense(LodgingExpenseRequest lodgingExpenseRequest,LoginUser user) throws Exception;
	
	public long deleteLogdingExpense(long id,LoginUser user) throws Exception;
}
