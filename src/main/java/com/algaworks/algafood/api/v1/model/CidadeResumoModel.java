package com.algaworks.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeResumoModel extends RepresentationModel<CidadeResumoModel> {

    @ApiModelProperty(example = "1", required = true)
    private Long id;

    @ApiModelProperty(example = "Uberlândia", required = true)
    private String nome;

    @ApiModelProperty(example = "Minas Gerais", required = true)
    private String estado;
}
