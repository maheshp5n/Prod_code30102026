package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.BtPacking;

public interface BtProductRequestService extends GenricService {
	
	Page<Map<String,Object>> getByFilterParam(String filterparam,Pageable page,LoginUser user) throws BadRequestException,UnauthoriseException;
	// public void changeStatus(Long productRequestId,String status,LoginUser user) throws BadRequestException,UnauthoriseException;

	public void changeStatus(Long productRequestId,LoginUser user,String status) throws BadRequestException,UnauthoriseException;

	public Page<Map<String,Object>> getProductRequestBySourceLocation(Long locationId,Pageable page,LoginUser user) throws BadRequestException,UnauthoriseException;
	public Page<Map<String,Object>> getProductRequestByDestinationLocation(Long locationId,Pageable page,LoginUser user) throws BadRequestException,UnauthoriseException;

	public List<Map<String,Object>> advanceSearchByLocationId(Long locationId,Map<String,Object> request,Boolean toMe,LoginUser user) throws BadRequestException,UnauthoriseException;
	public BtPacking getPackingList(Long id);

	List<Map<String, Object>> getProductRequestSubmittedDate(Long productRequestId, LoginUser user);
	
}
