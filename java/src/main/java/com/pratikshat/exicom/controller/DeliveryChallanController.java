package com.pratikshat.exicom.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.DeliveryChallan;
import com.pratikshat.exicom.entity.DeliveryChallanShipmentMode;
import com.pratikshat.exicom.entity.DeliveryChallanStatus;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.request.DeliveryChallanRequest;
import com.pratikshat.exicom.model.response.DeliveryChallanResponse;
import com.pratikshat.exicom.repository.DeliveryChallanStatusRepository;
import com.pratikshat.exicom.repository.DeliveryChallanShipmentModeRepository;

@RestController
@RequestMapping("api/deliverychallan")
public class DeliveryChallanController {

	@Autowired
	DeliveryChallanStatusRepository dbDeliveryChalanStatus;
	@Autowired
	DeliveryChallanShipmentModeRepository dbDeliveryChallanShipmentMode;
	
	
	
	@GetMapping("status")
	public List<DeliveryChallanStatus> getStatus(){
		return dbDeliveryChalanStatus.findAll();
	}
	
	@GetMapping("shipmentmode")
	public List<DeliveryChallanShipmentMode> getShipmentMode(){
		return dbDeliveryChallanShipmentMode.findAll();
	}
	
	public DeliveryChallanResponse createDeliveryChallan(@Valid @RequestBody DeliveryChallanRequest deliveryChallanRequest,@CurrentUser LoginUser user,HttpServletResponse httpResponse) {
		
		
		
		return null;
	}
}
