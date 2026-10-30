package com.pratikshat.exicom.repository;

import java.util.Map;

import com.pratikshat.exicom.entity.BtProductStatusChange;
import com.pratikshat.exicom.repository.custom.BtProductStatusChangeRepositoryCustom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BtProductStatusChangeRepository extends JpaRepository<BtProductStatusChange,Long>,BtProductStatusChangeRepositoryCustom {
    // @Query(value = "select ",nativeQuery = true)
    // public Map<String,Object> getSingle(@Param("id")Long id);
}
