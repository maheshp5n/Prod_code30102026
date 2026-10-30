package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pratikshat.exicom.entity.ForgotPass;

public interface ForgoPassRepository extends JpaRepository<ForgotPass, String>,ForgetPassServiec {
	ForgotPass findByUserEmailId(String userEmailId);
}
