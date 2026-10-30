package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface BtJobCardService extends GenricService{
    List<Map<String,Object>> getRepariEngineerList(LoginUser user) throws BadRequestException,UnauthoriseException;

    public List<Map<String, Object>> saveObject(List<Map<String, Object>> request, LoginUser user) throws BadRequestException,UnauthoriseException;
    public Page<Map<String,Object>> getProductConsumed(Long jobCardId,Pageable page,LoginUser user) throws BadRequestException,UnauthoriseException;
    public List<Map<String,Object>> getProductConsumedList(Long jobCardId,LoginUser user)   throws BadRequestException,UnauthoriseException;
    public Page<Map<String,Object>> getJobCardQcDetails(Long jobCardId,LoginUser user,Pageable page) throws BadRequestException,UnauthoriseException;
    public List<Map<String,Object>> getJobCardQcDetailsList(Long jobCardId,LoginUser user) throws BadRequestException,UnauthoriseException;
   
    public Page<Map<String,Object>> getAssignedToMeJobCard(LoginUser user,Pageable page) throws BadRequestException,UnauthoriseException;
    public Page<Map<String,Object>> getAssignedToMeJobCardPending(LoginUser user,Pageable page) throws BadRequestException,UnauthoriseException;
    public Page<Map<String,Object>> getAssignedToMeRejected(LoginUser user,Pageable page) throws BadRequestException,UnauthoriseException;

    //DROP DOWN

    public List<Map<String,Object>> getResolutionMethod();
    public List<Map<String,Object>> getBatteryBalanceAt();
    public List<Map<String,Object>> getBerRemarks();
    public List<Map<String, Object>> getCorrectiveAction();
    public List<Map<String, Object>> getEngRemarks();

	Page<Map<String, Object>> getSubmitToQc(LoginUser user, Pageable page);

	Map<String, Object> jobCardReassign(Long id, Map<String, Object> request, LoginUser user) throws BadRequestException, UnauthoriseException;

	

	
}
