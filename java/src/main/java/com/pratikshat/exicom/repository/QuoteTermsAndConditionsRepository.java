package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.QuoteTermsAndConditions;
import com.pratikshat.exicom.model.response.TermsAndConditionsResponse;

@Repository
public interface QuoteTermsAndConditionsRepository extends JpaRepository<QuoteTermsAndConditions,Long> {

	@Query("select new com.pratikshat.exicom.model.response.TermsAndConditionsResponse(tc) from QuoteTermsAndConditions tc left join tc.createdBy cby left join tc.modifiedBy mby where tc.objectName=:objectName and tc.isActive=:isActive order by tc.serialNumber asc")
	List<TermsAndConditionsResponse> getByObjectNameAndActiveOrderBySerialNumberAsc(@Param("objectName")String objectName,@Param("isActive")boolean isActive);
}
