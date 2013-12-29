package br.com.brejaonline.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.glassfish.jersey.jettison.JettisonFeature;

import br.com.brejaonline.model.Cerveja;
import br.com.brejaonline.model.rest.Cervejas;

public class Cliente {

	public static void main(String[] args) throws JAXBException {
		
		Client client = ClientBuilder.newClient();
		Cervejas cervejas = client.target("http://localhost:8080/cervejaria/")
				.register(JettisonFeature.class)
				.path("cervejas").request(MediaType.APPLICATION_JSON)				
				.get(Cervejas.class);

		for (Link link : cervejas.getLinks()) {
			Cerveja cerveja = client
					.register(JettisonFeature.class)
					.invocation(link)
					.accept(MediaType.APPLICATION_JSON)
					.get(Cerveja.class);
			System.out.println(cerveja.getNome());
		}

	}

}
