package br.com.brejaonline.client;

import java.io.File;
import java.net.URISyntaxException;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

public class Cliente {

	public static void main(String[] args) throws URISyntaxException {

		ClientBuilder
				.newClient()
				.register(MultiPartFeature.class)
				.target("http://localhost:8080/cervejaria/services/email")
				.request()
				.header("To", "alesaudate@gmail.com")
				.header("Cc", "fake@fake.com")
				.header("Bcc", "fake2@fake.com")
				.header("Subject", "Olá, mundo!")
				.post(Entity
						.text("Teste de envio de mensagens de email utilizando JAX-RS"));

		
		MultiPart multiPart = new MultiPart();
		multiPart.bodyPart("Mensagem com anexos", MediaType.TEXT_PLAIN_TYPE)
				.bodyPart(
						new FileDataBodyPart("imagem",
								new File(Cliente.class.getResource(
										"/Erdinger Weissbier.jpg").toURI())));
		
		ClientBuilder
		.newClient()
		.register(MultiPartFeature.class)
		.target("http://localhost:8080/cervejaria/services/email")
		.request()
		.header("To", "alesaudate@gmail.com")
		.header("Cc", "fake@fake.com")
		.header("Bcc", "fake2@fake.com")
		.header("Subject", "Olá, mundo!")
		.post(Entity.entity(multiPart, multiPart.getMediaType()));

	}

}
