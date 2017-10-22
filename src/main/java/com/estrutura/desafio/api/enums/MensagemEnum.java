package com.estrutura.desafio.api.enums;

public enum MensagemEnum {
	NOME_VAZIO("Nome não pode ser vazio."),
	NOME_TAMANHO_INVALIDO("Nome deve conter entre 3 e 100 caracteres."),
	IP_VAZIO("IP não pode ser vazio."),
	SERVIDOR_NAO_ENCONTRADO("Servidor não encontrado."),
	PRIMEIRO_SERVIDOR_NAO_ENCONTRADO("Primeiro servidor não encontrado."),
	SEGUNDO_SERVIDOR_NAO_ENCONTRADO("Segundo servidor não encontrado."),
	USUARIO_NAO_ENCONTRADO("Usuário não encontrado."),
	USUARIO_DA_REDE_NAO_ENCONTRADO("Usuário da rede não encontrado."),
	NO_DA_REDE_NAO_ENCONTRADO("No da rede não encontrado."),
	USUARIO_NAO_PERTENCE_A_REDE("Usuário não esta cadastrado em nenhuma rede."),
	SERVIDOR_NAO_PERTENCE_A_REDE("Servidor não esta cadastrado em nenhuma rede.");
	
	private String mensagem;
	
	private MensagemEnum(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	
	@Override
	public String toString() {
		return this.getMensagem();
	}
	
}
