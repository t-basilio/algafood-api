package com.algaworks.algafood.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoInput {

    @ApiModelProperty(example = "Espetinho de Cupim", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "Acompanha farinha, mandioca e vinagrete", required = true)
    @NotBlank
    private String descricao;

    @ApiModelProperty(example = "12.50", required = true)
    @PositiveOrZero
    @NotNull
    private BigDecimal preco;

    @ApiModelProperty(example = "true", required = true)
    @NotNull
    private Boolean ativo;
}
