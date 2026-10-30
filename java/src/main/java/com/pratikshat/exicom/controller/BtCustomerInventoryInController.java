package com.pratikshat.exicom.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.config.FileStorageProperties;
import com.pratikshat.exicom.entity.BulkUploadAction;
import com.pratikshat.exicom.entity.Bulkupload;
import com.pratikshat.exicom.entity.BulkuploadFileDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.WebMessage;
import com.pratikshat.exicom.repository.BRCCasePdfGen;
import com.pratikshat.exicom.services.BtCustomerInventoryInService;
import com.pratikshat.exicom.services.BulkuploadService;
import com.pratikshat.exicom.utils.BtCustomerInventoryFileParser;
import com.pratikshat.exicom.utils.DateConverter;
import com.pratikshat.exicom.utils.ExcelParser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("bt/api/customerinventoryin")
public class BtCustomerInventoryInController {
    @Autowired
    BtCustomerInventoryInService dbIn;

    @Autowired
	SimpMessagingTemplate websocket;
	
	@Value("${app.bulkupload.location}")
	String bulkuploadLocation;
	
	@Autowired
	BulkuploadService dbBulkUploadService;

	@Autowired
	BRCCasePdfGen pdf;
	
	@Autowired
	FileStorageProperties fileStorageProperties;

    @GetMapping
    public Page<Map<String,Object>> getPage(@CurrentUser LoginUser user,Pageable page,@RequestParam(value="filtername",required = false)String filtername) throws BadRequestException,UnauthoriseException{
		if(filtername==null||filtername.equalsIgnoreCase("")||filtername.equalsIgnoreCase("all"))
        	return dbIn.getPage(page, user);
		else 
			return dbIn.getPageByStatus(user, page, filtername);
    }

    @GetMapping("{id}")
    public Map<String,Object> getSingle(@PathVariable("id")Long id,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbIn.getSingle(id, user);
    }

    @PostMapping
    public Map<String,Object> createIn(@RequestBody Map<String,Object> request,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        return dbIn.saveObject(request, user);
    }

	@PostMapping("outinventory")
	public Map<String,Object> outInventory(@RequestBody Map<String,Object> request,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
		return dbIn.out(request, user);
	}


    @PutMapping
    public Map<String,Object> update(@RequestBody Map<String,Object> request,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException {
        return dbIn.updateObject(request, user);
    }

    @GetMapping("advancesearch")
    public List<Map<String,Object>> advanceSearch(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException {
        return dbIn.advanceSearch(request, user);
    }

    @PostMapping("bulkupload")
    public ResponseEntity<?> bulkUpload(@CurrentUser LoginUser user,MultipartFile file) throws BadRequestException,UnauthoriseException,IOException,Exception{

        BulkuploadFileDetails inPutFile=new BulkuploadFileDetails();
			inPutFile.setFileType(file.getContentType());
			inPutFile.setSize(file.getSize());
			inPutFile.setFileName(file.getOriginalFilename());
			dbBulkUploadService.saveBulkuploadDocument(inPutFile, user);
			String finalPath=bulkuploadLocation+File.separator+inPutFile.getId()+"_"+file.getOriginalFilename();
			Files.copy(file.getInputStream(),Paths.get(finalPath));
			
			inPutFile.setPhysicalPath(finalPath);
			inPutFile.setSize(file.getSize());
			dbBulkUploadService.updateFilePath(inPutFile, user, inPutFile.getId());
			
			//BULK UPLOAD ACTION
			BulkUploadAction actn=new BulkUploadAction();
			
            actn.setBulkUpload(new Bulkupload(3L));
			actn.setCreatedDate(new Date());
			actn.setRequestDocumentId(inPutFile.getId());
			actn.setUploadStatus("Processing");

			dbBulkUploadService.saveBulkuploadAction(actn, user, 3L);

            List<Map<String,Object>> lstOfCustomerInventory=new BtCustomerInventoryFileParser().parseIn(new File(finalPath));

            new BtCustomerInventoryBack(lstOfCustomerInventory, user, actn, file.getOriginalFilename()).start();


        return ResponseEntity.ok().body("{}");
    }

	@PostMapping("bulkupload/out")
    public ResponseEntity<?> bulkUploadOut(@CurrentUser LoginUser user,MultipartFile file) throws BadRequestException,UnauthoriseException,IOException,Exception{

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
			
            actn.setBulkUpload(new Bulkupload(4L));
			actn.setCreatedDate(new Date());
			actn.setRequestDocumentId(inPutFile.getId());
			actn.setUploadStatus("Processing");

			dbBulkUploadService.saveBulkuploadAction(actn, user, 4L);

            List<Map<String,Object>> lstOfCustomerInventory=new BtCustomerInventoryFileParser().parseOut(new File(finalPath));

            new BtCustomerInventoryBackOut(lstOfCustomerInventory, user, actn, file.getOriginalFilename()).start();


        return ResponseEntity.ok().body("{}");
    }

    public class BtCustomerInventoryBack extends Thread{
        List<Map<String,Object>> requests;
		LoginUser user;
		BulkUploadAction actn;
		String fileName;
        public BtCustomerInventoryBack(List<Map<String,Object>> requests,LoginUser user,BulkUploadAction actn,String fileName) {
			this.requests=requests;
			this.user=user;
			this.actn=actn;
			this.fileName=fileName;
		}
        public void run(){
            try {
                List<Map<String,Object>> outData=new ArrayList<Map<String,Object>>();
				int total=requests.size();
				int done=0;
				int error=0;
				int pending=total;
				
                for(Map<String,Object> re:requests){
					Map<String,Object> reM=new LinkedHashMap<String,Object>();
                    try {

						reM.put("ASSETS SERIAL NUMBER",re.get("assetsSerialNumber"));
						reM.put("PRODUCT PART CODE", re.get("partCode"));
						reM.put("STAGE", re.get("customerInventoryStage"));
						reM.put("STATUS", re.get("customerInventoryStatus"));
						reM.put("CONTACT NAME", re.get("contactName"));
						reM.put("CONTACT NUMBER", re.get("contactNumber"));
						reM.put("ACCOUNT SAP NUMBER", re.get("accountSapNumber"));
						
						//reM.put("ACCOUNT NAME", re.get("accountName"));
						reM.put("CHALLAN NUMBER", re.get("challanNumber"));
						reM.put("RECEIVED DATE", re.get("receiveDate"));
						reM.put("CIRCLE", re.get("circle"));
						reM.put("CUSTOMER LR NUMBER", re.get("customerLrNumber"));
						reM.put("CUSTOMER COURIER PARTNER NAME", re.get("customerCurierPartnarName"));
						reM.put("CUSTOMER PLANT", re.get("customerPlant"));
						reM.put("PACKING CONDATION", re.get("pacingCondation"));
						reM.put("PHYSICAL DAMAGE", re.get("physicalDamage"));
						reM.put("REMARKS", re.get("remarks"));

						reM.put("RMA NUMBER", re.get("rmaManualId"));

						//COMMENTED FIELDS DEPRECATED AS PER NEW DOCUMENT ON 01-11-2021
						// reM.put("LOT NUMBER", re.get("lotNumber"));
						// reM.put("TRACKING NUMBER", re.get("trackingNumber"));
						// reM.put("DOCKET NUMBER", re.get("docketNumber"));
						// reM.put("DOCKET DATE", re.get("docketDate"));
						
//						System.out.println(re.get("receiveDate").toString()+ " RECEIVE DATE");
						

						dbIn.saveObject(re, user);
						
						reM.put("ID",re.get("customerInventoryInId"));
						reM.put("BRC NUMBER",re.get("customerInventoryInManualId"));
						
						reM.put("UPLOAD STATUS", "Uploaded");
						done++;
						pending--;
						websocket.convertAndSendToUser(user.getUsername(), "/topic/customerinventory", new WebMessage("info", "Total Record "+total+",Total Done "+done+", Total Error "+error+" Total pending "+pending+", Current process for part code "+re.get("partCode")+" and serial number "+re.get("assetsSerialNumber")+" uploaded successfully"));
					}
					catch(Exception ex) {
						ex.printStackTrace();
						reM.put("ID", "");
						reM.put("INVENTORY IN NUMBER", "");
						reM.put("UPLOAD STATUS", "Error "+ex.getMessage());
						error++;
						pending--;
						websocket.convertAndSendToUser(user.getUsername(), "/topic/customerinventory", new WebMessage("error", "Total Record "+total+",Total Done "+done+", Total Error "+error+" Total pending "+pending+", unable to process for part code "+re.get("partCode")+" and serial number "+re.get("assetsSerialNumber")+" due to "+ex.getMessage()));
					}
					outData.add(reM);
                }
                
				ExcelParser ex=new ExcelParser();
				BulkuploadFileDetails outPutFile=new BulkuploadFileDetails();

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
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

	@GetMapping("template")
	public void getTemplate(@RequestParam("type")String type,HttpServletResponse response) throws IOException{
		Workbook wb=new XSSFWorkbook();
		Sheet sheet=wb.createSheet();
		
		CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setColor(Font.COLOR_NORMAL);

        style.setFont(font);
		style.setFillBackgroundColor(IndexedColors.GREEN.index);

		Row headerRow=sheet.createRow(0);
		headerRow.setRowStyle(style);
		if(type.equalsIgnoreCase("in")){
			
			Cell cell0 =headerRow.createCell(0);
			cell0.setCellValue("ASSETS SERIAL NUMBER");
			cell0.setCellStyle(style);


			Cell cell1 = headerRow.createCell(1);
			cell1.setCellValue("PRODUCT PART CODE");
			cell1.setCellStyle(style);

			Cell cell2= headerRow.createCell(2);
			cell2.setCellValue("Stage	");
			cell2.setCellStyle(style);


			Cell cell3=headerRow.createCell(3);
			cell3.setCellValue("Status	");
			cell3.setCellStyle(style);

			Cell warehouse=headerRow.createCell(4);
			warehouse.setCellValue("Warehouse Name");
			warehouse.setCellStyle(style);

			Cell cell4=headerRow.createCell(5);
			cell4.setCellValue("Contact Name");
			cell4.setCellStyle(style);

			Cell cell5=headerRow.createCell(6);
			cell5.setCellValue("Contact No");
			cell5.setCellStyle(style);

			Cell cell6=headerRow.createCell(7);
			cell6.setCellValue("Customer");
			cell6.setCellStyle(style);

			Cell cell7 = headerRow.createCell(8);
			cell7.setCellValue("Receive Challan No");
			cell7.setCellStyle(style);

			Cell cell8 = headerRow.createCell(9);
			cell8.setCellValue("Receive Challan date (YYYY-MM-DD)");
			cell8.setCellStyle(style);

			Cell cell9 = headerRow.createCell(10);
			cell9.setCellValue("Circle Name");
			cell9.setCellStyle(style);

			Cell cell10=headerRow.createCell(11);
			cell10.setCellValue("Location");

			Cell cell11=headerRow.createCell(12);
			cell11.setCellValue("Customer LR NO");
			cell11.setCellStyle(style);

			Cell cell12 = headerRow.createCell(13);
			cell12.setCellValue("Customer Courier Partner Name");
			cell12.setCellStyle(style);

			Cell cell13 =headerRow.createCell(14);
			cell13.setCellValue("Customer Plant");
			cell13.setCellStyle(style);

			Cell cell14 = headerRow.createCell(15);
			cell14.setCellValue("Receive Date (YYYY-MM-DD)");
			cell14.setCellStyle(style);

			Cell cell15 = headerRow.createCell(16);
			cell15.setCellValue("Packing condition");
			cell15.setCellStyle(style);

			Cell cell16 = headerRow.createCell(17);
			cell16.setCellValue("Physical Damage If Any");
			cell16.setCellStyle(style);

			Cell cell17 = headerRow.createCell(18);
			cell17.setCellValue("Remarks(Receipt)");
			cell17.setCellStyle(style);

			
			Cell cell18 = headerRow.createCell(19);
			cell18.setCellValue("RMA NUMBER");
		//	cell2.setCellStyle(style);

			
			
		}else if (type.equalsIgnoreCase("out")){
			Cell cell0=headerRow.createCell(0);
			cell0.setCellValue("BRC Case No.");
			cell0.setCellStyle(style);
			Cell cell1=headerRow.createCell(1);
			cell1.setCellValue("DISPATCHED DATE (YYYY-MM-DD)");
			cell1.setCellStyle(style);
			Cell cell2=headerRow.createCell(2);
			cell2.setCellValue("EXICOM DOCUMENTATION NO");
			cell2.setCellStyle(style);

			Cell cell3 =headerRow.createCell(3);
			cell3.setCellValue("COURIER PARTNER");
			cell3.setCellStyle(style);

			Cell cell4=headerRow.createCell(4);
			cell4.setCellValue("EXICOM DELIVERY CHALLAN NUMBER");
			cell4.setCellStyle(style);

			Cell cell5 =headerRow.createCell(5);
			cell5.setCellValue("GRN NO");
			cell5.setCellStyle(style);

			Cell cell6 = headerRow.createCell(6);
			cell6.setCellValue("OUT GRN DATE (YYYY-MM-DD)");
			cell6.setCellStyle(style);

			 Cell cell7= headerRow.createCell(7);
			 cell7.setCellValue("E-WAY BILL NO");
			 cell7.setCellStyle(style);


			// Cell cell3=headerRow.createCell(3);
			// cell3.setCellValue("PRODUCT STATUS (1 FOR OK 2 FOR FAULTY)");
			// cell3.setCellStyle(style);

			// Cell cell4 = headerRow.createCell(4);
			// cell4.setCellValue("TRACKING NUMBER");
			// cell4.setCellStyle(style);

			// Cell cell12=headerRow.createCell(5);
			// cell12.setCellValue("BRC NUMBER");
		}

		response.addHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=CUSTOMER_INVENTORY_RMA_TEMPLATE_"+type+".xlsx");
		response.setContentType("application/vnd.ms-excel");
		wb.write(response.getOutputStream());
		wb.close();
		
	}

	public class BtCustomerInventoryBackOut extends Thread{
        List<Map<String,Object>> requests;
		LoginUser user;
		BulkUploadAction actn;
		String fileName;
        public BtCustomerInventoryBackOut(List<Map<String,Object>> requests,LoginUser user,BulkUploadAction actn,String fileName) {
			this.requests=requests;
			this.user=user;
			this.actn=actn;
			this.fileName=fileName;
		}
        public void run(){
            try {
                List<Map<String,Object>> outData=new ArrayList<Map<String,Object>>();
				int total=requests.size();
				int done=0;
				int error=0;
				int pending=total;
				
                for(Map<String,Object> re:requests){
					Map<String,Object> reM=new LinkedHashMap<String,Object>();
                    try {

						reM.put("BRC Case No.", re.get("manualid"));
						reM.put("DISPATCHED DATE",re.get("actualOutDate"));
						reM.put("EXICOM DOCUMENTATION NO", re.get("outLrDocNumber"));
						reM.put("COURIER PARTNER", re.get("outCourierPartner"));
						reM.put("EXICOM DELIVERY CHALLAN NUMBER", re.get("outdeleveryChallanNumber"));
						reM.put("GRN No", re.get("outGrnNumber"));
						reM.put("OUT GRN DATE", re.get("outGrnDate"));
						reM.put("E-WAY BILL NO", re.get("trackingNumber"));
						re.put("bulk", "bulkUpload");
						dbIn.out(re, user);
						
						reM.put("OUT ID",re.get("outId"));
						reM.put("INVENTORY OUT NUMBER", re.get("outManualId"));
						reM.put("STATUS", "Uploaded");
						done++;
						pending--;
						websocket.convertAndSendToUser(user.getUsername(), "/topic/customerinventory", new WebMessage("info", "Total Record "+total+",Total Done "+done+", Total Error "+error+" Total pending "+pending+", Current process for part code "+re.get("partCode")+" and serial number "+re.get("assetsSerialNumber")+" uploaded successfully"));
					}
					catch(Exception ex) {
						reM.put("OUT ID", "");
						reM.put("INVENTORY OUT NUMBER", "");
						reM.put("STATUS", "Error "+ex.getMessage());
						error++;
						pending--;
						websocket.convertAndSendToUser(user.getUsername(), "/topic/customerinventory", new WebMessage("error", "Total Record "+total+",Total Done "+done+", Total Error "+error+" Total pending "+pending+", unable to process for part code "+re.get("partCode")+" and serial number "+re.get("assetsSerialNumber")+" due to "+ex.getMessage()));
					}
					outData.add(reM);
                }
                
				ExcelParser ex=new ExcelParser();
				BulkuploadFileDetails outPutFile=new BulkuploadFileDetails();

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
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

	@GetMapping("downloadpdf/{id}")
	public void downloadPdf(@PathVariable("id")Long id,HttpServletResponse response) throws IOException,BadRequestException,UnauthoriseException{
		response.addHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=BRC_CASE_"+id+".pdf");
		response.setContentType("application/pdf");
		pdf.getBRCPdf(id, response.getOutputStream(),fileStorageProperties);
		response.flushBuffer();
	}

	@PutMapping(value="updateoutgrn/{id}")
	public Map<String,Object> updateGrnOut(@PathVariable Long id, @RequestBody Map<String,Object> request,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException {
		return dbIn.updateOutGrn(id, request, user);
	}

}
