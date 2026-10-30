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
import com.pratikshat.exicom.entity.MailerEntity;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

@Repository
public class MailerEntityRepositoryCustomeImpl implements MailerEntityRepositoryCustome {
	@PersistenceContext
	EntityManager em;

	@Override
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,BadRequestException.class,UnauthoriseException.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.READ_UNCOMMITTED)
	public Map<String, Object> saveMailerEntity(Map<String, Object> request, LoginUser user) throws BadRequestException,UnauthoriseException {
		MailerEntity mailer=new MailerEntity();
		mailer.setActive(false);
		mailer.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		mailer.setCreatedDate(new Date());
		mailer.setDefaultPassword(request.get("password").toString());
		mailer.setDefaultUserName(request.get("emailId").toString());
		mailer.setLastModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		mailer.setLastModifiedDate(new Date());
		mailer.setRequiredAuthentication((boolean)request.get("requiredAuth"));
		mailer.setRequiredSsl((boolean)request.get("requiredSSL"));
		mailer.setRequiredTlsStart((boolean)request.get("tslStart"));
		mailer.setSmtpPort(Integer.parseInt(request.get("smtpPort").toString()));
		mailer.setSmtpServer(request.get("smtp").toString());
		mailer.setSslPort(Integer.parseInt(request.get("sslPort").toString()));
		
		em.persist(mailer);
		
		for(Map<String,String> ob:(List<Map<String,String>>)request.get("otherPropertyes")) {
			com.pratikshat.exicom.entity.MailerProperty _pro=new com.pratikshat.exicom.entity.MailerProperty();
			_pro.setActive(true);
			_pro.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_pro.setCreatedDate(new Date());
			_pro.setLastModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_pro.setLastModifiedDate(new Date());
			_pro.setMailerEntity(mailer);
			_pro.setMailerPropertyName(ob.get("propertyName"));
			_pro.setMailerpropertyValue(ob.get("propertyValue"));
			em.persist(_pro);
		}
		return request;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,BadRequestException.class,UnauthoriseException.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.READ_UNCOMMITTED)
	public Map<String, Object> updateMailerEntity(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		MailerEntity mailer=null;
		try {
			mailer=em.createQuery("select m from MailerEntity m where m.id=?1",com.pratikshat.exicom.entity.MailerEntity.class).setParameter(1,Long.valueOf(request.get("id").toString())).getSingleResult();
		}catch(NoResultException nEx) {
			
			throw new BadRequestException(nEx.getMessage());
		}
		em.createQuery("delete from MailerProperty m where m.mailerEntity=?1").setParameter(1, mailer).executeUpdate();
		
		mailer.setActive(false);
		mailer.setDefaultPassword(request.get("password").toString());
		mailer.setDefaultUserName(request.get("emailId").toString());
		mailer.setLastModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		mailer.setLastModifiedDate(new Date());
		mailer.setRequiredAuthentication((boolean)request.get("requiredAuth"));
		mailer.setRequiredSsl((boolean)request.get("requiredSSL"));
		mailer.setRequiredTlsStart((boolean)request.get("tslStart"));
		mailer.setSmtpPort(Integer.parseInt(request.get("smtpPort").toString()));
		mailer.setSmtpServer(request.get("smtp").toString());
		mailer.setSslPort(Integer.parseInt(request.get("sslPort").toString()));
		
		em.persist(mailer);
		List<Map<String,String>> other=(List<Map<String,String>>) request.get("otherPropertyes");
		
		for(Map<String,String> ob:other) {
			com.pratikshat.exicom.entity.MailerProperty _pro=new com.pratikshat.exicom.entity.MailerProperty();
			_pro.setActive(true);
			_pro.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_pro.setCreatedDate(new Date());
			_pro.setLastModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_pro.setLastModifiedDate(new Date());
			_pro.setMailerEntity(mailer);
			_pro.setMailerPropertyName(ob.get("propertyName"));
			_pro.setMailerpropertyValue(ob.get("propertyValue"));
			em.persist(_pro);
			System.out.println(ob.get("propertyName"));
		}
		
		return null;
	}
	
	
}
