package com.intervale.test.library.service;

import com.intervale.test.library.exception.MagazineNotFoundException;
import com.intervale.test.library.model.Magazine;
import com.intervale.test.library.model.Publisher;
import com.intervale.test.library.repository.MagazineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MagazineService implements BaseService<Magazine> {

    private final MagazineRepository magazineRepository;

    @Autowired
    public MagazineService(MagazineRepository magazineRepository) {
        this.magazineRepository = magazineRepository;
    }

    @Override
    public Magazine save(Magazine magazine) {
        return magazineRepository.save(magazine);
    }

    @Override
    public Magazine updateDescription(Long id, String description) {
        magazineRepository.updateDescription(description, id);
        return magazineRepository.findById(id).orElseThrow(() ->
                new MagazineNotFoundException("Can't find the magazine by id: " + id));

    }

    @Override
    public Magazine findById(Long id) {
        return magazineRepository.findById(id).orElseThrow(() -> new MagazineNotFoundException("Can't find the magazine by id: " + id));
    }

    @Override
    public List<Magazine> findByDateOfPublication(Date dateOfPublication) {
        final List<Magazine> magazines = magazineRepository.findByDateOfPublication(dateOfPublication);
        if (magazines.isEmpty()) {
            throw new MagazineNotFoundException("Can't find the magazine by date publication: " + dateOfPublication.toString());
        }
        return magazines;
    }

    @Override
    public List<Magazine> findByTitle(String title) {
        final List<Magazine> magazines = magazineRepository.findByTitle(title);
        if (magazines.isEmpty()) {
            throw new MagazineNotFoundException("Can't find the magazine by title: " + title);
        }
        return magazines;

    }

    @Override
    public List<Magazine> findByDescription(String description) {
        final List<Magazine> magazines = magazineRepository.findByDescription(description);
        if (magazines.isEmpty()) {
            throw new MagazineNotFoundException("Can't find the magazine by description: " + description);
        }
        return magazines;

    }

    public Magazine findByPublisher(Publisher publisher) {
        return magazineRepository.findByPublisher(publisher).orElseThrow(() ->
                new MagazineNotFoundException("Can't find the magazine by publisher: " + publisher));
    }

    @Override
    public Magazine deleteById(Long id) {
        final Magazine magazine = magazineRepository.findById(id).orElseThrow(() ->
                new MagazineNotFoundException("Can't find the magazine by id: " + id));
        magazineRepository.deleteById(id);
        return magazine;
    }
}
