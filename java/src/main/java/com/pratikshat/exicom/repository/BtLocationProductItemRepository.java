package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtLocationProductItem;

@Repository
public interface BtLocationProductItemRepository extends JpaRepository<BtLocationProductItem, Long>,BtLocationProductItemCustomeRepository{

}
