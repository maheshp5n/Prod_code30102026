package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.Accounts;
import com.pratikshat.exicom.model.request.AccountCreate;
import com.pratikshat.exicom.model.request.Contacts;
import com.pratikshat.exicom.model.response.AccountsForList;
import com.pratikshat.exicom.model.response.ContactForList;
@Repository

public interface AccountsRepository extends JpaRepository<Accounts, String>,AccountsCustomeRepo {
	@Transactional(readOnly=true)
	int countBySapCustomerNumber(String sapCustomerNumber);
	@Transactional(readOnly=true)
	@Query("select new com.pratikshat.exicom.model.request.AccountCreate(ac,ac.accountOwnerDetails,bi,si) from Accounts ac left join ac.shippingInfo si left join ac.billingInfo bi where ac.businessDoamin.businessDomainId=:businessDomainId or ac.businessDoamin.businessDomainId=3")
	Page<AccountCreate> getAll(Pageable page, @Param("businessDomainId")int businessDomainId);
	
	@Transactional(readOnly=true)
	@Query("select new com.pratikshat.exicom.model.request.AccountCreate(ac,ac.accountOwnerDetails,bi,si) from Accounts ac left join ac.accountOwnerDetails onr left join ac.shippingInfo si left join ac.billingInfo bi where onr in :userlist and (ac.businessDoamin.businessDomainId=:businessDomainId or ac.businessDoamin.businessDomainId=3)")
	Page<AccountCreate> getAllByUserList(@Param("userlist")java.util.List<com.pratikshat.exicom.entity.EmployeeDetails> userList,Pageable page, @Param("businessDomainId")int businessDomainId);
	@Transactional(readOnly=true)
	Accounts findBySapCustomerNumber(String sapNumber);
	@Transactional(readOnly=true)
	@Query("select new com.pratikshat.exicom.model.request.AccountCreate(ac,ac.accountOwnerDetails,bil,ship) from Accounts ac left join ac.billingInfo bil left join ac.shippingInfo ship where ac.sapCustomerNumber=:sapNumber")
	AccountCreate getBySapCustomerNumber(@Param("sapNumber")String sapNumber);
	@Transactional(readOnly=true)
	@Query("select new com.pratikshat.exicom.model.request.AccountCreate(ac,ac.accountOwnerDetails,bi,si) from Accounts ac left join ac.shippingInfo si left join ac.billingInfo bi join  AccountRecentVisit rac on ac.sapCustomerNumber=rac.accountSapNumber where rac.visitorEmailId=:emailId and (ac.businessDoamin.businessDomainId=:businessDomainId or ac.businessDoamin.businessDomainId=3)")
	Page<AccountCreate> getRecentVisist(@Param("emailId")String visitorEmailId, Pageable page, @Param("businessDomainId")int businessDomainId);
	@Transactional(readOnly=true)
	@Query("select new com.pratikshat.exicom.model.request.AccountCreate(ac,ac.accountOwnerDetails,bi,si) from Accounts ac left join ac.shippingInfo si left join ac.billingInfo bi left join ContactDetails ct on ct.account=ac where ct.emailId=:emailId and (ac.businessDoamin.businessDomainId=:businessDomainId or ac.businessDoamin.businessDomainId=3)")
	Page<AccountCreate> getMyAccount(@Param("emailId")String emailId,Pageable page, @Param("businessDomainId")int businessDomainId);
	@Transactional(readOnly=true)
	@Query("select new com.pratikshat.exicom.model.response.AccountsForList(ac.sapCustomerNumber,ac.name,pac.sapCustomerNumber) from Accounts ac left join ac.parentAccount pac where ac.businessDoamin.businessDomainId=:businessDomainId or ac.businessDoamin.businessDomainId=3")
	List<AccountsForList> getAccountForList(@Param("businessDomainId")int businessDomainId);
	@Transactional(readOnly=true)
	@Query("select new com.pratikshat.exicom.model.request.Contacts(ct) from ContactDetails ct where ct.account.sapCustomerNumber=:sapNumber")
	List<Contacts> getAccountsContacts(@Param("sapNumber")String sapNumber);
	@Transactional(readOnly=true)
	@Query("select new com.pratikshat.exicom.model.response.ContactForList(ct.contactId,ct.firstName,ct.middleName,ct.lastName,ct.emailId,ct.mobile) from ContactDetails ct where ct.account.sapCustomerNumber=:sapNumber")
	List<com.pratikshat.exicom.model.response.ContactForList> getContactForListByAccountId(@Param("sapNumber")String sapNumber);
	
	@Query("SELECT new com.pratikshat.exicom.model.response.ContactForList(ct.contactId,ct.firstName,ct.middleName,ct.lastName,ct.emailId,ct.mobile) FROM ContactDetails ct  WHERE ct.emailId NOT IN(SELECT ur.username FROM LoginUser ur) AND ct.account.sapCustomerNumber=:sapNumber")
	List<com.pratikshat.exicom.model.response.ContactForList> getContactNotUserForListByAccountId(@Param("sapNumber")String sapNumber);

	@Query("select new com.pratikshat.exicom.model.response.AssetsForList(ast.serialNumber,pr.productPartCode,pr.pdoductDescription) from Assets ast join ast.product pr join ast.account act where act.sapCustomerNumber=:sapNumber")
	List<com.pratikshat.exicom.model.response.AssetsForList> getAssetsForListBySapNumber(@Param("sapNumber")String sapNumber);
	
	@Query(value = "SELECT e.`email_id` FROM `accounts` ac \r\n"
			+ "LEFT JOIN `contact_details` cd ON cd.`account_id`=ac.`sap_customer_number`\r\n"
			+ "LEFT JOIN `employee_details` e ON e.`contact_contact_id`=cd.`contact_id`\r\n"
			+ "WHERE ac.`sap_customer_number`=:sapNumber",nativeQuery = true)
	List<String> getAccountsUser(@Param("sapNumber")String sapNumber);
	
	//create by shyamu
	@Query("select new com.pratikshat.exicom.model.request.Contacts(ct) from ContactDetails ct where ct.account.sapCustomerNumber=:sapNumber")
	Page<com.pratikshat.exicom.model.request.Contacts> getContactByAccountId(@Param("sapNumber")String sapNumber,Pageable page);

	//close

	@Query("select new com.pratikshat.exicom.model.response.ContactForList(ct.contactId,ct.firstName,ct.middleName,ct.lastName,ct.emailId,ct.mobile) from ContactDetails ct where ct.account.sapCustomerNumber=:sapNumber and (ct.firstName like :conta or ct.middleName like :conta  or ct.lastName like :conta)")
	Page<Contacts> getAccountsContacts(@Param("sapNumber")String sapNumber,@Param("conta")String conta,Pageable page);
	//for Bt

	@Query("select new com.pratikshat.exicom.model.request.Contacts(ct) from ContactDetails ct left join ct.businessDomain bd where ct.account.sapCustomerNumber=:sapNumber AND ((ct.firstName like :conta or ct.middleName like :conta)  OR (ct.lastName like :conta or ct.mobile like :conta)) AND (bd.businessDomainId=1 or bd.businessDomainId=3)")
	Page<Contacts> getBtAccountsContacts(@Param("sapNumber")String sapNumber,@Param("conta")String conta,Pageable page);	

}
