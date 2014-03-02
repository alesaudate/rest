package br.com.brejaonline.services;

import static javax.ws.rs.core.MediaType.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.HashSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.server.oauth1.DefaultOAuth1Provider;
import org.glassfish.jersey.server.oauth1.DefaultOAuth1Provider.Token;

@Path("/authorize")
@Consumes(APPLICATION_FORM_URLENCODED)
public class AuthorizeOAuth {

	
	@Context
	private DefaultOAuth1Provider provider;
	
	
	private static String authorizeForm;
	
	static {
		try {
			authorizeForm = IOUtils.toString(AuthorizeOAuth.class.getResourceAsStream("/login.html"));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@GET
	@Produces(TEXT_HTML)
	public Response beginAuthorization(@QueryParam("oauth_token") String token) throws URISyntaxException {
		String htmlForm = authorizeForm.replace("$$$OAUTH_TOKEN$$$", token);
		
		return Response.ok(htmlForm).build();
		
	}
	
	
	
	@POST
	@Path("/authorized")
	public Response authorizeApp(@FormParam("oauth_token") String token, 
		@FormParam("username") final String usuario,
		@FormParam("password") String senha) throws URISyntaxException {
		
		Token requestToken = (Token)provider.getRequestToken(token);
		
		String verifier = provider.authorizeToken(requestToken, new Principal() {
			
			@Override
			public String getName() {
				return usuario;
			}
		}, new HashSet<String>());
		
		StringBuilder callbackUrl = new StringBuilder(requestToken.getCallbackUrl());
		if (callbackUrl.toString().contains("?")) {
			callbackUrl.append("&");
		}
		else {
			callbackUrl.append("?");
		}
		callbackUrl.append("oauth_verifier=").append(verifier).append("&");
		callbackUrl.append("oauth_token=").append(token);
		return Response.status(Status.FOUND).location(new URI(callbackUrl.toString())).build();
		
	}
	
	
}
