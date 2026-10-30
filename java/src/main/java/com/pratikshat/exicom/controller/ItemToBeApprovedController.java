package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ToBeApproved;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.ApprovalRequest;
import com.pratikshat.exicom.repository.UserProfileRepository;

@RestController
@RequestMapping("api/itemtobeapproved")
public class ItemToBeApprovedController {
	@Autowired
	com.pratikshat.exicom.repository.ToBeApprovedRepository dbItemsToBeApproved;

	@Autowired
	UserProfileRepository dbProfile;

	@GetMapping
	public Page<com.pratikshat.exicom.entity.ToBeApproved> getItemsToBeApproved(@CurrentUser LoginUser user,
			Pageable page) {

		Map<String, String> rlGrp = dbProfile.getAccountAndRoleGroup(user);
		String _rl = rlGrp.get("roleGroup").toString();
		System.out.println(_rl);
		if (_rl.equalsIgnoreCase("COMMERCIAL") || _rl.equalsIgnoreCase("FINANCE")
				|| _rl.equalsIgnoreCase("CUSTOMERCARE") || _rl.equalsIgnoreCase("DCT_MAIN_STORE")) {

			Page<ToBeApproved> appr = dbItemsToBeApproved.findByStatusAndAssignedTo_emailIdIn(false,
					dbProfile.getUsersInMainRoleGroup(_rl), page);

			for (ToBeApproved tobe : appr) {
				tobe.setInitiator(dbItemsToBeApproved.getInitiator(tobe.getObjectName(), tobe.getObjectId()));
			}

			return appr;
		} else {
			Page<ToBeApproved> appr = dbItemsToBeApproved.findByStatusAndAssignedTo_emailIdIn(false,
					dbProfile.getSameroleUsers(user), page);

			for (ToBeApproved tobe : appr) {
				tobe.setInitiator(dbItemsToBeApproved.getInitiator(tobe.getObjectName(), tobe.getObjectId()));
			}

			return appr;
		}

	}

	@GetMapping("hasapproval")
	public Map<String, Object> getApproval(@CurrentUser LoginUser user, @RequestParam("objectname") String objectName,
			@RequestParam("objectid") long objectId, HttpServletResponse response) throws IOException {
		if (objectName == null || objectId == 0) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "bad request");
			return null;
		}

		Map<String, String> rlGrp = dbProfile.getAccountAndRoleGroup(user);
		String _rl = rlGrp.get("roleGroup").toString();
		if (_rl.equalsIgnoreCase("COMMERCIAL") || _rl.equalsIgnoreCase("FINANCE")
				|| _rl.equalsIgnoreCase("CUSTOMERCARE") || _rl.equalsIgnoreCase("DCT_MAIN_STORE")) {
			Map<String, Object> tobe = dbItemsToBeApproved.findByAssignedToAndStatusAndObjectNameAndObjectId(
					dbProfile.getUsersInMainRoleGroup(_rl), false, objectName, objectId);
			if (tobe != null) {
				return tobe;
			}
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "bad request");
			return null;
		}

		Map<String, Object> tobe = dbItemsToBeApproved.findByAssignedToAndStatusAndObjectNameAndObjectId(
				dbProfile.getSameroleUsers(user), false, objectName, objectId);
		if (tobe != null) {
			return tobe;
		}
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "bad request");
		return null;
	}

	@PutMapping("{approvalid}/approvereject")
	public com.pratikshat.exicom.model.request.ApprovalRequest approve(@PathVariable("approvalid") long approvalid,
			@CurrentUser LoginUser user, @RequestBody com.pratikshat.exicom.model.request.ApprovalRequest appRequest,
			HttpServletResponse response) throws java.io.IOException {
		try {
			return dbItemsToBeApproved.approveReject(appRequest,
					new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()), user);
		} catch (com.pratikshat.exicom.exception.BadRequestException bd) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, bd.getMessage());
		} catch (com.pratikshat.exicom.exception.UnauthoriseException us) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, us.getMessage());
		}
		return null;
	}

	@GetMapping("history")
	public List<java.util.Map<String, Object>> getHistory(@RequestParam("objectname") String objectName,
			@RequestParam("objectid") long objectId) {
		return dbItemsToBeApproved.findByObjectNameAndObjectId(objectName, objectId);
	}

	@PutMapping("multipleapproval")
	public Map<String, String> multipleApproved(@RequestBody List<ApprovalRequest> req, @CurrentUser LoginUser user) {

		for (ApprovalRequest rq : req) {
			try {
				dbItemsToBeApproved.approveReject(rq, new EmployeeDetails(user.getUsername()), user);
			} catch (BadRequestException | UnauthoriseException e) {
				e.printStackTrace();
			}
		}

		return new HashMap<String, String>();
	}

	@GetMapping("advancesearch")
	public List<Map<String, Object>> getAdvanceSarch(@RequestParam Map<String, Object> search,
			@CurrentUser LoginUser user) {
		try {
			List<Map<String, Object>> result = dbItemsToBeApproved.getAdvanceSearchResult(search, user);

			return result;
		} catch (BadRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
