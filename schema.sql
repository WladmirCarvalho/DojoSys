-- schema.sql
CREATE DATABASE dojosys;

\c dojosys;

CREATE TABLE praticantes (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    faixa VARCHAR(30) NOT NULL,
    graus INT DEFAULT 0,
    tipo VARCHAR(20) NOT NULL, -- Define se é 'ALUNO' ou 'INSTRUTOR'
    data_cadastro DATE DEFAULT CURRENT_DATE
);
