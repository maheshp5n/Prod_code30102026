package com.pratikshat.exicom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.entity.CountryDetails;
import com.pratikshat.exicom.entity.StateProvinceDetails;
import com.pratikshat.exicom.repository.CountryDetailsRepository;
import com.pratikshat.exicom.repository.StateProvinceRepository;

@RestController
@RequestMapping("api/country")
public class CountryDetailsController {
	@Autowired
	private CountryDetailsRepository dbCountry;
	@Autowired
	private StateProvinceRepository dbState;
	
	@GetMapping
	public List<CountryDetails> getAllCountry(){
		return dbCountry.findAll();
	}
	@GetMapping("{id}")
	public CountryDetails getCountry(@PathVariable("id")String id) {
		return dbCountry.findByConuntryId(id);
	}
	@GetMapping("{id}/state")
	public List<StateProvinceDetails> getStateByCountryCode(@PathVariable("id")String id){
		return dbState.findByCountry_conuntryId(id);
	}
}
