package com.algaworks.algafood.api.v1.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PageModel")
@Getter
@Setter
public class PageModelOpenApi {

    @ApiModelProperty(example = "10", value = "Quantidade de resgistors por página")
    private Long size;

    @ApiModelProperty(example = "50", value = "Total de registros")
    private Long totalElements;

    @ApiModelProperty(example = "10", value = "Total de páginas")
    private Long totalPages;

    @ApiModelProperty(example = "0", value = "Número da página (começa em 0)")
    private Long number;
}