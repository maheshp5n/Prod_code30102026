package com.pratikshat.exicom.repository;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.request.ExpenseAccountsOnlyRequest;
import com.pratikshat.exicom.model.request.ExpenseManagementRequest;
import com.pratikshat.exicom.model.response.ExpenseManagementResponse;
import com.pratikshat.exicom.model.response.GlCodeResponse;

public interface ExpenseManagementCustomRepository {

	ExpenseManagementRequest saveExpense(ExpenseManagementRequest request,LoginUser user) throws Exception;
	ExpenseManagementRequest updateExpense(ExpenseManagementRequest request,LoginUser user) throws Exception;
	void submitForApproval(long expenseManagementId,String remark, int businessDoaminId, LoginUser user)throws Exception;
	
	ByteArrayInputStream createPdf(LoginUser user, ExpenseManagementResponse response) throws Exception;
	
	List<GlCodeResponse> getGLCodeSummary(long expenseManagementId, LoginUser user) throws Exception;
	
	ExpenseAccountsOnlyRequest saveUpdateExpenseAccountsOnly(ExpenseAccountsOnlyRequest expenseAccountsOnlyRequest, LoginUser user)throws Exception;
	
	public List<ExpenseManagementResponse> advanceSearch(Map<String,Object> searchKeys,LoginUser user,List<String> userLists)throws ParseException;
}
