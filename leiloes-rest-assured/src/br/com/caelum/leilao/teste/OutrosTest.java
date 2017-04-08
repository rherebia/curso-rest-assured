package br.com.caelum.leilao.teste;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.*;

public class OutrosTest {

	@Test
	public void deveGerarUmCookie() {
		expect()
			.cookie("rest-assured", "funciona")
		.get("/cookies/teste");
	}
	
	@Test
	public void deveGerarUmHeader() {
		expect()
			.header("novo-header", "abc")
		.get("/cookies/teste");
	}
}
