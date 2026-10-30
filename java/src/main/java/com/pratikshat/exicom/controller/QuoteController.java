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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.QuoteRecentVisit;
import com.pratikshat.exicom.entity.QuoteStatus;
import com.pratikshat.exicom.entity.UploadedFiles;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.LodgingExpenseRequest;
import com.pratikshat.exicom.model.request.OppertunityLineItemRequest;
import com.pratikshat.exicom.model.request.QuoteLineItemRequest;
import com.pratikshat.exicom.model.request.QuoteRequest;
import com.pratikshat.exicom.model.request.WorkOrderRequest;
import com.pratikshat.exicom.model.response.MailLogResponse;
import com.pratikshat.exicom.model.response.OppertunityLineItemResponse;
import com.pratikshat.exicom.model.response.QuoteLineItemResponse;
import com.pratikshat.exicom.model.response.QuoteResponse;
import com.pratikshat.exicom.model.response.SimpleResponseMessage;
import com.pratikshat.exicom.repository.QuoteLineItemRepository;
import com.pratikshat.exicom.repository.QuoteRecentVisitRepository;
import com.pratikshat.exicom.repository.QuoteRepository;
import com.pratikshat.exicom.repository.QuoteStatusRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;

@RestController
@RequestMapping("api/quote")
public class QuoteController {

	@Autowired
	QuoteRepository dbQuote;
	@Autowired
	QuoteRecentVisitRepository dbQuoteRecentVisit;
	@Autowired
	QuoteStatusRepository dbQuoteStatusRepository;

	@Autowired
	QuoteLineItemRepository dbLineItemRepository;

	@Autowired
	private com.pratikshat.exicom.repository.UserProfileRepository dbProfile;

	@GetMapping
	public Page<QuoteResponse> getAllQuotes(@CurrentUser LoginUser user, Pageable pageable,
			HttpServletResponse response,@RequestParam(value="filtername",required=false)String filtername) throws IOException {
		if (dbProfile.hasObjectAccess(user.getUsername(), "Quote", "viewRights")
				|| dbProfile.hasObjectAccess(user.getUsername(), "Quote", "viewAllRights")) {
			try {
				if(filtername!=null&&filtername.equalsIgnoreCase("Approved by Store")) {
					return dbQuote.getQuotesByStatus(filtername, pageable);
				}
				if(filtername!=null&&filtername.equalsIgnoreCase("Quote Sent")) {
					return dbQuote.getQuotesByStatus(filtername, pageable);
				}
				if(filtername!=null&&filtername.equalsIgnoreCase("Rejected by Store")) {
					return dbQuote.getQuotesByStatus(filtername, pageable);
				}
				if(filtername!=null&&filtername.equalsIgnoreCase("Work Order Not Received")) {
					return dbQuote.getQuotesByStatus(filtername, pageable);
				}
				if(filtername!=null&&filtername.equalsIgnoreCase("All")) {
					return dbQuote.getAll(pageable);
				}
				if(filtername!=null&&filtername.equalsIgnoreCase("Submitted For Approval")) {
					return dbQuote.getQuotesByStatus(filtername, pageable);
				}
				if(filtername!=null&&filtername.equalsIgnoreCase("Draft")) {
					return dbQuote.getQuotesByStatus(filtername, pageable);
				}
				if(filtername!=null&&filtername.equalsIgnoreCase("Approved")) {
					return dbQuote.getQuotesByStatus(filtername, pageable);
				}
				if(filtername!=null&&filtername.equalsIgnoreCase("Recently Viewed")) {
					return dbQuote.getRecentVisits(user.getUsername(), pageable);
				}
				if(filtername!=null&&filtername.equalsIgnoreCase("Invoiced")) {
					return dbQuote.getQuotesByStatus(filtername, pageable);
				}
				if(filtername!=null&&filtername.equalsIgnoreCase("Work Order Received")) {
					return dbQuote.getQuotesByStatus(filtername, pageable);
				}
				if(filtername!=null&&filtername.equalsIgnoreCase("Withdrawn")) {
					return dbQuote.getQuotesByStatus(filtername, pageable);
				}
				if(filtername!=null&&filtername.equalsIgnoreCase("Rejected")) {
					return dbQuote.getQuotesByStatus(filtername, pageable);
				}
				
					
				return dbQuote.getAll(pageable);
			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}

		}
		return Page.empty();

	}

	@GetMapping("recentvisit")
	public Page<QuoteResponse> getRecentQuotes(@CurrentUser LoginUser user, Pageable pageable,
			HttpServletResponse response) throws IOException {
		if (dbProfile.hasObjectAccess(user.getUsername(), "Quote", "viewRights")
				|| dbProfile.hasObjectAccess(user.getUsername(), "Quote", "viewAllRights")) {
			try {
				return dbQuote.getRecentVisits(user.getUsername(), pageable);
			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}

		}
		return Page.empty();
	}

	@GetMapping("bystatus")
	public Page<QuoteResponse> getQuotesByStatus(@RequestParam("status") String status, @CurrentUser LoginUser user,
			Pageable pageable, HttpServletResponse response) throws IOException {
		if (dbProfile.hasObjectAccess(user.getUsername(), "Quote", "viewRights")
				|| dbProfile.hasObjectAccess(user.getUsername(), "Quote", "viewAllRights")) {
			try {
				if(status!=null&&status.equalsIgnoreCase("All"))
					return dbQuote.getAll(pageable);
				return dbQuote.getQuotesByStatus(status, pageable);
			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}

		}
		return Page.empty();

	}

	@GetMapping("{quoteId}")
	public QuoteResponse getByQuoteId(@PathVariable("quoteId") long quoteId, @CurrentUser LoginUser user) {

		if (dbProfile.hasObjectAccess(user.getUsername(), "Quote", "viewRights")
				|| dbProfile.hasObjectAccess(user.getUsername(), "Quote", "viewAllRights")) {
			try {
				QuoteRecentVisit _recent = dbQuoteRecentVisit.findByQuoteIdAndVisitorId(quoteId, user.getUsername());
				if (_recent != null) {
					_recent.setVisitDate(new Date());
				} else {
					_recent = new QuoteRecentVisit(quoteId, user.getUsername());
				}
				dbQuoteRecentVisit.save(_recent);

				return dbQuote.getById(quoteId);
			} catch (Exception e) {
			}
		}

		return new QuoteResponse();
	}

	@PostMapping
	public QuoteResponse createQuote(@Valid @RequestBody QuoteRequest quoteRequest, @CurrentUser LoginUser user,
			HttpServletResponse response) throws IOException {

		if (!dbProfile.hasObjectAccess(user.getUsername(), "Quote", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}

		try {
			dbQuote.saveQuote(quoteRequest, user);
			return dbQuote.getById(quoteRequest.getId());
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

	@PutMapping
	public QuoteResponse updateQuote(@Valid @RequestBody QuoteRequest quoteRequest, @CurrentUser LoginUser user,
			HttpServletResponse response) throws IOException {

		if (!dbProfile.hasObjectAccess(user.getUsername(), "Quote", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised 1");
			return null;
		}
		try {
			dbQuote.updateQuote(quoteRequest, user);
			return dbQuote.getById(quoteRequest.getId());
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

	@GetMapping("status")
	public List<QuoteStatus> getQuotesStatusList(@CurrentUser LoginUser user, Pageable pageable) {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1); // Draft
		list.add(2); // Work Order Received
		list.add(5); // Work Order Not Received
		list.add(6); // Submitted For Approval
		list.add(8); // Invoiced
		list.add(9); // Withdrawn
		return dbQuoteStatusRepository.getStatus(list);
	}

	@PostMapping("lineitem")
	public List<QuoteLineItemResponse> addQuoteLineItems(@Valid @RequestBody List<QuoteLineItemRequest> lineItems,
			@RequestParam(name = "priceBookId") long priceBookId, @RequestParam(name = "quoteId") long quoteId,
			@RequestParam(name = "businessDomainId") int businessDomainId, @CurrentUser LoginUser user,
			HttpServletResponse response) throws IOException {

		if (!dbProfile.hasObjectAccess(user.getUsername(), "Quote", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}

		try {
			dbQuote.addLineItem(lineItems, priceBookId, quoteId, businessDomainId, user);
			return dbLineItemRepository.getLineItemListByQuoteId(quoteId);
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

	@PutMapping("lineitem")
	public QuoteLineItemResponse updateQuoteLineItem(@Valid @RequestBody QuoteLineItemRequest lineItem,
			@RequestParam(name = "priceBookId") long priceBookId, @RequestParam(name = "quoteId") long quoteId,
			@RequestParam(name = "businessDomainId") int businessDomainId, @CurrentUser LoginUser user,
			HttpServletResponse response) throws IOException {

		if (!dbProfile.hasObjectAccess(user.getUsername(), "Quote", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			dbQuote.updateLineItem(lineItem, priceBookId, quoteId, businessDomainId, user);
			return dbLineItemRepository.getLineItemById(lineItem.getId());
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

	@GetMapping("lineitem/{id}")
	public QuoteLineItemResponse getQuoteLineItemById(@CurrentUser LoginUser user, @PathVariable("id") long id) {
		return dbLineItemRepository.getLineItemById(id);
	}

	@GetMapping("lineitem")
	public Page<QuoteLineItemResponse> getQuoteLineItems(@CurrentUser LoginUser user,
			@RequestParam(name = "quoteId") long quoteId, Pageable pageable) {
		return dbLineItemRepository.getLineItemPageByQuoteId(quoteId, pageable);
	}

	@GetMapping("lineitemList")
	public List<QuoteLineItemResponse> getLineItemListByQuoteId(@CurrentUser LoginUser user,
			@RequestParam(name = "quoteId") long quoteId) {
		return dbLineItemRepository.getLineItemListByQuoteId(quoteId);
	}

	@PutMapping("lineitem/updateall")
	public List<QuoteLineItemResponse> updateQuoteLineItemsList(
			@Valid @RequestBody List<QuoteLineItemRequest> lineItems,
			@RequestParam(name = "priceBookId") long priceBookId, @RequestParam(name = "quoteId") long quoteId,
			@RequestParam(name = "businessDomainId") int businessDomainId, @CurrentUser LoginUser user,
			HttpServletResponse response) throws IOException {

		if (!dbProfile.hasObjectAccess(user.getUsername(), "Quote", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			dbQuote.updateLineItemList(lineItems, priceBookId, quoteId, businessDomainId, user);
			return dbLineItemRepository.getLineItemListByQuoteId(quoteId);
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

	@DeleteMapping("{quoteId}")
	public SimpleResponseMessage deleteQuote(@PathVariable("quoteId") long quoteId, @CurrentUser LoginUser user,
			HttpServletResponse response) throws IOException {

		if (dbProfile.hasObjectAccess(user.getUsername(), "Quote", "createRights")
				|| dbProfile.hasObjectAccess(user.getUsername(), "Quote", "editRights")) {
			try {
				dbQuote.deleteQuote(quoteId, user);
				return new SimpleResponseMessage("deleted Successfully");
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
		} else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}

	}

	@DeleteMapping("lineitem/{quoteLineItemId}")
	public SimpleResponseMessage deleteOppertunityLineItem(@PathVariable("quoteLineItemId") long quoteLineItemId,
			@CurrentUser LoginUser user, HttpServletResponse response) throws IOException {

		if (dbProfile.hasObjectAccess(user.getUsername(), "Quote", "createRights")
				|| dbProfile.hasObjectAccess(user.getUsername(), "Quote", "editRights")) {
			try {
				dbQuote.deleteQuoteLineItem(quoteLineItemId, user);
				return new SimpleResponseMessage("deleted Successfully");
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
		} else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}

	}

	@GetMapping("syncquote")
	public boolean syncQuoteWithOppertunity(@CurrentUser LoginUser user, @RequestParam(name = "quoteId") long quoteId,
			@RequestParam(name = "businessDomainId") int businessDomainId, HttpServletResponse response)throws IOException {
		
		if (dbProfile.hasObjectAccess(user.getUsername(), "Quote", "createRights")
				|| dbProfile.hasObjectAccess(user.getUsername(), "Quote", "editRights")) {
			try {
				return dbQuote.syncQuoteWithOppertunity(quoteId, businessDomainId, user);
			} catch (BadRequestException ex) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
				return false;
			} catch (UnauthoriseException ex) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
				return false;
			} catch (Exception ex) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
				return false;
			}
		} else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return false;
		}
	}

	@GetMapping("stopsyncquote")
	public boolean stopSyncQuoteWithOppertunity(@CurrentUser LoginUser user,
			@RequestParam(name = "quoteId") long quoteId, @RequestParam(name = "businessDomainId") int businessDomainId,
			HttpServletResponse response) throws IOException {
		
		if (dbProfile.hasObjectAccess(user.getUsername(), "Quote", "createRights")
				|| dbProfile.hasObjectAccess(user.getUsername(), "Quote", "editRights")) {
			try {
				return dbQuote.stopSyncQuoteWithOppertunity(quoteId, businessDomainId, user);
			} catch (BadRequestException ex) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
				return false;
			} catch (UnauthoriseException ex) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
				return false;
			} catch (Exception ex) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
				return false;
			}
		} else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return false;
		}

	}

	@GetMapping(value = "/pdf")
	public ResponseEntity<?> createPdf(@RequestParam("quoteId") long quoteId, HttpServletResponse response,
			@CurrentUser LoginUser user) throws IOException {
		
		if (dbProfile.hasObjectAccess(user.getUsername(), "Quote", "createRights")
				|| dbProfile.hasObjectAccess(user.getUsername(), "Quote", "editRights")) {
			try {
				QuoteResponse quoteResponse = dbQuote.getById(quoteId);

				if (quoteResponse != null) {

					ByteArrayInputStream bis = dbQuote.createPdf(quoteId, user);

					HttpHeaders headers = new HttpHeaders();
					headers.add("Content-Disposition", "inline; filename=" + quoteResponse.getQuoteName() + ".pdf");

					return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
							.body(new InputStreamResource(bis));

				} else {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Quote does not found");
					return null;
				}
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
		} else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
	}
	
	
	@PostMapping("submitforapproval")
	public SimpleResponseMessage submitForApproval(@RequestParam("quoteId")long quoteId, @CurrentUser LoginUser user, HttpServletResponse httpServletResponse) throws IOException{
		
		if (dbProfile.hasObjectAccess(user.getUsername(), "Quote", "createRights")
				|| dbProfile.hasObjectAccess(user.getUsername(), "Quote", "editRights")) {
			
			try {
				dbQuote.submitForApproval(quoteId, user);
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
		}else {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
	}
	
	
	@PutMapping("updateworkorder")
	public QuoteResponse updateWorkOrder(@RequestBody WorkOrderRequest request, @CurrentUser LoginUser user, HttpServletResponse httpServletResponse) throws Exception{
		
		if (dbProfile.hasObjectAccess(user.getUsername(), "Quote", "createRights")
				|| dbProfile.hasObjectAccess(user.getUsername(), "Quote", "editRights")) {
			
			try {
				
				if(request.getWorkOrderNumber()==null) {
					httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Please enter work order number");
					return null;
				}
				
				if(request.getWorkOrderDate()==null) {
					httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Please enter work order date");
					return null;	
				}
				
				dbQuote.workOrderUpdate(request, user);
				return dbQuote.getById(request.getQuoteId());
			} catch(BadRequestException ex) {
				ex.printStackTrace();
				httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
				return null;
			}catch(UnauthoriseException ex) {
				ex.printStackTrace();
				httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
				return null;
			} catch (Exception ex) {
				ex.printStackTrace();
				httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
				return null;
			}
		}else {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
	}
	
	@GetMapping("mail/{quoteId}")
	public List<MailLogResponse> getMailLogOfQuote(@CurrentUser LoginUser user, @PathVariable("quoteId") long quoteId,HttpServletResponse response) throws IOException {
		
		if (dbProfile.hasObjectAccess(user.getUsername(), "Quote", "viewRights")
				|| dbProfile.hasObjectAccess(user.getUsername(), "Quote", "viewAllRights")) {
			try {
				return dbQuote.getMailLog(quoteId, user);
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
		} else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}

	}
	
	
	@GetMapping("advancesearch")
	public List<QuoteResponse> getAdvanceSearchresult(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException{
		
		if(dbProfile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.Quote.class, UserProfileRepository.VIEW_ALL)) {
			return 	dbQuote.advanceSearch(request, null);
		}
		
		if(dbProfile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.Quote.class, UserProfileRepository.VIEW)) {
			return 	dbQuote.advanceSearch(request,null);
		}
		
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		return null;
		
	}
	
	@GetMapping("status/forlist")
	public java.util.List<String> getQuoteStatusList() {
		return dbQuoteStatusRepository.getQuoteStatusList();
	}
}
