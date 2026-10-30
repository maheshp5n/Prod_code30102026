package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BRCRepiarStatus;

@Repository
public interface BRCRepiarStatusRepository extends JpaRepository<BRCRepiarStatus, Integer>{

}
