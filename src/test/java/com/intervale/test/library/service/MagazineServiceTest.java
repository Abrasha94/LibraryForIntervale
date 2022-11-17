package com.intervale.test.library.service;

import com.intervale.test.library.dto.request.MagazineRequestDto;
import com.intervale.test.library.model.Magazine;
import com.intervale.test.library.model.Publisher;
import com.intervale.test.library.repository.MagazineRepository;
import com.intervale.test.library.repository.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MagazineServiceTest {

    @InjectMocks
    private MagazineService magazineService;

    @Mock
    private MagazineRepository magazineRepository;

    @Mock
    private PublisherRepository publisherRepository;

    private final Magazine magazine = new Magazine("title", "description", LocalDate.EPOCH);
    private final Publisher publisher = new Publisher("publisher");
    private final MagazineRequestDto requestDto = new MagazineRequestDto("title", "description",
            LocalDate.EPOCH, "publisher");

    @BeforeEach
    void setUp() {
        magazine.setPublisher(publisher);
    }

    @Test
    void whenSaveMagazine_thenReturnValidDto() {
        when(magazineRepository.save(any(Magazine.class))).thenReturn(magazine);

        final Magazine savedMagazine = magazineService.save(requestDto);

        verify(magazineRepository, times(1)).save(any(Magazine.class));
        assertThat(savedMagazine.getTitle()).isEqualTo("title");
        assertThat(savedMagazine.getDescription()).isEqualTo("description");
    }

    @Test
    void WhenUpdateDescription_ThenEqualDescription() {
        when(magazineRepository.findById(1L)).thenReturn(Optional.of(magazine));

        final Magazine updateMagazine = magazineService.updateDescription(1L, "Updated description");

        verify(magazineRepository, times(1)).updateDescription("Updated description", 1L);
        assertThat(updateMagazine.getDescription()).isEqualTo("Updated description");
    }

    @Test
    void whenFindById_thenReturnValidDto() {
        when(magazineRepository.findById(1L)).thenReturn(Optional.of(magazine));

        final Magazine findedMagazine = magazineService.findById(1L);

        verify(magazineRepository, times(1)).findById(1L);
        assertThat(findedMagazine.getTitle()).isEqualTo("title");
    }

    @Test
    void whenFindByDateOfPublication_thenReturnNotEmptyList() {
        when(magazineRepository.findByDateOfPublication(any(LocalDate.class))).thenReturn(List.of(magazine));

        final List<Magazine> magazines = magazineService.findByDateOfPublication("2022-11-16");

        verify(magazineRepository, times(1)).findByDateOfPublication(any(LocalDate.class));
        assertThat(magazines).isNotEmpty();
    }

    @Test
    void whenFindByTitle_thenReturnValidDto() {
        when(magazineRepository.findByTitle("title")).thenReturn(List.of(magazine));

        final List<Magazine> magazines = magazineService.findByTitle("title");

        verify(magazineRepository, times(1)).findByTitle("title");
        assertThat(magazines.get(0).getTitle()).isEqualTo("title");
    }

    @Test
    void whenFindByDescription_thenReturnValidDto() {
        when(magazineRepository.findByDescription("description")).thenReturn(List.of(magazine));

        final List<Magazine> magazines = magazineService.findByDescription("description");

        verify(magazineRepository, times(1)).findByDescription("description");
        assertThat(magazines.get(0).getDescription()).isEqualTo("description");
    }

    @Test
    void whenFindByPublisher_thenReturnValidDto() {
        publisher.setMagazines(List.of(magazine));
        when(publisherRepository.findByNameOf("publisher")).thenReturn(Optional.of(publisher));

        final List<Magazine> magazines = magazineService.findByPublisher("publisher");

        assertThat(magazines.get(0).getTitle()).isEqualTo("title");
        assertThat(magazines.get(0).getDescription()).isEqualTo("description");
    }

    @Test
    void whenDeleteById_thenBookDeleted() {
        magazineService.deleteById(1L);

        verify(magazineRepository, times(1)).deleteById(1L);
    }
}