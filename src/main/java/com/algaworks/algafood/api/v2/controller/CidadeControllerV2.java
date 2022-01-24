package com.algaworks.algafood.api.v2.controller;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v2.assembler.CidadeInputDisassemblerV2;
import com.algaworks.algafood.api.v2.assembler.CidadeModelAssemblerV2;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;
import com.algaworks.algafood.api.v2.openapi.controller.CidadeControllerV2OpenApi;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 implements CidadeControllerV2OpenApi {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CidadeInputDisassemblerV2 cidadeInputDisassembler;

    @Autowired
    private CidadeModelAssemblerV2 cidadeModelAssembler;

    @Override
    @GetMapping
    public CollectionModel<CidadeModelV2> listar () {
         return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
    }

    @Override
    @GetMapping("/{cidadeId}")
    public CidadeModelV2 buscar ( @PathVariable Long cidadeId) {
        Cidade cidade = cadastroCidadeService.buscarOuFalhar(cidadeId);

        return  cidadeModelAssembler.toModel(cidade);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModelV2 adicionar (@RequestBody @Valid CidadeInputV2 cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

            cidade = cadastroCidadeService.salvar(cidade);
            CidadeModelV2 cidadeModel = cidadeModelAssembler.toModel(cidade);

            ResourceUriHelper.addUriResponseHeader(cidadeModel.getIdCidade());
            return cidadeModel;
        } catch (EstadoNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @PutMapping("/{cidadeId}")
    public CidadeModelV2 atualizar (@PathVariable Long cidadeId, @RequestBody @Valid CidadeInputV2 cidadeInput) {
            try {
                Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(cidadeId);

                cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

                cidadeAtual = cadastroCidadeService.salvar(cidadeAtual);

                return cidadeModelAssembler.toModel(cidadeAtual);
            } catch (EstadoNaoEncontradoException e){
                throw new NegocioException(e.getMessage(), e);
            }
    }

    @Override
    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover (@PathVariable Long cidadeId) {
        cadastroCidadeService.excluir(cidadeId);
    }

}
