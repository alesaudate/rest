package br.com.geladaonline.model;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Estoque {

	   private Map<String, Cerveja> cervejas = new HashMap<>();

	   
	   public Estoque() {
		      Cerveja primeiraCerveja = new Cerveja("Stella Artois", 
		            "A cerveja belga mais francesa do mundo :)", 
		            "Artois", 
		            Cerveja.Tipo.LAGER);
		      Cerveja segundaCerveja = new Cerveja("Erdinger Weissbier",
		            "Cerveja de trigo alemã",
		            "Erdinger Weissbräu",
		            Cerveja.Tipo.WEIZEN);
		      this.cervejas.put("Stella Artois", primeiraCerveja);
		      this.cervejas.put("Erdinger Weissbier", segundaCerveja);
		   }

	   
	   public Collection<Cerveja> listarCervejas() {
	      return new ArrayList<>(this.cervejas.values());
	   }

	   public void adicionarCerveja (Cerveja cerveja) {
	      this.cervejas.put(cerveja.getNome(), cerveja);
	   }
	   
	   public Cerveja recuperarCervejaPeloNome (String nome) {
		   return this.cervejas.get(nome);
	   }

	
}
