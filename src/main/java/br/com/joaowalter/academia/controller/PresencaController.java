package br.com.joaowalter.academia.controller;

import br.com.joaowalter.academia.service.MatriculaService;
import br.com.joaowalter.academia.service.PresencaService;
import br.com.joaowalter.academia.service.TurmaService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/presencas")
public class PresencaController {

    private final PresencaService presencaService;
    private final TurmaService turmaService;
    private final MatriculaService matriculaService;

    public PresencaController(PresencaService presencaService,
            TurmaService turmaService,
            MatriculaService matriculaService) {
        this.presencaService = presencaService;
        this.turmaService = turmaService;
        this.matriculaService = matriculaService;
    }

    @GetMapping
    public String listar(
            @PageableDefault(size = 5, sort = "dataHoraAula", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {

        model.addAttribute("paginaPresencas", presencaService.listar(pageable));
        return "presencas/lista";
    }

    @GetMapping("/nova")
    public String nova(@RequestParam(required = false) Long turmaId,
            Model model) {

        model.addAttribute("turmas", turmaService.listarTodos());
        model.addAttribute("turmaSelecionadaId", turmaId);

        if (turmaId != null) {
            model.addAttribute("matriculas", matriculaService.listarAtivasPorTurma(turmaId));
        }

        return "presencas/form";
    }

    @PostMapping("/salvar")
    public String salvar(@RequestParam(required = false) Long turmaId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dataHoraAula,
            @RequestParam(required = false) List<Long> matriculasPresentes,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (turmaId == null || dataHoraAula == null) {
            model.addAttribute("erro", "Selecione uma turma e informe a data/hora da aula.");
            model.addAttribute("turmas", turmaService.listarTodos());
            model.addAttribute("turmaSelecionadaId", turmaId);

            if (turmaId != null) {
                model.addAttribute("matriculas", matriculaService.listarAtivasPorTurma(turmaId));
            }

            return "presencas/form";
        }

        try {
            presencaService.registrarChamada(turmaId, dataHoraAula, matriculasPresentes);
            redirectAttributes.addFlashAttribute("sucesso", "Presença registrada com sucesso.");
            return "redirect:/presencas";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("turmas", turmaService.listarTodos());
            model.addAttribute("turmaSelecionadaId", turmaId);
            model.addAttribute("matriculas", matriculaService.listarAtivasPorTurma(turmaId));

            return "presencas/form";
        }
    }
}