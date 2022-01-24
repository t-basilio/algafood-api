package com.algaworks.algafood.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SenhaInput {

    @ApiModelProperty(example = "123@senha", position = 1)
    @NotBlank
    private String senhaAtual;

    @ApiModelProperty(example = "senha@321", position = 2)
    @NotBlank
    private String novaSenha;
}
