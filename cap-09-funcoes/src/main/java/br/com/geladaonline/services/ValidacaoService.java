package br.com.geladaonline.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/validacao")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class ValidacaoService {

	@Path("/cpf/{valor}")
	@GET
	public String validarCPF(
			@PathParam("valor") String cpf,
			@QueryParam("algoritmo") @DefaultValue("TODOS") AlgoritmoValidacao algoritmo) {
		boolean resultado = algoritmo.validar(cpf);
		if (!resultado) {
			throw new WebApplicationException(Response
					.status(Status.BAD_REQUEST)
					.entity("CPF inválido de acordo com o algoritmo "
							+ algoritmo.getNomeAlgoritmo()).build());
		}
		return "CPF válido";
	}
	
	
	@Path("/cpf/{valor}")
	@HEAD
	public Response validarCPFSemResultado(
			@PathParam("valor") String cpf,
			@QueryParam("algoritmo") @DefaultValue("TODOS") AlgoritmoValidacao algoritmo) {
		validarCPF(cpf, algoritmo);
		return Response.ok().build();
	}

}
