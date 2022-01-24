package com.algaworks.algafood.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeInput {

    @ApiModelProperty(example = "Campinas", position = 1, required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(position = 2)
    @Valid
    @NotNull
    private EstadoIdInput estado;

}
