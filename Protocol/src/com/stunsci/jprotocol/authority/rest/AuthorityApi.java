package com.stunsci.jprotocol.authority.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import com.stunsci.jprotocol.authority.models.*;
import com.stunsci.jprotocol.encryption.EncryptionHelper;
import com.stunsci.jprotocol.persistence.EmfInstanceManager;

	
@Path("authority")
public class AuthorityApi {

	EncryptionHelper eh = new EncryptionHelper();
	
	@GET
	@Produces("application/json")
	@Path("/products/")
	public List<DProduct> getAllProducts()
	{
	 	EntityManager em = EmfInstanceManager.getInstance().get().createEntityManager();
	    Query query = em.createQuery("SELECT e FROM "+ DProduct.class.getName() +" as e");
	    
	    @SuppressWarnings("unchecked")
		List<DProduct> results = query.getResultList();
	    return results;
	}
	 @POST
	 @Consumes("application/json")
	 @Produces("application/json")
	 @Path("/products/")
	 public DProduct createProduct(DProduct product) {
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
	 public void updateProduct(DProduct product)
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
	 @Produces("application/json")
	 @Path("/certify/")
	 public String certifyProduct(@FormParam("id") int id, @FormParam("merchant") String merchant) {
	 	EntityManager em = EmfInstanceManager.getInstance().get().createEntityManager();

	 	Certificate cert = new Certificate();
	 	
	 	try{
	 		DProduct product = em.find(DProduct.class, id);
	 		
	 		String shaProductMerchant = eh.digestString(product.getName() + product.getContent() + merchant);
	 		
	 		cert.setMerchant(merchant);
	 		cert.setProductId(product.getId());
	 		cert.setHash(shaProductMerchant);
	 		
	 		em.persist(cert);
	 		
	 	}catch(Exception ex)
	 	{
	 		System.err.println(ex);
	 	}
	 	finally
	 	{
	 		em.close();
	 	}
	 	
	 	return cert.getHash();
    }
	 
	@GET
	@Path("/verify/{hash}")
	@Produces("application/json")
	public Verification validateCertificate(@PathParam("hash") String hash)
	{
		EntityManager em = EmfInstanceManager.getInstance().get().createEntityManager();
		Query query = em.createQuery("SELECT c FROM " + Certificate.class.getName() + " c WHERE c.hash = :hash");
		
		query.setParameter("hash", hash);
		
		Verification verification = new Verification();
		
		 	try{
				@SuppressWarnings("unchecked")
				List<Certificate> certificates = query.getResultList();
				
				if(certificates.isEmpty())
			        throw new WebApplicationException(404);
				
				Certificate certificate = certificates.get(0);
				
				DProduct product = em.find(DProduct.class, certificate.getProductId());
				
				verification.setProductName(product.getName());
				verification.setProductDescription(product.getDescription());
				verification.setMerchant(certificate.getMerchant());
						
		 	}catch(Exception ex)
		 	{
		 		System.err.println(ex);
		        throw new WebApplicationException(404);
		 	}
		 	finally
		 	{
		 		em.close();
		 	}
		 	
		 	
			return verification;
	 }
}
