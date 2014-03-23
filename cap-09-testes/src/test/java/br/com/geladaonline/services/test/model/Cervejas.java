package br.com.geladaonline.services.test.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Cervejas {

	@XmlElement(name = "link")
	private List<CustomLink> links;

	public List<Link> getLinks() {
		List<Link> links = new ArrayList<>();

		for (CustomLink customLink : this.links) {

			Link newLink = 
					 // É necessário inserir o host, pois os links retornados pelo servidor 
			         // são relativos, i.e., não têm essa informação
					Link.fromUri(Constants.CAMINHO_COMPLETO + customLink.getHref())
					.rel(customLink.getRel())
					.title(customLink.getTitle())
					.build();

			links.add(newLink);
		}

		return links;
	}

}
