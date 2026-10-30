package com.pratikshat.exicom.repository;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.CustomerInventoryOutWardRequest;
import com.pratikshat.exicom.model.request.CustomerInventoryRequest;

public interface CustomerInventoryRepositoryCustome {
	
	public CustomerInventoryRequest doInward(CustomerInventoryRequest req,LoginUser user) throws BadRequestException,UnauthoriseException;
	public CustomerInventoryOutWardRequest doOutward(CustomerInventoryOutWardRequest req,LoginUser user) throws BadRequestException,UnauthoriseException;

	public List<Map<String,Object>> advanceSearch(Map<String,Object> searchKeys,LoginUser user,List<String> userLists) throws ParseException;
}
