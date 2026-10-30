package com.pratikshat.exicom.repository;


import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.request.DeliveryChallanRequest;
import com.pratikshat.exicom.model.response.DeliveryChallanResponse;

public interface DeliveryChallanRepositoryCustom {
		public DeliveryChallanRequest createDeliveryChallan(DeliveryChallanRequest deliveryChallanRequest,LoginUser user) throws Exception;
}
