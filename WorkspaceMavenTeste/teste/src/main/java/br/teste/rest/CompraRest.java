package br.teste.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.teste.dao.CompraDAO;
import br.teste.dao.ProdutoCompraDAO;
import br.teste.dao.ProdutoDAO;
import br.teste.modelo.Compra;
import br.teste.modelo.Produto;
import br.teste.modelo.ProdutoCompra;

@Path("compra")
public class CompraRest extends UtilRest{

	//Atributos DAO para todas as classes que forem gerenciadas pela JPA nesse Rest 
	private CompraDAO daoCompra = new CompraDAO();
	private ProdutoDAO daoProduto = new ProdutoDAO();
	private ProdutoCompraDAO daoProdutoCompra = new ProdutoCompraDAO();
	
	@Override
	protected void fechaConexao() {
		daoCompra.fechaConexao();
		daoProduto.fechaConexao();
		daoProdutoCompra.fechaConexao();
	}
	
	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String compraParam){
		//Cria uma referência para armazenar o retorno que esse método deve dar
		Response retorno = null;
		try{
			//Configura no construtor o formato dos campos de data visando inserí-las no BD
			Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

			//Converte o Json recebido para a classe Compra
			Compra compra = gSon.fromJson(compraParam, Compra.class);
			//Armazena separadamente os produtos comprados em uma lista
			List<ProdutoCompra> produtosComprados = compra.getProdutos();
			//System.out.println("compra recebida: "+new Gson().toJson(compra));
			//Na compra recebida, remove os produtos, pois ainda não podemos inserí-los na tabela produtocomprado 
			//(porquê? porque ainda não temos o id da compra, pois ela não foi inserida no BD...)
			compra.setProdutos(new ArrayList<ProdutoCompra>());
			//System.out.println("compra recebida s/ produtos: "+new Gson().toJson(compra));	
			//System.out.println("produtos: "+new Gson().toJson(produtosComprados));	
			
			//Faz uma alteração da compra ao invés de inserção. Assim, o objeto retornado terá o id da nova compra, para usarmos em seguida
			Compra compraManaged = daoCompra.alterar(compra);
			//System.out.println("Id da compra inserida = "+compraManaged.getId());
						
			//Para cada produto que o usuário colocou nessa compra, teremos um objeto de ProdutoCompra.
			//Para inserirmos corretamente na tabela produtocomprado, fazemos alguns passos...
			for (ProdutoCompra produtoCompra: produtosComprados) {
				//Setamos a compra como sendo a que acabamos de salvar
				produtoCompra.setCompra(compraManaged);
				//Buscamos no BD o produto que foi comprado
				Produto produtoManaged = daoProduto.buscarPorId(produtoCompra.getProduto().getId());
				//Setamos o produto como sendo o que acabamos de buscar
				produtoCompra.setProduto(produtoManaged);
				//System.out.println("n:n final = "+new Gson().toJson(produtoCompra));			
				//Inserimos esse carinha na tabela n:n do BD
				daoProdutoCompra.inserir(produtoCompra);
								
			}
			
			String msg = "Compra cadastrada com sucesso!";
			//Constrói a Response e armazena na variável
			retorno = this.buildResponse(msg);
			
		}catch(Exception e){
			e.printStackTrace();
			//Constrói a Response e armazena na variável
			retorno = this.buildErrorResponse(e.getMessage());
		}finally {
			//fecha a(s) conexão(ões) gerenciada(s)
			this.fechaConexao();
		}
		//Retorna a Response
		return retorno;
		
	}
	
	@GET
	@Path("/relatorio")
	@Produces(MediaType.APPLICATION_JSON)
	public Response gerarRelatorio(){
		Response retorno = null;
		try{
			//Busca todas as compras
			List<Compra> listaCompras = daoCompra.buscarTodos();
			
			//para cada compra encontrada
			for (Compra compra : listaCompras) {
				//formata seus atributos e objetos para exibição
				compra.formatarParaExibir();
			}

			//Constrói a Response e armazena na variável
			retorno = this.buildResponse(listaCompras);
			
		}catch(Exception e){
			e.printStackTrace();
			//Constrói a Response e armazena na variável
			retorno = this.buildErrorResponse(e.getMessage());
		}finally {
			//fecha a(s) conexão(ões) gerenciada(s)
			this.fechaConexao();
		}
		//Retorna a Response
		return retorno;
		
	}
	
}

