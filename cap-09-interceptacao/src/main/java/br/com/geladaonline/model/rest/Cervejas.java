package br.com.geladaonline.model.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.com.geladaonline.model.Cerveja;
import br.com.geladaonline.util.Hash;

@XmlRootElement
public class Cervejas{

	private List<Cerveja> cervejas = new ArrayList<>();

	public Cervejas() {
	}

	public Cervejas(List<Cerveja> cervejas) {
		this.cervejas = cervejas;
	}

	@XmlTransient
	public List<Cerveja> getCervejas() {
		return cervejas;
	}

	public void setCervejas(List<Cerveja> cervejas) {
		this.cervejas = cervejas;
	}
	
	
	@XmlElement(name="link")
	public List<Link> getLinks() {
		List<Link> links = new ArrayList<>();
		for (Cerveja cerveja : getCervejas()) {
			
			Link link = Link.fromPath("cervejas/{nome}")
					.rel("cerveja")
					.title(cerveja.getNome())
					.build(cerveja.getNome());
			
			
			links.add(link);
		}
		return links;
	}
	
	public void setLinks (List<Link> links) {
		
	}

}
