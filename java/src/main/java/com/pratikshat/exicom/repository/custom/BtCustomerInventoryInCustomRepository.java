package com.pratikshat.exicom.repository.custom;

import java.util.List;
import java.util.Map;

import com.google.api.gax.rpc.UnauthenticatedException;
import com.pratikshat.exicom.entity.BtCustomerInventoryIn;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.repository.GenericOperation;

public interface BtCustomerInventoryInCustomRepository extends GenericOperation<BtCustomerInventoryIn> {

    public List<Map<String,Object>> getAdvanceSearchResult(Map<String,Object> request,List<Long> locationIds,LoginUser user) throws BadRequestException,UnauthenticatedException;
    
}
