package com.pratikshat.exicom.repository;

import java.io.IOException;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.QuotePdfHeaderAndFooter;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.QuotePdfHeaderAndFooterRequest;

public interface QuotePdfHeaderAndFooterRepositoryCustom {

	public QuotePdfHeaderAndFooterRequest createTemplate(QuotePdfHeaderAndFooterRequest request, LoginUser user) throws Exception;
	
	public QuotePdfHeaderAndFooterRequest updateTemplate(QuotePdfHeaderAndFooterRequest request, LoginUser user) throws BadRequestException,UnauthoriseException;
}
