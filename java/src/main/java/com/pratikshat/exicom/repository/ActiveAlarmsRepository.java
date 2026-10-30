package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pratikshat.exicom.entity.ActiveAlarms;

public interface ActiveAlarmsRepository extends JpaRepository<ActiveAlarms, Long> {
	
	@Query(value = "SELECT aa.`id` AS id ,aa.`type_name` AS activeAlarmValue  FROM `active_alarms` aa",nativeQuery = true)
	public List<Map<String,Object>> getActiveAlarms();

}
