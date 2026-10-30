package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.services.BulkuploadService;

@RestController
@RequestMapping("api/bulkutil")
public class BulkuploadUtilController {

	private static final Logger logger=LoggerFactory.getLogger(BulkuploadUtilController.class);
	
	@Autowired
	BulkuploadService dbBulkService;
	
	@GetMapping
	public Page<Map<String,Object>> getBulkUtils(@CurrentUser LoginUser user,Pageable page){
		return dbBulkService.getBulkuploadOption(user, page);
	}
	@GetMapping("{id}")
	public Map<String,Object> getBulkUtilById(@CurrentUser LoginUser user,@PathVariable("id")Long id){
		return dbBulkService.getBulkuploadOptionById(user, id);
	}
	@GetMapping("{id}/actions")
	public Page<Map<String,Object>> getBulkAction(@CurrentUser LoginUser user,@PathVariable("id")Long id,Pageable page){
		return dbBulkService.getBulkuploadAction(user, id, page);
	}
	
	@GetMapping("/document/{id}")
	public ResponseEntity<Resource> getdocument(HttpServletResponse response,@PathVariable("id")Long id,HttpServletRequest request) throws IOException {
		Map<String,Object> doc=dbBulkService.getDocumentDetails(id);
		
		if(doc!=null) {
			try {
				Path path=Paths.get(doc.get("physical_path").toString());
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
				                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				                .body(resource);
				}else {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Resource not found");
					return null;
				}
				
//				File fl=new File(doc.get("physical_path").toString());
//				response.addHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+fl.getName());
//				response.setContentType("application/vnd.ms-excel");
//				Files.copy(fl.toPath(), response.getOutputStream());
				
			} catch (IOException e) {
				
				try {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
				} catch (IOException e1) {
				
					e1.printStackTrace();
				}
				
				e.printStackTrace();
				return null;
			}
			
		}
		else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Resource not found");
			return null;
		}
			
	}
	
}
