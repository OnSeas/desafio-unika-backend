package com.unika.desafio.controler;

import com.unika.desafio.dto.*;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.filtros.FiltroMonitoradorDTO;
import com.unika.desafio.model.Monitorador;
import com.unika.desafio.service.EnderecoService;
import com.unika.desafio.service.MonitoradorService;
import com.unika.desafio.service.ReportService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/monitorador")
public class MonitoradorController {
    @Autowired
    private MonitoradorService monitoradorService;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private ReportService reportService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarMonitorador(@RequestBody RequestPessoaDto requestDto){
        try{
            ResponsePessoaDto responseDto = monitoradorService.cadastrarMonitorador(requestDto);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarMonitoradores(){
        try {
            List<ResponsePessoaDto> responseList = monitoradorService.listarMonitoradores();
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("buscar/{id}")
    public ResponseEntity<?> monitoradorPeloId(@PathVariable Long id){
        try {
            ResponsePessoaDto responseDto = monitoradorService.getMonitoradorResponsePeloId(id);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarMonitorador(@RequestBody RequestPessoaDto requestDto, @PathVariable Long id){
        try {
            System.out.println(requestDto);
            ResponsePessoaDto responseDto = monitoradorService.atualizarMonitorador(requestDto, id);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarMonitorador(@PathVariable Long id){
        try {
            ResponseEnderecoDto res = monitoradorService.deletarMonitorador(id);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @PostMapping("/{idMonitorador}/endereco")
    @Transactional
    public ResponseEntity<?> cadastrarEndereco(@PathVariable Long idMonitorador, @RequestBody RequestEnderecoDto requestDto){
        try {
            Monitorador monitorador = monitoradorService.pegarMonitoradorPeloId(idMonitorador);
            ResponseEnderecoDto responseDto = enderecoService.cadastrarEndereco(monitorador, requestDto);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        } catch (IOException | InterruptedException e){
            return new ResponseEntity<>("Houve um erro interno na conexão com ViaCEP", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @PutMapping("/ativar/{idMonitorador}")
    public ResponseEntity<?> ativarMonitorador(@PathVariable Long idMonitorador){
        try {
            ResponsePessoaDto res = monitoradorService.ativarMonitorador(idMonitorador);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @PutMapping("/desativar/{idMonitorador}")
    public ResponseEntity<?> desativarMonitorador(@PathVariable Long idMonitorador){
        try {
            ResponsePessoaDto res = monitoradorService.desativarMonitorador(idMonitorador);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    // Filtro
    @GetMapping("/filtro")
    public ResponseEntity<?> filtrarMonitorador(@RequestParam FiltroMonitoradorDTO filtro){
        try {
            return new ResponseEntity<>(monitoradorService.buscarMonitoradoresFiltro(filtro), HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("report/{id}")
    public ResponseEntity<?> gerarReport(@PathVariable Long id){
        try {
            File reportFile = reportService.exportUmMonitorador(id);
            return new ResponseEntity<>(FileUtils.readFileToByteArray(reportFile), HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("report")
    public ResponseEntity<?> gerarReport(){
        try {
            File reportFile = reportService.exportMonitoradoresPdf();
            return new ResponseEntity<>(FileUtils.readFileToByteArray(reportFile), HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("import")
    public ResponseEntity<?> importarMonitoradores(@RequestParam("file") MultipartFile file){
        try {
            File fileImport = new File("C:\\Projetos\\zArquivos\\recebidos\\" + file.getOriginalFilename());
            FileUtils.writeByteArrayToFile(fileImport, file.getBytes());
            List<ResponsePessoaDto> monitoradores = monitoradorService.importarMonitoradores(fileImport);
            return new ResponseEntity<>(monitoradores, HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("export/xlsx")
    public ResponseEntity<?> importarMonitoradores(){
        try {
            File file = monitoradorService.exportarMonitoradoresXlsx();
            return new ResponseEntity<>(FileUtils.readFileToByteArray(file), HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
