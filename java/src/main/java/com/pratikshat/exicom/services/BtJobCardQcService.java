package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BtJobCardQcService extends GenricService{
    public List<Map<String,Object>> getQcListByJobcardid(Long id,LoginUser user) throws BadRequestException,UnauthoriseException;
    public Page<Map<String,Object>> getQcPageByJobcardId(Long id,Pageable page,LoginUser user) throws BadRequestException,UnauthoriseException;
}
