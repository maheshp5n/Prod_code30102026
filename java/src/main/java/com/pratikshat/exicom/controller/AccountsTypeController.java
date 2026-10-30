package com.pratikshat.exicom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.entity.AccountsType;
import com.pratikshat.exicom.repository.AccountsTypeRepository;

@RestController
@RequestMapping("api/accountstype")
public class AccountsTypeController {

	@Autowired
	private AccountsTypeRepository dbType;
	@GetMapping
	public List<AccountsType> getAccountsType(){
		return dbType.findAll();
	}
}
