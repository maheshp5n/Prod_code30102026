package com.pratikshat.exicom.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.entity.HelpGuide;
import com.pratikshat.exicom.repository.HelpGuideRepository;

@RestController
@RequestMapping("api/help")
public class HelpGuideController {
	@Autowired
	HelpGuideRepository db;
	
	@GetMapping
	public Page<HelpGuide> getBySearchName(@RequestParam(value="language",required=false)String language,@RequestParam(value="platform",required=false)String platform,Pageable page){
		if(language==null&&platform==null) {
			return db.findAll(page);
		}
		if(language!=null&&platform!=null&&!language.equalsIgnoreCase("")&&!platform.equalsIgnoreCase(""))
			return db.findByLanuguageAndPlatForm(language, platform,page);
		if(language!=null&&!language.equalsIgnoreCase(""))
			return db.findByLanuguage(language,page);
		if(platform!=null&&!platform.equalsIgnoreCase(""))
			return db.findByPlatForm(platform,page);
		return Page.empty();
	}
}
