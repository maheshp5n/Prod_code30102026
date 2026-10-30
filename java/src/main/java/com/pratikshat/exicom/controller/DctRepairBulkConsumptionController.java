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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.BulkUploadAction;
import com.pratikshat.exicom.entity.Bulkupload;
import com.pratikshat.exicom.entity.BulkuploadFileDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.WebMessage;
import com.pratikshat.exicom.services.BulkuploadService;
import com.pratikshat.exicom.services.DctRepairBulkConsumptionService;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/repair/bulk")
public class DctRepairBulkConsumptionController {

    @Autowired
    DctRepairBulkConsumptionService dbBulkUpload;

    @Autowired
	SimpMessagingTemplate websocket;
	
	@Value("${app.bulkupload.location}")
	String bulkuploadLocation;
	
	@Autowired
	BulkuploadService dbBulkUploadService;

    @GetMapping("template")
    public void getTemplate(@CurrentUser LoginUser user, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();

        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setColor(Font.COLOR_RED);

        style.setFont(font);
        style.setFillBackgroundColor(IndexedColors.GREEN.index);

        Row headerRow = sheet.createRow(0);
        headerRow.setRowStyle(style);

        Cell cell0 = headerRow.createCell(0);
        cell0.setCellValue("Material Partcode");
        cell0.setCellStyle(style);

        Cell cell1 = headerRow.createCell(1);
        cell1.setCellValue("Qty.");
        cell1.setCellStyle(style);

        Cell cell2 = headerRow.createCell(2);
        cell2.setCellValue("Remarks");
        

        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=DCT_REPAIR_BULK_CONSUME.xlsx");
        response.setContentType("application/vnd.ms-excel");
        wb.write(response.getOutputStream());
        wb.close();
    }

    @PostMapping
    public ResponseEntity<?> bulkUpload(@CurrentUser LoginUser user,MultipartFile file) throws BadRequestException,UnauthoriseException,Exception{
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
			
            actn.setBulkUpload(new Bulkupload(7L));
			actn.setCreatedDate(new Date());
			actn.setRequestDocumentId(inPutFile.getId());
			actn.setUploadStatus("Processing");

			dbBulkUploadService.saveBulkuploadAction(actn, user, 7L);

            List<Map<String,Object>> lstOfCustomerInventory= ExcelParser.parseDctRepairBulkConsumedd(new File(finalPath)); //new BtCustomerInventoryFileParser().parseIn(new File(finalPath));

            new DctRepairBulkUploadBack(lstOfCustomerInventory, user, actn, file.getOriginalFilename()).start();


        return ResponseEntity.ok().body("{}");
    }

    public class DctRepairBulkUploadBack extends Thread{
        List<Map<String,Object>> requests;
		LoginUser user;
		BulkUploadAction actn;
		String fileName;
        public DctRepairBulkUploadBack(List<Map<String,Object>> requests,LoginUser user,BulkUploadAction actn,String fileName) {
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

						reM.put("Material Partcode",re.get("consumedProductPartcode"));
						reM.put("Qty.", re.get("consumedQuantity"));
						reM.put("Remarks", re.get("remarks"));

						dbBulkUpload.saveObject(re, user);
						
						reM.put("Id",re.get("id"));
						reM.put("Manual Id",re.get("manualId"));
						
						reM.put("Upload Status", "Uploaded");
						done++;
						pending--;
						websocket.convertAndSendToUser(user.getUsername(), "/topic/customerinventory", new WebMessage("info", "Total Record "+total+",Total Done "+done+", Total Error "+error+" Total pending "+pending+", Current process for part code "+re.get("consumedProductPartcode")+" uploaded successfully"));
					}
					catch(Exception ex) {
						reM.put("Id",re.get("id"));
						reM.put("Manual Id",re.get("manualId"));
						reM.put("Upload Status", "Error "+ex.getMessage());
						error++;
						pending--;
						websocket.convertAndSendToUser(user.getUsername(), "/topic/customerinventory", new WebMessage("error", "Total Record "+total+",Total Done "+done+", Total Error "+error+" Total pending "+pending+", unable to process for part code "+re.get("consumedProductPartcode")+" due to "+ex.getMessage()));
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

}
