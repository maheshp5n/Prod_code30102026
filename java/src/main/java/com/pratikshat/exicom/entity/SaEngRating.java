package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class SaEngRating {

	@Id
	@Column(name="sa_id")
	long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name="sa_id")
	ServiceAppointment sa;
	@Column(length = 500)
	String userRemarks;
	
	int engrating;
	
	Date ratingDate;
	
	public SaEngRating() {}
	
	public SaEngRating(long id) {
		this.id=id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ServiceAppointment getSa() {
		return sa;
	}

	public void setSa(ServiceAppointment sa) {
		this.sa = sa;
	}

	public String getUserRemarks() {
		return userRemarks;
	}

	public void setUserRemarks(String userRemarks) {
		this.userRemarks = userRemarks;
	}


	public Date getRatingDate() {
		return ratingDate;
	}

	public void setRatingDate(Date ratingDate) {
		this.ratingDate = ratingDate;
	}

	public int getEngrating() {
		return engrating;
	}

	public void setEngrating(int engrating) {
		this.engrating = engrating;
	}
	
	
}
