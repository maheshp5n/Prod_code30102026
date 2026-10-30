package com.pratikshat.exicom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.entity.BtSiteStatus;
import com.pratikshat.exicom.entity.BtSiteType;
import com.pratikshat.exicom.repository.BtSiteStatusRepository;
import com.pratikshat.exicom.repository.BtSiteTypeRepository;

@RestController
@RequestMapping("bt/api/status/type")
public class BtSiteAndStatusController {
	
	@Autowired
	BtSiteTypeRepository dbTypeRepository;
	
	@Autowired
	BtSiteStatusRepository dbStatusRepository;
	
	
	@GetMapping("type")
	List<BtSiteType> getvalue(){
		return dbTypeRepository.findAll();
	}
	@GetMapping("status")
	List<BtSiteStatus> getvalueofStatus(){
		return dbStatusRepository.findAll();
	}

}
