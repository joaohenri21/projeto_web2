package br.com.joaowalter.academia.controller;

import br.com.joaowalter.academia.model.Professor;
import br.com.joaowalter.academia.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/professores")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    public String listar(
            @RequestParam(required = false) String busca,
            @PageableDefault(size = 5, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable,
            Model model) {

        if (busca != null && !busca.isBlank()) {
            model.addAttribute("paginaProfessores", professorService.pesquisarPorNome(busca, pageable));
        } else {
            model.addAttribute("paginaProfessores", professorService.listar(pageable));
        }

        model.addAttribute("busca", busca);

        return "professores/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("professor", new Professor());
        return "professores/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Professor professor,
            BindingResult result) {

        if (result.hasErrors()) {
            return "professores/form";
        }

        professorService.salvar(professor);
        return "redirect:/professores";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("professor", professorService.buscarPorId(id));
        return "professores/form";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhes(@PathVariable Long id, Model model) {
        model.addAttribute("professor", professorService.buscarPorId(id));
        return "professores/detalhes";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        professorService.excluir(id);
        return "redirect:/professores";
    }
}