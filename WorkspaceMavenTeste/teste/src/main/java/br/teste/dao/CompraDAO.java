package br.teste.dao;

import br.teste.modelo.Compra;

//Classe DAO para gerenciamento da entidade Compra
public class CompraDAO extends GenericDAO<Compra>{
	
	//Construtor que armazena no atributo meuTipo o tipo da classe.
	//Usado em diversos momentos pelo GenericDAO
	public CompraDAO() {
		this.meuTipo = Compra.class; 
	}

}
