package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BRCRepairInventoryDispatch;
import com.pratikshat.exicom.entity.BRCRepairInventoryReceive;
import com.pratikshat.exicom.entity.BRCRepairInventoryReceiveRepairItem;
import com.pratikshat.exicom.entity.LoginUser;

public interface BRCRepairInventoryReceiveRepositoryCustome {
	com.pratikshat.exicom.model.request.BRCRepairInventoryReceiveRequest saveRequest(com.pratikshat.exicom.model.request.BRCRepairInventoryReceiveRequest request,com.pratikshat.exicom.entity.LoginUser user)throws Exception;
	com.pratikshat.exicom.model.request.BRCRepairInventoryReceiveRequest updateRequest(com.pratikshat.exicom.model.request.BRCRepairInventoryReceiveRequest request,com.pratikshat.exicom.entity.LoginUser user) throws Exception;
	//com.pratikshat.exicom.model.request.BRCRepairInventoryReceiveItemRequest saveItemRequest(com.pratikshat.exicom.model.request.BRCRepairInventoryReceiveItemRequest itemRequest,com.pratikshat.exicom.entity.LoginUser user) throws Exception;
	BRCRepairInventoryReceive saveDispatch(com.pratikshat.exicom.model.request.BRCRepairInventoryDispatchRequest dispatchRequest,com.pratikshat.exicom.entity.LoginUser user) throws Exception;
	BRCRepairInventoryReceiveRepairItem assignToEng(long receivingId, long serviceResourceId, LoginUser user) throws Exception;
	com.pratikshat.exicom.model.request.BRCRepairInventoryReceiveItemRequest receiveFromEng(com.pratikshat.exicom.model.request.BRCRepairInventoryReceiveItemRequest itemRequest,com.pratikshat.exicom.entity.LoginUser user) throws Exception;
	com.pratikshat.exicom.model.request.BRCRepariInventoryProductConsumedRequest consumeProduct(com.pratikshat.exicom.model.request.BRCRepariInventoryProductConsumedRequest consumeRequest,com.pratikshat.exicom.entity.LoginUser uesr) throws Exception;
}
