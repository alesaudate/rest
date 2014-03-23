package br.com.geladaonline.services;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Feature;

import org.glassfish.jersey.client.oauth1.AccessToken;
import org.glassfish.jersey.client.oauth1.ConsumerCredentials;
import org.glassfish.jersey.client.oauth1.OAuth1AuthorizationFlow;
import org.glassfish.jersey.client.oauth1.OAuth1ClientSupport;
import org.glassfish.jersey.jackson.JacksonFeature;

public class TwitterOAuthFlowService {

	public static final String OAUTH_TOKEN_FIELD = "oauth_token";

	private static final String CONSUMER_KEY = "99viW3aGuXaCjcCmgBCOA";
	private static final String CONSUMER_SECRET = "UHgI9BgC5gFsxSlh2SRfZppQEQnaetQ1Ts42c4r6O68";

	public static String init(HttpServletRequest req) {

		ConsumerCredentials credentials = new ConsumerCredentials(CONSUMER_KEY,
				CONSUMER_SECRET);
		
		StringBuffer callback = req.getRequestURL();
		callback.append(";jsessionid=").append(req.getSession().getId());
		if (req.getQueryString() != null) {
			callback.append("?").append(req.getQueryString());
		}
		
		String callbackHost = callback.toString().replace("localhost", "127.0.0.1");
		

		OAuth1AuthorizationFlow flow = OAuth1ClientSupport
				.builder(credentials)
				.authorizationFlow(
						"https://api.twitter.com/oauth/request_token", 
						"https://api.twitter.com/oauth/access_token", 
						"https://api.twitter.com/oauth/authorize")
				
				.callbackUri(callbackHost)
				.build();

		String authorizationUri = flow.start();

		String token = authorizationUri.substring(authorizationUri
				.indexOf(OAUTH_TOKEN_FIELD) + OAUTH_TOKEN_FIELD.length() + 1);

		req.getSession().setAttribute(token, flow);

		return authorizationUri;

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
	
	public static String reissueAuthorization(HttpServletRequest req, HttpServletResponse resp ) {
		
		String redirectURL = init(req);
		
		Cookie accessTokenCookie = new Cookie(TwitterLoginFilter.TOKEN_COOKIE, TwitterLoginFilter.EMPTY_COOKIE);
		accessTokenCookie.setPath("/");
		
		Cookie accessTokenSecretCookie = new Cookie(TwitterLoginFilter.TOKEN_COOKIE_SECRET, TwitterLoginFilter.EMPTY_COOKIE);
		accessTokenSecretCookie.setPath("/");
		
		resp.addCookie(accessTokenCookie);
		resp.addCookie(accessTokenSecretCookie);
		
		return redirectURL;
	}

}
