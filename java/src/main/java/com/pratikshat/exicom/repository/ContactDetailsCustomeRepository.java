package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.ContactDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.Contacts;

public interface ContactDetailsCustomeRepository {
	Contacts saveContacts(Contacts contact,EmployeeDetails ownerDetails, int businessDomainId) throws BadRequestException,UnauthoriseException;
	Contacts updateContacts(Contacts contact,EmployeeDetails ownerDetails, int businessDomainId)throws BadRequestException,UnauthoriseException;
	List<com.pratikshat.exicom.model.request.Contacts> searchAdvance(Map<String,Object> searchKeys,LoginUser user,List<String> userList, int businessDomainId);
    
	public ContactDetails getWithContactIdAndAccountId(long contractId, String sapNumber) throws BadRequestException;
	public ContactDetails getWithContactWithId(long contractId) throws BadRequestException;
	ContactDetails getContactDetailsWithEmail(String email) throws BadRequestException,UnauthoriseException;

}
