package com.pratikshat.exicom.services;

import java.util.Map;


import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BtCustomerInventoryInService extends GenricService {
    public Map<String,Object> out(Map<String,Object> request,LoginUser user) throws BadRequestException,UnauthoriseException;
    public Map<String,Object> updateOutGrn(Long outId,Map<String,Object> request,LoginUser user) throws BadRequestException,UnauthoriseException;
    public Page<Map<String,Object>> getPageByStatus(LoginUser user,Pageable page,String status) throws BadRequestException,UnauthoriseException;
}
