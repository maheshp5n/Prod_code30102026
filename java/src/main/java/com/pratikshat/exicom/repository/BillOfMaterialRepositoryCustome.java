package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BillOfMaterialRequest;

public interface BillOfMaterialRepositoryCustome {
	public void saveBillOfMaterial(String partCode,List<BillOfMaterialRequest> lstReq,LoginUser user) throws BadRequestException,UnauthoriseException;
	public void addProductToBillOfMaterial(String partCode,BillOfMaterialRequest req,LoginUser user) throws BadRequestException,UnauthoriseException;
	public void uploadExcelFile(MultipartFile file,String fPartCode,LoginUser user) throws BadRequestException,UnauthoriseException;
}
