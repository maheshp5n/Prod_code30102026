package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.response.CityDetailsResponse;

public interface CityDetailsCustomRepository {
	public com.pratikshat.exicom.model.request.CityDetails saveCityDetails(com.pratikshat.exicom.model.request.CityDetails cityDetails,String userId)throws Exception;
	
	public List<CityDetailsResponse> advanceSearch(Map<String,Object> searchKeys,LoginUser user,List<String> userLists);
}
