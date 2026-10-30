package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtServiceAppointmentExpense;

@Repository
public interface BtServiceAppointmentExpenseRepository extends JpaRepository<BtServiceAppointmentExpense, Long> {

}
