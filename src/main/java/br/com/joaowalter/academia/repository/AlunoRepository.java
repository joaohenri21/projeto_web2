package br.com.joaowalter.academia.repository;

import br.com.joaowalter.academia.model.Aluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    List<Aluno> findTop5ByOrderByIdDesc();

    Page<Aluno> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    List<Aluno> findTop10ByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);

}