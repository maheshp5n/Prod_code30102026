package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.AccountsType;

@Repository
public interface AccountsTypeRepository extends JpaRepository<AccountsType, String> {
	List<AccountsType> findAll();
}
