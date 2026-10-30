package com.pratikshat.exicom.repository.custom;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtProductTransferItem;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.GenericOperation;

public interface BtProductTransferItemRepositoryCustom extends GenericOperation<BtProductTransferItem> {
    public void addTotalIssuedQty(Long btProductTransferItemId,Float qty,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void addTotalReceivedQty(Long btProductTransferItemId,Float qty,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void subTotalIssueQty(Long btProductTransferItemId,Float qty,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void subTotalReceivedQty(Long btProductTransferItemId,Float qty,LoginUser user) throws BadRequestException,UnauthoriseException;

    //ADVANCE SEARCH RESULT BY PRODUCT REQUEST ID
    public List<Map<String,Object>> getAdvanceSearchByProductRequestId(Long productRequestId,List<Long>locationIds,Map<String,Object> request,LoginUser user) throws BadRequestException,UnauthoriseException;
    //ADVANCE SEARCH RESULT BY PRODUCT TRANSFER ID

    public List<Map<String,Object>> getAdvanceSerachByProductTransferId(Long productTransferId,List<Long>locationIds,Map<String,Object> request,LoginUser user) throws BadRequestException,UnauthoriseException;
}
