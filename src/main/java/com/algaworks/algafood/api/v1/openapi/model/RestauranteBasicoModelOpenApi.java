package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.CozinhaModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@ApiModel("RestauranteBasicoModel")
@Getter
@Setter
public class RestauranteBasicoModelOpenApi {

    @ApiModelProperty(example = "1", position = 1)
    private Long id;

    @ApiModelProperty(example = "Thai Gourmet", position = 2)
    private String nome;

    @ApiModelProperty(example = "12.90", position = 3)
    private BigDecimal taxaFrete;

    @ApiModelProperty(position = 4)
    private CozinhaModel cozinha;
}
