package com.intervale.test.library.service;

import com.intervale.test.library.dto.request.MagazineRequestDto;
import com.intervale.test.library.dto.response.MagazineResponseDto;
import com.intervale.test.library.exception.MagazineNotFoundException;
import com.intervale.test.library.model.Magazine;
import com.intervale.test.library.model.Publisher;
import com.intervale.test.library.repository.MagazineRepository;
import com.intervale.test.library.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MagazineService implements BaseService<MagazineResponseDto, MagazineRequestDto> {

    private final MagazineRepository magazineRepository;
    private final PublisherRepository publisherRepository;

    @Autowired
    public MagazineService(MagazineRepository magazineRepository, PublisherRepository publisherRepository) {
        this.magazineRepository = magazineRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public MagazineResponseDto save(MagazineRequestDto requestDto) {
        final Magazine magazine = requestDto.toMagazineWithoutPublisher();

        //Save publisher if nor exist
        savePublisherIfNotExist(requestDto, magazine);

        final Magazine saveMagazine = magazineRepository.save(magazine);

        return MagazineResponseDto.fromMagazine(saveMagazine);
    }

    @Override
    public MagazineResponseDto updateDescription(Long id, String description) {
        final Magazine magazine = magazineRepository.findById(id).orElseThrow(() ->
                new MagazineNotFoundException("Can't find the magazine by id: " + id));
        magazineRepository.updateDescription(description, id);
        magazine.setDescription(description);
        return MagazineResponseDto.fromMagazine(magazine);
    }

    @Override
    public MagazineResponseDto findById(Long id) {
        final Magazine magazine = magazineRepository.findById(id).orElseThrow(() ->
                new MagazineNotFoundException("Can't find the magazine by id: " + id));
        return MagazineResponseDto.fromMagazine(magazine);
    }

    @Override
    public List<MagazineResponseDto> findByDateOfPublication(String dateOfPublication) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        final LocalDate date = LocalDate.parse(dateOfPublication, formatter);
        final List<Magazine> magazines = magazineRepository.findByDateOfPublication(date);
        if (magazines.isEmpty()) {
            return null;
        }
        return magazines.stream().map(MagazineResponseDto::fromMagazine).collect(Collectors.toList());
    }

    @Override
    public List<MagazineResponseDto> findByTitle(String title) {
        final List<Magazine> magazines = magazineRepository.findByTitle(title);
        if (magazines.isEmpty()) {
            return null;
        }
        return magazines.stream().map(MagazineResponseDto::fromMagazine).collect(Collectors.toList());
    }

    @Override
    public List<MagazineResponseDto> findByDescription(String description) {
        final List<Magazine> magazines = magazineRepository.findByDescription(description);
        if (magazines.isEmpty()) {
            return null;
        }
        return magazines.stream().map(MagazineResponseDto::fromMagazine).collect(Collectors.toList());
    }

    public List<MagazineResponseDto> findByPublisher(String publisherNameOf) {
        final Optional<Publisher> publisher = publisherRepository.findByNameOf(publisherNameOf);

        if (publisher.isEmpty()) {
            return null;
        }

        final List<Magazine> magazines = publisher.get().getMagazines();

        return magazines.stream().map(MagazineResponseDto::fromMagazine).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        magazineRepository.deleteById(id);
    }

    private void savePublisherIfNotExist(MagazineRequestDto requestDto, Magazine magazine) {
        final Optional<Publisher> optionalPublisher = publisherRepository.findByNameOf(requestDto.getPublisher());

        if (optionalPublisher.isEmpty()) {
            final Publisher publisher = new Publisher();
            publisher.setNameOf(requestDto.getPublisher());
            final Publisher savePublisher = publisherRepository.save(publisher);
            magazine.setPublisher(savePublisher);
        } else {
            magazine.setPublisher(optionalPublisher.get());
        }
    }
}
