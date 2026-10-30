package com.pratikshat.exicom.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.services.BtTobeApprovedService;

@RestController
@RequestMapping("/bt/api/tobeapproved")
public class BtTobeApprovedController {
	
	
	@Autowired
	BtTobeApprovedService dbservice;
	
	@GetMapping
	public Page<Map<String,Object>> getPendingApproval(@CurrentUser LoginUser user,Pageable page){
		return dbservice.getPendingPage(page, user);
	}
	
	@PutMapping("{id}")
	public Map<String,Object> updateAction(@CurrentUser LoginUser user,@PathVariable("id")Long id,@RequestBody Map<String,Object> action) throws BadRequestException, UnauthoriseException{
		if(!id.equals(Long.parseLong(action.get("id").toString()))) {
			throw new BadRequestException("id mismatch");
		}
		return dbservice.updateObject(action, user);
	}
	
	@PutMapping("/multiple")
	public Map<String,Object> updateMultipleAction(@CurrentUser LoginUser user, @RequestBody List<Map<String,Object>> actions) throws BadRequestException, UnauthoriseException{

		List<Map<String, Object>> responseList = new ArrayList<Map<String, Object>>();	//to store responses for each action
		//Iterate over actions provided in request body and update object for each request object in the list
		for(Map<String, Object> action : actions) {
			Map<String, Object> result = dbservice.updateObject(action, user);
			responseList.add(result);
		}
		return new HashMap<String, Object>(){{put("Response: ", responseList);}};	//send final response as list of objects received as result from db
	}
		
	
	@GetMapping("/single/approval")
	public Map<String, Object> getToBeApprovedBasedOnIdAndObjectName(@RequestParam(value ="objectId") long objectId,@RequestParam(value ="objectName") String objectName,@CurrentUser LoginUser user){
		
		return dbservice.getToBeApprovedBasedOnIdAndObjectName(objectId,objectName,user);
	}

	@GetMapping("/advanceSearch")
	public List<Map<String, Object>> getAdvanceSarch(@RequestParam Map<String, Object> search, @CurrentUser LoginUser user) throws UnauthoriseException
	{
		try
		{
			List<Map<String, Object>> result= dbservice.advanceSearch(search, user);
			return result;
		}
		catch(BadRequestException ex)
		{
			ex.printStackTrace();
		}
		return null;
		
	}
}
