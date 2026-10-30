package com.pratikshat.exicom.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.BulkUploadAction;
import com.pratikshat.exicom.entity.Bulkupload;
import com.pratikshat.exicom.entity.BulkuploadFileDetails;
import com.pratikshat.exicom.entity.CustomerInventory;
import com.pratikshat.exicom.entity.CustomerInventoryOut;
import com.pratikshat.exicom.entity.ExpenseManagement;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.request.CustomerInventoryOutWardRequest;
import com.pratikshat.exicom.model.request.CustomerInventoryRequest;
import com.pratikshat.exicom.model.response.ExpenseManagementResponse;
import com.pratikshat.exicom.model.response.WebMessage;
import com.pratikshat.exicom.repository.CustomerInventoryRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BulkuploadService;
import com.pratikshat.exicom.utils.CustomerInventoryFileParser;
import com.pratikshat.exicom.utils.ExcelParser;

@RestController
@RequestMapping("api/customerinventory")
public class CustomerInventoryController {
	
	@Autowired
	CustomerInventoryRepository dbCustomerInventory;
	@Autowired
	UserProfileRepository dbUserprofile;
	
	@Autowired
	SimpMessagingTemplate websocket;
	
	@Value("${app.bulkupload.location}")
	String bulkuploadLocation;
	
	@Autowired
	BulkuploadService dbBulkUploadService;
	
	@GetMapping
	public Page<Map<String,Object>> getAllCustomerInventory(@CurrentUser LoginUser user,HttpServletResponse response,Pageable page) throws IOException{
		if(dbUserprofile.hasObjectAccess(user.getUsername(), CustomerInventory.class, UserProfileRepository.VIEW_ALL))
			return dbCustomerInventory.getCustomerAllInventory(page);
		if(dbUserprofile.hasObjectAccess(user.getUsername(), CustomerInventory.class, UserProfileRepository.VIEW))
			return dbCustomerInventory.getCustomerInventoryIn(dbUserprofile.getListOfUserAndChildByUser(user.getUsername()), page);
		return Page.empty();
	}
	
	@PostMapping
	public ResponseEntity<?> bulkUdpated(@RequestParam(value="file")MultipartFile file,@CurrentUser LoginUser user,HttpServletResponse response)throws IOException{
		if(!dbUserprofile.hasObjectAccess(user.getUsername(), CustomerInventory.class, UserProfileRepository.CREATE)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorise");
			return null;
		}
		
		try {
			
			//BULK UPLOAD FILE DECORATION
			
			BulkuploadFileDetails inPutFile=new BulkuploadFileDetails();
			inPutFile.setFileType(file.getContentType());
			inPutFile.setSize(file.getSize());
			inPutFile.setFileName(file.getOriginalFilename());
			dbBulkUploadService.saveBulkuploadDocument(inPutFile, user);
			String finalPath=bulkuploadLocation+File.separator+inPutFile.getId()+"_"+file.getOriginalFilename();
			Files.copy(file.getInputStream(),Paths.get(finalPath));
			//file.transferTo(new File(finalPath));
			inPutFile.setPhysicalPath(finalPath);
			inPutFile.setSize(file.getSize());
			dbBulkUploadService.updateFilePath(inPutFile, user, inPutFile.getId());
			
			//BULK UPLOAD ACTION
			BulkUploadAction actn=new BulkUploadAction();
			actn.setBulkUpload(new Bulkupload(1L));
			actn.setCreatedDate(new Date());
			actn.setRequestDocumentId(inPutFile.getId());
			actn.setUploadStatus("Processing");
			dbBulkUploadService.saveBulkuploadAction(actn, user, 1L);
			
			List<CustomerInventoryRequest> requests=new CustomerInventoryFileParser().parseIn(new File(finalPath));
			//List<CustomerInventoryRequest> requests=new CustomerInventoryFileParser().parseIn(file);
			
			new CustomerInventoryInBack(requests,user,actn,file.getOriginalFilename()).start();
			
		}catch(Exception ex) {
			ex.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Your file has been received and started processing in background");
		return null;
		
	}
	
	@PostMapping("out")
	public void customerInventoryOut(@RequestParam(value="file")MultipartFile file,@CurrentUser LoginUser user,HttpServletResponse response)throws IOException {
		if(!dbUserprofile.hasObjectAccess(user.getUsername(), CustomerInventoryOut.class, UserProfileRepository.CREATE)){
			response.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorise");
			return;
		}
		try {
			BulkuploadFileDetails inPutFile=new BulkuploadFileDetails();
			inPutFile.setFileType(file.getContentType());
			inPutFile.setSize(file.getSize());
			inPutFile.setFileName(file.getOriginalFilename());
			dbBulkUploadService.saveBulkuploadDocument(inPutFile, user);
			String finalPath=bulkuploadLocation+File.separator+inPutFile.getId()+"_"+file.getOriginalFilename();
			Files.copy(file.getInputStream(), Paths.get(finalPath));
			//file.transferTo(new File(finalPath));
			inPutFile.setPhysicalPath(finalPath);
			inPutFile.setSize(file.getSize());
			dbBulkUploadService.updateFilePath(inPutFile, user, inPutFile.getId());
			
			//BULK UPLOAD ACTION
			BulkUploadAction actn=new BulkUploadAction();
			actn.setBulkUpload(new Bulkupload(2L));
			actn.setCreatedDate(new Date());
			actn.setRequestDocumentId(inPutFile.getId());
			actn.setUploadStatus("Processing");
			dbBulkUploadService.saveBulkuploadAction(actn, user, 2L);
			
			List<CustomerInventoryOutWardRequest> data =new CustomerInventoryFileParser().parseOut(new File(finalPath));
			//List<CustomerInventoryOutWardRequest> data=new CustomerInventoryFileParser().parseOut(file);
			
			new  CustomerInventoryOutBack(data,user,actn,file.getOriginalFilename()).start();

			
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,e.getMessage());
			return;
			
		}
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Your file has been received and started processing in background");
	}
	
	@GetMapping("template")
	public void getTemplate(@RequestParam("type")String type,HttpServletResponse response) throws IOException {
		Workbook wb=new XSSFWorkbook();
		Sheet sheet=wb.createSheet();
		
		CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setColor(Font.COLOR_RED);
        style.setFont(font);
		
		Row headerRow=sheet.createRow(0);
		if(type.equalsIgnoreCase("in")) {
			Cell cell0 = headerRow.createCell(0);
			cell0.setCellValue("Account SAP");
			cell0.setCellStyle(style);
			
			Cell cell1 = headerRow.createCell(1);
			cell1.setCellValue("Circle");
			cell1.setCellStyle(style);
			
			Cell cell2 = headerRow.createCell(2);
			cell2.setCellValue("Customer Challan Date");
			
			Cell cell3 = headerRow.createCell(3);
			cell3.setCellValue("Customer Challan Number");
			
			Cell cell4 = headerRow.createCell(4);
			cell4.setCellValue("Customer Docket Number");
			
			Cell cell5 = headerRow.createCell(5);
			cell5.setCellValue("Product Part Code");
			cell5.setCellStyle(style);
			
			Cell cell6 = headerRow.createCell(6);
			cell6.setCellValue("Product Serial Number");
			cell6.setCellStyle(style);
			
			Cell cell7 = headerRow.createCell(7);
			cell7.setCellValue("Received Date");
			cell7.setCellStyle(style);
			
			Cell cell8 = headerRow.createCell(8);
			cell8.setCellValue("Remarks");
			
			Cell cell9 = headerRow.createCell(9);
			cell9.setCellValue("Warranty Status");
			
			Cell cell10 = headerRow.createCell(10);
			cell10.setCellValue("Lot Number");
			
//			headerRow.createCell(0).setCellValue("Account SAP");
//			headerRow.createCell(1).setCellValue("Circle");
//			headerRow.createCell(2).setCellValue("Customer Challan Date");
//			headerRow.createCell(3).setCellValue("Customer Challan Number");
//			headerRow.createCell(4).setCellValue("Customer Docket Number");
//			headerRow.createCell(5).setCellValue("Product Part Code");
//			headerRow.createCell(6).setCellValue("Product Serial Number");
//			headerRow.createCell(7).setCellValue("Received Date");
//			headerRow.createCell(8).setCellValue("Remarks");
//			headerRow.createCell(9).setCellValue("Warranty Status");
//			headerRow.createCell(10).setCellValue("Lot Number");
		}
		if(type.equalsIgnoreCase("out")) {
			
			Cell cell0 = headerRow.createCell(0);
			cell0.setCellValue("Dispatch Challan Number");
			
			Cell cell1 = headerRow.createCell(1);
			cell1.setCellValue("Dispatch Docket Number");
			
			Cell cell2 = headerRow.createCell(2);
			cell2.setCellValue("Product Part Code");
			cell2.setCellStyle(style);
			
			Cell cell3 = headerRow.createCell(3);
			cell3.setCellValue("Product Serial Number");
			cell3.setCellStyle(style);
			
			Cell cell4 = headerRow.createCell(4);
			cell4.setCellValue("Lot Number");
			
			Cell cell5 = headerRow.createCell(5);
			cell5.setCellValue("Remarks");
			
			
//			headerRow.createCell(0).setCellValue("Dispatch Challan Number");
//			headerRow.createCell(1).setCellValue("Dispatch Docket Number");
//			headerRow.createCell(2).setCellValue("Product Part Code");
//			headerRow.createCell(3).setCellValue("Product Serial Number");
//			headerRow.createCell(4).setCellValue("Lot Number");
//			headerRow.createCell(5).setCellValue("Remarks");
		}
		
		response.addHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=customerinventory_"+type+".xlsx");
		response.setContentType("application/vnd.ms-excel");
		wb.write(response.getOutputStream());
		wb.close();
	}
	public class CustomerInventoryInBack extends Thread{
		List<CustomerInventoryRequest> requests;
		LoginUser user;
		BulkUploadAction actn;
		String fileName;
		public CustomerInventoryInBack(List<CustomerInventoryRequest> requests,LoginUser user,BulkUploadAction actn,String fileName) {
			this.requests=requests;
			this.user=user;
			this.actn=actn;
			this.fileName=fileName;
		}
		public void run() {
			try {
				List<Map<String,Object>> outData=new ArrayList<Map<String,Object>>();
				int total=requests.size();
				int done=0;
				int error=0;
				int pending=total;
				for(CustomerInventoryRequest re:requests) {
					Map<String,Object> d=new HashMap<String,Object>();
					d.put("Account SAP", re.getAccountSapNumber());
					d.put("Product Part Code", re.getProductPartCode());
					d.put("Product Serial Number", re.getProductSerialNumber());
					d.put("Circle", re.getCircle());
					try {
						dbCustomerInventory.doInward(re, user);
						d.put("STATUS", "Uploaded");
						done++;
						pending--;
						websocket.convertAndSendToUser(user.getUsername(), "/topic/customerinventory", new WebMessage("info", "Total Record "+total+",Total Done "+done+", Total Error "+error+" Total pending "+pending+", Current process for part code "+d.get("PART_CODE")+" and serial number "+d.get("SERIAL_NUMBER")+" uploaded successfully"));
					}catch(Exception ex) {
						d.put("STATUS", "Error "+ex.getMessage());
						error++;
						pending--;
						websocket.convertAndSendToUser(user.getUsername(), "/topic/customerinventory", new WebMessage("error", "Total Record "+total+",Total Done "+done+", Total Error "+error+" Total pending "+pending+", unable to process for part code "+d.get("PART_CODE")+" and serial number "+d.get("SERIAL_NUMBER")+" due to "+ex.getMessage()));
					}
					outData.add(d);
				}
				
				
				ExcelParser ex=new ExcelParser();
				BulkuploadFileDetails outPutFile=new BulkuploadFileDetails();
//				outPutFile.setFileType(file.getContentType());
//				outPutFile.setSize(file.getSize());
				outPutFile.setFileName("result_"+fileName);
				
				dbBulkUploadService.saveBulkuploadDocument(outPutFile, user);
				String outputFinalPath=bulkuploadLocation+File.separator+outPutFile.getId()+"_result_"+fileName;
				
				FileOutputStream o=new FileOutputStream(outputFinalPath);
				o.write(ex.genrateFile(outData).toByteArray());
				o.flush();
				o.close();
				outPutFile.setPhysicalPath(outputFinalPath);
				outPutFile.setSize(new File(outputFinalPath).length());
				dbBulkUploadService.updateFilePath(outPutFile, user, outPutFile.getId());
				actn.setResultDocumentId(outPutFile.getId());
				actn.setUploadStatus("Completed");
				dbBulkUploadService.updateBulkuploadAction(actn, user, actn.getId());
				
				websocket.convertAndSendToUser(user.getUsername(),"/topic/customerinventorydata",outData);
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	public class CustomerInventoryOutBack extends Thread{
		List<CustomerInventoryOutWardRequest> requests;
		LoginUser user;
		String fileName;
		BulkUploadAction actn;
		public CustomerInventoryOutBack(List<CustomerInventoryOutWardRequest> requests,LoginUser user,BulkUploadAction actn,String fileName) {
			this.requests=requests;
			this.user=user;
			this.actn=actn;
			this.fileName=fileName;
		}
		
		public void run() {
			try {
				
				//BULK UPLOAD FILE DECORATION
	
				List<Map<String,Object>> returndAta=new ArrayList<Map<String,Object>>();
				int total=requests.size();
				int done=0;
				int error=0;
				int pending=total;
				
				for(CustomerInventoryOutWardRequest req:requests) {
					Map<String,Object> retdata=new HashMap<String,Object>();
					retdata.put("Dispatch Challan Number",req.getDispatchChallanNumber());
					retdata.put("Dispatch Date",req.getDispatchDate());
					retdata.put("Dispatch Docket Number", req.getDispatchDocketNumber());
					retdata.put("Product Part Code",req.getProductPartcode());
					retdata.put("Product Serial Number",req.getProductSerialNumber());
					retdata.put("Lot Number", req.getLotNo());
					retdata.put("Remarks",req.getRemark());
					try {
						
						dbCustomerInventory.doOutward(req, user);
						retdata.put("STATUS", "updated");
						done++;
						pending--;
						websocket.convertAndSendToUser(user.getUsername(), "/topic/customerinventory", new WebMessage("info", "Total Record "+total+",Total Done "+done+", Total Error "+error+" Total pending "+pending+", Current process for part code "+req.getProductPartcode()+" and serial number "+req.getProductSerialNumber()+" uploaded successfully"));
						
					}catch(Exception ex) {
						retdata.put("STATUS","error -> "+ ex.getMessage());
						error++;
						pending--;
						websocket.convertAndSendToUser(user.getUsername(), "/topic/customerinventory", new WebMessage("error", "Total Record "+total+",Total Done "+done+", Total Error "+error+" Total pending "+pending+", unable to process for part code "+req.getProductPartcode()+" and serial number "+req.getProductSerialNumber()+" due to "+ex.getMessage()));
					}
					returndAta.add(retdata);
					//System.out.println("returnAta"+returndAta.size());
				}
				
				ExcelParser ex=new ExcelParser();
				BulkuploadFileDetails outPutFile=new BulkuploadFileDetails();
				outPutFile.setFileName("result_"+fileName);
				dbBulkUploadService.saveBulkuploadDocument(outPutFile, user);
				String outputFinalPath=bulkuploadLocation+File.separator+outPutFile.getId()+"_result_"+fileName;
				
				FileOutputStream o=new FileOutputStream(outputFinalPath);
				o.write(ex.genrateFile(returndAta).toByteArray());
				o.flush();
				o.close();
				outPutFile.setPhysicalPath(outputFinalPath);
				outPutFile.setSize(new File(outputFinalPath).length());
				
				dbBulkUploadService.updateFilePath(outPutFile, user, outPutFile.getId());
				actn.setResultDocumentId(outPutFile.getId());
				actn.setUploadStatus("Completed");
				dbBulkUploadService.updateBulkuploadAction(actn, user, actn.getId());
				
				websocket.convertAndSendToUser(user.getUsername(),"/topic/customerinventorydata",returndAta);
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	@GetMapping("advancesearch")
	public List<Map<String,Object>> getAdvanceSearchresult(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException{
		if(dbUserprofile.hasObjectAccess(user.getUsername(), CustomerInventory.class, UserProfileRepository.VIEW_ALL)) {
			return dbCustomerInventory.advanceSearch(request, user, null);
		}
		
		if(dbUserprofile.hasObjectAccess(user.getUsername(), CustomerInventory.class, UserProfileRepository.VIEW)) {
			return dbCustomerInventory.advanceSearch(request, user, dbUserprofile.getListOfUserAndChildByUser(user.getUsername()));
		}
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		return null;
	}
}
