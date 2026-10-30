package com.pratikshat.exicom.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.services.PageFilterSetupService;

@RestController
@RequestMapping("api/profilefiltersetup")
public class ProfileFilterSetupController {
	@Autowired
	PageFilterSetupService pageService;
	
	@GetMapping("allurl")
	public List<Map<String,Object>> getAllObjectUrls(){
		return pageService.getNaviationModules();
	}
	@GetMapping("profile")
	public List<Map<String,Object>> getAllProfile(){
		return pageService.getProfiles();
	}
	@GetMapping("urlwithprofile")
	public List<Map<String,Object>> getPageFilterByUrlAndProfile(@RequestParam("profileid")long profileid,@RequestParam("navigationid")long nvId){
		return pageService.getFilters(nvId, profileid);
	}
	@PostMapping
	public String saveProfileFilter(@RequestBody List<Map<String,Object>> data) {
		pageService.saveProfileFilters(data);
		return "{}";
	}
}
