CREATE TABLE pessoa (
	codigo BIGINT (20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR (200) NOT NULL,
	logradouro VARCHAR (200), 
	numero VARCHAR (200),
	complemento VARCHAR (200),
	bairro VARCHAR (200), 
	cep VARCHAR (200),
	cidade VARCHAR (200), 
	estado VARCHAR (200),
	ativo BIT(1) NOT NULL 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pessoa (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo)
VALUES ('Gustavo', 'Av. Goiás', 'SN', 'Q.10 L.28', 'Humaita', '74595410', 'Goiânia', 'Goiás', true);

INSERT INTO pessoa (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo)
VALUES ('João', 'Av. Inhumas', 'SN', 'Q.1 L.4', 'Teste', '74456754', 'Goiânia', 'Goiás', true);

INSERT INTO pessoa (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo)
VALUES ('Maria', 'Av. Goiás', 'SN', 'Q.2 L.35', 'Humaita', '74546386', 'Goiânia', 'Goiás', true);
