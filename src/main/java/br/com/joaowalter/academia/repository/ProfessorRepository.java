package br.com.joaowalter.academia.repository;

import br.com.joaowalter.academia.model.Professor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    Page<Professor> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    List<Professor> findTop10ByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);

}