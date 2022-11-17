package com.intervale.test.library.rest;

import com.intervale.test.library.dto.request.NewspaperRequestDto;
import com.intervale.test.library.model.Newspaper;
import com.intervale.test.library.model.Publisher;
import com.intervale.test.library.service.NewspaperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NewspaperController.class)
class NewspaperControllerTest {

    @MockBean
    NewspaperService newspaperService;

    @Autowired
    MockMvc mockMvc;

    private final Newspaper newspaper = new Newspaper("test", "test", LocalDate.EPOCH);

    @BeforeEach
    void setUp() {
        newspaper.setId(1L);
        newspaper.setPublisher(new Publisher("testP"));
        final List<Newspaper> newspaperList = List.of(newspaper);
        when(newspaperService.save(any(NewspaperRequestDto.class))).thenReturn(newspaper);
        when(newspaperService.updateDescription(1L, "updatedTest")).thenReturn(newspaper);
        when(newspaperService.findById(1L)).thenReturn(newspaper);
        when(newspaperService.findByTitle("test")).thenReturn(newspaperList);
        when(newspaperService.findByDateOfPublication("2022-11-16")).thenReturn(newspaperList);
        when(newspaperService.findByDescription("test")).thenReturn(newspaperList);
        when(newspaperService.findByPublisher("test")).thenReturn(newspaperList);
    }

    @Test
    void whenCreateNewspaper_thenReturnRightDto() throws Exception {

        final MockHttpServletRequestBuilder request = post("/api/newspapers/")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"title\":\"test\",\n" +
                        "    \"description\":\"test\",\n" +
                        "    \"dateOfPublication\":\"2022-11-17\",\n" +
                        "    \"publisher\":\"test\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("test"));
    }

    @Test
    void whenUpdateNewspaperDescription_thenReturnRightDto() throws Exception {
        final MockHttpServletRequestBuilder request = put("/api/newspapers/{id}", 1L)
                .contentType(MediaType.TEXT_HTML)
                .content("updatedTest");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("test"));
    }

    @Test
    void whenGetNewspaperById_thenReturnRightDto() throws Exception {
        final MockHttpServletRequestBuilder request = get("/api/newspapers/id/{id}", 1L);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("test"));
    }

    @Test
    void whenGetNewspaperByDateOfPublication_thenReturnReturnRightDto() throws Exception {
        final MockHttpServletRequestBuilder request = get("/api/newspapers/date")
                .param("dateOfPublication", "2022-11-16");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("test"));
    }

    @Test
    void whenGetNewspaperByTitle_thenReturnReturnRightDto() throws Exception {
        final MockHttpServletRequestBuilder request = get("/api/newspapers/title/{title}", "test");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("test"));
    }

    @Test
    void whenGetNewspaperByDescription_thenReturnReturnRightDto() throws Exception {
        final MockHttpServletRequestBuilder request = get("/api/newspapers/desc/{description}",
                "test");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("test"));
    }

    @Test
    void whenGetNewspaperByAuthor_thenReturnOK() throws Exception {
        final MockHttpServletRequestBuilder request = get("/api/newspapers/publisher/{publisherNameOf}",
                "test");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("test"));
    }

    @Test
    void whenDeleteNewspaper_thenResponseStatusNoContent() throws Exception {
        final MockHttpServletRequestBuilder request = delete("/api/newspapers/{id}", 1L);

        mockMvc.perform(request)
                .andExpect(status().isNoContent());
    }

}