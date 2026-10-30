package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.MrnDetails;
@Repository
public interface MrnDetailsRepository extends JpaRepository<MrnDetails, Long> {

}
