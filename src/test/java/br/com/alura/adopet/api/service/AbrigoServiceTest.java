package br.com.alura.adopet.api.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {
	
	@InjectMocks
    private AbrigoService service;

    @Mock
    private AbrigoRepository abrigoRepository;

    @Mock
    private PetRepository petRepository;
    
    @Mock
    private Abrigo abrigo;
    
    @Mock
    private CadastroAbrigoDto abrigoDto;
    
    @Captor
    private ArgumentCaptor<Abrigo> abrigoCaptor;

	@Test
	void deveriaListarTodosAbrigos() {
		//ACT
        service.listar();
        
        //ASSERT
        BDDMockito.then(abrigoRepository).should().findAll();
	}
	
	 @Test
		void verificaAbrigoJaCadastrado() {
			//ARRANGE
	    	BDDMockito.given(abrigoRepository.existsByNomeOrTelefoneOrEmail(abrigoDto.nome(), abrigoDto.telefone(), abrigoDto.email())).willReturn(true);
	    	
	    	//ASSERT + ACT
	    	 Assertions.assertThrows(ValidacaoException.class,() -> service.cadastrar(abrigoDto));
		}
	 
	 @Test
		void permiteCadastroAbrigoAindaNaoCadastrado() {
			//ARRANGE
	    	BDDMockito.given(abrigoRepository.existsByNomeOrTelefoneOrEmail(abrigoDto.nome(), abrigoDto.telefone(), abrigoDto.email())).willReturn(false);
	    	
	    	//ASSERT + ACT
	    	Assertions.assertDoesNotThrow(() -> service.cadastrar(abrigoDto));
		}
	 
	 @Test
		void cadastrarAbrigo() {		
	    	//ACT
	        service.cadastrar(abrigoDto);
	        
	        //ASSERT
	        BDDMockito.then(abrigoRepository).should().save(abrigoCaptor.capture());
	        Abrigo abrigoSalvo = abrigoCaptor.getValue();
	        Assertions.assertEquals(abrigoDto.nome(), abrigoSalvo.getNome());
	        Assertions.assertEquals(abrigoDto.telefone(), abrigoSalvo.getTelefone());
	        Assertions.assertEquals(abrigoDto.email(), abrigoSalvo.getEmail());
	 }
	 
	 @Test
		void listarPetsDoAbrigoPorNome() {
		 	//ARRANGE
		 	BDDMockito.given(abrigoRepository.findByNome(abrigoDto.nome())).willReturn(Optional.of(abrigo));
		 	
			//ACT
	        service.listarPetsDoAbrigo(abrigoDto.nome());
	        
	        //ASSERT
	        BDDMockito.then(petRepository).should().findByAbrigo(abrigo);
	}
	 
	 @Test
		void permiteCarregarAbrigoExistentePorNome() {
		 	//ARRANGE
	    	BDDMockito.given(abrigoRepository.findByNome(abrigoDto.nome())).willReturn(Optional.of(abrigo));
	    	
	    	//ASSERT + ACT
	    	Assertions.assertDoesNotThrow(() -> service.carregarAbrigo(abrigoDto.nome()));
		}
	 
	 @Test
		void naoPermiteCarregarAbrigoInexistentePorNome() {
		 	//ASSERT + ACT
	    	Assertions.assertThrows(ValidacaoException.class,() -> service.carregarAbrigo("abrigo teste"));
		}
}
