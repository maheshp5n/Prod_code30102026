package com.pratikshat.exicom.repository;


import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.Accounts;
import com.pratikshat.exicom.entity.AddressInformation;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.model.request.AccountCreate;

public interface AccountsCustomeRepo {

	AccountCreate createAccount(AccountCreate account,EmployeeDetails createdBy, int businessDomainId) throws Exception;
	void updateAccounsDetails(com.pratikshat.exicom.model.request.AccountCreate accountDetails,com.pratikshat.exicom.entity.LoginUser user,AddressInformation billingInfo,AddressInformation shippingInfo, int businessDomainId) throws Exception;
	List<AccountCreate> advanceSearch(Map<String,Object> searchKeys,LoginUser user,List<String>userList, int businessDomainId);
	
	public Accounts getWithId(String id) throws BadRequestException;
	
}
