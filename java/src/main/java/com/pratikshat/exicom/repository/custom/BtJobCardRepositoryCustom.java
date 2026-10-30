package com.pratikshat.exicom.repository.custom;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtJobCard;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.GenericOperation;

public interface BtJobCardRepositoryCustom extends GenericOperation<BtJobCard>{

    public List<Map<String,Object>> advanceSearch(Map<String,Object> request,LoginUser user) throws BadRequestException,UnauthoriseException;
    public List<Map<String,Object>> advanceSearch(Map<String,Object> request,List<String> ownerList,LoginUser user) throws BadRequestException,UnauthoriseException;
    
}
