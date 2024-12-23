package br.com.alura.adopet.api.service;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {
	
	@InjectMocks
    private AdocaoService service;

    @Mock
    private AdocaoRepository repository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private List<ValidacaoSolicitacaoAdocao> validacoes;

    @Mock
    private Pet pet;

    @Mock
    private Tutor tutor;

    @Mock
    private Abrigo abrigo;

    private SolicitacaoAdocaoDto dto;
	
    @Test
	void salvarAdocaoAoSolicitar() {
		//ARRANGE
		
    	//ACT
        service.solicitar(dto);
        
      //ASSERT
        BDDMockito.then(petRepository).should().save(ArgumentMatchers.any());
	}

	

}
