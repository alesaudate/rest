package br.com.geladaonline.parsers;

import br.com.geladaonline.modelo.pessoa.PessoaFisica;

import com.thoughtworks.xstream.MarshallingStrategy;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.ReferenceByIdMarshallingStrategy;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class XStreamParser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PessoaFisica pessoaFisica = Parser.criarPessoaFisicaTeste();
		
		JettisonMappedXmlDriver jettisonMappedXmlDriver = new JettisonMappedXmlDriver();
		
		
		XStream xStream = new XStream(new JettisonMappedXmlDriver());
		
		
		System.out.println(xStream.toXML(pessoaFisica));

	}

}
