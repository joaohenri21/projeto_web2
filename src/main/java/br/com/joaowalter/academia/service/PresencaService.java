package br.com.joaowalter.academia.service;

import br.com.joaowalter.academia.model.Matricula;
import br.com.joaowalter.academia.model.Presenca;
import br.com.joaowalter.academia.repository.PresencaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PresencaService {

    private final PresencaRepository presencaRepository;
    private final MatriculaService matriculaService;

    public PresencaService(PresencaRepository presencaRepository,
                           MatriculaService matriculaService) {
        this.presencaRepository = presencaRepository;
        this.matriculaService = matriculaService;
    }

    public Page<Presenca> listar(Pageable pageable) {
        return presencaRepository.findAll(pageable);
    }

    public void registrarChamada(Long turmaId,
                                 LocalDateTime dataHoraAula,
                                 List<Long> matriculasPresentesIds) {

        List<Matricula> matriculasAtivas = matriculaService.listarAtivasPorTurma(turmaId);

        if (matriculasAtivas.isEmpty()) {
            throw new IllegalArgumentException("Esta turma não possui alunos matriculados ativos.");
        }

        Set<Long> idsPresentes = new HashSet<>();

        if (matriculasPresentesIds != null) {
            idsPresentes.addAll(matriculasPresentesIds);
        }

        for (Matricula matricula : matriculasAtivas) {
            Presenca presenca = presencaRepository
                    .findByMatriculaAndDataHoraAula(matricula, dataHoraAula)
                    .orElse(new Presenca());

            presenca.setMatricula(matricula);
            presenca.setDataHoraAula(dataHoraAula);
            presenca.setPresente(idsPresentes.contains(matricula.getId()));

            presencaRepository.save(presenca);
        }
    }
}