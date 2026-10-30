package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;

public interface UserProfileRepositoryCustome {
	public java.util.List<String> getListOfUserAndChildByUser(String user);
	public List<com.pratikshat.exicom.model.response.ObjectAccessResponse> getObjectsAccess(String userId);
	public boolean hasObjectAccess(String userEmailId,String objectName,String rightsName);
	public boolean hasObjectAccess(String userEmailId,Class cls,String rightsName);
	public com.pratikshat.exicom.model.request.ProfileRequest saveProfile(com.pratikshat.exicom.model.request.ProfileRequest profileRquest,com.pratikshat.exicom.entity.LoginUser user, int businessDomainId) throws com.pratikshat.exicom.exception.BadRequestException,com.pratikshat.exicom.exception.UnauthoriseException;
	public com.pratikshat.exicom.model.request.ProfileRequest editProfile(com.pratikshat.exicom.model.request.ProfileRequest profileRequest,com.pratikshat.exicom.entity.LoginUser user,int buisnessdomainid)throws com.pratikshat.exicom.exception.BadRequestException,com.pratikshat.exicom.exception.UnauthoriseException;
	public Map<String,String> getAccountAndRoleGroup(LoginUser user);
	public List<String> getSameroleUsers(LoginUser user);
	public boolean samroleUsers(String userFirst,String userSecond);
	public List<Long> getLocationAndChildLocation(String userEmailId);
	public List<String> getUsersInMainRoleGroup(String groupName);
}
