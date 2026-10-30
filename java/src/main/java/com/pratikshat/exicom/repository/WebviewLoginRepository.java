package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.WebviewLogin;

@Repository
public interface WebviewLoginRepository extends JpaRepository<WebviewLogin, String> {
	WebviewLogin findByStoAndServerHashAndCallbackurl(String sto,String serverhash,String callbackurl);
}
