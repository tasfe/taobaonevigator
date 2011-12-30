package com.walmart.fraudengine.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import com.walmart.fraudengine.exception.ErrorCode;
import com.walmart.fraudengine.exception.FraudEngineSystemException;
import com.walmart.fraudengine.util.FraudCheckConst;

/**
 * Base class for all JPA entities used within the Fraud Engine application. This class provides default behavior (audit
 * columns, version column for optimistic concurrency, serializable behavior) which should be implemented by all JPA
 * entities.
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "created_by", updatable = false)
	private String createdBy;

	@Column(name = "created_date", updatable = false)
	private Timestamp createdDate;

	@Column(name = "last_modified_by")
	private String lastModifiedBy;

	@Column(name = "last_modified_date")
	private Timestamp lastModifiedDate;

	@Column(name = "soft_delete")
	private String softDelete;

	@Version
	@Column(name = "version")
	private Integer version;

	/**
	 * Set name of user or process creating this record.
	 * 
	 * @param createdBy
	 */
	public void setCreatedBy(String createdBy) {

		this.createdBy = createdBy;
	}

	/**
	 * Get name of user or process that created this record.
	 * 
	 * @return User or process name
	 */
	public String getCreatedBy() {

		return createdBy;
	}

	/**
	 * Set record creating timestamp.
	 * 
	 * @param createdDate
	 */
	private void setCreatedDate(Timestamp createdDate) {

		this.createdDate = createdDate;
	}

	/**
	 * Get date and time when record was created.
	 * 
	 * @return Timestamp
	 */
	public Timestamp getCreatedDate() {

		return createdDate;
	}

	/**
	 * Set name of user or process updating this record.
	 * 
	 * @param lastModifiedBy
	 */
	public void setLastModifiedBy(String lastModifiedBy) {

		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * Get name of user or process which last updated this record.
	 * 
	 * @return User or process name.
	 */
	public String getLastModifiedBy() {

		return lastModifiedBy;
	}

	/**
	 * Set record update timestamp.
	 * 
	 * @param lastModifiedDate
	 */
	private void setLastModifiedDate(Timestamp lastModifiedDate) {

		this.lastModifiedDate = lastModifiedDate;
	}

	/**
	 * Get date and time when record was last updated.
	 * 
	 * @return Timestamp
	 */
	public Timestamp getLastModifiedDate() {

		return lastModifiedDate;
	}

	/**
	 * Indicates whether this record is active or soft deleted. A value of 'Y' means record has been soft deleted. A new
	 * record is always created with a value of 'N'.
	 * 
	 * @return 'Y' or 'N'
	 */
	public String getSoftDelete() {

		return softDelete;
	}

	/**
	 * Indicates whether this record is active or soft deleted. A value of 'Y' means record has been soft deleted. A new
	 * record is always created with a value of 'N'.
	 * 
	 * @param softDelete 'Y' or 'N'
	 */
	public void setSoftDelete(String softDelete) {

		this.softDelete = softDelete;
	}

	/**
	 * Optimistic concurrency column value used internally by the Persistence Provider to support ACID behavior.
	 * 
	 * @return Current version number.
	 */
	public Integer getVersion() {

		return version;
	}

	@PrePersist
	public void prePersist() {

		setSoftDelete(FraudCheckConst.EXCLUDE_SOFT_DELETE);
		setCreatedDate(new Timestamp(System.currentTimeMillis()));
		setLastModifiedDate(new Timestamp(System.currentTimeMillis()));

		// Provide default value only if one has not been provided.
		if (this.getCreatedBy() == null) {
			this.setCreatedBy(FraudCheckConst.CREATE_BY);
		}

		// Provide default value only if one has not been provided.
		if (this.getLastModifiedBy() == null) {
			setLastModifiedBy(FraudCheckConst.LAST_MODIFIED_BY);
		}
	}

	@PreUpdate
	public void preUpdate() {

		setLastModifiedDate(new Timestamp(System.currentTimeMillis()));

		// Provide default value only if one has not been provided.
		if (this.getLastModifiedBy() == null) {
			setLastModifiedBy(FraudCheckConst.LAST_MODIFIED_BY);
		}

	}

	@PreRemove
	public void preRemove() throws FraudEngineSystemException {

		throw new FraudEngineSystemException(ErrorCode.HARD_DELETE_NOT_ALLOWED);
	}

	/**
	 * Check if the entity has been soft deleted
	 * 
	 * @return soft delete status
	 */
	public boolean isDeleted() {

		return FraudCheckConst.INCLUDE_SOFT_DELETE.equalsIgnoreCase(this.getSoftDelete());
	}
}