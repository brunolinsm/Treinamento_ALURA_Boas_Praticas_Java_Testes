package br.com.alura.adopet.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.alura.adopet.api.service.PetService;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private PetService service;

	@Test
	void deveriaDevolverCodigo200ParaListarTodosPetsDisponiveisDoAbrigo() throws Exception {
		//ACT
		var response = mvc.perform(
				get("/pets")
					.contentType(MediaType.APPLICATION_JSON)
					).andReturn().getResponse();
		
		//ASSERT
		Assertions.assertEquals(200, response.getStatus());
	}

}
