package br.com.geladaonline.parsers;

import br.com.geladaonline.modelo.pessoa.PessoaFisica;

import com.google.gson.Gson;

public class GSONParser {

	public static void main(String[] args) {
		
		PessoaFisica pessoaFisica = Parser.criarPessoaFisicaTeste();
		Gson gson = new Gson();
		System.out.println(gson.toJson(pessoaFisica));

	}

}
