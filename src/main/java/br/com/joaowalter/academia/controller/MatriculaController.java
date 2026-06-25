package br.com.joaowalter.academia.controller;

import br.com.joaowalter.academia.service.AlunoService;
import br.com.joaowalter.academia.service.MatriculaService;
import br.com.joaowalter.academia.service.TurmaService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/matriculas")
public class MatriculaController {

    private final MatriculaService matriculaService;
    private final AlunoService alunoService;
    private final TurmaService turmaService;

    public MatriculaController(MatriculaService matriculaService,
            AlunoService alunoService,
            TurmaService turmaService) {
        this.matriculaService = matriculaService;
        this.alunoService = alunoService;
        this.turmaService = turmaService;
    }

    @GetMapping
    public String listar(
            @PageableDefault(size = 5, sort = "dataMatricula", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {

        model.addAttribute("paginaMatriculas", matriculaService.listar(pageable));
        return "matriculas/lista";
    }

    @GetMapping("/pesquisar-alunos")
    public String pesquisarAlunos(@RequestParam("alunoBusca") String alunoBusca,
            Model model) {

        model.addAttribute("alunosEncontrados", alunoService.pesquisarPorNome(alunoBusca));
        return "matriculas/alunos-busca :: lista";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("alunos", alunoService.listarTodos());
        model.addAttribute("turmas", turmaService.listarTodos());
        return "matriculas/form";
    }

    @PostMapping("/salvar")
    public String salvar(@RequestParam(required = false) Long alunoId,
            @RequestParam(required = false) Long turmaId,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (alunoId == null || turmaId == null) {
            model.addAttribute("erro", "Selecione um aluno e uma turma.");
            model.addAttribute("alunos", alunoService.listarTodos());
            model.addAttribute("turmas", turmaService.listarTodos());
            return "matriculas/form";
        }

        try {
            matriculaService.matricular(alunoId, turmaId);

            redirectAttributes.addFlashAttribute("sucesso", "Matrícula realizada com sucesso.");

            return "redirect:/matriculas";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("alunos", alunoService.listarTodos());
            model.addAttribute("turmas", turmaService.listarTodos());
            return "matriculas/form";
        }
    }

    @GetMapping("/cancelar/{id}")
    public String cancelar(@PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        matriculaService.cancelar(id);

        redirectAttributes.addFlashAttribute("alerta", "Matrícula cancelada com sucesso.");

        return "redirect:/matriculas";
    }
}