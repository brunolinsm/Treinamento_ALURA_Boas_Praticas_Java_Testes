package br.com.alura.adopet.api.validacoes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetDisponivelTest {
	
	@InjectMocks
	private ValidacaoPetDisponivel validacao;
	
	@Mock
	private PetRepository petRepository;
	
	@Mock
	private SolicitacaoAdocaoDto dto;

	@Mock
	private Pet pet;

	@Test
	@DisplayName("Permitir solicitação de adoção de Pet")
	void permitirSolicitacaoAdocaoPet() {
		
		//ARRANGE
		BDDMockito.given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
		BDDMockito.given(pet.getAdotado()).willReturn(false);
		        
        //ASSERT + ACT
        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
	}
	
	@Test
	@DisplayName("Não permitir solicitação de adoção de Pet")
	void naoPermitirSolicitacaoAdocaoPet() {
		
		//ARRANGE
		BDDMockito.given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
		BDDMockito.given(pet.getAdotado()).willReturn(true);
		        
        //ASSERT + ACT
        Assertions.assertThrows(ValidacaoException.class,() -> validacao.validar(dto));
	}

}
