package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
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
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.CircleDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.LegalEntity;
import com.pratikshat.exicom.entity.LegalEntityReceintVisit;
import com.pratikshat.exicom.entity.LegalEntityType;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.LegalEntityResponse;
import com.pratikshat.exicom.model.response.LocationResponse;
import com.pratikshat.exicom.repository.CircleDetailsRepository;
import com.pratikshat.exicom.repository.GlobalSearchIndexRepository;
import com.pratikshat.exicom.repository.LegalEntityRecentVisitRepo;
import com.pratikshat.exicom.repository.LegalEntityRepository;
import com.pratikshat.exicom.repository.LegalEntityTypeRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;

@RestController
@RequestMapping("api/legalentity")
public class LegalEntityController {
	@Autowired
	private LegalEntityRepository dbLegal;
	@Autowired
	private LegalEntityRecentVisitRepo dbReceint;
	@Autowired
	LegalEntityTypeRepository dbLegalEntityType;
	@Autowired
	private com.pratikshat.exicom.repository.UserProfileRepository dbUserProfile;

	@Autowired CircleDetailsRepository dbCircle;
	
	@Autowired
	GlobalSearchIndexRepository dbGlobalSearch;
	
	int businessDmainId=2;
	
	@GetMapping
	public Page<LegalEntityResponse> getLegalEntityList(Pageable page, @CurrentUser LoginUser user,@RequestParam(value="filtername",required=false)String filtername){
		if(dbUserProfile.hasObjectAccess(user.getUsername(), "LegalEntity", "viewAllRights") || dbUserProfile.hasObjectAccess(user.getUsername(), "LegalEntity", "viewRights")) {
			if(filtername!=null&&filtername.equalsIgnoreCase("All"))
				return dbLegal.getAll(page, businessDmainId);
			if(filtername!=null&&filtername.equalsIgnoreCase("related"))
				return dbLegal.getReceiveVisitByOwnerId(user.getUsername(),page, businessDmainId);
			return dbLegal.getAll(page, businessDmainId);
		}
		return Page.empty();
		
	}
	@GetMapping("{id}")
	public LegalEntityResponse getLegalentity(@PathVariable("id")long id,@CurrentUser LoginUser user) {
		
		if(dbUserProfile.hasObjectAccess(user.getUsername(), "LegalEntity", "viewAllRights") || dbUserProfile.hasObjectAccess(user.getUsername(), "LegalEntity", "viewRights")) {
			
			try {
				LegalEntityReceintVisit receint=dbReceint.findByLegalEntityIdAndVisitorId(id, user.getUsername());
				if(receint!=null) {
					receint.setDateOfVisit(new Date());
				}
				else {
					receint=new LegalEntityReceintVisit(id,new Date(),user.getUsername());
				}
					
				dbReceint.save(receint);	
			}catch(Exception e) {}
			
			return dbLegal.getByLegalEntityId(id);
		}
		return new LegalEntityResponse();
		
		
	}
	@GetMapping("mylegalentity")
	public List<LegalEntityResponse> getMyLegalEnttiy(@CurrentUser LoginUser userDetails){
		return dbLegal.getByOwnerId(userDetails.getUsername());
	}
	@GetMapping("recentvisit")
	public Page<LegalEntityResponse> getRecentvisit(@CurrentUser LoginUser user,Pageable page){
		
		if(dbUserProfile.hasObjectAccess(user.getUsername(), "LegalEntity", "viewAllRights") || dbUserProfile.hasObjectAccess(user.getUsername(), "LegalEntity", "viewRights")) {
			
			return dbLegal.getReceiveVisitByOwnerId(user.getUsername(),page, businessDmainId);
		}
		return Page.empty();
		
		
	}
	@PostMapping
	public LegalEntityResponse postLegalEntity(@RequestBody LegalEntityResponse legalentity,@CurrentUser LoginUser user, HttpServletResponse response) throws IOException {
		
		if(!dbUserProfile.hasObjectAccess(user.getUsername(), "LegalEntity", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have permission");
			return null;
		}
		
		try {
			
			if(dbLegal.countByLegalEntityId(legalentity.getLegalentityId())>0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Legal Entity Already Exists");
				return null;
			}



			LegalEntity _legal=dbLegal.findByLegalEntityName(legalentity.getLegalEntityName());//new LegalEntity();
			if(_legal==null){
				_legal=new LegalEntity();
				_legal.setAccountOwner(new EmployeeDetails(legalentity.getOwnerAccountId()));
			if(legalentity.getLegalEntiytFixedLayou()!=0) {
				_legal.setLegalEntityFixedPayout(legalentity.getLegalEntiytFixedLayou());
			}else {
				_legal.setLegalEntityFixedPayout(0);
			}
			
			_legal.setLegalEntityName(legalentity.getLegalEntityName());
			_legal.setLegalEntityType(new LegalEntityType(legalentity.getLegalEntityTypeId()));
			_legal.setSynced(false);
			_legal.setCreatedBy(new EmployeeDetails(user.getUsername()));
			_legal.setCreatedDate(new java.util.Date());
			_legal.setModifiedBy(new EmployeeDetails(user.getUsername()));
			_legal.setModifiedDate(new java.util.Date());
			_legal.setBusinessDoamin(new BusinessDomain(businessDmainId));
			dbLegal.save(_legal);
			
			try {
				GlobalSearchIndex _search=new GlobalSearchIndex();
				_search.setSearchString(_legal.getLegalEntityName());
				_search.setKeyName("legalEntityName");
				_search.setObjectName("LegalEntity");
				_search.setObjectDetailsUrl("legalentity/"+_legal.getLegalEntityId());
				_search.setObjectIdName("legalEntityId");
				_search.setObjectIdValue(String.valueOf(_legal.getLegalEntityId()));
				
				dbGlobalSearch.save(_search);
				
			}catch(Exception e) {}
			}
			else{
				if(_legal.getBusinessDoamin()==null)
					_legal.setBusinessDoamin(new BusinessDomain(businessDmainId));
				if(_legal.getBusinessDoamin().getBusinessDomainId()!=businessDmainId){
					_legal.setBusinessDoamin(new BusinessDomain(3));
					dbLegal.save(_legal);
				}
			}
			
			
			
			legalentity.setLegalentityId(_legal.getLegalEntityId());
			return dbLegal.getByLegalEntityId(_legal.getLegalEntityId());
			
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
	@PutMapping("{legalEntityId}")
	public LegalEntityResponse putLegalentity(@PathVariable("legalEntityId")long legalEntityId, @RequestBody LegalEntityResponse legalentity,@CurrentUser LoginUser user, HttpServletResponse response) throws Exception{
		
		if(!dbUserProfile.hasObjectAccess(user.getUsername(), "LegalEntity", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have permission");
			return null;
		}
		
		try {
			if(dbLegal.countByLegalEntityId(legalEntityId)==0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Legal Entity Does Not Exists");
				return null;
			}

			if(legalEntityId!=legalentity.getLegalentityId()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Legal Entity Does Not Exists");
				return null;
			}

			LegalEntity _legal=dbLegal.findByLegalEntityId(legalEntityId);
			_legal.setAccountOwner(new EmployeeDetails(legalentity.getOwnerAccountId()));
			_legal.setLegalEntityFixedPayout(legalentity.getLegalEntiytFixedLayou());
			_legal.setLegalEntityName(legalentity.getLegalEntityName());
			_legal.setLegalEntityType(new LegalEntityType(legalentity.getLegalEntityTypeId()));
			_legal.setSynced(false);
			_legal.setModifiedBy(new EmployeeDetails(user.getUsername()));
			_legal.setModifiedDate(new java.util.Date());
			if(_legal.getBusinessDoamin()==null)
				_legal.setBusinessDoamin(new BusinessDomain(businessDmainId));
			if(_legal.getBusinessDoamin().getBusinessDomainId()!=businessDmainId){
				_legal.setBusinessDoamin(new BusinessDomain(3));
			}
			
				
			dbLegal.save(_legal);

			List<CircleDetails> lstcircle=dbCircle.circleListByLegalEntitys(_legal.getLegalEntityId());
			for(CircleDetails cr: lstcircle){
				if(businessDmainId==1) {
					cr.setCircleManagerBattry(_legal.getAccountOwner().getContact());
					cr.setServiceCenterBattery(_legal);
				}
				if(businessDmainId==2) {
					cr.setCircleManagerPower(_legal.getAccountOwner().getContact());
					cr.setServiceCenterPower(_legal);

				}
				dbCircle.save(cr);
			}

			return dbLegal.getByLegalEntityId(_legal.getLegalEntityId());
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
	@GetMapping("forlist")
	public List<com.pratikshat.exicom.model.response.ServiceCenterForList> getForList(){
		return dbLegal.getListForServiceCenter(businessDmainId);
	}
	@GetMapping("forlist/{type}")
	public List<com.pratikshat.exicom.model.response.ServiceCenterForList> getForListByType(@PathVariable("type")String legalEntityType){
		return dbLegal.getListForServiceCenterByType(legalEntityType, businessDmainId);
	}
	
	@GetMapping("type")
	public List<LegalEntityType> getType(){
		return dbLegalEntityType.findAll();
	}
	@GetMapping("releted/{legalentityid}/location")
	public Page<com.pratikshat.exicom.model.response.LocationResponse> getLocationByLegalEntityId(@PathVariable("legalentityid") long legalentityid,Pageable page){
		return dbLegal.getLocationFromLegalEntityId(legalentityid,page);
	}
	@GetMapping("releted/{legalentityid}/eng")
	public Page<com.pratikshat.exicom.model.request.Contacts> getServiceResource(@PathVariable("legalentityid") long legalentityid,Pageable page){
		return dbLegal.getEngByLegalEntity(legalentityid, page);
	}
	
	@GetMapping("advancesearch")
	public List<LegalEntityResponse> getAdvanceSearchresult(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException{
		
		if(dbUserProfile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.LegalEntity.class, UserProfileRepository.VIEW_ALL)) {
			return 	dbLegal.advanceSearch(request, user, businessDmainId);
		}
		
		if(dbUserProfile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.LegalEntity.class, UserProfileRepository.VIEW)) {
			return 	dbLegal.advanceSearch(request, user, businessDmainId);
		}
		
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		return null;
		
	}
}
