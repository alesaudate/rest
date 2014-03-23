package br.com.geladaonline.modelo.pessoa;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;


@XmlSeeAlso({
    PessoaFisica.class
})
public abstract class Pessoa {

    private String nome;
    private List<Endereco> endereco;    
    private Long id;

    public String getNome() {
        return nome;
    }

    public void setNome(String value) {
        this.nome = value;
    }

    public List<Endereco> getEndereco() {
        return this.endereco;
    }
    
    public void setEndereco(List<Endereco> endereco) {
		this.endereco = endereco;
	}

    @XmlAttribute(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long value) {
        this.id = value;
    }

}
