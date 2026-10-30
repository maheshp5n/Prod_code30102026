package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public interface BtProductRequestItemService extends GenricService {
	Page<Map<String, Object>> getPage(Long productRequestId,Pageable page, LoginUser user) throws BadRequestException,UnauthoriseException;
	List<Map<String,Object>> searchForList(Long productRequestId,String searchKey,LoginUser user) throws BadRequestException,UnauthoriseException;
	List<Map<String,Object>> advanceSearch(Long productRequestId,Map<String,Object> search,LoginUser user) throws BadRequestException,UnauthoriseException;
	void updateStatus(Long productRequestItemId,String status,LoginUser user) throws BadRequestException,UnauthoriseException;
	void deleteProductItem(Long productRequestItemId,LoginUser user) throws BadRequestException,UnauthoriseException;

	List<Map<String, Object>> getList(Long productRequestId, LoginUser user) throws BadRequestException,UnauthoriseException;
}
