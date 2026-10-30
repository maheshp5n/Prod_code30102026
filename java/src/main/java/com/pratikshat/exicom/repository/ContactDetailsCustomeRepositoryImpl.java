package com.pratikshat.exicom.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.Accounts;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.ExpenseManagementBusinessPlace;
import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.LegalEntity;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.entity.AddressInformation;
import com.pratikshat.exicom.entity.BoardingLoadingDetails;
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.ContactDesignation;
import com.pratikshat.exicom.entity.ContactDetails;
import com.pratikshat.exicom.entity.ContactType;
import com.pratikshat.exicom.model.request.Contacts;

public class ContactDetailsCustomeRepositoryImpl implements ContactDetailsCustomeRepository {
	@PersistenceContext
	private EntityManager em;
	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;
	
	@Transactional(isolation=Isolation.READ_UNCOMMITTED,rollbackFor = {Exception.class,RuntimeException.class,BadRequestException.class,UnauthoriseException.class},propagation = Propagation.REQUIRES_NEW)
	public Contacts saveContacts(Contacts contact,EmployeeDetails ownerDetails, int businessDomainId) throws BadRequestException,UnauthoriseException {
		if(contact.getContactTypeId()!=2) {
			if(contact.getContactEmailId()==null || contact.getContactEmailId().equalsIgnoreCase("")) {
				throw new BadRequestException("Please provide contact email id");
			}	
		}
		
		if(contact.getContactEmailId()!=null && !contact.getContactEmailId().equalsIgnoreCase("")) {
			long count= em.createQuery("select count(contact.emailId) from ContactDetails contact where contact.emailId=?1 and contact.businessDomain.businessDomainId=?2",Long.class).setParameter(1, contact.getContactEmailId()).setParameter(2, businessDomainId).getSingleResult();
			if(count>0) {
				throw new BadRequestException(String.format("Email id %s is already in use",contact.getContactEmailId()));
			}	
		}
		
		if(contact.getContactPhoneNumber()!=null && !contact.getContactPhoneNumber().equalsIgnoreCase("")) {
			long Phonecount= em.createQuery("select count(contact.phone) from ContactDetails contact  where contact.phone=?1 and contact.businessDomain.businessDomainId=?2",Long.class).setParameter(1, contact.getContactPhoneNumber()).setParameter(2, businessDomainId).getSingleResult();
			if(Phonecount>0) {
				throw new BadRequestException(String.format("Phone No.  %s is already in use",contact.getContactPhoneNumber()));
			}	
		}
		
		if(contact.getContactMobileNumber()!=null && !contact.getContactMobileNumber().equalsIgnoreCase("")) {
			long mobileCount= em.createQuery("select count(contact.mobile) from ContactDetails contact left join LoginUser ur on ur.username = contact.emailId where contact.mobile=?1 and contact.businessDomain.businessDomainId=?2 and ur.enabled=?3",Long.class).setParameter(1, contact.getContactMobileNumber()).setParameter(2, businessDomainId).setParameter(3, true).getSingleResult();
				if(mobileCount>0) {
					throw new BadRequestException(String.format("Mobile No.  %s is already in use",contact.getContactMobileNumber()));
				}				
		}
		////kalindi for battery customer unique mobile number
		if(contact.getContactMobileNumber()!=null && !contact.getContactMobileNumber().equalsIgnoreCase("") && contact.getContactTypeId()==2) {
			long mobileCount= em.createQuery("select count(contact.mobile) from ContactDetails contact where contact.mobile=?1 and contact.businessDomain.businessDomainId=?2 and contactType.typeId=?3",Long.class).setParameter(1, contact.getContactMobileNumber()).setParameter(2, businessDomainId).setParameter(3, contact.getContactTypeId()).getSingleResult();
			if(mobileCount>0) {
				throw new BadRequestException(String.format("Mobile No.  %s is already in use",contact.getContactMobileNumber()));
			}	
		}
		
		ContactDetails _contactExist = null;
		try {
			Query contactDetailsQuery=em.createQuery("select cd from ContactDetails cd where cd.emailId=?1",ContactDetails.class);
			contactDetailsQuery.setParameter(1,contact.getContactEmailId());
			_contactExist=(ContactDetails)contactDetailsQuery.getSingleResult();	
		}catch(NoResultException ex) {
			
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Multiple contact created using email Id\t"+contact.getContactEmailId());
		}catch(Exception ex) {
			throw new BadRequestException(ex.getMessage());
		}
		
		if(_contactExist!=null) {
			int contactBusinessDomainId=_contactExist.getBusinessDomain().getBusinessDomainId();
			String contactAccount = _contactExist.getAccount().getSapCustomerNumber();
			if(!contact.getAccountSAPNumber().equalsIgnoreCase(contactAccount)) {
				throw new BadRequestException("contact already exist with email Id\t"+contact.getContactEmailId()+" and account"+_contactExist.getAccount().getName());
			}
			
			EmployeeDetails employeeExist = null;
			try {
				Query employeeDetailsQuery=em.createQuery("select em from EmployeeDetails em where em.emailId=?1",EmployeeDetails.class);
				employeeDetailsQuery.setParameter(1,contact.getContactEmailId());
				employeeExist=(EmployeeDetails)employeeDetailsQuery.getSingleResult();	
			}catch(NoResultException ex) {
				
			}catch(NonUniqueResultException ex) {
				throw new BadRequestException("Multiple user created using email Id\t"+contact.getContactEmailId());
			}catch(Exception ex) {
				throw new BadRequestException(ex.getMessage());
			}
			
			if(businessDomainId!=contactBusinessDomainId) {
				if(businessDomainId==1&&contactBusinessDomainId==2) {
					_contactExist.setBusinessDomain(new BusinessDomain(3));
					if(employeeExist!=null) {
						employeeExist.setBusinessDomain(new BusinessDomain(3));
					}
				}else if(businessDomainId==2&& contactBusinessDomainId==1) {
					_contactExist.setBusinessDomain(new BusinessDomain(3));
					if(employeeExist!=null) {
						employeeExist.setBusinessDomain(new BusinessDomain(3));
					}
				}
			}
			if(employeeExist!=null) {
				em.persist(employeeExist);
			}
			em.persist(_contactExist);
			
			contact.setContactid(_contactExist.getContactId());
			if(_contactExist.getAddressInfo()!=null)
				contact.setContactMallingAddressId(_contactExist.getAddressInfo().getAddressInfoId());
			
		}else {
			
			AddressInformation addressInfo=null;
			if(contact!=null) {
				if(contact.getContactMallingAddress()!=null) {
					addressInfo=new AddressInformation(
								contact.getContactMallingAddress(),
								contact.getContactMallingStreet(),
								contact.getContactMallingCity(),
								contact.getContactMallingStateId(),
								contact.getContactMallingPincode(),
								"",
								0,0
							);
					addressInfo.setSynced(false);
					em.persist(addressInfo);
				}
				
				ContactDetails _contact=new ContactDetails();
				if(contact.getAccountSAPNumber()!=null)
					_contact.setAccount(new Accounts(contact.getAccountSAPNumber()));
				_contact.setAddressInfo(addressInfo);
				_contact.setContactOwner(ownerDetails);
				_contact.setCreatedBy(ownerDetails);
				_contact.setCreatedOn(new Date());
				
				_contact.setEmailId(contact.getContactEmailId());
				_contact.setFax(contact.getContactFaxNumber());
				_contact.setFirstName(contact.getContactFirstName());
				_contact.setLastModifiedOn(new Date());
				_contact.setLastName(contact.getContactLastName());
				_contact.setMiddleName(contact.getContactMiddleName());
				_contact.setMobile(contact.getContactMobileNumber());
				_contact.setModifiedBy(ownerDetails);
				_contact.setPhone(contact.getContactPhoneNumber());
				_contact.setSalutation(contact.getSalutation());
				_contact.setSuffix(contact.getContactSuffix());
				_contact.setTitle(contact.getTitle());
				
				if(contact.getBusinessPlaceId()!=0) {
					_contact.setBusinessPlace(new ExpenseManagementBusinessPlace(contact.getBusinessPlaceId()));
				}
				
				if(contact.getContactTypeId()==1) {
					_contact.setContactDesignation(new ContactDesignation(contact.getContactDesignationId()));
					
					if(contact.getGradeName()!=null)
						_contact.setGrade(new BoardingLoadingDetails(contact.getGradeName()));
					if(contact.getLegalEntityId()!=0)
						_contact.setLegalEntity(new LegalEntity(contact.getLegalEntityId()));
					
					_contact.setDepartment(contact.getContactDepartment());
				}else if(contact.getContactTypeId()==3) {
					if(contact.getLegalEntityId()!=0)
						_contact.setLegalEntity(new LegalEntity(contact.getLegalEntityId()));
				}
				_contact.setEmployeeCode(contact.getEmployeeCode());
				_contact.setContactType(new ContactType(contact.getContactTypeId()));
				_contact.setSynced(false);
				_contact.setBusinessDomain(new BusinessDomain(businessDomainId));
				workflow.executeWorkflow(em, _contact, "INSERT", "PRE", new com.pratikshat.exicom.entity.LoginUser(ownerDetails.getEmailId()));
				em.persist(_contact);
				workflow.executeWorkflow(em, _contact, "INSERT", "POST", new com.pratikshat.exicom.entity.LoginUser(ownerDetails.getEmailId()));
				contact.setContactid(_contact.getContactId());
				if(addressInfo!=null)
					contact.setContactMallingAddressId(addressInfo.getAddressInfoId());
				
				//Global Search Entry Point
				if(_contact.getFirstName()!=null && !_contact.getFirstName().equalsIgnoreCase("")) {
					try {
						GlobalSearchIndex _search=new GlobalSearchIndex();
						_search.setSearchString(_contact.getFirstName());
						_search.setKeyName("firstName");
						_search.setObjectName("ContactDetails");
						_search.setObjectDetailsUrl("contacts/"+_contact.getContactId());
						_search.setObjectIdName("contactId");
						_search.setObjectIdValue(String.valueOf(_contact.getContactId()));
						
						em.persist(_search);
						
					}catch(Exception e) {}
				}
				
				
				//Global Search Entry Point
				if(_contact.getMiddleName()!=null && !_contact.getMiddleName().equalsIgnoreCase("")) {
					try {
						GlobalSearchIndex _search=new GlobalSearchIndex();
						_search.setSearchString(_contact.getMiddleName());
						_search.setKeyName("middleName");
						_search.setObjectName("ContactDetails");
						_search.setObjectDetailsUrl("contacts/"+_contact.getContactId());
						_search.setObjectIdName("contactId");
						_search.setObjectIdValue(String.valueOf(_contact.getContactId()));
						
						em.persist(_search);
						
					}catch(Exception e) {}
				}
				
				
				//Global Search Entry Point
				if(_contact.getLastName()!=null && !_contact.getLastName().equalsIgnoreCase("")) {
					try {
						GlobalSearchIndex _search=new GlobalSearchIndex();
						_search.setSearchString(_contact.getLastName());
						_search.setKeyName("lastName");
						_search.setObjectName("ContactDetails");
						_search.setObjectDetailsUrl("contacts/"+_contact.getContactId());
						_search.setObjectIdName("contactId");
						_search.setObjectIdValue(String.valueOf(_contact.getContactId()));
						
						em.persist(_search);
						
					}catch(Exception e) {}
				}
				
			}
			
		}
		
		return contact;
	}
	
	
	
	@Transactional(isolation=Isolation.READ_UNCOMMITTED,rollbackFor = {Exception.class,RuntimeException.class,BadRequestException.class,UnauthoriseException.class},propagation = Propagation.REQUIRES_NEW)
	public Contacts updateContacts(Contacts contact,EmployeeDetails ownerDetails, int businessDomainId) throws BadRequestException,UnauthoriseException {
		AddressInformation addressInfo=null;
		if(contact!=null) {
			
			if(contact.getContactTypeId()!=2) {
				if(contact.getContactEmailId()==null && !contact.getContactEmailId().equalsIgnoreCase("")) {
					throw new BadRequestException("Please provide contact email id");
				}	
			}
			
			
			if(contact.getContactMallingAddress()!=null) {
				addressInfo=new AddressInformation(
							contact.getContactMallingAddress(),
							contact.getContactMallingStreet(),
							contact.getContactMallingCity(),
							contact.getContactMallingStateId(),
							contact.getContactMallingPincode(),
							"",
							0,0
						);
				addressInfo.setSynced(false);
				em.persist(addressInfo);
			}
			
			ContactDetails _contact = null;
			try {
				Query contactDetailsQuery=em.createQuery("select cd from ContactDetails cd where cd.contactId=?1",ContactDetails.class);
				contactDetailsQuery.setParameter(1,contact.getContactid());
				_contact=(ContactDetails)contactDetailsQuery.getSingleResult();	
			}catch(Exception ex) {
				throw new BadRequestException(ex.getMessage());
			}
			
			if(_contact==null) {
				throw new BadRequestException("Contact is null");
			}
			
			if(contact.getContactEmailId()!=null && !contact.getContactEmailId().equalsIgnoreCase("")) {
				if(!contact.getContactEmailId().equalsIgnoreCase(_contact.getEmailId())) {
					long count= em.createQuery("select count(contact.emailId) from ContactDetails contact where contact.emailId=?1",Long.class).setParameter(1, contact.getContactEmailId()).getSingleResult();
					if(count>0) {
						throw new BadRequestException(String.format("Email id %s is already in use",contact.getContactEmailId()));
					}	
				}
					
			}
			
			
			if(contact.getContactPhoneNumber()!=null && !contact.getContactPhoneNumber().equalsIgnoreCase("")) {
				if(!contact.getContactPhoneNumber().equalsIgnoreCase(_contact.getPhone())) {
					long Phonecount= em.createQuery("select count(contact.phone) from ContactDetails contact where contact.phone=?1",Long.class).setParameter(1, contact.getContactPhoneNumber()).getSingleResult();
					if(Phonecount>0) {
						throw new BadRequestException(String.format("Phone No.  %s is already in use",contact.getContactPhoneNumber()));
					}	
				}
					
			}
			
			if(contact.getContactMobileNumber()!=null && !contact.getContactMobileNumber().equalsIgnoreCase("")) {
				if(!contact.getContactMobileNumber().equalsIgnoreCase(_contact.getMobile())) {
					long mobileCount= em.createQuery("select count(contact.mobile) from ContactDetails contact where contact.mobile=?1",Long.class).setParameter(1, contact.getContactMobileNumber()).getSingleResult();
					if(mobileCount>0) {
						throw new BadRequestException(String.format("Mobile No.  %s is already in use",contact.getContactMobileNumber()));
					}	
				}
					
			}
			
			_contact.setAccount(new com.pratikshat.exicom.entity.Accounts(contact.getAccountSAPNumber()));
			_contact.setAddressInfo(addressInfo);
			_contact.setContactOwner(new com.pratikshat.exicom.entity.EmployeeDetails(contact.getContactOwnerEmailId()));
			
			if(contact.getContactTypeId()==1) {
				_contact.setContactDesignation(new ContactDesignation(contact.getContactDesignationId()));
				
				if(contact.getGradeName()!=null)
					_contact.setGrade(new BoardingLoadingDetails(contact.getGradeName()));
				if(contact.getLegalEntityId()!=0)
				{
					_contact.setLegalEntity(new LegalEntity(contact.getLegalEntityId()));
				}
				else {
					_contact.setLegalEntity(null);
				}
				
				_contact.setDepartment(contact.getContactDepartment());
			}else if(contact.getContactTypeId()==3) {
				if(contact.getLegalEntityId()!=0)
					_contact.setLegalEntity(new LegalEntity(contact.getLegalEntityId()));
			}
			
			if(contact.getBusinessPlaceId()!=0) {
				_contact.setBusinessPlace(new ExpenseManagementBusinessPlace(contact.getBusinessPlaceId()));
			}
			//_contact.setEmailId(contact.getContactEmailId());
			_contact.setEmployeeCode(contact.getEmployeeCode());
			_contact.setFax(contact.getContactFaxNumber());
			_contact.setFirstName(contact.getContactFirstName());
			_contact.setLastModifiedOn(new Date());
			_contact.setLastName(contact.getContactLastName());
			_contact.setMiddleName(contact.getContactMiddleName());
			_contact.setMobile(contact.getContactMobileNumber());
			_contact.setModifiedBy(ownerDetails);
			_contact.setPhone(contact.getContactPhoneNumber());
			_contact.setSalutation(contact.getSalutation());
			_contact.setSuffix(contact.getContactSuffix());
			_contact.setSynced(false);
			_contact.setTitle(contact.getTitle());
			_contact.setContactType(new ContactType(contact.getContactTypeId()));
			workflow.executeWorkflow(em, _contact, "UPDATE", "PRE", new com.pratikshat.exicom.entity.LoginUser(ownerDetails.getEmailId()));
			em.persist(_contact);
			workflow.executeWorkflow(em, _contact, "UPDATE", "POST", new com.pratikshat.exicom.entity.LoginUser(ownerDetails.getEmailId()));
			contact.setContactid(_contact.getContactId());
			if(addressInfo!=null)
				contact.setContactMallingAddressId(addressInfo.getAddressInfoId());
		}
		return contact;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<Contacts> searchAdvance(Map<String, Object> searchKeys, LoginUser user, List<String> userList, int businessDomainId) {
		String _q="select new com.pratikshat.exicom.model.request.Contacts(ct) from ContactDetails ct left join ct.account act left join ct.contactDesignation cd left join ct.contactType ctp where 1=1 and (ct.businessDomain.businessDomainId=:businessDomainId or ct.businessDomain.businessDomainId=3) ";
		StringBuilder sb=new StringBuilder();
		sb.append(_q);
		if(searchKeys.get("contact_name")!=null&&!searchKeys.get("contact_name").equals("")) {
			sb.append(" and (ct.firstName like :contact_name or ct.middleName like :contact_name or ct.lastName like :contact_name)");
		}
		if(searchKeys.get("contact_email")!=null&&!searchKeys.get("contact_email").equals("")) {
			sb.append(" and ct.emailId like :contact_email");
		}
		if(searchKeys.get("contact_phone")!=null&&!searchKeys.get("contact_phone").equals("")) {
			sb.append(" and (ct.phone like :contact_phone or ct.mobile like :contact_phone)");
		}
		if(searchKeys.get("contact_account")!=null&&!searchKeys.get("contact_account").equals("")) {
			sb.append(" and (act.sapCustomerNumber like :contact_account or act.name like :contact_account)");
		}
		if(searchKeys.get("contact_type")!=null&&!searchKeys.get("contact_type").equals("")) {
			sb.append(" and ctp.typeName like :contact_type");
		}
		if(searchKeys.get("emp_code")!=null&&!searchKeys.get("emp_code").equals("")) {
			sb.append(" and ct.employeeCode like :emp_code");
		}
		
		Query query=em.createQuery(sb.toString(),com.pratikshat.exicom.model.request.Contacts.class);
		
		query.setParameter("businessDomainId", businessDomainId);
		
		if(searchKeys.get("contact_name")!=null&&!searchKeys.get("contact_name").equals("")) {
			//sb.append(" and (ct.firstName like :contact_name or ct.middleName like :contact_name or ct.lastName like :contact_name)");
			query.setParameter("contact_name", "%"+searchKeys.get("contact_name")+"%");
		}
		if(searchKeys.get("contact_email")!=null&&!searchKeys.get("contact_email").equals("")) {
			//sb.append(" and ct.emailId like :contact_email");
			query.setParameter("contact_email", "%"+searchKeys.get("contact_email")+"%");
		}
		if(searchKeys.get("contact_phone")!=null&&!searchKeys.get("contact_phone").equals("")) {
			//sb.append(" and (ct.phone like :contact_phone or ct.mobile like :contact_phone)");
			query.setParameter("contact_phone", "%"+searchKeys.get("contact_phone")+"%");
		}
		if(searchKeys.get("contact_account")!=null&&!searchKeys.get("contact_account").equals("")) {
			///sb.append(" and (act.sapCustomerNumber like :contact_account or act.name like :contact_account)");
			query.setParameter("contact_account", "%"+searchKeys.get("contact_account")+"%");
		}
		if(searchKeys.get("contact_type")!=null&&!searchKeys.get("contact_type").equals("")) {
			//sb.append(" and ctp.typeName like :contact_type");
			query.setParameter("contact_type", "%"+searchKeys.get("contact_type")+"%");
		}
		if(searchKeys.get("emp_code")!=null&&!searchKeys.get("emp_code").equals("")) {
			//sb.append(" and ct.employeeCode like :emp_code");
			query.setParameter("emp_code","%"+searchKeys.get("emp_code")+"%");
		}
		
		return query.getResultList();
	}



	@Override
	public ContactDetails getWithContactIdAndAccountId(long contractId, String sapNumber) throws BadRequestException {
		ContactDetails contact=null;
		try {
			
			contact = em.createQuery(
					"select cnt from ContactDetails cnt join cnt.account act  where cnt.contactId=?1 and act.sapCustomerNumber=?2",
					com.pratikshat.exicom.entity.ContactDetails.class).setParameter(1,contractId).setParameter(2,sapNumber).getSingleResult();
		}catch(NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Contact does not exist");
		}
		return contact;
	}


	@Override
	public ContactDetails getWithContactWithId(long contractId) throws BadRequestException {
		ContactDetails contact = em
				.createQuery("select c from ContactDetails c where c.contactId=?1",
						com.pratikshat.exicom.entity.ContactDetails.class)
				.setParameter(1, contractId).getSingleResult();

		return contact;
	}



	@Override
	public ContactDetails getContactDetailsWithEmail(String email) throws BadRequestException, UnauthoriseException {
		ContactDetails contact = null;
		try {
			contact=em.createQuery("select ct from ContactDetails ct join ct.account act join act.accountType actt where ct.emailId=?1",ContactDetails.class).
					setParameter(1, email).getSingleResult();
		}catch(NoResultException ex) {
			throw new UnauthoriseException("You are not authorised");
		}
		catch(NonUniqueResultException ex) {
			throw new UnauthoriseException("You are not authorised");
		}
		return contact;
	}
}
