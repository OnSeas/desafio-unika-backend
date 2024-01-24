package com.unika.desafio.service;

import com.unika.desafio.model.Monitorador;
import com.unika.desafio.repository.MonitoradorRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private MonitoradorRepository monitoradorRepository;

    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "/home/osmar/Documentos/JasperSaves/";
        List<Monitorador> monitoradorList = monitoradorRepository.findAll();

        // Carregar o arquivo do relatório
        File file = ResourceUtils.getFile("classpath:relatorioPF.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(monitoradorList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Unika Sistemas");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if(reportFormat.equalsIgnoreCase("html")){
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path +"RelatorioPF.hmtl");
        }
        if (reportFormat.equalsIgnoreCase("pdf")){
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "RelatorioPF.pdf");
        }

        return "report generated in path: " + path;
    }
}
