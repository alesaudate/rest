package br.com.geladaonline.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		int indiceInicial = numeroPagina * tamanhoPagina;
		int indiceFinal = indiceInicial + tamanhoPagina;

		List<Cerveja> cervejas = listarCervejas();
		
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
