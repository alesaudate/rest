package br.com.geladaonline.servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;

import br.com.geladaonline.model.Cerveja;
import br.com.geladaonline.model.Estoque;
import br.com.geladaonline.model.rest.Cervejas;

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
			
			if (identificador != null && estoque.recuperarCervejaPeloNome(identificador) != null) {
				resp.sendError(409, "Já existe uma cerveja com esse nome");
				
				return ; 
			}
			
			String tipoDeConteudo = req.getContentType();
			
			
			
			if (tipoDeConteudo.startsWith("text/xml") || tipoDeConteudo.startsWith("application/xml")) {
				
					Unmarshaller unmarshaller = context.createUnmarshaller();
					Cerveja cerveja = (Cerveja)unmarshaller.unmarshal(req.getInputStream());
					cerveja.setNome(identificador);
					estoque.adicionarCerveja(cerveja);
					String requestURI = req.getRequestURI();
					resp.setHeader("Location", requestURI);
					resp.setStatus(201);
					escreveXML(req, resp);
				}
			else if (tipoDeConteudo.startsWith("application/json")){
					

					List<String> lines = IOUtils.readLines(req.getInputStream());
					StringBuilder builder = new StringBuilder();
					for (String line : lines) {
						builder.append(line);
					}
					
					MappedNamespaceConvention con = new MappedNamespaceConvention();
					JSONObject jsonObject = new JSONObject(builder.toString());
					
					XMLStreamReader xmlStreamReader = new MappedXMLStreamReader(jsonObject, con);

					Unmarshaller unmarshaller = context.createUnmarshaller();
					Cerveja cerveja = (Cerveja)unmarshaller.unmarshal(xmlStreamReader);
					
					cerveja.setNome(identificador);
					estoque.adicionarCerveja(cerveja);
					String requestURI = req.getRequestURI();
					resp.setHeader("Location", requestURI);
					resp.setStatus(201);
					
					escreveJSON(req, resp);
					
				}
			else {
					resp.sendError(415);
			}
			
		}
		catch (Exception e ) {
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
		
		Object objetoAEscrever = localizaObjetoASerEnviado(req);
		
		if (objetoAEscrever == null) {
			resp.sendError(404);
			return ;
		}
		
		try {
			resp.setContentType("application/xml;charset=UTF-8");
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(objetoAEscrever, resp.getWriter());

		} catch (JAXBException e) {
			resp.sendError(500);
		}

	}

	private Object localizaObjetoASerEnviado(HttpServletRequest req) {
		Object objeto = null;
		
		try {
			String identificador = obtemIdentificador(req);
			objeto = estoque.recuperarCervejaPeloNome(identificador);
		}
		catch (RecursoSemIdentificadorException e) {
			Cervejas cervejas = new Cervejas();
			cervejas.setCervejas(new ArrayList<>(estoque.listarCervejas()));
			objeto = cervejas;
		}
		return objeto;
	}

	private void escreveJSON(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Object objetoAEscrever = localizaObjetoASerEnviado(req);
		
		if (objetoAEscrever == null) {
			resp.sendError(404);
			return ;
		}
		
		try {
			resp.setContentType("application/json;charset=UTF-8");
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
