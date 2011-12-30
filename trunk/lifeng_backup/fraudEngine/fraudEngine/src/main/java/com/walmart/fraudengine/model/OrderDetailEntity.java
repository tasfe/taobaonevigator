package com.walmart.fraudengine.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity class for table ORDER_DETAIL.
 */
@Entity
@Table(name = "ORDER_DETAIL")
public class OrderDetailEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final String BY_OMS_ORDER_ID = "BY_OMS_ORDER_ID";
	public static final String FETCH_ALL = "FETCH_ALL";

	@Id
	@Column(name = "ORDER_DETAIL_PK")
	@SequenceGenerator(name = "OrderDetailEntityGenerator", sequenceName = "ORDER_DETAIL_SEQ")
	@GeneratedValue(generator = "OrderDetailEntityGenerator")
	private Long orderDetailPk;

	@Column(name = "CUSTOMER_EMAIL")
	private String customerEmail;

	@Column(name = "CUSTOMER_ID", nullable = false)
	private String customerId;

	@Column(name = "IP_ADDRESS")
	private String ipAddress;

	@Column(name = "NUMBER_OF_LINES", nullable = false)
	private Integer numberOfLines;

	@Column(name = "OMS_ORDER_ID", nullable = false)
	private String omsOrderId;

	@Column(name = "ORDER_AMOUNT", nullable = false)
	private BigDecimal orderAmount;

	@Column(name = "ORDER_PRIORITY")
	private BigDecimal orderPriority;

	@Column(name = "ORDER_RECEIVED_DATE", nullable = false)
	private Timestamp orderReceivedDate;

	@Column(name = "ORDER_RETRY_COUNT")
	private Integer orderRetryCount;

	@Column(name = "PAYMENT_TYPE", nullable = false)
	private String paymentType;

	@Column(name = "TRANSACTION_ID", nullable = false)
	private String transactionId;

	@OneToMany(mappedBy = "orderDetail", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Set<OrderStatusEntity> orderStatuses = new HashSet<OrderStatusEntity>();

	public Long getOrderDetailPk() {

		return this.orderDetailPk;
	}

	public String getCustomerEmail() {

		return this.customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {

		this.customerEmail = customerEmail;
	}

	public String getCustomerId() {

		return this.customerId;
	}

	public void setCustomerId(String customerId) {

		this.customerId = customerId;
	}

	public String getIpAddress() {

		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {

		this.ipAddress = ipAddress;
	}

	public Integer getNumberOfLines() {

		return this.numberOfLines;
	}

	public void setNumberOfLines(Integer numberOfLines) {

		this.numberOfLines = numberOfLines;
	}

	public String getOmsOrderId() {

		return this.omsOrderId;
	}

	public void setOmsOrderId(String omsOrderId) {

		this.omsOrderId = omsOrderId;
	}

	public BigDecimal getOrderAmount() {

		return this.orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {

		this.orderAmount = orderAmount;
	}

	public BigDecimal getOrderPriority() {

		return this.orderPriority;
	}

	public void setOrderPriority(BigDecimal orderPriority) {

		this.orderPriority = orderPriority;
	}

	public Timestamp getOrderReceivedDate() {

		return this.orderReceivedDate;
	}

	public void setOrderReceivedDate(Timestamp orderReceivedDate) {

		this.orderReceivedDate = orderReceivedDate;
	}

	public Integer getOrderRetryCount() {

		return this.orderRetryCount;
	}

	public void setOrderRetryCount(Integer orderRetryCount) {

		this.orderRetryCount = orderRetryCount;
	}

	public String getPaymentType() {

		return this.paymentType;
	}

	public void setPaymentType(String paymentType) {

		this.paymentType = paymentType;
	}

	public String getTransactionId() {

		return this.transactionId;
	}

	public void setTransactionId(String transactionId) {

		this.transactionId = transactionId;
	}

	public Set<OrderStatusEntity> getOrderStatuses() {

		return this.orderStatuses;
	}

	public void setOrderStatuses(Set<OrderStatusEntity> orderStatuses) {

		this.orderStatuses = orderStatuses;
	}
}