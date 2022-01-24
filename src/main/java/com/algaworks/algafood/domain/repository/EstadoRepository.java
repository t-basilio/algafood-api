package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EstadoRepository extends CustomJpaRepository<Estado, Long> {
    //List<Estado> consultarPorNome(String nome); Apenas para esse repositorio
}
