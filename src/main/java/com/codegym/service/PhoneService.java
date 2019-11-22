package com.codegym.service;

import com.codegym.model.Category;
import com.codegym.model.Phone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PhoneService  {
    Page<Phone> findAll(Pageable pageable);
    Page<Phone> findAllByNameContaining(String name, Pageable pageable);
    Page<Phone> findAllByCategory(Category category, Pageable pageable);
    Page<Phone> findAllByOrderByPriceAsc(Pageable pageable);
    Page<Phone> findAllByOrderByPriceDesc(Pageable pageable);
    Phone findById(Long id);
    void save(Phone phone);
    void remove(Long id);
    Iterable<Phone> findAllByCategory(Category category);
}
