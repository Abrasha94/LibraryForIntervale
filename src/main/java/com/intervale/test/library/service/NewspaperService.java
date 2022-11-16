package com.intervale.test.library.service;

import com.intervale.test.library.dto.request.NewspaperRequestDto;
import com.intervale.test.library.dto.response.NewspaperResponseDto;
import com.intervale.test.library.exception.NewspaperNotFoundException;
import com.intervale.test.library.model.Newspaper;
import com.intervale.test.library.model.Publisher;
import com.intervale.test.library.repository.NewspaperRepository;
import com.intervale.test.library.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewspaperService implements BaseService<NewspaperResponseDto, NewspaperRequestDto> {

    private final NewspaperRepository newspaperRepository;
    private final PublisherRepository publisherRepository;

    @Autowired
    public NewspaperService(NewspaperRepository newspaperRepository, PublisherRepository publisherRepository) {
        this.newspaperRepository = newspaperRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public NewspaperResponseDto save(NewspaperRequestDto requestDto) {
        final Newspaper newspaper = requestDto.toNewspaperWithoutPublisher();

        //Save publisher if nor exist
        savePublisherIfNotExist(requestDto, newspaper);

        final Newspaper saveNewspaper = newspaperRepository.save(newspaper);

        return NewspaperResponseDto.fromNewspaper(saveNewspaper);
    }

    @Override
    public NewspaperResponseDto updateDescription(Long id, String description) {
        final Newspaper newspaper = newspaperRepository.findById(id).orElseThrow(() ->
                new NewspaperNotFoundException("Can't find the newspaper by id: " + id));
        newspaperRepository.updateDescription(description, id);
        newspaper.setDescription(description);
        return NewspaperResponseDto.fromNewspaper(newspaper);
    }

    @Override
    public NewspaperResponseDto findById(Long id) {
        final Newspaper newspaper = newspaperRepository.findById(id).orElseThrow(() ->
                new NewspaperNotFoundException("Can't find the newspaper by id: " + id));
        return NewspaperResponseDto.fromNewspaper(newspaper);
    }

    @Override
    public List<NewspaperResponseDto> findByDateOfPublication(String dateOfPublication) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        final LocalDate date = LocalDate.parse(dateOfPublication, formatter);
        final List<Newspaper> newspapers = newspaperRepository.findByDateOfPublication(date);
        if (newspapers.isEmpty()) {
            return null;
        }
        return newspapers.stream().map(NewspaperResponseDto::fromNewspaper).collect(Collectors.toList());
    }

    @Override
    public List<NewspaperResponseDto> findByTitle(String title) {
        final List<Newspaper> newspapers = newspaperRepository.findByTitle(title);
        if (newspapers.isEmpty()) {
            return null;
        }
        return newspapers.stream().map(NewspaperResponseDto::fromNewspaper).collect(Collectors.toList());
    }

    @Override
    public List<NewspaperResponseDto> findByDescription(String description) {
        final List<Newspaper> newspapers = newspaperRepository.findByDescription(description);
        if (newspapers.isEmpty()) {
            return null;
        }
        return newspapers.stream().map(NewspaperResponseDto::fromNewspaper).collect(Collectors.toList());
    }

    public List<NewspaperResponseDto> findByPublisher(String publisherNameOf) {
        final Optional<Publisher> publisher = publisherRepository.findByNameOf(publisherNameOf);

        if (publisher.isEmpty()) {
            return null;
        }

        final List<Newspaper> newspapers = publisher.get().getNewspapers();

        return newspapers.stream().map(NewspaperResponseDto::fromNewspaper).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        newspaperRepository.deleteById(id);
    }

    private void savePublisherIfNotExist(NewspaperRequestDto requestDto, Newspaper newspaper) {
        final Optional<Publisher> optionalPublisher = publisherRepository.findByNameOf(requestDto.getPublisher());

        if (optionalPublisher.isEmpty()) {
            final Publisher publisher = new Publisher();
            publisher.setNameOf(requestDto.getPublisher());
            final Publisher savePublisher = publisherRepository.save(publisher);
            newspaper.setPublisher(savePublisher);
        } else {
            newspaper.setPublisher(optionalPublisher.get());
        }
    }
}
