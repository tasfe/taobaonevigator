package com.walmart.fraudengine.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity class for table USER_DETAIL.
 */
@Entity
@Table(name = "USER_DETAIL")
public class UserDetailEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USER_DETAIL_PK")
	@SequenceGenerator(name = "UserDetailEntityGenerator", sequenceName = "USER_DETAIL_SEQ")
	@GeneratedValue(generator = "UserDetailEntityGenerator")
	private Long userDetailPk;

	@ManyToOne
	@JoinColumn(name = "USER_MASTER_FK")
	private UserMasterEntity userMaster;

	@Column(name = "LOGIN_IP_ADDRESS", length = 30)
	private String loginIpAddress;

	@Column(name = "LOGIN_DATE", nullable = false)
	private Timestamp loginDate;

	public Long getUserDetailPk() {

		return this.userDetailPk;
	}

	public UserMasterEntity getUserMaster() {

		return this.userMaster;
	}

	public void setUserMaster(UserMasterEntity userMaster) {

		this.userMaster = userMaster;
	}

	public String getLoginIpAddress() {

		return this.loginIpAddress;
	}

	public void setLoginIpAddress(String loginIpAddress) {

		this.loginIpAddress = loginIpAddress;
	}

	public Timestamp getLoginDate() {

		return this.loginDate;
	}

	public void setLoginDate(Timestamp loginDate) {

		this.loginDate = loginDate;
	}
}