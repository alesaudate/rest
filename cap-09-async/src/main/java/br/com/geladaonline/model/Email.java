package br.com.geladaonline.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
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
	
	public Email() {}
	
	public Email(String mensagem, String formatoMensagem, String assunto, String destinatarios, String comCopia, String comCopiaOculta) {
		this(mensagem, 
				formatoMensagem,
				assunto,
				destinatarios != null ? 
						(List<String>)Arrays.asList(destinatarios.split(SEPARADOR_ENDERECOS)) 
						: new ArrayList<String>(), 
				comCopia != null ? 
						(List<String>)Arrays.asList(comCopia.split(SEPARADOR_ENDERECOS)) 
						: new ArrayList<String>(), 
				comCopiaOculta != null ? 
						(List<String>)Arrays.asList(comCopiaOculta.split(SEPARADOR_ENDERECOS)) 
						: new ArrayList<String>()
		);
	}

	public Email(String mensagem, String formatoMensagem,
			String assunto,
			List<String> destinatarios, List<String> comCopia,
			List<String> comCopiaOculta) {
		this.mensagem = mensagem != null ? mensagem : "";
		this.formatoMensagem = formatoMensagem != null ? formatoMensagem : FORMATO_PADRAO;
		if (destinatarios != null) {
			this.destinatarios = destinatarios;
		}
		if (comCopia != null) {
			this.comCopia = comCopia ;
		}
		if (comCopiaOculta != null) {
			this.comCopiaOculta = comCopiaOculta;	
		}
	}
	
	
	
	
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
