package br.com.alura.adopet.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AbrigoControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private AbrigoService abrigoService;
	
	@MockBean
	private PetService petService;
	
	 @Autowired
	 private JacksonTester<CadastroAbrigoDto> jsonCadastroAbrigoDto;
	 
	 @Autowired
	 private JacksonTester<CadastroPetDto> jsonCadastroPetDto;
	 
	 @Mock
	 private Abrigo abrigo;

	@Test
	void deveriaDevolverCodigo200ParaListarTodosAbrigos() throws Exception {
		//ACT
		var response = mvc.perform(
				get("/abrigos")
					.contentType(MediaType.APPLICATION_JSON)
					).andReturn().getResponse();
		
		//ASSERT
		Assertions.assertEquals(200, response.getStatus());
	}
	
	@Test
	void deveriaDevolverCodigo200ParaCadastrarAbrigoSemErros() throws Exception {
		//ARRANGE
		CadastroAbrigoDto dto = new CadastroAbrigoDto("Bruno", "81995588956", "exemplo@exemplo.com");
		
		//ACT
		var response = mvc.perform(
				post("/abrigos")
					.content(jsonCadastroAbrigoDto.write(dto).getJson())
					.contentType(MediaType.APPLICATION_JSON)
				
		).andReturn().getResponse();
		
		//ASSERT
		Assertions.assertEquals(200, response.getStatus());
	}
	
	@Test
	void deveriaDevolverCodigo400ParaCadastrarAbrigoComErros() throws Exception {
		//ARRANGE
		String json = """
                {
                    
                }
                """;
		
		//ACT
		var response = mvc.perform(
				post("/abrigos")
					.content(json)
					.contentType(MediaType.APPLICATION_JSON)
				
		).andReturn().getResponse();
		
		//ASSERT
		Assertions.assertEquals(400, response.getStatus());
	}
	
	@Test
	void deveriaDevolverCodigo200ParaListarPetsPorNomeDoAbrigo() throws Exception {
		//ARRANGE
		String nome = "Abrigo teste";
		
		//ACT
		MockHttpServletResponse response = mvc.perform(
                get("/abrigos/{nome}/pets", nome)
                ).andReturn().getResponse();
		
		//ASSERT
		Assertions.assertEquals(200, response.getStatus());
	}
	
	@Test
    void deveriaDevolverCodigo400ParaListarPetsPorNomeInvalido() throws Exception {
        //Arrange
        String nome = "nada";
        BDDMockito.given(abrigoService.listarPetsDoAbrigo(nome)).willThrow(ValidacaoException.class);

        //Act
        MockHttpServletResponse response = mvc.perform(
                get("/abrigos/{nome}/pets",nome)
        ).andReturn().getResponse();

        //Assert
        Assertions.assertEquals(404,response.getStatus());
    }
	
	@Test
	void deveriaDevolverCodigo200ParaListarPetsPorIdDoAbrigo() throws Exception {
		//ARRANGE
		String id = "1";
		
		//ACT
		MockHttpServletResponse response = mvc.perform(
                get("/abrigos/{id}/pets", id)
                ).andReturn().getResponse();
		
		//ASSERT
		Assertions.assertEquals(200, response.getStatus());
	}
	
	
	@Test
    void deveriaDevolverCodigo400ParaListarPetsPorIdInvalido() throws Exception {
        //Arrange
        String id = "1";
        BDDMockito.given(abrigoService.listarPetsDoAbrigo(id)).willThrow(ValidacaoException.class);

        //Act
        MockHttpServletResponse response = mvc.perform(
                get("/abrigos/{id}/pets",id)
        ).andReturn().getResponse();

        //Assert
        Assertions.assertEquals(404,response.getStatus());
    }
	
	@Test
	void deveriaDevolverCodigo200ParaCadastrarPetPeloIdDoAbrigo() throws Exception {
		//ARRANGE
		CadastroPetDto dto = new CadastroPetDto(TipoPet.GATO, "Miau", "padrao", 5, "Parda", 6.4f);

        String abrigoId = "1";
        
        BDDMockito.given(abrigoService.carregarAbrigo(abrigoId)).willReturn(abrigo);
        petService.cadastrarPet(abrigo,dto);
		
		//ACT
		MockHttpServletResponse response = mvc.perform(
                post("/abrigos/{abrigoId}/pets", abrigoId)
                		.content(jsonCadastroPetDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
		
		//ASSERT
		Assertions.assertEquals(200, response.getStatus());
	}
	
	@Test
	void deveriaDevolverCodigo200ParaCadastrarPetPeloNomeDoAbrigo() throws Exception {
		//ARRANGE
		CadastroPetDto dto = new CadastroPetDto(TipoPet.GATO, "Miau", "padrao", 5, "Parda", 6.4f);

        String abrigoNome = "Abrigo novo";
        
        BDDMockito.given(abrigoService.carregarAbrigo(abrigoNome)).willReturn(abrigo);
        petService.cadastrarPet(abrigo,dto);
		
		//ACT
		MockHttpServletResponse response = mvc.perform(
                post("/abrigos/{abrigoNome}/pets",abrigoNome)
                		.content(jsonCadastroPetDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
		
		//ASSERT
		Assertions.assertEquals(200, response.getStatus());
	}
	
	@Test
	void deveriaDevolverCodigo404ParaCadastrarPetPeloIdDoAbrigoNaoEncontrado() throws Exception {
		//ARRANGE
		String json = """
                {
                    "tipo": "GATO",
                    "nome": "Miau",
                    "raca": "padrao",
                    "idade": "5",
                    "cor" : "Parda",
                    "peso": "6.4"
                }
                """;

        String abrigoId = "1";
        
        BDDMockito.given(abrigoService.carregarAbrigo(abrigoId)).willThrow(ValidacaoException.class);
		
		//ACT
		MockHttpServletResponse response = mvc.perform(
                post("/abrigos/{abrigoId}/pets",abrigoId)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
		
		//ASSERT
		Assertions.assertEquals(404, response.getStatus());
	}
	
	@Test
	void deveriaDevolverCodigo404ParaCadastrarPetPeloNomeDoAbrigoNaoEncontrado() throws Exception {
		//ARRANGE
		String json = """
                {
                    "tipo": "GATO",
                    "nome": "Miau",
                    "raca": "padrao",
                    "idade": "5",
                    "cor" : "Parda",
                    "peso": "6.4"
                }
                """;

        String abrigoNome = "Abrigo teste";
        
        BDDMockito.given(abrigoService.carregarAbrigo(abrigoNome)).willThrow(ValidacaoException.class);
		
		//ACT
		MockHttpServletResponse response = mvc.perform(
                post("/abrigos/{abrigoId}/pets",abrigoNome)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
		
		//ASSERT
		Assertions.assertEquals(404, response.getStatus());
	}

}
