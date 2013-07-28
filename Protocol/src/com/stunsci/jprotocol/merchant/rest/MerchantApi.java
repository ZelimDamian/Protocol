package com.stunsci.jprotocol.merchant.rest;

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

import com.stunsci.jprotocol.merchant.models.Product;
import com.stunsci.jprotocol.persistence.EmfInstanceManager;

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
}
