package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.pratikshat.exicom.entity.CaseDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.PreRegistrationCases;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.CaseSearchRequest;
import com.pratikshat.exicom.model.request.PrcActionRequest;
import com.pratikshat.exicom.model.response.CaseResponse;
import com.pratikshat.exicom.model.response.PrcResponse;
import com.pratikshat.exicom.repository.CaseDetailsRepository;
import com.pratikshat.exicom.repository.PrcProblemReportedRepository;
import com.pratikshat.exicom.repository.PreRegistrationCasesRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;

@RestController
@RequestMapping("api/preRegistrationCases")
public class PreRegistrationCasesController {
	
	@Autowired
	PreRegistrationCasesRepository dbPreRegistrationCases;
	
	@Autowired
	UserProfileRepository dbProfile;
	
	@Autowired
	PrcProblemReportedRepository dbProblemReported;
	
	@Autowired
	CaseDetailsRepository dbCase;
	
	@GetMapping
	public Page<com.pratikshat.exicom.model.response.PrcResponse> getAll(Pageable page, @CurrentUser LoginUser user,@RequestParam(value="filterparam",required=false)String filterparam) {
		
		
		if (dbProfile.hasObjectAccess(user.getUsername(), PreRegistrationCases.class, UserProfileRepository.VIEW_ALL)) {
			if(filterparam.equalsIgnoreCase("Pending")) {
				return dbPreRegistrationCases.getAllPendingCase(page);
			}
			if(filterparam==null||filterparam.equalsIgnoreCase("All")) {
				return dbPreRegistrationCases.getAllCase(page);
			}
			return getCaseListByAction(filterparam,page,user);
			
		}
		if (dbProfile.hasObjectAccess(user.getUsername(),  PreRegistrationCases.class, UserProfileRepository.VIEW)) {
			List<String> _us = new ArrayList<String>();
			_us = dbProfile.getListOfUserAndChildByUser(user.getUsername());
			List<com.pratikshat.exicom.entity.EmployeeDetails> userLis = new ArrayList<com.pratikshat.exicom.entity.EmployeeDetails>();
			for (String u : _us) {
				userLis.add(new com.pratikshat.exicom.entity.EmployeeDetails(u));
			}
			if(filterparam==null||filterparam.equalsIgnoreCase("All"))
				return dbPreRegistrationCases.getAllCase(userLis, page);
			if(filterparam.equalsIgnoreCase("Pending")) {
				return dbPreRegistrationCases.getAllPendingCase(userLis,page);
			}
			
			return getCaseListByAction(filterparam,page,user);
		}
		return Page.empty();
	}

	@GetMapping("{caseid}")
	public com.pratikshat.exicom.model.response.PrcResponse getByCaseId(
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, @PathVariable("caseid") long caseid) {
		com.pratikshat.exicom.model.response.PrcResponse _case = dbPreRegistrationCases.getByCaseId(caseid);
		if (_case != null) {
			_case.setProblemReported(dbProblemReported.getByCaseId(_case.getPrcCaseId()));
		}
		return _case;
	}

	@PostMapping
	public com.pratikshat.exicom.model.response.PrcResponse createCase(
			@Valid @RequestBody com.pratikshat.exicom.model.request.PrcRequest request,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws java.io.IOException {

		if (!dbProfile.hasObjectAccess(user.getUsername(),  PreRegistrationCases.class, UserProfileRepository.CREATE)) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}

		if (dbPreRegistrationCases.countById(request.getCaseId()) > 0) {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Caseid already exists");
			return null;
		}
//		if (dbPreRegistrationCases.getCountByAssetsAndNoLastAction(request.getAssetsSerialNumber()) > 0) {
//			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
//					"Pre registration Case for the assets already registred and no action taken on it");
//			return null;
//		}
		
//		if (dbCase.getCountByAssetsAndNotCaseStatus(request.getAssetsSerialNumber(), "Closed") > 0) {
//			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
//					"Case for the assets already registred in open mode");
//			return null;
//		}
		
		if(request.getCaseProblemReported()==null || request.getCaseProblemReported().isEmpty()) {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"Please choose expected problem");
			return null;
		}

		try {
			long id = dbPreRegistrationCases.createPreRegistrationCases(request, user);
			com.pratikshat.exicom.model.response.PrcResponse _case = dbPreRegistrationCases.getByCaseId(id);
			if (_case != null) {
				_case.setProblemReported(dbProblemReported.getByCaseId(id));
			}
			return _case;
		} catch (BadRequestException ex) {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		} catch (UnauthoriseException ex) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}


	@PutMapping
	public PrcResponse updateCase(@Valid @RequestBody com.pratikshat.exicom.model.request.PrcRequest request,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws IOException {

		try {
			
			if(request.getCaseProblemReported()==null || request.getCaseProblemReported().isEmpty()) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
						"Please choose expected problem");
				return null;
			}
			
			PreRegistrationCases _caseDetails = dbPreRegistrationCases.findByCaseId(request.getCaseId());
			if (_caseDetails == null) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Case does not exist");
				return null;
			} else {
				if (_caseDetails.getLastAction()!=null) {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
							"You can not update the case");
					return null;
				}
				
				if (dbProfile.hasObjectAccess(user.getUsername(), PreRegistrationCases.class,
						UserProfileRepository.EDIT)) {

					long id = dbPreRegistrationCases.updatePreRegistrationCases(request, user);

					com.pratikshat.exicom.model.response.PrcResponse _case = dbPreRegistrationCases.getByCaseId(id);
					if (_case != null) {
						_case.setProblemReported(dbProblemReported.getByCaseId(_case.getPrcCaseId()));
					}
					return _case;

				} else {
					httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized");
					return null;
				}
			}

		} catch (BadRequestException ex) {
			ex.printStackTrace();
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		} catch (UnauthoriseException ex) {
			ex.printStackTrace();
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
	
	
	@PutMapping("action")
	public PrcResponse buttonAction(@Valid @RequestBody PrcActionRequest request,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws IOException {

		try {
			
			if(request.getCaseid() == 0) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Pre registration case id wrong");
				return null;
			}
			
			if(request.getAction() == null) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Action is null");
				return null;
			}
			
			if(request.getAction() !=null && request.getAction().equalsIgnoreCase("")) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Action is null");
				return null;
			}
			
			if(request.getComment() == null) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Please write comment");
				return null;
			}
			
			if(request.getComment() !=null && request.getComment().equalsIgnoreCase("")) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Please write comment");
				return null;
			}
			
			if(!request.getAction().equalsIgnoreCase(PreRegistrationCasesRepository.IGNORE) && !request.getAction().equalsIgnoreCase(PreRegistrationCasesRepository.GENERATE_CASE)) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Action is wrong");
				return null;
			}
			
			
			PreRegistrationCases _caseDetails = dbPreRegistrationCases.findByCaseId(request.getCaseid());
			if (_caseDetails == null) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Pre registration case id wrong");
				return null;
			}
			
			if (_caseDetails.getLastAction()!=null) {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Action on this record already taken");
					return null;
			}
				
				
			long id = dbPreRegistrationCases.actionOnPreRegistrationCase(request.getCaseid(), request.getAction(), request.getComment(), user);

			com.pratikshat.exicom.model.response.PrcResponse _case = dbPreRegistrationCases.getByCaseId(id);
			if (_case != null) {
					_case.setProblemReported(dbProblemReported.getByCaseId(_case.getPrcCaseId()));
				}
					return _case;

		} catch (BadRequestException ex) {
			ex.printStackTrace();
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		} catch (UnauthoriseException ex) {
			ex.printStackTrace();
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}

	}

	
	
	@GetMapping("related/{prcId}/getcase")
	public com.pratikshat.exicom.model.response.CaseResponse getCaseByPrcId(
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, @PathVariable("prcId") long prcId) {
		return dbCase.getCasesByPreRegistrationCase(prcId);
	}

	
	@GetMapping("byaction")
	public Page<com.pratikshat.exicom.model.response.PrcResponse> getCaseListByAction(
			@RequestParam("action") String action, Pageable page, @CurrentUser com.pratikshat.exicom.entity.LoginUser user) {
		if (dbProfile.hasObjectAccess(user.getUsername(), PreRegistrationCases.class, UserProfileRepository.VIEW_ALL)) {
			return dbPreRegistrationCases.getAllCaseWithAction(action, page);
		}
		if (dbProfile.hasObjectAccess(user.getUsername(),  PreRegistrationCases.class, UserProfileRepository.VIEW)) {
			List<String> _us = new ArrayList<String>();
			_us = dbProfile.getListOfUserAndChildByUser(user.getUsername());
			List<com.pratikshat.exicom.entity.EmployeeDetails> userLis = new ArrayList<com.pratikshat.exicom.entity.EmployeeDetails>();
			for (String u : _us) {
				userLis.add(new com.pratikshat.exicom.entity.EmployeeDetails(u));
			}
			return dbPreRegistrationCases.getAllCaseWithAction(userLis, action, page);
		}
		return Page.empty();
	}

	@GetMapping("related/case/{caseId}/getprc")
	public Page<com.pratikshat.exicom.model.response.PrcResponse> getPrcByCaseId(
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, @PathVariable("caseId") long caseId, Pageable page) {
		return dbPreRegistrationCases.getAllPrcByCaseId(caseId,page);
	}
	
	@GetMapping("related/site/{siteId}/getprc")
	public Page<com.pratikshat.exicom.model.response.PrcResponse> getPrcBySiteId(
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, @PathVariable("siteId") long siteId, Pageable page) {
		return dbPreRegistrationCases.getAllCasesBySiteId(siteId,page);
	}
	
	@GetMapping("related/assets/getprc")
	public Page<com.pratikshat.exicom.model.response.PrcResponse> getPrcByAsset(
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, @RequestParam("assetsserialnumber")String assetsSerialNumber, Pageable page) {
		return dbPreRegistrationCases.getAllCasesByAssetSerialNumber(assetsSerialNumber,page);
	}
	
	@GetMapping("advancesearch")
	public List<PrcResponse> caseSearch(@RequestParam Map<String,Object> request, @CurrentUser LoginUser user, HttpServletResponse  response)throws IOException, ParseException {
		if (dbProfile.hasObjectAccess(user.getUsername(), PreRegistrationCases.class, UserProfileRepository.VIEW_ALL))
			return dbPreRegistrationCases.advanceSearch(request, null);
		if (dbProfile.hasObjectAccess(user.getUsername(), PreRegistrationCases.class, UserProfileRepository.VIEW)) {
			List<String> usrs = dbProfile.getListOfUserAndChildByUser(user.getUsername());
			if (usrs != null && usrs.size() > 0) {
				return dbPreRegistrationCases.advanceSearch(request, usrs);
			}
		}

		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		return null;
	}
	
	@GetMapping("lastaction/forlist")
	public java.util.List<String> getLastActionList() {
		List<String> list = new ArrayList<>();
		list.add("Generate Case");
		list.add("Ignore");
		list.add("Pending");
		return list;
	}
}
