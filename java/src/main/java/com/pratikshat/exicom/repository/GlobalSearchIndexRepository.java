package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.GlobalSearchIndex;
@Repository
public interface GlobalSearchIndexRepository extends JpaRepository<GlobalSearchIndex, Long> {
	@Query("from GlobalSearchIndex gi where gi.searchString like %:searchKeyWord%")
	java.util.List<GlobalSearchIndex> findBySearchString(@Param("searchKeyWord")String searchKeyWord);
}
