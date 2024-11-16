package com.inovamed.clinical_study_system.service.research;

import com.inovamed.clinical_study_system.model.research.Research;
import com.inovamed.clinical_study_system.model.research.ResearchRequestDTO;
import com.inovamed.clinical_study_system.model.research.ResearchResponseDTO;
import com.inovamed.clinical_study_system.repository.ResearchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateResearchServiceTest {

    @Mock
    private ResearchRepository researchRepository;

    @Mock
    private ResearchDTOMapperService researchDTOMapperService;

    @InjectMocks
    private CreateResearchService createResearchService;

    private ResearchRequestDTO researchRequestDTO;
    private ResearchResponseDTO researchResponseDTO;
    private Research research;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        researchRequestDTO = new ResearchRequestDTO(
                "Pesquisa Teste",
                "Área Teste",
                50,
                5,
                null,
                null,
                "Descrição",
                null,
                null,
                null,
                1,
                "Localização",
                null,
                1L
        );

        research = new Research();
        research.setTitle("Pesquisa Teste");
        research.setArea("Área Teste");

        // Exemplo de criação do DTO de resposta
        researchResponseDTO = new ResearchResponseDTO(
                1,  // Código
                "Pesquisa Teste",
                "Área Teste",
                50,
                5,
                null,
                null,
                "Descrição",
                null,
                null,
                null,
                1,
                "Localização",
                null,
                null,
                null
        );
    }

    @Test
    @DisplayName("Deve criar uma nova pesquisa com sucesso")
    void testExecute_Success() {
        when(researchDTOMapperService.toEntity(researchRequestDTO)).thenReturn(research);
        when(researchRepository.save(research)).thenReturn(research);
        when(researchDTOMapperService.toDTO(research)).thenReturn(researchResponseDTO);

        ResearchResponseDTO result = createResearchService.execute(researchRequestDTO);

        assertNotNull(result);
        assertEquals("Pesquisa Teste", result.title());
        assertEquals("Área Teste", result.area());
        verify(researchDTOMapperService, times(1)).toEntity(researchRequestDTO);
        verify(researchRepository, times(1)).save(research);
        verify(researchDTOMapperService, times(1)).toDTO(research);
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar uma pesquisa com título nulo")
    void testExecute_NullTitle() {
        researchRequestDTO = new ResearchRequestDTO(
                null,
                "Área Teste",
                50,
                5,
                null,
                null,
                "Descrição",
                null,
                null,
                null,
                1,
                "Localização",
                null,
                1L
        );

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            createResearchService.execute(researchRequestDTO);
        });

        assertEquals("Title cannot be null or empty", thrown.getMessage());
    }
}
