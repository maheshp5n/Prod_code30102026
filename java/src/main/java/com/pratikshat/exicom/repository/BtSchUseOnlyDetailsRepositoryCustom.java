package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtSchUseOnlyDetails;
import com.pratikshat.exicom.entity.BtSolutionImpartedProblem;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public interface BtSchUseOnlyDetailsRepositoryCustom extends GenericOperation<BtSchUseOnlyDetails>{
	
	public BtSolutionImpartedProblem saveDetail(BtSolutionImpartedProblem btSolutionImpartedRepository, LoginUser user)
			throws BadRequestException, UnauthoriseException ;
		
	public BtSchUseOnlyDetails getObject(long id);
	
	public void deleteSolutionImparted(long id);

}
