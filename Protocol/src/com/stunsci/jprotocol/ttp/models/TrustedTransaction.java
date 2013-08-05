package com.stunsci.jprotocol.ttp.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrustedTransaction {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String paymentHash;
	private String paymentPrivateKey;
	private Long productId;
	private String productPrivateKey;
	private String hash;
	private String merchantOrderHash;
	
	private String customerName;
	
	
	public Long getId()
	{
		return this.id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getPaymentHash() {
		return paymentHash;
	}
	public void setPaymentHash(String paymentHash) {
		this.paymentHash = paymentHash;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getPaymentPrivateKey() {
		return paymentPrivateKey;
	}
	public void setPaymentPrivateKey(String paymentPrivateKey) {
		this.paymentPrivateKey = paymentPrivateKey;
	}
	public String getProductPrivateKey() {
		return productPrivateKey;
	}
	public void setProductPrivateKey(String productPrivateKey) {
		this.productPrivateKey = productPrivateKey;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public String getMerchantOrderHash() {
		return merchantOrderHash;
	}

	public void setMerchantOrderHash(String merchantOrderHash) {
		this.merchantOrderHash = merchantOrderHash;
	}

	@Override
	public String toString() {
		return "TrustedTransaction [id=" + id + ", paymentHash=" + paymentHash
				+ ", paymentPrivateKey=" + paymentPrivateKey + ", productId="
				+ productId + ", productPrivateKey=" + productPrivateKey
				+ ", merchantOrderHash=" + merchantOrderHash + ", customerId="
				+ customerName + "]";
	}

}
