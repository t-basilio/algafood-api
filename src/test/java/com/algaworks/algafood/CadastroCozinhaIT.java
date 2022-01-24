package com.algaworks.algafood;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {

    public static final int COZINHA_ID_INEXISTENTE = 100;

    private Cozinha cozinhaAmericana;
    private int quantidadeCozinhasCadastradas;
    private String jsonCorretoCozinhaChinesa;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    CozinhaRepository cozinhaRepository;

    @Before
    public void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        databaseCleaner.clearTables();
        jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
                "/json/correto/cozinha-chinesa.json");
        prepararDados();
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas(){

        given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveConterQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas(){

        given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .body("", hasSize(quantidadeCozinhasCadastradas))
                .body("nome", hasItems("Tailandesa", "Americana"));
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarCozinha(){
        given()
                .body(jsonCorretoCozinhaChinesa)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.CREATED.value());

    }

    @Test
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
        given()
                .pathParam("cozinhaId", cozinhaAmericana.getId())
                .accept(ContentType.JSON)
        .when()
                .get("/{cozinhaId}")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", equalTo(cozinhaAmericana.getNome()));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
        given()
                .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
                .accept(ContentType.JSON)
        .when()
                .get("/{cozinhaId}")
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados() {

        Cozinha cozinhaTailandesa = new Cozinha();
        cozinhaTailandesa.setNome("Tailandesa");
        cozinhaRepository.save(cozinhaTailandesa);

        cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome("Americana");
        cozinhaRepository.save(cozinhaAmericana);

        quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
    }

    //Testes de integração (serviços)
    /*
    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Test
    public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos(){
        //canário
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");

        //ação
        novaCozinha = cadastroCozinha.salvar(novaCozinha);

        //validação
        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();
        assertThat(novaCozinha.getNome()).isEqualTo("Chinesa");
    }

    @Test(expected = ConstraintViolationException.class)
    public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
        //cenário
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome(null);

        //ação
        novaCozinha = cadastroCozinha.salvar(novaCozinha);
    }

    @Test(expected = EntidadeEmUsoException.class)
    public void deveFalhar_QuandoExcluirCozinhaEmUso() {
        cadastroCozinha.excluir(1L);
    }

    @Test(expected = CozinhaNaoEncontradaException.class)
    public void deveFalhar_QuandoExcluirCozinhaInexistente() {
        cadastroCozinha.excluir(100L);
    }
     */
}
