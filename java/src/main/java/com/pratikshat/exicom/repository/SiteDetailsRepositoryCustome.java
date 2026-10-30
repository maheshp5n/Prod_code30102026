package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.response.SiteDetailsResponse;

public interface SiteDetailsRepositoryCustome {
	com.pratikshat.exicom.model.request.SiteDetailsRequest saveDetails(com.pratikshat.exicom.model.request.SiteDetailsRequest siteDetails,com.pratikshat.exicom.entity.LoginUser loginUser)throws Exception;
	List<SiteDetailsResponse> getAdvanceSearchReslt(Map<String,Object> kyes,LoginUser user,List<String> userList);
	
	com.pratikshat.exicom.model.request.SiteDetailsRequest updateSiteDetails(com.pratikshat.exicom.model.request.SiteDetailsRequest siteDetails,com.pratikshat.exicom.entity.LoginUser loginUser)throws Exception;
}
