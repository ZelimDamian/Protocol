package com.stunsci.jprotocol.merchant.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.stunsci.jprotocol.bank.models.Payment;
import com.stunsci.jprotocol.encryption.EncryptionHelper;
import com.stunsci.jprotocol.merchant.models.*;
import com.stunsci.jprotocol.persistence.EmfInstanceManager;
import com.stunsci.jprotocol.ttp.models.EncryptedProduct;
import com.stunsci.jprotocol.ttp.models.TrustedTransaction;

	@Path("merchant")
	public class MerchantApi {

		
		@GET
		@Produces("application/json")
		@Path("/products/")
		public List<Product> getAllProducts()
		{
		 	EntityManager em = EmfInstanceManager.getInstance().get().createEntityManager();
		    Query query = em.createQuery("SELECT e FROM "+ Product.class.getName() +" as e");
		    
		    @SuppressWarnings("unchecked")
			List<Product> results = query.getResultList();
		    return results;
		}
		
		
		@GET
		@Produces("application/json")
		@Path("/products/{id}")
		public Product getAllProducts(@PathParam("id") int id)
		{
		 	EntityManager em = EmfInstanceManager.getInstance().get().createEntityManager();
		    
			Product result = em.find(Product.class, id);
		    return result;
		}
		
		@GET
		@Consumes("application/json")
		@Path("/products/{id}/price")
		public String getProductPrice(@PathParam("id") int id)
		{
		 	EntityManager em = EmfInstanceManager.getInstance().get().createEntityManager();
		    
			Product result = em.find(Product.class, id);
		    return "" + result.getPrice();
		}
		
	 @POST
	 @Consumes("application/json")
	 @Produces("application/json")
	 @Path("/products/")
	 public Product createProduct(Product product) {
	 	EntityManager em = EmfInstanceManager.getInstance().get().createEntityManager();
	 	
	 	try{
	 		em.persist(product);
	 	}catch(Exception ex)
	 	{
	 		System.err.println(ex);
	 	}
	 	finally
	 	{
	 		em.close();
	 	}
	 	
	 	return product;
    }
	 
	 @PUT
	 @Consumes("application/json")
	 @Path("/products/{id}")
	 public void updateProduct(Product product)
	 {
		 EntityManager em = EmfInstanceManager.getInstance().get().createEntityManager();
		 	
		 	try{
		 		em.merge(product);
		 	}catch(Exception ex)
		 	{
		 		System.err.println(ex);
		 	}
		 	finally
		 	{
		 		em.close();
		 	}
		 return;
	 }
	 
	 @POST
	 @Consumes("application/json")
	 @Produces("application/json")
	 @Path("/orders/")
	 public EncryptedProduct createOrder(TrustedTransaction trustedTransaction) {
	 	EntityManager em = EmfInstanceManager.getInstance().get().createEntityManager();
	 	EncryptionHelper encryption = new EncryptionHelper();

	 	EncryptedProduct enProduct = null;
	 	
	 	try{
	 		Product product = em.find(Product.class, trustedTransaction.getProductId());
	 		
	 		// 1. Generate and store an order entity to keep track of this purchase
	 		Order order = new Order();
	 		order.setCustomerName(trustedTransaction.getCustomerName());
	 		order.setPaymentEnHash(trustedTransaction.getPaymentHash());
	 		order.setProductId(trustedTransaction.getProductId());
	 		order.setState("NOT PAID");
	 		
	 		order.setHash(encryption.digestString(order.toString()));
	 		
	 		em.persist(order);
	 		
	 		// 2. Generate an encrypted product instance
	 		String encryptedContent = encryption.encryptStringWithSymKey(product.getContent().getBytes());
	 		String encryptedAesKey = encryption.getEncryptedSymKey();
	 		
	 		enProduct = new EncryptedProduct();
	 		
	 		enProduct.setCert(product.getCert());
	 		enProduct.setDescription(product.getDescription());
	 		enProduct.setName(product.getName());
	 		enProduct.setEnSymKey(encryptedAesKey);
	 		enProduct.setPrivateKey(encryption.getPrivateKey());
	 		enProduct.setContent(encryptedContent);
	 		enProduct.setTimeStamp(new java.util.Date().getTime());
	 		
	 		// 3. Connect the order and instance
	 		enProduct.setOrderHash(order.getHash());
	 		
	 	}catch(Exception ex)
	 	{
	 		System.err.println(ex);
	 	}
	 	finally
	 	{
	 		em.close();
	 	}
	 	
	 	return enProduct;
    }
	 
		@POST
		@Path("/orders/{hash}/payment")
		public String verifyPayment(@FormParam("privateKey") String privateKey, @PathParam("hash") String hash)
		{
		 	EntityManager em = EmfInstanceManager.getInstance().get().createEntityManager();
		 	
		 	try{
			    Query query = em.createQuery("SELECT o FROM "+ Order.class.getName() +" o WHERE o.hash = :hash");
			    query.setParameter("hash", hash);
			    
		 		Order found = (Order) query.getResultList().get(0);
		 		
		 		found.setPaymentPrivateKey(privateKey);
		 		found.setState("PAID");
		 		
		 		em.merge(found);
		 		
		 		return "SUCCESS";
		 		
		 	}catch(Exception ex) {
		 		System.err.println(ex);
		 	}
		 	finally {
		 		em.close();
		 	}
		 	
		 	return "FAIL";	
		}
} 
	
