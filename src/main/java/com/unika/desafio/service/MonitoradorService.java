package com.unika.desafio.service;

import com.unika.desafio.dto.*;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import com.unika.desafio.model.*;
import com.unika.desafio.repository.MonitoradorRepository;
import com.unika.desafio.validations.monitorador.IMonitoradorValid;
import lombok.Cleanup;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MonitoradorService {
    @Autowired
    private MonitoradorRepository repository;

    @Autowired
    private EnderecoService enderecoService;

    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    private List<IMonitoradorValid> monitoradorValid;

    @Transactional
    public ResponsePessoaDto cadastrarMonitorador(RequestPessoaDto requestDto){
        monitoradorValid.forEach(v -> v.validar(requestDto)); // Validações do request de monitorador
        Monitorador monitorador = getMonitoradorByTipo(requestDto);
        monitorador.ativar();

        Monitorador monitoradorBD = repository.save(monitorador);
        requestDto.getEnderecoList().forEach(e -> {
            try {
                enderecoService.cadastrarEndereco(monitoradorBD, e);
            } catch (Exception ex) {
                throw new BusinessException(ex.getMessage());
            }
        });
        return mapper.map(monitorador, ResponsePessoaDto.class);
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
        monitoradorValid.forEach(v -> v.validar(requestDto, id)); // Validações do request de monitorador

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

    public ResponseEnderecoDto deletarMonitorador(Long id){
        Optional<Monitorador> monitoradorOptional = repository.findById(id);
        if (monitoradorOptional.isPresent()){
            repository.deleteById(id);
            return mapper.map(monitoradorOptional.get(), ResponseEnderecoDto.class);
        } else throw new BusinessException(ErrorCode.MONITORADOR_NAO_ENCONTRADO);

    }

    public ResponsePessoaDto ativarMonitorador(Long id){
        Optional<Monitorador> optionalMonitorador = repository.findById(id);
        if(optionalMonitorador.isPresent()){
            Monitorador monitorador = optionalMonitorador.get();

            if(monitorador.isAtivo())
                throw new BusinessException(ErrorCode.MONITORADOR_JA_ATIVO);

            monitorador.ativar();
            return mapper.map(repository.save(monitorador), ResponsePessoaDto.class);
        } else throw new BusinessException(ErrorCode.MONITORADOR_NAO_ENCONTRADO);
    }

    public ResponsePessoaDto desativarMonitorador(Long id){
        Optional<Monitorador> optionalMonitorador = repository.findById(id);
        if(optionalMonitorador.isPresent()){
            Monitorador monitorador = optionalMonitorador.get();

            if(!monitorador.isAtivo())
                throw new BusinessException(ErrorCode.MONITORADOR_JA_DESATIVADO);

            monitorador.desativar();
            return mapper.map(repository.save(monitorador), ResponsePessoaDto.class);
        } else throw new BusinessException(ErrorCode.MONITORADOR_NAO_ENCONTRADO);
    }

    // Pesquisas
    public List<ResponsePessoaDto> buscarMonitoradoresFiltro(FiltroDTO filtro){ // TODO descobrir se tem uma maneira mais eficiente (mais rápida)
        System.out.println(filtro);

        List<Monitorador> monitoradorList;
        if (filtro.getBusca() == null || filtro.getBusca().isBlank() || filtro.getTipoBusca() == null) monitoradorList = repository.findAll();
        else switch (filtro.getTipoBusca()){
            case EMAIL ->monitoradorList = repository.findByEmailContaining(filtro.getBusca());
            case CPF -> {filtro.setBusca(limparBusca(filtro.getBusca())); monitoradorList = repository.findByCpfContaining(filtro.getBusca());}
            case CNPJ -> {filtro.setBusca(limparBusca(filtro.getBusca())); monitoradorList = repository.findByCnpjContaining(filtro.getBusca());}
            default -> throw new BusinessException("Algo deu errado na pesquisa!");
        };

        if (filtro.getSoAtivados()) monitoradorList = monitoradorList.stream().filter(Monitorador::isAtivo).collect(Collectors.toList());

        if(filtro.getPessoaJuridica() || filtro.getPessoaFisica()){ // Se os dois forem falso mostra ambos.
            if (!filtro.getPessoaFisica()) monitoradorList = monitoradorList.stream().filter(m -> !m.getTipoPessoa().equals(TipoPessoa.PESSOA_FISICA)).collect(Collectors.toList());
            if (!filtro.getPessoaJuridica()) monitoradorList = monitoradorList.stream().filter(m -> !m.getTipoPessoa().equals(TipoPessoa.PESSOA_JURIDICA)).collect(Collectors.toList());
        }

        return monitoradorList
                .stream()
                .map(m -> mapper.map(m, ResponsePessoaDto.class))
                .collect(Collectors.toList());
    }

    private String limparBusca(String busca){
        return busca.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
    }

    // Funções usando apache POI, referências usadas: https://www.devmedia.com.br/apache-poi-manipulando-documentos-em-java/31778
    public File exportarMonitoradoresXlsx(){
        final String PATH = "C:\\Projetos\\zArquivos\\excel\\";

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheetMonitoradores = workbook.createSheet("monitoradores");

        List<ResponsePessoaDto> monitoradoresList = listarMonitoradores();

        String[] headers = {"Tipo pessoa", "Email", "Data de nascimento", "Nome", "RG", "CPF", "Razão Social", "CNPJ", "Inscrição Estadual",
        "Endereço", "Número", "CEP", "Bairro", "Telefone", "Cidade", "Estado", "Principal"};

        int rownum = 0;
        Row row = sheetMonitoradores.createRow(rownum++);
        for (int i = 0; i < headers.length; i++){
            Cell cellHeader = row.createCell(i);
            cellHeader.setCellValue(headers[i]);
        }

        for (ResponsePessoaDto monitorador : monitoradoresList) {
            row = sheetMonitoradores.createRow(rownum++);
            int cellnum = 0;

            Cell cellTipoPessoa = row.createCell(cellnum++);
            cellTipoPessoa.setCellValue(monitorador.getTipoPessoa().getCodigo()); // Tipo pessoa sendo colcoado como int 0 ou 1

            Cell cellEmail = row.createCell(cellnum++);
            cellEmail.setCellValue(monitorador.getEmail());

            Cell cellDataNascimento = row.createCell(cellnum++);
            cellDataNascimento.setCellValue(monitorador.getDataNascimento().toString());

            Cell cellNome = row.createCell(cellnum++);
            Cell cellRg = row.createCell(cellnum++);
            Cell cellCpf = row.createCell(cellnum++);

            Cell cellRazaoSocial = row.createCell(cellnum++);
            Cell cellCnpj = row.createCell(cellnum++);
            Cell cellInscricaoEstadual = row.createCell(cellnum++);

            if (monitorador.getTipoPessoa().equals(TipoPessoa.PESSOA_FISICA)){

                cellNome.setCellValue(monitorador.getNome());
                cellRg.setCellValue(monitorador.getRg());
                cellCpf.setCellValue(monitorador.getCpf());

                cellRazaoSocial.setCellValue("null");
                cellCnpj.setCellValue("null");
                cellInscricaoEstadual.setCellValue("null");
            }else {
                cellNome.setCellValue("null");
                cellRg.setCellValue("null");
                cellCpf.setCellValue("null");

                cellRazaoSocial.setCellValue(monitorador.getRazaoSocial());
                cellCnpj.setCellValue(monitorador.getCnpj());
                cellInscricaoEstadual.setCellValue(monitorador.getInscricaoEstadual());
            }

            List<ResponseEnderecoDto> enderecoList = monitorador.getEnderecoList();
            for (ResponseEnderecoDto endereco : enderecoList) {
                Cell cellEnd = row.createCell(cellnum++);
                cellEnd.setCellValue(endereco.getEndereco());

                Cell cellNumero = row.createCell(cellnum++);
                cellNumero.setCellValue(endereco.getNumero());

                Cell cellCep = row.createCell(cellnum++);
                cellCep.setCellValue(endereco.getCep());

                Cell cellBairro = row.createCell(cellnum++);
                cellBairro.setCellValue(endereco.getBairro());

                Cell cellTelefone = row.createCell(cellnum++);
                cellTelefone.setCellValue(endereco.getTelefone());

                Cell cellCidade = row.createCell(cellnum++);
                cellCidade.setCellValue(endereco.getCidade());

                Cell cellEstado = row.createCell(cellnum++);
                cellEstado.setCellValue(endereco.getEstado().toString());

                Cell cellPrincipal = row.createCell(cellnum++);
                cellPrincipal.setCellValue(endereco.getPrincipal());
            }

        }

        try {
            FileOutputStream out =
                    new FileOutputStream(PATH + "monitoradores-" + LocalDate.now() + ".xlsx");
            workbook.write(out);
            out.close();
            System.out.println("Monitoradores exportados para planilha xlsx");

            return new File(PATH + "monitoradores-" + LocalDate.now() + ".xlsx");

        } catch (IOException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Transactional
    public String importarMonitoradores(File file) throws IOException {
        if(!file.getName().endsWith(".xlsx") && !file.getName().endsWith(".xls")){
            throw new BusinessException(ErrorCode.TIPO_ARQUIVO_INVALIDO);
        }

        @Cleanup // Fecha o arquivo após ser usado
        FileInputStream fileInputStream = new FileInputStream(file);

        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        List<Row> rows = (List<Row>) toList(sheet.iterator());
        rows.remove(0);

        for(int r = 0; r < rows.size(); r++){
            Row row = rows.get(r);

            if (row.getLastCellNum() <= 0) break; // Em caso de o arquivo vier com linhas em branco no final
            List<Cell> cells = (List<Cell>) toList(row.cellIterator());
            RequestPessoaDto requestPessoaDto = new RequestPessoaDto();
            int i=0;
            try {
                // Informações do monitorador
                requestPessoaDto.setTipoPessoa(TipoPessoa.getTipo((int) cells.get(i++).getNumericCellValue()));
                requestPessoaDto.setEmail(cells.get(i++).getStringCellValue());
                requestPessoaDto.setDataNascimento(LocalDate.parse(cells.get(i++).getStringCellValue())); // Conversão de string para LocalDate
                requestPessoaDto.setNome(cells.get(i++).getStringCellValue());
                requestPessoaDto.setRg(cells.get(i++).getStringCellValue());
                requestPessoaDto.setCpf(cells.get(i++).getStringCellValue());
                requestPessoaDto.setRazaoSocial(cells.get(i++).getStringCellValue());
                requestPessoaDto.setCnpj(cells.get(i++).getStringCellValue());
                requestPessoaDto.setInscricaoEstadual(cells.get(i++).getStringCellValue());

                List<RequestEnderecoDto> requestEndList = new ArrayList<>();

                while (i < cells.size()){ // Pegando endereços
                    RequestEnderecoDto enderecoDto = new RequestEnderecoDto();
                    enderecoDto.setEndereco(cells.get(i++).getStringCellValue());
                    enderecoDto.setNumero(cells.get(i++).getStringCellValue());
                    enderecoDto.setCep(cells.get(i++).getStringCellValue());
                    enderecoDto.setBairro(cells.get(i++).getStringCellValue());
                    enderecoDto.setTelefone(cells.get(i++).getStringCellValue());
                    enderecoDto.setCidade(cells.get(i++).getStringCellValue());
                    enderecoDto.setEstado(UF.valueOf(cells.get(i++).getStringCellValue()));
                    enderecoDto.setPrincipal(cells.get(i++).getBooleanCellValue());

                    requestEndList.add(enderecoDto);
                }

                requestPessoaDto.setEnderecoList(requestEndList);

                System.out.println(requestPessoaDto);
                cadastrarMonitorador(requestPessoaDto);
            } catch (BusinessException e){
                throw new BusinessException(
                        "Seu arquivo possuí um erro na linha " + (rows.indexOf(row)+1) + ". Erro: " + e.getMessage());
            } catch (Exception e){
                throw new BusinessException(
                        "Seu arquivo possuí um erro na linha " + (rows.indexOf(row)+1) + ". Erro: Campo em formato inesperado.");
            }
        }

        return "Os monitoradores foram importados!";
    }

    private List<?> toList(Iterator<?> iterator){
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
}
