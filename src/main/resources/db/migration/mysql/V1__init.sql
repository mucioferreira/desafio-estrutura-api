CREATE TABLE Servidores (
	id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    ip VARCHAR(100) NOT NULL UNIQUE,
	tipo varchar(100) NOT NULL
);

CREATE TABLE Usuarios (
	id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE NosDaRede (
	id INT PRIMARY KEY AUTO_INCREMENT,
    servidor INT NOT NULL,
    proximo_no INT,
    ambiente VARCHAR(100) NOT NULL,
    descricao TEXT,
    CONSTRAINT fk_servidor FOREIGN KEY (servidor) REFERENCES Servidores(id),
    CONSTRAINT fk_proximo_no FOREIGN KEY (proximo_no) REFERENCES NosDaRede(id) 
);

CREATE TABLE UsuariosDaRede (
	id INT PRIMARY KEY AUTO_INCREMENT,
    servidor INT NOT NULL,
    usuario INT NOT NULL,
    descricao TEXT,
    CONSTRAINT fk_servidor_usuario FOREIGN KEY (servidor) REFERENCES Servidores(id),
    CONSTRAINT fk_usuario FOREIGN KEY (usuario) REFERENCES Usuarios(id) 
);

