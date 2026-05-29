package br.com.joaowalter.academia.repository;

import br.com.joaowalter.academia.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}