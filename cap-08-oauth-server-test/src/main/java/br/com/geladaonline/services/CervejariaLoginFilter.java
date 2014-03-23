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

@WebFilter("/cervejas/*")
public class CervejariaLoginFilter implements Filter {

	public static final String OAUTH_VERIFIER = "oauth_verifier";
	public static final String OAUTH_TOKEN_FIELD = "oauth_token";
	public static final String TOKEN_COOKIE = "CervejariaToken";
	public static final String TOKEN_COOKIE_SECRET = "CervejariaTokenSecret";

	public static final String EMPTY_COOKIE = "empty";
	
	public static final String ACCESS_TOKEN_KEY = "AccessToken";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		AccessToken accessToken = getAccessToken(req);

		
		
		if (accessToken == null && req.getParameter("reissue") == null) {

			String verifier = req.getParameter(OAUTH_VERIFIER);
			String token = req.getParameter(OAUTH_TOKEN_FIELD);
			
			if (verifier != null && token != null) {
				accessToken = CervejariaOAuthFlowService.verify(token, verifier,req);
				
				Cookie accessTokenCookie = new Cookie(TOKEN_COOKIE, accessToken.getToken());
				accessTokenCookie.setPath("/");
				
				Cookie accessTokenSecretCookie = new Cookie(TOKEN_COOKIE_SECRET, accessToken.getAccessTokenSecret());
				accessTokenSecretCookie.setPath("/");
				
				resp.addCookie(accessTokenCookie);
				resp.addCookie(accessTokenSecretCookie);
			} else {
				String twitterAuthUri = CervejariaOAuthFlowService.init(req);
				resp.sendRedirect(twitterAuthUri);
				return;
			}
		} 
		req.setAttribute(ACCESS_TOKEN_KEY, accessToken);
		chain.doFilter(req, resp);

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	protected AccessToken getAccessToken(HttpServletRequest req) {

		String accessToken = null;
		String accessTokenSecret = null;

		Cookie[] cookies = req.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(TOKEN_COOKIE)) {
				accessToken = cookie.getValue();
			} else if (cookie.getName().equals(TOKEN_COOKIE_SECRET)) {
				accessTokenSecret = cookie.getValue();
			}
		}

		if (accessToken != null && accessTokenSecret != null) {
			if (accessToken.equals(EMPTY_COOKIE) || accessTokenSecret.equals(EMPTY_COOKIE)) {
				return null;
			}
			return new AccessToken(accessToken, accessTokenSecret);
		}
		return null;

	}

}
