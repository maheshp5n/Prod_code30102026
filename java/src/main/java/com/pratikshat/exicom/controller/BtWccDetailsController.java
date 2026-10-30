package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.BtWccDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.WccDetailsRequest;
import com.pratikshat.exicom.repository.BtVendorInvoiceDetailsRepository;
import com.pratikshat.exicom.repository.BtVendorPoDetailsRepository;
import com.pratikshat.exicom.repository.BtWccDetailsRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtWccDetailsService;

@RestController
@RequestMapping("bt/api/wcc")
public class BtWccDetailsController {
	
	@Autowired
	BtWccDetailsService wccService;
	@Autowired
	UserProfileRepository dbprofile;
	@Autowired
	BtWccDetailsRepository db;
	@Autowired
	BtVendorInvoiceDetailsRepository dbInvoice;
	@Autowired
	BtVendorPoDetailsRepository dbPo;
	
	@GetMapping("type")
	public List<Map<String,Object>> getwsstypes(){
		return db.getTypeForDropdownList();
	}
	
	@GetMapping("status")
	public List<Map<String,Object>> getWssStatus(){
		return db.getWccStatus();
	}
	@PostMapping
	public Map<String, Object> saveObject(@RequestBody WccDetailsRequest wcRequest,  @CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
		return wccService.saveObject(wcRequest, user);
	}
	@GetMapping("po/{wcctypeid}")
	public List<Map<String,Object>> getPoDetails(@CurrentUser LoginUser user,@PathVariable("wcctypeid")Long id){
		return dbPo.getPoListByUserIdAndWccType(user.getUsername(), id);
	}
	
	@PutMapping("{id}")
	public Map<String, Object> updateObject(@RequestBody Map<String, Object> req,@PathVariable("id") long id,@CurrentUser LoginUser user)throws BadRequestException,UnauthoriseException{
		return wccService.updateObject(req,user);
	}
	
	@GetMapping("{id}")
	public Map<String, Object> getSingleResult(@PathVariable("id") long id,@CurrentUser LoginUser user)throws BadRequestException,UnauthoriseException{
		return wccService.getSingle(id,user);
	}
	
	@GetMapping("advancesearch")
	public List<Map<String, Object>> advancesearch(@RequestParam Map<String, Object> req, @CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
		if (dbprofile.hasObjectAccess(user.getUsername(), BtWccDetails.class, UserProfileRepository.VIEW_ALL)) {
			System.out.println("here");
			return wccService.advanceSearchs(req, null);
		}
		if (dbprofile.hasObjectAccess(user.getUsername(), BtWccDetails.class, UserProfileRepository.VIEW)) {
			List<String> usrs = dbprofile.getListOfUserAndChildByUser(user.getUsername());
			if (usrs != null && usrs.size() > 0) {
				return wccService.advanceSearchs(req, usrs);
			}
		}	
		return null;
	}
	@GetMapping("{id}/sa")
	public List<Map<String,Object>> getSa(@PathVariable("id")Long id,@CurrentUser LoginUser user) throws UnauthoriseException{
		if(dbprofile.hasObjectAccess(user.getUsername(), BtWccDetails.class, UserProfileRepository.VIEW_ALL)||dbprofile.hasObjectAccess(user.getUsername(), BtWccDetails.class, UserProfileRepository.VIEW))
			return db.getServiceAppointmentByWccId(id);
		else 
			 throw new UnauthoriseException("You are not authorize");
	}
	@GetMapping("{id}/invoice")
	public List<Map<String,Object>> getInvoiceByWccId(@PathVariable("id")Long id){
		return dbInvoice.getVendorInvoiceByWccId(id);
	}
	
	@GetMapping
	public Page<Map<String,Object>> getWssList(@CurrentUser LoginUser user,Pageable page,@RequestParam(value = "filterparam",required = false)Integer filtername){
		
		if(dbprofile.hasObjectAccess(user.getUsername(), BtWccDetails.class, UserProfileRepository.VIEW_ALL)){
			if(filtername!=null&&filtername!=0)
				return db.getAll(filtername,page);
			return db.getPage(page);
		}
		if(dbprofile.hasObjectAccess(user.getUsername(), BtWccDetails.class, UserProfileRepository.VIEW)) {
			List<String> ids=dbprofile.getListOfUserAndChildByUser(user.getUsername());
			if(filtername!=null&&filtername!=0)
				return db.getAllWccByUserId(ids, filtername, page);
			return db.getAllWccByUserId(ids, page);
		}
		return Page.empty();
	}
	
	@GetMapping("pendingsa")
	public List<Map<String,Object>> getPendingSa(@CurrentUser LoginUser user,
			@RequestParam("type")long typeid,
			@RequestParam(value="datefrom",required=false)String datefrom,
			@RequestParam(value="dateto",required=false)String dateto){
		SimpleDateFormat frm=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date fdate=null;
		Date todate=null;
		try {
			fdate=frm.parse(datefrom);
			todate=frm.parse(dateto);
		}catch(Exception ex) {
		}
		return db.getPendingServiceAppointment(user.getUsername(), typeid,fdate,todate);
	}
	
	@PostMapping("{id}/forapproval")
	public Map<String,Object> submitForApproval(@PathVariable("id")long id,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		try {
			wccService.submitForapproval(id,user);
			return new HashMap<String,Object>();
		}catch(BadRequestException ex)
		{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
		}catch (UnauthoriseException ex) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
		}
		
		return new HashMap<>();
	}

}