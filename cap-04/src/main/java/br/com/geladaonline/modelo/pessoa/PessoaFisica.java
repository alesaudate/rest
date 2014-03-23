package br.com.geladaonline.modelo.pessoa;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;



@XmlRootElement
public class PessoaFisica
    extends Pessoa
{

    private String cpf;
    
    private String dadoTransiente = "dadoTransiente";

    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String value) {
        this.cpf = value;
    }
    
    @XmlTransient
    public String getDadoTransiente() {
		return dadoTransiente;
	}
    
    public void setDadoTransiente(String dadoTransiente) {
		this.dadoTransiente = dadoTransiente;
	}    
}
