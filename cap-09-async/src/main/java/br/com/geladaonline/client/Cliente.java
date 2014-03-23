package br.com.geladaonline.client;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.jettison.JettisonFeature;
import org.glassfish.jersey.message.internal.DateProvider;

import br.com.geladaonline.model.Email;

public class Cliente {

	public static void main(String[] args) throws URISyntaxException, InterruptedException, ExecutionException {

		Future<Response> futureResponse = ClientBuilder
				.newClient()
				.target("http://localhost:8080/cervejaria/services/email")
				.request()
				.async()
				.get();
		
		System.out.println("Requisição submetida para o primeiro. Aguardando resposta...");
		Response response = futureResponse.get();
		
		System.out.println("Resposta do primeiro tipo:");
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(Email.class));
		
		
		System.out.println("-------------------");
		
		response = ClientBuilder
				.newClient()
				.target("http://localhost:8080/cervejaria/services/emailInterop")
				.request()
				.get();
		
		System.out.println("Status da resposta:");
		System.out.println(response.getStatus());
		String expires = response.getHeaderString("Expires");
		System.out.println("Retornar a requisição em " + expires);
		System.out.println("link recuperado: " + response.getHeaderString("Location"));
		
		Date date = new DateProvider().fromString(expires);
		
		long now = System.currentTimeMillis();
		long waitUntil = date.getTime() - now;
		
		System.out.println("Esperar " + (waitUntil ) + " millissegundos");
		Thread.sleep(waitUntil);
		
		System.out.println("Re-submetendo a requisição...");
		response = ClientBuilder
				.newClient()
				.target(response.getHeaderString("Location"))
				.register(JettisonFeature.class)
				.request()
				.get();
		
		System.out.println("Lido:");
		System.out.println(response.readEntity(Email.class));
		
		

		
	}

}
