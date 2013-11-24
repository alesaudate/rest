package br.com.brejaonline.model.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.Link.JaxbLink;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.com.brejaonline.model.Cerveja;
import br.com.brejaonline.services.CervejaService;

@XmlRootElement
public class Cervejas {

	private List<Cerveja> cervejas = new ArrayList<>();

	public Cervejas() {
	}

	public Cervejas(List<Cerveja> cervejas) {
		this.cervejas = cervejas;
	}

	@XmlElement(name="cerveja")
	public List<Cerveja> getCervejas() {
		return cervejas;
	}

	public void setCervejas(List<Cerveja> cervejas) {
		this.cervejas = cervejas;
	}
	
	
	//@XmlElement(name="link")
	@XmlTransient
	public List<Link.JaxbLink> getLinks() {
		List<Link.JaxbLink> links = new ArrayList<>();
		for (Cerveja cerveja : getCervejas()) {
			JaxbLink link = new JaxbLink(Link
					.fromPath("/cervejas/{nome}")
					.build(cerveja.getNome()).getUri());
			links.add(link);
		}
		return links;
	}
	
	public void setLinks (List<Link.JaxbLink> links) {
		
	}

}
