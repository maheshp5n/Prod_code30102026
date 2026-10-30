package com.pratikshat.exicom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.entity.ProductLineItemProductState;
import com.pratikshat.exicom.entity.ProductLineItemStatus;
import com.pratikshat.exicom.repository.ProductLineItemProductStateRepository;
import com.pratikshat.exicom.repository.ProductLineItemStatusRepository;

@RestController
@RequestMapping("api/productlineitem")
public class ProductLineItemController {

	@Autowired
	ProductLineItemProductStateRepository dbProductLineItemProductState;
	
	@Autowired
	ProductLineItemStatusRepository dbProductLineItemStatus;
	
	
	@GetMapping("productstate")
	public List<ProductLineItemProductState> getProductState(){
		return dbProductLineItemProductState.findAll();
	}
	
	
	@GetMapping("status")
	public List<ProductLineItemStatus> getStatus(){
		return dbProductLineItemStatus.findAll();
	}
}
