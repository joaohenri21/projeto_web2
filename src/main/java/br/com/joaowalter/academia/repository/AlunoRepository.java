package br.com.joaowalter.academia.repository;

import br.com.joaowalter.academia.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
