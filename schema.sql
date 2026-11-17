-- schema.sql
CREATE TABLE praticantes (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    faixa VARCHAR(30) NOT NULL,
    graus INT DEFAULT 0,
    tipo VARCHAR(20) NOT NULL, -- Define se é 'ALUNO' ou 'INSTRUTOR'
    data_cadastro DATE DEFAULT CURRENT_DATE
);

INSERT INTO praticantes (nome, faixa, graus, tipo) VALUES 
('Davi Summer', 'Preta', 0, 'INSTRUTOR'),
('Lucas Silva', 'Branca', 2, 'ALUNO'),
('Mariana Costa', 'Azul', 1, 'ALUNO'),
('Pedro Santos', 'Roxa', 3, 'ALUNO'),
('Ana Beatriz', 'Branca', 0, 'ALUNO'),
('Rafael Oliveira', 'Marrom', 2, 'ALUNO'),
('Juliana Mendes', 'Azul', 4, 'ALUNO'),
('Carlos Eduardo', 'Roxa', 1, 'ALUNO'),
('Fernanda Lima', 'Branca', 3, 'ALUNO'),
('Thiago Almeida', 'Marrom', 0, 'ALUNO');

SELECT * FROM praticantes;