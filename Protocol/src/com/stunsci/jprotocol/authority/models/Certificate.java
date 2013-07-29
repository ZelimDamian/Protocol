package com.stunsci.jprotocol.authority.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Certificate {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//@ManyToOne()
	//private DProduct product;
	
	// Hash of the digital product
	private Long productId;
	
	// Merchant id
	private String merchant;
	
	private String hash;
	
	public Certificate()
	{
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long long1) {
		this.productId = long1;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
	
} 