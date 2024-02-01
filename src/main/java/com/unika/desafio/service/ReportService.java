package com.unika.desafio.service;

import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import com.unika.desafio.model.*;
import com.unika.desafio.repository.MonitoradorRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.ExporterInputItem;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleExporterInputItem;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    @Autowired
    private MonitoradorRepository monitoradorRepository;

    // O subreport só fica vazio TODO descobrir como preencher o subreport
    public String exportarReport(String tipo, Long id) throws IOException, JRException { // A ordem das coisas manda em tudo aqui

        // =========== Criação do relatório de monitorador ===========
        File reportFile;
        JasperReport jasperReport;
        JRBeanCollectionDataSource reportDataSource;
        JasperPrint jasperSubprintPrint;


        // =========== Criação do subrelatório de endereço ===========
        File subreportFile = ResourceUtils.getFile("classpath:enderecoReport.jrxml");
        JasperReport jasperSubreport = JasperCompileManager.compileReport(subreportFile.getAbsolutePath());
        JRSaver.saveObject(jasperSubreport, "enderecoReport.jasper");

        Optional<Monitorador> monitoradorOptional = monitoradorRepository.findById(id); // Buscar o monitorador
        if (monitoradorOptional.isEmpty())
            throw new BusinessException(ErrorCode.MONITORADOR_NAO_ENCONTRADO);
        else {
            List<Endereco> enderecoList = monitoradorOptional.get().getEnderecoList(); // Buscar endereços do monitorador
            JRBeanCollectionDataSource subreportDataSource = new JRBeanCollectionDataSource(enderecoList);
            jasperSubprintPrint = JasperFillManager.fillReport(jasperSubreport, null, subreportDataSource);

            if (monitoradorOptional.get().getTipoPessoa() == TipoPessoa.PESSOA_FISICA) { // Criação
                List<PessoaFisica> pfList = new ArrayList<>();
                pfList.add((PessoaFisica) monitoradorOptional.get());
                reportDataSource = new JRBeanCollectionDataSource(pfList);
                reportFile = ResourceUtils.getFile("classpath:pessoaFisicaReport.jrxml");
                jasperReport = JasperCompileManager.compileReport(reportFile.getAbsolutePath());
                JRSaver.saveObject(jasperReport, "pessoaFisicaReport.jasper");
            } else {
                List<PessoaJuridica> pjList = new ArrayList<>();
                pjList.add((PessoaJuridica) monitoradorOptional.get());
                reportDataSource = new JRBeanCollectionDataSource(pjList);
                reportFile = ResourceUtils.getFile("classpath:pessoaJuridicaReport.jrxml");
                jasperReport = JasperCompileManager.compileReport(reportFile.getAbsolutePath());
                JRSaver.saveObject(jasperReport, "pessoaJuridicaReport.jasper");
            }
        }

        // Preenchendo formulários
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, reportDataSource);

        List<ExporterInputItem> simpleExporterInputs = new ArrayList<>();
        simpleExporterInputs.add(new SimpleExporterInputItem(jasperPrint));
        simpleExporterInputs.add(new SimpleExporterInputItem(jasperSubprintPrint));

        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(simpleExporterInputs));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("relatorioMonitorador.pdf"));

        exporter.exportReport();
        File file = ResourceUtils.getFile("relatorioMonitorador.pdf");

        return file.getAbsolutePath();


//        if (Objects.equals(tipo, "pdf")){
//            // Exportando o relatório com o subrelatório? Acho que não, parece que exporta um depois o outro.
//            List<ExporterInputItem> simpleExporterInputs = new ArrayList<>();
//            simpleExporterInputs.add(new SimpleExporterInputItem(jasperPrint));
//            simpleExporterInputs.add(new SimpleExporterInputItem(jasperSubprintPrint));
//
//            JRPdfExporter exporter = new JRPdfExporter();
//            exporter.setExporterInput(new SimpleExporterInput(simpleExporterInputs));
//            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("relatorioMonitorador.pdf"));
//
//            exporter.exportReport();
//            return "Relatório em PDF gerado!";
//        }
//        else if (Objects.equals(tipo, "xls")){ // não é bem o que eu queria TODO decidir se vai usar mesmo essa parte ou só gerar o PDF
//            List<ExporterInputItem> simpleExporterInputs = new ArrayList<>();
//            simpleExporterInputs.add(new SimpleExporterInputItem(jasperPrint));
//            simpleExporterInputs.add(new SimpleExporterInputItem(jasperSubprintPrint));
//
//            JRCsvExporter exporter = new JRCsvExporter();
//            exporter.setExporterInput(new SimpleExporterInput(simpleExporterInputs));
//            exporter.setExporterOutput(new SimpleWriterExporterOutput("dadosMonitorador.csv"));
//
//            exporter.exportReport();
//            return "Dados em XLS gerados!";
//        }
//        else throw new RuntimeException("Tipo não encontrado");
    }
}
