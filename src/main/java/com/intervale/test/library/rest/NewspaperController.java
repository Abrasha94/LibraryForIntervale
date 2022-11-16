package com.intervale.test.library.rest;

import com.intervale.test.library.dto.request.NewspaperRequestDto;
import com.intervale.test.library.dto.response.NewspaperResponseDto;
import com.intervale.test.library.exception.NewspaperNotFoundException;
import com.intervale.test.library.service.NewspaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            final NewspaperResponseDto responseDto = newspaperService.save(requestDto);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<NewspaperResponseDto> updateMagazineDescription(@PathVariable("id") Long id,
                                                                         @RequestBody String description) {
        try {
            final NewspaperResponseDto responseDto = newspaperService.updateDescription(id, description);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (NewspaperNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<NewspaperResponseDto> getNewspaperById(@PathVariable("id") Long id) {
        try {
            final NewspaperResponseDto responseDto = newspaperService.findById(id);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (NewspaperNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("date")
    public ResponseEntity<List<NewspaperResponseDto>> getNewspaperByDateOfPublication(@RequestParam(value = "dateOfPublication") String dateOfPublication) {
        try {
            final List<NewspaperResponseDto> responseDto = newspaperService.findByDateOfPublication(dateOfPublication);
            if (responseDto == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("title/{title}")
    public ResponseEntity<List<NewspaperResponseDto>> getNewspaperByTitle(@PathVariable("title") String title) {
        try {
            final List<NewspaperResponseDto> responseDto = newspaperService.findByTitle(title);
            if (responseDto == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("desc/{description}")
    public ResponseEntity<List<NewspaperResponseDto>> getNewspaperByDescription(@PathVariable("description") String description) {
        try {
            final List<NewspaperResponseDto> responseDto = newspaperService.findByDescription(description);
            if (responseDto == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("publisher/{publisherNameOf}")
    public ResponseEntity<List<NewspaperResponseDto>> getNewspaperByPublisher(@PathVariable("publisherNameOf") String publisherNameOf) {
        try {
            final List<NewspaperResponseDto> responseDto = newspaperService.findByPublisher(publisherNameOf);
            if (responseDto == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteNewspaper(@PathVariable("id") Long id) {
        try {
            newspaperService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
