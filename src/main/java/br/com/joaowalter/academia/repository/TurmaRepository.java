package br.com.joaowalter.academia.repository;

import br.com.joaowalter.academia.model.Turma;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

    List<Turma> findTop5ByOrderByIdDesc();

    Page<Turma> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

}
