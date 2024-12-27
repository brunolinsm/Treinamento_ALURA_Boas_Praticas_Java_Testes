package br.com.alura.adopet.api.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {
	
	@InjectMocks
    private TutorService service;

    @Mock
    private TutorRepository tutorRepository;
    
    @Mock
    private Tutor tutor;
    
    @Mock
    private CadastroTutorDto tutorDto;
    
    @Mock
    private AtualizacaoTutorDto atualizacaoTutorDto;

    @Test
	void naoDeveriaCadastrarTutorJaCadastrado() {
		//ARRANGE
    	BDDMockito.given(tutorRepository.existsByTelefoneOrEmail(tutorDto.telefone(), tutorDto.email())).willReturn(true);
    	
    	//ASSERT + ACT
    	 Assertions.assertThrows(ValidacaoException.class,() -> service.cadastrar(tutorDto));
	}
    
    @Test
	void permiteCadastroTutorAindaNaoCadastrado() {
		//ARRANGE
    	BDDMockito.given(tutorRepository.existsByTelefoneOrEmail(tutorDto.telefone(), tutorDto.email())).willReturn(false);
    	
    	//ASSERT + ACT
    	Assertions.assertDoesNotThrow(() -> service.cadastrar(tutorDto));
	}
    
    @Test
  	void deveriaCadastrarTutor() {		
    	//ACT
        service.cadastrar(tutorDto);
          
        //ASSERT
        BDDMockito.then(tutorRepository).should().save(new Tutor(tutorDto));
      }
    
    @Test
	void deveriaAtualizarTutor() {	
    	//ARRANGE
	 	BDDMockito.given(tutorRepository.getReferenceById(atualizacaoTutorDto.id())).willReturn(tutor);
    	
    	//ACT
        service.atualizar(atualizacaoTutorDto);
        
        //ASSERT
        BDDMockito.then(tutor).should().atualizarDados(atualizacaoTutorDto);
    }

}
