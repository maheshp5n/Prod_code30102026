
package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtProducts;

import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.UnauthoriseException;


public interface BtProductsRepositoryCustome extends GenericOperation<BtProducts> {
	
	public BtProducts getProductWithId(long id) throws BadRequestException;

    public String updateStatus(long productId, boolean status, LoginUser user) throws BadRequestException, UnauthoriseException;
    
}
