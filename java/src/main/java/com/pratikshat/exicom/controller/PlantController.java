package com.pratikshat.exicom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.entity.PlantDetails;
import com.pratikshat.exicom.repository.PlantRepository;

@RestController
@RequestMapping("api/plant")
public class PlantController {
	@Autowired
	private PlantRepository dbPlant;
	@GetMapping
	public List<PlantDetails> getAll(){
		return dbPlant.findAll();
	}
}	
