package br.com.brejaonline.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.builder.HashCodeBuilder;

import br.com.brejaonline.services.CervejaService;

public class Hash {

	private static final MessageDigest md;
	
	static {
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

	public static String hash(Object entity) {
		int hashCode = HashCodeBuilder.reflectionHashCode(entity);

		byte[] array = ByteBuffer.allocate(4).putInt(hashCode).array();

		String hash = Hex.encodeHexString(array);
		return hash;

	}
	
	public static String hash(byte[] array) {
		
		array = md.digest(array);
		
		String hash = Hex.encodeHexString(array);
		return hash;
	}
	

}
