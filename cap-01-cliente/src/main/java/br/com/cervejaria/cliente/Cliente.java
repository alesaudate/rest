package br.com.cervejaria.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Cliente {

	public static void main(String[] args) throws IOException {
		/*
		 * String caminho = "file:///home/alexandre/workspace/rest/" +
		 * "cap-01/src/main/resources/clientes.xml";
		 * 
		 * URL url = new URL(caminho);
		 */
		URL url = new URL("http://localhost:8080/cervejaria/clientes");
		InputStream inputStream = url.openConnection().getInputStream();
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));

		String line = null;

		while ((line = bufferedReader.readLine()) != null) {
			System.out.println(line);
		}

	}

}
