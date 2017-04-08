package br.com.caelum.leilao.teste;

import org.junit.Test;

import com.jayway.restassured.path.xml.XmlPath;

import br.com.caelum.leilao.modelo.Usuario;

import static com.jayway.restassured.RestAssured.*;

import static org.junit.Assert.*;

import java.util.List;

public class UsuarioWSTest {

	@Test
	public void deveRetorarUsuarios() {
		XmlPath xmlPath = given()
				.header("Accept", "application/xml")
				.get("/usuarios")
				.andReturn()
				.xmlPath();
		
		List<Usuario> usuarios = xmlPath.getList("list.usuario", Usuario.class);
		
		Usuario esperado1 = new Usuario(1L, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
		Usuario esperado2 = new Usuario(2L, "Guilherme Silveira", "guilherme.silveira@caelum.com.br");
		
		assertEquals(esperado1, usuarios.get(0));
		assertEquals(esperado2, usuarios.get(1));
	}
}
