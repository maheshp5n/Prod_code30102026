package com.pratikshat.exicom.services.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pratikshat.exicom.entity.BtSaEngRating;
import com.pratikshat.exicom.entity.SaEngRating;
import com.pratikshat.exicom.model.response.BtServiceAppointmentResponse;
import com.pratikshat.exicom.repository.BtSaEngRatingRepository;
import com.pratikshat.exicom.repository.BtServiceAppointmentRepository;
import com.pratikshat.exicom.services.BtEngRatingService;
import com.pratikshat.exicom.services.BtServiceAppointmentService;

@Service
public class BtEngRatingServiceImpl implements BtEngRatingService {

	@Autowired
	BtServiceAppointmentRepository dbServiceAppointmentRepo;

	@Autowired
	BtSaEngRatingRepository dbSaEngRatingRepo;
	
	@Autowired
	BtServiceAppointmentService btServiceAppointmentService;

	@Override
	public String getRatingPage(long saId, Model model, String vf, RedirectAttributes rda) {
		if (vf == null || vf.equalsIgnoreCase("")||!btServiceAppointmentService.verifyCode(saId, vf)) {
			rda.addAttribute("message", "Invalid Request");
			return "redirect:/btengrating/thankyou";
		}
		Optional<BtSaEngRating> engRating = dbSaEngRatingRepo.findById(saId);

		if (engRating.isPresent()) {
			rda.addAttribute("message", "You have submitted your ratings already. Thank You.");
			return "redirect:/btengrating/thankyou";
		}

		BtServiceAppointmentResponse appointmentResponse = dbServiceAppointmentRepo.getServiceAppointmentDataById(saId);
		model.addAttribute("saData", appointmentResponse);
		model.addAttribute("vfd", vf);
		model.addAttribute("already", true);

		return "btengratting";
	}

	@Override
	public String redirectToThankyou(long id, int rattong, String remarks, String vf, RedirectAttributes rd) {
		if (vf == null || vf.equalsIgnoreCase("")||!btServiceAppointmentService.verifyCode(id, vf)) {
			rd.addAttribute("message", "Invalid requests");
			return "redirect:/btengrating/thankyou";
		}

		BtSaEngRating saRating = null;
		Optional<BtSaEngRating> engRating = dbSaEngRatingRepo.findById(id);
		if (engRating.isPresent())
			saRating = engRating.get();
		else
			new SaEngRating();
		if (saRating == null)
			saRating = new BtSaEngRating();

		saRating.setEngrating(rattong);
		saRating.setRatingDate(new Date());
		saRating.setBtServiceAppointment(dbServiceAppointmentRepo.findById(id).get());
		if (remarks != null && remarks.length() > 499) {
			remarks = remarks.substring(0, 499);
			saRating.setUserRemarks(remarks);
		}
		
		dbSaEngRatingRepo.save(saRating);
		
		return "redirect:/btengrating/thankyou";
	}

	@Override
	public String thankYouPage(String message, Model model) {
		if (message == null || message.equalsIgnoreCase(""))
			message = "Thank you, your feedback has been submitted successfully.";
		model.addAttribute("message", message);
		return "thankyou";
	}

}
