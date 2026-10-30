package com.pratikshat.exicom.repository;

import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public interface SmsSettingRepositoryCustome {
	Map<String,Object> saveSmsSetting(Map<String,Object> request,LoginUser user) throws BadRequestException,UnauthoriseException;
	Map<String,Object> updateSmsSetting(Map<String,Object>request,LoginUser user) throws BadRequestException,UnauthoriseException;
}
