package com.intervale.test.library.service;

import com.intervale.test.library.dto.request.NewspaperRequestDto;
import com.intervale.test.library.dto.response.NewspaperResponseDto;
import com.intervale.test.library.model.Newspaper;
import com.intervale.test.library.model.Publisher;
import com.intervale.test.library.repository.NewspaperRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewspaperServiceTest {

    @InjectMocks
    private NewspaperService newspaperService;

    @Mock
    private NewspaperRepository newspaperRepository;

    @Mock
    private PublisherRepository publisherRepository;

    private final Newspaper newspaper = new Newspaper("title", "description", LocalDate.EPOCH);
    private final Publisher publisher = new Publisher("publisher");
    private final NewspaperRequestDto requestDto = new NewspaperRequestDto("title", "description",
            LocalDate.EPOCH, "publisher");

    @BeforeEach
    void setUp() {
        newspaper.setPublisher(publisher);
    }

    @Test
    void whenSaveNewspaper_thenReturnValidDto() {
        when(newspaperRepository.save(any(Newspaper.class))).thenReturn(newspaper);

        final NewspaperResponseDto responseDto = newspaperService.save(requestDto);

        verify(newspaperRepository, times(1)).save(any(Newspaper.class));
        assertThat(responseDto.getTitle()).isEqualTo("title");
        assertThat(responseDto.getDescription()).isEqualTo("description");
    }

    @Test
    void WhenUpdateDescription_ThenEqualDescription() {
        when(newspaperRepository.findById(1L)).thenReturn(Optional.of(newspaper));

        final NewspaperResponseDto responseDto = newspaperService.updateDescription(1L, "Updated description");

        verify(newspaperRepository, times(1)).updateDescription("Updated description", 1L);
        assertThat(responseDto.getDescription()).isEqualTo("Updated description");
    }

    @Test
    void whenFindById_thenReturnValidDto() {
        when(newspaperRepository.findById(1L)).thenReturn(Optional.of(newspaper));

        final NewspaperResponseDto responseDto = newspaperService.findById(1L);

        verify(newspaperRepository, times(1)).findById(1L);
        assertThat(responseDto.getTitle()).isEqualTo("title");
    }

    @Test
    void whenFindByDateOfPublication_thenReturnNotEmptyList() {
        when(newspaperRepository.findByDateOfPublication(any(LocalDate.class))).thenReturn(List.of(newspaper));

        final List<NewspaperResponseDto> responseDto = newspaperService.findByDateOfPublication("2022-11-16");

        verify(newspaperRepository, times(1)).findByDateOfPublication(any(LocalDate.class));
        assertThat(responseDto).isNotEmpty();
    }

    @Test
    void whenFindByTitle_thenReturnValidDto() {
        when(newspaperRepository.findByTitle("title")).thenReturn(List.of(newspaper));

        final List<NewspaperResponseDto> responseDto = newspaperService.findByTitle("title");

        verify(newspaperRepository, times(1)).findByTitle("title");
        assertThat(responseDto.get(0).getTitle()).isEqualTo("title");
    }

    @Test
    void whenFindByDescription_thenReturnValidDto() {
        when(newspaperRepository.findByDescription("description")).thenReturn(List.of(newspaper));

        final List<NewspaperResponseDto> responseDto = newspaperService.findByDescription("description");

        verify(newspaperRepository, times(1)).findByDescription("description");
        assertThat(responseDto.get(0).getDescription()).isEqualTo("description");
    }

    @Test
    void whenFindByPublisher_thenReturnValidDto() {
        publisher.setNewspapers(List.of(newspaper));
        when(publisherRepository.findByNameOf("publisher")).thenReturn(Optional.of(publisher));

        final List<NewspaperResponseDto> responseDto = newspaperService.findByPublisher("publisher");

        assertThat(responseDto.get(0).getTitle()).isEqualTo("title");
        assertThat(responseDto.get(0).getDescription()).isEqualTo("description");
    }

    @Test
    void whenDeleteById_thenBookDeleted() {
        newspaperService.deleteById(1L);

        verify(newspaperRepository, times(1)).deleteById(1L);
    }
}