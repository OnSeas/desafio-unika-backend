package com.unika.desafio.service;

import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import com.unika.desafio.model.Endereco;
import com.unika.desafio.model.Monitorador;
import com.unika.desafio.model.TipoPessoa;
import com.unika.desafio.repository.MonitoradorRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    @Autowired
    private MonitoradorRepository monitoradorRepository;

    // O subreport só fica vazio TODO descobrir como preencher o subreport
    public File exportarReport(String tipo, Long id) throws IOException, JRException { // A ordem das coisas manda em tudo aqui
        File fileReport;
        List<Endereco> enderecoList;

        List<Monitorador> monitoradorList = new ArrayList<>();
        Optional<Monitorador> monitoradorOptional = monitoradorRepository.findById(id);

        if (monitoradorOptional.isEmpty())
            throw new BusinessException(ErrorCode.MONITORADOR_NAO_ENCONTRADO);
        else{
            Monitorador monitorador = monitoradorOptional.get();
            monitoradorList.add(monitorador);
            if (monitorador.getTipoPessoa() == TipoPessoa.PESSOA_FISICA){
                fileReport = ResourceUtils.getFile("classpath:pfReport.jrxml");
            }else {
                fileReport = ResourceUtils.getFile("classpath:pjReport.jrxml");
            }
            enderecoList = monitorador.getEnderecoList();
        }

        JasperReport jasperReport = JasperCompileManager.compileReport(fileReport.getAbsolutePath());
        JRBeanCollectionDataSource reportDataSource = new JRBeanCollectionDataSource(monitoradorList);

        HashMap<String, Object> parameters = new HashMap<>();

        System.out.println(enderecoList);
        JRBeanCollectionDataSource enderecoDataSource = new JRBeanCollectionDataSource(enderecoList);
        parameters.put("EnderecoList", enderecoDataSource);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, reportDataSource);

        if (tipo.equals("pdf")){
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("relatorioMonitorador.pdf"));
            exporter.exportReport();
        } else if (tipo.equals("html")){
            HtmlExporter exporter = new HtmlExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleHtmlExporterOutput("relatorioMonitorador.html"));
            exporter.exportReport();
        } else throw new BusinessException("Tipo de documento inválido", HttpStatus.BAD_REQUEST);

        return new File("relatorioMonitorador." + tipo);
    }
}
