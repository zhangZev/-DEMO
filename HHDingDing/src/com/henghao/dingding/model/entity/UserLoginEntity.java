package com.henghao.dingding.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.dingding.model.IdEntity;

public class UserLoginEntity extends IdEntity {

	@Expose
	private String userId;

	@Expose
	private String userName;

	@Expose
	private String company;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return this.company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @param userId
	 * @param userName
	 * @param company
	 */
	public UserLoginEntity(String userId, String userName, String company) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.company = company;
	}

}
