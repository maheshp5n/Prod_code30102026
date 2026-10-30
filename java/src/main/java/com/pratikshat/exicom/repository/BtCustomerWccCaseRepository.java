
  package com.pratikshat.exicom.repository;
 
  import org.springframework.data.jpa.repository.JpaRepository; 
  import org.springframework.stereotype.Repository;
 
  import com.pratikshat.exicom.entity.BtCustomerWccCase;
 
  @Repository 
  public interface BtCustomerWccCaseRepository extends JpaRepository<BtCustomerWccCase, Long>{
 
 }
 