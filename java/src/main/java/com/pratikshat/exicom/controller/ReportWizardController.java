package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.pratikshat.exicom.entity.LoginUserRoles;
import com.pratikshat.exicom.entity.PreRegistrationCases;
import com.pratikshat.exicom.entity.ReportWizard;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.PrcResponse;
import com.pratikshat.exicom.repository.ReportWizardRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.utils.ExcelParser;
@RestController
@RequestMapping("api/report")
public class ReportWizardController {

	@Autowired
	UserProfileRepository dbProfile;
	@Autowired
	ReportWizardRepository dbReport;
	
	@GetMapping
	public List<Map<String,Object>> getAllReport(@CurrentUser LoginUser user,@RequestParam(value="type",required=false)String type){
		Iterator<LoginUserRoles> roleList=(Iterator<LoginUserRoles>) user.getAuthorities().iterator();
		List<Map<String,Object>> lst=new ArrayList<Map<String,Object>>();
		String role="";
		while(roleList.hasNext()) {
			LoginUserRoles rl=roleList.next();
			role=rl.getAuthority();
		}
		
		if(type==null) {
			lst.addAll(dbReport.getAllReportByTypeAndExecuter("ROLE", role));
			lst.addAll(dbReport.getAllReportByTypeAndExecuter("USER", user.getUsername()));	
		}
		if(type!=null) {
			lst.addAll(dbReport.getAllReportByTypeAndExecuter("ROLE", role,type));
			lst.addAll(dbReport.getAllReportByTypeAndExecuter("USER", user.getUsername(),type));
		}
		return lst;
	}
	@GetMapping("{reportid}/param")
	public List<Map<String,Object>> getParams(@PathVariable("reportid")Long reportId){
		return dbReport.getParamByReportId(reportId);
	}
	@GetMapping("{reportid}/executer")
	public List<Map<String,Object>> getExecuter(@PathVariable("reportid")Long reportId){
		return dbReport.getExecuterById(reportId);
	}
	@GetMapping("wizard")
	public List<Map<String,Object>> getWizard(@CurrentUser LoginUser user,HttpServletResponse response)throws IOException{
		if(!dbProfile.hasObjectAccess(user.getUsername(), ReportWizard.class, UserProfileRepository.VIEW)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You are not authorise");
			return null;
		}
		return dbReport.getAll();
	}
	
	@PostMapping("wizard")
	public String postReportWizard(@RequestBody Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException {
		if(!dbProfile.hasObjectAccess(user.getUsername(),ReportWizard.class, UserProfileRepository.CREATE)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorise");
			return null;
		}
		try {
			dbReport.createNewReportWizard(request, user);
			return "{}";
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,e.getMessage());
		} catch (UnauthoriseException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,e.getMessage());
		}
		return null;
	}
	@PutMapping("wizard/{reportid}")
	public String editReportWizard(@PathVariable("reportid")Long Id,@RequestBody Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		if(!dbProfile.hasObjectAccess(user.getUsername(), ReportWizard.class, UserProfileRepository.EDIT)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorise");
			return null;
		}
		try {
			dbReport.editReportWizard(Id, request, user);
			return "{}";
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,e.getMessage());
		} catch (UnauthoriseException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,e.getMessage());
		}
		return null;
	}
	@PostMapping("execute/{reportid}")
	public List<Map<String,Object>> executeReport(@PathVariable("reportid")Long id,@RequestBody List<Map<String,String>> param,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		List<String> userList=dbProfile.getListOfUserAndChildByUser(user.getUsername());
		try {
			return dbReport.executeReport(id, param, user, userList);
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		} catch (UnauthoriseException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
		}
		return null;
	}
	@GetMapping("executertype")
	public List<Map<String,Object>> getUserAndRoleList(@RequestParam("type")String type){
		if(type.equalsIgnoreCase("ROLE"))
			return dbReport.getRoles();
		if(type.equalsIgnoreCase("USER"))
			return dbReport.getUser();
		return null;
	}
	
	@DeleteMapping("wizard/{reportid}")
	public String deleteReport(@PathVariable("reportid")Long Id,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException {
		
		if(!dbProfile.hasObjectAccess(user.getUsername(), ReportWizard.class, UserProfileRepository.EDIT)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorise");
			return null;
		}
		
		try {
			dbReport.deleteReportWizard(Id, user);
			return "{}";
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("execute/download/{reportid}")
	public void executeReportAndDownload(@PathVariable("reportid")Long id,@RequestParam List<Map<String,String>> param,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		List<String> userList=dbProfile.getListOfUserAndChildByUser(user.getUsername());
		try {
			
			List<Map<String,Object>> lst =dbReport.executeReport(id, param, user, userList);
			
//			StringBuffer sb=new StringBuffer();
//			
//			if(lst!=null) {
//				
//				for(Map<String,Object> ob:lst) {
//					
//				}
//			}
			
			ExcelParser prser=new ExcelParser();
			try {
				prser.genrateFile(lst);
				
				response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+id+".xlsx");
				response.setContentType("application/vnd.ms-excel");
				response.getOutputStream().write(prser.genrateFile(lst).toByteArray());
				response.getOutputStream().flush();
				response.getOutputStream().close();
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		} catch (UnauthoriseException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
		}
	
	}
	
	@GetMapping("advancesearch")
	public List<Map<String,Object>> reportSearch(@RequestParam Map<String,Object> request, @CurrentUser LoginUser user, HttpServletResponse  response)throws IOException, ParseException {
		Iterator<LoginUserRoles> roleList=(Iterator<LoginUserRoles>) user.getAuthorities().iterator();
		List<Map<String,Object>> lst=new ArrayList<Map<String,Object>>();
		String role="";
		while(roleList.hasNext()) {
			LoginUserRoles rl=roleList.next();
			role=rl.getAuthority();
		}
		
		lst.addAll(dbReport.getAllReportByTypeAndExecuterAdvanceSearch(request, "ROLE", role));
		lst.addAll(dbReport.getAllReportByTypeAndExecuterAdvanceSearch(request, "USER", user.getUsername()));	
		
		return lst;
	}
	
	@GetMapping("wizard/forlist")
	public List<Map<String,Object>> forlist(){
		
		return dbReport.getReportNameforList();
	}
}
