package com.pratikshat.exicom.services.impl;

import com.pratikshat.exicom.controller.VendorInvoiceController;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.request.VendorInvoiceCommercialRequest;
import com.pratikshat.exicom.repository.VendorPoDetailsRepository;
import com.pratikshat.exicom.services.VendorInvoiceService;
import com.pratikshat.exicom.utils.ExcelParser;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;

@Service
public class VendorInvoiceServiceImpl implements VendorInvoiceService {
    @Autowired
    VendorPoDetailsRepository dbPo;
    private static final Logger logger= LoggerFactory.getLogger(VendorInvoiceServiceImpl.class);
    @Override
    public ByteArrayOutputStream bulkUploadInvoiceCommericalDetails(MultipartFile file, LoginUser user) throws Exception {
        List<VendorInvoiceCommercialRequest> vendorInvoiceCommercialRequestList = parseVendorInvoiceCommericalFile(file);
        List<Map<String,Object>> outData = new ArrayList<>();
        for(VendorInvoiceCommercialRequest vendorInvoiceCommercialRequest: vendorInvoiceCommercialRequestList)
        {
            Map<String,Object> mp = convertObjectIntoMap(vendorInvoiceCommercialRequest);
            String status = "";
            try {
                dbPo.updateGrnDetails(vendorInvoiceCommercialRequest, user);
                mp.put("Status","Success");
            }
            catch(Exception exception)
            {
                mp.put("Status", "Error : " + exception.getMessage());
            }
            outData.add(mp);
        }
        ByteArrayOutputStream st = null;

        st = new ExcelParser().genrateFile(outData);

        return st;
    }

    private Map<String,Object> convertObjectIntoMap(VendorInvoiceCommercialRequest vendorInvoiceCommercialRequest){
        Map<String, Object> mp = new LinkedHashMap<>();
        mp.put("CRM Invoice No",vendorInvoiceCommercialRequest.getVendorInvoiceId());
        mp.put("GRN Number",vendorInvoiceCommercialRequest.getGrnNumber());
        mp.put("GRN Date",vendorInvoiceCommercialRequest.getGrnDate());
        mp.put("Service Entry Number",vendorInvoiceCommercialRequest.getEntrySheetNumber());
        mp.put("Internal Service Code",vendorInvoiceCommercialRequest.getInternalServiceCode());
        return mp;
    }

    private List<VendorInvoiceCommercialRequest> parseVendorInvoiceCommericalFile(MultipartFile file) throws Exception {
        List<VendorInvoiceCommercialRequest> vendorInvoiceCommercialRequestList = new ArrayList<>();
        if(file.isEmpty())
            throw new Exception("File not found");

        DataFormatter formatter=new DataFormatter();
        ByteArrayInputStream str=new ByteArrayInputStream(file.getBytes());

        Workbook wb =new XSSFWorkbook(str);
        Sheet sheet=wb.getSheetAt(0);
        Iterator<Row> rows=sheet.iterator();
        rows.next();//SKIPPING Header ROW
        while(rows.hasNext()) {

            Row row =rows.next();


            // Check for empty row
            boolean isEmptyRow = true;
            for (Cell cell : row) {
                if (cell != null && !formatter.formatCellValue(cell).trim().isEmpty()) {
                    isEmptyRow = false;
                    break;
                }
            }

            if (isEmptyRow) {
                break; // Stop processing when an empty row is encountered
            }


            Map<String,Object> mp = new LinkedHashMap<>();
            /**
             * {"grnDate":"2023-09-05T18:30:00.000Z" -- 2,
             * "grnNumber":"123" --1 ,
             * "entrySheetNumber":"458" -- 3 ,
             * "internalServiceCode":2 -- 4 ,
             * "vendorInvoiceId":"135" -- 0}
             */

            VendorInvoiceCommercialRequest vendorInvoiceCommercialRequest = new VendorInvoiceCommercialRequest();
            vendorInvoiceCommercialRequest.setVendorInvoiceId(Long.valueOf(formatter.formatCellValue(row.getCell(0)).trim()));
            vendorInvoiceCommercialRequest.setGrnNumber(formatter.formatCellValue(row.getCell(1)).trim());
            try {
                vendorInvoiceCommercialRequest.setGrnDate(row.getCell(2).getDateCellValue());
            }catch(Exception ex) {
                throw new Exception("Update GRN date is blank");
            }
            vendorInvoiceCommercialRequest.setEntrySheetNumber(formatter.formatCellValue(row.getCell(3)).trim());
            vendorInvoiceCommercialRequest.setInternalServiceCode(Integer.valueOf(formatter.formatCellValue(row.getCell(4)).trim()));
            vendorInvoiceCommercialRequestList.add(vendorInvoiceCommercialRequest);
        }
        return vendorInvoiceCommercialRequestList;
    }
}
