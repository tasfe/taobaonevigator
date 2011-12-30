package com.walmart.fraudengine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity class for table ADDRESS_DETAIL.
 */
@Entity
@Table(name = "ADDRESS_DETAIL")
public class AddressDetailEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final String FETCH_ALL = "FETCH_ALL";

	@Id
	@Column(name = "ADDRESS_DETAIL_PK")
	@SequenceGenerator(name = "AddressDetailEntityGenerator", sequenceName = "ADDRESS_DETAIL_SEQ")
	@GeneratedValue(generator = "AddressDetailEntityGenerator")
	private Long addressDetailPk;

	@Column(name = "ADDRESS_TYPE", nullable = false)
	private String addressType;

	@Column(name = "ADDRESS_LINE_1", nullable = false)
	private String addressline1;

	@Column(name = "ADDRESS_LINE_2")
	private String addressline2;

	@Column(name = "ADDRESS_LINE_3")
	private String addressline3;

	@Column(name = "ADDRESS_LINE_4")
	private String addressline4;

	@Column(name = "ADDRESS_LINE_5")
	private String addressline5;

	@Column(name = "DISTRICT")
	private String district;

	@Column(name = "CITY")
	private String city;

	@Column(name = "PROVINCE", nullable = false)
	private String province;

	@Column(name = "COUNTRY", nullable = false)
	private String country;

	@Column(name = "HOME_PHONE")
	private String homePhone;

	@Column(name = "CELL_PHONE")
	private String cellPhone;

	@Column(name = "WORK_PHONE")
	private String workPhone;

	public String getAddressType() {

		return this.addressType;
	}

	public void setAddressType(String addressType) {

		this.addressType = addressType;
	}

	public String getAddressline1() {

		return this.addressline1;
	}

	public Long getAddressDetailPk() {

		return addressDetailPk;
	}

	public void setAddressline1(String addressline1) {

		this.addressline1 = addressline1;
	}

	public String getAddressline2() {

		return this.addressline2;
	}

	public void setAddressline2(String addressline2) {

		this.addressline2 = addressline2;
	}

	public String getAddressline3() {

		return this.addressline3;
	}

	public void setAddressline3(String addressline3) {

		this.addressline3 = addressline3;
	}

	public String getAddressline4() {

		return this.addressline4;
	}

	public void setAddressline4(String addressline4) {

		this.addressline4 = addressline4;
	}

	public String getAddressline5() {

		return this.addressline5;
	}

	public void setAddressline5(String addressline5) {

		this.addressline5 = addressline5;
	}

	public String getDistrict() {

		return this.district;
	}

	public void setDistrict(String district) {

		this.district = district;
	}

	public String getCity() {

		return this.city;
	}

	public void setCity(String city) {

		this.city = city;
	}

	public String getProvince() {

		return this.province;
	}

	public void setProvince(String province) {

		this.province = province;
	}

	public String getCountry() {

		return this.country;
	}

	public void setCountry(String country) {

		this.country = country;
	}

	public String getCellPhone() {

		return this.cellPhone;
	}

	public void setCellPhone(String cellPhone) {

		this.cellPhone = cellPhone;
	}

	public String getHomePhone() {

		return this.homePhone;
	}

	public void setHomePhone(String homePhone) {

		this.homePhone = homePhone;
	}

	public String getWorkPhone() {

		return this.workPhone;
	}

	public void setWorkPhone(String workPhone) {

		this.workPhone = workPhone;
	}
}