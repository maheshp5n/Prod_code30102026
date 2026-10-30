package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.AmcUpdateRequest;
import com.pratikshat.exicom.model.request.AssetsRequest;
import com.pratikshat.exicom.model.request.ChangeCaseTypeRequest;
import com.pratikshat.exicom.model.response.AssetsResponse;

public interface AssetsCustomeRepository {
	public AssetsRequest saveAssets(AssetsRequest assets,LoginUser user)throws Exception;
	public com.pratikshat.exicom.model.request.SiteDetailsRequest saveSiteOnAssets(String assetsSerialNumber,com.pratikshat.exicom.model.request.SiteDetailsRequest site,LoginUser user)throws Exception;
	
	public List<AssetsResponse> advanceSearch(Map<String,Object> searchKeys,LoginUser user,List<String> userLists);
	
	public AmcUpdateRequest updateAmcBulk(AmcUpdateRequest req, LoginUser user)throws BadRequestException, UnauthoriseException;
}
