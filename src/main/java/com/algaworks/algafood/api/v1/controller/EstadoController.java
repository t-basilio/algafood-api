package com.algaworks.algafood.api.v1.controller;
import com.algaworks.algafood.api.v1.assembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;
import com.algaworks.algafood.api.v1.openapi.controller.EstadosControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadosControllerOpenApi {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @Autowired
    private EstadoModelAssembler estadoModelAssembler;

    @Autowired
    private EstadoInputDisassembler estadoInputDisassembler;

    @CheckSecurity.Estados.PodeConsultar
    @Override
    @GetMapping
    public CollectionModel<EstadoModel> listar(){
        return estadoModelAssembler.toCollectionModel(estadoRepository.findAll());
    }

    @CheckSecurity.Estados.PodeConsultar
    @Override
    @GetMapping("/{estadoId}")
    public EstadoModel buscar (@PathVariable Long estadoId) {
        Estado estado = cadastroEstadoService.buscarOuFalhar(estadoId);

        return estadoModelAssembler.toModel(estado);
    }

    @CheckSecurity.Estados.PodeEditar
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModel adicionar (@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = estadoInputDisassembler.toDomainObjetc(estadoInput);

        estado = cadastroEstadoService.salvar(estado);

        return estadoModelAssembler.toModel(estado);
    }

    @CheckSecurity.Estados.PodeEditar
    @Override
    @PutMapping("/{estadoId}")
    public EstadoModel atualizar (@PathVariable Long estadoId,
                                             @RequestBody @Valid EstadoInput estadoInput) {
        Estado estadoAtual = cadastroEstadoService.buscarOuFalhar(estadoId);

        estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);

        estadoAtual = cadastroEstadoService.salvar(estadoAtual);
        
        return estadoModelAssembler.toModel(estadoAtual);
    }

    @CheckSecurity.Estados.PodeEditar
    @Override
    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover (@PathVariable Long estadoId) {
        cadastroEstadoService.excluir(estadoId);
    }
}
