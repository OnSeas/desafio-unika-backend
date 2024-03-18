package com.unika.desafio.filtros;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.unika.desafio.model.Monitorador;
import com.unika.desafio.model.TipoPessoa;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FiltroMonitoradorDTO implements IFiltroSpecification<Monitorador> {
    private String busca;
    private TipoBusca tipoBusca;
    private Boolean pessoaFisica;
    private Boolean pessoaJuridica;
    private Boolean soAtivados;

    public FiltroMonitoradorDTO(String busca, TipoBusca tipoBusca, Boolean pessoaFisica, Boolean pessoaJuridica, Boolean soAtivados) {
        this.busca = busca;
        this.tipoBusca = tipoBusca;
        this.pessoaFisica = pessoaFisica;
        this.pessoaJuridica = pessoaJuridica;
        this.soAtivados = soAtivados;

        if(this.pessoaFisica == null) this.pessoaFisica = false;
        if(this.pessoaJuridica == null) this.pessoaJuridica = false;
        if(this.soAtivados == null) this.soAtivados = false;
    }

    @Override
    public Specification<Monitorador> toSpecification() {
        return new Specification<Monitorador>() {
            @Override
            public Predicate toPredicate(Root<Monitorador> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) { // retorna a query que vai ser usada para filtrar
                List<Predicate> predicates = new ArrayList<>();
                if (!(busca == null || busca.isBlank() || tipoBusca == null)) {
                    switch (tipoBusca){
                        case EMAIL -> predicates.add(criteriaBuilder.like(root.get("email"), "%"+busca+"%"));
                        case CPF -> predicates.add(criteriaBuilder.like(root.get("cpf"), "%"+busca+"%"));
                        case CNPJ -> predicates.add(criteriaBuilder.like(root.get("cnpj"), "%"+busca+"%"));
                    };
                }
                if (soAtivados) {
                    System.out.println("Só ativados!");
                    predicates.add(criteriaBuilder.equal(root.get("ativo"), true));
                }

                if (pessoaFisica && !pessoaJuridica) {
                    predicates.add(criteriaBuilder.equal(root.get("tipoPessoa"), TipoPessoa.PESSOA_FISICA));
                } else if (!pessoaFisica && pessoaJuridica) {
                    predicates.add(criteriaBuilder.equal(root.get("tipoPessoa"), TipoPessoa.PESSOA_JURIDICA));
                }
                return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
            }
        };
    }

    @Getter
    public enum TipoBusca{
        CPF(0),
        CNPJ(1),
        EMAIL(2);

        private final int value;

        TipoBusca(int value){
            this.value = value;
        }

        public static TipoBusca getTipo(Integer codigo){
            if (codigo == null) return null;
            else if(codigo == 0){
                return TipoBusca.CPF;
            } else if (codigo == 1){
                return TipoBusca.CNPJ;
            } else if (codigo == 2){
                return TipoBusca.EMAIL;
            } else return null;
        }
    }
}


