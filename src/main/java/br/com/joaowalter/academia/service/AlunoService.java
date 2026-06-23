package br.com.joaowalter.academia.service;

import br.com.joaowalter.academia.model.Aluno;
import br.com.joaowalter.academia.repository.AlunoRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public Page<Aluno> listar(Pageable pageable) {
        return alunoRepository.findAll(pageable);
    }

    public Page<Aluno> pesquisarPorNome(String nome, Pageable pageable) {
        return alunoRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public List<Aluno> pesquisarPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return List.of();
        }

        return alunoRepository.findTop10ByNomeContainingIgnoreCaseOrderByNomeAsc(nome);
    }

    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }

    public Iterable<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Aluno salvar(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public void excluir(Long id) {
        alunoRepository.deleteById(id);
    }
}
