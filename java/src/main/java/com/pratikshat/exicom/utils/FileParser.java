package com.pratikshat.exicom.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileParser {
    public static List<Map<String,Object>> parsePmSiteFileIn(File file) throws Exception{
        List<Map<String,Object>> lst=new ArrayList<Map<String,Object>>();
        DataFormatter formatter=new DataFormatter();
        if(!file.exists())
			throw new Exception("File not found");
            Workbook wb=null;
            try {
                wb=new XSSFWorkbook(new FileInputStream(file));
            }catch(Exception ex) {
                ex.printStackTrace();
            }
            if(wb==null) {
                try {
                    wb=new HSSFWorkbook(new FileInputStream(file));	
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
            if(wb==null)
                throw new Exception("Wrong file format");	
            Sheet sheet=wb.getSheetAt(0);
            Iterator<Row> rows=sheet.iterator();
            rows.next();//SKIPPING FIRST ROW

            while(rows.hasNext()) {
                Row row =rows.next();
               Map<String,Object> req=new HashMap<String,Object>();
               req.put("assetsSapNumber", formatter.formatCellValue(row.getCell(0)));
               req.put("siteId", formatter.formatCellValue(row.getCell(1)));
                req.put("siteManualId", formatter.formatCellValue(row.getCell(2)));
               lst.add(req);
            }

        return lst;
    }
}
