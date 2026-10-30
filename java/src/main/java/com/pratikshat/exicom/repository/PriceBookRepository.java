package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.PriceBook;
import com.pratikshat.exicom.model.response.PriceBookForList;
import com.pratikshat.exicom.model.response.PriceBookResponse;

@Repository
public interface PriceBookRepository extends JpaRepository<PriceBook, Long>,PriceBookRepositoryCustom {

	@Query("select new com.pratikshat.exicom.model.response.PriceBookResponse(pb) from PriceBook pb left join pb.createdBy cby left join pb.modifiedBy mby where pb.id=:id")
	PriceBookResponse getById(@Param("id")long id);
	
	
	@Query("select new com.pratikshat.exicom.model.response.PriceBookResponse(pb) from PriceBook pb left join pb.createdBy cby left join pb.modifiedBy mby")
	Page<PriceBookResponse> getAll(Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.PriceBookForList(pb.id, pb.name) from PriceBook pb left join pb.createdBy cby left join pb.modifiedBy mby")
	List<PriceBookForList> getAllPriceBookList();
	
}
