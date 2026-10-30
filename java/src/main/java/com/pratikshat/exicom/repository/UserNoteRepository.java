package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.UserNote;

@Repository
public interface UserNoteRepository extends JpaRepository<UserNote, Long> {
	List<UserNote> findByUserEmailId(String userEmailId);
	@Modifying
	@Transactional
	void deleteById(long id);
}
