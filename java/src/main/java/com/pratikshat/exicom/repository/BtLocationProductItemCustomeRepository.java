package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtLocationProductItem;
import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.exception.BadRequestException;

public interface BtLocationProductItemCustomeRepository extends GenericOperation<BtLocationProductItem>{
	
	public BtLocationProductItem getProductItemByLocationAndProduct(Location locationId,long productId) throws BadRequestException;

}
