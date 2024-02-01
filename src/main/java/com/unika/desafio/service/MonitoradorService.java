package com.unika.desafio.service;

import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.dto.ResponsePessoaDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import com.unika.desafio.model.Monitorador;
import com.unika.desafio.model.PessoaFisica;
import com.unika.desafio.model.PessoaJuridica;
import com.unika.desafio.model.TipoPessoa;
import com.unika.desafio.repository.MonitoradorRepository;
import com.unika.desafio.validations.monitorador.IMonitoradorJaExiste;
import jakarta.validation.Valid;
import lombok.Cleanup;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MonitoradorService {
    @Autowired
    private MonitoradorRepository repository;

    private ModelMapper mapper = new ModelMapper();

    @Autowired
    private List<IMonitoradorJaExiste> monitoradorJaExiste;

    public ResponsePessoaDto cadastrarMonitorador(RequestPessoaDto requestDto){
        monitoradorJaExiste.forEach(v -> v.validar(requestDto)); // Validando se já existe
        Monitorador monitorador = getMonitoradorByTipo(requestDto);
        monitorador.ativar();
        return mapper.map(repository.save(monitorador), ResponsePessoaDto.class);
    }

    public List<ResponsePessoaDto> listarMonitoradores(){
        List<Monitorador> monitoradorList = repository.findAll();
        return monitoradorList.
                stream()
                .map(m -> mapper.map(m, ResponsePessoaDto.class))
                .collect(Collectors.toList());
    }

    public ResponsePessoaDto getMonitoradorResponsePeloId(Long id) {
        return mapper.map(pegarMonitoradorPeloId(id), ResponsePessoaDto.class);
    }

    public Monitorador pegarMonitoradorPeloId(Long id){
        Optional<Monitorador> optionalMonitorador = repository.findById(id);
        if (optionalMonitorador.isPresent()){
            Monitorador monitorador = optionalMonitorador.get();
            System.out.println("Monitorador BD: " + monitorador.getClass());
            return monitorador;
        } else {
            throw new BusinessException(ErrorCode.MONITORADOR_NAO_ENCONTRADO);
        }
    }

    public ResponsePessoaDto atualizarMonitorador(RequestPessoaDto requestDto, Long id){
        monitoradorJaExiste.forEach(v -> v.validar(requestDto, id)); // Validar se tem outro monitorador com informação que não pode ser repetida

        Optional<Monitorador> optionalMonitorador = repository.findById(id);
        if (optionalMonitorador.isPresent()){
            Monitorador monitoradorDB = optionalMonitorador.get();

            Monitorador monitorador = getMonitoradorByTipo(requestDto);
            System.out.println(monitorador);

            BeanUtils.copyProperties(
                    monitorador,
                    monitoradorDB,
                    "id", "ativo");

            System.out.println(monitoradorDB);

            return mapper.map(repository.save(monitoradorDB), ResponsePessoaDto.class);
        } else {
            throw new BusinessException(ErrorCode.MONITORADOR_NAO_ENCONTRADO);
        }
    }

    public void deletarMonitorador(Long id){
        monitoradorExiste(id);
        repository.deleteById(id);
    }

    public void ativarMonitorador(Long id){
        Optional<Monitorador> optionalMonitorador = repository.findById(id);
        if(optionalMonitorador.isPresent()){
            Monitorador monitorador = optionalMonitorador.get();

            if(monitorador.isAtivo())
                throw new BusinessException(ErrorCode.MONITORADOR_JA_ATIVO);

            monitorador.ativar();
            repository.save(monitorador);
        } else throw new BusinessException(ErrorCode.MONITORADOR_NAO_ENCONTRADO);
    }

    public void desativarMonitorador(Long id){
        Optional<Monitorador> optionalMonitorador = repository.findById(id);
        if(optionalMonitorador.isPresent()){
            Monitorador monitorador = optionalMonitorador.get();

            if(!monitorador.isAtivo())
                throw new BusinessException(ErrorCode.MONITORADOR_JA_DESATIVADO);

            monitorador.desativar();
            repository.save(monitorador);
        } else throw new BusinessException(ErrorCode.MONITORADOR_NAO_ENCONTRADO);
    }

    // Pesquisas

    // Buscar por Email
    public List<ResponsePessoaDto> buscarPorEmail(String email){
        List<Monitorador> monitoradores = repository.findByEmailContaining(email);
        if (!monitoradores.isEmpty()){
            return monitoradores
                    .stream()
                    .map(m -> mapper.map(m, ResponsePessoaDto.class))
                    .collect(Collectors.toList());
        } else throw new BusinessException(ErrorCode.NENHUM_MONITORADOR_POR_EMAIL);
    }

    // Buscar por CPF
    public List<ResponsePessoaDto> buscarPorCpf(String cpf){
        cpf = cpf.replaceAll("[.]", "").replaceAll("[-]", "");
        List<Monitorador> monitoradores = repository.findByCpfContaining(cpf);
        if (!monitoradores.isEmpty()){
            return monitoradores
                    .stream()
                    .map(m -> mapper.map(m, ResponsePessoaDto.class))
                    .collect(Collectors.toList());
        } else throw new BusinessException(ErrorCode.PESSOA_POR_CPF);
    }

    // Buscar por CNPJ
    public List<ResponsePessoaDto> buscarPorCnpj(String cnpj){
        cnpj = cnpj.replaceAll("[.]", "").replaceAll("[/]", "").replaceAll("[-]", "");
        List<Monitorador> monitoradores = repository.findByCnpjContaining(cnpj);
        if (!monitoradores.isEmpty()){
            return monitoradores
                    .stream()
                    .map(m -> mapper.map(m, ResponsePessoaDto.class))
                    .collect(Collectors.toList());
        } else throw new BusinessException(ErrorCode.PESSOA_POR_CNPJ);
    }

    public List<ResponsePessoaDto> getAllPF(){
        List<Monitorador> pessoaFisicaList = repository.findByTipoPessoa(TipoPessoa.PESSOA_FISICA);
        return pessoaFisicaList.
                stream()
                .map(m -> mapper.map(m, ResponsePessoaDto.class))
                .collect(Collectors.toList());
    }

    public List<ResponsePessoaDto> getAllPJ(){
        List<Monitorador> pessoaFisicaList = repository.findByTipoPessoa(TipoPessoa.PESSOA_JURIDICA);
        return pessoaFisicaList.
                stream()
                .map(m -> mapper.map(m, ResponsePessoaDto.class))
                .collect(Collectors.toList());
    }


    @Transactional
    public String importarMonitoradores(File file) throws IOException {
        System.out.println(file);

        @Cleanup
        FileInputStream fileInputStream = new FileInputStream(file);

        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        List<Row> rows = (List<Row>) xlsxToList(sheet.iterator());
        rows.remove(0); // Remove o cabeçalho. O cabeçalho pode ser usado se quisermos.

        rows.forEach(row ->{
            List<Cell> cells = (List<Cell>) xlsxToList(row.cellIterator());
            RequestPessoaDto requestPessoaDto = new RequestPessoaDto();
            requestPessoaDto.setTipoPessoa((int) cells.get(0).getNumericCellValue());
            requestPessoaDto.setEmail(cells.get(1).getStringCellValue());
            requestPessoaDto.setDataNascimento((cells.get(2).getDateCellValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()); //conversão de data para localDate
            if (requestPessoaDto.getTipoPessoa() == TipoPessoa.PESSOA_FISICA){
                requestPessoaDto.setCpf(cells.get(3).getStringCellValue());
                requestPessoaDto.setRg(cells.get(4).getStringCellValue());
                requestPessoaDto.setNome(cells.get(5).getStringCellValue());
            }else {
                requestPessoaDto.setCnpj(cells.get(3).getStringCellValue());
                requestPessoaDto.setRazaoSocial(cells.get(4).getStringCellValue());
                requestPessoaDto.setInscricaoEstadual(cells.get(5).getStringCellValue());
            }

            try {
                cadastrarMonitorador(requestPessoaDto); // TODO validar! Não usa o @Valid.
            } catch (Exception e){
                throw new BusinessException(
                        "Seu arquivo possuí um erro na linha " + (rows.indexOf(row)+1) + ". Erro: " + e.getMessage(),
                        HttpStatus.BAD_REQUEST);
            }

        });

        return "Os monitoradores foram importados!";
    }

    private List<?> xlsxToList(Iterator<?> iterator){
        return IteratorUtils.toList(iterator);
    }

    // MÉTODOS ENCAPSULADOS
    private Monitorador getMonitoradorByTipo(RequestPessoaDto requestDto){
        if(requestDto.getTipoPessoa() == TipoPessoa.PESSOA_FISICA){
            return mapper.map(requestDto, PessoaFisica.class);
        } else{
            return mapper.map(requestDto, PessoaJuridica.class);
        }
    }

    // Validacoes
    public void monitoradorExiste(Long id){
        if(!repository.existsById(id))
            throw new BusinessException(ErrorCode.MONITORADOR_NAO_ENCONTRADO);
    }
}
