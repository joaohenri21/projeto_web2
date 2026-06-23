package br.com.joaowalter.academia.repository;

import br.com.joaowalter.academia.model.Matricula;
import br.com.joaowalter.academia.model.Presenca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PresencaRepository extends JpaRepository<Presenca, Long> {

    Optional<Presenca> findByMatriculaAndDataHoraAula(Matricula matricula, LocalDateTime dataHoraAula);

}