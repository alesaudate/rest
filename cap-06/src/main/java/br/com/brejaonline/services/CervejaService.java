package br.com.brejaonline.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;

import br.com.brejaonline.model.Cerveja;
import br.com.brejaonline.model.Estoque;
import br.com.brejaonline.model.rest.Cervejas;

@Path("/cervejas")
@Consumes({ MediaType.TEXT_XML, MediaType.APPLICATION_XML,
		MediaType.APPLICATION_JSON })
@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_XML,
		MediaType.APPLICATION_JSON })
public class CervejaService {

	private static Estoque estoque = new Estoque();
	
	private static final int TAMANHO_PAGINA = 1;

	@GET
	@Path("{nome}")
	public Cerveja encontreCerveja(@PathParam("nome") String nomeDaCerveja) {
		Cerveja cerveja = estoque.recuperarCervejaPeloNome(nomeDaCerveja);
		if (cerveja != null)
			return cerveja;

		throw new WebApplicationException(Status.NOT_FOUND);

	}

	@GET
	public Cervejas listeTodasAsCervejas(
			@QueryParam("pagina") @DefaultValue("0") Integer pagina) {

		List<Cerveja> cervejas = estoque.listarCervejas();
		
		int indiceInicial = pagina * TAMANHO_PAGINA;
		int indiceFinal = indiceInicial + TAMANHO_PAGINA;
		
		if (cervejas.size() >  indiceInicial) {
			if (cervejas.size() >  indiceFinal) {
				cervejas = cervejas.subList(indiceInicial, indiceFinal);
			}
			else {
				cervejas = cervejas.subList(indiceInicial, cervejas.size());
			}
		}
		else {
			cervejas = new ArrayList<>();
		}
		

		return new Cervejas(cervejas);
	}

	@POST
	public Response criarCerveja(Cerveja cerveja) {
		estoque.adicionarCerveja(cerveja);

		URI uri = UriBuilder.fromPath("cervejas/{nome}").build(
				cerveja.getNome());

		return Response.created(uri).entity(cerveja).build();
	}

	@PUT
	@Path("{nome}")
	public void atualizarCerveja(@PathParam("nome") String nome, Cerveja cerveja) {
		encontreCerveja(nome);
		cerveja.setNome(nome);
		estoque.atualizarCerveja(cerveja);
	}

	@DELETE
	@Path("{nome}")
	public void apagarCerveja(@PathParam("nome") String nome) {
		estoque.apagarCerveja(nome);
	}

}
