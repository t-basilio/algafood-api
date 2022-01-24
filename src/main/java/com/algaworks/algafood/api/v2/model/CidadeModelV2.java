package com.algaworks.algafood.api.v2.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
@Getter
@Setter
@ApiModel("CidadeModel")
public class CidadeModelV2 extends RepresentationModel<CidadeModelV2> {

    @ApiModelProperty(example = "1", position = 1)
    private Long idCidade;

    @ApiModelProperty(example = "São Paulo", position = 2)
    private String nomeCidade;

    private Long idEstado;
    private String nomeEstado;
}
