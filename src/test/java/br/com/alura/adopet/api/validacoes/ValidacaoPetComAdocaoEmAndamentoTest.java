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
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetComAdocaoEmAndamentoTest {

	@InjectMocks
	private ValidacaoPetComAdocaoEmAndamento validacao;
	
	@Mock
	private AdocaoRepository adocaoRepository;
	
	@Mock
	private SolicitacaoAdocaoDto dto;

	@Test
	@DisplayName("Permitir solicitação de adoção de Pet com pedido inexistente")
	void permitirSolicitacaoAdocaoPet() {
		
		//ARRANGE
		BDDMockito.given(adocaoRepository
		        .existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(false);
		        
        //ASSERT + ACT
        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
	}
	
	@Test
	@DisplayName("Não permitir solicitação de adoção de Pet com pedido em andamento")
	void naoPermitirSolicitacaoAdocaoPet() {
		
		//ARRANGE
				BDDMockito.given(adocaoRepository
				        .existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(true);
		        
        //ASSERT + ACT
        Assertions.assertThrows(ValidacaoException.class,() -> validacao.validar(dto));
	}

}
