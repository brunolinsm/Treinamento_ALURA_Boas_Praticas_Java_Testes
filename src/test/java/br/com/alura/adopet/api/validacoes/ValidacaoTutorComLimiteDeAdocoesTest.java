package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComLimiteDeAdocoesTest {

	@InjectMocks
    private ValidacaoTutorComLimiteDeAdocoes validacao;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    @DisplayName("Não permitir solicitação de adoção de Pet se tutor atingiu 5 adoções")
	void naoPermitirSolicitacaoAdocaoPet() {
        //ARRANGE
    	BDDMockito.given(adocaoRepository.countByTutorIdAndStatus(dto.idTutor(), StatusAdocao.APROVADO)).willReturn(5);

        //ACT + ASSERT
    	 Assertions.assertThrows(ValidacaoException.class,() -> validacao.validar(dto));
    }
    
    @Test
    @DisplayName("Permitir solicitação de adoção de Pet se tutor não atingiu 5 adoções")
	void permitirSolicitacaoAdocaoPet() {
        //ARRANGE
    	BDDMockito.given(adocaoRepository.countByTutorIdAndStatus(dto.idTutor(), StatusAdocao.APROVADO)).willReturn(4);

        //ACT + ASSERT
    	Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
    }
}
