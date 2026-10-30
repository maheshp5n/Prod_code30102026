package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.Http;
import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.MailerEntity;
import com.pratikshat.exicom.entity.SmsSetting;
import com.pratikshat.exicom.entity.SmsSettingHeaders;
import com.pratikshat.exicom.entity.TemplateDetails;
import com.pratikshat.exicom.entity.TemplateType;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.TemplateRequest;
import com.pratikshat.exicom.model.response.TemplateResponse;
import com.pratikshat.exicom.repository.UserProfileRepository;


@RestController
@RequestMapping("api/sysconfig")
public class SysConfigController {
	@Autowired
	com.pratikshat.exicom.repository.TemplateDetailsRepository dbtemplate;
	
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbprofile;
	@Autowired
	com.pratikshat.exicom.repository.SmsSettingRepository dbSmsSetting;
	@Autowired
	com.pratikshat.exicom.repository.MailerEntityRepository dbMailer;
	
	
	@GetMapping
	public List<TemplateResponse> getTemplte(@CurrentUser LoginUser user,HttpServletResponse response) throws java.io.IOException{
		if(dbprofile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.TemplateDetails.class, UserProfileRepository.VIEW)||dbprofile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.TemplateDetails.class, UserProfileRepository.VIEW_ALL))
			return dbtemplate.getAll();
		else
			return new ArrayList<TemplateResponse>();
	}
	@PutMapping("{id}")
	public TemplateDetails updatetemplate(@PathVariable("id")Long id,@RequestBody TemplateDetails data,@CurrentUser LoginUser user, HttpServletResponse response)throws java.io.IOException {
		if(!dbprofile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.TemplateDetails.class,UserProfileRepository.EDIT)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			TemplateDetails _details=dbtemplate.getByIds(id);
			if(_details!=null) {
				_details.setBody(data.getBody());
				_details.setLastModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
				_details.setLastModifiedDate(new java.util.Date());
				dbtemplate.save(_details);
			}
			return data;	
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
		
	}
	@PostMapping
	TemplateRequest createTemplate(@RequestBody TemplateRequest req,HttpServletResponse response,@CurrentUser LoginUser user) throws java.io.IOException {
		if(!dbprofile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.TemplateDetails.class, UserProfileRepository.CREATE)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorised");
			return null;
		}
		
		try {
			if(dbtemplate.countByTemplaeCode(req.getTemplateCode())>0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Please provide different Template Code");
				return null;
			}
			TemplateDetails _temp=new TemplateDetails();
			_temp.setActive(true);
			_temp.setBody(req.getBody());
			_temp.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_temp.setCreatedDate(new Date());
			_temp.setLastModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_temp.setLastModifiedDate(new Date());
			_temp.setTemplaeCode(req.getTemplateCode());
			_temp.setTemplateType(TemplateType.valueOf(req.getTemplateType()));
			dbtemplate.save(_temp);
			req.setId(_temp.getId());
			return req;
			
		}catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
	}
	@GetMapping("smsserver")
	public List<Map<String,Object>> getSmsServierSettings(@CurrentUser LoginUser user){
		if(dbprofile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.SmsSetting.class, UserProfileRepository.VIEW)||dbprofile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.SmsSetting.class, UserProfileRepository.VIEW_ALL)) {
			return dbSmsSetting.getAllSmsServer();
		}
		return new ArrayList<Map<String,Object>>();
	}
	@GetMapping("smsserver/{id}")
	public Map<String,Object> getSmsServer(@PathVariable("id")Long id,@CurrentUser LoginUser user){
		if(dbprofile.hasObjectAccess(user.getUsername(),com.pratikshat.exicom.entity.SmsSetting.class, UserProfileRepository.VIEW)||dbprofile.hasObjectAccess(user.getUsername(),com.pratikshat.exicom.entity.SmsSetting.class, UserProfileRepository.VIEW_ALL)) {
			return dbSmsSetting.getSmsServer(id);
		}
		return new HashMap<String,Object>();
	}
	@GetMapping("smsserver/{id}/headers")
	public List<Map<String,Object>> getHeadersBySmsServerId(@PathVariable("id")Long id,@CurrentUser LoginUser user){
		if(dbprofile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.SmsSetting.class, UserProfileRepository.VIEW)||dbprofile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.SmsSetting.class, UserProfileRepository.VIEW_ALL)) {
			return dbSmsSetting.getHeadersBySmsId(id);
		}
		return new ArrayList<Map<String,Object>>();
	}
	@GetMapping("smsserver/{id}/params")
	public List<Map<String,Object>> getParams(@PathVariable("id")Long id,@CurrentUser LoginUser user){
		if(dbprofile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.SmsSetting.class, UserProfileRepository.VIEW)||dbprofile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.SmsSetting.class,UserProfileRepository.VIEW_ALL)) {
			return dbSmsSetting.getParamsBySmsId(id);
		}
		return new ArrayList<Map<String,Object>>();
	}
	@PostMapping("smsserver")
	@Transactional
	public Map<String,Object> postDetails(@RequestBody Map<String,Object> request,HttpServletResponse response,@CurrentUser LoginUser  user) throws java.io.IOException{
		if(!dbprofile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.SmsSetting.class, UserProfileRepository.CREATE)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			if(request==null) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No content found");
				return null;
			}
			if(dbSmsSetting.countByUrl(request.get("url").toString())>0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "API already registered");
				return null;
			}
			
			try {
				new URL(request.get("url").toString());
			}catch(Exception ex) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "URL is not correct");
				return null;
			}
			
			dbSmsSetting.saveSmsSetting(request, user);
			
			return request;
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
	@PutMapping("smsserver/{id}")
	public Map<String,Object> updateSmsServerStting(@RequestBody Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse response,@PathVariable("id")Long id) throws java.io.IOException{
		if(!dbprofile.hasObjectAccess(user.getUsername(), SmsSetting.class, UserProfileRepository.EDIT)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			if(id!=Long.valueOf(request.get("id").toString())) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID not found");
				return null;
			}
			
			return dbSmsSetting.updateSmsSetting(request, user);
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
	
	@PutMapping("smsserver/{id}/enabledisable")
	@Transactional
	public ResponseEntity<?> updateSmsflag(@RequestParam("flag")boolean flag,@PathVariable("id")Long id,@CurrentUser LoginUser user, HttpServletResponse response) throws IOException{
		
		if(!dbprofile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.SmsSetting.class, UserProfileRepository.CREATE) || !dbprofile.hasObjectAccess(user.getUsername(), SmsSetting.class, UserProfileRepository.EDIT)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			
			dbSmsSetting.updateByActive(false);
			Optional<SmsSetting> sms= dbSmsSetting.findById(id);
			SmsSetting _sms= sms.get();
			_sms.setActive(flag);
			_sms.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_sms.setModifiedDate(new Date());
			dbSmsSetting.save(_sms);
			return ResponseEntity.ok(new HashMap<String,String>());
			
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
	}
	@GetMapping("mailserver")
	public List<Map<String,Object>> getMailerDetails(@CurrentUser LoginUser user){
		if(dbprofile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.MailerEntity.class, UserProfileRepository.VIEW)||dbprofile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.MailerEntity.class, UserProfileRepository.VIEW_ALL)) {
			return dbMailer.getAll();
		}
		return new ArrayList<Map<String,Object>>();
	}
	@PostMapping("mailserver")
	public Map<String,Object> saveMailerDetails(@CurrentUser LoginUser user,@RequestBody Map<String,Object> request,HttpServletResponse response) throws java.io.IOException {
		if(!dbprofile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.MailerEntity.class, UserProfileRepository.CREATE)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			dbMailer.saveMailerEntity(request, user);
			return request;
		}catch(BadRequestException bd) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, bd.getMessage());
		}
		catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
		}
			
		return null;
	}
	@PutMapping("mailserver/{id}/enabledisable")
	@Transactional
	public ResponseEntity<?> updateServerFFlag(@RequestParam("flg")boolean flg,@PathVariable("id")Long id,@CurrentUser LoginUser user, HttpServletResponse response) throws IOException{
		if(!dbprofile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.MailerEntity.class, UserProfileRepository.CREATE) || !dbprofile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.MailerEntity.class, UserProfileRepository.EDIT)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorised");
			return null;
		}
		
		try {
		
			dbMailer.updateAllActivity(false);
			Optional<MailerEntity> mailer=	dbMailer.findById(id);
			MailerEntity _m= mailer.get();
			_m.setActive(true);
			_m.setLastModifiedDate(new Date());
			_m.setLastModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			dbMailer.save(_m);
			return ResponseEntity.ok(new ArrayList());
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
	}
	@PutMapping("mailserver/{id}")
	public Map<String,Object> updateMailerEntity(@RequestBody Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse response,@PathVariable("id")Long id)throws java.io.IOException{
		if(!dbprofile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.MailerEntity.class, UserProfileRepository.EDIT)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorised");
			return null;
		}
		
		try {
			if(id!=Long.valueOf(request.get("id").toString())) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID not found");
				return null;
			}
			
			dbMailer.updateMailerEntity(request, user);
			return request;
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
	@GetMapping("mailserver/{id}/otherproperty")
	public List<Map<String,Object>> getMailServerOtherPropertyById(@PathVariable("id")Long id,@CurrentUser LoginUser user){
		
		return dbMailer.getMailerentityProperty(id);
		
	}
}
