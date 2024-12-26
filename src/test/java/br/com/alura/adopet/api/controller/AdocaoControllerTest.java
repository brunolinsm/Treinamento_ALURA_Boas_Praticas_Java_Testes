package br.com.alura.adopet.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.service.AdocaoService;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AdocaoControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private AdocaoService service;
	
	 @Autowired
	 private JacksonTester<SolicitacaoAdocaoDto> jsonDto;

	@Test
	void deveDevolverCodigo400ParaSolicitacaoDeAdocaoComErros() throws Exception {
		//ARRANGE
		String json = "{}";
		
		//ACT
		var response = mvc.perform(
				post("/adocoes")
					.content(json)
					.contentType(MediaType.APPLICATION_JSON)
				
		).andReturn().getResponse();
		
		//ASSERT
		Assertions.assertEquals(400, response.getStatus());
	}
	
	@Test
	void deveDevolverCodigo200ParaSolicitacaoDeAdocaoSemErros() throws Exception {
		//ARRANGE
		SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(1l, 1l, "Motivo qualquer");
		
		//ACT
		var response = mvc.perform(
				post("/adocoes")
					.content(jsonDto.write(dto).getJson())
					.contentType(MediaType.APPLICATION_JSON)
				
		).andReturn().getResponse();
		
		//ASSERT
		Assertions.assertEquals(200, response.getStatus());
		Assertions.assertEquals("Adoção solicitada com sucesso!", response.getContentAsString());
	}
}
