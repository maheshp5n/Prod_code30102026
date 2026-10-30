package com.pratikshat.exicom.config;

public interface WorkFlow {
	void executeTask(javax.persistence.EntityManager em,Object object,com.pratikshat.exicom.entity.LoginUser user) throws com.pratikshat.exicom.exception.BadRequestException,com.pratikshat.exicom.exception.UnauthoriseException;
}
