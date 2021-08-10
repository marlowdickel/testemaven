package br.teste.dao;

import br.teste.modelo.ProdutoCompra;

//Classe DAO para gerenciamento da entidade ProdutoCompra
public class ProdutoCompraDAO extends GenericDAO<ProdutoCompra>{
	 
	//Construtor que armazena no atributo meuTipo o tipo da classe.
	//Usado em diversos momentos pelo GenericDAO
	public ProdutoCompraDAO() {
		 this.meuTipo = ProdutoCompra.class; 
	}
	
}
