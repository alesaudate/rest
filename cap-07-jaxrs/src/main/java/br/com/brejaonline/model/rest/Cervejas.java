package br.com.brejaonline.model.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//import br.com.brejaonline.client.Constants;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Cervejas {

/*	@XmlElement(name = "link")
	private List<CustomLink> links;

	public List<Link> getLinks() {
		List<Link> links = new ArrayList<>();

		for (CustomLink link : this.links) {

			//Link newLink = Link.fromUri(Constants.HOST +link.getHref())
			//		.rel(link.getRel()).title(link.getTitle()).build();

			//links.add(newLink);
		}

		return links;
	}*/

}
