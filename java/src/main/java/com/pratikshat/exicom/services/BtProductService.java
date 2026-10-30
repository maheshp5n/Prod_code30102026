package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public interface BtProductService extends GenricService{
    public Map<String, Object> updateProductStatus(long productId, boolean active, LoginUser user) throws BadRequestException, UnauthoriseException;

	Page<Map<String, Object>> getPageFilter(String filterParam, Pageable page, LoginUser user) throws BadRequestException, UnauthoriseException;
	public List<Map<String,Object>> searchForList(String search,LoginUser user,Pageable pageable) throws BadRequestException,UnauthoriseException;
	public List<Map<String, Object>> searchPartCode(String string,LoginUser user,Pageable pageable);
}
