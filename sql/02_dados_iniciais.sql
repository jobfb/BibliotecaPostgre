



INSERT INTO editora (nome, cnpj, telefone, email) VALUES
('Companhia das Letras', '60.499.523/0001-00', '(11) 3707-3500', 'contato@companhiadasletras.com.br'),
('Editora Rocco', '33.635.965/0001-30', '(21) 3525-2000', 'rocco@rocco.com.br'),
('Editora Globo', '61.535.617/0001-90', '(11) 3767-4000', 'contato@globolivros.com.br');

INSERT INTO autor (nome, nacionalidade, data_nascimento) VALUES
('Machado de Assis', 'Brasileira', '1839-06-21'),
('Clarice Lispector', 'Brasileira', '1920-12-10'),
('George Orwell', 'Britânica', '1903-06-25'),
('J.K. Rowling', 'Britânica', '1965-07-31'),
('Yuval Noah Harari', 'Israelense', '1976-02-24');

INSERT INTO genero (nome) VALUES
('Romance'), ('Ficcao Cientifica'), ('Fantasia'), ('Nao-Ficcao'), ('Conto');

INSERT INTO livro (id_editora, id_autor, id_genero, titulo, ano_publicacao, num_paginas, idioma) VALUES
(1, 1, 1, 'Dom Casmurro', 1899, 256, 'Português'),
(2, 2, 5, 'Lacos de Familia', 1960, 160, 'Português'),
(3, 3, 2, '1984', 1949, 328, 'Inglês'),
(1, 4, 3, 'Harry Potter e a Pedra Filosofal', 1997, 223, 'Inglês'),
(1, 5, 4, 'Sapiens: Uma Breve Historia da Humanidade', 2011, 464, 'Inglês');

INSERT INTO exemplar (id_livro, numero_tombo, conservacao, localizacao, situacao) VALUES
(1, 'TB-0001', 'Bom', 'Estante A1', 'Disponivel'),
(1, 'TB-0002', 'Novo', 'Estante A1', 'Disponivel'),
(2, 'TB-0003', 'Bom', 'Estante A2', 'Disponivel'),
(3, 'TB-0004', 'Regular', 'Estante B1', 'Disponivel'),
(3, 'TB-0005', 'Bom', 'Estante B1', 'Disponivel'),
(4, 'TB-0006', 'Novo', 'Estante C1', 'Disponivel'),
(5, 'TB-0007', 'Bom', 'Estante D1', 'Disponivel');

INSERT INTO usuario (nome, cpf, telefone, email, data_cadastro) VALUES
('Ana Beatriz Souza', '111.111.111-11', '(47) 99911-1111', 'ana.souza@email.com', CURRENT_DATE - 200),
('Carlos Eduardo Lima', '222.222.222-22', '(47) 99922-2222', 'carlos.lima@email.com', CURRENT_DATE - 150),
('Fernanda Oliveira', '333.333.333-33', '(47) 99933-3333', 'fernanda.oliveira@email.com', CURRENT_DATE - 90);

INSERT INTO funcionario (nome, cpf, cargo, email, data_cont) VALUES
('Juliana Martins', '444.444.444-44', 'Bibliotecaria', 'juliana.martins@biblioteca.com', CURRENT_DATE - 400),
('Roberto Almeida', '555.555.555-55', 'Auxiliar de Biblioteca', 'roberto.almeida@biblioteca.com', CURRENT_DATE - 220);

-- Empréstimos de exemplo: um em andamento, um devolvido no prazo, um em atraso
INSERT INTO exemplar (id_livro, numero_tombo, conservacao, localizacao, situacao) VALUES
(2, 'TB-0008', 'Bom', 'Estante A2', 'Emprestado');

INSERT INTO emprestimo (id_exemplar, id_usuario, id_funcionario, data_emp, data_dev, data_devolucao_efetiva, situacao) VALUES
(8, 1, 1, CURRENT_DATE - 5, CURRENT_DATE + 9, NULL, 'Em andamento');

UPDATE exemplar SET situacao = 'Emprestado' WHERE id_exemplar = 6;
INSERT INTO emprestimo (id_exemplar, id_usuario, id_funcionario, data_emp, data_dev, data_devolucao_efetiva, situacao) VALUES
(6, 2, 2, CURRENT_DATE - 20, CURRENT_DATE - 6, NULL, 'Atrasado');

INSERT INTO emprestimo (id_exemplar, id_usuario, id_funcionario, data_emp, data_dev, data_devolucao_efetiva, situacao) VALUES
(4, 3, 1, CURRENT_DATE - 30, CURRENT_DATE - 16, CURRENT_DATE - 18, 'Devolvido');
