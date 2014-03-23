package br.com.geladaonline.model.twitter;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @XmlElement(name = "name")
    private String name;

    public String getName() {
        return name;
    }
}
