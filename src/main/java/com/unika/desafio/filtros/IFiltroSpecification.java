package com.unika.desafio.filtros;

import org.springframework.data.jpa.domain.Specification;

public interface IFiltroSpecification<T> {
    Specification<T> toSpecification();
}
