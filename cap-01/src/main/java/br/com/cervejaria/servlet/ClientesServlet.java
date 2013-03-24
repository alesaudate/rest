package br.com.cervejaria.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/clientes", loadOnStartup = 1)
public class ClientesServlet extends HttpServlet {

	private String clientes;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.getWriter().print(clientes);
	}

	@Override
	public void init() throws ServletException {
		try {
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(
							ClientesServlet.class
									.getResourceAsStream("/clientes.xml")));

			String line = null;
			StringBuilder builder = new StringBuilder();
			while ((line = bufferedReader.readLine()) != null) {
				builder.append(line);
				builder.append("\n");
			}
			clientes = builder.toString();
		} catch (IOException e) {
			throw new ServletException(e);
		}

	}

}
