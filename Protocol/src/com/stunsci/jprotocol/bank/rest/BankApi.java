package com.stunsci.jprotocol.bank.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import com.stunsci.jprotocol.bank.models.*;
import com.stunsci.jprotocol.encryption.EncryptionHelper;
import com.stunsci.jprotocol.persistence.EmfInstanceManager;




@Path("bank")
public class BankApi {
	
	EncryptionHelper eh = new EncryptionHelper();
	
	@GET
	@Produces("application/json")
	@Path("/clients/")
	public List<Client> getAllClients()
	{
	 	EntityManager em = EmfInstanceManager.getInstance().get().createEntityManager();
	    Query query = em.createQuery("SELECT e FROM "+ Client.class.getName() +" as e");
	    @SuppressWarnings("unchecked")
		List<Client> results = query.getResultList();
	    return results;
	}
	 @POST
	 @Consumes("application/json")
	 @Produces("application/json")
	 @Path("/clients/")
	 public Client createClient(Client client) {
	 	EntityManager em = EmfInstanceManager.getInstance().get().createEntityManager();
	 	
	 	try{
	 		em.persist(client);
	 	}catch(Exception ex)
	 	{
	 		System.err.println(ex);
	 	}
	 	finally
	 	{
	 		em.close();
	 	}
	 	
	 	return client;
    }
	 
	 @PUT
	 @Consumes("application/json")
	 @Path("/clients/{id}")
	 public void updateClient(Client client)
	 {
		 EntityManager em = EmfInstanceManager.getInstance().get().createEntityManager();
		 	
		 	try{
		 		em.merge(client);
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
	 
	@GET
	@Produces("application/json")
	@Path("/payments/")
	public List<Payment> getAllPayments()
	{
	 	EntityManager em = EmfInstanceManager.getInstance().get().createEntityManager();
	    Query query = em.createQuery("SELECT e FROM "+ Payment.class.getName() +" as e");
	    @SuppressWarnings("unchecked")
		List<Payment> results = query.getResultList();
	    return results;
	}
	 
	@POST
	 @Produces("application/json")
	 @Path("/clients/{id}/payments")
	 public List<Payment> getPayments(@PathParam("id") Long id) {
	 	EntityManager em = EmfInstanceManager.getInstance().get().createEntityManager();
	 	
	 	List<Payment> payments = null;
	 	
	 	try{
		    Query query = em.createQuery("SELECT p FROM "+ Payment.class.getName() +" p WHERE p.clientId = :clientId");
		    query.setParameter("clientId", id);
		    
		    
	    	payments = query.getResultList();
		    
	 	} catch(Exception ex) {
	 		System.err.println(ex);
	 	}
	 	finally {
	 		em.close();
	 	}
	 	
	 	return payments;
    }
	
	@POST
	@Consumes("application/json")
	@Path("/payments/verify/")
	public String verifyPayment(Payment payment)
	{
	 	EntityManager em = EmfInstanceManager.getInstance().get().createEntityManager();
	 	
	 	try{
		    Query query = em.createQuery("SELECT p FROM "+ Payment.class.getName() +" p WHERE p.enHash = :enHash");
		    query.setParameter("enHash", payment.getEnHash());
		    
	 		Payment found = (Payment) query.getResultList().get(0);
	 		
	 		if(found.equals(payment))
	 			return "SUCCESS";
	 	}catch(Exception ex) {
	 		System.err.println(ex);
	 	}
	 	finally {
	 		em.close();
	 	}
	 	
	 	return "FAIL";	
	}
	
	 @POST
	 @Consumes("application/json")
	 @Produces("application/json")
	 @Path("/payments/")
	 public Payment createPayment(Payment payment) {
	 	EntityManager em = EmfInstanceManager.getInstance().get().createEntityManager();
	 	EncryptionHelper encryption = new EncryptionHelper();
	 	
	 	payment.setPrivateKey(encryption.getPrivateKey());
	 	payment.setPublicKey(encryption.getPublicKey());
	 	
	 	payment.setTimeStamp(new java.util.Date().getTime());
	 	
	 	String digestedPayment = eh.digestString(payment.getPayee() + payment.getAmount() + payment.getTimeStamp().toString()); 
	 	payment.setHash(digestedPayment);
	 	
	 	String enHash = encryption.encryptStringWithPublicKey(digestedPayment);
	 	payment.setEnHash(enHash);
	 	
	 	try{
	 		em.persist(payment);
	 	}catch(Exception ex) {
	 		System.err.println(ex);
	 	}
	 	finally {
	 		em.close();
	 	}
	 	
	 	return payment;
	}
	 
	 @PUT
	 @Consumes("application/json")
	 @Path("/payments/{id}")
		 public void updatePayment(Payment payment)
		 {
			 EntityManager em = EmfInstanceManager.getInstance().get().createEntityManager();
			 	
			 	try{
			 		em.merge(payment);
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
	}
