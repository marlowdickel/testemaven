package br.teste.dao;

import java.util.List;

import br.teste.modelo.Produto;

public class ProdutoDAO extends GenericDAO<Produto> {

	 public ProdutoDAO() {
		 this.meuTipo = Produto.class; 
	 }
	 
	 public List<Produto> buscarParaVenda(int idMarca, int categoria) {
		 try{
				//repare que é "marca", não "marcas", pois é conforme nome da classe e não da tabela do BD
				String condicao = "FROM "+meuTipo.getName()+" WHERE marca.id = '"+idMarca+"' AND categoria = '"+categoria+"' ORDER BY modelo ASC";
				List<Produto> listaProdutos = this.buscarPorJpql(condicao);
				
				return this.mudarCategoriaIntParaString(listaProdutos);
				
			}catch(Exception e){
				throw e;
			}
	 }
	 
	 public List<Produto> buscarPorModelo(String modelo) {
		 try{
				//repare que é "marca", não "marcas", pois é conforme nome da classe e não da tabela do BD
				String condicao = "FROM "+meuTipo.getName()+" WHERE modelo LIKE '%"+modelo+"%' ORDER BY categoria ASC, marca.nome ASC, modelo ASC";
				List<Produto> listaProdutos = this.buscarPorJpql(condicao);
				
				return this.mudarCategoriaIntParaString(listaProdutos);
				
			}catch(Exception e){
				throw e;
			}
	 }
	 
	 private List<Produto> mudarCategoriaIntParaString(List<Produto> listaProdutos){
		 
		 for (Object item : listaProdutos) {
				((Produto) item).setCategoria(((Produto) item).getCategoria().equals("1") ? "Geladeira" : "Freezer");			
			}
			return listaProdutos;
		 
	 }
	 
}
