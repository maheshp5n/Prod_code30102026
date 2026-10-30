package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtJobCardQc;
import com.pratikshat.exicom.entity.BtJobCardQcFile;
import com.pratikshat.exicom.repository.custom.BtJobCardQcFileRepositoryCustom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BtJobCardQcFileRepository extends JpaRepository<BtJobCardQcFile,Long>,BtJobCardQcFileRepositoryCustom{
    List<BtJobCardQcFile> findByJobCardQc(BtJobCardQc jobCardQc);
    @Query(value = "SELECT * FROM bt_job_card_qc_file jcf join  (select max(id) as id_max from bt_job_card_qc_file where job_card_qc_id=:id) ff on ff.id_max=jcf.id",nativeQuery = true)
    Map<String,Object> getByJobCardQcId(@Param("id") Long id);
}
