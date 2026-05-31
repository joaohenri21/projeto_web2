CREATE TABLE professores (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE alunos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE
);

CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    login VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    papel VARCHAR(20) NOT NULL
);

CREATE TABLE turmas (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    modalidade VARCHAR(50) NOT NULL,
    dia_semana VARCHAR(30) NOT NULL,
    horario TIME NOT NULL,
    professor_id BIGINT NOT NULL,
    CONSTRAINT fk_turma_professor
        FOREIGN KEY (professor_id)
        REFERENCES professores(id)
);

CREATE TABLE matriculas (
    id BIGSERIAL PRIMARY KEY,
    data_matricula DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    aluno_id BIGINT NOT NULL,
    turma_id BIGINT NOT NULL,
    CONSTRAINT fk_matricula_aluno
        FOREIGN KEY (aluno_id)
        REFERENCES alunos(id),
    CONSTRAINT fk_matricula_turma
        FOREIGN KEY (turma_id)
        REFERENCES turmas(id)
);

CREATE TABLE presencas (
    id BIGSERIAL PRIMARY KEY,
    data_hora_aula TIMESTAMP NOT NULL,
    presente BOOLEAN NOT NULL,
    matricula_id BIGINT NOT NULL,
    CONSTRAINT fk_presenca_matricula
        FOREIGN KEY (matricula_id)
        REFERENCES matriculas(id)
);