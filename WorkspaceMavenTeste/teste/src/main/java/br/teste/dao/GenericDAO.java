package br.teste.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.teste.bd.Conexao;

//Classe mãe de todos os outros DAO.
//Possui atributos do EntityManager para interagir com a JPA e para o tipo da classe filha, além 
//todos os métodos para as operações básicas e um para consultas JPQL
public class GenericDAO<E> {
	
	private EntityManager em = Conexao.getEntityManager();
	
	//Deve ser alimentado pelo construtor das classes DAO filhas com o tipo da classe. 
	//Isso é essencial para o funcionamento dessa classe  
	public Class<E> meuTipo;
	
	//método para encerrar a conexão com o EntityManager
	public void fechaConexao() {
		this.em.close();
	}
	
	//Método para inserir um registro no BD.
	//Precisa receber o objeto que contém os dados a serem inseridos.
	public void inserir(E entidade){
		
		try{
			em.getTransaction().begin();
			em.persist(entidade);
			em.getTransaction().commit();
		}catch(Exception e){
			throw e; 
		}
		
	}
	
	//Método para buscar todos os registros de uma entidade no BD
	@SuppressWarnings("unchecked")
	public List<E> buscarTodos(){
		
		try{
			em.getTransaction().begin();
			//Executa uma consulta "SELECT * FROM tabela" da tabela referente ao tipo do E da GenericDAO
			List<E> lista = em.createQuery("FROM "+meuTipo.getName()).getResultList();
			em.getTransaction().commit();
			return lista;
		}catch(Exception e){
			throw e; 
		}
		
	}
	
	//Método para buscar apenas um registro de uma entidade no BD
	//Precisa receber o ID do objeto a ser encontrado.
	public E buscarPorId(int id){
		
		try{
			E entidadeEncontrada = (E) em.find(meuTipo, id);
			return entidadeEncontrada;
		}catch(Exception e){
			throw e; 
		}
		
	}
	
	//Método para buscar todos os registros de uma entidade no BD
	//Precisa receber o ID do objeto a ser excluido.
	public void excluir(int id){

		try{
			//Captura o objeto daquele ID para removê-lo
			E entidade = this.buscarPorId(id);
			em.getTransaction().begin();
			em.remove(entidade);
			em.getTransaction().commit();
		}catch(Exception e){
			throw e; 
		}

	}
	

	//Método para alterar um registro no BD.
	//Precisa receber o objeto que contém os dados a serem alterados.
	public E alterar(E entidade){

		try{
			em.getTransaction().begin();
			E entidadeAlterada = em.merge(entidade);
			em.getTransaction().commit();
			return entidadeAlterada;
		}catch(Exception e){
			throw e; 
		}
		
	}
	
	
	//Método para executar uma consulta JPQL no BD.
	//Precisa receber a JPQL a ser executada.
	//A utilidade desse método é facilitar a execução de uma JPQL. 
	//Sempre que alguma consulta diferente for necessária, um método na DAO correspondente deve ser criado, 
	//recebendo o que deve ser usado na pesquisa, 
	//e aquele método então deve montar a JPQL e enviá-la pronta para este método aqui 
	@SuppressWarnings("unchecked")
	protected List<E> buscarPorJpql(String jpql){
		
		try{
			em.getTransaction().begin();
			List<E> lista = em.createQuery(jpql).getResultList();
			em.getTransaction().commit();
			return lista;
		}catch(Exception e){
			throw e; 
		}
		
	}

}
