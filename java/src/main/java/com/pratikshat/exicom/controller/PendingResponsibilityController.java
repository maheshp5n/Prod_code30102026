package com.pratikshat.exicom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.entity.PendingResponsibility;
import com.pratikshat.exicom.repository.PendingResponsibilityRepository;

@RestController
@RequestMapping("pendingresponsibility")
public class PendingResponsibilityController {
	
	@Autowired
	PendingResponsibilityRepository dbPendingResponsibility;
	
	@GetMapping
	public List<PendingResponsibility> getAll(){
		return dbPendingResponsibility.findAll();
	}

}
