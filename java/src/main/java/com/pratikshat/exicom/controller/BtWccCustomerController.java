package com.pratikshat.exicom.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.pratikshat.exicom.entity.BtCustomerWcc;
import com.pratikshat.exicom.entity.BtCustomerWccReject;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.PodRequest;
import com.pratikshat.exicom.services.BtWccCustomerService;
import com.pratikshat.exicom.utils.DateConverter;

@RestController
@RequestMapping ("bt/api/wcccustomer")
public class BtWccCustomerController {
	
	@Autowired
	BtWccCustomerService btwccCustomerService;
	
	List<String> headsMD = new ArrayList<String>();
	
	SimpleDateFormat input=new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat timeInput=new SimpleDateFormat("dd-MM-yyyy hh:mm");

	  @PostMapping
	  public BtCustomerWcc creatCustomerWCC(@RequestPart("data")PodRequest request,@RequestPart("file")List<MultipartFile> files, @CurrentUser LoginUser user)throws BadRequestException, UnauthoriseException{
	  	return btwccCustomerService.postWccCustomer(request, files , user);
	  }
	
	
	@GetMapping
	public Page<Map<String, Object>> getWccCustomService(@CurrentUser LoginUser loginUser,Pageable pageable) throws BadRequestException, UnauthoriseException{
		return btwccCustomerService.getPage(pageable, loginUser);
	}
	
	@GetMapping("{id}")
	public Map<String, Object> getSingleDetails(@CurrentUser LoginUser loginUser,@PathVariable("id") long id) throws BadRequestException, UnauthoriseException{
		return btwccCustomerService.getSingle(id, loginUser);
	}
	
	@GetMapping("wcccase/{id}")
	public List<Map<String, Object>> getCaseFromWcc(@PathVariable("id") long id, @CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return btwccCustomerService.getAllCase(id, user);
	}
	
	@PutMapping("/approval")
	public Map<String, Object> postWccCustomer(@RequestPart("data")Map<String, Object> request, @RequestPart("file") List<MultipartFile> files, @CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException, ParseException
	{
		return btwccCustomerService.customerWccApproval(request,files, user);
	}
	
	@PutMapping("/reject")
	public Map<String, Object> rejectWccCustomer(@RequestBody Map<String, Object> req, @CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return btwccCustomerService.rejectCustomerWCC(req,user);
		
	}
	
	@GetMapping("/advancesearch")
	public List<Map<String, Object>> searchWCC(@RequestParam Map<String, Object> req, @CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return btwccCustomerService.advanceSearch(req,user);
	}
	
	@GetMapping("/rejectReasonForlist")
	public List<BtCustomerWccReject> rejectReason(){
		return btwccCustomerService.getReasonList();
	}

	@GetMapping("/craeteWccTemplate/{wccid}")
	public ResponseEntity<ByteArrayResource> getWccTemplate(@PathVariable("wccid") long wccid, HttpServletResponse httpResponse) throws IOException, ParseException
	{
		HttpHeaders header = new HttpHeaders();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Customer_WCC");
		Object[][] bookData = { { "Sr No", "WCC Number", "WCC Creation date", "WCC Approval Date ( RSH)", "JMS Number",
			"Customer", "Zone", "Circle", "Case No",  "SA No",
			"Case Reason", "Case Type", "PO/ ARC no", "PO date", "SERVICE PERIOD FROM", "SERVICE PERIOD TO"},};

		int rowCount = 0;

		CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(Font.COLOR_NORMAL);
        style.setFont(font);
		
		
		for (Object[] aBook : bookData) {
			Row row = sheet.createRow(rowCount);

			int columnCount = 0;

			for (Object field : aBook) {
				Cell cell = row.createCell(columnCount++);
				if (field instanceof String) {
					cell.setCellValue((String) field);
					if(columnCount!=14&&columnCount!=17) {
						cell.setCellStyle(style);
					}
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
					if(columnCount!=14&&columnCount!=17) {
						cell.setCellStyle(style);
					}
				}
			}

		}
		
        List<Map<String, Object>> wcc= btwccCustomerService.getWccData(wccid);

        for(int i=0; i<wcc.size();i++)
        {
        	int k=1;
        	
        for(Map<String, Object> lst:wcc)
        {
        	int j=1;
			
			Row row=sheet.createRow(k);
			Cell cell1= row.createCell(0);
			cell1.setCellValue(k);
			
			Cell cell2=row.createCell(1);
			cell2.setCellValue(lst.get("wccNumber").toString());
			
			
			Cell cell3=row.createCell(2);
			cell3.setCellValue(input.format(DateConverter.convertToDate(lst.get("wccCreationDate").toString())));
			
			String rshApprovalDate=lst.get("wccApprovalDate")==null?"":input.format(DateConverter.convertToDate(lst.get("wccApprovalDate").toString()));
			Cell cell4=row.createCell(3);
			cell4.setCellValue(rshApprovalDate);
			
			Cell cell5=row.createCell(4);
			cell5.setCellValue(lst.get("jmsNumber")==null?"":lst.get("jmsNumber").toString());
			
			Cell cell6=row.createCell(5);
			cell6.setCellValue(lst.get("customer")==null?"":lst.get("customer").toString());
			
			Cell cell7=row.createCell(6);
			cell7.setCellValue(lst.get("zone")==null?"":lst.get("zone").toString());
			
			Cell cell8=row.createCell(7);
			cell8.setCellValue(lst.get("circle")==null?"":lst.get("circle").toString());
			
			Cell cell9=row.createCell(8);
			cell9.setCellValue(lst.get("caseNo")==null?"":lst.get("caseNo").toString());
			
			Cell cell10=row.createCell(9);
			cell10.setCellValue(lst.get("SaNo")==null?"":lst.get("SaNo").toString());
			
			Cell cell11=row.createCell(10);
			cell11.setCellValue(lst.get("caseReason")==null?"":lst.get("caseReason").toString());
			
			Cell cell12=row.createCell(11);
			cell12.setCellValue(lst.get("caseType")==null?"":lst.get("caseType").toString());
			
			Cell cell13=row.createCell(12);
			cell13.setCellValue(lst.get("poNo")==null?"":lst.get("poNo").toString());
			
			String poDate=lst.get("poDate")==null?"":input.format(DateConverter.convertToDate(lst.get("poDate").toString()));
			Cell cell14=row.createCell(13);
			cell14.setCellValue(poDate);
			
			String fromDate=lst.get("servicePeriodFrom")==null?"":input.format(DateConverter.convertToDate(lst.get("servicePeriodFrom").toString()));
			Cell cell15=row.createCell(14);
			cell15.setCellValue(fromDate);
			
			String toDate=lst.get("servicePeriodTo")==null?"":input.format(DateConverter.convertToDate(lst.get("servicePeriodTo").toString()));
			Cell cell16=row.createCell(15);
			cell16.setCellValue(toDate);
			
//			for (Map.Entry<String, Object> entry : lst.entrySet()) {				
//				this.headsMD.add(entry.getValue().toString());				
//			}
			j++;
			k++;
//			}
//			for (int i = 0; i < headsMD.size(); i++) {
//				
//				Cell cell = row.createCell(i);
//				cell.setCellValue(headsMD.get(i));				
//			}		
        
			headsMD.clear();			

        }
		
		
        }
		

		header.setContentType(new MediaType("application", "force-download"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "btcase_template.xlsx");
		httpResponse.setContentType("application/xlsx");
		ByteArrayOutputStream bytest = new ByteArrayOutputStream();
		workbook.write(bytest);
		return new ResponseEntity<>(new ByteArrayResource(bytest.toByteArray()), header, HttpStatus.OK);
		
	}
}
