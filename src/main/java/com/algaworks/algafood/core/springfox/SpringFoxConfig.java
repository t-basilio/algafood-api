package com.algaworks.algafood.core.springfox;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.*;
import com.algaworks.algafood.api.v1.openapi.model.*;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.CozinhaModelV2;
import com.algaworks.algafood.api.v2.openapi.model.CidadesModelV2OpenApi;
import com.algaworks.algafood.api.v2.openapi.model.CozinhasModelV2OpenApi;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

    @Bean
    public Docket apiDocketV1(){
        var typeResolver = new TypeResolver();

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("V1")
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
                    .paths(PathSelectors.ant("/v1/**"))
                    .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalGetResponseMessage())
                .globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessage())
                .globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessage())
                .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessage())
                .additionalModels(typeResolver.resolve(Problem.class))
                .ignoredParameterTypes(ServletWebRequest.class,
                        URL.class, URI.class, URLStreamHandler.class, Resource.class, File.class, InputStream.class)
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, CozinhaModel.class),
                        CozinhasModelOpenApi.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, CidadeModel.class),
                        CidadesModelOpenApi.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, EstadoModel.class),
                        EstadosModelOpenApi.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, FormaPagamentoModel.class),
                        FormasPagamentoModelOpenApi.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, GrupoModel.class),
                        GruposModelOpenApi.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, PermissaoModel.class),
                        PermissoesModelOpenApi.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, PedidoResumoModel.class),
                        PedidosResumoModelOpenApi.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, ProdutoModel.class),
                        ProdutosModelOpenApi.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, RestauranteBasicoModel.class),
                        RestaurantesBasicoModelOpenApi.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, UsuarioModel.class),
                        UsuariosModelOpenApi.class))

                .securitySchemes(Arrays.asList(securityScheme()))
                .securityContexts(Arrays.asList(securityContext()))

                .apiInfo(apiInfoV1())
                .tags(new Tag("Cidades", "Gerencia as cidades"),
                      new Tag("Grupos","Gerencia os grupos de usuários"),
                      new Tag("Cozinhas","Gerencia as cozinhas"),
                      new Tag("Formas de pagamento","Gerencia as formas de pagamento"),
                      new Tag("Pedidos","Gerencia os pedidos"),
                      new Tag("Restaurantes","Gerencia os restaurantes"),
                      new Tag("Estados","Gerencia os estados"),
                      new Tag("Produtos","Gerencia os produtos de restaurantes"),
                      new Tag("Usuários","Gerencia os usuários"),
                      new Tag("Estatísticas","Estatísticas da AlgaFood"),
                      new Tag("Permissões", "Gerencia as permissões"));
    }

    private SecurityScheme securityScheme() {
        return new OAuthBuilder()
                .name("AlgaFood")
                .grantTypes(grantTypes())
                .scopes(scopes())
                .build();
    }

    private SecurityContext securityContext() {
        var securityReference = SecurityReference.builder()
                .reference("AlgaFood")
                .scopes(scopes().toArray(new AuthorizationScope[0]))
                .build();

        return SecurityContext.builder()
                .securityReferences(Arrays.asList(securityReference))
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<GrantType> grantTypes() {
        return Arrays.asList(new ResourceOwnerPasswordCredentialsGrant("/oauth/token"));
    }

    private List<AuthorizationScope> scopes() {
        return Arrays.asList(new AuthorizationScope("READ", "Acesso de leitura"),
                new AuthorizationScope("WRITE", "Acesso de escrita"));
    }

    private List<ResponseMessage> globalDeleteResponseMessage() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .responseModel(new ModelRef("Problema"))
                        .message("Requisição inválida (erro do cliente)")
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .responseModel(new ModelRef("Problema"))
                        .message("Erro interno do servidor")
                        .build()
        );
    }

    private List<ResponseMessage> globalPostPutResponseMessage() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Requisição inválida (erro do cliente)")
                        .responseModel(new ModelRef("Problema"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Erro interno do servidor")
                        .responseModel(new ModelRef("Problema"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.NOT_ACCEPTABLE.value())
                        .message("Recurso não possui representação que poderia ser aceita pelo consumidor")
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                        .message("Requisição recusada porque o corpo está em um formato não suportado")
                        .build()
        );
    }

    private List<ResponseMessage> globalGetResponseMessage() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Erro interno do servidor")
                    .responseModel(new ModelRef("Problema"))
                    .build(),
                new ResponseMessageBuilder()
                    .code(HttpStatus.NOT_ACCEPTABLE.value())
                    .message("Recurso não possui representação que poderia ser aceita pelo consumidor")
                    .build()
        );
    }

    @Bean
    public Docket apiDocketV2(){
        var typeResolver = new TypeResolver();

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("V2")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
                .paths(PathSelectors.ant("/v2/**"))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalGetResponseMessage())
                .globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessage())
                .globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessage())
                .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessage())
                .additionalModels(typeResolver.resolve(Problem.class))
                .ignoredParameterTypes(ServletWebRequest.class,
                        URL.class, URI.class, URLStreamHandler.class, Resource.class, File.class, InputStream.class)
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, CozinhaModelV2.class),
                        CozinhasModelV2OpenApi.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, CidadeModelV2.class),
                        CidadesModelV2OpenApi.class))

                .apiInfo(apiInfoV2())

                .tags(new Tag("Cidades", "Gerencia as cidades"),
                        new Tag("Cozinhas", "Gerencia as cozinhas"));

    }

    private ApiInfo apiInfoV1(){
        return new ApiInfoBuilder()
                .title("AlgaFood API")
                .description("API aberta para clientes e restaurantes.")
                .version("1")
                .contact(new Contact("AlgaWorks", "https://algaworks.com", "contato@algaworks.com"))
                .build();
    }

    private ApiInfo apiInfoV2(){
        return new ApiInfoBuilder()
                .title("AlgaFood API")
                .description("API aberta para clientes e restaurantes")
                .version("2")
                .contact(new Contact("AlgaWorks", "https://algaworks.com", "contato@algaworks.com"))
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
