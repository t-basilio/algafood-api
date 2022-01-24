package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Cidade;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends CustomJpaRepository<Cidade, Long> {
    //List<Cidade> colsultarPorNome(String nome); //Apenas para esse repositorio
}
