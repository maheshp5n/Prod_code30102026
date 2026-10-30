package com.pratikshat.exicom.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.repository.ReportRequestRepository;

@RestController
@RequestMapping("api/asyreport")
public class ReportRequestController {
	@Autowired
	ReportRequestRepository dbReport;
	
	@Value("${app.report.filepath}")
	String filepath;
	private static final Logger logger=LoggerFactory.getLogger(ReportRequestController.class);
	
	@GetMapping
	Page<Map<String,Object>> getReports(@RequestParam(required = false) Map<String,Object> search,@CurrentUser LoginUser user,Pageable page) throws ParseException{
		if(search==null)
			return dbReport.findByUserEmailId(user.getUsername(), page);
		if(search.get("report_name")!=null) {
			String stDAte=search.get("report_date").toString();
			SimpleDateFormat dformat=new SimpleDateFormat("dd-MM-yy");
			Date date=dformat.parse(stDAte);
			SimpleDateFormat sFormat=new SimpleDateFormat("yyyy-MM-dd");
			String reportDate=sFormat.format(date);
			return dbReport.searchByEmailIdReportNameAndDate(user.getUsername(), search.get("report_name").toString(), reportDate,page);
		}
		else
			return dbReport.findByUserEmailId(user.getUsername(), page);
			
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Resource> downloadFile(@PathVariable("id")Long reqId,@CurrentUser LoginUser user,HttpServletResponse response,HttpServletRequest request) throws IOException{
		Path path=Paths.get(filepath, reqId+".csv");
		//Resource resource=new UrlResource(filepath+File.separator+reqId+".csv");
		Map<String,Object> rpt=dbReport.getReportByRequestId(reqId);
		String reportName=rpt.get("report_name").toString();
		Date reportDate=(Date)rpt.get("request_date");
		SimpleDateFormat format=new SimpleDateFormat("ddMMyyyyHHmmss");
		reportName=reportName.replaceAll(" ", "_");
		String fileName=reportName+"_"+format.format(reportDate);
		
		
		
		
		Resource resource=new UrlResource(path.toUri());
		if(resource.exists()) {
			String contentType=null;
			 try {
		            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		        } catch (IOException ex) {
		            logger.info("Could not determine file type.");
		        }
			 if(contentType == null) {
		            contentType = "application/octet-stream";
		        }
			 return ResponseEntity.ok()
		                .contentType(MediaType.parseMediaType(contentType))
		                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +fileName+"_"+ resource.getFilename() + "\"")
		                .body(resource);
		}else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Resource not found");
			return null;
		}
		
//		
//		response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report_"+reqId+".csv");
//		response.setContentType("text/csv");
//		
//		OutputStream out=response.getOutputStream();
//		Files.copy(new File(filepath+File.separator+reqId+".csv").toPath(), out);
//		try {
//			out.flush();
//			out.close();
//		}
//		catch(Exception ex) {
//			logger.error(ex.getLocalizedMessage(), ex);
//		}
	}
}
