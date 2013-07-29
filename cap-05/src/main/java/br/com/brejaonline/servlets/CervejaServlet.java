package br.com.brejaonline.servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamWriter;

import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;

import br.com.brejaonline.model.Cerveja;
import br.com.brejaonline.model.Estoque;
import br.com.brejaonline.model.rest.Cervejas;

@WebServlet(value = "/cervejas/*")
public class CervejaServlet extends HttpServlet {

	private Estoque estoque = new Estoque();
	private static JAXBContext context;

	static {
		try {
			context = JAXBContext.newInstance(Cervejas.class);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	// Esse código é apenas para apresentar a escrita de XML
	/*
	 * @Override protected void doGet(HttpServletRequest req,
	 * HttpServletResponse resp) throws ServletException, IOException {
	 * PrintWriter out = resp.getWriter(); try {
	 * 
	 * Marshaller marshaller = context.createMarshaller();
	 * resp.setContentType("application/xml");
	 * 
	 * Cervejas cervejas = new Cervejas(); cervejas.setCervejas(new
	 * ArrayList<>(estoque.listarCervejas())); marshaller.marshal(cervejas,
	 * out);
	 * 
	 * 
	 * } catch (Exception e) { resp.sendError(500, e.getMessage()); }
	 * 
	 * }
	 */

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String acceptHeader = req.getHeader("Accept");
		if (acceptHeader == null || acceptHeader.contains("application/xml")) {
			escreveXML(req, resp);
		} else if (acceptHeader.contains("application/json")) {
			escreveJSON(req, resp);
		} else {
			// O header accept foi recebido com um valor não suportado
			resp.sendError(415); // Formato não suportado
		}

	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		try {
			String identificador = null;
			try {
				identificador = obtemIdentificador(req);
			} catch (RecursoSemIdentificadorException e) {
				resp.sendError(400, e.getMessage()); //Manda um erro 400 - Bad Request
			}
			
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Cerveja cerveja = (Cerveja)unmarshaller.unmarshal(req.getInputStream());
			cerveja.setNome(identificador);
			String requestURI = req.getRequestURI();
			resp.setHeader("Location", requestURI);
			resp.setStatus(201);
		}
		catch (JAXBException e ) {
			resp.sendError(500, e.getMessage());
		}
	}
	
	
	
	private String obtemIdentificador(HttpServletRequest req) throws RecursoSemIdentificadorException {
		String requestUri = req.getRequestURI();
		String[] pedacosDaUri = requestUri.split("/");
		
		boolean contextoCervejasEncontrado = false;
		for (String contexto : pedacosDaUri) {
			if (contexto.equals("cervejas")) {
				contextoCervejasEncontrado = true;
				continue;
			}
			if (contextoCervejasEncontrado) {
				try {
					return URLDecoder.decode(contexto, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					return URLDecoder.decode(contexto);
				}
			}
		}
		throw new RecursoSemIdentificadorException("Recurso sem identificador");
		
	}

	private void escreveXML(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Object objetoAEscrever = null;
		
		try {
			String identificador = obtemIdentificador(req);
			objetoAEscrever = estoque.recuperarCervejaPeloNome(identificador);
		}
		catch (RecursoSemIdentificadorException e) {
			Cervejas cervejas = new Cervejas();
			cervejas.setCervejas(new ArrayList<>(estoque.listarCervejas()));
			objetoAEscrever = cervejas;
		}
		
		if (objetoAEscrever == null) {
			resp.sendError(404);
			return ;
		}
		
		try {
			resp.setContentType("application/xml");
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(objetoAEscrever, resp.getWriter());

		} catch (JAXBException e) {
			resp.sendError(500);
		}

	}

	private void escreveJSON(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Object objetoAEscrever = null;
		
		try {
			String identificador = obtemIdentificador(req);
			objetoAEscrever = estoque.recuperarCervejaPeloNome(identificador);
		}
		catch (RecursoSemIdentificadorException e) {
			Cervejas cervejas = new Cervejas();
			cervejas.setCervejas(new ArrayList<>(estoque.listarCervejas()));
			objetoAEscrever = cervejas;
		}
		
		if (objetoAEscrever == null) {
			resp.sendError(404);
			return ;
		}
		
		try {
			resp.setContentType("application/json");
			MappedNamespaceConvention con = new MappedNamespaceConvention();

			XMLStreamWriter xmlStreamWriter = new MappedXMLStreamWriter(con,
					resp.getWriter());

			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(objetoAEscrever, xmlStreamWriter);
			
		} catch (JAXBException e) {
			resp.sendError(500);
		}

	}

}
