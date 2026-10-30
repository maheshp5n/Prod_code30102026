package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AddressInformationRepository extends JpaRepository<com.pratikshat.exicom.entity.AddressInformation, Long> {
	int countByAddressInfoId(long addressInfoId);
	com.pratikshat.exicom.entity.AddressInformation findByAddressInfoId(long addressInfoId);
}
