package com.pratikshat.exicom.repository.custom;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtServiceOrderItem;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.repository.GenericOperation;

public interface BtServiceOrderItemRepositoryCustom extends GenericOperation<BtServiceOrderItem> {
    public List<Map<String,Object>> advanceSearch(Long serviceOrderId,Map<String,Object> request,List<Long> locationIds,LoginUser user);
}
