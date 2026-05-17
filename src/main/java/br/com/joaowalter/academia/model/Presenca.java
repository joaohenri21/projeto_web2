package br.com.joaowalter.academia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "presencas")
public class Presenca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime dataHoraAula;

    @NotNull
    @Column(nullable = false)
    private Boolean presente;

    @ManyToOne
    @JoinColumn(name = "matricula_id", nullable = false)
    private Matricula matricula;

    public Presenca() {
    }

    public Presenca(Long id, LocalDateTime dataHoraAula, Boolean presente) {
        this.id = id;
        this.dataHoraAula = dataHoraAula;
        this.presente = presente;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataHoraAula() {
        return dataHoraAula;
    }

    public Boolean getPresente() {
        return presente;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDataHoraAula(LocalDateTime dataHoraAula) {
        this.dataHoraAula = dataHoraAula;
    }

    public void setPresente(Boolean presente) {
        this.presente = presente;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }
}