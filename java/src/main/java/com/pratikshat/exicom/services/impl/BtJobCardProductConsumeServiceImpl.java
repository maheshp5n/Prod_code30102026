package com.pratikshat.exicom.services.impl;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.services.BtJobCardProductConsumeService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BtJobCardProductConsumeServiceImpl implements BtJobCardProductConsumeService {

    @Override
    public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> searchForList(String search, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        // TODO Auto-generated method stub
        return null;
    }
    
}
