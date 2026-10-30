package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ContactsRecentVisit;

@Repository
public interface ContactsRecentVisitRepository extends JpaRepository<ContactsRecentVisit, Long> {
	ContactsRecentVisit findByContactIdAndVisitedByid(long contactId,String visitorId);
}
