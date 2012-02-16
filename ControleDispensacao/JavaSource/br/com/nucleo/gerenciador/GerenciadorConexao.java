package br.com.nucleo.gerenciador;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GerenciadorConexao {
    private EntityManagerFactory emf; 

    public EntityManager CreateEntityManager2() {
    	return Persistence.createEntityManagerFactory("ControleDispensacao").createEntityManager();
    }
    
    
    public EntityManager getEntityManager2(){
    	if(emf == null){
    		emf = CreateEntityManager2().getEntityManagerFactory();
    		return emf.createEntityManager();
    	}else{
    		return emf.createEntityManager();
    	}
    }
}
