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
public class BtSaEngRating {

	@Id
	@Column(name = "sa_id")
	long id;
	
	int engrating;
	
	String userRemarks;
	
	Date ratingDate;
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "sa_id")
	BtServiceAppointment btServiceAppointment;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getEngrating() {
		return engrating;
	}

	public void setEngrating(int engrating) {
		this.engrating = engrating;
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

	public BtServiceAppointment getBtServiceAppointment() {
		return btServiceAppointment;
	}

	public void setBtServiceAppointment(BtServiceAppointment btServiceAppointment) {
		this.btServiceAppointment = btServiceAppointment;
	}
	
	
	
}
