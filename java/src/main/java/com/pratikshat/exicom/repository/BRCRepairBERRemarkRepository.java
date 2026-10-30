package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BRCRepairBERRemark;

@Repository
public interface BRCRepairBERRemarkRepository extends JpaRepository<BRCRepairBERRemark, Integer> {

}
