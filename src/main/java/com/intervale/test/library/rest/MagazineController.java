package com.intervale.test.library.rest;

import com.intervale.test.library.dto.request.MagazineRequestDto;
import com.intervale.test.library.dto.response.MagazineResponseDto;
import com.intervale.test.library.exception.MagazineNotFoundException;
import com.intervale.test.library.model.Magazine;
import com.intervale.test.library.service.MagazineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/magazines/")
public class MagazineController {

    private final MagazineService magazineService;

    @Autowired
    public MagazineController(MagazineService magazineService) {
        this.magazineService = magazineService;
    }

    @PostMapping
    public ResponseEntity<MagazineResponseDto> createMagazine(@RequestBody MagazineRequestDto requestDto) {
        try {
            final Magazine magazine = magazineService.save(requestDto);
            return new ResponseEntity<>(MagazineResponseDto.fromMagazine(magazine), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Magazine Not Created", e);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<MagazineResponseDto> updateMagazineDescription(@PathVariable("id") Long id,
                                                                         @RequestBody String description) {
        try {
            final Magazine magazine = magazineService.updateDescription(id, description);
            return new ResponseEntity<>(MagazineResponseDto.fromMagazine(magazine), HttpStatus.OK);
        } catch (MagazineNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Magazine Not Updated", e);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<MagazineResponseDto> getMagazineById(@PathVariable("id") Long id) {
        try {
            final Magazine magazine = magazineService.findById(id);
            return new ResponseEntity<>(MagazineResponseDto.fromMagazine(magazine), HttpStatus.OK);
        } catch (MagazineNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Magazine Not Found", e);
        }
    }

    @GetMapping("date")
    public ResponseEntity<List<MagazineResponseDto>> getMagazineByDateOfPublication(
            @RequestParam(value = "dateOfPublication") String dateOfPublication) {
        try {
            final List<Magazine> magazines = magazineService.findByDateOfPublication(dateOfPublication);
            if (magazines == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Magazine Not Found");
            }
            return new ResponseEntity<>(magazines.stream().map(MagazineResponseDto::fromMagazine).collect(Collectors.toList()),
                    HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error On Server", e);
        }
    }

    @GetMapping("title/{title}")
    public ResponseEntity<List<MagazineResponseDto>> getMagazineByTitle(@PathVariable("title") String title) {
        try {
            final List<Magazine> magazines = magazineService.findByTitle(title);
            if (magazines == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Magazine Not Found");
            }
            return new ResponseEntity<>(magazines.stream().map(MagazineResponseDto::fromMagazine).collect(Collectors.toList()),
                    HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error On Server", e);
        }
    }

    @GetMapping("desc/{description}")
    public ResponseEntity<List<MagazineResponseDto>> getMagazineByDescription(@PathVariable("description") String description) {
        try {
            final List<Magazine> magazines = magazineService.findByDescription(description);
            if (magazines == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Magazine Not Found");
            }
            return new ResponseEntity<>(magazines.stream().map(MagazineResponseDto::fromMagazine).collect(Collectors.toList()),
                    HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error On Server", e);
        }
    }

    @GetMapping("publisher/{publisherNameOf}")
    public ResponseEntity<List<MagazineResponseDto>> getMagazineByPublisher(@PathVariable("publisherNameOf") String publisherNameOf) {
        try {
            final List<Magazine> magazines = magazineService.findByPublisher(publisherNameOf);
            if (magazines == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Magazine Not Found");
            }
            return new ResponseEntity<>(magazines.stream().map(MagazineResponseDto::fromMagazine).collect(Collectors.toList()),
                    HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error On Server", e);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteMagazine(@PathVariable("id") Long id) {
        try {
            magazineService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error On Server", e);
        }
    }
}
