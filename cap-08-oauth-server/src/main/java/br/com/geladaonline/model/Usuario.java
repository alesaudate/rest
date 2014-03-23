package br.com.geladaonline.model;

import java.security.Principal;

public class Usuario implements Principal{

	private String name;
	
	public Usuario(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}

}
