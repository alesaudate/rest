package br.com.geladaonline.services;

public enum AlgoritmoValidacao {
	
	
	MODULO_11 {
		@Override
		public boolean validar(String cpf) {
			return cpf.equals("12345678909");
		}
		@Override
		public String getNomeAlgoritmo() {
			return "Módulo 11";
		}
	}, RECEITA {
		@Override
		public boolean validar(String cpf) {
			return cpf.equals("12345678909");
		}
		
		@Override
		public String getNomeAlgoritmo() {
			return "Checagem na Receita Federal";
		}
	}
	
	, TODOS {
		@Override
		public boolean validar(String cpf) {
			
			return MODULO_11.validar(cpf) && RECEITA.validar(cpf);
		}

		@Override
		public String getNomeAlgoritmo() {
			return MODULO_11.getNomeAlgoritmo() + " e " + RECEITA.getNomeAlgoritmo();
		}
	};
	
	public abstract boolean validar(String cpf);
	
	public abstract String getNomeAlgoritmo();

}
