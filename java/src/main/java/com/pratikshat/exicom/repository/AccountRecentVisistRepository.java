package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.AccountRecentVisit;

@Repository
public interface AccountRecentVisistRepository extends JpaRepository<AccountRecentVisit, String> {

}
