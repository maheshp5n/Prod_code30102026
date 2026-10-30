package com.pratikshat.exicom.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.pratikshat.exicom.entity.TemplateDetails;
import com.pratikshat.exicom.entity.TemplateType;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.TemplateRequest;
import com.pratikshat.exicom.model.response.TemplateResponse;
import com.pratikshat.exicom.repository.UserProfileRepository;

@RestController
@RequestMapping("api/template")
public class TemplateController {
	@Autowired
	com.pratikshat.exicom.repository.TemplateDetailsRepository dbTemplate;
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	@GetMapping
	List<TemplateResponse> getTemplates(@CurrentUser LoginUser user,@RequestParam(value="type",required = false)TemplateType templateType){
		if(dbProfile.hasObjectAccess(user.getUsername(), "TemplateDetails", "viewAllRights")||dbProfile.hasObjectAccess(user.getUsername(), "TemplateDetails", "viewRights")) {
			if(templateType!=null) {
				return dbTemplate.getByTemplatetype(templateType);
			}
			return dbTemplate.getAll();
		}
		return new ArrayList<TemplateResponse>();
	}
	@GetMapping("{templateid}")
	TemplateResponse getTemplate(@CurrentUser LoginUser user,@PathVariable("templateid")Long templateid) {
		if(dbProfile.hasObjectAccess(user.getUsername(), "TemplateDetails", "viewAllRights")||dbProfile.hasObjectAccess(user.getUsername(), "TemplateDetails", "viewRights")) {
			return dbTemplate.getById(templateid);
		}
		return new TemplateResponse();
	}
	@PostMapping
	TemplateRequest createTemplate(@RequestBody TemplateRequest req,HttpServletResponse response,@CurrentUser LoginUser user) throws java.io.IOException {
		if(!dbProfile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.TemplateDetails.class, UserProfileRepository.CREATE)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorised");
			return null;
		}
		
		try {
		
			TemplateDetails _temp=new TemplateDetails();
			_temp.setActive(true);
			_temp.setBody(req.getBody());
			_temp.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_temp.setCreatedDate(new Date());
			_temp.setLastModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_temp.setLastModifiedDate(new Date());
			_temp.setTemplaeCode(req.getTemplateCode());
			_temp.setTemplateType(TemplateType.valueOf(req.getTemplateType()));
			dbTemplate.save(_temp);
			req.setId(_temp.getId());
			return req;
		}catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
	}
	@PutMapping("{id}")
	TemplateRequest edittemplate(@PathVariable("id")Long id, @RequestBody TemplateRequest req,HttpServletResponse response,@CurrentUser LoginUser user) throws java.io.IOException {
		if(!dbProfile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.EmployeeDetails.class, UserProfileRepository.EDIT)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
		
			if(id!=req.getId()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Template id mismatch");
				return null;
			}
			
			TemplateDetails _temp=dbTemplate.getByIds(req.getId());
			if(_temp==null) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Template not found");
				return null;
			}
			_temp.setLastModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_temp.setLastModifiedDate(new Date());
			dbTemplate.save(_temp);
			
			return req;
			
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
		
		
	}
}
