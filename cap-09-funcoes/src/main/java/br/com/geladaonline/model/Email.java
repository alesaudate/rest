package br.com.geladaonline.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Email {

	private String mensagem = "";
	
	private String formatoMensagem = FORMATO_PADRAO;
	
	private String assunto = "";
	
	private List<String> destinatarios = new ArrayList<>();
	
	private List<String> comCopia = new ArrayList<>();
	
	private List<String> comCopiaOculta = new ArrayList<>();
	
	private List<Anexo> anexos = new ArrayList<>();
	
	public static final String SEPARADOR_ENDERECOS = ",";
	
	public static final String FORMATO_PADRAO = "text/plain";
	
	public Email withMensagem(String mensagem, String formato) {
		if (mensagem != null && formato != null) {
			this.mensagem = mensagem;
			this.formatoMensagem = formato;
		}
		return this;
	}
	
	public Email withAssunto(String assunto) {
		if (assunto != null) {
			this.assunto = assunto;
		}
		return this;
	}
	
	public Email withDestinatario(String destinatario) {
		if (destinatario != null) {
			withDestinatarios(destinatario.split(SEPARADOR_ENDERECOS));
		}
		return this;
	}
	
	public Email withDestinatarios(String... destinatarios) {
		if (destinatarios != null) {
			this.destinatarios.addAll(Arrays.asList(destinatarios));
		}
		return this;
	}
	
	public Email withComCopia (String comCopia) {
		if (comCopia != null) {
			withComCopias(comCopia.split(SEPARADOR_ENDERECOS));
		}
		return this;
	}
	
	public Email withComCopias(String... comCopias) {
		if (comCopias != null) {
			this.comCopia.addAll(Arrays.asList(comCopias));
		}
		return this;
	}
	
	public Email withComCopiaOculta (String comCopiaOculta) {
		if (comCopiaOculta != null) {
			withComCopiasOcultas(comCopiaOculta.split(SEPARADOR_ENDERECOS));
		}
		return this;
	}
	
	public Email withComCopiasOcultas(String... copiasOcultas) {
		if (copiasOcultas != null) {
			this.comCopiaOculta.addAll(Arrays.asList(copiasOcultas));
		}
		return this;
	}
	
	public Email withAnexo (Anexo anexo) {
		if (anexo != null) {
			this.anexos.add(anexo);
		}
		return this;
	}

	public String getMensagem() {
		return mensagem;
	}

	public String getFormatoMensagem() {
		return formatoMensagem;
	}

	public List<String> getDestinatarios() {
		return destinatarios;
	}

	public List<String> getComCopia() {
		return comCopia;
	}

	public List<String> getComCopiaOculta() {
		return comCopiaOculta;
	}

	public List<Anexo> getAnexos() {
		return anexos;
	}
	
	
	
	
	

	@Override
	public String toString() {
		return "Email [mensagem=" + mensagem + ", formatoMensagem="
				+ formatoMensagem + ", assunto=" + assunto + ", destinatarios="
				+ destinatarios + ", comCopia=" + comCopia
				+ ", comCopiaOculta=" + comCopiaOculta + ", anexos=" + anexos
				+ "]";
	}

	public void enviar() {
		System.out.println("Enviando email...: " + toString());
	}

}
