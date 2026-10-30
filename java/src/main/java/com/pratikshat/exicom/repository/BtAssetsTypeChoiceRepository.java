package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtAssetsTypeChoice;

@Repository
public interface BtAssetsTypeChoiceRepository extends JpaRepository<BtAssetsTypeChoice, Integer>{
	
	@Query(value = "SELECT btatc.`id` AS id,btatc.`name` AS `name` FROM `bt_assets_type_choice` btatc",nativeQuery = true)
	public List<Map<String, Object>> forListOfAssetsType();
	
	@Query("select ast from BtAssetsTypeChoice ast where ast.id=:id")
	public BtAssetsTypeChoice getTypeById(int id);
	
	BtAssetsTypeChoice findByName(String name);

}
