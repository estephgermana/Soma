package com.inovamed.clinical_study_system.service.research;

import com.inovamed.clinical_study_system.model.clinical_study_representative.ClinicalStudyRepresentative;
import com.inovamed.clinical_study_system.model.research.ResearchAddRepresentativeDTO;
import com.inovamed.clinical_study_system.model.research.ResearchResponseDTO;
import com.inovamed.clinical_study_system.model.research.Research;
import com.inovamed.clinical_study_system.repository.ClinicalStudyRepresentiveRepository;
import com.inovamed.clinical_study_system.repository.ResearchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddRepresentativeInResearchServiceTest {

    @Mock
    private ResearchRepository researchRepository;

    @Mock
    private ClinicalStudyRepresentiveRepository clinicalRepresentiveRepository;

    @Mock
    private ResearchDTOMapperService researchDTOMapperService;

    @InjectMocks
    private AddRepresentativeInResearchService addRepresentativeInResearchService;

    private ClinicalStudyRepresentative clinicalRepresentative;
    private ClinicalStudyRepresentative clinicalRepresentativeCurrent;
    private Research research;
    private ResearchAddRepresentativeDTO dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clinicalRepresentative = new ClinicalStudyRepresentative();
        clinicalRepresentative.setId(1L);

        clinicalRepresentativeCurrent = new ClinicalStudyRepresentative();
        clinicalRepresentativeCurrent.setId(2L);

        research = new Research();
        dto = new ResearchAddRepresentativeDTO(1L, 3L, 2L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando a pesquisa não for encontrada")
    void testExecute_ResearchNotFound() {

        when(clinicalRepresentiveRepository.findById(1L)).thenReturn(Optional.of(clinicalRepresentative));
        when(researchRepository.findById(3L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            addRepresentativeInResearchService.execute(dto);
        });

        assertEquals("Research Not Found", thrown.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o representante clínico não for encontrado")
    void testExecute_ClinicalRepresentativeNotFound() {

        when(researchRepository.findById(3L)).thenReturn(Optional.of(research));
        when(clinicalRepresentiveRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            addRepresentativeInResearchService.execute(dto);
        });

        assertEquals("Clinical Representative Not Found", thrown.getMessage());
    }

    @Test
    @DisplayName("Deve adicionar o representante clínico à pesquisa com sucesso")
    void testExecute_Success() {
        // Arrange
        when(researchRepository.findById(3L)).thenReturn(Optional.of(research));
        when(clinicalRepresentiveRepository.findById(1L)).thenReturn(Optional.of(clinicalRepresentative));
        when(clinicalRepresentiveRepository.findById(2L)).thenReturn(Optional.of(clinicalRepresentativeCurrent));

        ResearchResponseDTO expectedResponse = new ResearchResponseDTO(
                200, "Title", "Area", 100, 50, null, null,
                "Description", null, null, null,
                0, "Location", null, null, null
        );

        when(researchDTOMapperService.toDTO(any())).thenReturn(expectedResponse);

        ResearchResponseDTO response = addRepresentativeInResearchService.execute(dto);

        assertEquals(expectedResponse, response);
        assertEquals(clinicalRepresentative, research.getClinicalRepresentative());
    }
}
