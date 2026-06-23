package br.com.joaowalter.academia.dto;

import java.util.List;

public class RelatorioTurmaDTO {

    private String nome;
    private String modalidade;
    private String diaSemana;
    private String horario;
    private String professor;
    private List<RelatorioAlunoDTO> alunos;

    public RelatorioTurmaDTO(String nome,
                             String modalidade,
                             String diaSemana,
                             String horario,
                             String professor,
                             List<RelatorioAlunoDTO> alunos) {
        this.nome = nome;
        this.modalidade = modalidade;
        this.diaSemana = diaSemana;
        this.horario = horario;
        this.professor = professor;
        this.alunos = alunos;
    }

    public String getNome() {
        return nome;
    }

    public String getModalidade() {
        return modalidade;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public String getHorario() {
        return horario;
    }

    public String getProfessor() {
        return professor;
    }

    public List<RelatorioAlunoDTO> getAlunos() {
        return alunos;
    }
}