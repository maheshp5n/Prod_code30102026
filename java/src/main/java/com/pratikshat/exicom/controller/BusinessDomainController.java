package com.pratikshat.exicom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.repository.BusinessDomainRepository;

@RestController
@RequestMapping("api/businessdomain")
public class BusinessDomainController {

	@Autowired
	BusinessDomainRepository dbBusinessDomain;
	
	@GetMapping
	public List<BusinessDomain> getBusinessDomain(){
		return dbBusinessDomain.findAll();
	}
}
