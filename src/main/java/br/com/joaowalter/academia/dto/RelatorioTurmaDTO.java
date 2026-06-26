package br.com.joaowalter.academia.dto;

import java.util.List;

public class RelatorioTurmaDTO {

    private String nome;
    private String modalidade;
    private String diaSemana;
    private String horario;
    private String professor;
    private List<RelatorioPresencaDTO> presencas;

    public RelatorioTurmaDTO(String nome,
                             String modalidade,
                             String diaSemana,
                             String horario,
                             String professor,
                             List<RelatorioPresencaDTO> presencas) {
        this.nome = nome;
        this.modalidade = modalidade;
        this.diaSemana = diaSemana;
        this.horario = horario;
        this.professor = professor;
        this.presencas = presencas;
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

    public List<RelatorioPresencaDTO> getPresencas() {
        return presencas;
    }
}