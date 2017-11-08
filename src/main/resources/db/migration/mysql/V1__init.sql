CREATE TABLE tb_servidor (
	idt_servidor INT PRIMARY KEY AUTO_INCREMENT,
    nme_servidor VARCHAR(100) NOT NULL,
    ip_servidor VARCHAR(100) NOT NULL UNIQUE,
	tpo_servidor varchar(100) NOT NULL
);

CREATE TABLE tb_usuario (
	idt_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nme_usuario VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE tb_no_da_rede (
	idt_no_da_rede INT AUTO_INCREMENT,
    cod_servidor INT NOT NULL,
    cod_proximo_no INT,
    amb_no_da_rede VARCHAR(100) NOT NULL,
    desc_no_da_rede TEXT,
    PRIMARY KEY (idt_no_da_rede, cod_servidor),
    CONSTRAINT fk_servidor FOREIGN KEY (cod_servidor) REFERENCES tb_servidor(idt_servidor),
    CONSTRAINT fk_proximo_no FOREIGN KEY (cod_proximo_no) REFERENCES tb_no_da_rede(idt_no_da_rede) 
);

CREATE TABLE tb_usuario_da_rede (
	idt_usuario_da_rede INT AUTO_INCREMENT,
    cod_no_da_rede INT NOT NULL,
    cod_usuario INT NOT NULL,
    desc_usuario_da_rede TEXT,
    PRIMARY KEY (idt_usuario_da_rede, cod_usuario),
    CONSTRAINT fk_no_da_rede FOREIGN KEY (cod_no_da_rede) REFERENCES tb_no_da_rede(idt_no_da_rede),
    CONSTRAINT fk_usuario FOREIGN KEY (cod_usuario) REFERENCES tb_usuario(idt_usuario) 
);

INSERT INTO tb_servidor (nme_servidor, ip_servidor, tpo_servidor)
VALUES
	("Servidor 1", "225.336.232.12", "COMPONENTE"),
	("Servidor 2", "123.54.87.6", "SERVIDOR_DE_APLICACAO"),
	("Servidor 3", "45.68.22.1", "PROXY"),
	("Servidor 4", "465.824.102.34", "COMPONENTE"),
	("Servidor 5", "54.213.88.67", "SERVIDOR_DE_BANCO");

INSERT INTO tb_usuario (nme_usuario) 
VALUES
	("Desenvolvedor PHP"), ("Desenvolvedor C#"), ("Desenvolvedor DevOps");


