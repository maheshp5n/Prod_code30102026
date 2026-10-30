package com.pratikshat.exicom.repository;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;

public interface LoginUserCustomeRepository {

	List<com.pratikshat.exicom.model.response.UserResponse> advanceSearch(Map<String,Object> request,LoginUser user, int businessDomainId) throws ParseException;
}
