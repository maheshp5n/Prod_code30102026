package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.DeliveryChallan;

@Repository
public interface DeliveryChallanRepository extends JpaRepository<DeliveryChallan, Long>,DeliveryChallanRepositoryCustom{

}
