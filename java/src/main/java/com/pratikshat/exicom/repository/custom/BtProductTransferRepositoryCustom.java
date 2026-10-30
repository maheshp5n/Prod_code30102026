package com.pratikshat.exicom.repository.custom;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtProductTransfer;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.GenericOperation;

public interface BtProductTransferRepositoryCustom extends GenericOperation<BtProductTransfer> {
	
	public void addTransfredQty(Long productTransferId,Float qty,LoginUser user) throws BadRequestException,UnauthoriseException;
	public void addReceivedQty(Long productTransferId,Float qty,LoginUser user) throws BadRequestException,UnauthoriseException;
	public void subTransferdQty(Long productTransferId,Float qty,LoginUser user) throws BadRequestException,UnauthoriseException;
	public void subTransferReceiveQty(Long productTransferId,Float qty,LoginUser user) throws BadRequestException,UnauthoriseException;
	
	public List<Map<String,Object>> advanceSearch(List<Long> locationIds,Map<String,Object> searchRequest,LoginUser user) throws BadRequestException,UnauthoriseException;

}
