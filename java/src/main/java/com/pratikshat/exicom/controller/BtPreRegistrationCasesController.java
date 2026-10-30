package com.pratikshat.exicom.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import com.pratikshat.exicom.entity.BtPreRegistrationCases;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.PreRegistrationCases;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.BtCaseResponse;
import com.pratikshat.exicom.model.response.BtPrcResponse;
import com.pratikshat.exicom.model.response.BtPreRegisterReportResponse;
import com.pratikshat.exicom.repository.BtPreRegisterReportRepository;
import com.pratikshat.exicom.repository.BtPreRegistrationCasesRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtPreRegistrationCasesService;

@RestController
@RequestMapping("/bt/api/preRegistrationCases")
public class BtPreRegistrationCasesController {
	
	@Autowired
	BtPreRegistrationCasesService preService;
	
	@Autowired
	BtPreRegistrationCasesRepository dbPrc;
	
	@Autowired
	BtPreRegisterReportRepository dbpre;
	
	@Autowired
	UserProfileRepository dbProfile;
	
	@Autowired
	BtPreRegistrationCasesRepository dbCase;
	
	@PostMapping 
	private BtPrcResponse btPreCases(@RequestBody Map<String, Object> req, @CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return preService.saveObjects(req, user);
	}
	
	@PutMapping("{id}")
	private Map<String, Object> updatebtPreCase(@RequestBody Map<String, Object> reqq,@PathVariable("id") long id,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException {
		 return preService.updateObject(reqq, user);
	}
	
	@GetMapping
	private Page<Map<String, Object>> getPage(@CurrentUser LoginUser user,Pageable page,@RequestParam(value="filterparam",required=false)String filterparam) throws BadRequestException, UnauthoriseException{
		boolean viewAll= dbProfile.hasObjectAccess(user.getUsername(), BtPreRegistrationCases.class, UserProfileRepository.VIEW_ALL);
		boolean view= dbProfile.hasObjectAccess(user.getUsername(), BtPreRegistrationCases.class, UserProfileRepository.VIEW);
		if(viewAll) {
			if(filterparam==null||filterparam.equalsIgnoreCase("All")) {
				System.out.println("getPage");
				return dbCase.getPage(page);
			}
			if(filterparam==null||filterparam.equalsIgnoreCase("Pending")) {
				System.out.println("getPending");
				return dbCase.getPanding(page);
			}
			return dbCase.getPage(page);
		}
		if(view) {
			List<String> _us = new ArrayList<String>();
			_us = dbProfile.getListOfUserAndChildByUser(user.getUsername());
			List<String> userLis = new ArrayList<String>();
			for (String u : _us) {
				userLis.add(u);
			}
			if(filterparam==null||filterparam.equalsIgnoreCase("All")) {
				return dbCase.getPageForUser(page,userLis);
			}
			if(filterparam==null||filterparam.equalsIgnoreCase("Pending")) {
				return dbCase.getPandingForUser(page,userLis);
			}
		}
		
		return Page.empty();
	}
	
//	@GetMapping("/{id}")
//	private Map<String, Object> getById(@PathVariable( "id" )int id,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
//		return  preService.getSingle(id, user);	
//	}
	
	@GetMapping("{caseid}")
	public BtPrcResponse getByCaseId(@CurrentUser com.pratikshat.exicom.entity.LoginUser user, @PathVariable("caseid") long caseid) throws BadRequestException,UnauthoriseException {
		return preService.getByCaseId(user, caseid);
	}
	
	@GetMapping("pre/{caseid}")
	public Set<BtPreRegisterReportResponse> getpreByCaseId(@CurrentUser com.pratikshat.exicom.entity.LoginUser user, @PathVariable("caseid") long caseid) throws BadRequestException,UnauthoriseException {
		return dbpre.getByCaseId(caseid);
	}
	
	@GetMapping("/advancesearch")
	private List<Map<String, Object>> advanceSearch(@RequestParam Map<String, Object> reqq,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return preService.advanceSearch(reqq, user);
	}
	
	@GetMapping("forlist")
	private List<Map<String, Object>> forList(@RequestParam("search") String search,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return	preService.searchForList(search, user);
	}
	
	@PutMapping("action")
	public Map<String, Object> buttonAction(@Valid @RequestBody Map<String, Object> request,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws  UnauthoriseException, BadRequestException {
		return preService.actionOnBtPreRegistrationCase(request, user);
	}
	
//	@GetMapping("related/site/{siteId}/getprc")
//	public Page<Map<String, Object>> getPrcBySiteId(@PathVariable("siteId") long siteId, Pageable page) {
//		return preService.getPrcBySiteId(siteId,page);
//	}
	
	@GetMapping("lastaction/forlist")
	public java.util.List<String> getLastActionList() {
		List<String> list = new ArrayList<>();
		list.add("Generate Case");
		list.add("Ignore");
		list.add("Pending");
		return list;
	}
	
	@GetMapping("related/{prcId}/getcase")
	public Map<String, Object> getCaseByPrcId(
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, @PathVariable("prcId") long prcId) {
		return preService.getCaseBtPrcId(prcId);
	}
	
	@GetMapping("byaction")
	public Page<BtPrcResponse> getCaseListByAction(
			@RequestParam("action") String action, Pageable page, @CurrentUser com.pratikshat.exicom.entity.LoginUser user) throws UnauthoriseException {
		return preService.getAllCaseList(action, page, user);
	}
	
	@GetMapping("related/assets/getprc")
	public Page<Map<String, Object>> getPrcByAsset(@RequestParam("assetsserialnumber")String assetsSerialNumber, Pageable page) {
		return dbPrc.getAllCasesByAssetSerialNumber(assetsSerialNumber,page);
	}
	
	@GetMapping("related/site/{siteId}/getprc")
	public Page<Map<String, Object>> getPrcBySiteId(
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, @PathVariable("siteId") long siteId, Pageable page) {
		return dbPrc.getAllCasesBySiteId(siteId,page);
	}
	
	@GetMapping("related/case/{caseId}/getprc")
	public Page<Map<String, Object>> getPrcByCaseId(
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, @PathVariable("caseId") long caseId, Pageable page) {
		return dbPrc.getAllPrcByCaseId(caseId,page);
	}

}
