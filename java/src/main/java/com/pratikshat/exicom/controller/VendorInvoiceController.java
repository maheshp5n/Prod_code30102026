package com.pratikshat.exicom.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.pratikshat.exicom.entity.CircleDetails;
import com.pratikshat.exicom.entity.ExpenseManagement;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.VendorInvoiceDetails;
import com.pratikshat.exicom.entity.VendorInvoiceGrn;
import com.pratikshat.exicom.entity.VendorInvoiceSap;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.VendorInvoiceCommercialRequest;
import com.pratikshat.exicom.model.request.VendorInvoiceRequest;
import com.pratikshat.exicom.model.request.VendorInvoiceSapRequest;
import com.pratikshat.exicom.model.response.CircleDetailsResponse;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.repository.VendorInvoiceDetailsRepository;
import com.pratikshat.exicom.repository.VendorInvoicePDFGeneration;
import com.pratikshat.exicom.repository.VendorPoDetailsRepository;
import com.pratikshat.exicom.repository.WccDetailsRepository;
import com.pratikshat.exicom.services.VendorInvoiceService;

@RestController
@RequestMapping("api/vendorinvoice")
public class VendorInvoiceController {
	@Autowired
	VendorPoDetailsRepository dbPo;
	@Autowired
	VendorInvoiceDetailsRepository dbInvoice;
	@Autowired
	UserProfileRepository dbUserprofile;
	@Autowired
	WccDetailsRepository dbWcc;
	
	@Autowired
	VendorInvoicePDFGeneration pdfgen;
	
	@Autowired
	VendorInvoiceService vendorInvoiceService;
	
	int businessDomain=2;
	private static final Logger logger=LoggerFactory.getLogger(VendorInvoiceController.class); 
	
	
	@GetMapping("potypes")
	public List<Map<String,Object>> getPoTypes(){
		return dbPo.getAllPoType();
	}
	@GetMapping("pocategory")
	public List<Map<String,Object>> getCategory(){
		return dbPo.getPoCategory();
	}
	@GetMapping("po/{typeid}")
	public List<Map<String,Object>> getActivePoByTypeId(@PathVariable("typeid")Integer typeId,@CurrentUser LoginUser user){
		return dbPo.getPendingPoByVendorCodeAndPoTypeAndValidDate(user.getUsername(), typeId);
	}
	@GetMapping("wccpendingtoinvoice")
	public List<Map<String,Object>> getPendingToInvoiceWcc(@CurrentUser LoginUser user){
		return dbWcc.getPendingToInvoiceWccByUser(user.getUsername());
	}
	@PostMapping
	public VendorInvoiceRequest saveInvoice(@RequestBody VendorInvoiceRequest req,@CurrentUser LoginUser user,HttpServletResponse response)throws IOException{
		if(!dbUserprofile.hasObjectAccess(user.getUsername(), VendorInvoiceDetails.class, UserProfileRepository.CREATE)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			
			return null;
		}
		try {
			dbPo.saveVendorInvoice(req, user);
			return req;
		}catch(BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
		}catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,ex.getMessage());
		}
		return null;
	}
	@GetMapping("{id}")
	public Map<String,Object> getVendorInvoiceById(@PathVariable("id")Long id,@CurrentUser LoginUser user){
		if(dbUserprofile.hasObjectAccess(user.getUsername(), VendorInvoiceDetails.class,UserProfileRepository.VIEW_ALL)||dbUserprofile.hasObjectAccess(user.getUsername(), VendorInvoiceDetails.class,UserProfileRepository.VIEW)) {
		 return	dbInvoice.getVendorInvoiceById(id);
		}
		return new HashedMap<String,Object>();
	}
	@GetMapping
	public Page<Map<String,Object>> getVendorInvoice(@CurrentUser LoginUser user,Pageable page,@RequestParam(value="filtername",required=false)Integer filtername){
		if(dbUserprofile.hasObjectAccess(user.getUsername(), VendorInvoiceDetails.class, UserProfileRepository.VIEW_ALL)) {
			if(filtername!=null&&filtername!=0)
				return dbInvoice.getVendorInvoiceList(filtername,page);
			return dbInvoice.getVendorInvoiceList(page);
		}
		if(dbUserprofile.hasObjectAccess(user.getUsername(), VendorInvoiceDetails.class, UserProfileRepository.VIEW_ALL)||dbUserprofile.hasObjectAccess(user.getUsername(), VendorInvoiceDetails.class, UserProfileRepository.VIEW)) {
			List<String> userList=dbUserprofile.getListOfUserAndChildByUser(user.getUsername());
			if(filtername!=null&&filtername!=0)
				return dbInvoice.getVendorInvoiceListByOwners(userList,filtername,page);
			return dbInvoice.getVendorInvoiceListByOwners(userList,page);
		}
		return Page.empty();
	}
	@GetMapping("myinvoice")
	public Page<Map<String,Object>> getVendorInvoiceByUser(@CurrentUser LoginUser user,Pageable page){
		if(dbUserprofile.hasObjectAccess(user.getUsername(), VendorInvoiceDetails.class, UserProfileRepository.VIEW_ALL)||dbUserprofile.hasObjectAccess(user.getUsername(), VendorInvoiceDetails.class, UserProfileRepository.VIEW)) {
			
			return dbInvoice.getVendorInvoiceListByOwner(user.getUsername(),page);
		}
		return Page.empty();
	}
	@PutMapping("{id}")
	public Map<String,Object> updateCommercial(@PathVariable("id")Long id,@RequestBody VendorInvoiceCommercialRequest req,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		
		logger.info("Pathvarial {}",id);
		logger.info("Body {}",req.getVendorInvoiceId());
		
		if(!id.equals(req.getVendorInvoiceId())) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid id");
			return null;
		}
		if(!dbUserprofile.hasObjectAccess(user.getUsername(), VendorInvoiceGrn.class, UserProfileRepository.CREATE)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			dbPo.updateGrnDetails(req, user);
			return dbInvoice.getVendorInvoiceById(id);
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		} catch (UnauthoriseException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,e.getMessage());
		}
		return null;
	}
	//bulkupdatecommercials starts
	@PutMapping("bulkupdatecommercials")
	public ResponseEntity<?> bulkUpdateCommercials(@RequestParam(value = "file") MultipartFile file, @CurrentUser LoginUser user,
												   HttpServletResponse response) throws IOException {
		if(!dbUserprofile.hasObjectAccess(user.getUsername(), VendorInvoiceGrn.class, UserProfileRepository.CREATE)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}

		ByteArrayOutputStream st = null;
		try{
			st = vendorInvoiceService.bulkUploadInvoiceCommericalDetails(file,user);
		}
		catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}

		response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=GrnbulkupdateResult.xlsx");
		response.setContentType("application/vnd.ms-excel");
		response.getOutputStream().write(st.toByteArray());
		response.getOutputStream().flush();
		response.getOutputStream().close();
		return null;
	}
	
	@GetMapping("/templateGRNUpdate")
	public ResponseEntity<ByteArrayResource> getTemplateGRN(HttpServletResponse httpResponse)throws IOException {
		HttpHeaders header = new HttpHeaders();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("GRNUpdate");
		Object[][] bookData = { { "CRM Invoice No." , "GRN Number" ,"GRN Date (YYYY-MM-DD)", 
			"Service Entry Number", "Internal Service Code" },};

		int rowCount = 0;
		
		CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(Font.COLOR_RED);
        style.setFont(font);

		for (Object[] aBook : bookData) {
			Row row = sheet.createRow(rowCount);

			int columnCount = 0;

			for (Object field : aBook) {
				Cell cell = row.createCell(columnCount++);
				sheet.autoSizeColumn(columnCount);
				if (field instanceof String) {
					cell.setCellValue((String) field);
					cell.setCellStyle(style);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
					cell.setCellStyle(style);
				}
			}

		}

		header.setContentType(new MediaType("application", "force-download"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "GRNUpdateTemplate.xlsx");
		httpResponse.setContentType("application/xlsx");
		ByteArrayOutputStream bytest = new ByteArrayOutputStream();
		workbook.write(bytest);
		FileOutputStream fileOut = new FileOutputStream("GRNUpdateTemplate.xlsx");
		return new ResponseEntity<>(new ByteArrayResource(bytest.toByteArray()), header, HttpStatus.OK);
	}
	
	//end

	@PutMapping("{id}/sap")
	public Map<String,Object> uddateSap(@PathVariable("id")Long id,@RequestBody VendorInvoiceSapRequest req,@CurrentUser LoginUser user,HttpServletResponse response)throws IOException{
		if(!id.equals(req.getVendorInvoiceId())) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid Id");
			return null;
		}
		if(!dbUserprofile.hasObjectAccess(user.getUsername(),VendorInvoiceSap.class, UserProfileRepository.CREATE)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			
			dbPo.updateVendorSapDetails(req, user);
			return dbInvoice.getVendorInvoiceById(id);
		}catch(BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
		}
		catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
		}
		return null;
	}
	@PostMapping("invoicecopy")
	public VendorInvoiceRequest saveInvoiceWithInvoiceCopy(@RequestPart("data") VendorInvoiceRequest req,@RequestPart("file") MultipartFile invoice ,@CurrentUser LoginUser user,HttpServletResponse response)throws IOException{
		if(!dbUserprofile.hasObjectAccess(user.getUsername(), VendorInvoiceDetails.class, UserProfileRepository.CREATE)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			
			return null;
		}
		try {
			dbPo.saveVendorInvoiceWithCopy(req, user, invoice);
			return req;
		}catch(BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
		}catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,ex.getMessage());
		}
		return null;
	}
	
	@PostMapping("invoicecopy/v1")
	public VendorInvoiceRequest saveInvoiceWithInvoiceCopy(@RequestParam(value="file",required=false) MultipartFile invoice,
			@RequestParam("wccid")Long wccId,
			@RequestParam("vendorInvoiceNumber")String vendorInvoiceNumber,
			@RequestParam("vendorInvoiceDate")String vendorInvoiceDate,
			@RequestParam("remarks")String remarks,
			@CurrentUser LoginUser user,HttpServletResponse response)throws IOException{
		
		VendorInvoiceRequest req=new VendorInvoiceRequest();
		req.setWccId(wccId);
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			req.setVendorInvoiceDate(format.parse(vendorInvoiceDate));
		} catch (ParseException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unable to parse date");
			
			e.printStackTrace();
			return null;
		}
		req.setVendorInvoiceNumber(vendorInvoiceNumber);
		req.setRemarks(remarks);
		
		if(!dbUserprofile.hasObjectAccess(user.getUsername(), VendorInvoiceDetails.class, UserProfileRepository.CREATE)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			dbPo.saveVendorInvoiceWithCopy(req, user, invoice);
			return req;
		}catch(BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
		}catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,ex.getMessage());
		}
		return null;
	}
	@PutMapping("{id}/update")
	public VendorInvoiceRequest udpateVendorInvoice(
			@PathVariable("id")Long id,
			@RequestParam(value="file",required=false) MultipartFile invoice,
			@RequestParam("wccid")Long wccId,
			@RequestParam("vendorInvoiceNumber")String vendorInvoiceNumber,
			@RequestParam("vendorInvoiceDate")String vendorInvoiceDate,
			@RequestParam("remarks")String remarks,
			@CurrentUser LoginUser user,HttpServletResponse response)throws IOException{
		if(!dbUserprofile.hasObjectAccess(user.getUsername(), VendorInvoiceDetails.class, UserProfileRepository.EDIT)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorise");
			return null;
		}
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		VendorInvoiceRequest req=new VendorInvoiceRequest();
		req.setId(id);
		req.setRemarks(remarks);
		try {
			req.setVendorInvoiceDate(format.parse(vendorInvoiceDate));	
		}
		catch(Exception ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}
		req.setVendorInvoiceNumber(vendorInvoiceNumber);
		req.setWccId(wccId);
		try {
			
			dbPo.updateVendorInvoiceWithCopy(req, user, invoice);
			return req;
		}catch(BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
		}
		catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,ex.getMessage());
		}
		
		return null;
	}
	@PostMapping("submitforapproval/{id}")
	public Map<String,Object> submitForApproval(@PathVariable("id")Long id,@CurrentUser LoginUser user,HttpServletResponse response)throws IOException{
		
		if(!dbUserprofile.hasObjectAccess(user.getUsername(), VendorInvoiceDetails.class, UserProfileRepository.CREATE)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorise");
			return null;
		}
		try {
			
			dbPo.submitforApproval(id, user);
			
			return dbInvoice.getVendorInvoiceById(id);
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,e.getMessage());
		} catch (UnauthoriseException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	@GetMapping("internalserviceocode")
	public List<Map<String,Object>> getInternalServiceCode(){
		return dbInvoice.getInternalServiceCode(businessDomain);
	}
	
	@GetMapping("vendorinvoicepdf/{id}")
	public void getPdf(@PathVariable("id")Long id,@CurrentUser LoginUser user,HttpServletResponse response) throws Exception{
		
		response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendorinvoice_"+id+".pdf");
		response.setContentType("application/pdf");
		response.getOutputStream().write(pdfgen.generatePDF(id, user).toByteArray());
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
	@GetMapping("advancesearch")
	public List<Map<String,Object>> getAdvanceSearchresult(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException{
		
		if(dbUserprofile.hasObjectAccess(user.getUsername(), VendorInvoiceDetails.class, UserProfileRepository.VIEW_ALL)) {
			return dbPo.advanceSearch(request, user, null);
		}
		
		if(dbUserprofile.hasObjectAccess(user.getUsername(), VendorInvoiceDetails.class, UserProfileRepository.VIEW)) {
			return dbPo.advanceSearch(request, user, dbUserprofile.getListOfUserAndChildByUser(user.getUsername()));
		}
		
		if(dbUserprofile.hasObjectAccess(user.getUsername(), VendorInvoiceDetails.class, UserProfileRepository.VIEW) || 
				dbUserprofile.hasObjectAccess(user.getUsername(), VendorInvoiceDetails.class, UserProfileRepository.VIEW_ALL)) {
			return dbPo.advanceSearch(request, user, null);
		}
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		return null;
		
	}
	
	@GetMapping("status/forlist")
	public java.util.List<String> getInvoiceStatusList() {
		return dbInvoice.getInvoiceStatusList();
	}
}
