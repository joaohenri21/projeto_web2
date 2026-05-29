package br.com.joaowalter.academia.service;

import br.com.joaowalter.academia.model.Turma;
import br.com.joaowalter.academia.repository.TurmaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TurmaService {

    private final TurmaRepository turmaRepository;

    public TurmaService(TurmaRepository turmaRepository) {
        this.turmaRepository = turmaRepository;
    }

    public Page<Turma> listar(Pageable pageable) {
        return turmaRepository.findAll(pageable);
    }

    public Turma buscarPorId(Long id) {
        return turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
    }

    public Turma salvar(Turma turma) {
        return turmaRepository.save(turma);
    }

    public void excluir(Long id) {
        turmaRepository.deleteById(id);
    }
}
