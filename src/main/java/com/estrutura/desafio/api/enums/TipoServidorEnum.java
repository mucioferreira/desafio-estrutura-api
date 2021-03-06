package com.estrutura.desafio.api.enums;

import java.util.HashMap;
import java.util.Map;

public enum TipoServidorEnum {
	COMPONENTE,
	SERVIDOR_DE_APLICACAO,
	SERVIDOR_DE_BANCO,
	PROXY;
	
	private static Map<String, TipoServidorEnum> nomesTipoServidor = new HashMap<String, TipoServidorEnum>(4);
	
    static {
    	nomesTipoServidor.put("Componente", COMPONENTE);
        nomesTipoServidor.put("Aplicação", SERVIDOR_DE_APLICACAO);
        nomesTipoServidor.put("Banco de Dados", SERVIDOR_DE_BANCO);
        nomesTipoServidor.put("Proxy", PROXY);
    }

	public static Map<String, TipoServidorEnum> getNomesTipoServidor() {
		return nomesTipoServidor;
	}
	
	public static String getNomeTipoServidor(TipoServidorEnum value) {
		for(Map.Entry<String, TipoServidorEnum> entry : nomesTipoServidor.entrySet())
			if(value.equals(entry.getValue())) return entry.getKey();
		return null;
	}
    
}
