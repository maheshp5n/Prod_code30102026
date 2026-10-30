
package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.pratikshat.exicom.entity.BtApproval;
import com.pratikshat.exicom.entity.BtApprovalStapes;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.ApprovalRuleRequest;
import com.pratikshat.exicom.model.request.ApprovalStepRequest;
import com.pratikshat.exicom.model.response.ApprovalResponse;
import com.pratikshat.exicom.model.response.ApprovalStepsResponse;
import com.pratikshat.exicom.model.response.SimpleResponseMessage;
import com.pratikshat.exicom.repository.ApprovalRepository;
import com.pratikshat.exicom.repository.ApprovalStepsRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtApprovalService;
import com.pratikshat.exicom.services.BtApprovalStapesService;

@RestController
@RequestMapping("bt/api/approval")
public class BtApprovalController {
	@Autowired
	BtApprovalService dbApproval;
	
	@Autowired
	BtApprovalStapesService dbApprovalStep;

	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;

	@GetMapping
	public Page<Map<String,Object>> getPage(Pageable page,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return dbApproval.getPage(page, user);
	}

	@GetMapping("{approvalId}")
	public Map<String,Object> getSingleByApprovalId(
			@PathVariable("approvalId") Long approvalId, @CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException {
		
		return dbApproval.getSingle(approvalId, user);
	}

	@PostMapping
	public Map<String,Object> createApproval(@RequestBody Map<String,Object> request, @CurrentUser LoginUser user,
			HttpServletResponse httpServletResponse) throws IOException, BadRequestException, UnauthoriseException {

		if (!dbProfile.hasObjectAccess(user.getUsername(), BtApproval.class, UserProfileRepository.CREATE)) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		return dbApproval.saveObject(request, user);
		
	}

	@PutMapping("{id}")
	public Map<String,Object> updateApproval(@RequestBody Map<String,Object> request, @CurrentUser LoginUser user,
			HttpServletResponse httpServletResponse) throws IOException, BadRequestException, UnauthoriseException {

		if (!dbProfile.hasObjectAccess(user.getUsername(),BtApproval.class, UserProfileRepository.EDIT)) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}

		return dbApproval.updateObject(request, user);
	}

	@PostMapping("{id}/stape")
	public Map<String,Object> createApprovalStep(@RequestBody Map<String,Object> request, @CurrentUser LoginUser user,
			HttpServletResponse httpServletResponse) throws IOException, BadRequestException, UnauthoriseException {

		if (!dbProfile.hasObjectAccess(user.getUsername(), BtApprovalStapes.class,UserProfileRepository.CREATE)) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		return dbApprovalStep.saveObject(request, user);
	}

	@PutMapping("{id}/stape/{approvalstapeid}")
	public Map<String,Object> updateApprovalStep(@PathVariable("id")Long approvalId,@PathVariable("approvalstapeid")Long approvalStapeId,@RequestBody Map<String,Object> request, @CurrentUser LoginUser user,
			HttpServletResponse httpServletResponse) throws IOException, BadRequestException, UnauthoriseException {

		if (!dbProfile.hasObjectAccess(user.getUsername(),BtApprovalStapes.class,UserProfileRepository.EDIT)) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}

		return dbApprovalStep.updateObject(request, user);
		
	}

	
	@DeleteMapping("{id}/stape/{approvalstapeid}")
	public SimpleResponseMessage updateApprovalStep(@PathVariable("approvalstapeid") long id, @CurrentUser LoginUser user,
			HttpServletResponse httpServletResponse) throws IOException {
		
		if (!dbProfile.hasObjectAccess(user.getUsername(), BtApprovalStapes.class, UserProfileRepository.DELETE)) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		return null;
		
	}
	
	
	@GetMapping("{approvalid}/stape/{approvalStepId}")
	public Map<String,Object> getApprovalStepsByApprovalStepId(
			@PathVariable("approvalid")Long approvalid,
			@PathVariable("approvalStepId") Long approvalStepId, @CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException {
		
		return  dbApprovalStep.getApprovalStateByApprovalIdAndStateId(approvalid, approvalStepId, user);
		
	}
	
	
	@GetMapping("{approvalid}/stapes")
	public List<Map<String,Object>> getStepsByApprovalId(
			@PathVariable("approvalid") Long approvalId, @CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException {
		return dbApprovalStep.getApprovalStapesByApprovalId(approvalId, user);
		
	}
	@PutMapping("enabledisable/{id}")
	public String enableDisableApprovalFlow(@PathVariable("id")Long id,@RequestBody Map<String,Object> flag) {
		dbApproval.enableDisable(id, Boolean.parseBoolean(flag.get("flag").toString()));
		return "{}";
	}
	
	@GetMapping("/advancesearch")
	private List<Map<String, Object>> advanceSearch(@RequestParam Map<String, Object> searchKey,
			@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException {
		return dbApproval.advanceSearch(searchKey, user);
	}

}
