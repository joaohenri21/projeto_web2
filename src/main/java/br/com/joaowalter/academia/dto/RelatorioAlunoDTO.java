package br.com.joaowalter.academia.dto;

public class RelatorioAlunoDTO {

    private String nome;
    private String status;

    public RelatorioAlunoDTO(String nome, String status) {
        this.nome = nome;
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public String getStatus() {
        return status;
    }
}