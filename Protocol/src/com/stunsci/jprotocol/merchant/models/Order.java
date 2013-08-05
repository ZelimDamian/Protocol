package com.stunsci.jprotocol.merchant.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Order {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String customerName;
	private Long productId;
	private String state;
	private String paymentEnHash;
	private String paymentPrivateKey;
	
	private String hash;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPaymentEnHash() {
		return paymentEnHash;
	}

	public void setPaymentEnHash(String paymentEnHash) {
		this.paymentEnHash = paymentEnHash;
	}

	public String getPaymentPrivateKey() {
		return paymentPrivateKey;
	}

	public void setPaymentPrivateKey(String paymentPrivateKey) {
		this.paymentPrivateKey = paymentPrivateKey;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customerName=" + customerName
				+ ", productId=" + productId + ", state=" + state
				+ ", paymentEnHash=" + paymentEnHash + ", paymentPrivateKey="
				+ paymentPrivateKey + ", hash=" + hash + "]";
	}
	
	
}
