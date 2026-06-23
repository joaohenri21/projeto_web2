package br.com.joaowalter.academia.controller;

import br.com.joaowalter.academia.repository.AlunoRepository;
import br.com.joaowalter.academia.repository.MatriculaRepository;
import br.com.joaowalter.academia.repository.PresencaRepository;
import br.com.joaowalter.academia.repository.ProfessorRepository;
import br.com.joaowalter.academia.repository.TurmaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final TurmaRepository turmaRepository;
    private final MatriculaRepository matriculaRepository;
    private final PresencaRepository presencaRepository;

    public HomeController(AlunoRepository alunoRepository,
                          ProfessorRepository professorRepository,
                          TurmaRepository turmaRepository,
                          MatriculaRepository matriculaRepository,
                          PresencaRepository presencaRepository) {
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.turmaRepository = turmaRepository;
        this.matriculaRepository = matriculaRepository;
        this.presencaRepository = presencaRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("totalAlunos", alunoRepository.count());
        model.addAttribute("totalProfessores", professorRepository.count());
        model.addAttribute("totalTurmas", turmaRepository.count());
        model.addAttribute("totalMatriculas", matriculaRepository.count());
        model.addAttribute("totalPresencas", presencaRepository.count());

        model.addAttribute("ultimosAlunos", alunoRepository.findTop5ByOrderByIdDesc());
        model.addAttribute("ultimasTurmas", turmaRepository.findTop5ByOrderByIdDesc());

        return "index";
    }
}