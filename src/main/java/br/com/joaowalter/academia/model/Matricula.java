package br.com.joaowalter.academia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matriculas")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private LocalDate dataMatricula;

    @NotBlank
    @Column(nullable = false, length = 20)
    private String status;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    @OneToMany(mappedBy = "matricula")
    private List<Presenca> presencas = new ArrayList<>();

    public Matricula() {
    }

    public Matricula(Long id, LocalDate dataMatricula, String status) {
        this.id = id;
        this.dataMatricula = dataMatricula;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public String getStatus() {
        return status;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Turma getTurma() {
        return turma;
    }

    public List<Presenca> getPresencas() {
        return presencas;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public void setPresencas(List<Presenca> presencas) {
        this.presencas = presencas;
    }
}