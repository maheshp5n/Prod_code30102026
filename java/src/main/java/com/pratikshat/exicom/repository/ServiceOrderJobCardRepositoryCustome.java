package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public interface ServiceOrderJobCardRepositoryCustome {
	public void createJobCard(List<Long>items,long serviceresourceId,LoginUser user) throws BadRequestException,UnauthoriseException;
	public List<Map<String,Object>> advanceSearch(Map<String,Object> searchkey,LoginUser user,List<String> lstUsers,boolean viewAll) throws BadRequestException,UnauthoriseException;
}
