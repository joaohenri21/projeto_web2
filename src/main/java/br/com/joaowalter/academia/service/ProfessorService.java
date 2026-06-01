package br.com.joaowalter.academia.service;

import br.com.joaowalter.academia.model.Professor;
import br.com.joaowalter.academia.repository.ProfessorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public Page<Professor> listar(Pageable pageable) {
        return professorRepository.findAll(pageable);
    }

    public Iterable<Professor> listarTodos() {
        return professorRepository.findAll();
    }

    public Professor buscarPorId(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
    }

    public Page<Professor> pesquisarPorNome(String nome, Pageable pageable) {
        return professorRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Professor salvar(Professor professor) {
        return professorRepository.save(professor);
    }

    public void excluir(Long id) {
        professorRepository.deleteById(id);
    }
}
