package br.com.geladaonline.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("/olaMundo")
public class TesteService {

	public TesteService() {
		System.out.println("construindo testeservice");
	}
	
	@GET
	@Produces("text/plain")
	public String dizOla() {
		return "Olá, mundo REST!";
	}
}
