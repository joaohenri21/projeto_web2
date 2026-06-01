package br.com.joaowalter.academia.controller;

import br.com.joaowalter.academia.model.Aluno;
import br.com.joaowalter.academia.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    public String listar(
            @RequestParam(required = false) String busca,
            @PageableDefault(size = 5, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable,
            Model model) {

        if (busca != null && !busca.isBlank()) {
            model.addAttribute("paginaAlunos", alunoService.pesquisarPorNome(busca, pageable));
        } else {
            model.addAttribute("paginaAlunos", alunoService.listar(pageable));
        }

        model.addAttribute("busca", busca);

        return "alunos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "alunos/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Aluno aluno,
                         BindingResult result) {

        if (result.hasErrors()) {
            return "alunos/form";
        }

        alunoService.salvar(aluno);
        return "redirect:/alunos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("aluno", alunoService.buscarPorId(id));
        return "alunos/form";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhes(@PathVariable Long id, Model model) {
        model.addAttribute("aluno", alunoService.buscarPorId(id));
        return "alunos/detalhes";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        alunoService.excluir(id);
        return "redirect:/alunos";
    }
}
