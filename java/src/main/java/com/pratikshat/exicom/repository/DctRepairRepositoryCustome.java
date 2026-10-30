package com.pratikshat.exicom.repository;

import java.util.List;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.DctRepairRequest;

public interface DctRepairRepositoryCustome {
	public void repairItems(DctRepairRequest request,LoginUser user) throws BadRequestException,UnauthoriseException;
	public List<String> getOkSerialNumbers(String partcode,LoginUser user) throws BadRequestException,UnauthoriseException;
}
