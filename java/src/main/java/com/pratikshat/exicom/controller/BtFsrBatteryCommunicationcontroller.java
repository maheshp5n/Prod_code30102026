package com.pratikshat.exicom.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtFsrBatteryCommunicationRepository;
import com.pratikshat.exicom.services.BtFsrBatteryCommunicationService;

@RestController
@RequestMapping("/bt/api/fsr/comm")
public class BtFsrBatteryCommunicationcontroller {
	
	@Autowired
	BtFsrBatteryCommunicationRepository dbfsr;
	
	@Autowired
	BtFsrBatteryCommunicationService serFsr;
	
	@PostMapping
	private Map<String, Object> saveFsrComm(@RequestBody Map<String, Object> req, @CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
		return serFsr.saveObject(req, user);
	}
	@PutMapping("{id}")
	private Map<String, Object> updateBtFsrOdc(@RequestBody Map<String, Object> req,@PathVariable("id") long id, @CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException {
		return serFsr.updateObject(req, user);
	}
	@GetMapping("forlist")
	private List<Map<String, Object>> forlist(@RequestParam("search") String search, @CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return serFsr.searchForList(search, user);
	}
	
	@GetMapping("advance")
	private List<Map<String, Object>> advanceSearch(@RequestParam Map<String ,Object> req, @CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return serFsr.advanceSearch(req, user);
	}
	@GetMapping("/{id}")
	private Map<String, Object> getById(@PathVariable( "id" )int id,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return  serFsr.getSingle(id, user);
		
	}
	@GetMapping()
	private Page<Map<String, Object>> getPage(@CurrentUser LoginUser user,Pageable page) throws BadRequestException, UnauthoriseException{
		return serFsr.getPage(page, user);
	}
	

}
