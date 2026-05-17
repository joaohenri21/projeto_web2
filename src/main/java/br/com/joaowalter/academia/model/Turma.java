package br.com.joaowalter.academia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "turmas")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String modalidade;

    @NotBlank
    @Column(nullable = false, length = 30)
    private String diaSemana;

    @NotNull
    @Column(nullable = false)
    private LocalTime horario;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @OneToMany(mappedBy = "turma")
    private List<Matricula> matriculas = new ArrayList<>();

    public Turma() {
    }

    public Turma(Long id, String nome, String modalidade, String diaSemana, LocalTime horario) {
        this.id = id;
        this.nome = nome;
        this.modalidade = modalidade;
        this.diaSemana = diaSemana;
        this.horario = horario;
    }

    public Long getId() {
        return id;
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

    public LocalTime getHorario() {
        return horario;
    }

    public Professor getProfessor() {
        return professor;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }
}