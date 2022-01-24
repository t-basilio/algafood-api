package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Estados")
public interface EstadosControllerOpenApi {

    @ApiOperation(value = "Lista os estados")
    CollectionModel<EstadoModel> listar();

    @ApiOperation(value = "Busca um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do estado inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)})
    EstadoModel buscar (@ApiParam(value = "ID de um estado", example = "1", required = true) Long estadoId);

    @ApiOperation(value = "Cadastra um estado")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Estado cadastrado")})
    EstadoModel adicionar (@ApiParam(name = "corpo", value = "Representação de um novo estado", required = true)
                                   EstadoInput estadoInput);

    @ApiOperation(value = "Atualiza um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estado atualizado"),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)})
    EstadoModel atualizar (@ApiParam(value = "ID de um estado", example = "1", required = true) Long estadoId,
                           @ApiParam(name = "corpo", value = "Representação de um novo estado", required = true)
                                   EstadoInput estadoInput);

    @ApiOperation(value = "Exclui um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Estado excluído"),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)})
    void remover (@ApiParam(value = "ID de um estado", example = "1", required = true) Long estadoId);

}
