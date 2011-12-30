package com.walmart.fraudengine.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity class for table USER_MASTER.
 */
@Entity
@Table(name = "USER_MASTER")
public class UserMasterEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final String BY_USER_NAME = "BY_USER_NAME";

	@Id
	@Column(name = "USER_MASTER_PK")
	@SequenceGenerator(name = "UserMasterEntityGenerator", sequenceName = "USER_MASTER_SEQ")
	@GeneratedValue(generator = "UserMasterEntityGenerator")
	private Long userMasterPk;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "FIRSTNAME")
	private String firstname;

	@Column(name = "LASTNAME")
	private String lastname;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "LOGINCOUNT")
	private Long loginCount;

	@OneToMany(mappedBy = "userMaster")
	private Set<UserDetailEntity> userDetails = new HashSet<UserDetailEntity>(0);

	public Long getUserMasterPk() {

		return this.userMasterPk;
	}

	public String getUsername() {

		return this.username;
	}

	public void setUsername(String username) {

		this.username = username;
	}

	public String getPassword() {

		return this.password;
	}

	public void setPassword(String password) {

		this.password = password;
	}

	public String getFirstname() {

		return this.firstname;
	}

	public void setFirstname(String firstname) {

		this.firstname = firstname;
	}

	public String getLastname() {

		return this.lastname;
	}

	public void setLastname(String lastname) {

		this.lastname = lastname;
	}

	public String getEmail() {

		return this.email;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public String getPhone() {

		return this.phone;
	}

	public void setPhone(String phone) {

		this.phone = phone;
	}

	public Long getLoginCount() {

		return this.loginCount;
	}

	public void setLoginCount(Long loginCount) {

		this.loginCount = loginCount;
	}

	public Set<UserDetailEntity> getUserDetails() {

		return this.userDetails;
	}

	public void setUserDetails(Set<UserDetailEntity> userDetails) {

		this.userDetails = userDetails;
	}
}