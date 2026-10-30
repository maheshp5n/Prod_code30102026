package com.pratikshat.exicom.services;


import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.BtPacking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BtProductTransferService extends GenricService  {

    List<Map<String,Object>> getPendingLocationForProductTransfer(LoginUser user) throws BadRequestException,UnauthoriseException;
    List<Map<String,Object>> getPendingItemToTransfer(Long destinationLocataionId,LoginUser user) throws BadRequestException,UnauthoriseException;
    List<Map<String,Object>> getProductTransferByPrductRequestId(Long productRequestId,LoginUser user) throws BadRequestException,UnauthoriseException;
    Page<Map<String,Object>> getProductTransferByPrductRequestId(Long productRequestId,LoginUser user,Pageable page) throws BadRequestException,UnauthoriseException;

    List<Map<String,Object>> getProductTransferByProductRequestIdAndProductTransferItemId(Long productRequestId,Long productRequestItemId,LoginUser user) throws BadRequestException,UnauthoriseException;

    //METHODS FOR RECEVING ITEM WHICH ARE PENDING TO RECIEVE

    List<Map<String,Object>> getPendingTransferItemToReceive(LoginUser user) throws BadRequestException,UnauthoriseException;
    void receiveTransferdItem(List<Map<String,Object>> lstItemReceived,LoginUser user) throws BadRequestException,UnauthoriseException;

    //ADVANCE SEARCH BY PRODUCT RREQUESTID

    List<Map<String,Object>> getAdvanceSearchByProductRequestId(Long productRequestId,Map<String,Object> request,LoginUser user) throws BadRequestException,UnauthoriseException;

    BtPacking getPacking(Long id);
}
