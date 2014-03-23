package br.com.geladaonline.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Cervejas {

	
	private List<CustomLink> links;

	@XmlElement(name = "link")
	public List<CustomLink> getLinks() {
		return links;
	}

	public void setLinks(List<CustomLink> links) {
		this.links = links;
	}

}
