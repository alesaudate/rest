package br.com.geladaonline.model.twitter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class Statuses {

	private List<Status> statusCollection = new ArrayList<>();
	
	
	@XmlElement(name="status")
	public List<Status> getStatusCollection() {
		return statusCollection;
	}
	
	public void setStatusCollection(List<Status> statusCollection) {
		this.statusCollection = statusCollection;
	}
	
}
