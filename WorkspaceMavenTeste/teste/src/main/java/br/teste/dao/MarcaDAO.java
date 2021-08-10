package br.teste.dao;

import br.teste.modelo.Marca;

//Classe DAO para gerenciamento da entidade Marca
public class MarcaDAO extends GenericDAO<Marca>{
	
	//Construtor que armazena no atributo meuTipo o tipo da classe.
	//Usado em diversos momentos pelo GenericDAO
	public MarcaDAO() {
		 this.meuTipo = Marca.class; 
	}
	
}
