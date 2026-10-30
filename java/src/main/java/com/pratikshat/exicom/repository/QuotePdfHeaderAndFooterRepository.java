package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.QuotePdfHeaderAndFooter;
import com.pratikshat.exicom.model.response.QuotePdfHeaderAndFooterResponse;

@Repository
public interface QuotePdfHeaderAndFooterRepository extends JpaRepository<QuotePdfHeaderAndFooter, Long>, QuotePdfHeaderAndFooterRepositoryCustom{

	QuotePdfHeaderAndFooter findById(long id);
	
	@Query("select new com.pratikshat.exicom.model.response.QuotePdfHeaderAndFooterResponse(qhf) from QuotePdfHeaderAndFooter qhf left join qhf.createdBy cby left join qhf.modifiedBy mby left join qhf.businessDomain bd where qhf.id=:id")
	QuotePdfHeaderAndFooterResponse getById(@Param("id")long id);
	
	
	@Query("select new com.pratikshat.exicom.model.response.QuotePdfHeaderAndFooterResponse(qhf) from QuotePdfHeaderAndFooter qhf left join qhf.createdBy cby left join qhf.modifiedBy mby left join qhf.businessDomain bd where qhf.isActive=true")
	List<QuotePdfHeaderAndFooterResponse> getAllListByActive();
	
	@Query("select new com.pratikshat.exicom.model.response.QuotePdfHeaderAndFooterResponse(qhf) from QuotePdfHeaderAndFooter qhf left join qhf.createdBy cby left join qhf.modifiedBy mby left join qhf.businessDomain bd")
	Page<QuotePdfHeaderAndFooterResponse> getAllPageable(Pageable pageable);
}
