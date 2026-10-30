package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BtProductTransferItemService extends GenricService {
    Page<Map<String,Object>> getTransferItemDetails(Long transferId,LoginUser user,Pageable page) throws BadRequestException,UnauthoriseException;
    List<Map<String,Object>> getTransferItemDetails(Long transferId,LoginUser user) throws BadRequestException,UnauthoriseException;

    List<Map<String,Object>> advanceSearchOfItemByproduct(Long transferId,Map<String,Object> request,LoginUser user) throws BadRequestException,UnauthoriseException;
}
