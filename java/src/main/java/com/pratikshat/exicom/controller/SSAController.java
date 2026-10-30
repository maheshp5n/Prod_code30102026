package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.text.ParseException;
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
import com.pratikshat.exicom.entity.ServiceOrder;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.SSARequest;
import com.pratikshat.exicom.model.response.SSAForList;
import com.pratikshat.exicom.model.response.SSAResponse;
import com.pratikshat.exicom.repository.SSARepository;
import com.pratikshat.exicom.repository.UserProfileRepository;

@RestController
@RequestMapping("api/ssa")
public class SSAController {

	@Autowired
	SSARepository dbSSA;
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	@GetMapping
	public Page<SSAResponse> getAll(@CurrentUser LoginUser user, Pageable page) {
		if(dbProfile.hasObjectAccess(user.getUsername(), "SSA", "viewAllRights")||dbProfile.hasObjectAccess(user.getUsername(), "SSA", "viewRights")) {
			return dbSSA.getAllPageable(page);	
		}
		return Page.empty();
		
	}
	
	@GetMapping("{id}")
	public SSAResponse getById(@PathVariable("id")long id,@CurrentUser LoginUser user) {
		if(dbProfile.hasObjectAccess(user.getUsername(), "SSA", "viewAllRights")||dbProfile.hasObjectAccess(user.getUsername(), "SSA", "viewRights")) {
			return dbSSA.getById(id);	
		}
		return new SSAResponse();
		
	}
	
	
	@GetMapping("forlist")
	public List<SSAForList> getSSAForListByCircleName(@RequestParam("circleName")String circleName, @CurrentUser LoginUser user){
		return dbSSA.getSAAForList(circleName);
	}
	
	
	@PostMapping
	public SSAResponse saveSSA(@RequestBody SSARequest request, @CurrentUser LoginUser user, HttpServletResponse response)throws IOException {
		if(!dbProfile.hasObjectAccess(user.getUsername(),"SSA", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			SSARequest ssaRequest = dbSSA.saveSSA(request, user);	
			return dbSSA.getById(ssaRequest.getSSAId());
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
	
	
	@PutMapping
	public SSAResponse updateSSA(@RequestBody SSARequest request, @CurrentUser LoginUser user, HttpServletResponse response) throws IOException {
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"SSA", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			SSARequest ssaRequest = dbSSA.updateSSA(request, user);
			return dbSSA.getById(ssaRequest.getSSAId());
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
	
	@GetMapping("advancesearch")
	public List<SSAResponse> getAdvanceSearchresult(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException{
		
		if(dbProfile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.SSA.class, UserProfileRepository.VIEW_ALL)) {
			return 	dbSSA.advanceSearch(request, user);
		}
		
		if(dbProfile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.SSA.class, UserProfileRepository.VIEW)) {
			return 	dbSSA.advanceSearch(request, user);
		}
		
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		return null;
		
	}
}
