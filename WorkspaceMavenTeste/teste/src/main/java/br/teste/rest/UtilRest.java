package br.teste.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UtilRest {

	public Response buildResponse(Object result) {

		try {

			Gson gSon=  new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

			String valorResposta = gSon.toJson(result);
			
			return Response.ok(valorResposta).build();
		} catch (Exception ex) {
			ex.printStackTrace();
			return this.buildErrorResponse(ex.getMessage());
		}
			
	}
	
	
	public Response buildErrorResponse(String str) {

		ResponseBuilder rb = Response.status(Response.Status.INTERNAL_SERVER_ERROR);

		rb = rb.entity(str);

		rb = rb.type("text/plain");
		
		return rb.build();
	}
}

