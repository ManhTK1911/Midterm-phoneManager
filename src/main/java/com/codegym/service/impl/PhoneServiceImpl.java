package com.codegym.service.impl;

import com.codegym.model.Category;
import com.codegym.model.Phone;
import com.codegym.repository.PhoneRepository;
import com.codegym.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class PhoneServiceImpl implements PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    @Override
    public Page<Phone> findAll(Pageable pageable) {
        return phoneRepository.findAll(pageable);
    }

    @Override
    public Page<Phone> findAllByNameContaining(String name, Pageable pageable) {
        return phoneRepository.findAllByNameContaining(name, pageable);
    }

    @Override
    public Page<Phone> findAllByCategory(Category category, Pageable pageable) {
        return phoneRepository.findAllByCategory(category, pageable);
    }

    @Override
    public Page<Phone> findAllByOrderByPriceAsc(Pageable pageable) {
        return phoneRepository.findAllByOrderByPriceAsc(pageable);
    }

    @Override
    public Page<Phone> findAllByOrderByPriceDesc(Pageable pageable) {
        return findAllByOrderByPriceDesc(pageable);
    }

    @Override
    public Phone findById(Long id) {
        return phoneRepository.findOne(id);
    }

    @Override
    public void save(Phone phone) {
        phoneRepository.save(phone);
    }

    @Override
    public void remove(Long id) {
        phoneRepository.delete(id);
    }

    @Override
    public Iterable<Phone> findAllByCategory(Category category) {
        return phoneRepository.findAllByCategory(category);
    }
}
