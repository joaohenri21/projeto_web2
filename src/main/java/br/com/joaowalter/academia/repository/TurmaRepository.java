package br.com.joaowalter.academia.repository;

import br.com.joaowalter.academia.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
}