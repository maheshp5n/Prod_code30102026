package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.OwnConveyance;

@Repository
public interface OwnConveyanceRepository extends JpaRepository<OwnConveyance, Integer> {

	@Query("select owc from OwnConveyance owc left join owc.modeOfTravel mt where mt.id=:id")
	List<OwnConveyance> getByModeOfTravel_id(@Param("id")int id);
	
	@Query(value ="SELECT owc.id,owc.name,owc.synced,owc.mode_of_travel_id FROM own_conveyance owc LEFT JOIN mode_of_travel mt ON mt.id=owc.mode_of_travel_id  WHERE mt.id=:id",nativeQuery = true)
	List<Map<String, Object>> getByModeOfTravelId(@Param("id")int id);
}
