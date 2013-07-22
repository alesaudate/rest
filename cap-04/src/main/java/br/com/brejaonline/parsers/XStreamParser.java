package br.com.brejaonline.parsers;

import br.com.brejaonline.modelo.pessoa.PessoaFisica;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class XStreamParser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PessoaFisica pessoaFisica = Parser.criarPessoaFisicaTeste();
		
		XStream xStream = new XStream(new JettisonMappedXmlDriver());
		
		System.out.println(xStream.toXML(pessoaFisica));

	}

}
