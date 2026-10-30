package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.core.io.Resource;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.ExpenseManagement;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ServiceAppointment;
import com.pratikshat.exicom.entity.UploadedFiles;
import com.pratikshat.exicom.model.response.ExpenseManagementResponse;
import com.pratikshat.exicom.model.response.ServiceAppointmentResponse;
import com.pratikshat.exicom.repository.ExpenseManagementRepository;
import com.pratikshat.exicom.repository.ServiceAppointmentStatusRepository;
import com.pratikshat.exicom.repository.UploadedFilesRepository;
import com.pratikshat.exicom.services.FileStorageService;


@RestController
@RequestMapping("api/")
public class FileUploadController {

	@Autowired
    private FileStorageService fileStorageService;
	
	@Autowired
	private UploadedFilesRepository dbUploadFiles;
	@Autowired
	private com.pratikshat.exicom.repository.UserProfileRepository dbUserProfile;
	
	@Autowired
	com.pratikshat.exicom.repository.ServiceAppointmentRepository dbServiceAppointment;
	
	@Autowired
	ExpenseManagementRepository dbExpenseManagement;
    
    @PostMapping("/uploadFile")
    public java.util.Map<String,Object> uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("objectName")String objectName,@RequestParam("objectId")String objectId, @CurrentUser LoginUser user, HttpServletResponse httpResponse) throws IOException {
        boolean createRights=dbUserProfile.hasObjectAccess(user.getUsername(), objectName, "createRights");
        boolean editRights=dbUserProfile.hasObjectAccess(user.getUsername(), objectName, "editRights");
    	System.out.println("Created Rights "+createRights+" EditRights "+editRights);
    	
    	if( createRights||editRights ) {
    		
    		switch(objectName) {
    			case "ServiceAppointment":{
    				List<Long> claimStatus = new ArrayList<Long>();
    				claimStatus.add(1L);	//Submitted
  //  				claimStatus.add(2L);	//To be Claimed
    				claimStatus.add(3L);	//Approved By SCH
    				claimStatus.add(5L);	//Approved By Finance
    				claimStatus.add(7L);	//Approved By RSH
    				claimStatus.add(9L);	//Posted in SAP
    				
    				ServiceAppointmentResponse _serviceAppointment=dbServiceAppointment.getByAppointmentId(Long.parseLong(objectId));
    				if(claimStatus.contains(_serviceAppointment.getClaimStatusId())) {
    					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "You can't upload the document");
    	            	return null;
    				}
    				break;
    			}
    			case "ExpenseManagement" : {
    				List<Long> status = new ArrayList<Long>();
    				status.add(2L);	// Submitted - Battery
    				status.add(3L);	// Approved by Circle Manager - Battery
    				status.add(4L);	// Approved by Zonal Manager - Battery
    				status.add(5L);	// Approved by HOD - Battery
    				status.add(6L);	// Approved by Finance - Battery
    				status.add(9L);	// Submitted - DCT
    				status.add(10L);	// Approved by Circle Manager - DCT
    				status.add(11L);	// Approved by Zonal Manager - DCT
    				status.add(12L);	// Approved by HOD - DCT
    				status.add(13L);	// Approved by Finance - DCT
    				
    				ExpenseManagementResponse response = dbExpenseManagement.getById(Long.parseLong(objectId));
    				if(status.contains(response.getStatusId())) {
    					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "You can't upload the document");
    	            	return null;
    				}
    				break;
    			}
    		}
    		
    		String fileName = fileStorageService.storeFile(file);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/downloadFile/")
                    .path(fileName)
                    .toUriString();
            fileDownloadUri=fileDownloadUri.replace("http:", "https:");
            try {
            	UploadedFiles uploadedFiles = new UploadedFiles();
                uploadedFiles.setObjectName(objectName);
                uploadedFiles.setObjectId(objectId);
                uploadedFiles.setFileName(fileName);
                uploadedFiles.setFileSize(file.getSize());
                uploadedFiles.setFileContentType(file.getContentType());
                uploadedFiles.setFileDownloadUri(fileDownloadUri);
                uploadedFiles.setCreatedDate(new Date());
                uploadedFiles.setCreatedBy(new EmployeeDetails(user.getUsername()));
                uploadedFiles.setModifiedBy(new EmployeeDetails(user.getUsername()));
                uploadedFiles.setModifiedDate(new Date());
                uploadedFiles.setDeleted(false);
                dbUploadFiles.save(uploadedFiles);	
                
                return dbUploadFiles.findById(uploadedFiles.getId());
            }catch(Exception ex) {
            	httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
            	return null;
            }
    		
    		
		}
    	
    	else {
    		httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have permission");
			return null;
    	}
        
    }

    @PostMapping("/uploadMultipleFiles")
    public List<?> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,@RequestParam("objectName")String objectName,@RequestParam("objectId")String objectId, @CurrentUser LoginUser user, HttpServletResponse httpResponse) throws IOException{
    	
    	if(dbUserProfile.hasObjectAccess(user.getUsername(), objectName, "createRights") || dbUserProfile.hasObjectAccess(user.getUsername(), objectName, "editRights")) {
    		switch(objectName) {
    		case "ServiceAppointment":{
				List<Long> claimStatus = new ArrayList<Long>();
				claimStatus.add(1L);	//Submitted
		//		claimStatus.add(2L);	//To be Claimed
				claimStatus.add(3L);	//Approved By SCH
				claimStatus.add(5L);	//Approved By Finance
				claimStatus.add(7L);	//Approved By RSH
				claimStatus.add(9L);	//Posted in SAP
				
				ServiceAppointmentResponse _serviceAppointment=dbServiceAppointment.getByAppointmentId(Long.parseLong(objectId));
				if(claimStatus.contains(_serviceAppointment.getClaimStatusId())) {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "You can't upload the document");
	            	return null;
				}
				break;
			}
    		case "ExpenseManagement" : {
				List<Long> status = new ArrayList<Long>();
				status.add(2L);	// Submitted - Battery
				status.add(3L);	// Approved by Circle Manager - Battery
				status.add(4L);	// Approved by Zonal Manager - Battery
				status.add(5L);	// Approved by HOD - Battery
				status.add(6L);	// Approved by Finance - Battery
				status.add(9L);	// Submitted - DCT
				status.add(10L);	// Approved by Circle Manager - DCT
				status.add(11L);	// Approved by Zonal Manager - DCT
				status.add(12L);	// Approved by HOD - DCT
				status.add(13L);	// Approved by Finance - DCT
				
				ExpenseManagementResponse response = dbExpenseManagement.getById(Long.parseLong(objectId));
				if(status.contains(response.getStatusId())) {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "You can't upload the document");
	            	return null;
				}
				break;
			}
    	}
		}
    	else {
    		httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have permission");
			return null;
    	}
    	
    	try {
    	return Arrays.asList(files).stream()
                .map(file -> {
                	try{
                	return	uploadFile(file,objectName,objectId,user,httpResponse);		
                	}catch(IOException ex) {
                		return null;
                	}
                }).collect(Collectors.toList());
    	} catch (Exception e) {
    		httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
    		return null;
		}
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    
    
    @PostMapping("/uploadFile/image")
    public UploadedFiles uploadSingleFile(@RequestParam("file") MultipartFile file, @CurrentUser LoginUser user, HttpServletResponse httpResponse) throws IOException {
        
    	String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/downloadFile/")
                .path(fileName)
                .toUriString();
        fileDownloadUri=fileDownloadUri.replace("http:", "https:");
        try {
        	UploadedFiles uploadedFiles = new UploadedFiles();
            uploadedFiles.setFileName(fileName);
            uploadedFiles.setFileSize(file.getSize());
            uploadedFiles.setFileContentType(file.getContentType());
            uploadedFiles.setFileDownloadUri(fileDownloadUri);
            uploadedFiles.setCreatedDate(new Date());
            uploadedFiles.setCreatedBy(new EmployeeDetails(user.getUsername()));
            uploadedFiles.setModifiedBy(new EmployeeDetails(user.getUsername()));
            uploadedFiles.setModifiedDate(new Date());
            uploadedFiles.setDeleted(false);
            return uploadedFiles;
        }catch(Exception ex) {
        	httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        	return null;
        }
        
    }

    
    @GetMapping("uploadedFiles")
	public List<java.util.Map<String,Object>> getApprovalHistory(@RequestParam("objectName")String objectName,@RequestParam("objectId")String objectId, @CurrentUser LoginUser user, HttpServletResponse httpResponse) throws IOException {
		return dbUploadFiles.findByObjectNameAndObjectId(objectName, objectId);
	}
    
    @DeleteMapping("deleteFiles")
    public List<java.util.Map<String,Object>> deleteFile(@RequestParam("imageId") long imageId, @RequestParam("objectName")String objectName,@RequestParam("objectId")String objectId, @CurrentUser LoginUser user, HttpServletResponse httpResponse) throws IOException {
    	boolean createRights=dbUserProfile.hasObjectAccess(user.getUsername(), objectName, "createRights");
        boolean editRights=dbUserProfile.hasObjectAccess(user.getUsername(), objectName, "editRights");
    	
    	if( createRights||editRights ) {
    		
    		switch(objectName) {
    			case "ServiceAppointment":{
    				List<Long> claimStatus = new ArrayList<Long>();
    				claimStatus.add(1L);	//Submitted
  //  				claimStatus.add(2L);	//To be Claimed
    				claimStatus.add(3L);	//Approved By SCH
    				claimStatus.add(5L);	//Approved By Finance
    				claimStatus.add(7L);	//Approved By RSH
    				claimStatus.add(9L);	//Posted in SAP
    				
    				ServiceAppointmentResponse _serviceAppointment=dbServiceAppointment.getByAppointmentId(Long.parseLong(objectId));
    				if(claimStatus.contains(_serviceAppointment.getClaimStatusId())) {
    					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "You can't delete the document");
    	            	return null;
    				}
    				break;
    			}
    			case "ExpenseManagement" : {
    				List<Long> status = new ArrayList<Long>();
    				status.add(2L);	// Submitted - Battery
    				status.add(3L);	// Approved by Circle Manager - Battery
    				status.add(4L);	// Approved by Zonal Manager - Battery
    				status.add(5L);	// Approved by HOD - Battery
    				status.add(6L);	// Approved by Finance - Battery
    				status.add(9L);	// Submitted - DCT
    				status.add(10L);	// Approved by Circle Manager - DCT
    				status.add(11L);	// Approved by Zonal Manager - DCT
    				status.add(12L);	// Approved by HOD - DCT
    				status.add(13L);	// Approved by Finance - DCT
    				
    				ExpenseManagementResponse response = dbExpenseManagement.getById(Long.parseLong(objectId));
    				if(status.contains(response.getStatusId())) {
    					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "You can't delete the document");
    	            	return null;
    				}
    				break;
    			}
    		}
        	
    		try {
            	UploadedFiles uploadedFiles = dbUploadFiles.getById(imageId);
                uploadedFiles.setModifiedBy(new EmployeeDetails(user.getUsername()));
                uploadedFiles.setModifiedDate(new Date());
                uploadedFiles.setDeleted(true);
                dbUploadFiles.save(uploadedFiles);	
                
                return dbUploadFiles.findByObjectNameAndObjectId(objectName, objectId);
            }catch(Exception ex) {
            	httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
            	return null;
            }
    	}else {
    		httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have permission");
			return null;
    	}
	}
}
