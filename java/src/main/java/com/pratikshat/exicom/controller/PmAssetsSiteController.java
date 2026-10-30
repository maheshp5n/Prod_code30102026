package com.pratikshat.exicom.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.pratikshat.exicom.services.PmSiteAssetsService;
import com.pratikshat.exicom.utils.ExcelParser;
import com.pratikshat.exicom.utils.FileParser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("bt/api/pmsiteassets")
public class PmAssetsSiteController {
    @Autowired
    PmSiteAssetsService dbPmSite;
    @Autowired
    BulkuploadService dbBulkUploadService;

    @Value("${app.bulkupload.location}")
    String bulkuploadLocation;

    @Autowired
    SimpMessagingTemplate websocket;

    @GetMapping("site/{siteid}")
    public List<Map<String, Object>> getListBySiteid(@PathVariable("siteid") Long siteId) {
        return dbPmSite.checkWithSiteDetails(siteId);

    }

    @GetMapping("assets")
    public List<Map<String, Object>> getListByAssetsid(@RequestParam("assetssapnumber") String sapNumber) {
        return dbPmSite.checkWithAssets(sapNumber);
    }

    @GetMapping("siteiwthasses/{siteid}")
    public List<Map<String, Object>> getAssetsWithSite(@PathVariable("siteid") Long siteid,
            @RequestParam("assetssapnumber") String assetsSapNumber,@RequestParam(value = "condation",required = false)String condation) {
        
        return dbPmSite.checkWithAssetsAndSite(assetsSapNumber, siteid,condation==null?"":condation);
    }

    @PostMapping
    public Map<String, String> bulkUploadOfFile(MultipartFile file, @CurrentUser LoginUser user) throws Exception {
        Map<String, String> msg = new HashMap<>();
        BulkuploadFileDetails inPutFile = new BulkuploadFileDetails();
        inPutFile.setFileType(file.getContentType());
        inPutFile.setSize(file.getSize());
        inPutFile.setFileName(file.getOriginalFilename());
        dbBulkUploadService.saveBulkuploadDocument(inPutFile, user);
        String finalPath = bulkuploadLocation + File.separator + inPutFile.getId() + "_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(), Paths.get(finalPath));
        // file.transferTo(new File(finalPath));
        inPutFile.setPhysicalPath(finalPath);
        inPutFile.setSize(file.getSize());
        dbBulkUploadService.updateFilePath(inPutFile, user, inPutFile.getId());

        // BULK UPLOAD ACTION
        BulkUploadAction actn = new BulkUploadAction();

        actn.setBulkUpload(new Bulkupload(5L));
        actn.setCreatedDate(new Date());
        actn.setRequestDocumentId(inPutFile.getId());
        actn.setUploadStatus("Processing");

        dbBulkUploadService.saveBulkuploadAction(actn, user, 5L);
        List<Map<String, Object>> lst = FileParser.parsePmSiteFileIn(new File(finalPath));
        new PmSiteAssetsBackUpload(lst, user, actn, file.getOriginalFilename()).start();

        return msg;
    }

    @GetMapping("uploadtemplate")
    public void getTemplate(HttpServletResponse response) throws IOException {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();

        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setColor(Font.COLOR_RED);
        style.setFont(font);

        Row headerRow = sheet.createRow(0);

        Cell cell0 = headerRow.createCell(0);
        cell0.setCellValue("ASSETS SERIAL NUMBER");
        cell0.setCellStyle(style);

        Cell cell1 = headerRow.createCell(1);
        cell1.setCellValue("SITE ID");
        cell1.setCellStyle(style);

        Cell cell2 =headerRow.createCell(2);
        cell2.setCellValue("SITE MANUAL ID");

        response.addHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=PM_SITE_ASSETS_TEMPLATE.xlsx");
        response.setContentType("application/vnd.ms-excel");
        wb.write(response.getOutputStream());
        wb.close();
    }

    public class PmSiteAssetsBackUpload extends Thread {
        List<Map<String, Object>> requests;
        LoginUser user;
        BulkUploadAction actn;
        String fileName;

        public PmSiteAssetsBackUpload(List<Map<String, Object>> requests, LoginUser user, BulkUploadAction actn,
                String fileName) {
            this.requests = requests;
            this.user = user;
            this.actn = actn;
            this.fileName = fileName;
        }

        public void run() {
            try {
                List<Map<String, Object>> outData = new ArrayList<Map<String, Object>>();
                int total = requests.size();
                int done = 0;
                int error = 0;
                int pending = total;

                for (Map<String, Object> re : requests) {
                    Map<String, Object> reM = new LinkedHashMap<String, Object>();
                    try {

                        reM.put("ASSETS SERIAL NUMBER", re.get("assetsSapNumber"));
                        reM.put("SITE ID", re.get("siteId"));
                        reM.put("SITE MANUAL ID", re.get("siteManualId"));
                        dbPmSite.saveObject(re, user);

                        reM.put("ID", re.get("id"));
                        reM.put("UPLOAD MANUAL ID", re.get("manuaId"));

                        reM.put("UPLOAD STATUS", "Uploaded");
                        done++;
                        pending--;
                        websocket.convertAndSendToUser(user.getUsername(), "/topic/customerinventory",
                                new WebMessage("info",
                                        "Total Record " + total + ",Total Done " + done + ", Total Error " + error
                                                + " Total pending " + pending + ", Current process for Assets serial number "
                                                + re.get("assetsSapNumber") + " and Site Id "
                                                + re.get("siteId") + " uploaded successfully"));
                    } catch (Exception ex) {
                        reM.put("ID", "");
                        reM.put("UPLOAD MANUAL ID", "");
                        reM.put("UPLOAD STATUS", "Error " + ex.getMessage());
                        error++;
                        pending--;
                        websocket.convertAndSendToUser(user.getUsername(), "/topic/customerinventory",
                                new WebMessage("error",
                                        "Total Record " + total + ",Total Done " + done + ", Total Error " + error
                                                + " Total pending " + pending + ", unable to process for Assets serial number "
                                                + re.get("assetsSapNumber") + " and site id "
                                                + re.get("siteId") + " due to " + ex.getMessage()));
                        //ex.printStackTrace();
                    }
                    outData.add(reM);
                }

                ExcelParser ex = new ExcelParser();
                BulkuploadFileDetails outPutFile = new BulkuploadFileDetails();

                outPutFile.setFileName("result_" + fileName);

                dbBulkUploadService.saveBulkuploadDocument(outPutFile, user);
                String outputFinalPath = bulkuploadLocation + File.separator + outPutFile.getId() + "_result_"
                        + fileName;

                FileOutputStream o = new FileOutputStream(outputFinalPath);
                o.write(ex.genrateFile(outData).toByteArray());
                o.flush();
                o.close();
                outPutFile.setPhysicalPath(outputFinalPath);
                outPutFile.setSize(new File(outputFinalPath).length());
                dbBulkUploadService.updateFilePath(outPutFile, user, outPutFile.getId());
                actn.setResultDocumentId(outPutFile.getId());
                actn.setUploadStatus("Completed");
                dbBulkUploadService.updateBulkuploadAction(actn, user, actn.getId());

                websocket.convertAndSendToUser(user.getUsername(), "/topic/customerinventorydata", outData);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
