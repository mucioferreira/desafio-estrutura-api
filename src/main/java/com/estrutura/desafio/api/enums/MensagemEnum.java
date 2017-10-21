package com.estrutura.desafio.api.enums;

public enum MensagemEnum {
	NOME_VAZIO("Nome não pode ser vazio."),
	NOME_TAMANHO_INVALIDO("Nome deve conter entre 3 e 100 caracteres."),
	
	IP_VAZIO("IP não pode ser vazio."),
	
	SERVIDOR_NAO_ENCONTRADO("Servidor não encontrado."),
	USUARIO_NAO_ENCONTRADO("Usuário não encontrado.");
	
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
