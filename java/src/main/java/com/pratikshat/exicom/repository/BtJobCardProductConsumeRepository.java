package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtJobCardProductConsume;

import com.pratikshat.exicom.repository.custom.BtJobCardProductConsumeRepositoryCustom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.pratikshat.exicom.entity.BtJobCard;

@Repository
public interface BtJobCardProductConsumeRepository extends JpaRepository<BtJobCardProductConsume,Long>,BtJobCardProductConsumeRepositoryCustom{
    @Query("select btns from BtJobCardProductConsume btns join btns.product prd where btns.jobCard=:jobCard")
    List<BtJobCardProductConsume> getByJobCard(@Param("jobCard") BtJobCard jobCardId);
}
