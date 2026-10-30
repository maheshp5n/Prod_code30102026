package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.TaxAndVisitCharges;

@Repository
public interface TaxAndVisitChargesRepository extends JpaRepository<TaxAndVisitCharges, Integer> {
	TaxAndVisitCharges findByBusinessDomain_BusinessDomainId(int businessDomainId);
}
