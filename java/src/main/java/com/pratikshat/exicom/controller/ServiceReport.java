package com.pratikshat.exicom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.entity.ServiceReportStatus;
import com.pratikshat.exicom.repository.ServiceReportStatusRepository;

@RestController
@RequestMapping("api/servicereport")
public class ServiceReport {

	@Autowired
	ServiceReportStatusRepository dbServiceReportStatus;
	
	@GetMapping("status")
	public List<ServiceReportStatus> getStatus(){
		return dbServiceReportStatus.findAll();
	}
}
