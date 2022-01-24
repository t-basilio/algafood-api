package com.algaworks.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "fotos")
@Getter
@Setter
public class FotoProdutoModel extends RepresentationModel<FotoProdutoModel> {

    @ApiModelProperty(value = "65a0ad71-2c5e-40e8-a2c4-c529884d92de_ribs.jpeg")
    private String nomeArquivo;

    @ApiModelProperty(value = "Ribs")
    private String descricao;

    @ApiModelProperty(value = "image/jpeg")
    private String contentType;

    @ApiModelProperty(value = "202912")
    private Long tamanho;
}
