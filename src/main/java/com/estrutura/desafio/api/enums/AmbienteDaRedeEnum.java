package com.estrutura.desafio.api.enums;

import java.util.HashMap;
import java.util.Map;

public enum AmbienteDaRedeEnum {
	DESENVOLVIMENTO,
	HOMOLOGACAO,
	LOCAL,
	PRODUCAO,
	NENHUM;
	
	private static Map<String, AmbienteDaRedeEnum> nomesAmbienteDaRede = new HashMap<String, AmbienteDaRedeEnum>(5);
	
    static {
    	nomesAmbienteDaRede.put("Desenvolvimento", DESENVOLVIMENTO);
    	nomesAmbienteDaRede.put("Homologação", HOMOLOGACAO);
    	nomesAmbienteDaRede.put("Local", LOCAL);
    	nomesAmbienteDaRede.put("Produção", PRODUCAO);
    	nomesAmbienteDaRede.put("Nenhum", NENHUM);
    }

	public static Map<String, AmbienteDaRedeEnum> getNomesAmbienteDaRede() {
		return nomesAmbienteDaRede;
	}
	
	public static String getNomeTipoServidor(AmbienteDaRedeEnum value) {
		for(Map.Entry<String, AmbienteDaRedeEnum> entry : nomesAmbienteDaRede.entrySet())
			if(value.equals(entry.getValue())) return entry.getKey();
		return null;
	}
}
