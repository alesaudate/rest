package br.com.geladaonline.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;
import org.glassfish.jersey.jettison.JettisonFeature;
import org.glassfish.jersey.server.oauth1.DefaultOAuth1Provider;
import org.glassfish.jersey.server.oauth1.OAuth1ServerFeature;
import org.glassfish.jersey.server.oauth1.OAuth1ServerProperties;


public class ApplicationJAXRS extends Application{

	
	@Override
	public Map<String, Object> getProperties() {
		Map<String, Object> properties = new HashMap<>();
		properties.put("jersey.config.server.provider.packages", "br.com.geladaonline.services");
		properties.put(OAuth1ServerProperties.ENABLE_TOKEN_RESOURCES, Boolean.TRUE);
		return properties;
	}
	
	@Override
	public Set<Object> getSingletons() {
		
		DefaultOAuth1Provider provider = new DefaultOAuth1Provider();
		String IDDoConsumidor = "App consumidora";
		String consumerKey = "123";
		String consumerSecret = "123";
		
		provider.registerConsumer(IDDoConsumidor, consumerKey, consumerSecret, new MultivaluedStringMap());
		
		
		
		Set<Object> singletons = new HashSet<>();
		singletons.add(new JettisonFeature());
		singletons.add(new OAuth1ServerFeature(provider));
		
		
		
		
		return singletons;
	}
	
	
}
