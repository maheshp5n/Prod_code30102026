package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ContactDetails;
import com.pratikshat.exicom.model.request.Contacts;
import com.pratikshat.exicom.model.response.ContactForList;
import com.pratikshat.exicom.model.response.ExpenseManagementResponse;

@Repository
public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Long>,ContactDetailsCustomeRepository {
	int countByContactId(long contactId);
	com.pratikshat.exicom.entity.ContactDetails findByContactId(long contactId);
	
	@Query("select new com.pratikshat.exicom.model.request.Contacts(ct) from ContactDetails ct left join ct.contactDesignation cd left join ct.contactType ctp where ct.businessDomain.businessDomainId=:businessDomainId or ct.businessDomain.businessDomainId=3")
	Page<Contacts> getAllContacts(Pageable page, @Param("businessDomainId")int businessDomainId);
	@Query("select new com.pratikshat.exicom.model.request.Contacts(ct) from ContactDetails ct left join ct.contactDesignation cd left join ct.contactType  ctp where ctp.typeName=:type and (ct.businessDomain.businessDomainId=:businessDomainId or ct.businessDomain.businessDomainId=3)")
	Page<Contacts> getContactsByType(@Param("type")String type,Pageable page, @Param("businessDomainId")int businessDomainId);
	@Query("select new com.pratikshat.exicom.model.request.Contacts(ct) from ContactDetails ct left join ct.contactDesignation cd left join ct.contactType  ctp where ct.contactId=:id")
	Contacts getContacts(@Param("id")long id);
	@Query("select new com.pratikshat.exicom.model.request.Contacts(ct) from ContactDetails ct left join ct.contactDesignation cd left join ct.contactType  ctp join ContactsRecentVisit rt on ct.contactId=rt.contactId where rt.visitedByid=:username and (ct.businessDomain.businessDomainId=:businessDomainId or ct.businessDomain.businessDomainId=3)")
	Page<Contacts> getRecentVisit(@Param("username")String username,Pageable page, @Param("businessDomainId")int businessDomainId);
	@Query("select new com.pratikshat.exicom.model.request.Contacts(ct) from ContactDetails ct left join ct.contactDesignation cd left join ct.contactType  ctp where ct.account.sapCustomerNumber=:accountSapNumber and (ct.businessDomain.businessDomainId=:businessDomainId or ct.businessDomain.businessDomainId=3)")
	java.util.List<Contacts> getContactByAccount(@Param("accountSapNumber")String accountSapNumber, @Param("businessDomainId")int businessDomainId);
	@Query("select new com.pratikshat.exicom.model.request.Contacts(ct) from ContactDetails ct where ct.account.sapCustomerNumber=:accountSapNumber and (ct.businessDomain.businessDomainId=:businessDomainId or ct.businessDomain.businessDomainId=3)")
	Page<Contacts> getContactByAccount(@Param("accountSapNumber")String accountSapNumber,Pageable page, @Param("businessDomainId")int businessDomainId);
	@Query("select new com.pratikshat.exicom.model.response.LoginUserAccountsDetails(ct.contactId,ct.emailId,act.sapCustomerNumber,act.name,actt.accountTypeName,actc.businessDomainName,ct.firstName,ct.lastName, ct.middleName) from ContactDetails ct join ct.account act join act.accountType actt join act.businessDoamin actc where ct.contactId=:contactId")
	com.pratikshat.exicom.model.response.LoginUserAccountsDetails getLoginUserDetailsByContactId(@Param("contactId")long contactId);
	
	@Query("select new com.pratikshat.exicom.model.response.ContactForList(ct.contactId,ct.firstName,ct.middleName,ct.lastName,ct.emailId, ct.mobile) from ContactDetails ct join ct.account act join act.accountType atp join EmployeeDetails emp on emp.emailId=ct.emailId where atp.accountTypeName in :accountType and (ct.businessDomain.businessDomainId=:businessDomainId or ct.businessDomain.businessDomainId=3)")
	List<ContactForList> getAllContactsForServiceResources(@Param("accountType")List<String> accountType, @Param("businessDomainId")int businessDomainId);
	
	@Query("select new com.pratikshat.exicom.model.response.ContactForList(ct.contactId,ct.firstName,ct.middleName,ct.lastName,ct.emailId,ct.phone,ac.sapCustomerNumber,ac.parentAccount.sapCustomerNumber) from ContactDetails ct left join ct.account ac left join ac.accountType at where at.accountTypeName in :accountTypeName and (ct.businessDomain.businessDomainId=:businessDomainId or ct.businessDomain.businessDomainId=3)")
	List<ContactForList> getAllContactsList(@Param("accountTypeName") List<String> accountTypeName, @Param("businessDomainId")int businessDomainId);

	@Query("select new com.pratikshat.exicom.model.response.ExpenseManagementResponse(em,aco) from ExpenseManagement em left join em.businessDomain bd left join em.contact cnt left join em.expensePurpose ep left join em.expenseType et left join em.status es left join em.createdBy cby left join em.owner onr left join ExpenseAccountsOnly aco on aco.expense=em where cnt.contactId=:id and (cnt.businessDomain.businessDomainId=:businessDomainId or cnt.businessDomain.businessDomainId=3)")
	Page<ExpenseManagementResponse> getExpenseManagementByContactId(@Param("id") long id, Pageable page, @Param("businessDomainId")int businessDomainId);

	@Query("select new com.pratikshat.exicom.model.request.Contacts(ct) from ContactDetails ct left join ct.contactDesignation cd left join ct.contactType  ctp where ct.emailId=:emailId")
	Contacts getContactsByEmployeeEmailId(@Param("emailId")String emailId);
	
	@Query("select ct from ContactDetails ct left join ct.contactDesignation cd left join ct.contactType  ctp where ct.phone=:phone OR ct.mobile=:phone and ct.account.sapCustomerNumber=:account")
	ContactDetails getContactsByPhoneNoAndAccount(@Param("phone")String phone, @Param("account")String account);
	
	@Query("select ct from ContactDetails ct left join ct.contactDesignation cd left join ct.contactType  ctp where (ct.phone=:phone OR ct.mobile=:phone) and ct.account.sapCustomerNumber=:account and (ct.businessDomain.businessDomainId=1 or ct.businessDomain.businessDomainId=3)")
	ContactDetails getContactsByPhoneNoAndAccountForBat(@Param("phone")String phone, @Param("account")String account);

	@Query("select new com.pratikshat.exicom.model.response.ContactForList(ct.contactId,ct.firstName,ct.middleName,ct.lastName,ct.emailId,ct.phone,ac.sapCustomerNumber,ac.parentAccount.sapCustomerNumber) from ContactDetails ct left join ct.account ac left join ac.accountType at where at.accountTypeName in :accountTypeName and (ct.businessDomain.businessDomainId=:businessDomainId or ct.businessDomain.businessDomainId=3) and (ct.firstName like :contactName or ct.middleName like :contactName or ct.lastName like :contactName or ct.emailId like :contactName)")
	Page<ContactForList> getAllContactsListForList(@Param("accountTypeName") List<String> accountTypeName,@Param("contactName") String contactname,@Param("businessDomainId")int businessDomainId,Pageable page);
}
