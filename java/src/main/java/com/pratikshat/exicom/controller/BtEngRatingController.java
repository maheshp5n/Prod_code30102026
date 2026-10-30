package com.pratikshat.exicom.controller;


import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.pratikshat.exicom.services.BtEngRatingService;

@Controller
@RequestMapping("/btengrating")
public class BtEngRatingController {
	
	@Autowired
	BtEngRatingService engRatingService;
	
	@GetMapping({"id"})
	public String getRatingPage(@PathVariable("id") long saId,Model model,@RequestParam("vf") String vf,RedirectAttributes rda ){
		
		return engRatingService.getRatingPage(saId,model,vf,rda);

	}
	
	@PostMapping("{id}")
	@Transactional
	public String redirectToThankyou(@PathVariable("id") long id ,@RequestParam("rate") int rattong,
			@RequestParam(value = "remarks",required = false)String remarks,@RequestParam("vfd")String vf,RedirectAttributes rd){
		
		return engRatingService.redirectToThankyou(id,rattong,remarks,vf,rd);
		
		
	}
	
	@GetMapping("thankyou")
	public String thankYouPage(@RequestParam(value="message",required = false)String message,Model model) {
		
		return engRatingService.thankYouPage(message,model);
		
	}
	
}

