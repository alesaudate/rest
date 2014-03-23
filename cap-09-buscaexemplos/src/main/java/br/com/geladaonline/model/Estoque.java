package br.com.geladaonline.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import br.com.geladaonline.model.Cerveja.Tipo;

public class Estoque {

	private Map<String, Cerveja> cervejas = new HashMap<>();

	public Estoque() {
		Cerveja primeiraCerveja = new Cerveja("Stella Artois",
				"A cerveja belga mais francesa do mundo :)", "Artois",
				Cerveja.Tipo.LAGER);
		Cerveja segundaCerveja = new Cerveja("Erdinger Weissbier",
				"Cerveja de trigo alemã", "Erdinger Weissbräu",
				Cerveja.Tipo.WEIZEN);
		this.cervejas.put("Stella Artois", primeiraCerveja);
		this.cervejas.put("Erdinger Weissbier", segundaCerveja);
	}

	public List<Cerveja> listarCervejas() {
		return new ArrayList<>(this.cervejas.values());
	}

	public List<Cerveja> listarCervejas(int numeroPagina, int tamanhoPagina) {
		return filtrarPaginacao(numeroPagina, tamanhoPagina, listarCervejas());
	}
	
	private List<Cerveja> filtrarPaginacao(int numeroPagina, int tamanhoPagina, List<Cerveja> cervejas) {
		
		int indiceInicial = numeroPagina * tamanhoPagina;
		int indiceFinal = indiceInicial + tamanhoPagina;		
		
		if (cervejas.size() > indiceInicial) {
			if (cervejas.size() > indiceFinal) {
				cervejas = cervejas.subList(indiceInicial, indiceFinal);
			} else {
				cervejas = cervejas.subList(indiceInicial, cervejas.size());
			}
		} else {
			cervejas = new ArrayList<>();
		}
		return cervejas;
		
	}
	
	public List<Cerveja> listarCervejasPorExemplos(int numeroPagina, int tamanhoPagina, MultivaluedMap<String, String> exemplos) {
		
		List<Cerveja> resultados = new ArrayList<>(tamanhoPagina);
		
		Cerveja exemplo = Cerveja.builder()
			.withNome(exemplos.getFirst("nome"))
			.withCervejaria(exemplos.getFirst("cervejaria"))
			.withDescricao(exemplos.getFirst("descricao"))
			.withTipo(exemplos.getFirst("tipo"))
			.build();
		
		for (Cerveja cerveja : listarCervejas()) {
			if (exemplo.matchExemplo(cerveja)) {
				resultados.add(cerveja);
			}
		}
		
		return filtrarPaginacao(numeroPagina, tamanhoPagina, resultados);
		
	}

	public void adicionarCerveja(Cerveja cerveja) {
		if (this.cervejas.containsKey(cerveja.getNome())) {
			throw new CervejaJaExisteException();
		}
		this.cervejas.put(cerveja.getNome(), cerveja);
	}

	public Cerveja recuperarCervejaPeloNome(String nome) {
		return this.cervejas.get(nome);
	}

	public void apagarCerveja(String nome) {
		this.cervejas.remove(nome);
	}

	public void atualizarCerveja(Cerveja cerveja) {
		if (this.cervejas.containsKey(cerveja.getNome())) {
			throw new CervejaNaoEncontradaException();
		}
		cervejas.put(cerveja.getNome(), cerveja);
	}

}
