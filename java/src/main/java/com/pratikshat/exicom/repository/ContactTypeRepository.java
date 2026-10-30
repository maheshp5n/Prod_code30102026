package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ContactType;

@Repository
public interface ContactTypeRepository extends JpaRepository<ContactType,Long> {

	List<ContactType> findAll();
}
