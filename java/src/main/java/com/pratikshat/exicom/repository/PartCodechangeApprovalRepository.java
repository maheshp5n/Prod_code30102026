package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.PartCodechangeApproval;

@Repository
public interface PartCodechangeApprovalRepository extends JpaRepository<PartCodechangeApproval, Long> {
	@Query("select apr from PartCodechangeApproval apr where apr.active=:active and (apr.initRole like :initRole or apr.initRole like :userName)")
	List<PartCodechangeApproval> findByActiveAndInitRoleLikeOrInitRoleLike(@Param("active")Boolean active,@Param("initRole")String initRole,@Param("userName")String userName);
}
