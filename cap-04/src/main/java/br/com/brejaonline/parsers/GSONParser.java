package br.com.brejaonline.parsers;

import br.com.brejaonline.modelo.pessoa.PessoaFisica;

import com.google.gson.Gson;

public class GSONParser {

	public static void main(String[] args) {
		
		PessoaFisica pessoaFisica = Parser.criarPessoaFisicaTeste();
		Gson gson = new Gson();
		System.out.println(gson.toJson(pessoaFisica));

	}

}
