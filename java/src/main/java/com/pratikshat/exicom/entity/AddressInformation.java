package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners(ObjectAuditListener.class)
public class AddressInformation implements java.io.Serializable,OldPresist<AddressInformation>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long addressInfoId;
	String address;
	String street;
	String city;
	//String state;
	@ManyToOne
	@JoinColumn(name="state_id")
	StateProvinceDetails state;
	String pincode;
	//String country;
	double latitude;
	double longitude;
	boolean synced;
	
	/**
	 * @return the synced
	 */
	public boolean isSynced() {
		return synced;
	}



	/**
	 * @param synced the synced to set
	 */
	public void setSynced(boolean synced) {
		this.synced = synced;
	}



	public AddressInformation() {}
	
	public AddressInformation(long addressId) {
		this.addressInfoId=addressId;
	}
	
	
	/**
	 * @param addressInfoId
	 * @param address
	 * @param street
	 * @param city
	 * @param state
	 * @param pincode
	 * @param country
	 * @param latitude
	 * @param longitude
	 */
	public AddressInformation(String address, String street, String city, long state,
			String pincode, String country, double latitude, double longitude) {
		
		this.address = address;
		this.street = street;
		this.city = city;
		//this.state = state;
		this.state=new StateProvinceDetails(state);
		this.pincode = pincode;
		//this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
	}



	/**
	 * @param addressInfoId
	 * @param address
	 * @param street
	 * @param city
	 * @param state
	 * @param pincode
	 * @param country
	 * @param latitude
	 * @param longitude
	 */
	public AddressInformation(long addressInfoId, String address, String street, String city, long state,
			String pincode, String country, double latitude, double longitude) {
		this.addressInfoId = addressInfoId;
		this.address = address;
		this.street = street;
		this.city = city;
		//this.state = state;
		this.state=new StateProvinceDetails(state);
		this.pincode = pincode;
		//this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
	}



	/**
	 * @return the addressInfoId
	 */
	public long getAddressInfoId() {
		return addressInfoId;
	}
	/**
	 * @param addressInfoId the addressInfoId to set
	 */
	public void setAddressInfoId(long addressInfoId) {
		this.addressInfoId = addressInfoId;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}
	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the state
	 */
	/*public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	/*public void setState(String state) {
		this.state = state;
	}*/
	/**
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}
	/**
	 * @param pincode the pincode to set
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	/**
	 * @return the country
	 */
	/*public String getCountry() {
		return country;
	}*/
	/**
	 * @param country the country to set
	 */
	/*public void setCountry(String country) {
		this.country = country;
	}*/
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}



	/**
	 * @return the state
	 */
	public StateProvinceDetails getState() {
		return state;
	}



	/**
	 * @param state the state to set
	 */
	public void setState(StateProvinceDetails state) {
		this.state = state;
	}

	@Transient transient AddressInformation old;
	
	@Override
	@PostLoad
	public void old() {
		old=(AddressInformation)SerializationUtils.clone(this);
	}



	@Override
	public AddressInformation gOld() {
		// TODO Auto-generated method stub
		return old;
	}
	public String toString() {
		return ""+this.addressInfoId;
	}
	

}
