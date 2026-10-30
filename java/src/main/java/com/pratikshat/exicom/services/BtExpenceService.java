package com.pratikshat.exicom.services;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.ExpenseAccountsOnlyRequest;
import com.pratikshat.exicom.model.response.BtExpenseManagementResponse;
import com.pratikshat.exicom.model.response.ExpenseManagementResponse;
import com.pratikshat.exicom.model.response.GlCodeResponse;

public interface BtExpenceService extends GenricService  {

	 public List<Map<String,Object>> advanceSearch(Map<String,Object> searchKey,LoginUser user,List<String> users) throws BadRequestException,UnauthoriseException;

public List<Map<String,Object>> getBusinessArea(int businessDomainId);

public List<Map<String,Object>> getBusinessPlace(int businessDomainId);

public List<Map<String,Object>> getCostCenter(int businessDomainId);

public List<Map<String,Object>> getExpensePurpose(int businessDomainId);

public List<Map<String,Object>> getExpenseType(int businessDomainId);

public List<Map<String,Object>> getStatus(int businessDomainId);

Map<String,Object> saveUpdateExpenseAccountsOnly(Map<String,Object> map, LoginUser user)throws BadRequestException,UnauthoriseException;

void submitForApproval(long btExpenseManagementId,String remark, int businessDoaminId, LoginUser user)throws BadRequestException,UnauthoriseException;

ByteArrayInputStream createPdf(LoginUser user, BtExpenseManagementResponse response) throws BadRequestException,UnauthoriseException;

List<GlCodeResponse> getGLCodeSummary(long btExpenseManagementId, LoginUser user) throws BadRequestException,UnauthoriseException;




}
