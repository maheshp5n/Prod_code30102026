package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.pratikshat.exicom.entity.ExpenseManagement;
import com.pratikshat.exicom.entity.LPTransferReceive;
import com.pratikshat.exicom.entity.LProductRequest;
import com.pratikshat.exicom.entity.LProductRequestLineItem;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.CircleDetailsResponse;
import com.pratikshat.exicom.model.response.LocationProductRequestItemResponse;
import com.pratikshat.exicom.model.response.LocationProductRequestResponse;
import com.pratikshat.exicom.repository.UserProfileRepository;

@RestController
@RequestMapping("api/productrequest")
public class LocationProductRequestController {
	@Autowired
	com.pratikshat.exicom.repository.LocationProductRequestRepository dbProductRequest;
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;

	@GetMapping
	public Page<com.pratikshat.exicom.model.response.LocationProductRequestResponse> getProductRequest(
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user,
			@RequestParam(value = "filtername", required = false) String filtername, Pageable page) {
		if (dbProfile.hasObjectAccess(user.getUsername(), "LProductRequest", "viewAllRights")) {
			if (filtername == null || filtername.equalsIgnoreCase("") || filtername.equalsIgnoreCase("all")) {
				return dbProductRequest.getByAll(page);
			}
			if (filtername.equalsIgnoreCase("open")) {
				java.util.List<String> status = new java.util.ArrayList<String>();
				status.add("Open");
				status.add("Submitted");
				status.add("Receipt Pending");
				status.add("Partila Received");
				status.add("Submitted BY SCH");
				return dbProductRequest.getByStatus(status, page);
			}
			if (filtername.equalsIgnoreCase("Draf")) {
				java.util.List<String> status = new java.util.ArrayList<String>();
				status.add("Draft");
				return dbProductRequest.getByStatus(status, page);
			}
			if (filtername.equalsIgnoreCase("receiptpending")) {
				java.util.List<String> status = new java.util.ArrayList<String>();
				status.add("Receipt Pending");
				return dbProductRequest.getByStatus(status, page);
			}
			if (filtername.equalsIgnoreCase("recent")) {
				return null;
			}
			if (filtername.equalsIgnoreCase("forme")) {
				return dbProductRequest.getForOwnerAll(user.getUsername(), page);
			}
			if (filtername.equalsIgnoreCase("byme")) {

				return dbProductRequest.getByOwnerAll(user.getUsername(), page);
			}
			return dbProductRequest.getByAll(page);
		}
		if (dbProfile.hasObjectAccess(user.getUsername(), "LProductRequest", "viewRights")) {
			java.util.List<String> lst = new java.util.ArrayList<String>();
			lst = dbProfile.getListOfUserAndChildByUser(user.getUsername());
			List<Long> ids = dbProfile.getLocationAndChildLocation(user.getUsername());

			java.util.List<com.pratikshat.exicom.entity.EmployeeDetails> userList = new java.util.ArrayList<com.pratikshat.exicom.entity.EmployeeDetails>();

			for (String u : lst) {
				userList.add(new com.pratikshat.exicom.entity.EmployeeDetails(u));
			}
			if (filtername == null || filtername.equalsIgnoreCase("") || filtername.equalsIgnoreCase("all")) {
				return dbProductRequest.getByAllWithChilds(ids, page);
			}
			if (filtername.equalsIgnoreCase("Draf")) {
				java.util.List<String> status = new java.util.ArrayList<String>();
				status.add("Draft");
				return dbProductRequest.getByStatus(status, ids, page);
			}
			if (filtername.equalsIgnoreCase("open")) {
				java.util.List<String> status = new java.util.ArrayList<String>();
				status.add("Open");
				status.add("Submitted");
				status.add("Receipt Pending");
				status.add("Submitted BY SCH");
				return dbProductRequest.getByStatus(status, ids, page);
			}
			if (filtername.equalsIgnoreCase("receiptpending")) {
				java.util.List<String> status = new java.util.ArrayList<String>();
				status.add("Receipt Pending");
				return dbProductRequest.getByStatus(status, ids, page);
			}
			if (filtername.equalsIgnoreCase("recent")) {
				return null;
			}
			if (filtername.equalsIgnoreCase("forme")) {

				return dbProductRequest.getForOwnerAll(user.getUsername(), page);
			}
			if (filtername.equalsIgnoreCase("byme")) {
				return dbProductRequest.getByOwnerAll(user.getUsername(), page);
			}
			return dbProductRequest.getByAllWithChilds(ids, page);
		}

		return Page.empty();
	}

	@GetMapping("{productrequestid}")
	public com.pratikshat.exicom.model.response.LocationProductRequestResponse getLocationProductRequest(
			@PathVariable("productrequestid") long productrequestid,
			@CurrentUser LoginUser user) {
		if (dbProfile.hasObjectAccess(user.getUsername(), "LProductRequest", "viewRights")
				|| dbProfile.hasObjectAccess(user.getUsername(), "LProductRequest", "viewRights")) {
			return dbProductRequest.getByProductRequestId(productrequestid);
		}
		return new com.pratikshat.exicom.model.response.LocationProductRequestResponse();
	}

	@GetMapping("{productrequestid}/items")
	public Page<com.pratikshat.exicom.model.response.LocationProductRequestItemResponse> getProductRequestItems(
			@PathVariable("productrequestid") long productrequestid, Pageable page, @CurrentUser LoginUser user) {
		if (dbProfile.hasObjectAccess(user.getUsername(), "LProductRequest", "viewRights")
				|| dbProfile.hasObjectAccess(user.getUsername(), "LProductRequest", "viewRights")) {
			return dbProductRequest.getProductRequestItemsByRequestId(productrequestid, page);
		}
		return Page.empty();
	}

	@GetMapping("{productrequestid}/items/fortransfer")
	public List<com.pratikshat.exicom.model.response.LocationProductRequestItemResponse> getProductRequestItemsforTransfer(
			@PathVariable("productrequestid") long productrequestid, Pageable page, @CurrentUser LoginUser user) {
		if (dbProfile.hasObjectAccess(user.getUsername(), "LProductRequest", "viewRights")
				|| dbProfile.hasObjectAccess(user.getUsername(), "LProductRequest", "viewRights")) {
			return dbProductRequest.getProductRequestItemsByRequestIdForTransfer(productrequestid);
//			return dbProductRequest.getProductRequestItemsByRequestIdForTransfer(productrequestid, false);
		}
		return new ArrayList<LocationProductRequestItemResponse>();
	}

	@PutMapping("{productrequestid}/submit")
	public void submitRequest(@PathVariable("productrequestid") long productrequestid,
			@com.pratikshat.exicom.annotation.CurrentUser com.pratikshat.exicom.entity.LoginUser user,
			HttpServletResponse response) throws java.io.IOException {
		if (!dbProfile.hasObjectAccess(user.getUsername(), "LProductRequest", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return;
		}

		try {
			dbProductRequest.submitProductRequest(productrequestid, user);
		} catch (RuntimeException rEx) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, rEx.getMessage());
		} catch (UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
		}
	}

	@PutMapping("{productrequestid}/additem")
	public com.pratikshat.exicom.model.request.LPItemRequest addItems(
			@PathVariable("productrequestid") long productrequestid,
			@RequestBody com.pratikshat.exicom.model.request.LPItemRequest item,
			@com.pratikshat.exicom.annotation.CurrentUser com.pratikshat.exicom.entity.LoginUser user,
			HttpServletResponse response) throws java.io.IOException {
		if (!dbProfile.hasObjectAccess(user.getUsername(), "LProductRequest", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}

		try {
			if (item.getLprId() != productrequestid) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Not a valid request");
				return null;
			}

			return dbProductRequest.addProductItem(item, user);
		} catch (BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		} catch (UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}

	@GetMapping("{productrequestid}/okitems/{lineitemid}")
	public com.pratikshat.exicom.model.response.OkItemResponse getOkQuantity(
			@PathVariable("productrequestid") long prid, @PathVariable("lineitemid") long lineItemId) {
		return dbProductRequest.getOkItems(prid, lineItemId);
	}

	@PostMapping("{productrequestid}/transferitem")
	public com.pratikshat.exicom.model.request.LPTransferRequest transferItems(
			@PathVariable("productrequestid") long productrequestid,
			@com.pratikshat.exicom.annotation.CurrentUser com.pratikshat.exicom.entity.LoginUser user,
			@RequestBody com.pratikshat.exicom.model.request.LPTransferRequest tRequest,
			HttpServletResponse response) throws java.io.IOException {
		if (productrequestid != tRequest.getLocationProductRequestId()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product request id not found");
			return null;
		}
		try {
			return dbProductRequest.transferProducts(tRequest, user);
		} catch (com.pratikshat.exicom.exception.BadRequestException bd) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, bd.getMessage());
		} catch (com.pratikshat.exicom.exception.UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		}
		return null;
	}

	@GetMapping("{productrequestid}/pendingtoreceiveitem")
	public java.util.List<com.pratikshat.exicom.model.response.LocationProductRequestPendingToReceive> getPendingItem(
			@com.pratikshat.exicom.annotation.CurrentUser com.pratikshat.exicom.entity.LoginUser user,
			@PathVariable("productrequestid") long productrequestid,
			HttpServletResponse response) throws java.io.IOException {
		java.util.List<com.pratikshat.exicom.model.response.LocationProductRequestPendingToReceive> items = dbProductRequest
				.getPendingItemToReceive(productrequestid, user.getUsername());
		int i = 0;
		int counter = items.size();
		for (i = 0; i < counter; i++) {

			items.get(i).setSerialNumbers(
					dbProductRequest.getPendingLineItemToReceive(items.get(i).getProductTransferItemId()));

		}
		return items;
	}

	@PostMapping("{productrequestid}/receiveitem")
	public void receiveItems(@PathVariable("productrequestid") long productrequestid,
			@com.pratikshat.exicom.annotation.CurrentUser com.pratikshat.exicom.entity.LoginUser user,
			@RequestBody java.util.List<com.pratikshat.exicom.model.request.LocationProductTransferReceiveRequest> items,
			HttpServletResponse response) throws java.io.IOException {
		try {
			if (!dbProfile.hasObjectAccess(user.getUsername(), LPTransferReceive.class, UserProfileRepository.CREATE)) {

				throw new UnauthoriseException("You are not authorised");
			}

			dbProductRequest.receiveTransfredItem(productrequestid, items,
					new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));

		} catch (com.pratikshat.exicom.exception.BadRequestException bd) {
			bd.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, bd.getMessage());
		} catch (com.pratikshat.exicom.exception.UnauthoriseException ex) {
			ex.printStackTrace();
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		}

	}

	@GetMapping("{productrequestid}/transferitem")
	public Page<com.pratikshat.exicom.model.response.LocationProductTransferItemResponse> getTransferItems(
			@PathVariable("productrequestid") long productrequestid, Pageable page) {
		return dbProductRequest.getLocationProductTransferByProductRequestId(productrequestid, page);
	}

	// shagufta find By Id
	@GetMapping("lineitem/{id}")
	public LocationProductRequestItemResponse getLegalentity(@PathVariable("id") long id,
			@CurrentUser LoginUser userDetails) {
		return dbProductRequest.getProductRequestItemsById(id);
	}

	@DeleteMapping("deleteitem/{requestitemid}")
	public ResponseEntity<?> deleteProductRequestItem(@PathVariable("requestitemid") long requestitemid,
			@CurrentUser LoginUser user, HttpServletResponse response) throws IOException {
		try {
			dbProductRequest.removeProductRequestItem(requestitemid, user);
			ResponseEntity.ok("{}");
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		} catch (UnauthoriseException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
		}
		return null;
	}

	@PutMapping("updatesourcelocation/{prid}/{sourcelocationid}")
	public String updateSourceLocation(@PathVariable("prid") long prdId,
			@PathVariable("sourcelocationid") long sourceLocationId, @CurrentUser LoginUser user,
			HttpServletResponse response) throws IOException {
		try {
			dbProductRequest.updateSourceLocation(prdId, sourceLocationId, user);
			return "{}";
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		} catch (UnauthoriseException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
		}
		return null;
	}

	@PutMapping("cahngepartcodeofpr/{prlineitemid}")
	public String updateProductPartcode(@PathVariable("prlineitemid") long prlineItemId,
			@RequestBody Map<String, String> partCode, HttpServletResponse response, @CurrentUser LoginUser user)
			throws IOException {

		if (!dbProfile.hasObjectAccess(user.getUsername(), LProductRequestLineItem.class, UserProfileRepository.EDIT)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorise");
			return null;
		}

		Map<String, String> act = dbProfile.getAccountAndRoleGroup(user);
		if (act.get("roleGroup") == null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorise");
			return null;
		}
		if (!act.get("roleGroup").equalsIgnoreCase("SCH")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorise");
			return null;
		}

		try {
			dbProductRequest.updateProductPartCode(prlineItemId, partCode.get("partCode").toString(), user);
			return "{}";
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		} catch (UnauthoriseException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
		}
		return null;
	}

	@PutMapping("submittosap/{productrequestid}")
	public String submitTosap(@PathVariable("productrequestid") long prdId, @CurrentUser LoginUser user,
			HttpServletResponse resposne) throws IOException {
		try {
			dbProductRequest.submitProductRequestToSAP(prdId, user);
		} catch (Exception e) {
			e.printStackTrace();
			resposne.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			return null;
		}
		return "{}";
	}

	@PutMapping("disabllineitem/{productrequestlineitemid}")
	public String disableEnableProductLineItem(@PathVariable("productrequestlineitemid") long lineItemId,
			@CurrentUser LoginUser user, HttpServletResponse response) throws IOException {
		try {
			dbProductRequest.disableProductLineItems(lineItemId, true, user);
			return "{}";
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			e.printStackTrace();
		} catch (UnauthoriseException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("advancesearch")
	public List<LocationProductRequestResponse> getAdvanceSearchresult(@RequestParam Map<String, Object> request,
			@CurrentUser LoginUser user, HttpServletResponse response) throws IOException, ParseException {

		if (dbProfile.hasObjectAccess(user.getUsername(), LProductRequest.class, UserProfileRepository.VIEW_ALL)) {
			return dbProductRequest.advanceSearch(request, user, null);
		}

		if (dbProfile.hasObjectAccess(user.getUsername(), LProductRequest.class, UserProfileRepository.VIEW)) {
			return dbProductRequest.advanceSearch(request, user,
					dbProfile.getLocationAndChildLocation(user.getUsername()));
		}

		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		return null;

	}

	@GetMapping("{productrequestid}/items/advancesearch")
	public List<com.pratikshat.exicom.model.response.LocationProductRequestItemResponse> getProductRequestItemsAdvanceSearch(
			@PathVariable("productrequestid") long productrequestid, @RequestParam Map<String, Object> request,
			@CurrentUser LoginUser user, HttpServletResponse response) throws IOException, ParseException {
		if (dbProfile.hasObjectAccess(user.getUsername(), "LProductRequest", "viewRights")
				|| dbProfile.hasObjectAccess(user.getUsername(), "LProductRequest", "viewRights")) {
			if (productrequestid == 0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product Request Id is 0");
			}
			return dbProductRequest.getProductRequestItemsAdvanceSearch(productrequestid, request);
		}
		return new ArrayList<>();
	}

	@GetMapping("{productrequestid}/transferitem/advancesearch")
	public List<com.pratikshat.exicom.model.response.LocationProductTransferItemResponse> getTransferItemsAdvanceSearch(
			@PathVariable("productrequestid") long productrequestid, @RequestParam Map<String, Object> request,
			HttpServletResponse response) throws IOException, ParseException {
		if (productrequestid == 0) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product Request Id is 0");
		}
		return dbProductRequest.getLocationProductTransferByProductAdvanceSearch(productrequestid, request);
	}

	@GetMapping("status/forlist")
	public java.util.List<String> getProductRequestStatusList() {
		List<String> list = new ArrayList<>();
		list.add("Draft");
		list.add("Open");
		list.add("Partial Received");
		list.add("Receipt Pending");
		list.add("Received");
		list.add("Submitted");
		list.add("Submitted BY SCH");
		return list;
	}
	
//	shortCloseProductRequest of items
	@PutMapping("shortclose/{productrequestid}/item")
	public String shortClosePRItem(@PathVariable("productrequestid") long prdId, @RequestParam(name="productRequestLineItemId") long productRequestLineItemId, @CurrentUser LoginUser user,
			HttpServletResponse resposne) throws IOException {
		try {
			System.out.println(productRequestLineItemId);
			dbProductRequest.shortCloseProductRequestItems(prdId, user, productRequestLineItemId);
		} catch (Exception e) {
			e.printStackTrace();
			resposne.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			return null;
		}
		return "{}";
	}	
	
//	shortCloseProductRequest of pr
	@PutMapping("shortclose/{productrequestid}")
	public String shortClosePR(@PathVariable("productrequestid") long prdId, @CurrentUser LoginUser user,
			HttpServletResponse resposne) throws IOException {
		try {
			dbProductRequest.shortCloseProductRequest(prdId, user);
		} catch (Exception e) {
			e.printStackTrace();
			resposne.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			return null;
		}
		return "{}";
	}	
	
	
//	get owner role  of pr
	
	@GetMapping("getOwnerdetails/{productrequestid}")
	public Map<Object, String> ownerPR(@PathVariable("productrequestid") long prdId, @CurrentUser LoginUser user,
			HttpServletResponse resposne) throws IOException {
		try {
			dbProfile.getuserRole(prdId);
		} catch (Exception e) {
			e.printStackTrace();
			resposne.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			return null;
		}
		return dbProfile.getuserRole(prdId);
	}

}
