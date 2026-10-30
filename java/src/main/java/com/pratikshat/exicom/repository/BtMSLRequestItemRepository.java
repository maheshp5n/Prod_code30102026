package com.pratikshat.exicom.repository;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtMSLRequestItem;

@Repository
public interface BtMSLRequestItemRepository extends JpaRepository<BtMSLRequestItem, Long> {

	BtMSLRequestItem findById(long id);
}
