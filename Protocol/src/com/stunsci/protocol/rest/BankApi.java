package com.stunsci.protocol.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import emf.*;

import model.Customer;

	
	@Path("bank")
	public class BankApi {
	
	
	@GET
	@Produces("application/json")
	@Path("/customers/")
	public List<Customer> getAllCustomers()
	{
	 	EntityManager em = EmfInstanceManager.getInstance().get().createEntityManager();
	    Query query = em.createQuery("SELECT e FROM "+ Customer.class.getName() +" as e");
	    @SuppressWarnings("unchecked")
		List<Customer> results = query.getResultList();
	    return results;
	}
	 @POST
	 @Consumes("application/json")
	 @Produces("application/json")
	 @Path("/customers/")
	 public Customer createCustomer(Customer customer) {
	 	EntityManager em = EmfInstanceManager.getInstance().get().createEntityManager();
	 	
	 	try{
	 		em.persist(customer);
	 	}catch(Exception ex)
	 	{
	 		System.err.println(ex);
	 	}
	 	finally
	 	{
	 		em.close();
	 	}
	 	
	 	return customer;
    }
	 
	 @PUT
	 @Consumes("application/json")
	 @Path("/customers/{id}")
	 public void updateCustomer(Customer customer)
	 {
		 EntityManager em = EmfInstanceManager.getInstance().get().createEntityManager();
		 	
		 	try{
		 		em.merge(customer);
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
