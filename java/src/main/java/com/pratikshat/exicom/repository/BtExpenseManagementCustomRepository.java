package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtExpenseManagement;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BtExpenseManagementRequest;
import com.pratikshat.exicom.model.request.ExpenseAccountsOnlyRequest;
import com.pratikshat.exicom.model.response.GlCodeResponse;

public interface BtExpenseManagementCustomRepository extends GenericOperation<BtExpenseManagementRequest> {

	ExpenseAccountsOnlyRequest saveUpdateExpenseAccountsOnly(ExpenseAccountsOnlyRequest expenseAccountsOnlyRequest, LoginUser user) throws BadRequestException,UnauthoriseException;

	List<GlCodeResponse> getGLCodeSummary(long btExpenseManagementId, LoginUser user) throws BadRequestException,UnauthoriseException;

	void submitForApproval(long btExpenseManagementId, String remark, int businessDoaminId, LoginUser user) throws BadRequestException,UnauthoriseException;
}
