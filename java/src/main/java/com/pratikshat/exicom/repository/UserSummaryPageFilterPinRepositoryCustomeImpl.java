package com.pratikshat.exicom.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.UserSummaryPageFilter;
import com.pratikshat.exicom.entity.UserSummaryPageFilterPin;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

@Repository
public class UserSummaryPageFilterPinRepositoryCustomeImpl implements UserSummaryPageFilterPinRepositoryCustome {

	@PersistenceContext
	EntityManager em;
	
	@Override
	@Transactional
	public void pinFilter(Long id, LoginUser user) throws BadRequestException, UnauthoriseException {
		EmployeeDetails emp=new EmployeeDetails(user.getUsername());
		UserSummaryPageFilter filter=null;
		
		try {
			filter=em.createQuery("select flt from UserSummaryPageFilter flt join flt.userProfile as nvm join flt.pageFilter pflt where flt=?1",UserSummaryPageFilter.class)
					.setParameter(1, new UserSummaryPageFilter(id))
					.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Filter not found");
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Invalid filter ID");
		}
		List<UserSummaryPageFilterPin> extPinList=
		em.createQuery("select pin from UserSummaryPageFilterPin pin join pin.filter flt join flt.userProfile nvm where pin.user=?1 and nvm=?2",UserSummaryPageFilterPin.class)
		.setParameter(1, emp)
		.setParameter(2,filter.getUserProfile())
		.getResultList();
		if(extPinList!=null&&extPinList.size()>0) {
			for(UserSummaryPageFilterPin p:extPinList) {
				em.createQuery("delete from UserSummaryPageFilterPin pin where pin=?1").setParameter(1, p).executeUpdate();
			}
			
		}
		
		UserSummaryPageFilterPin pin=new UserSummaryPageFilterPin();
		pin.setFilter(filter);
		pin.setPind(true);
		pin.setUser(emp);
		
		em.persist(pin);
	}

}
