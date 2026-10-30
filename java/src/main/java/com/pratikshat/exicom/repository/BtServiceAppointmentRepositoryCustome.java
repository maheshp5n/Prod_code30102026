package com.pratikshat.exicom.repository;

import java.util.List;

import com.pratikshat.exicom.entity.BtServiceAppointment;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public interface BtServiceAppointmentRepositoryCustome extends GenericOperation<BtServiceAppointment> {
	
	public BtServiceAppointment getServiceAppointment(long id) throws  BadRequestException;
	
	public List<BtServiceAppointment> getListWithCaseIDandStatus(long caseId,List<Long> saStatusList) throws BadRequestException;
	
	List<BtServiceAppointment> getListWithCaseId(long caseId) throws BadRequestException;
	
	
	EmployeeDetails caseCreatedByEmployee(String email) throws BadRequestException,UnauthoriseException;
	
}