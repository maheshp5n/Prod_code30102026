package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ProductTransferEwaybill;

@Repository
public interface ProductTransferEwaybillRepository extends JpaRepository<ProductTransferEwaybill, Long>  {

	ProductTransferEwaybill findTopByProductTransferIdOrderByCreatedAtDesc(Long productTransferId);
}
