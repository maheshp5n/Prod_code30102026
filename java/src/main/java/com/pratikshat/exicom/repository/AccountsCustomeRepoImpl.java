package com.pratikshat.exicom.repository;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.Accounts;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.StateProvinceDetails;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.entity.AccountsType;
import com.pratikshat.exicom.entity.AddressInformation;
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.CustomerDeatils;
import com.pratikshat.exicom.model.request.AccountCreate;
import com.pratikshat.exicom.model.response.CaseResponse;

@Repository
public class AccountsCustomeRepoImpl implements AccountsCustomeRepo {

	@PersistenceContext
	EntityManager em;

	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter interceptor;

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, RuntimeException.class, BadRequestException.class, UnauthoriseException.class,
			SQLException.class })
	@Override
	public AccountCreate createAccount(AccountCreate account, EmployeeDetails createdBy, int businessDomainId) throws Exception {
		AddressInformation billingAddress = new AddressInformation();

		billingAddress.setAddress(account.getBillingAddress());
		billingAddress.setCity(account.getBillingCity());
		// billingAddress.setCountry(account.getBillingCountry());
		billingAddress.setPincode(account.getBillingPincode());
		billingAddress.setState(new StateProvinceDetails(account.getBillingStateId()));
		billingAddress.setStreet(account.getBillingStreet());
		billingAddress.setSynced(false);
		em.persist(billingAddress);

		AddressInformation shippingAddress = new AddressInformation();
		if (account.isShippingAsBilling()) {
			shippingAddress = billingAddress;
		} else {
			if (account.getShippingAddress() != null && !account.getShippingAddress().isEmpty()) {
				shippingAddress.setAddress(account.getShippingAddress());
				shippingAddress.setCity(account.getShippingCity());
				// shippingAddress.setCountry(account.getShippingCountry());
				if (account.getShippingStateId() != 0) {
					shippingAddress.setState(new StateProvinceDetails(account.getShippingStateId()));
				} else {
					shippingAddress.setState(null);
				}
				shippingAddress.setPincode(account.getShippingPincode());
				shippingAddress.setStreet(account.getShippingStreet());
				shippingAddress.setSynced(false);
				em.persist(shippingAddress);
			}
		}
		
		Accounts act = null;
		try {
			act = em.createQuery("select act from Accounts act where act=?1", Accounts.class).setParameter(1, new Accounts(account.getSampAccountNumber())).getSingleResult();
		}catch (NoResultException ex) {

		}
		
		if(act!=null) {
			int accountBusinessDomainId = act.getBusinessDoamin().getBusinessDomainId();
			if(businessDomainId!=accountBusinessDomainId) {
				if(businessDomainId==1&&accountBusinessDomainId==2) {
					act.setBusinessDoamin(new BusinessDomain(3));
				}else if(businessDomainId==2&& accountBusinessDomainId==1) {
					act.setBusinessDoamin(new BusinessDomain(3));
				}
				
				em.persist(act);
			}
		}else {
			
			Accounts _account = new Accounts();
			if (account.getAccountTypeId() != 0) {
				_account.setAccountType(new AccountsType(account.getAccountTypeId()));
			}

			_account.setBillingInfo(billingAddress);
			if (account.getAccountCategoryId() != 0) {
				_account.setBusinessDoamin(new BusinessDomain(account.getAccountCategoryId()));
			}

			_account.setCreatedDate(new Date());
			_account.setEmailId(account.getEmailid());
			_account.setLastModified(new Date());
			_account.setName(account.getAccountName());
			_account.setPhoneNumber(account.getPhone());
			_account.setSapCustomerNumber(account.getSampAccountNumber());

			if (shippingAddress.getAddress() != null && !shippingAddress.getAddress().isEmpty()) {
				_account.setShipingInfo(shippingAddress);
			} else {
				_account.setShippingInfo(null);
			}
			_account.setCreatedBy(createdBy);
			_account.setModifiedBy(createdBy);
			_account.setAccountOwnerDetails(createdBy);
			_account.setDescription(account.getDescription());
			if (account.getParentAccountSapNumber() != null && !account.getParentAccountSapNumber().equalsIgnoreCase("")
					&& !account.getParentAccountSapNumber().equalsIgnoreCase("0"))
				_account.setParentAccount(new Accounts(account.getParentAccountSapNumber()));
			_account.setParentCustomer(new com.pratikshat.exicom.entity.CustomerDeatils(account.getParentCustomerId()));
			_account.setSynced(false);
			interceptor.executeWorkflow(em, _account, "INSERT", "PRE",
					new com.pratikshat.exicom.entity.LoginUser(createdBy.getEmailId()));
			em.persist(_account);
			interceptor.executeWorkflow(em, _account, "INSERT", "POST",
					new com.pratikshat.exicom.entity.LoginUser(createdBy.getEmailId()));
			
			try {
				GlobalSearchIndex _search = new GlobalSearchIndex();
				_search.setSearchString(_account.getSapCustomerNumber());
				_search.setKeyName("sapCustomerNumber");
				_search.setObjectName("Accounts");
				_search.setObjectDetailsUrl("accounts/" + _account.getSapCustomerNumber());
				_search.setObjectIdName("sapCustomerNumber");
				_search.setObjectIdValue(_account.getSapCustomerNumber());

				em.persist(_search);

			} catch (Exception e) {
			}

			try {
				GlobalSearchIndex _search = new GlobalSearchIndex();
				_search.setSearchString(_account.getName());
				_search.setKeyName("name");
				_search.setObjectName("Accounts");
				_search.setObjectDetailsUrl("accounts/" + _account.getSapCustomerNumber());
				_search.setObjectIdName("sapCustomerNumber");
				_search.setObjectIdValue(_account.getSapCustomerNumber());

				em.persist(_search);

			} catch (Exception e) {
			}
		}
		
		

		
		return account;
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, RuntimeException.class, BadRequestException.class, UnauthoriseException.class,
			SQLException.class })
	@Override
	public void updateAccounsDetails(com.pratikshat.exicom.model.request.AccountCreate accountDetails,
			com.pratikshat.exicom.entity.LoginUser user, AddressInformation billingInfo,
			AddressInformation shippingInfo, int businessDomainId) throws Exception {
		Accounts act = em.createQuery("select act from Accounts act where act=?1", Accounts.class)
				.setParameter(1, new Accounts(accountDetails.getSampAccountNumber())).getSingleResult();

		act.setAccountOwnerDetails(new com.pratikshat.exicom.entity.EmployeeDetails(accountDetails.getAccountOwner())); // accountUpdateSqlQuery.setParameter(1,));
		act.setAccountType(new com.pratikshat.exicom.entity.AccountsType(accountDetails.getAccountTypeId())); // accountUpdateSqlQuery.setParameter(2,));
		act.setBusinessDoamin(new com.pratikshat.exicom.entity.BusinessDomain(accountDetails.getAccountCategoryId())); // accountUpdateSqlQuery.setParameter(3,));

		act.setDescription(accountDetails.getDescription()); // accountUpdateSqlQuery.setParameter(4,);

		act.setEmailId(accountDetails.getEmailid()); // accountUpdateSqlQuery.setParameter(5,);
		act.setModifiedBy(new EmployeeDetails(user.getUsername()));// accountUpdateSqlQuery.setParameter(6,new
																	// java.util.Date());
		act.setLastModified(new Date()); // accountUpdateSqlQuery.setParameter(7,new
											// com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		act.setName(accountDetails.getAccountName()); // accountUpdateSqlQuery.setParameter(8,);
		if (accountDetails.getParentAccountSapNumber() == null
				|| accountDetails.getParentAccountSapNumber().equalsIgnoreCase("0")
				|| accountDetails.getParentAccountSapNumber().equalsIgnoreCase("")) {
			act.setParentAccount(null);// accountUpdateSqlQuery.setParameter(9,null);
		} else {
			act.setParentAccount(new Accounts(accountDetails.getParentAccountSapNumber()));
			// accountUpdateSqlQuery.setParameter(9,new
			// com.pratikshat.exicom.entity.Accounts());
		}
		act.setParentCustomer(new CustomerDeatils(accountDetails.getParentCustomerId())); // accountUpdateSqlQuery.setParameter(10,new
																							// com.pratikshat.exicom.entity.());
		act.setPhoneNumber(accountDetails.getPhone());// accountUpdateSqlQuery.setParameter(11,);
		em.persist(act);

		AddressInformation _accountB = null;
		try {
			Query addressQueryB = em.createQuery(
					"select bil from Accounts act left join act.billingInfo bil  where act.sapCustomerNumber=?1",
					com.pratikshat.exicom.entity.AddressInformation.class);
			addressQueryB.setParameter(1, accountDetails.getSampAccountNumber());
			_accountB = (AddressInformation) addressQueryB.getSingleResult();
		} catch (NoResultException ex) {

		}

		AddressInformation _accountS = null;
		try {
			Query addressQueryS = em.createQuery(
					"select shi from Accounts act left join act.shippingInfo shi where act.sapCustomerNumber=?1",
					com.pratikshat.exicom.entity.AddressInformation.class);
			addressQueryS.setParameter(1, accountDetails.getSampAccountNumber());
			_accountS = (AddressInformation) addressQueryS.getSingleResult();
		} catch (NoResultException ex) {

		}

		if (_accountB != null) {
			_accountB.setAddress(accountDetails.getBillingAddress());
			_accountB.setCity(accountDetails.getBillingCity());
			_accountB.setPincode(accountDetails.getBillingPincode());
			if (accountDetails.getBillingStateId() != 0) {
				_accountB.setState(
						new com.pratikshat.exicom.entity.StateProvinceDetails(accountDetails.getBillingStateId()));
			} else {
				_accountB.setState(null);
			}

			_accountB.setStreet(accountDetails.getBillingStreet());
			_accountB.setSynced(false);
			em.persist(_accountB);
		} else {
			AddressInformation billingAdderess = new AddressInformation();
			billingAdderess.setAddress(accountDetails.getBillingAddress());
			billingAdderess.setCity(accountDetails.getBillingCity());
			billingAdderess.setPincode(accountDetails.getBillingPincode());
			if (accountDetails.getBillingStateId() != 0) {
				billingAdderess.setState(
						new com.pratikshat.exicom.entity.StateProvinceDetails(accountDetails.getBillingStateId()));
			} else {
				billingAdderess.setState(null);
			}
			billingAdderess.setStreet(accountDetails.getBillingStreet());
			billingAdderess.setSynced(false);
			em.persist(billingAdderess);
			
			act.setBillingInfo(billingAdderess);

		}
		if (_accountS != null) {
			if (!accountDetails.isShippingAsBilling()) {
				_accountS.setAddress(accountDetails.getShippingAddress());
				_accountS.setCity(accountDetails.getShippingCity());
				_accountS.setPincode(accountDetails.getShippingPincode());
				if (accountDetails.getShippingStateId() != 0) {
					_accountS.setState(
							new com.pratikshat.exicom.entity.StateProvinceDetails(accountDetails.getShippingStateId()));
				} else {
					_accountS.setState(null);
				}

				_accountS.setStreet(accountDetails.getShippingStreet());
				_accountS.setSynced(false);
				em.persist(_accountS);
			} else {
				_accountS.setAddress(accountDetails.getBillingAddress());
				_accountS.setCity(accountDetails.getBillingCity());
				_accountS.setPincode(accountDetails.getBillingPincode());
				if (accountDetails.getBillingStateId() != 0) {
					_accountS.setState(
							new com.pratikshat.exicom.entity.StateProvinceDetails(accountDetails.getBillingStateId()));
				} else {
					_accountS.setState(null);
				}

				_accountS.setStreet(accountDetails.getBillingStreet());
				_accountS.setSynced(false);
				em.persist(_accountS);
			}
		} else {
			if (accountDetails.getShippingAddress() != null && !accountDetails.getShippingAddress().isEmpty()) {
				AddressInformation shippingAddress = new AddressInformation();
				shippingAddress.setAddress(accountDetails.getShippingAddress());
				shippingAddress.setCity(accountDetails.getShippingCity());
				shippingAddress.setPincode(accountDetails.getShippingPincode());
				if (accountDetails.getShippingStateId() != 0) {
					shippingAddress.setState(
							new com.pratikshat.exicom.entity.StateProvinceDetails(accountDetails.getShippingStateId()));
				} else {
					shippingAddress.setState(null);
				}

				shippingAddress.setStreet(accountDetails.getShippingStreet());
				shippingAddress.setSynced(false);
				em.persist(shippingAddress);

				act.setShippingInfo(shippingAddress);
			}
		}
		em.persist(act);
		interceptor.executeWorkflow(em,
				new com.pratikshat.exicom.entity.Accounts(accountDetails.getSampAccountNumber()), "UPDATE", "POST",
				user);
	}

	@Override
	public List<AccountCreate> advanceSearch(Map<String, Object> keys, LoginUser user, List<String> userList, int businessDomainId) {
		String _q = "select new com.pratikshat.exicom.model.request.AccountCreate(ac,ac.accountOwnerDetails,bi,si) from Accounts ac left join ac.shippingInfo si left join si.state sist left join ac.billingInfo bi left join bi.state bist where 1=1 and (ac.businessDoamin.businessDomainId=:businessDomainId or ac.businessDoamin.businessDomainId=3) ";
		StringBuilder queryBuilder = new StringBuilder();
		
		queryBuilder.append(_q);
		if (keys.get("sap_number") != null && !keys.get("sap_number").equals("")) {
			queryBuilder.append(" and ac.sapCustomerNumber like :sap_number");
		}
		if (keys.get("name") != null && !keys.get("name").equals("")) {
			queryBuilder.append(" and ac.name like :name");
		}
		if (keys.get("type") != null && !keys.get("type").equals("")) {
			queryBuilder.append(" and ac.accountType.accountTypeName like :type");
		}
		if (keys.get("email") != null && !keys.get("email").equals("")) {
			queryBuilder.append(" and ac.emailId like :email");
		}
		if (keys.get("phone") != null && !keys.get("phone").equals("")) {
			queryBuilder.append(" and ac.phoneNumber like :phone");
		}
		if (keys.get("address") != null && !keys.get("address").equals("")) {
			queryBuilder.append(" and (bi.address like :address or si.address like :address)");
		}
		if (keys.get("pincode") != null && !keys.get("pincode").equals("")) {
			queryBuilder.append(" and (bi.pincode like :pincode or si.pincode like :pincode)");
		}
		if (keys.get("state") != null && !keys.get("state").equals("")) {
			queryBuilder.append(" and (sist.stateName like :state or bist.stateName like :state)");
		}
		
		Query qry=em.createQuery(queryBuilder.toString(),AccountCreate.class);
		
		qry.setParameter("businessDomainId",businessDomainId);
		
		if (keys.get("sap_number") != null && !keys.get("sap_number").equals("")) {
			qry.setParameter("sap_number","%"+keys.get("sap_number").toString().trim()+"%");
		}
		if (keys.get("name") != null && !keys.get("name").equals("")) {
			//queryBuilder.append(" and ac.name like :name");
			qry.setParameter("name", "%"+keys.get("name").toString().trim()+"%");
		}
		if (keys.get("type") != null && !keys.get("type").equals("")) {
			//queryBuilder.append(" and ac.accountType.accountTypeName like :type");
			qry.setParameter("type", "%"+keys.get("type").toString().trim()+"%");
		}
		if (keys.get("email") != null && !keys.get("email").equals("")) {
			//queryBuilder.append(" and ac.emailId like :email");
			qry.setParameter("email", "%"+keys.get("email").toString().trim()+"%");
		}
		if (keys.get("phone") != null && !keys.get("phone").equals("")) {
			//queryBuilder.append(" and ac.phoneNumber like :phone");
			qry.setParameter("phone", "%"+keys.get("phone").toString().trim()+"%");
		}
		if (keys.get("address") != null && !keys.get("address").equals("")) {
			//queryBuilder.append(" and (bi.address like :address or si.address like :address)");
			qry.setParameter("address", "%"+keys.get("address").toString().trim()+"%");
		}
		if (keys.get("pincode") != null && !keys.get("pincode").equals("")) {
			//queryBuilder.append(" and (bi.pincode like :pincode or si.pincode like :pincode)");
			qry.setParameter("pincode", "%"+keys.get("pincode").toString().trim()+"%");
		}
		if (keys.get("state") != null && !keys.get("state").equals("")) {
			//queryBuilder.append(" and (sist.stateName like :state or bist.stateName like :state)");
			qry.setParameter("state", "%"+keys.get("state").toString().trim()+"%");
		}
		
		List<AccountCreate> csRes= qry.getResultList();
		return csRes;
	}

	@Override
	public Accounts getWithId(String id) throws BadRequestException {
		Accounts accounts=null;	
        
    	try {
    		Query selectAccountSap=em.createQuery("select asn from Accounts asn where asn.sapCustomerNumber=?1",Accounts.class);
    		selectAccountSap.setParameter(1, id);
    		accounts=(Accounts) selectAccountSap.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Account does not exist");
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Multiple account exist with this account Id");
		}
		return accounts;
	}

}
