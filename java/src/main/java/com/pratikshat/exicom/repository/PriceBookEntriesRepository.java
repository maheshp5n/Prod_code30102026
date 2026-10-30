package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.PriceBookEntries;
import com.pratikshat.exicom.model.response.PriceBookEntriesResponse;
import com.pratikshat.exicom.model.response.PriceBookResponse;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceBookEntriesRepository
		extends JpaRepository<PriceBookEntries, Long>, PriceBookEntriesRepositoryCustom {

	@Query("select new com.pratikshat.exicom.model.response.PriceBookEntriesResponse(pbe) from PriceBookEntries pbe left join pbe.product prd left join pbe.priceBook pb left join pbe.createdBy cby left join pbe.modifiedBy mby where pbe.id=:id")
	PriceBookEntriesResponse getById(@Param("id") long id);

	@Query("select new com.pratikshat.exicom.model.response.PriceBookEntriesResponse(pbe) from PriceBookEntries pbe left join pbe.product prd left join pbe.priceBook pb left join pbe.createdBy cby left join pbe.modifiedBy mby where pb.id=:priceBookId")
	Page<PriceBookEntriesResponse> getByPriceBookId(@Param("priceBookId") long priceBookId, Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.PriceBookEntriesResponse(pbe) from PriceBookEntries pbe left join pbe.product prd left join pbe.priceBook pb left join pbe.createdBy cby left join pbe.modifiedBy mby where pbe.product.productPartCode=:partCode")
	Page<PriceBookEntriesResponse> getPriceBookByProductPartCode(@Param("partCode") String partCode, Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.PriceBookEntriesResponse(pbe) from PriceBookEntries pbe left join pbe.product prd left join pbe.priceBook pb left join pbe.createdBy cby left join pbe.modifiedBy mby where pb.id=:priceBookId")
	List<PriceBookEntriesResponse> getByPriceBookId(@Param("priceBookId") long priceBookId);
}
