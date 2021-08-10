package br.teste.dao;

import java.util.Iterator;
import java.util.List;

import br.teste.modelo.Produto;

//Classe DAO para gerenciamento da entidade Produto
public class ProdutoDAO extends GenericDAO<Produto> {

	//Construtor que armazena no atributo meuTipo o tipo da classe.
	//Usado em diversos momentos pelo GenericDAO
	public ProdutoDAO() {
		this.meuTipo = Produto.class; 
	}
	 
	//Busca do produto para a venda via JPQL
	 public List<Produto> buscarParaVenda(int idMarca, int categoria) {
		 try{
				//Repare que sempre que quiser se referir a uma tabela no JPQL deve usar o nome da classe modelo que é sua entidade
				String condicao = "FROM "+meuTipo.getName()+" WHERE marca.id = '"+idMarca+"' AND categoria = '"+categoria+"' ORDER BY modelo ASC";
				//Chama o método da GenericDAO que executa JPQLs
				List<Produto> listaProdutos = this.buscarPorJpql(condicao);
				//retorna a lista, já com as categorias dos produtos em formato String
				return this.categoriaProdutosParaString(listaProdutos);
				
			}catch(Exception e){
				throw e;
			}
	 }
	
	//Busca do produto por modelo para o CRUD via JPQL
	 public List<Produto> buscarPorModelo(String modelo) {
		 try{
				//Repare que sempre que quiser se referir a uma tabela no JPQL deve usar o nome da classe modelo que é sua entidade
				String condicao = "FROM "+meuTipo.getName()+" WHERE modelo LIKE '%"+modelo+"%' ORDER BY categoria ASC, marca.nome ASC, modelo ASC";
				//Chama o método da GenericDAO que executa JPQLs
				List<Produto> listaProdutos = this.buscarPorJpql(condicao);

				//retorna a lista, já com as categorias dos produtos em formato String
				return this.categoriaProdutosParaString(listaProdutos);
				
			}catch(Exception e){
				throw e;
			}
	 }
	 
	//Método que atualiza os numeros inteiros identificando a categoria no BD para seu devido nome, visando exibição no front-end
	 public List<Produto> categoriaProdutosParaString(List<Produto> listaProdutos){
		//para cada produto da lista 
		for (Produto produto : listaProdutos) {
			//chama o método que converte o número da categoria para seu texto
			produto.categoriaParaTexto();
		}
		//retorna a lista atualizada
		return listaProdutos;
		 
	 }
	 
}
