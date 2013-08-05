package com.stunsci.jprotocol.ttp.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Text;

@Entity
public class EncryptedProduct {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String cert;
	private String name;
	private String description;
	private String enSymKey;
	private String privateKey;
	private Long timeStamp;
	private String orderHash;
	private String content;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCert() {
		return cert;
	}
	public void setCert(String hash) {
		this.cert = hash;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContent() {
		return this.content;
	}
	public void setContent(String enContent) {
		this.content = enContent;
	}
	
	public String getEnSymKey() {
		return enSymKey;
	}
	public void setEnSymKey(String enSymKey) {
		this.enSymKey = enSymKey;
	}
	public Long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public String getOrderHash() {
		return this.orderHash;
	}
	public void setOrderHash(String orderHash) {
		this.orderHash = orderHash;
	}
	
	
}
