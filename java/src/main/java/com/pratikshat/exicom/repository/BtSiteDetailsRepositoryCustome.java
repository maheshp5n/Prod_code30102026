package com.pratikshat.exicom.repository;

import java.util.Map;
import com.pratikshat.exicom.entity.BtSiteDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public interface BtSiteDetailsRepositoryCustome extends GenericOperation<BtSiteDetails> {
	
	public Map<String,Object> siteDetailsSaveExcelData(Map<String,Object> req,LoginUser user) throws BadRequestException,UnauthoriseException;
	public BtSiteDetails getSiteWithId(long id) throws BadRequestException;
}
