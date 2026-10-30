package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.request.OtherChargesExpenseRequest;

public interface OtherChargesExpenseCustomeRepository {

	public OtherChargesExpenseRequest saveOtherChargesExpense(OtherChargesExpenseRequest request,LoginUser user) throws Exception;
	public OtherChargesExpenseRequest updateOtherChargesExpense(OtherChargesExpenseRequest request,LoginUser user) throws Exception;
	
	public long deleteOtherChargesExpense(long id, LoginUser user) throws Exception;
}
