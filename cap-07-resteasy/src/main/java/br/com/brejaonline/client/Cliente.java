package br.com.brejaonline.client;

import javax.ws.rs.client.ClientBuilder;
import javax.xml.bind.JAXBException;

import org.jboss.resteasy.client.jaxrs.ProxyBuilder;

import br.com.brejaonline.model.rest.Cervejas;

public class Cliente {

	public static void main(String[] args) throws JAXBException {

		
		ProxyBuilder<CervejaService> proxy = ProxyBuilder. builder(
				CervejaService.class,
				ClientBuilder.newClient()	
				.target(
						"http://localhost:8080/cervejaria"));
		CervejaService service = proxy.build();
		
		Cervejas cervejas = service.listeTodasAsCervejas(0);
		
		System.out.println(cervejas.getLinks().get(0));
	}

}
