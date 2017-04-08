package br.com.caelum.leilao.teste;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;

import br.com.caelum.leilao.modelo.Usuario;

public class UsuarioWSTest {
	
	private Usuario mauricio;
	private Usuario guilherme;
	
	@Before
	public void setup() {
		mauricio = new Usuario(1L, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
		guilherme = new Usuario(2L, "Guilherme Silveira", "guilherme.silveira@caelum.com.br");
		
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
	}

	@Test
	public void deveRetorarUsuarios() {
		XmlPath xmlPath = given()
				.header("Accept", "application/xml")
				.get("/usuarios")
				.andReturn()
				.xmlPath();
		
		List<Usuario> usuarios = xmlPath.getList("list.usuario", Usuario.class);
		
		assertEquals(mauricio, usuarios.get(0));
		assertEquals(guilherme, usuarios.get(1));
	}
	
	@Test
	public void deveRetornarUsuarioPeloId() {
		JsonPath jsonPath = given()
			.header("Accept", "application/json")
			.parameter("usuario.id", 1)
			.get("/usuarios/show")
			.andReturn()
			.jsonPath();
		
		Usuario usuario = jsonPath.getObject("usuario", Usuario.class);
		
		assertEquals(mauricio, usuario);
	}
	
	@Test
	public void deveAdicionarUsuario() {
		Usuario joao = new Usuario("Joao da Silva", "joao@dasilva.com");
		
		XmlPath xmlPath = given().header("Accept", "application/xml")
				.contentType(ContentType.XML)
				.body(joao)
				.expect()
					.statusCode(200)
				.when()
					.post("/usuarios")
				.andReturn()
					.xmlPath();
		
		Usuario usuarioResposta = xmlPath.getObject("usuario", Usuario.class);
		
		assertEquals("Joao da Silva", usuarioResposta.getNome());
		assertEquals("joao@dasilva.com", usuarioResposta.getEmail());
		
		given()
		.contentType("application/xml").body(usuarioResposta)
		.expect().statusCode(200)
		.when().delete("/usuarios/deleta").andReturn().asString();
	}
}
