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
	TIPO_DO_SERVIDOR_NAO_EXISTE("Tipo do servidor é inválido, tente novamente."),
	SERVIDOR_NAO_PERTENCE_A_REDE("Servidor não esta cadastrado em nenhuma rede."),
	USUARIO_NAO_PODE_SER_EXCLUIDO("Usuário não pode ser excluido."),
	SERVIDOR_NAO_PODE_SER_EXCLUIDO("Servidor não pode ser excluido."),
	USUARIO_JA_EXISTE("Usuário já cadastrado."),
	SERVIDOR_JA_EXISTE("Servidor com o mesmo IP já cadastrado."),
	NENHUM_ID_DO_SERVIDOR("Servidor não está cadastro do sistema."),
	NENHUM_ID_DO_USUARIO("Usuário não está cadastro do sistema.");
	
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
