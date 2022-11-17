package com.intervale.test.library.service;

import com.intervale.test.library.dto.request.NewspaperRequestDto;
import com.intervale.test.library.exception.NewspaperNotFoundException;
import com.intervale.test.library.model.Newspaper;
import com.intervale.test.library.model.Publisher;
import com.intervale.test.library.repository.NewspaperRepository;
import com.intervale.test.library.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class NewspaperService implements BaseService<Newspaper, NewspaperRequestDto> {

    private final NewspaperRepository newspaperRepository;
    private final PublisherRepository publisherRepository;

    @Autowired
    public NewspaperService(NewspaperRepository newspaperRepository, PublisherRepository publisherRepository) {
        this.newspaperRepository = newspaperRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public Newspaper save(NewspaperRequestDto requestDto) {
        final Newspaper newspaper = requestDto.toNewspaperWithoutPublisher();

        //Save publisher if nor exist
        savePublisherIfNotExist(requestDto, newspaper);

        return newspaperRepository.save(newspaper);
    }

    @Override
    public Newspaper updateDescription(Long id, String description) {
        final Newspaper newspaper = newspaperRepository.findById(id).orElseThrow(() ->
                new NewspaperNotFoundException("Can't find the newspaper by id: " + id));
        newspaperRepository.updateDescription(description, id);
        newspaper.setDescription(description);
        return newspaper;
    }

    @Override
    @Cacheable("newspapers")
    public Newspaper findById(Long id) {
        return newspaperRepository.findById(id).orElseThrow(() ->
                new NewspaperNotFoundException("Can't find the newspaper by id: " + id));
    }

    @Override
    public List<Newspaper> findByDateOfPublication(String dateOfPublication) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        final LocalDate date = LocalDate.parse(dateOfPublication, formatter);
        final List<Newspaper> newspapers = newspaperRepository.findByDateOfPublication(date);
        if (newspapers.isEmpty()) {
            return null;
        }
        return newspapers;
    }

    @Override
    public List<Newspaper> findByTitle(String title) {
        final List<Newspaper> newspapers = newspaperRepository.findByTitle(title);
        if (newspapers.isEmpty()) {
            return null;
        }
        return newspapers;
    }

    @Override
    public List<Newspaper> findByDescription(String description) {
        final List<Newspaper> newspapers = newspaperRepository.findByDescription(description);
        if (newspapers.isEmpty()) {
            return null;
        }
        return newspapers;
    }

    public List<Newspaper> findByPublisher(String publisherNameOf) {
        final Optional<Publisher> publisher = publisherRepository.findByNameOf(publisherNameOf);

        if (publisher.isEmpty()) {
            return null;
        }

        return publisher.get().getNewspapers();
    }

    @Override
    @CacheEvict("newspapers")
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
