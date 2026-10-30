package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BtServiceOrderReturnService extends GenricService {
    public List<Map<String,Object>> getPendingItemToReturn(LoginUser user,Long destinationLocationId) throws BadRequestException,UnauthoriseException;
    public List<Map<String,Object>> getPendingItemToReceive(LoginUser user) throws BadRequestException,UnauthoriseException;
    public List<Map<String,Object>> getPendingLocationToTransfer(LoginUser user) throws BadRequestException,UnauthoriseException;

    public List<Map<String,Object>> getItemList(Long serviceOrderReturnId,LoginUser user) throws BadRequestException,UnauthoriseException;

    public Page<Map<String,Object>> getItemPage(Long serviceOrderReturnId,Pageable page,LoginUser user) throws BadRequestException,UnauthoriseException;

    public void updateReceive(List<Map<String,Object>> request,LoginUser user) throws BadRequestException,UnauthoriseException;
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user, String filterParam) throws BadRequestException, UnauthoriseException;
}
