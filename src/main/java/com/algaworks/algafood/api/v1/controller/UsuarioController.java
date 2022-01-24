package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.model.input.SenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioInput;
import com.algaworks.algafood.api.v1.openapi.controller.UsuarioControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Autowired
    private UsuarioInputDisassembler usuarioInputDisassembler;

    @CheckSecurity.UsuarioGruposPermissoes.PodeConsultar
    @Override
    @GetMapping
    public CollectionModel<UsuarioModel> listar () {
        return usuarioModelAssembler.toCollectionModel(usuarioRepository.findAll());
    }

    @CheckSecurity.UsuarioGruposPermissoes.PodeConsultar
    @Override
    @GetMapping("/{usuarioId}")
    public UsuarioModel buscar (@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);

        return usuarioModelAssembler.toModel(usuario);
    }

    @CheckSecurity.UsuarioGruposPermissoes.PodeEditar
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel adicionar (@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
        usuario = cadastroUsuarioService.salvar(usuario);

        return usuarioModelAssembler.toModel(usuario);
    }

    @CheckSecurity.UsuarioGruposPermissoes.PodeAlterarUsuario
    @Override
    @PutMapping("/{usuarioId}")
    public UsuarioModel atualizar (@PathVariable Long usuarioId,
                                   @RequestBody @Valid UsuarioInput usuarioInput){
        Usuario usuarioAtual = cadastroUsuarioService.buscarOuFalhar(usuarioId);
        usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
        usuarioAtual = cadastroUsuarioService.salvar(usuarioAtual);

        return usuarioModelAssembler.toModel(usuarioAtual);
    }

    @CheckSecurity.UsuarioGruposPermissoes.PodeAlterarPropriaSenha
    @Override
    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha (@PathVariable Long usuarioId,
                                      @RequestBody @Valid SenhaInput senhaInput) {
        cadastroUsuarioService.alterSenha(usuarioId,
                senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());

    }

}
