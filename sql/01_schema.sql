
DROP TABLE IF EXISTS emprestimo CASCADE;
DROP TABLE IF EXISTS exemplar CASCADE;
DROP TABLE IF EXISTS livro CASCADE;
DROP TABLE IF EXISTS funcionario CASCADE;
DROP TABLE IF EXISTS usuario CASCADE;
DROP TABLE IF EXISTS genero CASCADE;
DROP TABLE IF EXISTS autor CASCADE;
DROP TABLE IF EXISTS editora CASCADE;


CREATE TABLE editora (
    id_editora   SERIAL PRIMARY KEY,
    nome         VARCHAR(120) NOT NULL,
    cnpj         VARCHAR(18),
    telefone     VARCHAR(20),
    email        VARCHAR(120)
);

CREATE TABLE autor (
    id_autor        SERIAL PRIMARY KEY,
    nome            VARCHAR(120) NOT NULL,
    nacionalidade   VARCHAR(60),
    data_nascimento DATE
);

CREATE TABLE genero (
    id_genero SERIAL PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL
);

CREATE TABLE usuario (
    id_usuario     SERIAL PRIMARY KEY,
    nome           VARCHAR(120) NOT NULL,
    cpf            VARCHAR(14) NOT NULL UNIQUE,
    telefone       VARCHAR(20),
    email          VARCHAR(120),
    data_cadastro  DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE funcionario (
    id_funcionario SERIAL PRIMARY KEY,
    nome           VARCHAR(120) NOT NULL,
    cpf            VARCHAR(14) NOT NULL UNIQUE,
    cargo          VARCHAR(60) NOT NULL,
    email          VARCHAR(120),
    data_cont      DATE
);

CREATE TABLE livro (
    id_livro        SERIAL PRIMARY KEY,
    id_editora      INT NOT NULL REFERENCES editora(id_editora),
    id_autor        INT NOT NULL REFERENCES autor(id_autor),
    id_genero       INT NOT NULL REFERENCES genero(id_genero),
    titulo          VARCHAR(200) NOT NULL,
    ano_publicacao  INT,
    num_paginas     INT,
    idioma          VARCHAR(40)
);

CREATE TABLE exemplar (
    id_exemplar   SERIAL PRIMARY KEY,
    id_livro      INT NOT NULL REFERENCES livro(id_livro),
    numero_tombo  VARCHAR(30) NOT NULL UNIQUE,
    conservacao   VARCHAR(20) NOT NULL,
    localizacao   VARCHAR(60),
    situacao      VARCHAR(20) NOT NULL DEFAULT 'Disponivel'
        CHECK (situacao IN ('Disponivel', 'Emprestado', 'Em manutencao', 'Baixado'))
);



CREATE TABLE emprestimo (
    id_emprestimo            SERIAL PRIMARY KEY,
    id_exemplar              INT NOT NULL REFERENCES exemplar(id_exemplar),
    id_usuario               INT NOT NULL REFERENCES usuario(id_usuario),
    id_funcionario           INT NOT NULL REFERENCES funcionario(id_funcionario),
    data_emp                 DATE NOT NULL DEFAULT CURRENT_DATE,
    data_dev                 DATE NOT NULL,
    data_devolucao_efetiva   DATE,
    situacao                 VARCHAR(20) NOT NULL DEFAULT 'Em andamento'
        CHECK (situacao IN ('Em andamento', 'Devolvido', 'Atrasado'))
);

CREATE INDEX idx_livro_titulo ON livro(titulo);
CREATE INDEX idx_exemplar_situacao ON exemplar(situacao);
CREATE INDEX idx_emprestimo_situacao ON emprestimo(situacao);
