package br.com.brejaonline.modelo.pessoa;

import java.util.Arrays;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class PessoaFisica
    extends Pessoa
{

    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String value) {
        this.cpf = value;
    }

    public static void main(String[] args) {
		PessoaFisica pessoaFisica = new PessoaFisica();
		pessoaFisica.setCpf("12345678909");
		pessoaFisica.setNome("Alexandre Saudate");
		
		Endereco endereco = new Endereco();
		endereco.setCep("12345-678");
		
		pessoaFisica.setEndereco(Arrays.asList(endereco));
		
		
		JAXB.marshal(pessoaFisica, System.out);
		
	}
    
}
