package com.pratikshat.exicom.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.Warehouse;
import com.pratikshat.exicom.model.response.WarehouseResponse;

@Repository
public interface WarehouseRepo extends JpaRepository<Warehouse,Long>,WarehouseRepoCustome {
	int countByWarehouseId(long warehouseId);
	com.pratikshat.exicom.entity.Warehouse findByWarehouseId(long warehouseId);
	@Query(value="select new com.pratikshat.exicom.model.response.WarehouseResponse(w) from Warehouse w left join w.legalEntity l left join w.createdBy c left join w.modifiedBy m")
	Page<WarehouseResponse> getByAll(Pageable page);
	@Query("select new com.pratikshat.exicom.model.response.WarehouseResponse(w) from Warehouse w left join w.legalEntity l left join w.createdBy c left join w.modifiedBy m where w.warehouseId=:id")
	WarehouseResponse getByWarehouseId(@Param("id")long id);
	@Query("select new com.pratikshat.exicom.model.response.WarehouseResponse(w) from Warehouse w left join w.legalEntity l left join w.createdBy c left join w.modifiedBy m join WarehouseRecentVisit wr on w.warehouseId=wr.warehouseId where wr.visitorId =:visitorId")
	Page<WarehouseResponse> getRecentVisit(@Param("visitorId")String username, Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.WarehouseForList(w.warehouseId,w.warehouseName) from Warehouse w")
	java.util.List<com.pratikshat.exicom.model.response.WarehouseForList> getWarehouseForList();
}
