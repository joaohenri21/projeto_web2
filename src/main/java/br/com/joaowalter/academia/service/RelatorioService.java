package br.com.joaowalter.academia.service;

import br.com.joaowalter.academia.dto.RelatorioAlunoDTO;
import br.com.joaowalter.academia.dto.RelatorioTurmaDTO;
import br.com.joaowalter.academia.model.Matricula;
import br.com.joaowalter.academia.model.Turma;
import br.com.joaowalter.academia.repository.MatriculaRepository;
import br.com.joaowalter.academia.repository.TurmaRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RelatorioService {

    private final TurmaRepository turmaRepository;
    private final MatriculaRepository matriculaRepository;
    private final ResourceLoader resourceLoader;

    public RelatorioService(TurmaRepository turmaRepository,
                            MatriculaRepository matriculaRepository,
                            ResourceLoader resourceLoader) {
        this.turmaRepository = turmaRepository;
        this.matriculaRepository = matriculaRepository;
        this.resourceLoader = resourceLoader;
    }

    public byte[] gerarRelatorioTurmas() {
        try {
            List<RelatorioTurmaDTO> dados = turmaRepository.findAll()
                    .stream()
                    .map(this::converterTurmaParaRelatorio)
                    .toList();

            Resource relatorioPrincipalResource =
                    resourceLoader.getResource("classpath:relatorios/relatorio_turmas.jrxml");

            Resource subRelatorioResource =
                    resourceLoader.getResource("classpath:relatorios/subrelatorio_alunos.jrxml");

            InputStream relatorioPrincipalStream = relatorioPrincipalResource.getInputStream();
            InputStream subRelatorioStream = subRelatorioResource.getInputStream();

            JasperReport relatorioPrincipal = JasperCompileManager.compileReport(relatorioPrincipalStream);
            JasperReport subRelatorio = JasperCompileManager.compileReport(subRelatorioStream);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("SUBRELATORIO_ALUNOS", subRelatorio);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dados);

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    relatorioPrincipal,
                    parametros,
                    dataSource
            );

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar relatório de turmas", e);
        }
    }

    private RelatorioTurmaDTO converterTurmaParaRelatorio(Turma turma) {
        List<Matricula> matriculas =
                matriculaRepository.findByTurmaIdAndStatusOrderByAlunoNomeAsc(turma.getId(), "ATIVA");

        List<RelatorioAlunoDTO> alunos = matriculas.stream()
                .map(matricula -> new RelatorioAlunoDTO(
                        matricula.getAluno().getNome(),
                        matricula.getStatus()
                ))
                .toList();

        String professor = turma.getProfessor() != null
                ? turma.getProfessor().getNome()
                : "Sem professor";

        String horario = turma.getHorario() != null
                ? turma.getHorario().toString()
                : "";

        return new RelatorioTurmaDTO(
                turma.getNome(),
                turma.getModalidade(),
                turma.getDiaSemana(),
                horario,
                professor,
                alunos
        );
    }
}