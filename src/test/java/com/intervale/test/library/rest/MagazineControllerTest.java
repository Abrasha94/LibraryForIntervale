package com.intervale.test.library.rest;

import com.intervale.test.library.dto.request.MagazineRequestDto;
import com.intervale.test.library.model.Magazine;
import com.intervale.test.library.model.Publisher;
import com.intervale.test.library.service.MagazineService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MagazineController.class)
class MagazineControllerTest {

    @MockBean
    MagazineService magazineService;

    @Autowired
    MockMvc mockMvc;

    private final Magazine magazine = new Magazine("test", "test", LocalDate.EPOCH);

    @BeforeEach
    void setUp() {
        magazine.setId(1L);
        magazine.setPublisher(new Publisher("testP"));
        final List<Magazine> magazineList = List.of(magazine);
        when(magazineService.save(any(MagazineRequestDto.class))).thenReturn(magazine);
        when(magazineService.updateDescription(1L, "updatedTest")).thenReturn(magazine);
        when(magazineService.findById(1L)).thenReturn(magazine);
        when(magazineService.findByTitle("test")).thenReturn(magazineList);
        when(magazineService.findByDateOfPublication("2022-11-16")).thenReturn(magazineList);
        when(magazineService.findByDescription("test")).thenReturn(magazineList);
        when(magazineService.findByPublisher("test")).thenReturn(magazineList);
    }

    @Test
    void whenCreateMagazine_thenReturnRightDto() throws Exception {

        final MockHttpServletRequestBuilder request = post("/api/magazines/")
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
    void whenUpdateMagazineDescription_thenReturnRightDto() throws Exception {
        final MockHttpServletRequestBuilder request = put("/api/magazines/{id}", 1L)
                .contentType(MediaType.TEXT_HTML)
                .content("updatedTest");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("test"));
    }

    @Test
    void whenGetMagazineById_thenReturnRightDto() throws Exception {
        final MockHttpServletRequestBuilder request = get("/api/magazines/id/{id}", 1L);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("test"));
    }

    @Test
    void whenGetMagazineByDateOfPublication_thenReturnReturnRightDto() throws Exception {
        final MockHttpServletRequestBuilder request = get("/api/magazines/date")
                .param("dateOfPublication", "2022-11-16");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("test"));
    }

    @Test
    void whenGetMagazineByTitle_thenReturnReturnRightDto() throws Exception {
        final MockHttpServletRequestBuilder request = get("/api/magazines/title/{title}", "test");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("test"));
    }

    @Test
    void whenGetMagazineByDescription_thenReturnReturnRightDto() throws Exception {
        final MockHttpServletRequestBuilder request = get("/api/magazines/desc/{description}",
                "test");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("test"));
    }

    @Test
    void whenGetMagazineByAuthor_thenReturnOK() throws Exception {
        final MockHttpServletRequestBuilder request = get("/api/magazines/publisher/{publisherNameOf}",
                "test");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("test"));
    }

    @Test
    void whenDeleteMagazine_thenResponseStatusNoContent() throws Exception {
        final MockHttpServletRequestBuilder request = delete("/api/magazines/{id}", 1L);

        mockMvc.perform(request)
                .andExpect(status().isNoContent());
    }
}