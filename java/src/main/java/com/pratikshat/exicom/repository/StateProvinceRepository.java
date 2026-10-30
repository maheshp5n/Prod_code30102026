package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.StateProvinceDetails;
@Repository
public interface StateProvinceRepository extends JpaRepository<StateProvinceDetails, Long> {
	public List<StateProvinceDetails> findByCountry_conuntryId(String countryId);
}
