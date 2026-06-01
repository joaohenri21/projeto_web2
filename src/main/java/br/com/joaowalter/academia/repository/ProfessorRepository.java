package br.com.joaowalter.academia.repository;

import br.com.joaowalter.academia.model.Professor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    Page<Professor> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

}