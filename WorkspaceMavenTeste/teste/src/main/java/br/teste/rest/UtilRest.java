package br.teste.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class UtilRest {

	//Método para criar as respostas de sucesso
	public Response buildResponse(Object result) {

		try {
			//Configura o formato das datas a ser enviado no Json de resultado
			Gson gSon=  new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

			String valorResposta = gSon.toJson(result);
			
			return Response.ok(valorResposta).build();
		} catch (Exception ex) {
			ex.printStackTrace();
			return this.buildErrorResponse(ex.getMessage());
		}
			
	}
	
	//Método para criar as respostas de erro
	public Response buildErrorResponse(String str) {

		ResponseBuilder rb = Response.status(Response.Status.INTERNAL_SERVER_ERROR);

		rb = rb.entity(str);

		rb = rb.type("text/plain");
		
		return rb.build();
	}
	
	//Método abstrato que declara a necessidade de suas filhas terem um método chamado fechaConexao
	//Eles devem, por sua vez, chamar o método fechaConexao da GenericDao para todas as DAO que forem abertas nela.
	protected abstract void fechaConexao();
	
}

