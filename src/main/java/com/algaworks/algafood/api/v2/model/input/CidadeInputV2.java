package com.algaworks.algafood.api.v2.model.input;

import com.algaworks.algafood.api.v1.model.input.EstadoIdInput;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel("CidadeInput")
public class CidadeInputV2 {

    @ApiModelProperty(example = "Campinas", position = 1, required = true)
    @NotBlank
    private String nomeCidade;

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long idEstado;

}
