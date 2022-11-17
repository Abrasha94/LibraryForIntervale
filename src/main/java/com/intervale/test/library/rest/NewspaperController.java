package com.intervale.test.library.rest;

import com.intervale.test.library.dto.request.NewspaperRequestDto;
import com.intervale.test.library.dto.response.NewspaperResponseDto;
import com.intervale.test.library.exception.NewspaperNotFoundException;
import com.intervale.test.library.model.Newspaper;
import com.intervale.test.library.service.NewspaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/newspapers/")
public class NewspaperController {

    private final NewspaperService newspaperService;

    @Autowired
    public NewspaperController(NewspaperService newspaperService) {
        this.newspaperService = newspaperService;
    }

    @PostMapping
    public ResponseEntity<NewspaperResponseDto> createNewspaper(@RequestBody NewspaperRequestDto requestDto) {
        try {
            final Newspaper newspaper = newspaperService.save(requestDto);
            return new ResponseEntity<>(NewspaperResponseDto.fromNewspaper(newspaper), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Newspaper Not Created", e);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<NewspaperResponseDto> updateMagazineDescription(@PathVariable("id") Long id,
                                                                          @RequestBody String description) {
        try {
            final Newspaper newspaper = newspaperService.updateDescription(id, description);
            return new ResponseEntity<>(NewspaperResponseDto.fromNewspaper(newspaper), HttpStatus.OK);
        } catch (NewspaperNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Newspaper Not Updated", e);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<NewspaperResponseDto> getNewspaperById(@PathVariable("id") Long id) {
        try {
            final Newspaper newspaper = newspaperService.findById(id);
            return new ResponseEntity<>(NewspaperResponseDto.fromNewspaper(newspaper), HttpStatus.OK);
        } catch (NewspaperNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Newspaper Not Found", e);
        }
    }

    @GetMapping("date")
    public ResponseEntity<List<NewspaperResponseDto>> getNewspaperByDateOfPublication(
            @RequestParam(value = "dateOfPublication") String dateOfPublication) {
        try {
            final List<Newspaper> newspapers = newspaperService.findByDateOfPublication(dateOfPublication);
            if (newspapers == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Newspaper Not Found");
            }
            return new ResponseEntity<>(newspapers.stream().map(NewspaperResponseDto::fromNewspaper).collect(Collectors.toList()),
                    HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error On Server", e);
        }
    }

    @GetMapping("title/{title}")
    public ResponseEntity<List<NewspaperResponseDto>> getNewspaperByTitle(@PathVariable("title") String title) {
        try {
            final List<Newspaper> newspapers = newspaperService.findByTitle(title);
            if (newspapers == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Newspaper Not Found");
            }
            return new ResponseEntity<>(newspapers.stream().map(NewspaperResponseDto::fromNewspaper).collect(Collectors.toList()),
                    HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error On Server", e);
        }
    }

    @GetMapping("desc/{description}")
    public ResponseEntity<List<NewspaperResponseDto>> getNewspaperByDescription(@PathVariable("description") String description) {
        try {
            final List<Newspaper> newspapers = newspaperService.findByDescription(description);
            if (newspapers == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Newspaper Not Found");
            }
            return new ResponseEntity<>(newspapers.stream().map(NewspaperResponseDto::fromNewspaper).collect(Collectors.toList()),
                    HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error On Server", e);
        }
    }

    @GetMapping("publisher/{publisherNameOf}")
    public ResponseEntity<List<NewspaperResponseDto>> getNewspaperByPublisher(@PathVariable("publisherNameOf") String publisherNameOf) {
        try {
            final List<Newspaper> newspapers = newspaperService.findByPublisher(publisherNameOf);
            if (newspapers == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Newspaper Not Found");
            }
            return new ResponseEntity<>(newspapers.stream().map(NewspaperResponseDto::fromNewspaper).collect(Collectors.toList()),
                    HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error On Server", e);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteNewspaper(@PathVariable("id") Long id) {
        try {
            newspaperService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error On Server", e);
        }
    }
}
