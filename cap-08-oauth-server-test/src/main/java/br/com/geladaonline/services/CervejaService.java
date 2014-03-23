package br.com.geladaonline.services;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.oauth1.AccessToken;

import br.com.geladaonline.model.Cerveja;
import br.com.geladaonline.model.rest.Cervejas;

@Path("/cervejas")
@Consumes({ MediaType.TEXT_XML, MediaType.APPLICATION_XML,
		MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_XML,
		MediaType.APPLICATION_JSON })
public class CervejaService {

	@Context
	private HttpServletRequest req;
	
	
	@Context
	private HttpServletResponse resp;

	@GET
	@Path("{nome}")
	public Response encontreCerveja(@PathParam("nome") String nomeDaCerveja)
			throws URISyntaxException {

		try {
			Client client = getClient();
			return Response
					.ok(client
							.target("http://localhost:8080/cervejaria/services/cervejas/")
							.path(nomeDaCerveja)
							.request(MediaType.APPLICATION_XML)
							.get(Cerveja.class)).build();
		} catch (WebApplicationException ex) {
			if (ex.getResponse().getStatus() == Status.UNAUTHORIZED
					.getStatusCode()) {
				
				
				String twitterAuthUri = CervejariaOAuthFlowService.reissueAuthorization(req, resp);
				return Response.temporaryRedirect(new URI(twitterAuthUri))
						.build();

			} else {
				throw ex;
			}
		}

	}

	@GET
	public Response listeTodasAsCervejas(@QueryParam("pagina") int pagina) throws URISyntaxException {

		Client client = getClient();

		try {
			return Response
					.ok(client
							.target("http://localhost:8080/cervejaria/services/cervejas/")
							.queryParam("pagina", pagina)
							.request(MediaType.APPLICATION_XML)
							.get(Cervejas.class)).build();
		} catch (WebApplicationException ex) {
			if (ex.getResponse().getStatus() == Status.UNAUTHORIZED
					.getStatusCode()) {
				String twitterAuthUri = CervejariaOAuthFlowService.reissueAuthorization(req, resp);
				return Response.temporaryRedirect(new URI(twitterAuthUri))
						.build();

			} else {
				throw ex;
			}
		}
	}

	private Client getClient() {
		AccessToken token = (AccessToken) req
				.getAttribute(CervejariaLoginFilter.ACCESS_TOKEN_KEY);
		Client client = CervejariaOAuthFlowService.getClient(token);

		return client;
	}

}
