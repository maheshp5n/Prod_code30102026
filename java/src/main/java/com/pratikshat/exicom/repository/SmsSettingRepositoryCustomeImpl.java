package com.pratikshat.exicom.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.SmsSetting;
import com.pratikshat.exicom.entity.SmsSettingHeaders;
import com.pratikshat.exicom.entity.SmsSettingParams;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

@Repository
public class SmsSettingRepositoryCustomeImpl implements SmsSettingRepositoryCustome {
	@PersistenceContext
	EntityManager em;

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED,propagation = Propagation.REQUIRES_NEW,rollbackFor = {Exception.class,BadRequestException.class,UnauthoriseException.class,RuntimeException.class})
	public Map<String, Object> saveSmsSetting(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
			SmsSetting sms=new SmsSetting();
			sms.setActive(false);
			if(request.get("contentType")!=null)
				sms.setContentType(request.get("contentType").toString());
			if(request.get("url")==null)
				throw new BadRequestException("Please provide valid sms server setting");
			sms.setUrl(request.get("url").toString().toString());
			if(request.get("method")==null||request.get("method").equals(""))
				sms.setMethodName("GET");
			else
				sms.setMethodName(request.get("method").toString());
			sms.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			sms.setCreatedDate(new Date());
			sms.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			sms.setModifiedDate(new Date());
			
			em.persist(sms);
			List<Map<String,Object>> headers=(List<Map<String,Object>>)request.get("headers");
			List<Map<String,Object>> params=(List<Map<String,Object>>) request.get("params");
			if(headers!=null&&headers.size()>0) {
				for(Map<String,Object> header:headers) {
					SmsSettingHeaders _header=new SmsSettingHeaders();
					_header.setSmsSetting(sms);
					_header.setKeyHeader(header.get("keyName").toString());
					_header.setValueHeader(header.get("keyValue").toString());
					em.persist(_header);
				}
			}
			if(params!=null&&params.size()>0) {
				for(Map<String,Object> param:params) {
					SmsSettingParams _param=new SmsSettingParams();
					_param.setKeyParam(param.get("keyName").toString());
					_param.setParamType(param.get("paramType").toString());
					_param.setSmsSetting(sms);
					_param.setValueParam(param.get("keyValue").toString());
					em.persist(_param);
				}
			}
		return request;
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED,propagation = Propagation.REQUIRES_NEW,rollbackFor = {Exception.class,RuntimeException.class,BadRequestException.class,UnauthoriseException.class})
	public Map<String, Object> updateSmsSetting(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		SmsSetting sms=null;
		try {
			sms=em.createQuery("select s from SmsSetting s where s.id=?1",SmsSetting.class).setParameter(1, Long.valueOf(request.get("id").toString())).getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException(ex.getMessage());
		}
		sms.setActive(false);
		if(request.get("contentType")!=null)
			sms.setContentType(request.get("contentType").toString());
		if(request.get("url")==null)
			throw new BadRequestException("Please provide valid sms server setting");
		sms.setUrl(request.get("url").toString().toString());
		if(request.get("method")==null||request.get("method").equals(""))
			sms.setMethodName("GET");
		else
			sms.setMethodName(request.get("method").toString());
		
		sms.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		sms.setModifiedDate(new Date());
		
		em.persist(sms);
		em.createQuery("delete from SmsSettingHeaders d where d.smsSetting=?1").setParameter(1,sms).executeUpdate();
		em.createQuery("delete from SmsSettingParams d where d.smsSetting=?1").setParameter(1, sms).executeUpdate();
		
		List<Map<String,Object>> headers=(List<Map<String,Object>>)request.get("headers");
		List<Map<String,Object>> params=(List<Map<String,Object>>) request.get("params");
		if(headers!=null&&headers.size()>0) {
			for(Map<String,Object> header:headers) {
				SmsSettingHeaders _header=new SmsSettingHeaders();
				_header.setSmsSetting(sms);
				_header.setKeyHeader(header.get("keyName").toString());
				_header.setValueHeader(header.get("keyValue").toString());
				em.persist(_header);
			}
		}
		if(params!=null&&params.size()>0) {
			for(Map<String,Object> param:params) {
				SmsSettingParams _param=new SmsSettingParams();
				_param.setKeyParam(param.get("keyName").toString());
				_param.setParamType(param.get("paramType").toString());
				_param.setSmsSetting(sms);
				_param.setValueParam(param.get("keyValue").toString());
				em.persist(_param);
			}
		}
	return request;
	}
}
