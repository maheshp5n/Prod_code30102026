package com.pratikshat.exicom.repository.custom;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtJobCardQc;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.GenericOperation;

public interface BtJobCardQcCustom extends GenericOperation<BtJobCardQc>{
    List<Map<String,Object>> advanceSearch(Map<String,Object> request,List<String> emailids) throws BadRequestException,UnauthoriseException;
}
