package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.pratikshat.exicom.entity.Accounts;
import com.pratikshat.exicom.entity.CaseDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.Oppertunity;
import com.pratikshat.exicom.entity.OppertunityForecastCategory;
import com.pratikshat.exicom.entity.OppertunityRecentVisit;
import com.pratikshat.exicom.entity.OppertunityStage;
import com.pratikshat.exicom.entity.OppertunityType;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.OppertunityLineItemRequest;
import com.pratikshat.exicom.model.request.OppertunityRequest;
import com.pratikshat.exicom.model.response.OppertunityLineItemResponse;
import com.pratikshat.exicom.model.response.OppertunityResponse;
import com.pratikshat.exicom.model.response.QuoteResponse;
import com.pratikshat.exicom.model.response.SimpleResponseMessage;
import com.pratikshat.exicom.repository.OppertunityForecastCategoryRepository;
import com.pratikshat.exicom.repository.OppertunityLineItemRepository;
import com.pratikshat.exicom.repository.OppertunityRecentVisitRepository;
import com.pratikshat.exicom.repository.OppertunityRepository;
import com.pratikshat.exicom.repository.OppertunityStageRepository;
import com.pratikshat.exicom.repository.OppertunityTypeRepository;
import com.pratikshat.exicom.repository.QuoteRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;

@RestController
@RequestMapping("api/oppertunity")
public class OppertunityController {

	@Autowired
	OppertunityStageRepository dbOppertunityStage;

	@Autowired
	OppertunityTypeRepository dbOppertunityType;

	@Autowired
	OppertunityForecastCategoryRepository dbOppertunityForecastCategory;

	@Autowired
	OppertunityRepository dbOppertunityRepository;

	@Autowired
	OppertunityRecentVisitRepository dbOppertunityRecentVisit;
	
	@Autowired
	OppertunityLineItemRepository dbOppertunityLineItem;
	
	@Autowired
	QuoteRepository dbQuote;
	
	@Autowired
	private com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	@GetMapping("stage")
	public List<OppertunityStage> getOppertunityStage() {
		return dbOppertunityStage.findAll();
	}

	@GetMapping("type")
	public List<OppertunityType> getOppertunityType() {
		return dbOppertunityType.findAll();
	}

	@GetMapping("forecastcategory")
	public List<OppertunityForecastCategory> getOppertunityForecastCategory() {
		return dbOppertunityForecastCategory.findAll();
	}

	@PostMapping
	public OppertunityResponse createOppertunity(@Valid @RequestBody OppertunityRequest oppertunityRequest,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse response)throws IOException {

		if(!dbProfile.hasObjectAccess(user.getUsername(),"Oppertunity", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			dbOppertunityRepository.saveOppertunity(oppertunityRequest, user);
			return dbOppertunityRepository.getByOppertunityId(oppertunityRequest.getOppertunityId());
		} catch(BadRequestException ex) {
			ex.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			ex.printStackTrace();
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}

	@PutMapping
	public OppertunityResponse updateOppertunity(@Valid @RequestBody OppertunityRequest oppertunityRequest,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse response)throws IOException {

		if(!dbProfile.hasObjectAccess(user.getUsername(),"Oppertunity", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			
			OppertunityRequest request = dbOppertunityRepository.updateOppertunity(oppertunityRequest, user);
			
			return dbOppertunityRepository.getByOppertunityId(oppertunityRequest.getOppertunityId());
		}  catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}

	
	@GetMapping("list")
	public Page<OppertunityResponse> getOppertunityByCaseStage(@CurrentUser com.pratikshat.exicom.entity.LoginUser user,@RequestParam(name = "stage") String stage,Pageable page,@RequestParam(value="filtername",required=false)String filtername,@RequestParam(value="filterparam",required=false)String filterparam){
		if(dbProfile.hasObjectAccess(user.getUsername(), "Oppertunity", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "Oppertunity", "viewAllRights")) {
			if(filtername!=null)
				stage=filtername;
			if(filterparam!=null)
				stage=filterparam;
			if(stage!=null&&stage.equalsIgnoreCase("Pipeline"))
				return dbOppertunityRepository.getOppertunityListByForecast(stage, page);
			if(stage==null||stage.equalsIgnoreCase("All"))
				return dbOppertunityRepository.getOppertunityListAll(page);
			
			return dbOppertunityRepository.getOppertunityListByCaseStage(stage, page);
		}
		return Page.empty();
	}

	@GetMapping("{oppertunityId}")
	public OppertunityResponse getByOppertunityId(@CurrentUser com.pratikshat.exicom.entity.LoginUser user,
			@PathVariable("oppertunityId") long oppertunityId) {
		
		if(dbProfile.hasObjectAccess(user.getUsername(), "Oppertunity", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "Oppertunity", "viewAllRights")) {
			OppertunityResponse _oppertunity = dbOppertunityRepository.getByOppertunityId(oppertunityId);

			try {
				OppertunityRecentVisit _recent = dbOppertunityRecentVisit.findByOppertunityIdAndVisitorId(oppertunityId,
						user.getUsername());
				if (_recent == null)
					_recent = new OppertunityRecentVisit(oppertunityId, user.getUsername());
				else
					_recent.setVisitedDate(new Date());
				dbOppertunityRecentVisit.save(_recent);
				dbOppertunityRecentVisit.flush();
			} catch (Exception e) {
			}
			return _oppertunity;
		}
		return new OppertunityResponse();
		
	}

	@GetMapping("recentvisit")
	public Page<OppertunityResponse> getReceintVisist(@CurrentUser LoginUser user, Pageable page) {
		if(dbProfile.hasObjectAccess(user.getUsername(), "Oppertunity", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "Oppertunity", "viewAllRights")) {
			return dbOppertunityRepository.getOppertunityRecentVisitList(user.getUsername(), page);
		}
		return Page.empty();
		
	}

	public Oppertunity updateOppertunity(OppertunityRequest oppertunityRequest, LoginUser user) throws BadRequestException,Exception {
		if (oppertunityRequest != null) {

			Oppertunity _oppertunity = dbOppertunityRepository
					.findByOppertunityId(oppertunityRequest.getOppertunityId());

			_oppertunity.setAccounts(new Accounts(oppertunityRequest.getAccountId()));
			_oppertunity.setAmmount(oppertunityRequest.getAmmount());
			_oppertunity.setCaseDetails(new CaseDetails(oppertunityRequest.getCaseId()));
			_oppertunity.setExpectedRevenue(oppertunityRequest.getExpectedRevenue());
			_oppertunity.setCreateDate(new Date());
			_oppertunity.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_oppertunity.setForcastCategory(new OppertunityForecastCategory(oppertunityRequest.getForcastCategoryId()));
			_oppertunity.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_oppertunity.setModifiedDate(new Date());
			_oppertunity.setOppertunityStage(new OppertunityStage(oppertunityRequest.getOppertunityStageId()));
			_oppertunity.setOppertunityType(new OppertunityType(oppertunityRequest.getOppertunityTypeId()));
			_oppertunity.setOwnerDetails(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_oppertunity.setProbability(oppertunityRequest.getProbability());
			_oppertunity.setSyncFlag(false);

			dbOppertunityRepository.save(_oppertunity);

			return _oppertunity;
		} else {
			throw new BadRequestException("No data found to save details");
		}
	}
	
	@GetMapping("listbyforecast")
	public Page<OppertunityResponse> getOppertunityByForecast(@CurrentUser com.pratikshat.exicom.entity.LoginUser user,@RequestParam(name = "forecast") String forecast,Pageable page){
		if(dbProfile.hasObjectAccess(user.getUsername(), "Oppertunity", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "Oppertunity", "viewAllRights")) {
			return dbOppertunityRepository.getOppertunityListByForecast(forecast, page);
		}
		return Page.empty();
		
	}
	
	
	@PostMapping("lineitem")
	public Page<OppertunityLineItemResponse> addOppertunityLineItems(@Valid @RequestBody List<OppertunityLineItemRequest> lineItems, @RequestParam(name = "priceBookId") long priceBookId,
			@RequestParam(name = "oppertunityId") long oppertunityId, @RequestParam(name = "businessDomainId") int businessDomainId, @CurrentUser LoginUser user, HttpServletResponse response) throws IOException{
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"Oppertunity", "editRights") || !dbProfile.hasObjectAccess(user.getUsername(),"Oppertunity", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			dbOppertunityRepository.addLineItem(lineItems, priceBookId, oppertunityId, businessDomainId, user);
			return dbOppertunityLineItem.getLineItemPageByOppertunityId(oppertunityId,PageRequest.of(0,5));
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
	
	
	@PutMapping("lineitem")
	public OppertunityLineItemResponse updateOppertunityLineItem(@Valid @RequestBody OppertunityLineItemRequest lineItem, @RequestParam(name = "priceBookId") long priceBookId,
			@RequestParam(name = "oppertunityId") long oppertunityId, @RequestParam(name = "businessDomainId") int businessDomainId, @CurrentUser LoginUser user, HttpServletResponse response) throws IOException{
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"Oppertunity", "editRights") || !dbProfile.hasObjectAccess(user.getUsername(),"Oppertunity", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			dbOppertunityRepository.updateLineItem(lineItem, priceBookId, oppertunityId, businessDomainId, user);
			return dbOppertunityLineItem.getLineItemById(lineItem.getId());
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
	
	@GetMapping("lineitem/{id}")
	public OppertunityLineItemResponse getOppertunityLineItemById(@CurrentUser LoginUser user,@PathVariable("id")long id){
		return dbOppertunityLineItem.getLineItemById(id);
	}

//	@GetMapping("related/{partCode}/Opportunities")
//	public Page<OppertunityResponse> getProductsByOppertunity(@PathVariable("partCode")String partCode,Pageable page){
//		return dbOppertunityRepository.getProductsByOppertunity(partCode, page);
//	}
	@GetMapping("lineitem")
	public Page<OppertunityLineItemResponse> getOppertunityLineItems(@CurrentUser LoginUser user,@RequestParam(name = "oppertunityId") long oppertunityId,Pageable pageable){
		return dbOppertunityLineItem.getLineItemPageByOppertunityId(oppertunityId, pageable);
	}

	@GetMapping("lineitemList")
	public List<OppertunityLineItemResponse> getLineItemListByOppertunityId(@CurrentUser LoginUser user,@RequestParam(name = "oppertunityId") long oppertunityId){
		return dbOppertunityLineItem.getLineItemListByOppertunityId(oppertunityId);
	}
	
	
	@PutMapping("lineitem/updateall")
	public List<OppertunityLineItemResponse> updateOppertunityLineItemsList(@Valid @RequestBody List<OppertunityLineItemRequest> lineItems, @RequestParam(name = "priceBookId") long priceBookId,
			@RequestParam(name = "oppertunityId") long oppertunityId, @RequestParam(name = "businessDomainId") int businessDomainId, @CurrentUser LoginUser user, HttpServletResponse httpServletResponse) throws IOException{
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"Oppertunity", "editRights") || !dbProfile.hasObjectAccess(user.getUsername(),"Oppertunity", "createRights")) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			dbOppertunityRepository.updateLineItemList(lineItems, priceBookId, oppertunityId, businessDomainId, user);
			return dbOppertunityLineItem.getLineItemListByOppertunityId(oppertunityId);
		}catch(BadRequestException ex) {
			httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
	
	@DeleteMapping("{oppertunityId}")
	public SimpleResponseMessage deleteOppertunity(@PathVariable("oppertunityId")long oppertunityId, @CurrentUser LoginUser user, HttpServletResponse httpServletResponse) throws IOException {
		if(!dbProfile.hasObjectAccess(user.getUsername(),"Oppertunity", "editRights") || !dbProfile.hasObjectAccess(user.getUsername(),"Oppertunity", "createRights")) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			dbOppertunityRepository.deleteOppertunity(oppertunityId,user);
			return new SimpleResponseMessage("deleted Successfully");
		}catch(BadRequestException ex) {
			httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
	}
	
	@DeleteMapping("lineitem/{oppertunityLineItemId}")
	public SimpleResponseMessage deleteOppertunityLineItem(@PathVariable("oppertunityLineItemId")long oppertunityLineItemId, @CurrentUser LoginUser user, HttpServletResponse httpServletResponse) throws IOException {
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"Oppertunity", "editRights") || !dbProfile.hasObjectAccess(user.getUsername(),"Oppertunity", "createRights")) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			dbOppertunityRepository.deleteOppertunityLineItem(oppertunityLineItemId,user);
			return new SimpleResponseMessage("deleted Successfully");
		}catch(BadRequestException ex) {
			httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
	}
	
	@GetMapping("related/quote/{oppertunityId}")
	public Page<QuoteResponse> getAllQuoteByOpportunityId(@CurrentUser LoginUser user, @PathVariable("oppertunityId")long oppertunityId, Pageable pageable){
	return dbQuote.getAllQuoteByOpportunityId(oppertunityId, pageable);
	}
	
	
	@GetMapping("advancesearch")
	public List<com.pratikshat.exicom.model.response.OppertunityResponse> getAdvanceSearchresult(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException{
		
		if(dbProfile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.Oppertunity.class, UserProfileRepository.VIEW_ALL)) {
			return 	dbOppertunityRepository.advanceSearch(request, user);
		}
		
		if(dbProfile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.Oppertunity.class, UserProfileRepository.VIEW)) {
			return 	dbOppertunityRepository.advanceSearch(request,user);
		}
		
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		return null;
		
	}

}
