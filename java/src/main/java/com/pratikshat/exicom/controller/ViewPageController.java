package com.pratikshat.exicom.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.UserSummaryPageFilterPinRepository;
import com.pratikshat.exicom.services.SummeryPageMetaService;

@RestController
@RequestMapping("api/page")
public class ViewPageController {
	
	@Autowired
	SummeryPageMetaService mservice;
	
	@Autowired
	UserSummaryPageFilterPinRepository dbfilter;
	
	@GetMapping("filter/{pageurl}")
	public List<Map<String,Object>> getPagefilterMeta(@CurrentUser LoginUser user,@PathVariable("pageurl")String pageurl){
		
		return dbfilter.getSummaryPageFilter(user.getUsername(), "/"+pageurl);
	}
	
	@PostMapping("pin/{filterid}")
	public Map<String,Object> postPin(@PathVariable("filterid")Long filterid,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		
		dbfilter.pinFilter(filterid, user);
		
		return new HashMap<String,Object>();
	}
	
}
