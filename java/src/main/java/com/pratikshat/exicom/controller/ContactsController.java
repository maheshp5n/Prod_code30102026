package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.ContactDesignation;
import com.pratikshat.exicom.entity.ContactDetails;
import com.pratikshat.exicom.entity.ContactType;
import com.pratikshat.exicom.entity.ContactsRecentVisit;
import com.pratikshat.exicom.entity.Department;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.Contacts;
import com.pratikshat.exicom.model.response.CircleDetailsResponse;
import com.pratikshat.exicom.model.response.ContactForList;
import com.pratikshat.exicom.model.response.ExpenseManagementResponse;
import com.pratikshat.exicom.model.response.OppertunityResponse;
import com.pratikshat.exicom.repository.ContactDesignationRepository;
import com.pratikshat.exicom.repository.ContactDetailsRepository;
import com.pratikshat.exicom.repository.ContactTypeRepository;
import com.pratikshat.exicom.repository.ContactsRecentVisitRepository;
import com.pratikshat.exicom.repository.DepartmentRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;

@RestController
@RequestMapping("api/contacts")
public class ContactsController {
	@Autowired
	private ContactDetailsRepository dbContacts;
	@Autowired
	private ContactsRecentVisitRepository dbContactRecentVisit;
	@Autowired
	ContactTypeRepository dbContactType;
	@Autowired
	ContactDesignationRepository dbContactDesignation;
	@Autowired
	private com.pratikshat.exicom.repository.UserProfileRepository dbUserProfile;
	
	@Autowired
	private DepartmentRepository dbDepartment;
	
	int businessDomainId=2;
	
	@GetMapping
	public Page<Contacts> getAllContacts(Pageable page, @CurrentUser LoginUser user,@RequestParam(value="filtername",required=false)String filtername){
		if(dbUserProfile.hasObjectAccess(user.getUsername(), "ContactDetails", "viewAllRights") || dbUserProfile.hasObjectAccess(user.getUsername(), "ContactDetails", "viewRights")) {
			if(filtername!=null&&filtername.equalsIgnoreCase("Customer"))
				return dbContacts.getContactsByType(filtername,page, businessDomainId);
			
			if(filtername!=null&&filtername.equalsIgnoreCase("Vendor"))
				return dbContacts.getContactsByType(filtername,page, businessDomainId);
			
			if(filtername!=null&&filtername.equalsIgnoreCase("related"))
				return dbContacts.getRecentVisit(user.getUsername(),page, businessDomainId);
			
			if(filtername!=null&&filtername.equalsIgnoreCase("All"))
				return dbContacts.getAllContacts(page, businessDomainId);
			
			if(filtername!=null&&filtername.equalsIgnoreCase("Employee"))
				return dbContacts.getContactsByType(filtername,page, businessDomainId);
			
			return dbContacts.getAllContacts(page, businessDomainId);
		}
		return Page.empty();
		
	}
	
	@GetMapping("forlist")
	public List<ContactForList> getAllContactsList(@RequestParam(value="searchkey",required = false)String contactSearch ,Pageable page){
		List<String> list = new ArrayList<String>();
		list.add("Customer");
		return dbContacts.getAllContactsListForList(list, contactSearch,businessDomainId,page).getContent();
	}
	
	@GetMapping("accounts/{accountid}")
	public Page<Contacts>getContactDetailsByAccounts(@PathVariable("accountid")String accountid,Pageable page){
		return dbContacts.getContactByAccount(accountid, page, businessDomainId);
	}
	@GetMapping("{id}")
	public Contacts getContact(@CurrentUser LoginUser user ,@PathVariable("id")long id) {
		
		if(dbUserProfile.hasObjectAccess(user.getUsername(), "ContactDetails", "viewAllRights") || dbUserProfile.hasObjectAccess(user.getUsername(), "ContactDetails", "viewRights")) {
			
			try {
				ContactsRecentVisit _recentVisist=dbContactRecentVisit.findByContactIdAndVisitedByid(id,user.getUsername());
				if(_recentVisist!=null) {
					_recentVisist.setVisitedDate(new java.util.Date());
				}
				else {
					_recentVisist=new ContactsRecentVisit(id,new java.util.Date(),user.getUsername());
				}
				dbContactRecentVisit.save(_recentVisist);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return dbContacts.getContacts(id);
		}

		return new Contacts();
		
		
	}
	@GetMapping("recentvisit")
	public Page<Contacts> getRecentVisit(@CurrentUser LoginUser user,Pageable page){
		return dbContacts.getRecentVisit(user.getUsername(),page, businessDomainId);
	}
	
	@PostMapping
	public Contacts postContact(@CurrentUser LoginUser user,@RequestBody Contacts contact, HttpServletResponse response) throws IOException {
		
		if(!dbUserProfile.hasObjectAccess(user.getUsername(), "ContactDetails", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have permission");
			return null;
		}
		
		try {
			
			if(dbContacts.countByContactId(contact.getContactid())>0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Contact Details Already Exists");
				return null;
			}
			
			dbContacts.saveContacts(contact, new EmployeeDetails(user.getUsername()),businessDomainId);
			return dbContacts.getContacts(contact.getContactid());
			
		}catch(BadRequestException ex) {
			ex.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			ex.printStackTrace();
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
	}
	
	@PutMapping("{id}")
	public Contacts editContacts(@CurrentUser LoginUser user, @PathVariable("id")long id, @RequestBody Contacts contact, HttpServletResponse response) throws IOException {
		
		if(!dbUserProfile.hasObjectAccess(user.getUsername(), "ContactDetails", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have permission");
			return null;
		}
		
		try {
			
			if(dbContacts.countByContactId(id)==0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "\"Contact does not exists\"");
				return null;
			}
			if(contact.getContactid()!=id) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Contact Id does not match");
				return null;
			}
			
			dbContacts.updateContacts(contact, new EmployeeDetails(user.getUsername()), businessDomainId);
			
			return dbContacts.getContacts(contact.getContactid());
			
		}catch(BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
	
	@GetMapping("type")
	public List<ContactType> getAllType(){
		return dbContactType.findAll();
	}
	
	@GetMapping("designation")
	public List<ContactDesignation> getAllDesignation(){
		return dbContactDesignation.findAll();
	}
	
	@GetMapping("list")
	public Page<Contacts> getContactsListByType(@CurrentUser com.pratikshat.exicom.entity.LoginUser user,@RequestParam(name = "type") String type,Pageable page){
		return dbContacts.getContactsByType(type,page, businessDomainId);
	}
	
	@GetMapping("forlist/serviceresource")
	public List<ContactForList> getContactListFromServiceResource(){
		List<String>  list = new ArrayList<String>();
		list.add("Self");
		list.add("Vendor");
		return dbContacts.getAllContactsForServiceResources(list,businessDomainId);
	}
	
	@GetMapping("forlist/serviceresource/cirlce")
	public List<ContactForList> getContactListFromServiceResourceForCircle(){
		List<String>  list = new ArrayList<String>();
		list.add("Self");
		return dbContacts.getAllContactsForServiceResources(list, businessDomainId);
	}
	
	@GetMapping("related/{contactId}/expenseManagement")
	public Page<ExpenseManagementResponse> getExpenseManagementByContactId(@PathVariable("contactId")long contactId,Pageable page) {
		return dbContacts.getExpenseManagementByContactId(contactId,page, businessDomainId);
	}
	
	@GetMapping("employee")
	public Contacts getContactsByEmployeeEmailId(@RequestParam("emailId")String emailId) {
		
		return dbContacts.getContactsByEmployeeEmailId(emailId);
	}
	@GetMapping("department")
	public List<Department> getDepartment(){
		return dbDepartment.findByActive(true);
	}
	
	@GetMapping("advancesearch")
	public List<Contacts> findAdvancecontact(@RequestParam Map<String,Object> req,@CurrentUser LoginUser user,HttpServletResponse response)throws IOException{
		if(dbUserProfile.hasObjectAccess(user.getUsername(), ContactDetails.class, UserProfileRepository.VIEW)||dbUserProfile.hasObjectAccess(user.getUsername(), ContactDetails.class, UserProfileRepository.VIEW_ALL))
				return dbContacts.searchAdvance(req, user,null, businessDomainId);
		return new ArrayList<Contacts>();
	}
}
