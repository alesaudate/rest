package br.com.geladaonline.services;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

import java.net.URI;
import java.util.Calendar;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import br.com.geladaonline.model.Email;

@Path("/emailInterop")
public class EmailServiceInterop {

	private static final ExecutorService executorService;
	private static final Map<String, Email> emails;

	static {
		executorService = Executors.newFixedThreadPool(20);
		emails = new ConcurrentHashMap<>();
	}

	@GET
	public Response recuperarEmails() {

		final String emailId = UUID.randomUUID().toString();

		executorService.execute(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(20 * 1000); // 20 segundos
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				Email email = new Email().withAssunto("Email recebido");
				emails.put(emailId, email);

			}
		});

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.SECOND, 21); //Tempo do Thread.sleep + tempo para gerar o email 

		URI uri = UriBuilder
				.fromPath("/cervejaria/services")
				.path(EmailServiceInterop.class)
				.path("/{id}")
				.build(emailId);
		
		Link link = Link.fromUri(uri).build();
		
		return Response
				.accepted()
				.header("Location", link.getUri())
				.header("Expires", calendar.getTime())
				.build();
	}

	@Path("/{id}")
	@GET
	@Produces({ APPLICATION_XML, APPLICATION_JSON })
	public Response recuperaEmail(@PathParam("id") String id) {

		Email email = emails.get(id);
		if (email == null) {
			return Response.noContent().build();
		} else {
			emails.remove(id);
			return Response.ok(email).build();
		}

	}

}
