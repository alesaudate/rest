package br.com.geladaonline.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.oauth1.AccessToken;

import br.com.geladaonline.model.twitter.Status;
import br.com.geladaonline.model.twitter.Statuses;

@Path("/twitter/messages")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class TwitterMessages {

	@GET
	public Response list(@Context HttpServletRequest req,
			@Context HttpServletResponse resp) throws URISyntaxException {

		AccessToken token = (AccessToken) req
				.getAttribute(TwitterLoginFilter.ACCESS_TOKEN_KEY);
		Client client = TwitterOAuthFlowService.getClient(token);

		try {
			Response response = client
					.target("https://api.twitter.com/1.1/statuses/home_timeline.json")
					.request().get();

			
			
			
			Status[] statusList = response
					.readEntity(Status[].class);
			Statuses status = new Statuses();

			status.setStatusCollection(Arrays.asList(statusList));

			return Response.ok(status).build();

		} catch (WebApplicationException ex) {
			if (ex.getResponse().getStatus() == Response.Status.UNAUTHORIZED
					.getStatusCode()) {
				// Indica que o acesso foi revogado pelo usuário e um novo token deve
				// ser obtido

				String twitterAuthUri = TwitterOAuthFlowService
						.reissueAuthorization(req, resp);
				return Response.temporaryRedirect(new URI(twitterAuthUri))
						.build();

			} else {
				throw ex;
			}
		}

	}

}
