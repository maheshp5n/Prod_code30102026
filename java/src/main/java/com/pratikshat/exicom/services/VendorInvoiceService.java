package com.pratikshat.exicom.services;

import com.pratikshat.exicom.entity.LoginUser;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;

public interface VendorInvoiceService {

    public ByteArrayOutputStream bulkUploadInvoiceCommericalDetails(MultipartFile file, LoginUser user) throws Exception;

}
