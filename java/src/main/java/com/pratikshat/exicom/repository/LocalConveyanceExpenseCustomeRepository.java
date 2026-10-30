package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.request.LocalConveyanceExpenseRequest;

public interface LocalConveyanceExpenseCustomeRepository {

	public LocalConveyanceExpenseRequest saveLocalConveyanceExpense(LocalConveyanceExpenseRequest request,LoginUser user) throws Exception;
	public LocalConveyanceExpenseRequest updateLocalConveyanceExpense(LocalConveyanceExpenseRequest request,LoginUser user) throws Exception;
	
	public long deleteLocalConveyanceExpense(long boardingExpenseId, LoginUser user) throws Exception;
}
