package com.algaworks.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "usuarios")
@Getter
@Setter
public class UsuarioModel extends RepresentationModel<UsuarioModel> {

    @ApiModelProperty(example = "1", position = 1)
    private Long id;

    @ApiModelProperty(example = "João da Silva", position = 2)
    private String nome;

    @ApiModelProperty(example = "joao.ger@algafood.com.br", position = 3)
    private String email;
}
