package br.com.geladaonline.services;

import javax.net.ssl.SSLContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Feature;

import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.oauth1.AccessToken;
import org.glassfish.jersey.client.oauth1.ConsumerCredentials;
import org.glassfish.jersey.client.oauth1.OAuth1AuthorizationFlow;
import org.glassfish.jersey.client.oauth1.OAuth1ClientSupport;
import org.glassfish.jersey.jackson.JacksonFeature;

public class CervejariaOAuthFlowService {

	private static final String CONSUMER_KEY = "123";
	private static final String CONSUMER_SECRET = "123";

	public static String init(HttpServletRequest req) {

		ConsumerCredentials credentials = new ConsumerCredentials(CONSUMER_KEY,
				CONSUMER_SECRET);
		
		StringBuffer callback = req.getRequestURL();
		callback.append(";jsessionid=").append(req.getSession().getId());
		if (req.getQueryString() != null) {
			callback.append("?").append(req.getQueryString());
		}
		
		SSLContext context = SslConfigurator.newInstance()
				.trustStoreFile("/home/alexandre/git/codigoFonte/rest/rest/cap-08-ssl/src/main/java/br/com/brejaonline/client/server.keystore")
				.keyPassword("cervejaria").createSSLContext();
		
		Client client = ClientBuilder.newBuilder().sslContext(context).build();
		
		OAuth1AuthorizationFlow flow = OAuth1ClientSupport
				.builder(credentials)
				.authorizationFlow(
						"https://localhost:8443/cervejaria/services/requestToken", 
						"https://localhost:8443/cervejaria/services/accessToken", 
						"https://localhost:8443/cervejaria/services/authorize")
				.client(client)
				.callbackUri(callback.toString())
				.build();

		String authorizationUri = flow.start();

		String token = authorizationUri.substring(authorizationUri
				.indexOf(CervejariaLoginFilter.OAUTH_TOKEN_FIELD) + 
				CervejariaLoginFilter.OAUTH_TOKEN_FIELD.length() + 1);

		req.getSession().setAttribute(token, flow);

		return authorizationUri;

	}

	public static String reissueAuthorization(HttpServletRequest req,
			HttpServletResponse resp) {

		String redirectURL = init(req);

		Cookie accessTokenCookie = new Cookie(
				CervejariaLoginFilter.TOKEN_COOKIE,
				CervejariaLoginFilter.EMPTY_COOKIE);
		accessTokenCookie.setPath("/");

		Cookie accessTokenSecretCookie = new Cookie(
				CervejariaLoginFilter.TOKEN_COOKIE_SECRET,
				CervejariaLoginFilter.EMPTY_COOKIE);
		accessTokenSecretCookie.setPath("/");

		resp.addCookie(accessTokenCookie);
		resp.addCookie(accessTokenSecretCookie);

		return redirectURL;
	}

	public static AccessToken verify(String token, String verifier,
			HttpServletRequest req) {

		OAuth1AuthorizationFlow flow = (OAuth1AuthorizationFlow) req
				.getSession().getAttribute(token);

		AccessToken accessToken = flow.finish(verifier);

		req.getSession().removeAttribute(token);

		return accessToken;

	}

	public static Client getClient(AccessToken accessToken) {
		ConsumerCredentials credentials = new ConsumerCredentials(CONSUMER_KEY,
				CONSUMER_SECRET);

		Feature feature = OAuth1ClientSupport.builder(credentials).feature()
				.accessToken(accessToken).build();

		Client client = ClientBuilder.newClient().register(feature)
				.register(JacksonFeature.class);

		return client;

	}

}
