DROP TABLE IF EXISTS praticantes;

CREATE TABLE praticantes (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    faixa VARCHAR(30) NOT NULL,
    graus INT DEFAULT 0,
    tipo VARCHAR(20) NOT NULL, -- 'ALUNO' ou 'INSTRUTOR'
    mensalidade_em_dia BOOLEAN DEFAULT TRUE,
    salario NUMERIC(10, 2) DEFAULT 0.00,
    data_cadastro DATE DEFAULT CURRENT_DATE
);