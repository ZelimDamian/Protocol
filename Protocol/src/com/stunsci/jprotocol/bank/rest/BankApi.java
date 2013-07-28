package com.stunsci.jprotocol.bank.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import com.stunsci.jprotocol.bank.models.*;
import com.stunsci.jprotocol.persistence.EmfInstanceManager;



	
	@Path("bank")
	public class BankApi {
	
	
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
}
