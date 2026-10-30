package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtServiceAppoinmentProductConsumed;
import com.pratikshat.exicom.exception.BadRequestException;

public interface BtServiceAppointmentProductConsumedCustomeRepo extends GenericOperation<BtServiceAppoinmentProductConsumed>{

	BtServiceAppoinmentProductConsumed getproductConsumed(long id)  throws BadRequestException;
	
	void deleteItem(BtServiceAppoinmentProductConsumed id) throws BadRequestException;
	
	
}
