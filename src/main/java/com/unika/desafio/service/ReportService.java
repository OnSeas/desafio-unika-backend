package com.unika.desafio.service;

import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import com.unika.desafio.model.PessoaFisica;
import com.unika.desafio.repository.MonitoradorRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
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

    public String exportReport(String reportFormat, Long id) throws IOException, JRException {
        Optional<PessoaFisica> pessoaFisicaOptional = monitoradorRepository.findByIdPF(id); // Buscar a pessoa física desejada.

        if (pessoaFisicaOptional.isEmpty())
            throw new BusinessException(ErrorCode.MONITORADOR_NAO_ENCONTRADO);

        List<PessoaFisica> pfList = new ArrayList<>();
        pfList.add(pessoaFisicaOptional.get());

        File file = ResourceUtils.getFile("classpath:RelatorioPF.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
//        JRSaver.saveObject(jasperReport, "employeeReport.jasper");

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(pfList);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("relatorioPF.pdf"));

        exporter.exportReport();

        return "Relatório em PDF gerado!";
    }
}
