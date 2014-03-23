package br.com.geladaonline.model;

import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Cerveja {

	private String nome;
	private String descricao;
	private String cervejaria;

	private Tipo tipo;

	public Cerveja() {}
	
	public Cerveja(String nome, String descricao, String cervejaria, Tipo tipo) {
		this.nome = nome;
		this.descricao = descricao;
		this.cervejaria = cervejaria;
		this.tipo = tipo;
	}

	public enum Tipo {
		LAGER, PILSEN, BOCK, WEIZEN;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public boolean matchExemplo(Cerveja cerveja) {
		boolean match = true;
		match &= matchRegex(cerveja.nome, this.nome); 
		match &= matchRegex(cerveja.descricao, this.descricao);
		match &= matchRegex(cerveja.cervejaria, this.cervejaria);
		match &= this.tipo != null ? matchRegex(cerveja.tipo.name(), this.tipo.name()) : true;
		
		return match;
	}
	
	private boolean matchRegex(String toCompare, String source) {
		if (source != null) {
			return Pattern.compile(source).matcher(toCompare).find();
		}
		return true;
	}
	
	public String toString() {
	      return this.nome + " - " + this.descricao;
	   }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cerveja other = (Cerveja) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	
	public static Builder builder() {
		return new Builder();
	}
	
	
	public static class Builder {
		private Cerveja building;
		
		public Builder() {
			building = new Cerveja();
			building.nome = "";
			building.descricao = "";
			building.cervejaria = "";
			building.tipo = null;
		}
		
		public Builder withNome(String nome) {			
			building.nome = nome;
			return this;
		}
		
		public Builder withDescricao(String descricao) {
			building.descricao = descricao;
			return this;
		}
		
		public Builder withCervejaria(String cervejaria) {
			building.cervejaria = cervejaria;
			return this;
		}
		
		public Builder withTipo(Tipo tipo) {
			building.tipo = tipo;
			return this;
		}
		
		public Builder withTipo(String tipo) {
			if (tipo == null || tipo.trim().equals("")) {
				return this;
			}
			building.tipo = Tipo.valueOf(tipo);
			return this;
		}
		
		public Cerveja build() {
			return building;
		}
		
	}
	
}
