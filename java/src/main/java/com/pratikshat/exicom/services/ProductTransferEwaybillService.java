package com.pratikshat.exicom.services;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.entity.ProductTransferEwaybill;
import com.pratikshat.exicom.model.response.ResponseProductTransfer;

public interface ProductTransferEwaybillService {
	
	ProductTransferEwaybill saveEwaybill(
            Long ptId,
            String ptNumber,
            String ewaybillNumber,
            LocalDate ewaybillDate,
            MultipartFile file,
            String uploadedBy
    ) throws Exception;

    ResponseProductTransfer getByProdId(Long ptNum);


}
