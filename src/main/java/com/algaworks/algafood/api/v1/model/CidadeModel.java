package com.algaworks.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeModel extends RepresentationModel<CidadeModel> {

    @ApiModelProperty(example = "1", position = 1)
    private Long id;

    @ApiModelProperty(example = "São Paulo", position = 2)
    private String nome;

    @ApiModelProperty(position = 3)
    private EstadoModel estado;
}
