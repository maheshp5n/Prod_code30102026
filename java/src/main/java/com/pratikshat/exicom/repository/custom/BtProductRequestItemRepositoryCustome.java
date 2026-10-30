package com.pratikshat.exicom.repository.custom;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtProductRequestItem;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.GenericOperation;

public interface BtProductRequestItemRepositoryCustome extends GenericOperation<BtProductRequestItem> {
	public void addItem(BtProductRequestItem item,LoginUser user) throws BadRequestException,UnauthoriseException;
	public void addRequiredQty(Long itemId,Float qty,LoginUser user) throws BadRequestException,UnauthoriseException;
	public void addIssueQty(Long itemId,Float qty,LoginUser user) throws BadRequestException,UnauthoriseException;
	public void addReceivedQty(Long itemId,Float qty,LoginUser user) throws BadRequestException,UnauthoriseException;
	public void subtractRequiredQty(Long itemId,Float qty,LoginUser user) throws BadRequestException,UnauthoriseException;
	public void subtractIssueQty(Long itemId,Float qty,LoginUser user) throws BadRequestException,UnauthoriseException;
	public void subtractReceivedQty(Long itemId,Float qty,LoginUser user) throws BadRequestException,UnauthoriseException;
	public void changeItemsStatus(Long itemId,String status,LoginUser user) throws BadRequestException,UnauthoriseException;
	
	public List<Map<String,Object>> advanceSearch(Long productRequestId,Map<String,Object> search,LoginUser user) throws BadRequestException,UnauthoriseException;
}
