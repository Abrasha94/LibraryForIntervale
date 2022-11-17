package com.intervale.test.library.rest;

import com.intervale.test.library.dto.request.BookRequestDto;
import com.intervale.test.library.model.Author;
import com.intervale.test.library.model.Book;
import com.intervale.test.library.service.BookService;
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
@WebMvcTest(BookController.class)
class BookControllerTest {

    @MockBean
    BookService bookService;

    @Autowired
    MockMvc mockMvc;

    private final Book book = new Book("test", "test", LocalDate.EPOCH);

    @BeforeEach
    void setUp() {
        book.setId(1L);
        book.setAuthors(List.of(new Author("testA", "testA")));
        final List<Book> bookList = List.of(book);
        when(bookService.save(any(BookRequestDto.class))).thenReturn(book);
        when(bookService.updateDescription(1L, "updatedTest")).thenReturn(book);
        when(bookService.findById(1L)).thenReturn(book);
        when(bookService.findByTitle("test")).thenReturn(bookList);
        when(bookService.findByDateOfPublication("2022-11-16")).thenReturn(bookList);
        when(bookService.findByDescription("test")).thenReturn(bookList);
        when(bookService.findByAuthor("test", "test")).thenReturn(bookList);
    }

    @Test
    void whenCreateBook_thenReturnRightDto() throws Exception {

        final MockHttpServletRequestBuilder request = post("/api/books/")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"title\":\"test\",\n" +
                        "    \"description\":\"test\",\n" +
                        "    \"dateOfPublication\":\"2022-11-17\",\n" +
                        "    \"author\":\"test\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("test"));
    }

    @Test
    void whenUpdateBookDescription_thenReturnRightDto() throws Exception {
        final MockHttpServletRequestBuilder request = put("/api/books/{id}", 1L)
                .contentType(MediaType.TEXT_HTML)
                .content("updatedTest");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("test"));
    }

    @Test
    void whenGetBookById_thenReturnRightDto() throws Exception {
        final MockHttpServletRequestBuilder request = get("/api/books/id/{id}", 1L);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("test"));
    }

    @Test
    void whenGetBookByDateOfPublication_thenReturnReturnRightDto() throws Exception {
        final MockHttpServletRequestBuilder request = get("/api/books/date")
                .param("dateOfPublication", "2022-11-16");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("test"));
    }

    @Test
    void whenGetBookByTitle_thenReturnReturnRightDto() throws Exception {
        final MockHttpServletRequestBuilder request = get("/api/books/title/{title}", "test");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("test"));
    }

    @Test
    void whenGetBookByDescription_thenReturnReturnRightDto() throws Exception {
        final MockHttpServletRequestBuilder request = get("/api/books/desc/{description}",
                "test");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("test"));
    }

    @Test
    void whenGetBookByAuthor_thenReturnOK() throws Exception {
        final MockHttpServletRequestBuilder request = get("/api/books/author/{lastname}/{firstname}",
                "test", "test");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("test"));
    }

    @Test
    void whenDeleteBook_thenResponseStatusNoContent() throws Exception {
        final MockHttpServletRequestBuilder request = delete("/api/books/{id}", 1L);

        mockMvc.perform(request)
                .andExpect(status().isNoContent());
    }
}