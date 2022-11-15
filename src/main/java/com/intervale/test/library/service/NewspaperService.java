package com.intervale.test.library.service;

import com.intervale.test.library.exception.NewspaperNotFoundException;
import com.intervale.test.library.model.Newspaper;
import com.intervale.test.library.model.Publisher;
import com.intervale.test.library.repository.NewspaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NewspaperService implements BaseService<Newspaper> {

    private final NewspaperRepository newspaperRepository;

    @Autowired
    public NewspaperService(NewspaperRepository newspaperRepository) {
        this.newspaperRepository = newspaperRepository;
    }

    @Override
    public Newspaper save(Newspaper newspaper) {
        return newspaperRepository.save(newspaper);
    }

    @Override
    public Newspaper updateDescription(Long id, String description) {
        newspaperRepository.updateDescription(description, id);
        return newspaperRepository.findById(id).orElseThrow(() ->
                new NewspaperNotFoundException("Can't find the newspaper by id: " + id));

    }

    @Override
    public Newspaper findById(Long id) {
        return newspaperRepository.findById(id).orElseThrow(() ->
                new NewspaperNotFoundException("Can't find the newspaper by id: " + id));
    }

    @Override
    public List<Newspaper> findByDateOfPublication(Date dateOfPublication) {
        final List<Newspaper> newspapers = newspaperRepository.findByDateOfPublication(dateOfPublication);
        if (newspapers.isEmpty()) {
            throw new NewspaperNotFoundException("Can't find the newspaper by date publication: " + dateOfPublication.toString());
        }
        return newspapers;

    }

    @Override
    public List<Newspaper> findByTitle(String title) {
        final List<Newspaper> newspapers = newspaperRepository.findByTitle(title);
        if (newspapers.isEmpty()) {
            throw new NewspaperNotFoundException("Can't find the newspaper by title: " + title);
        }
        return newspapers;

    }

    @Override
    public List<Newspaper> findByDescription(String description) {
        final List<Newspaper> newspapers = newspaperRepository.findByDescription(description);
        if (newspapers.isEmpty()) {
            throw new NewspaperNotFoundException("Can't find the newspaper by description: " + description);
        }
        return newspapers;

    }

    public Newspaper findByPublisher(Publisher publisher) {
        return newspaperRepository.findByPublisher(publisher).orElseThrow(() ->
                new NewspaperNotFoundException("Can't find the newspaper by publisher: " + publisher));
    }

    @Override
    public Newspaper deleteById(Long id) {
        final Newspaper newspaper = newspaperRepository.findById(id).orElseThrow(() ->
                new NewspaperNotFoundException("Can't find the newspaper by id: " + id));
        newspaperRepository.deleteById(id);
        return newspaper;
    }
}
