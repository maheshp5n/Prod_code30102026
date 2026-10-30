package com.pratikshat.exicom.controller;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.CustomerDetailsRepository;

@RestController
@RequestMapping("api/customer")
public class CustomerDetailsController {
	@Autowired
	CustomerDetailsRepository dbCustomer;
	@Autowired
	private com.pratikshat.exicom.repository.UserProfileRepository dbUserProfile;
	
	@GetMapping
	public java.util.List<com.pratikshat.exicom.model.response.CustomerForList> getByAll(@CurrentUser LoginUser user){
		if(dbUserProfile.hasObjectAccess(user.getUsername(), "CustomerDeatils", "viewAllRights") || dbUserProfile.hasObjectAccess(user.getUsername(), "CustomerDeatils", "viewRights")) {
			
			return dbCustomer.getByAll();
		}
		return new ArrayList<>();
		
	}
	@PostMapping
	public com.pratikshat.exicom.model.response.CustomerForList saveCustomer(@RequestBody com.pratikshat.exicom.model.response.CustomerForList customerDetails,@CurrentUser LoginUser user, HttpServletResponse response) throws IOException{
		
		if(!dbUserProfile.hasObjectAccess(user.getUsername(), "CustomerDeatils", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have permission");
			return null;
		}
		
		try {
			
			if(dbCustomer.countByCustomerId(customerDetails.getCustomerId())>0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Customer already exists");
				return null;
			} 

			com.pratikshat.exicom.entity.CustomerDeatils _customer=new com.pratikshat.exicom.entity.CustomerDeatils();
			_customer.setCustomerName(customerDetails.getCustomerName());
			_customer.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_customer.setCreatedDate(new java.util.Date());
			_customer.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_customer.setModifiedDate(new java.util.Date());
			_customer.setSynced(false);
			dbCustomer.save(_customer);
			return dbCustomer.getById(_customer.getCustomerId());
			
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
	}
	
	@PutMapping("{id}")
	public com.pratikshat.exicom.model.response.CustomerForList updateCustomer(@RequestBody com.pratikshat.exicom.model.response.CustomerForList customerDetails,@CurrentUser LoginUser user,@PathVariable("id")long id, HttpServletResponse response) throws IOException{
		
		if(!dbUserProfile.hasObjectAccess(user.getUsername(), "CustomerDeatils", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have permission");
			return null;
		}
		
		try {
			
			if(dbCustomer.countByCustomerId(id)==0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Customer does not exists");
				return null;
			} 

			if(id!=customerDetails.getCustomerId()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Customer id wrong");
				return null;
			}
			com.pratikshat.exicom.entity.CustomerDeatils _customer=dbCustomer.findByCustomerId(id);
			_customer.setCustomerName(customerDetails.getCustomerName());
			_customer.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_customer.setModifiedDate(new java.util.Date());
			_customer.setSynced(false);
			dbCustomer.save(_customer);
			return dbCustomer.getById(id);
			
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
}
