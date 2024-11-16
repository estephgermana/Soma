package com.inovamed.clinical_study_system.service.research;

import com.inovamed.clinical_study_system.repository.ResearchRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteByIdResearshServiceTest {

    @Mock
    private ResearchRepository researchRepository;

    @InjectMocks
    private DeleteByIdResearshService deleteByIdResearshService;

    @Test
    @DisplayName("Deve excluir uma pesquisa com sucesso")
    void testExecute_Success() {
        Long researchId = 1L;

        when(researchRepository.existsById(researchId)).thenReturn(true);
        doNothing().when(researchRepository).deleteById(researchId);
        when(researchRepository.existsById(researchId)).thenReturn(false);

        String result = deleteByIdResearshService.execute(researchId);

        assertEquals("Research " + researchId + " deleted.", result);
        verify(researchRepository, times(1)).deleteById(researchId);
        verify(researchRepository, times(1)).existsById(researchId); // Espera-se que seja chamado uma vez
    }

    @Test
    @DisplayName("Deve lançar exceção ao falhar na exclusão da pesquisa")
    void testExecute_Failure() {
        Long researchId = 1L;

        when(researchRepository.existsById(researchId)).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            deleteByIdResearshService.execute(researchId);
        });

        assertEquals("Falid deleted Research.", exception.getMessage());
        verify(researchRepository, times(1)).deleteById(researchId);
        verify(researchRepository, times(1)).existsById(researchId);
    }
}
