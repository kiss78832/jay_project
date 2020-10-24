package com.application.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class ApplicationDTO implements Serializable{


	private static final long serialVersionUID = 1L;

	/**
	  編號
	 */
	private String appNum="";
	
	/**
	  編號
	 */
	private String groupId="";
	
	/**
	  編號
	 */
	private String memberId="";
	
	/**
	  編號
	 */
	private String routeId="";
	
	/**
	  編號
	 */
	private String egcContact="";
	
	/**
	  編號
	 */
	private String egcPhone="";
	
	/**
	  編號
	 */
	private String satellite="";
	
	/**
	  編號
	 */
	private String radio="";
	
	/**
	  編號
	 */
	private Integer appStatus = 0;
	
	/**
	  編號
	 */
	private Timestamp appTime = null;
	/**
	  編號
	 */
	private Integer appDays = 0;
	
	/**
	  編號
	 */
	private Date firstDay = null;
	
	/**
	  編號
	 */
	private Integer dailybedProvided = 0 ;
	
	/**
	  編號
	 */
	private Integer mealProvided = 0;
	
	/**
	  編號
	 */
	private String locations;
	


	public String getAppNum() {
		return appNum;
	}

	public void setAppNum(String appNum) {
		this.appNum = appNum;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getEgcPhone() {
		return egcPhone;
	}

	public void setEgcPhone(String egcPhone) {
		this.egcPhone = egcPhone;
	}

	public String getSatellite() {
		return satellite;
	}

	public void setSatellite(String satellite) {
		this.satellite = satellite;
	}

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public Timestamp getAppTime() {
		return appTime;
	}

	public void setAppTime(Timestamp appTime) {
		this.appTime = appTime;
	}

	public String getLocations() {
		return locations;
	}

	public void setLocations(String locations) {
		this.locations = locations;
	}

	public Integer getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(Integer appStatus) {
		this.appStatus = appStatus;
	}

	public Integer getAppDays() {
		return appDays;
	}

	public void setAppDays(Integer appDays) {
		this.appDays = appDays;
	}

	public Date getFirstDay() {
		return firstDay;
	}

	public void setFirstDay(Date firstDay) {
		this.firstDay = firstDay;
	}

	public Integer getDailybedProvided() {
		return dailybedProvided;
	}

	public void setDailybedProvided(Integer dailybedProvided) {
		this.dailybedProvided = dailybedProvided;
	}

	public Integer getMealProvided() {
		return mealProvided;
	}

	public void setMealProvided(Integer mealProvided) {
		this.mealProvided = mealProvided;
	}

	public String getEgcContact() {
		return egcContact;
	}

	public void setEgcContact(String egcContact) {
		this.egcContact = egcContact;
	}
	
}
