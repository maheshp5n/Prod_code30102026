package com.pratikshat.exicom.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.BtVendorInvoiceDetails;
import com.pratikshat.exicom.entity.BtVendorInvoiceSap;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.VendorInvoiceDetails;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtVendorInvoiceDetailsRepository;
import com.pratikshat.exicom.repository.BtVendorInvoicePDFGeneration;
import com.pratikshat.exicom.repository.BtVendorPoDetailsRepository;
import com.pratikshat.exicom.repository.BtWccDetailsRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.repository.VendorInvoiceDetailsRepository;
import com.pratikshat.exicom.services.BtVendorInvoiceDetailsService;
import com.pratikshat.exicom.services.BtVendorInvoiceSapService;
import com.pratikshat.exicom.services.BtWccDetailsService;

@RestController
@RequestMapping("/bt/api/invoice")
public class BtVendorInvoiceDetailsController {

	@Autowired
	BtVendorInvoiceDetailsService serbtVendor;
	@Autowired
	BtVendorPoDetailsRepository dbPo;

	@Autowired
	UserProfileRepository dbUserprofile;
	@Autowired
	BtVendorInvoiceDetailsRepository dbInvoice;
	@Autowired
	BtWccDetailsService serWcc;
	@Autowired
	BtVendorInvoicePDFGeneration pdfgen;
	@Autowired
	BtVendorInvoiceSapService sapService;
	@Autowired
	VendorInvoiceDetailsRepository dbInvoiced;
	
	int businessDomain=1;

	
	@GetMapping("vendorinvoicepdf/{id}")
	public void getPdf(@PathVariable("id")Long id,@CurrentUser LoginUser user,HttpServletResponse response) throws Exception{
		
		response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendorinvoice_"+id+".pdf");
		response.setContentType("application/pdf");
		response.getOutputStream().write(pdfgen.generatePDF(id, user).toByteArray());
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
	@GetMapping("potypes")
	public List<Map<String,Object>> getPoTypes(){
		return serbtVendor.getAllPoType();
	}
	
	@GetMapping("pocategory")
	public List<Map<String,Object>> getCategory(){
		return serbtVendor.getPoCategory();
	}
	
	@GetMapping("wccpendingtoinvoice")
	public List<Map<String,Object>> getPendingToInvoiceWcc(@CurrentUser LoginUser user){
		return serWcc.getPendingToInvoiceWccByUser(user.getUsername());
	}

	@GetMapping("status/forlist")
	public java.util.List<String> getInvoiceStatusList() {
		return serbtVendor.getInvoiceStatusList();
	}
	@PostMapping
	private Map<String, Object> saveObject(@RequestBody Map<String, Object> req, @CurrentUser LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return serbtVendor.saveObject(req, user);
	}

	@PutMapping("{id}")
	private Map<String, Object> updateObject(@RequestBody Map<String, Object> req, @PathVariable("id") long id,
			@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException {
		return serbtVendor.updateObject(req, user);
	}
	
	@GetMapping
	private Page<Map<String, Object>> getResult(@CurrentUser LoginUser user, Pageable page,@RequestParam(value="filtername",required=false)Integer filtername) throws UnauthoriseException,BadRequestException{
		return serbtVendor.getData(user, page, filtername);
	}
	
	@GetMapping("{id}")
	private Map<String, Object> getSingleResult(@PathVariable("id") long id,@CurrentUser LoginUser user)throws BadRequestException,UnauthoriseException{
		return serbtVendor.getSingle(id, user);
	}
	
	@GetMapping("advancesearch") 
	private List<Map<String, Object>> advanceSearch(@RequestParam Map<String, Object> req, @CurrentUser LoginUser user)throws BadRequestException, UnauthoriseException{
		return serbtVendor.advanceSearch(req, user);
	}
	
	@PostMapping("invoicecopy")
	private Map<String, Object> saveInvoiceWithInvoiceCopy(@RequestPart("data") Map<String, Object> req,
			@RequestPart("file") MultipartFile invoice ,@CurrentUser LoginUser user)throws UnauthoriseException, BadRequestException{
		return serbtVendor.saveVendorInvoiceWithCopy(req, user, invoice);
	}
	
	@PutMapping("invoicecopy/{id}")
	public Map<String,Object> updateInvoiceWithInvoiceCopy(@RequestPart("data") Map<String, Object> req,
			@RequestPart("file") MultipartFile invoice ,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return serbtVendor.updateVendorInvoiceWithCopy(req, user,invoice);
	}
	@PutMapping("{id}/grn")
	public Map<String,Object> updateGrnDeatil(@RequestBody Map<String,Object> request,@CurrentUser LoginUser user)throws BadRequestException, UnauthoriseException{
		return serbtVendor.updateGrnDetails(request, user);
	}
	
	@PutMapping("{id}/sap")
	public Map<String,Object> updateVendorInvoieSapDetails(@RequestBody Map<String,Object> request,@CurrentUser LoginUser user)throws BadRequestException, UnauthoriseException{
		if(!dbUserprofile.hasObjectAccess(user.getUsername(),BtVendorInvoiceSap.class, UserProfileRepository.CREATE))
			throw new UnauthoriseException("You are not authorised");
		return sapService.updateObject(request, user);
	
}	
	@GetMapping("internalserviceocode")
	public List<Map<String,Object>> getInternalServiceCode(){
		return dbInvoiced.getInternalServiceCode(businessDomain);
	}
	
	@PostMapping("sap")
	public Map<String,Object> saveVendorInvoieSapDetails(@RequestBody Map<String,Object> request,@CurrentUser LoginUser user)throws BadRequestException, UnauthoriseException{
		return sapService.saveObject(request, user);
	}

	@PostMapping("submitforapproval/{id}")
	public String submitForApproval(@CurrentUser LoginUser user, @PathVariable("id")Long id) throws BadRequestException,UnauthoriseException{
		serbtVendor.submitForApproval(user, id);
		return "{}";
	}
	
	
}	
