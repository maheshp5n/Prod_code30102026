package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtVendorInvoiceSap;

@Repository
public interface BtVendorInvoiceSapRepository extends JpaRepository<BtVendorInvoiceSap, Long>,BtVendorInvoiceSapCustome {
	

}
