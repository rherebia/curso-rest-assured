package br.com.caelum.leilao.teste;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;

import br.com.caelum.leilao.modelo.Leilao;
import br.com.caelum.leilao.modelo.Usuario;

public class LeilaoWSTest {

	@Test
	public void deveRetornarLeilaoPorId() {
		JsonPath jsonPath = given()
				.header("Accept", "application/json")
				.parameter("leilao.id", 1)
				.get("/leiloes/show")
				.andReturn()
				.jsonPath();
			
		Long idLeilao = jsonPath.getLong("leilao.id");
		
		assertEquals(1L, idLeilao.longValue());
	}
	
	@Test
	public void deveRetornarTotalDeLeiloes() {
		XmlPath path = given()
                .header("Accept", "application/xml")
                .get("/leiloes/total")
                .andReturn().xmlPath();

        int total = path.getInt("int");

        assertEquals(2, total);
	}
	
	@Test
	public void deveAdicionarLeilao() {
		Usuario mauricio = new Usuario(1l, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
        Leilao leiloes = new Leilao(1l, "Geladeira", 800.0, mauricio, false);

        XmlPath retorno = 
                given()
                    .header("Accept", "application/xml")
                    .contentType("application/xml")
                    .body(leiloes)
                .expect()
                    .statusCode(200)
                .when()
                    .post("/leiloes")
                .andReturn()
                    .xmlPath();

        Leilao resposta = retorno.getObject("leilao", Leilao.class);

        assertEquals("Geladeira", resposta.getNome());

        // deletando aqui
        given()
            .contentType("application/xml")
            .body(resposta)
        .expect()
            .statusCode(200)
        .when()
            .delete("/leiloes/deleta")
        .andReturn().asString();
	}
}
