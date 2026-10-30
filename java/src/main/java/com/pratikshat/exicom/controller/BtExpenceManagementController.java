package com.pratikshat.exicom.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.pratikshat.exicom.entity.BtExpenseManagement;
import com.pratikshat.exicom.entity.BtSiteDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.ExpenseAccountsOnly;
import com.pratikshat.exicom.entity.ExpenseManagement;
import com.pratikshat.exicom.entity.ExpenseManagementExpenseType;
import com.pratikshat.exicom.entity.ExpenseManagementRecentVisit;
import com.pratikshat.exicom.entity.ExpenseManagementStatus;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.LoginUserRoles;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BtExpenseManagementRequest;
import com.pratikshat.exicom.model.request.ExpenseAccountsOnlyRequest;
import com.pratikshat.exicom.model.request.ExpenseManagementRequest;
import com.pratikshat.exicom.model.response.BtExpenseManagementResponse;
import com.pratikshat.exicom.model.response.BusinessAreaResponse;
import com.pratikshat.exicom.model.response.BusinessPlaceResponse;
import com.pratikshat.exicom.model.response.CostCenterResponse;
import com.pratikshat.exicom.model.response.ExpenseManagementResponse;
import com.pratikshat.exicom.model.response.ExpensePurposeResponse;
import com.pratikshat.exicom.model.response.ExpenseStatusResponse;
import com.pratikshat.exicom.model.response.ExpenseTypeResponse;
import com.pratikshat.exicom.model.response.GlCodeResponse;
import com.pratikshat.exicom.model.response.SimpleResponseMessage;
import com.pratikshat.exicom.model.response.StatusMessageResponse;
import com.pratikshat.exicom.repository.BtExpenseManagementCustomRepositoryImpl;
import com.pratikshat.exicom.repository.BtExpenseManagementRepository;
import com.pratikshat.exicom.repository.ExpenseManagementBusinessAreaRepository;
import com.pratikshat.exicom.repository.ExpenseManagementBusinessPlaceRepository;
import com.pratikshat.exicom.repository.ExpenseManagementCostCenterRepository;
import com.pratikshat.exicom.repository.ExpenseManagementExpensePurposeRepository;
import com.pratikshat.exicom.repository.ExpenseManagementExpenseTypeRepository;
import com.pratikshat.exicom.repository.ExpenseManagementRecentVisitRepository;
import com.pratikshat.exicom.repository.ExpenseManagementRepository;
import com.pratikshat.exicom.repository.ExpenseManagementStatusRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtExpenceService;
import com.pratikshat.exicom.services.impl.BtExpenceServiceImpl;

@RestController
@RequestMapping("bt/api/expense")
public class BtExpenceManagementController {

	@Autowired
	ExpenseManagementExpensePurposeRepository btExpensePurposeDB;
	@Autowired
	ExpenseManagementBusinessAreaRepository btBusinessAreaDB;
	@Autowired
	ExpenseManagementBusinessPlaceRepository btBusinessPlaceDB;
	@Autowired
	ExpenseManagementCostCenterRepository btCostCenterDB;
	@Autowired
	ExpenseManagementExpenseTypeRepository btExpenseTypeDb;
	@Autowired
	ExpenseManagementRecentVisitRepository btRecentVisitDB;
	@Autowired
	BtExpenseManagementRepository btExpenseManagementDB;
	@Autowired
	ExpenseManagementStatusRepository btManagementStatusDB;
	@Autowired
	ExpenseManagementStatusRepository dbExpenseManagementStatus;
	@Autowired
	BtExpenceServiceImpl serviceImpl;
	@Autowired
	BtExpenceService expService;
	
	@Autowired
	BtExpenseManagementCustomRepositoryImpl customRepositoryImpl; 

	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	@GetMapping("businessarea/{businessDomainId}")
	public List<Map<String,Object>> getBusinessArea(@PathVariable("businessDomainId") int businessDomainId) throws IOException {
		
		return serviceImpl.getBusinessArea(1);
		
	}

	@GetMapping("businessplace/{businessDomainId}")
	public List<Map<String,Object>> getBusinessPlace(@PathVariable("businessDomainId") int businessDomainId) throws IOException {
		
		return serviceImpl.getBusinessPlace(businessDomainId);
		
	}

	@GetMapping("costcenter/{businessDomainId}")
	public List<Map<String,Object>> getCostCenter(@PathVariable("businessDomainId") int businessDomainId) throws IOException {
		
		return serviceImpl.getCostCenter(1);
		
	}

	@GetMapping("expensepurpose/{businessDomainId}")
	public List<Map<String,Object>> getExpensePurpose(@PathVariable("businessDomainId") int businessDomainId) throws IOException {
		return serviceImpl.getExpensePurpose(1);
		
	}

	@GetMapping("expensetype/{businessDomainId}")
	public List<Map<String,Object>> getExpenseType(@PathVariable("businessDomainId") int businessDomainId) throws IOException {
		
		return serviceImpl.getExpenseType(1);
		
	}

	@GetMapping("status/{businessDomainId}")
	public List<Map<String,Object>> getStatus(@PathVariable("businessDomainId") int businessDomainId) throws IOException {
		
		return serviceImpl.getStatus(businessDomainId);
		
	}
	
	@PostMapping
	public Map<String, Object> createExpense(
			@RequestBody Map<String,Object> map,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)throws IOException, BadRequestException, UnauthoriseException{
		
		System.out.println("map => "+map);
		System.out.println("user =>"+user);
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"BtExpenseManagement", "createRights")) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}

			return serviceImpl.saveObject(map, user);
		}
	
	@PutMapping
	public Map<String, Object> updateExpense(
			@Valid @RequestBody Map<String,Object> map,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)throws IOException, BadRequestException, UnauthoriseException {
		

		System.out.println("map => "+map);
		System.out.println("user =>"+user);
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"BtExpenseManagement", "editRights")) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
			return serviceImpl.updateObject(map, user);
		
	}
	
	
	@GetMapping("getById/{id}")
	public Map<String, Object> getExpenseAccountsOnly(@PathVariable("id") long id,@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)throws IOException, UnauthoriseException, BadRequestException {
		System.out.println("user => "+user.getUsername());
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtExpenseManagement.class, UserProfileRepository.VIEW);
		boolean viewAll = dbProfile.hasObjectAccess(user.getUsername(), BtExpenseManagement.class,UserProfileRepository.VIEW_ALL);
		
		if (view || viewAll) {
			return serviceImpl.getSingle(id, user); 
		} else {
			throw new UnauthoriseException("You are not authorised");
		}

	}
	
	@GetMapping
	public Page<Map<String, Object>> getAll(Pageable page,@CurrentUser LoginUser user,@RequestParam(value="filterparam",required=false)String filterparam) throws UnauthoriseException {
		System.out.println("user roll ==  "+user.getAuthorities());
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtExpenseManagement.class, UserProfileRepository.VIEW);
		boolean viewAll = dbProfile.hasObjectAccess(user.getUsername(), BtExpenseManagement.class,
				UserProfileRepository.VIEW_ALL);
		//test
		Iterator<LoginUserRoles> ite= (Iterator<LoginUserRoles>) user.getAuthorities().iterator();
		String _rl="";
		while(ite.hasNext()) {
			_rl=ite.next().getAuthority();
			break;
		} //
		
		
//		Map<String,String> rlGrp=dbProfile.getAccountAndRoleGroup(user);
//		System.out.println("rlGrp  "+rlGrp);
//		String _rl=rlGrp.get("roleGroup").toString();
		if(viewAll)
		{
System.out.println("view all rl  =>  "+_rl);
			if(_rl.equalsIgnoreCase("COMMERCIAL")||_rl.equalsIgnoreCase("FINANCE")||_rl.equalsIgnoreCase("CUSTOMERCARE")) {
				
				if(filterparam!=null&&filterparam.equalsIgnoreCase("TobeApproved")) {
					System.out.println("users in viewAll  =>  "+dbProfile.getUsersInMainRoleGroup(_rl));
//					return btExpenseManagementDB.getToBeApprovedExpesneByUser(dbProfile.getUsersInMainRoleGroup(_rl), page);
					return btExpenseManagementDB.getToBeApprovedExpesneByUseronRoleBase("ROLE:"+_rl, page);
				}
				if(filterparam!=null&&filterparam.equalsIgnoreCase("Approved")) {
					System.out.println("ncxz");
//					return btExpenseManagementDB.getApprovedExpesneByUser(dbProfile.getUsersInMainRoleGroup(_rl),page);
					return btExpenseManagementDB.getApprovedExpesneByUseronRoleBase("ROLE:"+_rl, page);
				}
				if(filterparam!=null&&filterparam.equalsIgnoreCase("Rejected")) {
					System.out.println("jkdsjk");
//					return btExpenseManagementDB.getRejectedExpesneByUser(dbProfile.getUsersInMainRoleGroup(_rl),page);
					return btExpenseManagementDB.getRejectedExpesneByUserRoleBase("ROLE:"+_rl, page);
				}
				if(filterparam!=null&&filterparam.equalsIgnoreCase("Submitted")) {
					System.out.println("][][dsa");
					return btExpenseManagementDB.getSubmittedExpesneByUser(user.getUsername(),page);
				}
			}
			else {
				System.out.println("users in viewAll in else   =>  "+dbProfile.getUsersInMainRoleGroup(_rl));
				if(filterparam!=null&&filterparam.equalsIgnoreCase("TobeApproved")) {
					System.out.println("][]");
//					return btExpenseManagementDB.getToBeApprovedExpesneByUser(dbProfile.getSameroleUsers(user), page);
					return btExpenseManagementDB.getToBeApprovedExpesneByUseronRoleBase("ROLE:"+_rl, page);
				}
				if(filterparam!=null&&filterparam.equalsIgnoreCase("Approved")) {
					System.out.println("jhkha");
//					return btExpenseManagementDB.getApprovedExpesneByUser(dbProfile.getSameroleUsers(user),page);
					return btExpenseManagementDB.getApprovedExpesneByUseronRoleBase("ROLE:"+_rl, page);
				}
				if(filterparam!=null&&filterparam.equalsIgnoreCase("Rejected")) {
					System.out.println("xmzkm");
//					return btExpenseManagementDB.getRejectedExpesneByUser(dbProfile.getSameroleUsers(user),page);
					return btExpenseManagementDB.getRejectedExpesneByUserRoleBase("ROLE:"+_rl, page);
				}
				if(filterparam!=null&&filterparam.equalsIgnoreCase("Submitted")) {
					System.out.println("mbnbzx");
					return btExpenseManagementDB.getSubmittedExpesneByUser(user.getUsername(),page);
				}
			}
		
		}
		if (view) 
		{
			System.out.println("view  rl  =>  "+_rl);
if(_rl.equalsIgnoreCase("COMMERCIAL")||_rl.equalsIgnoreCase("FINANCE")||_rl.equalsIgnoreCase("CUSTOMERCARE")) {
				
				if(filterparam!=null&&filterparam.equalsIgnoreCase("TobeApproved")) {
					System.out.println("users in view  =>  "+dbProfile.getUsersInMainRoleGroup(_rl));
//					return btExpenseManagementDB.getToBeApprovedExpesneByUser(dbProfile.getUsersInMainRoleGroup(_rl), page);
					return btExpenseManagementDB.getToBeApprovedExpesneByUseronRoleBase("ROLE:"+_rl, page);
				}
				if(filterparam!=null&&filterparam.equalsIgnoreCase("Approved")) {
					System.out.println("nc");
//					return btExpenseManagementDB.getApprovedExpesneByUser(dbProfile.getUsersInMainRoleGroup(_rl),page);
					return btExpenseManagementDB.getApprovedExpesneByUseronRoleBase("ROLE:"+_rl, page);
				}
				if(filterparam!=null&&filterparam.equalsIgnoreCase("Rejected")) {
					System.out.println("nsan");
//					return btExpenseManagementDB.getRejectedExpesneByUser(dbProfile.getUsersInMainRoleGroup(_rl),page);
					return btExpenseManagementDB.getRejectedExpesneByUserRoleBase("ROLE:"+_rl, page);
				}
				if(filterparam!=null&&filterparam.equalsIgnoreCase("Submitted")) {
					System.out.println("hgashgdg");
					return btExpenseManagementDB.getSubmittedExpesneByUser(user.getUsername(),page);
				}
			}
			else {
				if(filterparam!=null&&filterparam.equalsIgnoreCase("TobeApproved")) {
					System.out.println("users in view in else  =>  "+dbProfile.getUsersInMainRoleGroup(_rl));
					System.out.println("db value  "+btExpenseManagementDB.getToBeApprovedExpesneByUser(dbProfile.getSameroleUsers(user), page));
//					return btExpenseManagementDB.getToBeApprovedExpesneByUser(dbProfile.getSameroleUsers(user), page);
					return btExpenseManagementDB.getToBeApprovedExpesneByUseronRoleBase("ROLE:"+_rl, page);
				}
				if(filterparam!=null&&filterparam.equalsIgnoreCase("Approved")) {
					System.out.println("k;kzk");
					System.out.println("bbsd   "+dbProfile.getSameroleUsers(user));
//					return btExpenseManagementDB.getApprovedExpesneByUser(dbProfile.getSameroleUsers(user),page);
					return btExpenseManagementDB.getApprovedExpesneByUseronRoleBase("ROLE:"+_rl, page);
				}
				if(filterparam!=null&&filterparam.equalsIgnoreCase("Rejected")) {
					System.out.println("vgads");
//					return btExpenseManagementDB.getRejectedExpesneByUser(dbProfile.getSameroleUsers(user),page);
					return btExpenseManagementDB.getRejectedExpesneByUserRoleBase("ROLE:"+_rl, page);
				}
				if(filterparam!=null&&filterparam.equalsIgnoreCase("Submitted")) {
					System.out.println("hcjhzjhc");
					return btExpenseManagementDB.getSubmittedExpesneByUser(user.getUsername(),page);
				}
			}
		}
		return Page.empty(); 
		
			
	}

	
	@GetMapping("searchForList")
	public List<Map<String, Object>> searchForList(@CurrentUser LoginUser user,
			@RequestParam("searchKey") String searchKey, Pageable page) throws BadRequestException, UnauthoriseException {
		System.out.println(searchKey);
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtExpenseManagement.class, UserProfileRepository.VIEW);
		boolean viewAll = dbProfile.hasObjectAccess(user.getUsername(), BtExpenseManagement.class,
				UserProfileRepository.VIEW_ALL);
		
		if (view || viewAll) {
		if(searchKey!=null&& (!searchKey.equalsIgnoreCase("")))
		{
			return serviceImpl.searchForList(searchKey, user);
		}
		else
		{
			throw new BadRequestException("request parameter is null or empty");	
		}
		} else {
			throw new UnauthoriseException("You are not authorised");
		}
		
		
		

		
	}
	
	
	
	@GetMapping("advancesearch")
	public List<Map<String, Object>> getAdvanceSearchresult(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException, BadRequestException, UnauthoriseException{
		if(dbProfile.hasObjectAccess(user.getUsername(), BtExpenseManagement.class, UserProfileRepository.VIEW_ALL)) {
			return expService.advanceSearch(request,user ,null);
		}
		
		if(dbProfile.hasObjectAccess(user.getUsername(), BtExpenseManagement.class, UserProfileRepository.VIEW)) {
			List<String> usrs = dbProfile.getListOfUserAndChildByUser(user.getUsername());
			if (usrs != null && usrs.size() > 0) {
			return expService.advanceSearch(request,user ,usrs);
			}
		}
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		return null;
	}
	
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteExpense(
			@PathVariable("id") long id,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)throws IOException {
		StatusMessageResponse response = new StatusMessageResponse();
		if(!dbProfile.hasObjectAccess(user.getUsername(),"BtExpenseManagement", "deleteRights")) {
			response.setMessage("You are not authorised");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(response);
		}
			BtExpenseManagement expenseManagement = btExpenseManagementDB.getByIds(id);
			if (expenseManagement==null) {
				response.setMessage("Expense does not exist");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(response);
			} else {
				if(!expenseManagement.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
					
					response.setMessage("You are not authorised to delete this record");
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(response);
				}
				int statusId = expenseManagement.getStatus().getStatusId();
				List<Integer> list = new ArrayList<>();
				list.add(1);	// Draft from Battery Division 
				list.add(7);	// Rejected from Battery Division 
				list.add(8);	// Draft from DCT Division 
				list.add(14);	// Rejected from DCT Division
				list.add(15);	// Draft from EV Division
				list.add(16);	// Rejected by Circle Manager from Battery Division
				list.add(17);	// Rejected by Zonal Manager from Battery Division
				list.add(18);	// Rejected by HOD from Battery Division
				list.add(19);	// Rejected by Finance from Battery Division
				list.add(20);	// Rejected by Circle Manager from DCT Division
				list.add(21);	// Rejected by Zonal Manager from DCT Division
				list.add(22);	// Rejected by HOD from DCT Division
				list.add(23);	// Rejected by Finance from DCT Division
				list.add(25);   // Rejected by CC Head
				if(!list.contains(statusId)) {
					response.setMessage("You can't able to delete this record");
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(response);
				}
				expenseManagement.setDeleted(true);
				expenseManagement.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
				expenseManagement.setLastModifiedDate(new Date());
				btExpenseManagementDB.save(expenseManagement);
				System.out.println("deleted");
				response.setMessage("Expesne Deleted successfully");
				response.setStatus(HttpServletResponse.SC_OK);
				return ResponseEntity.status(HttpServletResponse.SC_OK).body(response);
			}
		
	}
	
	
	
	
	@PostMapping("accountsonly")
	public Map<String, Object> btExpenseAccountsOnly(
			@Valid @RequestBody Map<String, Object> expenseAccountsOnlyRequest,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)throws IOException, BadRequestException, UnauthoriseException {

		if(!dbProfile.hasObjectAccess(user.getUsername(),"BtExpenseManagement", "createRights")) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		return serviceImpl.saveUpdateExpenseAccountsOnly(expenseAccountsOnlyRequest, user);
	
	}
	
	
	@GetMapping("accountsonly/{btExpenseManagementId}")
	public Map<String, Object> getBtExpenseAccountsOnly(@PathVariable("btExpenseManagementId") long btExpenseManagementId,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)throws IOException {
		if(!dbProfile.hasObjectAccess(user.getUsername(),"BtExpenseAccountsOnly", "createRights") || !dbProfile.hasObjectAccess(user.getUsername(),"BtExpenseAccountsOnly", "editRights")) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			
			if(btExpenseManagementId==0) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Expense Id 0");
				return null;
			}else {
				BtExpenseManagement expenseManagement = btExpenseManagementDB.getById(btExpenseManagementId);
				if(expenseManagement==null) {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Expense does not exist");
					return null;	
				}
				
				return btExpenseManagementDB.findByExpense(expenseManagement);
			}
		} catch (Exception ex) {
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
	
	@GetMapping("glcodesummary/{btExpenseManagementId}")
	public List<GlCodeResponse> getGLCodeSummary(@PathVariable("btExpenseManagementId") long btExpenseManagementId,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws IOException {
		if (dbProfile.hasObjectAccess(user.getUsername(), "BtExpenseManagement", "viewAllRights")
				|| dbProfile.hasObjectAccess(user.getUsername(), "BtExpenseManagement", "viewRights")) {
			try {
				if (btExpenseManagementId == 0) {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Expense does not exist");
					return null;
				} else {
					return serviceImpl.getGLCodeSummary(btExpenseManagementId, user);
				}
			} catch (BadRequestException ex) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
				return null;
			} catch (UnauthoriseException ex) {
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
				return null;
			} catch (Exception ex) {
				httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
				return null;
			}
		}
		return new ArrayList<>();
	}
	
//	@GetMapping("recentvisit")
//	public Page<ExpenseManagementResponse> getRecentvisit(@CurrentUser LoginUser userDetails, Pageable page) {
//		return dbExpenseManagement.getRecentVisitByVisitor(userDetails.getUsername(), page);
//	}
	
	@GetMapping("submitforapproval")
	public SimpleResponseMessage submitForApproval(@RequestParam("btExpenseManagementId")long btExpenseManagementId, @RequestParam("businessDoaminId")int businessDoaminId, @RequestParam(value="remark",required=false)String remark, @CurrentUser LoginUser user, HttpServletResponse httpServletResponse) throws IOException{
		try {
			serviceImpl.submitForApproval(btExpenseManagementId,remark, businessDoaminId, user);
			return new SimpleResponseMessage("submitted Successfully");
		} catch(BadRequestException ex) {
			httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} 
	}
	
	//value = 
	@GetMapping("pdf")
	public ResponseEntity<?> createPdf(@RequestParam("btExpenseManagementId")long btExpenseManagementId,HttpServletResponse httpServletResponse,@CurrentUser LoginUser user) throws IOException {
		try {
			
			BtExpenseManagementResponse response = btExpenseManagementDB.getByBtExpenseId(btExpenseManagementId);
			if(response!=null) {
				
				if(!response.getOwnerEmailId().equalsIgnoreCase(user.getUsername())) {
					httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorised");
					return null;
				}
				
				ByteArrayInputStream bis = serviceImpl.createPdf(user,response);
				
				
				
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Disposition", "inline; filename="+response.getManualId()+".pdf");
				

				return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bis));
			}else {
				httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Expense does not found");
				return null;
			}
		} catch(BadRequestException ex) {
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
}
