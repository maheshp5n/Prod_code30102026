package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.OtherChargesTypes;

@Repository
public interface OtherChargesTypesRepository extends JpaRepository<OtherChargesTypes, Integer> {

	@Query("select oct from OtherChargesTypes oct left join oct.businessDoamin bd where bd.businessDomainId=:id")
	List<OtherChargesTypes> getByBusinessDoamin_BusinessDomainId(@Param("id")int businessDomainId);
	
	@Query(value ="SELECT octs.id,octs.name,octs.synced FROM other_charges_types octs LEFT JOIN business_domain bd ON octs.business_doamin_business_domain_id=bd.business_domain_id  WHERE octs.business_doamin_business_domain_id=:businessDomainId",nativeQuery = true)
	List<Map<String, Object>> getByBusinessDoaminBusinessDomainId(@Param("businessDomainId")int businessDomainId);
}
