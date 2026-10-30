package com.pratikshat.exicom.services;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface BtEngRatingService {

	String getRatingPage(long saId, Model model, String vf, RedirectAttributes rda);

	String redirectToThankyou(long id, int rattong, String remarks, String vf, RedirectAttributes rd);

	String thankYouPage(String message, Model model);

}
