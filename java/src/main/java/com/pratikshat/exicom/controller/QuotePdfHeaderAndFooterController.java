package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.QuotePdfHeaderAndFooter;
import com.pratikshat.exicom.entity.StateWithQuotePdf;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.QuotePdfHeaderAndFooterRequest;
import com.pratikshat.exicom.model.request.QuoteTemplateEnableAndDisableRequest;
import com.pratikshat.exicom.model.response.QuotePdfHeaderAndFooterResponse;
import com.pratikshat.exicom.model.response.TermsAndConditionsResponse;
import com.pratikshat.exicom.repository.QuotePdfHeaderAndFooterRepository;
import com.pratikshat.exicom.repository.QuoteTermsAndConditionsRepository;
import com.pratikshat.exicom.repository.StateWithQuotePdfRepository;

@RestController
@RequestMapping("api/quotetemplate")
public class QuotePdfHeaderAndFooterController {

	@Autowired
	QuotePdfHeaderAndFooterRepository dbQuoteHeaderAndFooter;

	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	@Autowired
	QuoteTermsAndConditionsRepository dbTermsAndCondition;
	
	@Autowired
	StateWithQuotePdfRepository dbStatesWithPdf;

	@GetMapping("{id}")
	public QuotePdfHeaderAndFooterResponse getTemplateById(@PathVariable("id") long id, @CurrentUser LoginUser user,
			HttpServletResponse response) {
		if (dbProfile.hasObjectAccess(user.getUsername(), "QuotePdfHeaderAndFooter", "viewAllRights")) {
			List<TermsAndConditionsResponse> termsAndConditions = dbTermsAndCondition.getByObjectNameAndActiveOrderBySerialNumberAsc("Quote",true);
			QuotePdfHeaderAndFooterResponse reasponse = dbQuoteHeaderAndFooter.getById(id);
			if(reasponse!=null) {
				reasponse.setTermsAndConditions(termsAndConditions);
			}
			
			List<StateWithQuotePdf> states = dbStatesWithPdf.getStatesByTemplateId(id);
			if(states!=null) {
				reasponse.setStateList(states);
			}
			
			return reasponse;
		}
		
		if (dbProfile.hasObjectAccess(user.getUsername(), "QuotePdfHeaderAndFooter", "viewRights")) {
			List<TermsAndConditionsResponse> termsAndConditions = dbTermsAndCondition.getByObjectNameAndActiveOrderBySerialNumberAsc("Quote",true);
			QuotePdfHeaderAndFooterResponse reasponse = dbQuoteHeaderAndFooter.getById(id);
			if(reasponse!=null) {
				reasponse.setTermsAndConditions(termsAndConditions);
			}
			
			List<StateWithQuotePdf> states = dbStatesWithPdf.getStatesByTemplateId(id);
			if(states!=null) {
				reasponse.setStateList(states);
			}
			return reasponse;
		}
		return new QuotePdfHeaderAndFooterResponse();
	}

	@GetMapping
	public Page<QuotePdfHeaderAndFooterResponse> getAllTemplate(Pageable page, @CurrentUser LoginUser user,
			HttpServletResponse response) {
		if (dbProfile.hasObjectAccess(user.getUsername(), "QuotePdfHeaderAndFooter", "viewAllRights")) {
			return dbQuoteHeaderAndFooter.getAllPageable(page);
		}
		
		if (dbProfile.hasObjectAccess(user.getUsername(), "QuotePdfHeaderAndFooter", "viewRights")) {
			return dbQuoteHeaderAndFooter.getAllPageable(page);
		}

		return Page.empty();
	}

	@PostMapping
	public QuotePdfHeaderAndFooterResponse saveTemplate(@RequestBody QuotePdfHeaderAndFooterRequest request,
			@CurrentUser LoginUser user, HttpServletResponse response) throws IOException {
		try {
			if (!dbProfile.hasObjectAccess(user.getUsername(), "QuotePdfHeaderAndFooter", "createRights")) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
				return null;
			}

			if (request != null) {
				if (request.getTemplateName() == null || request.getTemplateName().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Template Name is null");
					return null;
				}

				if (request.getLogo() == null || request.getLogo().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Logo is null");
					return null;
				}

				if (request.getSignature() == null || request.getSignature().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Signature is null");
					return null;
				}

				if (request.getCompanyName() == null || request.getCompanyName().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Company name is null");
					return null;
				}

				if (request.getAddressLine1() == null || request.getAddressLine1().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Address line 1 is null");
					return null;
				}

				if (request.getCity() == null || request.getCity().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "City is null");
					return null;
				}

				if (request.getPincode() == null || request.getPincode().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Pincode is null");
					return null;
				}

				if (request.getStateName() == null || request.getStateName().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "State name is null");
					return null;
				}

				if (request.getCin() == null || request.getCin().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "CIN number is null");
					return null;
				}

				if (request.getDocumentNumber() == null || request.getDocumentNumber().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Document number is null");
					return null;
				}

				if (request.getTelephone() == null || request.getTelephone().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Telephone number is null");
					return null;
				}

				if (request.getGstNumber() == null || request.getGstNumber().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "GST number is null");
					return null;
				}

				if (request.getWebsite() == null || request.getWebsite().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Website is null");
					return null;
				}

				if (request.getFooterline1() == null || request.getFooterline1().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Footer line 1 is null");
					return null;
				}

				QuotePdfHeaderAndFooterRequest template = dbQuoteHeaderAndFooter.createTemplate(request,user);

				List<TermsAndConditionsResponse> termsAndConditions = dbTermsAndCondition.getByObjectNameAndActiveOrderBySerialNumberAsc("Quote",true);
				QuotePdfHeaderAndFooterResponse reasponse = dbQuoteHeaderAndFooter.getById(template.getId());
				if(reasponse!=null) {
					reasponse.setTermsAndConditions(termsAndConditions);
				}
				
				List<StateWithQuotePdf> states = dbStatesWithPdf.getStatesByTemplateId(template.getId());
				if(states!=null) {
					reasponse.setStateList(states);
				}
				
				return reasponse;
			} else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Request is null");
				return null;
			}
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		} catch (UnauthoriseException ex) {
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
	public QuotePdfHeaderAndFooterResponse updateTemplate(@RequestBody QuotePdfHeaderAndFooterRequest request,
			@CurrentUser LoginUser user, HttpServletResponse response) throws IOException{
		try {
			
			if (!dbProfile.hasObjectAccess(user.getUsername(), "QuotePdfHeaderAndFooter", "editRights")) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
				return null;
			}

			if (request != null) {
				if (request.getTemplateName() == null || request.getTemplateName().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Template Name is null");
					return null;
				}

				if (request.getLogo() == null || request.getLogo().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Logo is null");
					return null;
				}

				if (request.getSignature() == null || request.getSignature().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Signature is null");
					return null;
				}

				if (request.getCompanyName() == null || request.getCompanyName().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Company name is null");
					return null;
				}

				if (request.getAddressLine1() == null || request.getAddressLine1().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Address line 1 is null");
					return null;
				}

				if (request.getCity() == null || request.getCity().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "City is null");
					return null;
				}

				if (request.getPincode() == null || request.getPincode().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Pincode is null");
					return null;
				}

				if (request.getStateName() == null || request.getStateName().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "State name is null");
					return null;
				}

				if (request.getCin() == null || request.getCin().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "CIN number is null");
					return null;
				}

				if (request.getDocumentNumber() == null || request.getDocumentNumber().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Document number is null");
					return null;
				}

				if (request.getTelephone() == null || request.getTelephone().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Telephone number is null");
					return null;
				}

				if (request.getGstNumber() == null || request.getGstNumber().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "GST number is null");
					return null;
				}

				if (request.getWebsite() == null || request.getWebsite().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Website is null");
					return null;
				}

				if (request.getFooterline1() == null || request.getFooterline1().equalsIgnoreCase("")) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Footer line 1 is null");
					return null;
				}
				
				QuotePdfHeaderAndFooterRequest template = dbQuoteHeaderAndFooter.updateTemplate(request,user);

				List<TermsAndConditionsResponse> termsAndConditions = dbTermsAndCondition.getByObjectNameAndActiveOrderBySerialNumberAsc("Quote",true);
				QuotePdfHeaderAndFooterResponse reasponse = dbQuoteHeaderAndFooter.getById(template.getId());
				if(reasponse!=null) {
					reasponse.setTermsAndConditions(termsAndConditions);
				}
				/*
				List<StateWithQuotePdf> states = dbStatesWithPdf.getStatesByTemplateId(template.getId());
				if(states!=null) {
					reasponse.setStateList(states);
				}*/
				
				return reasponse;
			} else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Request is null");
				return null;
			}
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		} catch (UnauthoriseException ex) {
			ex.printStackTrace();
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
	
	
	
	@PutMapping("enable")
	public QuotePdfHeaderAndFooterResponse enableAndDisableTemplate(@RequestBody QuoteTemplateEnableAndDisableRequest request,
			@CurrentUser LoginUser user, HttpServletResponse response) throws IOException{
		try {
			if (!dbProfile.hasObjectAccess(user.getUsername(), "QuotePdfHeaderAndFooter", "editRights")) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
				return null;
			}

			if (request != null) {
				
				if (request.getId() == 0) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "id is invalid");
					return null;
				}
				

				QuotePdfHeaderAndFooter template = dbQuoteHeaderAndFooter.findById(request.getId());
				if(template==null) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Template does not exist");
					return null;	
				}
				
				template.setModifiedBy(new EmployeeDetails(user.getUsername()));
				template.setModifiedDate(new Date());
				template.setActive(request.isActive());

				dbQuoteHeaderAndFooter.save(template);

				List<TermsAndConditionsResponse> termsAndConditions = dbTermsAndCondition.getByObjectNameAndActiveOrderBySerialNumberAsc("Quote",true);
				QuotePdfHeaderAndFooterResponse reasponse = dbQuoteHeaderAndFooter.getById(template.getId());
				if(reasponse!=null) {
					reasponse.setTermsAndConditions(termsAndConditions);
				}
				
				List<StateWithQuotePdf> states = dbStatesWithPdf.getStatesByTemplateId(template.getId());
				if(states!=null) {
					reasponse.setStateList(states);
				}
				
				return reasponse;
			} else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Request is null");
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		

	}

}
