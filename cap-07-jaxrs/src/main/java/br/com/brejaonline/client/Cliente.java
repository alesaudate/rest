package br.com.brejaonline.client;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.jettison.JettisonFeature;

import br.com.brejaonline.model.Cerveja;
import br.com.brejaonline.model.rest.Cervejas;

public class Cliente {

	public static void main(String[] args) {

		Client client = ClientBuilder.newClient();

		List<Cerveja> cervejas = recuperarCervejas(client
				.target(Constants.HOST));

		for (Cerveja cerveja : cervejas) {
			System.out.println(cerveja.getNome());
		}

	}

	private static List<Cerveja> recuperarCervejas(WebTarget target) {
		Cervejas cervejas = target.path("cervejas")
				.request(MediaType.APPLICATION_XML).get(Cervejas.class);

		List<Cerveja> cervejaList = new ArrayList<>();

		for (Link link : cervejas.getLinks()) {
			Cerveja cerveja = ClientBuilder.newClient()
					.register(JettisonFeature.class).invocation(link)
					.accept(MediaType.APPLICATION_JSON).get(Cerveja.class);
			cervejaList.add(cerveja);
		}
		return cervejaList;
	}

}
