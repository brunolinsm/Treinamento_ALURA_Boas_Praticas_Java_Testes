package br.com.alura.adopet.api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {
	
	@InjectMocks
    private PetService service;

    @Mock
    private PetRepository petRepository;
    
    @Mock
    private Abrigo abrigo;
    
    @Mock
    private CadastroPetDto petDto;

    @Test
	void listarPetsDisponiveis() {
		//ACT
        service.buscarPetsDisponiveis();
        
        //ASSERT
        BDDMockito.then(petRepository).should().findAllByAdotadoFalse();
	}
    
    @Test
	void cadastrarPet() {		
    	//ACT
        service.cadastrarPet(abrigo, petDto);
        
        //ASSERT
        BDDMockito.then(petRepository).should().save(new Pet(petDto,abrigo));
    }
}
