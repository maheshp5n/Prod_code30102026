package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import com.pratikshat.exicom.entity.BatteryComplaintStatus;
import com.pratikshat.exicom.entity.BatteryFSRRecentVisit;
import com.pratikshat.exicom.entity.BatterySiteType;
import com.pratikshat.exicom.entity.CaseProblemReported;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BatteryFSRRequest;
import com.pratikshat.exicom.model.response.BatteryFSRResponse;
import com.pratikshat.exicom.repository.BatteryComplaintStatusRepository;
import com.pratikshat.exicom.repository.BatteryFSRRecentVisitRepository;
import com.pratikshat.exicom.repository.BatteryFSRRepository;
import com.pratikshat.exicom.repository.BatterySiteTypeRepository;
import com.pratikshat.exicom.repository.CaseProblemReportedRepository;

@RestController
@RequestMapping("api/batteryfsr")
public class BatteryFSRController {

	@Autowired
	BatteryFSRRecentVisitRepository dbBatteryFSRRecentVisit;
	
	@Autowired
	BatteryFSRRepository dbBatteryFSR;
	
	@Autowired
	BatteryComplaintStatusRepository dbBatteryComplaintStatus;
	
	@Autowired
	BatterySiteTypeRepository dbBatterySiteType;
	
	@Autowired
	CaseProblemReportedRepository dbCaseProblemReported;
	
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	@GetMapping("complaintstatus")
	public List<BatteryComplaintStatus> getBatteryComplaintStatus(){
		return dbBatteryComplaintStatus.findAll();
	}
	
	@GetMapping("sitetype")
	public List<BatterySiteType> getBatterySiteType(){
		return dbBatterySiteType.findAll();
	}
	
	@GetMapping
	public Page<BatteryFSRResponse> getAll(Pageable page,@CurrentUser LoginUser user,@RequestParam(value="filtername",required=false)String filtername){		
		if(dbProfile.hasObjectAccess(user.getUsername(), "BatteryFSR", "viewAllRights")) {
			if(filtername!=null&&filtername.equalsIgnoreCase("related"))
				return dbBatteryFSR.getBatteryFSRRecentVisit(user.getUsername(), page);
			return dbBatteryFSR.getBatteryFSR(page);	
		}
		if(dbProfile.hasObjectAccess(user.getUsername(), "BatteryFSR", "viewRights")) {
			if(filtername!=null&&filtername.equalsIgnoreCase("related"))
				return dbBatteryFSR.getBatteryFSRRecentVisit(user.getUsername(), page);
			
			List<String> urs=new ArrayList<String>();
			urs=dbProfile.getListOfUserAndChildByUser(user.getUsername());
			List<com.pratikshat.exicom.entity.EmployeeDetails> emps=new ArrayList<com.pratikshat.exicom.entity.EmployeeDetails>();
			for(String u:urs) {
				emps.add(new com.pratikshat.exicom.entity.EmployeeDetails(u));
			}
			return dbBatteryFSR.getBatteryFSR(emps, page);
		}
		return Page.empty();
	}
	
	@GetMapping("recentvisit")
	public Page<BatteryFSRResponse> getReceintVisist(@CurrentUser LoginUser user,Pageable page, HttpServletResponse response)throws IOException{
		if(dbProfile.hasObjectAccess(user.getUsername(), "BatteryFSR", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "BatteryFSR", "viewAllRights")) {
			try {
				return dbBatteryFSR.getBatteryFSRRecentVisit(user.getUsername(), page);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return Page.empty();
		
	}
	
	
	@GetMapping("{id}")
	public BatteryFSRResponse getByCaseId(@CurrentUser LoginUser user,@PathVariable("id")long id, HttpServletResponse response) throws java.io.IOException{
		if(dbProfile.hasObjectAccess(user.getUsername(), "BatteryFSR", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "BatteryFSR", "viewAllRights")) {
			BatteryFSRResponse fsrResponse=dbBatteryFSR.getBatteryFSRById(id);
			if(fsrResponse!=null) {
				fsrResponse.setProblemReported(dbCaseProblemReported.getByCaseId(fsrResponse.getCaseId()));
			}
			try {
				BatteryFSRRecentVisit _recent=dbBatteryFSRRecentVisit.findByBatteryFSRIdAndVisitorId(id, user.getUsername());
				if(_recent==null)
					_recent=new BatteryFSRRecentVisit(id, user.getUsername());
				else
					_recent.setVisitDate(new java.util.Date());
				dbBatteryFSRRecentVisit.save(_recent);
				dbBatteryFSRRecentVisit.flush();
				
				return fsrResponse;
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return new BatteryFSRResponse();
	}
	
	
	@PostMapping
	public BatteryFSRResponse createCase(@Valid @RequestBody BatteryFSRRequest request,@CurrentUser LoginUser user,HttpServletResponse response)throws java.io.IOException{
		
		if(!dbProfile.hasObjectAccess(user.getUsername(), "BatteryFSR", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			BatteryFSRRequest batteryRequest = dbBatteryFSR.saveBatteryFSR(request, user);
			BatteryFSRResponse fsrResponse= dbBatteryFSR.getBatteryFSRById(batteryRequest.getId());
			 if(fsrResponse!=null) {
					fsrResponse.setProblemReported(dbCaseProblemReported.getByCaseId(fsrResponse.getCaseId()));
				}
			 return fsrResponse;
		} catch(BadRequestException ex) {
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
	public BatteryFSRResponse updateCase(@Valid @RequestBody BatteryFSRRequest request,@CurrentUser LoginUser user,HttpServletResponse response) throws java.io.IOException {
		if(!dbProfile.hasObjectAccess(user.getUsername(), "BatteryFSR", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			BatteryFSRRequest batteryRequest = dbBatteryFSR.updateBatteryFSR(request, user);
			BatteryFSRResponse fsrResponse= dbBatteryFSR.getBatteryFSRById(batteryRequest.getId());
			 if(fsrResponse!=null) {
					fsrResponse.setProblemReported(dbCaseProblemReported.getByCaseId(fsrResponse.getCaseId()));
				}
			 return fsrResponse;
		} catch(BadRequestException ex) {
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
	
	
	@GetMapping("related/{caseId}")
	public Page<BatteryFSRResponse> getReceintVisist(@PathVariable("caseId")long caseId, @CurrentUser LoginUser user,Pageable page){
		return dbBatteryFSR.getBatteryFSRByCaseId(caseId, page);
	}
	
	@GetMapping("problemreported/{caseId}")
	public Set<CaseProblemReported> getProblemReported(@PathVariable("caseId")long caseId){
	return dbCaseProblemReported.getByCaseId(caseId);
	}
}
