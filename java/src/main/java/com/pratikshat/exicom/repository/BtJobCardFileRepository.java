package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtJobCard;
import com.pratikshat.exicom.entity.BtJobCardFile;
import com.pratikshat.exicom.repository.custom.BtJobCardFileRepositoryCustom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BtJobCardFileRepository extends JpaRepository<BtJobCardFile,Long>,BtJobCardFileRepositoryCustom{
    List<BtJobCardFile> findByJobCard(BtJobCard jobCard);
    @Query(value = "select * from bt_job_card_file jcf join (select max(id) as id_max from bt_job_card_file where job_card_id=:id) ff on ff.id_max= jcf.id",nativeQuery = true)
    Map<String,Object> getByJobCardId(@Param("id") Long id);
}
