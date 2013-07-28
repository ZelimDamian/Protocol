package com.stunsci.jprotocol.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
 
public class EmfInstanceManager {
 
    private EntityManagerFactory emfInstance;

    private static EmfInstanceManager emf;

    private EmfInstanceManager() {
    }

    public EntityManagerFactory get() {
        if(emfInstance == null) {
            emfInstance = Persistence.createEntityManagerFactory("transactions-optional");
        }
        return emfInstance;
    }

    public static EmfInstanceManager getInstance() {
        if(emf == null) {
            emf = new EmfInstanceManager();
        }
        return emf;
    }
}