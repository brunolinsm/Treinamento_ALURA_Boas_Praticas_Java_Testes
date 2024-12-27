package br.com.alura.adopet.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.service.TutorService;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TutorControllerTest {
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private TutorService tutorService;
	
	 @Autowired
	 private JacksonTester<CadastroTutorDto> jsonCadastroTutorDto;
	 
	 @Autowired
	 private JacksonTester<AtualizacaoTutorDto> jsonAtualizacaoTutorDto;
	 
	 @Mock
	 private Tutor tutor;

	 @Test
		void deveriaDevolverCodigo200ParaCadastrarTutorSemErros() throws Exception {
			//ARRANGE
		 	CadastroTutorDto dto = new CadastroTutorDto("Bruno", "81995588956", "exemplo@exemplo.com");
			
			//ACT
			var response = mvc.perform(
					post("/tutores")
						.content(jsonCadastroTutorDto.write(dto).getJson())
						.contentType(MediaType.APPLICATION_JSON)
					
			).andReturn().getResponse();
			
			//ASSERT
			Assertions.assertEquals(200, response.getStatus());
		}
	 
	 @Test
		void deveriaDevolverCodigo400ParaCadastrarTutorComErros() throws Exception {
			//ARRANGE
			String json = """
	                {
	                    
	                }
	                """;
			
			//ACT
			var response = mvc.perform(
					post("/tutores")
						.content(json)
						.contentType(MediaType.APPLICATION_JSON)
					
			).andReturn().getResponse();
			
			//ASSERT
			Assertions.assertEquals(400, response.getStatus());
		}
	 
	 @Test
		void deveriaDevolverCodigo200ParaAtualizarTutorSemErros() throws Exception {
			//ARRANGE
		 	AtualizacaoTutorDto dto = new AtualizacaoTutorDto(1l,"Bruno", "81995588956", "exemplo@exemplo.com");
			
			//ACT
			var response = mvc.perform(
					put("/tutores")
						.content(jsonAtualizacaoTutorDto.write(dto).getJson())
						.contentType(MediaType.APPLICATION_JSON)
					
			).andReturn().getResponse();
			
			//ASSERT
			Assertions.assertEquals(200, response.getStatus());
		}
	 
	 @Test
		void deveriaDevolverCodigo400ParaAtualizarTutorComErros() throws Exception {
			//ARRANGE
			String json = """
	                {
	                    
	                }
	                """;
			
			//ACT
			var response = mvc.perform(
					put("/tutores")
						.content(json)
						.contentType(MediaType.APPLICATION_JSON)
					
			).andReturn().getResponse();
			
			//ASSERT
			Assertions.assertEquals(400, response.getStatus());
		}

}
