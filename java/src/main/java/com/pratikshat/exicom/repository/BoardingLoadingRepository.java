package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BoardingLoadingDetails;
import com.pratikshat.exicom.model.response.BoardingLoadingDetailsResponse;
import com.pratikshat.exicom.model.response.BoardingLoadingForListResponse;
@Repository
public interface BoardingLoadingRepository extends JpaRepository<BoardingLoadingDetails, String> {
	
	@Query("select new com.pratikshat.exicom.model.response.BoardingLoadingDetailsResponse(bd) from BoardingLoadingDetails bd left join bd.accountOwner ao left join bd.createdBy cb left join bd.modifiedBy mb")
	public Page<BoardingLoadingDetailsResponse> getAll(Pageable page);
	
	
	@Query("select new com.pratikshat.exicom.model.response.BoardingLoadingDetailsResponse(bd) from BoardingLoadingDetails bd left join bd.accountOwner ao left join bd.createdBy cb left join bd.modifiedBy mb where bd.boardingName=:boardingName")
	public BoardingLoadingDetailsResponse getByBoardingName(@Param("boardingName")String boardingName);
	
	public BoardingLoadingDetails findByBoardingName(String boardingName);
	
	
	
	@Query("select new com.pratikshat.exicom.model.response.BoardingLoadingForListResponse(bd.boardingName) from BoardingLoadingDetails bd")
	public List<BoardingLoadingForListResponse> getBoardingForList();
}
