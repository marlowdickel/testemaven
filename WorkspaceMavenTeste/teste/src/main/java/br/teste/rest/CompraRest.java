package br.teste.rest;

import java.sql.Connection;
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
import com.google.gson.JsonObject;

import br.teste.bd.Conexao;
import br.teste.dao.CompraDAO;
import br.teste.dao.GenericDAO;
import br.teste.dao.ProdutoCompraDAO;
import br.teste.dao.ProdutoDAO;
import br.teste.modelo.Compra;
import br.teste.modelo.Produto;
import br.teste.modelo.ProdutoCompra;
import br.teste.modelo.id;

@Path("compra")
public class CompraRest extends UtilRest{

	private CompraDAO daoCompra = new CompraDAO();
	private ProdutoDAO daoProduto = new ProdutoDAO();
	private ProdutoCompraDAO daoProdutoCompra = new ProdutoCompraDAO();
	
	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String compraParam){

		try{
			//possibilidade: insere compra com merge, e depois coloca produtos nela - não deu...
			//possibilidade 2:insere compra com merge, coloca o id dela nos produtos, depois do for usa o merge de novo - não deu: Unable to find br.teste.modelo.ProdutoCompra with id br.teste.modelo.ProdutoCompraKey@a1e
			//possibilidade 3: limpa produto e compra do ProdutoCompra antes de inserir um por um no BD
			Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

			Compra compra = gSon.fromJson(compraParam, Compra.class);
			List<ProdutoCompra> produtosComprados = compra.getProdutos();
			System.out.println("compra recebida: "+new Gson().toJson(compra));			
			compra.setProdutos(new ArrayList<ProdutoCompra>());
			System.out.println("compra recebida s/ produtos: "+new Gson().toJson(compra));	
			System.out.println("produtos: "+new Gson().toJson(produtosComprados));	
			
			
			Compra compraManaged = daoCompra.alterar(compra);
			Object idCompra = Conexao.getPrimaryKey(compraManaged);
			System.out.println("Id inserido = "+(int) idCompra);
			for (ProdutoCompra produtoCompra: produtosComprados) {
				
				compraManaged = daoCompra.buscarPorId(compraManaged.getId());
				produtoCompra.setCompra(compraManaged);
				Produto produtoManaged = daoProduto.buscarPorId(produtoCompra.getProduto().getId());
				produtoCompra.setProduto(produtoManaged);
				
				//produtoCompra.setId(new id(produtoManaged.getId(),compraManaged.getId()));
				
				System.out.println("n:n final = "+new Gson().toJson(produtoCompra));			
				//P1
				daoProdutoCompra.inserir(produtoCompra);
				//P2
				//compraManaged.getProdutos().add(produtoCompra);
				//P3 inicio
				/*
				produtoCompra.setCompra(null);
				produtoCompra.setProduto(null);
				daoProdutoCompra.inserir(produtoCompra);
				*/
				//p3 fim
				
			}
			//P2
			//compraManaged = daoCompra.alterar(compraManaged);
			
			String msg = "Compra cadastrada com sucesso!";

			return this.buildResponse(msg);
			
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
		
	}
	
	@GET
	@Path("/relatorio")
	@Produces(MediaType.APPLICATION_JSON)
	public Response gerarRelatorio(){
		try{
			
			List<Compra> listaCompras = daoCompra.buscarTodos(Compra.class);

			for (Compra compra : listaCompras) {
				compra.formatarParaExibir();
			}
			return this.buildResponse(listaCompras);
			
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	
		
	}
	
}

