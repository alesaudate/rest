package br.com.geladaonline.model;

public class CervejaJaExisteException extends RuntimeException {

	public CervejaJaExisteException() {
	}

	public CervejaJaExisteException(String message) {
		super(message);
	}

	public CervejaJaExisteException(Throwable cause) {
		super(cause);
	}

	public CervejaJaExisteException(String message, Throwable cause) {
		super(message, cause);
	}

	public CervejaJaExisteException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
