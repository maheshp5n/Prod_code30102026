package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.request.CircleDetailsRequest;

import com.pratikshat.exicom.model.response.CircleDetailsResponse;

public interface CircleDetailsRepositoryCustome {
	public CircleDetailsRequest saveDetails(CircleDetailsRequest details,String userId, int businessDomainId)throws Exception;
	public void updateCircleDetails(CircleDetailsRequest circleRequest,String userId, int businessDomainId)throws Exception;
	
	public List<CircleDetailsResponse> advanceSearch(Map<String,Object> searchKeys,LoginUser user,List<String> userLists, int businessDomainId);
}
