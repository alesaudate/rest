package br.com.geladaonline.model;

public class CervejaNaoEncontradaException extends RuntimeException {

	public CervejaNaoEncontradaException() {

	}

	public CervejaNaoEncontradaException(String message) {
		super(message);

	}

	public CervejaNaoEncontradaException(Throwable cause) {
		super(cause);

	}

	public CervejaNaoEncontradaException(String message, Throwable cause) {
		super(message, cause);

	}

	//Construtor JDK 1.7
	public CervejaNaoEncontradaException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
