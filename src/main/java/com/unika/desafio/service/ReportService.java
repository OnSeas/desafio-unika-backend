package com.unika.desafio.service;

import com.unika.desafio.dto.ResponsePessoaDto;
import com.unika.desafio.model.TipoPessoa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    @Autowired
    private MonitoradorService monitoradorService;
    long Count = 0;
    final String PATH = "C:\\Projetos\\zArquivos\\relatorios\\";

    public File exportMonitoradoresPdf() throws FileNotFoundException, JRException { //Todos os monitoradores
        List<ResponsePessoaDto> monitoradorList = monitoradorService.listarMonitoradores();

        HashMap<String, Object> params = new HashMap<>();
        params.put("isRelatorioGeral", true);

        List<GraphicParam> graphic = new ArrayList<>();
        graphic.add(new GraphicParam("Pessoa Física", monitoradorList.stream().filter(m -> m.getTipoPessoa().equals(TipoPessoa.PESSOA_FISICA)).toList().size()));
        graphic.add(new GraphicParam("Pessoa Jurídica", monitoradorList.stream().filter(m -> m.getTipoPessoa().equals(TipoPessoa.PESSOA_JURIDICA)).toList().size()));

        params.put("graphicParam", graphic);

        return createReportPdf(monitoradorList, "relatorioMonitoradores" + LocalDate.now() + "-" + Count + ".pdf", params);
    }

    public File exportUmMonitorador(Long id) throws JRException, FileNotFoundException {
        List<ResponsePessoaDto> monitoradorList = new ArrayList<>();
        ResponsePessoaDto responsePessoaDto = monitoradorService.getMonitoradorResponsePeloId(id);
        monitoradorList.add(responsePessoaDto);

        HashMap<String, Object> params = new HashMap<>();
        params.put("isRelatorioGeral", false);
        params.put("graphicParam", new ArrayList<>());

        return createReportPdf(monitoradorList,
                "relatorioMonitoradores-" + (responsePessoaDto.getTipoPessoa().equals(TipoPessoa.PESSOA_FISICA) ? responsePessoaDto.getNome() : responsePessoaDto.getRazaoSocial()) + "-" + Count + ".pdf",
                params
                );
    }

    public File createReportPdf(List<ResponsePessoaDto> monitoradorList, String fileName, Map<String, Object> params) throws JRException, FileNotFoundException {
        File fileReport = ResourceUtils.getFile("classpath:monitoradoresReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(fileReport.getAbsolutePath());
        JRBeanCollectionDataSource reportDataSource = new JRBeanCollectionDataSource(monitoradorList);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, reportDataSource);

        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(PATH + fileName));
        exporter.exportReport();

        return new File(PATH + fileName);
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public class GraphicParam{
        private String nome;
        private Integer quantidade;
    }
}
