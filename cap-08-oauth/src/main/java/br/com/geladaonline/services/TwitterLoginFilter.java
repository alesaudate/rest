package br.com.geladaonline.services;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.glassfish.jersey.client.oauth1.AccessToken;

@WebFilter("/services/twitter/*")
public class TwitterLoginFilter implements Filter {

	public static final String OAUTH_VERIFIER = "oauth_verifier";
	public static final String TOKEN_COOKIE = "TwitterAccessToken";
	public static final String TOKEN_COOKIE_SECRET = "TwitterAccessTokenSecret";
	
	public static final String EMPTY_COOKIE = "empty";

	public static final String ACCESS_TOKEN_KEY = "AccessToken";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		org.glassfish.jersey.client.oauth1.AccessToken accessToken = recuperaAccessTokenDosCookies(req);

		if (accessToken == null) {

			String verifier = req.getParameter(OAUTH_VERIFIER);
			String token = req.getParameter(TwitterOAuthFlowService.OAUTH_TOKEN_FIELD);

			if (verifier != null && token != null) {
				accessToken = TwitterOAuthFlowService.verify(token, verifier,req);
				
				ajustaCookiesNaResposta(resp, accessToken);
			} else {
				String twitterAuthUri = TwitterOAuthFlowService.init(req);
				resp.sendRedirect(twitterAuthUri);
				return;
			}
		} 
		req.setAttribute(ACCESS_TOKEN_KEY, accessToken);
		chain.doFilter(req, resp);

	}

	private void ajustaCookiesNaResposta(HttpServletResponse resp,
			AccessToken accessToken) {
		Cookie accessTokenCookie = new Cookie(TOKEN_COOKIE, accessToken.getToken());
		accessTokenCookie.setPath("/");
		
		Cookie accessTokenSecretCookie = new Cookie(TOKEN_COOKIE_SECRET, accessToken.getAccessTokenSecret());
		accessTokenSecretCookie.setPath("/");
		
		resp.addCookie(accessTokenCookie);
		resp.addCookie(accessTokenSecretCookie);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	protected AccessToken recuperaAccessTokenDosCookies(HttpServletRequest req) {

		String accessToken = null;
		String accessTokenSecret = null;

		Cookie[] cookies = req.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(TOKEN_COOKIE)) {
				if (!cookie.getValue().equals(EMPTY_COOKIE)) {
					accessToken = cookie.getValue();
				}
			} else if (cookie.getName().equals(TOKEN_COOKIE_SECRET)) {
				if (!cookie.getValue().equals(EMPTY_COOKIE)) {
					accessTokenSecret = cookie.getValue();
				}
			}
		}

		if (accessToken != null && accessTokenSecret != null) {
			return new AccessToken(accessToken, accessTokenSecret);
		}
		return null;

	}

}
