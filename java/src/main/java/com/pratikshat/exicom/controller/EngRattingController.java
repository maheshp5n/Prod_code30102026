package com.pratikshat.exicom.controller;

import java.util.Date;
import java.util.Optional;

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

import com.pratikshat.exicom.entity.SaEngRating;
import com.pratikshat.exicom.entity.ServiceAppointment;
import com.pratikshat.exicom.model.response.ServiceAppointmentResponse;
import com.pratikshat.exicom.repository.SaEngRatingRepository;
import com.pratikshat.exicom.repository.ServiceAppointmentRepository;

@Controller
@RequestMapping("/engrating")
public class EngRattingController {

	@Autowired
	ServiceAppointmentRepository saDb;
	
	@Autowired SaEngRatingRepository saRatingDb;
	
	@GetMapping("{id}")
	public String getRattingpage(
			@PathVariable("id")long said,
			Model modal,
			@RequestParam("vf")String vf,
			RedirectAttributes rda) {
		if(vf==null||vf.equalsIgnoreCase("")||!saDb.verifyCode(said, vf)) {
			rda.addAttribute("message", "Invalid Request");
			return "redirect:/engrating/thankyou";
		}
			
		
		Optional<SaEngRating> opt=saRatingDb.findById(said);
		if(opt.isPresent()) {
			rda.addAttribute("message","You have submitted your ratings already. Thank You.");
			return "redirect:/engrating/thankyou";
		}
			
		
		ServiceAppointmentResponse sa=saDb.getByAppointmentId(said);
		modal.addAttribute("saData",sa);
		modal.addAttribute("vfd",vf);
		modal.addAttribute("alraldy","true");
		return "engratting";
	}
	
	@PostMapping("{id}")
	@Transactional
	public String redirectToThandkyou(@RequestParam("rate") int rattong,
			@RequestParam(value="remarks",required=false)String remarks,
			@PathVariable("id")long id,
			@RequestParam("vfd")String vf,
			RedirectAttributes rd) {
		
		System.out.println("code "+vf+" Id "+id+" Rating "+rattong);
		
		if(vf==null||vf.equalsIgnoreCase("")||!saDb.verifyCode(id, vf)) {
			rd.addAttribute("message","Invalid request");
			return "redirect:/engrating/thankyou";
		}
			
		
		SaEngRating saRatting=null;
		Optional<SaEngRating> opt=saRatingDb.findById(id);
		if(opt.isPresent())
			saRatting=opt.get();
		else
			new SaEngRating();
		if(saRatting==null)
			saRatting=new SaEngRating();
		
		saRatting.setEngrating(rattong);
		saRatting.setRatingDate(new Date());
		//saRatting.setId(id);
		saRatting.setSa(saDb.findById(id).get());
		if(remarks!=null&&remarks.length()>499)
			remarks=remarks.substring(0, 499);
		saRatting.setUserRemarks(remarks);
		
		saRatingDb.save(saRatting);
		
		return "redirect:/engrating/thankyou";
	}
	@GetMapping("thankyou")
	public String thankYouPage(
			@RequestParam(value="message",required=false)String message,Model model) {
		if(message==null||message.equalsIgnoreCase(""))
			message="Thank you, your feedback has been submitted successfully.";
		model.addAttribute("message", message);
		return "thankyou";
	}
	
}
