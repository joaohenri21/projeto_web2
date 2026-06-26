package br.com.joaowalter.academia.dto;

public class RelatorioPresencaDTO {

    private String dataHora;
    private String aluno;
    private String situacao;

    public RelatorioPresencaDTO(String dataHora, String aluno, String situacao) {
        this.dataHora = dataHora;
        this.aluno = aluno;
        this.situacao = situacao;
    }

    public String getDataHora() {
        return dataHora;
    }

    public String getAluno() {
        return aluno;
    }

    public String getSituacao() {
        return situacao;
    }
}