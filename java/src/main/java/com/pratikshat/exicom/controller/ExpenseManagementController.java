package com.pratikshat.exicom.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
import com.pratikshat.exicom.entity.CircleDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.ExpenseAccountsOnly;
import com.pratikshat.exicom.entity.ExpenseManagement;
import com.pratikshat.exicom.entity.ExpenseManagementBusinessArea;
import com.pratikshat.exicom.entity.ExpenseManagementBusinessPlace;
import com.pratikshat.exicom.entity.ExpenseManagementRecentVisit;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ServiceAppointment;
import com.pratikshat.exicom.entity.ToBeApproved;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.ExpenseAccountsOnlyRequest;
import com.pratikshat.exicom.model.request.ExpenseManagementRequest;
import com.pratikshat.exicom.model.response.BusinessAreaResponse;
import com.pratikshat.exicom.model.response.BusinessPlaceResponse;
import com.pratikshat.exicom.model.response.CircleDetailsResponse;
import com.pratikshat.exicom.model.response.CostCenterResponse;
import com.pratikshat.exicom.model.response.ExpenseManagementResponse;
import com.pratikshat.exicom.model.response.ExpensePurposeResponse;
import com.pratikshat.exicom.model.response.ExpenseStatusResponse;
import com.pratikshat.exicom.model.response.ExpenseTypeResponse;
import com.pratikshat.exicom.model.response.GlCodeResponse;
import com.pratikshat.exicom.model.response.QuoteResponse;
import com.pratikshat.exicom.model.response.SimpleResponseMessage;
import com.pratikshat.exicom.model.response.StatusMessageResponse;
import com.pratikshat.exicom.repository.ExpenseManagementBusinessAreaRepository;
import com.pratikshat.exicom.repository.ExpenseManagementBusinessPlaceRepository;
import com.pratikshat.exicom.repository.ExpenseManagementCostCenterRepository;
import com.pratikshat.exicom.repository.ExpenseManagementExpensePurposeRepository;
import com.pratikshat.exicom.repository.ExpenseManagementExpenseTypeRepository;
import com.pratikshat.exicom.repository.ExpenseManagementRecentVisitRepository;
import com.pratikshat.exicom.repository.ExpenseManagementRepository;
import com.pratikshat.exicom.repository.ExpenseManagementStatusRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;

@RestController
@RequestMapping("api/expensemanagement")
public class ExpenseManagementController {

	@Autowired
	ExpenseManagementBusinessAreaRepository dbExpenseManagementBusinessArea;

	@Autowired
	ExpenseManagementBusinessPlaceRepository dbExpenseManagementBusinessPlace;

	@Autowired
	ExpenseManagementCostCenterRepository dbExpenseManagementCostCenter;

	@Autowired
	ExpenseManagementExpensePurposeRepository dbExpenseManagementExpensePurpose;

	@Autowired
	ExpenseManagementExpenseTypeRepository dbExpenseManagementExpenseType;

	@Autowired
	ExpenseManagementStatusRepository dbExpenseManagementStatus;

	@Autowired
	ExpenseManagementRepository dbExpenseManagement;

	@Autowired
	ExpenseManagementRecentVisitRepository dbExpenseRecentVisit;
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	

	@GetMapping("businessarea/{division}")
	public List<BusinessAreaResponse> getBusinessArea(@PathVariable("division") int division) {
		return dbExpenseManagementBusinessArea.getByBusinessDomain(division);
	}

	@GetMapping("businessplace/{division}")
	public List<BusinessPlaceResponse> getBusinessPlace(@PathVariable("division") int division) {
		return dbExpenseManagementBusinessPlace.getByBusinessDomain(division);
	}

	@GetMapping("costcenter/{division}")
	public List<CostCenterResponse> getCostCenter(@PathVariable("division") int division) {
		return dbExpenseManagementCostCenter.getByBusinessDomain(division);
	}

	@GetMapping("expensepurpose/{division}")
	public List<ExpensePurposeResponse> getExpensePurpose(@PathVariable("division") int division) {
		return dbExpenseManagementExpensePurpose.getByBusinessDomain(division);
	}

	@GetMapping("expensetype/{division}")
	public List<ExpenseTypeResponse> getExpenseType(@PathVariable("division") int division) {
		return dbExpenseManagementExpenseType.getByBusinessDomain(division);
	}

	@GetMapping("status/{division}")
	public List<ExpenseStatusResponse> getStatus(@PathVariable("division") int division) {
		return dbExpenseManagementStatus.getByBusinessDomain(division);
	}

	@GetMapping
	public Page<ExpenseManagementResponse> getAll(Pageable page,@CurrentUser LoginUser user,@RequestParam(value="filterparam",required=false)String filterparam) {
		
		Map<String,String> rlGrp=dbProfile.getAccountAndRoleGroup(user);
		String _rl=rlGrp.get("roleGroup").toString();
		
		if(dbProfile.hasObjectAccess(user.getUsername(), ExpenseManagement.class, UserProfileRepository.VIEW_ALL)) {
			
			if(_rl.equalsIgnoreCase("COMMERCIAL")||_rl.equalsIgnoreCase("FINANCE")||_rl.equalsIgnoreCase("CUSTOMERCARE")||_rl.equalsIgnoreCase("DCT_MAIN_STORE")) {
				
				if(filterparam!=null&&filterparam.equalsIgnoreCase("To be Approved")) {
					return dbExpenseManagement.getToBeApprovedExpesneByUser(dbProfile.getUsersInMainRoleGroup(_rl), page);
				}
				if(filterparam!=null&&filterparam.equalsIgnoreCase("Approved")) {
					return dbExpenseManagement.getApprovedExpesneByUser(dbProfile.getUsersInMainRoleGroup(_rl),page);
				}
				if(filterparam!=null&&filterparam.equalsIgnoreCase("Rejected")) {
					return dbExpenseManagement.getRejectedExpesneByUser(dbProfile.getUsersInMainRoleGroup(_rl),page);
				}
				if(filterparam!=null&&filterparam.equalsIgnoreCase("Submitted")) {
					return dbExpenseManagement.getSubmittedExpesneByUser(user.getUsername(),page);
				}
			}
			else {
				if(filterparam!=null&&filterparam.equalsIgnoreCase("To be Approved")) {
					return dbExpenseManagement.getToBeApprovedExpesneByUser(dbProfile.getSameroleUsers(user), page);
				}
				if(filterparam!=null&&filterparam.equalsIgnoreCase("Approved")) {
					return dbExpenseManagement.getApprovedExpesneByUser(dbProfile.getSameroleUsers(user),page);
				}
				if(filterparam!=null&&filterparam.equalsIgnoreCase("Rejected")) {
					return dbExpenseManagement.getRejectedExpesneByUser(dbProfile.getSameroleUsers(user),page);
				}
				if(filterparam!=null&&filterparam.equalsIgnoreCase("Submitted")) {
					return dbExpenseManagement.getSubmittedExpesneByUser(user.getUsername(),page);
				}
			}
		}
		if(dbProfile.hasObjectAccess(user.getUsername(), ExpenseManagement.class, UserProfileRepository.VIEW)) {
				if(_rl.equalsIgnoreCase("COMMERCIAL")||_rl.equalsIgnoreCase("FINANCE")||_rl.equalsIgnoreCase("CUSTOMERCARE")||_rl.equalsIgnoreCase("DCT_MAIN_STORE")) {
				
				if(filterparam!=null&&filterparam.equalsIgnoreCase("To be Approved")) {
					return dbExpenseManagement.getToBeApprovedExpesneByUser(dbProfile.getUsersInMainRoleGroup(_rl), page);
				}
				if(filterparam!=null&&filterparam.equalsIgnoreCase("Approved")) {
					return dbExpenseManagement.getApprovedExpesneByUser(dbProfile.getUsersInMainRoleGroup(_rl),page);
				}
				if(filterparam!=null&&filterparam.equalsIgnoreCase("Rejected")) {
					return dbExpenseManagement.getRejectedExpesneByUser(dbProfile.getUsersInMainRoleGroup(_rl),page);
				}
				if(filterparam!=null&&filterparam.equalsIgnoreCase("Submitted")) {
					return dbExpenseManagement.getSubmittedExpesneByUser(user.getUsername(),page);
				}
			}
			else {
				if(filterparam!=null&&filterparam.equalsIgnoreCase("To be Approved")) {
					return dbExpenseManagement.getToBeApprovedExpesneByUser(dbProfile.getSameroleUsers(user), page);
				}
				if(filterparam!=null&&filterparam.equalsIgnoreCase("Approved")) {
					return dbExpenseManagement.getApprovedExpesneByUser(dbProfile.getSameroleUsers(user),page);
				}
				if(filterparam!=null&&filterparam.equalsIgnoreCase("Rejected")) {
					return dbExpenseManagement.getRejectedExpesneByUser(dbProfile.getSameroleUsers(user),page);
				}
				if(filterparam!=null&&filterparam.equalsIgnoreCase("Submitted")) {
					return dbExpenseManagement.getSubmittedExpesneByUser(user.getUsername(),page);
				}
			}
		}
		return Page.empty();
	}

	@GetMapping("{id}")
	public ExpenseManagementResponse getByCaseId(@CurrentUser com.pratikshat.exicom.entity.LoginUser user,
			@PathVariable("id") long id) {
		
		if(dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewAllRights") || dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewRights")) {
			try {
				ExpenseManagementRecentVisit receint = dbExpenseRecentVisit.findByExpenseManagementIdAndVisitorId(id,
						user.getUsername());
				if (receint != null) {
					receint.setVisitedDate(new Date());
				} else {
					receint = new ExpenseManagementRecentVisit(id, user.getUsername());
				}
				dbExpenseRecentVisit.save(receint);
			}
			catch (Exception e) {
				
			}
			ExpenseManagementResponse response = dbExpenseManagement.getById(id);
			return response;
		}
		
		return new ExpenseManagementResponse();
	}

	@PostMapping
	public ExpenseManagementResponse createExpense(
			@Valid @RequestBody ExpenseManagementRequest expenseManagementRequest,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)throws IOException {
		if(!dbProfile.hasObjectAccess(user.getUsername(),"ExpenseManagement", "createRights")) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			ExpenseManagementRequest request = dbExpenseManagement.saveExpense(expenseManagementRequest, user);
			return dbExpenseManagement.getById(request.getExpenseManagementId());
		} catch(BadRequestException ex) {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}

	@PutMapping
	public ExpenseManagementResponse updateExpense(
			@Valid @RequestBody ExpenseManagementRequest expenseManagementRequest,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)throws IOException {
		if(!dbProfile.hasObjectAccess(user.getUsername(),"ExpenseManagement", "editRights")) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			ExpenseManagement expenseManagement = dbExpenseManagement.findByExpenseManagementId(expenseManagementRequest.getExpenseManagementId());
			if (expenseManagement == null) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Expense does not exist");
				return null;
			} else {
				
//				if(!expenseManagement.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
//					httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised to edit this record");
//					return null;
//				}
				
				ExpenseManagementRequest request = dbExpenseManagement.updateExpense(expenseManagementRequest, user);
				return dbExpenseManagement.getById(request.getExpenseManagementId());
			}
		} catch(BadRequestException ex) {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}

	@GetMapping("listbystatus")
	public Page<ExpenseManagementResponse> getExpenseByStatus(@CurrentUser LoginUser user,
			@RequestParam(name = "status") String status, Pageable page) {
		
		
		Map<String,String> rlGrp=dbProfile.getAccountAndRoleGroup(user);
		String _rl=rlGrp.get("roleGroup").toString();
		
		if(dbProfile.hasObjectAccess(user.getUsername(), ExpenseManagement.class, UserProfileRepository.VIEW_ALL)) {
			
			if(_rl.equalsIgnoreCase("COMMERCIAL")||_rl.equalsIgnoreCase("FINANCE")||_rl.equalsIgnoreCase("CUSTOMERCARE")||_rl.equalsIgnoreCase("DCT_MAIN_STORE")) {
				
				if(status!=null&&status.equalsIgnoreCase("To be Approved")) {
					return dbExpenseManagement.getToBeApprovedExpesneByUser(dbProfile.getUsersInMainRoleGroup(_rl), page);
				}
				if(status!=null&&status.equalsIgnoreCase("Approved")) {
					return dbExpenseManagement.getApprovedExpesneByUser(dbProfile.getUsersInMainRoleGroup(_rl),page);
				}
				if(status!=null&&status.equalsIgnoreCase("Rejected")) {
					return dbExpenseManagement.getRejectedExpesneByUser(dbProfile.getUsersInMainRoleGroup(_rl),page);
				}
				if(status!=null&&status.equalsIgnoreCase("Submitted")) {
					return dbExpenseManagement.getSubmittedExpesneByUser(user.getUsername(),page);
				}
			}
			else {
				if(status!=null&&status.equalsIgnoreCase("To be Approved")) {
					return dbExpenseManagement.getToBeApprovedExpesneByUser(dbProfile.getSameroleUsers(user), page);
				}
				if(status!=null&&status.equalsIgnoreCase("Approved")) {
					return dbExpenseManagement.getApprovedExpesneByUser(dbProfile.getSameroleUsers(user),page);
				}
				if(status!=null&&status.equalsIgnoreCase("Rejected")) {
					return dbExpenseManagement.getRejectedExpesneByUser(dbProfile.getSameroleUsers(user),page);
				}
				if(status!=null&&status.equalsIgnoreCase("Submitted")) {
					return dbExpenseManagement.getSubmittedExpesneByUser(user.getUsername(),page);
				}
			}
		}
		if(dbProfile.hasObjectAccess(user.getUsername(), ExpenseManagement.class, UserProfileRepository.VIEW)) {
				if(_rl.equalsIgnoreCase("COMMERCIAL")||_rl.equalsIgnoreCase("FINANCE")||_rl.equalsIgnoreCase("CUSTOMERCARE")||_rl.equalsIgnoreCase("DCT_MAIN_STORE")) {
				
				if(status!=null&&status.equalsIgnoreCase("To be Approved")) {
					return dbExpenseManagement.getToBeApprovedExpesneByUser(dbProfile.getUsersInMainRoleGroup(_rl), page);
				}
				if(status!=null&&status.equalsIgnoreCase("Approved")) {
					return dbExpenseManagement.getApprovedExpesneByUser(dbProfile.getUsersInMainRoleGroup(_rl),page);
				}
				if(status!=null&&status.equalsIgnoreCase("Rejected")) {
					return dbExpenseManagement.getRejectedExpesneByUser(dbProfile.getUsersInMainRoleGroup(_rl),page);
				}
				if(status!=null&&status.equalsIgnoreCase("Submitted")) {
					return dbExpenseManagement.getSubmittedExpesneByUser(user.getUsername(),page);
				}
			}
			else {
				if(status!=null&&status.equalsIgnoreCase("To be Approved")) {
					return dbExpenseManagement.getToBeApprovedExpesneByUser(dbProfile.getSameroleUsers(user), page);
				}
				if(status!=null&&status.equalsIgnoreCase("Approved")) {
					return dbExpenseManagement.getApprovedExpesneByUser(dbProfile.getSameroleUsers(user),page);
				}
				if(status!=null&&status.equalsIgnoreCase("Rejected")) {
					return dbExpenseManagement.getRejectedExpesneByUser(dbProfile.getSameroleUsers(user),page);
				}
				if(status!=null&&status.equalsIgnoreCase("Submitted")) {
					return dbExpenseManagement.getSubmittedExpesneByUser(user.getUsername(),page);
				}
			}
		}
		return Page.empty();
		
//		List<String> list = new ArrayList<>();
//		if(status.equalsIgnoreCase("Rejected")) {
//			
//			list.add("Rejected by Circle Manager");
//			list.add("Rejected by Zonal Manager");
//			list.add("Rejected by HOD");
//			list.add("Rejected by Finance");
//			list.add("Rejected");
//			
//		}if(status.equalsIgnoreCase("Approved")){
//			list.add("Approved by Circle Manager");
//			list.add("Approved by Zonal Manager");
//			list.add("Approved by HOD");
//			list.add("Approved by Finance");
//		}else {
//			list.add(status);
//		}
//		
//		if(dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewAllRights")) {
//			
//			return dbExpenseManagement.getByStatus(list, page);
//			
//		}
//		if(dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewRights")) {
//			List<String> userList=new ArrayList<String>();
//			userList=dbProfile.getListOfUserAndChildByUser(user.getUsername());
//			List<com.pratikshat.exicom.entity.EmployeeDetails> empDetails=new ArrayList<com.pratikshat.exicom.entity.EmployeeDetails>();
//			for(String u:userList) {
//				empDetails.add(new com.pratikshat.exicom.entity.EmployeeDetails(u));
//			}
//			
//			return dbExpenseManagement.getByStatus(empDetails,list, page);
//		}
//		return Page.empty();
		
	}

	@GetMapping("recentvisit")
	public Page<ExpenseManagementResponse> getRecentvisit(@CurrentUser LoginUser userDetails, Pageable page) {
		return dbExpenseManagement.getRecentVisitByVisitor(userDetails.getUsername(), page);
	}


	@PostMapping("submitforapproval")
	public SimpleResponseMessage submitForApproval(@RequestParam("expenseManagementId")long expenseManagementId, @RequestParam("businessDoaminId")int businessDoaminId, @RequestParam(value="remark",required=false)String remark, @CurrentUser LoginUser user, HttpServletResponse httpServletResponse) throws IOException{
		try {
			dbExpenseManagement.submitForApproval(expenseManagementId,remark, businessDoaminId, user);
			return new SimpleResponseMessage("submitted Successfully");
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
	
	
	@GetMapping(value = "/pdf")
	public ResponseEntity<?> createPdf(@RequestParam("expenseManagementId")long expenseManagementId,HttpServletResponse httpServletResponse,@CurrentUser LoginUser user) throws IOException {
		try {
			
			ExpenseManagementResponse response = dbExpenseManagement.getById(expenseManagementId);
			if(response!=null) {
				
				if(!response.getOwnerEmailId().equalsIgnoreCase(user.getUsername())) {
					httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorised");
					return null;
				}
				
				ByteArrayInputStream bis = dbExpenseManagement.createPdf(user,response);
				
				
				
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Disposition", "inline; filename="+response.getExpenseManagementManualId()+".pdf");
				

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
	
	@GetMapping("glcodesummary/{expenseManagementId}")
	public List<GlCodeResponse> getGLCodeSummary(@PathVariable("expenseManagementId") long expenseManagementId,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws IOException {
		if (dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewAllRights")
				|| dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewRights")) {
			try {
				if (expenseManagementId == 0) {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Expense does not exist");
					return null;
				} else {
					return dbExpenseManagement.getGLCodeSummary(expenseManagementId, user);
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
	
	
	@PostMapping("accountsonly")
	public ExpenseManagementResponse expenseAccountsOnly(
			@Valid @RequestBody ExpenseAccountsOnlyRequest expenseAccountsOnlyRequest,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)throws IOException {
		if(!dbProfile.hasObjectAccess(user.getUsername(),"ExpenseAccountsOnly", "createRights") || !dbProfile.hasObjectAccess(user.getUsername(),"ExpenseAccountsOnly", "editRights")) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			
			if(expenseAccountsOnlyRequest.getExpenseManagementId()==0) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Expense Id 0");
				return null;
			}else if(expenseAccountsOnlyRequest.getBusinessAreaId()==0) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Business Area Id 0");
				return null;
			}else if(expenseAccountsOnlyRequest.getBusinessPlaceId()==0) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Business Place Id 0");
				return null;
			}else if(expenseAccountsOnlyRequest.getDocumentDate()==null) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Document Date is null");
				return null;
			}else {
				ExpenseManagement expenseManagement = dbExpenseManagement.findByExpenseManagementId(expenseAccountsOnlyRequest.getExpenseManagementId());
				if(expenseManagement==null) {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Expense does not exist");
					return null;	
				}
				
				if(expenseManagement.isPost()) {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "You can't edit this record");
					return null;
				}
				
				ExpenseManagementBusinessArea businessArea= dbExpenseManagementBusinessArea.findByBusinessAreaId(expenseAccountsOnlyRequest.getBusinessAreaId());
				if(businessArea==null) {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Business Area does not exist");
					return null;	
				}
				
				ExpenseManagementBusinessPlace businessPlace = dbExpenseManagementBusinessPlace.findByBusinessPlaceId(expenseAccountsOnlyRequest.getBusinessPlaceId());
				if(businessPlace==null) {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Business Place does not exist");
					return null;
				}
				
				ExpenseAccountsOnlyRequest accountsOnly = dbExpenseManagement.saveUpdateExpenseAccountsOnly(expenseAccountsOnlyRequest, user);
				
				return dbExpenseManagement.getById(accountsOnly.getExpenseManagementId());
			}
		}catch (BadRequestException ex) {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch (UnauthoriseException ex) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
	
	
	
	@GetMapping("accountsonly/{expenseManagementId}")
	public ExpenseAccountsOnly getExpenseAccountsOnly(
			@PathVariable("expenseManagementId") long expenseManagementId,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)throws IOException {
		if(!dbProfile.hasObjectAccess(user.getUsername(),"ExpenseAccountsOnly", "createRights") || !dbProfile.hasObjectAccess(user.getUsername(),"ExpenseAccountsOnly", "editRights")) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			
			if(expenseManagementId==0) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Expense Id 0");
				return null;
			}else {
				ExpenseManagement expenseManagement = dbExpenseManagement.findByExpenseManagementId(expenseManagementId);
				if(expenseManagement==null) {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Expense does not exist");
					return null;	
				}
				
				return dbExpenseManagement.findByExpense(expenseManagement);
			}
		} catch (Exception ex) {
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
	
	
	@DeleteMapping("{expenseManagementId}")
	public ResponseEntity<?> deleteExpense(
			@PathVariable("expenseManagementId") long expenseManagementId,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)throws IOException {
		StatusMessageResponse response = new StatusMessageResponse();
		if(!dbProfile.hasObjectAccess(user.getUsername(),"ExpenseManagement", "editRights")) {
			response.setMessage("You are not authorised");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(response);
		}
		try {
			ExpenseManagement expenseManagement = dbExpenseManagement.findByExpenseManagementId(expenseManagementId);
			if (expenseManagement == null) {
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
				if(!list.contains(statusId)) {
					response.setMessage("You can't able to delete this record");
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(response);
				}
				
				expenseManagement.setDeleted(true);
				expenseManagement.setModifiedBy(new EmployeeDetails(user.getUsername()));
				expenseManagement.setModifiedDate(new Date());
				dbExpenseManagement.save(expenseManagement);
				
				response.setMessage("Expesne Deleted successfully");
				response.setStatus(HttpServletResponse.SC_OK);
				return ResponseEntity.status(HttpServletResponse.SC_OK).body(response);
			}
		}catch (Exception ex) {
			response.setMessage(ex.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	
	@GetMapping("advancesearch")
	public List<ExpenseManagementResponse> getAdvanceSearchresult(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException{
		if(dbProfile.hasObjectAccess(user.getUsername(), ExpenseManagement.class, UserProfileRepository.VIEW_ALL)) {
			return dbExpenseManagement.advanceSearch(request, user, null);
		}
		
		if(dbProfile.hasObjectAccess(user.getUsername(), ExpenseManagement.class, UserProfileRepository.VIEW)) {
			return dbExpenseManagement.advanceSearch(request, user, dbProfile.getListOfUserAndChildByUser(user.getUsername()));
		}
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		return null;
	}
	
	@GetMapping("status/forlist")
	public java.util.List<String> getExpenseStatusList() {
		return dbExpenseManagementStatus.getExpenseStatusList();
	}
}
