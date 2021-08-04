package br.teste.dao;

import java.util.List;

import javax.persistence.EntityManager;

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

		try{
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
