package br.teste.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import br.teste.bd.Conexao;

public class GenericDAO<E> {
	
	private EntityManager em = Conexao.getEntityManager();
	
	public Class<E> meuTipo;
	
	public void inserir(E entidade){
		
		try{
			em.getTransaction().begin();
			em.persist(entidade);
			em.getTransaction().commit();
		}catch(Exception e){
			throw e; 
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<E> buscarTodos(Class<E> entidade){
		//System.out.println("buscando produto");
		
		try{
			List<E> lista = null;
			em.getTransaction().begin();
			lista = em.createQuery("FROM "+entidade.getName()).getResultList();
			em.getTransaction().commit();
			return lista;
		}catch(Exception e){
			throw e; 
		}
		
		
	}
	
	
	public void excluir(E entidade){
		//entidade.getClass().getDeclaredAnnotations();
		try{
			/*
			Object id = Conexao.getPrimaryKey(entidade);
			em.getTransaction().begin();
			em.createNativeQuery("DELETE FROM "+entidade.getClass().getSimpleName().toLowerCase()+" WHERE id = "+id).executeUpdate();
			em.getTransaction().commit();
			*/
			em.getTransaction().begin();
			em.remove(entidade);
			em.getTransaction().commit();
			
		}catch(Exception e){
			throw e; 
		}

	}
	
	public E buscarPorId(int id){
		
		try{
			E entidadeEncontrada = null;
			entidadeEncontrada = (E) em.find(meuTipo, id);
			return entidadeEncontrada;
		}catch(Exception e){
			throw e; 
		}
		
	}
	
	
	public E alterar(E entidade){

		try{
			E entidadeAlterada = null;
			em.getTransaction().begin();
			entidadeAlterada = em.merge(entidade);
			em.getTransaction().commit();
			return entidadeAlterada;
		}catch(Exception e){
			throw e; 
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	protected List<E> buscarPorJpql(String jpql){
		
		try{
			List<E> lista = null;
			em.getTransaction().begin();
			lista = em.createQuery(jpql).getResultList();
			em.getTransaction().commit();
			return lista;
		}catch(Exception e){
			throw e; 
		}
		
	}

}
