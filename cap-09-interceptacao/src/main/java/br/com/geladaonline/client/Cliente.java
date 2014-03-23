package br.com.geladaonline.client;

import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.geladaonline.model.rest.Cervejas;

public class Cliente {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();

		Response response = client
				.target("http://localhost:8080/cervejaria/services")
				.path("/cervejas").request(MediaType.APPLICATION_XML).get();

		Date responseDate = new Date();
		System.out.println("Código: " + response.getStatus());

		Cervejas cervejas = response.readEntity(Cervejas.class);

		System.out.println("Resultado: " + cervejas);

		response = client.target("http://localhost:8080/cervejaria/services")
				.path("/cervejas").request(MediaType.APPLICATION_XML)
				.header("If-Modified-Since", responseDate).get();

		System.out.println("Código: " + response.getStatus());

		cervejas = response.readEntity(Cervejas.class);

		System.out.println("Resultado: " + cervejas);

	}

}
