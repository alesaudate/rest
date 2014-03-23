package br.com.geladaonline.client;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.jettison.JettisonFeature;

import br.com.geladaonline.model.Cerveja;
import br.com.geladaonline.model.Cerveja.Tipo;
import br.com.geladaonline.model.rest.Cervejas;

public class Cliente {

	public static void main(String[] args) {

		List<Cerveja> cervejas = recuperarCervejas();

		for (Cerveja cerveja : cervejas) {
			System.out.println("Nome: " + cerveja.getNome());
		}

		Cerveja cerveja = new Cerveja();
		cerveja.setCervejaria("Ambev");
		cerveja.setNome("Skol");
		cerveja.setTipo(Tipo.PILSEN);
		
		cerveja = criarCerveja(cerveja);
		
		System.out.println("Nova cerveja: " + cerveja.getNome());
		
	}

	public static List<Cerveja> recuperarCervejas() {

		Client client = ClientBuilder.newClient();

		return recuperarCervejas(client.target(Constants.HOST));
	}

	public static Cerveja criarCerveja(Cerveja cerveja) {
		Client cliente = ClientBuilder.newClient();
		
		return criarCerveja(cliente.target(Constants.HOST), cerveja);
	}
	
	private static List<Cerveja> recuperarCervejas(WebTarget target) {
		Cervejas cervejas = target.path("cervejas")
				.request(MediaType.APPLICATION_XML).get(Cervejas.class);

		List<Cerveja> cervejaList = new ArrayList<>();

		for (Link link : cervejas.getLinks()) {
			if (!link.getRel().equals(Constants.REL_CERVEJA)) {
				continue;
			}
			Cerveja cerveja = ClientBuilder.newClient()
					.register(JettisonFeature.class).invocation(link)
					.accept(MediaType.APPLICATION_JSON).get(Cerveja.class);
			cervejaList.add(cerveja);
		}
		return cervejaList;
	}

	private static Cerveja criarCerveja(WebTarget target, Cerveja cerveja) {
		Response response = ClientBuilder.newClient().target(Constants.HOST).path("cervejas")
				.request(MediaType.APPLICATION_XML).post(Entity.xml(cerveja));
		if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {

			Link link = Link.fromUri(response.getLocation()).build();

			cerveja = ClientBuilder.newClient().invocation(link)
					.accept(MediaType.APPLICATION_XML).get(Cerveja.class);
			
			return cerveja;

		}
		throw new RuntimeException("Código retornado diferente do esperado");
	}

}
