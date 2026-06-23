package br.com.joaowalter.academia.repository;

import br.com.joaowalter.academia.model.Aluno;
import br.com.joaowalter.academia.model.Matricula;
import br.com.joaowalter.academia.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    boolean existsByAlunoAndTurmaAndStatus(Aluno aluno, Turma turma, String status);

    List<Matricula> findByTurmaIdAndStatusOrderByAlunoNomeAsc(Long turmaId, String status);

}