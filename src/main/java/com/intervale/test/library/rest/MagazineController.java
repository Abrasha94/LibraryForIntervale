package com.intervale.test.library.rest;

import com.intervale.test.library.dto.request.MagazineRequestDto;
import com.intervale.test.library.dto.response.MagazineResponseDto;
import com.intervale.test.library.exception.MagazineNotFoundException;
import com.intervale.test.library.service.MagazineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            final MagazineResponseDto responseDto = magazineService.save(requestDto);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<MagazineResponseDto> updateMagazineDescription(@PathVariable("id") Long id,
                                                                         @RequestBody String description) {
        try {
            final MagazineResponseDto responseDto = magazineService.updateDescription(id, description);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (MagazineNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<MagazineResponseDto> getMagazineById(@PathVariable("id") Long id) {
        try {
            final MagazineResponseDto responseDto = magazineService.findById(id);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (MagazineNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("date")
    public ResponseEntity<List<MagazineResponseDto>> getMagazineByDateOfPublication(@RequestParam(value = "dateOfPublication") String dateOfPublication) {
        try {
            final List<MagazineResponseDto> responseDto = magazineService.findByDateOfPublication(dateOfPublication);
            if (responseDto == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("title/{title}")
    public ResponseEntity<List<MagazineResponseDto>> getMagazineByTitle(@PathVariable("title") String title) {
        try {
            final List<MagazineResponseDto> responseDto = magazineService.findByTitle(title);
            if (responseDto == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("desc/{description}")
    public ResponseEntity<List<MagazineResponseDto>> getMagazineByDescription(@PathVariable("description") String description) {
        try {
            final List<MagazineResponseDto> responseDto = magazineService.findByDescription(description);
            if (responseDto == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("publisher/{publisherNameOf}")
    public ResponseEntity<List<MagazineResponseDto>> getMagazineByPublisher(@PathVariable("publisherNameOf") String publisherNameOf) {
        try {
            final List<MagazineResponseDto> responseDto = magazineService.findByPublisher(publisherNameOf);
            if (responseDto == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteMagazine(@PathVariable("id") Long id) {
        try {
            magazineService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
