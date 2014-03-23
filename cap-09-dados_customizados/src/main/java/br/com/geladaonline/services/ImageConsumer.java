package br.com.geladaonline.services;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.MessageBodyReader;

import org.apache.commons.io.IOUtils;

import br.com.geladaonline.model.Imagem;

public class ImageConsumer implements MessageBodyReader<Imagem>{
	
	
	


	@Override
	public boolean isReadable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		
		return type.equals(Imagem.class) && mediaType.getType().equals("image");
	}

	@Override
	public Imagem readFrom(Class<Imagem> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {
		
		
		
		
		if (!Imagem.EXTENSOES.containsKey(mediaType.toString())) {
			throw new WebApplicationException(Status.UNSUPPORTED_MEDIA_TYPE);
		}
		
		String nome = httpHeaders.getFirst("nome");
				
		byte[] dados = IOUtils.toByteArray(entityStream);
		
		
		Imagem imagem = new Imagem(dados, nome, mediaType);
		
		return imagem;
	}

}
