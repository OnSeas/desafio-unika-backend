package com.unika.desafio.service;

import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import com.unika.desafio.model.*;
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

    public File exportarReport(String tipo, Long id) throws IOException, JRException {
        HashMap<String, Object> parameters;
        List<Monitorador> monitoradorList = new ArrayList<>();

        Optional<Monitorador> monitoradorOptional = monitoradorRepository.findById(id);
        if (monitoradorOptional.isEmpty())
            throw new BusinessException(ErrorCode.MONITORADOR_NAO_ENCONTRADO);
        else{
            Monitorador monitorador = monitoradorOptional.get();
            monitoradorList.add(monitorador);
            parameters = getParameters(monitorador);
        }

        File fileReport = ResourceUtils.getFile("classpath:monitoradorReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(fileReport.getAbsolutePath());
        JRBeanCollectionDataSource reportDataSource = new JRBeanCollectionDataSource(monitoradorList);
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

    private HashMap<String, Object> getParameters(Monitorador monitorador){ // Setar os parametros que mudama se é pessoa física ou pessoa jurídica
        HashMap<String, Object> parameters = new HashMap<>();

        if (monitorador.getTipoPessoa() == TipoPessoa.PESSOA_FISICA){
            PessoaFisica pessoaFisica = (PessoaFisica) monitorador;

            parameters.put("tipoPessoa", 0);
            parameters.put("nome-razao", pessoaFisica.getNome());
            parameters.put("rg-inscricaoEstadual", pessoaFisica.getRg());
            parameters.put("CPF-CNPJ", pessoaFisica.getCpf());
        } else{
            PessoaJuridica pessoaJuridica = (PessoaJuridica) monitorador;

            parameters.put("tipoPessoa", 1);
            parameters.put("nome-razao", pessoaJuridica.getRazaoSocial());
            parameters.put("rg-inscricaoEstadual", pessoaJuridica.getInscricaoEstadual());
            parameters.put("CPF-CNPJ", pessoaJuridica.getCnpj());
        }

        List<Endereco> enderecoList = monitorador.getEnderecoList();
        JRBeanCollectionDataSource enderecoDataSource = new JRBeanCollectionDataSource(enderecoList);
        parameters.put("EnderecoList", enderecoDataSource);

        return parameters;
    }
}
