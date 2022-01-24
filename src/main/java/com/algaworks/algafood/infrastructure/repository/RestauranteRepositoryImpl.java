package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;
import org.hibernate.criterion.CriteriaQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired @Lazy
    private RestauranteRepository restauranteRepository;

    @Override
    public List<Restaurante> consultaDinamica(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        var jpql = new StringBuilder();
        var parametros = new HashMap<String,Object>();

        jpql.append("FROM Restaurante WHERE 0 = 0 ");

        if (StringUtils.hasLength(nome)) {
            jpql.append("AND nome LIKE :nome ");
            parametros.put("nome", "%" + nome + "%");
        }

        if (taxaFreteInicial != null ){
            jpql.append("AND taxaFrete >= :taxaInicial ");
            parametros.put("taxaInicial", taxaFreteInicial);
        }

        if (taxaFreteFinal != null ){
            jpql.append("AND taxaFrete <= :taxaFinal ");
            parametros.put("taxaFinal", taxaFreteFinal);
        }

        TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);
        parametros.forEach((chave, valor) -> query.setParameter(chave, valor));
        return query.getResultList();
    }

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

        var builder = manager.getCriteriaBuilder();

        var criteria = builder.createQuery(Restaurante.class);

        var root = criteria.from(Restaurante.class); //FROM Restaurante

        var predicates = new ArrayList<Predicate>();

        if (StringUtils.hasText(nome)) {
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }
        if (taxaFreteInicial != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }
        if (taxaFreteFinal != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }

        criteria.where(predicates.toArray(new Predicate[0]));

        var query = manager.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public List<Restaurante> findComFreteGratis(String nome) {
        return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
    }

}
