package br.com.geladaonline.parsers;

import java.util.ArrayList;
import java.util.List;

import br.com.geladaonline.modelo.pessoa.Endereco;
import br.com.geladaonline.modelo.pessoa.PessoaFisica;

public abstract class Parser {
	
	public static PessoaFisica criarPessoaFisicaTeste() {
		PessoaFisica pessoaFisica = new PessoaFisica();
		pessoaFisica.setCpf("123.456.789-09");
		pessoaFisica.setId(1L);
		pessoaFisica.setNome("Alexandre");
		
		Endereco endereco = new Endereco();
		endereco.setCep("12345-678");
		endereco.setLogradouro("Rua Um");
		
		List<Endereco> enderecos = new ArrayList<>();
		enderecos.add(endereco);
		
		pessoaFisica.setEndereco(enderecos);
		return pessoaFisica;
	}

}
