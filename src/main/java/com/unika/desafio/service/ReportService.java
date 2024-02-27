package com.unika.desafio.service;

import com.unika.desafio.dto.ResponsePessoaDto;
import com.unika.desafio.model.TipoPessoa;
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
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private MonitoradorService monitoradorService;
    long Count = 0;
    final String PATH = "C:\\Projetos\\zArquivos\\relatorios\\";

    public File exportMonitoradoresPdf() throws FileNotFoundException, JRException { //Todos os monitoradores
        List<ResponsePessoaDto> monitoradorList = monitoradorService.listarMonitoradores();
        return createReportPdf(monitoradorList, "relatorioMonitoradores" + LocalDate.now() + "-" + Count + ".pdf");
    }

    public File exportUmMonitorador(Long id) throws JRException, FileNotFoundException {
        List<ResponsePessoaDto> monitoradorList = new ArrayList<>();
        ResponsePessoaDto responsePessoaDto = monitoradorService.getMonitoradorResponsePeloId(id);
        monitoradorList.add(responsePessoaDto);
        return createReportPdf(monitoradorList, "relatorioMonitoradores-" + (responsePessoaDto.getTipoPessoa().equals(TipoPessoa.PESSOA_FISICA) ? responsePessoaDto.getNome() : responsePessoaDto.getRazaoSocial()) + "-" + Count + ".pdf");
    }

    public File createReportPdf(List<ResponsePessoaDto> monitoradorList, String fileName) throws JRException, FileNotFoundException {
        File fileReport = ResourceUtils.getFile("classpath:monitoradoresReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(fileReport.getAbsolutePath());
        JRBeanCollectionDataSource reportDataSource = new JRBeanCollectionDataSource(monitoradorList);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, reportDataSource);

        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(PATH + fileName));
        exporter.exportReport();

        return new File(PATH + fileName);
    }
}
