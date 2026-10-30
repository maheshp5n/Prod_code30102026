package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtWccDetails;
import com.pratikshat.exicom.entity.BtWccServiceAppointment;

@Repository
public interface BtWccServiceAppointmentRepository extends JpaRepository<BtWccServiceAppointment, Long>{
	
	@Query("select ws from BtWccServiceAppointment ws join ws.serviceAppointment sr join ws.poDetails po join sr.serviceResource srr join srr.contact ct join ct.legalEntity lg where ws.wcc=:wcc")
	List<BtWccServiceAppointment> getAllAppointmentWithWcc(BtWccDetails wcc);
	
	@Modifying
	@Query("delete from BtWccServiceAppointment ws where ws.wcc=:wcc")
	public void deleteAppointmentWithWcc(BtWccDetails wcc);
}
