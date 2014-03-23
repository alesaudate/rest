package br.com.geladaonline.util;

import java.nio.ByteBuffer;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Hash {

	public static String hash(Object entity) {
		int hashCode = HashCodeBuilder.reflectionHashCode(entity);

		byte[] array = ByteBuffer.allocate(4).putInt(hashCode).array();

		String hash = Hex.encodeHexString(array);
		return hash;

	}
	

}
