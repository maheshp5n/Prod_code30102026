package com.pratikshat.exicom.repository;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public interface ReportWizardCustome {
	//CREATE NEW REPORT WIZARD FOR ADMIN
	void createNewReportWizard(Map<String,Object> request,LoginUser user) throws BadRequestException,UnauthoriseException;
	//ADMIN ONLY CAN EDIT 
	void editReportWizard(Long Id,Map<String,Object> request,LoginUser user) throws BadRequestException,UnauthoriseException;
	//THE EXECUTER CAN EXECUTE
	List<Map<String,Object>> executeReport(Long Id,List<Map<String,String>> paeram,LoginUser user,List<String> lstUsers) throws BadRequestException,UnauthoriseException;
	//DELETEING EXISTING REPORT
	void deleteReportWizard(Long id,LoginUser user) throws BadRequestException;
	
	public List<Map<String, Object>> getAllReportByTypeAndExecuterAdvanceSearch(Map<String, Object> request, String role_user_type, String role_user_value) throws ParseException;
}
