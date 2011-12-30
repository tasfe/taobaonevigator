package com.walmart.fraudengine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity class for table ORDER_STATUS.
 */
@Entity
@Table(name = "ORDER_STATUS")
public class OrderStatusEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ORDER_STATUS_PK")
	@SequenceGenerator(name = "OrderStatusEntityGenerator", sequenceName = "ORDER_STATUS_SEQ")
	@GeneratedValue(generator = "OrderStatusEntityGenerator")
	private Long orderStatusPk;

	@Column(name = "STATUS_REASON")
	private String statusReason;

	@Column(name = "POS_NEG_LIST_FK", nullable = true)
	private long posNegListFk;

	@Column(name = "BUS_RULE_FK", nullable = true)
	private Long busRule;

	@Column(name = "REASON_CODE")
	private String reasonCode;

	@ManyToOne
	@JoinColumn(name = "ORDER_DETAIL_FK", nullable = false)
	private OrderDetailEntity orderDetail;

	@OneToOne(optional = false)
	@JoinColumn(name = "STATUS_FK", nullable = false)
	private StatusLkEntity statusLk;

	public Long getOrderStatusPk() {

		return this.orderStatusPk;
	}

	public String getReasonCode() {

		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {

		this.reasonCode = reasonCode;
	}

	public String getStatusReason() {

		return this.statusReason;
	}

	public void setStatusReason(String statusReason) {

		this.statusReason = statusReason;
	}

	public OrderDetailEntity getOrderDetail() {

		return this.orderDetail;
	}

	public void setOrderDetail(OrderDetailEntity orderDetail) {

		this.orderDetail = orderDetail;
	}

	public StatusLkEntity getStatusLk() {

		return this.statusLk;
	}

	public void setStatusLk(StatusLkEntity statusLk) {

		this.statusLk = statusLk;

	}

	public long getPosNegListFk() {

		return posNegListFk;
	}

	public void setPosNegListFk(long posNegListFk) {

		this.posNegListFk = posNegListFk;
	}

	public Long getBusRule() {

		return busRule;
	}

	public void setBusRule(Long busRule) {

		this.busRule = busRule;
	}
}