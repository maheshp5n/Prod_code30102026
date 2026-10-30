package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pratikshat.exicom.entity.BRCRepiarBalancedAt;
import org.springframework.stereotype.Repository;

@Repository
public interface BRCRepiarBalancedAtRepository extends JpaRepository<BRCRepiarBalancedAt, Integer>{

}
