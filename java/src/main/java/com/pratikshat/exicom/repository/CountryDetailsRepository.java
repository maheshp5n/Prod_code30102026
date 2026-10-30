package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.CountryDetails;
@Repository
public interface CountryDetailsRepository extends JpaRepository<CountryDetails, String> {
	public List<CountryDetails> findAll();
	public CountryDetails findByConuntryId(String countryId);
}
