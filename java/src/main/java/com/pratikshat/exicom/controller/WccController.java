package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.text.ParseException;
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
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.PreRegistrationCases;
import com.pratikshat.exicom.entity.WccDetails;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.WccDetailsRequest;
import com.pratikshat.exicom.model.response.PrcResponse;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.repository.VendorInvoiceDetailsRepository;
import com.pratikshat.exicom.repository.VendorPoDetailsRepository;
import com.pratikshat.exicom.repository.WccDetailsRepository;

@RestController
@RequestMapping("api/wcc")
public class WccController {
	@Autowired
	WccDetailsRepository db;
	@Autowired
	UserProfileRepository dbProfile;
	@Autowired
	VendorPoDetailsRepository dbPo;
	
	@Autowired
	VendorInvoiceDetailsRepository dbInvoice;
	
	@GetMapping("type")
	public List<Map<String,Object>> getwsstypes(){
		return db.getTypeForDropdownList();
	}
	@GetMapping("status")
	public List<Map<String,Object>> getWssStatus(){
		return db.getWccStatus();
	}
	@GetMapping("pendingsa")
	public List<Map<String,Object>> getPendingSa(@CurrentUser LoginUser user,
			@RequestParam("type")long typeid,
			@RequestParam(value="datefrom",required=false)String datefrom,
			@RequestParam(value="dateto",required=false)String dateto){
		SimpleDateFormat frm=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date fdate=null;//frm.parse(datefrom);
		Date todate=null;//frm.parse(dateto);
		try {
			fdate=frm.parse(datefrom);
			todate=frm.parse(dateto);
		}catch(Exception ex) {
			
		}
		
		return db.getPendingServiceAppointment(user.getUsername(), typeid,fdate,todate);
	}
	@GetMapping("po/{wcctypeid}")
	public List<Map<String,Object>> getPoDetails(@CurrentUser LoginUser user,@PathVariable("wcctypeid")Long id){
		return dbPo.getPoListByUserIdAndWccType(user.getUsername(), id);
	}
	@GetMapping
	public Page<Map<String,Object>> getWssList(@CurrentUser LoginUser user,Pageable page,@RequestParam("filterparam")Integer filtername){
		
		
		if(dbProfile.hasObjectAccess(user.getUsername(), WccDetails.class, UserProfileRepository.VIEW_ALL)){
			if(filtername!=null&&filtername!=0)
				return db.getAll(filtername,page);
			return db.getAll(page);
		}
		if(dbProfile.hasObjectAccess(user.getUsername(), WccDetails.class, UserProfileRepository.VIEW)) {
			List<String> ids=dbProfile.getListOfUserAndChildByUser(user.getUsername());
			if(filtername!=null&&filtername!=0)
				return db.getAllWccByUserId(ids, filtername, page);
			return db.getAllWccByUserId(ids, page);
		}
		return Page.empty();
	}
	
	@GetMapping("{id}")
	public Map<String,Object> getWcc(@PathVariable("id")Long id,@CurrentUser LoginUser user){
		if(dbProfile.hasObjectAccess(user.getUsername(), WccDetails.class, UserProfileRepository.VIEW_ALL)||dbProfile.hasObjectAccess(user.getUsername(), WccDetails.class, UserProfileRepository.VIEW))
		return db.getWccById(id);
		else return new HashMap<String,Object>();
	}
	@GetMapping("{id}/sa")
	public List<Map<String,Object>> getSa(@PathVariable("id")Long id,@CurrentUser LoginUser user){
		if(dbProfile.hasObjectAccess(user.getUsername(), WccDetails.class, UserProfileRepository.VIEW_ALL)||dbProfile.hasObjectAccess(user.getUsername(), WccDetails.class, UserProfileRepository.VIEW))
			return db.getServiceAppointmentByWccId(id);
		else
			return new ArrayList<>();
	}
	@PostMapping
	public Map<String,Object> create(@RequestBody WccDetailsRequest wcRequest,  @CurrentUser LoginUser user,HttpServletResponse response) throws IOException {
		if(!dbProfile.hasObjectAccess(user.getUsername(), WccDetails.class, UserProfileRepository.CREATE)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorised");
			return null;
		}
		try {
			db.saveWcc(wcRequest, user);
			return new HashMap<String,Object>();
			
		}catch(BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
		}
		catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
		}
		return null;
	}
	
	@PutMapping("{id}")
	public Map<String,Object> updateWcc(@PathVariable("id")Long id, @RequestBody WccDetailsRequest wcRequest,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		if(!dbProfile.hasObjectAccess(user.getUsername(),WccDetails.class, UserProfileRepository.EDIT)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorised");
			return null;
		}
		if(!id.equals(wcRequest.getId())) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid id");
			return null;
		}
		try {
			db.updateWcc(wcRequest, user);
			return db.getWccById(id);
		}
		catch(BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,ex.getMessage());
		}
		catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
		}
		return null;
	}
	@PostMapping("{id}/forapproval")
	public Map<String,Object> submitForApproval(@PathVariable("id")Long id,@CurrentUser LoginUser user,HttpServletResponse response)throws IOException {
		try {
			db.submitForapproval(id, user);
			return new HashMap<String,Object>();
		}
		catch(BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,ex.getMessage());
		}
		catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
		}
		return null;
	}
	@GetMapping("{id}/invoice")
	public List<Map<String,Object>> getInvoiceByWccId(@PathVariable("id")Long id){
		return dbInvoice.getVendorInvoiceByWccId(id);
	}
	
	@GetMapping("advancesearch")
	public List<Map<String,Object>> advanceSearch(@RequestParam Map<String,Object> request, @CurrentUser LoginUser user, HttpServletResponse  response)throws IOException, ParseException {
		if (dbProfile.hasObjectAccess(user.getUsername(), WccDetails.class, UserProfileRepository.VIEW_ALL))
			return db.advanceSearch(request, null);
		if (dbProfile.hasObjectAccess(user.getUsername(), WccDetails.class, UserProfileRepository.VIEW)) {
			List<String> usrs = dbProfile.getListOfUserAndChildByUser(user.getUsername());
			if (usrs != null && usrs.size() > 0) {
				return db.advanceSearch(request, usrs);
			}
		}

		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		return null;
	}
}
