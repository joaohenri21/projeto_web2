package br.com.joaowalter.academia.service;

import br.com.joaowalter.academia.model.Aluno;
import br.com.joaowalter.academia.model.Matricula;
import br.com.joaowalter.academia.model.Turma;
import br.com.joaowalter.academia.repository.MatriculaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AlunoService alunoService;
    private final TurmaService turmaService;

    public MatriculaService(MatriculaRepository matriculaRepository,
                            AlunoService alunoService,
                            TurmaService turmaService) {
        this.matriculaRepository = matriculaRepository;
        this.alunoService = alunoService;
        this.turmaService = turmaService;
    }

    public Page<Matricula> listar(Pageable pageable) {
        return matriculaRepository.findAll(pageable);
    }

    public Matricula buscarPorId(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matrícula não encontrada"));
    }

    public List<Matricula> listarAtivasPorTurma(Long turmaId) {
        return matriculaRepository.findByTurmaIdAndStatusOrderByAlunoNomeAsc(turmaId, "ATIVA");
    }

    public Matricula matricular(Long alunoId, Long turmaId) {
        Aluno aluno = alunoService.buscarPorId(alunoId);
        Turma turma = turmaService.buscarPorId(turmaId);

        boolean jaExisteMatriculaAtiva =
                matriculaRepository.existsByAlunoAndTurmaAndStatus(aluno, turma, "ATIVA");

        if (jaExisteMatriculaAtiva) {
            throw new IllegalArgumentException("Este aluno já está matriculado nesta turma.");
        }

        Matricula matricula = new Matricula();
        matricula.setAluno(aluno);
        matricula.setTurma(turma);
        matricula.setDataMatricula(LocalDate.now());
        matricula.setStatus("ATIVA");

        return matriculaRepository.save(matricula);
    }

    public void cancelar(Long id) {
        Matricula matricula = buscarPorId(id);
        matricula.setStatus("CANCELADA");
        matriculaRepository.save(matricula);
    }
}