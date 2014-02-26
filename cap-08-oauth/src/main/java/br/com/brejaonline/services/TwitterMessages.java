package br.com.brejaonline.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.oauth1.AccessToken;

import br.com.brejaonline.model.twitter.Status;
import br.com.brejaonline.model.twitter.Statuses;

@Path("/twitter/messages")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class TwitterMessages {

	@GET
	public Statuses list(@Context HttpServletRequest req) {

		AccessToken token = (AccessToken) req
				.getAttribute(TwitterLoginFilter.ACCESS_TOKEN_KEY);
		Client client = TwitterOAuthFlowService.getClient(token);

		Response response = client
				.target("https://api.twitter.com/1.1/statuses/home_timeline.json")
				.request().get();
		
		List<Status> statusList = response.readEntity(new GenericType<List<Status>>() {});
		Statuses status = new Statuses();
		
		status.setStatusCollection(statusList);
		
		return status;
		

	}

}
