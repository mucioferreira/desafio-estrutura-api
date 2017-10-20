CREATE DATABASE bd_desafio_ed;
USE bd_desafio_ed;

CREATE TABLE tb_servidor(
	idt_servidor INT PRIMARY KEY AUTO_INCREMENT,
    nme_servidor VARCHAR(255) NOT NULL,
    ip_servidor VARCHAR(100) NOT NULL,
	tipo_servidor varchar(255) NOT NULL
);

CREATE TABLE tb_usuario(
	idt_usuairo INT PRIMARY KEY AUTO_INCREMENT,
    nme_usuario VARCHAR(100) NOT NULL
);

CREATE TABLE ta_no_rede(
	idt_no_rede INT PRIMARY KEY AUTO_INCREMENT,
    cod_primeiro_servidor INT NOT NULL,
    cod_segundo_servidor INT NOT NULL,
    desc_no_rede TEXT,
    CONSTRAINT fk_primeiro_servidor FOREIGN KEY (cod_primeiro_servidor) REFERENCES tb_servidor(idt_servidor),
    CONSTRAINT fk_segundo_servidor FOREIGN KEY (cod_segundo_servidor) REFERENCES tb_servidor(idt_servidor) 
);

CREATE TABLE ta_usuario_rede (
	idt_usuario_rede INT PRIMARY KEY AUTO_INCREMENT,
    cod_servidor INT NOT NULL,
    cod_usuario INT NOT NULL,
    desc_rede TEXT,
    CONSTRAINT fk_servidor FOREIGN KEY (cod_servidor) REFERENCES tb_servidor(idt_servidor),
    CONSTRAINT fk_usuario FOREIGN KEY (cod_usuario) REFERENCES tb_usuario(idt_usuairo) 
);

