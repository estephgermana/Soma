package com.inovamed.clinical_study_system.service.research;

import com.inovamed.clinical_study_system.model.research.Research;
import com.inovamed.clinical_study_system.model.research.ResearchResponseDTO;
import com.inovamed.clinical_study_system.repository.ResearchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindByIdResearchServiceTest {

    @Mock
    private ResearchRepository researchRepository;

    @Mock
    private ResearchDTOMapperService researchDTOMapperService;

    @InjectMocks
    private FindByIdResearchService findByIdResearchService;

    private Research research;
    private ResearchResponseDTO researchResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        research = new Research();
        research.setTitle("Title");
        research.setArea("Area");

        researchResponseDTO = new ResearchResponseDTO(
                research.getCode(),
                research.getTitle(),
                research.getArea(),
                research.getNumberOfPatients(),
                research.getAvailableVacancies(),
                research.getResponsibleDoctors(),
                research.getInstitutions(),
                research.getDescription(),
                research.getCriteria(),
                research.getStudyDuration(),
                research.getPhases(),
                research.getCurrentPhase(),
                research.getLocation(),
                research.getAttachments(),
                research.getPatients(),
                research.getClinicalRepresentative()
        );
    }

    @Test
    void execute_whenResearchExists() {
        when(researchRepository.findById(1L)).thenReturn(java.util.Optional.of(research));
        when(researchDTOMapperService.toDTO(research)).thenReturn(researchResponseDTO);

        ResearchResponseDTO result = findByIdResearchService.execute(1L);

        assertNotNull(result);
        assertEquals("Title", result.title());
        assertEquals("Area", result.area());

        verify(researchRepository, times(1)).findById(1L);
        verify(researchDTOMapperService, times(1)).toDTO(research);
    }

    @Test
    void execute_whenResearchDoesNotExist() {

        when(researchRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            findByIdResearchService.execute(1L);
        });

        assertEquals("Research not found.", exception.getMessage());

        verify(researchRepository, times(1)).findById(1L);
        verify(researchDTOMapperService, times(0)).toDTO(any());
    }
}
