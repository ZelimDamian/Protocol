package com.stunsci.jprotocol.ttp.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import com.google.gson.Gson;

import com.stunsci.jprotocol.encryption.EncryptionHelper;
import com.stunsci.jprotocol.httpClient.SimpleHttpClient;
import com.stunsci.jprotocol.persistence.EmfInstanceManager;
import com.stunsci.jprotocol.ttp.models.EncryptedProduct;
import com.stunsci.jprotocol.ttp.models.TrustedTransaction;

//TODO: NEEDS TO BE CHANGED
import com.stunsci.jprotocol.bank.models.Payment;
//TODO: SAME AS ABOVE


@Path("ttp")
public class TrustedPartyApi {
	private static final Logger log = Logger.getLogger(TrustedPartyApi.class.getName());

	EncryptionHelper eh = new EncryptionHelper();
	
	public boolean sendPaymentKeyToMerchant(String orderHash, String privateKey)
	{
		String json = "{privateKey:" + privateKey + "}";

		SimpleHttpClient client = new SimpleHttpClient();
		
		String response = client.getJsonStringFromURL("http://java-protocol-implementation.appspot.com/rest/merchant/orders/" + orderHash + "/payment", json, "POST");
		
		if(response.equals("SUCCESS"))
			return true;
		
		return false;
	}
	
	
	@POST
	@Path("/continue/{productHash}")
	@Consumes("application/json")
	public String initiateProtocol(@PathParam("productHash") String productHash)
	{
	 	EntityManager em = EmfInstanceManager.getInstance().get().createEntityManager();
	 	
	 	String privateKey = "";
	 	
	 	try{
		    Query query = em.createQuery("SELECT o FROM "+ TrustedTransaction.class.getName() +" o WHERE o.hash = :hash");
		    query.setParameter("hash", productHash);
		    
		    TrustedTransaction found = (TrustedTransaction) query.getResultList().get(0);
	 		
		    // TODO: Deliberately left out of an IF cause the payment is verified anyways
	 		this.sendPaymentKeyToMerchant(found.getMerchantOrderHash(), found.getPaymentPrivateKey());
	 		
	 		privateKey = found.getProductPrivateKey();
	 		
	 	}catch(Exception ex) {
	 		System.err.println(ex);
	 		throw new WebApplicationException(400);
	 	}
	 	finally {
	 		em.close();
	 	}
	 	
	 	return privateKey;
	}
	 

	public EncryptedProduct requestProductFromMerchant(TrustedTransaction order)
	{
		Gson gson = new Gson();
		
		String orderJson = gson.toJson(order);
		
		SimpleHttpClient client = new SimpleHttpClient();
		String productString = client.getJsonStringFromURL("http://java-protocol-implementation.appspot.com/rest/merchant/orders/", orderJson, "POST");
		
		EncryptedProduct product = gson.fromJson(productString, EncryptedProduct.class);
		return product;

	}
	
	public boolean verifyPaymentWithBank(Payment payment)
	{
		Gson gson = new Gson();
		 
		String json = gson.toJson(payment);

		SimpleHttpClient client = new SimpleHttpClient();
		
		String response = client.getJsonStringFromURL("http://java-protocol-implementation.appspot.com/rest/bank/payments/verify", json, "POST");

		//log.severe("BANK RESPONSE: " + response);

		
		if(response.equals("SUCCESS"))
			return true;
		
		return false;
	}
	
	int getProductPriceFromMerchant(Long id)
	{
		String response = new SimpleHttpClient()
		.getStringFromURL("http://java-protocol-implementation.appspot.com/rest/merchant/products/"+ id +"/price", "", "GET");

		log.severe("PRODUCT ID: " + id);
		log.severe("MERCHANT RESPONSE: " + response);
		
		return Integer.parseInt(response);
	}
	
	@POST
	@Path("/initiate/")
	@Consumes("application/json")
	@Produces("application/json")
	public EncryptedProduct initiateProtocol(Payment payment)
	{
	 	EntityManager em = EmfInstanceManager.getInstance().get().createEntityManager();
	 	EncryptionHelper eh = new EncryptionHelper();
	 	
		// 1. Verify the payment
		if(!this.verifyPaymentWithBank(payment))
			throw new WebApplicationException(400);
	    
		if(this.getProductPriceFromMerchant(payment.getProductId()) > payment.getAmount())
			throw new WebApplicationException(403);

		// 2. Form an order entity
		TrustedTransaction transaction = new TrustedTransaction();
		transaction.setCustomerName(payment.getPayerName());
		transaction.setPaymentHash(payment.getEnHash()); // NOTE: the payment hash is encrypted here!
		transaction.setProductId(payment.getProductId());
		transaction.setPaymentPrivateKey(payment.getPrivateKey());
		
		// 3. Request encrypted product from the merchant
		EncryptedProduct enProduct = this.requestProductFromMerchant(transaction);
		
		// 4. Store private key in the order
		transaction.setProductPrivateKey(enProduct.getPrivateKey());
		transaction.setMerchantOrderHash(enProduct.getOrderHash());
		
		// 5. Generate a secure hash for the order
		String hash = eh.digestString(transaction.toString());
		transaction.setHash(hash);
		enProduct.setOrderHash(hash);
		
		
		// 5. Store the transaction and encrypted product for further processing
		try{
			em.getTransaction().begin();
			em.persist(transaction);
			em.getTransaction().commit();
			
			enProduct.setOrderHash(transaction.getHash());
			
			em.getTransaction().begin();
			//em.persist(enProduct);
			em.getTransaction().commit();
			
			// 5. Hide private key from the product
			enProduct.setPrivateKey("");
			
		}catch(Exception ex) {
	 		System.err.println(ex);
	 		throw new WebApplicationException(400);
	 	}
	 	finally {
	 		em.close();
	 	}
	 	
		
		return enProduct;
	}
}
