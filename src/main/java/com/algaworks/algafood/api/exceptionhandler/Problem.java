package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@ApiModel("Problema")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {

    @ApiModelProperty(example = "400", position = 1)
    private Integer status;

    @ApiModelProperty(example = "2021-06-23T21:06:25.66421Z", position = 5)
    private OffsetDateTime timestamp;

    @ApiModelProperty(example = "https://algafood.com.br/dados-invalidos", position = 10)
    private String type;

    @ApiModelProperty(example = "Dados inválidos", position = 15)
    private String title;

    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente",
            position = 20)
    private String datail;

    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente",
            position = 25)
    private String userMessage;

    @ApiModelProperty(value = "Lista de objetos ou campos que geraram erros (opcional)", position = 30)
    private List<Object> objects;

    @ApiModel("ObjetoProblemas")
    @Builder
    @Getter
    public static class Object {

        @ApiModelProperty("preco")
        private String name;

        @ApiModelProperty("O preço é obrigatório")
        private String userMessage;
    }
}
