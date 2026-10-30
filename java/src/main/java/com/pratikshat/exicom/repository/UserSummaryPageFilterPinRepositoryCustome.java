package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public interface UserSummaryPageFilterPinRepositoryCustome {
	public void pinFilter(Long id,LoginUser user) throws BadRequestException,UnauthoriseException;
}
