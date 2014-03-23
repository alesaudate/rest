package br.com.geladaonline.services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import br.com.geladaonline.model.Email;

@Path("/email")
public class EmailService {

	
	private static ExecutorService executorService;
	
	static {
		executorService = Executors.newFixedThreadPool(20);
	}
	
	
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public void recuperarEmails(@Suspended final AsyncResponse asyncResponse) {
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(20 * 1000); //20 segundos
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
				
				Email email = new Email()
					.withAssunto("Email recebido");
				
				asyncResponse.resume(email);
				
			}
		});
	}

	
	
}
