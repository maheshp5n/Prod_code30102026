package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.BtExpenseManagement;
import com.pratikshat.exicom.entity.BtGlcodeAndCostcenter;
import com.pratikshat.exicom.entity.BtServiceAppointmentExp;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.StatusMessageResponse;
import com.pratikshat.exicom.repository.BtGLCodeAndCostCenterRepository;
import com.pratikshat.exicom.repository.BtServiceAppointmentExpRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.impl.BtServiceAppointmentExpServiceImpl;
import com.pratikshat.exicom.services.impl.BtServiceAppointmentServiceImpl;

@RestController
@RequestMapping("bt/api/btServiceAppointmentExp")
public class BtServiceAppointmentExpController {

	@Autowired
	private BtServiceAppointmentExpServiceImpl serviceImpl;
	
	@Autowired
	private BtServiceAppointmentExpRepository dbRepo;
	
	@Autowired
	private com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	
	@Autowired
	BtGLCodeAndCostCenterRepository glCodeAndCostcenterDB;
	
	
	
	@GetMapping("getcostcenterandglcode")
	public List<Map<String,Object>> getAllCostCenterAndGlCode()
			{
		return glCodeAndCostcenterDB.getByGlCodeAndCostCenter();
			}
	
	
	
	@GetMapping("list/{btExpenseManagmentId}")
	public Page<Map<String, Object>> getBtServiceAppointmentExpByExpenseId(@PathVariable("btExpenseManagmentId")long btExpenseManagmentId,@CurrentUser LoginUser user,Pageable page, HttpServletResponse response)throws IOException{
		boolean viewAll = dbProfile.hasObjectAccess(user.getUsername(), BtExpenseManagement.class,UserProfileRepository.VIEW_ALL);
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtExpenseManagement.class, UserProfileRepository.VIEW);
		System.out.println("hello");
		
		if (viewAll || view) {	try {
			System.out.println("btExpenseManagmentId  => "+btExpenseManagmentId);
				return serviceImpl.getList(btExpenseManagmentId, page);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return Page.empty();
		
	}
	
	@GetMapping("getbyid/{id}")
	public Map<String, Object> getBtServiceAppointmentExpDetails(@PathVariable("id")long id,@CurrentUser LoginUser user,Pageable page, HttpServletResponse response)throws IOException{
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtExpenseManagement.class, UserProfileRepository.VIEW);
		System.out.println("id  =>  "+id);
		if (view ) {
			try {
				return serviceImpl.getSingle(id, user);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return new HashMap<String, Object>();
		
	}
	
	@PostMapping
	public Map<String, Object> createBtServiceAppointmentExp(@RequestBody Map<String, Object> map,@CurrentUser LoginUser user,HttpServletResponse response)throws IOException, BadRequestException, UnauthoriseException {
		
		
		System.out.println("user =>"+user);
		if(!dbProfile.hasObjectAccess(user.getUsername(),"BtExpenseManagement", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
				System.out.println("map => "+map);
				return serviceImpl.saveObject(map, user);	
			}
	
	
	@PutMapping
	public Map<String, Object> updateBtServiceAppointmentExp(@RequestBody Map<String, Object> map,@CurrentUser LoginUser user,HttpServletResponse response)throws IOException, BadRequestException, UnauthoriseException {
		System.out.println("user =>"+user);
		if(!dbProfile.hasObjectAccess(user.getUsername(),"BtExpenseManagement", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}	
				return serviceImpl.updateObject(map, user);	
		}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteBtServiceAppointmentExp(@PathVariable("id")long id,@CurrentUser LoginUser user, HttpServletResponse response)throws IOException, BadRequestException, UnauthoriseException{
		StatusMessageResponse simpleResponse = new StatusMessageResponse();
		if(dbProfile.hasObjectAccess(user.getUsername(), "BtExpenseManagement", "editRights")) {
			
				StatusMessageResponse btServiceAppointmentExpRes = serviceImpl.deleteBtServiceAppointmentExp(id, user);
				
				simpleResponse.setMessage("Deleted Successfully");
				simpleResponse.setStatus(HttpServletResponse.SC_OK);
				
				return ResponseEntity.status(HttpServletResponse.SC_OK).body(btServiceAppointmentExpRes);
				
		}
		return null;
	}
	
	@GetMapping("getbtserviceappointment")
	List<Map<String,Object>> getServiceAppointmentDetailByExpId(@CurrentUser LoginUser user, HttpServletResponse response) 
	{
		System.out.println("nbnhbj"+user.getUsername());
		return serviceImpl.getBtServiceAppointmentDetailByExpId(user.getUsername());
		
		
	}
}
