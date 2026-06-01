package br.com.joaowalter.academia.controller;

import br.com.joaowalter.academia.model.Turma;
import br.com.joaowalter.academia.service.ProfessorService;
import br.com.joaowalter.academia.service.TurmaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/turmas")
public class TurmaController {

    private final TurmaService turmaService;
    private final ProfessorService professorService;

    public TurmaController(TurmaService turmaService, ProfessorService professorService) {
        this.turmaService = turmaService;
        this.professorService = professorService;
    }

    @GetMapping
    public String listar(
            @RequestParam(required = false) String busca,
            @PageableDefault(size = 5, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable,
            Model model) {

        if (busca != null && !busca.isBlank()) {
            model.addAttribute("paginaTurmas", turmaService.pesquisarPorNome(busca, pageable));
        } else {
            model.addAttribute("paginaTurmas", turmaService.listar(pageable));
        }

        model.addAttribute("busca", busca);

        return "turmas/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("turma", new Turma());
        model.addAttribute("professores", professorService.listarTodos());
        return "turmas/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Turma turma,
            BindingResult result,
            @RequestParam Long professorId,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("professores", professorService.listarTodos());
            return "turmas/form";
        }

        turma.setProfessor(professorService.buscarPorId(professorId));
        turmaService.salvar(turma);

        return "redirect:/turmas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("turma", turmaService.buscarPorId(id));
        model.addAttribute("professores", professorService.listarTodos());
        return "turmas/form";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhes(@PathVariable Long id, Model model) {
        model.addAttribute("turma", turmaService.buscarPorId(id));
        return "turmas/detalhes";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        turmaService.excluir(id);
        return "redirect:/turmas";
    }
}
