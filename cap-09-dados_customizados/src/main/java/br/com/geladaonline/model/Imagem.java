package br.com.geladaonline.model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;

public class Imagem {
	
	public static final Map<String, String> EXTENSOES;
	
	static {
		EXTENSOES = new HashMap<>();
		EXTENSOES.put("image/jpg", ".jpg");
	}

	private byte[] dados;
	
	private String nome;
	
	private MediaType mediaType;

	public Imagem(byte[] dados, String nome, MediaType mediaType) {
		super();
		this.dados = dados;
		this.nome = nome;
		this.mediaType = mediaType;
	}
	

	public void salvar(String caminho) throws IOException {
		
		FileOutputStream fos = new FileOutputStream(caminho
				+ java.io.File.separator + nome
				+ EXTENSOES.get(mediaType.toString()));
		IOUtils.write(dados, fos);
		IOUtils.closeQuietly(fos);
	}

}
