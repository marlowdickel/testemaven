package br.teste.bd;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Conexao {
	
	//nome da unidade de persistencia definia no persistence.xml
	private static final String UNIT_NAME = "testeJPA";
		
	private static EntityManagerFactory factory = null;
	
	public static EntityManager getEntityManager() {
		EntityManager em = null;
		try {
			if (factory == null) {
				factory = Persistence.createEntityManagerFactory(UNIT_NAME);
			}
			em = factory.createEntityManager();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return em;
	}
	
	public static Object getPrimaryKey(Object entidade){
		return factory.getPersistenceUnitUtil().getIdentifier(entidade);
	}
	/*
	//nome da unidade de persistencia definia no persistence.xml
	 private static final String UNIT_NAME = "testeJPA";
	 
	 private EntityManagerFactory emf = null;
	 
	 private EntityManager em = null;
	 
	 public EntityManager getEntityManager() {
	  
	  if (emf == null) {
	   emf = Persistence.createEntityManagerFactory(UNIT_NAME);
	  }
	  
	  if (em == null) {
	   em = emf.createEntityManager();
	  }
	  
	  return em;
	  
	 } 
	/*
	private Connection conexao;
	
	public Connection conecta() throws SQLException, ClassNotFoundException {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		conexao = java.sql.DriverManager.
				getConnection("jdbc:mysql://localhost/bdcoldigo?"
						+ "user=root&password=root&useTimezone=true&serverTimezone=UTC");
		
		return conexao;
		
	}
	*/
}
