package br.teste.bd;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Conexao {
	
	//nome da unidade de persistencia definida no persistence.xml
	private static final String UNIT_NAME = "testeJPA";
		
	private static EntityManagerFactory factory = null;
	
	//método para gerar/buscar a EntityManager, para gerenciar os objetos do JPA
	public static EntityManager getEntityManager() {
		EntityManager em = null;
		try {
			if (factory == null) {
				factory = Persistence.createEntityManagerFactory(UNIT_NAME);
			}
			em = factory.createEntityManager();
		}catch(Exception e) {
			throw e;
		}
		return em;
	}
	
	public static Object getPrimaryKey(Object entidade){
		return factory.getPersistenceUnitUtil().getIdentifier(entidade);
	}
	
}
