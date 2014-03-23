package br.com.geladaonline.client;

import javax.ws.rs.client.ClientBuilder;

import org.jboss.resteasy.client.jaxrs.ProxyBuilder;

import br.com.geladaonline.model.rest.Cervejas;

public class Cliente {

	public static void main(String[] args) {

		ProxyBuilder<CervejaService> proxy = ProxyBuilder.builder(
				CervejaService.class,
				ClientBuilder.newClient().target(
						"http://localhost:8080/cervejaria/services"));		
		
		CervejaService service = proxy.build();

		Cervejas cervejas = service.listeTodasAsCervejas(0);

		System.out.println(cervejas.getLinks().get(0));
	}

}
