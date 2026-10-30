package com.pratikshat.exicom.services;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface GenricService {
    public Map<String,Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException;
    public Page<Map<String,Object>> getPage(Pageable page,LoginUser user) throws BadRequestException,UnauthoriseException;
    
    public Map<String,Object> saveObject(Map<String,Object> request,LoginUser user)throws BadRequestException,UnauthoriseException;
    public Map<String,Object> updateObject(Map<String,Object> request,LoginUser user) throws BadRequestException,UnauthoriseException;
    public List<Map<String,Object>> searchForList(String search,LoginUser user) throws BadRequestException,UnauthoriseException;
    public List<Map<String,Object>> advanceSearch(Map<String,Object> searchRequest,LoginUser user) throws BadRequestException,UnauthoriseException;

    
}
