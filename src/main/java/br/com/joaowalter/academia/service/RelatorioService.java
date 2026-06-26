package br.com.joaowalter.academia.service;

import br.com.joaowalter.academia.dto.RelatorioPresencaDTO;
import br.com.joaowalter.academia.dto.RelatorioTurmaDTO;
import br.com.joaowalter.academia.model.Presenca;
import br.com.joaowalter.academia.model.Turma;
import br.com.joaowalter.academia.repository.PresencaRepository;
import br.com.joaowalter.academia.repository.TurmaRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RelatorioService {

    private final TurmaRepository turmaRepository;
    private final PresencaRepository presencaRepository;
    private final ResourceLoader resourceLoader;

    public RelatorioService(TurmaRepository turmaRepository,
                            PresencaRepository presencaRepository,
                            ResourceLoader resourceLoader) {
        this.turmaRepository = turmaRepository;
        this.presencaRepository = presencaRepository;
        this.resourceLoader = resourceLoader;
    }

    public byte[] gerarRelatorioTurma(Long id) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        RelatorioTurmaDTO dados = converterTurmaParaRelatorio(turma);

        return gerarPdfTurma(dados);
    }

    private byte[] gerarPdfTurma(RelatorioTurmaDTO dados) {
        try {
            Resource relatorioPrincipalResource =
                    resourceLoader.getResource("classpath:relatorios/relatorio_turmas.jrxml");

            Resource subRelatorioResource =
                    resourceLoader.getResource("classpath:relatorios/subrelatorio_presencas.jrxml");

            InputStream relatorioPrincipalStream = relatorioPrincipalResource.getInputStream();
            InputStream subRelatorioStream = subRelatorioResource.getInputStream();

            JasperReport relatorioPrincipal = JasperCompileManager.compileReport(relatorioPrincipalStream);
            JasperReport subRelatorio = JasperCompileManager.compileReport(subRelatorioStream);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("SUBRELATORIO_PRESENCAS", subRelatorio);

            JRBeanCollectionDataSource dataSource =
                    new JRBeanCollectionDataSource(List.of(dados));

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    relatorioPrincipal,
                    parametros,
                    dataSource
            );

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar relatório da turma", e);
        }
    }

    private RelatorioTurmaDTO converterTurmaParaRelatorio(Turma turma) {
        List<Presenca> presencasRegistradas =
                presencaRepository.findByMatricula_Turma_IdOrderByDataHoraAulaDesc(turma.getId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        List<RelatorioPresencaDTO> presencas = presencasRegistradas.stream()
                .map(presenca -> new RelatorioPresencaDTO(
                        presenca.getDataHoraAula().format(formatter),
                        presenca.getMatricula().getAluno().getNome(),
                        Boolean.TRUE.equals(presenca.getPresente()) ? "Presente" : "Falta"
                ))
                .toList();

        if (presencas.isEmpty()) {
            presencas = List.of(new RelatorioPresencaDTO(
                    "",
                    "Nenhuma presença registrada para esta turma.",
                    ""
            ));
        }

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
                presencas
        );
    }
}