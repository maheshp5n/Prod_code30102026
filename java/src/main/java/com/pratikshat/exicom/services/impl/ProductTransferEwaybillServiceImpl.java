package com.pratikshat.exicom.services.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.entity.ProductTransferEwaybill;
import com.pratikshat.exicom.model.response.ResponseProductTransfer;
import com.pratikshat.exicom.services.ProductTransferEwaybillService;

@Service
public class ProductTransferEwaybillServiceImpl implements ProductTransferEwaybillService {
	
	 @Autowired
	    private com.pratikshat.exicom.repository.ProductTransferEwaybillRepository repo;

	    @Value("${file.upload.location}")
	    private String uploadDir;	

	@Override
	public ProductTransferEwaybill saveEwaybill(Long ptId, String ptNumber, String ewaybillNumber,
			LocalDate ewaybillDate, MultipartFile file, String uploadedBy) throws Exception {
		if (ptId == null || file == null) {
	          throw new IllegalArgumentException("Missing required parameters");
	      }

	      

	      
	      File baseDir = new File(uploadDir);
	      if (!baseDir.exists()) {
	          baseDir.mkdirs();
	      }

	      
	      String fileName = ptNumber + "-ewaybill.pdf";

	      
	      Path filePath = Paths.get(uploadDir, fileName);

	      
	      Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

	      
	      ProductTransferEwaybill ewb = new ProductTransferEwaybill();
	      ewb.setProductTransferId(ptId);
	      ewb.setProductTransferNumber(ptNumber);
	      ewb.setEwaybillNumber(ewaybillNumber);
	      ewb.setEwaybillDate(ewaybillDate);
	      ewb.setEwb_status(1);
	      ewb.setFilePath(filePath.toString());
	      ewb.setCreatedBy(uploadedBy);

	     
	      String requestNumber = "EWBM-" + ptNumber;   
	      ewb.setRequestNumber(requestNumber);
	      ewb.setCreatedDate(LocalDateTime.now());     
	      ewb.setApprovalStatus("PENDING");            
	      ewb.setApprovedDate(null);                   
	      ewb.setRemarks(null);                        

	      return repo.save(ewb);
	}

	@Override
	public ResponseProductTransfer getByProdId(Long ptNum) {
		ResponseProductTransfer response = new ResponseProductTransfer();
		ProductTransferEwaybill result =  repo.findTopByProductTransferIdOrderByCreatedAtDesc(ptNum);
		if (result != null) {
			response.setEwaybill(result);
			response.setNumber(1);
	    }
		else {
		response.setEwaybill(null);
		response.setNumber(-1);
		}
		return response;
	}
	
	

}
